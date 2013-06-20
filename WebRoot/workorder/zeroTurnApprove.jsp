<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnHeaderDTO"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnLineDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<%
	ZeroTurnHeaderDTO headerDTO = (ZeroTurnHeaderDTO) request.getAttribute(AssetsWebAttributes.ZERO_TURN_DATA);
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    String transId=headerDTO.getTransId();
    String transStatus=headerDTO.getTransStatus();
%>
<head>
	<title>零购转资维护</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/DefaultLocationProcess.js"></script>
</head>
<script type="text/javascript">
    printToolBar();
</script>
<script type="text/javascript" src="/WebLibary/js/jquery-1.2.6.js"></script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('3.M.1')" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<input type="hidden" name="helpId" value="">
<form action="/servlet/com.sino.ams.workorder.servlet.ZeroTurnServlet" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:29px;left:1px;width:100%">

<table border="0" width="100%" style="border-collapse: collapse;top:0px;" id="table1">
	<tr>
		<td>
			<table width=100% border="0" align="center">
			    <tr align="cneter">
			        <td align=right width="8%" height="18">交接工单：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="transNo" class="input_style2" readonly style="width:100%" 	value="<%=headerDTO.getTransNo() %>" size="20"></td>
			        <td align=right width="6%" height="18">创建日期：</td>
                    <td width="15%" height="18">
			         <input type="text" name="creationDate" class="input_style2" readonly style="width:100%;cursor:pointer" value="<%=headerDTO.getCreationDate()%>"  size="20"></td>
			        <td align=right width="6%" height="18">创建人：</td>
			        <td width="15%" height="18">
					   <input type="text" name="createdByName" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getCreatedByName() %>">
					</td>
					<%  if(transStatus.equals("PRE_ASSETS")){ %>
						<td align=right width="10%" height="18">执行周期(天数)：</td>
				        <td width="15%" height="18">
						   <input type="text" name="computeTims" class="finputNoEmpty"  style="width:100%" value="<%=headerDTO.getComputeTims() %>">
						   <input type="text" name="groupName"  class="input_style2" readonly style="width:100%;cursor:pointer;display:none;"  value="<%=headerDTO.getDeptName()%>"  onClick="do_SelectAppGroup()" size="20"></td>
						</td>
					<%}else { %>
						<td align=right width="10%" height="18">执行周期(天数)：</td>
				        <td width="15%" height="18">
						   <input type="text" name="computeTims" class="input_style2" readonly  style="width:100%" value="<%=headerDTO.getComputeTims() %>">
						   <input type="text" name="groupName"  class="input_style2" readonly style="width:100%;cursor:pointer;display:none;"  value="<%=headerDTO.getDeptName()%>"  onClick="do_SelectAppGroup()" size="20"></td>
						</td>
				    <%  } %>
					
			    </tr>
			    <tr>
			    <%  if(transStatus.equals("PRE_ASSETS")){ %>
			        <td align=right width="6%" height="18">工单执行人：</td>
			        <td width="15%" height="18">
			             <input type="text" name="orderExecuterName"  readonly class="finputNoEmpty" style="width:100%;cursor:pointer" value="<%=headerDTO.getOrderExecuterName()%>" title="点击选择或更改“工单执行人”" onClick="do_ChooseExcuter()" size="20"></td>
			        <td align=right width="6%" height="18">工单归档人：</td>
			        <td width="15%" height="18">
			        	  <input type="text"  name="orderFilerName" class="finputNoEmpty" readonly  style="width:100%;cursor:pointer" value="<%=headerDTO.getOrderFilerName() %>" title="点击选择或更改“工单归档人”" onClick="do_ChooseArcUser()" size="20"></td>
			       
			     <%}else { %>
			        <td align=right width="6%" height="18">工单执行人：</td>
			        <td width="15%" height="18">
			             <input type="text" name="orderExecuterName"  readonly class="input_style2" style="width:100%" value="<%=headerDTO.getOrderExecuterName()%>"  size="20"></td>
			        <td align=right width="6%" height="18">工单归档人：</td>
			        <td width="15%" height="18">
			        	  <input type="text"  name="orderFilerName" class="input_style2" readonly  style="width:100%" value="<%=headerDTO.getOrderFilerName() %>"  size="20"></td>
			       
			     <%  } %>
			     
			       <!--  <td align=right width="6%" height="18">采购单号：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="orderNo" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getOrderNo() %>" size="20"></td>
			        -->
			        <td align=right width="6%" height="18">单据状态：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="statusType" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getStatusType()%>" size="20"></td>
			        	<input type="hidden" name="transStatus" value="<%=headerDTO.getTransStatus() %>" />
			    </tr>
			    <tr>
                    <td align=right width="10%" height="40">备注：</td>
			        <td width="40%" height="40" colspan="3">
				        <textarea name="remark1" style="width:100%; height:100%"><%=headerDTO.getRemark1()%></textarea>
					</td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
