<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.system.county.dto.EtsCountyDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: zz_jlc
  Date: 2007-12-25
  Time: 15:38:24
  To change this template use File | Settings | File Templates.
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
//    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
//    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
%>
<HTML>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>区县维护详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
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

<body <%--onload="do_Select();"--%>>
<jsp:include page="/message/MessageProcess"/>
<%
    EtsCountyDTO county = (EtsCountyDTO) request.getAttribute(WebAttrConstant.COUNTY_ATTR);
	String countyCodeMis = county.getCountyCodeMis();
	if (countyCodeMis == null) {
		countyCodeMis = "";
	}
%>
<script type="text/javascript">
    printTitleBar("区县维护详细信息")
</script>

<form name="mainFrm" method="POST">
    <input type="hidden" name="show" value="show">
    <input type="hidden" name="countyCode" value="<%=county.getCountyCode()%>">
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="25%" align="right" height="22">MIS代码：</td>
            <td width="35%" align="left" height="22">
            <input type="text" name="countyCodeMis" size="40" class="input_style1" style="width:100%" value="<%=countyCodeMis %>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font color="red">*</font>  </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">MIS_COA_CC代码：</td>
            <td width="35%" align="left" height="22">
            <input type="text" name="countyCodeCoaCc" size="40" class="input_style1"  style="width:100%" value="<%=county.getCountyCodeCoaCc()%>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font color="red">*</font></td>
        </tr>
        <!--<tr>-->
        <!--<td width="25%" align="right" height="22">地市：</td>-->
        <%--<td width="35%"><select style="width:100%" type="text" name="organizationId"><%=organizationId%></select></td>--%>
        <!--<td width="25%" align="left" height="22"></td>-->
        <!--</tr>-->
        <tr>
            <td width="25%" align="right" height="22">区县：</td>
            <td width="35%" align="left" height="22">
              <input type="text" name="countyName" size="40" class="input_style1" style="width:100%" value="<%=county.getCountyName()%>">
            </td>
            <td width="25%" align="left" height="22">&nbsp;<font color="red">*</font></td>
        </tr>

        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_Save(); return false;">&nbsp;
                <%
                    //if (!county.getCountyCode().equals("")) {
                    if ( !("").equals(county.getCountyCode())) {
                %>
                <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_Delete(); return false;">&nbsp;
                <%
                    }
                %>
                <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
    <input type="hidden" name="act" value="">
</form>
</body>
</html>
<script>
    function do_Save() {
        var fieldNames = "countyCodeMis;countyCodeCoaCc;countyName";
        var fieldLabels = "MIS代码;MIS_COA_CC代码;区县";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);

        if (isValid) {
            var action = "<%=WebActionConstant.CREATE_ACTION%>";
            if (mainFrm.countyCode.value != "" && mainFrm.countyCode.value > 0  ) {
                action = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
            mainFrm.act.value = action;
            mainFrm.action = "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet";
            mainFrm.submit();
        }
    }

    function do_Delete() {
        var countyCode = mainFrm.countyCode.value;
        if (confirm("确认删除该区县吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet?countyCode=" + countyCode;
            mainFrm.submit();
        }
    }


    function do_Back() {
        mainFrm.countyCode.value = "";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet";
        mainFrm.submit();
    }

    function do_Select() {

    }

    function do_Check(object) {
        if (object.value != '') {
            if (!isNumber(object.value)) {
                alert("请输入合法数字！");
                object.value = "";
                object.focus();
            } else {
                return true;
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

    function handleReadyStateChange1() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                if (xmlHttp.responseText == 'Y') {
                    document.form1.isExist.value = 'Y';
                    document.getElementById("barcodeNo11").style.visibility = "visible"
                    document.form1.workorderObjectCode.style.color = "red";
                    document.form1.workorderObjectCode.focus();
                } else {
                    document.form1.isExist.value = 'N';
                    document.getElementById("barcodeNo11").style.visibility = "hidden";
                }
            } else {
                alert(xmlHttp.status);
            }
        }
    }

</SCRIPT>