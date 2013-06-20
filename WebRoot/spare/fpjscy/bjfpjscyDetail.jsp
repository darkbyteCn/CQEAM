<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-29
  Time: 11:29:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件分配接收差异详细信息</title>
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
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<style type="text/css" rel="stylesheet">
    .noneinput {
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
    }
</style>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<body leftmargin="1" topmargin="1">
<%
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.fpjscy.servlet.AmsBjFpJsCyServlet" method="post">
    <jsp:include page="/flow/include.jsp" flush="true"/>
    <input type="hidden" name="act" value="">
    <table border="0" bordercolor="#9FD6FF" class="detailHeader" id="table1">
        <tr>
            <td>
                <table width="100%" id="table2" cellspacing="1" border="0">
                    <tr height="22">
                        <td align="right">单据编号：</td>
                        <td><%=amsItemTransH.getTransNo()%>
                        </td>
                        <td align="right">创建人：</td>
                        <td><%=amsItemTransH.getCreatedUser()%>
                        </td>
                    </tr>
                    <tr height="22">
                       <td align="right">分配公司：</td>
                        <td><%=amsItemTransH.getToOrganizationName()%></td>
                        <td align="right">创建日期：</td>
                        <td><%=amsItemTransH.getCreationDate()%>
                        </td>
                        <td align="right">单据状态：</td>
                        <td><%=amsItemTransH.getTransStatusName()%>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <fieldset>
        <legend>
            <img src="/images/button/toExcel.gif" alt="导出数据" onClick="do_excel()">
            <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
        </legend>
        <script type="text/javascript">
            var columnArr = new Array("设备名称","规格型号", "部件号", "分配数量", "接收数量");
            var widthArr = new Array("15%", "15%","12%", "8%", "8%");
            printTableHead(columnArr, widthArr);
        </script>
        <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
            <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
                <%
                    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
                    if (rows != null && !rows.isEmpty()) {

                        Row row = null;
                        for (int i = 0; i < rows.getSize(); i++) {
                            row = rows.getRow(i);
                %>
                <tr height="20">
                    <td width="15%" align="center"><%=row.getValue("ITEM_NAME")%>
                    </td>
                    <td width="15%" align="center"><%=row.getValue("ITEM_SPEC")%>
                    </td>
                    <td width="12%" align="center"><%=row.getValue("BARCODE")%>
                    </td>
                    <td width="8%" align="center"><%=row.getValue("QUANTITY")%>
                    </td>
                    <td width="8%" align="center"><%=row.getValue("ACCEPT_QTY")%>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </fieldset>
    <input type="hidden" name="objectCategory" value="73">
    <input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
    <input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
    <input type="hidden" name="addressId" value="<%=amsItemTransH.getAddressId()%>">
    <input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
    <input type="hidden" name="itemCode">
    <input type="hidden" name="detailid">
</form>
</body>
</html>
<script type="text/javascript">
    function do_show(itemCode, detailid) {
        /* mainForm.act.value = "show";
      var url = "/servlet/com.sino.ams.spare.fpjscy.servlet.AmsBjFpJsCyServlet?act=" + mainForm.act.value + "&detailid=" + detailid + "&itemCode=" + itemCode;
      var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
      window.open(url, "instrum", popscript);*/
    }
    function do_excel() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
</script>