<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%@page import="com.sino.ams.yearchecktaskmanager.dto.*"%>


<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    AssetsYearCheckTaskQueryDTO dto = (AssetsYearCheckTaskQueryDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = false;
    if (rows != null && !rows.isEmpty()) {
        hasData = true;
    }
%>

<html>
<style>

FORM {
    margin-top: 0;
    margin-bottom: 0;
}
</style>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('2.1.1');" onkeydown="autoExeFunction('do_Search()');">
<script type="text/javascript">

    <%if(dto.getTaskType().equals("send")){ %>
          printTitleBar("已盘点任务查询");
    <%}else if(dto.getTaskType().equals("received")){ %>
          printTitleBar("个人盘点任务查询");
    <%} %>
    
</script> 
<form name="mainFrm" method="post" action="" > 
<jsp:include page="/message/MessageProcess"/>
<table width="100%" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <tr>
        <td width="8%" align="right">任务名称：</td>
        <td width="14%"><input class="input_style1" type="text" name="orderName" style="width:100%" value="<%=dto.getOrderName()%>"></td>
        <td width="8%" align="right">任务编号：</td>
        <td width="14%"><input class="input_style1" type="text" name="orderNumber" style="width:100%" value="<%=dto.getOrderNumber()%>"></td>
        <td width="56%" align="center">
           <img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:pointer" onclick="do_Search();" title="查询">
        </td>
    </tr>
</table>
<input type="hidden" name="act" value="">
<input type="hidden" name="taskType" value="<%=dto.getTaskType()%>">

</form>
<input type="hidden" name="helpId" value=""> 
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:48px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="100%">
        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="25%">任务编号</td>
            <td align=center width="25%">任务名称</td>
            <%if(dto.getTaskType().equals("send")){ %>
            <td align=center width="22%">任务接收人</td>
            <td align=center width="25%">接收人所属部门</td>
            <%}else if(dto.getTaskType().equals("received")){ %>
            <td align=center width="22%">任务下发人</td>
            <td align=center width="25%">任务下发日期</td>
            <%} %>
			<td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>

<% 
    if (hasData) {
%>
<div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:71px;left:0px;height:485px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
		Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
    <tr class="dataTR" onclick="executeClick(this)">
        <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" ></td></td>
        <td width="25%" align="center" style="cursor:pointer" >
            <input type="text" class="finput2" readonly name="orderNumber" value="<%=row.getValue("ORDER_NUMBER")%>"></td>
        <td width="25%" align="left" style="cursor:pointer" >
            <input type="text" class="finput2" readonly  name="orderName"  value="<%=row.getValue("ORDER_NAME")%>"></td>
         <%if(dto.getTaskType().equals("send")){ %>
        <td width="22%" align="left" style="cursor:pointer" >
            <input type="text" class="finput2" readonly value="<%=row.getValue("IMPLEMENT_NAME")%>">
         </td>
        <td width="25%" align="left" style="cursor:pointer" >
            <input type="text" class="finput" readonly value="<%=row.getValue("IMPLEMNET_DEPT_NAME")%>"></td>
         <% }else if(dto.getTaskType().equals("received")){%>
            <td width="22%" align="left" style="cursor:pointer" >
           <input type="text" class="finput2" readonly value="<%=row.getValue("DISTRUBTE_BY_NAME")%>">
         </td>
        <td width="25%" align="left" style="cursor:pointer" >
            <input type="text" class="finput" readonly value="<%=row.getValue("SEND_DATE")%>"></td>
         <%} %>
		<td style="display:none">
		 <%if(dto.getTaskType().equals("send")){ %>
			<input type="hidden" name="implementBy" value="<%=row.getValue("IMPLEMENT_BY")%>">
			<input type="hidden" name="implementDeptId" value="<%=row.getValue("IMPLEMENT_DEPT_ID")%>">
			 <% }else if(dto.getTaskType().equals("received")){%>
			 <input type="hidden" name="distrubteBy" value="<%=row.getValue("DISTRUBTE_BY")%>">
			   <%} %>
		</td>

    </tr>
 
<% 
		}
%>
</table>
</div>
<div id="pageNaviDiv"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<iframe style="display:none" src="" name="downFrm"></iframe>
<script type="text/javascript">

function initPage() {
    do_SetPageWidth();
}

function do_Search() {
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckQueryServlet";
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

</script>
