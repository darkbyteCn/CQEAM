<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.carddepot.dto.YsCardDefineDTO" %>
<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%--
  created by YS
  Date: 2007-09-27
  Time: 18:23:30
--%>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<HTML>
<head>
    <title>编辑业务用品信息</title>
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

<body TEXT="000000" BGCOLOR="FFFFFF" leftmargin=0 topmargin=0 class="STYLE1"
      onload="window.focus();">
<%
    String depotDefineListOption = request.getAttribute("depotDefineListOption").toString();
    ysCardDefineDTO ysCardDefineDTO = (YsCardDefineDTO) request.getAttribute("YsCardDefineDTO");
    RequestParser parser = new RequestParser();
    parser.transData(request);
%>

<form name="mainFrm" method="post">
    <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
    <table border="0" width="100%" style="position:absolute;top:20px">
        <tr>
            <td width="25%" align="right" height="22">代码：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" id="cardCode" name="cardCode" size="40" class="noemptyInput" style="width:100%"
                       value="<%=ysCardDefineDTO.getCardCode()%>" onblur="do_checkCode()">
            </td>
            <td width="" align="left" height="22"></td>
        </tr><tr>
        <td width="25%" align="right" height="22">名称：</td>
        <td width="50%" align="left" height="22" colspan="3">
            <input type="text" id="cardName" name="cardName" size="40" class="noemptyInput" style="width:100%"
                   value="<%=ysCardDefineDTO.getCardName()%>" onblur="do_checkName()">
        </td>
        <td width="" align="left" height="22"></td>
    </tr>
        <tr>
            <td width="25%" align="right" height="22">单价：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="cardPrice" size="40"  style="width:100%"
                       value="<%=ysCardDefineDTO.getCardPrice()%>">
            </td>
            <td width=" " align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">单位：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="cardUnit" size="40" class="noemptyInput" style="width:100%"
                       value="<%=ysCardDefineDTO.getCardUnit()%>">
            </td>
            <td width=" " align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">所属仓库：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <select name="depotId" style="width :100%" class="noemptyInput"><%=depotDefineListOption%></select>
            </td>
            <td width=" " align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onclick="do_submit()">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="取消" onclick="do_close();"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="cardId" value="<%=ysCardDefineDTO.getCardId()%>">
</form>
</BODY>

<script type="text/javascript">
    function do_submit() {

        var fieldNames = "cardCode;cardName;cardUnit";
        var fieldLabels = "代码;名称;单位";
        if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
            if (mainFrm.cardId.value != "") {
                mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            } else {
                mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            }
            mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet";
            mainFrm.submit();
        }
        opener.location.href = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
    }

    function do_close() {
        window.close();
        opener.location.href = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
    }
    //  检查代码
    function do_checkCode() {

        var url = "";
        var cardCode = document.getElementById("cardCode").value;
        createXMLHttpRequest();
        if (cardCode != "") {
            url = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet?act=CHECK_CODE" +
                  "&cardCode=" + cardCode;
            xmlHttp.onreadystatechange = handleReadyStateChange1;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
    }
    //检查代码
    function handleReadyStateChange1() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                var resText = xmlHttp.responseText;
                if (resText == "Y") {
                    alert("代码已存在！");
                    document.getElementById("cardCode").value = "";
                } else if (resText == "N") {
                } else {
                    alert("error!");
                }
            } else {
                alert(xmlHttp.status);
            }
        }
    }//  检查名称
    function do_checkName() {

        var url = "";
        var cardName = document.getElementById("cardName").value;
        createXMLHttpRequest();
        if (cardName != "") {
            url = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet?act=CHECK_NAME" +
                  "&cardName=" + cardName;
            xmlHttp.onreadystatechange = handleReadyStateChange2;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
    }
    //检查名称
    function handleReadyStateChange2() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                var resText = xmlHttp.responseText;
                if (resText == "Y") {  //Y存在N不存在
                    alert("名称已存在！");
                    document.getElementById("cardName").value = "";
                } else if (resText == "N") {
                } else {
                    alert("error!");
                }
            } else {
                alert(xmlHttp.status);
            }
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
</SCRIPT>
</HTML>