<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
	<script language="javascript" src="/WebLibary/js/calendar.js"></script>

</head>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
	RequestParser parser = new RequestParser();
    parser.transData(request);
    
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.expand.servlet.EtsItemInfoExServlet">
<script type="text/javascript">
    printTitleBar("车辆管理");
</script>
    <table border="0" width="100%" bordercolor="red" class="queryHeadBg" id="table1">
		<tr>
			<td width="15%" align="right">条码号：</td>
			<td width="20%">
				<input type="text" name="barcode" style="width:100%" value="<%=parser.getParameter("barcode")%>"></td>
			<td width="10%" align="right">名&nbsp;&nbsp;称：</td>
			<td width="20%">
				<input type="text" name="itemName" style="width:100%" value="<%=parser.getParameter("itemName")%>"></td>
            <td width="10%" align="right">型&nbsp;&nbsp;号：</td>
			<td width="20%">
				<input type="text" name="itemSpec" style="width:100%" value="<%=parser.getParameter("itemSpec")%>"></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="10%" align="right">牌&nbsp;&nbsp;照：</td>
			<td width="15%">
				<input type="text" name="attribute1" style="width:100%" value="<%=parser.getParameter("attribute1")%>"></td>
			<td width="10%" align="right">驾驶员：</td>
			<td width="15%">
				<input type="text" name="attribute4" style="width:100%" value="<%=parser.getParameter("attribute4")%>"></td>
            <td width="50%" colspan="3" align="center"><img src="/images/eam_images/search.jpg" alt="车辆管理查询" onClick="do_Search(); return false;">
            </td>
            <td>&nbsp;&nbsp;</td>
		</tr>
	</table>
  <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">

		<tr class="headerTable">
			<td height="22" width="6%" align="center">条码号</td>
			<td height="22" width="10%" align="center">名称</td>
			<td height="22" width="10%" align="center">型号</td>
			<td height="22" width="6%" align="center">牌照</td>
			<td height="22" width="6%" align="center">驾驶员</td>
			<td height="22" width="8%" align="center">启用日期</td>
			<td height="22" width="6%" align="center">折旧年限</td>
			<td height="22" width="6%" align="center">责任人</td>
		</tr>
	</table>
  </div>
    <div style="overflow-y:scroll; height:300px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="100%" border="1" bordercolor="#666666">
	<input type="hidden" name="itemInfoExId" value="<%=parser.getParameter("itemInfoExId")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("ITEM_INFO_EX_ID")%>'); return false;">
				<td height="22" width="6%" align="center"><%=row.getValue("BARCODE")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("ITEM_NAME")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
				<td height="22" width="6%" align="center"><%=row.getValue("ATTRIBUTE1")%></td>
				<td height="22" width="6%" align="center"><%=row.getValue("ATTRIBUTE4")%></td>
				<td height="22" width="8%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
				<td height="22" width="6%" align="center"><%=row.getValue("LIFE_IN_YEARS")%></td>
				<td height="22" width="6%" align="center"><%=row.getValue("EMPLOYEE_NAME")%></td>
			</tr>
<%
		}
	}
%>		
		</table>
	</div>
    </form>

<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script language="javascript">

function do_Search(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value="<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(primaryKey){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.itemInfoExId.value = primaryKey;
	mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
	mainFrm.submit();
}

</script>