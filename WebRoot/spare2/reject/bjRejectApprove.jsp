<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-12-15
  Time: 22:04:00
--%>
<html>
<head><title>备件报废审批页面</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>

</head>
<body topMargin=1 leftMargin=1>
<jsp:include page="/message/MessageProcess"/>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute("AIT_LINES");
    //  System.out.print("size:"+set.getSize()+"\n");

%>
<form action="/servlet/com.sino.ams.spare2.reject.servlet.AmsBjRejectServlet" name="mainForm" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1" style="border-collapse: collapse">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="22">
                    <td width="10%" align="right">报废单据：</td>
                    <td width="15%"><%=dto.getTransNo()%></td>
                    <td width="10%" align="right">创建人：</td>
                    <td width="10%"><%=dto.getCreatedUser()%></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="10%"><%=dto.getCreationDate()%></td>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="10%"><%=dto.getTransStatusName()%></td>
                </tr>
                <tr height="50">
                    <td align="right">备注：</td>
                    <td colspan="7"><%=dto.getRemark()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <%
            if(dto.getTransStatus().equals(DictConstant.IN_PROCESS)){
        %>
        <img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">
        <img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve2();">
        <%
            }
        %>
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" id="img5" onClick="viewFlow();">
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array( "部件号", "设备名称", "规格型号", "现有量", "报废数量");
        var widthArr = new Array( "12%", "15%", "20%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="width:1010px;overflow-y:scroll;height:500px">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
            <%
                if (set != null && !set.isEmpty()) {
                    for (int i = 0; i < set.getSize(); i++) {
                        AmsItemTransLDTO lineDto = (AmsItemTransLDTO) set.getDTO(i);
                        System.out.println("2");

            %>
            <tr height=20px onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width="12%"><%=lineDto.getBarcode()%>
                </td>
                <td width="15%" name="itemName" id="itemName<%=i%>"><%=lineDto.getItemName()%>
                </td>
                <td width="20%" name="itemSpec" id="itemSpec<%=i%>"><%=lineDto.getItemSpec()%>
                </td>
                <td width="8%" align="right"><%=lineDto.getOnhandQty()%>
                </td>
                <td width="8%" align="right"><%=lineDto.getQuantity()%>
                </td>
                <td width=1% style="display:none"><input type="hidden" name="itemCode" id="itemCode<%=i%>"
                                                         value="<%=lineDto.getItemCode()%>"></td>

            </tr>
            <%
                    }
                }
                System.out.println("333333");

            %>
        </table>
    </div>
</fieldset>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transNo" value="<%=dto.getTransNo()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="objectCategory" value="76">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="flag" value="">
</form>
</body>
</html>
<script type="text/javascript">
    function do_Approve(flag) {
        document.mainForm.flag.value = flag;
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = "<%=user.getCurrGroupId()%>";
        paramObj.procdureName = "报废流程";
        paramObj.flowCode = "";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    }
    function do_Approve2() {
        mainForm.act.value="<%=WebActionConstant.REJECT_ACTION%>";
        addApproveContent();
        mainForm.submit();
    }
    function submitH() {
        var flag = document.mainForm.flag.value;
        var actVal = "";
        if (flag == 1) {
            actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
        } else if (flag == 2) {
            actVal = "<%=WebActionConstant.REJECT_ACTION%>";
        } else {
            actVal = "<%=WebActionConstant.RECEIVE_ACTION%>";
        }
        document.mainForm.act.value = actVal;
        document.mainForm.submit();
    }
    function do_Approve1() {
        document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        document.mainForm.submit();
    }
</script>