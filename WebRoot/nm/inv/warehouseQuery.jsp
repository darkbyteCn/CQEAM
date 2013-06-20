<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  created by YSB
  Date: 2008-12-10
  Time: 17:34:36
--%>

<html>
<head>
    <title>仓库地点维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
//    String workorderObjectNo = parser.getParameter("workorderObjectNo");
    String workorderObjectCode = parser.getParameter("workorderObjectCode");
    String workorderObjectName = parser.getParameter("workorderObjectName");
//    String workorderObjectLocation = parser.getParameter("workorderObjectLocation");
//    String disableDate = parser.getParameter("disableDate");
//    String objectCategory = parser.getParameter("objectCategory");
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String warehouseType = (String) request.getAttribute(WebAttrConstant.WAREHOUSE_TYPE_OPTION);
    String busihouseType = (String) request.getAttribute(WebAttrConstant.BUSIHOUSE_TYPE_OPTION);
%>
<form method="post" name="mainFrm" action="/servlet/com.sino.nm.ams.inv.common.servlet.EtsObjectServlet">
    <script type="text/javascript">
        printTitleBar("仓库地点维护")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="15%" align="right">仓库代码：</td>
            <td width="25%"><input style="width:100%" type="text" name="workorderObjectCode"
                                   value="<%=workorderObjectCode%>"></td>
            <td width="20%" align="right">仓库名称：</td>
            <td width="25%"><input name="workorderObjectName" style="width:100%" value="<%=workorderObjectName%>"></td>
			<!--  
            <td width="20%" align="center"><img src="/images/button/query.gif" style="cursor:'hand'"
                                                onclick="do_search();" alt="查询">&nbsp;&nbsp;&nbsp;<img
                    src="/images/button/new.gif" alt="新增" onClick="do_add();"></td>
            -->
            <td></td>
        </tr>
        <tr>
            <td width="15%" align="right">仓库类型：</td>
            <td width="25%"><select style="width:100%" type="text" name="objectCategory"><%=warehouseType%>
            </select>
            </td>
            <td width="20%" align="right">所属成本中心：</td>
            <td width="25%"><select style="width:100%" type="text" name="countyCode"><%=countyCode%>
            </select></td>
            <!--  
            <td align="center"><img src="/images/button/efficient.gif" style="cursor:'hand'" onclick="do_efficient();"
                                    alt="批量生效">&nbsp;&nbsp;&nbsp;<img src="/images/button/disabled.gif"
                                                                      style="cursor:'hand'" onclick="do_disabled();"
                                                                      alt="批量失效"></td>
            -->
            <td></td>
        </tr>
        <tr>
        	<td width="15%" align="right">业务类型：</td>
            <td width="25%">
            	<select style="width:100%" type="text" name="businessCategory"><%=busihouseType%></select>
            </td>
            
            <td width="45%" align="right" colspan="2">
            	<img src="/images/button/query.gif" style="cursor:'hand'" onclick="do_search();" alt="查询">&nbsp;&nbsp;&nbsp;
            	<img src="/images/button/new.gif" alt="新增" onClick="do_add();">&nbsp;&nbsp;&nbsp;
            	<img src="/images/button/efficient.gif" style="cursor:'hand'" onclick="do_efficient();" alt="批量生效">&nbsp;&nbsp;&nbsp;
            	<img src="/images/button/disabled.gif" style="cursor:'hand'" onclick="do_disabled();" alt="批量失效">
            </td>
            <td></td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                       class="headCheckbox"
                                                                       id="controlBox"
                                                                       onclick="checkAll('titleCheck','workorderObjectNos')">
                </td>
                <td width="9%" align="center">仓库代码</td>
                <td width="15%" align="center">仓库名称</td>
                <td width="20%" align="center">所在地点</td>
                <td width="10%" align="center">所属成本中心</td>
                <td width="10%" align="center">仓库类型</td>
                <td width="10%" align="center">业务类型</td>
                <td width="10%" align="center">区域类型</td>
                <td width="10%" align="center">失效日期</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">

    <div style="overflow-y:scroll;left:0px;width:100%;height:300px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align="center"><input type="checkbox" name="workorderObjectCodes"
                                                     value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
                <td width="9%" align="center"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="15%"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')">
                    <input type="text" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>" readonly="readonly" class="finput">
                </td>
                <td width="20%" align="left"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')">
                    <input type="text" value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>" readonly="readonly" class="finput">
                </td>
                <td width="10%" align="center"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')">
                    <input type="text" value="<%=row.getValue("COUNTY_NAME")%>" readonly="readonly" class="finput">
                </td>
                <td width="10%" align="center"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')">
                    <input type="text" value="<%=row.getValue("INV_CATEGORY_NAME")%>" readonly="readonly" class="finput">
                </td>
                <!-- ETS_FLEX_VALUES表中的VALUE值 -->
                <td width="10%" align="left"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><input type="text" name="businessCategory" value="<%=row.getValue("VALUE")%>" readonly="readonly" class="finput">
                </td>
                <td width="10%" align="left"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><input type="text" name="areaType" value="<%=row.getValue("AREA_TYPE")%>" readonly="readonly" class="finput">
                </td>
                <td width="10%" align="left"
                    onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO")%>')"><%=row.getValue("DISABLE_DATE")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
 <%if (rows != null && !rows.isEmpty()) {%>	
<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<% }%>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.nm.ams.inv.common.servlet.EtsObjectServlet";
    mainFrm.submit();
}

