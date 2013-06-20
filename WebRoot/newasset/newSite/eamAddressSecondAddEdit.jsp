<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newSite.dto.EamAddressAddHDTO " %>
<%@ page import="com.sino.ams.newSite.dto.EamAddressAddLDTO " %>
<%
	EamAddressAddHDTO batchDTO = (EamAddressAddHDTO) request.getAttribute(AssetsWebAttributes.ADDRESS_HEAD_DATA);
	EtsObjectDTO etsObjectDTO = (EtsObjectDTO) request.getAttribute("EtsObjectDTO");
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    String transStatus = batchDTO.getTransStatus();
 %>
<html>
<head>
<title>资产物理地点维护</title>
    <script type="text/javascript" src="/WebLibary/js/test.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util2.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
</head>

<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" rightmargin="1" onload="initPage();helpInit('2.M.1');"  onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<input type="hidden" name="helpId" value="">
<form action="/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<script type="text/javascript">
   printToolBar();
</script>
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="overflow:hidden;position:absolute;top:28px;left:0px;width:100%;">
<table border="0" width="100%" style="border-collapse: collapse;top:0px;" id="table1">
	<tr>
		<td>
			<table width=100% border="0" align="center">
			    <tr align="center">
			        <td align=right width="6%" height="18">单据编号：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="transNo" class="input_style2" readonly style="width:100%" 	value="<%=batchDTO.getTransNo() %>" size="20"></td>
			        <td align=right width="6%" height="18">单据类型：</td>
			        <td width="20%" height="18">
			          <input type="text" name="transType" class="input_style2" readonly style="width:100%"  value="<%=batchDTO.getTransType()%>" size="20">
			        <td align=right width="6%" height="18">紧急程度：</td>
			        <td width="15%" height="18">
<%
    if ("IN_PROCESS".equals(transStatus)) {
%>
						<select name="emergentLevel" class="select_style1" disabled style="width:100%" ><%=batchDTO.getEmergentLevelOption()%></select>
<%
    } else {
%>
				        <select name="emergentLevel" class="select_style1" style="width:100%"  onchange="document.getElementById('sf_priority').value = this.value;"><%=batchDTO.getEmergentLevelOption()%></select>
<%
    }
%>
						<input type="text" name="groupName"  class="input_style2" readonly style="width:100%;cursor:pointer;display:none;"  value="<%=batchDTO.getDeptName()%>"  onClick="do_SelectAppGroup()" size="20"></td>
			    </tr>
			    <tr>
			        <td align=right width="6%" height="18">公司名称：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="organizationName" class="input_style2" readonly style="width:100%" value="<%=batchDTO.getOrganizationName() %>" size="20"></td>
                    <td align=right width="10%" height="18">部门名称：</td>
			        <td width="20%" height="18">
			         <input type="text" name="deptName" class="input_style2" readonly style="width:100%;cursor:pointer"  value="<%=batchDTO.getDeptName() %>" size="20"></td>
                    <td align=right width="10%" height="18">创建日期：</td>
                    <td width="15%" height="18">
			         <input type="text" name="creationDate" class="input_style2" readonly style="width:100%;cursor:pointer" value="<%=batchDTO.getCreationDate()%>"  size="20"></td>
			    </tr>
			    <tr>
			    	<td align=right width="6%" height="18">创建人：</td>
			        <td width="15%" height="18">
					   <input type="text" name="createdByName" class="input_style2" readonly style="width:100%" value="<%=batchDTO.getCreatedByName() %>">
					</td>
                    <td align=right width="10%" height="40">创建原因：</td>
			        <td width="40%" height="40" colspan="3">
<%
    if ("IN_PROCESS".equals(transStatus)) {
%>
			           	<textarea name="createdReason" readonly style="width:100%; height:100%"><%=batchDTO.getCreatedReason()%></textarea>
<%
    } else {
%>
				        <textarea name="createdReason" style="width:100%; height:100%"><%=batchDTO.getCreatedReason()%></textarea>
<%
    }
