<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-4-1
  Time: 14:55:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>部门设备查询</title>
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
<body>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AssetsTagNumberQueryDTO dto = (AssetsTagNumberQueryDTO) request.getAttribute("TAG_NUMBER");
//    out.print(dto);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String assetsType = reqParser.getParameter("assetsType");
%>
<form action="/servlet/com.sino.ams.newasset.servlet.EquipmentInfoQueryServlet" name="mainForm" method="post">

    <script type="text/javascript">
        printTitleBar("部门设备查询")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td width="10%" align="right">责任部门：</td>
            <td><input type="text" name="deptName"  readonly class="noEmptyInput" value="<%=dto.getDeptName()%>"><a
                    class="linka" style="cursor:'hand'" onclick="do_selectDeptName();">[…]</a></td>
            <td width="10%" align="right">责任人：</td>
            <td><input type="text" name="userName" value="<%=dto.getUserName()%>"></td>
            <td width=10% align="right"> 设备地点：</td>
            <td width="22%"><input name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>"></td>
            <td width=10% align="center"><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
            <td width="10%"><img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_export()"></td>
        </tr>
    </table>
    <%--<input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <input type="hidden" name="type" value="dept">
    <script type="text/javascript">
        var columnArr = new Array("标签号", "设备名称", "设备型号", "设备地点", "责任人", "责任部门", "资产类型");
        var widthArr = new Array("10%", "10%", "20%", "20%", "6%", "15%", "6%");
        printTableHead(columnArr, widthArr);

    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" >
                <td width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("USER_NAME")%>
                </td>
                <td width="15%" align="left"><%=row.getValue("DEPT_NAME")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("FINANCE_PROP_NAME")%>
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
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        var deptValue = mainForm.deptName.value;
        if (deptValue == "" || deptValue == null) {
            alert("请选择责任部门!");
        } else {
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            mainForm.submit();
        }

    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_selectDeptName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_DEPT1%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var userPara = "userId=" +<%=user.getUserId()%>;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        } else {
            mainForm.deptName.value = '';
        }
    }
</script>