<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-6-5
  Time: 19:23:22
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);

    boolean hasData = (rows != null && !rows.isEmpty());
%>
<title>通服台帐维护--查询数据</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage()" onkeydown="autoExeFunction('do_Search();')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.newasset.servlet.ItemVillageServlet" method="post" name="mainFrm">
    <input type="hidden" name="act" value="">
	<input type="hidden" name="itemCode" value="">
	<input type="hidden" name="addressId" value="">
	<input type="hidden" name="responsibilityUser" value="">
	<input type="hidden" name="employeeNumber" value="">
	<input type="hidden" name="responsibilityDeptName" value="">
    <input type="hidden" name="workorderObjectCode" value="">
    <input type="hidden" name="specialityDeptName" value="">
    <input type="hidden" name="remark" value="">
    <input type="hidden" name="specialityUser" value="">

    <input type="hidden" name="projectid" value="">
    <input type="hidden" name="manufacturerId" value="">
    <input type="hidden" name="contentCode" value="">
    <input type="hidden" name="otherInfo" value="">
<table border="1" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <tr height="20px">
        <td align="right" width="7%">公司：</td>
        <td width="25%"><select style="width:100%" class="select_style1" name="organizationId" onchange="getDeptOption();"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select></td>
        <td width="7%" align="right">设备名称：</td>
		<td width="16%"><input type="text" name="itemName" class="input_style1" style="width:85%" value="<%=dto.getItemName()%>"><a href=""  title="点击选择设备名称" onclick="do_SelectItemName(); return false;">[…]</a></td>
        <td width="7%" align="right">设备条码：</td>
		<td width="14%"><input type="text" name="barcode" class="input_style1" style="width:100%" value="<%=dto.getBarcode()%>"></td>
        <td width="10%" align="center">使用人：</td>
		<td width="14%"><input type="text" name="maintainUser" class="input_style1" style="width:100%" value="<%=dto.getMaintainUser()%>"></td>
    </tr>
	<tr height="20px">
		<td align="right" width="7%">实物部门：</td>
        <td width="25%"><select name="specialityDept" style="width:100%" class="select_style1"><%=request.getAttribute("DEPT_OPTIONS2")%></select></td>
        <td width="7%" align="right">规格型号：</td>
        <td width="16%"><input type="text" name="itemSpec" class="input_style1" value="<%=dto.getItemSpec()%>" style="width:85%"><a href="" title="点击选择规格型号" onclick="do_SelectItemName(); return false;">[…]</a></td>
        <td width="7%" align="right">启用日期：</td>
		<td width="14%"><input type="text" name="startDate" class="input_style1" value="<%=dto.getStartDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
        <td width="10%" align="center">到</td>
		<td width="14%"><input type="text" name="endDate" class="input_style1" value="<%=dto.getEndDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
	</tr>
    <tr height="20px">
        <td width="7%" align="right">责任部门：</td>
		<td width="25%"> <div id="responsibilityDept1"><select style="width:100%" class="select_style1" name="responsibilityDept"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></div></td>
        <td width="7%" align="right">责任人：</td>
		<td width="16%"><input type="text" name="responsibilityUserName" class="input_style1" value="<%=dto.getResponsibilityUserName()%>" style="width:85%" readonly><a style="cursor:'hand'" onclick="do_SelectPerson();">[…]</a></td>
        <td width="7%" align="right">创建日期：</td>
		<td width="14%"><input type="text" name="startCreationDate" class="input_style1" value="<%=dto.getStartCreationDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fStartPop(startCreationDate, endCreationDate)"></td>
        <td width="10%" align="center">到</td>
		<td width="14%"><input type="text" name="endCreationDate" class="input_style1" value="<%=dto.getEndCreationDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fEndPop(startCreationDate, endCreationDate)"></td>
    </tr>
    <tr height="20px">
		<td width="7%" align="right">设备状态：</td>
		<td width="25%"><select name="itemStatus" class="select_style1" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS)%></select></td>
		<td width="7%" align="right">所在地点：</td>
        <td width="16%"><input type="text" name="workorderObjectName" class="input_style1" value="<%=dto.getWorkorderObjectName()%>" style="width:85%" ><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
        <td width="7%" align="right">失效日期：</td>
		<td width="14%"><input type="text" name="startDisableDate" class="input_style1" value="<%=dto.getStartDisableDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fStartPop(startDisableDate, endDisableDate)"></td>
        <td width="10%" align="center">到</td>
		<td width="14%"><input type="text" name="endDisableDate" class="input_style1" value="<%=dto.getEndDisableDate()%>" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fEndPop(startDisableDate, endDisableDate)"></td>
    </tr>
    <tr height="20px">
		<td width="7%" align="right">是否生效：</td>
		<td width="25%">
            <select name="isAbate" style="width:100%" class="select_style1">
                <option value="">--请选择--</option>
                <option value="Y" <%=dto.getIsAbate().equals("Y")?"selected":""%>>失效</option>
                <option value="N" <%=dto.getIsAbate().equals("N")?"selected":""%>>生效</option>
             </select>
        </td>
        <td width="7%" align="right">资产目录：</td>
        <td width="16%"><input type="text" name="contentName" class="input_style1" value="<%=dto.getContentName()%>" style="width:85%" ><a href="" title="点击选择资产目录" onclick="do_SelectContent(); return false;">[…]</a></td>
        <td width="7%" align="right">厂商：</td>
		<td width="14%"><input type="text" name="manufacturerName" class="input_style1" value="<%=dto.getManufacturerName()%>" style="width:85%"><a href="" title="点击选择厂商" onclick="do_selectNameManufacturer(); return false;">[…]</a></td>
        <td width="10%" align="center">实际数量：</td>
        <%
        if (dto.getActualQty() == 0 || dto.getActualQty() == -1) {
        %>
        <td width="14%"><input type="text" name="actualQty" class="input_style1" style="width:100%" value=""></td>
        <%	
        } else {
        %>	
        <td width="14%"><input type="text" name="actualQty" class="input_style1" style="width:100%" value="<%=dto.getActualQty()%>"></td>
        <%	
        }
        %>
    </tr>
    <tr height="20px">
		<td width="7%" align="right">通服资产类型：</td>
		<td width="25%">
            <select name="tfType" style="width:100%" class="select_style1">
                <option value="">--请选择--</option>
                <option value="DG_ASSETS" <%=dto.getTfType().equals("DG_ASSETS")?"selected":""%>>村通</option>
                <option value="QD_ASSETS" <%=dto.getTfType().equals("QD_ASSETS")?"selected":""%>>渠道</option>
             </select>
        </td>
        <td width="7%" align="right">共享状态：</td>
        <td width="16%"><select class="select_style1" name="isShare" style="width:85%"><%=request.getAttribute("SHARE_OPTION")%></select></td>
        <td width="7%" align="right">共建状态：</td>
		<td width="14%"><select class="select_style1" name="constructStatus" style="width:85%"><%=request.getAttribute("CONSTRUCT_OPTION")%></select></td>
        <td width="24%" colspan=2 align="right">
            <img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
            <img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();">
            <img src="/images/eam_images/batch_update.jpg" alt="点击更新设备" onclick="do_UpdateItem();">
        </td>
    </tr>

