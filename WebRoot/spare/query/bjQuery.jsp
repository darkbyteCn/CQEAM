<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.query.dto.AmsBjQueryDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-23
  Time: 10:48:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件信息查询页面</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    AmsBjQueryDTO dto = (AmsBjQueryDTO) request.getAttribute("SPARE");
    String action = reqParser.getParameter("act");
%>

<body>
<body onkeydown="autoExeFunction('do_Search()');" onload="do_drop()">
<%=WebConstant.WAIT_TIP_MSG%>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>

<form action="/servlet/com.sino.ams.spare.query.servlet.AmsBjQueryServlet" name="mainForm" method="post">
<%
    if (dto.getType().equals("address")) {
%>
<script type="text/javascript">
    printTitleBar("备件查询--按地点")
</script>
<%
} else if (dto.getType().equals("category")) {
%>
<script type="text/javascript">
    printTitleBar("备件查询--按专业")
</script>
<%
} else {

%>
<script type="text/javascript">
    printTitleBar("备件查询--按部件号")
</script>
<%
    }
%>
<table border="0" width="100%" class="queryHeadBg">
    <%
        if (dto.getType().equals("address")) {
    %>
    <tr>
        <td align="right">地市：</td>
        <td><select name="company" id="company1" style="width:60%">
            <%=request.getAttribute("OU")%>
        </select></td>

        <td align="right" width="5%">专业类型：</td>
        <td width="10%" align="left"><select name="itemCategoryName" style="width:60%">
            <%=request.getAttribute("CATEGORYSELECT")%>
        </select></td>
        <td align="right" width="5%">地点：</td>
        <td width="10%" align="left"><input type="text" name="workorderObjectName" style="width:80%"
                                            value="<%=reqParser.getParameter("workorderObjectName")%>"><a
                class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a></td>
    </tr>
    <tr>
        <td width="8%" align="right">备件名称：</td>
        <td width="12%" align="left"><input type="text" name="itemName" style="width:100%"
                                            value="<%=StrUtil.nullToString(request.getParameter("itemName"))%>">
        </td>
        <td width="8%" align="right">规格型号：</td>
        <td width="13%" align="left">
            <input type="text" name="itemSpec" style="width:100%"
                   value="<%=StrUtil.nullToString(request.getParameter("itemSpec"))%>">
        </td>
        <td width=8% align="center"><img src="/images/button/query.gif" alt="查询备件"
                                         onClick="do_Search(); return false;"></td>
        <td width="8%" align="center"><img src="/images/button/toExcel.gif" alt="导出数据" onclick="do_export()"></td>
    </tr>
    <%
    } else if (dto.getType().equals("category")) {
    %>
    <tr>
        <td align="right">地市：</td>
        <td><select name="company" id="company2" style="width:60%">
            <%=request.getAttribute("OU")%>
        </select></td>

        <td align="right" width="5%">专业类型：</td>
        <td width="10%" align="left"><select name="itemCategoryName" style="width:60%">
            <%=request.getAttribute("CATEGORYSELECT")%>
        </select></td>
        <td width="8%" align="center"><img src="/images/button/toExcel.gif" alt="导出数据" onclick="do_export()"></td>
    </tr>
    <tr>
        <td width="8%" align="right">备件名称：</td>
        <td width="12%" align="left"><input type="text" name="itemName" style="width:100%"
                                            value="<%=StrUtil.nullToString(request.getParameter("itemName"))%>">
        </td>
        <td width="8%" align="right">规格型号：</td>
        <td width="13%" align="left">
            <input type="text" name="itemSpec" style="width:100%"
                   value="<%=StrUtil.nullToString(request.getParameter("itemSpec"))%>">
        </td>
        <td width=8% align="center"><img src="/images/button/query.gif" alt="查询备件"
                                         onClick="do_Search(); return false;"></td>
    </tr>
    <%
    } else {

    %>
    <tr>
        <td align="right" width="10%">地市：</td>
        <td width="15%"><select name="company" id="company3" style="width:60%">
            <%=request.getAttribute("OU")%>
        </select></td>
        <td width="10%" align="right">备件部件号：</td>
        <td width="15%" align="left"><input type="text" name="barcode" style="width:85%"
                                            value="<%=StrUtil.nullToString(request.getParameter("barcode"))%>">
        </td>
        <td width=8% align="center"><img src="/images/button/query.gif" alt="查询备件"
                                         onClick="do_Search(); return false;"><img src="/images/button/toExcel.gif"
                                                                                   alt="导出数据" onclick="do_export()">
        </td>
    </tr>
    <%
        }
    %>

