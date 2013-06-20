<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util2.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script type="text/javascript" src="/WebLibary/js/api.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

</head>
<body onload="initPage();" topmargin="0" leftmargin="0">
<%
	DTOSet datas = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.ObjectAssetsServlet" method="post" name="mainFrm">
    <script type="text/javascript">
        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
        var ArrAction1 = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
        ArrActions = new Array(ArrAction0, ArrAction1);
        var ArrSinoViews = new Array();
        printTitleBar("地点设备列表");
        printToolBar();
    </script>

<div id="headDiv" style="overflow-x:hidden;overflow-y:hidden;position:absolute;top:50px;left:1px;width:100%">
	<table class="headerTable" border="1" width="120%" style="text-align:center">
		<tr>
			<td width="9%" height="22">标签号</td>
			<td width="6%" height="22">设备专业</td>
            <td width="13%" height="22">设备名称</td>
			<td width="13%" height="22">设备型号</td>
			<td width="18%" height="22">所在地点</td>
			<td width="12%" height="22">地点代码</td>
            <td width="18%" height="22">责任部门</td>
            <td width="8%" height="22">责任人</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:74px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
	if(datas != null && !datas.isEmpty()){
%>    
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = datas.getSize();
        for(int i = 0; i < dataCount; i++){
			ObjectAssetsDTO data = (ObjectAssetsDTO)datas.getDTO(i);
%>
		<tr>
			<td width="9%" height="22"><input type="text" class="finput" readonly value="<%=data.getBarcode()%>"></td>
			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=data.getItemCategoryName()%>"></td>
			<td width="13%" height="22"><input type="text" class="finput" readonly value="<%=data.getItemName()%>"></td>
			<td width="13%" height="22"><input type="text" class="finput" readonly value="<%=data.getItemSpec()%>"></td>
			<td width="18%" height="22"><input type="text" class="finput" readonly value="<%=data.getWorkorderObjectName()%>"></td>
            <td width="12%" height="22"><input type="text" class="finput" readonly value="<%=data.getWorkorderObjectCode()%>"></td>
			<td width="18%" height="22"><input type="text" class="finput" readonly value="<%=data.getDeptName()%>"></td>
			<td width="8%" height="22"><input type="text" class="finput" readonly value="<%=data.getUserName()%>"></td>
		</tr>
<%
		}
%>
    </table>
<%
	}
%>
</div>
    <input type="hidden" name="act">
    <input type="hidden" name="checkLocation" value="">
</form>
<div id="pageNaviDiv" style="position:absolute;bottom:0px;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">

function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_Close() {
    window.close();
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.checkLocation.value = document.forms["$$$WebGridSystemFrm$$$"].checkLocation.value;
    mainFrm.submit();
}
</script>
