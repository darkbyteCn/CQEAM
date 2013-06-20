<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_Search();')">
<%=WebConstant.WAIT_TIP_MSG%>

<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId()+"";
%>
<form action="<%=AssetsURLList.CHECK_HEADER_SERVLET%>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("资产盘点管理>>盘点工单查询");
</script>
    <input type="hidden" name="act" value="">
	<table class="queryTable"style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
	    <tr>
		    <td width="8%" align="right">工单编号：</td>
		    <td width="10%" align="right"><input class="input_style1"  type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
		    <td width="8%" align="right">地点代码：</td>
		    <td width="13%" align="left"><input type="text" class="input_style1"  name="objectCode" style="width:75%" value="<%=dto.getObjectCode()%>"><a href="" onclick="do_SelectLocation(); return false;" title="点击选择盘点地点">[…]</a></td>
	        <td width="8%" align="right">下单组别：</td>
	        <td width="27%"><select class="select_style1" name="groupId" style="width:100%"><%=dto.getGroupOpt()%></select></td>
		    <td width="6%" align="right">执行人：</td>
		    <td width="12%"><input type="text" class="input_style1"  name="implementUser" style="width:75%" value="<%=dto.getImplementUser()%>"><a href="" onclick="do_SelectUser(mainFrm.implementUser); return false;" title="点击选择执行人">[…]</a> </td>
	        <td width="8%" align="right"><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();"></td>
	    </tr>
	    <tr>
		    <td width="8%" align="right">下单日期：</td>
		    <td width="10%"><input type="text" name="startDate" value="<%=dto.getStartDate()%>"   style="width:100%;cursor:hand" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
		    <td width="8%" align="right">到：</td>
		    <td width="13%"><input type="text" name="endDate" value="<%=dto.getEndDate()%>"   style="width:75%;cursor:hand" title="点击选择截止日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
		    <td width="8%" align="right">单据状态：</td>
		    <td width="27%"><select size="1" class="select_style1" name="orderStatus" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ORDER_STATUS_OPT)%></select></td>
		    <td width="6%" align="right">归档人：</td>
		    <td width="12%"><input type="text" name="archivedUser" class="input_style1"  style="width:75%" value="<%=dto.getArchivedUser()%>"><a href="" onclick="do_SelectUser(mainFrm.archivedUser); return false;" title="点击选择盘点地点">[…]</a></td>
		    <td width="8%" align="right"><img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();"></td>
	    </tr>
	</table>
</form>
<div id="headDiv" style="top:70px;left:1px;width:830px">
		<table class="headerTable" border="1" width="200%">
			<tr height="20px">
				<td align=center width="10%">工单编号</td>
				<td align=center width="8%">盘点公司</td>
				<td align=center width="15%">下单组别</td>
				<td align=center width="10%">地点代码</td>
				<td align=center width="22%">盘点地点</td>
				<td align=center width="5%">扫描专业</td>
				<td align=center width="6%">开始日期</td>
				<td align=center width="5%">执行人</td>
				<td align=center width="5%">执行周期(天)</td>
				<td align=center width="4%">归档人</td>
				<td align=center width="5%">归档日期</td>
				<td align=center width="4%">单据状态</td>
			</tr>
		</table>
	</div>
<div id="dataDiv" style="overflow:scroll;height:320px;width:847px;position:absolute;top:92px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		for (int i = 0; i < rows.getSize(); i++) {
			Row row=rows.getRow(i);
%>
	        <tr class="dataTR" onclick="showDetail('<%=row.getValue("HEADER_ID")%>')" style="cursor:hand">
			  <td width=10% align="center"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
	          <td width=8%><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY_NAME")%>"></td>
	          <td width=15%><input type="text" class="finput" readonly value="<%=row.getValue("GROUP_NAME")%>"></td>
	          <td width=10%><input type="text" class="finput" readonly value="<%=row.getValue("LOCATION_CODE")%>"></td>
	          <td width=22%><input type="text" class="finput" readonly value="<%=row.getValue("CHECK_LOCATION")%>"></td>
	          <td width=5%><input type="text" class="finput" readonly value="<%=row.getValue("CHECK_CATEGORY_NAME")%>"></td>
	          <td width=6%><input type="text" class="finput2" readonly value="<%=row.getValue("START_TIME")%>"></td>
	          <td width=5%><input type="text" class="finput" readonly value="<%=row.getValue("IMPLEMENT_USER")%>"></td>
	          <td width=5%><input type="text" class="finput3" readonly value="<%=row.getValue("IMPLEMENT_DAYS")%>"></td>
	          <td width=4%><input type="text" class="finput" readonly value="<%=row.getValue("ARCHIVED_USER")%>"></td>
	          <td width=5%><input type="text" class="finput" readonly value="<%=row.getValue("ARCHIVED_DATE")%>"></td>
	          <td width=4%><input type="text" class="finput" readonly value="<%=row.getValue("ORDER_STATUS")%>"></td>
	        </tr>
<%
		}
	}
%>
	    </table>
	</div>
<%
	if (hasData) {
%>
<div id="pageNaviDiv" style="position:absolute;top:495px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
function do_Create() {
    var url = "<%=AssetsURLList.CHECK_HEADER_SERVLET%>?act=<%=AssetsActionConstant.NEW_ACTION%>";
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'discardWin', style);
}

function showDetail(headerId){
    var url = "<%=AssetsURLList.CHECK_HEADER_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&headerId="+headerId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'orderWin', style);
}

function do_Cancel(){
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}

function do_SelectUser(userBox){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=<%=orgId%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var boxName = userBox.name;
	if (objs) {
		var obj = objs[0];
		userBox.value = obj["userName"];
	} else {
		userBox.value = "";
	}
}


function do_SelectLocation(){
	with(mainFrm){
		var lookUpName = "LOOK_UP_ADDRESS";
		var dialogWidth = 55;
		var dialogHeight = 30;
		userPara = "organizationId=<%=orgId%>";
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (objs) {
			var obj = objs[0];
			objectCode.value = obj["workorderObjectCode"];
		} else {
			objectCode.value = "";	
		}
	}
}
</script>
</html>
