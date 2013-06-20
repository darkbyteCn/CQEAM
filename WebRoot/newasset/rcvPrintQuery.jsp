<title>接收单查询</title>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form action="/servlet/com.sino.ams.newasset.servlet.RcvOrderPrintServlet" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    printTitleBar("调拨单据打印");
</script>
    <table width=100% bgcolor="#E9EAE9">
        <tr>
            <td width="8%" align="right">调拨单号：</td>
            <td width="18%">
            <input type="text" name="transNo" value="<%=dto.getTransNo()%>" style="width:100%"></td>
            <td width="8%" align="right">接收单号：</td>
            <td width="18%">
            <input type="text" name="receiveNo" value="<%=dto.getReceiveNo()%>" style="width:100%"></td>
            <td width="8%" align="right">接收日期：</td>
            <td width="25%"><input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:48%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:48%" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
            </td>
            <td width="15%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
			</td>
        </tr>
    </table>
    <input type="hidden" name="act" value="">
	<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:833px">
		<table class="headerTable" border="1" width="160%">
	        <tr height=20px>
	            <td align="center" width="13%">接收单号</td>
	            <td align="center" width="6%">接收日期</td>
	            <td align="center" width="6%">接收人</td>
	            <td align="center" width="8%">接收公司</td>
	            <td align="center" width="15%">接收部门</td>
	            <td align="center" width="13%">调拨单号</td>
	            <td align="center" width="8%">调出公司</td>
				<td align="center" width="15%">调出部门</td>
	            <td align="center" width="6%">调出日期</td>
	            <td align="center" width="6%">资产大类</td>
				<td align="center" width="4%">单据状态</td>
	        </tr>
		</table>
	</div>    
</form>
	
<%
	if (hasData) {
%>
	<div id="dataDiv" style="overflow:scroll;height:78%;width:850px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		String rcvNo = "";
		for (int i = 0; i < rows.getSize(); i++) {
			Row row=rows.getRow(i);
			rcvNo = row.getStrValue("RECEIVE_NO");
%>
			<tr title="点击查看该接收单“<%=rcvNo%>”详细信息" onClick="do_ShowDetail('<%=row.getValue("RECEIVE_HEADER_ID")%>')" style="cursor:hand">
	            <td align="center" width="13%"><input type="text" class="finput2" readonly value="<%=rcvNo%>"></td>
	            <td align="center" width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("RECEIVE_DATE")%>"></td>
	            <td align="center" width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("RECEIVE_USER_NAME")%>"></td>
	            <td align="center" width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("RECEIVE_COMPANY")%>"></td>
	            <td align="center" width="15%"><input type="text" class="finput" readonly value="<%=row.getValue("RECEIVE_DEPT_NAME")%>"></td>
	            <td align="center" width="13%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
	            <td align="center" width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_COMPANY")%>"></td>
				<td align="center" width="15%"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_DEPT_NAME")%>"></td>
	            <td align="center" width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_OUT_DATE")%>"></td>
	            <td align="center" width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("FA_CONTENT_NAME")%>"></td>
				<td align="center" width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ORDER_STATUS_NAME")%>"></td>
	        </tr>
<%
		}
%>
	    </table>
	</div>
	<div style="position:absolute;top:468px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ShowDetail(receiveHeaderId){
    var url = "/servlet/com.sino.ams.newasset.servlet.RcvOrderPrintServlet";
	url += "?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&receiveHeaderId="+receiveHeaderId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'discardWin', style);
}
</script>
</html>
