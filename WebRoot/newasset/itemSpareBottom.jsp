<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-6-10
  Time: 22:39:01
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>备品备件台帐维护</title></head>
<body leftmargin="0" topmargin="0" onload="initPage()">
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    int orgId = userAccount.getOrganizationId();
%>

<form name="mainFrm">
    <div id="aa" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:0px;left:0px;width:100%" class="crystalScroll">
        <table border=1 width="200%" class="headerTable">
            <tr height="22px">
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">所在地点</td>
                <td width="8%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">责任部门</td>
                <td width="8%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">实物部门</td>
                <td width="2%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">专业责任人</td>
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">资产目录</td>
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">厂商</td>
                <td width="2%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">使用人</td>
                <td width="2%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">入库日期</td>
                <td width="3%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">设备状态</td>
                <td width="2%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">是否失效</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">备注</td>
            </tr>
        </table>
    </div>
    <div id="bb" style="overflow:scroll;;width:100%;position:absolute;top:22px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <tr>
                <td width="6%"><input type="text" name="workorderObjectName" style="width:100%;cursor:hand" readonly class="readonlyInput" title="点击选择设备地点" onClick="do_SelectAddress();"></td>
                <td width="8%"><select name="responsibilityDept" class="select_style1" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></td>
                <td width="8%"><select name="specialityDept" class="select_style1" style="width:100%"><%=request.getAttribute("DEPT_OPTIONS2")%></select></td>
                <td width="2%"><input type="text" name="specialityUserName" class="input_style2" style="width:100%" style="width:100%;cursor:hand" readonly title="点击选择专业责任人" onClick="do_selectSpecialityUser();"></td>
                <td width="6%"><input type="text" name="contentName" style="width:100%;cursor:hand" readonly class="input_style2" title="点击选择资产目录" onClick="do_SelectContent();"></td>
                <td width="6%"><input type="text" name="manufacturerName" class="input_style2" style="width:100%" style="width:100%;cursor:hand" readonly title="点击选择厂商" onClick="do_selectNameManufacturer();"></td>
                <td width="2%"><input type="text" name="maintainUser" class="input_style1" style="width:100%"></td>
                <td width="2%"><input class="input_style2" type="text" name="startDate" style="width:100%;cursor:hand" title="点击选择日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"><input type="hidden" name="endDate"></td>
                <td width="3%"><select name="itemStatus" class="select_style1" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS)%></select></td>
                <td width="2%">
                    <select name="isAbate" class="select_style1" style="width:100%">
                        <option value="">请选择</option>
                        <option value="Y">失效</option>
                        <option value="N">生效</option>
                    </select>
                </td>
                <td width="10%"><input type="text" name="remark" class="input_style1" style="width:100%"></td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="addressId" value="">
	<input type="hidden" name="responsibilityUser" value="">
	<input type="hidden" name="employeeNumber" value="">
	<input type="hidden" name="itemCode" value="">
	<input type="hidden" name="maintainDept" value="">
	<input type="hidden" name="responsibilityDeptName" value="">
    <input type="hidden" name="financePropName" value="">
	<input type="hidden" name="ROLL_BACK_DEPT" value="">
	<input type="hidden" name="workorderObjectCode" value="">
    <input type="hidden" name="manufacturerId" value="">
    <input type="hidden" name="specialityUser" value="">
    <input type="hidden" name="contentCode" value="">
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script>
function initPage() {
    var itemStatus = document.getElementById("itemStatus");
    dropSpecialOption(itemStatus,'PRE_ASSETS;ON_WAY;TO_DISCARD;DISCARDED;CLEARED;DAMAGED;DISCARDED_TRANS;TO_DISCARD_TRANS');
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
		var deptCode = responsibilityDept.value;
		if(deptCode == ""){
			alert("请先选择责任部门，再选择责任人");
			return;
		}
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
		} else {
			responsibilityUserName.value = "";
			responsibilityUser.value = "";
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
		    maintainDeptName.value = obj.groupname;
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
        } else {
            mainFrm.manufacturerId.value = "";
            mainFrm.manufacturerName.value = "";
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
        mainFrm.amsCexId.value = "";
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
        mainFrm.amsOpeId.value = "";
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
        mainFrm.amsLneId.value = "";
    }
}
function do_SelectContent() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_DZYH_CONTENT%>";
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
function do_selectSpecialityUser() {
        //var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_SPECIAL_USER%>";
        var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_RECIIVER%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var userPara = "organizationId=" + <%=orgId%>;
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
</script>