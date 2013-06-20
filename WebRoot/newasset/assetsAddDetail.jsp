<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.ams.newasset.dto.AssetsAddDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by ai.
  Date: 2008-3-13
--%>
<html>
<%
    AssetsAddDTO assetsAddH = (AssetsAddDTO) request.getAttribute("ASSETS_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<head><title>新增管理资产</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/arrUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>

    <script type="text/javascript">
        printTitleBar("管理资产新增");
        var ArrAction0 = new Array(true, "暂存", "action_save.gif", "暂存", "do_SaveTmp");
        var ArrAction1 = new Array(true, "确定", "action_complete.gif", "确定", "do_Submit");
        var ArrAction2 = new Array(true, "生成标签", "act_firstpage.gif", "生成标签", "do_ProduceNewTag");
        var ArrAction3 = new Array(true, "打印标签", "print.gif", "打印标签", "do_PrintNewTag");
        var ArrAction4 = new Array(true, "导出标签", "toexcel.gif", "导出标签", "do_ExportNewTag");
        var ArrAction5 = new Array(true, "粘贴数据", "PASTE.gif", "粘贴数据", "doPaste");
        var ArrAction6 = new Array(true, "导出模板", "toexcel.gif", "导出模板", "do_exportToExcel");
        var ArrAction7 = new Array(true, "导出地点", "toexcel.gif", "导出地点", "do_expAdress");
        var ArrAction8 = new Array(true, "导出部门", "toexcel.gif", "导出部门", "do_expDept");
        var ArrAction9 = new Array(true, "导出名称", "toexcel.gif", "导出名称", "do_expItem");
        var ArrAction10 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
        var ArrActions ;
        <%if(assetsAddH.getStatus().equals("已完成")){%>
            ArrActions = new Array(ArrAction10);
        <%}else{%>
            ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2,ArrAction3, ArrAction4, ArrAction5,ArrAction6, ArrAction7, ArrAction8, ArrAction9, ArrAction10);
        <%}%>
        printToolBar();
    </script>
</head>
<body topmargin="0" leftmargin="0" onload="init();">
<jsp:include page="/message/MessageProcess"/>

<form name="mainForm" action="/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="headId" value="<%=assetsAddH.getHeadId()%>">
<table width="98%" align="center" id="table2" cellspacing="1">
    <tr height="22">
        <td width="8%" align="right">单据编号：</td>
        <td width="17%"><input type="text" style="width:100%" name="billNo" value="<%=assetsAddH.getBillNo()%>" readonly class="blueborderGray"></td>
        <td width="8%" align="right">单据状态：</td>
        <td width="17%"><input type="text" style="width:100%" name="status" readonly value="<%=assetsAddH.getStatus()%>" class="blueborderGray"></td>
        <td width="8%"  align="right">创建人：</td>
        <td width="17%" ><input type="text" style="width:100%" name="createUser" value="<%=assetsAddH.getCreateUser()%>" readonly class="blueborderGray"></td>
        <td width="8%"  align="right">创建日期：</td>
        <td width="17%"><input type="text" name="createdDate" readonly style="width:100%" value="<%=assetsAddH.getCreatedDate()%>" class="blueborderGray"></td>
    </tr>
    <tr>
	    <%
	        //单据非完成状态并且当前用户是创建人才有操作权限
	        	if (!assetsAddH.getSpecialityDeptOption().equals("")) {
	    %>
		<td align=right >实物管理部门<font color="red">*</font></td>
		<td>
	        <select name="specialityDept" style="width:100%" class="selectNoEmpty" onmouseover="do_ProcessOptionWidth(this)" ><%=assetsAddH.getSpecialityDeptOption()%></select>
	    </td>
		<%
	        	} else {
	    %>		
		<td align=right >实物管理部门</td>
		<td>
	        <input type="text" style="width:100%" name="specDept" value="<%=assetsAddH.getSpecialityDept()%>" readonly class="blueborderGray">
	        <input type="hidden" style="width:100%" name="specialityDept" value="<%=assetsAddH.getSpecDept()%>" readonly class="blueborderGray">
	    </td>
	    <%

	        }
	   %>
	    
        <td align="right" valign="top">备注：</td>
        <td colspan="5"><textarea name="remark" rows="2" cols="" style="width:100%" class="blueBorder"><%=assetsAddH.getRemark()%></textarea></td>
    </tr>
