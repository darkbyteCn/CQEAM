<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>备件设备分类导入错误信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<%
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.ETS_SPARE_DTO);
    Row row = null;
%>
<body leftmargin="0" topmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.spare.part.servlet.ImportSpareCategoryServlet">
    <input type="hidden" name="act">
    <script type="text/javascript">
      printTitleBar("备件设备分类导入错误信息")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="100%" colspan="15" align="right">
                <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_concel();return false;">
                <%--<img src="/images/button/toExcel.gif" id="exportImg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">--%>
            </td>
        </tr>
    </table>
    <div id="headDiv" style="overflow-y:scroll;position:absolute;top:40px;left:1px;width:100%">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="8%" align="center">错误信息</td>
                <td width="5%" align="center">设备名称</td>
                <td width="5%" align="center">设备型号</td>
                <td width="5%" align="center">设备类型</td>
                <td width="5%" align="center">用途</td>
                <td width="3%" align="center">厂商ID</td>
                <td width="3%" align="center">单位</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:450px;width:100%;position:absolute;top:61px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="8%" align="left"><%=row.getValue("ERROR")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("SPARE_USAGE")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("VENDOR_ID")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("ITEM_UNIT")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
</body>
</html>
<script type="text/javascript">
//    function do_Export() {
        <%--document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";--%>
//        document.mainFrm.submit();
//    }
    function do_concel() {
        mainFrm.action = "/spare/part/importSpareCategory.jsp";
        mainFrm.submit();
    }
</script>