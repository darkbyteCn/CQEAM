<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.onnet.dto.AmsItemOnNetDTO" %>
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
    <title>设备现网量维护</title>
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
    AmsItemOnNetDTO onNetDTO = (AmsItemOnNetDTO) request.getAttribute(WebAttrConstant.ON_NET_DTO);
%>
<script type="text/javascript">
    printTitleBar("设备现网量维护")
</script>
<body  leftmargin="0">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form action="" method="post" name="mainFrm">
    <input type="hidden" name="act">
    <input type="hidden" name="isExist">
    <table width="60%" align="center">
        <tr align="center" id="barcodeNo11" style="color:red;visibility:hidden">对不起，该地点号已存在!</tr>
        <tr>
            <td width="8%" align="right">数量：</td>
            <td width="40%"><input  name="quantity" type="text" id="quantity" value="<%=onNetDTO.getQuantity()%>" class="input_style1" style="width:80%" onblur="do_verifyQuty();"></td>
        </tr>
        <tr>
            <td align="right">公司：</td>
            <td><input name="orgnizationName" type="text" id="orgnizationName"  readonly value="<%=onNetDTO.getOrgnizationName()%>" class='readonlyInput' style="width:80%"></td>
        </tr>
        <tr>
            <td align="right">设备名称：</td>
            <td>
                <input class='readonlyInput' type="text" name="itemName" readonly style="width:80%" value= "<%=onNetDTO.getItemName()%>">
            </td>
        </tr>
        <tr>
            <td align="right">设备型号：</td>
            <td>
               <input name="itemSpec" readonly class = "readonlyInput"  type="text" id="itemSpec" value="<%=onNetDTO.getItemSpec()%>"  style="width:80%">
            </td>
        </tr>
        <tr>
            <td align="right">设备类型：</td>
            <td>
               <input name="itemCategory" readonly class = "readonlyInput"  type="text" id="itemCategory" value="<%=onNetDTO.getItemCategory()%>"  style="width:80%">
            </td>
        </tr>
        <tr>
            <td align="right">用途：</td>
            <td>
               <input name="spareUsage" readonly class = "readonlyInput"  type="text" id="spareUsage" value="<%=onNetDTO.getSpareUsage()%>"  style="width:80%">
            </td>
        </tr>
        <tr>
            <td align="right">厂商：</td>
            <td>
               <input name="vendorName" type="text" style="width:80%" class="readonlyInput" readonly value ="<%=onNetDTO.getVendorName()%>">
            </td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" style="cursor:'hand'" onClick="do_submit();">
                <img src="/images/eam_images/back.jpg" onClick="do_back();" alt="返回">
            </td>
        </tr>
    </table>
<input name="itemCode" type="hidden" id="itemCode" value="<%=onNetDTO.getItemCode()%>">
<input name="id" type="hidden" id="id" value="<%=onNetDTO.getId()%>">
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
function do_back() {
    mainFrm.action = "com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
    mainFrm.submit();
}

function do_submit() {
    var fieldNames = "quantity";
    var fieldLabels = "数量";
    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
        mainFrm.act.value="<%=WebActionConstant.UPDATE_ACTION%>";
        mainFrm.action="com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
        mainFrm.submit();
     }
}

function choosePrj() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
        dto2Frm(projects[0], "mainFrm");
    }
}


function do_delete() {
    document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
    document.mainFrm.action = "com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
    document.mainFrm.submit();
}

function do_efficient(){
    document.mainFrm.act.value = "<%=AMSActionConstant.INURE_ACTION%>";
    document.mainFrm.action = "com.sino.ams.onnet.servlet.AmsItemOnNetServlet";
    document.mainFrm.submit();
}


function do_verifyQuty() {
    var fieldNames = "quantity";
    var fieldLabels = "数量";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
//        alert("租金必须为正数字！");
    }
}
</script>