
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<title>资产综合查询</title></head>
<html>
<%
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String treeCategory = dto.getTreeCategory();
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}	
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_SearchAssets();')">
	<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.servlet.CommonQueryServlet">
	<table border="0" width="100%" cellspacing="0" style="border-collapse: collapse" bordercolor="#000000" id="table1" cellpadding="0">
		<tr>
			<td>
				<table border="1" width="100%" id="table1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#336699">
					<tr>
						<td width="48%" height="20" bgcolor="#E6E6E6" colspan="4" align="center">
						<b>按资产详细资料</b></td>
						<td width="48%" height="20" bgcolor="#E6E6E6" colspan="4" align="center">
						<b>按账簿</b></td>
					</tr>
					<tr>
						<td width="6%" height="20" align="right">资产编号：</td>
						<td width="19%" height="20" align="right"><input class="input_style1" type="text" name="assetNumber"  style="width:100%" value="<%=dto.getAssetNumber()%>"></td>
						<td width="6%" height="20" align="right">资产名称：</td>
						<td width="19%" height="20"><input type="text" class="input_style1" name="assetsDescription" style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
						<td width="6%" height="20" align="right">资产账簿：</td>
						<td width="44%" height="20" align="right" colspan="3"><select class="select_style1" name="bookTypeCode" style="width:100%"><%=dto.getBookTypeOption()%></select></td>
					</tr>
					<tr>
						<td width="6%" height="20" align="right">标签号：</td>
						<td width="19%" height="20" align="right"><input class="input_style1" type="text" name="barcode"  style="width:100%" value="<%=dto.getBarcode()%>"></td>
						<td width="6%" height="20" align="right">类别代码：</td>
						<td width="19%" height="20">
						<input class="input_style1" type="text" name="startCategoryCode" style="width:46%" value="<%=dto.getStartCategoryCode()%>" size="20">－<input class="input_style1" type="text" name="endCategoryCode" style="width:46%" value="<%=dto.getEndCategoryCode()%>" size="20"></td>
						<td width="6%" height="44" align="right" rowspan="2">启用日期：　</td>
						<td width="44%" height="20" align="right" colspan="3"><input class="input_style2" type="text" name="startDate"  style="width:100%" value="<%=dto.getStartDate()%>" class="readonlyInput" readonly title="点击选择开始日期" onClick="gfPop.fStartPop(startDate, endDate)"></td>
					</tr>
					<tr>
						<td width="6%" height="20" align="right">资产型号：</td>
						<td width="19%" height="20" align="right"><input class="input_style1" type="text" name="modelNumber"  style="width:100%" value="<%=dto.getModelNumber()%>"></td>
						<td width="6%" height="20" align="right">资产原值：</td>
						<td width="19%" height="20">
						<input class="input_style1" type="text" name="startCost" style="width:46%" value="<%=dto.getStartCost()%>" size="20">－<input class="input_style1" type="text" name="endCost" style="width:46%" value="<%=dto.getEndCost()%>" size="20">
						</td>
						<td width="44%" height="20" align="right" colspan="3"><input class="input_style2" type="text" name="endDate" style="width:100%" value="<%=dto.getEndDate()%>"  class="readonlyInput" readonly title="点击选择截至日期" onClick="gfPop.fEndPop(startDate, endDate)"></td>
					</tr>
					<tr>
						<td width="50%" height="20" align="center" bgcolor="#E6E6E6" colspan="4">
						<b>按分配</b></td>
						<td width="50%" height="20" align="center" bgcolor="#E6E6E6" colspan="4">
						<b>按来源行</b></td>
					</tr>
					<tr>
						<td width="6%" height="20" align="right">员工姓名：</td>
						<td width="19%" height="20" align="right"><input class="input_style2" type="text" name="responsibilityUserName"  style="width:100%" value="<%=dto.getResponsibilityUserName()%>" class="readonlyInput" readonly title="点击选择员工" onclick="do_SelectPerson()"></td>
						<td width="6%" height="20" align="right">员工号：</td>
						<td width="19%" height="20"><input class="input_style2" type="text" name="employeeNumber"  style="width:100%" value="<%=dto.getEmployeeNumber()%>" class="readonlyInput" readonly title="点击选择员工"  onclick="do_SelectPerson()"></td>
						<td width="6%" height="20" align="right">厂商名称：</td>
						<td width="19%" height="20" align="right"><input class="input_style2" type="text" name="vendorName"  style="width:100%" value="<%=dto.getVendorName()%>" class="readonlyInput" readonly title="点击选择供应商" onclick="do_SelectVendor()"></td>
						<td width="6%" height="20" align="right">厂商编号：</td>
						<td width="19%" height="20"><input class="input_style2" type="text" name="vendorNumber"  style="width:100%" value="<%=dto.getVendorNumber()%>" class="readonlyInput" readonly title="点击选择供应商" onclick="do_SelectVendor()"></td>
					</tr>
					<tr>
						<td width="6%" height="20" align="right">费用账户：</td>
						<td width="19%" height="20" align="right" bgcolor="#E6E6E6"><input class="input_style2" type="text" name="depreciationAccount"  style="width:100%" value="<%=dto.getDepreciationAccount()%>" class="readonlyInput" readonly title="点击选择费用账户" onClick="do_SelectDepreciationAccount(this)"></td>
						<td width="6%" height="20" align="right">资产地点：</td>
						<td width="19%" height="20" align="right" bgcolor="#E6E6E6"><input class="input_style2" type="text" name="workorderObjectName"  style="width:100%" value="<%=dto.getWorkorderObjectName()%>" class="readonlyInput" readonly title="点击选择资产地点" onclick="do_SelectAssetsLocation()"></td>
						<td width="6%" height="20" align="right">项目编号：</td>
						<td width="19%" height="20" align="right"><input class="input_style2" type="text" name="projectNumber"  style="width:100%" value="<%=dto.getProjectNumber()%>" class="readonlyInput" readonly title="点击选择项目编号" onclick="do_SelectProject()"></td>
						<td width="6%" height="20" align="right">采购单号：</td>
						<td width="19%" height="20"><input class="input_style1" type="text" name="T18"  style="width:100%"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td width="100%" height="20" align="right" bgcolor="#E6E6E6">
			<img border="0" src="/images/eam_images/search.jpg" onClick="do_SearchAssets()">
			<img border="0" src="/images/eam_images/export.jpg" onClick="do_ExportAssets()">
			</td>
		</tr>
	</table>
	<input type="hidden" name="act" value="">	
	</form>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:185px;left:1px;width:990px">
		<table class="headerTable" border="1" width="500%">
			<tr height="20px">
				<td align=center width="2%">标签号</td>
				<td align=center width="2%">资产编号</td>
				<td align=center width="2%">资产类别1</td>
				<td align=center width="8%">资产类别2</td>
				<td align=center width="2%">类别代码1</td>
				<td align=center width="2%">类别代码2</td>
				<td align=center width="2%">类别代码</td>
				<td align=center width="3%">资产名称</td>
				<td align=center width="3%">资产型号</td>
				<td align=center width="2%">设备分类</td>
				<td align=center width="3%">设备名称</td>
				<td align=center width="3%">设备型号</td>
				<td align=center width="1%">计量单位</td>
				<td align=center width="1%">资产数量</td>
				<td align=center width="2%">资产原值</td>
				<td align=center width="1%">使用年限</td>
				<td align=center width="2%">启用日期</td>
				<td align=center width="2%">资产净值</td>
				<td align=center width="2%">累计折旧</td>
				<td align=center width="3%">折旧账户</td>
				<td align=center width="2%">资产残值</td>
				<td align=center width="2%">资产账簿</td>
				<td align=center width="1%">项目编号</td>
				<td align=center width="3%">项目名称</td>
				<td align=center width="1%">厂家编号</td>
				<td align=center width="3%">厂家名称</td>
				<td align=center width="1%">资产状态</td>
				<td align=center width="4%">责任部门</td>
				<td align=center width="1%">责任人</td>
				<td align=center width="2%">责任人员工号</td>
				<td align=center width="1%">使用人</td>
				<td align=center width="1%">地点代码</td>
				<td align=center width="6%">地点简称</td>
				<td align=center width="8%">所在地点</td>
				<td align=center width="3%">所在区县</td>
				<td align=center width="3%">所属公司</td>
			</tr>
		</table>
	</div>
