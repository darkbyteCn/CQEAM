<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-7-7
  Time: 11:56:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<%
    String flag = StrUtil.nullToString(request.getParameter("flag"));
    String flagMeaning = "入";
    if (flag.equals("OUT")) {
        flagMeaning = "出";
    }
    String transType = flag.equals("OUT") ? DictConstant.FTMCK : DictConstant.FTMRK;
    String readonlyProp = flag.equals("OUT") ? "readonly" : "";
    String classProp = flag.equals("OUT") ? "noborderGray" : "noborderYellow";
%>
<head><title>非条码设备<%=flagMeaning%>库</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
</head>
<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/message/MessageProcess"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    RequestParser rp = new RequestParser();
    rp.transData(request);

%>
<form name="mainForm" action="/servlet/com.sino.ams.others.servlet.NoBarcodeOutServlet" method="post">
<%--<jsp:include page="/flow/include.jsp" flush="true"/>--%>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="<%=flag%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="FTMCK">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=user.getOrganizationId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">仓库名称：</td>

                    <td width="25%"><input type="text" name="fromObjectName"
                                           value="<%=amsItemTransH.getFromObjectName()%>"
                                           class="blueborderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectFromObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>

                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>


<fieldset>
    <legend>设备信息
        <%
            if (amsItemTransH.getTransId().equals("")) {
        %>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_addLine();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行"
             onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_save(2);">
        <%}%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("checkbox", "批次", "设备名称", "规格型号", "单位", "可出数量", "出库数量");
        var widthArr = new Array("2%", "10%", "15%", "15%", "5%", "10%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%if (rows == null || rows.isEmpty()) {%>
            <tr id="mainTr0" style="display:none">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="10%" align="center"><input type="text" name="batchNo" id="batchNo0" <%=readonlyProp%>
                                                      value="" class="<%=classProp%>"
                                                      style="width:100%;text-align:center">
                </td>
                <td width="15%"><input type="text" name="itemName" id="itemName0" <%=readonlyProp%>
                                       value="" class="<%=classProp%>"
                                       style="width:100%;text-align:center"></td>
                <td width="15%"><input type="text" name="itemSpec" id="itemSpec0" <%=readonlyProp%>
                                       value="" class="<%=classProp%>"
                                       style="width:100%;text-align:center"></td>
                <td width="5%" align="center"><input type="text" name="itemUnit" id="itemUnit0" <%=readonlyProp%>
                                                     value="" class="<%=classProp%>"
                                                     style="width:100%;text-align:center">
                </td>
                <td width="10%" align="center"><input type="text" name="nowQty" id="nowQty0"
                                                      value="" class=""
                                                      style="width:100%;text-align:center">
                </td>
                <td width="10%" align="center"><input type="text" name="outQuantity" id="outQuantity0"
                                                      value="" class=""
                                                      style="width:100%;text-align:center">
                </td>

                <td style="display:none">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                </td>
            </tr>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);

            %>
            <tr id="mainTr<%=i%>">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="10%" align="center"><input type="text" name="batchNo" id="batchNo<%=i%>"
                                                      value="<%=row.getValue("BATCH_NO")%>" class="<%=classProp%>"
                                                      style="width:100%;text-align:center">
                </td>
                <td width="15%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="5%" align="center"><%=row.getValue("ITEM_UNIT")%>
                </td>
                <td width="10%" align="center"><input type="text" name="nowQty" id="nowQty<%=i%>"
                                                      value="<%=row.getValue("NOW_QTY")%>" class="<%=classProp%>"
                                                      style="width:100%;text-align:center">
                </td>

                <td width="10%" align="center"><input type="text" name="outQuantity" id="outQuantity<%=i%>"
                                                      value="<%=row.getValue("OUT_QUANTITY")%>" class="noborderYellow"
                                                      style="width:100%;text-align:center">
                </td>

                <td style="display:none">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

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
function init() {
}

