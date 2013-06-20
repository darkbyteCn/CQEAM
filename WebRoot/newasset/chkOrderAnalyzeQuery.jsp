<title>已归档工单差异分析</title>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')">
<%
	AmsAssetsCheckHeaderDTO queryArgs = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form action="<%=AssetsURLList.ORDER_ANALYZE_SERVLET%>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("资产盘点管理>>盘点差异分析");
</script>
    <input type="hidden" name="act" value="">
    <table width=100% class="queryTable">
        <tr>
            <td width="8%" align="right">盘点单号：</td>
            <td width="14%"><input type="text" class="input_style1"  name="transNo" style="width:100%" value="<%=queryArgs.getTransNo()%>"></td>
            <td width="8%" align="right">盘点部门：</td>
            <td width="30%">
			<select name="checkDept" class="select_style1" style="width:100%"><%=queryArgs.getCheckDeptOption()%></select></td>
            <td width="8%" align="right">任务日期：</td>
            <td width="28%">
            <input type="text" name="startDate" value="<%=queryArgs.getStartDate()%>" size="10" title="点击选择开始日期" readonly class="input_style2"  onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate);">
            <input type="text" name="endDate" value="<%=queryArgs.getEndDate()%>"  size="10" title="点击选择截至日期" readonly class="input_style2" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop(startDate, endDate);">
            <td width="6%">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();"></td>
        </tr>
	</table>
<script type="text/javascript">
    var columnArr = new Array("盘点单号", "盘点公司","盘点部门", "盘点地点", "开始日期", "执行周期", "执行人", "单据状态");
    var widthArr = new Array("18%","10%","14%", "20%", "8%", "8%", "10%", "8%");
    printTableHead(columnArr,widthArr);
</script>
<%
	if (hasData) {
%>
	<div style="width:100%;height:362px;left:1px;position:absolute;overflow-y:scroll;">
<%	
	}
%>
<%
	if (hasData) {
%>
	    <table width=100% border=1 bordercolor="#666666">
<%
		for (int i = 0; i < rows.getSize(); i++) {
			Row row=rows.getRow(i);
%>
	        <tr class="dataTR" onclick="showDetail('<%=row.getValue("HEADER_ID")%>')">
			  <td width=18% align="center"><%=row.getValue("TRANS_NO")%></td>
	          <td width=10%><%=row.getValue("COMPANY_NAME")%></td>
	          <td width=14%><%=row.getValue("GROUP_NAME")%></td>
	          <td width=20%><%=row.getValue("CHECK_LOCATION")%></td>
	          <td width=8% align="center"><%=row.getValue("START_TIME")%></td>
	          <td width=8% align="right"><%=row.getValue("IMPLEMENT_DAYS")%>&nbsp;</td>
	          <td width=10%><%=row.getValue("IMPLEMENT_USER")%></td>
	          <td width=8%><%=row.getValue("ORDER_STATUS")%></td>
	        </tr>
<%
		}
%>
	    </table>
	</div>
<%
	}
%>	
</form>
<%		
	if(hasData){
%>
<div style="position:absolute;top:458px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%	
	}
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}

function showDetail(headerId){
    var url = "<%=AssetsURLList.ORDER_ANALYZE_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&headerId="+headerId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'orderWin', style);
}
</script>
</html>
