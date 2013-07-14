<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@page import="com.sino.base.dto.DTOSet"%>
<%@page import="com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO"%>
<%@ include file="/newasset/headerInclude.htm" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>导入时错误显示页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%
		DTOSet lineSetPri = (DTOSet)session.getAttribute("errorDTOSet");
%>
  </head>
  
  <body>
  		<script type="text/javascript">
    	  printTitleBar("导入问题清单");
  		  var myArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Cancel");
          ArrActions = new Array(myArrAction0);
          printToolBar();
  		</script>
  		
    	<table width="100%" class="eamHeaderTable">
				<tr class="headerTable">
					<td height="20px" width="10%" align="center">
						行号
					</td>
					<td height="20px" width="10%" align="center">
						资产标签号
					</td>
					<td height="20px" width="30%" align="center">
						错误原因
					</td>
				</tr>
		</table>
		<div   style= "overflow:auto;height:88% "> 
		<table width="100.23%" id="dataTable" border="1" bordercolor="#666666"
			style="TABLE-LAYOUT: fixed; word-break: break-all">
<%
				if (lineSetPri == null || lineSetPri.isEmpty()) {
%>
			<tr>
				<td height="20%" width="10%"></td>
				<td height="20%" width="10%"></td>
				<td height="20%" width="30%"></td>
			</tr>
<%
				} else {
					int count = lineSetPri.getSize();
					for (int i = 0; i < count; i++) {
						EtsItemYearCheckLineDTO lineDTO = null;
						lineDTO = (EtsItemYearCheckLineDTO) lineSetPri.getDTO(i);
%>
			<tr>
				<td height="20%" width="10%" align="center">
					<font size="2" color="#FF0000"><%=lineDTO.getExcelLineId()%></font>
				</td>
				<td height="20%" width="10%" align="center">
					<font size="2" color="#FF0000"><%=lineDTO.getBarcode()%></font>
				</td>
				<td height="20%" width="30%" align="center">
					<font size="2" color="#FF0000"><%=lineDTO.getErrorMsg()%></font>
				</td>
			</tr>
			<%
				}
				}
			%>
		</table>
		</div>
  </body>
</html>
