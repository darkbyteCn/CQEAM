<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<title>资产地点查询</title>
</head>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();">
<%=WebConstant.WAIT_TIP_MSG%>

<%
	AmsAssetsAddressVDTO queryDTO = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	DTOSet locations = (DTOSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (locations != null && !locations.isEmpty());
%>
<form name="mainFrm" action="<%=AssetsURLList.LOCATION_QUERY_SERVLET%>" method="post">
	<table border="0" width="100%">
		<tr>
			<td width="20%" height="22" align="right">地点编号：</td>
			<td width="20%" height="22"><input type="text" name="workorderObjectCode" value="<%=queryDTO.getWorkorderObjectCode()%>"></td>
			<td width="20%" height="22" align="right">地点说明：</td>
			<td width="20%" height="22" align="right"><input type="text" name="workorderObjectName" value="<%=queryDTO.getWorkorderObjectName()%>"></td>
            <td width="20%" align="right"><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchLocation();"></td>
		</tr>
	</table>
	<input type="hidden" name="companyName" value="<%=queryDTO.getCompanyName()%>">
	<input type="hidden" name="countyName" value="<%=queryDTO.getCountyName()%>">
	<input type="hidden" name="act">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:23px;left:1px;width:800px">
	<table class="headerTable" border="1" width="160%" style="text-align:center">
		<tr>
			<td width="10%" height="22">地点编号</td>
			<td width="5%" height="22">地点专业</td>
			<td width="26%" height="22">地点简称</td>
			<td width="28%" height="22">所属地点</td>
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
    <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = locations.getSize();
		AmsAssetsAddressVDTO location = null;
		for(int i = 0; i < dataCount; i++){
			location = (AmsAssetsAddressVDTO)locations.getDTO(i);
%>
		<tr title="点击查看地点信息及其资产信息" onclick="do_ShowDetail('<%=location.getWorkorderObjectNo()%>')">
			<td width="10%" height="22"><input class="finput" readonly value="<%=location.getWorkorderObjectCode()%>"></td>
			<td width="5%" height="22"><input class="finput" readonly value="<%=location.getObjectCategoryName()%>"></td>
			<td width="26%" height="22"><input class="finput" readonly value="<%=location.getWorkorderObjectName()%>"></td>
			<td width="28%" height="22"><input class="finput" readonly value="<%=location.getWorkorderObjectLocation()%>"></td>
			<td width="15%" height="22"><input class="finput" readonly value="<%=location.getCountyName()%>"></td>
			<td width="8%" height="22"><input class="finput" readonly value="<%=location.getCompanyName()%>"></td>
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
function do_SearchLocation(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ShowDetail(objectNo){
	var url = "<%=AssetsURLList.LOCATION_QUERY_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&workorderObjectNo=" + objectNo;
	var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, 'locationWin', style);
}

</script>