</table>
    <%
        //单据非完成状态并且当前用户是创建人才有操作权限
        if (assetsAddH.getStatus().equals("未完成") && (assetsAddH.getCreatedBy()==user.getUserId())) {
    %>
    <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
    <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <%--<img src="/images/eam_images/save.jpg" alt="保存" id="img3" onClick="do_Save(1);">--%>
    <%--<img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_Save(2);">--%>
    <%--<img src="/images/button/toExcel_addr.gif" alt="导出地点" id="img5" onClick="do_expAdress();">--%>
    <%--<img src="/images/button/toExcel_dept.gif" alt="导出部门" id="img6" onClick="do_expDept();">--%>
    <%--<img src="/images/button/toExcel_name.gif" alt="导出名称型号" id="img7" onClick="do_expItem();">--%>
    <%--<img src="/images/button/toExcel_template.gif" alt="EXCEL模板下载" id="img8" onclick="do_exportToExcel();">--%>
    <%--<img src="/images/button/newTagNumber.gif" alt="生成标签" id="img9" onclick="do_ProduceNewTag();">--%>
    <%--<img src="/images/button/pasteData.gif"  alt="粘贴EXCEL" onClick="doPaste(); return false;">--%>
    <%
        }
    %>
      <%--<span id="warn"></span>--%>
    <%--<img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">--%>
<script type="text/javascript">
    var columnArr = new Array("checkbox", "资产条码", "资产名称", "资产型号", "资产地点", "责任部门", "责任人","使用人", "备注");
    var widthArr = new Array("2%", "10%", "12%", "12%", "14%", "18%", "6%", "6%", "10%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
        <%
            RowSet rows = (RowSet) request.getAttribute("ASSETS_LINES");
            if (rows == null || rows.isEmpty()) {
        %>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">

            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%" align="center"><input type="text" name="barcode" id="barcode"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%" readonly>
            </td>
            <td width="12%" align="center" onclick="do_SelectItemInfo(this,1);"><input type="text" name="itemName" readonly  id="itemName"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="12%" align="center" onclick="do_SelectItemInfo(this,2);"><input type="text" name="itemSpec" readonly id="itemSpec"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="14%" align="center" onclick="do_SelectAddress(this);"><input type="text"
                                                                                    name="workorderObjectName"
                                                                                    id="workorderObjectName0"
                                                                                    value="" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                    readonly title="点击选择资产地点"><input
                    type="hidden" name="addressId" id="addressId0" value="">
            </td>
            <td width="18%" align="center" onclick="do_SelectRespDept(this);"><input type="text" name="deptName"
                                                                                     id="deptName0"
                                                                                     value="" class="blueborderYellow"
                                                                                     style="width:100%;text-align:center"
                                                                                     readonly title="点击选择责任部门"><input
                    type="hidden" name="deptCode" id="deptCode0" value="">
                    <input type="hidden" name="specDept" id="specDept0" value="">
            </td>
            <td width="6%" align="center" onclick="do_SelectRespUser(this);"><input type="text" name="userName"
                                                                                    id="userName0"
                                                                                    value="" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                    readonly title="点击选择责任人"><input
                    type="hidden" name="employeeId" id="employeeId0" value="">
            </td>
            <td width="6%" align="center"><input type="text" name="maintainUser" id="maintainUser0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="10%" align="center"><input type="text" name="remark1" id="remark10"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <!--
            <td width="10%" align="center"><input type="text" name="specDeptName" id="specDeptName0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             -->
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId0" value=""><input type="hidden" name="itemCode"
                                                                                id="itemCode0" value="">
            </td>
        </tr>
        <%
        } else {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">
            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%" align="center"><input type="text" name="barcode"
                                                  id="barcode<%=i%>"
                                                  value="<%=row.getValue("BARCODE")%>"
                                                  class="blueborderYellow"
                                                  style="width:100%">
            </td>
            <td width="12%" name="itemName" id="itemName<%=i%>" onclick="do_SelectItemInfo(this,1);"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="12%" name="itemSpec" id="itemSpec<%=i%>" onclick="do_SelectItemInfo(this,2);"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td align=center width="14%" <%if (assetsAddH.getStatus().equals("未完成")) {%>
                onclick="do_SelectAddress(this);"<%} %>><input type="text" class="blueborderYellow" readonly
                                                               name="workorderObjectName" id="workorderObjectName<%=i%>"
                                                               style="width:100%;text-align:center"
                                                               value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"
                                                               title="点击选择资产地点"><input
                    type="hidden" name="addressId" id="addressId<%=i%>" value="<%=row.getValue("ADDRESS_ID")%>">
            </td>
            <td align=center width="18%" <%if (assetsAddH.getStatus().equals("未完成")) {%>
                onclick="do_SelectRespDept(this);"<%} %>><input type="text" class="blueborderYellow" readonly
                                                                name="deptName" id="deptName<%=i%>"
                                                                style="width:100%;text-align:center"
                                                                value="<%=row.getValue("DEPT_NAME")%>" title="点击选择责任部门"><input
                    type="hidden" name="deptCode" id="deptCode<%=i%>" value="<%=row.getValue("RESP_DEPT")%>">
                    <input type="hidden" name="specDept" id="specDept<%=i%>" value="<%=row.getValue("SPEC_DEPT")%>">
            </td>
            <td align=center width="6%" <%if (assetsAddH.getStatus().equals("未完成")) {%>
                onclick="do_SelectRespUser(this);"<%} %>><input type="text" class="blueborderYellow" readonly
                                                                name="userName" id="userName<%=i%>"
                                                                style="width:100%;text-align:center"
                                                                value="<%=row.getValue("USER_NAME")%>"
                                                                title="点击选择责任人"><input
                    type="hidden" name="employeeId" id="employeeId<%=i%>" value="<%=row.getValue("RESP_USER")%>">
            </td>
            <td width="6%" align="center"><input type="text" name="maintainUser" id="maintainUser<%=i%>"
                                                  value="<%=row.getValue("MAINTAIN_USER")%>" style="width:100%;text-align:right">
            </td>
            <td width="10%" align="center"><input type="text" name="remark1" id="remark1<%=i%>"
                                                  value="<%=row.getValue("REMARK")%>"
                                                  style="width:100%;text-align:right">
            </td>
            <!--
            <td width="10%" align="center"><input type="text" name="specDeptName" id="specDeptName<%=i%>"
                                                  value="<%=row.getValue("SPEC_DEPT_NAME")%>"
                                                  style="width:100%;text-align:right">
            </td>
             -->
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>"><input
                    type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</form>
</body>
<script type="text/javascript">
function init() {
}

function do_SelectItem() {

    var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_SYSITEM%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userdata = "masterOrganizationId=" + <%=user.getOrganizationId()%>;
    
    //userdata += "&specialityDept="+specialityDept;
    
    var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userdata);
    var barcodePrefix = '<%=user.getBookTypeCode()%>';
    barcodePrefix = barcodePrefix.substr(barcodePrefix.length - 4, 4);

    <%--var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_ASSETS_SYSITEM%>";--%>
    <%--var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";--%>
    <%--var assets = window.showModalDialog(url, null, popscript);--%>
    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            if (!isItemExist(data)) {
            	<%--data["barcode"] = "<%=user.getCompanyCode()%>"+'-';--%>
//            	data["barcode"] = barcodePrefix + '-';
                appendDTORow(tab, data);
            }
        }
    }
}

