<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.*"%>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO"%>
<%@page import="com.sino.config.SinoConfig"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
	<head>
		<title>地点信息维护</title>
		<style type="text/css" media="screen">
body {
	font: 11px arial;
}

.suggest_link {
	background-color: #FFFFFF;
	padding: 0px 6px 2px 6px;
}

.suggest_link_over {
	background-color: #CCCCCC;
	padding: 0px 6px 2px 6px;
}

#search_suggest {
	position: absolute;
	background-color: #FFFFFF;
	text-align: left
}
</style>
		<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	</head>
	<%
		EtsObjectDTO dto = (EtsObjectDTO) request.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
	%>
	<body leftmargin="0" topmargin="0" leftmargin="0"
		onload="do_initPage();">
		<form method="post" name="mainFrm"
			action="/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet">
			<script type="text/javascript">
    printTitleBar("地点信息维护");
    var btnCount = ArrActions.length;
    for(var i = 0; i < btnCount; i++){
        ArrActions[i][0] = false;
    }
    ArrActions[0] = new Array(true, "保存", "action_save.gif", "保存", "do_SaveObject");
    ArrActions[1] = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
    ArrActions[2] = new Array(<%=!dto.getWorkorderObjectNo().equals("")%>, "新增", "action_edit.gif", "新增", "do_CreateObject");
    printToolBar();
</script>
			<table width="90%" align="center" border="1" bordercolor="#666666">
				<tr>
					<td width="20%" align="right">
						公司名称：
					</td>
					<td width="80%" colspan="3">
						<select class='select_style1' name="organizationId" id="organizationId" style="width: 100%" onchange="do_ChangeOrg(this);"><%=dto.getOrganizationOption()%></select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						地点专业：
					</td>
					<td width="80%" colspan="3">
						<select class='select_style1' name="objectCategory" id="objectCategory" style="width: 100%" onchange="to_object_code()" onclick="do_Cancle();"><%=dto.getObjCategoryOption()%></select>
					</td>
				</tr>
				<tr>
					<td align="right" width="15%">
						所属区域：
					</td>
					<td width="35%">
						<select name="countyCode" id="countyCode" class="select_style1" style="width: 100%" onchange="do_ProduceWorkOrderObjectCode()"><%=dto.getCountyOption()%></select>
					</td>
					<td align="right" width="15%">
						经纬度：
					</td>
					<td width="35%">
						<input name="latitudeLongitude" type="text" id="latitudeLongitude" style="width: 100%" value="<%=dto.getLatitudeLongitude()%>" class='input_style1'>
					</td>
				</tr>
				<tr id="bts">
					<td align="right" id="btsTd">
						基站或营业厅编号：
					</td>
					<td colspan="3">
						<input name="btsNo" type="text" id="btsNo" value="<%=dto.getBtsNo()%>" class='input_style1' style="width: 100%" onblur="do_ValidateBtsNo();">
					</td>
				</tr>
				<tr>
					<td align="right" width="15%">
						新增物理地点：
					</td>
					<td>
						<select class='select_style1' onclick="do_IsHasObjectClick();" onchange="do_IsHasObject();" name="isHasObject" style="width: 100%">
							<option value="Y" selected>
								是
							</option>
							<option value="N">
								否
							</option>
						</select>
					</td>
					<td align="right">
						物理地点代码：
					</td>
					<td>
						<div id="loc2DescDiv" style="display: none">
							<input name="loc2Code" type="text" id="loc2Code" value="<%=dto.getLoc2Code()%>" class='input_style1' onclick="do_Cancle();" readonly style="width: 87%">
							<a href="#" onClick="chooseLocDesc()" title="点机选择地点第二段" class="linka">[…]</a>
						</div>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						地点组合代码：
					</td>
					<td width="80%" colspan="3">
						<input class='input_style1' name="workorderObjectCode" type="text" id="workorderObjectCode" value="<%=dto.getWorkorderObjectCode()%>" style="width: 100%" readonly onblur="do_ValidateObjectCode();">
					</td>
				</tr>
				<tr>
					<td align="right">
						项目名称：
					</td>
					<td colspan="3">
						<input name="projectName" type="text" id="projectName" value="<%=dto.getProjectName()%>" class='input_style1' onclick="do_Cancle();" readonly style="width: 92%">
						<a href="#" onClick="choosePrj()" title="点机选择工程" class="linka">[…]</a>
					</td>
				</tr>
				<tr>
					<td align="right">
						行政区划：
					</td>
					<td colspan="3">
						<select onclick="do_Cancle();" name="areaType" class="select_style1" style="width: 100%"><%=dto.getAreaTypeOption()%></select>
					</td>
				</tr>
				<tr>
					<td align="right">
						地市：
					</td>
					<td colspan="3">
						<select class="select_style1" id="city" name="city" onPropertyChange="do_ChangeCounty(this);" style="width: 100%"><%=dto.getCityOption()%></select>
					</td>
				</tr>
				<tr>
					<td align="right">
						区/县：
					</td>
					<td colspan="3">
						<select class="select_style1" id="county" name="county" style="width: 100%"><%=dto.getCountyOptions()%></select>
					</td>
				</tr>
