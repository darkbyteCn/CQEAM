<%--
  Created by IntelliJ IDEA.
  User: V-jiachuanchuan
  Date: 2007-12-4
  Time: 11:26:59
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>设备返修率-按厂家</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
    String itemName = parser.getParameter("itemName");
    String itemSpec = parser.getParameter("itemSpec");
    String vendorName = parser.getParameter("vendorName");
    String repairQuery = parser.getParameter("repairQuery");
%>
<body bgcolor="#FFFFFF" text="#000000" bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.spare.repair.servlet.RepairStatisticServlet">
    <script>
        printTitleBar("设备返修率-按厂家");
    </script>
    <table width="100%" border=0 bgcolor='#efefef' cellpadding="2" cellspacing="0">
        <input type="hidden" name="toexcel" value="1">
        <input type="hidden" name="repairQuery" value="<%=repairQuery%>">
        <tr>
            <td>
                <table width="99%" align='right'>
                    <tr>
                        <td align="right" width="10%">公司：</td>
                        <td width="15%"><select style="width:100%"
                                                name="orgId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%></select>
                        </td>
                        <td align="right" width="12%">设备名称：</td>
                        <td width="18%"><input style="width:80%" type="text" name="itemName"
                                               value="<%=itemName%>">
                            <a style="cursor:'hand' " onclick="do_SelectSpec()" title="点击选择设备名称" class="linka">[…]</a>
                        </td>
                        <td align="right" width="12%">规格型号：</td>
                        <td width="18%"><input style="width:100%" type="text" name="itemSpec"
                                               value="<%=itemSpec%>"></td>
                        <td align="center"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'"
                                                onclick="do_exportToExcel()" alt="导出到Excel"></td>
                    </tr>
                    <tr>
                        <td align="right" width="8%">厂家：</td>
                        <td width="18%"><input style="width:80%" type="text" name="vendorName"
                                               value="<%=vendorName%>">
                            <a style="cursor:'hand' " onclick="do_SelectVendor()" title="点击选择厂家" class="linka">[…]</a>
                        </td>
                        <td width="10%" align="right">年份：</td>
                        <td width="15%"><select style="width:100%" name="year"><%=yearOption%></select></td>
                        <td width="10%" align="right">月份：</td>
                        <td width="15%"><select style="width:100%" name="month"><%=monthOption%></select></td>
                        <td align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'"
                                                onclick="do_search();" alt="查询"></td>

                    </tr>
                </table>
            </td>
        </tr>

        <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    </table>
    <script type="text/javascript">
        var columnArr = new Array("公司","厂家", "设备名称", "规格型号", "返修率");
        var widthArr = new Array("15%","20%", "20%", "25%", "20%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px" align="left">
        <table border="1" width="100%"  bordercolor="#666666"
               id="table1">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);

            %>
            <tr  height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <!--<td align="center" width='2%'></td>-->
                <td width="15%"><%=row.getValue("ORG_NAME")%></td>
                <td width="20%"><%=row.getValue("VENDOR_NAME")%></td>
                <td width="20%"><%=row.getValue("ITEM_NAME")%></td>
                <td width="25%"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="20%"><%=row.getValue("QUANTITY")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

    <div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</form>


</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<script>
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        <%--mainFrm.repairQuery.value = "<%=DictConstant.REPAIR_STATISTIC_VENDOR%>";--%>
        <%--mainFrm.action = "<%=URLDefineList.STAT_WO_SERVLET%>";--%>
        mainFrm.submit();

    }
    function do_SelectSpec() {
        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }
    }
    function do_SelectVendor() {
        var lookUpVendor = "<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var dialogWidth = 42;
        var dialogHeight = 30;
        var vendors = getLookUpValues(lookUpVendor, dialogWidth, dialogHeight);
        if (vendors) {
            var vendor = null;
            for (var i = 0; i < vendors.length; i++) {
                vendor = vendors[i];
                dto2Frm(vendor, "mainFrm");
            }
        }
    }
</script>