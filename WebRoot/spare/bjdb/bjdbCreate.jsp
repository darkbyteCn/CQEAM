<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>派发出库单</title>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<%@ include file="/flow/flowNoButton.jsp" %>
<body leftmargin="1" topmargin="1" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemAllocateHDTO dto = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    String fromOrgOpt = (String) request.getAttribute("CITY_OPTION");
    String toOrgOpt = (String) request.getAttribute("OU_OPTION");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjdbServlet" method="post">
<%@ include file="/flow/flowPara.jsp" %>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
<input type="hidden" name="errorKyl" value="">

<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1" width="100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>" readonly class="blueborderGray"></td>
                    <td width="9%" align="right">调出公司：</td>
                    <td width="25%"><select name="fromOrganizationId" onchange="do_Delete(document.getElementById('dataTable'),'subCheck')" style="width:100%"><%=fromOrgOpt%></select></td>
                    <td width="9%" align="right">调入公司：</td>
                    <td width="25%"><select name="toOrganizationId" onchange="do_Delete2()" style="width:100%"><%=toOrgOpt%></select></td>
                </tr>
                <tr height="22">
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" style="width:100%" readonly value="<%=dto.getTransStatusName()%>" class="blueborderGray"></td>
                    <td align="right">调出仓库：</td>
                    <td><input type="text" name="fromObjectName" class="blueborderYellow" style="width:88%" readonly value="<%=dto.getFromObjectName()%>"><a href="#" class="linka" style="cursor:'hand'" onclick="do_selectFromName();">[…]</a></td>
                    <td align="right">调入仓库：</td>
                    <td><input type="text" name="toObjectName" class="blueborderYellow" style="width:88%" readonly value="<%=dto.getToObjectName()%>"><a href="#" class="linka" style="cursor:'hand'" onclick="do_selectToName();">[…]</a></td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" style="width:100%" value="<%=dto.getCreatedUser()%>" readonly class="blueborderGray"></td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:100%" value="<%=dto.getCreationDate()%>" class="blueborderGray"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="5"><textarea name="remark" rows="3" cols="" style="width:100%" class="blueBorder"><%=dto.getRemark()%></textarea></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
<%
	String transStatus = dto.getTransStatus();
    if(dto.getTransId().equals("")){
%>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        统一设置：<label for="allTransCount">调拨数量</label><input type="checkbox" name="allTransCount" id="allTransCount">
<%
    }
%>
    </legend>
	<div id="aa" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:172px;left:0px;width:100%">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this)" style="cursor:'hand'" title="点击全选或取消全选">
				<td width="3%" align="center"><input type="checkbox"  class="headCheckbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">可用数量</td>
				<td width="5%" align="center">调拨数量</td>
			</tr>
		</table>
	</div>
	<div id="bb" style="overflow:scroll;height:500px;width:100%;position:absolute;top:195px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    if (rows == null || rows.isEmpty()) {
%>
            <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0"></td>
                <td width="10%"><input type="text" name="itemName" id="itemName0" class="finput"></td>
                <td width="15%"><input type="text" name="itemSpec" id="itemSpec0" class="finput"></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory0" class="finput"></td>
                <td width="10%"><input type="text" name="spareUsage" id="spareUsage0" class="finput"></td>
                <td width="10%"><input type="text" name="vendorName" id="vendorName0" class="finput"></td>
                <td width="5%"><input type="text" name="onhandQty" id="onhandQty" value="" class="finput" readonly></td>
                <td width="5%"><input type="text" name="quantity" id="quantity0" value="" class="finputNoEmpty3" onblur="checkQty(this);"></td>
                <td style="display:none">
					<input type="hidden" name="lineId" id="lineId0" value="">
					<input type="hidden" name="barcode" id="barcode0" value="">
				</td>
            </tr>
<%
	} else {
	    Row row = null;
	    for (int i = 0; i < rows.getSize(); i++) {
	        row = rows.getRow(i);
%>
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
				<td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" readonly value="<%=row.getValue("ITEM_NAME")%>" class="finput"></td>
                <td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" readonly value="<%=row.getValue("ITEM_SPEC")%>" class="finput"></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" readonly value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput"></td>
				<td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" readonly value="<%=row.getValue("SPARE_USAGE")%>" class="finput"></td>
                <td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" readonly value="<%=row.getValue("VENDOR_NAME")%>" class="finput"></td>
				<td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" readonly value="<%=row.getValue("ONHAND_QTY")%>" class="finput"></td>
                <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" onblur="checkQty(this);" value="<%=row.getValue("QUANTITY")%>" class="finputNoEmpty3"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">
                    <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
                </td>
            </tr>
<%
        }
    }
%>
        </table>
    </div>
</fieldset>
</form>
<div id="$$$disableMsg$$$" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
	<table width="100%" height="100%" style="background-color:#FFFFFF;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900"  height="60">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td bgcolor="#FFFFCC" align="center"><font color="#008000" size="2">正在提交数据，请稍候......</font><img src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
