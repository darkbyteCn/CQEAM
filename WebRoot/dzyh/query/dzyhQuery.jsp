<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<jsp:directive.page import="com.sino.ams.dzyh.constant.DzyhActionConstant"/>
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
    
    String parameter=request.getParameter("dzyh");
    String cmd="";
    if(parameter.equals(DzyhActionConstant.CHAXUN_ACTION)){
    	cmd="查询";
    }else if(parameter.equals(DzyhActionConstant.WEIHU_ACTION)){
    	cmd="维护";
    }

%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EtsSystemItemInfoServlet">
<script type="text/javascript">
    printTitleBar("低值易耗台账>>低值易耗品<%=cmd%>");
</script>
    <table border="0" width="100%" bordercolor="red" class="queryHeadBg" id="table1">
		<tr>
			<td width="10%" align="right">目录编号：</td>
			<td width="15%">
				<input type="text" name="eiiItemCategory2" style="width:100%" value="<%=parser.getParameter("eiiItemCategory2")%>"></td>
			<td width="10%" align="right">条码号：</td>
			<td width="15%">
				<input type="text" name="barcode" style="width:100%" value="<%=parser.getParameter("barcode")%>"></td>
            <td width="10%" align="right">品&nbsp;&nbsp;名：</td>
			<td width="15%">
				<input type="text" name="eiiItemName" style="width:100%" value="<%=parser.getParameter("eiiItemName")%>"></td>
			<td width="10%" align="right">规格型号：</td>
			<td width="15%">
				<input type="text" name="eiiItemSpec" style="width:100%" value="<%=parser.getParameter("eiiItemSpec")%>"></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="10%" align="right">使用部门：</td>
			<td width="15%">
				<input type="text" name="responsibilityDept" style="width:100%" value="<%=parser.getParameter("responsibilityDept")%>"></td>
			<td width="10%" align="right">地&nbsp;&nbsp;点：</td>
			<td width="15%">
				<input type="text" name="eiiWorkorderObjectName" style="width:100%" value="<%=parser.getParameter("eiiWorkorderObjectName")%>"></td>
            <td width="10%" align="right">保管人：</td>
			<td width="15%">
				<input type="text" name="maintainUser" style="width:100%" value="<%=parser.getParameter("maintainUser")%>"></td>
			<td width="10%" align="right">厂&nbsp;&nbsp;&nbsp;&nbsp;家：</td>
			<td width="15%">
				<input type="text" name="attribute3" style="width:100%" value="<%=parser.getParameter("attribute3")%>"></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="10%" align="right">领用日期：</td>
			<td width="40%" colspan="2">
			<input type="text" name="startDate" value="" style="width:80%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
			<img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate);">
				<!-- <input type="text" name="lastLocChgDate" style="width:80%" value=""> -->
			</td>
			<td width="40%" colspan="2">
			<input type="text" name="endDate" value="" style="width:80%" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
			<img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop(startDate, endDate);">
            <td width="50%" colspan="3" align="center"><img src="/images/eam_images/search.jpg" alt="低值易耗品查询" onClick="do_Search(); return false;">
            </td>
            
		</tr>
	</table>
  <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">

	</table>
  </div>
    <div style="overflow-y:scroll;overflow-x:scroll; height:380px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="150%" border="1" bordercolor="#666666">
	<input type="hidden" name="dzyh" value="<%=parameter%>">
	<input type="hidden" name="systemid" value="<%=parser.getParameter("systemid")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

		<tr class="headerTable">
			<td height="22" width="4%" align="center">物品编号</td>
			<td height="22" width="4%" align="center">目录编号</td>
			<td height="22" width="5%" align="center">条码号</td>
			<td height="22" width="7%" align="center">品 名</td>
			<td height="22" width="10%" align="center">规格型号</td>
			<td height="22" width="4%" align="center">数 量</td>
			<td height="22" width="4%" align="center">单 价</td>
			<td height="22" width="14%" align="center">使用部门</td>
			<td height="22" width="4%" align="center">领用人</td>
			<td height="22" width="12%" align="center">地 点</td>
			<td height="22" width="4%" align="center">保管人</td>
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
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("SYSTEMID")%>'); return false;">
				<td height="22" width="4%" align="center"><%--<%=row.getValue("")%>--%>&nbsp;</td>
				<td height="22" width="4%" align="center"><%=row.getValue("EII_ITEM_CATEGORY2")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("BARCODE")%></td>
				<td height="22" width="7%" align="center"><%=row.getValue("EII_ITEM_NAME")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("EII_ITEM_SPEC")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("ITEM_QTY")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("PRICE")%></td>
				<td height="22" width="14%" align="center"><%=row.getValue("EII_DEPT_NAME")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("EII_USER_NAME")%></td>
				<td height="22" width="12%" align="center"><%=row.getValue("EII_WORKORDER_OBJECT_NAME")%></td>
				<td height="22" width="4%" align="center"><%=row.getValue("MAINTAIN_USER")%></td>
				<td height="22" width="7%" align="center"><%=row.getValue("LAST_LOC_CHG_DATE")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("ATTRIBUTE3")%></td>
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
	mainFrm.systemid.value = primaryKey;
	mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
	mainFrm.submit();
}

</script>