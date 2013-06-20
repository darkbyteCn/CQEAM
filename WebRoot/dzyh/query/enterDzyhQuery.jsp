<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
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

	String billStatus=(String)request.getAttribute(WebAttrConstant.DZYH_BILL_STATUS_OPT);
	String organization=(String)request.getAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT);
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EamDhBillHServlet">
<script type="text/javascript">
    printTitleBar("低值易耗台账>>低值易耗品信息录入");
</script>
    <table border="0" width="100%" bordercolor="red" class="queryHeadBg" id="table1">
		<tr>
			<td width="20%" align="right">公&nbsp;&nbsp;&nbsp;&nbsp;司：</td>
			<td width="20%">
				<select name="orgId" style="width:100%"><%=organization%></select>
				</td>
			<td width="15%" align="right">创建时间：</td>
			<td width="30%">
			<input type="text" name="startDate" value="" style="width:40%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
			<img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate);">
            <input type="text" name="endDate" value="" style="width:40%" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
			<img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop(startDate, endDate);">
            <td width="15%" align="center"><img src="/images/eam_images/search.jpg" alt="查询低值易耗信息" onClick="do_Search(); return false;"></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="20%" align="right">单据编号：</td>
			<td width="20%">
				<input type="text" name="billNo" style="width:100%" value="<%=parser.getParameter("billNo")%>"></td>
			<td width="15%" align="right">单据状态：</td>
			<td width="30%">
			<select name="billStatus" style="width:90%"><%=billStatus %></select>
            <td width="15%" align="center"><img src="/images/eam_images/new_add.jpg" alt="新增低值易耗信息" onClick="do_Create(); return false;"></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
	</table>
  <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">

		<tr>
			<td height="22" width="25%" align="center">单据编号</td>
			<td height="22" width="20%" align="center">创建人</td>
			<td height="22" width="30%" align="center">创建时间</td>
			<td height="22" width="25%" align="center">单据状态</td>
		</tr>
	</table>
  </div>
    <div style="overflow-y:scroll;height:380px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="100%" border="1" bordercolor="#666666">
	<input type="hidden" name="billHeaderId" value="<%=parser.getParameter("billHeaderId")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR">
				<td height="22" width="25%" align="center"><%=row.getValue("BILL_NO")%></td>
				<td height="22" width="20%" align="center"><%=row.getValue("EDBH_USERNAME")%></td>
				<td height="22" width="30%" align="center"><%=row.getValue("CREATION_DATE")%></td>
				<td height="22" width="25%" align="center"><%=row.getValue("BILL_STATUS")%></td>
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

function do_Create() {
    mainFrm.billHeaderId.value = "";
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.submit();
}

</script>