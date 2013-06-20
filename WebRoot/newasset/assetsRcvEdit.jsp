<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	String transId = headerDTO.getTransId();
	String transStatus = headerDTO.getTransStatus();
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
</head>
<body leftmargin="0" topmargin="0" onload="window.focus();">
<form action="<%=AssetsURLList.ASSETS_TRANS_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/newasset/headerDetail.jsp" flush="true"/>
<fieldset style="border:1px solid #397DF3; position:absolute;top:123px;width:100%;height:80%">
    <legend>
        <img src="/images/eam_images/receive.jpg" id="img6" alt="接收" onClick="do_Receive(); return false;">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="window.close(); return false;">
		<input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">统一分配地点</label>
		<input type="checkbox" name="allUser" id="allUser"><label for="allUser">统一分配用户</label>
    </legend>
    <table class=headerTable border=1 style="width:990px">
        <tr height=20px>
            <td align=center width="10%">标签号</td>
            <td align=center width="8%">设备专业</td>
            <td align=center width="20%">设备名称</td>
            <td align=center width="20%">设备型号</td>
            <td align=center width="12%">责任部门</td>
            <td align=center width="20%">分配到地点</td>
            <td align=center width="8%">分配到用户</td>
        </tr>
    </table>
    <div style="width:1007px;overflow-y:scroll;height:520px">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
    if (lineSet != null && !lineSet.isEmpty()) {
		AmsAssetsTransLineDTO lineDTO = null;
		String barcode = "";
	    for (int i = 0; i < lineSet.getSize(); i++) {
	        lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
			if(transStatus.equals(AssetsDictConstant.COMPLETED)){
%>
            <tr class="dataTR" onClick="executeClick('<%=barcode%>')" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
                <td align="center" width="10%"><input type="text" class="finput" readonly name="barcode" id="barcode<%=i%>" value="<%=lineDTO.getBarcode()%>"></td>
                <td align="center" width="8%"><input type="text" class="finput" readonly name="itemCategoryName" id="itemCategoryName<%=i%>" value="<%=lineDTO.getItemCategoryName()%>"></td>
                <td align="left" width="20%"><input type="text" class="finput" readonly name="itemName" id="itemName<%=i%>" value="<%=lineDTO.getItemName()%>"></td>
                <td align="left" width="20%"><input type="text" class="finput" readonly name="itemSpec" id="itemSpec<%=i%>" value="<%=lineDTO.getItemSpec()%>"></td>
                <td align="right" width="12%"><input type="text" class="finput" readonly name="deptName" id="deptName<%=i%>" value="<%=lineDTO.getDeptName()%>"></td>
				<td align="right" width="20%"><input type="text" class="finput" readonly name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" value="<%=lineDTO.getWorkorderObjectLocation()%>"></td>
                <td align="center" width="8%"><input type="text" class="finput" readonly name="responsibilityUserName" id="responsibilityUserName<%=i%>" value="<%=lineDTO.getResponsibilityUserName()%>"></td>
            </tr>
<%
			} else {
%>
            <tr class="dataTR" onClick="executeClick('<%=barcode%>')" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
                <td align="center" width="10%"><input type="text" class="finput" readonly name="barcode" id="barcode<%=i%>" value="<%=lineDTO.getBarcode()%>"></td>
                <td align="center" width="8%"><input type="text" class="finput" readonly name="itemCategoryName" id="itemCategoryName<%=i%>" value="<%=lineDTO.getItemCategoryName()%>"></td>
                <td align="left" width="20%"><input type="text" class="finput" readonly name="itemName" id="itemName<%=i%>" value="<%=lineDTO.getItemName()%>"></td>
                <td align="left" width="20%"><input type="text" class="finput" readonly name="itemSpec" id="itemSpec<%=i%>" value="<%=lineDTO.getItemSpec()%>"></td>
                <td align="right" width="12%"><input type="text" class="finput" readonly name="deptName" id="deptName<%=i%>" value="<%=lineDTO.getDeptName()%>"></td>
				<td align="right" width="20%"><input type="text" style="width:100%; border: 1px solid #FFFFFF; cursor:hand" readonly class="noEmptyInput" name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" value="<%=lineDTO.getWorkorderObjectLocation()%>" title="点击选择或更改分配地点" onClick="do_SelectLocation(this)"></td>
                <td align="center" width="8%"><input type="text" style="width:100%; border: 1px solid #FFFFFF; cursor:hand" readonly class="noEmptyInput" name="responsibilityUserName" id="responsibilityUserName<%=i%>" value="<%=lineDTO.getResponsibilityUserName()%>" title="点击选择或更改分配用户" onClick="do_SelectPerson(this)"></td>
				<input type="hidden"  name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
				<input type="hidden"  name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>">
				<input type="hidden"  name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
            </tr>
<%
			}
        }
    }
%>
        </table>
    </div>
</fieldset>
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="act">
</form>
</body>
</html>
<script>

function do_Receive(){
	var fieldNames = "workorderObjectLocation;responsibilityUserName";
	var fieldLabels = "分配地点;分配用户";
	var validateType = EMPTY_VALIDATE;
	if(formValidate(fieldNames, fieldLabels, validateType)){
		mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
		mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AssetsReceiveServlet";
		mainFrm.submit();
	}
}


/**
  * 功能：选择分配用户
 */
function do_SelectPerson(textObj){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
	var dialogWidth = 32;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	var id = textObj.id;
	var userChk = mainFrm.allUser;
	if(!userChk.checked){
		var name = textObj.name;
		id = id.substring(name.length);
		var personObj = document.getElementById("responsibilityUser" + id);
		if (objs) {
			var dto = objs[0];
			textObj.value = dto["toPersonName"];
			personObj.value = dto["userId"];
		} else {
			textObj.value = "";
			personObj.value = "";				
		}
	} else {
		var dto = null;
		if (objs) {
			dto = objs[0];
		} else {
			dto = new Object();
			dto.toPersonName = "";
			dto.userId = "";
		}
		var userIds = document.getElementsByName("responsibilityUser");
		var userNames = document.getElementsByName("responsibilityUserName");
		var count = userIds.length;
		for(var i = 0; i < count; i++){
			userIds[i].value = dto["userId"];
			userNames[i].value = dto["toPersonName"];
		}
	}
}

/**
  * 功能：选择分配地点
 */
function do_SelectLocation(textObj){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	var addressChk = mainFrm.allLocation;
	if(!addressChk.checked){
		var id = textObj.id;
		var name = textObj.name;
		id = id.substring(name.length);
		var objectLo = "assignedToLocation" + id;
		var address = "addressId" + id;
		var locationObj = document.getElementById(objectLo);
		var addressObj = document.getElementById(address);
		if (objs) {
			var dto = objs[0];
			textObj.value = dto["workorderObjectLocation"];
			locationObj.value = dto["toObjectNo"];
			addressObj.value = dto["addressId"];
		} else {
			textObj.value = "";
			locationObj.value = "";	
			addressObj = "";
		}
	} else {
		var dto = null;
		if (objs) {
			dto = objs[0];
		} else {
			dto = new Object();
			dto.workorderObjectLocation = "";
			dto.toObjectNo = "";
			dto.addressId = "";
		}
		var addressIds = document.getElementsByName("addressId");
		var addressNames = document.getElementsByName("workorderObjectLocation");
		var addressNos = document.getElementsByName("assignedToLocation");
		var count = addressIds.length;
		for(var i = 0; i < count; i++){
			addressIds[i].value = dto["addressId"];
			addressNames[i].value = dto["workorderObjectLocation"];
			addressNos[i].value = dto["toObjectNo"];
		}
	}
}


function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
</script>
