<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.constant.URLDefineList" %>
<%@ page import = "com.sino.ams.constant.LookUpConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import = "com.sino.base.util.StrUtil" %>

<%--
  created by V-yuanshuai
  Date: 2007-09-28
  Time: 8:23:36
--%>

<html>

<head>
    <title>设备统计</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/RadioProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/LookUp.js"></script>

</head>

<body >

<%

    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String objCateOption = parser.getAttribute("OBJECT_CATEGORY_OPTION").toString();
    String financePropOption = parser.getAttribute("FINANCE_PROP_OPTION").toString();
    String itemSpec = parser.getParameter("itemSpec");
    String workorderObjectName = parser.getParameter("workorderObjectName");
    String countyOption = (String) parser.getAttribute("COUNTY_OPTION");

%>
<form method = "post" name = "mainFrm">
    <script type = "text/javascript">
        printTitleBar("设备现有量--按地点")
    </script>
    <table width = "100%" border = "0" class = "queryHeadBg">
        <tr>
            <td width = "7%" align = "right">公司：</td>
            <td width = "8%"><select name = organizationId style = "width:100%" onchange = "getCountyOption();"><%=cityOption%>
            </select></td>
            <td width = "7%" align = "right">所属区县：</td>

            <td width = "20%">
                <div id = countyField><select name = countyCode style = "width:100%"><%=countyOption%></select></div>
            </td>
            <td width = "8%" align = "right">设备型号：</td>
            <td width = "15%"><input type = text name = itemSpec value = "<%=itemSpec%>"><a href = # title = "点击选择设备型号" class = "linka"
                                                                                            onclick = "do_SelectSpec();">[…]</a>
            </td>
            <td width = "8%"><img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询">
            </td>

        </tr>
        <tr>
            <td align = "right">资产类型：</td>
            <td><select name = finaceProp style = "width:100%"><%=financePropOption%>
            </select></td>
            <td align = "right">地点类型：</td>
            <td><select name = objectCategory style = "width:100%"><%=objCateOption%>
            </select></td>
            <td align = "right">地点简称：</td>
            <td><input name = workorderObjectName type = text value = "<%=workorderObjectName%>"><a href = # title = "点击选择地点"
                                                                                                    class = "linka"
                                                                                                    onclick = "do_SelectObjct();">[…]</a>
            </td>
            <td><img src = "/images/eam_images/export.jpg" id = "queryImg" style = "cursor:'hand'" onclick = "do_exportToExcel()"
                     alt = "导出到Excel"></td>
        </tr>
    </table>

    <input type = "hidden" name = "act" value = "<%=parser.getParameter("act")%>">
    <input type = "hidden" name = "qryType" value = "<%=WebAttrConstant.BY_LOCUS%>">

    <script type = "text/javascript">
        var columnArr = new Array("公司", "地点简称", "所在地点", "设备名称", "设备型号", "现有量");
        var widthArr = new Array("10%", "15%", "25%", "15%", "10%", "5%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style = "overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width = "100%" border = "1" bordercolor = "#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class = "dataTR">
                <td height = "22" width = "10%" align = "center"><%=row.getValue("ORGANIZATION_NAME")%>
                </td>
                <td height = "22" width = "15%" align = "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td height = "22" width = "25%" align = "center"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%>
                </td>
                <td height = "22" width = "15%" align = "center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td height = "22" width = "10%" align = "center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td height = "22" width = "5%" align = "center"><%=row.getValue("CNT")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page = "<%=URLDefineList.MESSAGE_PROCESS%>" flush = "true" />
</body>
</html>

<script type = "text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.STAT_EQP_SERVLET%>";
        mainFrm.submit();
    }

    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.STAT_EQP_SERVLET%>";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
    }
    function do_SelectObjct() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var objs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (objs) {
            var obj = null;
            for (var i = 0; i < objs.length; i++) {
                obj = objs[i];
                dto2Frm(obj, "mainFrm");
            }
        }
    }
    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }
    }



    var xmlHttp;
    //-- checkObjectCode
    function getCountyOption() {
        var url = "";
        var organizationId = document.getElementById("organizationId").value;
        createXMLHttpRequest();
        if (organizationId != "") {
            url = "<%=URLDefineList.STAT_EQP_SERVLET%>?act=GET_GIVEN_COUNTY_OPTION&companyId=" + organizationId  ;
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
                var cf = document.getElementById("countyField");
                cf.innerHTML = "<select name = countyCode style=\"width:100%\">" + xmlHttp.responseText + "</select>";

            } else {
                alert(xmlHttp.status);
            }
        }
    }

</script>