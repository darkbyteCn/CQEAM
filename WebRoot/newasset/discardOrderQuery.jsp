<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsURLList" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <title>资产报废单</title>
</head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')">
<%
	AmsAssetsTransHeaderDTO queryArgs = (AmsAssetsTransHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form action="<%=AssetsURLList.ASSETS_DISCARD_SERVLET%>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("资产报废管理>>创建报废申请");
</script>
    <input type="hidden" name="act" value="">
    <table width=100% bgcolor="#E9EAE9">
        <tr>
            <td width="10%" align="right">报废单号：</td>
            <td width="20%"><input type="text" name="transNo" style="width:100%" value="<%=queryArgs.getTransNo()%>"></td>
            <td width="10%" align="right">申请日期：</td>
            <td width="35%">
            <input type="text" name="startDate" value="<%=queryArgs.getStartDate()%>" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(startDate, endDate);">
            <input type="text" name="endDate" value="<%=queryArgs.getEndDate()%>" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(startDate, endDate);">
            </td>
            <td width="25%">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
            <img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_Create();">
            <img src="/images/eam_images/revoke.jpg" alt="点击撤销" onclick="do_Cancel();">
			</td>
        </tr>
    </table>
<script type="text/javascript">
    var columnArr = new Array("checkbox","报废单号","单据状态", "申请公司","申请部门", "申请人", "申请日期");
    var widthArr = new Array("4%","16%","16%","16%", "16%", "16%", "16%", "16%");
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
	        <tr class="dataTR" onclick="showDetail('<%=row.getValue("TRANS_ID")%>')">
	          <td width=4% align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
			  <td width=16% align="center"><%=row.getValue("TRANS_NO")%></td>
	          <td width=16%><%=row.getValue("TRANS_STATUS_DESC")%></td>
	          <td width=16%><%=row.getValue("CREATE_COMPANY")%></td>
	          <td width=16%><%=row.getValue("CREATE_DEPT")%></td>
	          <td width=16%><%=row.getValue("CREATE_USER")%></td>
	          <td width=16% align="center"><%=row.getValue("CREATION_DATE")%></td>
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
<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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

function do_Create() {
    var url = "<%=AssetsURLList.ASSETS_DISCARD_SERVLET%>?act=<%=AssetsActionConstant.NEW_ACTION%>";
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'discardWin', style);
}

function showDetail(transId){
    var url = "<%=AssetsURLList.ASSETS_DISCARD_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&transId="+transId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'discardWin', style);
}

function do_Cancel(){
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}
</script>
</html>
