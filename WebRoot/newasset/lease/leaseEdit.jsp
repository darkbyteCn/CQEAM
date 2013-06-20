<%@page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@page import="com.sino.ams.newasset.lease.constant.LeaseAppConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.lease.constant.LeaseURLListConstant" %>
<%@ page import="com.sino.ams.newasset.lease.dto.LeaseDTO"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseHeaderDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<%  
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	
	LeaseHeaderDTO headerDTO = null;
	LeaseDTO leaseDTO = null;
	leaseDTO = (LeaseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = leaseDTO.getHeaderDTO();
    DTOSet lines = leaseDTO.getLines(); 
    String transId = headerDTO.getTransId();
    
%>
<head>
	<title>资产续租申请</title>
	 
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<%@ include file="/flow/flowNoButton.jsp" %>
<form action="<%= LeaseURLListConstant.LEASE_SERVELT %>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
 
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId()%>">
<input type="hidden" name="act" value="">  
<jsp:include page="/flow/include.jsp" flush="true"/>
 <%@ include file="/flow/flowPara.jsp"%>
  
<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<jsp:include page="/newasset/lease/leaseHeader.jsp"></jsp:include> 
		</td>
	</tr>
</table> 
<div id="searchDiv" style="width:100%">
    	<%
        String att3 = info.getSfactTaskAttribute3();
        if( att3.equals( LeaseAppConstant.ATT3_FILL_DATA ) ){
        %>
        <img id="selectBtn" src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img id="delBtn" src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
        <% 
        }
        %>
        <% 
        if( att3.equals( LeaseAppConstant.ATT3_FILL_DATA ) ){
        %>
         统一设置：
        <input type="checkbox" name="allRentDate" id="allRentDate"><label for="allRentDate">起租日期</label>
        <input type="checkbox" name="allRentEndDate" id="allRentEndDate"><label for="allRentEndDate">止租日期</label>
        <input type="checkbox" name="allTenancy" id="allTenancy"><label for="allTenancy">租期</label> 
        <% 
        }
        %>
 </div>
	<jsp:include page="/newasset/lease/leaseLine.jsp"/>

</form>
 
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function initPage() {
    window.focus();
    doLoad();
	do_SetPageWidth(); 
	var att3 = document.getElementById("sf_task_attribute3").value;
	if( att3 != "<%=LeaseAppConstant.ATT3_FILL_DATA%>" ){
		setFrmReadonly("mainFrm");
	}
    do_ControlProcedureBtn();
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
 

function deleteLine() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
} 

/**
  * 功能：选择资产
 */
function do_SelectAssets() {
	try{ 
		var dataTable = document.getElementById("dataTable");
		
		var dialogWidth = 52;
		var dialogHeight = 29; 
		<%
	    	String lun = AssetsLookUpConstant.LOOK_ASSETS_LEASE ; 
	    %>
		var lookUpName = "<%=lun%>";
		var userPara = "faContentCode=RENT_ASSETS" ;
		 
		var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (assets) {
			var data = null;
			for (var i = 0; i < assets.length; i++) {
				data = assets[i];
				appendDTO2Table(dataTable, data, false, "barcode");
			}
		}
	}catch(ex){
		alert( ex.message );
	}
} 

//行数据验证 - sj add 
function validateLine(){
	var isValid = true;
	var rows = dataTable.rows;
    var rowCount = rows.length;
    
	if ( rowCount == 0 || (dataTable.rows[0].style.display == 'none' && rowCount == 1)) {
        alert("没有选择行数据，请选择行数据！");
        isValid = false;
    }else{  
	    var rentDate = null;
	    var rentEndDate= null;
	    var rentPerson  = null;
	    var contractNumber = null;
	    var contractName = null;
	    var tenancy = null;
	    var yearRental = null;
	    var monthRental = null;
	    
	    for (var i =0; i<rowCount; i++) {
	        rentDate = rows[i].cells[6].childNodes[0].value;
	        rentEndDate = rows[i].cells[7].childNodes[0].value;
	        rentPerson = rows[i].cells[8].childNodes[0].value;
	        contractNumber = rows[i].cells[9].childNodes[0].value;
	        contractName = rows[i].cells[10].childNodes[0].value;
	        
	        tenancy = rows[i].cells[11].childNodes[0].value;
	        yearRental = rows[i].cells[12].childNodes[0].value;
	        monthRental = rows[i].cells[13].childNodes[0].value;
	        if( rentDate == "" ){
	        	alert("起租日期不能为空!");
	        	isValid = false;
	            break;
	        } 
	        if( rentEndDate == "" ){
	        	alert("止租日期不能为空!");
	        	isValid = false;
	            break;
	        } 
	        if( rentPerson == "" ){
	        	alert("签约单位不能为空!");
	        	isValid = false;
	            break;
	        }
	        if( contractNumber == "" ){
	        	alert("合同编号不能为空!");
	        	isValid = false;
	            break;
	        }
	        if( contractName == "" ){
	        	alert("合同名称不能为空!");
	        	isValid = false;
	            break;
	        } 
	        if( rentPerson == "" ){
	        	alert("租期不能为空!");
	        	isValid = false;
	            break;
	        }
	        if( yearRental == "" ){
	        	alert("年租金不能为空!");
	        	isValid = false;
	            break;
	        }
	        if( monthRental == "" ){
	        	alert("月租金不能为空!");
	        	isValid = false;
	            break;
	        } 
	         
	        if( !isPositiveNumber( yearRental ) ){
	        	alert("年租金只能为数字而且 >= 0!");  
	        	rows[i].cells[12].childNodes[0].value = "";
	        	rows[i].cells[12].childNodes[0].select();
	        	isValid = false;
	            break;
	        } 
	        
	        if( !isPositiveNumber( monthRental ) ){
	        	alert("月租金只能为数字而且 >= 0!");  
	        	rows[i].cells[13].childNodes[0].value = "";
	        	rows[i].cells[13].childNodes[0].select();
	        	isValid = false;
	            break;
	        }  
	        
	        if( !isPositiveInteger( tenancy ) ){
	        	alert("租期只能为正整数!");  
	        	rows[i].cells[11].childNodes[0].value = "";
	        	rows[i].cells[11].childNodes[0].select();
	        	isValid = false;
	            break;
	        }    
	    }
    }
    
    return isValid;
}


/**
 *功能:设置表单内所有元素不可用
 *参数:表单名
 */
function setFrmReadonly(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
    	var obj = frmObj.elements[i];
    	var objType = obj.type;
        var fieldType = obj.type;
        obj.readOnly = true; 
        if( objType == "text" || objType == "password" || objType == "textarea" ){
        	obj.onclick = null;
        } 
        
        if( fieldType == "checkbox" ){
        	obj.disabled = true;
        }
    }
} 
 