%>
					</td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
</div>
<input type="hidden" name="transId"  value="<%=batchDTO.getTransId()%>">
<input type="hidden" name="transStatus"   value="<%=batchDTO.getTransStatus() %>">
<input type="hidden" name="organizationId" value="<%=batchDTO.getOrganizationId() %>">
<input type="hidden" name="createdBy" value="<%=batchDTO.getCreatedBy() %>">
<input type="hidden" name="dept"  value="<%=batchDTO.getDept() %>">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="act" value="">
<input type="hidden" name="excel" value="">
<input type="hidden" name="excelPath"  value="" >
<div id="buttonDiv" style="overflow:hidden;position:absolute;top:125px;left:0px;width:100%;">
<%
    if ("form1".equals(actInfo.getSfactTaskAttribute1())) {
%>
        <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"  onClick="do_excel();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除" onClick="deleteLine(); return false;">
        <img src="/images/eam_images/choose.jpg" alt="添加行" onclick="add_addr_lines();">
        <!--<input type="text" name="lgh" id="lgh" size="1" value=""/>行数-->
<%
    }
%>
</div>
    <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%;">
    <table class=headerTable border=1 style="width:100%">
        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
            <td align=center width="2%">
             <input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="8%">地点专业</td>
            <td align=center width="10%">基站或营业厅编号</td>
            <td align=center width="8%">行政区域</td>
            <td align=center width="7%">地市</td>
            <td align=center width="7%">区县</td> 
            <td align=center width="20%">物理地点描述</td>
            <td align=center width="12%">物理地点代码</td>
            <td align=center width="7%">是否共享</td>
            <td align=center width="7%">维护类型</td>
            <td align=center width="12%">错误提示</td>
        </tr>
    </table>
	</div>

    <div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:48px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
     <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
    <%
		if (lineSet == null || lineSet.isEmpty()) {
     %>
            <!--<tr>
                <td colspan="10"><font style="color: red;">请在此处增加记录!</font> </td>
            </tr>-->            
            <tr class="dataTR" style="display:none">
			<td width="2%">
			<td width="8%">
			<td width="10%">
			<td width="8%">
			<td width="7%">
			<td width="7%">
			<td width="20%">
			<td width="12%">
			<td width="7%">
			<td width="7%">
			<td width="12%">
           </tr>
<%
 	  } else {
			EamAddressAddLDTO lineDTO = null;
		  for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (EamAddressAddLDTO) lineSet.getDTO(i);
			String shareTypeName = "";
            if (lineDTO.getShareType().equals("1")) {
            	shareTypeName = "仅上市公司使用";
            } else if (lineDTO.getShareType().equals("2")) {
            	shareTypeName = "上市和TD共用";
            } else {
            	shareTypeName = "仅TD使用";
            }
%>
            <tr class="dataTR" errorMessage="<%=StrUtil.htmlStrEncode(lineDTO.getErrorMessage())%>" onmouseover="showAltValue(this)" onmouseout="hideAltValue()">
				<td width="2%" align="center">
				<input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=lineDTO.getLineId() %>">
				<input type="checkbox" name="subCheck" id="subCheck0" value="<%=i+1%>"></td>
				<td width="8%" align="center" style="cursor:pointer" >
				  <input type="text" name="objectCategory" id="objectCategory<%=i%>" class="finput" readonly value="<%=lineDTO.getObjectCategory()  %>"></td>
				<td width="10%" align="center" style="cursor:pointer" >
				  <input type="text" name="btsNo" id="btsNo<%=i%>" class="finput" readonly value="<%=lineDTO.getBtsNo() %>"></td>
				<td width="8%" align="center" style="cursor:pointer" >
				  <input type="text" name="areaType" id="areaType<%=i%>" class="finput" readonly value="<%=lineDTO.getAreaType()  %>"></td>
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="city" id="city<%=i%>" class="finput" readonly value="<%=lineDTO.getCity()  %>"></td>
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="county" id="county<%=i%>" class="finput" readonly value="<%=lineDTO.getCounty()  %>"></td>
				<td width="20%" align="center" style="cursor:pointer" >
				  <input type="text" name="workorderObjectName" id="workorderObjectName<%=i%>" class="finput" readonly value="<%=lineDTO.getWorkorderObjectName() %>"></td>
				<td width="12%" align="center" style="cursor:pointer" >
				  <input type="text" name="workorderObjectCode" id="workorderObjectCode<%=i%>" class="finput2" readonly value="<%=lineDTO.getWorkorderObjectCode()%>"></td>
				<td width="7%" align="center" style="cursor:pointer" >				  
				  <input type="text" name="shareTypeName" id="shareTypeName<%=i%>" readonly class="finput" value="<%=shareTypeName%>">
				  <input type="hidden" name="shareType" id="shareType<%=i%>" value="<%=lineDTO.getShareType() %>">
				</td>
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="addrMaintainType" id="addrMaintainType<%=i%>" class="finput" readonly value="<%=lineDTO.getAddrMaintainType()  %>"></td>
				<td width="12%" align="center" style="cursor:pointer">
				  <input type="text" name="errorMessage" id="errorMessage<%=i%>" class="finput" readonly value="<%=lineDTO.getErrorMessage()%>"></td>
				  <input type="hidden" name="countyCode" id="countyCode<%=i%>" class="finput" readonly value="<%=lineDTO.getCountyCode()%>">
            </tr>
   <%
			}
		}
   %>
     </table>
    </div>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
