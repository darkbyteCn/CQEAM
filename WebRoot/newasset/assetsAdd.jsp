<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by ai.
  Date: 2008-3-13
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>新增管理资产</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String barcod = parser.getParameter("barcod");
    String itemName = parser.getParameter("itemName");
    String itemSpec = parser.getParameter("itemSpec");
    String createUser = parser.getParameter("createUser");
    String status = parser.getParameter("status");
    String fromDate = parser.getParameter("fromDate");
    String toDate = parser.getParameter("toDate");
%>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet">
    <script type="text/javascript">
        printTitleBar("新增管理资产")
    </script>
    <table border="0" width="100%" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <input type="hidden" name="act">
        <tr>
            <td width="12%" align="right">资产条码：</td>
            <td width="18%"><input style="width:90%" type="text" name="barcod" class="input_style1" value="<%=barcod%>"></td>
            <td width="10%" align="right">资产名称：</td>
            <td width="25%"><input style="width:88%" type="text" name="itemName" class="input_style1" value="<%=itemName%>"></td>
            <td width="10%" align="right">创建人：</td>
            <td width="10%"><input style="width:100%" type="text" name="createUser" class="input_style1" value="<%=createUser%>"></td>
            <td align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            </td>
        </tr>
        <tr>
            <td align="right">资产型号：</td>
            <td><input type="text" name="itemSpec" value="<%=itemSpec%>" class="input_style1" style="width:90%"></td>
            <td align="right">创建日期：</td>
            <td>
				<input type="text" name="fromDate" value="<%=fromDate%>" class="input_style1" style="width:39%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate, toDate)">
			到：<input type="text" name="toDate" value="<%=toDate%>" class="input_style1" style="width:39%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td align="right">单据状态：</td>
            <td><select name="status" class="select_style1" style="width:100%">
                <option value="">--请选择--</option>
                <option value="0" <%=status.equals("0")?"selected":""%>>未完成</option>
                <option value="1" <%=status.equals("1")?"selected":""%>>已完成</option>
                </select>
            </td>
            <td align="center"><img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
            </td>
        </tr>
    </table>

       <script type="text/javascript">
        var columnArr = new Array("单据编号", "资产条码", "资产名称", "资产型号","创建人","创建日期","单据状态");
        var widthArr = new Array("20%","19%","19%","18%","9%","8%","7%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="show_detail('<%=row.getValue("HEAD_ID")%>')">
                <td width="20%" align="center"><%=row.getValue("BILL_NO")%></td>
                <td width="19%" align="center"><%=row.getValue("BARCODE")%></td>
                <td width="19%" align="center"><%=row.getValue("ITEM_NAME")%></td>
                <td width="18%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="9%" align="center"><%=row.getValue("USERNAME")%></td>
                <td width="8%" align="center"><%=row.getValue("CREATED_DATE")%></td>
                <td width="7%" align="center"><%=row.getValue("STATUS")%></td>
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
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
    function do_CreateOrder() {
	    var style = "width="+screen.width+",height="+screen.height+",top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	    var url = "/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?act=<%=WebActionConstant.NEW_ACTION%>";
        var win = "bjOrder";
        window.open(url, win, style);
    }
    function show_detail(headId) {
        var style = 'width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes';
        var url = "/servlet/com.sino.ams.newasset.servlet.AssetsAddServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&headId=" + headId;
        var win = "detailWin";
        window.open(url, win, style);
    }
</script>