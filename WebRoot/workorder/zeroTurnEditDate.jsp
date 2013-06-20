<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnLineDTO"%>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<html>
<head> 
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

    <%
    	RowSet rows = (RowSet) request.getAttribute("ROW_SET");
    	String organizationId = request.getAttribute("OID").toString();
    %>
<title>零购转资差异核对修改保存界面</title>
</head>

<body leftmargin="0" topmargin="0" >
<input type="hidden" name="helpId" value=""> 
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.ZeroTurnEditQueryServlet">
<script type="text/javascript">
      printTitleBar("零购转资差异核对保存界面");
</script>
<input type="hidden" name="act" value="">
<input type="hidden" name=organizationId value="<%=organizationId%>">
<img src="/images/eam_images/save.jpg"    onClick="do_Save();return false;">
 <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:50px;left:0px;width:100%;">
 		<%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                String barcode=row.getValue("BARCODE").toString().trim();
        %>
    <table class=headerTable  style="width:100%">
			<tr>
            	<td>标签号</td><td><input type="text" name="barcode" id="barcode<%=i%>"  class="finput" readonly value="<%=row.getValue("BARCODE")%>"></td>
            </tr>
            <tr>
            	<td>资产目录</td>
            	<td>
            	  <input type="text" name="contentCode" id="contentCode<%=i%>"  class="finput"  value="<%=row.getValue("CONTENT_CODE")%>"  onClick="chooseContentCode()" >
            	  <input type="hidden" name="SourceContentCode"  value="<%=row.getValue("CONTENT_CODE")%>" >
            	</td>
            </tr>
            <tr>
            	<td>规格型号</td>
            	<td>
            	  <input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput"   value="<%=row.getValue("ITEM_SPEC")%>">
            	   <input type="hidden" name="SourceItemSpec" value="<%=row.getValue("ITEM_SPEC")%>">
            	</td>
            </tr>
            <tr>
            	<td>数量</td>
            	<td>
            	  <input type="text" name="itemQty" id="itemQty<%=i%>"  class="finput"  value="<%=row.getValue("ITEM_QTY")%>">
            	   <input type="hidden" name="SourceItemQty" value="<%=row.getValue("ITEM_QTY")%>">
            	</td>
            </tr>
            <tr>
            	<td>数量单位</td>
            	<td>
            	   <input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>"  class="finput"  value="<%=row.getValue("UNIT_OF_MEASURE")%>">
            	    <input type="hidden" name="SourceUnitOfMeasure" value="<%=row.getValue("UNIT_OF_MEASURE")%>">
            	</td>
            </tr>
            <tr>
            	<td>地点名称 </td>
            	<td>
            	  <input type="text" name="workorderObjectName"  onclick="showLocation()" class="finput"  value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>">
            	   <input type="hidden" name="SourceWorkorderObjectName" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>">
            	</td>
            </tr>
            <tr>
            	<!-- <td>成本中心</td><td><input type="text" name="costCenterName" class="finput"  value="<%=row.getValue("COST_CENTER_NAME")%>"  onClick="chooseCostCenter()" /> </td> -->
            	<td>成本中心</td><td><input type="text" name="costCenterName" class="finput"  value="<%=row.getValue("COST_CENTER_NAME")%>"  readonly/> </td>
            </tr>
            <tr>
            	<td>采购单号</td><td><input type="text" name="procureCode" id="procureCode<%=i%>"  class="finput"  value="<%=row.getValue("PROCURE_CODE")%>" readonly></td>
            </tr>
            <tr>
            	<td>启用日期</td>
                <td>
                   <input type="text" name="startDate" id="startDate<%=i%>"  class="finput"  value="<%=row.getValue("START_DATE")%>">
                    <input type="hidden" name="SourceStartDate" value="<%=row.getValue("START_DATE")%>">
                </td>
            </tr>
            <tr>
            	<td>差异处理结果</td>
            	<td>
            	  <input type="text" name="diffResult" id="diffResult<%=i%>"  class="finput"  value="<%=row.getValue("DIFF_RESULT")%>">
            	   <input type="hidden" name="SourceDiffResult" value="<%=row.getValue("DIFF_RESULT")%>">
            	</td>
            </tr>
            <input type="hidden" name="specialityCode" value="<%=row.getValue("SPECIALITY_CODE")%>">
 			<input type="hidden" name="deptName" value="">
 			<input type="hidden" name="deptCode" value="">
 			<input type="hidden" name="responsibilityCode" value="<%=row.getValue("RESPONSIBILITY_CODE")%>">
 			<input type="hidden" name="objectNo" value="<%=row.getValue("OBJECT_NO")%>">
 			<input type="hidden" name="workorderObjectCode" value="">
 			<input type="hidden" name="responsibilityUser" value="<%=row.getValue("RESPONSIBILITY_USER")%>">
 			<input type="hidden" name="costCenterCode" value="<%=row.getValue("COST_CENTER_CODE")%>" >
    </table>
