<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/match/prematch/headerInclude.jsp"%>
<%@ include file="/match/prematch/headerInclude.htm"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">

<script language="javascript">
	var ArrAction0 = new Array(true, "关闭", "close.gif", "关闭", "do_Close()");
	var ArrAction1 = new Array(true, "匹配", "save.gif", "匹配", "do_PreMatch()");
	var ArrAction2 = new Array(true, "解除匹配", "del.gif", "解除匹配", "do_ReleaseMatch()");
	var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="initPage();">
<%
	String manualMatchURL = "/servlet/com.sino.ams.prematch.servlet.ManualMatchFrmServlet";
	String autoMatchURL = "/servlet/com.sino.ams.prematch.servlet.AutoMatchServlet";
	String matchedListURL = "/servlet/com.sino.ams.prematch.servlet.AmsPaMatchServlet?srcPage=" + AMSActionConstant.DELETE_ACTION;
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("autoMatch", "自动匹配");
	tabBox.addtab("manualMatch", "手工匹配");
	tabBox.addtab("matchedList", "匹配清单");
	printTitleBar("EAM设备－MIS转资清单预匹配");
	printToolBar();
	tabBox.init();
</script>

<table align="center" width='100%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF" style="width:100%;height:88%">
	<tr>
		<td style="width:100%;height:100%">
			<div id="autoMatch" style='display:none'>
				<iframe id="autoMatchFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=autoMatchURL%>"></iframe>
			</div>
			<div id="manualMatch" style='display:none'>
				<iframe id="manualMatchFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=manualMatchURL%>"></iframe>
			</div>
			<div id="matchedList" style='display:none'>
				<iframe id="matchedListFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=matchedListURL%>"></iframe>
			</div>
		</td>
	</tr>
</table>
</body>
</html>

<script language="javascript">
function initPage() {
	window.focus();
	document.all("autoMatch").style.display = "";
	document.all("manualMatch").style.display = "none";
	document.all("matchedList").style.display = "none";
	tabBox.inithidetab(1);
}

function do_PreMatch(){
	var autoStyle = document.all("autoMatch").style.display;
	var manualStyle = document.all("manualMatch").style.display;
	if(autoStyle != "none"){
		do_AutoMatch();
	} else if(manualStyle != "none"){
		do_ManualMatch();
	} else {
		alert("“自动匹配”和“手工匹配”均不处于活动状态，不能执行匹配操作");
	}
}

/**
 * 功能：自动匹配
 */
function do_AutoMatch(){
	var wind = window.frames["autoMatchFrm"];
	var mainFrm = wind.document.mainFrm;
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能匹配");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("没有选择数据，不能匹配");
		return;
	}
	if(confirm("确定匹配选中的数据吗？继续请点击“确定”按钮，否则请点击“取消”按钮")){
		wind.do_AutoMatch();
	}
}

/**
 * 功能：手工匹配
 */
function do_ManualMatch(){
	var manWind = window.frames["manualMatchFrm"];
	var amsWind = manWind.frames["amsFrm"];
	var misWind = manWind.frames["misFrm"];
	var amsDoc = amsWind.document;
	var misDoc = misWind.document;
	var sysObjs = amsDoc.getElementsByName("systemId");
	var tagObjs = misDoc.getElementsByName("tagNumber");

	if(!sysObjs){
		alert("没有数据，不能匹配。请先查询EAM实物数据。");
		return;
	}
	if(!tagObjs){
		alert("没有数据，不能匹配。请先查询MIS转自清单数据。");
		return;
	}
	var systemId = getRadioValueByObj(sysObjs);
	var tagNumber = getRadioValueByObj(tagObjs);
	if(systemId == ""){
		alert("没有选择EAM实物数据，不能匹配");
		return;
	}
	if(tagNumber == ""){
		alert("没有选择MIS实物数据，不能匹配");
		return;
	}
	if(confirm("确定匹配选中的数据吗？继续请点击“确定”按钮，否则请点击“取消”按钮")){
		manWind.do_ManualMatch(systemId, tagNumber);
	}
}

function do_ReleaseMatch(){
	var matchedStyle = document.all("matchedList").style.display;
	if(matchedStyle == "none"){
		alert("“匹配清单”没有处于活动状态，不能执行指定操作");
		return;
	}
	var wind = window.frames["matchedListFrm"];
	var mainFrm = wind.document.mainFrm;
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能解除匹配");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("没有选择数据，不能解除匹配");
		return;
	}
	if(confirm("确定解除选中的匹配关系吗？继续请点击“确定”按钮，否则请点击“取消”按钮")){
		wind.do_ReleaseMatch();
	}
}
</script>