</div>
<input type="hidden" name="groupId" value="<%=headerDTO.getGroupId()%>">
<input type="hidden" name="groupName" value="<%=headerDTO.getGroupName()%>">
<input type="hidden" name="deptCode" value="<%=headerDTO.getDeptCode()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="trStatus" value="<%=transStatus%>">
<input type="hidden" name="isFilter" >
<input type="hidden" name="excel" value="">
<input type="hidden" name="excelPath"  value="" >
<input type="hidden" name="login"  value="" >
<input type="hidden" name="transType"  value="<%=headerDTO.getTransType()%>" >
<input type="hidden" name="orderExecuter"  value="<%=headerDTO.getOrderExecuter()%>" >
<input type="hidden" name="orderFiler"  value="<%=headerDTO.getOrderFiler()%>" >
<input type="hidden" name="ccenter"  value="<%=headerDTO.getCcenter()%>" >
<input type="hidden" name="copye"  value="<%=headerDTO.getCopye()%>" >

  <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%;">
    <table class=headerTable border=1 style="width:150%">
        <tr height="22px" >
            <td align=center width="7%">标签号</td>
            <td align=center width="7%">采购单号</td>
            <td align=center width="7%">预计到货日期</td>
            <td align=center width="7%">资产名称</td>
            <td align=center width="6%">规格型号</td>
            <td align=center width="6%">数量</td>
            <td align=center width="6%">数量单位</td>
            <td align=center width="6%">金额</td>
            <td align=center width="6%">资产目录</td>
            <td align=center width="6%">资产目录名称</td>
            <td align=center width="6%">地点编号</td>
            <td align=center width="6%">地点名称</td>
            <td align=center width="6%">厂商</td>
            <td align=center width="6%">备注</td>
            <td align=center width="6%">责任部门</td>
            <td align=center width="6%">责任人</td>
        </tr>
    </table>
</div>

 <div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:1px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
     <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
    <%
		if (lineSet == null || lineSet.isEmpty()) {
     %>
             <tr class="dataTR" style="display:none">
		        <td width="7%">
	            <td width="7%">
	            <td width="7%">
	            <td width="7%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
	            <td width="6%">
            </tr>
<%
 	  } else {
 		 ZeroTurnLineDTO lineDTO = null;
		  for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (ZeroTurnLineDTO) lineSet.getDTO(i);
%>
            <tr class="dataTR" style="cursor:pointer" >
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="record" id="record<%=i%>" class="finput" readonly value="<%=lineDTO.getRecord()%>"></td>
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="procureCode" id="procureCode<%=i%>" class="finput" readonly value="<%=lineDTO.getProcureCode()%>"></td>
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="expectedDate" id="expectedDate<%=i%>" class="finput" readonly value="<%=lineDTO.getExpectedDate()%>"></td>
				<td width="7%" align="center" style="cursor:pointer" >
				  <input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput" readonly value="<%=lineDTO.getItemSpec()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="itemQty" id="itemQty<%=i%>" class="finput" readonly value="<%=lineDTO.getItemQty()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="price" id="price<%=i%>" class="finput" readonly value="<%=lineDTO.getPrice()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="contentCode" id="contentCode<%=i%>" class="finput" readonly value="<%=lineDTO.getContentCode()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="contentName" id="contentName<%=i%>" class="finput" readonly value="<%=lineDTO.getContentName()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="objectNo" id="objectNo<%=i%>" class="finput" readonly value="<%=lineDTO.getObjectNo()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="workorderObjectName" id="workorderObjectName<%=i%>" class="finput" readonly value="<%=lineDTO.getWorkorderObjectName()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="manufacturerName" id="manufacturerName<%=i%>" class="finput" readonly value="<%=lineDTO.getManufacturerName()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="responsibilityDept" id="responsibilityDept<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityDept()%>"></td>
				<td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="responsibilityUser" id="responsibilityUser<%=i%>" class="finput"  value="<%=lineDTO.getResponsibilityUser()%>"></td>
				  <input type="hidden" name="costCenterCode" value="<%=lineDTO.getCostCenterCode()%>">
            </tr>
            <%
		  	    }
 	 		 }
            %>

     </table>
    </div>
	
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
<div id="hiddenDiv" style="position: absolute; overflow:scroll;width:550px;height:180px; display:none;background-color:#C6EBF4;color:red"></div>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">

