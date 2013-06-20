<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<%@page import="com.sino.ams.newasset.print.dto.BarCodePrintDTO"%>
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

			//60,38
		%>
		<title>已有资产标签打印</title>
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
			BarCodePrintDTO line = null;
			for (int i = 0; i < lines.getSize(); i++) {
				line = (BarCodePrintDTO) lines.getDTO(i);
			%>
			<tr height="22">
				<td >
					<img src="/images/CMMC.JPG" style="vertical-align:middle;" width="22px" height="22px" /><img src="<%=line.getBarcodeImg()%>" style="vertical-align:middle;" width="146px"
									height="28px" />
				</td>
			</tr> 
			<tr height="5">
				<td ></td>
			</tr>
			<%
				}
			%> 
		</table>
		
		
	</body>
</html>
 
