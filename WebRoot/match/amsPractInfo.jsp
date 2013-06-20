<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2008-7-21
  Time: 15:46:35
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>实物资产信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String assetsNumber = parser.getParameter("assetsNumber");
    String tagNumber = parser.getParameter("tagNumber");
    String assetsDescription = parser.getParameter("assetsDescription");
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.match.servlet.AmsPractInfoServlet">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    printTitleBar("实物资产信息")
</script>
<table width="100%" border="0" bgcolor="#EFEFEF">
        <tr>
            <td align="right" width="7%">资产编号：</td>
            <td width="10%"><input style="width:100%" type="text" name="assetsNumber" value="<%=assetsNumber%>"></td>
            <td align="right" width="7%">MIS资产条码：</td>
            <td width="10%"><input style="width:100%" type="text" name="tagNumber" value="<%=tagNumber%>"></td>
            <td align="right" width="7%">MIS资产名称：</td>
            <td width="15%"><input style="width:85%" type="text"  name="assetsDescription" value="<%=assetsDescription%>"></td>
            <td width="5%" align= "right"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("","EAM条码","EAM设备名称","EAM设备型号","EAM资产地点","EAM地点代码");
            var widthArr = new Array("4%","15%","20%","20%","26%","10%");
            printTableHead(columnArr,widthArr);
        </script>
<input type="hidden" name="act">
<div style="overflow-y:scroll;width:100%;height:360px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && rows.getSize() > 0) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="4%" align="center"><input type="radio" id="systemid" name="systemid" value="<%=row.getValue("BARCODE")%>"></td>
            <td width="15%" align="center"><%=row.getValue("BARCODE")%></td>
            <td width="20%" align="center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="20%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="26%" align="center"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%></td>
            <td width="10%" align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
        </tr>
<%
	    }
    }
%>
    </table>
</div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.match.servlet.AmsPractInfoServlet";
        mainFrm.submit();
    }
    function do_ShowDetail(barcode){
        if(barcode == ""){
            alert("自定义显示字段中不含“标签号”，无法显示该资产详细信息。");
            return;
        }
        var url = "/servlet/com.sino.ams.net.equip.servlet.PlantMessageServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
        var winName = "assetsWin";
        var style = "width=600,height=340,left=100,top=150";
        window.open(url, winName, style);
}
</script>
