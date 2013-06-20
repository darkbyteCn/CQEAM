<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
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

</head>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
	RequestParser parser = new RequestParser();
    parser.transData(request);

	String barcodeFlag = (String)request.getAttribute(WebAttrConstant.DZYH_BARCODE_OPT);
	String commonFlag = (String)request.getAttribute(WebAttrConstant.DZYH_COMMON_OPT);
	
	String setName = new String((parser.getParameter("catalogSetName")).getBytes("ISO8859_1"),"GBK");
	String title = "低值易耗品维护";
	if(!setName.equals("")){
		title += "：" + setName;
	}
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EamDhCatalogValuesServlet">
<script type="text/javascript">
    printTitleBar("<%=title%>");
</script>
    <table border="0" width="100%" class="queryHeadBg" id="table1">
		<tr>
			<td width="10%" align="right">类别名称：</td>
			<td width="10%">
				<input type="text" name="catalogSetName" style="width:100%" value="<%=setName%>"></td>
			<td width="10%" align="right">目录编号：</td>
			<td width="10%">
				<input type="text" name="catalogCode" style="width:100%" value="<%=parser.getParameter("catalogCode")%>"></td>
            <td width="10%" align="right">品名：</td>
			<td width="10%">
				<input type="text" name="catalogName" style="width:100%" value="<%=parser.getParameter("catalogName")%>"></td>
            <td width="10%" align="center"><img src="/images/eam_images/search.jpg" alt="查询低值易耗目录" onClick="do_Search(); return false;"></td>
            
		</tr>
		<tr>
			<td width="10%" align="right">条码标识：</td>
			<td width="10%" align="left" colspan="2">
				<select name="barcodeFlag" style="width:50%"><%=barcodeFlag%></select></td>
			<td width="10%" align="right">常用标识：</td>
			<td width="10%" align="left" colspan="2">
				<select name="commonFlag" style="width:50%"><%=commonFlag%></select></td>
			<td width="10%" align="center">
			<img src="/images/eam_images/new_add.jpg" alt="点击新增" onClick="do_Create(); return false;"></td>
		</tr>
	</table>
     <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">
	<input type="hidden" name="catalogSetId" value="<%=parser.getParameter("catalogSetId")%>">
	<input type="hidden" name="catalogSetName" value="<%=setName%>">
	<input type="hidden" name="catalogValueId" value="<%=parser.getParameter("catalogValueId")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

		<tr>
			<td height="22" width="8%" align="center">类别名称</td>
			<td height="22" width="8%" align="center">目录编号</td>
			<td height="22" width="10%" align="center">品名</td>
			<td height="22" width="5%" align="center">计量单位</td>
			<td height="22" width="10%" align="center">注明</td>
			<td height="22" width="9%" align="center">条码标识</td>
			<td height="22" width="9%" align="center">常用标识</td>
			<td height="22" width="5%" align="center">是否生效</td>
			<td height="22" width="8%" align="center">创建人</td>
			<td height="22" width="10%" align="center">创建日期</td>
			<td height="22" width="8%" align="center">更新人</td>
			<td height="22" width="10%" align="center">更新日期</td>
		</tr>

</table>
         </div>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="100%" border="1" bordercolor="#666666">		
<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("CATALOG_VALUE_ID")%>'); return false;">
				<td height="22" width="8%" align="center"><%=row.getValue("SET_NAME")%></td>
				<td height="22" width="8%" align="center"><%=row.getValue("CATALOG_CODE")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("CATALOG_NAME")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("UNIT_VALUE")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("DESCRIPTION")%></td>
				<td height="22" width="9%" align="center"><%=row.getValue("BARCODE_FLAG")%></td>
				<td height="22" width="9%" align="center"><%=row.getValue("COMMON_FLAG")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("ENABLED")%></td>
				<td height="22" width="8%" align="center"><%=row.getValue("CREATED_BY")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("CREATION_DATE")%></td>
				<td height="22" width="8%" align="center"><%=row.getValue("LAST_UPDATE_BY")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("LAST_UPDATE_DATE")%></td>
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

<script language="javascript">

function do_Search(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value="<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_Create(){
	mainFrm.catalogValueId.value = "";
	mainFrm.catalogCode.value = "";
	mainFrm.catalogName.value = "";
	mainFrm.barcodeFlag.value="";
	mainFrm.commonFlag.value="";
	mainFrm.act.value="<%=WebActionConstant.NEW_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(primaryKey){
	mainFrm.catalogValueId.value = primaryKey;
	mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
	mainFrm.submit();
}

</script>