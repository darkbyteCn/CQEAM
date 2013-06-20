<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body topmargin="0" leftmargin="0" onload="init();" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="">
<script type="text/javascript">
    printTitleBar("调拨资产是否确认");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");
    var ArrAction1 = new Array(true, "导出", "toexcel.gif", "关闭", "do_Export()");
    var ArrActions = new Array(ArrAction0, ArrAction1);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();
</script>
<%
	AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}	
	
%>
	<table border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
	    <tr>
	    	<td width="8%" align="right">资产名称：</td>
	    	<td width="12%"><input class="input_style1" type="text" name="assetsDescription"style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
	    	<td width="8%" align="right">调拨单号：</td>
	    	<td width="12%"><input class="input_style1" type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>" size="20">
	    	<td width="8%" align="right">调出日期：</td>
	    	<td width="32%"><input class="input_style1" type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:45%" title="点击选择开始日期" readonly  onclick="gfPop.fStartPop(startDate, endDate)">&nbsp;<input class="input_style1" type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:45%" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"></td>
	        <td width="8%" align="right">是否确认：</td>
	        <td width="12%">
	        	<select class="select_style1" name="confirmYN" >
	        		<option value="">--请选择--</option>
	        		<option value="N" <%request.getAttribute("confirmYN"); if (request.getAttribute("confirmYN") == "N") out.print("selected"); %> >未确认</option>
	        		<option value="Y" <%request.getAttribute("confirmYN"); if (request.getAttribute("confirmYN") == "Y") out.print("selected"); %> >已确认</option>
	        	</select>
	        </td>
	    </tr>
	</table>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="exportType" value="">
</form>

	<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:80px;left:1px;width:100%">
		<table class="eamHeaderTable" border="1" width="200%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <%--<td align=center width="2%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>--%>
	            <td align=center width="7%">资产标签</td>
	            <td align=center width="10%">调拨单号</td>
	            <td align=center width="5%">资产编号</td>
	            <td align=center width="8%">资产名称</td>
	            <td align=center width="8%">资产型号</td>
	            <td align=center width="14%">原地点</td>
	            <td align=center width="4%">原责任人</td>
	            <td align=center width="11%">原责任部门</td>
				<td align=center width="14%">调入地点</td>
	            <td align=center width="5%">新责任人</td>
	            <td align=center width="11%">调入部门</td>
	            <td align=center width="5%">确认状态</td>
				<td style="display:none">隐藏域字段</td>
	        </tr>
	    </table>
	</div>
<%
	if(hasData){
%>
	<div id="dataDiv" style="overflow:scroll;height:75%;width:100%;position:absolute;top:103px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%		Row row = null;
		String barcode = "";
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			barcode = row.getStrValue("BARCODE");
%>		
			<tr class="dataTR" onclick="executeClick(this)">
				<%--<td width="2%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>--%>
				<td width="7%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput2" readonly name="newBarcode" value="<%=row.getValue("NEW_BARCODE")%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
				<td width="5%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
				<td width="8%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
				<td width="8%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
				<td width="14%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_LOCATION_NAME")%>"></td>
				<td width="4%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_RESPONSIBILITY_USER_NAME")%>"></td>
				<td width="11%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_RESPONSIBILITY_DEPT_NAME")%>"></td>
				<td width="14%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_LOCATION_NAME")%>"></td>
				<td width="5%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
				<td width="11%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_DEPT_NAME")%>"></td>
				<td width="5%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("CONFIRM_YN")%>"></td>
				<td style="display:none">
					<input type="hidden" name="oldAddressId" value="<%=row.getValue("OLD_ADDRESS_ID")%>">
					<input type="hidden" name="addressId" value="<%=row.getValue("ADDRESS_ID")%>">
					<input type="hidden" name="oldLocation" value="<%=row.getValue("OLD_LOCATION")%>">
					<input type="hidden" name="assignedToLocation" value="<%=row.getValue("ASSIGNED_TO_LOCATION")%>">
					<input type="hidden" name="oldResponsibilityUser" value="<%=row.getValue("OLD_RESPONSIBILITY_USER")%>">
					<input type="hidden" name="responsibilityUser" value="<%=row.getValue("RESPONSIBILITY_USER")%>">
					<input type="hidden" name="oldResponsibilityDept" value="<%=row.getValue("OLD_RESPONSIBILITY_DEPT")%>">
					<input type="hidden" name="responsibilityDept" value="<%=row.getValue("RESPONSIBILITY_DEPT")%>">

					<input type="hidden" name="transferType" value="<%=row.getValue("TRANSFER_TYPE")%>">
					<input type="hidden" name="barcode" value="<%=barcode%>">
					<input type="hidden" name="fromOrganizationId" value="<%=row.getValue("FROM_ORGANIZATION_ID")%>">
					<input type="hidden" name="toOrganizationId" value="<%=row.getValue("TO_ORGANIZATION_ID")%>">
				</td>
			</tr>
<%
		}
%>
		</table>
	</div>
<div id="pageNaviDiv"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%		
	}
%>	
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<iframe style="display:none" src="" name="downFrm"></iframe>

<script language="javascript">

function init(){
    do_SetPageWidth();
    var t = '<%=request.getAttribute("confirmYN")%>';
    if (t == 'null' ) t = "";
    document.getElementById('confirmYN').value = t.toString();
}

function do_Export(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能执行指定的操作。");
		return;
	}
	var exportType = "";
	if(confirm("是导出查询的所有设备还是导出选择的设备？导出查询的设备请点击“确定”按钮，导出选择的设备请点击“取消”按钮")){
		exportType = "<%=AssetsWebAttributes.EXPORT_QUERY_ASSETS%>";
	} else {
		if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
			alert("没有数据，不能执行指定的操作。");
			return;
		}
		if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$.value){
			alert("没有选择数据，不能执行指定的操作。");
			return;
		}
		exportType = "<%=AssetsWebAttributes.EXPORT_SELECTED_ASSETS%>";
	}
	if(exportType == ""){
		return;
	}
	mainFrm.exportType.value = exportType;
	var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "downFrm";
	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AATConfirmYNServlet";
	mainFrm.submit();
}

function do_Search(){
	var cfmYN = document.getElementById('confirmYN').value;
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AATConfirmYNServlet?confirmYN=" + cfmYN;
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_Confirm(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能执行指定的操作。");
		return;
	}
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$.value){
		alert("没有选择数据，不能执行指定的操作。");
		return;
	}
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AATConfirmYNServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.CONFIRM_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}
</script>
