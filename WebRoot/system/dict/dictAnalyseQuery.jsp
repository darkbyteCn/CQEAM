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
	String enabled = parser.getParameter("enabled");
	String opartor = System.getProperty("file.separator");
	String remark = null;
	if(opartor.equals("\\")){
	     remark = new String(((String)request.getAttribute("remark")).getBytes("ISO-8859-1"),"GBK");
	}else{
	     remark = (String)request.getAttribute("remark");
	}
	if(remark == null || remark.equals(""))remark = parser.getParameter("remark");
	String[] descs = remark.split(",");
	int len = descs.length;
    String flexValueSetName = (String)request.getAttribute("flexValueSetName");
	String title = "资产分析系统字典维护";
	if(flexValueSetName != null && !flexValueSetName.equals("")){
		title += ">>" + flexValueSetName;
	}
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet">
<script type="text/javascript">
    printTitleBar("<%=title%>");
</script>
    <table border="0" width="100%" class="queryHeadBg" id="table1">
		<tr>
			<td width="10%" align="right">代码：</td>
			<td width="10%"><input type="text" name="code" style="width:100%" value="<%=parser.getParameter("code")%>"></td>
			<td width="8%" align="right">值：</td>
			<td width="10%"><input type="text" name="value" style="width:100%" value="<%=parser.getParameter("value")%>"></td>
            <td width="10%" align="right">有效：</td>
			<td width="15%">
				<%=request.getAttribute(WebAttrConstant.ENABLED_RADIO)%>
			</td>
            <td width="10%" align="right">内置：</td>
			<td width="15%">
				<%=request.getAttribute(WebAttrConstant.IS_INNER_RADIO)%>
			</td>
            <td width="8%" align="center"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()"></td>
            <td width="8%" align="center"><img src="/images/eam_images/search.jpg" alt="查询字典" onClick="do_Search(); return false;"></td>
            <td width="8%" align="center"><img src="/images/eam_images/new_add.jpg" alt="点击新增" onClick="do_Create(); return false;"></td>
		</tr>
	</table>
     <div style="left:1px;width:200%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">
	<input type="hidden" name="flexValueSetId" value="<%=parser.getParameter("flexValueSetId")%>">
	<input type="hidden" name="flexValueSetName" value="<%=parser.getParameter("flexValueSetName")%>">
	<input type="hidden" name="flexValueId" value="<%=parser.getParameter("flexValueId")%>">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

		<tr>
			<td height="22" width="6%" align="center">分类代码</td>
			<td height="22" width="6%" align="center">分类名称</td>
			<td height="22" width="3%" align="center">字典代码</td>
			<td height="22" width="7%" align="center">字典值</td>
			<td height="22" width="11%" align="center">字典描述</td>
			<td height="22" width="3%" align="center">是否有效</td>
			<td height="22" width="3%" align="center">是否内置</td>
			<td height="22" width="3%" align="center">可否维护</td>
			<td height="22" width="3%" align="center">文件版本</td>
			<% 
			  for(int i = 0; i < descs.length; i++){
				   if(!descs[i].equals("") && !descs[i].equals(" ")){
			 %>
			   <td height="22" width="7%" align="center"><%=descs[i]%></td>
			<%
			    }
			  }
			%>
		</tr>

</table>
         </div>
    <div style="overflow-y:scroll;height:362px;width:200%;left:1px;margin-left:0" align="left">
	    <table width="100%" border="1" bordercolor="#666666">		
<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("FLEX_VALUE_ID")%>'); return false;">
				<td height="22" width="6%"><%=row.getValue("DICT_TYPE_CODE")%></td>
				<td height="22" width="6%"><%=row.getValue("DICT_TYPE_NAME")%></td>
				<td height="22" width="3%"><%=row.getValue("CODE")%></td>
				<td height="22" width="7%"><%=row.getValue("VALUE")%></td>
				<td height="22" width="11%"><%=row.getValue("DESCRIPTION")%></td>
				<td height="22" width="3%"><%=row.getValue("ENABLED")%></td>
				<td height="22" width="3%"><%=row.getValue("IS_INNER")%></td>
				<td height="22" width="3%"><%=row.getValue("MAINTAIN_FLAG")%></td>
				<td height="22" width="3%"><%=row.getValue("FILE_VERSION")%></td>
				<% 
				  for(int j = 0; j < descs.length; j++){
					   if(!descs[j].equals("") && !descs[j].equals(" ")){
					    String ind = "ATTRIBUTE"+(j+1);
				 %>
				   <td height="22" width="7%" align="center"><%=row.getValue(ind)%></td>
				<%
				    }
				  }
				%>
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
</html>
<script language="javascript">

function do_Search(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value="<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?flexValueSetName=<%=flexValueSetName%>&remark=<%=remark%>";
	mainFrm.submit();
}

function do_Create(){
	mainFrm.flexValueId.value = "";
	mainFrm.code.value = "";
	mainFrm.value.valuue = "";
	mainFrm.act.value="<%=WebActionConstant.NEW_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?flexValueSetName=<%=flexValueSetName%>&remark=<%=remark%>";
	mainFrm.submit();
}

function do_ShowDetail(primaryKey){
	mainFrm.flexValueId.value = primaryKey;
	mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?flexValueSetName=<%=flexValueSetName%>&remark=<%=remark%>";
	mainFrm.submit();
}
function do_export(){    
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?flexValueSetName=<%=flexValueSetName%>&remark=<%=remark%>";
    mainFrm.submit();
}
</script>