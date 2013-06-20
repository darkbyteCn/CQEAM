<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%--
  created by YS
  Date: 2008-07-31
  Time: 2:20:36
--%>

<html>
<head>
    <title>单据查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>

</head>

<body onkeydown="autoExeFunction('do_search()')" onload="do_SetPageWidth()">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("单据查询")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">单据号</td>
            <td width="20%"><input style="width:100%" type="text" name="headerNo" value=""></td>
            <td width="10%" align="right">单据类型</td>
            <td width="20%"><input style="width:100%" type="text" name="orderType" value=""></td>
            <td width="10%" align="center">
              <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
              <img src="/images/eam_images/revoke.jpg" alt="点击撤销" onclick="do_CancelOrder();">
            </td>
        </tr>
    </table>
    <div id="headDiv" style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td align=center width="3%"></td>
                <td width="40%" align="center">单据号</td>
                <td width="10%" align="center">单据类型</td>
                <td width="42%" align="center">建单时间</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act" value="">

    <div id="dataDiv" style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align="center"><input type="checkbox" name="headerId"
                                                 value="<%=row.getValue("HEADER_ID")%>">
                </td>
                <td width="40%" align="center"
                    onclick="show_detail('<%= row.getValue("HEADER_ID")%>')"><%=row.getValue("HEADER_NO")%></td>
                <td width="10%" align="center"
                    onclick="show_detail('<%=row.getValue("HEADER_ID")%>')"><%=row.getValue("ORDER_TYPE")%></td>
                <td width="42%" align="center"
                    onclick="show_detail('<%=row.getValue("HEADER_ID")%>')"><%=row.getValue("CREATE_DATE")%></td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsOrderHeaderServlet";
        mainFrm.submit();
    }

    function show_detail(headerId) {
     <%--   var url = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&headerId=" + headerId;
        var popscript = 'width=400,height=200,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
        window.open(url, 'basePot', popscript);--%>
        mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsOrderHeaderServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&headerId="+headerId;
        mainFrm.submit();
    }
    function do_CancelOrder(){
    var checkedCount = getCheckedBoxCount("headerId");
        if (checkedCount < 1) {
             alert("请至少选择一行数据！");
             return;
        } else if (checkedCount > 1) {
             alert("本操作只限一次撤消一条单据！");
             return;
        } else {
            if(confirm("确定要撤销选择的单据吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
              mainFrm.act.value = "<%=WebActionConstant.CANCEL_ACTION%>";
              mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsOrderHeaderServlet"; 
              mainFrm.submit();
        }
    }
}
</script>