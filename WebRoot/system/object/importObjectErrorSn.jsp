<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-6-1
  Time: 18:24:07
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
    <title>地点导入错误信息（新）</title>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.object.servlet.ImportObjectServletSn">
    <script type="text/javascript">
        printTitleBar("地点导入错误信息（新）")
    </script>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:18px;left:1px;width:1095px">
        <table width="250%" class="headerTable" border="1">
            <tr height="20">
                <td width="5%" align="center">地点代码</td>
                <td width="2%" align="center">地市</td>
                <td width="2%" align="center">区县</td>
                <td width="10%" align="center">地点名称</td>
                <td width="4%" align="center">地点专业</td>
                <td width="3%" align="center">成本中心</td>
                <td width="2%" align="center">是否TD</td>
                <td width="3%" align="center">公司代码</td>
                <td width="5%" align="center">地点代码错误信息</td>
                <td width="3%" align="center">地市错误信息</td>
                <td width="3%" align="center">区县错误信息</td>
                <td width="10%" align="center">地点名称错误信息</td>
                <td width="4%" align="center">地点专业错误信息</td>
                <td width="3%" align="center">成本中心错误信息</td>
                <td width="4%" align="center">是否TD错误信息</td>
                <td width="3%" align="center">公司代码错误信息</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:68%;width:1111px;position:absolute;top:40px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="5%" align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="2%" align="center"><%=row.getValue("CITY")%>
                </td>
                <td width="2%" align="center"><%=row.getValue("COUNTY")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="4%" align="center"><%=row.getValue("OBJECT_CATEGORY")%>
                </td>
                <td width="3%" align="center"><%=row.getValue("COUNTY_CODE")%>
                </td>
                <td width="2%" align="center"><%=row.getValue("IS_TD")%>
                </td>
                <td width="3%" align="center"><%=row.getValue("COUNTY_CODE")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("OBJECT_CODE_ERROR")%>
                </td>
                <td width="3%" align="center"><%=row.getValue("CITY_ERROR")%>
                </td>
                <td width="3%" align="center"><%=row.getValue("COUNTY_ERROR")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("OBJECT_NAME_ERROR")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("OBJECT_CATEGORY_ERROR")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("COUNTY_CODE_ERROR")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("IS_TD_ERROR")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("COMPANY_CODE_ERROR")%>
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
</script>