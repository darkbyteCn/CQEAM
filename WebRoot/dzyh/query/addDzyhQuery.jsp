<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
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
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EamDhBillLServlet">
<script type="text/javascript">
    printTitleBar("低值易耗台账>>部门低值易耗品新增列表");
</script>
    <table border="0" width="100%" bordercolor="red" class="queryHeadBg" id="table1">
		<tr>
			<td width="10%" align="right">目录编号：</td>
			<td width="20%">
				<input type="text" name="edblCatalogCode" style="width:100%" value="<%=parser.getParameter("edblCatalogCode")%>"></td>
			<td width="15%" align="right">品&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
			<td width="30%">
				<input type="text" name="edblItemName" style="width:100%" value="<%=parser.getParameter("edblItemName")%>"></td>
            <td width="15%" align="right"><img src="/images/eam_images/search.jpg" alt="查询新增的低值易耗品" onClick="do_Search(); return false;"></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="10%" align="right">规格型号：</td>
			<td width="20%">
				<input type="text" name="edblItemSpec" style="width:100%" value="<%=parser.getParameter("edblItemSpec")%>"></td>
			<td width="10%" align="right">创建时间：</td>
			<td width="30%">
			<input type="text" name="startDate" value="" style="width:40%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
			<img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate);">
			
			<input type="text" name="endDate" value="" style="width:40%" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
			<img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop(startDate, endDate);">
			</td>
            <td width="15%" align="right"><img src="/images/eam_images/new_add.jpg" alt="新增低值易耗品" onClick="do_CreateOrder(); return false;"></td>
            <td>&nbsp;&nbsp;</td>
            
		</tr>
	</table>
  <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">

	</table>
  </div>
    <div style="overflow-y:scroll;overflow-x:scroll; height:380px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="150%" border="1" bordercolor="#666666">
	<input type="hidden" name="billLineId" value="<%=parser.getParameter("billLineId")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

		<tr class="headerTable">
			<td height="22" width="5%" align="center">目录编号</td>
			<td height="22" width="7%" align="center">品 名</td>
			<td height="22" width="10%" align="center">规格型号</td>
			<td height="22" width="4%" align="center">数 量</td>
			<td height="22" width="4%" align="center">单 价</td>
			<td height="22" width="14%" align="center">使用部门</td>
			<td height="22" width="4%" align="center">领用人</td>
			<td height="22" width="12%" align="center">地 点</td>
			<td height="22" width="7%" align="center">领用日期</td>
			<td height="22" width="10%" align="center">厂 家</td>
			<td height="22" width="15%" align="center">备 注</td>
		</tr>
<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("BILL_LINE_ID")%>'); return false;">
				<td height="22" width="5%" align="center"><%=row.getValue("EDBL_CATALOG_CODE")%></td>
				<td height="22" width="7%" align="center"><%=row.getValue("EDBL_ITEM_NAME")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("EDBL_ITEM_SPEC")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("QUANTITY")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("PRICE")%></td>
				<td height="22" width="14%" align="center"><%=row.getValue("EDBL_DEPT_NAME")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("EDBL_USER_NAME")%></td>
				<td height="22" width="12%" align="center"><%=row.getValue("EDBL_WORKORDER_OBJECT_NAME")%></td>
				<td height="22" width="7%" align="center"><%=row.getValue("LAST_LOC_CHG_DATE")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("MANUFACTORY")%></td>
				<td height="22" width="15%" align="center"><%=row.getValue("REMARK")%></td>
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
	mainFrm.billLineId.value = primaryKey;
	mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
	mainFrm.submit();
}

function do_CreateOrder() {
 var style = "width=1425,height=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
 var url = "/servlet/com.sino.ams.dzyh.servlet.EamDhBillLServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var win = "bjOrder";
    window.open(url, win, style);
}

function do_Create() {
    mainFrm.billLineId.value = "";
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.submit();
}

</script>