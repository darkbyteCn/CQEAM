<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<!-- onload="do_SetPageWidth()" -->
</head>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    int orgId = userAccount.getOrganizationId();
%>
<form action="<%=AssetsURLList.ARCHIVE_ORDER_SERVLET%>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("资产盘点管理>>盘点工单归档");
</script>
    <input type="hidden" name="act" value="">
    <table class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="8%" align="right">工单任务：</td>
            <td width="20%"><input type="text" class="input_style1"  name="taskDesc" style="width:100%" value="<%=dto.getTaskDesc()%>" size="20"></td>
	        <td width="8%" align="right">地点编号：</td>
	        <td width="20%"><input type="text" class="input_style1"  name="objectCode" style="width:85%" value="<%=dto.getObjectCode()%>"><a href="" onclick="do_SelectLocation(); return false;" title="点击选择盘点地点">[…]</a></td>
            <td width="8%" align="right">下单日期：</td>
            <td width="13%"><input type="text" name="startDate"   value="<%=dto.getStartDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
            <td width="13%"><input type="text" name="endDate"   value="<%=dto.getEndDate()%>" style="width:75%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
            <td width="10%"><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();" align="right"></td>
        </tr>
        <tr>
            <td width="8%" align="right">工单编号：</td>
            <td width="20%"><input type="text" class="input_style1"  name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="8%" align="right">下单人：</td>
            <td width="20%"><input type="text" class="input_style1"  name="createdUser" style="width:85%" value="<%=dto.getCreatedUser()%>"><a href="" onclick="do_SelectUser(mainFrm.createdUser); return false;" title="点击选择下单人">[…]</a></td>
            <td width="8%" align="right">执行人：</td>
            <td width="26%" colspan="2"><input type="text" class="input_style1"  name="implementUser" style="width:89%" value="<%=dto.getImplementUser()%>"><a href="" onclick="do_SelectUser(mainFrm.implementUser); return false;" title="点击选择执行人">[…]</a></td>
	        <td width="10%"><img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();" align="right"></td>
        </tr>
	</table>
</form>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:69px;left:1px;width:830px">
		<table class="headerTable" border="1" width="200%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="2%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
		        <td align=center width="10%">工单编号</td>
	            <td align=center width="10%">工单批号</td>
	            <td align=center width="10%">下单组别</td>
	            <td align=center width="8%">地点代码</td>
	            <td align=center width="20%">盘点地点</td>
	            <td align=center width="5%">扫描专业</td>
                <td align=center width="5%">下单人</td>
                <td align=center width="5%">开始日期</td>
                <td align=center width="5%">执行人</td>
                <td align=center width="5%">执行周期(天)</td>
				<td align=center width="5%">归档人</td>
				<td align=center width="5%">归档日期</td>
				<td align=center width="5%">单据状态</td>
	        </tr>
		</table>
	</div>
<%
	if (hasData) {
%>
	<div id="dataDiv" style="overflow:scroll;height:68%;width:847px;position:absolute;top:93px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>                                            
	        <tr class="dataTR" onclick="showDetail('<%=row.getValue("HEADER_ID")%>')">
	          <td width="2%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
			  <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("BATCH_NO")%>"></td>
	          <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("GROUP_NAME")%>"></td>
	          <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("LOCATION_CODE")%>"></td>
	          <td width="20%"><input type="text" class="finput" readonly value="<%=row.getValue("CHECK_LOCATION")%>"></td>
	          <td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("CHECK_CATEGORY_NAME")%>"></td>
              <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("CREATED_USER")%>"></td>
              <td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("START_TIME")%>"></td>
              <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("IMPLEMENT_USER")%>"></td>
	          <td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("IMPLEMENT_DAYS")%>"></td>
	          <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ARCHIVED_USER")%>"></td>
	          <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ARCHIVED_DATE")%>"></td>
	          <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ORDER_STATUS")%>"></td>
	        </tr>
<%
		}
%>	
	    </table>
	</div>
<div id="pageNaviDiv" style="position:absolute;top:458px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function do_Create() {
    var url = "<%=AssetsURLList.ARCHIVE_ORDER_SERVLET%>?act=<%=AssetsActionConstant.NEW_ACTION%>";
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'discardWin', style);
}

function showDetail(headerId){
    var url = "<%=AssetsURLList.ARCHIVE_ORDER_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&headerId="+headerId;
    //var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    var screenHeight = window.screen.height - 100;
    var screenWidth = window.screen.width;
  	var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
    window.open(url, 'orderWin', winstyle);
}

function do_Cancel(){
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}

/**
  * 功能：选择执行人
 */
function do_SelectUser(userObj){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=<%=orgId%>";
	var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (users) {
        var user = users[0];
        userObj.value = user["userName"];
    } else {
        userObj.value = "";
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
