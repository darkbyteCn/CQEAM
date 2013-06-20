<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<title>部门查询</title>
</head>
<body topmargin="0" leftmargin="0">
<%
	AmsMisDeptDTO queryDTO = (AmsMisDeptDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	DTOSet locations = (DTOSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (locations != null && !locations.isEmpty());
%>
<form name="mainFrm" action="<%=AssetsURLList.DEPT_SERVLET%>" method="post">
	<table border="0" width="100%">
		<tr>
			<td width="20%" height="22" align="right">部门代码：</td>
			<td width="20%" height="22"><input type="text" name="deptCode" value="<%=queryDTO.getDeptCode()%>"></td>
			<td width="20%" height="22" align="right">部门名称：</td>
			<td width="20%" height="22" align="right"><input type="text" name="deptName" value="<%=queryDTO.getDeptName()%>"></td>
            <td width="20%" align="right"><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchDept();"></td>
		</tr>
	</table>
	<input type="hidden" name="companyCode" value="<%=queryDTO.getCompanyCode()%>">
	<input type="hidden" name="act">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:23px;left:1px;width:800px">
	<table class="headerTable" border="1" width="140%" style="text-align:center">
		<tr>
			<td width="6%" height="22">部门代码</td>
			<td width="29%" height="22">部门名称</td>
			<td width="30%" height="22">是否实物部门</td>
			<td width="15%" height="22">区县名称</td>
			<td width="8%" height="22">公司名称</td>
			<td width="7%" height="22">创建日期</td>
		</tr>
	</table>
</div>
<%
	if(hasData){
%>    
<div id="dataDiv" style="overflow:scroll;height:86%;width:817px;position:absolute;top:47px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = locations.getSize();
		AmsMisDeptDTO location = null;
		for(int i = 0; i < dataCount; i++){
			location = (AmsMisDeptDTO)locations.getDTO(i);
%>
		<tr title="点击查看部门信息及其资产信息" onclick="do_ShowDetail('<%=location.getDeptNo()%>')">
			<td width="6%" height="22" align="center"><input type="text" class="finput" readonly value="<%=location.getDeptCode()%>"></td>
			<td width="5%" height="22" align="center"><input type="text" class="finput" readonly value="<%=location.getObjectCategoryName()%>"></td>
			<td width="29%" height="22"><input type="text" class="finput" readonly value="<%=location.getDeptName()%>"></td>
			<td width="30%" height="22"><input type="text" class="finput" readonly value="<%=location.getDeptLocation()%>"></td>
			<td width="15%" height="22"><input type="text" class="finput" readonly value="<%=location.getCountyName()%>"></td>
			<td width="8%" height="22"><input type="text" class="finput" readonly value="<%=location.getCompanyName()%>"></td>
			<td width="7%" height="22"><input type="text" class="finput2" readonly value="<%=location.getCreationDate()%>"></td>
		</tr>
<%
		}
%>		
	</table>
</div>	
<div style="position:absolute;top:580px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

</body>
</html>
<script>
function do_SearchDept(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(objectNo){
	var url = "<%=AssetsURLList.LOCATION_QUERY_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&deptNo=" + objectNo;
	var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, 'locationWin', style);
}

</script>
