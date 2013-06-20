<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-8-20
  Time: 11:31:41
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
    <title>实物资产成批导入错误信息</title>
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
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.object.servlet.ImportItemServletJt">
    <input type="hidden" name="act">
    <script type="text/javascript">
      printTitleBar("实物资产成批导入错误信息")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="100%" colspan="15" align="right">
                <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_concel();return false;">
                <img src="/images/eam_images/export.jpg" id="exportImg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>
    <div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:40px;left:1px;width:100%" class="crystalScroll">
        <table width="300%" class="headerTable" border="1">
            <tr height="20">
                <td width="8%" align="center">错误信息</td>
                <td width="4%" align="center">资产帐簿</td>
                <td width="4%" align="center">标签号</td>
                <td width="6%" align="center">新资产目录组合代码</td>
                <td width="6%" align="center">新资产名称</td>
                <td width="6%" align="center">新规格型号</td>
                <td width="6%" align="center">新地点编码组合</td>
                <td width="4%" align="center">新责任部门代码</td>
                <td width="4%" align="center">新责任人员工编号</td>
                <td width="4%" align="center">新实物部门代码</td>
                <td width="4%" align="center">新使用部门代码</td>
                <td width="4%" align="center">新使用人姓名</td>
                <td width="4%" align="center">新厂商代码</td>
                <td width="4%" align="center">新网元编码</td>
                <td width="4%" align="center">新投资分类编码</td>
                <td width="4%" align="center">新业务平台编码</td>
                <td width="4%" align="center">新网络层次编码</td>
                <td width="4%" align="center">新备注一</td>
                <td width="4%" align="center">新共建状态</td>
                <td width="4%" align="center">新共享状态</td>
            </tr>
        </table>
    </div>
    <div style="overflow:scroll;height:400px;width:100%;position:absolute;top:61px;left:1px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table width="300%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="8%" align="left"><%=row.getValue("ERROR")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("BOOK_TYPE_CODE")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("BARCODE")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("CONTENT_CODE")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("NEW_ITEM_NAME")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("NEW_ITEM_SPEC")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("NEW_OBJECT_CODE")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_RESPONSIBILITY_DEPT")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_EMPLOYEE_NUMBER")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_SPECIALITY_DEPT")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_MAINTAIN_DEPT")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_MAINTAIN_USER")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_MANUFACTURER_ID")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_LNE_ID")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_CEX_ID")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_OPE_ID")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_NLE_ID")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_REMARK1")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_CONSTRUCT_STATUS")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_SHARE_STATUS")%>
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
    function do_Export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    function do_concel() {
        mainFrm.action = "/system/object/importItemJt.jsp";
        mainFrm.submit();
    }
</script>