function do_SelectItemInfo(obj, n) {

    obj.childNodes[0].value = "";
	obj.childNodes[0].style.color="black";
    var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_SYSITEM_S%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userdata = "masterOrganizationId=" + <%=user.getOrganizationId()%>;
    var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userdata);
    if (assets) {
    	if (n == 1) {
        	obj.childNodes[0].value = assets[0].itemName;
        	obj.parentNode.cells[3].childNodes[0].value = assets[0].itemSpec;
        	obj.parentNode.cells[3].childNodes[0].style.color="black";
        } else {
        	obj.childNodes[0].value = assets[0].itemSpec;
        	obj.parentNode.cells[2].childNodes[0].value = assets[0].itemName;
        	obj.parentNode.cells[2].childNodes[0].style.color="black";
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
        var itemValue = itemObj.itemCode;
        var rowValue = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[5].childNodes[1].value;
            if (itemValue == rowValue) {
                retVal = true;
            }
        }
    }
    return retVal;
}

function do_SelectRespUser(obj) {

    obj.childNodes[0].value = "";
    obj.childNodes[1].value = "";
    obj.childNodes[0].style.color="black";
    var objName = obj.childNodes[0].name;
    var objId = obj.childNodes[0].id;

    var idNumber = objId.substring(objName.length);

    var deptName = document.getElementById("deptName" + idNumber).value;

    if (deptName == "") {
        alert("请先选择责任部门，再选择责任人！");
        return;
    }

    var upName = "<%=LookUpConstant.LOOK_UP_MIS_USER%>";
    var dialogWidth = 50;
    var dialogHeight = 30;

    var userPara = "deptCode=" + document.getElementById("deptCode" + idNumber).value;
 
    var users = getLookUpValues(upName, dialogWidth, dialogHeight, userPara);
    if (users) {
        obj.childNodes[0].value = users[0].userName;
        obj.childNodes[1].value = users[0].employeeId;
    }
}

