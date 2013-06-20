<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsCJYCDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-11-14
  Time: 14:16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>资产盘点报表</title>
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
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
  </head>
  <body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')">
  <jsp:include page="/message/MessageProcess"/>
   <%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsAssetsCJYCDTO dto = (AmsAssetsCJYCDTO) request.getAttribute("AMSBJTRANSNOHDTO");
//    out.print(dto);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
          Row row = null;
%>
  <form action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckExportSelvet" name="mainForm" method="post">
   <script type="text/javascript">
        printTitleBar("资产盘点报表")
    </script>
      <table border="1" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td width="10%" align="right">责任部门：</td>
            <td width="22%" ><select name="deptCode" style="width:100%">
                                    style="width:100%"><%=request.getAttribute("DEPT")%>
              </select></td>
            <td width="10%" align="right">创建日期：</td>
            <td width="15%">
				<input type="text" name="fromDate" value="<%=reqParser.getAttribute("fromDate")%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate, toDate)">
			</td>
            <td width="5%" align="right">到：</td>
            <td width="15%">
                <input type="text" name="toDate" value="<%=reqParser.getAttribute("toDate")%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
           
            <td width=10% align="center"><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
            <td  width=10% align="left"><img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_export()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="">
   <script type="text/javascript">
        var columnArr = new Array("资产编号", "资产条码", "资产名称", "资产型号","计量单位","启用日期","折旧年限","原值","累计折旧","净值","账面数量","实际数量");
        var widthArr = new Array("7%","12%","10%","10%","5%","8%","5%","8%","8%","8%","8%","8%");
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
                onMouseOut="style.backgroundColor='#ffffff'" >
                <td width="7%" align="center"><%=row.getValue("ASSET_ID")%></td>
                <td width="12%" align="center"><%=row.getValue("TAG_NUMBER")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_DESCRIPTION")%></td>
                <td width="10%" align="center"><%=row.getValue("MODEL_NUMBER")%></td>
                <td width="5%" align="center"><%=row.getValue("UNIT_OF_MEASURE")%></td>
                <td width="8%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
                <td width="5%" align="center"><%=row.getValue("LIFE_IN_YEARS")%></td>
                <td width="8%" align="center"><%=row.getValue("COST")%></td>
                <td width="8%" align="center"><%=row.getValue("DEPRN_RESERVE")%></td>
                <td width="8%" align="center"><%=row.getValue("DEPRN_COST")%></td>
                <td width="8%" align="center"><%=row.getValue("CURRENT_UNITS")%></td>
                <td width="8%" align="center"><%=row.getValue("NOW_COUNT")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
  </form>
 <div style="position:absolute;top:480px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
  </body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
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

</script>