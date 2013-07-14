<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>任务提醒列表</title>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
    <script language="javascript" src="/WebLibary/js/IESTitleBar.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/login/loginValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/lookup/lookup.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/site.css">
</head>
<script type="text/javascript">
    //printTitleBar("重要信息查看")
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
%>

<div id="$$$waitTipMsg$$$" style="position:absolute; bottom:45%; left:5; z-index:10; visibility:hidden">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900">
				<table width="100%" height="60" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td bgcolor="#eeeeee" align="center">正在请求数据，请稍候......<img
                                src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
	</table>
</div>
<body leftmargin="0" topmargin="0">
<form action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet" name="impForm1" method="post">
    <input type="hidden" name="forward" value="show_all">
    <table border="0" class="searchBg" >
        <tr>
            <td width="30%" align="right">任务名称：</td>
            <td width="60%">
                <input style="width:90%" type="text" name="taskName" maxlength="100"
                       value="<%=StrUtil.nullToString(request.getAttribute("taskName"))%>">
            </td>
            <td>
                <input type="button" name = 'button' class="btn_long" title="点击查询"  align="left"
                			value=" 查 询 " id="queryImg" style="cursor:'hand'" onClick="query(); return false;"/>
            </td>
        </tr>
    </table>
<table  id="headTb" style="display:none;">
        <tr height="20">
            <td width="20%" align=center>任务名称</td>
            <td width="20%" align=center>任务编码</td>
            <td width="15%" align=center>下发人</td>
            <%--<td width="10%" align=center>基准日</td>--%>
            <td width="15%" align=center>任务创建日期</td>
            <%--<td width="10%" align=center>基准日开始日期</td>
            <td width="10%" align=center>基准日结束日期</td>--%>
            <td width="15%" align=center>任务开始时间</td>
            <td width="15%" align=center>任务完成时间</td>
        </tr>
    </table>


<table   border="1" id="listTb" style="display:none;" >
        <%
            RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="20%" height="22"><%=row.getValue("ORDER_NAME") %>
            </td>
            <td width="20%" height="22"><%=row.getValue("ORDER_NUMBER") %>
            </td>
            <td width="15%" height="22"><%=row.getValue("DISTRUBTE_BY_NAME") %>
            </td>
            <%--<td width="10%" height="22"><%=row.getValue("CHECK_BASE_DATE_CITY") %>
            </td>
            --%>
            <td width="15%" height="22"><%=row.getValue("CREATION_DATE") %>
            </td>
            <%--<td width="10%" height="22"><%=row.getValue("CHECK_BASE_DATE_FROM") %>
            </td>
            <td width="10%" height="22"><%=row.getValue("CHECK_BASE_DATE_END") %>
            </td>
            --%><td width="15%" height="22"><%=row.getValue("CHECK_TASK_DATE_FROM") %>
            </td>
            <td width="15%" height="22"><%=row.getValue("CHECK_TASK_DATE_END") %>
            </td>
        <%
                }
            }
        %>
    </table>
<script type="text/javascript">
	var obj = new Object();
	obj.headTbId = "headTb";
	obj.listTbId = "listTb";
    obj.topHeight = 57; //div的absolute : top 值
    //obj.divWidth = "822"; //div的宽度 :
    obj.lTbHeight = "75%"
    printUnitTable( obj );
</script>
</form>
</body>
<div style="position:absolute;top:94%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</html>
<script>
    var flag;
    function show_user_detail(publishId, docType) {
        var url = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet?forward=show_detail";
        var popscript =getpopscript();
        window.open(url, '', popscript);
    }
    function query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = 'visible';
        var url = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.ASSCHKTaskRemainServlet?forward=show_all" ;
        impForm1.action = url;
        document.getElementById("queryImg").disabled = true;
        impForm1.submit();
    }
    
</script>