</table>


</form>
<div id="aa" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:121px;left:0px;width:100%" class="crystalScroll">
	<table border=1 width="450%" class="eamHeaderTable">
		<tr class=headerTable height="15px" onclick="executeClick(this)" style="cursor:hand" title="点击全选或全不选">
			<td align=center width=1%><input type="checkbox" name="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
            <td align=center width=2%>公司</td>
            <td align=center width=2%>设备条码</td>
            <td align=center width=2%>设备分类</td>
            <td align=center width=4%>设备名称</td>
            <td align=center width=4%>规格型号</td>

            <td align=center width=3%>地点代码</td>
            <td align=center width=4%>地点简称</td>
            <td align=center width=2%>设备状态</td>
            <td align=center width=2%>是否生效</td>
            <td align=center width=2%>计量单位</td>

            <td align=center width=2%>数量</td>
            <td align=center width=2%>实际数量</td>
            <td align=center width=5%>责任部门</td>
            <td align=center width=2%>责任人</td>

            <td align=center width=2%>使用人</td>
            <td align=center width=5%>实物部门</td>
            <td align=center width=2%>设备性能</td>
            <td align=center width=2%>资产类别代码</td>
            <td align=center width=4%>资产类别描述</td>

            <td align=center width=3%>资产启用日期</td>
            <td align=center width=2%>资产类型</td>
            <td align=center width=3%>项目</td>
            <td align=center width=3%>厂商</td>
            <td align=center width=2%>共享状态</td>

            <td align=center width=2%>共建状态</td>
            <td align=center width=2%>资产原值</td>
            <td align=center width=2%>资产净值</td>
            <td align=center width=2%>资产净额</td>
            <td align=center width=2%>折旧年限</td>

            <td align=center width=6%>备注</td>
       </tr>
	</table>
