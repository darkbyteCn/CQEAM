<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>Help URL资源查询</title>
<link rel="stylesheet" type="text/css" href="/style/css/main.css">
<script language="javascript" src="/style/js/jslib.js"></script>
</head>
<html>
<body leftmargin="1" topmargin="0" onload="doChecked(); init();" onkeydown="do_check()">
<%
	RequestParser reqParser = new RequestParser();
	reqParser.transData(request);
	String resId = reqParser.getParameter("resId");
    String resName = reqParser.getParameter("resName");
    String  resUrl = reqParser.getParameter("resUrl");
    String show = reqParser.getParameter("show");
    String action = reqParser.getParameter("action");
    if(show.equals("show")){
        resName="";
        resUrl="";
        resId="";
    }
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ies.systemsetup.resource.servlet.HelpResourceProcessServlet">
	<input type="hidden" name="resId" value="<%=resId%>">
	<input type="hidden" name="act" value="<%=action%>">
	<input type="hidden" name="resParId">
    <input type="hidden" name="isdisable" value="<%=reqParser.getParameter("disabled")%>">
    <table border="0" width="100%" align="left" id="table1">
		<tr>
			<td width="8%">名称：</td>
			<td width="15%">
			<input type="text" name="resName" style="width:100%" value="<%=resName%>"></td>
			<td width="8%">URL：</td>
			<td width="32%">
			<input type="text" name="resUrl" style="width:100%" value="<%=resUrl%>">
            </td>
            <td width="11%" align="right">栏目状态：</td>
			<td width="10%">
			<select name="disabled" id="disabled" style="width:100%" >
                <option value="">请选择</option>
                <option value="Y">失效</option>
                <option value="N">有效</option>
            </select>
            </td>
            <td width="8%" align="center"><img src="/images/eam_images/search.jpg" alt="查询栏目" onClick="do_SearchResource(); return false;"></td>
            <td width="8%" align="center"><img src="/images/eam_images/new_add.jpg" alt="点击新增" onClick="do_CreateResource(); return false;"></td>
		</tr>
	</table>
</form>
	<table width="683" align="left" border="1" cellpadding="2" cellspacing="0" style="position:absolute;left:1px;top:30px" class="headerTable">

		<tr>
			<td height="22" width="6%" align="center">编号</td>
			<td height="22" width="15%" align="center">栏目名称</td>
			<td height="22" width="61%" align="center">栏目URL</td>
			<td height="22" width="14%" align="center">父栏目名称</td>
		</tr>
</table>
    <div style="overflow-y:scroll;height:362;width:700;position:absolute;top:54px;left:1px;margin-left:0px" align="left">
	    <table width="100%" border="1" bordercolor="#666666">		
<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("RES_ID")%>'); return false;">
				<td style="word-wrap:break-word" height="22" width="6%"  ><%=row.getValue("RES_ID")%></td>
				<td style="word-wrap:break-word" height="22" width="15%" ><%=row.getValue("RES_NAME")%></td>
				<td style="word-wrap:break-word" height="22" width="61%" ><%=row.getValue("RES_URL")%></td>
				<td style="word-wrap:break-word" height="22" width="14%" ><%=row.getValue("PAR_NAME")%></td>
			</tr>
<%
		}
	}
%>		
		</table>
	</div>
<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
</body>
</html>
<script language="javascript">

function do_SearchResource(){
    mainFrm.resId.value="";
    mainFrm.act.value="";
    mainFrm.action = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?action=<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_CreateResource(){
	mainFrm.resParId.value = mainFrm.resId.value;
	mainFrm.resId.value = "";
	mainFrm.resName.value = "";
	mainFrm.resUrl.value = "";
	mainFrm.action = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?action=<%=WebActionConstant.CREATE_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(resId){
//    alert(resId);
        mainFrm.action = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?action=<%=WebActionConstant.DETAIL_ACTION%>&resId=" + resId;
		mainFrm.submit();
}

    function doChecked(){
        var select = document.getElementById("disabled");
        for(var i=0;i<select.length;i++){
            if(select.options[i].value==document.mainFrm.isdisable.value){
                select.options[i].selected=true;
            }
        }
        refreshTree();
    }

    function refreshTree(){
        var action = document.forms[0].act.value;
        if(action == "<%=WebActionConstant.UPDATE_ACTION%>" || action == "<%=WebActionConstant.DELETE_ACTION%>" || action == "<%=WebActionConstant.CREATE_ACTION%>"){
            parent.contents.location.reload();
            parent.parent.banner.location="/servlet/com.sino.ies.resource.servlet.HelpResourceServlet?fromPage=top";
        }
    }
    function do_check(){
        if(event.keyCode == 13){
            do_SearchResource();
        }
    }
    
function init() {
	document.onkeydown = showKeyDown
}

function showKeyDown(evt) {
	evt = (evt) ? evt : window.event
	if (evt.keyCode == 121){
		var helpCode = "ams31";
	    var url = "/servlet/com.sino.appbase.help.servlet.HelpProcessServlet?helpCode=" + helpCode + ".htm";
	    var pop = "dialogWidth=900px;dialogHeight=480px";
	    //window.open(url, "", pop);
	    var retArr = window.showModalDialog(url, '', pop);
	
	}
}
    
    
</script>