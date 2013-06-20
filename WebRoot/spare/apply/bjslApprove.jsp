<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
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
<head><title>备件申领审批页面</title>
<script type="text/javascript">
    printToolBar();
</script>
</head>
<%@ include file="/flow/flowNoButton.jsp" %>
<body leftmargin="1" topmargin="1" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<%
    RequestParser rp = new RequestParser();
    rp.transData(request);
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjslApproveServlet" method="post">
<%@ include file="/flow/flowPara.jsp" %>
<input type="hidden" name="act" value="">
<input type="hidden" name="barcode1" value="">
<input type="hidden" name="orgvalue" value="">
<input type="hidden" name="lineId1" value="">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transNo" value="<%=dto.getTransNo()%>">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=dto.getFromOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="8%" align="right">单据号：</td>
                    <td width="17%"><%=dto.getTransNo()%></td>
                    <td width="8%" align="right">申请公司：</td>
                    <td width="17%"><%=dto.getToOrganizationName()%></td>
                    <td align="right" width="8%">创建人：</td>
                    <td width="17%"><%=dto.getCreatedUser()%></td>
                    <td align="right" width="8%">创建日期：</td>
                    <td width="17%"><%=dto.getCreationDate()%></td>
                </tr>
                <tr height="22">
                    <td align="right" width="8%">单据状态：</td>
                    <td width="17%"><%=dto.getTransStatusName()%></td>
                    <td align="right" width="8%">厂商：</td>
                    <td width="17%"><%=dto.getVendorName()%></td>
 <%
	if(dto.getAttribute1().equals("ALLOCATE")){
%>
					<td width="8%" align="right">调拨类型：</td>
					<td width="17%" colspan="2"><input type="radio" name="org" id="org1" checked  value="0"><label for="org1">只能省公司调拨</label><input type="radio" name="org" id="org2" value="1"><label for="org2">可从市公司调拨</label></td>
					<td align="left" width="8%">备注：<%=dto.getRemark()%></td>
<%
	} else {
%>
					<td align="right" width="8%">备注：</td>
					<td width="43%" colspan="3"><textarea name="remark" readonly style="width:100%;height:100%" class="blueBorder"><%=dto.getRemark()%></textarea></td>
<%
	}
%>
                </tr>
			</table>
        </td>
    </tr>
</table>

<fieldset style="border:1px solid #397DF3; position:absolute;top:77px;width:100%;height:90%">
<%
	if (dto.getAttribute1().equals("ALLOCATE")) {
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:0px;left:1px;width:990px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
			<td width="3%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
			<td width="10%" align="center">设备名称</td>
			<td width="15%" align="center">设备型号</td>
			<td width="10%" align="center">设备类型</td>
			<td width="10%" align="center">用途</td>
			<td width="10%" align="center">厂商</td>
			<td width="5%" align="center">申领数量</td>
			<td width="5%" align="center">实分数量</td>
			<td style="display:none">隐藏域字段</td>
		</tr>
	</table>
</div>
<%
	} else {
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:0px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
				<td width="3%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">申领数量</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
<%
	}
%>
	<div id="dataDiv" style="overflow:scroll;height:540px;width:1007px;position:absolute;top:23px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
	Row row = null;
        if (dto.getAttribute1().equals("ALLOCATE")) {
        if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				row = rows.getRow(i);
%>
	<tr id="mainTr<%=i%>" title="点击分配数量" style="cursor:hand" onClick="do_Allocate(this)">
		<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
		<td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly style="cursor:hand"></td>
		<td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly style="cursor:hand"></td>
		<td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput" readonly style="cursor:hand"></td>
		<td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" value="<%=row.getValue("SPARE_USAGE")%>" class="finput" readonly style="cursor:hand"></td>
		<td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" value="<%=row.getValue("VENDOR_NAME")%>" class="finput" readonly style="cursor:hand"></td>
		<td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" readonly class="finput3" style="cursor:hand"></td>
        <td width="5%"><input type="text" name="actualQty" id="actualQty<%=i%>" value="<%=row.getValue("ACTUAL_QTY")%>" class="finputNoEmpty3" readonly style="cursor:hand"></td>
		<td style="display:none">
            <input type="text" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>"><input type="text" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
			<input type="text" name="organizationId" id="organizationId<%=i%>">
			<input type="text" name="holdCount" id="holdCount<%=i%>">
		</td>
	</tr>
<%
			}
		}
    	} else {
        if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				row = rows.getRow(i);
%>
        <tr id="mainTr<%=i%>">
		<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
		<td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly></td>
		<td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly></td>
		<td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput" readonly></td>
		<td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" value="<%=row.getValue("SPARE_USAGE")%>" class="finput" readonly></td>
		<td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" value="<%=row.getValue("VENDOR_NAME")%>" class="finput" readonly></td>
		<td width="5%"><input type="text" name="quantity" id="quantity<%=i%>"  value="<%=row.getValue("QUANTITY")%>" class="finput3" readonly></td>
		<td style="display:none">
            <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
            <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
			<input type="hidden" name="organizationId" id="organizationId<%=i%>">
			<input type="hidden" name="holdCount" id="holdCount<%=i%>">
		</td>
	</tr>
<%
            }
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
<div id="$$$requestMsg$$$" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
	<table width="100%" height="100%" style="background-color:#FFFFFF;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900"  height="60">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td bgcolor="#FFFFCC" align="center"><font color="#008000" size="2">正在请求数据，请稍候......</font><img src="/images/wait.gif" alt=""></td>
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
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
function initPage() {
    do_SetPageWidth();
    doLoad();
    HideSinoButton(1);
    HideSinoButton(8);
    <%
    if (!StrUtil.isEmpty(dto.getTransNo())) {
    %>
    ShowSinoButton(15);
    <%
    }
    %>
}