<input type="hidden" name="countyCache">
<input type="hidden" name="errorMsgCache">
</form>
<div id="hiddenDiv" style="position: absolute; overflow:scroll;width:550px;height:180px; display:none;background-color:#C6EBF4;color:red"></div>
</body>
</html>

<script type="text/javascript"><!--

/**
  *Excel导入
 **/

 function do_excel() {
    excelType = "4";
   var returnValue = do_ImportExcelData();
    if (returnValue) {
    	if(!isLoaded) {
	        alert("页面数据没有完全载入, 暫时不能保存");
	        return;
	    }
	    do_ProcessEmergent();
	    autoValue(AV_SAVE_MASK);
	    if(!validation()) return;
	    SFQuerySave();
	    if(!Launch_Continue) {
	        if(Error_Msg != "")
	            alert(Error_Msg);
	        return;
	    }
	    clearDivRight();
	    saveInfo();
	    SFPostSave();
	    isSave = true;

        //isSave=true;
        document.mainFrm.excelPath.value = returnValue;
        document.mainFrm.excel.value = "1";
        document.mainFrm.act.value="<%=AssetsActionConstant.IMPORT_ACTION%>";
        document.mainFrm.submit();
//        do_Save_app ();
    }
}

function deleteLine(){
   deleteTableRow(dataTable, 'subCheck');
}

//*************Ajax处理_begin***********

