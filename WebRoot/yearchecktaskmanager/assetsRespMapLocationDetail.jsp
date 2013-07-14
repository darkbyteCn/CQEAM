<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "No-cache");

RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
Row row = null;

String action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsRespMapLocationServlet";
String templateFile = "/document/template/AssetsRespMapLocation.zip";
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>导入盘点责任人和地点匹配关系明细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" action="<%= action %>" method="post">
<jsp:include page="/message/MessageProcess"/>
    <script type="text/javascript">
        printTitleBar("批量导入角色权限明细信息"); 
        var ArrAction0 = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
        var ArrActions = new Array(ArrAction0);
        printToolBar();
    </script>
    <input type="hidden" name="act">
    <input type="hidden" name="flag" value="0">
<div id ="headDiv" style="position:absolute;width:100%;overflow:hidden;top:66px;padding:0px; margin:0px;">
    <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22">
        	<td width="5%"  align="center">excel行号</td>
            <td width="7%" align="center">盘点责任人姓名</td>
            <td width="7%" align="center">盘点责任人员工编号</td>
            <td width="15%" align="center">盘点责任人的部门</td>
            <td width="15%" align="center">无线地点组合编号</td>
            <td width="7%" align="center">无线地点组合描述</td>
            <td width="13%" align="center">错误信息</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:hidden;height:368px;width:100%;position:absolute;top:91px;left:1px" align="left">
    <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
		    <td width="5%" ><input type="text" class="finput2" readonly value="<%=row.getValue("EXCEL_ROW_NUMBER")%>"></td>
		    <td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("USER_NAME")%>"></td>
            <td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("DEPT_NAME")%>"/></td>
			<td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NMAE")%>"></td>
            <td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("ERROR_MESSAGE")%>"></td>
</tr>
 <%
        }
    }
%>
  </table>
    </div>
</form>
</body>
<script type="text/javascript">

    function do_Export() {
        document.mainFrm.act.value = "EXPORT";
        document.mainFrm.submit();
    }
</script>
</html>