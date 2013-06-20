<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-3-30
  Time: 14:22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
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
<!--<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjfxsqServlet" method="post">-->
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
    <%--<img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">--%>
    <!--<img src="/images/button/deleteLine.gif" alt="删除行"-->
         <!--onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">-->
    <%--<img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_SavePo(1);">--%>
    <%--<img src="/images/button/submit.gif" alt="提交" id="img4" onClick="do_SavePo(2);">--%>
    <%
        if (amsItemTransH.getTransStatus().equals(DictConstant.REJECTED)) {
    %>
    <%--<img src="/images/button/viewOpinion.gif" alt="查阅意见" onClick="viewOpinion();">--%>
    <%}%>
    <%--<img src="/images/button/close.gif" alt="关闭" onClick="window.close();">--%>
    <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
</legend>
<script type="text/javascript">
    var columnArr = new Array("checkbox", "设备名称", "设备型号", "设备类型", "用途", "厂商", "故障地点", "返修数量", "工程待修数", "正常待修数", "实分数量");
    var widthArr = new Array("2%", "10%", "15%", "10%", "10%", "10%", "10%", "5%", "5%", "5%", "5%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
        <%
            RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
            if (rows == null || rows.isEmpty()) {
                int k = 0;
        %>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">

            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%" name="itemName" id="itemName0"></td>
            <td width="15%" name="itemSpec" id="itemSpec0"></td>
            <td width="10%" name="itemCategory" id="itemCategory0"></td>
            <td width="10%" name="spareUsage" id="spareUsage0"></td>
            <td width="10%" name="vendorName" id="vendorName0"></td>
            <td width="10%"><input type="text" name="reasons" id="reasons0" value="" class="blueBorder" style="width:100%;text-align:left">
            </td>
            <td width="5%" align="center"><input type="text" name="onhandQty" id="onhandQty" value="" class="noborderGray" readonly style="width:100%;text-align:right">
            </td>
            <td width="5%" align="center"><input type="text" name="quantity" id="quantity0" value="" class="blueborderYellow" onblur="do_change(this)" style="width:100%">
            </td>
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
        <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">
            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%"><%=row.getValue("ITEM_NAME")%></td>
            <td width="15%"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="10%"><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width="10%"><%=row.getValue("SPARE_USAGE")%></td>
            <td width="10%"><%=row.getValue("VENDOR_NAME")%></td>
            <td width="10%"><%=row.getValue("REASONS")%></td>
            <td width="5%"><%=row.getValue("QUANTITY")%></td>
            <td width="5%"><%=row.getValue("PRO_QTY")%></td>
            <td width="5%"><%=row.getValue("ORD_QTY")%></td>
            <td width="5%"><%=row.getValue("ACTUAL_QTY")%></td>
            <td style="display:none">
                <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>" >
                <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">

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
      var headerId=document.mainForm.transId.value;
        var url="/servlet/com.sino.ams.spare.servlet.SpareDiffServlet?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }

</script>
</html>