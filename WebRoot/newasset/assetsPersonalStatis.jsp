<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>
<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>

</head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
	EtsFaAssetsDTO dto = (EtsFaAssetsDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String assetsCategory = dto.getAssetsCategory();
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}	
%>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="">
<script type="text/javascript">
    printTitleBar("资产统计报表>>责任人资产统计");
</script>
	<table width="100%" border="0">
	    <tr>
	    	<td width="8%" align="right">资产名称：</td>
	    	<td width="15%">
			<input type="text" name="assetsDescription" size="20" style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
	    	<td width="8%" align="right"><span lang="zh-cn">资产型号</span>：</td>
	    	<td width="15%">
			<input type="text" name="modelNumber" size="20" style="width:100%" value="<%=dto.getModelNumber()%>"></td>
	    	<td width="8%" align="right">启用日期：</td>
	    	<td width="32%">
			<input type="text" name="startDate" size="20" style="width:34%" class="readonlyInput" readonly title="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate)" value="<%=dto.getStartDate()%>"><img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate)">到：<input type="text" name="endDate" size="20" style="width:34%;" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)" value="<%=dto.getEndDate()%>"><img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(startDate, endDate)"></td>
	    	<td width="14%" align="right">
	    		<img src="/images/eam_images/search.jpg" title="点击查询" onClick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" title="导出到Excel" onClick="do_Export();">
	    	</td>
	    </tr>
	    </table>
	<input type="hidden" name="act" value="">
	<input type="hidden" name="assetsCategory" value="<%=assetsCategory%>">
	<input type="hidden" name="companyCode" value="<%=dto.getCompanyCode()%>">
	<input type="hidden" name="deptCode" value="<%=dto.getDeptCode()%>">

<script type="text/javascript">
    var columnArr = new Array("资产类别一", "资产类别二", "资产名称", "资产型号", "计量单位", "数量", "原值(元)");
    var widthArr = new Array("10%","18%","24%","28%", "6%", "6%", "8%");
    printTableHead(columnArr,widthArr);
</script>
<%
	if(hasData){
%>
    <div style="overflow-y:scroll;height:360px;width:100%;left:1px;margin-left:0">
<%
	}
%>    
</form>	
<%
	if(hasData){
%>
	    <table width="100%" border="1" bordercolor="#666666">		

<%		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR">
				<td width="10%" align="left"><%=row.getValue("FA_CATEGORY1")%></td>
				<td width="18%" align="left"><%=row.getValue("FA_CATEGORY2")%></td>
				<td width="24%" align="left"><%=row.getValue("ASSETS_DESCRIPTION")%></td>
				<td width="28%" align="left"><%=row.getValue("MODEL_NUMBER")%></td>
				<td width="6%" align="center" ><%=row.getValue("UNIT_OF_MEASURE")%></td>
				<td width="6%" align="right" ><%=row.getValue("ASSETS_COUNT")%>&nbsp;</td>
				<td width="8%" align="right" ><%=row.getValue("ASSETS_COST")%>&nbsp;</td>
			</tr>
<%
		}
%>
		</table>
	</div>
<div style="position:absolute;top:440px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%		
	}
%>	
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<iframe style="display:none" src="" name="downFrm"></iframe>

<script language="javascript">

function do_Search(){
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.action = "/servlet/com.sino.ams.assets.servlet.FaAssetsStatisServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_Export(){
	mainFrm.target = "downFrm";
	mainFrm.action = "/servlet/com.sino.ams.assets.servlet.FaAssetsStatisServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}


</script>
