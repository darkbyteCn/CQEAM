<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@page import="com.sino.framework.security.bean.SessionUtil"%>
<%@page import="com.sino.ams.system.assetcatelogMaintenanceLNE.dto.AssetcatelogMLneDTO"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ include file="/newasset/headerInclude.htm" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
    <title>资产目录加多维度对应LNE</title>
</head>
 <jsp:include page="/message/MessageProcess"/>
 <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
 <body leftmargin="0" topmargin="0" onload="initPage();" >
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	Row row = null;
%>

  <script type="text/javascript">
    printTitleBar("资产目录加多维度对应LNE");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");  
    var ArrAction1 = new Array(true, "新增", "action_edit.gif", "新增", "do_Create()");
    var ArrAction2 = new Array(true, "删除", "action_cancel.gif", "删除", "do_Delete()");
    var ArrActions = new Array(ArrAction0, ArrAction1,ArrAction2);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();

</script>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.assetcatelogMaintenanceLNE.servlet.AssetcatelogMLneServlet">
	<input type="hidden" name="act">
	<div id="headDiv" style="overflow:hidden;position:absolute;top:50px;left:1px;width:150%">
	    <table class="headerTable" border="1" style="width:100%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td width="2%" align="center"><input type="checkbox" name="subCheck" class="headCheckbox" id="controlBox" onclick="checkAll('subCheck','id')"></td>
				<td width="25%" align="center">类项目节描述</td>
				<td width="15%" align="center">资产目录编码</td>
	            <td width="15%" align="center">多维度描述</td>
	            <td width="10%" align="center">多维度编码</td> 
	            <td width="20%" align="center">逻辑网络元素的名称</td> 
	            <td width="15%" align="center">逻辑网络元素的编码</td> 
	        </tr>
	    </table>
	</div>
	
	<div id="dataDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:150%">
	    <table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
	 <%
			
	 if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				 row=rows.getRow(i);
	%>
			<tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            	<td width="2%" align="center"><input type="checkbox" name="coutentCode" value="<%=row.getValue("CONTENT_CODE")%>|<%=row.getValue("MATCH_CODE")%>"></td>
	            <td width="25%" ><input type="text" class="finput2" readonly value="<%=row.getValue("CONTENT_NAME")%>"></td>
	            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("CONTENT_CODE")%>"></td>
	            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("MATCH_DESC")%>"></td>
	            <td width="10%" ><input type="text" class="finput2" readonly value="<%=row.getValue("MATCH_CODE")%>"></td>
	            <td width="20%"><input type="text" class="finput2" readonly value="<%=row.getValue("NLE_NAME")%>"></td>
	            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("NLE_CODE")%>"></td>
			</tr>
	<%  
		}
	 }
	%>
	</table>
	</div>
	</form>
<% 
    if (rows != null && !rows.isEmpty()) {
%>
<div style="position: absolute; bottom:1px; left: 0; right: 20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>

<%
    }
%>
  </body>
</html>
<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
}

function initPage(){
	do_SetPageWidth();
	do_SetControlBtnEnable();
}

function do_SetControlBtnEnable(){
	var enPic = 5;
	var disPic = 4;
        ShowSinoButton(disPic);
        HideSinoButton(enPic);
}

function do_Search() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Create() {
    mainFrm.act.value = "create";
    mainFrm.submit();
}

function do_Delete() {
    var checkedCount = getCheckedBoxCount("coutentCode");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
        return;
    } else {
    	if(confirm("是否确定逻辑网络元素属性失效？继续请点击“确定”，否则请点击“取消”按钮")){
    		mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION %>";
    		mainFrm.submit();
    	}       
    }
}
</script>