var dataTable = document.getElementById("dataTable");

function initPage() {
    window.focus();
    do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();
    if (mainFrm.groupId.value == "0") {
        mainFrm.groupName.value = document.getElementById("flow_group_id").value;
        mainFrm.groupName.value = sf_group;
    }
    
    var appDeptCode = document.getElementById("app_dept_code").value;
    var appDeptName = document.getElementById("app_dept_name").value;
    if (appDeptCode) {
		document.getElementById("groupId").value = document.getElementById("app_dept_code").value;
    }
    if (appDeptName) {
    	document.getElementById("DeptName").value = document.getElementById("app_dept_name").value;
    }
    
}
//提交
function do_AppValidate() {
	var isValid = true;
     if(!ajaxValid()){
         return false;
     }else if(!do_nomalV()) {
    	 return false;
     }
    return isValid;
}

function do_Save_app () {
	var isValid = true;
    if(!do_AppValidate()){
        return false;
    }
   return isValid;
}

function do_nomalV(){
	var isValid = true;
	var computeDays=mainFrm.computeTims.value;
	var orderExecuter=mainFrm.orderExecuter.value;
	var orderFiler=mainFrm.orderFiler.value;
	if(computeDays== ""){
		isValid=false;
		alert("执行周期必须输入");
	}
	else if(orderExecuter==""){
		isValid=false;
		alert("工单执行人必须输入");
    }
    else if(orderFiler==""){
		isValid=false;
		alert("工单归档人必须输入");
    }else {
        
    }
    return isValid;
}

function ajaxValid(){
	var isValid = true;
	var trStatus=mainFrm.trStatus.value;
    if(trStatus=="ALR_ISSUED"){
		//$.ajax({
		  //  url: '/servlet/com.sino.ams.workorder.servlet.ZeroTurnServlet?act=VALIDATE_ACTION&transId='+mainFrm.transId.value,
		  //  type: 'POST',
		  //  success: function (data)
		   // {
			//	if(data=="1"){
			//		isValid=false;
			//		alert("尚有工单未归档!");
			//	}
		   // },
			//error:function (){
		   // 	alert("ajax 出错了！");
			//}
		//});
    	do_isFilter();
    	var isF=mainFrm.isFilter.value;
    	if(isF=="1"){
    		isValid=false;
    		alert("尚有工单未归档!");
        }
    }
    return isValid;
}


function do_isFilter() {
	var trStatus=mainFrm.trStatus.value;
	var url="/servlet/com.sino.ams.workorder.servlet.ZeroTurnServlet?act=VALIDATE_ACTION&transId="+mainFrm.transId.value;
    var ajaxProcessor = new AjaxProcessor(url, do_ProcessResponse, false);
    ajaxProcessor.performTask();
}

function do_ProcessResponse(responseContent){
    mainFrm.isFilter.value = responseContent;
}


function do_ChooseExcuter() {
	var lookUpName = "LOOK_UP_ZERO_USER";
	var userPara="groupId=" + document.mainFrm.groupId.value + "&record=" +  document.getElementsByName("record")[0].value;
	var dialogWidth = 50;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.orderExecuter.value = obj["userId"];
        mainFrm.orderExecuterName.value = obj["userName"];
	    } else {
	    	mainFrm.orderExecuter.value = "";
	    	mainFrm.orderExecuterName.value ="";
	    }
}

function do_ChooseArcUser() {
	var lookUpName = "LOOK_UP_USER_ZERO_ACHIEVE";
	var userPara="groupId=" + document.mainFrm.groupId.value + "&record=" +  document.getElementsByName("record")[0].value;
	var dialogWidth = 50;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.orderFiler.value = obj["userId"];
        mainFrm.orderFilerName.value = obj["userName"];
        mainFrm.login.value=obj["loginName"];
	} else {
	    	mainFrm.orderFiler.value = "";
	    	mainFrm.orderFilerName.value ="";
	}
}

</script>