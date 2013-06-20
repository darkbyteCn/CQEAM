<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
//RowSet sets = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
//session.setAttribute(QueryConstant.SPLIT_DATA_VIEW,sets);
//session.setAttribute(QueryConstant.SPLIT_PAGE_HTML,request.getAttribute(QueryConstant.SPLIT_PAGE_HTML));
Object obj=request.getAttribute("DTO");
session.setAttribute("DTO",obj);
%>	
	<script>
		var tabBox = new TabBox("tab");
	    tabBox.addtab("modelListDiv", "角色信息");
	    tabBox.addtab("modelDataDiv", "栏目信息");
	    tabBox.init();
	    window.onload=function (){
	    	document.getElementById("$$$waitTipMsg1$$$").style.visibility = "hidden";
	    }
	</script>
	<div id="$$$waitTipMsg1$$$" style="position:absolute; bottom:45%; left:5; z-index:10;visibility: visible" >
						   <table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
						   <td width="30%"></td>
						   <td bgcolor="#ff9900">
						   <table width="100%" height="60" border="0" cellspacing="2" cellpadding="0">
						   <tr>
						   <td bgcolor="#eeeeee" align="center">正在请求数据，请稍候......<img src="/images/wait.gif" alt=""></td>
						   </tr>
						   </table>
						   </td>
						   <td width="30%"></td>
						   </tr>
						   </table>
	</div>
<div id="modelListDiv">
	<iframe id="modelDataFrm" style="width: 100%; height: 100%" border="0"
		frameborder="0" src="com.sino.ams.newasset.rolequery.servlet.TestServlet2?act=juese"></iframe>
</div>
<div id="modelDataDiv" style='display: none;overflow: scroll;'>
	<iframe id="modelListFrm" style="width: 100%; height: 100%" border="0"
		frameborder="0" src="com.sino.ams.newasset.rolequery.servlet.TestServlet?act=lanmu"</iframe>
</div>
