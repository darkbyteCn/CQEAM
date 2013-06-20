<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  User: zhoujs
  Date: 2007-10-30
  Time: 10:22:03
  Function:待归档工单列表
--%>
<script type="text/javascript" src="/WebLibary/js/help.js"></script>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    boolean matchRight = StrUtil.nullToString(request.getAttribute("matchEnable")).equalsIgnoreCase("TRUE");
    String orderNo= StrUtil.nullToString(request.getParameter("orderNo"));
    String locName= StrUtil.nullToString(request.getParameter("locName"));
    String workorderType = StrUtil.nullToString(request.getParameter("workorderType"));
%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>待归档工单</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    
    <script type="text/javascript">
        var hasRight =  <%=matchRight%>
        var ArrAction1 = new Array(false, "自动归档", "act_refresh.gif", "自动归档", "autoAchieve");
        var ArrActions = new Array(ArrAction1);
        var ArrSinoViews = new Array();


    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 onkeydown="do_check();" onload="helpInit('4.4.5');">
<input type="hidden" name="helpId" value="">
<form name="mainFrm" action="/servlet/com.sino.ams.workorder.servlet.OrderAchieveServlet">
<script type="text/javascript">
    printTitleBar("待归档工单")
    printToolBar();
</script>
 <input type="hidden" name="act" value="match">
    <table border="0" width="100%" class="queryTable" id="table1">
        <tr>
            <td width="8%" align="right">工单号：</td>
            <td width="12%"><input type="text" class="input_style1" name="orderNo" style="width:100%" value="<%=orderNo%>"></td>
            <td width="8%" align="right">地点名称：</td>
            <td width="12%">
            	<input type="hidden" name="locNo" value="">
            	<input type="hidden" name="workorderType" value="<%=workorderType%>">
            	<input type="text" class="input_style1" name="locName" style="width:100%" value="<%=locName%>">
            </td>
            <td width="20%"  align="right" colspan="2">
            	<img src="/images/eam_images/search.jpg" alt="查询待归档工单" onClick="do_Search();">
            </td>
        </tr>
    </table>
<script type="text/javascript">
    var columnArr = new Array("工单编号", "处理状态", "任务名称", "工单类型", "上传时间", "地点名称", "执行人");
    var widthArr = new Array("12%", "8%", "24%", "10%", "14%", "26%", "6%");
    printTableHead(columnArr, widthArr);
</script>

<%
    RowSet rs = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rs != null && !rs.isEmpty()){
%>
<div style="overflow-y:scroll;height:325;width:100%;position:absolute;top:73px;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#666666">

<%
        Row row = null;
		for(int i = 0; i < rs.getSize(); i++){
			row = rs.getRow(i);
            String prevOrder =  row.getStrValue("FIRSTPENDINGORDER");
            String dealState = (prevOrder.equals("")) ? ("待归档") : ("等待工单:" + prevOrder);
%>
			<tr class="dataTR" title="单击查阅工单差异信息" onclick="showDiffDetail('<%=row.getValue("SYSTEMID")%>');">
				<td style="word-wrap:break-word" height="22" width="12%"  ><%=row.getValue("WORKORDER_NO")%></td>
                <input type="hidden" name="workorderNo" value="<%=row.getStrValue("WORKORDER_NO")%>">
                <input type="hidden" name="objectNo" value="<%=row.getStrValue("WORKORDER_OBJECT_NO")%>">
                <input type="hidden" name="hasDiff" value="<%=row.getStrValue("HASDIFF")%>">
                <%--<input type="hidden" name="prjId" value="<%=row.getStrValue("PRJ_ID")%>">--%>
                <td style="word-wrap:break-word" height="22" width="8%" ><%=dealState%></td>
				<td style="word-wrap:break-word" height="22" width="24%" ><%=row.getValue("WORKORDER_BATCH_NAME")%></td>
				<td style="word-wrap:break-word" height="22" width="10%" ><%=row.getValue("WORKORDER_TYPE_DESC")%></td>
				<td style="word-wrap:break-word" height="22" width="14%" ><%=row.getValue("UPLOAD_DATE")%></td>
				<td style="word-wrap:break-word;" height="22" width="26%" ><%=row.getValue("OBJECT_NAME")%></td>
				<td style="word-wrap:break-word" height="22" width="6%" ><%=row.getValue("USERNAME")%></td>
				<%--<td style="word-wrap:break-word" height="22" width="4%" ><%=row.getValue("HASDIFF")%></td>--%>
			</tr>
<%
		}
%>
    </table>
</div>
<%
    }
    %>
</form>
<div style="position:absolute;top:428px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script type="text/javascript">
         function showDiffDetail(val) {
            var url = "/workorder/order/waitOrder.jsp?val=" + val;
            var screenHeight = window.screen.height - 100;
            var screenWidth = window.screen.width;
            var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
            window.open(url, "", winstyle);
        }
        function autoAchieve() {
            document.mainFrm.act.value = "match";
            document.mainFrm.submit();
        }
        function do_Search() {
        	var wt = document.getElementById("workorderType").value;
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.workorder.servlet.PendingOrderServlet?workorderType=" + wt;
            document.mainFrm.submit();
        }
        function do_check() {
            if (event.keyCode == 13) {
                do_Search();
            }
        }
</script>
</html>
