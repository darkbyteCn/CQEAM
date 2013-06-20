<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-12-16
  Time: 20:52:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
	<title>业务单据查询</title>
</head>
<body leftmargin="0" topmargin="0">
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')" >
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsAssetsNoMatchDTO dto = (AmsAssetsNoMatchDTO) request.getAttribute("AMSBJTRANSNOHDTO");
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsNoMatchDetailServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("未匹配资产明细")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryTable">
        <tr>
            <td width=10% align="left"> 公司名称：</td>
            <td width="22%"><select name="organizationId" id="organizationId" class="select_style1"
                                    style="width:100%"><%=request.getAttribute("OU")%>
            </select></td>
            <td width="15%" align="right">创建日期：</td>
            <td><input type="text" name="fromDate" value="<%=dto.getFromDate()%>"
                       style="width:80%" title="点击选择开始日期" readonly class="input_style2"
                       onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td width="7%" align="right">到：</td>
            <td><input type="text" name="toDate" value="<%=dto.getToDate()%>"
                       style="width:80%" title="点击选择截止日期" readonly class="input_style2"
                       onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td width=10% align="center"><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
        </tr>
    </table>
    <%--<input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <script type="text/javascript">
        var columnArr = new Array("公司名称", "资产编号", "资产名称","资产型号","资产类别","责任人","启用日期");
        var widthArr = new Array("10%", "10%",  "15%","15%", "10%",  "10%","10%");
        printTableHead(columnArr, widthArr);

    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="10%" align="center"><%=row.getValue("ORGANIZATION_NAME")%> </td>
                <td width="10%" align="left"><%=row.getValue("ASSET_NUMBER")%></td>
                <td width="15%" align="center"><%=String.valueOf(row.getValue("ASSETS_DESCRIPTION"))%></td>
                <td width="15%" align="center"><%=String.valueOf(row.getValue("MODEL_NUMBER"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("FA_CATEGORY_CODE"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("ASSIGNED_TO_NAME"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("DATE_PLACED_IN_SERVICE"))%></td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_drop() {
        var transType = document.getElementById("transType")   ;
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transType, 'BJBFS');
        //        dropSpecialOption(transStatus, 'APPROVED');
    }
    function do_ShowDetail(transId, transType) {
        mainForm.transId.value = transId;
        mainForm.transType.value = transType;
        var url = "/servlet/com.sino.ams.spare.query.servlet.AmsBjTransQueryServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId + "&transType=" + transType;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);
    }
</script>