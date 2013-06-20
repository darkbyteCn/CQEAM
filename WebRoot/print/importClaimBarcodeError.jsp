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
    <title>标签领用导入错误信息</title>
</head>
<%
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.REC_BARCODE_DTO);
    Row row = null;
%>
<body leftmargin="0" topmargin="0" >
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet" >
    <script type="text/javascript">
      printTitleBar("标签领用导入错误信息")
    </script>
    <%-- 
    <table width="100%" border="0" class="queryTable">
        <tr>
            <td width="100%" colspan="15" align="right">
                <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_concel();return false;">
                <img src="/images/eam_images/export.jpg" id="exportImg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>
    --%>
    <div id="aa" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:40px;left:0px;width:100%">
        <table width="150%" class="headerTable" border="1">
            <tr height="20">
            	<td width="30%" align="center">错误描述</td>
				<td width="8%"  align="center">标签号</td>
				<td width="10%" align="center">所属地市</td>
				<td width="10%" align="center">领用部门</td>
				<td width="5%"  align="center">领用人</td>
				<%--
				<td width="7%"  align="center">领用日期</td>
				<td width="7%"  align="center">打印日期</td>
				--%>
				<td width="5%"  align="center">打印人</td>
				<td width="10%" align="center">领用原因</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:400px;width:100%;position:absolute;top:61px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table width="150%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="30%" align="left"><%=row.getValue("IMP_ERROR")%></td>
                <td width="8%"  align="left"><%=row.getValue("BARCODE")%></td>
                <td width="10%" align="left"><%=row.getValue("ORGANIZATION")%></td>
                <td width="10%" align="left"><%=row.getValue("RECEIVE_DEPT")%></td>
                <td width="5%"  align="left"><%=row.getValue("RECEIVE_USER")%></td>
                <%-- 
                <td width="7%"  align="left"><%=row.getValue("RECEIVE_DATE")%></td>
                <td width="7%"  align="left"><%=row.getValue("PRINT_DATE")%></td>
                --%>
                <td width="5%"  align="left"><%=row.getValue("PRINT_USER")%></td>
                <td width="10%" align="left"><%=row.getValue("RECEIVE_REMARK")%></td>
                
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
        mainFrm.action = "/print/importClaimBarcodeError.jsp";
        mainFrm.submit();
    }
</script>