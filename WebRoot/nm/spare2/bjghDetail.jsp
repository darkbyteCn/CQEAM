<%--
  Created by HERRY.
  Date: 2008-3-13
  Time: 11:22:56
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>

<html>
<head><title>备件归还</title>
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
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/checkBarcode.js"></script>
    <style type="text/css">
        input {
            font-size: 11px
        }
    </style>
</head>
<body leftmargin="1" topmargin="1">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.BjghServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
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
                    <td width="20%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>" readonly
                                           style="width:80%"
                                           class="blueborderGray">
                    </td>
                    <td width="9%" align="right">借出公司：</td>
                    <td width="25%"><input type="text" name="toOrganizationName"
                                           value="<%=amsItemTransH.getToOrganizationName()%>" class="blueBorderYellow"
                                           style="width:60%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择供出公司和仓库" class="linka">[…]</a>
                    </td>
                    <td width="9%" align="right">借出仓库：</td>
                    <td width="25%"><input type="text" name="toObjectName" value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueBorderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择供出公司和仓库" class="linka">[…]</a>
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                               readonly style="width:80%"
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:60%"
                               value="<%=amsItemTransH.getCreationDate()%>"
                               class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" readonly
                               value="<%=amsItemTransH.getTransStatusName()%>"
                               class="blueborderGray"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="7"><textarea name="remark" rows="3" cols="" style="width:90%"
                                              class="blueBorder"><%=amsItemTransH.getRemark()%></textarea>
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
        if (!amsItemTransH.getTransStatus().equals(DictConstant.IN_PROCESS) && amsItemTransH.getCreatedBy()==user.getUserId()) {
    %>
    <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
    <img src="/images/button/deleteLine.gif" alt="删除行"
         onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_save(1);">
    <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_submit();">
    <%
        }else if (amsItemTransH.getTransStatus().equals(DictConstant.IN_PROCESS)) {
    %>
    <img src="/images/button/confirm.gif" alt="确认" id="img3" onClick="do_confirm();">
    <img src="/images/button/noPass.gif" alt="退回" id="img4" onClick="do_untread();">
    <%
        }
    %>
    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
</legend>