function do_SelectRespDept(obj) {
    var objId = obj.childNodes[0].id;
    obj.childNodes[0].value = "";
    obj.childNodes[1].value = "";
    obj.childNodes[0].style.color="black";
    var objName = obj.childNodes[0].name;

    var idNumber = objId.substring(objName.length);
    //mainForm.get
    //alert(idNumber) ;
    document.getElementById("userName" + idNumber).value = "";
    var lookUpSpec = "<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
    if (specs) {
        obj.childNodes[0].value = specs[0].deptName;
        obj.childNodes[1].value = specs[0].deptCode;
    }
    //        if (specs) {
    //            var spec = null;
    //            for (var i = 0; i < specs.length; i++) {
    //                spec = specs[i];
    //                dto2Frm(spec, "mainForm");
    //            }
    //        }
}

/**
 * 功能:选择地点
 */
function do_SelectAddress(obj) {
//    objid=obj.childNodes[0].id;
//    alert(objid)
    obj.childNodes[0].value = "";
    obj.childNodes[1].value = "";
    obj.childNodes[0].style.color="black";
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var userPara = "organizationId=" +<%=user.getOrganizationId()%>;
    var locations = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (locations) {
        obj.childNodes[0].value = locations[0].toObjectName;
        obj.childNodes[1].value = locations[0].addressId;
    }
}

var sflag = null;
function do_Save(flag) {
    var tb = document.getElementById("dataTable");
    if (tb.rows.length == 0) {
        alert("没有选择行数据，请选择行数据！");
        return false;
    }
    to_value();
    
    var specialityDept = mainForm.specialityDept.value;
	if (mainForm.specialityDept.value == "") {
		alert("请选择实物管理部门");
		return;
	}
    
    if (validateData()&& isBarcodeRight()) {
        if (isBarcodeRepeated()) {
             alert("标签号不能重复，请修改红色的标签号！");
             return;
        }else{
	        if (flag == 2) {//submit
	           mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
	        } else {    //save
	           mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
	        }
	        mainForm.submit();
        }
    }
}

function do_SaveTmp() {
    do_Save(1);
}

function do_Submit() {
    do_Save(2);
}

    function isBarcodeRight(){
        var right = true;
        var barcodes = document.getElementsByName("barcodeNo");
        var tab=document.getElementById("dataTable");
        var rCount=tab.rows.length;
        var companyCode = <%=user.getCompanyCode()%>+"-";
        for(var j=0;j<rCount;j++){
           var barcode=  tab.rows[j].cells[1].childNodes[0].value  ;
            if(barcode=="" || barcode==companyCode){
                alert("请输入标签号！");
               	tab.rows[j].cells[1].childNodes[0].focus();
                return false;
            } else if (barcode.substring(0,5) != companyCode) {
            	alert("标签号应该以"+companyCode+"开头！");
               	tab.rows[j].cells[1].childNodes[0].focus();
                return false;
            }
        }
        return right;
    }
    var repeated;
	function isBarcodeRepeated(){
	    repeated = false;
        var barcodes = document.getElementsByName("barcodeNo");
        var barcodeArr = new Array();
        var barcodeArr2 = new Array();
        var tab=document.getElementById("dataTable");
        var rCount=tab.rows.length;
        for(var j=0;j<rCount;j++){
           barcodeArr[j] =tab.rows[j].cells[1].childNodes[0].value;
           barcodeArr2[j] =tab.rows[j].cells[1].childNodes[0].value;
        }
     
        for(var n=0;n<rCount;n++){
           for(var m=1;m<rCount;m++){
           		if(n!=m && barcodeArr[n]==barcodeArr2[m]){
	           		 tab.rows[n].cells[1].childNodes[0].style.color = "red";
	           		 tab.rows[m].cells[1].childNodes[0].style.color = "red";
	           		 repeated=true;
	           		 return repeated;
           		}
           }
        }
        
        //到数据库验证是否有重复
        var url = "/servlet/com.sino.ams.spare.servlet.CheckBarcodeServlet";
        xmlHttp = GetXmlHttpObject(checkBarcode);
        xmlHttp.open('POST', url, false);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("barcodes=" + barcodeArr);
        
        return repeated;
    }
    
