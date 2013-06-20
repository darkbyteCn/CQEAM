<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-25
  Time: 21:29:52
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>新增</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String company = userAccount.getCompany();
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsReportDeptServlet">
<%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="6%">公司：<%=company%></td>
            <td width="6%">起始日期：<%=dto.getStartDate()%></td>
            <td width="6%">截止日期：<%=dto.getEndDate()%></td>
			<td width="12%" align="center">成本中心：<%=dto.getCostCenterName()%></td>
            <td width="4%" align="center">地点名称：</td>
			<td width="10%"><input type="text" name="checkLocationName" value="<%=dto.getCheckLocationName()%>" style="width:80%"><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
            <td width="3%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
		</tr>
	</table>
    <input type="hidden" name="costCode" value="<%=dto.getCostCode()%>">
    <input type="hidden" name="costCenterName" value="<%=dto.getCostCenterName()%>">
    <input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="act" value="<%=AssetsActionConstant.EXPORT_ACTION%>">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:21px;left:1px;width:990px">
	<table border="1" style="color: #FFFFFF" bgcolor="#2983CB" width="400%">

		<tr height="20">
			<td width="3%" align="center">资产标签</td>
			<td width="3%" align="center">资产编号</td>

			<td width="3%" align="center">应用领域</td>
			<td width="6%" align="center">资产类别</td>
			<td width="5%" align="center">资产名称</td>

			<td width="5%" align="center">资产型号</td>
			<td width="3%" align="center">计量单位</td>
			<td width="3%" align="center">数量</td>

			<td width="3%" align="center">资产创建日期</td>
			<td width="3%" align="center">启用日期</td>
			<td width="3%" align="center">使用年限</td>

			<td width="3%" align="center">原始成本</td>
			<td width="3%" align="center">当前成本</td>
			<td width="3%" align="center">资产净值</td>

			<td width="3%" align="center">减值准备累计</td>
			<td width="3%" align="center">资产残值</td>
			<td width="8%" align="center">折旧费用账户</td>

			<td width="3%" align="center">责任人</td>
			<td width="3%" align="center">员工编号</td>
			<td width="7%" align="center">地点名称</td>

			<td width="3%" align="center">地点代码</td>
			<td width="7%" align="center">项目名称</td>
			<td width="3%" align="center">项目编号</td>
		</tr>
	</table>


</div>
<div id="dataDiv" style="overflow:scroll;height:530px;width:1007px;position:absolute;top:41px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="400%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>

  			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY1")%>"></td>
  			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
  			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

  			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
  			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("CURRENT_UNITS")%>"></td>

  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_CREATE_DATE")%>"></td>
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>

  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("ORIGINAL_COST")%>"></td>
  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>

  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("IMPAIR_RESERVE")%>"></td>
  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("SCRAP_VALUE")%>"></td>
  			<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("DEPRECIATION_ACCOUNT")%>"></td>

  			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>
  			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>

  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
  			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_PROJECT_NUMBER")%>"></td>

		</tr>
<%
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:585px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AMSActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_SelectAddress(){
	var lookUpName = "LOOK_UP_ADDRESS";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.checkLocationName.value = obj["toObjectName"];
    }
}
</script>