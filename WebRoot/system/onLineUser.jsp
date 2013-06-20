<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="com.sino.framework.security.servlet.SessionHandle"%>
<%@page import="com.sino.base.dto.DTOSet"%>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>在线用户列表</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
		<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
		<script type="text/javascript" src="/WebLibary/js/api.js"></script>
		<script type="text/javascript" src="/WebLibary/js/BarVar.js"></script>
		<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	</head>

	<body leftmargin="1" topmargin="0" onload="do_SetPageWidth();" > 
	<script language="javascript">
        printTitleBar("在线用户列表"); 
        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "doClose");
        var ArrAction1 = new Array(true, "刷新", "action_save.gif", "刷新", "doRefresh");
        
        var ArrActions = new Array();
        ArrActions = new Array(ArrAction0, ArrAction1 );
        var ArrSinoViews = new Array();
        printToolBar(); 
        
        function doClose(){
        	window.close();
        } 
        function doRefresh(){
        	location.href="/system/onLineUser.jsp";
        } 
   
    </script>
    
		<table border="0" width="96%" id="table1">
			<tr>
				<td width="100%" align="right">
					当前共<font color="red"><%= SessionHandle.getOnLineUserCount() %></font>位用户在线&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<div id="headDiv"
			style="overflow-x: hidden; overflow-y: scroll; position: absolute; top: 80px; left: 1px; width: 100%">
			<table class="headerTable" border="1" width="100%"
				style="text-align: center">
				<tr style="height: 23px">
					<td width="15%">
						用户名
					</td>
					<td width="15%">
						姓名
					</td>
					<td width="15%">
						所属公司
					</td>
					<td width="25%">
						登陆时间
					</td>
					<td width="25%">
						登陆IP
					</td>
				</tr>
			</table>
		</div>

		<%
			DTOSet rows = SessionHandle.getOnLineUser();
			if (rows != null && !rows.isEmpty()) {
		%>
		<div id="dataDiv"
			style="overflow: scroll; height: 81%; width: 100%; position: absolute; top: 104px; left: 1px"
			align="left" align="left"
			onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
			<table width="100%" border="1">
				<%
					SfUserDTO user = null;
						for (int i = 0; i < rows.getSize(); i++) {
							user = (SfUserDTO) rows.getDTO(i);
				%>
				<tr class="dataTR">
					<td style="word-wrap: break-word" align="center" height="22"
						width="15%"><%=user.getLoginName()%></td>
					<td style="word-wrap: break-word" align="center" height="22"
						width="15%"><%=user.getUsername()%></td>
					<td style="word-wrap: break-word" align="center" height="22"
						width="15%"><%=user.getCompany()%></td>
					<td style="word-wrap: break-word" align="center" height="22"
						width="25%"><%=user.getLogonTime()%></td>
					<td style="word-wrap: break-word" align="center" height="22"
						width="25%"><%=user.getRemoteIp()%></td>
				</tr>
				<%
					}
					}
				%>
			</table>
		</div>
</html>
