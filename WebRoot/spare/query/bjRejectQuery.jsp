<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotDDto" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-12-20
  Time: 14:41:47
--%>
<html>
<head><title>报废单据详细页面</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>

</head>
<body topMargin=1 leftMargin=1>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.ALLOT_H_DTO);
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
    //  System.out.print("size:"+set.getSize()+"\n");
//    System.out.println("666666666");
%>
<form action="/servlet/com.sino.ams.spare.reject.servlet.AmsBjRejectServlet" name="mainForm" method="post">
    <jsp:include page="/flow/include.jsp" flush="true"/>
    <table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1" style="border-collapse: collapse">
        <tr>
            <td>
                <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                    <tr height="24">
                        <td width="10%" align="right">报废单据：</td>
                        <td width="15%"><%=dto.getTransNo()%>
                        </td>
                        <td width="10%" align="right">创建人：</td>
                        <td width="10%"><%=dto.getCreatedUser()%>
                        </td>
                        <td width="10%" align="right">创建时间：</td>
                        <td width="8%"><%=dto.getCreationDate()%>
                        </td>
                        <td width="10%" align="right">单据状态：</td>
                        <td width="10%"><%=dto.getTransStatusName()%>
                        </td>
                    </tr>
                    <tr height="50">
                        <td align="right">备注：</td>
                        <td colspan="6"><%=dto.getRemark()%>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <fieldset>
        <legend>
            <img src="/images/button/viewFlow.gif" alt="查阅流程" onClick="viewFlow();">
            <img src="/images/button/viewOpinion.gif" alt="查阅意见" onClick="viewOpinion();">
            <img src="/images/button/close.gif" alt="关闭" onclick="window.close()">
        </legend>
        <script type="text/javascript">
            var columnArr = new Array("部件号", "设备名称", "规格型号", "数量");
            var widthArr = new Array("15%", "22%", "25%", "10%");
            printTableHead(columnArr, widthArr);
        </script>

        <div style="width:100%;overflow-y:scroll;height:500px">
            <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="2" cellspacing="0">
                <%
                    if (set != null && !set.isEmpty()) {

                    for (int i = 0; i < set.getSize(); i++) {
                        AmsBjsAllotDDto dto1 = (AmsBjsAllotDDto) set.getDTO(i);
                %>
                <tr height=20px onMouseMove="style.backgroundColor='#EFEFEF'"
                    onMouseOut="style.backgroundColor='#FFFFFF'">
                    <td width="15%"><%=dto1.getBarcode()%>
                    </td>
                    <td width="22%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
                    </td>
                    <td width="25%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
                    </td>
                    <td align="right" width="10%"><%=dto1.getQuantity()%>
                    </td>
                    <td width=1% style="display:none"><input type="hidden" name="itemCode" id="itemCode<%=i%>"
                                                             value="<%=dto1.getItemCode()%>"></td>

                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </fieldset>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
    <input type="hidden" name="transId" value="<%=dto.getTransId()%>">
    <input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
    <input type="hidden" name="objectCategory" value="76">

</form>
</body>
</html>