</div>
</body>
<script type="text/javascript">

function initPage() {
    do_SetPageWidth();
    doLoad();
    var fromOu = "<%=dto.getFromOrganizationId()%>";
    var toOu = "<%=dto.getToOrganizationId()%>";
    selectSpecialOption("fromOrganizationId", fromOu);
    selectSpecialOption("toOrganizationId", toOu);
    HideSinoButton(1);
    HideSinoButton(3);
    HideSinoButton(8);
}


function do_SelectObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=userAccount.getOrganizationId()%>");
    if (projects) {
        mainForm.toObjectName.value = projects[0].workorderObjectName;
        mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}


function do_SelectItem() {
    if (document.mainForm.fromObjectNo.value == '') {
        alert("请先选择调出仓库！");
        return false;
    }
    var lookUpName = "<%=LookUpConstant.LOOK_UP_DB%>";
    var dialogWidth = 65;
    var dialogHeight = 30;
    var userPara = "organizationId=" + mainForm.fromOrganizationId.value;
    userPara += "&objectNo=" + document.mainForm.fromObjectNo.value;

    //LOOKUP传参数 必须和DTO中一致
    var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);

    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            if (!isItemExist(data)) {
                appendDTO2Table(tab, data, false, "barcode");
            }
        }
    }
}
function isItemExist(itemObj) {
    var retVal = false;
    var tab = document.getElementById("dataTable");
    if (tab.rows) {
        var trObjs = tab.rows;
        var trCount = trObjs.length;
        var trObj = null;
        var itemValue = itemObj.barcode;
        var rowValue = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[1].childNodes[0].value;
            if (itemValue == rowValue) {
                retVal = true;
            }
        }
    }
    return retVal;
}
function do_SavePo(flag) {
    if (validateData()) {
        if (!validateKYL()) {
            mainForm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
            mainForm.submit();
        } else {
            if (flag == 1) {
                document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                document.mainForm.submit();
            } else {
                var orgId = "<%=userAccount.getOrganizationId()%>";
                var userId = "<%=userAccount.getUserId()%>";
                var groupId = document.mainForm.groupId.value;
                var procdureName = "备件调拨流程";
                var flowCode = "";
                var paramObj = new Object();
                paramObj.orgId = orgId;
                paramObj.useId = userId;
                paramObj.groupId = groupId;
                paramObj.procdureName = procdureName;
                paramObj.flowCode = flowCode;
                paramObj.submitH = "submitH()";
                assign(paramObj);
            }
        }
    }
}

function submitH() {
    mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
    mainForm.submit();
}
function validateData() {
    var validate = false;
    if (mainForm.fromObjectName.value == '') {
        alert("请选择调出仓库");
        return false;
    }
    if (mainForm.toObjectName.value == '') {
        alert("请选择调入仓库");
        return false;
    }
    var fieldNames = "quantity";
    var fieldLabels = "数量";
    var validateType = EMPTY_VALIDATE;
    validate = formValidate(fieldNames, fieldLabels, validateType);
    if (validate) {
        validateType = POSITIVE_INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
    }
    return validate;
}
function do_Delete(tab,checkboxName) {
    if (document.mainForm.fromObjectNo.value != '') {
        document.mainForm.fromObjectNo.value = '';
        document.mainForm.fromObjectName.value = '';
    }

    if(!tab || !checkboxName){
		return;
	}
	var rowCount = tab.rows.length;
	if (rowCount == 0) {

	    return;
	}
	var boxArr = document.all(checkboxName);

    var chkCount = getCheckBoxCount(checkboxName);
     if (!getvalues()) {
            return;
        }
    if(confirm("确定要改变公司吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
        var chkObj = null;
		for(var i = chkCount-1; i>-1; i--){
            chkObj = boxArr[i];
			if(tab.rows.length > 1){
                delTableRow(tab, chkObj);
			} else {
				clearContent(tab, chkObj);
				tab.rows[0].style.display = "none";
			}
		}
	}
}
function getvalues() {
   var tab = document.getElementById("dataTable");
   if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
       return false;
   }
   return true;
}

function do_Delete2() {
    if (document.mainForm.toObjectNo.value != '') {
        document.mainForm.toObjectNo.value = '';
        document.mainForm.toObjectName.value = '';
    }
}

function checkQty(obj){
	var id = obj.id.substring(8,obj.id.length);
	var qtyObj = document.getElementById("quantity"+id);
	var onhandQty = document.getElementById("onhandQty"+id).value;
	if (isNaN(obj.value)) {
		alert("调拨数量必须是数字");
		obj.focus();
		return;
	}
	if (!(Number(obj.value)>0)) {
		alert("调拨数量必须大于0！");
		obj.focus();
		return;
	}
	if(Number(obj.value)>Number(onhandQty)){
		alert("调拨数量不能大于可用量，请重新输入！");
		obj.focus();
		return;
	}
	do_SetLineCount(obj);
}

