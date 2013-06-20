<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <script language="JavaScript" src="/WebLibary/js/MzTreeView10.js"></script>
    <title>资产台账</title>
    <style type="text/css">
    body, td
    {
      font-family: 宋体;
      font-size: 12px;
    }
    A:LINK, A:VISITED, A:ACTIVE, A:HOVER
    {
      color: #225AD9;
      font-size: 12px;
      padding-left: 3px;
      TEXT-DECORATION: NONE;
    }
    </style>
    <base target="assetsMain">
  </head>
<body leftmargin="0" topmargin="0" rightmargin="0">
<%
	String treeCategory = request.getParameter("treeCategory");
	String assetsTree = (String)request.getAttribute(AssetsWebAttributes.ASSETS_TREE);
	if(!StrUtil.isEmpty(assetsTree)){
		out.print(assetsTree);
	} else {
		out.print("there's no data");
	}
%>
<form name="mainFrm" action="" method="post">
</form>
</body>

</html>
<script>
function do_AssetsQuery(){
	var treeCategory = "<%=treeCategory%>";
	var url = event.srcElement.toString();
	var paras = url.substring(url.indexOf("#?") + 2);
	<%--url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=QUERY_ACTION&treeCategory=<%=treeCategory%>";--%>
	url = "/servlet/com.sino.ams.system.rent.servlet.AmsRentAssetServlet?act=QUERY_ACTION&treeCategory=<%=treeCategory%>";
	var paraArrs = paras.split("&");
	var fieldName = "";
	var fieldValue = "";
	for(var i = 0; i < paraArrs.length; i++){
		fieldName = paraArrs[i].substring(0, paraArrs[i].indexOf("="));
		fieldValue = paraArrs[i].substring(paraArrs[i].indexOf("=") + 1);
		alert("fieldName is " + fieldName);
		alert("fieldValue is " + fieldValue);
	}
}
</script>
