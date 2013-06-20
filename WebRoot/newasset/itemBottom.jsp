<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>设备台帐维护--新数据页</title>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage()">
<script type="text/javascript">
    printTitleBar("字段维护修改区");
</script>
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	Object obj=session.getAttribute("DWZCGLY");
	String type="";
	if(obj!=null){
		type=obj.toString();
		session.removeAttribute("DWZCGLY");
	}
%>

<form name="mainFrm">
    <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:21px;left:0px;width:100%">
        <table border=1 width="400%" class="headerTable">
            <tr style="height:22px">
             <%
            	if(type.equals("dwzcgly")){
            		%>
 
                <td width="5%" align="center">实物部门</td>
                <td width="5%" align="center">使用部门</td>
                <td width="5%" align="center">使用人</td>
                
                <td width="10%" align="center">备注一</td>
                <td width="10%" align="center">备注二</td>
                <td width="65%" align="center"></td>
                	
            		<%
            	}else{
            		%>
            		
                <td width="3%" align="center">设备名称</td>
                <td width="3%" align="center">规格型号</td>
                <td width="5%" align="center">地点名称</td>
                <td width="5%" align="center">责任部门</td>
                <td width="3%" align="center">责任人</td>
                <td width="5%" align="center">实物部门</td>
                <td width="5%" align="center">使用部门</td>
                <td width="3%" align="center">使用人</td>
                <td width="4%" align="center">厂商</td>
                <td width="2%" align="center">计量单位</td>
                <td width="2%" align="center">实际数量</td>
                <td width="6%" align="center">备注一</td>
                <td width="6%" align="center">备注二</td>
                <td width="5%" align="center">目录</td>
                <td width="3%" align="center">共享类型</td>
                <td width="3%" align="center">共建类型</td>
                <td width="3%" align="center">设备状态</td>
                <td width="4%" align="center">逻辑网络元素</td>
                <td width="4%" align="center">投资分类</td>
                <td width="4%" align="center">业务平台</td>
                <td width="4%" align="center">网络层次</td>
                
            <%
            	}
            %>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;width:100%;position:absolute;top:22px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="400%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <tr style="height:22px">
            <%
            	if(type.equals("dwzcgly")){
            		%>
            	
                <td width="5%"><select class="select_style1" name="specialityDept" style="width:100%" ><%=request.getAttribute("DEPT_OPTIONS2")%></select></td>
                <td width="5%"><input class="input_style2" type="text" name="maintainDeptName" style="width:100%;cursor:pointer" readonly  title="点击选择使用部门" onClick="do_SelectGroup();"></td>
                <td width="5%"><input class="input_style1" type="text" name="maintainUser" style="width:100%"></td>
               

                <td width="10%"><input class="input_style1" type="text" name="remark1" style="width:100%"></td>
                <td width="10%"><input class="input_style1" type="text" name="remark2" style="width:100%"></td>
                <td width="65%" align="center"></td>           		
            		<%
            	}else{
            		%>
            		 <td width="3%"><input class="input_style2" type="text" name="itemName" style="width:100%;cursor:pointer" readonly title="点击选择设备分类" onClick="do_SelectItemCode();"></td>
                <td width="3%"><input class="input_style2" type="text" name="itemSpec" style="width:100%;cursor:pointer" readonly title="点击选择设备分类" onClick="do_SelectItemCode();"></td>
                <td width="5%"><input class="input_style2" type="text" name="workorderObjectName" style="width:100%;cursor:pointer" readonly title="点击选择设备地点" onClick="do_SelectAddress();"></td>
                <td width="5%"><input class="input_style2" type="text" name="responsibilityDeptName" style="width:100%;cursor:pointer" readonly title="点击选择责任部门和责任人" onClick="do_SelectPerson();"/></td>
                <td width="3%"><input class="input_style2" type="text" name="responsibilityUserName" style="width:100%;cursor:pointer" readonly title="点击选择责任部门和责任人" onClick="do_SelectPerson();"></td>
                <td width="5%"><select class="select_style1" name="specialityDept" style="width:100%"><%=request.getAttribute("DEPT_OPTIONS2")%></select></td>
                <td width="5%"><input class="input_style2" type="text" name="maintainDeptName" style="width:100%;cursor:pointer" readonly title="点击选择使用部门" onClick="do_SelectGroup();"></td>
                <td width="3%"><input class="input_style1" type="text" name="maintainUser" style="width:100%"></td>
                <td width="4%"><input class="input_style2" type="text" name="manufacturerName" style="width:100%" style="width:100%;cursor:pointer" readonly title="点击选择厂商" onClick="do_selectNameManufacturer();"></td>
                <td width="2%"><select class="select_style1" name="itemUnit" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ITEM_UNIT_OPTIONS)%></select></td>
                <td width="2%"><input class="input_style1" type="text" name="actualQty" style="width:100%" onblur="checkQty(this);"></td>
                <td width="6%"><input class="input_style1" type="text" name="remark1" style="width:100%"></td>
                <td width="6%"><input class="input_style1" type="text" name="remark2" style="width:100%"></td>
                <td width="5%"><input class="input_style2" type="text" name="contentName" style="width:100%;cursor:pointer" readonly title="点击选择目录" onClick="do_SelectContent();"></td>
                <td width="3%"><select class="select_style1" name="isShare" style="width:100%"><%=request.getAttribute("SHARE_OPTION")%></select></td>
                <td width="3%"><select class="select_style1" name="constructStatus" style="width:100%"><%=request.getAttribute("CONSTRUCT_OPTION")%></select></td>
                <td width="3%"><select class="select_style1" name="itemStatus" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS)%></select></td>
                <td width="4%"><input class="input_style2" type="text" name="logNetEle" style="width:100%;cursor:pointer" readonly title="点击选择逻辑网络元素" onClick="do_SelectLne();"></td>
                <td width="4%"><input class="input_style2" type="text" name="investCatName" style="width:100%;cursor:pointer" readonly title="点击选择投资分类" onClick="do_SelectCex();"></td>
                <td width="4%"><input class="input_style2" type="text" name="opeName" style="width:100%;cursor:pointer" readonly title="点击选择业务平台" onClick="do_SelectOpe();"></td>
                <td width="4%"><input class="input_style2" type="text" name="lneName" style="width:100%;cursor:pointer" readonly title="点击选择网络层次" onClick="do_SelectNle();"></td>
            		
            		<%
            	}
            %>
			</tr>
        </table>
    </div>
    <input type="hidden" name="addressId" value="">
	<input type="hidden" name="responsibilityUser" value="">
	<input type="hidden" name="employeeNumber" value="">
	<input type="hidden" name="itemCode" value="">
	<input type="hidden" name="maintainDept" value="">
	<input type="hidden" name="responsibilityDept2" value="">
	<input type="hidden" name="responsibilityDept" value="">
	<input type="hidden" name="financePropName" value="">
	<input type="hidden" name="ROLL_BACK_DEPT" value="">
	<input type="hidden" name="workorderObjectCode" value="">
    <input type="hidden" name="manufacturerId" value="">
    <input type="hidden" name="contentCode" value="">
    <input type="hidden" name="lneId">
	<input type="hidden" name="cexId">
	<input type="hidden" name="opeId">
	<input type="hidden" name="nleId">

