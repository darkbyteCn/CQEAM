<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ include file="/sampling/headerInclude.htm"%>
<%
String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
if( null == allResName ){
	allResName = "实物台账查询";
}

%>
<html>
<head>
	<title>
	<%= allResName %>
	</title>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<body onload="initPage();">

<script type="text/javascript">
    printTitleBar("<%= allResName %>");
</script>

<iframe style="width:100%;height:95%" src="/newasset/itemQueryIframe.jsp"></iframe>
</body>
</html>
<script type="text/javascript">
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