<script type="text/javascript">
    var columnArr = new Array("checkbox", "调拨单号", "物料编码", "设备名称", "规格型号", "预计归还日期", "借用数量", "已归还数量", "归还数量(正常)", "归还数量(坏件)");
    var widthArr = new Array("2%", "15%", "10%", "15%", "18%", "8%", "7%", "7%", "9%", "9%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
        <%if (rows == null || rows.isEmpty()) {%>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">

            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="15%"><input type="text" name="batchNo" id="batchNo0"
                                   value=""
                                   readonly class="noborderGray"
                                   style="width:100%;">
            </td>
            <td width="10%"><input type="text" name="barcode" id="barcode0"
                                   value=""
                                   readonly class="noborderGray"
                                   style="width:100%;">
            </td>
            <td width="15%" name="itemName" id="itemName0"></td>
            <td width="18%" name="itemSpec" id="itemSpec0"></td>
            <td width="8%" name="respectReturnDate" id="respectReturnDate0" align="center"></td>
            <td width="7%" >
                <input type="text" name="quantity" id="quantity0" value="" readonly
                                                 style="width:100%;text-align:right;border:none">
            </td>
            <td width="7%">
                <input type="text" name="returnQty" id="returnQty0" value="" readonly
                                                 style="width:100%;text-align:right;border:none">
            </td>

            <td width="9%" align="center"><input type="text" name="normalQuantity" id="normalQuantity0"
                                                 onblur="checkQty(this,1);"
                                                 value="0" class="noborderYellow"
                                                 style="width:100%;text-align:right">
            </td>
            <td width="9%" align="center"><input type="text" name="badQuantity" id="badQuantity0"
                                                 onblur="checkQty(this,2);"
                                                 value="0" class="noborderYellow"
                                                 style="width:100%;text-align:right">
            </td>
            <td style="display:none"><input type="hidden" name="lineId" id="lineId0"
                                            value=""><input type="hidden" name="itemCode" id="itemCode0"
                                                            value=""><input type="hidden" name="storageId"
                                                                            id="storageId0" value="">
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
            <td width="15%"><input type="text" name="batchNo" id="batchNo<%=i%>"
                                   value="<%=row.getValue("BATCH_NO")%>"
                                   readonly class="noborderGray"
                                   style="width:100%;">
            </td>
            <td width="10%"><input type="text" name="barcode"
                                   id="barcode<%=i%>"
                                   value="<%=row.getValue("BARCODE")%>"
                                   readonly class="noborderGray"
                                   style="width:100%">
            </td>
            <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="18%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="8%" name="respectReturnDate" id="respectReturnDate<%=i%>"
                align="center"><%=row.getValue("RESPECT_RETURN_DATE")%>
            </td>
            <td width="7%">
                <input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" readonly
                                                 style="width:100%;text-align:right;border:none">
            </td>
            <td width="7%">
                <input type="text" name="returnQty" id="returnQty<%=i%>" value="<%=row.getValue("RETURN_QTY")%>" readonly
                                                 style="width:100%;text-align:right;border:none">
            </td>
            <td width="9%" align="center"><input type="text" name="normalQuantity" id="normalQuantity<%=i%>"
                                                 onblur="checkQty(this,1);"
                                                 value="<%=row.getValue("NORMAL_QUANTITY")%>" class="noborderYellow"
                                                 style="width:100%;text-align:right">
            </td>
            <td width="9%" align="center"><input type="text" name="badQuantity" id="badQuantity<%=i%>"
                                                 onblur="checkQty(this,2);"
                                                 value="<%=row.getValue("BAD_QUANTITY")%>" class="noborderYellow"
                                                 style="width:100%;text-align:right">
            </td>
            <td style="display:none"><input type="hidden" name="lineId" id="lineId<%=i%>"
                                            value="<%=row.getValue("LINE_ID")%>"><input type="hidden"
                                                                                        name="itemCode"
                                                                                        id="itemCode<%=i%>"
                                                                                        value="<%=row.getValue("ITEM_CODE")%>"><input
                    type="hidden" name="storageId"
                    id="storageId<%=i%>" value="<%=row.getValue("STORAGE_ID")%>">
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</fieldset>
<div id="showMsg" style="color:red">
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</div>
</form>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script type="text/javascript">
    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.SPARE_LOAN_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            document.mainForm.toObjectName.value = projects[0].workorderObjectName;
            document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
            document.mainForm.toOrganizationId.value = projects[0].organizationId;
            document.mainForm.toOrganizationName.value = projects[0].company;
        }
    }
    function do_SelectItem() {
        var toObjectNo = document.mainForm.toObjectNo.value;
        if (toObjectNo == "") {
            alert("请先选择借出公司和仓库！");
            return;
        }

        var items = getLookUpValues("<%=LookUpConstant.SPARE_RETURN%>", 60, 33, "objectNo=" + toObjectNo);
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
            var itemValue = itemObj.storageId;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
//                alert("trObj.cells[10].childNodes[0].id=" + trObj.cells[10].childNodes[0].id)
//                alert("trObj.cells[10].childNodes[1].id=" + trObj.cells[10].childNodes[1].id)
//                alert("trObj.cells[10].childNodes[2].id=" + trObj.cells[10].childNodes[2].id)
                rowValue = trObj.cells[10].childNodes[2].value;
//                alert(rowValue)
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
    function do_submit() {
        //检查输入数据是否合法,是否归还数量大于借出数量
        if(validateData()){
            document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
            document.mainForm.submit();
        }
    }

    function validateData(){
        var isvalid = false;
        var normalQuantityObjs = document.getElementsByName("normalQuantity");
        if(!normalQuantityObjs || !normalQuantityObjs.length || normalQuantityObjs.length == 0){
            alert("请选择要归还的设备！");
        }else{
            var tempId = -1;
//            var quantity;
            var normalQuantity;
            var badQuantity;
//            var returnQty;
            for(var j = 0; j < normalQuantityObjs.length; j++){
                tempId = normalQuantityObjs[j].id.substring(14,normalQuantityObjs[j].id.length);
                normalQuantity = Number(normalQuantityObjs[j].value)
//                quantity = Number(document.getElementById("quantity"+tempId).value);
                badQuantity = Number(document.getElementById("badQuantity"+tempId).value);
//                returnQty = Number(document.getElementById("returnQty"+tempId).value);
                if(normalQuantity + badQuantity == 0 ){
                    alert("正常与坏件的归还数量之和不能全为零，请重新输入！");
                    normalQuantityObjs[j].value = "";
                    normalQuantityObjs[j].focus();
                    break;
                }
            }
            isvalid = true;
        }
        return isvalid;
    }

    function checkQty(obj, type) {
        var val = obj.value;
        if(val == ""){
            obj.value = 0;
        }else if (isNaN(val)) {
            alert("请输入数字！");
            obj.value = "";
            obj.focus();
        }else if(Number(val) < 0){
            alert("请输入不小于0的整数！");
            obj.value = "";
            obj.focus();
        }else{
            var tempId = obj.id.substring(14,obj.id.length);
            if(type == 2) tempId = obj.id.substring(11,obj.id.length);
            var normalQuantity = Number(document.getElementById("normalQuantity"+tempId).value);
            var badQuantity = Number(document.getElementById("badQuantity"+tempId).value);
            var returnQty = Number(document.getElementById("returnQty"+tempId).value);
            var quantity = Number(document.getElementById("quantity"+tempId).value);
            if(normalQuantity + badQuantity > quantity - returnQty){
                alert("正常与坏件的归还数量之和不能大于未归还数量，请重新输入！");
                obj.value = "";
                obj.focus();
            }
        }

    }

    function do_confirm(){
        document.mainForm.act.value = "<%=WebActionConstant.RECEIVE_ACTION%>";
        document.mainForm.submit();
    }

    function do_untread(){
        document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        document.mainForm.submit();
    }

</script>
</html>