<%@ page import="com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-9-18
  Time: 10:41:53
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>账实不符</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.sampling.report.servlet.AssetsSamplingReportTwoServlet">
<%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" border="0" class="queryTable">
		<tr>
            <td width="20%">工单批号：<%=dto.getBatchNo()%></td>
            <td align="right" width="100%"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
		</tr>
	</table>
	<input type="hidden" name="act" value="<%=AssetsActionConstant.EXPORT_ACTION%>">
    <input type="hidden" name="batchNo" value="<%=dto.getBatchNo()%>">
</form>
<div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:21px;left:0px;width:100%" class="crystalScroll">
	<table border="1" width="400%" class="eamDbHeaderTable">
		<tr height=20px>
			<td width="4%" align="center" rowspan="2">公司名称</td>
			<td width="4%" align="center" rowspan="2">设备条码</td>

			<td width="10%" align="center" colspan="2">设备名称</td>
			<td width="10%" align="center" colspan="2">设备型号</td>

			<td width="10%" align="center" colspan="2">地点代码</td>
			<td width="14%" align="center" colspan="2">地点名称</td>
			<td width="8%" align="center" colspan="2">员工编号</td>

			<td width="8%" align="center" colspan="2">责任人</td>

			<td width="10%" align="center" rowspan="2">变更数据</td>
		</tr>
		<tr height=20px class="eamDbHeaderTr">
			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">实际扫描</td>

			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">实际扫描</td>

			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">实际扫描</td>

			<td width="7%" align="center">EAM系统</td>
			<td width="7%" align="center">实际扫描</td>

			<td width="4%" align="center">EAM系统</td>
			<td width="4%" align="center">实际扫描</td>
			<td width="4%" align="center">EAM系统</td>
			<td width="4%" align="center">实际扫描</td>
		</tr>
	</table>
</div>
<div style="overflow:scroll;height:520px;width:100%;position:absolute;top:62px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="400%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
  			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_ITEM_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_ITEM_NAME")%>"></td>

			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_ITEM_SPEC")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_ITEM_SPEC")%>"></td>

			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_LOCATION_CODE")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("SCAN_LOCATION_CODE")%>"></td>
			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_LOCATION")%>"></td>
			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_LOCATION")%>"></td>

			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_EMPLOYEE_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("SCAN_EMPLOYEE_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_USER_NAME")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_USER_NAME")%>"></td>

			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("CHANGED_CONTENT")%>"></td>
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
</script>