<%
    if (dto.getWorkorderObjectNo().equals("")) {
%>
				<tr>
					<td align="right">
						物理地点描述：
					</td>
					<td colspan="3">
						<input class='input_style1' style="width: 100%" type="text" id="location" name="location" value="<%=dto.getLocation()%>" onkeyup="do_ValidateLocationName();" autocomplete="off"/>
						<div id="search_suggest">
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						地点名称：
					</td>
					<td colspan="3">
						<input name="workorderObjectName" type="text" id="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" class='input_style1' style="width: 100%" readonly="readonly">
					</td>
				</tr>
<%
    } else {
%>
				<tr>
					<td align="right">
						物理地点描述：
					</td>
					<td colspan="3">
						<input class='input_style1' style="width: 100%; display: none" type="text" id="location" readonly="readonly" name="location" value="<%=dto.getLocation()%>" autocomplete="off" />
						<br>
						<div id="search_suggest">
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						地点名称：
					</td>
					<td colspan="3">
						<input name="workorderObjectName" type="text" id="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" class='input_style1' style="width: 100%">
					</td>
				</tr>
<%
    }
%>
				<tr>
					<td align="right">
						辅助信息：
					</td>
					<td>
						<input name="auxiliaryInfo" type="text" id="auxiliaryInfo"
							style="width: 100%" value="<%=dto.getAuxiliaryInfo()%>"
							class='input_style1'>
					</td>
					<td align="right">
						是否TD地点：
					</td>
					<td>
						<select class='select_style1' onchange="do_Cancle();" name="isTd"
							style="width: 100%" disabled="true"><%=dto.getIsTdOption()%></select>
					</td>
				</tr>
				<tr>
					<td align="right">
						地点规则描述：
					</td>
					<td colspan="3">
						<textarea name="objetcRemark" height="120" type="areatext" id="objetcRemark" value="" style="width: 100%"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" height="60">
						备注：
					</td>
					<td height="60" colspan="3">
						<textarea name="remark" onclick="do_Cancle();" id="remark" value="<%=StrUtil.htmlStrEncode(dto.getRemark())%>" class="input_style1" style="width: 100%; height: 100%"><%=dto.getRemark()%></textarea>
					</td>
				</tr>
			</table>
			<input name="workorderObjectNo" type="hidden" id="workorderObjectNo" value="<%=dto.getWorkorderObjectNo()%>">
			<input name="projectId" type="hidden" id="projectId" value="<%=dto.getProjectId()%>">
			<input name="provinceCode" type="hidden" id="provinceCode" value="<%=dto.getProvinceCode() %>"/>
			<input type="hidden" name="act" id="act" value="<%=dto.getAct()%>">
			<input name="warnWorkorderObjectName" type="hidden" id="warnWorkorderObjectName" value="" />
		</form>
		<jsp:include page="/message/MessageProcess" />
		<div align="center" id="warnExist"
			style="color: red; visibility: hidden">			
		</div>
		<div align="center" id="warnAddressName" style="visibility: hidden"></div>
	</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js"
	id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm"
	scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;"></iframe>
<script type="text/javascript"><!--

function suggestOver(div_value) {
	div_value.className = 'suggest_link_over';
}

function suggestOut(div_value) {
	div_value.className = 'suggest_link';
}

function setSearch(value) {
	document.getElementById('location').value = value;
	document.getElementById('search_suggest').innerHTML = '';
	
	var county = mainFrm.county.value;
	var city = mainFrm.city.value;
	var countyCode = mainFrm.countyCode.value;
	var organizationId = mainFrm.organizationId.value;
	var location = document.getElementById('location').value;
	var currCitySelectText = document.all.city.options[document.all.city.selectedIndex].text;
	var currCountySelectText = document.all.county.options[document.all.county.selectedIndex].text;
	var currcountyCodeSelectText = document.all.countyCode.options[document.all.countyCode.selectedIndex].text;
	var temp = 0;
	if(countyCode !=""){
		mainFrm.workorderObjectName.value =currcountyCodeSelectText+"."+currCitySelectText+currCountySelectText+location+"."+temp+temp+temp;
	}
}