function createXMLHttpRequest() {
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

function submitExcel(){
    createXMLHttpRequest();
    url = "/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet?act=getExcelPath";
    xmlHttp.onreadystatechange = processor2;
    xmlHttp.open("post", url, true);
    xmlHttp.setRequestHeader('Content-type', 'multipart/form-data');
    xmlHttp.send(null);
}

   //用来处理状态改变的函数
   function processor2(){
     var responseContext;
     if(xmlHttp.readyState == 4){
        if(xmlHttp.status == 200){
          //取出服务器的响应内容
          responseContext = xmlHttp.responseText;
          document.getElementsByName("excelPath")[0].innerText=responseContext;
          document.getElementsByName("excel")[0].innerText="1";
         do_Save_app ();
	   }
	  }
	}

//*************Ajax处理_end***********
var lgh = 1;
//***************以下是流程相关JavaScript*****************************
function initPage() {
    window.focus();
    do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();
    do_MarkErrorRows();
   	if (<%="".equals(batchDTO.getDeptName()) %>) {
	    document.mainFrm.groupId.value=document.getElementById("flow_group_id").value;
	    document.mainFrm.groupName.value=document.getElementById("flow_group_name").value;
	    document.mainFrm.dept.value=document.getElementById("app_dept_code").value;
	    document.mainFrm.deptName.value=document.getElementById("app_dept_name").value;
  	}  	
  	var lineNumArr = document.getElementsByName("lineId");
    for (var i = 0; i < lineNumArr.length; i++) {
        if (lgh <= lineNumArr[i].value) 
        lgh=Number(lineNumArr[i].value) + 1;
    }
}

function do_Save_app () {                 //保存
	var size=document.getElementsByName("subCheck").length;
	if (mainFrm.excel.value == "2") {
		if (do_Validate() != undefined) {
			return false;
		}
	} else {
		if( size>0){
	      var error =document.getElementsByName("errorMessage");
	      for(var i=0; i<size;i++ ){
	        if(error[i].value!=""){
	            alert("存在错误信息，请检查后再导入！"+error[i].value);
	         	return false;
	        }
	      }
	    } else {
	    	alert("请导入地点信息！");
	    	return false;
	    }
	}
    mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

/**
 * 功能：要进行附件管理的页面需要覆盖本方法。
 */
function setAttachmentConfig(){
    var attchmentConfig = new AttachmentConfig();
    attchmentConfig.setOrderPkName("transId");
    return attchmentConfig;
}

function do_AppValidate() {
	var isValid = false;
	var size=document.getElementsByName("subCheck").length;
	var error = document.getElementsByName("errorMessage");
	if (mainFrm.excel.value == "2") {
		if (do_Validate() == undefined) {
			isValid = true
		}
	} else {
		if( size>0){
			isValid = true;
	        for(var i=0; i<size;i++ ){
	            if(error[i].value!=""){
	                alert("存在错误信息，请检查后再导入！"+error[i].value);
	                isValid = false ;
	                break;
	            }
	        }
	    }else{
	    	alert("请导入地点信息！");
	    }
	}
	return isValid;
}

function do_MarkErrorRows(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var rowCount = rows.length;
    if(rowCount == 1 && rows[0].style.display == "none"){
    	return;
    }
    for(var i = 0; i < rows.length; i++){
        var row = rows[i];
        do_SetCellInputBordorColor(row, "red");
    }
}

function do_SetCellInputBordorColor(tr, color){
    var cells = tr.cells;
    var cellCount = cells.length;
    for(var i = 0; i < cellCount; i++){
        var cell = cells[i];
        var nodes = cell.childNodes;
        if(nodes[0].tagName == "INPUT"){
            nodes[0].style.border = "1px";
            nodes[0].style.borderColor = color;
        }
    }
}

function showAltValue(tr){
    do_SetTrCheckedColor(tr);
    var errorMessage = tr.getAttribute("errorMessage");
    if(errorMessage == ""){
        return;
    }
    var bodyHeight = document.body.clientHeight;
    var screenHeight = window.screen.height;
    if(bodyHeight + 72 > screenHeight){
        bodyHeight = screenHeight - 72;
    }
    var rowNumber = new Number((tr.rowIndex + 1));
    var divObj = document.getElementById("hiddenDiv");
    var top = tr.offsetTop;
    var height = tr.clientHeight;
    while (tr = tr.offsetParent) {
        top += tr.offsetTop;
    }
    var left = event.clientX;
    left += document.documentElement.scrollLeft;    
    divObj.style.left = left;

    var divTop = top + height;
    divObj.style.top = divTop;
    var divHeight = divObj.offsetHeight;
    var searchHeight = searchDiv.offsetHeight;
    if(divHeight + divTop + searchHeight + 72> bodyHeight){
        divTop = bodyHeight - divHeight - searchHeight - 92;
        divObj.style.bottom = bodyHeight - searchHeight - 23;
        divObj.style.top = divTop;
    } else {
        divObj.style.top = divTop;
    }
    divObj.style.display = "block";
    errorMessage = "<p align='center'>第"
            + rowNumber
            + "行的错误信息"
            + "</p><hr color='white' width='100%' size='1'>"
            + errorMessage;
    divObj.innerHTML = errorMessage;
}

var checkedColor = "#FFFF99";

function do_SetTrCheckedColor(tr){
    var errorMessage = tr.getAttribute("errorMessage");
    if(errorMessage != ""){
        do_SetCellInputColor(tr, checkedColor);
    }
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    for(var i = 0; i < rows.length; i++){
        var row = rows[i];
        if(row.rowIndex == tr.rowIndex){
            continue;
        }
        do_SetCellInputColor(row, "#FFFFFF");
    }
}

function do_SetCellInputColor(tr, color){
    var cells = tr.cells;
    var cellCount = cells.length;
    for(var i = 0; i < cellCount; i++){
        var cell = cells[i];
        var nodes = cell.childNodes;
        if(nodes[0].tagName == "INPUT"){
            nodes[0].style.backgroundColor = color;
        }
    }
}

function hideAltValue(){
    var clientX = event.clientX;
    var clientY = event.clientY;
    var hiddenDiv = document.getElementById("hiddenDiv");
    var offsetLeft = hiddenDiv.offsetLeft;
    var offsetTop = hiddenDiv.offsetTop;
    var offsetWidth = hiddenDiv.offsetWidth;
    var offsetHeight = hiddenDiv.offsetHeight;
    if(clientX < offsetLeft ||  clientX > (offsetLeft + offsetWidth)){
        hiddenDiv.style.display = "none";
    } else if(clientY < offsetTop ||  clientY > (offsetTop + offsetHeight)){
        hiddenDiv.style.display = "none";
    }
}

function add_addr_lines2() {
	var fieldNames = getFieldNames(dataTable);
	var valueDTO = new Object();
	for(var j = 0; j < columnCount; j++){
      	valueDTO[fieldNames[j]] = fieldNames[j].value;
	}
	appendDTO2Table(dataTable, valueDTO, false, "lineId" );
}

function add_addr_lines() {
	mainFrm.excel.value = "2";
	//lgh = mainFrm.lgh.value;
	//for (i=0;i<lgh;i++) {
	   var tab = document.getElementById("dataTable");
	   var obj = tab.insertRow(-1);
       var td0 = document.createElement("TD");
       td0.align = "center";
       td0.width = "2%";        
       var td1 = document.createElement("TD");
       td1.align = "center";
       td1.width = "8%";
       var td2 = document.createElement("TD");
       td2.align = "center";
       td2.width = "10%";
       var td3 = document.createElement("TD");
       td3.align = "center";
       td3.width = "8%";
       var td4 = document.createElement("TD");
       td4.align = "center";
       td4.width = "7%";
       var td5 = document.createElement("TD");
       td5.align = "center";
       td5.width = "7%";
       var td6 = document.createElement("TD");
       td6.align = "center";
       td6.width = "20%";
       var td7 = document.createElement("TD");
       td7.align = "center";
       td7.width = "12%";
       var td8 = document.createElement("TD");
       td8.align = "center";
       td8.width = "7%";
       var td9 = document.createElement("TD");
       td9.align = "center";
       td9.width = "7%";
       var td10 = document.createElement("TD");
       td10.align = "center";
       td10.width = "12%";
	
       td0.innerHTML = '<input type="checkbox" name="subCheck" id="subCheck' + lgh + '"  style="width:100%;border:none;">'+
       				   '<input type="hidden" name="lineId" id="lineId' + lgh + '" value="">';
       td1.innerHTML = '<select class="selectNoEmpty" name="objectCategory" id="objectCategory' + lgh + '" style="width: 100%" onChange="do_ObjectCategory(this);"><%=etsObjectDTO.getObjCategoryOption() %></select>';
       td2.innerHTML = '<input type="text" name="btsNo" id="btsNo' + lgh + '" class="finputNoEmpty" value="">';
       td3.innerHTML = '<select class="selectNoEmpty" id="areaType' + lgh + '" name="areaType" style="width: 100%"><%=etsObjectDTO.getAreaTypeOption()%></select>';
       td4.innerHTML = '<select class="selectNoEmpty" id="city' + lgh + '" name="city" style="width: 100%" onChange="do_ChangeCounty(this);do_CountyValueChange(this);"><%=etsObjectDTO.getCityOption() %></select>';
       td5.innerHTML = '<select class="selectNoEmpty" id="county' + lgh + '" name="county" style="width: 100%"></select>';
       td6.innerHTML = '<input type="text" name="workorderObjectName" id="workorderObjectName' + lgh + '" class="finputNoEmpty" value="">';
       td7.innerHTML = '<input type="text" name="workorderObjectCode" id="workorderObjectCode' + lgh + '" class="finput" readonly value="">';
       td8.innerHTML = '<select class="selectNoEmpty" id="shareType' + lgh + '" name="shareType" style="width: 100%"><option value="1" selected>仅上市公司使用</option><option value="2">上市和TD共用</option><option value="3">仅TD使用</option></select>';
       td9.innerHTML = '<input type="text" align="center" name="addrMaintainType" id="addrMaintainType' + lgh + '" class="finput" readonly value="新增"></td>';
       				//'<select class="select_style1" id="addrMaintainType' + lgh + '" name="addrMaintainType" style="width: 100%"><%=batchDTO.getAddrMaintainTypeOption()%></select>';        					
       td10.innerHTML = '<input type="text" name="errorMessage" id="errorMessage' + lgh + '" class="finput" readonly value="">' + 
       					'<input type="hidden" name="countyCode" id="countyCode' + lgh + '" class="finput" value="">';
       
       obj.appendChild(td0);
       obj.appendChild(td1);
       obj.appendChild(td2);
       obj.appendChild(td3);
       obj.appendChild(td4);
       obj.appendChild(td5);
       obj.appendChild(td6);
       obj.appendChild(td7);
       obj.appendChild(td8);
       obj.appendChild(td9);
       obj.appendChild(td10);
       lgh++;
//}        
}
   
function do_ChangeCounty(obj) {
    var actionURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet";
    var userParameters = "act=CHANGE_COUNTYS";
    userParameters += "&city=" + obj.value;
    var ajaxProcessor = new AjaxProcessor(actionURL, do_ShowCountyOption, false);
    ajaxProcessor.setSendData(encodeURI(encodeURI(userParameters)));
    ajaxProcessor.performTask();
}

function do_ShowCountyOption(resText){
   	mainFrm.countyCache.value = resText;
}

function do_CountyValueChange(obj) {
	var objId = obj.id;
	var idNum = objId.substring(obj.name.length);
	var countyId = "county" + idNum ;
	document.getElementById(countyId).outerHTML = "<select class=\"selectNoEmpty\" id=" + countyId + " style=\"width:100%\" name=\"county\" onChange=\"do_SetObjectName("+idNum+");\">" + mainFrm.countyCache.value + "</select>";
}

function do_ObjectCategory(obj) {
	var objId = obj.id;
	var idNum = objId.substring(obj.name.length);
	if (obj.value == "JZ" || obj.value == "YY") {
		document.getElementById("btsNo"+idNum).style.display = "block";
	} else {
		document.getElementById("btsNo"+idNum).style.display = "none";
	}
}

//设置地点描述前段
function do_SetObjectName(idNum) {
	var cityObj = document.getElementById("city" + idNum);
   	var city = cityObj.options[cityObj.selectedIndex].text;
   	var countyObj = document.getElementById("county" + idNum);
   	var county = countyObj.options[countyObj.selectedIndex].text; 		    	
	document.getElementById("workorderObjectName"+idNum).value = city+county;
}

function do_Validate() {
	var excel = mainFrm.excel.value;
	if (excel == "2") {
		var objectCategorys = document.getElementsByName("objectCategory");
		var btsNos = document.getElementsByName("btsNo");
		var citys = document.getElementsByName("city");
		var countys = document.getElementsByName("county");
		var workorderObjectNames = document.getElementsByName("workorderObjectName");
		var fieldNames = "objectCategory;areaType;city;county;workorderObjectName";
	    var	fieldLabels = "地点专业;行政区域;地市;区县;物理地点描述";
	    
		if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
            document.mainFrm.errorMsgCache.value = "";
			for (var i = 0; i < objectCategorys.length; i++) {
				var objectStartName = citys[i].value + countys[i].value;
				if (objectCategorys[i].value == "JZ" || objectCategorys[i].value == "YY") {
	    			if (btsNos[i].value.trim() == "") {
	    				alert("输入非法，原因是：“基站或营业厅编号”必须填写，不能为空！");
	    				btsNos[i].focus();
	    				return false;
	    			} else {
	    				do_ValidateBtsNo(btsNos[i].value.trim());
	    				btsNos[i].focus();
	    			}    			
	    		} 
				if (workorderObjectNames[i].value.indexOf(".") >=0 ) {
		    		alert("物理地点描述不规范请检查");
		    		workorderObjectNames[i].focus();
		    		return false;
		    	} else if (workorderObjectNames[i].value.indexOf(objectStartName) == 0) {
		    		do_ValidateWorkorderObjectName(workorderObjectNames[i].value);
		    		workorderObjectNames[i].focus();
		    	} else {
		    		alert("物理地点描述“" + workorderObjectNames[i].value + "”与地市“" + citys[i].value + "”区县“" + countys[i].value + "”名称不一致");
					return false;
		    	}
			    for (var j = 0; j < objectCategorys.length;j++) {
		    		if (j==i) {
		    			continue;
		    		}
		    		if (workorderObjectNames[i].value == workorderObjectNames[j].value) {
		    			alert("物理地点描述“"+workorderObjectNames[i].value+"”与第"+(j+1)+"行物理地点描述重复!");
		    			return false;
		    		}
		    		if (objectCategorys[i].value == "JZ" || objectCategorys[i].value == "YY") {
			    		if (btsNos[i].value == btsNos[j].value) {
			    			alert("基站或营业厅编号“"+btsNos[i].value+"”与第"+(j+1)+"行编号重复!");
			    			return false;
			    		}
		    		}
		    	}
		    	var errorMsg = mainFrm.errorMsgCache.value;
				if (errorMsg == "BH") {
					alert("基站或营业厅编号已存在！");
					return false;
				} else if (errorMsg == "DDCZ") {
					alert("物理地点描述已存在！");	
					return false;
				} else if (errorMsg == "DD") {
					alert("物理地点描述正在审批中！");
					return false;
				}					
			}
		}		
	}
}

//验证名称
function do_ValidateWorkorderObjectName(workorderObjectName) {
	if( workorderObjectName != ""){
        var actionURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet";
        var userParameters = "act=VALIDATE_OBJECT_NAME";
        userParameters += "&workorderObjectName=" + workorderObjectName;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseObjectName, false);
        ajaxProcessor.setSendData(encodeURI(encodeURI(userParameters)));
        ajaxProcessor.setSynchronize(false);
        ajaxProcessor.performTask();
	}
}

function do_ResponseObjectName(resText) {
	if(resText == "Y"){
		mainFrm.errorMsgCache.value = "DDCZ";
    } else if (resText == "N"){
    	mainFrm.errorMsgCache.value = "DD";
    }
}

function do_ValidateBtsNo(btsNo) {
	if (btsNo != "") {
	    var actionURL = "/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet";
	    var userParameters = "act=VALIDATE_BTSNO";
	    userParameters += "&btsNo=" + btsNo;
	    var ajaxProcessor = new AjaxProcessor(actionURL, do_ResponseBtsNo, false);
	    ajaxProcessor.setSendData(userParameters);
        ajaxProcessor.setSynchronize(false);
	    ajaxProcessor.performTask();
    }
}

function do_ResponseBtsNo(resText) {
	if(resText == "Y"){
		mainFrm.errorMsgCache.value = "BH";
    }
}
</script>
