<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.part.dto.AmsSpareCategoryDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>备件设备分类维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<%
    AmsSpareCategoryDTO partNoDTO = (AmsSpareCategoryDTO) request.getAttribute(WebAttrConstant.SPARE_CATEGORY_DTO);
%>
<script type="text/javascript">
    printTitleBar("备件设备分类维护")
</script>
<body leftmargin="0">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form action="" method="post" name="mainFrm">
    <input type="hidden" name="act">
    <input type="hidden" name="isExist">
    <input name="New" type="hidden" id="New" value="<%=partNoDTO.getNew()%>">
    <input name="barcode" type="hidden" value="<%=partNoDTO.getBarcode()%>">
    <table width="100%" border="0">
        <%--<tr align="right" id="barcodeNo11" style="color:red;visibility:hidden">对不起，该部件号已存在!</tr>--%>
        <tr>
            <td align="right" width="20%">设备名称：</td>
            <td width="50%"><input name="itemName" type="text" value="<%=partNoDTO.getItemName()%>" style="width:60%" class="input_style1">&nbsp;<font color="red">*</font>  </td>
        </tr>
        <tr>
            <td align="right">设备型号：</td>
            <td><input name="itemSpec" type="text" value="<%=partNoDTO.getItemSpec()%>" style="width:60%" class="input_style1">&nbsp;<font color="red">*</font> </td>
        </tr>
        <tr>
            <td align="right">设备类型：</td>
            <td><input name="itemCategory" type="text" value="<%=partNoDTO.getItemCategory()%>" style="width:60%" class="input_style1">&nbsp;<font color="red">*</font> </td>
        </tr>
        <tr>
            <td align="right">用途：</td>
            <td><input name="spareUsage" type="text" value="<%=partNoDTO.getSpareUsage()%>" style="width:60%" class="input_style1">&nbsp;<font color="red">*</font> </td>
        </tr>
        <tr>
            <td align="right">厂商：</td>
            <td>
                <select name="vendorId" style="width:60%" class="select_style1"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select>&nbsp;<font color="red">*</font> 
            </td>
        </tr>
        <tr>
            <td align="right">单位：</td>
            <td>
                <select name="itemUnit" style="width:60%" class="select_style1"><%=request.getAttribute(WebAttrConstant.ITEM_UNIT_OPTION)%></select>&nbsp;<font color="red">*</font> 
            </td>
        </tr>
        <tr>
            <td align="right">备注：</td>
            <td>
                <textarea name="remark" type="areatext" id="remark" value="<%=partNoDTO.getRemark()%>" style="width:60%"><%=partNoDTO.getRemark()%></textarea>
            </td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" style="cursor:'hand'" onClick="do_submit();">
                <img src="/images/eam_images/back.jpg" onClick="do_back();" alt="返回">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
function do_back() {
    mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
    mainFrm.submit();
}

function do_submit() {
    var fieldNames = "itemName;itemSpec;itemCategory;spareUsage;vendorId";
    var fieldLabels = "设备名称;设备型号;设备类型;用途;厂商";
    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
        do_verifyworkNo();
        if (mainFrm.New.value == "true") {
            if (mainFrm.isExist.value == "Y") {
                alert("备件设备分类已存在！");
                return;
            }
            mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        } else {
            if ((mainFrm.itemName.value == "<%=partNoDTO.getItemName()%>" && mainFrm.itemSpec.value == "<%=partNoDTO.getItemSpec()%>" && mainFrm.itemCategory.value == "<%=partNoDTO.getItemCategory()%>" && mainFrm.spareUsage.value == "<%=partNoDTO.getSpareUsage()%>" && mainFrm.vendorId.value == "<%=partNoDTO.getVendorId()%>") && (mainFrm.itemUnit.value != "<%=partNoDTO.getItemUnit()%>" || mainFrm.remark.value != "<%=partNoDTO.getRemark()%>")) {
                mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            } else {
                if (mainFrm.isExist.value == "Y") {
                    alert("备件设备分类已存在！");
                    return;
                }
                mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
        }
        mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
        mainFrm.submit();
    }
}

var xmlHttp;
function do_verifyworkNo() {
    var url = "";
    createXMLHttpRequest();
    url = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet?act=verifyworkNo&itemName=" + document.mainFrm.itemName.value + "&itemSpec=" + document.mainFrm.itemSpec.value + "&itemCategory=" + document.mainFrm.itemCategory.value + "&spareUsage=" + document.mainFrm.spareUsage.value + "&vendorId=" + document.mainFrm.vendorId.value;
    xmlHttp.onreadystatechange = handleReadyStateChange1;
    xmlHttp.open("post", url, false);
    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp.send(null);
}

function createXMLHttpRequest() {//创建XMLHttpRequest对象
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
}

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'Y') {
                document.mainFrm.isExist.value = 'Y';
//                document.getElementById("barcodeNo11").style.visibility = "visible";
            } else {
                document.mainFrm.isExist.value = 'N';
//                document.getElementById("barcodeNo11").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

function do_delete() {
    document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
    document.mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
    document.mainFrm.submit();
}
</script>