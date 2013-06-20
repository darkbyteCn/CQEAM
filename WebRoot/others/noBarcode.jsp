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
<%--
  Created by HERRY.
  Date: 2007-11-15
  Time: 15:11:17
--%>
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
<form name="mainForm" action="/servlet/com.sino.ams.others.servlet.NoBarcodeServlet" method="post">
<%--<jsp:include page="/flow/include.jsp" flush="true"/>--%>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="<%=flag%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
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
                    <%if (flag.equals("IN")) {%>
                    <td width="25%"><input type="text" name="toObjectName" value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueborderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>
                    <%} else {%>
                    <td width="25%"><input type="text" name="toObjectName"
                                           value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueborderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectFromObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>
                    <%}%>
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
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="createLine();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行"
             onClick="delRow()">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_save(2);">
        <%}%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("checkbox", "批次", "设备名称", "规格型号", "单位", "数量");
        var widthArr = new Array("2%", "10%", "15%", "15%", "5%", "10%");
        <%if(flag.equals("OUT")){%>
        columnArr[6] = "出库数量";
        widthArr[6] = "10%";
        <%}%>
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%if (rows == null || rows.isEmpty()) {%>
            <%--<tr>
                <td width="2%"><input type="checkbox" name="subCheck"
                                      style="height:20px;margin:0;padding:0"></td>
                <td width="10%"><input type="text" name="batchNo"
                                       value="" class="<%=classProp%>"
                                       style="width:100%;text-align:center"></td>
                <td width="15%"><input type="text" name="itemName"
                                       value="" class="<%=classProp%>"
                                       style="width:100%;text-align:center"></td>
                <td width="15%"><input type="text" name="itemSpec"
                                       value="" class="<%=classProp%>"
                                       style="width:100%;text-align:center"></td>
                <td width="5%"><input type="text" name="itemUnit"
                                      value="" class="<%=classProp%>"
                                      style="width:100%;text-align:center"></td>
                <td width="10%"><input type="text" name="quantity"
                                       value="" class="<%=classProp%>"
                                       style="width:100%;text-align:center"></td>

                <td style="display:none">
                    <input type="hidden" name="storageId" id="storageId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                </td>
            </tr>--%>
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
                <td width="10%" align="center"><input type="text" name="quantity" id="quantity<%=i%>"
                                                      value="<%=row.getValue("QUANTITY")%>" class="<%=classProp%>"
                                                      style="width:100%;text-align:center">
                </td>
                <%if (flag.equals("OUT")) {%>
                <td width="10%" align="center"><input type="text" name="outQuantity" id="outQuantity<%=i%>"
                                                      value="" class="noborderYellow"
                                                      style="width:100%;text-align:center">
                </td>
                <%}%>
                <td style="display:none">
                    <input type="hidden" name="storageId" id="storageId<%=i%>" value="<%=row.getValue("STORAGE_ID")%>">
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

function do_SelectObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
    }
}
function do_SelectFromObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
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
        var itemValue = itemObj.itemCode;
        var rowValue = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[4].childNodes[1].value;
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
        var isValid = true;
        var fieldNames = "quantity";
        var fieldLabels = "数量";
        if (isValid) {
            isValid = formValidate("batchNo", "批次", EMPTY_VALIDATE);
        }
        if (isValid) {
            isValid = formValidate("itemName", "名称", EMPTY_VALIDATE);
        }
        if (isValid) {
            isValid = formValidate("itemSpec", "规格型号", EMPTY_VALIDATE);
        }
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

    var rowCount = document.getElementById("dataTable").rows.length - 1 ;
    if (rowCount < 20) {
        var tbObj = document.getElementById("dataTable");
        var rs = tbObj.rows;
        var count = rs.length
        var row0 = rs[count - 1]
        var newRow = row0.cloneNode(true);
        newRow.id = row0.rowIndex + 1;
        row0.appendChild(newRow)
    } else {
        alert("最多可添加20行");
    }
}
function deleteTableRow(tab, checkboxName) {
    if (!tab || !checkboxName) {
        return;
    }
    var rowCount = tab.rows.length;

    if (rowCount == 0) {
        alert("不存在要删除的行。");
        return;
    }
    var boxArr = getCheckedBox(checkboxName);
    var chkCount = boxArr.length;
    if (chkCount < 1) {
        alert("请先选择要删除的行！");
        return;
    }

    if (confirm("确定要删除选中的行吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")) {
        var chkObj = null;
        for (var i = 0; i < chkCount; i++) {
            chkObj = boxArr[i];

            if (tab.rows.length > 1) {
                deleteRow(tab);
            } else {
                clearContent(tab, chkObj);
                tab.rows[0].style.display = "none";
            }
        }
    }
}
function delTableRow(tab, chkObj) {
    if (!tab || !chkObj) {
        return;
    }
    var trObj = chkObj;
    var trHtm = "";
    for (var i = 0; ; i++) {
        trHtm = trObj.outerHTML;
        var index = trHtm.indexOf("<TR");
        if (index > -1) {
            tab.deleteRow(trObj.rowIndex);
            return;
        }
        trObj = trObj.parentNode;
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
function do_deleete(tab, checkboxName) {

    if (!tab || !checkboxName) {
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
    if (confirm("确定要删除数据吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")) {
        var chkObj = null;
        for (var i = chkCount - 1; i > -1; i--) {
            chkObj = boxArr[i];

            if (tab.rows.length > 1) {
                delTableRow(tab, chkObj);
            } else {
                clearContent(tab, chkObj);
                tab.rows[0].style.display = "none";
            }
        }
    }
}
function delRow() {
    var chkbox = document.getElementsByName("subCheck");
    var tabObj = document.getElementById("dataTable");
    if (confirm("确定要删除选中的行吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")) {
        if (chkbox.length && chkbox.length > 0) {
            for (var i = chkbox.length - 1; i > -1; i--) {
                if (chkbox[i].checked) {
                    //                var id = chkbox[i].parentNode.parentNode.rowIndex;
                    tabObj.deleteRow(i)
                }
            }
        } else {
            alert("请确认有数据行！");
        }
    }
}
var addedRows;
var lastNumOfId = 0;
var initTable = document.getElementById("dataTable");
if (initTable.rows.length && initTable.rows.length > 0) {
    lastNumOfId = initTable.rows.length;
}
function createLine(rowIndex) {
    var fromObjectNo = document.mainForm.toObjectNo.value;
    if (fromObjectNo == "") {
        alert("请选择仓库!");
        return;
    }
    var table = document.getElementById("dataTable");
    var nullTr = document.getElementById('mainTr');
    if (nullTr) {
        table.deleteRow(nullTr.index);
    }
    var nowLength;
    if (typeof(rowIndex) == '') {
        nowLength = table.rows.length;
    } else {
        nowLength = rowIndex;
    }
    var lineNum = '';


    var myRow = table.insertRow(nowLength);
    myRow.id = 'mainTr' + lastNumOfId;
    var vColor = '';
    var itemId;
    var cell1 = myRow.insertCell();
    cell1.width = '2%';
    cell1.align = 'center';
    cell1.innerHTML = '<input type="checkbox" name="subCheck" id="subCheck' + lastNumOfId + '" style="height:20px;margin:0;padding:0;border:none">';


    var cell2 = myRow.insertCell();
    cell2.width = '10%';
    cell2.align = 'center';
    cell2.style.backgroundColor = "#EFEFEF"
    //            lineNum = getLineNum(lastNumOfId);
    cell2.innerHTML = '<input type="text" name="batchNo" id="batchNo' + lastNumOfId + '" value="' + lineNum + '" style = "width:100%;text-align:right;"  class="noborderGray"> ';

    var cell3 = myRow.insertCell();
    cell3.width = '15%';
    cell3.align = 'center';
    cell3.style.backgroundColor = "#EFEFEF"
    //            lineNum = getLineNum(lastNumOfId);
    cell3.innerHTML = '<input type="text" name="itemName" id="itemName' + lastNumOfId + '" value="' + lineNum + '" style = "width:100%;text-align:right;"  class="noborderGray"> ';

    var cell4 = myRow.insertCell();
    cell4.width = '15%';
    cell4.align = 'center';
    cell4.style.backgroundColor = "#EFEFEF"
    //            lineNum = getLineNum(lastNumOfId);
    cell4.innerHTML = '<input type="text" name="itemSpec" id="itemSpec' + lastNumOfId + '" value="' + lineNum + '" style = "width:100%;text-align:right;"  class="noborderGray"> ';
    var cell5 = myRow.insertCell();
    cell5.width = '5%';
    cell5.align = 'center';
    cell5.style.backgroundColor = "#EFEFEF"
    //            lineNum = getLineNum(lastNumOfId);
    cell5.innerHTML = '<input type="text" name="itemUnit" id="itemUnit' + lastNumOfId + '" value="' + lineNum + '" style = "width:100%;text-align:right;"  class="noborderGray"> ';
    var cell6 = myRow.insertCell();
    cell6.width = '10%';
    cell6.align = 'center';
    cell6.style.backgroundColor = "#EFEFEF"
    //            lineNum = getLineNum(lastNumOfId);
    cell6.innerHTML = '<input type="text" name="quantity" id="quantity' + lastNumOfId + '" value="' + lineNum + '" style = "width:100%;text-align:right;"  class="noborderGray"> ';
    lastNumOfId++;
    nowLength++;

}
</script>
</html>
