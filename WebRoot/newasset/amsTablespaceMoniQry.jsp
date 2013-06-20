<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-6-15
  Time: 15:40:17
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<%
    AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    String orgId = userAccount.getOrganizationId();
%>
    <title>表空间占用情况</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage()" onkeydown="autoExeFunction('do_Search();')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.AmsTablespaceMonitorServlet">
    <input type="hidden" name="act">
    <input type="hidden" name="companyName">
    <script type="text/javascript">
        printTitleBar("表空间占用情况")
    </script>
    <table width="100%" border="1" class="queryHeadBg">
        <tr>
            <td align="right"><img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();"><img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();"></td>
        </tr>
    </table>
</form>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:42px;left:1px;width:830px">
        <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="20%" align="center">表空间名</td>
                <td width="20%" align="center">表空间大小(B)</td>
                <td width="20%" align="center">表空间剩余大小(B)</td>
                <td width="20%" align="center">表空间使用大小(B)</td>
                <td width="20%" align="center">使用率</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:350px;width:847px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                Row row = null;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22">
                <td width="20%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("TABLESPACE_NAME")%>"></td>
                <td width="20%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("TABLESPACE_SIZE")%>"></td>
                <td width="20%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("TABLESPACE_FREE_SIZE")%>"></td>
                <td width="20%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("TTABLESPACE_USE_SIZE")%>"></td>
                <td width="20%" align="center"><input type="text" class="finput2" style="cursor:hand" readonly value="<%=row.getStrValue("TABLESPACE_USE_RATE")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
<%
	if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:425px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function initPage(){
        do_SetPageWidth();
    }
    function do_Search() {
        mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
        document.mainFrm.target = "_self";
        mainFrm.submit();
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }
    function do_Export(){
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.target = "_self";
        mainFrm.submit();
    }
</script>