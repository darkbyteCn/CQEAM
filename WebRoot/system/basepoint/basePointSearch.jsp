<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  created by Zyun
  Date: 2007-09-28
  Time: 8:23:36
--%>

<html>
<head>
    <title>基站地点维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>
<body onkeydown="autoExeFunction('do_search()')">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String name = parser.getParameter("name");
    String workorderObjectCode = parser.getParameter("workorderObjectCode");
    String workorderObjectName = parser.getParameter("workorderObjectName");
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet">
<script type="text/javascript">
    printTitleBar("基站地点维护")
</script>
<table width="100%" border="0" bgcolor="#eeeeee">
        <tr>
            <td width="12%" align="right">基站号：</td>
            <td width="26%"><input style="width:100%" type="text" name="workorderObjectCode" value="<%=workorderObjectCode%>"></td>
            <td width="13%" align="right">基站名：</td>
            <td width="27%"><input name="workorderObjectName" style="width:100%" value = "<%=workorderObjectName%>"></td>
            <td width="22%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">&nbsp;&nbsp;&nbsp;<img src="/images/eam_images/new_add.jpg" alt="新增" onClick="do_add();"></td>
        </tr>
        <tr>
            <td align="right">地市：</td>
            <td><select style="width:100%" type="text" name="organizationId" onchange="getCountyOption();"><%=organizationId%></select></td>
            <td align="right">区县：</td>
           <td> <div id="countyCode1"><select style="width:100%" type="text" name="countyCode" ><%=countyCode%></select></div>  </td>
            <td align="left"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"><img src="/images/button/efficient.gif" style="cursor:'hand'" onclick="do_efficient();" alt="批量生效"><img src="/images/eam_images/disable.jpg" style="cursor:'hand'" onclick="do_disabled();" alt="批量实效"></td>
        </tr>
</table>
<%--<div style="position:absolute;top:73px;left:0px;width:833px">--%>
   <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">

    <!--<table width="100%" class="headerTable" border="1">-->
    <table  border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('titleCheck','workorderObjectNos')"></td>
            <td width="8%" align="center">基站号</td>
            <td width="25%" align="center">基站名称</td>
            <td width="31%" align="center">基站地点</td>
            <td width="10%" align="center">区县</td>
            <td width="13%" align="center">巡检模式</td>
            <td width="10%" align="center">失效日期</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">    

<div style="overflow-y:scroll;left:0px;width:100%;height:360px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="3%" align="center"><input type="checkbox" name="workorderObjectNos" value="<%=row.getValue("WORKORDER_OBJECT_NO")%>"></td>
            <td width="8%" align="center" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
            <td width="25%" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            <td width="31%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%></td>
            <td width="10%" align="center" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("COUNTY_NAME")%></td>
            <td width="13%" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("ISALL")%></td>
            <td width="10%" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("DISABLE_DATE")%></td>
        </tr>
<%
	    }   }
%>
    </table>
</div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet";
    mainFrm.submit();
}

function show_detail(workorderObjectNo) {
    var url = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&workorderObjectNo="+workorderObjectNo;
    var popscript = 'width=870,height=700,top=1,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}

function do_add() {
    var url = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var popscript = 'width=870,height=700,top=1,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}

function do_disabled(){
    var checkedCount = getCheckedBoxCount("workorderObjectNos");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
        } else  {

    <%--mainFrm.act.value = "<%=AMSActionConstant.DISABLED_ACTION%>";--%>
//    mainFrm.action = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet";
//    mainFrm.submit();
      do_verifyObjectNos();
                 }
}

function do_efficient(){
    var checkedCount = getCheckedBoxCount("workorderObjectNos");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
        } else  {

    mainFrm.act.value = "<%=AMSActionConstant.EFFICIENT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet";
    mainFrm.submit();
                 }
}
 
function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet";
    mainFrm.submit();
}

var xmlHttp;
function do_verifyObjectNos() {
    var url = "";
    var   a   =   document.getElementsByName("workorderObjectNos");
    var   str="";
    var workorderObjectNos=new Array();
//    alert("a.length="+a.length);
    for(var i=0;i<a.length;i++) {

        if(a[i].checked){
//            workorderObjectNos[i]= a[i].value;
//            str+=","+ a[i].value;
            workorderObjectNos[workorderObjectNos.length]=a[i].value
            
        }
    }
//    alert(workorderObjectNos);
    createXMLHttpRequest();
        url = "/servlet/com.sino.ams.system.switches.servlet.EtsObjectServlet?act=verifyObjectNos&workorderObjectNos="+workorderObjectNos;
        xmlHttp.onreadystatechange = handleReadyStateChange0;
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

function handleReadyStateChange0() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
//            alert(xmlHttp.responseText);
            if (xmlHttp.responseText == 'Y') {
                alert("所选地点中存在设备，不允许失效！");
            } else {
                //                document.mainFrm.isExist.value = 'N';
                if (confirm(DISABLE_MSG)) {
                    //                    alert("sucess");
                    mainFrm.act.value = "<%=AMSActionConstant.DISABLED_ACTION%>";
                    mainFrm.action = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet";
                    mainFrm.submit();
                }
            }
        } else {
            alert(xmlHttp.status);
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