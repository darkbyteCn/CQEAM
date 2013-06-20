<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.spare.constant.SparePROCConstant" %>
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
<head><title>备件出库</title>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<%@ include file="/flow/flowNoButton.jsp" %>
<body leftmargin="1" topmargin="1" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    String transStatus = dto.getTransStatus();
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    int rowCount = 0;
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareCKServlet" method="post">
<%@ include file="/flow/flowPara.jsp" %>
    <table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
        <tr>
            <td>
                <table width="100%" id="table2" cellspacing="1">
                    <tr height="22">
                        <td width="9%" align="right">单据号：</td>
                        <td width="20%"><%=dto.getTransNo()%></td>
                        <td width="9%" align="right">出库原因：</td>
                        <td width="25%">
                            <select name="spareReason" class="blueBorderYellow" style="width:80%"><%=request.getAttribute("ORDER_REASON")%></select>
                        </td>
                        <td width="9%" align="right">调出仓库：</td>
                        <td width="25%">
                            <input type="text" name="fromObjectName" class="blueborderYellow" style="width:88%" readonly value="<%=dto.getFromObjectName()%>"><a
                                href="#" class="linka" style="cursor:'hand'" onclick="do_selectToName();">[…]</a>
                        </td>
                    </tr>
                    <tr height="22">
                        <td align="right">单据类型：</td>
                        <td>备件出库单</td>
                        <td align="right">创建人：</td>
                        <td><%=dto.getCreatedUser()%></td>
                        <td align="right">创建日期：</td>
                        <td><%=dto.getCreationDate()%></td>
                    </tr>
                    <tr height="22">
                        <td align="right">单据状态：</td>
                        <td><%=dto.getTransStatusName()%></td>
                        <td align="right">备 注：</td>
                        <td colspan="4" align="left">
                            <textarea class="blueborder" cols="90" name="remark" style="width:95%" rows="2"><%=dto.getRemark()%> </textarea>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

    <fieldset>
        <legend>
            <%
                if (dto.getAttribute1().equals("FIRST")) {
            %>
            <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
            <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
            统一设置：<label for="allTransCount">出库数量</label><input type="checkbox" name="allTransCount" id="allTransCount">
            <%
                }
            %>

        </legend>
        <div id="headDiv" style="overflow:hidden;position:absolute;top:133px;left:1px;width:990px">
            <table class="headerTable" border="1" width="100%">
                <tr height="22" onClick="executeClick(this);" style="cursor:'hand'" title="点击全选或取消全选">
                    <td width="3%" align="center">
                        <input type="checkbox" class="headCheckbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck');">
                    </td>
                    <td width="10%" align="center">设备名称</td>
                    <td width="15%" align="center">设备型号</td>
                    <td width="10%" align="center">设备类型</td>
                    <td width="10%" align="center">用途</td>
                    <td width="10%" align="center">厂商</td>
                    <td width="5%" align="center">可用量</td>
                    <td width="5%" align="center">出库数量</td>
                    <td width="10%" align="center">备注</td>
                    <td style="display:none">隐藏域字段</td>
                </tr>
            </table>
        </div>

        <div id="dataDiv" style="overflow:scroll;height:540px;width:1007px;position:absolute;top:155px;left:1px"
             align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
            <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
                <%
                    if (rows == null || rows.isEmpty()) {
                %>
                <tr id="mainTr0" style="display:none">
                    <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                         style="height:20px;margin:0;padding:0"></td>
                    <td width="10%"><input type="text" name="itemName" id="itemName0" value="" class="finput" readonly>
                    </td>
                    <td width="15%"><input type="text" name="itemSpec" id="itemSpec0" value="" class="finput" readonly>
                    </td>
                    <td width="10%"><input type="text" name="itemCategory" id="itemCategory0" value="" class="finput"
                                           readonly></td>
                    <td width="10%"><input type="text" name="spareUsage" id="spareUsage0" value="" class="finput"
                                           readonly></td>
                    <td width="10%"><input type="text" name="vendorName" id="vendorName0" value="" class="finput"
                                           readonly></td>
                    <td width="5%"><input type="text" name="onhandQty" id="onhandQty0" readonly value=""
                                          class="finput3"></td>
                    <td width="5%"><input type="text" name="quantity" id="quantity0" value="" class="finputNoEmpty3"
                                          onblur="checkQty(this);"></td>
                    <td width="10%"><input type="text" name="remarkl" id="remarkl0" value="" class="finput"></td>
                    <td style="display:none">
                        <input type="hidden" name="lineId" id="lineId0" value=""><input type="hidden" name="spareId"
                                                                                        id="spareId0" value="">
                        <input type="hidden" name="barcode" id="barcode0" value="">
                    </td>
                </tr>
                <%
                } else {
                    Row row = null;
                    String readProp = "";
                    String readPropClass = "finput3";
                    String qtyOnBlur="";
                    if (dto.getTransId().equals("")) {
                        readProp = "";
                        readPropClass = "finputNoEmpty3";
                        qtyOnBlur="checkQty(this);";
                    } else {
                        readProp = " readonly";
                        readPropClass = "finput3";
                        qtyOnBlur="";
                    }
                    rowCount = rows.getSize();
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                %>
                <tr id="mainTr<%=i%>">
                    <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                         style="height:20px;margin:0;padding:0"></td>
                    <td width="10%"><input type="text" name="itemName" id="itemName<%=i%>"
                                           value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly></td>
                    <td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>"
                                           value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly></td>
                    <td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>"
                                           value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput" readonly></td>
                    <td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>"
                                           value="<%=row.getValue("SPARE_USAGE")%>" class="finput" readonly></td>
                    <td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>"
                                           value="<%=row.getValue("VENDOR_NAME")%>" class="finput" readonly></td>
                    <td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" readonly
                                          value="<%=row.getValue("ONHAND_QTY")%>" class="finput3"></td>
                    <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>"
                                          value="<%=row.getValue("QUANTITY")%>" onblur="<%=qtyOnBlur%>"
                                          class="<%=readPropClass%>" <%=readProp%>></td>
                    <td width="10%"><input type="text" name="remarkl" id="remarkl<%=i%>"
                                           value="<%=row.getValue("REMARK")%>" class="finput"></td>
                    <td style="display:none">
                        <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>"><input
                            type="hidden" name="spareId" id="spareId<%=i%>" value="<%=row.getValue("SPARE_ID")%>">
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
    <input type="hidden" name="act" value="">
    <input type="hidden" name="flag" value="">
    <input type="hidden" name="transId" value="<%=dto.getTransId()%>">
    <input type="hidden" name="transNo" value="<%=dto.getTransNo()%>">
    <input type="hidden" name="transType" value="<%=dto.getTransType()%>">
    <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
    <input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
    <input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
    <input type="hidden" name="toOrganizationId" value="<%=dto.getToOrganizationId()%>">
    <input type="hidden" name="applyGroup" value="<%=dto.getApplyGroup()%>">
    <input type="hidden" name="fromDept" value="<%=dto.getFromDept()%>">
    <input type="hidden" name="procName" value="<%=SparePROCConstant.SPARE_OUT_PROC%>">
    <input type="hidden" name="errorKyl" value="">
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
</html>

<script type="text/javascript">
function do_SelectItem() {
    if (document.mainForm.fromObjectNo.value == "") {
        alert("请先选择调出仓库！");
        return;
    }
    var spareReason = document.mainForm.spareReason.value;
    if (spareReason == "") {
        alert("请先选择出库原因!");
        return;
    }
    var fromObjectNo = document.mainForm.fromObjectNo.value;
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BJBF%>&organizationId=<%=userAccount.getOrganizationId()%>&objectNo=" + fromObjectNo;
    var popscript = "dialogWidth:65;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var items = window.showModalDialog(url, null, popscript);
    if (items) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < items.length; i++) {
            data = items[i];
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


function do_save(flag) {
    if (flag == 1) {
        document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    } else {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    }
    document.mainForm.submit();
}


function do_Submit() {//提交
    if (!getvalues()) {
        return;
    }
    if (!validateKYL()) {
        document.mainForm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        document.mainForm.submit();
    } else {
        var paramObj = new Object();
        paramObj.orgId = "<%=userAccount.getOrganizationId()%>";
        paramObj.useId = "<%=userAccount.getUserId()%>";
        paramObj.groupId = document.mainForm.fromDept.value;
        paramObj.procdureName = "<%=SparePROCConstant.SPARE_OUT_PROC%>";
        paramObj.flowCode = "";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    }
}


function submitH() {
    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    document.mainForm.submit();
}


function checkQty(obj) {
    var id = obj.id.substring(8, obj.id.length);
    var qtyObj = document.getElementById("quantity" + id);
    var onhandQty = document.getElementById("onhandQty" + id).value;
    if (isNaN(qtyObj.value)) {
        alert("出库数量必须是数字");
        qtyObj.focus();
    }
    if (!(Number(qtyObj.value) > 0)) {
        alert("出库数量大于0！");
        qtyObj.focus();
    }
    if (Number(qtyObj.value) > Number(onhandQty)) {
        alert("出库数量不能大于可用量，请重新输入！");
        qtyObj.focus();
    }
    do_SetLineCount(obj);
}

function do_SetLineCount(lineBox) {
    if (!document.mainForm.allTransCount) {
        return;
    }
    if (!document.mainForm.allTransCount.checked) {
        return;
    }
    var id = lineBox.id;
    var lineValue = lineBox.value;
    var fields = document.getElementsByName("quantity");
    var onhandFields = document.getElementsByName("onhandQty");
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    var row = null;
    var checkObj = null;
    var checkedCount = getCheckedBoxCount("subCheck");
    for (var i = 0; i < fields.length; i++) {
        if (checkedCount > 0) {
            row = rows[i];
            checkObj = row.childNodes[0].childNodes[0];
            if (!checkObj.checked) {
                continue;
            }
        }
        if (fields[i].id == id) {
            continue;
        }
        if (Number(lineValue) <= Number(onhandFields[i].value)) {
            fields[i].value = lineValue;
        }
    }
}


function getvalues() {
    var tab = document.getElementById("dataTable");
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        return false;
    }
    return true;
}

function validateQty() {
    var qtyObj ,onhandQty;
    for (var i = 0; i <<%=rowCount%>; i++) {
        qtyObj = document.getElementById("quantity" + i);
        onhandQty = document.getElementById("onhandQty" + i).value;
        if (isNaN(qtyObj.value)) {
            alert("出库数量必须是数字");
            return false;
        }
        if (!(Number(qtyObj.value) > 0)) {
            alert("出库数量大于0！");
            return false;
        }
        if (Number(qtyObj.value) > Number(onhandQty)) {
            alert("出库数量不能大于现有量！");
            return false;
        }
    }
    return true;
}

function initPage() {
    var spareReason = document.getElementById("spareReason");
    dropSpecialOption(spareReason, '1;2;3;4;7');
    do_SetPageWidth();
    doLoad();
    HideSinoButton(1);
    HideSinoButton(3);
    HideSinoButton(8);
    <%
    if (dto.getAttribute1().equals("PRINT")) {
    %>
    ShowSinoButton(15);
    <%
    }
    %>
}

function do_Approve() {
    if (!getvalues()) {
        return;
    }

    var paramObj = new Object();
    paramObj.orgId = "<%=userAccount.getOrganizationId()%>";
    paramObj.useId = "<%=userAccount.getUserId()%>";
    paramObj.groupId = document.mainForm.fromDept.value;
    paramObj.procdureName = "<%=SparePROCConstant.SPARE_OUT_PROC%>";
    paramObj.flowCode = "";
    paramObj.submitH = "submitH1()";
    assign(paramObj);
}

function submitH1() {
    document.mainForm.act.value = "<%=WebActionConstant.APPROVE_ACTION%>";
    document.mainForm.submit();
}


function do_Reject(flag) {
    addApproveContent(flag);
    var paramObj = new Object();
    paramObj.orgId = "<%=userAccount.getOrganizationId()%>";
    paramObj.useId = "<%=userAccount.getUserId()%>";
    paramObj.groupId = document.mainForm.fromDept.value;
    paramObj.procdureName = "<%=SparePROCConstant.SPARE_OUT_PROC%>";
    paramObj.flowCode = "";
    paramObj.submitH = "submitH2()";
    assign(paramObj);
}

function submitH2() {
    document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
    if (confirm("确定要退回出库申请吗？继续请点击“确定”按钮，否则请点击“取消”按钮")) {
        document.mainForm.submit();
    }
}


function do_Rejve(flag) {
    addApproveContent(flag);
    var paramObj = new Object();
    paramObj.orgId = "<%=userAccount.getOrganizationId()%>";
    paramObj.useId = "<%=userAccount.getUserId()%>";
    paramObj.groupId = "";
    paramObj.procdureName = "<%=SparePROCConstant.SPARE_OUT_PROC%>";
    paramObj.flowCode = flag;
    paramObj.submitH = "submitH6()";
    document.mainForm.act.value = "<%=WebActionConstant.APPROVE_ACTION%>";
    if (flag == 9) {
        assign(paramObj);
    } else {
        document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        if (confirm("确定要退回出库申请吗？继续请点击“确定”按钮，否则请点击“取消”按钮")) {
            submitH6();
        }
    }
}

function submitH6() {
    document.mainForm.submit();
}


function do_Print() {
    var headerId = document.mainForm.transId.value;
    var url = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=print&transId=" + headerId;
    var style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, "", style);
}

