<%@ page contentType="text/html;charset=GBK" language="java" %>
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
<%--
  created by YS
  Date: 2008-07-31
  Time: 2:20:36
--%>

<html>
<head>
    <title>子库定义</title>
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
    String depotCode = parser.getParameter("depotCode");
    String dePotName = parser.getParameter("dePotName");
%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("子库定义")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">子库代码：</td>
            <td width="20%"><input style="width:100%" type="text" name="depotCode" value="<%=depotCode%>"></td>
            <td width="10%" align="right">子库名称：</td>
            <td width="40%"><input name="dePotName" style="width:100%" value="<%=dePotName%>"></td>
            <td width="10%" align="center">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td width="10%" align="center">
                <img src="/images/eam_images/new_add.jpg" alt="新增" onClick="do_add();"></td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                       class="headCheckbox"
                                                                       id="controlBox"
                                                                       onclick="checkAll('titleCheck','depotIds')">
                </td>
                <td width="30%" align="center">子库代码</td>
                <td width="45%" align="center">子库名称</td>
                <td width="45%" align="center">是否为仓库</td>
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
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align="center"><input type="checkbox" name="depotIds"
                                                     value="<%=row.getValue("DEPOT_ID")%>"></td>
                <td width="30%" align="center"
                    onclick="show_detail('<%= row.getValue("DEPOT_ID")%>')"><%=row.getValue("DEPOT_CODE")%></td>
                <td width="45%" align="center"
                    onclick="show_detail('<%=row.getValue("DEPOT_ID")%>')"><%=row.getValue("DEPOT_NAME")%></td>
                <td width="45%" align="center"
                    onclick="show_detail('<%=row.getValue("DEPOT_ID")%>')"><%=(row.getValue("DEPOT_TYPE").toString().equals("0"))?"是":"否"%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsDepotDefineServlet";
        mainFrm.submit();
    }
    function show_detail(depotId) {
        var url = "/servlet/com.sino.ams.carddepot.servlet.YsDepotDefineServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&depotId=" + depotId;
        var popscript = 'width=400,height=200,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
        window.open(url, 'basePot', popscript);
    }

    function do_add() {
        var url = "/servlet/com.sino.ams.carddepot.servlet.YsDepotDefineServlet?act=<%=WebActionConstant.NEW_ACTION%>";
        var popscript = 'width=400,height=200,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
        window.open(url, 'basePot', popscript);
    }
</script>