//统一设置 - 租期
function do_setTenancy( lineBox ){
    try{
        if(!mainFrm.allTenancy){
            return;
        }
        if(!mainFrm.allTenancy.checked){
            return;
        }
        do_SetLineDate( lineBox, "tenancy" );
    }catch(ex){
        alert( ex.message );
    }
}

function getCalendarPostProcessor(){
    return new CalendarPostProcessor("do_SetRentDate");
}

function do_SetRentDate(lineBox){
    var name = lineBox.name;
    if(name.indexOf("rentDate") > -1){
        do_SetRentStartDate(lineBox);
    } else if(name.indexOf("rentEndDate") > -1){
        do_SetRentEndDate(lineBox);
    }
}


//统一设置 - 起租日期
function do_SetRentStartDate( lineBox ){
    try{
        if(!mainFrm.allRentDate){
            return;
        }
        if(!mainFrm.allRentDate.checked){
            return;
        }
        do_SetLineDate( lineBox, "rentDate" );
    }catch(ex){
        alert( ex.message );
    }
}

//统一设置 - 止租日期
function do_SetRentEndDate( lineBox ){
    try{
        if(!mainFrm.allRentEndDate){
            return;
        }
        if(!mainFrm.allRentEndDate.checked){
            return;
        }
        do_SetLineDate( lineBox, "rentEndDate" );
    } catch(ex){
        alert( ex.message );
    }
}

//统一设置 - 共用代码
function do_SetLineDate(lineBox , fieldName ){ 
	var id = lineBox.id;
	var lineDate = lineBox.value;
	
	var dateFields = document.getElementsByName( fieldName );
	  
	  
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
	   
	for(var i = 0; i < dateFields.length; i++){
		if(checkedCount > 0){
			row = rows[i];
			checkObj = row.childNodes[0].childNodes[0];
			if(!checkObj.checked){
				continue;
			}
		}
		if(dateFields[i].id == id){
			continue;
		}
		dateFields[i].value = lineDate;
	}
}

 function floatOnlyOnkeyDown(obj) {
//    style="ime-mode:disabled"
    var k = window.event.keyCode;
    if ((k == 46) || (k == 8) || (k == 189) || (k == 109) || (k == 190) || (k == 110) || (k >= 48 && k <= 57) || (k >= 96 && k <= 105) || (k >= 37 && k <= 40)) {
    } else if (k == 13 || k == 9) {
//        window.event.keyCode = 9;
    } else {
        window.event.returnValue = false;
    }
}
function floatOnlyOnKeyUp(obj) { 
    if(!isNumber(obj.value)){obj.value = ""}; 
}
       
function changeYearRent( obj ){
	var monthRent = formatNum( Number(obj.value) / 12 , 3 );
	var trObj = obj.parentElement.parentElement;
	trObj.childNodes[13].childNodes[0].value = monthRent;
}

function changeMonthRent( obj ){
	var yearRent = formatNum( Number(obj.value) * 12 , 3 );
	var trObj = obj.parentElement.parentElement;
	trObj.childNodes[12].childNodes[0].value = yearRent;
}
 

function do_Cancel_app(){
	var actObj = document.getElementById("act");
	actObj.value = "CANCEL_ACTION"; 
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	document.forms[0].submit(); 
}


function do_AppValidate() {
	var createdReason = mainFrm.createdReason.value;
    if( createdReason == "" ){
    	alert("请填写说明！");
    	return false;
    }
    return validateLine();
} 
</script>