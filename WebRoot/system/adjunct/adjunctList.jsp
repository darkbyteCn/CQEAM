<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>附件管理</title>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
    <link href="/WebLibary/css/site.css" rel="stylesheet" type="text/css">
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/IESTitleBar.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("附件管理")
</script>
<%
    String orderPkName =StrUtil.nullToString(request.getParameter("orderPkName"));
    String orderTable =StrUtil.nullToString(request.getParameter("orderTable"));
    RowSet rows = (RowSet) request.getAttribute("UPLOAD_FILES");
    Row row = null;
%>
<body leftmargin="0" topmargin="0">
<form name="mainFrm" action="" method="post">
    <input type="hidden" name="orderPkName" value="<%=orderPkName%>">
    <input type="hidden" name="orderTable" value="<%=orderTable%>">
    <input type="hidden" name="forward" value="">
    <div id="headDiv" style="overflow:hidden;position:absolute;top:31px;left:39px;width:600px">
        <table style="width:570px;margin-left:0px;" cellpadding="0" cellspacing="1" border="0" class="headerTable">

        <tr height="22">
            <%--<td width="2%" align="center"><input type="checkBox" name="controlBox" onclick="checkAll('controlBox','subBox')"></td>--%>
            <td width="50%" align="center">附件名称</td>
            <td width="8%" align="center">上传时间</td>
            <td width="8%" align="center">上传人</td>
        </tr>
    </table>
    </div>
    <div style="overflow-y:scroll;overflow-x:auto;height:360;width:617px;position:absolute;top:56px;left:24px;margin-left:0" align="left"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
                <table id="mtlTable" border="0" align="center" cellpadding="0" cellspacing="1" class="gridTbBorder" style="width:570px;margin-left:0px;" id="ytTable">

            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        String fileName = StrUtil.nullToString(row.getValue("FILE_NAME"));
                        String creationDate = StrUtil.nullToString(row.getValue("CREATION_DATE"));
                        String userName = StrUtil.nullToString(row.getValue("USER_NAME"));
            %>
            <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" style="cursor:'HAND'">
                <%--<td width="2%" align="center"><input type="checkbox" name="subBox" value="<%=row.getValue("ORDER_PK_VALUE")%>"></td>--%>
                <td width="50%" align="center"><a class="linka" onclick="downLoadAttach('<%=row.getValue("ORDER_PK_VALUE")%>')"><%=fileName%></a></td>
                <td width="8%" align="center"><%=creationDate%></td>
                <td width="8%" align="center"><%=userName%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%--<form action="" name="fileForm" method="post" enctype="multipart/form-data">--%>
    <%--<input type="hidden" name="orderPkName" value="<%=orderPkName%>">--%>
    <%--<input type="hidden" name="orderTable" value="<%=orderTable%>">--%>
    <%--<table style="position:absolute;left:1px;top:275px;width:600px;">--%>
        <%--<%--%>
            <%--for (int i = 0; i < 3; i++) {--%>
        <%--%>--%>
        <%--<tr height="22">--%>
            <%--<td width="5%"></td>--%>
            <%--<td width="80%" class="noborder"><input type="file" style="width:80%" name="file<%=i%>" value=""></td>--%>
        <%--</tr>--%>
        <%--<%--%>
            <%--}--%>
        <%--%>--%>
        <%--<tr>--%>
            <%--<td width="5%"></td>--%>
            <%--<td width="80%" class="noborder">--%>
                <%--<input type=button value="上传" class=button2 onclick="uploadAttaches()">&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--<input type=button value="删除" class=button2 onclick="deleteFile()">&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--<input type=button value="关闭" class=button2 onclick="window.close();">--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</form>--%>
</body>
</html>
<iframe name="fileFrm" style="display:none"></iframe>
<script type="text/javascript">
    function uploadAttaches() {
        var orderPkName = fileForm.orderPkName.value;
        var orderTable = fileForm.orderTable.value;
        fileForm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=UPLOAD_ACTION&orderPkName=" + orderPkName + "&orderTable=" + orderTable;
        fileForm.submit();
    }
    function downLoadAttach(orderPkValue) {
        mainFrm.forward.value = "DOWNLOAD_ACTION";
        mainFrm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?orderPkValue=" + orderPkValue;
        mainFrm.target = "fileFrm";
        mainFrm.submit();
    }
    function deleteFile() {
        mainFrm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=DELETE_ACTION";
        mainFrm.submit();
    }
</script>