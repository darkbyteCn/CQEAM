<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2007-12-23
  Time: 16:42:10
--%>
<head><title>备件新购入库详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/arrUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>

</head>
<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute("EAM_ITEMH_REPAIR");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.AmsItemTransHServlet" method="post">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
    <input type="hidden" name="transType" value="<%=DictConstant.BJRK%>">
    <input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
    <input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
    <input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
    <table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
        <tr>
            <td>
                <table width="100%" id="table2" cellspacing="1">
                    <tr height="22">
                        <td width="10%" align="right">单据号：</td>
                        <td width="20%"><%=amsItemTransH.getTransNo()%>
                        </td>
                        <td width="10%" align="right">仓库名称：</td>
                        <td width="20%"><%=amsItemTransH.getToObjectName()%>
                        </td>
                        <%--<td width="9%" align="right">仓库地点：</td>
                        <td width="25%"><input type="text" name="toObjectLocation" value="<%=amsItemTransH.getToObjectLocation()%>"
                                               class="blueborderGray">
                        </td>--%>
                    </tr>
                    <tr height="22">
                        <td align="right">创建人：</td>
                        <td><%=amsItemTransH.getCreatedUser()%>
                        </td>
                        <td align="right">创建日期：</td>
                        <td><%=amsItemTransH.getCreationDate()%>
                        </td>
                        <td width="10%" align="right">单据状态：</td>
                        <td><%=amsItemTransH.getTransStatusName()%>
                        </td>
                    </tr>
                    <tr height="50">
                        <td align="right">备注：</td>
                        <td colspan="6"><%=amsItemTransH.getRemark()%>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <fieldset>
        <legend>
            <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
        </legend>
        <script type="text/javascript">
            var columnArr = new Array("部件号", "设备名称", "规格型号", "数量");
            var widthArr = new Array("12%", "15%", "15%", "10%");
            printTableHead(columnArr, widthArr);
        </script>
        <div style="overflow-y:scroll;height:600px;width:100%;left:1px;margin-left:0"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
            <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="2" cellspacing="0">
                <%
                    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                    if (rows != null && !rows.isEmpty()) {
                        Row row = null;
                        for (int i = 0; i < rows.getSize(); i++) {
                            row = rows.getRow(i);
                %>
                <tr id="mainTr<%=i%>" height="20" onMouseMove="style.backgroundColor='#EFEFEF'"
                    onMouseOut="style.backgroundColor='#FFFFFF'">

                    <td width="12%"><%=row.getValue("BARCODE")%>
                    </td>
                    <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                    </td>
                    <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                    </td>
                    <td width="10%" name="quantity" id="quantity<%=i%>"><%=row.getValue("QUANTITY")%>
                    </td>
                    <td style="display:none">
                        <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                        <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </fieldset>
</form>
</body>
<script type="text/javascript">
    function init() {
    }
</script>
