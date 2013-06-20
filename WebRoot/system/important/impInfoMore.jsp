<%--
  Created by IntelliJ IDEA.
  User: yu
  Date: 2007-6-3
  Time: 3:20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>重要信息发布标题</title>
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
<form action="/servlet/com.sino.ies.systemsetup.important.servlet.ImpInfoServlet" name="impForm1" method="post">
    <input type="hidden" name="forward">
    <table border="0" class="searchBg" >
        <tr>
            <td width="30%" align="right">发布标题：</td>
            <td width="60%">
                <input style="width:90%" type="text" name="title" maxlength="100"
                       value="<%=StrUtil.nullToString(request.getParameter("title"))%>">
            </td>
            <td>
                <!--<img src="/images/eam_images/search.jpg" id="queryImg" alt="点击查询" onClick="query(); return false;">-->
                <input type="button" name = 'button' class="btn_long" title="点击查询"  align="left"
                			value=" 查 询 " id="queryImg" style="cursor:'hand'" onClick="query(); return false;"/>
            </td>
        </tr>
    </table>
<table  id="headTb" style="display:none;">
        <tr height="20">
            <td width="15%" align=center>发布标题</td>
            <td width="8%" align=center>发布时间</td>
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
        <tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
            style="cursor:'hand'"
            onclick="show_user_detail('<%=row.getValue("PUBLISH_ID")%>','<%=row.getValue("DOC_TYPE")%>')">
            <td width="15%" height="22"><%=row.getValue("TITLE") %>
            </td>
            <td width="8%" height="22"><%=row.getValue("PUBLISH_DATE") %>
            </td>
        </tr>
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
        var url = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=show_detail&publishId=" + publishId + "&docType=" + docType;
        var popscript =getpopscript();
        window.open(url, '', popscript);
    }
    function query() {
               
        document.getElementById("$$$waitTipMsg$$$").style.visibility = 'visible';
        var url = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=show_all" ;
        impForm1.action = url;
        document.getElementById("queryImg").disabled = true;
        impForm1.submit();
    }
    
</script>