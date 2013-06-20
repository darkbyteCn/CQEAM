<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@page import="com.sino.base.constant.db.QueryConstant"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.log.dto.SfUserLogDTO" %>
<html>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/input.css">
</head>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	SfUserLogDTO dto = (SfUserLogDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	
	String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
    //if( null == allResName ){
    	if("PERSONAL".equals(dto.getColumeType())){
    		allResName = "个人日志查询";
        }else{
        	allResName = "系统日志查询";
        }
    	
    //}
    
    
%>
<form action="/servlet/com.sino.ams.system.log.servlet.SfUserLogServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%= allResName %>");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");
    var ArrAction1 = new Array(true, "导出", "toexcel.gif", "导出", "do_ShowExcel()");
    var ArrActions = new Array(ArrAction0, ArrAction1);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="excelType"/>
    <input type="hidden" name="columeType" value='<%=dto.getColumeType()%>'>
    <table  style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="8%" align="right" >请求时间：</td>
            <td width="12%"><input type="text" name="startDate" value="<%=dto.getStartDate()%>" class="input_style1" style="width:90%;cursor:pointer" title="点击选择日期" readonly onclick="gfPop.fStartPop(startDate,endDate)"></td>
            <td width="8%" align="right">到：</td>
            <td width="12%"><input type="text" name="endDate" value="<%=dto.getEndDate()%>" class="input_style1" style="width:90%;cursor:pointer" title="点击选择日期" readonly onclick="gfPop.fEndPop(startDate,endDate)"></td>
<% 
    if("PERSONAL".equals(dto.getColumeType())){
%>
	        <input name="userAccount" value="" type="hidden"/>
<% 
    } else {
%>
	        <td width="8%" align="right">用户姓名：</td>
	        <td width="12%"><input class="input_style1" name="userAccount" value="<%=dto.getUserAccount() %>" style="width:90%;"/></td>
<%
    }
%>
	        <td width="8%" align="right">请求资源：</td>
	        <td width="12%"><input class="input_style1" name="resName" value="<%=dto.getResName() %>" style="width:90%;"/></td>
	        <td width="8%" align="right">客户端IP：</td>
	        <td width="12%"><input class="input_style1" name="clientIp" value="<%=dto.getClientIp() %>" style="width:90%;"/></td>
        </tr>
	</table>

</form>
<div id="ddDiv" style="position:absolute;z-index:2;top:130px;left:350px;background-color:azure;border:1px;width:300px;height:50px;text-align:center;visibility:hidden;">
    <table border = "0" width="100%">
       <tr style="cursor:move;background:#0997F7;color:white;font:bold;height:20">
            <td>&nbsp;&nbsp;<span key="PleaseSelectFunction"/></td>
            <td align="right"><div style="padding-right:10px"><font face="webdings" style="cursor:pointer" onclick="do_ShowExcel()">r</font></div></td>
        </tr>
       <tr>
           <td width="80%" nowrap="nowrap" align="center">
                <input type="button" value="导出EXCEL" onclick="do_Export('xls')"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <input type="button" value="导出CSV" onclick="do_Export('csv')"/>
           </td>
       </tr>
     </table>
     <iframe   src="" frameborder="0"  style="position:absolute;   visibility:inherit;   top:0px;   left:0px;  width:expression(this.parentNode.offsetWidth);   height:expression(this.parentNode.offsetHeight);   z-index:-1;"></iframe>
</div>
	<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:75px;left:1px;width:100%"  >
		<table class="headerTable" border="1" width="100%" style="text-align:center">
	        <tr height="23px">
		        <%--<td width="5%">用户ID</td>--%>
	            <td width="12%">用户姓名</td>
	            <td width="12%">用户账号</td>
	            <td width="12%">客户端IP</td>
	            <td width="12%">应用服务器</td>
	            <td width="16%">请求资源</td>
                <td width="12%">操作类型</td>
                <td width="24%">请求时间</td>
	        </tr>
		</table>
	</div>
<%
	if (hasData) {
%>
	<div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:74px;left:1px" align="left" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1"  style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
			String actionType="";
			if(row.getValue("ACTION_TYPE").toString().trim().equals("UNKNOWN"))
				{
				actionType="查询";
				}
			else
				{
				actionType=row.getValue("ACTION_TYPE").toString();
				}
%>                                            
	        <tr class="dataTR" align="center">
			  <%--<td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("USER_ID")%>"></td>--%>
              <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("USERNAME")%>"></td>
	          <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_ACCOUNT")%>"></td>
	          <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("CLIENT_IP")%>"></td>
	          <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("SERVER")%>"></td>
	          <td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("RES_NAME")%>"></td>
              <td width="12%"><input type="text" class="finput" readonly value="<%=actionType %>"></td>
              <td width="24%"><input type="text" class="finput2" readonly value="<%=row.getValue("LOG_TIME")%>"></td>
	        </tr>
<%
		}
%>	
	    </table>
	</div>
<div style="position:absolute;top:400px;left:0; right:20" id="pageNaviDiv">
<%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%	
	}
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_Search() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ShowExcel() {
	var _d = document.getElementById("ddDiv");
	var left = event.clientX;
	var top = event.clientY;
	_d.style.position = "absolute";
	_d.style.top = top + event.srcElement.offsetHeight;
	_d.style.left = left;
	if (_d.style.visibility == "hidden") {
		_d.style.visibility = "visible";
	}else {
		_d.style.visibility = "hidden";
	}
}

function do_Export(type) {
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.excelType.value = type;
    mainFrm.submit();
}
</script>
</html>
