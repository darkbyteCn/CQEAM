<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2009-6-12
  Time: 11:57:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.system.user.dto.AmsSynRightDTO" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>资产同步权限查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="autoExeFunction('do_SearchGroup()');">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    AmsSynRightDTO dto = (AmsSynRightDTO) request.getAttribute(QueryConstant.QUERY_DTO);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet">
    <script language="javascript">
        printTitleBar("资产同步权限查询");
    </script>
    <input type="hidden" name="act" value="<%=dto.getAct()%>">
    <input type="hidden" name="groupId" value="">

    <table border="0" width="100%" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="12%" align="right">用户名：</td>
            <td width="15%"><input type="text" name="userName" class="input_style1" style="width:100%" value="<%=dto.getUserName()%>"></td>
            <td width="11%" align="right">登录名：</td>
            <td width="10%">
                <input type="text" name="loginName" class="input_style1" style="width:100%" value="<%=dto.getLoginName()%>">
            </td>
            <td width="8%" align="center"><img src="/images/eam_images/search.jpg" alt="查询权限"
                                               onClick="do_SearchGroup(); return false;"></td>
            <td width="8%" align="center"><img src="/images/eam_images/new.jpg" alt="新增权限" onclick="do_create()"></td>
        </tr>
    </table>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:50px;left:1px;width:830px">
		<table class="headerTable" border="1" width="100%">
			<tr height="20px">
				<td align=center width="10%">用户名</td>
				<td align=center width="15%">登录名</td>
				<td align=center width="10%">员工编号</td>
				<td align=center width="10%">联系电话</td>
				<td align=center width="26%">操作地市</td>
			</tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:68%;width:847px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed&#59&#59;word-break:break-all">

<%
    if (rows != null && !rows.isEmpty()) {
%>
<%
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("USER_ID")%>'); return false;">
                <td style="word-wrap:break-word" height="22" width="10%" align=center><%=row.getValue("USERNAME")%></td>
                <td style="word-wrap:break-word" height="22" width="15%"><%=row.getValue("LOGIN_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="10%"><%=row.getValue("EMPLOYEE_NUMBER")%></td>
                <td style="word-wrap:break-word" height="22" width="10%"><%=row.getValue("MOVETEL")%></td>
                <td style="word-wrap:break-word" height="22" width="26%"><%=row.getValue("ORGNIZATION_NAME")%></td>
            </tr>
<%
            }
        }
%>
        </table>
    </div>
</form>
<%
    if (rows != null && !rows.isEmpty()) {
%>
<div id="pageNaviDiv" style="position:absolute;top:87%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">
    function do_create(){
         mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet";
        mainFrm.submit();
    }

    function do_SearchGroup() {
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet";
        mainFrm.submit();
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }
    
    function do_ShowDetail(userId) {        
        mainFrm.action = "/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&userId=" + userId;
        mainFrm.submit();
    }

    function doChecked() {
		do_SetPageWidth();
    }

    function refreshTree() {
        var action = mainFrm.act.value;
        if (action == "<%=WebActionConstant.UPDATE_ACTION%>" || action == "<%=WebActionConstant.DELETE_ACTION%>" || action == "<%=WebActionConstant.CREATE_ACTION%>") {
            parent.contents.location.reload();
            parent.parent.banner.location = "/servlet/com.sino.ams.system.resource.servlet.ResourceTreeServlet";
        }
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchGroup();
        }
    }
</script>