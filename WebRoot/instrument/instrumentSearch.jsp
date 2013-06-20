<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentInfoDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-29
  Time: 0:06:42
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>仪器仪表统计页面</title>
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
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
%>
<jsp:include page="/message/MessageProcess"/>
<body onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentSearchServlet" name="mainForm" method="post">
    <script type="text/javascript">
    printTitleBar("仪器仪表借用查询")
    </script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td align="right" width="20%">仪器名称：</td>
            <td align="left" width="20%"><input type="text" name="itemName" value="<%=StrUtil.nullToString(request.getParameter("itemName"))%>" style="width:80%" ></td>
            <td align="right" width="20%">仪器条码：</td>
            <td align="left" width="20%"><input type="text" name="barcode" value="<%=StrUtil.nullToString(request.getParameter("barcode"))%>" style="width:80%" ></td>
            <td width="20%"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export();"></td>
        </tr>
        <tr>
            <td align="right">借用人：</td>
            <td align="left"><input type="text" name="borrowName" value="<%=StrUtil.nullToString(request.getParameter("borrowName"))%>" style="width:80%" ></td>
            <td align="right">逾期未归还：</td>
            <td align="left" width="20%"><select name = "borrowNum" width="100%"><option value= "">--否--</option><option value="Y" <%=StrUtil.nullToString(request.getParameter("borrowNum")).equals("Y")? "selected" : ""%>>--是--</option></select></td>
            <td><img src="/images/eam_images/search.jpg" alt="查询设备"  onClick="do_Search(); return false;"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="barcodeNo">
    <input type="hidden" name="itemCategory">
      <script type="text/javascript">
        var columnArr = new Array("仪器仪表条码", "仪器仪表名称", "仪器仪表类型","借用人","责任人","供应商","借用日期","已借用(天)");
        var widthArr = new Array("10%","15%","7%","7%","7%","20%","20%","7%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="noHandTR" >
                 <%--onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>','<%=row.getValue("ITEM_CATEGORY")%>')"--%>
                <input type="hidden" name="barcode">
                <td height="22" width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td height="22" width="15%" align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td height="22" width="7%" align="center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                 <td height="22" width="7%"><%=row.getValue("MAINTAIN_USER")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("RESPONSIBILITY_NAME")%>
                </td>
                <td height="22" width="20%"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td height="22" width="20%" align="center"><%=row.getValue("BORROW_DATE")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("DAYS")%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</form>
<%
    }
%>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
</html>
<script type="text/javascript">
function do_Search() {
    with (mainForm) {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        act.value = "<%=AMSActionConstant.STATISTICS_ACTION%>";
        submit();
    }
}
function do_Create(itemCategory) {
    var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet?act=<%=WebActionConstant.NEW_ACTION%>&itemCategory=" + itemCategory ;
    var popscript = 'width=500,height=400,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'planWin', popscript);
}
function do_export() {
    mainForm.act.value = "EXPORT_ACTION2";
    mainForm.submit();
}
function do_ShowDetail(barcode, itemCategory) {
    mainForm.barcode.value = barcode;
    mainForm.itemCategory.value = itemCategory;
    var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentSearchServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=" + barcode + "&itemCategory=" + itemCategory;
    var popscript = 'width=500,height=400,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'planWin', popscript);
}
</script>
