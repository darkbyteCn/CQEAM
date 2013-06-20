<%@ page import="com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-9-18
  Time: 9:18:29
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>帐实一致</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.sampling.report.servlet.AssetsSamplingReportOneServlet">
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
	<table border="1" class="eamHeaderTable" width="250%">

		<tr height="20">
			<td width="3%" align="center">公司名称</td>
			<td width="3%" align="center">设备条码</td>
			<td width="6%" align="center">设备名称</td>
			<td width="6%" align="center">设备型号</td>
			<td width="2%" align="center">计量单位</td>
			<td width="5%" align="center">责任部门</td>
			<td width="3%" align="center">责任人员工号</td>
			<td width="3%" align="center">责任人</td>
			<td width="3%" align="center">地点代码</td>
			<td width="6%" align="center">地点名称</td>
			<td width="3%" align="center">项目编号</td>
			<td width="6%" align="center">项目名称</td>
		</tr>
	</table>


</div>
<div style="overflow:scroll;height:530px;width:100%;position:absolute;top:41px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("COMPANY")%>"></td>
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
  			<td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
  			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
  			<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>
  			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
  			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
  			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
  			<td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
  			<td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
  			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>
  			<td width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
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
