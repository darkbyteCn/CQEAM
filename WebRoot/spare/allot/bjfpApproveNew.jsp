<%--
  Created by HERRY.
  Date: 2008-2-18
  Time: 14:31:20
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotDDto" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<head><title>备件分配审批(新)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>

</head>

<body topMargin=0 leftMargin=0>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.ALLOT_H_DTO);
    if (amsItemTransH == null) {
        amsItemTransH = new AmsBjsAllotHDTO();
    }
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);

    RequestParser rp = new RequestParser();
    rp.transData(request);
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.ALLOT_D_DTO);
    String sectionRight = rp.getParameter("sectionRight");
    String divHeight = "500";
    if (sectionRight.equals("OUT")) {
        divHeight = "200";
    }
    String itemCodes = "";
%>
<form action="/servlet/com.sino.ams.spare.allot.servlet.BjfpApproveServlet" name="mainForm" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1" width="100%">
    <tr height="24">
        <td>
            <table width="100%" id="table2" cellspacing="1" border="0">
                <tr height="22">
                    <td align="right">单据号：</td>
                    <td width="20%"><input type="text" class="detailHeader" name="transNo" readonly style="width:100%;border:none"
                               value="<%=amsItemTransH.getTransNo()%>">
                    </td>
                    <td align="right">分配地市：</td>
                    <td><%=amsItemTransH.getToOrganizationName()%>
                    </td>
                </tr>
                <tr height="24">
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
    <legend>
        <img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">
        <img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve2();">
        <img src="/images/button/viewFlow.gif" alt="查阅流程" onClick="viewFlow(); return false;">
        <img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <%--<table id="itemTable" class="headertable" width="100%" border="1">
        <tr>
            <td width="5%" align="center"><input type="checkbox"  name="mainCheck" value=""
                                                 class="headCheckbox"
                                                 onclick="checkAll('mainCheck','subCheck')"></td>

            <td width="30%" align="center">设备名称</td>
            <td width="35%" align="center">规格型号</td>
            <td width="15%" align="center">现有数量</td>
            <td width="15%" align="center">分配数量</td>
            <td style="display:none">

            </td>
        </tr>
    </table>--%>
    <script type="text/javascript">
        var columnArr = new Array("", "设备名称", "规格型号", "现有数量", "总分配数量");
        var widthArr = new Array("3%", "30%", "35%", "15%", "15%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:<%=divHeight%>px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
            <%
                if (set != null && !set.isEmpty()) {

                    for (int i = 0; i < set.getSize(); i++) {
                        AmsBjsAllotDDto dto1 = (AmsBjsAllotDDto) set.getDTO(i);
                        itemCodes += dto1.getItemCode() + ",";
            %>
            <tr height="20px" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'" onclick="this.cells[0].childNodes[0].checked=true;">
                <td width="3%" align="center"><input type="radio" name="selectItemCode"
                                                     value="<%=dto1.getItemCode()%>:<%=dto1.getQuantity()%>">
                </td>
                <td width="30%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
                </td>
                <td width="35%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
                </td>
                <td align=center width="15%"><input name="itemAmount" class="noborderGray" readonly
                                                    style="width:100%;text-align:right"
                                                    id="itemAmount<%=i%>" value="<%=dto1.getItemAmount()%>">
                </td>
                <td align=center width="15%"><input type="text" name="quantity" id="quantity<%=i%>"
                                                    class="noborderYellow"
                                                    style="width:100%;text-align:right" value="<%=dto1.getQuantity()%>">
                </td>
                <td style="display:none">
                    <%--<input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=dto1.getItemCode()%>">--%><%--<input type="radio" name="tempRadio" id="tempRadio<%=i%>" value="<%=i%>">--%>
                </td>
                <td style="display:none">
                    <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=dto1.getDetailId()%>">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<%
    if (sectionRight.equals("OUT")) {
%>
<fieldset>
    <legend>选择出库设备
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/button/deleteLine.gif" alt="删除行"
             onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
        <%--<img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve(2);">--%>
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("checkbox", "部件号", "设备名称", "规格型号", "现有量", "分配数量");
        var widthArr = new Array("2%", "12%", "15%", "25%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:250px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode" id="barcode0"
                                                                            value=""
                                                                            readonly class="noborderGray"
                                                                            style="width:100%;text-align:center">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="25%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="onhandQty" id="onhandQty0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="allocateQty"
                                                                           id="allocateQty0"
                                                                           value=""
                                                                           class="noborderYellow"
                                                                           onblur="checkQty(this);" onfocus="selectItem(this);"
                                                                           style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                    <input type="hidden" name="objectNo" id="objectNo0" value="">
                </td>
            </tr>

        </table>
    </div>
</fieldset>
<%
    }
%>
<input type="hidden" name="act" value="">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="value1" id="value1" value="">
<input type="hidden" name="checkedIndex" value="">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="detailIds" value="">
<input type="hidden" name="flag" value="">
</form>
</body>
</html>
<script type="text/javascript">

function isItemExist(itemObj) {
    var retVal = false;
    var tabObj = document.getElementById("mtlTable");
    if (tabObj.rows) {
        var trObjs = tabObj.rows;
        var trCount = trObjs.length;
        var trObj = null;
        var itemValue = itemObj.itemCode;
        var rowValue = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[5].childNodes[0].value;
            if (itemValue == rowValue) {
                retVal = true;
            }
        }
    }
    return retVal;
}
function do_Approve(flag) {
    document.mainForm.flag.value = flag;
    var paramObj = new Object();
    paramObj.orgId = "<%=user.getOrganizationId()%>";
    paramObj.useId = "<%=user.getUserId()%>";
    paramObj.groupId = "<%=user.getCurrGroupId()%>";
    paramObj.procdureName = "备件分配流程";
    paramObj.flowCode = "";
    paramObj.submitH = "submitH()";
    assign(paramObj);
}
function do_Approve2() {
    mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
    addApproveContent();
    mainForm.submit();
}
function submitH() {
    var flag = document.mainForm.flag.value;
    var sessionR = "<%=sectionRight%>";
    var actVal = "";
    switch (flag) {
        case 1: actVal = "<%=WebActionConstant.APPROVE_ACTION%>"; break;
        case 2: actVal = "<%=WebActionConstant.REJECT_ACTION%>"; break;
        case 3: actVal = "<%=WebActionConstant.RECEIVE_ACTION%>"; break;
        default :actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
    }
    mainForm.act.value = actVal;
    mainForm.submit();
    //        mainForm.action = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotouServlet";
    //        document.mainForm.act.value = actVal;
    //        document.mainForm.submit();
}
function do_SelectItem() {
    var selectedItemCode = getRadioValue("selectItemCode");
    if (selectedItemCode == null || selectedItemCode == "") {
        alert("请先选择一种设备名称型号！");
        return;
    }
    var ic = selectedItemCode.split(":");
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJSL_ITEM_INFO2%>&itemCodes=" + ic[0] + "&organizationId=<%=amsItemTransH.getFromOrganizationId()%>";
    var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
    /*   window.open(url);*/
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
function do_delete() {
    var tab = document.getElementById("mtlTable");
    //将删除的有LINE_ID的行的LINE_ID加到lineIds上
    var rowCount = tab.rows.length;
    if (rowCount > 0) {
        var boxArr = getCheckedBox("subCheck");
        var chkCount = boxArr.length;
        if (chkCount > 0) {
            var chkObj = null;
            for (var i = 0; i < chkCount; i++) {
                chkObj = boxArr[i];
                var checkboxId = chkObj.id;
                var id = checkboxId.substring(8, checkboxId.length);
                var detailId = document.getElementById("detailId" + id).value;
                if (detailId != "") {
                    mainForm.detailIds.value += detailId + ",";
                }
            }
        }
    }
    //        alert("mainForm.detailIds.value="+mainForm.detailIds.value)

    deleteTableRow(tab, 'subCheck');

}
function checkValues() {
    var retVal = true;
    var tab = document.getElementById("mtlTable");
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        retVal = false;
    } else {
        var qtys = document.getElementsByName("allotQty");
        for (var i = 0; i < qtys.length; i++) {
            if (qtys[i].value == "") {
                alert("请输入数量！");
                qtys[i].focus();
                retVal = false;
                break;
            }
        }
    }

    return retVal;
}
function init() {

}
function selectItem(obj){
    var id = obj.id.substring(11, obj.id.length);
    var itemCode = document.getElementById("itemCode" + id).value;
    var allRadios = document.getElementsByName("selectItemCode");
    if(allRadios.length){
         for(var i=0;i<allRadios.length;i++){
             var radioItem = allRadios[i];
             var radioValue = radioItem.value;
             var splitValue = radioValue.split(":");
             var tempItemCode = splitValue[0];
             if(tempItemCode == itemCode){
                 radioItem.checked = true;
                 break;
             }
         }
    }else {
        allRadios.checked = true;
    }
}
function checkQty(obj) {
    var quantity;
    //该设备型号分配审批通过的数量
    var tempQty = 0;
    var selectedItemCode = getRadioValue("selectItemCode");
    var si = selectedItemCode.split(":");
    quantity = si[1];
//    alert("quantity=" + quantity);
    var itemCode = document.getElementById("itemCode" + obj.id.substring(11, obj.id.length)).value;
    var allocateQty = document.getElementById("allocateQty" + obj.id.substring(11, obj.id.length)).value;
    var itemCodes = document.getElementsByName("itemCode");
    var tempId;
    var tempObj;
    if (itemCodes.length) {
        tempObj = itemCodes[0];
        for (var i = 0; i < itemCodes.length; i++) {
            var item = itemCodes[i];
            if (item.value == itemCode) {
                tempId = item.id.substring(8, item.id.length);
                tempQty += Number(document.getElementById("allocateQty" + tempId).value);
                //                   alert("tempQty="+tempQty);
            }
        }
    } else {
        tempId = itemCodes.id.substring(8, item.id.length);
        //            alert("tempId="+tempId);
        tempQty += Number(document.getElementById("allocateQty" + tempId).value);
        //                   alert("tempQty="+tempQty);
        tempObj = itemCodes;
    }
//    alert("tempQty=" + tempQty)
    if (tempQty > Number(quantity)) {
        alert("每一种设备的分配数量之和不能超过总分配数量，请重新输入！");
        document.getElementById("allocateQty" + tempId).focus();
    }
}
</script>