</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="addressId" value="">
<input type="hidden" name="workorderObjectNo" value="">
<input type="hidden" name="itemCategory">
<input type="hidden" name="type" value="<%=dto.getType()%>">
<%
    if (dto.getType().equals("barcode")) {
%>
<%--<div style="height:362;width:100%;position:absolute;top:19px;left:1px;margin-left:0px"--%>
<%--align="left">--%>
<%--<table width="838" align="left" border="1" cellpadding="2" cellspacing="0"--%>
<%--style="position:absolute;left:1px;top:30px" class="headerTable">--%>

<%--<tr>--%>
<%--<td height="22" width="15%" align="center">所属公司</td>--%>
<%--<td height="22" width="15%" align="center">备件条码</td>--%>
<%--<td height="22" width="15%" align="center">备件名称</td>--%>
<%--<td height="22" width="15%" align="center">规格型号</td>--%>
<%--<td height="22" width="15%" align="center">设备类型</td>--%>
<%--<td height="22" width="20%" align="center">所属地点</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</div>--%>
<script type="text/javascript">
    var columnArr = new Array("所属公司", "部件号", "备件名称", "规格型号", "数量", "设备类型", "所属地点");
    var widthArr = new Array("15%", "15%", "15%", "15%", "5%", "15%", "20%");
    printTableHead(columnArr, widthArr);
</script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:350px;width:100%;left:1px;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666" id="dataTab">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"
            onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#ffffff'">
            <td height="22" width="15%"><%=row.getValue("COMPANY")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("BARCODE")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td height="22" width="5%"><%=row.getValue("QUANTITY")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("ITEM_CATEGORY_NAME")%>
            </td>
            <td height="22" width="20%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
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
<%

} else {

%>
<%--<div style="height:362;width:100%;position:absolute;top:44px;left:1px;margin-left:0px"--%>
<%--align="left">--%>
<%--<table width="838" align="left" border="1" cellpadding="2" cellspacing="0"--%>
<%--style="position:absolute;left:1px;top:30px" class="headerTable">--%>

<%--<tr>--%>
<%--<td height="22" width="15%" align="center">所属公司</td>--%>
<%--<td height="22" width="15%" align="center">备件条码</td>--%>
<%--<td height="22" width="15%" align="center">备件名称</td>--%>
<%--<td height="22" width="15%" align="center">规格型号</td>--%>
<%--<td height="22" width="15%" align="center">设备类型</td>--%>
<%--<td height="22" width="20%" align="center">所属地点</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</div>--%>
<script type="text/javascript">
    var columnArr = new Array("所属公司", "部件号", "备件名称", "规格型号", "数量", "设备类型", "所属地点");
    var widthArr = new Array("15%", "15%", "15%", "15%", "5%", "15%", "20%");
    printTableHead(columnArr, widthArr);
</script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:350px;width:100%;left:1px;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666" id="dataTab1">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')"
            onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#ffffff'">
            <td height="22" width="15%"><%=row.getValue("COMPANY")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("BARCODE")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td height="22" width="5%"><%=row.getValue("QUANTITY")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("ITEM_CATEGORY_NAME")%>
            </td>
            <td height="22" width="20%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
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
<%

    }
%>
</body>

</body>
</html>
<script type="text/javascript">
    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_ShowDetail() {

    }
    function do_drop() {
        var user = "<%=sfUser.getLoginName()%>"  ;
        if (user == 'AMSADMIN') {
        } else {
            var type = "<%=dto.getType()%>";
            if (type == 'address') {
                var status = document.getElementById("company1");
            } else if (type == 'category') {
                var status = document.getElementById("company2");
            } else {
                var status = document.getElementById("company3");
            }

            dropSpecialOption(status, '')
        }
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var users = window.showModalDialog(url, null, popscript);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
</script>