<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<%@page import="com.sino.ams.workorder.dto.EtsItemDTO"%>
<%@page import="com.sino.ams.system.object.dto.CommonObjectPrintDTO"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
		<%
			RequestParser reqParser = new RequestParser();
			reqParser.transData(request);
			DTOSet lines = (DTOSet) reqParser.getAttribute(QueryConstant.QUERY_DTO);

			//60,38
		%>
		<title>地点标签打印</title>
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

	<body leftmargin="0" topmargin="0" style="overflow: auto">
		<jsp:include page="/public/print.jsp" flush="false" />
		<table align="center" width="99%" border="0">
			<%
				CommonObjectPrintDTO line = null;
				for (int i = 0; i < lines.getSize(); i++) {
					line = (CommonObjectPrintDTO) lines.getDTO(i);
			%>
			<tr >
				<td align="left" >
					<table width="220" style="border-top: 1px solid black ; border-left:  1px solid black; border-right: 1px solid black; border-bottom:  1px solid black;  ">
						<tr height="30">
							<td width="100%" ><img src="/images/CMMC.JPG" style="vertical-align:middle;" width="22px" height="22px" />&nbsp;<font class="title" ><%=line.getCompanyName()%></font></td>
						</tr>
						<tr height="58">
							<td width="100%" align="center"><img src="<%=line.getObjectNoImg()%>" width="146px" height="28px" /></td>
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
