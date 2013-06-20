<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: zz_jlc
  Date: 2007-12-25
  Time: 15:38:00
  Function:区县维护
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>区县维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css"> 


    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>


</head>

<body onkeydown="autoExeFunction('do_search()')">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);       //查询结果接收(固定写法)
    Row row = null;
    String countyCodeMis = parser.getParameter("countyCodeMis");
    String countyCodeCoaCc = parser.getParameter("countyCodeCoaCc");
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
%>
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet">
    <script type="text/javascript">
        printTitleBar("区县维护")
    </script>
    <table width="100%" border="0" >
        <tr>
            <td align="right" width="4%">地市：</td>
            <td width="10%">
             <select  class="select_style1" style="width:100%" type="text" name="organizationId"  onchange="getCountyOption();"><%=organizationId%></select>
            </td>
            <td align="right" width="4%">区县：</td>
            <td width="19%">
             <div id="countyCode1">
               <select class="select_style1" style="width:100%" type="text"  name="countyCode"><%=countyCode%></select>
             </div>
            </td>
            <td width="6%" align="right">MIS代码：</td>
            <td width="8%"><input class="input_style1"  style="width:100%" type="text" name="countyCodeMis" value="<%=countyCodeMis%>"></td>
            <td width="9%" align="right">MIS_COA_CC代码：</td>
            <td width="6%"><input name="countyCodeCoaCc" class="input_style1"  style="width:100%" value="<%=countyCodeCoaCc%>"></td>
            <td width="11%" align="center">
            <%--
            <img align="middle" src="/images/eam_images/new.jpg"  alt="点击新增"  onClick="do_add(); return false;">
             --%>
            <img align="middle" src="/images/eam_images/search.jpg"  alt="查询"  onClick="do_search(); return false;"></td>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="10%" align="center">地市</td>
                <td width="30%" align="center">区县</td>
                <td width="15%" align="center">MIS代码</td>
                <td width="15%" align="center">MIS_COA_CC代码</td>
                <!--<td width="10%" align="center">失效日期</td>-->
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">

    <div style="overflow-y:scroll;height:322px;width:100%">
        <table width="100%" border="1" >
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" onclick="show_detail('<%=row.getValue("COUNTY_CODE")%>') ; return false;"
                style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="10%" align="center"><%=row.getValue("ORGNIZATION_NAME")%></td>
                <td width="30%" align="center"><%=row.getValue("COUNTY_NAME")%></td>
                <td width="15%" align="center"><%=row.getValue("COUNTY_CODE_MIS")%></td>
                <td width="15%" align="center"><%=row.getValue("COUNTY_CODE_COA_CC")%></td>
                <%--<td width="13%" align="center"><%=row.getValue("LAST_UPDATE_DATE")%></td>--%>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet";
        mainFrm.submit();
    }

    function show_detail(countyCode) {
    	return false;
        //mainFrm.action = "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&countyCode=" + countyCode;
        //mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet";
        mainFrm.submit();
    }


    var xmlHttp;

    //-- checkObjectCode
    function getCountyOption() {
        var url = "";
        var organizationId = document.getElementById("organizationId").value;
        createXMLHttpRequest();
        if (organizationId != "") {
            url = "<%=URLDefineList.STAT_EQP_SERVLET%>?act=GET_GIVEN_COUNTY_OPTION&companyId=" + organizationId;
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
                unescape(xmlHttp.responseText);
                var cf = document.getElementById("countyCode1");
                cf.innerHTML = "<select name = countyCode style=\"width:100%\">" + xmlHttp.responseText + "</select>";

            } else {
                alert(xmlHttp.status);
            }
        }
    }
</script>