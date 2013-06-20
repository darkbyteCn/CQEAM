<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<base target="contents">
<style type="text/css">
.style1 {
	font-family: 隶书;
	font-size: 16pt;
	text-align: center;
	color: #FFFFFF;
}
A:LINK, A:VISITED, A:ACTIVE, A:HOVER
{
  color: #FFFFFF;
  font-size: 12px;
  padding-left: 3px;
  TEXT-DECORATION: NONE;
}
</style>
</head>
<body onload="initPage();" leftmargin="0" topmargin="0" rightmargin="1" background="/images/HeaderBack.png">
<%
	String treeCategory = request.getParameter("treeCategory");
%>
<table border="0" width="100%" height="42" bordercolor="#000000" id="table1" cellspacing="1">
	<tr>
		<td height="21" align="center"><span style="letter-spacing: 3pt"><font face="隶书" size="5" color="#FFFFFF">租赁资产台账查询平台</font></span></td>
	</tr>
	<tr>
		<td height="21" align="center"><%=request.getAttribute(AssetsWebAttributes.ASSETS_RADIO)%>&nbsp;<a href="" onClick="do_Close(); return false;">退出</a></td>
	</tr>
</table>

</body>
</html>
<script>
var treeCategory = "<%=treeCategory%>";
function initPage(){
	var radios = document.getElementsByName("treeCategory");
	if(radios){
		if(radios.length){
			for(var i = 0; i < radios.length; i++){
				if(radios[i].value == treeCategory){
					radios[i].click();
					break;
				}
			}
		} else {
			radios.click();
		}
	}
}

function do_ChangeAssetsTree(obj){
	var treeCategory = obj.value;
	if(treeCategory != "<%=AssetsWebAttributes.ASSETS_TREE_COMM_QUERY%>"){
//		var url = "/servlet/com.sino.ams.newasset.servlet.AssetsTreeServlet?treeCategory=" + treeCategory;
		var url = "/servlet/com.sino.ams.system.rent.servlet.RentAssetsTreeServlet?treeCategory=" + treeCategory;
		parent.contents.location.href = url;
//		var assetsLocation = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet";
		var assetsLocation = "/servlet/com.sino.ams.system.rent.servlet.AmsRentAssetServlet";
		if(treeCategory == "<%=AssetsWebAttributes.LOCATION_TREE_QUERY%>"){
			assetsLocation = "/servlet/com.sino.ams.newasset.servlet.LoctionQueryServlet";
		} if(treeCategory == "<%=AssetsWebAttributes.ASSETS_TREE_CUST_QUERY%>"){
			assetsLocation = "/servlet/com.sino.ams.newasset.servlet.CustomQueryServlet";
		}
		parent.assetsMain.location.href = assetsLocation + "?act=&treeCategory=" + treeCategory;
	} else {
		parent.location.href = "<%=AssetsURLList.ASSETS_FRM_SERVLET%>?treeCategory=" + treeCategory;;
	}
}

function do_Close(){
	if(confirm("请确认你已经保存了所进行的工作，继续请点击“确定”按钮，否则请点击“取消”按钮！")){
		parent.close();
	}
}
</script>
