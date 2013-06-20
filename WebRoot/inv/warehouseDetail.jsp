<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%--
  created by YSB
  Date: 2008-12-10
  Time: 17:33:30
--%>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<HTML>
<head>
    <title>编辑仓库信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<body TEXT="000000" BGCOLOR="FFFFFF" leftmargin=0 topmargin=0 class="STYLE1"
      onload="window.focus();">
<%
    EtsObjectDTO warehouseDTO = (EtsObjectDTO) request.getAttribute(WebAttrConstant.WAREHOUSE_ATTR);
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String warehouseType = (String) request.getAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION);
    String busihouseType = (String) request.getAttribute(WebAttrConstant.BUSIHOUSE_TYPE_OPTION);
    RequestParser parser = new RequestParser();
    parser.transData(request);
%>

<form name="mainFrm" method="post">
    <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
    <table border="0" width="100%" style="position:absolute;top:20px">
        <tr>
            <td width="25%" align="right" height="22">仓库代码：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="workorderObjectCode" size="40" class="noemptyInput" style="width:100%"
                       value="<%=warehouseDTO.getWorkorderObjectCode()%>" onblur="do_comeForm();">
            </td>
            <td width="" align="left" height="22"><div id="tipMsg" style="visibility:hidden"><font color="red">仓库代码已存在</font></div></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">仓库名称：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="workorderObjectName" size="40" class="noemptyInput" style="width:100%"
                       value="<%=warehouseDTO.getWorkorderObjectName()%>">
            </td>
            <td width=" " align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">仓库类别：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <select style="width:100%" type="text" name="objectCategory" id="objectCategory"
                        onchange="checkObjectCategory();"><%=warehouseType%>
                </select></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">所在地点：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="workorderObjectLocation" size="40" class="noemptyInput" style="width:100%"
                       value="<%=warehouseDTO.getWorkorderObjectLocation()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">所属成本中心：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <select style="width:100%" type="text" name="countyCode"><%=countyCode%>
                </select></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
        	<td width="25%" align="right" height="22">业务类型：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <select style="width:100%" type="text" name="businessCategory"><%=busihouseType%>
                </select></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onclick="do_submit()">&nbsp;
                <%--<%
                    if (!warehouseDTO.getWorkorderObjectNo().equals("")) {
                        if (StrUtil.isEmpty(warehouseDTO.getDisableDate())) {
                %>
                <img src="/images/eam_images/disable.jpg" onClick="do_delete(); return false;" alt="失效" id="delete">
                <%
                } else {
                %>
                <img src="/images/button/efficient.gif" onClick="do_efficient(); return false;" alt="生效" id="delete">
                <%
                        }
                    }
                %>--%>
                <img src="/images/eam_images/back.jpg" alt="取消" onclick="do_close();"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="codeExist" id="codeExist" value="N">
    <input type="hidden" name="categoryExist" id="categoryExist" value="N">
    <input type="hidden" name="workorderObjectNo" value="<%=warehouseDTO.getWorkorderObjectNo()%>">
</form>
</BODY>

<script type="text/javascript">
function do_submit() {
    var fieldNames = "workorderObjectCode;workorderObjectName;objectCategory;workorderObjectLocation;countyCode;businessCategory";
    var fieldLabels = "仓库代码;仓库名称;仓库类别;所在地点;所属成本中心;业务类型";
    var objectCode = "<%=warehouseDTO.getWorkorderObjectCode()%>" ;

    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
        mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";

        if (mainFrm.workorderObjectNo.value != "") {
            mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        }
        var codeExist = document.getElementById("codeExist").value;
        if (codeExist == "N") {
            var categoryExist = document.getElementById("categoryExist").value;
            if (categoryExist == "N") {
                mainFrm.action = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet";
                mainFrm.submit();
                opener.location.href = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
            } else {
                alert("该类型仓库已存在");
            }
        } else {
            alert("该仓库代码已存在");
        }
    }
}

function do_close() {
    window.close();
    opener.location.href = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
}

<%--   function do_delete() {
   document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
   document.mainFrm.action = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet";
   document.mainFrm.submit();
}
function do_efficient() {
   document.mainFrm.act.value = "<%=AMSActionConstant.INURE_ACTION%>";
   document.mainFrm.action = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet";
   document.mainFrm.submit();
} --%>

// ------------------------------------------------------------------------
var xmlHttp;

//-- checkObjectCode
function checkObjectCode() {

    var url = "";
    var objCode = document.getElementById("workorderObjectCode").value;
    xmlHttp = createXMLHttpRequest();
    if (objCode != "") {
        url = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet?act=CHECK_CODE_ACTION&workorderObjectCode=" + objCode;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}
//-- checkObjectCategory
function checkObjectCategory() {

    var url = "";
    var objCategory = document.getElementById("objectCategory").value;
    xmlHttp = createXMLHttpRequest();
    if (objCategory != "") {
        url = "/servlet/com.sino.ams.inv.common.servlet.EtsObjectServlet?act=CHECK_CATEGORY_ACTION&objectCategory=" + objCategory;
        xmlHttp.onreadystatechange = handleReadyStateChange2;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

//checkCode
function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            if (resText == '<%=WebAttrConstant.CODE_EXIST%>') {
                document.getElementById("codeExist").value = "Y";
                document.getElementById("tipMsg").style.visibility = "visible";
            } else if (resText == '<%=WebAttrConstant.CODE_NOT_EXIST%>') {
                document.getElementById("codeExist").value = "N";
                document.getElementById("tipMsg").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
//checkCategory
function handleReadyStateChange2() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            var objectCode = "<%=warehouseDTO.getWorkorderObjectCode()%>";
            if ((resText != "") && (resText != objectCode) && (resText != '<%=WebAttrConstant.CATEGORY_NOT_EXIST%>')) {
                document.getElementById("categoryExist").value = "Y";
                alert("该类型仓库已存在");
            } else if (resText == '<%=WebAttrConstant.CATEGORY_NOT_EXIST%>' || (resText == objectCode)) {
                document.getElementById("categoryExist").value = "N";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
function do_comeForm() {
    var oc = "<%=warehouseDTO.getWorkorderObjectCode()%>"  ;
    if (oc == "") {
        checkObjectCode();
    }
}
</SCRIPT>
</HTML>