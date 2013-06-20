<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2009-3-10
  Time: 15:42:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>条码物资出库</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    RequestParser rp = new RequestParser();
    rp.transData(request);

%>
<form name="mainForm" action="/servlet/com.sino.nm.ams.others.servlet.DeptItemBarcodeOutServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=DictConstant.TMCK%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
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
<legend>设备条码
    <%if (!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)) {%>
    <img src="/images/button/addData.gif" alt="添加数据" onclick="do_checkItem();">
    <img src="/images/button/deleteLine.gif" alt="删除行"
         onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <img src="/images/button/save.gif" alt="暂存" id="img3" onClick="do_save(1);">
    <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_save(2);">
    <%}%>
    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    <%
        if (!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)) {
    %>
    统一设置：
    <input type="checkbox" name="alldept" id="alldept"><label for="alldept">新责任部门</label>
    <input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
    <input type="checkbox" name="allAddress" id="allAddress"><label for="allAddress">新地点</label>
    <%
        }
    %>
</legend>

<script type="text/javascript">
    var columnArr = new Array("checkbox", "设备条码", "设备名称", "规格型号", "新责任部门", "新责任人", "新地点");
    var widthArr = new Array("2%", "12%", "15%", "15%", "20%", "10%", "20%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
        <tr id="mainTr0" style="display:none">
            <%if (rows == null || rows.isEmpty()) {%>
            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode" id="barcode0"
                                                                        value=""
                                                                        readonly class="noborderGray"
                                                                        style="width:100%;text-align:center">
            </td>
            <td width="15%" name="itemName" id="itemName0"></td>
            <td width="15%" name="itemSpec" id="itemSpec0"></td>

            <td width="20%" align="center" onclick="do_SelectRespDept(this);"><input type="text" name="deptName"
                                                                                     id="deptName0"
                                                                                     value="" class="blueborderYellow"
                                                                                     style="width:100%;text-align:center"
                                                                                     readonly title="点击选择责任部门"><input
                    type="hidden" name="responsibilityDept" id="responsibilityDept0" value="">
            </td>
            <td width="10%" align="center" onclick="do_SelectRespUser(this);"><input type="text" name="userName"
                                                                                     id="userName0"
                                                                                     value="" class="blueborderYellow"
                                                                                     style="width:100%;text-align:center"
                                                                                     readonly title="点击选择责任人"><input
                    type="hidden" name="employeeId" id="employeeId0" value="">
            </td>
            <td width="20%" align="center" onclick="do_SelectAddress(this);"><input type="text"
                                                                                    name="workorderObjectName"
                                                                                    id="workorderObjectName0"
                                                                                    value="" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                    readonly title="点击选择资产地点"><input
                    type="hidden" name="addressId" id="addressId0" value="">
            </td>
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
        <tr id="mainTr<%=i%>">

            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode"
                                                                        id="barcode<%=i%>"
                                                                        value="<%=row.getValue("BARCODE")%>"
                                                                        readonly class="noborderGray"
                                                                        style="width:100%;text-align:center">
            </td>
            <td width="15%"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="15%"><%=row.getValue("ITEM_SPEC")%>
            </td>

            <td align=center width="20%" <%if (amsItemTransH.getTransStatusName().equals("未完成")) {%>
                onclick="do_SelectRespDept(this);"<%} %>><input type="text" class="blueborderYellow" readonly
                                                                name="deptName" id="deptName<%=i%>"
                                                                style="width:100%;text-align:center"
                                                                value="<%=row.getValue("DEPT_NAME")%>" title="点击选择责任部门"><input
                    type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>"
                    value="<%=row.getValue("RESP_DEPT")%>">
            </td>
            <td align=center width="10%" <%if (amsItemTransH.getTransStatusName().equals("未完成")) {%>
                onclick="do_SelectRespUser(this);"<%} %>><input type="text" class="blueborderYellow" readonly
                                                                name="userName" id="userName<%=i%>"
                                                                style="width:100%;text-align:center"
                                                                value="<%=row.getValue("USER_NAME")%>"
                                                                title="点击选择责任人"><input
                    type="hidden" name="employeeId" id="employeeId<%=i%>" value="<%=row.getValue("RESP_USER")%>">
            </td>
            <td align=center width="20%" <%if (amsItemTransH.getTransStatusName().equals("未完成")) {%>
                onclick="do_SelectAddress(this);"<%} %>><input type="text" class="blueborderYellow" readonly
                                                               name="workorderObjectName" id="workorderObjectName<%=i%>"
                                                               style="width:100%;text-align:center"
                                                               value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"
                                                               title="点击选择资产地点"><input
                    type="hidden" name="addressId" id="addressId<%=i%>" value="<%=row.getValue("ADDRESS_ID")%>">
            </td>
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
</fieldset>
</form>
</body>
<script type="text/javascript">
function do_checkItem() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_DEPT_OUT%>&organizationId=" +<%=user.getOrganizationId()%>;
    var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
    var items = window.showModalDialog(url, null, popscript);

    if (items) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < items.length; i++) {
            data = items[i];
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
function do_save(flag) {
    var tb = document.getElementById("dataTable");
    if (tb.rows.length == 0 || (tb.rows[0].style.display == 'none' && tb.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        return;
    }
    if (flag == 1) {
        document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    } else {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    }
    document.mainForm.submit();
}
function do_Approve(flag) {
    document.mainForm.flag.value = flag;
    var paramObj = new Object();
    paramObj.orgId = "<%=user.getOrganizationId()%>";
    paramObj.useId = "<%=user.getUserId()%>";
    paramObj.groupId = "<%=user.getCurrGroupId()%>";
    paramObj.procdureName = "备件调拨流程";
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
function do_SelectRespDept(obj) {
    var objId = obj.childNodes[0].id;

    var objName = obj.childNodes[0].name;


    var idNumber = objId.substring(objName.length);
    //        mainForm.get
    //alert(idNumber) ;
    document.getElementById("userName" + idNumber).value = "";
    var lookUpSpec = "<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var check = false;
    var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
    check = mainForm.alldept.checked;
    if (!check) {
        if (specs) {
            obj.childNodes[0].value = specs[0].deptName;
            obj.childNodes[1].value = specs[0].responsibilityDept;
        } else {
            obj.childNodes[0].value = "";
            obj.childNodes[1].value = "";
            obj.childNodes[0].style.color = "black";
        }
    } else {
        var objValue = null;
        var emptyData = false;
        if (specs) {
            objValue = specs[0];
        } else {
            emptyData = true;
        }
        var deptName = document.getElementsByName("deptName");
        var responsibilityDept = document.getElementsByName("responsibilityDept");
        var datac = deptName.length;
        var dataTable = document.getElementById("dataTable");
        var rows = dataTable.rows;
        var row = null;
        var checkObj = null;
        var checkedCount = getCheckedBoxCount("subCheck");
        for (var i = 0; i < datac; i++) {
            if (checkedCount > 0) {
                row = rows[i];
                checkObj = row.childNodes[0].childNodes[0];
                if (!checkObj.checked) {
                    continue;
                }
            }
            if (emptyData) {
                deptName[i].value = "";
                responsibilityDept[i].value = "";
            } else {
                deptName[i].value = objValue["deptName"];
                responsibilityDept[i].value = objValue["responsibilityDept"];
            }
        }

    }
}
function do_SelectRespUser(obj) {


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

    var userPara = "deptCode=" + document.getElementById("responsibilityDept" + idNumber).value;
    var check = false;
    check = mainForm.allUser.checked;
    var users = getLookUpValues(upName, dialogWidth, dialogHeight, userPara);
    if (!check) {
        if (users) {

            obj.childNodes[0].value = users[0].userName;
            obj.childNodes[1].value = users[0].employeeId;
        } else {
            obj.childNodes[0].value = "";
            obj.childNodes[1].value = "";
            obj.childNodes[0].style.color = "black";
        }
    } else {
        var objValue = null;
        var emptyData = false;
        if (users) {
            objValue = users[0];
        } else {
            emptyData = true;
        }
        var userName = document.getElementsByName("userName");
        var employerId = document.getElementsByName("employeeId");
        var datac = userName.length;
        var dataTable = document.getElementById("dataTable");
        var rows = dataTable.rows;
        var row = null;
        var checkObj = null;
        var checkedCount = getCheckedBoxCount("subCheck");
        for (var i = 0; i < datac; i++) {
            if (checkedCount > 0) {
                row = rows[i];
                checkObj = row.childNodes[0].childNodes[0];
                if (!checkObj.checked) {
                    continue;
                }
            }
            if (emptyData) {
                userName[i].value = "";
                employerId[i].value = "";
            } else {
                userName[i].value = objValue["userName"];
                employerId[i].value = objValue["employeeId"];
            }
        }

    }
}
function do_SelectAddress(obj) {

    //    obj.childNodes[0].style.color="black";
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var userPara = "organizationId=" +<%=user.getOrganizationId()%>;
    var locations = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var check = false;
    check = mainForm.allAddress.checked;
    if (!check) {
        if (locations) {
            obj.childNodes[0].value = locations[0].toObjectName;
            obj.childNodes[1].value = locations[0].addressId;
        } else {
            obj.childNodes[0].value = "";
            obj.childNodes[1].value = "";
        }
    } else {
        var objValue = null;
        var emptyData = false;
        if (locations) {
            objValue = locations[0];
        } else {
            emptyData = true;
        }
        var workorderObjectName = document.getElementsByName("workorderObjectName");
        var addressId = document.getElementsByName("addressId");
        var datac = workorderObjectName.length;
        var dataTable = document.getElementById("dataTable");
        var rows = dataTable.rows;
        var row = null;
        var checkObj = null;
        var checkedCount = getCheckedBoxCount("subCheck");
        for (var i = 0; i < datac; i++) {
            if (checkedCount > 0) {
                row = rows[i];
                checkObj = row.childNodes[0].childNodes[0];
                if (!checkObj.checked) {
                    continue;
                }
            }
            if (emptyData) {
                workorderObjectName[i].value = "";
                addressId[i].value = "";
            } else {
                workorderObjectName[i].value = objValue["toObjectName"];
                addressId[i].value = objValue["addressId"];
            }
        }

    }
}
</script>
</html>