</div>

			
   <%
		}
   %>
     </table>
    </div>
 <input type="hidden" name="oneBarcode" >
 <input type="hidden" name="oneBarcode" >
 <input type="hidden" name="isChangeObjectCode" value ="0" >
	
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">
	
	function do_Save() {
		var isComplete=true;
		if(document.mainFrm.SourceContentCode.value!=document.mainFrm.contentCode.value){
			isComplete=false;
		}
		if(document.mainFrm.SourceItemSpec.value!=document.mainFrm.itemSpec.value){
			isComplete=false;
		}
		if(document.mainFrm.SourceItemQty.value!=document.mainFrm.itemQty.value){
			isComplete=false;
		}
		if(document.mainFrm.SourceUnitOfMeasure.value!=document.mainFrm.unitOfMeasure.value){
			isComplete=false;
		}
		if(document.mainFrm.SourceWorkorderObjectName.value!=document.mainFrm.workorderObjectName.value){
			isComplete=false;
			mainFrm.isChangeObjectCode.value="1";
		}
		if(document.mainFrm.SourceStartDate.value!=document.mainFrm.startDate.value){
			isComplete=false;
		}

		if(isComplete==false){
			if(document.mainFrm.diffResult.value==""){
				alert("请填写差异处理结果");
			}else{
				isComplete=true;
			}
		}

		if(isComplete==true) {
			mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
	        mainFrm.submit()
		}
    }
	
	function showLocation() {
        var userPara = "organizationId=" + document.getElementById("organizationId").value ;
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
                mainFrm.objectNo.value = mainFrm.workorderObjectCode.value; 
            }
        } 
    }
	
	function chooseDeptInfo() {
		var userPara = "organizationId=" + document.mainFrm.organizationId.value;
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU2%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var dept = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	    if(dept){
	        dto2Frm(dept[0], "mainFrm");
	        mainFrm.responsibilityDept.title = mainFrm.deptName.value;
	        mainFrm.responsibilityCode.value = mainFrm.deptCode.value;
	        mainFrm.responsibilityDept.value = mainFrm.deptName.value;   
	    } else {
	    	mainFrm.deptName.value = "";
	    	mainFrm.deptCode.value = "";
	    }
	}
	
	function chooseDeptInfo1() {
		var userPara = "organizationId=" + document.mainFrm.organizationId.value;
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU2%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var dept = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	    if(dept){
	        dto2Frm(dept[0], "mainFrm");
	        mainFrm.specialityDept.title = mainFrm.deptName.value;
	        mainFrm.specialityCode.value = mainFrm.deptCode.value;
	        mainFrm.specialityDept.value = mainFrm.deptName.value;   
	    } else {
	    	mainFrm.deptName.value = "";
	    	mainFrm.deptCode.value = "";
	    }
	}
	function chooseCostCenter() {
	    var lookUpName = "<%=LookUpConstant.COST_CENTER%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	    if (projects) {
	        dto2Frm(projects[0], "mainFrm");
	    } 
	}

	function chooseContentCode() {
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	    if (projects) {
	        dto2Frm(projects[0], "mainFrm");
	    } 
	}


	
	function do_SelectPerson(){
        with(mainFrm){
            var lookUpName = "LOOK_UP_PERSON";
            var dialogWidth = 47;
            var dialogHeight = 30;
            var userPara = "deptCode=" + mainFrm.responsibilityCode.value;
            var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
            if (users) {
                var user = users[0];
                responsibilityName.value = user["userName"];
                responsibilityUser.value = user["employeeNumber"];
            } 
        }
    }
	
</script>