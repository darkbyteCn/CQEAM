<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>

<%--
  created by V-yuanshuai
  Date: 2007-09-28
  Time: 8:23:36
--%>

<html>

<head>
    <title>仓库现有量--按地点</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>

</head>

<body onload="getInvOption(); do_ControlFP(mainFrm.materialAttr);">

<%

    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOptin = parser.getAttribute("CITY_OPTION").toString();
    String invOption = parser.getAttribute("INV_OPTION").toString();
    String objCateOption = parser.getAttribute("OBJECT_CATEGORY_OPTION").toString();
    String financePropOption = parser.getAttribute("FINANCE_PROP_OPTION").toString();
    String itemSpec = parser.getParameter("itemSpec");

%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("全省统计--按地点")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="20%"><select name=organizationId style="width:100%"
                                    onchange="getInvOption();"><%=cityOptin%></select></td>
            <td width="10%" align="right">仓库：</td>
            <td width="20%"><div id = invField ><select style = "width:100%" ></select></div></td>
            <td width="10%" align="right">规格型号：</td>
            <td width="20%"><input type=text  name=itemSpec value="<%=itemSpec%>"><a
                    href=# title="点击选择规格型号" class="linka" onclick="do_SelectSpec();">[…]</a></td>

        </tr>
        <tr>
            <td align="right">物料属性：</td>
            <td><select size="1" name="materialAttr" id="materialAttr" style="width:100%" onchange="do_ControlFP(this);">
                <option value="2" <%if (parser.getParameter("materialAttr").equals("2")) {%> selected <%}%>>条码设备</option>
                <option value="1" <%if (parser.getParameter("materialAttr").equals("1")) {%> selected <%}%>>非条码设备</option></select></td>
            <td align="right"><span id="financePropMsg" style="display:none">资产属性：</span></td>
            <td><select size="1" name="finaceProp" style="width:100%;display:none"><%=financePropOption%></select></td>
            <td align="right"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            </td>
            <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()"
                     alt="导出到Excel"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="qryType" value="<%=WebAttrConstant.BY_NAME%>">

 <script type="text/javascript">
        var columnArr = new Array( "设备名称", "规格型号", "现有量","地点简称");
        var widthArr = new Array("15%","15%", "10%", "20%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR">
                <td style="word-wrap:break-word" height="22" width="15%"
                    ><%=row.getValue("ITEM_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    ><%=row.getValue("ITEM_SPEC")%></td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("CNT")%></td>
                <td style="word-wrap:break-word" height="22" width="20%"
                    ><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>

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
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>

<script type="text/javascript">
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
    function getInvOption() {
        var url = "";
        var organizationId = document.getElementById("organizationId").value;
        createXMLHttpRequest();
        if (organizationId != "") {
            url = "<%=URLDefineList.STAT_EQP_SERVLET%>?act=GET_GIVEN_INV_OPTION&companyId=" + organizationId;
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
                var cf = document.getElementById("invField");
                cf.innerHTML = "<select name = workorderObjectCode style=\"width:100%\">" + xmlHttp.responseText + "</select>";

            } else {
                alert(xmlHttp.status);
            }
        }
    }

    function do_ControlFP(obj) {
        var selVal = obj.value;
        var displayProp = "block";
        document.getElementById("financePropMsg").innerText = "资产属性：";
        if (selVal == "1") {
            displayProp = "none";
            document.getElementById("financePropMsg").innerText = "";
        }
        document.mainFrm.finaceProp.style.display = displayProp;
        document.getElementById("financePropMsg").style.display = displayProp;
    }
</script>