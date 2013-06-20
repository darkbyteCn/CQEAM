<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<%@page import="com.sino.ams.workorder.dto.EtsItemDTO"%>
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
		<title>转资条码打印</title>
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
	<%-- 
	<div id="buttonSet">
	<script type="text/javascript">
		var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.close(); return false;");
        var ArrAction1 = new Array(true, "打印设置", "download.gif", "打印设置", "do_SetupPrint(); return false;");
        var ArrAction2 = new Array(true, "打印预览", "action_viewstatus.gif", "打印预览", "do_PrevPrint(); return false;");
        var ArrAction3 = new Array(true, "打印", "print.gif", "打印", "do_PrintOrder(); return false;"); 
        var ArrActions = new Array(); 
      	ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2 , ArrAction3 );  
        var ArrSinoViews = new Array();
        printToolBar();
        
        
        function do_SetupPrint(){
			webbrowser.execwb(8,0);
		}
		
		function do_PrevPrint(){
			document.getElementById("buttonSet").style.display = "none";
			webbrowser.execwb(7,0);
			document.getElementById("buttonSet").style.display = "block";
		}
		
		function do_PrintOrder(){
			document.getElementById("buttonSet").style.display = "none";
			webbrowser.execwb(6,6);
			document.getElementById("buttonSet").style.display = "block";
		}
		
		function do_Close(){ 
			window.close();
		} 
    </script>
    </div>
    <object id="webbrowser" width="0" height="0" classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
	--%> 
	<%--
	<table width="100%" id="buttonSet">
	    <tr>
	    	<td width="100%" align="left">
		        <img src="/images/eam_images/page_config.jpg" alt="打印设置" onClick="do_SetupPrint(); return false;">
		        <img src="/images/eam_images/print_view.jpg" alt="打印预览" onClick="do_PrevPrint(); return false;">
		        <img src="/images/eam_images/print.jpg" alt="打印" onClick="do_PrintOrder(); return false;">
		        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close(); return false;">
	    	</td>
	    </tr>
	</table> --%>
		<table align="center" width="99%" border="0">
			<%
				EtsItemDTO line = null;
				for (int i = 0; i < lines.getSize(); i++) {
					line = (EtsItemDTO) lines.getDTO(i);
			%>
			<tr height="22">
				<td  >
					<img src="/images/CMMC.JPG" style="vertical-align:middle;" width="22px" height="22px" />&nbsp;<font class="title" ><%=line.getCompanyName()%></font>
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
							<td>启用日期： </td><td><font class="content"><%=line.getStartDate2()%></font></td>
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
 
