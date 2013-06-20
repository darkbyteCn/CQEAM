<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<script language="javascript" src="/WebLibary/js/tab.js"></script>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
<style>
	.mainTitleClass{
		text-align: left;
		background: lightBlue;
		height: 25px;
	}
</style>
<%
AmsAssetsAddressVDetailDTO item = (AmsAssetsAddressVDetailDTO)request.getAttribute(AssetsWebAttributes.ASSETS_DATA);
	String title = "";
	if(!item.getAssetNumber().equals("")){
		 title = "资产“" + item.getTagNumber()+ "”的详细信息";
	}else{
		 title = "资产详细信息";
	}	
%>
<title><%= title %></title>
</head>
<body onload="do_initPage()" leftmargin="0" topmargin="0">
<div style="height: 600px;overflow-y: auto;">
<script type="text/javascript">
    printTitleBar("<%=title%>");
    var btnCount = ArrActions.length;
    for(var i = 0; i < btnCount; i++){
        ArrActions[i][0] = (i == 0 || i == 1);
    }
    ArrActions[0] = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
    ArrActions[1] = new Array(true, "变动历史", "action_view.gif", "点击导出", "do_ShowHistory");
    printToolBar(); 
</script>

<script type="text/javascript">
    var tabBox2 = new TabBox("myTabBox");
    tabBox2.addtab("baseInfo", "资产基本信息");
    tabBox2.addtab("moreInfo", "资产业务属性及价值信息");
    tabBox2.init();
</script>

<div id="baseInfo" >
<table border="1" width="100%" style="border-collapse: collapse" id="table2" bordercolor="#3366EE">
	<tr>
		<td>
			<jsp:include page="/newasset/public/assetsBaseDetail.jsp"></jsp:include>
		</td>
	</tr>
</table> 
</div>

<div id="moreInfo" style="display:none;">
<table border="1" width="100%" style="border-collapse: collapse" id="table2" bordercolor="#3366EE">
	<tr>
		<td>
			<jsp:include page="/newasset/public/assetsMoreDetail.jsp"></jsp:include>
		</td>
	</tr>
</table> 
</div>
</div>
</body>
</html>
<script type="text/javascript">

function do_Close(){
    self.close();
}

function do_ShowHistory(){
	var url = "/servlet/com.sino.ams.newasset.servlet.AmsItemInfoHistoryServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=<%=item.getBarcode()%>";
	var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, 'historyWin', style);
}

function do_initPage(){
    window.focus();
	var larg = 0;
	var altez = 0;
    var factor = 0.8;
    var factorHeight = 0.9;
    var left = 0;
    var top = 0;
	if (document.layers){
		larg = screen.availWidth * factor;
		altez = screen.availHeight * factorHeight;
    } else{
		larg = screen.availWidth * factor;
		altez = screen.availHeight * factorHeight;
	}
    left = larg / 8;
    top = altez / 9;
	self.resizeTo(larg,altez);
	self.moveTo(left, top);
	
}
</script>