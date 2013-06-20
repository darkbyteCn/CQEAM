<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsURLList" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>
<%@ page import="com.sino.ams.spare.constant.SparePROCConstant" %>

<%@ include file="/spare/headerInclude.htm"%>

<%--
	Created by IntelliJ IDEA.
	User: yuyao
	Date: 2007-11-12
	Time: 9:23:08
--%>
<html>
<head><title>备件送修(NEW)</title>
</head>
<body leftmargin="0" topmargin="0" >
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>

<%
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String procName = SparePROCConstant.REPAIRE_PROC;
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    String taskProp= StrUtil.nullToString(request.getParameter("taskProp"));
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
    boolean isFirstNode=taskProp.equals("")||taskProp.equals(FlowConstant.TASK_PROP_START);
    boolean isPrint=!dto.getTransId().equals("");
    boolean isApprove=(!dto.getTransId().equals(""))&&sectionRight.equals("");
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.repair.servlet.SpareRepairServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="flowAct" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="24">
                    <td width="10%" align="right">单据号：</td>
                    <td width="15%">
					  <input type="text" name="transNo" class="blueborderGray" readonly style="width:100%" value="<%=dto.getTransNo()%>">
					</td>
                    <td width="10%" align="right"> 维修公司：</td>
                    <td width="40%">
						<input type="text" name="company" class="blueborderYellow" style="width:70%"   readonly value="<%=dto.getVendorName()%>"><a href= "#" onclick="chooseBJWXCS();" title = "点击选择工程" class="linka">[…]</a>
					</td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">创建人：</td>
                    <td width="15%">
						<input type="text" name="createdUser" class="blueborderGray" readonly style="width:100%" value="<%=dto.getCreatedUser()%>">
					</td>
                    <td width="10%" align="right">委托书编号：</td>
                    <td width="40%">
						<input type="text" name="att1" class="blueborder" style="width:70%" value="<%=dto.getAttribute1()%>">
					</td>
                </tr>
                <tr height="24">                                       
                    <td width="10%" align="right">创建时间：</td>
                    <td width="15%">
						<input type="text" name="creationDate" class="blueborderGray" readonly style="width:100%" value="<%=dto.getCreationDate()%>">
					</td>
                    <td width="10%" align="right">承运人：</td>
                    <td width="40%">
						<input type="text" name="att2" class="blueborder" style="width:70%"  value="<%=dto.getAttribute2()%>">
					</td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">单据状态：</td>
                    <td width="15%">
						<input type="text" name="transStatusName" class="blueborderGray" readonly style="width:100%" value="<%=dto.getTransStatusName()%>">
					</td>
                    <td width="10%" align="right">保值金额：</td>
                    <td width="40%">
						<input type="text" name="att3" class="blueborder" style="width:70%"   onblur = "do_verify();" value="<%=dto.getAttribute3()%>">
					</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <% if (isFirstNode) {//单据非完成状态并且当前用户是创建人才有操作权限%>
        <img src="/images/button/saveTemp.gif" alt="暂存" onClick="do_save();">
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
        <%--<img src="/images/button/pasteData.gif" alt="粘贴" onclick="do_PasteExcelData();">--%>
        <img src="/images/button/deleteLine.gif" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <% 	}
            if(isApprove){
        %>
        <img src="/images/button/pass.gif" alt="通过" id="img4" onClick="do_submit();">
        <%--<img src="/images/button/noPass.gif" alt="退回" id="img3" onClick="do_back();">--%>
        <%}else{%>
        <img src="/images/button/submit.gif" alt="提交" id="img4" onClick="do_submit();">
        <%}%>
        <img src="/images/button/viewFlow.gif" alt="查看流程" id="img5" onClick="viewFlow();">
        <% if (isPrint) {%>
        <img src="/images/button/printView.gif" alt="显示打印页面" onclick="do_print()">
        <% }%>
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>


	<div id="headDiv" style="overflow:hidden;position:absolute;top:130px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%" id="headerTable">
			<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
				<td width="4%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<!--<td width="5%" align="center">序列号</td>-->
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">送修数量</td>
			</tr>
		</table>
	</div>


	<div id="dataDiv" style="overflow:scroll;height:500px;width:1007px;position:absolute;top:153px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
	if (rows == null || rows.isEmpty()) {
%>
            <tr id="mainTr0" style="display:none">
				<td width="4%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0"></td>
				<!--<td width="5%"><input type="text" name="serialNo" id="serialNo0" class="finputNoEmpty3"></td>-->
				<td width="10%"><input type="text" name="itemName" id="itemName0" class="finput" readonly></td>
				<td width="15%"><input type="text" name="itemSpec" id="itemSpec0" class="finput" readonly></td>
				<td width="10%"><input type="text" name="itemCategory" id="itemCategory0" class="finput" readonly></td>
				<td width="10%"><input type="text" name="spareUsage" id="spareUsage0" class="finput" readonly></td>
				<td width="10%"><input type="text" name="vendorName" id="vendorName0" class="finput" readonly></td>
				<td width="5%"><input type="text" name="quantity" id="quantity0" value=""  onblur="checkQty(this);" class="finputNoEmpty3"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="onhandQty" id="onhandQty0" value="">
                    <input type="hidden" name="barcode" id="barcode0" value="">
                    <input type="hidden" name="serialNo" id="serialNo0" value="">
                </td>
            </tr>
<%
	} else {
		Row row = null;
		String readProp = "";
		if(!sectionRight.equals("NEW") || !sectionRight.equals("")){
			readProp = "readonly";
		}
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr id="mainTr<%=i%>">
                <td width="4%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
                <%--<td width="5%"><input type="text" name="serialNo" id="serialNo<%=i%>" class="finputNoEmpty3" value="<%=row.getValue("SERIAL_NO")%>"></td>--%>
                <td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" class="finput" value="<%=row.getValue("ITEM_NAME")%>"></td>
                <td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput" value="<%=row.getValue("ITEM_SPEC")%>"></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" class="finput" value="<%=row.getValue("ITEM_CATEGORY")%>"></td>
				<td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" class="finput" value="<%=row.getValue("SPARE_USAGE")%>"></td>
                <td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput" value="<%=row.getValue("VENDOR_NAME")%>"></td>
                <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" onblur="checkQty(this);" <%=readProp%> value="<%=row.getValue("QUANTITY")%>" class="finputNoEmpty3"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="onhandQty" id="onhandQty<%=i%>" value="<%=row.getValue("QUANTITY")%>">
                    <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
                    <input type="hidden" name="serialNo" id="serialNo<%=i%>" value="<%=row.getValue("SERIAL_NO")%>">
                </td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>
</fieldset>
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
<input type="hidden" name="procName" value="<%=procName%>">
<input type="hidden" name="fromDept" value="<%=dto.getFromDept()%>">
<input type="hidden" name="fromOrganizationId" value="<%=dto.getFromOrganizationId()%>">
<input type="hidden" name="vendorCode" value = "<%=dto.getVendorCode()%>">
</form>
</body>
</html>
<script type="text/javascript">

function do_save() { //暂存
//    if (getvalues()) {
//        if (validateData()) {
//            if (document.mainForm.barcode.value == "") {
//                alert("请确认数据!");
//            } else {
                document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                document.mainForm.flowAct.value = "<%=DictConstant.FLOW_SAVE%>"
                doSubmit();
//            }
//        }
//    }
}

function do_submit() {
	if(doVendor()){
		if (getvalues()) {
			if (validateData()) {
				document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                document.mainForm.flowAct.value = "<%=DictConstant.FLOW_COMPLETE%>"
                var paramObj = new Object();
				var procdureName = document.mainForm.procName.value;
				var groupId = document.mainForm.fromDept.value;
				procdureName = "<%=procName%>";
				paramObj.orgId = "<%=sfUser.getOrganizationId()%>";
				paramObj.useId = "<%=sfUser.getUserId()%>";
				paramObj.groupId = groupId;
				paramObj.procdureName = procdureName;
				paramObj.flowCode = "";
				paramObj.submitH = "doSubmit()";
				assign(paramObj);
			}
		}
	}
}

function do_back() {
	if(doVendor()){
		if (getvalues()) {
			if (validateData()) {
				document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
                document.mainForm.flowAct.value = "<%=DictConstant.FLOW_COMPLETE%>"
                var paramObj = new Object();
				var procdureName = document.mainForm.procName.value;
				var groupId = document.mainForm.fromDept.value;
				procdureName = "<%=procName%>";
				paramObj.orgId = "<%=sfUser.getOrganizationId()%>";
				paramObj.useId = "<%=sfUser.getUserId()%>";
				paramObj.groupId = groupId;
				paramObj.procdureName = procdureName;
				paramObj.flowCode = "";
				paramObj.submitH = "doSubmit()";
				assign(paramObj);
			}
		}
	}
}

	
function doSubmit() {
	document.mainForm.submit();
}

function doVendor(){
	 if(document.mainForm.company.value==""){
		 alert("请选择服务方公司");
		 return false;
	 }
   return true;
}

function do_selectName() {
	var lookUpName = "<%=LookUpConstant.LOOK_UP_BF%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var userPara = "objectCategory=73";
	var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (users) {
		var user = null;
		for (var i = 0; i < users.length; i++) {
			mainForm.toObjectName.value = users[0].workorderObjectName;
			mainForm.toObjectNo.value = users[0].workorderObjectNo;
			mainForm.addressId.value = users[0].addressId;
		}
	}
}

function do_SelectItem() {
	var fromObjectNo = mainForm.fromObjectNo.value;
	var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BJSX%>&organizationId=<%=sfUser.getOrganizationId()%>&objectNo="+fromObjectNo;
	var popscript = "dialogWidth:65;dialogHeight:33;center:yes;status:no;scrollbars:no";
	var items = window.showModalDialog(url, null, popscript);
	if (items) {
		var data = null;
		var tab = document.getElementById("dataTable");
		for (var i = 0; i < items.length; i++) {
			data = items[i];
			if(!isItemExist(data)){
			appendDTORow(tab, data);
				}
		}
	}
}

function do_print() {
	var url = "/servlet/com.sino.ams.spare.repair.servlet.SpareRepairServlet?act=print&transId=" + document.mainForm.transId.value;
	var popscript =  'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
	window.open(url, null, popscript);
}


function validateData(){
	var validate = false;
	var fieldNames = "quantity";
	var fieldLabels = "数量";
	var validateType = EMPTY_VALIDATE;
	validate = formValidate(fieldNames, fieldLabels, validateType);
	if(validate){
		validateType = POSITIVE_INT_VALIDATE;
		validate = formValidate(fieldNames, fieldLabels, validateType);
	}
	return validate;
}

function getvalues() {
	var tab = document.getElementById("dataTable");
	if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
		alert("没有选择行数据，请选择行数据！");
		return false;
	}
	return true;
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


function checkQty(obj){
      var id = obj.id.substring(8,obj.id.length);
      var qtyObj = document.getElementById("quantity"+id);
      var onhandQty = document.getElementById("onhandQty"+id).value;
      if(Number(qtyObj.value)>Number(onhandQty)){
          alert("送修数量不能大于待修数量，请重新输入！");
          qtyObj.focus();
      }
  }


function chooseBJWXCS() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_BJWXC%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
           var user = null;
            for (var i = 0; i < projects.length; i++) {
                mainForm.company.value = projects[0].vendorName;
                mainForm.vendorCode.value = projects[0].vendorCode;
            }
    }
}

function do_verify() {
    var fieldNames = "att3";
    var fieldLabels = "保值金额";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}


function initPage() {
    window.focus();
	var fromGroup = mainForm.fromDept.value;
	if(fromGroup == ""){
		do_SelectCreateGroup();
    }
}


function do_SelectCreateGroup(){
	var fromGroup = mainForm.fromDept.value;
	var url = "/servlet/com.sino.ams.spare.servlet.BJGroupChooseServlet";
	var popscript = "dialogWidth:20;dialogHeight:15;center:yes;status:no;scrollbars:no;help:no";
    var group = window.showModalDialog(url, null, popscript);
    if(group){
        document.mainForm.fromDept.value = group;
    }
}

function do_PasteExcelData(){
	var headerTable = document.getElementById("headerTable");
	var dataTable = document.getElementById("dataTable");
    pasteData(headerTable, dataTable);
}
</script>