function do_Cancle(){
	document.getElementById('search_suggest').innerHTML = '';
}

function do_Close() {
	if(confirm("请确保已经保存了你的工作！继续请点击“确定”按钮，否则请点击“取消”按钮！")){
		self.close();
		opener.do_Search();
	}
}

function do_SaveObject() {
    var fieldNames = "organizationId;objectCategory;countyCode;workorderObjectCode;city;county;location;workorderObjectName;isTd";
    var fieldLabels = "公司名称;地点专业;所属区域;地点组合代码;地市;区/县;物理地点描述;地点名称;是否TD地点";
    if (document.getElementById("act").value == "") {
    	fieldNames = "organizationId;objectCategory;countyCode;workorderObjectCode;city;county;workorderObjectName;isTd";
    	fieldLabels = "公司名称;地点专业;所属区域;地点组合代码;地市;区/县;地点名称;是否TD地点";
    }
    var isHasObject = mainFrm.isHasObject.value;
    if (isHasObject=="N") {
    	fieldNames = "organizationId;objectCategory;countyCode;workorderObjectCode;location;workorderObjectName;isTd";
    	fieldLabels = "公司名称;地点专业;所属区域;地点组合代码;物理地点描述;地点名称;是否TD地点";
    }    
    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
        var objLength=document.getElementById("workorderObjectCode").value.length;
        do_ValidateWorkorderObjectName();
        if (mainFrm.warnWorkorderObjectName.value != "" && getElementById("warnExist").innerText != "") {
        	return false;
        }
        if(objLength==25){
            mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            setFrmEnable("mainFrm");
            do_ControlEditable(false);
            mainFrm.submit();
            self.close();
            opener.do_Search();
        } else {
            alert("请确认地点组合代码已经填写正确，必须是公司代码加地点类型加8位流水号一共25位！")
        }
    }
}

function choosePrj() {
	document.getElementById('search_suggest').innerHTML = '';
    var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
        dto2Frm(projects[0], "mainFrm");
    }
}

function chooseLocDesc() {
	document.getElementById('search_suggest').innerHTML = '';
	var lookUpName = "<%=LookUpConstant.LOOK_UP_LOC2DESC%>";
	var userPara = "organizationId="+document.getElementById("organizationId").value+"&objectCategory=" + document.getElementById("objectCategory").value;
	userPara += "&countyCode=" + document.getElementById("countyCode").value;
    var dialogWidth = 20;
    var dialogHeight = 10;
    var locCode = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var currcountyCodeSelectText = document.all.countyCode.options[document.all.countyCode.selectedIndex].text;
    var provinceCode = mainFrm.provinceCode.value;
    var suffix = "";
    var isTd = mainFrm.isTd.value;
    if (isTd == "N") {
	   	if(provinceCode == "25" || provinceCode == "33"){//河南、广西
	       	suffix = "." + "<%=SinoConfig.getEamLoc3ASet_Name() %>";
	   	} else {
	   	   	suffix = "." + "<%=SinoConfig.getEamLoc3Set_Name() %>";
	   	}
   	} else {
   		if (provinceCode == "<%=DictConstant.PROVINCE_CODE_JIN %>") { //山西
   			suffix = "." + "<%=SinoConfig.getEamLoc3TdSet_Name() %>";
   		} else {
   			suffix = "." + "<%=SinoConfig.getEamLoc3Set_Name() %>";
   		}   		
   	}
    if(locCode){
    	document.getElementById("city").disabled = false;
		document.getElementById("county").disabled =false;
        dto2Frm(locCode[0], "mainFrm");
		document.getElementById("city").disabled = true;
		document.getElementById("county").disabled = true;
        mainFrm.workorderObjectName.value = currcountyCodeSelectText + "." + locCode[0].location + suffix;
    }
    do_ProduceWorkOrderObjectCode();
}

function do_ChooseCounty(county){
	
}

function do_CreateObject() {
    var url = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
	url += "?act=<%=WebActionConstant.DETAIL_ACTION%>";
	url += "&workorderObjectNo=";
	location.href = url;
}

