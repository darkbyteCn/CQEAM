<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-14
  Time: 11:33:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>低值易耗台帐维护</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();">
<%
	String dataURL = "/servlet/com.sino.ams.newasset.servlet.ItemCostEasyServlet";
	String bottomURL = "/servlet/com.sino.ams.newasset.servlet.ItemCostBottomServlet";
%>
<script type="text/javascript">
    printTitleBar("低值易耗台帐维护");
</script>
<div style="height:97%;width:100%;overflow-y:auto;">
<div id="itemDataDiv" style="position:absolute;top:0px;left:0px; right:0;height:490px">
	<iframe name="dataFrm" style="width:100%;height:100%" src="<%=dataURL%>" border="0" frameborder="0"></iframe></div>
<div id="updateDiv" style="position:absolute;top:490px;left:0px; right:0;">
	<iframe name="updateDataFrm" style="width:100%;height:100%" src="<%=bottomURL%>" border="0" frameborder="0"></iframe></div>
</div>
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