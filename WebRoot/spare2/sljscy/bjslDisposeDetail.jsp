<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-28
  Time: 16:10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件申领处理情况信息</title>
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
</head>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="1">
<%
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.bjslcy.servlet.AmsBjSlDisposeServlet" method="post">
    <jsp:include page="/flow/include.jsp" flush="true"/>
    <input type="hidden" name="act" value="">
    <table border="0" bordercolor="#9FD6FF" class="detailHeader" id="table1">
        <tr>
            <td>
                <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                    <tr height="22">
                        <td width="15%" align="right" >单据编号：</td>
                        <td width="15%"><%=amsItemTransH.getTransNo()%>
                        </td>
                        <td width="10%" align="right">创建人：</td>
                        <td width="10%"><%=amsItemTransH.getCreatedUser()%>
                        </td>
                         <td width="10%" align="right">单据状态：</td>
                        <td width="10%"><%=amsItemTransH.getTransStatusName()%>
                        </td>
                    </tr>
                    <tr height="22">

                        <td align="right">申请公司：</td>
                        <td><%=amsItemTransH.getToOrganizationName()%>
                        </td>
                         <td width="10%" align="right">创建时间：</td>
                        <td width="8%"><%=amsItemTransH.getCreationDate()%>
                        </td>
                    </tr>

                </table>
            </td>
        </tr>
    </table>
    <fieldset>
        <legend>
            <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
        </legend>
        <script type="text/javascript">
            var columnArr = new Array("设备名称", "规格型号", "数量");
            var widthArr = new Array("12%", "15%", "8%");
            printTableHead(columnArr, widthArr);
        </script>
        <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
            <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
                <%
                    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
                    if (rows != null && !rows.isEmpty()) {

                        Row row = null;
                        for (int i = 0; i < rows.getSize(); i++) {
                            row = rows.getRow(i);
                %>
                <tr onclick="do_show('<%=row.getValue("ITEM_CODE")%>','<%=row.getValue("TRANS_ID")%>')" height="20">
                    <td width="12%"><%=row.getValue("ITEM_NAME")%>
                    </td>
                    <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                    </td>
                    <td width="8%"><%=row.getValue("QUANTITY")%>
                    </td>
                    <td style="display:none">
                        <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">
                        <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </fieldset>
    <input type="hidden" name="objectCategory" value="73">
    <input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
    <input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
    <input type="hidden" name="addressId" value="<%=amsItemTransH.getAddressId()%>">
    <input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
    <input type="hidden" name="itemCode">
    <input type="hidden" name="lineId">
</form>

</body>
</html>
<script type="text/javascript">
    function do_show(itemCode, transId) {
        mainForm.act.value = "show";
        var url = "/servlet/com.sino.ams.spare2.bjslcy.servlet.AmsBjSlDisposeServlet?act=" + mainForm.act.value + "&transId=" + transId + "&itemCode=" + itemCode;
        var popscript = "center:yes;dialogwidth:800px;dialogheight:400px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
        window.showModalDialog(url, null, popscript);
        //        window.open(url, "instrum", popscript);
    }
</script>