function show_detail(workorderObjectNo) {
    var url = "/servlet/com.sino.nm.ams.inv.common.servlet.EtsObjectServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&workorderObjectNo=" + workorderObjectNo;
    var popscript = 'width=600,height=400,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}

function do_add() {
    var url = "/servlet/com.sino.nm.ams.inv.common.servlet.EtsObjectServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var popscript = 'width=600,height=400,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}
var hasEqp = true;

function do_disabled() {
    var checkedCount = getCheckedBoxCount("workorderObjectCodes");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
    } else {
        do_verifyObjectNos();

    }
}

function do_efficient() {
    var checkedCount = getCheckedBoxCount("workorderObjectCodes");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
    } else {
        mainFrm.act.value = "<%=AMSActionConstant.EFFICIENT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.nm.ams.inv.common.servlet.EtsObjectServlet";
        mainFrm.submit();
    }
}
// ------------------------------------------------------------------------
var xmlHttp;

function do_verifyObjectNos() {
    var url = "";
    var a = document.getElementsByName("workorderObjectNos");
    var str = "";
    var workorderObjectNos = new Array();
    //    alert(workorderObjectNos);
    for (var i = 0; i < a.length; i++) {
        if (a[i].checked) {
            //            alert(a[i].value)
            workorderObjectNos[workorderObjectNos.length] = a[i].value

            //            workorderObjectNos[i]= a[i].value;
            //            alert(workorderObjectNos[i]);
            //            str+=","+ a[i].value;
        }
    }
    //    alert(workorderObjectNos);
    xmlHttp = createXMLHttpRequest();
    url = "/servlet/com.sino.ams.system.switches.servlet.EtsObjectServlet?act=verifyObjectNos&workorderObjectNos=" + workorderObjectNos;
    xmlHttp.onreadystatechange = handleReadyStateChange1;
    xmlHttp.open("post", url, true);
    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp.send(null);
}

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'Y') {
                alert("所选地点中存在设备，不允许失效！");
            } else {
                //                document.mainFrm.isExist.value = 'N';
                if (confirm(DISABLE_MSG)) {
                    //                    alert("sucess");
                    mainFrm.act.value = "<%=AMSActionConstant.DISABLED_ACTION%>";
                    mainFrm.action = "/servlet/com.sino.nm.ams.inv.common.servlet.EtsObjectServlet";
                    mainFrm.submit();
                }
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
</script>