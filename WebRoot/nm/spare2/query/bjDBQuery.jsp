<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-12-21
  Time: 16:33:09
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件调拨单</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
</head>
<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    RequestParser rp = new RequestParser();
    rp.transData(request);
    String transType = rp.getParameter("transType");
    String sectionRight = rp.getParameter("sectionRight");
    String divHeight = "500";
    if (sectionRight.equals("OUT")) {
        divHeight = "200";
    }
    String itemCodes = "";
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.BjdbApproveServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">调出地市：</td>
                    <td width="25%"><%=amsItemTransH.getFromOrganizationName()%>
                    </td>
                    <td width="9%" align="right">调入地市：</td>
                    <td width="25%"><%=amsItemTransH.getToOrganizationName()%>
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
                    <td colspan="6"><%=amsItemTransH.getRemark()%>
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
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("设备名称", "规格型号", "数量");
        var widthArr = new Array("22%", "25%", "18%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:<%=divHeight%>px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="xhTable" cellpadding="2" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                RowSet details = (RowSet) request.getAttribute("AIT_DETAILS");
                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr id="xhTr<%=i%>" height="20" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'" onclick="this.cells[0].childNodes[0].checked=true;">
                <td width="22%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="25%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="18%"><%=row.getValue("QUANTITY")%>
                </td>
                <td style="display:none">
                    <input type="hidden" name="xhlineId" id="xhlineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="xhItemCode" id="xhItemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<%
    if (sectionRight.equals("OUT")) {
%>
<fieldset>
    <legend>设备条码
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/button/deleteLine.gif" alt="删除行"
             onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
        <%--<img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve(2);">--%>
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("checkbox", "部件号", "设备名称", "规格型号", "现有量", "调拨数量");
        var widthArr = new Array("2%", "12%", "15%", "25%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:250px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode" id="barcode0"
                                                                            value=""
                                                                            readonly class="noborderGray"
                                                                            style="width:100%;text-align:center">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="25%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="onhandQty" id="onhandQty0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="allocateQty"
                                                                           id="allocateQty0"
                                                                           value=""
                                                                           class="noborderYellow"
                                                                           onblur="checkQty(this);"
                                                                           style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                    <input type="hidden" name="objectNo" id="objectNo0" value="">
                </td>
            </tr>
            <%

                if (details != null && !details.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < details.getSize(); i++) {
                        row = details.getRow(i);
                        if (row.getValue("QUANTITY") == null || row.getValue("QUANTITY").equals("")) {
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
                <td width="25%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="onhandQty"
                                                                           id="onhandQty<%=i%>"
                                                                           value="<%=row.getValue("ONHAND_QTY")%>"
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="allocateQty"
                                                                           id="allocateQty<%=i%>"
                                                                           value="<%=row.getValue("ALLOCATE_QTY")%>"
                                                                           class="noborderYellow"
                                                                           style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<%
    }
%>
</form>
</body>
<script type="text/javascript">
    function init() {
    }

</script>
</html>