function do_SelectFromObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        document.mainForm.fromObjectName.value = projects[0].workorderObjectName;
        document.mainForm.fromObjectNo.value = projects[0].workorderObjectNo;
    } else {
        document.mainForm.fromObjectName.value = "";
        document.mainForm.fromObjectNo.value = "";
    }
}
function do_SelectItem() {
    var assets;
<%if(flag.equals("IN")){%>
    assets = getLookUpValues("<%=LookUpConstant.BJ_SYSTEM_ITEM%>", 51, 33, "barcodeNullable=Y");
<%}else{%>
    var fromObjectNo = document.mainForm.fromObjectNo.value;
    if (fromObjectNo == "") {
        alert("请选择仓库!");
        return;
    }
    assets = getLookUpValues("<%=LookUpConstant.FTMCK_ITEM%>", 55, 33, "invCode=" + fromObjectNo);
<%}%>
    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            appendDTORow(tab, data);
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
        var batchNoValue = itemObj.batchNo;
        var itemNameValue = itemObj.itemName;
        var itemSpecValue = itemObj.itemSpec;
        var rowValue = null;
        var rowValue1 = null;
        var rowValue2 = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[1].childNodes[0].value;
            rowValue1 = trObj.cells[2].childNodes[0].value;
            rowValue2 = trObj.cells[3].childNodes[0].value;
//            alert(batchNoValue)
//            alert(itemNameValue)
//            alert(itemSpecValue)
//            alert(rowValue)
//            alert(rowValue1)
//            alert(rowValue2)
            if ((batchNoValue == rowValue) && (rowValue1 == itemNameValue) && (rowValue2 == itemSpecValue)) {
                retVal = true;
            }
        }
    }
    return retVal;
}
function do_save(flag) {
    var tab = document.getElementById("dataTable");
    if (tab.rows) {
        var trObjs = tab.rows;
        var trCount = trObjs.length;
        var trObj = null;
        var nqty = 0;
        var outqty = 0 ;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            nqty = Number(trObj.cells[5].childNodes[0].value);
            outqty = Number(trObj.cells[6].childNodes[0].value);
            if (outqty > nqty) {
                alert("请确认出库数量！");
                return;
            }
        }
    }
    if (flag == 1) {
        document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    } else {
        var isValid = true;

        var fieldNames = "outQuantity";
        var fieldLabels = "出库数量";

        if (isValid) {
            isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        }
        if (isValid) {
            isValid = formValidate(fieldNames, fieldLabels, NUMBER_VALIDATE);
        }
        if (isValid) {
            isValid = formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE);
        }
        if (!isValid) {
            return;
        }

        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    }
    document.mainForm.submit();
}
function do_addLine() {

    var assets;

    var fromObjectNo = document.mainForm.fromObjectNo.value;
    if (fromObjectNo == "") {
        alert("请选择仓库!");
        return;
    }
    assets = getLookUpValues("<%=LookUpConstant.FTMCK_ITEM%>", 55, 33, "invCode=" + fromObjectNo);

    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            if(isItemExist(data)){
                return;
            }else{
            appendDTORow(tab, data); }
        }
    }
}
<%--function do_Approve(flag) {
    document.mainForm.flag.value = flag;
    var paramObj = new Object();
    paramObj.orgId = "<%=user.getOrganizationId()%>";
    paramObj.useId = "<%=user.getUserId()%>";
    paramObj.groupId = "<%=user.getCurrGroupId()%>";
    paramObj.procdureName = "流程";
    paramObj.flowCode = "";
    paramObj.submitH = "submitH()";
    assign(paramObj);
}
function submitH() {
    var flag = document.mainForm.flag.value;
    var actVal = "";
    switch (flag) {
        case 1: actVal = "<%=WebActionConstant.APPROVE_ACTION%>"; break;
        case 2: actVal = "<%=WebActionConstant.REJECT_ACTION%>"; break;
        case 3: actVal = "<%=WebActionConstant.RECEIVE_ACTION%>"; break;
        default :actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
    }
    alert("actVal=" + actVal)
    document.mainForm.act.value = actVal;
    document.mainForm.submit();
}

function viewFlow() {
    var actId = document.getElementById("actId").value;
    if (actId == '') {
        alert("没有找到流程的信息");
        return;
    }
    var url = "/servlet/ViewFlowServlet?actId=" + actId;
    var style = "width=1020,height=668,top=0,left=0";
    var winName = "flowDetail";
    window.open(url, winName, style);
}--%>
</script>
</html>