function do_Close() {
    self.close();
}

function do_SaveOrder() {
    document.mainForm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    document.mainForm.submit();
}

function do_CancelOrder() {
    if (confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")) {
        document.mainForm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
        document.mainForm.submit();
    }
}

function do_selectToName() {
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
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BJCK%>&objectCategory=71";
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

function validateKYL() {
    var barcodes = document.getElementsByName("barcode");
    var lineCount = barcodes.length;
    for (var i = 0; i < lineCount; i++) {
        var cc = i+1;
        var barcode = document.getElementById('barcode' + cc).value;
        var fromObjectNo = document.mainForm.fromObjectNo.value;
        var orgId = "<%=userAccount.getOrganizationId()%>";
        var qtyObj = document.getElementById("quantity" + cc).value;
        checkKYL(barcode, fromObjectNo, orgId, qtyObj);
        if (document.mainForm.errorKyl.value == "Y") {
            alert("由于并发因素，出库数量大于可用量，页面将刷新请重新填写！");
            return false;
        }
    }
    return true;
}
function checkKYL(barcode, fromObjectNo, orgId, qtyObj) {
    var url = "";
    xmlHttp = createXMLHttpRequest();
    if (barcode) {
        url = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=CHECK_KYL&barcode=" + barcode + "&fromObjectNo=" + fromObjectNo +"&orgId=" +orgId + "&qtyObj=" +qtyObj;
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

function do_Complete_app_yy() {
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    if ("<%=dto.getAttribute1()%>" == "FIRST") {
        if (!validateKYL()) {
            document.mainForm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
            document.mainForm.submit();
        } else {
            document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
            document.mainForm.submit();
        }
    } else {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainForm.submit();
    }
}

function validateBjck() {
    var isValid = true;
    var tab = document.getElementById("dataTable");
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        isValid = false;
    }
    return isValid;
}
</script>
