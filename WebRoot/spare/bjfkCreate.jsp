<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>

<%@ include file="/spare/headerInclude.htm"%>

<%--
  Created by 贾龙川
  Date: 2008-3-17
  Time: 13:51:28
--%>
<html>
<head><title>坏件入库</title>
</head>

<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
	String status = dto.getTransStatus();
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjfkServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=DictConstant.BJFK%>">
<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="toOrganizationId" value="<%=dto.getToOrganizationId()%>">
<input type="hidden" name="submitFlag" value="0">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" value="<%=dto.getTransNo()%>" readonly style="width:100%" class="blueborderGray">
                    </td>
                    <td width="9%" align="right">仓库名称：</td>
                    <td width="25%"><input type="text" id="objectCategory" name="toObjectName" value="<%=dto.getToObjectName()%>" style="width:85%"
                                           class="blueborderYellow">
                        <a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>

                    <td width="9%" align="right">入库原因：</td>
                    <td width="20%"><select id="spareReason" type="text" name="spareReason" class="blueborderGray" style="width:100%">
                        <%=request.getAttribute(WebAttrConstant.SPARE_REASON)%>
                    </select></td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=dto.getCreatedUser()%>"
                               readonly style="width:100%"
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:85%"
                               value="<%=dto.getCreationDate()%>"
                               class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" readonly style="width:100%"
                               value="<%=dto.getTransStatusName()%>"
                               class="blueborderGray"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="7"><textarea name="remark" rows="3" cols="" style="width:100%" class="blueBorder"><%=dto.getRemark()%></textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
<legend>
    <%
        //单据非完成状态并且当前用户是创建人才有操作权限
        if (!dto.getTransStatus().equals(DictConstant.COMPLETED) && dto.getCreatedBy().equals(user.getUserId())) {
    %>
    <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
    <img src="/images/button/deleteLine.gif" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <%--<img src="/images/button/saveTemp.gif" alt="保存" id="img3" onClick="do_SavePo(1);"> 2010-3-26su--%>
    <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_SavePo(2);">
    <%
        }
    %>
       <%if (!StrUtil.isEmpty(dto.getTransNo())){
               if(!dto.getTransNo().equals(WebAttrConstant.ORDER_NO_AUTO_PRODUCE)){
       %>

    <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
     <%}
        }%>
    <img src="/images/button/close.gif" alt="关闭" onClick="do_Close();">
<%
	//单据非完成状态并且当前用户是创建人才有操作权限
	if (!status.equals(DictConstant.COMPLETED) && dto.getCreatedBy().equals(user.getUserId())) {
%>
        统一设置：<label for="allTransCount">入库数量</label><input type="checkbox" name="allTransCount" id="allTransCount">        
<%
	}		
%>

