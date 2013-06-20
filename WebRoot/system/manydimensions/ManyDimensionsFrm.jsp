<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>多维度信息维护</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('1.N');">
<input type="hidden" name="helpId" value="">
<%
	String dataURL = AssetsURLList.MANY_DIMENSION_SERVLET;
	String bottomURL = AssetsURLList.MANY_DIMENSION_BOTTOM_SERVLET;
%>
<script type="text/javascript">
    printTitleBar("多维度信息维护");
</script>
<div id="itemDataDiv" style="position:absolute;top:0px;left:0px; right:0;height:620px;">
	<iframe name="dataFrm" style="width:100%;height:100%" src="<%=dataURL%>" border="0" frameborder="0"></iframe></div>
<div id="updateDiv" style="position:absolute;top:600px;left:0px; right:0; height:80px">
	<iframe name="updateDataFrm" style="width:100%;height:100%" src="<%=bottomURL%>" border="0" frameborder="0"></iframe></div>
</body>
</html>
<script>
function initPage(){
	window.focus();
	var larg = 0;
	var altez = 0;
	if (document.layers){
		larg = screen.availWidth-10;
		altez = screen.availHeight-20;}
	else{
		larg = screen.availWidth+8;
		altez = screen.availHeight+7;
	}
	self.resizeTo(larg,altez);
	self.moveTo(-4,-4);
}
</script>