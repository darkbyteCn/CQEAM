<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-5-27
  Time: 10:35:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>集团低值易耗资产导入错误信息</title>
</head>
<%
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.ETS_SPARE_DTO);
    Row row = null;
%>
<body leftmargin="0" topmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.object.servlet.ImportDzyhAssetsServletJt">
    <input type="hidden" name="act">
    <script type="text/javascript">
      printTitleBar("集团低值易耗资产导入错误信息")
    </script>
    <table width="100%" border="0" class="queryTable">
        <tr>
            <td width="100%" colspan="15" align="right">
                <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_concel();return false;">
                <img src="/images/eam_images/export.jpg" id="exportImg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>
    <div id="aa" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:40px;left:0px;width:100%">
        <table width="350%" class="headerTable" border="1">
            <tr height="20">
                <td width="6%" align="center">错误信息</td>
                <td width="3%" align="center">公司代码</td>
                <td width="4%" align="center">低值易耗品标签号</td>
                <td width="5%" align="center">低值易耗品名称</td>
                <td width="5%" align="center">规格型号</td>
                <td width="2%" align="center">计量单位</td>
                <td width="4%" align="center">资产地点三段组合编码</td>
                <td width="5%" align="center">资产地点描述</td>
                <td width="3%" align="center">资产管理员员工编号</td>
                <td width="3%" align="center">责任人姓名</td>
                <td width="5%" align="center">重要低耗对应目录类别组合代码</td>
                <td width="5%" align="center">厂商</td>
                <td width="3%" align="center">设备状态代码</td>
                <td width="3%" align="center">实物部门代码</td>
                <td width="3%" align="center">专业责任人员工编号</td>
                <td width="3%" align="center">实际使用人姓名</td>
                <td width="3%" align="center">低值易耗品价值</td>
                <td width="3%" align="center">资产启用日期</td>
                <td width="6%" align="center">备注</td>

            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:400px;width:100%;position:absolute;top:61px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table width="350%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="6%" align="left"><%=row.getValue("ERROR")%></td>
                <td width="3%" align="left"><%=row.getValue("COMPANY_CODE")%></td>
                <td width="4%" align="left"><%=row.getValue("BARCODE")%></td>
                <td width="5%" align="left"><%=row.getValue("ITEM_NAME")%></td>
                <td width="5%" align="left"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="2%" align="left"><%=row.getValue("ITEM_UNIT")%></td>
                <td width="4%" align="left"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
                <td width="5%" align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
                <td width="3%" align="left"><%=row.getValue("RESPONSIBILITY_USER")%></td>
                <td width="3%" align="left"><%=row.getValue("EMPLOYEE_NAME")%></td>
                <td width="5%" align="left"><%=row.getValue("CONTENT_CODE")%></td>
                <td width="5%" align="left"><%=row.getValue("MANUFACTURER_ID")%></td>
                <td width="3%" align="left"><%=row.getValue("ITEM_STATUS")%></td>
                <td width="3%" align="left"><%=row.getValue("SPECIALITY_DEPT")%></td>
                <td width="3%" align="left"><%=row.getValue("SPECIALITY_USER")%></td>
                <td width="3%" align="left"><%=row.getValue("MAINTAIN_USER")%></td>
                <td width="3%" align="left"><%=row.getValue("PRICE")%></td>
                <td width="3%" align="left"><%=row.getValue("DZYH_START_DATE")%></td>
                <td width="6%" align="left"><%=row.getValue("REMARK")%></td>
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
        mainFrm.action = "/system/object/importDzyhAssetsJt.jsp";
        mainFrm.submit();
    }
</script>