</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script type="text/javascript">
function initPage() {
    do_SetPageWidth();
    var itemStatus = document.getElementById("itemStatus");
    dropSpecialOption(itemStatus,'PRE_ASSETS;ON_WAY;TO_DISCARD;DISCARDED;CLEARED;DAMAGED;DISCARDED_TRANS;TO_DISCARD_TRANS');
}

function checkQty(obj){
    var actualQty = document.mainFrm.actualQty.value;
    if (actualQty) {
        if (isNaN(actualQty)) {
            alert("实际数量必须是数字");
            document.mainFrm.actualQty.focus();
        } else if (!(Number(actualQty)>0)) {
            alert("实际数量必须大于0！");
            document.mainFrm.actualQty.focus();
        } else if (actualQty.indexOf(".")!== -1){
            alert("实际数量不能是小数！");
            document.mainFrm.actualQty.focus();
        }
    }
}

function do_SelectItemCode(){
	with(mainFrm){
		var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_SYS_ITEM%>";
		var dialogWidth = 48;
		var dialogHeight = 30;
		var userPara = "itemCategory=" + parent.dataFrm.document.mainFrm.itemCategory.value;
		var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (objs) {
		    var obj = objs[0];
			dto2Frm(obj, "mainFrm");
		} else {
			itemCode.value = "";
			itemName.value = "";
			itemSpec.value = "";
		}
	}
}

function do_SelectAddress(){
	with(mainFrm){
		var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
		var dialogWidth = 55;
		var dialogHeight = 30;
		userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (objs) {
			var obj = objs[0];
			addressId.value = obj["addressId"];
			workorderObjectName.value = obj["toObjectName"];
			workorderObjectCode.value = obj["workorderObjectCode"];
		} else {
			addressId.value = "";
			workorderObjectName.value = "";
			workorderObjectCode.value = "";
		}
	}
}

function do_SelectPerson(){
	with(mainFrm){
		var deptCode = "";
//		if(deptCode == ""){
//			alert("请先选择责任部门，再选择责任人");
//			return;
//		}
		var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
		var dialogWidth = 47;
		var dialogHeight = 30;
		var userPara = "deptCode=" + deptCode;
		var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if(users){
			var user = users[0];
			responsibilityUserName.value = user["userName"];
			responsibilityUser.value = user["employeeId"];
			employeeNumber.value = user["employeeNumber"];
			responsibilityDeptName.value = user["deptName"];
			responsibilityDept.value = user["deptCode"];
		} else {
			responsibilityUserName.value = "";
			responsibilityUser.value = "";
            responsibilityDeptName.value = "";
			responsibilityDept.value = "";
		}
	}
}

function do_SelectGroup(){
	with(mainFrm){
		var lookUpName = "<%=AssetsLookUpConstant.LOOK_MAINTAIN_DEPT%>";
		var dialogWidth = 47;
		var dialogHeight = 30;
		var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
		if(objs){
		   var obj = objs[0];
		    maintainDept.value = obj.groupId;
		    maintainDeptName.value = obj.groupName;
		} else {
			maintainDept.value = "";
			maintainDeptName.value = "";
		}
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
	}
}

function do_SelectLne(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_LNE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.lneId.value = obj["amsLneId"];
	} else {
        mainFrm.logNetEle.value = "";
        mainFrm.lneId.value = "";
    }
}

function do_SelectCex(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_CEX%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.cexId.value = obj["amsCexId"];
	} else {
        mainFrm.investCatName.value = "";
        mainFrm.cexId.value = "";
    }
}

function do_SelectOpe(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_OPE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.opeId.value = obj["amsOpeId"];
	} else {
        mainFrm.opeName.value = "";
        mainFrm.opeId.value = "";
    }
}

function do_SelectNle(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_NLE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.nleId.value = obj["amsLneId"];
	} else {
        mainFrm.lneName.value = "";
        mainFrm.nleId.value = "";
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
</script>