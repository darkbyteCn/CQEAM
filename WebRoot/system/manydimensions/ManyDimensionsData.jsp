<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<title>多维度信息维护--查询数据</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('1.N');" onkeydown="autoExeFunction('do_Search();')">
<input type="hidden" name="helpId" value="">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="<%=AssetsURLList.MANY_DIMENSION_SERVLET%>" method="post" name="mainFrm">
    <input type="hidden" name="act" value="">
	<input type="hidden" name="itemCode" value="">
	<input type="hidden" name="itemSpec" value="">
	<input type="hidden" name="addressId" value="">
	<input type="hidden" name="responsibilityUser" value="">
	<input type="hidden" name="employeeNumber" value="">
	<input type="hidden" name="responsibilityDeptName" value="">
    <input type="hidden" name="workorderObjectCode" value="">
	<input type="hidden" name="lneId" value="">
    <input type="hidden" name="cexId" value="">
    <input type="hidden" name="opeId" value="">
    <input type="hidden" name="nleId" value="">
    <input type="hidden" name="flag" value="0">
<table border="0" width="100%" class="queryTable">
    <tr height="25px">
		<td width="10%" align="right">责任部门：</td>
		<td width="27%"><select class="select_style1" name="responsibilityDept" style="width:100%" onchange="do_PassDeptName(this);"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></td>
        <td width="8%" align="right">责任人：</td>
        <td width="14%"><input class="input_style1" type="text" name="responsibilityUserName" title="点击选择责任人" onclick="do_SelectPerson(); return false;" value="<%=dto.getResponsibilityUserName()%>" style="width:100%;cursor:hand" readonly></td>
        <td width="8%" align="right">标签号：</td>
		<td width="11%"><input class="input_style1" type="text" name="fromBarcode" style="width:100%" value="<%=dto.getFromBarcode()%>"></td>
        <td width="6%" align="right">到：</td>
		<td width="11%"><input class="input_style1" type="text" name="toBarcode" style="width:100%" value="<%=dto.getToBarcode()%>"></td>
	</tr>
	<tr height="35px">
        <td align="right">地点：</td>
		<td>
            <input type="text" name="workorderObjectName"  title="点击选择地点"  class="input_style1"
            onclick="do_SelectAddress(); return false;" value="<%=dto.getWorkorderObjectName()%>" style="width:100%;cursor:hand" readonly>
            <%--<a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a>--%>
        </td>
		<td align="right">设备专业：</td>
		<td><select class="select_style1" name="itemCategory" style="width:100%"><%=dto.getItemCategoryOpt()%></select></td>
		<td align="right">设备名称：</td>
		<td colspan="3"><input class="input_style1" type="text" name="itemName" style="width:39.5%" value="<%=dto.getItemName()%>"><a href=""  title="点击选择设备名称" onclick="do_SelectItemName(); return false;">[…]</a></td>
    </tr>

    <tr height="25px">
        <td align="right" >多维度信息为空：</td>
        <td colspan="4">
            <input type="checkbox" name="manyDimensionsIsNull" value="lne" <%=dto.isLneIsNull() == true ? "checked" : " "%>>逻辑网络元素&nbsp;&nbsp;
            <input type="checkbox" name="manyDimensionsIsNull" value="cex" <%=dto.isCexIsNull() == true ? "checked" : " "%>>投资分类&nbsp;&nbsp;
            <input type="checkbox" name="manyDimensionsIsNull" value="ope" <%=dto.isOpeIsNull() == true ? "checked" : " "%>>业务平台&nbsp;&nbsp;
            <input type="checkbox" name="manyDimensionsIsNull" value="nle" <%=dto.isNleIsNull() == true ? "checked" : " "%>>网络层次
        </td>
        <td width="24%" align="right" colspan="3">
            <img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
            <img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();">
            <img src="/images/eam_images/batch_update.jpg" alt="点击更新设备" onclick="do_UpdateItem();">
		</td>
	</tr>
</table>
</form>

<div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:85px;left:0px;width:100%" class="crystalScroll">
	<table border=1 width="100%" class="eamHeaderTable">
		<tr height="20" onclick="executeClick(this)" style="cursor:hand" title="点击全选或全不选">

			<td align=center width=1%><input type="checkbox" name="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
			<td align=center width=3%>标签号</td>
			<td align=center width=4%>设备名称</td>
			<td align=center width=3%>设备型号</td>

			<td align=center width=4%>地点代码</td>
			<td align=center width=7%>地点简称</td>

			<td align=center width=3%>逻辑网络元素</td>
			<td align=center width=3%>投资分类</td>
			<td align=center width=3%>业务平台</td>
            <td align=center width=3%>网络层次</td>
</tr>
	</table>
</div>
<div style="overflow:scroll;height:75%;width:100%;position:absolute;top:107px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		Row row = null;
        String barcode = "";
        for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
            barcode = row.getStrValue("BARCODE");
%>
        <tr class="dataTR"  style="cursor:hand">

		  <td width=1%  align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
		  <td width=3%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getStrValue("BARCODE")%>"></td>
          <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
		  <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>

          <td width=4%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
          <td width=7%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>

          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LOG_NET_ELE")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("INVEST_CAT_NAME")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("OPE_NAME")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LNE_NAME")%>"></td>

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
<div id="navigatorDiv" style="position:absolute;top:93%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
</html>
<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
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

function do_SelectPerson(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
	var dialogWidth = 47;
	var dialogHeight = 30;
	var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(users){
		var user = users[0];
		mainFrm.responsibilityUserName.value = user["userName"];
	} else {
        mainFrm.responsibilityUserName.value = "";
        mainFrm.projectName.value = "";
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
	/*var fieldNames = "itemCode;itemName;itemSpec;";
	fieldNames += "addressId;workorderObjectCode;workorderObjectName;";
	fieldNames += "responsibilityUser;employeeNumber;responsibilityUserName;responsibilityDept;";
	fieldNames += "responsibilityDeptName;maintainUser;maintainDeptName;startDate";
    fieldNames += "manufacturerId;contentCode;contentName;isShare;power;itemStatus";
	fieldNames += "lneId;cexId;opeId;nleId";
*/
//    clearFieldValue(fieldNames);
	var srcFrm = parent.updateDataFrm.document.mainFrm;
	copyFrmValue(srcFrm, mainFrm);
//	do_CopySelectData();
	var totalContent = "";
	with(mainFrm){
		/*totalContent += itemCode.value;
		totalContent += itemName.value;
		totalContent += itemSpec.value;
		totalContent += addressId.value;
		totalContent += workorderObjectCode.value;
		totalContent += workorderObjectName.value;
		totalContent += responsibilityDept.value;
		totalContent += responsibilityDeptName.value;
		totalContent += responsibilityUser.value;
		totalContent += employeeNumber.value;
		totalContent += responsibilityUserName.value;*/

        totalContent += lneId.value;
        totalContent += cexId.value;
        totalContent += opeId.value;
        totalContent += nleId.value;

		if(totalContent == ""){
			alert("没有修改选中设备的任何信息，不能保存！");
			return;
		}
        if (document.mainFrm.flag.value == "1") {
                alert("正在更新数据，请等待。");
                return;
        }
        if(confirm("确保要更新选中设备的数据吗？继续请点击“确定”按钮？否则请点击“取消”按钮")){
            document.mainFrm.flag.value="1";
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
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
	var deptObj = mainFrm.responsibilityDept;
	selectSpecialOptionByItem(deptObj, oriDeptValue);
	if(newDeptValue != ""){
		dropSpecialOption(deptObj, newDeptValue);
	}
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
</script>
