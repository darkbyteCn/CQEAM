<%@ page import="com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant" %>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.AssetSharingHeaderDTO"%>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsURLList" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AssetSharingHeaderDTO headerDTO = (AssetSharingHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
%>
<head>
	<title>资产共享申请</title>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util2.js"></script>
    <script type="text/javascript" src="/WebLibary/js/api.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
</head>
<script type="text/javascript">

    printToolBar();
</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<form action="<%=AssetSharingConstant.ASSET_SHARE_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<%@ include file="/flow/flowPara.jsp" %>
<div id="searchDiv" style="position:absolute;top:29px;left:1px;width:100%">
    <table width=100% border="0">
        <tr style="height:23px">
            <td align=right width=8%>单据号：</td>
            <td width=17%>
                <input type="text" name="transNo" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getTransNo()%>">
            </td>
            <td align=right width=8%>单据类型：</td>
            <td width=17%>
                <input type="text" name="transTypeValue" class="input_style2" readonly style="width:100%" value="<%=AssetSharingConstant.ASSET_SHARE_CODE_DESC%>">
            </td>
             <td align=right width=8%>公司名称：</td>
            <td width=17%>
            <input name="company" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCompany()%>">
            </td>
            <td align=right width=8%>部门名称</td>
            <td width=17%>
                <input type=hidden name="fromDept" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getFromDept()%>">
                <input name="deptName" style="width:100%" class="input_style2" onclick="do_SelectCreateGroup();" value="<%=headerDTO.getDeptName()%>"/>
            </td>
        </tr>
        <tr style="height:23px">
            <td align=right width=8%>创建人：</td>
            <td width=13%>
                <input type="text" name="currUserName" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getCurrUserName()%>" >
            </td>
            <td align=right>创建日期：</td>
            <td>
                <input name="creationDate" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>" ></td>

            <td align=right width=8%>手机号码：</td>
             <td>
                <input name="mobilPhone" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getMobilePhone()%>"></td>
            <td align=right width=8%>电子邮件：</td>
             <td>
                <input name="email" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getEmail()%>"></td>
        </tr>
        <tr >
            <td align=right>紧急程度：</td>
            <td >
                <select name="emergentLevel" class="select_style1" style="width:100%"  onchange="document.getElementById('sf_priority').value = this.value;"><%=headerDTO.getEmergentLevelOption()%></select>
            </td>
	        <td align=right >实物管理部门<font color="red">*</font></td>
	        <td>
		        <select name="specialityDept" style="width:100%" class="selectNoEmpty" onmouseover="do_ProcessOptionWidth(this)" ><%=headerDTO.getSpecialityDeptOption()%></select>
	        </td>
            <td width="8%" align="right" valign="top">备注：</td>
            <td width="92%" height="40" colspan="3"><textarea name="remark" id=remark style="width:100%;height:100%"><%=headerDTO.getRemark()%></textarea></td>
        </tr>
    </table>
</div>
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getGroupId()%>">
<input type="hidden" name="fromGroupName" value="">
<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="excel" value="">
<input type="hidden" name="groupId" value="<%=headerDTO.getGroupId()%>">
<input type="hidden" name="flowCode" value="<%=headerDTO.getFlowCode()%>">
    <div id="buttonDiv" style="position:absolute;top:170px;left:1px;width:100%">
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
        <span id="warn"></span>
        <%--<img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="do_Close(); return false;">--%>
        统一设置：
        <input type="checkbox" name="setAllStatus" id="setAllStatus"><label for="setAllStatus">资产共享</label>
    </div>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:hidden;position:absolute;top:195px;left:1px;width:100%">
		<table class="headerTable" border="1" width="140%">
	        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="8%">标签号</td>
                <td align=center width="6%">资产名称</td>
	            <td align=center width="6%">资产型号</td>
	            <td align=center width="8%">资产地点描述</td>
                <td align=center width="6%">共享</td>
                <td align=center width="6%">责任人员工编号</td>
                <td align=center width="6%">责任人姓名</td>
                <td align=center width="6%">启用日期</td>
				 <td align=center width="6%">资产目录</td>
	            <td align=center width="8%">资产目录描述</td>
				<td style="display:none">隐藏域字段</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:223;left:1px"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
          List<AssetSharingLineDTO> lines = headerDTO.getLines();
          if(lines == null || lines.isEmpty()){
%>
            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="资产详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				<td width="8%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
				<td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName0" class="finput" readonly value=""></td>
				<td width="6%" align="center" title="资产详细信息" style="cursor:pointer" ><input name="itemSpec" id="itemSpec0" style="width:100%" class="finput"/></td>
				<td width="8%" align="left" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation0" class="finput" readonly value=""></td>
				
				<td width="6%" align="right" title="资产详细信息" style="cursor:pointer" ><select style="width:100%"name="shareStatus" id="shareStatus0" class="finputNoEmpty" onchange=do_SetCheckCategory(this);><%=headerDTO.getShareStatusOpt()%></select></td>
				<td width="6%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="employeeNumber" id="employeeNumber0" class="finput2" readonly value=""></td>
                <td width="6%" align="right"><input type="text" name="userName" class="finput" id="userName0" value=""></td>
                <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="startDate" id="startDate0" class="finput2" readonly value=""></td>
                <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="contentCode" id="contentCode0" class="finput" readonly value=""></td>
				
				<td width="8%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="contentName" id="contentName0" class="finput" readonly value=""></td>
				<td style="display:none">
					<input type="hidden" name="systemid" id="systemid0" value="">
					<input type="hidden" name="lineId" id="lineId0" value="">
					<input type="hidden" name="adressId" id="adressId0" value="">
				</td>
            </tr>
<%
          } else {
          	for(int i=0;i<lines.size();i++){
          		AssetSharingLineDTO line=lines.get(i);
%> 
            <tr class="dataTR" onClick="executeClick(this)"title="资产详细信息">
          		<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value=""></td>
				<td width="8%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=line.getBarcode()%>"></td>
				<td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName<%=i%>" class="finput" readonly value="<%=line.getItemName()%>"></td>
				<td width="6%" align="center" title="资产详细信息" style="cursor:pointer" ><input name="itemSpec" id="itemSpec<%=i%>" class="finput" value="<%=line.getItemSpec() %>"/></td>
				<td width="8%" align="left" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" class="finput" readonly value="<%=line.getWorkorderObjectLocation()%>"></td>
				
				<td width="6%" align="right" title="资产详细信息" style="cursor:pointer" ><select name="shareStatus" style="width:100%" id="shareStatus<%=i%>" class="finputNoEmpty" onchange=do_SetCheckCategory(this);><%=line.getShareStatusOpt()%></select></td>
				<td width="6%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="employeeNumber" id="employeeNumber<%=i%>" class="finput2" readonly value="<%=line.getEmployeeNumber()%>"></td>
                <td width="6%" align="right"><input type="text" name="userName" id="userName<%=i%>" class="finput" value="<%=line.getUserName()%>"></td>
                <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="startDate" id="startDate<%=i%>" class="finput2" readonly value="<%=line.getStartDate()%>"></td>
                <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="contentCode" id="contentCode<%=i%>" class="finput" readonly value="<%=line.getContentCode()%>"></td>
				
				<td width="8%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="contentName" id="contentName<%=i%>" class="finput" readonly value="<%=line.getContentName()%>"></td>
				<td style="display:none">
					<input type="hidden" name="systemId" id="systemId<%=i%>" value="<%=line.getSystemId()%>">
					<input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=line.getLineId()%>">
					<input type="hidden" name="adressId" id="adressId<%=i%>" value="<%=line.getAdressId()%>">
				</td>
          	</tr>
<%
        }
    }
%>
        </table>
    </div>

</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function do_CancelApply() {
	if(confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")){
		mainFrm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
		mainFrm.submit();
	}
}
function do_AppValidate() {
    return validateLine();
} 

function validateLine(){
	var isValid = true;
	var rows = dataTable.rows;
    var rowCount = rows.length;
    
	if ( rowCount == 0 || (dataTable.rows[0].style.display == 'none' && rowCount == 1)) {
        alert("没有选择行数据，请选择行数据！");
        isValid = false;
    }
    if( isValid ){
    	isValid = check_form_0();
    }
	return isValid;
}    
/**
  * 功能：选择资产
 */
function do_SelectAssets() {
	var dataTable = document.getElementById("dataTable");  
	var dialogWidth = 52;
	var dialogHeight = 29;
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_ASSETS_SHARE%>";
	var specialityDept = mainFrm.specialityDept.value;
	if (mainFrm.specialityDept.value == "") {
		alert("请选择实物管理部门，然后才能选择资产");
		return;
	}
	var userPara ="responsibilityDept="+document.mainFrm.fromDept.value;// "transType=" + mainFrm.transType.value;
	userPara += "&specialityDept="+specialityDept;
	//userPara += "&faContentCode=" + mainFrm.faContentCode.value;
	var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (assets) {
		var data = null;
		for (var i = 0; i < assets.length; i++) {
			data = assets[i];
			appendDTO2Table(dataTable, data, false, "barcode");
		}
	}
}        

function do_ShowDetail(obj){
	var tr = obj.parentNode;
	var cells = tr.cells;
	var cell = cells[1];
	var barcode = cell.childNodes[0].value;
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=900,height=480,left=70,top=100";
	window.open(url, winName, style);
}


 function initPage() {
    window.focus();
	do_SetPageWidth();
    doLoad();
    if(document.mainFrm.fromDept.value=='null'||document.mainFrm.fromDept.value=="0"||document.mainFrm.fromDept.value==""){

	    document.mainFrm.fromGroup.value=document.getElementById("flow_group_id").value;
	    //document.mainFrm.fromGroupName.value=document.getElementById("flow_group_name").value;
	    document.mainFrm.fromDept.value=document.getElementById("app_dept_code").value;
	    document.mainFrm.deptName.value=document.getElementById("app_dept_name").value;
    }
    
    do_ControlProcedureBtn();
}


function do_SelectCreateGroup(){
	
	var sf_Group = document.getElementById('sf_group').value;
	var fromGroup = mainFrm.fromGroup.value;
	var sf_project = document.getElementById('sf_project').value;
	var sf_role = document.getElementById('sf_role').value;
	var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>?fromGroup=" + fromGroup+"&sf_group="+sf_Group+"&sf_project="+sf_project+"&sf_role="+sf_role;
	var popscript = "dialogWidth:20;dialogHeight:10;center:yes;status:no;scrollbars:no;help:no";
	var group = window.showModalDialog(url, null, popscript);
	if(group){
		dto2Frm(group, "mainFrm");
		document.mainFrm.fromGroup.value=group.fromGroup;
		document.mainFrm.fromGroupName.value=group.fromGroupName;
		document.mainFrm.fromDept.value=group.deptCode;
		document.mainFrm.fromDeptName.value=group.deptName;
		//alert(group.fromDeptName);
        //do_isProfessionalGroup();
	}
}

/**
function  do_Div_Complete_Start_beforeSubmit(){
	if(check_form_0()){
		do_Div_Complete_Start();
	}
}
**/
	function check_form_0(){
		var isSubmit=true;
		var mess_age="";
		var remark=document.getElementById('remark');
		if(remark==''){
			isSubmit=false;
			mess_age="备注不能为空!";
		}
		var tab=document.getElementById('dataTable');
		if(tab){
			var rows=tab.rows;
			var flag=true;
			var leng=0;
			for(i=0;i<rows.length;i++){
					leng=i+1;
					if(rows[i].children[1].children[0].value!=''&&rows[i].children[5].children[0].value==''){///共享
						flag=false;
						rows[i].children[5].children[0].style.color='red';
						mess_age="请选择共享类型";
					}else{
						rows[i].children[5].children[0].style.color='green';
					}
			}
			
			if(leng=1&rows[0].children[1].children[0].value==''){
				//flag=false;
				mess_age="请至少选择一行数据!";
			}
		//alert(flag);
			isSubmit=flag;
			//alert(mess_age);
		}
		if(!isSubmit){
			alert(mess_age);
		}
		return isSubmit;
	}
	function select_change(obj){
		var setAllStatus=document.getElementById('setAllStatus');
		if(setAllStatus.checked){
			var subChecks=document.getElementsByName('subCheck');
			var shareStatus=document.getElementsByName('shareStatus');
			if(subChecks!=null&&subChecks.length>0){
				for(i=0;i<subChecks.length;i++){
					if(subChecks[i].checked){
						//shareStatus[i]
					}
				
				}
			}
		}
	}
	
	function do_SetCheckCategory(lineBox){
	if(!mainFrm.setAllStatus.checked){
		return;
	}
	var objs = document.getElementsByName("shareStatus");
	var dataCount = objs.length;
	var selectedVal = lineBox.value;
	if(objs && objs.length){		
	
		var chkObj = null;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < dataCount; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			chkObj = objs[i];
			selectSpecialOptionByItem(chkObj, selectedVal);
		}
	}
}   

function deleteLine() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
//    if(deleteTableRow(dataTable, 'subCheck')){
//		do_SaveOrder();
//	}
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
</script>