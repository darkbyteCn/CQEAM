<%@ page import="com.sino.ams.spare.repair.dto.AmsVendorInfoDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<title>备件厂商维护</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    AmsVendorInfoDTO dto = (AmsVendorInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.spare.servlet.SpareVendorServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
   printTitleBar("备件厂商维护")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
            <td width="10%" align="right">厂商：</td>
			<td width="25%" align="left">
                <input type="text" name="vendorName" style="width:100%" value="<%=dto.getVendorName()%>">
            </td>
            <td width="65%" align="right">
                <img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
                <img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
            </td>
		</tr>
	</table>
	<input type="hidden" name="act" value="">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
        <tr height="22">
            <td width="10%" align="center">厂商代码</td>
            <td width="25%" align="center">厂商名称</td>
        </tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
        <tr height="22" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("VENDOR_ID")%>')">
            <td width="10%" align="center"><input type="text" class="finput" readonly value="<%=row.getStrValue("VENDOR_ID")%>"></td>
            <td width="25%" align="center"><input type="text" class="finput" readonly value="<%=row.getStrValue("VENDOR_NAME")%>"></td>
        </tr>
<%
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:430px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ShowDetail(vendorId) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet?vendorId=" + vendorId;
    mainFrm.submit();
}

function do_CreateOrder() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet";
    mainFrm.submit();
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet";
    mainFrm.submit();
}
</script>