function do_ValidateObjectCode() {
	document.getElementById('search_suggest').innerHTML = '';
	if(mainFrm.workorderObjectCode.value != ""){
        var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
        var userParameters = "act=VALIDATE_ACTION";
        userParameters += "&organizationId=" + mainFrm.organizationId.value;
        userParameters += "&workorderObjectCode=" + mainFrm.workorderObjectCode.value;
        userParameters += "&workorderObjectNo=" + mainFrm.workorderObjectNo.value;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseObjectCode, false);
        ajaxProcessor.setSendData(userParameters);
        ajaxProcessor.performTask();
	}
}

function do_ValidateWorkorderObjectName() {
	document.getElementById('search_suggest').innerHTML = '';
	if( mainFrm.workorderObjectName.value != ""){
        var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
        var userParameters = "act=VALIDATE_WORKORDEROBJECTNAME";
        userParameters += "&workorderObjectName=" + mainFrm.workorderObjectName.value;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseObjectName, false);
        ajaxProcessor.setSendData(encodeURI(encodeURI(userParameters)));
        ajaxProcessor.performTask();
	}
}

function do_ValidateBtsNo() {
	document.getElementById('search_suggest').innerHTML = '';
	if(mainFrm.workorderObjectCode.value != "" && mainFrm.btsNo.value!=""){
        var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
        var userParameters = "act=VALIDATE_BTSNO";
        userParameters += "&btsNo=" + mainFrm.btsNo.value;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseObjectCode, false);
        ajaxProcessor.setSendData(userParameters);
        ajaxProcessor.performTask();
	}
}

function do_ResponseObjectName(resText) {
	var workorderObjectName = mainFrm.workorderObjectName.value;
	var startIndex = workorderObjectName.indexOf(".");
    var endIndex = workorderObjectName.lastIndexOf(".");
    var count = workorderObjectName.split(".").length-1;
	if(resText == "Y"){
		alert("地点名称已存在！");
		mainFrm.warnWorkorderObjectName.value = "地点名称已存在";
    //} else if (startIndex < 0 || endIndex != workorderObjectName.length - 3 || endIndex < startIndex || count!=2){
    } else if (startIndex < 0 || endIndex < startIndex || count!=2){
    	alert("地点名称不规范,应该为三段名称！");
    	mainFrm.warnWorkorderObjectName.value = "地点名称不规范";
    } else {
    	mainFrm.warnWorkorderObjectName.value = "";
    }
}

function do_ResponseObjectCode(resText){
	var objectCat = document.getElementById("objectCategory").value;
    with(document){
        if(resText == "Y"){
        	if (objectCat=="10") {
        		getElementById("warnExist").innerText = "对不起，该基站或营业厅编号已存在!";
        	} else {
        		getElementById("warnExist").innerText = "对不起，该基站或营业厅编号已存在!";
        	}
			
			getElementById("warnexist").innerText = "对不起，该地点号已存在!";
            getElementById("warnExist").style.visibility = "visible";
        } else {
        	getElementById("warnExist").innerText = "";
            getElementById("warnExist").style.visibility = "hidden";
        }
    }
}

function do_ValidateLocationName() {
    var locationValue = mainFrm.location.value;
    if(!isEmpty(locationValue)){
        var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
        actionURL += "?act=VALIDATE_LOCATION_ACTION";
        actionURL += "&location=" + locationValue;
        actionURL += "&organizationId=" + mainFrm.organizationId.value;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseLocation, false);
        ajaxProcessor.performTask();
    }
    var countyCode = mainFrm.countyCode.value;
    if(countyCode !=""){
        var cityName = mainFrm.city.options[mainFrm.city.selectedIndex].text;
        var countyName = mainFrm.county.options[mainFrm.county.selectedIndex].text;
        var areaName = mainFrm.countyCode.options[mainFrm.countyCode.selectedIndex].text;
        var provinceCode = mainFrm.provinceCode.value;
        var suffix = "";
	    var isTd = mainFrm.isTd.value;
	    if (isTd == "N") {
		   if(provinceCode == "25" || provinceCode == "33"){
		       suffix = "." + "<%=SinoConfig.getEamLoc3ASet_Name() %>";
		   } else {
		   	   suffix = "." + "<%=SinoConfig.getEamLoc3Set_Name() %>";
		   }
	   	} else {
	   		if (provinceCode == "<%=DictConstant.PROVINCE_CODE_JIN %>") { //山西
	   			suffix = "." + "<%=SinoConfig.getEamLoc3TdSet_Name() %>";
	   		} else {
	   			suffix = "." + "<%=SinoConfig.getEamLoc3Set_Name() %>";
	   		}   		
	   	}
        mainFrm.workorderObjectName.value =areaName+"."+cityName+countyName+locationValue+ suffix;
    }
}