function do_SetLineCount(lineBox){
	if(!mainForm.allTransCount){
		return;
	}
	if(!mainForm.allTransCount.checked){
		return
	}
	var id = lineBox.id;
	var lineValue = lineBox.value;
	var fields = document.getElementsByName("quantity");
	var onHandQtyFields = document.getElementsByName("onhandQty");
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
	var onHandQtyObj = null;
	for(var i = 0; i < fields.length; i++){
		if(checkedCount > 0){
			row = rows[i];
			checkObj = row.childNodes[0].childNodes[0];
			if(!checkObj.checked){
				continue;
			}
		}
		if(fields[i].id == id){
			continue;
		}
		onHandQtyObj = onHandQtyFields[i];
		if(Number(onHandQtyObj.value) >= Number(lineValue)){
			fields[i].value = lineValue;
		}
	}
}

function do_Close(){
	var transStatus = "<%=transStatus%>";
	if(transStatus == "" ||transStatus == "<%=DictConstant.SAVE_TEMP%>" || transStatus == "<%=DictConstant.REJECTED%>"){
		if(confirm(CANCEL_MSG)){
			self.close();
		}
	} else {
		self.close();
	}
}


function do_SaveOrder() {
    mainForm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    mainForm.submit();
}

function do_CancelOrder() {
	if(confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")){
		mainForm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
		mainForm.submit();
	}
}


function do_print() {
  var headerId=mainForm.transId.value;
    url = "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet?act=print&transId="+headerId;
    var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, "", style);
}

function do_selectFromName() {
    if (document.mainForm.fromObjectNo.value != "") {
        var count=getCheckBoxCount("subCheck");
        if (count>0) {
            var tab = document.getElementById("dataTable");
             if(!(count==1&&tab.rows[0].style.display == 'none')){
                 alert("请先删除数据，再重新选择仓库！");
                 return;
             }
        }
    }
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BF2%>&objectCategory=71&organizationId="+mainForm.fromOrganizationId.value;
    var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var users = window.showModalDialog(url, null, popscript);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            mainForm.fromObjectName.value = users[0].workorderObjectName;
            mainForm.fromObjectNo.value = users[0].workorderObjectNo;
        }
    }
}
function do_selectToName() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BF2%>&objectCategory=71&organizationId="+mainForm.toOrganizationId.value;
    var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var users = window.showModalDialog(url, null, popscript);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            mainForm.toObjectName.value = users[0].workorderObjectName;
            mainForm.toObjectNo.value = users[0].workorderObjectNo;
        }
    }
}

function validateKYL() {
    var barcodes = document.getElementsByName("barcode");
    var lineCount = barcodes.length;
    for (var i = 0; i < lineCount; i++) {
        if (document.getElementById('barcode' + i)) {
            var barcode = document.getElementById('barcode' + i).value;
            var fromObjectNo = document.mainForm.fromObjectNo.value;
            var quantity = document.getElementById("quantity" + i).value;
            checkKYL(barcode, fromObjectNo, quantity);
            if (document.mainForm.errorKyl.value == "Y") {
                alert("由于并发因素，调拨数量大于可用量，页面将刷新请重新填写！");
                return false;
            }
        }
    }
    return true;
}
function checkKYL(barcode, fromObjectNo, quantity) {
    var url = "";
    xmlHttp = createXMLHttpRequest();
    if (barcode) {
        url = "/servlet/com.sino.ams.spare.servlet.BjdbServlet?act=CHECK_KYL&barcode=" + barcode + "&fromObjectNo=" + fromObjectNo + "&qtyObj=" +quantity;
        xmlHttp.onreadystatechange = handleReadyItemCode;
        xmlHttp.open("post", url, false);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}
function handleReadyItemCode() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var returnValues = xmlHttp.responseText;
            if (returnValues == "Y") {
                document.mainForm.errorKyl.value = "Y";
            } else {
                document.mainForm.errorKyl.value = "N";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}



function validateBjdb() {
    var validate = true;
    if (mainForm.fromObjectName.value == '') {
        alert("请选择调出仓库");
        validate = false;
    }
    if (mainForm.toObjectName.value == '') {
        alert("请选择调入仓库");
        validate = false;
    }
    var fieldNames = "quantity";
    var fieldLabels = "数量";
    var validateType = EMPTY_VALIDATE;
    validate = formValidate(fieldNames, fieldLabels, validateType);
    if (validate) {
        validateType = POSITIVE_INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
    }
    return validate;
}

function do_Complete_app_yy() {
    if (!validateKYL()) {
        mainForm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainForm.submit();
    } else {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainForm.submit();
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    }
}
</script>
</html>