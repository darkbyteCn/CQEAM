<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-3-30
  Time: 14:22:04
  Funciton:备件返修申请单据查看页面
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<head><title>备件返修申请单明细</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
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
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareDiffServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>" readonly
                                           style="width:80%"
                                           class="blueborderGray">
                    </td>
                    <td width="9%" align="right">申请公司：</td>
                    <td width="25%"><input type="text" name="fromOrganizationName"
                                           value="<%=amsItemTransH.getFromOrganizationName()%>"
                                           class="blueborderGray">
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">申请人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                               readonly style="width:80%"
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly
                               value="<%=amsItemTransH.getCreationDate()%>"
                               class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td colspan="2"><input type="text" name="transStatusName" readonly
                                           value="<%=amsItemTransH.getTransStatusName()%>"
                                           class="blueborderGray"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="11"><textarea name="remark" rows="3" cols="" style="width:90%"
                                               class="blueBorder"><%=amsItemTransH.getRemark()%>
                    </textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("设备名称", "设备型号", "设备类型", "用途", "厂商", "故障地点", "待修数量", "返修数量");
        var widthArr = new Array("10%", "15%", "10%", "10%", "10%", "10%", "5%", "5%");
        printTableHead(columnArr, widthArr);
    </script>
    <table class="headerTable" border="1" width="100%" id="headerTable" style="display:none;">
        <tr height="22" style="cursor:hand" title="点击全选或取消全选">
            <td width="10%" align="center">设备名称</td>
            <td width="15%" align="center">设备型号</td>
            <td width="10%" align="center">设备类型</td>
            <td width="10%" align="center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="10%" align="center">故障地点</td>
            <td width="5%" align="center">待修数量</td>
            <td width="5%" align="center">返修数量</td>
        </tr>
    </table>
    <div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>" onMouseMove="this.style.backgroundColor='#EFEFEF'"
                onMouseOut="this.style.backgroundColor='#FFFFFF'">
                <td width="10%"><%=row.getStrValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getStrValue("ITEM_SPEC")%>
                </td>
                <td width="10%"><%=row.getStrValue("ITEM_CATEGORY")%>
                </td>
                <td width="10%"><%=row.getStrValue("SPARE_USAGE")%>
                </td>
                <td width="10%"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <%--<td width="10%"><%=row.getValue("REASONS")%>--%>
                <!--</td>-->
                <td width="10%"><%=row.getValue("TROUBLE_LOC")%>
                </td>
                <td width="5%"><%=row.getValue("ONHAND_QTY")%>
                </td>
                <td width="5%"><%=row.getValue("QUANTITY")%>
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
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
function do_SelectObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
function do_SelectItem() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_FXSQ%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userPara = "organizationId=" +<%=user.getOrganizationId()%>;

    //LOOKUP传参数 必须和DTO中一致
    var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);

<%--var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_FXSQ%>";
var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
/*   window.open(url);*/
var assets = window.showModalDialog(url, null, popscript);--%>
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
function do_edit() {
    var va = false;
    var tab = document.getElementById("dataTable");
    var trcount;
    if (mainForm.transStatus.value == "SAVE_TEMP") {
        trcount = tab.rows.length
        for (var i = 0; i < trcount; i++) {
            var onqty = document.getElementById("onhandQty" + i).value;
            var qty = document.getElementById("quantity" + i).value;
            if (qty == "" || qty == null) {
                alert("请确认返修数量不能为空!");
                return va;
            }

            alert(qty);
            alert(onqty);
            alert(qty - onqty);
            if (Number(qty) > Number(onqty)) {
                alert("请确认返修数量!");
                return va;
            } else {
                va = true;
                return va;
            }
        }
    }
    else
    {
        trcount = tab.rows.length + 1;
        for (var i = 1; i < trcount; i++) {
            var onqty = document.getElementById("onhandQty" + i).value;
            var qty = document.getElementById("quantity" + i).value
            if (qty == "" || qty == null) {
                alert("请确认返修数量不能为空!");
                return va;
            }
            alert(qty)
            alert(onqty)
            var cc = qty - onqty;
            if (cc > 0) {
                alert("请确认返修数量!");
                return;
            } else {
                va = true;
            }
        }
    }

    return va;
}
function getvalues() {
    var tab = document.getElementById("dataTable");
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        return false;
    }
    return true;
}
function do_SavePo(flag) {
    if (!getvalues()) {
        return;
    }
    if (validateData()) {
        if (flag == 1) {
            document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>"
            document.mainForm.submit();
        } else {
            var orgId = "<%=amsItemTransH.getFromOrganizationId()%>";
            var userId = "<%=amsItemTransH.getCreatedBy()%>";
            var groupId = document.mainForm.groupId.value;
            var procdureName = "备件返修申请流程";
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

    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
    document.mainForm.submit();
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
function do_change(obj) {
    var id = obj.id.substring(8, obj.id.length);
    var qtyObj = document.getElementById("quantity" + id);
    var onhandQty = document.getElementById("onhandQty" + id).value;
    if (isNaN(qtyObj.value)) {
        alert("返修数量必须是数字");
        qtyObj.focus();
    }
    if (!(Number(qtyObj.value) > 0)) {
        alert("返修数量大于0！");
        qtyObj.focus();
    }
    if (qtyObj.value.indexOf(".") !== -1) {
        alert("返修数量不能是小数！");
        qtyObj.focus();
    }
    if (Number(qtyObj.value) > Number(onhandQty)) {
        alert("返修数不能大于待修数量，请重新输入！");
        qtyObj.focus();
    }
}

function do_print() {
    var headerId = document.mainForm.transId.value;
    var url = "/servlet/com.sino.ams.spare.servlet.SpareDiffServlet?act=print&transId=" + headerId;
    var style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, "", style);
}

</script>
</html>