function do_Approve(flag) {
	if(flag == 9){
    <%if (dto.getAttribute1().equals("ALLOCATE")) {%>
        if (!nodistrist()) {
            return false;
        }
    <%}%>
        addApproveContent(flag);
				var paramObj = new Object();
				paramObj.orgId = "<%=userAccount.getOrganizationId()%>";
				paramObj.useId = "<%=userAccount.getUserId()%>";
				paramObj.groupId = "<%=userAccount.getCurrGroupId()%>";
				paramObj.procdureName = "备件申领流程";
				paramObj.flowCode = "";
				paramObj.submitH = "submitH()";
				assign(paramObj);
	} else {
        addApproveContent(flag);
        do_Reject();
	}
}


function do_Reject() {
	if(confirm("确定退回备件领用申请吗？继续请点击“确定”按钮，否则请点击“取消”按钮")){
		mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
		mainForm.submit();
	}
}

function submitH() {
	mainForm.act.value = "<%=WebActionConstant.APPROVE_ACTION%>";
	mainForm.submit();
}


function do_Allocate(obj) {
	var barcode;
	var transId;
	var lineId;
	var qty;
	var org;
	org = (document.getElementById("org1").checked) ? 0 : 1;
	barcode = obj.cells[8].childNodes[0].value;
	transId = mainForm.transId.value;
	qty = obj.cells[6].childNodes[0].value;
	lineId = obj.cells[8].childNodes[1].value;
	var url = "/servlet/com.sino.ams.spare.servlet.BjslApproveServlet?act=" + "ALLOT" + "&barcode1=" + barcode + "&transId=" + transId + "&lineId1=" + lineId+"&sqty="+qty+"&orgvalue="+org;
	var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    isSave = true;
	window.open(url, "bjOrder", popscript);
}


function do_search() {
	var transId;
	transId = mainForm.transId.value;
	var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "SEARCH" + "&transId=" + transId ;
	var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
	window.open(url, "bjOrder", popscript);
}


function do_Print() {
	var headerId=mainForm.transId.value;
	var url="/servlet/com.sino.ams.spare.servlet.SpareAttemperServlet?act=print&transId="+headerId;
	var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
	window.open(url, "", style);
}


function nodistrist() {
	var retVal = false;
	var tab = document.getElementById("dataTable");
	if (tab.rows) {
		var trObjs = tab.rows;
		var trCount = trObjs.length;
		var trObj = null;
		var allotQty = null;
        var applyQty=null;
        for (var i = 0; i < trCount; i++) {
			trObj = trObjs[i];
			applyQty = trObj.cells[6].childNodes[0].value;
			allotQty = trObj.cells[7].childNodes[0].value;
            if (Number(allotQty)<=Number(applyQty)) {
				retVal = true;
			}else{
                retVal=false;
                alert("实分数量不能大于申领数量！")
                break;
            }
		}
	}
	return retVal;
}

function do_Close(){
	self.close();
}

function do_Complete_app_yy() {
    mainForm.act.value = "<%=WebActionConstant.APPROVE_ACTION%>";
	mainForm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function validateBjsl() {
    <%if (dto.getAttribute1().equals("ALLOCATE")) {%>
        var retVal = false;
        var tab = document.getElementById("dataTable");
        if (tab.rows) {
            var trObjs = tab.rows;
            var trCount = trObjs.length;
            var trObj = null;
            var allotQty = null;
            var applyQty=null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                applyQty = trObj.cells[6].childNodes[0].value;
                allotQty = trObj.cells[7].childNodes[0].value;
                if (allotQty == "") {
                    retVal=false;
                    alert("请分配实分数量！");
                    break;
                }
                if (Number(allotQty)<=Number(applyQty)) {
                    retVal = true;
                } else {
                    retVal=false;
                    alert("实分数量不能大于申领数量！");
                    break;
                }
            }
        }
        return retVal;
    <%}%>
}
</script>
</html>