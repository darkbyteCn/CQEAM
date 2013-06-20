<%--
  Created by HERRY.
  Date: 2008-7-30
  Time: 17:54:57
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemAllocateHDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<html>
<head><title>备件调拨单据打印</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
</head>
<%
    AmsItemAllocateHDTO headerDto = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
%>
<body leftmargin="1" topmargin="1">
<h2 align="center">备件调拨单</h2>
<table width="100%">
<tr>
    <td>
        <table width="100%" id="table2" cellspacing="1" border="1" bordercolor="#000000">
            <tr height="22">
                <td width="13%" align="center">调拨单号</td>
                <td width="20%"><%=headerDto.getTransNo()%>
                </td>
                <td width="13%" align="center">调出仓库</td>
                <td width="23%"><%=headerDto.getFromObjectName()%>
                </td>

            </tr>
            <tr height="22">
                <td align="center">调入地市</td>
                <td><%=headerDto.getToOrganizationName()%>
                </td>
                <td align="center">预计归还日期</td>
                <td><%=headerDto.getRespectReturnDate()%>
                </td>
            </tr>
            <tr height="22">
                <td align="center">创建人</td>
                <td><%=headerDto.getCreatedUser()%>
                </td>
                <td align="center">创建日期</td>
                <td><%=headerDto.getCreationDate()%>
                </td>
            </tr>
            <tr height="22">
                <td align="center">出库部门</td>
                <td><%=headerDto.getFreightDeptName()%>
                </td>
                <td align="center">出库部门备件管理员</td>
                <td><%=headerDto.getFreightMisUserName()%>
                </td>

            </tr>
            <tr height="22">
                <td align="center">出库部门实物仓管员</td>
                <td><%=headerDto.getFreightUserName()%>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr height="22">
                <td align="center">接收备板人员</td>
                <td><%=headerDto.getReceiveUserName()%>
                </td>
                <td align="center">接收备板人员联系电话</td>
                <td><%=headerDto.getReceiveUserTel()%>
                </td>
            </tr>

            <tr height="22">
                <td align="center">备注</td>
                <td colspan="3"><%=headerDto.getRemark()%>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="1" bordercolor="#000000" cellspacing="1">
                    <tr height="22">
                        <td align="center">物料编码</td>
                        <td align="center">设备名称</td>
                        <td align="center">规格型号</td>
                        <td align="center">调拨数量</td>
                    </tr>
                    <%
                        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                        if (rows != null && !rows.isEmpty()) {
                            Row row = null;
                            String quantity = "";
                            for (int i = 0; i < rows.getSize(); i++) {
                                row = rows.getRow(i);
                                //quantity = Integer.parseInt(row.getValue("QUANTITY").toString());
                                quantity = (row.getValue("QUANTITY").toString()).equals("")?"0":(row.getValue("QUANTITY").toString());
                                
                    %>
                    <tr height="22" id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
                        onMouseOut="style.backgroundColor='#ffffff'">
                        <td width="10%" align="center"><%=row.getValue("BARCODE")%>
                        </td>
                        <td width="18%"><%=row.getValue("ITEM_NAME")%>
                        </td>
                        <td width="18%"><%=row.getValue("ITEM_SPEC")%>
                        </td>
                        <td width="7%" align="right"><%=quantity%>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
        </table>
    </td>
</tr>
</table>
<%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</body>
<script type="text/javascript">
    function beforePrint() {
    }
</script>
</html>