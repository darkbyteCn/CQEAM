<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>

<%@ include file="/spare/headerInclude.htm"%>

<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-18
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>备件调拨单</title>
</head>
<%--<body leftmargin="1" topmargin="1" onload="init();">--%>
<body leftmargin="1" topmargin="1">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemAllocateHDTO dto = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    String fromOrgOpt = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    String toOrgOpt = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjdbServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="groupId" value="">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1" width="100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>" readonly class="blueborderGray"></td>
                    <td width="9%" align="right">调出公司：</td>
                    <%--<td width="25%"><select name="fromOrganizationId" onchange="do_Delete(document.getElementById('dataTable'),'subCheck')" style="width:100%"><%=fromOrgOpt%></select></td>--%>
                    <td width="25%"><input type="text" name="fromOrganizationName" style="width:100%" value="<%=dto.getFromOrganizationName()%>" readonly class="blueborderGray"></td>
                    <td width="9%" align="right">调入公司：</td>
                    <%--<td width="25%"><select name="toOrganizationId" style="width:100%"><%=toOrgOpt%></select></td>--%>
                    <td width="25%"><input type="text" name="toOrganizationName" style="width:100%" value="<%=dto.getToOrganizationName()%>" readonly class="blueborderGray"></td>
                </tr>
                <tr height="22">
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" style="width:100%" readonly value="<%=dto.getTransStatusName()%>" class="blueborderGray"></td>
                    <td align="right">调出仓库：</td>
                    <td><input type="text" name="fromObjectName" style="width:100%" readonly value="<%=dto.getFromObjectName()%>" class="blueborderGray"></td>
                    <td align="right">调入仓库：</td>
                    <td><input type="text" name="toObjectName" style="width:100%" readonly value="<%=dto.getToObjectName()%>" class="blueborderGray"></td>
                </tr>
                <tr>
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
	String status = dto.getTransStatus();
//	if(status.equals("") || status.equals(DictConstant.SAVE_TEMP) || status.equals(DictConstant.REJECTED)){
    String taskProp= StrUtil.nullToString(request.getParameter("taskProp"));
    boolean isFirstNode=taskProp.equals("")||taskProp.equals(FlowConstant.TASK_PROP_START);

//    if(!status.equals("")){
    if(sectionRight.equals("PRINT")){
%>
        <img src="/images/button/print.gif" alt="打印" id="img1" onclick="do_print()">
<%
    }
%>
        <img src="/images/button/close.gif" alt="关闭" onClick="do_Close();">
    </legend>

	<div id="headDiv" style="overflow:hidden;position:absolute;top:155px;left:1px;width:990px">
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
	<div id="dataDiv" style="overflow:scroll;height:500px;width:1007px;position:absolute;top:178px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
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
                <td width="5%"><input type="text" name="onhandQty" id="onhandQty" value="" class="finput3" readonly></td>
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
				<td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" value="<%=row.getValue("ONHAND_QTY")%>" class="finput3"></td>
                <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" onblur="checkQty(this);" value="<%=row.getValue("QUANTITY")%>" class="finput3"></td>
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
</body>
<script type="text/javascript">

//function init() {
//	do_SetPageWidth();
    <%--var fromOu = "<%=dto.getFromOrganizationId()%>";--%>
    <%--var toOu = "<%=dto.getToOrganizationId()%>";--%>
//    selectSpecialOption("fromOrganizationId", fromOu);
//    selectSpecialOption("toOrganizationId", toOu);
//}


function do_SelectObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        mainForm.toObjectName.value = projects[0].workorderObjectName;
        mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}


function do_SelectItem() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_DB%>";
    var dialogWidth = 65;
    var dialogHeight = 30;
    var userPara = "organizationId=" + mainForm.fromOrganizationId.value;

    //LOOKUP传参数 必须和DTO中一致
    var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);

    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            if (!isItemExist(data)) {
                appendDTORow(tab, data);
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
        if (flag == 1) {
            mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            mainForm.submit();
        } else {
            var orgId = "<%=user.getOrganizationId()%>";
            var userId = "<%=user.getUserId()%>";
            var groupId = mainForm.groupId.value;
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

function submitH() {
    mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
    mainForm.submit();
}
function validateData() {
    var validate = false;
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
//            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
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
		alert("调拨数量不能大于现有量，请重新输入！");
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
	var status = "<%=status%>";
	if(status == "" ||status == "<%=DictConstant.SAVE_TEMP%>" || status == "<%=DictConstant.REJECTED%>"){
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
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_CancelOrder() {
	if(confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")){
		mainForm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
		mainForm.submit();
	}
}


function do_print() {
  var headerId=mainForm.transId.value;
//        var url="/servlet/com.sino.ams.spare.servlet.BjdbServlet?act=print&transId="+headerId;
    url = "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet?act=print&transId="+headerId;
    var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, "", style);
}

</script>
</html>