<%
	if(hasData){
%>
	<div id="dataDiv" style="overflow:scroll;height:60%;width:1007px;position:absolute;top:207px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="500%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%		Row row = null;
		String barcode = "";
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			barcode = row.getStrValue("BARCODE");
%>		
			<tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="2%"><input type="text" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY1")%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("SEGMENT1")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("SEGMENT2")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("FA_CATEGORY_CODE")%>"></td>				
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_CATEGORY_NAME")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
				<td width="1%"><input type="text" class="finput" readonly value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
				<td width="1%"><input type="text" class="finput3" readonly value="<%=row.getValue("CURRENT_UNITS")%>"></td>
				<td width="2%"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
				<td width="1%"><input type="text" class="finput3" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
				<td width="2%"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>
				<td width="2%"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRECIATION")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPRECIATION_ACCOUNT")%>"></td>
				<td width="2%"><input type="text" class="finput3" readonly value="<%=row.getValue("SCRAP_VALUE")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("BOOK_TYPE_CODE")%>"></td>
				<td width="1%"><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
				<td width="1%"><input type="text" class="finput2" readonly value="<%=row.getValue("VENDOR_NUMBER")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("VENDOR_NAME")%>"></td>
				<td width="1%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_STATUS_NAME")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
				<td width="1%"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
				<td width="1%"><input type="text" class="finput" readonly value="<%=row.getValue("MAINTAIN_USER_NAME")%>"></td>
				<td width="1%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
				<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("COUNTY_NAME")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			</tr>
<%
		}
%>
		</table>
	</div>
<div style="position:absolute;top:576px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%		
	}
%>	
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script>

/**
 * 功能:选择员工
 */
function do_SelectPerson(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
	var dialogWidth = 47;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.responsibilityUserName.value = obj["userName"];
		mainFrm.employeeNumber.value = obj["employeeNumber"];
	} else {
		mainFrm.responsibilityUserName.value = "";
		mainFrm.employeeNumber.value = "";
	}
}

/**
 * 功能:选择地点
 */
function do_SelectAssetsLocation() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=orgId%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (objs) {
		var obj = objs[0];
		mainFrm.workorderObjectName.value = obj["toObjectName"];
	} else {
		mainFrm.workorderObjectName.value = "";
	}
}


/**
 * 功能:选择厂商
 */
function do_SelectVendor() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_VENDOR%>";
	var dialogWidth = 38;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
	} else {
		mainFrm.vendorName.value = "";
		mainFrm.vendorNumber.value = "";
	}
}

/**
 * 功能:选择项目
 */
function do_SelectProject() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
	} else {
		mainFrm.projectNumber.value = "";
	}
}

/**
 * 功能：选择费用账户
 */
function do_SelectDepreciationAccount(obj){
	var userPara = "organizationId=<%=orgId%>&accountCode2=";
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ACCOUNT%>";
	var dialogWidth = 51;
	var dialogHeight = 29;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		obj = objs[0];
		obj.value = obj["accountCode"];
	} else {
		lineBox.value = "";
	}
}


function do_SearchAssets(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_ExportAssets(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
    
</script>
