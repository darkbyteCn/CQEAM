<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.yj.dto.AmsYjItemDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<HTML>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>装备名称详细信息</title>
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

<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsYjItemDTO amsYjItemDTO = (AmsYjItemDTO) request.getAttribute("AMS_YJ_ITEM");
    boolean isNew = StrUtil.isEmpty(amsYjItemDTO.getItemName());
%>
<script type="text/javascript">
    printTitleBar("装备名称详细信息");
</script>

<form name="mainFrm" action="/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet" method="post">
    <input type="hidden" name="show" value="show">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="itemCode" value="<%=amsYjItemDTO.getItemCode()%>">
    <input type="hidden" name="isExist" >
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="25%" align="right" height="22">装备名称：</td>
            <td width="35%" align="left" height="22">
            <input type="text" name="itemName" size="38" class="input_style1" style="width:95%" value="<%=amsYjItemDTO.getItemName()%>">&nbsp;<font color="red">*</font> </td>
            <td width="25%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="点击保存" onClick="do_Save(); return false;">&nbsp;
                <%
                    if (!amsYjItemDTO.getItemName().equals("")) {
                %>
                <img src="/images/eam_images/disable.jpg" alt="点击失效" onClick="do_Delete(); return false;">&nbsp;
                <img src="/images/eam_images/enable.jpg" alt="点击生效" onClick="do_Enable(); return false;">&nbsp;
                <%
                    }
                %>
                <img src="/images/eam_images/back.jpg" alt="点击取消" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
</form>
</body>
</html>
<script>
    function do_Save() {
        var fieldNames = "itemName";
        var fieldLabels = "装备名称";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);

        do_verifyItemName();

        if (isValid) {
             if (mainFrm.isExist.value == "Y") {
            alert("该装备名称已存在！");
            return;
        }
            var action = "<%=WebActionConstant.CREATE_ACTION%>";
        <%if(isNew){%>
            document.mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        <%}else{%>
            document.mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        <%}%>
            document.mainFrm.submit();
        }
    }

    function do_Delete() {
        var itemCode = document.mainFrm.itemCode.value;
        if (confirm("确认失效该装备名称吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?itemCode=" + itemCode;
            document.mainFrm.submit();
        }
    }

    function do_Enable() {
        var itemCode = document.mainFrm.itemCode.value;
        if (confirm("确认生效该装备名称吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=AMSActionConstant.INURE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?itemCode=" + itemCode;
            document.mainFrm.submit();
        }
    }

    function do_Back() {
        document.mainFrm.itemCode.value = "";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet";
        document.mainFrm.submit();
    }

    var xmlHttp;
function do_verifyItemName() {
    var url = "";
    createXMLHttpRequest();
    url = "/servlet/com.sino.ams.yj.servlet.AmsYjItemServlet?act=verifyItemName&itemName=" + document.mainFrm.itemName.value;
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

</SCRIPT>