</div>
<div id="bb" style="overflow:scroll;height:68%;width:100%;position:absolute;top:143px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="450%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		Row row = null;
        String barcode = "";
        for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
            barcode = row.getStrValue("BARCODE");
%>
        <tr class="dataTR">
		  <td width=1%  align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
		  <td width=2%><input type="text" class="finput2" readonly value="<%=row.getStrValue("COMPANY")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_CATEGORY")%>"></td>
		  <td width=4%><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
          <td width=4%><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>

          <td width=3%><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
          <td width=4%><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_STATUS_NAME")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("IS_ABATE")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>

          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_QTY")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("ACTUAL_QTY")%>"></td>
          <td width=5%><input type="text" class="finput2" readonly value="<%=row.getValue("RESPONSIBILITY_DEPT_NAME")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>

          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("MAINTAIN_USER")%>"></td>
          <td width=5%><input type="text" class="finput2" readonly value="<%=row.getValue("SPECIALITY_DEPT")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("OTHER_INFO")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("CONTENT_CODE")%>"></td>
          <td width=4%><input type="text" class="finput2" readonly value="<%=row.getValue("CONTENT_NAME")%>"></td>

          <td width=3%><input type="text" class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("FINANCE_PROP")%>"></td>
          <td width=3%><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
          <td width=3%><input type="text" class="finput2" readonly value="<%=row.getValue("MANUFACTURER_NAME")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("IS_SHARE")%>"></td>

          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("CONSTRUCT_STATUS")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("PRICE")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("TF_NET_ASSET_VALUE")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("TF_DEPRN_COST")%>"></td>
          <td width=2%><input type="text" class="finput2" readonly value="<%=row.getValue("TF_DEPRECIATION")%>"></td>
            
          <td width=6%><input type="text" class="finput2" readonly value="<%=row.getValue("REMARK")%>"></td>
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
<div id="navigatorDiv" style="position:absolute;top:570px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
</html>
<script type="text/javascript">
function initPage(){
	do_TransData();
}

function do_TransData(){
	var transTarget = null;
	if(!parent.updateDataFrm.document){
		return;
	}
	if(!parent.updateDataFrm.document.mainFrm){
		return;
	}
	if(!parent.updateDataFrm.document.mainFrm.checkedData){
		return;
	}
	transTarget = parent.updateDataFrm.document.mainFrm.checkedData;
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		transTarget.value = "";
	} else {
		var barcodes = new String(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value);
		barcodes = replaceStr(barcodes, "BARCODE:", "");
		barcodes = replaceStr(barcodes, "$$$", "  ");
		parent.updateDataFrm.document.mainFrm.checkedData.value = barcodes;
	}
}

function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);

    if (objs) {
        var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.workorderObjectName.value = obj["workorderObjectLocation"];
	} else {
        mainFrm.workorderObjectName.value = "";
    }
}
function do_SelectProject(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    } else {
        mainFrm.projectNumber.value = "";
        mainFrm.projectName.value = "";
    }
}

