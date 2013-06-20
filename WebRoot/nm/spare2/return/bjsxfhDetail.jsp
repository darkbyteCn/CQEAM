<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.nm.spare2.bean.SpareLookUpConstant" %>
<%--
  Created By HERRY
  Date: 2007-11-12
  Time: 9:30:15
--%>
<html>
<head><title>备件送修返还</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
%>
<body topMargin=1 leftMargin=1>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form action="/servlet/com.sino.nm.spare2.returnBj.servlet.BjReturnRepairServlet" name="mainForm" method="post">
<table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellpadding="1" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="24">
                    <td width="10%" align="right">单据号：</td>
                    <td width="15%"><input type="text" name="transNo" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=dto.getTransNo()%>"></td>

                    <td width="10%" align="right">送修仓库：</td>
                    <td width="15%"><input type="text" name="toObjectName" class="blueborderYellow" readonly
                                           value="<%=dto.getToObjectName()%>">
                        <a href="#" id="objectSelecter" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>
                </tr>
                <tr>
                    <td width="10%" align="right">创建人：</td>
                    <td width="15%"><input type="text" name="createdUser" class="blueborderGray" readonly
                                           value="<%=dto.getCreatedUser()%>"></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="15%"><input type="text" name="creationDate" class="blueborderGray" readonly
                                          value="<%=dto.getCreationDate()%>"></td>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="15%"><input type="text" name="transStatusName" class="blueborderGray" readonly
                                           value="<%=dto.getTransStatusName()%>"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="7"><textarea name="remark" rows="3" cols="" style="width:90%"
                                              class="blueBorder"><%=dto.getRemark()%></textarea>
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
            if (!dto.getTransStatus().equals(DictConstant.COMPLETED) && dto.getCreatedBy()==user.getUserId()) {
        %>
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_add();">
        <img src="/images/button/deleteLine.gif" alt="删除行" onClick="do_Delete();">
        <img src="/images/button/save.gif" alt="保存" onClick="do_save()">
        <img src="/images/button/ok.gif" alt="确定" onClick="do_ok()">
        <%
            }
        %>
        <img src="/images/button/close.gif" alt="关闭" onclick="window.close()">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("checkbox","送修单号","物料编码", "设备名称", "规格型号", "送修数量", "已返还数量", "返还数量");
        var widthArr = new Array("3%","15%","13%", "20%", "25%", "8%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style="width:100%;overflow-y:scroll;height:500px">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="1" cellspacing="0">
            <%
                if (set == null || set.isEmpty()) {
            %>
            <tr height=20px style="display:none"
                onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width=3%><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
                <td align=center width="15%">
                    <input class="noborderGray" type="text" name="batchNo" id="batchNo0" style="width:100%"
                           readonly></td>
                <td align=center width="13%">
                    <input class="noborderGray" type="text" name="barcode" id="barcode0" style="width:100%"
                    readonly></td>
                <td width="20%" name="itemName" id="itemName0"></td>
                <td width="25%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%"><input type="text" class="NOborderGray" name="repairQuantity"
                                                    id="repairQuantity0"
                                                    value="" style="width:100%;text-align:right">
                </td>
                <td align=center width="8%"><input type="text" class="NOborderGray" name="returnedQuantity"
                                                    id="returnedQuantity0"
                                                    value="" style="width:100%;text-align:right">
                </td>
                <td align=center width="8%"><input type="text" class="blueborderYellow" name="quantity"
                                                    id="quantity0" onblur="checkQty(this);"
                                                    value="" style="width:100%;text-align:right">
                </td>
                <td style="display:none"><input class="noneinput" type="hidden" name="itemCode" id="itemCode0"></td>
            </tr>
            <%
            } else {
                AmsItemTransLDTO lineDto = null;
                for (int i = 0; i < set.getSize(); i++) {
                    lineDto = (AmsItemTransLDTO) set.getDTO(i);
            %>
            <tr height=20px onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width=3%><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value=""></td>
                <td align=center width="15%">
                    <input class="noborderGray" type="text" name="batchNo" id="batchNo<%=i%>" style="width:100%"
                           readonly value="<%=lineDto.getBatchNo()%>"></td>
                <td align=center width="13%">
                    <input type="text" class="noborderGray" name="barcode" id="barcode<%=i%>" readonly
                           value="<%=lineDto.getBarcode()%>" style="width:100%">
                </td>
                <td width="20%" name="itemName" id="itemName<%=i%>"><%=lineDto.getItemName()%>
                </td>
                <td width="25%" name="itemSpec" id="itemSpec<%=i%>"><%=lineDto.getItemSpec()%>
                </td>
                <td align=center width="8%"><input type="text" class="NOborderGray" name="repairQuantity"
                                                    id="repairQuantity<%=i%>"
                                                    value="<%=lineDto.getRepairQuantity()%>" style="width:100%;text-align:right">
                </td>
                <td align=center width="8%"><input type="text" class="NOborderGray" name="returnedQuantity"
                                                    id="returnedQuantity<%=i%>"
                                                    value="<%=lineDto.getReturnedQuantity()%>" style="width:100%;text-align:right">
                </td>
                <td align=center width="8%"><input type="text" class="blueborderYellow" name="quantity"
                                                    id="quantity<%=i%>"
                                                    value="<%=lineDto.getQuantity()%>" style="width:100%;text-align:right">
                </td>
                <td style="display:none"><input type="hidden" name="itemCode" id="itemCode<%=i%>"
                                                value="<%=lineDto.getItemCode()%>"></td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=DictConstant.BJFH%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
</form>
</body>
</html>
<script type="text/javascript">
    function do_SelectObject() {
    var projects = getSpareLookUpValues("<%=SpareLookUpConstant.OBJECT_NO%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
//        document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
    function do_add() {
        var objectNo = mainForm.toObjectNo.value;
        if(objectNo == ""){
            alert("请先选择一个送修仓库！");
            return
        }
        var lookUpName = "<%=SpareLookUpConstant.BJSXFH_ITEM_INFO%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        //LOOKUP传参数 必须和DTO中一致
        var items = getSpareLookUpValues(lookUpName, dialogWidth, dialogHeight,"objectNo="+objectNo);
        var tab = document.getElementById("mtlTable")
        if (items) {
            document.getElementById("objectSelecter").disabled = true;
            document.getElementById("objectSelecter").onclick = "";
            var user = null;
            for (var i = 0; i < items.length; i++) {
                user = items[i];
                if (!isItemExist(user)) {
                    appendDTORow(tab, user);
                }
            }
        }
    }
    function isItemExist(itemObj) {
        var retVal = false;
        var tabObj = document.getElementById("mtlTable");
        if (tabObj.rows) {
            var trObjs = tabObj.rows;
            var trCount = trObjs.length;
            var trObj = null;
            var itemValue = itemObj.batchNo+itemObj.barcode;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                rowValue = trObj.cells[1].childNodes[0].value + trObj.cells[2].childNodes[0].value;
                if (itemValue == rowValue) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }
    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_KUCUN%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var items = window.showModalDialog(url, null, popscript);
        if (items) {
            var user = null;
            for (var i = 0; i < items.length; i++) {
                mainForm.toObjectName.value = items[0].workorderObjectName;
                mainForm.toObjectNo.value = items[0].workorderObjectNo;
            }
        }
    }
    function do_Delete() {
        var tab = document.getElementById("mtlTable");
        deleteTableRow(tab, 'subCheck');
        var tab2 = document.getElementById("mtlTable");
        var tabRows = tab2.rows;
//        alert("tabRows[0.innerHTML="+tabRows[0].innerHTML);
//        alert("tabRows[0].display="+tabRows[0].style.display);
//        alert("tab2.rows.length="+tabRows.length);
        if(tabRows.length == 0 || tabRows[0].style.display == "none"){
            document.getElementById("objectSelecter").disabled = false;
            document.getElementById("objectSelecter").onclick = do_SelectObject;
        }
    }
    function do_save() {
        if (validateData()) {
            mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>";
            mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            mainForm.submit();
        }
    }
    function do_ok() {
        var value1 = mainForm.toObjectName.value;
        if (value1 == "") {
            alert("请选择送修仓库！");
        } else {
            if (validateData()) {
                mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
                mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                mainForm.submit();
            }
        }
    }
    function validateData() {
        var validate = false;
        var tab = document.getElementById("mtlTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        validate = formValidate("quantity", "数量", EMPTY_VALIDATE);
        if(validate){
            validate = formValidate("quantity", "数量", POSITIVE_INT_VALIDATE);
        }
        return validate;
    }

    function checkQty(obj){
        var lastNoOfId = obj.id.substr(8,obj.id.length); //quantity为8个字符长度
        var quantity = Number(obj.value);
        var repairQuantity = Number(document.getElementById("repairQuantity"+lastNoOfId).value);
        var returnedQuantity = Number(document.getElementById("returnedQuantity"+lastNoOfId).value);
        if(quantity > repairQuantity - returnedQuantity){
            alert("返还数量不得大于送修数量与已返还数量之差，请重新输入！");
            obj.focus();
        }
    }
</script>