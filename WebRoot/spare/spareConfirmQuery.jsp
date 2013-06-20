<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsSpareCategoryDTO"%>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>备件分类确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	boolean hasData = (rows != null && rows.getSize() > 0);
    Row row = null;
%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_search()')" onload="initPage();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.spare.servlet.SpareConfirmServlet">
<script type="text/javascript">
    printTitleBar("备件分类确认");
</script>
<table width="100%" topmargin="0" border="0" bgcolor="#eeeeee">
        <input type="hidden" name="act">
        <tr>
            <td width="10%" align="right">备件ID：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="barcode" value="<%=dto.getBarcode()%>"></td>
            <td width="10%" align="right">设备名称：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="itemName" value="<%=dto.getItemName()%>"></td>
            <td width="10%" align="right">设备型号：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="itemSpec" value="<%=dto.getItemSpec()%>"></td>
            <td width="10%" align="right" rowspan=2><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
        <tr>
            <td width="10%" align="right">设备类型：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="itemCategory" value="<%=dto.getItemCategory()%>"></td>
            <td width="10%" align="right">用途：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="spareUsage" value="<%=dto.getSpareUsage()%>"></td>
            <td width="10%" align="right">厂商：</td>
            <td width="20%"><select name="vendorId" class="select_style1" style="width:100%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>

        </tr>
</table>

<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:830px">
	<table class="headerTable" border="1" width="100%">
        <tr height="22">
            <td width="3%" align="center">备件ID</td>
            <td width="10%" align="center">设备名称</td>
            <td width="15%" align="center">设备型号</td>
            <td width="10%" align="center">设备类型</td>
            <td width="10%" align="center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="3%" align="center">单位</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:70%;width:847px;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="3%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("BARCODE")%></td>
            <td width="10%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_NAME")%></td>
            <td width="15%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="10%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width="10%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("SPARE_USAGE")%></td>
            <td width="10%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("VENDOR_NAME")%></td>
            <td width="3%" align="left" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_UNIT")%></td>
        </tr>
<%
		}
	}
%>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:418px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
</html>

<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(barcode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareConfirmServlet?barcode="+barcode ;
        mainFrm.submit();
    }

    function initPage() {
		do_SetPageWidth();
    }
</script>