function do_SelectPerson(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
	var dialogWidth = 47;
	var dialogHeight = 30;
	var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(users){
		var user = users[0];
        mainFrm.employeeNumber.value = user["employeeNumber"];
        mainFrm.responsibilityUserName.value = user["userName"];
	} else {
        mainFrm.employeeNumber.value = "";
        mainFrm.responsibilityUserName.value = "";
    }
}


function do_UpdateItem(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询操作，选中相应资产后再保存！");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("没有选中一条资产，不能执行保存操作！");
		return;
	}
	var fieldNames = "itemCode;itemName;itemSpec;";
	fieldNames += "addressId;workorderObjectCode;workorderObjectName;";
	fieldNames += "responsibilityUser;employeeNumber;responsibilityUserName;responsibilityDept;";
	fieldNames += "responsibilityDeptName;maintainUser;specialityDept;isAbate;specialityDeptName";
	fieldNames += "projectid;manufacturerId;";
	fieldNames += "contentCode;contentName;actualQty;otherInfo;startDate;isShare;constructStatus;itemStatus";
    clearFieldValue(fieldNames);
    var srcFrm = parent.updateDataFrm.document.mainFrm;
    copyFrmValue(srcFrm, mainFrm);
	do_CopySelectData();
    do_CopySelectData2();
    var totalContent = "";
    with(mainFrm){
		totalContent += itemCode.value;
		totalContent += itemName.value;
		totalContent += itemSpec.value;
		
		totalContent += addressId.value;
		totalContent += workorderObjectCode.value;
		totalContent += workorderObjectName.value;
		totalContent += responsibilityUser.value;
		totalContent += employeeNumber.value;
		totalContent += responsibilityUserName.value; 
		totalContent += responsibilityDept.value;
		
		
		totalContent += responsibilityDeptName.value;
		totalContent += maintainUser.value;
        totalContent += specialityDept.value;
        totalContent += isAbate.value;
        totalContent += specialityDeptName.value;

        totalContent += projectid.value;
        totalContent += manufacturerId.value;

        totalContent += contentCode.value;
        totalContent += contentName.value;
        totalContent += actualQty.value;
        totalContent += otherInfo.value;
        totalContent += startDate.value;
        totalContent += isShare.value;
        totalContent += constructStatus.value;
        totalContent += itemStatus.value;

        if(totalContent == ""){
			alert("没有修改选中设备的任何信息，不能保存！");
			return;
		}
		if(confirm("确保要更新选中设备的数据吗？继续请点击“确定”按钮？否则请点击“取消”按钮")){
			act.value = "<%=AssetsActionConstant.UPDATE_ACTION%>";
			submit();
		} else {
			do_RollbackData();
		}
	}
}
/**
 * 功能：保留数据，以便用于数据恢复
 */
var oriDeptValue = mainFrm.responsibilityDept.value;
var oriItemName = mainFrm.itemName.value;
var oriItemSpec = mainFrm.itemSpec.value;
var oriItemCode = mainFrm.itemCode.value;
var oriResponsibilityUserName = mainFrm.responsibilityUserName.value;
var oriResponsibilityUser = mainFrm.responsibilityUser.value;
var oriEmployeeNumber = mainFrm.employeeNumber.value;
var oriAddressId = mainFrm.addressId.value;
var oriObjectCode = mainFrm.workorderObjectCode.value;
var oriObjectName = mainFrm.workorderObjectName.value;
var oriStartDate = mainFrm.startDate.value;
var oriMaintainUser = mainFrm.maintainUser.value;
var newDeptValue = "";