</legend>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:130px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
				<td width="3%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
                <td width="10%" align="center">厂商</td>
                <td width="5%" align="center">数量</td>
				<td width="10%" align="center">备注</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:500px;width:1007px;position:absolute;top:153px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
	<%
		RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
		if (rows == null || rows.isEmpty()) {
	%>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
            <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0"></td>
            <td width="10%"><input type="text" name="itemName" id="itemName0" value="" class="finput" readonly></td>
            <td width="15%"><input type="text" name="itemSpec" id="itemSpec0" value="" class="finput" readonly></td>
            <td width="10%"><input type="text" name="itemCategory" id="itemCategory0" value="" class="finput" readonly></td>
            <td width="10%"><input type="text" name="spareUsage" id="spareUsage0" value="" class="finput" readonly></td>
            <td width="10%"><input type="text" name="vendorName" id="vendorName0" value="" class="finput" readonly></td>
            <td width="5%"><input type="text" name="quantity" id="quantity0" value="" class="finputNoEmpty3" onblur="checkQty(this);"></td>
            <td width="10%"><input type="text" name="remarkl" id="remarkl0" value="" class="finput"></td>
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
            <td width="10%"><input type="text"  name="itemName" id="itemName<%=i%>" value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly></td>
            <td width="15%"><input type="text"  name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly></td>
            <td width="10%"><input type="text"  name="itemCategory" id="itemCategory<%=i%>" value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput" readonly></td>
            <td width="10%"><input type="text"  name="spareUsage" id="spareUsage<%=i%>" value="<%=row.getValue("SPARE_USAGE")%>" class="finput" readonly></td>
            <td width="10%"><input type="text"  name="vendorName" id="vendorName<%=i%>" value="<%=row.getValue("VENDOR_NAME")%>" class="finput" readonly></td>
            <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" class="finputNoEmpty3" onblur="checkQty(this);"></td>
            <td width="10%"><input type="text" name="remarkl" id="remarkl<%=i%>" value="<%=row.getValue("REMARK")%>" class="finput"></td>
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
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
</html>
<script type="text/javascript">
function init() {
	do_SetPageWidth();
    var spareReason = document.getElementById("spareReason");
    dropSpecialOption(spareReason, '1;2;4;5;6;7')
}
function do_SelectObject() {
    var objectCategory = getRadioValue("objectCategory");
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=" + "72");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        mainForm.toObjectName.value = projects[0].workorderObjectName;
        mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        //            mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
function do_SelectItem() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJ_SPARE_CATEGORY%>";
    var popscript = "dialogWidth:65;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var assets = window.showModalDialog(url, null, popscript);
    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            if (!isItemExist(data)) {
                data["quantity"] = 1;
//                appendDTORow(tab, data);
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

 function validateData2() {
    var validate = false;
    var fieldNames = "quantity";
    var fieldLabels = "数量";
    var validateType = POSITIVE_INT_VALIDATE;
    validate = formValidate(fieldNames, fieldLabels, validateType);
//    if (validate) {
//        validateType = POSITIVE_INT_VALIDATE;
//        validate = formValidate(fieldNames, fieldLabels, validateType);
//    }
    return validate;
}

var sflag = null;
function do_SavePo(flag) {
    if (mainForm.toObjectNo.value == '') {
        alert("请选择仓库！");
        return false;
    }
    if (flag == 2) {//submit

        var tb = document.getElementById("dataTable");
        if (tb.rows.length == 0 || (tb.rows[0].style.display == 'none' && tb.rows.length == 1)) {
            alert("请选择设备！");
            return false;
        }
        if (!validateData()) {
            return false;
        }
    }
    sflag = flag;
//    if (isBarcodeRepeated()) {
    //        alert("部件号不能重复，请确认输入的部件号！");
    //    }
    if (document.mainForm.submitFlag.value == "1") {
        alert("正在进行操作，请等待。");
        return;
    }
    document.mainForm.submitFlag.value="1";
    if (sflag == 1) {
        mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    } else {
        mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    }
    mainForm.submit();
}

var xmlHttp = null;
var isRepeated = true;
function isBarcodeRepeated() {
    var repeated = true;
    var barcodes = document.getElementsByName("barcodeNo");
    var barcodeArr = new Array();
    var barcodeArr2 = new Array();
    for (var i = 0; i < barcodes.length; i++) {
        if (barcodes[i].value == "") {
            alert("请输入部件号！");
            barcodes[i].focus();
            return;
        }
        barcodeArr[i] = barcodes[i].value;
        barcodeArr2[i] = barcodes[i].value;
    }
    //        alert("barcodeArr="+barcodeArr)
    if (barcodeArr.length == barcodeArr2.uniqueStrArr().length) {
        repeated = false;
    }
    if (!repeated) {
        //到数据库验证是否有重复
        var url = "/servlet/com.sino.ams.spare.servlet.CheckBarcodeServlet";
        xmlHttp = GetXmlHttpObject(checkBarcode);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("barcodes=" + barcodeArr);
    }
    return repeated;
}

function checkBarcode() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var flexValues = new Array();
        var descriptions = new Array();
        var resText = xmlHttp.responseText;
        if (resText == "ERROR") {
            alert(resText);
        } else if (resText == "OK") {
            isRepeated = false;
            if (sflag == 1) {
                mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            } else {
                mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
            }
            mainForm.submit();
        } else {
            var resArray = resText.parseJSON();
            if (resArray.length > 0) {
                var tabObj = document.getElementById("dataTable");
                var retStr;
                for (var i = 0; i < resArray.length; i++) {
                    retStr = resArray[i];
                    if (retStr == 1) {
                        tabObj.rows[i].cells[1].childNodes[0].style.color = "red";

                    }
                }
                alert("标记为红色的部件号已存在，请修改！");
            }
        }
        xmlHttp = null;
    }
}
 function do_print() {
     var headerId=mainForm.transId.value;
     var url="/servlet/com.sino.ams.spare.servlet.BjfkServlet?act=print&transId="+headerId;
     var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
     window.open(url, "", style);
    }


function checkQty(obj){
	var id = obj.id.substring(8,obj.id.length);
	var qtyObj = document.getElementById("quantity"+id);
	if (isNaN(obj.value)) {
		alert("入库数量必须是数字");
		obj.focus();
		return;
	}
	if (!(Number(obj.value)>0)) {
		alert("入库数量必须大于0！");
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
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
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
		fields[i].value = lineValue;
	}
}

function do_Close(){
	var status = "<%=status%>";
	if(status == "" ||status != "<%=DictConstant.COMPLETED%>"){
		if(confirm(CANCEL_MSG)){
			self.close();
		}
	} else {
		self.close();
	}
}	
</script>
