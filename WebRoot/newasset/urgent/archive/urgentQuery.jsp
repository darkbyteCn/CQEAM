<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@page import="com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant"%>
<%@page import="com.sino.ams.newasset.urgenttrans.constant.UrgentURLListConstant"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>

<%
	UrgentHeaderDTO dto = (UrgentHeaderDTO)request.getAttribute( QueryConstant.QUERY_DTO );
	if( null == dto ){
		dto = new UrgentHeaderDTO(); 
	}
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	String pageTitle = "资产紧急调拨>>单据归档";
%>
<title><%=pageTitle%></title>
<%-- 
<script type="text/javascript" src="/WebLibary/js/jquery-1.2.6.js"></script>
<script type="text/javascript" src="/WebLibary/js/MaskDIV.js"></script> 
<script type="text/javascript" src="/WebLibary/js/submit.js"></script>
--%>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();helpInit('4.6.3');" onkeydown="autoExeFunction('do_SearchOrder();')">
<input type="hidden" name="helpId" value="">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="<%= UrgentURLListConstant.URGENT_ARCHIVE_SERVELT %>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value=""> 
    <table class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr> 
            <td width="10%" align="right">单据号：</td>
            <td width="20%"><input type="text" name="transNo" class="input_style1" style="width:100%" value="<%=dto.getTransNo()%>"></td>
             <td width="10%" align="right">创建日期：</td>
            <td width="30%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:40%" title="点击选择日期" readonly class="input_style2" onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(startDate, endDate);">
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:40%" title="点击选择日期" readonly class="input_style2" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(startDate, endDate);">
            </td> 
            <td width="25%" align="right">
				<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
				<img src="/images/eam_images/export.jpg" title="点击导出" onclick="do_ExportOrder();">
			</td> 
        </tr>
       
    </table>

</form>
 
<div id="headDiv" style="overflow:hidden;position:absolute;top:50px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px"> 
			<td align=center width="18%">单据号</td>
			<td align=center width="10%">单据状态</td>
			<td align=center width="26%">公司</td>
			<td align=center width="12%">申请人</td>
			<td align=center width="10%">创建日期</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:320px;width:855px;position:absolute;top:71px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				Row row=rows.getRow(i);
%>
        <tr class="dataTR" onclick="showDetail('<%=row.getValue("TRANS_ID")%>','<%=row.getValue("TRANS_STATUS")%>')"> 
			<td width="18%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("TRANS_STATUS_DESC")%>"></td>
			<td width="26%"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_COMPANY_NAME")%>"></td>
			<td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("CREATED")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
        </tr>
<%
			}
		}
%>
    </table>
</div> 

<%

    if (rows != null && !rows.isEmpty()) {
%>
<div style="position: absolute;top:400px; bottom:1px; left: 0; right: 20">
<%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>

<%
    }
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_SearchOrder() { 
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ExportOrder(){
	mainFrm.act.value = "EXPORT_ACTION";
	mainFrm.submit();
} 

function do_CloseDiv(){
	document.getElementById("transferDiv").style.visibility = "hidden";
}


function showDetail(transId,status){
	var url = "<%= UrgentURLListConstant.URGENT_ARCHIVE_SERVELT %>?act=<%= WebActionConstant.DETAIL_ACTION %>&transId="+transId;
	if( status == "<%= UrgentAppConstant.STATUS_ARCHIVED %>" ){
		alert("单据已经归档");
		return;
	} 
	//var url =  "/servlet/com.sino.sinoflow.servlet.AppProcessCase?appName="+"leaseApp"+"&appId="+transId;
    var style = "width="+screen.availWidth+",height="+screen.availHeight+",top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'transferWin', style);
}

 
</script>
</html>