function do_ResponseLocation(resText){
	with(document){
		if(resText.length > 2){
			search_suggest.outerHTML = resText;
		} else {
			document.getElementById("warnAddressName").style.visibility = "hidden";
		}
	}
}

function do_IsHasObjectClick() {
	var objectCategory = mainFrm.objectCategory.value;
	var countyCode = mainFrm.countyCode.value;
	if (objectCategory=="" || countyCode=="") {
		alert("请先选择地点专业和所属区域");
		return;
	}
}

function do_IsHasObject() {
	var isHasObject = mainFrm.isHasObject.value;
	var loc2Desc = document.getElementById("loc2DescDiv");
	if (isHasObject=="N") {
		loc2Desc.style.display="block";
		document.getElementById("city").disabled="true";
		document.getElementById("county").disabled="true";
		document.getElementById("location").readOnly = true;
	} else {
		mainFrm.loc2Code.value = "";
		loc2Desc.style.display="none";
		document.getElementById("city").disabled="";
		document.getElementById("county").disabled="";
		document.getElementById("location").readOnly = false;
	}
	do_ProduceWorkOrderObjectCode();
}

var oldCity = mainFrm.city.value;

function do_ChangeCounty(obj) {
	var newCity = obj.value;
	if(newCity == oldCity){
		return;
	}
	oldCity = newCity;
    var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
    var userParameters = "act=CHANGE_COUNTYS";
    userParameters += "&city=" + obj.value;
    var ajaxProcessor = new AjaxProcessor(actionURL, do_ShowCountyOption, false);
    ajaxProcessor.setSendData(userParameters);
    ajaxProcessor.performTask();
}

function do_ShowCountyOption(resText){
    mainFrm.county.outerHTML = "<select class=\"select_style1\"  style=\"width:100%\" name=\"county\">" + resText + "</select>";
}

function do_InitBts(objectCat) {
	if(objectCat=="10"){   
    	document.getElementById("btsTd").innerText = "基站或营业厅编号："; 	
    	document.getElementById("bts").style.display="block";
    } else if (objectCat=="50") {
    	document.getElementById("btsTd").innerText = "基站或营业厅编号：";
    	document.getElementById("bts").style.display="block";
    } else {
    	document.getElementById("bts").style.display="none";
    }
}

function do_InitOrg(obj) {
	var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
    var userParameters = "act=CHANGE_ORG";
    userParameters += "&organizationId=" + obj;
    var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseOrgChange, false);
    ajaxProcessor.setSendData(userParameters);
    ajaxProcessor.performTask();
}

function do_ChangeOrg(obj) {
    do_Cancle();
    mainFrm.location.value = "";
    mainFrm.workorderObjectCode.value = "";
    mainFrm.workorderObjectName.value = "";
    var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
    var userParameters = "act=CHANGE_ORG";
    userParameters += "&organizationId=" + obj.value;
    var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseOrgChange, false);
    ajaxProcessor.setSendData(userParameters);
    ajaxProcessor.performTask();
}

/**
 * 将返回的列表加入所属区域下拉框
 * 修改完成。
 */

function do_ResponseOrgChange(resText){
    var resArr = resText.split("&");
    if(resArr[0] != "EMPTY_CONTENT"){
        mainFrm.countyCode.outerHTML = "<select  name=\"countyCode\"  class=\"select_style1\" style=\"width:100%\" onchange=\"do_ProduceWorkOrderObjectCode()\">" + resArr[0] + "</select>";
    }
    if(resArr[1] != "EMPTY_CONTENT"){
        selectSpecialOptionByItem(mainFrm.isTd, resArr[1]);
    }
}