function do_check(){
   var tb = document.getElementById("dataTable");
}
function validateData() {
    var validate = false;
    var fieldNames = "barcode;workorderObjectName;deptName;userName;itemCode;employeeId;addressId;deptCode";
    var fieldLabels = "资产条码;资产地点;责任部门;责任人;设备名称型号;责任人;资产地点;责任部门";
    var validateType = EMPTY_VALIDATE;
    validate = formValidate(fieldNames, fieldLabels, validateType);
    return validate;
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
                document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            } else {
                document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
            }
            document.mainForm.submit();
        } else {
            var resArray = resText.parseJSON();
            if (resArray.length > 0) {
                var tabObj = document.getElementById("dataTable");
                var retStr;
                for (var i = 0; i < resArray.length; i++) {
                    retStr = resArray[i];
                    if (retStr == 1) {
                        tabObj.rows[i].cells[1].childNodes[0].style.color = "red";
                        repeated = true;
                        //return;
                    }
                }
                
                //alert("标记为红色的标签号已存在，请修改！");
            }
        }
        xmlHttp = null;
    }
}
    var xmlHttp;
    var segment10Array = new Array();
    var projectNameArray = new Array();
    var segment10Index = -1;
    var projectNameIndex = -1;
    var mark = -1;
function doPaste() {
	try {
		if (confirm("确定粘贴到当前页面？")) {
			var text = window.clipboardData.getData("text");
			if (text == null || text == "") {
				alert("请先在EXCEL摸板里复制数据行数据，然后再粘贴！");
				return;
			}
			var rows = text.split('\n');
			for (var i = 0; i < rows.length - 1; i++) {
				mark ++;
				var row = rows[i];
				insertRow(row);
            }
			pageVerifySegment10();
            to_value();
        }
    } catch(e) {
        alert(e.description);
        alert("粘贴失败!");
    }
}
function pageVerifySegment10() {
    //var warn = document.getElementById('warn');
    //warn.innerText = '';
    doInitArray();
    xmlHttp = createXMLHttpRequest();
    var url = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsTransHeaderServlet" ;
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(segment10Array.toJSONString());
}

