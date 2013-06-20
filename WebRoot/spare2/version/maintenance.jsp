<%@ page import="com.sino.ams.spare2.version.dto.AmsItemVersionDTO" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: V-yuanshuai
  Date: 2007-10-18
  Time: 14:44:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GB2312" language="java" %>

<HTML>
<head>
    <title>版本维护</title>
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

<body leftmargin="0" rightmargin="0" topmargin="0">

<%
    AmsItemVersionDTO aivDTO = (AmsItemVersionDTO) request.getAttribute(WebAttrConstant.AMS_ITEM_VERSION_DTO);
    aivDTO.setBarcode("2531-00013095");
    aivDTO.setItemCode("ItemCode");
    aivDTO.setItemName("ItemName");
    aivDTO.setItemSpec("ItemSpec");
    aivDTO.setItemCategory("ItemCategory");
    RequestParser parser = new RequestParser();
    parser.transData(request);
%>
<form name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("版本维护")
    </script>

    <table border="0" width="100%" style="position:absolute;top:20px">
        <tr>
            <td width="25%" align="right" height="22">标签号：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="barcode" size="40" class="readonlyInput" style="width:100%" readonly
                       value="<%=aivDTO.getBarcode()%>">
            </td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">设备类别：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="itemCategory" size="40" class="readonlyInput" style="width:100%" readonly
                       value="<%=aivDTO.getItemCategory()%>">
            </td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">设备名称：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="itemName" size="40" class="readonlyInput" style="width:100%" readonly
                       value="<%=aivDTO.getItemName()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">规格型号：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="itemSpec" size="40" class="noemptyInput" style="width:100%"
                       value="<%=aivDTO.getItemSpec()%>"></td>
            <td width="25%" align="left" height="22">
                <a href="#" onClick="do_SelectName();" title="点击选择型号" class="linka">[…]</a></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">厂商条码：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="vendorBarcode" size="40"  style="width:100%"
                       value="<%=aivDTO.getVendorBarcode()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">版本号：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="versionNo" size="40"   style="width:100%"
                       value="<%=aivDTO.getVersionNo()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">备&nbsp&nbsp注：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <textArea name="remark" isMultiLine rows=3 cols=1 wrap style=" width:100%"
                          value="<%=aivDTO.getRemark()%>"></textArea></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onclick="do_submit()"></td>
            <%--<img src="/images/eam_images/back.jpg" alt="取消" onclick="do_close();">--%>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="itemCode" value="<%=aivDTO.getItemCode()%>">
    <input type="hidden" name="versionId" value="<%=aivDTO.getVersionId()%>">
</form>
</BODY>

<script type="text/javascript">
    function do_submit() {
        /*   var fieldNames = "itemSpec";
  var fieldLabels = "设备型号";
  if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
      with (mainFrm) {*/
        act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        action = "/servlet/com.sino.ams.spare2.version.servlet.AmsItemVersionServlet";
        submit();
    }
    function do_SelectName() {
     
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }

    /*
    function do_close() {
        window.close();
        opener.location.href = "/servlet/";
    }*/
</SCRIPT>
</HTML>