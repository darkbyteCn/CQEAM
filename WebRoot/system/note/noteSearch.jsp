<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-22
  Time: 15:10:29
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
    <title>租期提醒查询</title>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String barcode = parser.getParameter("barcode");
    String rentPerson = parser.getParameter("rentPerson");

//    String datestr3 = "";
//    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
//    String accountTime = CalendarUtil.getCurrDate();

//    SimpleDateFormat endFormatter = new SimpleDateFormat(CalendarConstant.LINE_PATTERN);
    SimpleDateFormat endFormatter = new SimpleDateFormat("yyyy-MM-dd");

    Date tempDate = new java.util.Date();      //当前时间

//    String end = "2008-08-07";
//    Date date1 = endFormatter.parse(end);
//    datestr3 = String.valueOf((tempDate.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));//计算两个日期之间的时间差，为计算天数

%>
<body onkeydown="autoExeFunction('do_search()')">
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.note.servlet.AmsRentDeadlineServlet">
    <script type="text/javascript">
        printTitleBar("租期提醒查询")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" topmargin="0" border="0" class="queryTable">
        <tr>
            <td width="10%" align="right">租赁资产条码：</td>
            <td width="20%"><input style="width:100%" class="input_style1" type="text" name="barcode" value="<%=barcode%>"></td>
            <td width="10%" align="right">租赁人：</td>
            <td width="20%"><input style="width:100%" class="input_style1" type="text" name="rentPerson" value="<%=rentPerson%>"></td>
            <td width="20%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            </td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="10%" align="center">租赁资产条码</td>
                <td width="10%" align="center">租赁人</td>
                <td width="10%" align="center">截至日期</td>
                <td width="10%" align="center">距离天数</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">

    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    int noticeQTy = 0;
                    String endday = "";
//            long end7 = 0;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);

//      --        noticeQTy = Integer.parseInt(row.getStrValue("NOTICE_BEFORE"));  //提前天数
//       --         endday = row.getStrValue("END_DATE");
//       --       long end7 = Long.parseLong(row.getStrValue("NOTICE_BEFORE"));

//           Date end1 = endFormatter.parse(endday);

//            String data4 = String.valueOf((end1.getTime()-tempDate.getTime())/ (24 * 60 * 60 * 1000));
//            long date6 = (end1.getTime()-tempDate.getTime()) / (24 * 60 * 60 * 1000); //截至时间减去当前时间（截至日期距当前的天数）
//     --       long end10 =date6-end7+1; //
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("RENT_PERSON")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("END_DATE")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("DAYS")%>
                </td>
                <%--<td width="5%"  align = "right"><%=row.getValue("NOTICE_BEFORE")%></td>--%>

            </tr>
            <%
                }
            %>
        </table>
    </div>
    <div style="position:absolute;top:93%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
    </div>
</form>
<%
    }
%>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(deadlineId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.note.servlet.AmsRentDeadlineServlet?deadlineId=" + deadlineId;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }


</script>