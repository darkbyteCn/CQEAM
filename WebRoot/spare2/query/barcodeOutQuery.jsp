<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2007-12-24
  Time: 10:10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
<title>条码设备出库</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/Constant.js"></script>
<script language="javascript" src="/WebLibary/js/calendar.js"></script>
<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
<script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
<script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<script language="javascript" src="/WebLibary/js/json.js"></script>
<script language="javascript" src="/flow/flow.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<jsp:include page="/message/MessageProcess"/>
<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute("AMS_ITEMH_REPAIR");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    RequestParser rp = new RequestParser();
    rp.transData(request);

%>
<form name="mainForm" action="/servlet/com.sino.ams.others.servlet.BarcodeOutServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=DictConstant.TMCK%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=user.getOrganizationId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>

                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%>
                    </td>
                </tr>
                 <tr height="50">
                        <td align="right">备注：</td>
                        <td colspan="6"><%=amsItemTransH.getRemark()%></td>
                    </tr>
            </table>
        </td>
    </tr>
</table>


<fieldset>
    <legend>标签号
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("checkbox", "标签号", "设备名称", "规格型号");
        var widthArr = new Array("2%", "12%", "15%", "15%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <tr id="mainTr0" style="display:none">
                <%if (rows == null || rows.isEmpty()) {%>
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode" id="barcode0"
                                                                            value=""
                                                                            readonly class="noborderGray"
                                                                            style="width:100%;text-align:center">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                </td>
            </tr>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);

            %>
            <tr id="mainTr<%=i%>">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode"
                                                                            id="barcode<%=i%>"
                                                                            value="<%=row.getValue("BARCODE")%>"
                                                                            readonly class="noborderGray"
                                                                            style="width:100%;text-align:center">
                </td>
                <td width="15%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>

                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
</form>
</body>
</html>