function do_CopySelectData(){
	var srcSelect = parent.updateDataFrm.document.mainFrm.responsibilityDept;
	var deptOpt = srcSelect.options[srcSelect.options.selectedIndex];
	var deptObj = mainFrm.responsibilityDept;
	var deptVal = deptOpt.value;
	if(!haveChild(deptObj, deptOpt)){
		var option = new Option(deptOpt.text, deptOpt.value);
		newDeptValue = deptOpt.value;
		deptObj.options[deptObj.length] = option;
	}
	selectSpecialOptionByItem(deptObj, deptOpt.value);
	if(deptOpt.value != ""){
		mainFrm.responsibilityDeptName.value = deptOpt.text;
	}
}
function do_CopySelectData2(){
	var srcSelect2 = parent.updateDataFrm.document.mainFrm.specialityDept;
	var deptOpt = srcSelect2.options[srcSelect2.options.selectedIndex];
	var deptObj = mainFrm.specialityDept;
	var deptVal = deptOpt.value;
	if(!haveChild(deptObj, deptOpt)){
		var option = new Option(deptOpt.text, deptOpt.value);
		newDeptValue = deptOpt.value;
		deptObj.options[deptObj.length] = option;
	}
	selectSpecialOptionByItem(deptObj, deptOpt.value);
	if(deptOpt.value != ""){
		mainFrm.specialityDeptName.value = deptOpt.text;
	}
}

function do_RollbackData(){
	mainFrm.itemName.value = oriItemName;
	mainFrm.itemSpec.value = oriItemSpec;
	mainFrm.itemCode.value = oriItemCode;
	mainFrm.responsibilityUserName.value = oriResponsibilityUserName;
	mainFrm.responsibilityUser.value = oriResponsibilityUser;
	mainFrm.employeeNumber.value = oriEmployeeNumber;
	mainFrm.addressId.value = oriAddressId;
	mainFrm.workorderObjectCode.value = oriObjectCode;
	mainFrm.workorderObjectName.value = oriItemName;
	mainFrm.startDate.value = oriStartDate;
	mainFrm.maintainUser.value = oriMaintainUser;
	var deptObj = mainFrm.responsibilityDept;
	selectSpecialOptionByItem(deptObj, oriDeptValue);
	if(newDeptValue != ""){
		dropSpecialOption(deptObj, newDeptValue);
	}
}

function do_ShowLog(barcode){
	var url = "<%=AssetsURLList.ITEM_MAINTAIN_SERVLET%>";
	url += "?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&barcode=" + barcode;
	var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
	window.open(url, 'applyInFlowWin', style);
}

function do_ShowDetail(barcode) {
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
}

function do_SelectItemName(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ITEMNAME%>";
	var dialogWidth = 45;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    } else {
        mainFrm.itemName.value = "";
    }
}
function do_selectSpecialityUser() {
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_SPECIAL_USER%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var orgId = mainFrm.organizationId.value;
    var userPara = "organizationId=" + orgId;
    var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if(users){
        var user = users[0];
        mainFrm.specialityUser.value = user["employeeId"];
        mainFrm.specialityUserName.value = user["userName"];
    } else {
        mainFrm.specialityUser.value = "";
        mainFrm.specialityUserName.value = "";
    }
}
function do_selectNameManufacturer() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_MANUFACTURER%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainFrm");
        }
    } else {
        mainFrm.manufacturerId.value = "";
        mainFrm.manufacturerName.value = "";
    }
}
function do_SelectContent() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainFrm");
        }
    } else {
        mainFrm.contentName.value = "";
        mainFrm.contentCode.value = "";
    }
}
 var xmlHttp;

    //-- checkObjectCode
    function getDeptOption() {
        var url = "";
        var organizationId = document.getElementById("organizationId").value;
        createXMLHttpRequest();
        if (organizationId != "") {
            url = "/servlet/com.sino.ams.newasset.servlet.ItemVillageServlet?act=GET_DEPT_OPTION&organizationId=" + organizationId;
            xmlHttp.onreadystatechange = handleReadyStateChange1;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
    }
    function createXMLHttpRequest() {     //创建XMLHttpRequest对象
        try {
            xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
            } catch(e) {
                try {
                    xmlHttp = new XMLHttpRequest();
                } catch(e) {
                    alert("创建XMLHttpRequest对象失败！");
                }
            }
        }
    }
    function handleReadyStateChange1() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                unescape(xmlHttp.responseText);
                var cf = document.getElementById("responsibilityDept1");
                cf.innerHTML = "<select class=\"select_style1\" name = responsibilityDept style=\"width:100%\">" + xmlHttp.responseText + "</select>";

            } else {
                alert(xmlHttp.status);
            }
        }
    }
</script>