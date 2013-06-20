<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
				<script language="javascript" src="/WebLibary/js/Constant.js">
		</script>
				<script language="javascript" src="/WebLibary/js/CommonUtil.js">
		</script>
				<script language="javascript" src="/WebLibary/js/SinoToolBarConst.js">
		</script>
				<script language="javascript" src="/WebLibary/js/SinoToolBar.js">
		</script>
				<script language="javascript" src="/WebLibary/js/AppStandard.js">
		</script>
	</head>
	<body>
		<div id="headDiv"
			style="overflow-y: scroll; overflow-x: hidden; position: absolute; top: 2px; left: 0px; width: 100%">
			<table border="1" width="100%" class="eamHeaderTable" cellpadding="0"
				cellspacing="0">
				<tr class="eamHeaderTable" height="20px">
					<td align=center width="15%">
						用户名称
					</td>
					<td align=center width="15%">
						组别名称
					</td>
					<td align=center width="15%">
						角色名称
					</td>
					<td align=center width="15%">
						角色描述
					</td>
				</tr>
			</table>
		</div>
		<div id="dataDiv"
			style="overflow: scroll; height: 72%; width: 100%; position: absolute; top:24px;  left: 0px"
			align="left"
			onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
			<table width="100%" border="1" bordercolor="#666666" id="dataTab">
				<%
					RowSet sets = (RowSet) request
							.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
					if (sets != null && !sets.isEmpty()) {
						for (int i = 0; i < sets.getSize(); i++) {
							Row row = sets.getRow(i);
				%>
				<tr class="dataTR">
					<td width="15%">
						<input type="text" class="finput" readonly
							value="<%=row.getStrValue("USER_NAME")%>">
					</td>
					<td width="15%">
						<input type="text" class="finput2" readonly
							value="<%=row.getStrValue("GROUP_NAME")%>">
					</td>
					<td width="15%">
						<input type="text" class="finput2" readonly
							value="<%=row.getStrValue("ROLE_NAME")%>">
					</td>
					<td width="15%">
						<input type="text" class="finput2" readonly
							value="<%=row.getStrValue("ROLE_DESC")%>">
					</td>
				</tr>
				<%
					}
					}
				%>
			</table>
			<form action="/servlet/com.sino.ams.newasset.rolequery.servlet.TestServlet2"></form>
				<%
			if (sets != null && !sets.isEmpty()) {
		%>
		<div id="pageNaviDiv" style="position: absolute; top: 110%; left: 0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
		<%
			}
		%>
		</div>
	
	</body>
</html>