<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
	<title>单据打印页面</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" >
<jsp:include page="/td/newasset/headerDetailTd.jsp" flush="true"/>
<jsp:include page="/newasset/approveContent.jsp" flush="true"/>
<fieldset style="border:0; position:relative;width:100%;height:80%">
    <legend>
		<table width="100%" id="buttonSet">
		    <tr>
		    	<td width="100%" align="center">
			        <img src="/images/eam_images/page_config.jpg" alt="打印设置" onClick="do_SetupPrint(); return false;">
			        <img src="/images/eam_images/print_view.jpg" alt="打印设置" onClick="do_PrevPrint(); return false;">
			        <img src="/images/eam_images/print.jpg" alt="打印" onClick="do_PrintOrder(); return false;">
			        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close(); return false;">
		    	</td>
		    </tr>
		</table>
    </legend>
	<jsp:include page="/td/newasset/printLineDetailTd.jsp" flush="true"/>
</fieldset>
  <object id="webbrowser" width="0" height="0" classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
</body>
</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.td.newasset.servlet.EtsFaTdAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_SetupPrint(){
	webbrowser.execwb(8,0);
}

function do_PrevPrint(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(7,0);
	document.getElementById("buttonSet").style.display = "block";
}

function do_PrintOrder(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(6,6);
	document.getElementById("buttonSet").style.display = "block";
}

function do_Close(){
	window.opener=null;
	window.close();
}
</script>