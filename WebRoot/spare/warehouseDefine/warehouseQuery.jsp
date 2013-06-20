<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>仓库地点设置</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>

</head>

<body onkeydown = "autoExeFunction('do_search()')">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String workorderObjectCode = parser.getParameter("workorderObjectCode");
    String workorderObjectName = parser.getParameter("workorderObjectName");
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String warehouseType = (String) request.getAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION);
%>
<form method = "post" name = "mainFrm" action = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet">
    <script type = "text/javascript">
        printTitleBar("仓库地点设置")
    </script>
    <table width = "100%" border = "0" class = "queryHeadBg">
        <tr>
            <td width = "10%" align = "right">仓库代码：</td>
            <td width = "20%"><input style = "width:100%" type = "text" name = "workorderObjectCode" value = "<%=workorderObjectCode%>"></td>
            <td align = "right">仓库名称：</td>
            <td><input name = "workorderObjectName" style = "width:100%" value = "<%=workorderObjectName%>"></td>
            <td width = "20%" align = "center"><img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询">&nbsp;&nbsp;&nbsp;<img src = "/images/eam_images/new_add.jpg" alt = "新增" onClick = "do_add();"></td>
        </tr>
        <tr><td width = "10%" align = "right">仓库类型：</td>
            <td width = "20%"><select style = "width:100%" type = "text" name = "objectCategory"><%=warehouseType%></select></td>
            <td width = "10%" align = "right">所属区县：</td>
            <td width = "20%"><select style = "width:100%" type = "text" name = "countyCode"><%=countyCode%></select></td>
            <td align = "center"><img src = "/images/eam_images/enable.jpg" style = "cursor:'hand'" onclick = "do_efficient();" alt = "批量生效">&nbsp;&nbsp;&nbsp;<img src = "/images/eam_images/disable.jpg" style = "cursor:'hand'" onclick = "do_disabled();" alt = "批量失效"></td>
        </tr>
    </table>
   <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width = "100%" class = "headerTable" border = "1">
            <tr height = "20">
                <td width = "3%" align = "center" style = "padding:0"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox" onclick = "checkAll('titleCheck','workorderObjectNos')"></td>
                <td width = "12%" align = "center">仓库代码</td>
                <td width = "25%" align = "center">仓库名称</td>
                <td width = "25%" align = "center">所在地点</td>
                <td width = "20%" align = "center">所属区县</td>
                <td width = "15%" align = "center">失效日期</td>
            </tr>
        </table>
    </div>
    <input type = "hidden" name = "act">

    <div style = "overflow-y:scroll;left:0px;width:100%;height:330px">
        <table width = "100%" border = "1" bordercolor = "#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height = "22" style = "cursor:'hand'" onMouseMove = "style.backgroundColor='#EFEFEF'" onMouseOut = "style.backgroundColor='#ffffff'">
                <td width = "3%" align = "center"><input type = "checkbox" name = "workorderObjectNos" value = "<%=row.getValue("WORKORDER_OBJECT_NO")%>"></td>
                <td width = "12%" align = "center" onclick = "show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
                <td width = "25%" onclick = "show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
                <td width = "25%" align = "left" onclick = "show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%></td>
                <td width = "20%" align = "center" onclick = "show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("COUNTY_NAME")%></td>
                <td width = "15%" onclick = "show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("DISABLE_DATE")%></td>
            </tr>
            <%
                }  }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page = "/servlet/com.sino.framework.servlet.MessageProcessServlet" flush = "true" />
</body>
</html>
<script type = "text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet";
    mainFrm.submit();
}

function show_detail(workorderObjectNo) {
    var url = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&workorderObjectNo=" + workorderObjectNo;
    var popscript = 'width=400,height=200,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}

function do_add() {
    var url = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var popscript = 'width=400,height=200,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}
var hasEqp = true;

function do_disabled() {
    var checkedCount = getCheckedBoxCount("workorderObjectNos");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
     } else {
        do_verifyObjectNos();
     }
}

function do_efficient() {
    var checkedCount = getCheckedBoxCount("workorderObjectNos");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
        return;
    } else {
        mainFrm.act.value = "<%=AMSActionConstant.EFFICIENT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet";
        mainFrm.submit();
    }
}
var xmlHttp;

function do_verifyObjectNos() {
    var url = "";
    var   a   =   document.getElementsByName("workorderObjectNos");
    var   str="";
    var workorderObjectNos=new Array();
    for(var i=0;i<a.length;i++) {
        if(a[i].checked){
            workorderObjectNos[workorderObjectNos.length]=a[i].value
        }
    }
    createXMLHttpRequest();
    url = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet?act=verifyObjectNos&workorderObjectNos="+workorderObjectNos;
    xmlHttp.onreadystatechange = handleReadyStateChange1;
    xmlHttp.open("post", url, true);
    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp.send(null);
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
                alert("所选地点中存在设备，不允许失效！");
            } else {
                if (confirm(DISABLE_MSG)) {
                    mainFrm.act.value = "<%=AMSActionConstant.DISABLED_ACTION%>";
                    mainFrm.action = "/servlet/com.sino.ams.spare.inv.servlet.EtsObjectServlet";
                    mainFrm.submit();
                 }
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
</script>