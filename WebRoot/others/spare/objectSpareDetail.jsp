<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.web.item.dto.EtsItemFixfeeDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-27
  Time: 11:45:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件维修管理详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("备件维修管理详细信息")
</script>
<jsp:include page="/message/MessageProcess"/>
<body>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    EtsItemFixfeeDTO dto = (EtsItemFixfeeDTO) request.getAttribute(WebAttrConstant.ETS_WORKORDER_ITEM_DTO);
    String action = parser.getParameter("act");
%>
<form action="/servlet/com.sino.ams.web.item.servlet.EtsItemFixfeeServlet" name="mainForm" method="post">
    <table border="0" width="100%">
        <tr>
            <td align="right" width="5%">备件名称：</td>
            <td width="15%"><input name="itemName" style="width:45%" id="itemName" onblur="do_isExist()"
                                  readonly class="readonlyInput"    onclick="do_selectName();"  title="点击选择备件名称"
                                   value="<%=dto.getItemName()%>"><a  href="#"
                    class="linka" style="cursor:'hand'" onclick="do_selectName();" title="点击选择备件名称">[…]</a></td>
        </tr>
        <tr>
            <td align="right" width="5%">备注：</td>
            <td width="35%"><textarea rows="10" cols="63" name="remark"><%=dto.getRemark()%></textarea></td>
        </tr>
        <tr>
            <td align="right" width="5%">维修费：</td>
            <td><input name="amount" class="noEmptyInput" type="text" value="<%=dto.getAmount()%>" onblur="do_check1();"style="width:46%">
            </td>
        </tr>
        <tr>
            <td align="right" width="5%">维修时间：</td>
            <td width="15%"><input type="text" name="fixDate" value="<%=dto.getFixDate()%>" style="width:46%"
                                   title="点击选择日期"   readonly class="readonlyInput"
                                   onclick="gfPop.fPopCalendar(fixDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(fixDate)"></td>

        </tr>
        <tr>
            <td align="right"></td>
            <td align="left">

                <img src="/images/eam_images/save.jpg" alt="保存"
                     onClick="do_savePlan(); return false;">

                <%-- <img src="/images/eam_images/revoke.jpg" alt="撤消计划" onclick="do_back();return false">--%>
                <img src="/images/eam_images/back.jpg" alt="返回"
                     onclick="do_concel();return false;"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="barcode" id="barcode" value="<%=dto.getBarcode()%>">
    <input type="hidden" name="systemId" value="<%=dto.getSystemId()%>">
    <input type="hidden" name="isExist">
</form>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</body>
</html>
<script type="text/javascript">
    function do_concel() {
        with (mainForm) {
            window.close();
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            submit();
        }
    }
    function do_savePlan() {
        var isValid = false;
        var fieldNames = "itemName;amount;fixDate";
        var fieldLabels = "备件名称; 维修费; 维修时间";
        var validateType = EMPTY_VALIDATE;
        isValid = formValidate(fieldNames, fieldLabels, validateType);
        if (isValid) {
            fieldNames = "amount";
            fieldLabels = "维修费";
            validateType = POSITIVE_VALIDATE;
            isValid = formValidate(fieldNames, fieldLabels, validateType);

            if ((isValid) && (mainForm.amount.value > 0)) {
                with (mainForm) {
                    if (systemId.value == "") {
                        act.value = "<%=WebActionConstant.CREATE_ACTION%>";
                    } else {
                        act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                    }
                    submit();
                }
            }
        }
    }
    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BEIJIAN_ITEM%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var users = window.showModalDialog(url, null, popscript);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
    var xmlHttp;
    function do_isExist() {
        var url = "";
        //    var workorderObjectCode = document.form1.workorderObjectCode.value;
        createXMLHttpRequest();
        if (document.mainForm.barcode.value) {
            url = "/servlet/com.sino.ams.web.item.servlet.EtsItemFixfeeServlet?act=barcode&barcode=" + document.mainForm.barcode.value;
            xmlHttp.onreadystatechange = handleReadyStateChange1;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
    }

    function createXMLHttpRequest() {     //创建XMLHttpRequest对象
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
                    document.mainForm.isExist.value = 'Y';
                    document.getElementById("barcode").style.visibility = "visible"
                    document.mainForm.barcode.style.color = "red";
                    document.mainForm.barcode.focus();
                } else {
                    document.mainForm.isExist.value = 'N';
                    document.getElementById("barcode").style.visibility = "hidden";
                }
            } else {
                alert(xmlHttp.status);
            }
        }
    }
    function do_check1(){
        var fieldNames = "amount";
        var fieldLabels = "维修费";
        if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {

        }
    }
</script>