function insertRow(row) {
    var cols;
    if (typeof(row) == 'string') {
        cols = row.split('\t');
    } else {
        cols = row;
    }
    var newRow;
    var rowCount =document.getElementById("dataTable").rows.length

    var row1=document.getElementById("mainTr0")
    row1=document.getElementById("dataTable").rows[rowCount-1]  ;
    if(rowCount == 1 && row1.style.display == "none"){
		newRow = row1;
	} else {
		newRow = row1.cloneNode(true);
//      var num = newRow.childNodes[5].childNodes[0].id.substring(newRow.childNodes[5].childNodes[0].name.length);
//      var newId = newRow.childNodes[5].childNodes[0].name + (Number(num) + 1);
//      newRow.childNodes[5].childNodes[0].id=newId;//"deptName"+rowCount;
        for(var i=0;i<9;i++)
        {
            var num = newRow.childNodes[i].childNodes[0].id.substring(newRow.childNodes[i].childNodes[0].name.length);
            var newId = newRow.childNodes[i].childNodes[0].name + (Number(num) + 1);
            newRow.childNodes[i].childNodes[0].id=newId;
            if(i==4 ||i==5 ||i==6 ||i==9)
            {
                var num = newRow.childNodes[i].childNodes[1].id.substring(newRow.childNodes[i].childNodes[1].name.length);
            	var newId = newRow.childNodes[i].childNodes[1].name + (Number(num) + 1);
            	newRow.childNodes[i].childNodes[1].id=newId;
            }
       }

    }

    newRow.style.display = 'block';
    newRow.childNodes[0].childNodes[0].value = mark;
    newRow.childNodes[1].childNodes[0].value = cols[0];
    newRow.childNodes[2].childNodes[0].value = cols[1];
    newRow.childNodes[3].childNodes[0].value = cols[2];
    newRow.childNodes[4].childNodes[0].value = cols[3];
    newRow.childNodes[5].childNodes[0].value = cols[4];

    newRow.childNodes[6].childNodes[0].value = cols[5];
    if (!cols[6]) {
    	newRow.childNodes[7].childNodes[0].value = "";
    } else {
        newRow.childNodes[7].childNodes[0].value = cols[6];
    }
    if (!cols[7]) {
    	newRow.childNodes[8].childNodes[0].value = "";
    } else {
    	newRow.childNodes[8].childNodes[0].value = cols[7];
    }

    document.getElementById("mainTr0").parentNode.appendChild(newRow);

}
function doInitArray() {
    segment10Array = new Array();
    projectNameArray = new Array();
    projectNameIndex = -1;
    var segment10 = document.getElementsByName("segment10");
    for (var i = 2; i < segment10.length; i++) {
        segment10Array[i - 2] = segment10[i].value;
    }
    var projectName = document.getElementsByName("projectName");
    for (var i = 2; i < projectName.length; i++) {
        if (!isEmpty(projectName[i].value)) {
            projectNameIndex++;
            projectNameArray[projectNameIndex] = projectName[i].value;
        }
    }
}
var glb_rownum;
function to_value(){
	var tab = document.getElementById("dataTable");
    var rCount = tab.rows.length;
    var iName;
    var iSpec;
    var addes;
    var depName;
   	var userName;
    var s;
    for(var i=0;i<rCount;i++){
        glb_rownum=i;
        iName = tab.rows[i].cells[2].childNodes[0].value;
        iSpec = tab.rows[i].cells[3].childNodes[0].value;
        addes = tab.rows[i].cells[4].childNodes[0].value;
        depName = tab.rows[i].cells[5].childNodes[0].value;
        userName = tab.rows[i].cells[6].childNodes[0].value;
        var pars = "&iName=" + iName +  "&iSpec=" + iSpec;
        var addre = "&adressName=" + addes;
        var deptName = "&deptName=" + depName;
        var useName= "&userName=" + userName;   

        requestAjaxWait("GET_ITEM_CODE", getId, null, pars);
        requestAjaxWait("GET_ADRESS", getaId, null, addre);
        requestAjaxWait("GET_DEPT", getbId, null, deptName);
        requestAjaxWait("GET_USE", getcId, null, useName);
//      getId(i,s);
    }


}
	function getId() {
		//var j=glb_rownum;
      	var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '' && ret != 0) {
            	var rCount = tab.rows.length;
             	//for(var j=0;j<rCount;j++){
                //if(j==glb_rownum){
                tab.rows[glb_rownum].cells[9].childNodes[1].value = ret;
              	//}
            //}
            } else {
            	tab.rows[glb_rownum].cells[2].childNodes[0].style.color="red";
            	tab.rows[glb_rownum].cells[3].childNodes[0].style.color="red";
            }
        }
    }
	function getaId() {
		//var j=glb_rownum;
      	var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '' && ret != 0) {
	            var rCount = tab.rows.length;
	            	//for(var j=0;j<rCount;j++){
	              	//if(j==glb_rownum){
	            tab.rows[glb_rownum].cells[4].childNodes[1].value = ret;
                	//}
             //}
            } else {
            	tab.rows[glb_rownum].cells[4].childNodes[0].style.color="red";
            }
        }
    }
	function getbId() {
		//var j=glb_rownum;
      	var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '' && ret != 0) {
            	var rCount = tab.rows.length;
              	//for(var j=0;j<rCount;j++){
              	//if(j==glb_rownum){
            	tab.rows[glb_rownum].cells[5].childNodes[1].value = ret;
               	//}
             //}
            } else {
            	tab.rows[glb_rownum].cells[5].childNodes[0].style.color="red";
            }
        }
    }
	function getcId() {
		//var j=glb_rownum;
      	var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '' && ret != 0) {
            var rCount = tab.rows.length;
	            //for(var j=0;j<rCount;j++){
	            //if(j==glb_rownum){
	            	tab.rows[glb_rownum].cells[6].childNodes[1].value = ret;
	            //}
            //}
            } else {
            	tab.rows[glb_rownum].cells[6].childNodes[0].style.color="red";
            }
        }
    }

    function checkBarcode2() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var flexValues = new Array();
            var descriptions = new Array();
            var resText = xmlHttp.responseText;
            if (resText == "ERROR") {
                alert(resText);
            } else if (resText == "OK") {
                isRepeated = false;
                if(sflag == 1){
                    document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                }else{
                    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                    document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
                }
                document.mainForm.submit();
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
                    alert("标记为红色的标签号为非网络标签号，请修改！");
                }
            }
            xmlHttp = null;
        }
    }
