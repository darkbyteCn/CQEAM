<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.system.LocationInfoConfirm.dto.LocationInfoConfirmDTO" %>

<html>
<head>
    <title>资产地点确认信息查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
	<script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    LocationInfoConfirmDTO dto = (LocationInfoConfirmDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	boolean hasData = (rows != null && rows.getSize() > 0);
    Row row = null;
%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_search()')" onload="initPage();">
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.LocationInfoConfirm.servlet.LocationInfoConfirmServlet">
<input type="hidden" name="act">
<script type="text/javascript">
    printTitleBar("资产地点确认信息查询");
</script>
<table width="100%"  border="0">
        <tr>
            <td width="10%" align="right">地点编号：</td>
            <td width="20%"><input class="input_style1" style="width:90%" type="text" name="workorderObjectNo" value="<%=dto.getWorkorderObjectNo() %>"></td>
            <td width="10%" align="right">确认人：</td>
            <td width="25%"><input  class="input_style1" style="width:90%" type="text" name="confirmUserName" value="<%=dto.getConfirmUserName() %>"></td>
            <td width="10%" align="right">确认日期：</td>
            <td width="25%">
            <input type="text" name="confirmDate" value="<%=dto.getConfirmDate() %>" style="width:90%" 
                      title="点击选择日期" readonly class="input_style1" onclick="gfPop.fStartPop(confirmDate, '')"></td>
            <td width="10%" align="center"><img src="/images/eam_images/search.jpg" align="middle" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:830px">
	<table class="headerTable" border="1" width="100%">
        <tr height="22" onClick="executeClick(this)"title="点击全选或取消全选" style="cursor:hand">
            <td width="15%" align="center">地点编码</td>
            <td width="25%" align="center">地点描述</td>
            <td width="10%" align="center">单据号</td>
            <td width="8%" align="center">审核状态</td>
            <td width="10%" align="center">审核时间</td>
            <td width="10%" align="center">审核人</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:320px;width:847px;position:absolute;top:69px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;">
<%
	if (hasData) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
        <tr style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="15%" align="center" style="word-wrap:break-word"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
            <td width="25%" align="left" style="word-wrap:break-word"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            <td width="10%" align="left"><%=row.getValue("TRANS_NO")%></td>
            <td width="8%" align="left"><%=row.getValue("CONFIRM_STATUS")%></td>
            <td width="10%" align="left"><%=row.getValue("CONFIRM_DATE")%></td>
            <td width="10%" align="left"><%=row.getValue("USERNAME") %></td>
        </tr>
<%
		}
	}
%>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:408px;left:0; right:20;display:none" ><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</body>
</html>

<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "QueryResult";
        mainFrm.submit();
    }
    function initPage() {
		do_SetPageWidth();
    }

</script>