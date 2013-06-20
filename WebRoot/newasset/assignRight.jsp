<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();

	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}
	String assProp = request.getParameter("assProp");
	if(assProp == null){
		assProp = "";
	}
%>
<html>
<body onload="initPage();" topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search()');">
<form method="POST" name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.AssignRightServlet">
<jsp:include page="/message/MessageProcess"/>
	<table border="0" width="100%" id="table1" style="border-collapse: collapse; "  bgcolor="#EFEFEF">
		<tr>
			<td width="10%" align="right">标签号：</td>
			<td width="20%" align="right" height="20"><input type="text" name="barcode" size="20" style="width:100%" value="<%=dtoPara.getBarcode()%>"></td>
			<td width="10%" align="right">资产名称：</td>
			<td width="20%" align="right"><input type="text" name="assetsDescription" size="20" style="width:100%" value="<%=dtoPara.getAssetsDescription()%>"></td>
			<td width="40%" align="right" rowspan="2">
			<img border="0" src="/images/eam_images/search.jpg" onClick="do_Search();">&nbsp;&nbsp;
			<img border="0" src="/images/button/distribute.gif" onClick="do_SaveAssign()">&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">所在地点：</td>
			<td width="20%" align="right" height="20"><input type="text" name="workorderObjectLocation" size="20" style="width:100%;cursor:hand" class="readonlyInput" readonly title="点击选择资产地点" onClick="do_SelectAddress()" value="<%=dtoPara.getWorkorderObjectLocation()%>"></td>
			<td width="10%" align="right">分配属性：</td>
			<td width="20%" align="right"><select name="assignProp" style="width:100%"><%=dtoPara.getAssignOptions()%></select></td>
		</tr>
	</table>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:48px;left:1px;width:743px">
		<table class="headerTable" border="1" width="140%" height="20px" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	        <tr height="20px">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="9%">标签号</td>
	            <td align=center width="9%">资产编号</td>
	            <td align=center width="14%">资产名称</td>
	            <td align=center width="14%">资产型号</td>
	            <td align=center width="6%">资产原值</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="6%">净值</td>
	            <td align=center width="9%">责任人</td>
				<td align=center width="15%">部门名称</td>
	            <td align=center width="9%">使用人</td>
	        </tr>
	    </table>
	</div>
<%
	if(hasData){
%>
	<div id="dataDiv" style="overflow:scroll;height:84%;width:760px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
	}
%>    
<input type="hidden" name="act">	
<input type="hidden" name="responsibilityUser" value="<%=dtoPara.getResponsibilityUser()%>">
<input type="hidden" name="responsibilityDept" value="<%=dtoPara.getResponsibilityDept()%>">
<input type="hidden" name="maintainUser" value="<%=dtoPara.getMaintainUser()%>">
<input type="hidden" name="workorderObjectNo" value="">
<input type="hidden" name="assProp" value="<%=assProp%>">

</form>	
<%
	if(hasData){
%>
	    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%		Row row = null;
		String barcode = "";
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			barcode = row.getStrValue("BARCODE");
%>		
			<tr class="dataTR" onclick="executeClick(this)">
				<td width="3%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
				<td width="9%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=barcode%>" size="20"></td>
				<td width="9%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
				<td width="14%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
				<td width="14%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
				<td width="6%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>
				<td width="9%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
				<td width="15%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
				<td width="9%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("MAINTAIN_USER_NAME")%>"></td>
			</tr>
<%
		}
%>
		</table>
	</div>
<div style="position:absolute;top:592px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%		
	}
%>	
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<script>
function initPage(){
	if(mainFrm.assProp.value != ""){
		return;
	}
	var radios = parent.banner.document.getElementsByName("assProp");
	if(radios){
		if(radios.length){
			for(var i = 0; i < radios.length; i++){
				if(radios[i].checked){
					mainFrm.assProp.value = radios[i].value;
				}
			}
		} else {
			mainFrm.assProp.value = radios.value;
		}
	}
	do_SetPageWidth();
}

function do_Search(){
	var assProp = mainFrm.assProp.value;
	if(assProp == "<%=AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER%>"){
		if(mainFrm.responsibilityDept.value == ""){
			alert("请先选择部门，再执行查询。");
			return;
		}
	}
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

/**
 * 功能:选择地点
 */
function do_SelectAddress() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=orgId%>";
	var locations = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (locations) {
		var address = locations[0];
		mainFrm.workorderObjectLocation.value = address["workorderObjectLocation"];
		mainFrm.workorderObjectNo.value = address["toObjectNo"];
	} else {
		mainFrm.workorderObjectLocation.value = "";
		mainFrm.workorderObjectNo.value = "";
	}
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_SaveAssign(){
	var assProp = mainFrm.assProp.value;
	var assignDept = mainFrm.responsibilityDept.value;
	var assignPerson = mainFrm.responsibilityUser.value;
	var assignUser = mainFrm.maintainUser.value;
//	alert("assProp = " + assProp );
//	alert("assignDept = " + assignDept);
//	alert("assignUser = " + assignUser );
//	alert("assignPerson = " + assignPerson );
	if(assProp == "<%=AssetsWebAttributes.ASSIGN_COST_CENTER%>"){
		if(assignDept == ""){
			if(!confirm("待分配部门没有选择，如果本次选择的资产已经分配成本中心，原来的分配关系将解除，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
				return;
			}
		}
	
	} else if(assProp == "<%=AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER%>"){
		if(assignDept == ""){
			alert("未选择部门，请选择成本中心后再分配责任人。");
			return;
		}
		if(assignPerson == ""){
			if(!confirm("待分配责任人没有选择，如果本次选择的资产已经分配责任人，原来的分配关系将解除，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
				return;
			}
		}
	} else if(assProp == "<%=AssetsWebAttributes.ASSIGN_MAINTAIN_USER%>"){
		if(assignUser == ""){
			if(!confirm("待分配使用人没有选择，如果本次选择的资产已经分配使用人，原来的分配关系将解除，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
				return;
			}
		}
	}
	with(mainFrm){
		if($$$CHECK_BOX_HIDDEN$$$ == "undefined"){
			alert("没有资产数据，不能执行该操作。");
			return;
		}
		if($$$CHECK_BOX_HIDDEN$$$.value == ""){
			alert("没有选择资产，不能执行该操作。");
			return;
		}
		action = "/servlet/com.sino.ams.newasset.servlet.AssetsAssignServlet";
		act.value = "<%=AssetsActionConstant.ASSIGN_ACTION%>";
		submit();
	}
}

function do_Close(){
	if(confirm("请确认已经保存了你的工作，继续请点击“确定”按钮，否则请点击“取消”按钮！")){
		parent.window.close();
	}
}

</script>
