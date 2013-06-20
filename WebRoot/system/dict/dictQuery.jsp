<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">		
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
	String enabled = parser.getParameter("enabled");

	String flexValueSetName = parser.getParameter("flexValueSetName");
	String title = "系统字典维护";
	if(!flexValueSetName.equals("")){
		title += "：" + flexValueSetName;
	}
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.system.dict.servlet.EtsFlexValuesServlet">
<script type="text/javascript">
    printTitleBar("<%=title%>");
</script>
    <table border="0" width="100%" class="queryTable" id="table1">
		<tr>
			<td width="7%" align="right">代码：</td>
			<td width="10%"><input type="text" name="code" class="input_style1" style="width:80%" value="<%=parser.getParameter("code")%>"></td>
			<td width="7%" align="right">值：</td>
			<td width="10%"><input type="text" name="value" class="input_style1" style="width:80%" value="<%=parser.getParameter("value")%>"></td>
            <td width="7%" align="right">有效：</td>
			<td width="11%">
				<%=request.getAttribute(WebAttrConstant.ENABLED_RADIO)%>
			</td>
            <td width="7%" align="right">内置：</td>
			<td width="11%"> 
				<%=request.getAttribute(WebAttrConstant.IS_INNER_RADIO)%>
			</td>
			<td width="8%" align="center">
                <%--<img align="middle" src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export();return false;">--%>
            </td>
            <td width="8%" align="center">
              <img align="middle" src="/images/eam_images/search.jpg" alt="查询字典" onclick="do_Search(); return false;"> 
            </td>
            <td width="8%" align="center">
              <img align="middle" src="/images/eam_images/new.jpg" alt="点击新增" onclick="do_Create(); return false;">
            </td>
		</tr>
	</table>
     <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">
	<input type="hidden" name="flexValueSetId" value="<%=parser.getParameter("flexValueSetId")%>">
	<input type="hidden" name="flexValueSetName" value="<%=parser.getParameter("flexValueSetName")%>">
	<input type="hidden" name="flexValueId" value="<%=parser.getParameter("flexValueId")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

		<tr>
			<td height="22" width="15%" align="center">分类代码</td>
			<td height="22" width="15%" align="center">分类名称</td>
			<td height="22" width="10%" align="center">字典代码</td>
			<td height="22" width="15%" align="center">字典值</td>
			<td height="22" width="15%" align="center">字典描述</td>
			<td height="22" width="10%" align="center">是否有效</td>
			<td height="22" width="10%" align="center">是否内置</td>
			<td height="22" width="10%" align="center">可否维护</td>
		</tr>

</table>
         </div>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="100%" border="1" >		
<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("FLEX_VALUE_ID")%>'); return false;">
				<td height="22" width="15%"><%=row.getValue("DICT_TYPE_CODE")%></td>
				<td height="22" width="15%"><%=row.getValue("DICT_TYPE_NAME")%></td>
				<td height="22" width="10%"><%=row.getValue("CODE")%></td>
				<td height="22" width="15%"><%=row.getValue("VALUE")%></td>
				<td height="22" width="15%"><%=row.getValue("DESCRIPTION")%></td>
				<td height="22" width="10%"><%=row.getValue("ENABLED")%></td>
				<td height="22" width="10%"><%=row.getValue("IS_INNER")%></td>
				<td height="22" width="10%"><%=row.getValue("MAINTAIN_FLAG")%></td>
			</tr>
<%
		}
	}
%>		
		</table>
	</div>
    </form>
<div style="position:absolute;top:89%;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">

function do_Search(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    document.mainFrm.act.value="<%=WebActionConstant.QUERY_ACTION%>";
	document.mainFrm.submit();
}

function do_Create(){
	document.mainFrm.flexValueId.value = "";
	document.mainFrm.code.value = "";
	document.mainFrm.value.valuue = "";
	document.mainFrm.act.value="<%=WebActionConstant.NEW_ACTION%>";
	document.mainFrm.submit();
}

function do_ShowDetail(primaryKey){
	document.mainFrm.flexValueId.value = primaryKey;
	document.mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
	document.mainFrm.submit();
}
function do_export(){
	document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	document.mainFrm.submit();
}
</script>