function to_object_code(){
    var objectCat = document.getElementById("objectCategory").value;
    var categoryArr = new Array("10", "15", "20", "66", "40", "25", "30", "50", "60", "35", "55", "80", "45");
    var remarkArr = new Array("指所有2G/3G/村通等涉及到的基站地点如果存在多站共址的，只能建立一个地点编码；可以选择三种编码方式的一种： 1)编码中包含基站编号 2)存量地点编码以流水号编，新增地点编码可用基站编号；或存量地点编码以基站编号，新增地点编码以流水号编 3)以流水号编码",
            "无线接入地点，指WLAN、室内分布等地点； -	室内分布按照建筑物编制地点  -	室内WLAN按照建筑物编制地点     -	室外WLAN按照某期工程包含的热点数量编制地点",
            "综合接入地点，指独立存在的汇聚机房地点 ",
            "适用于存放在集团客户方及家庭客户方的有线接入设备地点（家庭客户端设备地点按照小区地点编制，省公司可在此基础上进行细化；集团客户端设备地点按照客户地点编制） ",
            "适用于光缆、电缆、杆路、管道等资产的地点（传输线路地点的区划分类归属判断：如果一个传输线路地点跨越了多个区划分类，如同时跨越了直辖市和郊县，则按照此传输线路覆盖的主体区域所属区划进行判断，如果无法判断则按照此传输线路的起点位置所属区划进行归类）",
            "综合机房地点，指各专业专用机房以及综合类机房的地点 （按照独立存在的房间区分地点，并粘贴标签）",
            "适用于存放直放站等资产的地点 （指独立存在的直放站地点）",
            "营业地点，包含所有营业厅在内的相关地点 （按照一个营业厅整体编制一个地点规则，省公司可在此基础上进行细化）",
            "客服地点，包含客户服务等相关地点，包含VIP客户中心等地点 （按照楼层进行地点编码，省公司可在此基础上进行细化）",
            "适用于存放资产的仓库的地点（按照一个仓库整体编制一个地点规则，省公司可在此基础上进行细化） ",
            "每个地市（帐套）可设置一个虚拟的出租资产地点编码 （该地点不包括房屋类的出租资产，省公司可以在此基础上细化进行编码）",
            "其他地点，指除了以上十类地点以外的其他地点，如：食堂、员工活动中心等地点",
            "适用于存放在办公区地点内资产的地点 （按照楼层进行地点编码，省公司可在此基础上进行细化，如细化到房间/门牌号）");
    var count = categoryArr.length;
    for(var i = 0; i < count; i++){
        if(categoryArr[i] == objectCat){
            document.getElementById("objetcRemark").value = remarkArr[i];
            break;
        }
    }
    if(objectCat=="10"){   
    	document.getElementById("btsTd").innerText = "基站或营业厅编号："; 	
    	document.getElementById("bts").style.display="block";
    } else if (objectCat=="50") {
    	document.getElementById("btsTd").innerText = "基站或营业厅编号：";
    	document.getElementById("bts").style.display="block";
    } else {
    	document.getElementById("bts").style.display="none";
    }
    do_ProduceWorkOrderObjectCode();
}

function do_ProduceWorkOrderObjectCode(){
    var areaValue = mainFrm.countyCode.value;
    var objectCat = mainFrm.objectCategory.value;
    var organizationId = mainFrm.organizationId.value;
    if(objectCat != "" && areaValue != ""){
        var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
        var userParameters = "act=GET_MAX_OBJECT_CODE";
        userParameters += "&organizationId=" + organizationId;
        userParameters += "&objectCategory=" + objectCat;
        userParameters += "&countyCode=" + areaValue;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ShowWorkOrderObjectCode, false);
        ajaxProcessor.setSendData(userParameters);
        ajaxProcessor.performTask();
    }
}

function do_ShowWorkOrderObjectCode(resText){
	var isHasObject = mainFrm.isHasObject.value;
	var loc2Code = document.getElementById("loc2Code").value;
	var resTextLength = resText.length - 4;
	if (isHasObject=="N" && loc2Code!="") {
		resText = resText.replace(resText.substring(resText.indexOf(".")+1,resText.length-4), loc2Code);
	}
    mainFrm.workorderObjectCode.value = resText;
}

function do_initPage(){
    window.focus();
    var act = document.getElementById("act").value;
    if(act == ""){
        //do_InitOrg("<%=dto.getOrganizationId()%>");
    } else {
        do_InitBts("<%=dto.getObjectCategory()%>");
    }
    do_ControlEditable(true);
}

function do_ControlEditable(disableProp){
    with(mainFrm) {
        if(workorderObjectNo.value != ""){
            var disableObjs = new Array(organizationId, isHasObject, objectCategory, countyCode, latitudeLongitude, workorderObjectCode);
            for(var i = 0; i < disableObjs.length; i++){
                disableObjs[i].disabled = disableProp;
            }
        }
    }
}
--></script>