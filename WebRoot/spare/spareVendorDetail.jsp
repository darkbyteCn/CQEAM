<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.ams.spare.repair.dto.AmsVendorInfoDTO"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>备件厂商维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
<style type="text/css">
    <!--
    .STYLE1 {
        color: #0033FF
    }
    -->
</style>
</head>
<body>
 <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsVendorInfoDTO dto = (AmsVendorInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    String action = dto.getAct();
%>
<form name="mainFrm" method="POST">
<fieldset style="margin-left:0;height:450px">
<legend><span class="STYLE1">备件厂商维护</span></legend>
    <table width="50%" align="center">
        <tr>
            <td width="20%" align="right" height="22">厂商代码：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="vendorId" class="readonlyInput" readonly style="width:100%" value="<%=dto.getVendorId()%>">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">厂商名称：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="vendorName" class="noEmptyInput" style="width:100%" value="<%=StrUtil.nullToString(dto.getVendorName())%>">
            </td>
        </tr>
        <tr>
            <td width="50%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveData(); return false;">&nbsp;
<%
    if (!action.equals(WebActionConstant.NEW_ACTION)) {
%>
                <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_DeleteData(); return false;">&nbsp;
<%
    }
%>
                <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_Back(); return false;"></td>
        </tr>
    </table>
</fieldset>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="isExist">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script>
function do_SaveData() {
    var fieldNames = "vendorName";
    var fieldLabels = "厂商";
    var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
    do_verifyworkNo();
    if (isValid) {
        if (mainFrm.isExist.value == "Y") {
            alert("该厂商已存在！");
            return;
        }
        var action = "<%=WebActionConstant.CREATE_ACTION%>";
        var newAction = "<%=WebActionConstant.NEW_ACTION%>";
        if (newAction != "<%=action%>") {
            action = "<%=WebActionConstant.UPDATE_ACTION%>";
        }
        mainFrm.act.value = action;
		mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet";
		mainFrm.submit();
	}
}

function do_DeleteData() {
    var vendorId = mainFrm.vendorId.value;
    if (confirm("确认删除吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
        mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet?vendorId=" + vendorId;
        mainFrm.submit();
    }
}


function do_Back() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet";
	mainFrm.submit();
}

var xmlHttp;
function do_verifyworkNo() {
    var url = "";
    createXMLHttpRequest();
    url = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet?act=verifyworkNo&vendorName=" + document.mainFrm.vendorName.value;
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
            } else {
                document.mainFrm.isExist.value = 'N';
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
</script>