<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="srv.ams.accountbalance.dto.SrvAccountBalanceDTO" %>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>MIS科目余额查询</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	SrvAccountBalanceDTO dto = (SrvAccountBalanceDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	String pageTitle = request.getParameter("pageTitle");
	if(pageTitle == null){
		pageTitle = "MIS系统接口-->>MIS科目余额查询";
	}
%>
<form name="mainFrm" method="post" action="/servlet/srv.ams.accountbalance.servlet.MisAccountBanalceQueryServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>")
</script>
	<table border="0" width="100%" bgcolor="#EFEFEF">
		<tr height="20">
			<td align="right" width="8%">期间名称：</td>
			<td align="left" width="20%" nowrap="nowrap"><input type="text" name="periodName" id="periodName" value="<%=dto.getPeriodName()%>" style="width:80%">例如：Sep-06</td>
			<td align="right" width="8%">项目代码：</td>
			<td align="left" width="20%" nowrap="nowrap"><input type="text" name="segment5" id="segment5" value="<%=dto.getSegment5()%>" style="width:80%"></td>
		</tr>
		<tr height="20">
			<td align="right" width="8%">科目代码：</td>
			<td align="left" width="20%" nowrap="nowrap"><input type="text" name="segment3" id="segment3" value="<%=dto.getSegment3()%>" style="width:80%"></td>
			<td align="right" width="8%">公司代码：</td>
			<td align="left" width="20%" nowrap="nowrap"><select name="companyCode" id="companyCode" style="width:80%"><%=dto.getCompanyCode()%></select></td>
			<td width="4%" valign="top">
				<img src="/images/eam_images/search.jpg" alt="查询" onClick="do_Search(); return false;"></td>
			<td width="4%" valign="top">
				<img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_Export()">
			</td>
		</tr>

	</table>
	<input readonly name="act" type="hidden">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:840px">
	<table class="headerTable" border="1" width="150%">
		<tr height="22">
			<td width="3%" align="center">期间名称</td>
			<td width="3%" align="center">公司代码</td>
			<td width="3%" align="center">货币代码</td>
			<td width="3%" align="center">成本中心代码</td>
			<td width="3%" align="center">科目组合ID</td>
			
			<td width="3%" align="center">科目代码</td>
			<td width="3%" align="center">品牌代码</td>
			<td width="3%" align="center">项目代码</td>
			<td width="3%" align="center">期初余额</td>
			<td width="3%" align="center">本期借方发生额</td>
			
			<td width="3%" align="center">本期贷方发生额</td>
			<td width="3%" align="center">本期发生数</td>
			<td width="3%" align="center">期末借方余额</td>
			<td width="3%" align="center">期末贷方余额</td>
			<td width="3%" align="center">期末数</td>
			
			<td width="3%" align="center">公司层级</td>
			<td width="3%" align="center">成本中心层级</td>

		</tr>
	</table>
</div>		
<div id="dataDiv" style="overflow:scroll;height:70%;width:857px;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22">
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("PERIOD_NAME")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT1")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("CURRENCY_CODE")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT2")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("CODE_COMBINATION_ID")%>"></td>
			
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT3")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT4")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT5")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("BEGIN_BALANCE")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("PERIOD_NET_DR")%>"></td>
			
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("PERIOD_NET_CR")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("PERIOD_NET")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("END_BALANCE_DR")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("END_BALANCE_CR")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("END_BALANCE")%>"></td>
			
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("STRUCTURED_HIERARCHY_NAME_COM")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("STRUCTURED_HIERARCHY_NAME_COS")%>"></td>
		

		</tr>
<%
		}
	}
%>	
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:475px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script>
function do_Search(){

	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
/*
	if(!document.$$$WebGridSystemFrm$$$){
		alert("请先执行查询，再导出");
		return;
	}
	var totalRecord = Number($$$WebGridSystemFrm$$$.$$$WebGridTotalRecord$$$.value);
	if(totalRecord > 5000){
		alert("当前条件下记录数过多，请输入相应条件精简记录数后再导出");
		return;
	}
*/
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_MISLOC%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.assetsLocationCode.value = obj["assetsLocationCode"];
	}
}

function do_SelectPerson(){
	var lookUpName = "LOOK_UP_PERSON";
	var dialogWidth = 47;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(objs){
		var obj = objs[0];
		mainFrm.assignedToName.value = obj["userName"];
	}
}

function do_SelectProject() {
	var lookUpName = "LOOKUP_PROJECT2";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.projectName.value = obj["projectName"];
	}
}

function do_SelectCostCenter(){
	var lookUpName = "LOOK_UP_COST";
    var userPara="organizationId="+document.mainFrm.organizationId.value;
    var dialogWidth = 50;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
	if (objs) {
		var obj = objs[0];
		document.mainFrm.costCenterCode.value = obj["costCode"];
	}
}
</script>
</html>