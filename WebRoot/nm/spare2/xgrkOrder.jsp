<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.nm.spare2.bean.SpareLookUpConstant" %>
<%--
  Created by HR
  Date: 2007-10-11
  Time: 14:51:28
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>新购入库单</title>
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
    <script language="javascript" src="/WebLibary/js/arrUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <style type="text/css">
        input {
            width: 90%
        }
    </style>
</head>
<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.AmsItemTransHServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=DictConstant.BJRK%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="9%" align="right">仓库名称：</td>
                    <td width="25%"><input type="text" name="toObjectName" value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueborderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>
                    <td width="9%" align="right">仓库地点：</td>
                    <td width="25%"><input type="text" name="toObjectLocation"
                                           value="<%=amsItemTransH.getToObjectLocation()%>"
                                           class="blueborderGray">
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                               readonly
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:80%"
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
            if (!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED) && amsItemTransH.getCreatedBy()==user.getUserId()) {
        %>
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/button/deleteLine.gif" alt="删除行"
             onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_SavePo(1);">
        <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_SavePo(2);">
        <%
            }
        %>
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <%-- <div style="left:1px;width:100%;overflow-y:scroll" id="headDiv">
        <table id="headerTable" border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center"><input type="checkBox" name="titleCheck"
                                                     onclick="checkAll('titleCheck','subCheck');" class="headCheckbox">
                </td>
                <td width="12%" align="center">设备条码</td>
                <td width="15%" align="center">设备名称</td>
                <td width="15%" align="center">规格型号</td>
            </tr>
        </table>
    </div>--%>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "物料编码", "设备名称", "规格型号", "数量");
        var widthArr = new Array("2%", "12%", "15%", "15%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center"><input type="text" name="barcode" id="barcode0"
                                                      value="" class="noborderGray" readonly
                                                      style="width:100%">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%" align="center"><input type="text" name="quantity" id="quantity0"
                                                     value="" class="blueborderYellow"
                                                     style="width:100%;text-align:right">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value=""><input type="hidden" name="itemCode" id="itemCode0" value="">
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
                <td width="12%" align="center"><input type="text" name="barcode"
                                                      id="barcode<%=i%>"
                                                      value="<%=row.getValue("BARCODE")%>"
                                                      class="noborderGray" readonly
                                                      style="width:100%">
                </td>
                <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%" align="center"><input type="text" name="quantity" id="quantity<%=i%>"
                                                     value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow"
                                                     style="width:100%;text-align:right">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>"><input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

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
    var projects = getSpareLookUpValues("<%=SpareLookUpConstant.OBJECT_NO%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
function do_SelectItem() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJ_ITEM_CATEGORY3%>";
    var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
    /*   window.open(url);*/
    var assets = window.showModalDialog(url, null, popscript);
    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
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
        var itemValue = itemObj.itemCode;
        var rowValue = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[5].childNodes[1].value;
            if (itemValue == rowValue) {
                retVal = true;
            }
        }
    }
    return retVal;
}

function validateData(){
    var validate = false;
    var fieldNames = "quantity";
	var fieldLabels = "数量";
	var validateType = EMPTY_VALIDATE;
	validate = formValidate(fieldNames, fieldLabels, validateType);
    if(validate){
	    validateType = POSITIVE_INT_VALIDATE;
	    validate = formValidate(fieldNames, fieldLabels, validateType);
    }
    return validate;
}
var sflag = null;
function do_SavePo(flag) {
    if (document.mainForm.toObjectNo.value == '') {
        alert("请选择仓库！");
        return false;
    }
    if (flag == 2) {//submit
        var tb = document.getElementById("dataTable");
        if (tb.rows.length == 0) {
            alert("请选择设备！");
            return false;
        }
    }
    if(validateData()){
        sflag = flag;
        if (isBarcodeRepeated()) {
            alert("设备条码不能重复，请确认输入的设备条码！");
        }
    }
}

var xmlHttp = null;
var isRepeated = true;
function isBarcodeRepeated() {
    var repeated = true;
    var barcodes = document.getElementsByName("barcode");
    var barcodeArr = new Array();
    var barcodeArr2 = new Array();
    for (var i = 0; i < barcodes.length; i++) {
        if (barcodes[i].value == "") {
            alert("请输入设备条码！");
            barcodes[i].focus();
            return;
        }
        barcodeArr[i] = barcodes[i].value;
        barcodeArr2[i] = barcodes[i].value;
    }
    //        alert("barcodeArr="+barcodeArr)
    if (barcodeArr.length == barcodeArr2.uniqueStrArr().length) {
        repeated = false;
    }
    if (!repeated) {
        //到数据库验证是否有重复
        var url = "/servlet/com.sino.nm.spare2.servlet.CheckBarcodeServlet";
        xmlHttp = GetXmlHttpObject(checkBarcode);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("barcodes=" + barcodeArr);
    }
    return repeated;
}

function checkBarcode() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var flexValues = new Array();
        var descriptions = new Array();
        var resText = xmlHttp.responseText;
        if (resText == "ERROR") {
            alert(resText);
        } else if (resText == "OK") {
            isRepeated = false;
            if (sflag == 1) {
                document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            } else {
                document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
            }
            document.mainForm.submit();
        } else {
            var resArray = resText.parseJSON();
            if (resArray.length > 0) {
                var tabObj = document.getElementById("dataTable");
                var retStr;
                for (var i = 0; i < resArray.length; i++) {
                    retStr = resArray[i];
                    if (retStr == 1) {
                        tabObj.rows[i].cells[1].childNodes[0].style.color = "red";

                    }
                }
                alert("标记为红色的设备条码已存在，请修改！");
            }
        }
        xmlHttp = null;
    }
}
</script>
</html>