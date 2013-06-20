<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>实物台账维护</title>
</head>
<body style="overflow-y: auto; " leftmargin="0" topmargin="0" onload="initPage();helpInit('1.A');">
<div style="height: 650px;">
<input type="hidden" name="helpId" value="">
<%
	String dataURL = AssetsURLList.ITEM_MAINTAIN_SERVLET;
	String bottomURL = AssetsURLList.ITEM_BOTTOM_SERVLET;
	
 	String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
    if( null == allResName ){
    	allResName = "实物台账维护";
    }
%>
<script type="text/javascript">
     printTitleBar("<%= allResName %>"); 
</script>
<div id="itemDataDiv" style="position:absolute;top:22px;left:0; right:0;height:523px">
	<iframe name="dataFrm" style="width:100%;height:100%" src="<%=dataURL%>" border="0" frameborder="0"></iframe>
</div>
<div id="updateDiv" style="position:absolute;top:546px;left:0; right:0; height:90px">
	<iframe name="updateDataFrm" style="width:100%;height:100%" src="<%=bottomURL%>" border="0" frameborder="0"></iframe>
</div>

</div>
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