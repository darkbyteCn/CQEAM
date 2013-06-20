<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-24
  Time: 15:27:40
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
    <title>村通资产导入错误信息</title>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.object.servlet.ImportVillageAssetsServlet">
    <input type="hidden" name="act">
    <script type="text/javascript">
      printTitleBar("村通资产导入错误信息")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="100%" colspan="15" align="right">
                <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_concel();return false;">
                <img src="/images/eam_images/export.jpg" id="exportImg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:40px;left:1px;width:1083px">
        <table width="400%" class="headerTable" border="1">
            <tr height="20">
                <td width="6%" align="center">错误信息</td>
                <td width="3%" align="center">公司代码</td>
                <td width="3%" align="center">村通资产标签号</td>
                <td width="5%" align="center">资产名称</td>
                <td width="5%" align="center">规格型号</td>
                <td width="3%" align="center">责任人编号</td>
                <td width="3%" align="center">责任人姓名</td>
                <td width="6%" align="center">地点组合编码</td>
                <td width="3%" align="center">设备性能</td>
                <td width="4%" align="center">资产类别代码组合</td>
                <td width="4%" align="center">资产类别描述</td>
                <td width="6%" align="center">资产地点描述</td>
                <td width="3%" align="center">实物部门代码</td>
                <td width="3%" align="center">使用人姓名</td>
                <td width="3%" align="center">使用部门代码</td>
                <td width="3%" align="center">成本</td>
                <td width="3%" align="center">净值</td>
                <td width="3%" align="center">残值</td>
                <td width="3%" align="center">启用日期</td>
                <td width="3%" align="center">使用年限</td>
                <td width="3%" align="center">剩余月数</td>
                <td width="6%" align="center">备注</td>

            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:450px;width:1100px;position:absolute;top:61px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="400%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="6%" align="left"><%=row.getValue("ERROR")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("COMPANY_CODE")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("BARCODE")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="5%" align="left"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("EMPLOYEE_NUMBER")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("EMPLOYEE_NAME")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("EQUIPMENT_PERFORMANCE")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("CONTENT_CODE")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("CONTENT_NAME")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("SPECIALITY_DEPT")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("MAINTAIN_USER")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("MAINTAIN_DEPT")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("PRICE")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("DEPRN_COST")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("SCRAP_VALUE")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("VILLAGE_START_DATE")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("USE_YEAR")%>
                </td>
                <td width="3%" align="left"><%=row.getValue("REMAIN_MONTH")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("REMARK")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%--<div><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>--%>
</body>
</html>
<script type="text/javascript">
    function do_Export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    function do_concel() {
        mainFrm.action = "/system/object/importVillageAssets.jsp";
        mainFrm.submit();
    }
</script>