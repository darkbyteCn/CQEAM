<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<%@ page import="com.sino.ams.newasset.dto.AssetsAddLDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
		<%
			RequestParser reqParser = new RequestParser();
			reqParser.transData(request);
			DTOSet lines = (DTOSet) reqParser
					.getAttribute(QueryConstant.QUERY_DTO);
 SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
			//60,38
		%>
		<title>新增条码打印</title>
		<style>
		<!--
		.title{
			font-weight: bold;
		}
		.content{
			font-weight: bold;
			font-size: 8px;
		}
		//-->
		</style>
	</head>

	<body leftmargin="0" topmargin="0" style="overflow:auto">
	<jsp:include page="/public/print.jsp" flush="false" />

		<table align="center" width="99%" border="0">
			<%
				AssetsAddLDTO line = null;
				for (int i = 0; i < lines.getSize(); i++) {
					line = (AssetsAddLDTO) lines.getDTO(i);
			%>
			<tr height="22">
				<td  >
					<img src="/images/CMMC.JPG" style="vertical-align:middle;" width="22px" height="22px" />&nbsp;<font class="title" ><%=user.getCompany()%></font>
				</td>
			</tr>
			<tr>
				<td>
					<table width="220" border="1" align="left" bordercolor="#666666" >
						<tr height="22">
							<td width="60" >资产名称： </td><td><font class="content"><%=line.getItemName()%></font></td>
						</tr>
						<tr height="22">
							<td>规格型号： </td><td><font class="content"><%=line.getItemSpec()%></font></td>
						</tr>
						<tr height="22">
							<td>启用日期： </td><td><font class="content"><%=line.getStartDate()%></font></td>
						</tr>

						<tr>
							<td align="center" colspan="2">
								<img src="<%=line.getBarcodeImg()%>" width="146px"
									height="28px" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<%
				}
			%>
		</table>


	</body>
</html>

