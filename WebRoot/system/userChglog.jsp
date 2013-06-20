<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.sinoflow.user.dto.SfUserChgLogDTO" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>用户变更记录查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>

<body leftmargin="1" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_search();');">
<jsp:include page="/message/MessageProcess"/>
<%
    String actionServlet="/servlet/com.sino.sinoflow.user.servlet.SfUserChgLogServlet";
    SfUserChgLogDTO searchParameter = (SfUserChgLogDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<form name="mainFrm" method="POST" action="<%=actionServlet%>">
    <script language="javascript">
        printTitleBar("用户变更记录查询");
    </script>
    <input type="hidden" name="act">
    <input type="hidden" name="userId" value="">
    <input type="hidden" name="operator" value="">

    <table border="0" width="100%" id="table1">
        <tr>
            <td width="12%" align="right">用户名称：</td>
            <td width="15%"><input type="text" name="userName"  class="input_style1" style="width:100%" value="<%=searchParameter.getUserName()%>"></td>

            <td width="12%" align="right">经办人：</td>
            <td width="15%"><input type="text" name="operatorName"  class="input_style1" style="width:100%" value="<%=searchParameter.getOperatorName()%>"></td>

            <td width="8%" align="center">
             <img align="middle" src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_export(); return false;">
            </td>
        </tr>
        <tr>
           <td width="11%" align="right">开始时间：</td>
            <td width="10%"><input type="text" name="startDate" value="<%=searchParameter.getStartDate() %>" class="input_style1" style="width:100%;cursor:pointer" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate,endDate)"></td>
            
            <td width="12%" align="right">结束时间：</td>
            <td width="15%"><input type="text" name="endDate" value="<%=searchParameter.getEndDate() %>" class="input_style1" style="width:100%;cursor:pointer" title="点击选择截止日期" readonly onclick="gfPop.fEndPop(startDate,endDate)"></td>
            <td width="8%" align="center">
            <img align="middle" src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_search(); return false;">
            </td>
        </tr>
    </table>

	<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:70px;left:1px;width:100%"  >
		<table class="headerTable" border="1" width="100%" style="text-align:center">
	        <tr style="height:23px">
		        <td width="15%">用户名称</td>
	            <td width="15%">变更类型</td>
	            <td width="15%">经办人员</td>
	            <td width="25%">经办日期</td>
	            <td width="30%">变更说明</td>
	        </tr>
		</table>
	</div>

<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
    <div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:74px;left:1px" align="left" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1">
<%
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
            <tr class="dataTR">
                <td style="word-wrap:break-word" height="22" width="15%"><%=row.getValue("USER_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="15%"><%=row.getValue("CHG_TYPE")%></td>
                <td style="word-wrap:break-word" height="22" width="15%"><%=row.getValue("OPERATOR_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="25%"><%=row.getValue("OPERATOR_DATE")%></td>
                <td style="word-wrap:break-word" height="22" width="30%"><%=row.getValue("REMARK")%></td>
            </tr>
<%
        }
    }
%>
        </table>
    </div>
</form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
    <%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }

    function do_export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_search();
        }
    }
</script>