function do_expAdress(){
     document.mainForm.act.value = "ADRESS";
    mainForm.submit();
}
function do_expDept(){
        document.mainForm.act.value = "DEPT";
    mainForm.submit();
}
function do_expItem(){
        document.mainForm.act.value = "ITEM";
    mainForm.submit();
}
function do_exportToExcel() {
	mainForm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainForm.submit();
}
function do_PrintNewTag(){
 var tab = document.getElementById("dataTable");
      if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
       return;
    }
    var leg = tab.rows.length;
    var barcodeList="";
    var itemNameList="";
    var itemSpecList="";
    var startDate=null;
    for(var i=0;i<leg;i++){
        if(tab.rows[i].cells[1].childNodes[0].value==''||tab.rows[i].cells[1].childNodes[0].value==null){
            alert("请先生成标签，再打印！")
            return;
        }
       barcodeList+= tab.rows[i].cells[1].childNodes[0].value+';';
        itemNameList+= tab.rows[i].cells[2].childNodes[0].value+';';
         itemSpecList+=tab.rows[i].cells[3].childNodes[0].value+';';
    }

     mainForm.action="/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?count="+leg+"&barcodeList="+barcodeList+"&itemNameList="+itemNameList+"&itemSpecList="+itemSpecList;
    mainForm.act.value = "PRINT_BARCODE";
		mainForm.target = "_blank";
	    mainForm.submit();
    	mainForm.target = "self";
}
 function do_ExportNewTag   () {
	var tab = document.getElementById("dataTable");
      if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
       return;
    }
    var leg = tab.rows.length;
    var barcodeList="";
    var itemNameList="";
    var itemSpecList="";
    var startDate=null;
    for(var i=0;i<leg;i++){
        if(tab.rows[i].cells[1].childNodes[0].value==''||tab.rows[i].cells[1].childNodes[0].value==null){
            alert("请先生成标签，再导出！")
            return;
        }
       barcodeList+= tab.rows[i].cells[1].childNodes[0].value+';';
        itemNameList+= tab.rows[i].cells[2].childNodes[0].value+';';
         itemSpecList+=tab.rows[i].cells[3].childNodes[0].value+';';
    }
    mainForm.action="/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?count="+leg+"&barcodeList="+barcodeList+"&itemNameList="+itemNameList+"&itemSpecList="+itemSpecList;
    mainForm.act.value = "EXPORT_BARCODE";
	mainForm.submit();
}
var xmlHttp2;
function do_ProduceNewTag() {
    var tab = document.getElementById("dataTable");
      if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
       return;
    }
    createXMLHttpRequest2();
    var tagObjs = document.getElementsByName("barcode");
	var baocodeCount = tagObjs.length;
	var url = "/servlet/com.sino.ams.newasset.servlet.NewTagAssetsAddServlet";
	url += "?toOrganizationId=" + "<%=user.getOrganizationId()%>";
    url += "&count=" + baocodeCount;
	xmlHttp2.onreadystatechange = handleReadyStateChange2;
    xmlHttp2.open("post", url, false);
    xmlHttp2.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp2.send(null);
}
function createXMLHttpRequest2() {//创建XMLHttpRequest对象
    try {
        xmlHttp2 = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp2 = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp2 = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
}
function handleReadyStateChange2() {
    if (xmlHttp2.readyState == 4) {
        if (xmlHttp2.status == 200) {
            do_ProcessResponse2(xmlHttp2.responseText)
        } else {
            alert("SOA生成标签失败，请联系管理员！");
        }
    }
}
function do_ProcessResponse2(responseContent){
	var newTagObjs = document.getElementsByName("barcode");
	var responArr = responseContent.split("&&&");
	var barcodeStr = "";
	var bacodeCount = newTagObjs.length;
    var companyCode = "<%=user.getCompanyCode()%>";
	for(var i = 0; i < bacodeCount; i++){
        barcodeStr = responArr[i];
        barcodeStr = companyCode + barcodeStr.substring(4);
        newTagObjs[i].value = barcodeStr;
	}
}
</script>
</html>