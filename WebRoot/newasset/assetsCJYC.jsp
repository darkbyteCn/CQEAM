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
  Date: 2007-12-16
  Time: 20:52:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>资产折旧趋势预测</title>
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
<body>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsAssetsCJYCDTO dto = (AmsAssetsCJYCDTO) request.getAttribute("AMSBJTRANSNOHDTO");
//    out.print(dto);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsCJYCServlet" name="mainForm" method="post">

    <script type="text/javascript">
        printTitleBar("资产折旧趋势预测")
    </script>
    <table border="1" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td width=13% align="right"> 资产账簿：</td>
            <td width="22%"> <select name="bookTypeCode" id="bookTypeCode"
                                    style="width:100%"><%=request.getAttribute("BOOK_TYPE_CODE")%>
                            </select></td>
            <td width="15%" align="right">资产应用领域：</td>
            <td width="22%" ><select name="catSegment1" style="width:100%">
                                    style="width:100%"><%=request.getAttribute("CATSEGMENT1")%>
              </select></td>
            <td width="15%" align="right">资产分类目录：</td>
            <td><input type="text" name="catSegment2" value="<%=dto.getCatSegment2()%>"></td>
            <td width=10% align="center"><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
            <td  width=20% align="left"><img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_export()"></td>
        </tr>
    </table>
    <%--<input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">

    <div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:833px">
		<table class="headerTable" border="1" width="500%">
			<tr height="20px" >
				<td align=center width="3%">应用领域</td>
				<td align=center width="4%">分类目录</td>
				<td align=center width="3%">名称</td>
				<td align=center width="1%">1月</td>
				<td align=center width="1%">2月</td>
				<td align=center width="1%">3月</td>
				<td align=center width="1%">4月</td>
				<td align=center width="1%">5月</td>
				<td align=center width="1%">6月</td>
				<td align=center width="1%">7月</td>
				<td align=center width="1%">8月</td>
				<td align=center width="1%">9月</td>
				<td align=center width="1%">10月</td>
				<td align=center width="1%">11月</td>
				<td align=center width="1%">12月</td>
				<td align=center width="1%">13月</td>
				<td align=center width="1%">14月</td>
				<td align=center width="1%">15月</td>
				<td align=center width="1%">16月</td>
				<td align=center width="1%">17月</td>
				<td align=center width="1%">18月</td>
				<td align=center width="1%">19月</td>
				<td align=center width="1%">20月</td>
				<td align=center width="1%">21月</td>
				<td align=center width="1%">22月</td>
				<td align=center width="1%">23月</td>
				<td align=center width="1%">24月</td>
				<td align=center width="1%">25月</td>
				<td align=center width="1%">26月</td>
				<td align=center width="1%">27月</td>
				<td align=center width="1%">28月</td>
				<td align=center width="1%">29月</td>
				<td align=center width="1%">30月</td>
				<td align=center width="1%">31月</td>
				<td align=center width="1%">32月</td>
				<td align=center width="1%">33月</td>
				<td align=center width="1%">34月</td>
				<td align=center width="1%">35月</td>
				<td align=center width="1%">36月</td>
				<td align=center width="1%">37月</td>
				<td align=center width="1%">38月</td>
				<td align=center width="1%">39月</td>
				<td align=center width="1%">40月</td>
                <td align=center width="1%">41月</td>
				<td align=center width="1%">42月</td>
				<td align=center width="1%">43月</td>
				<td align=center width="1%">44月</td>
				<td align=center width="1%">45月</td>
				<td align=center width="1%">46月</td>
				<td align=center width="1%">47月</td>
				<td align=center width="1%">48月</td>
				<td align=center width="1%">49月</td>
				<td align=center width="1%">50月</td>
                <td align=center width="1%">51月</td>
				<td align=center width="1%">52月</td>
				<td align=center width="1%">53月</td>
				<td align=center width="1%">54月</td>
				<td align=center width="1%">55月</td>
				<td align=center width="1%">56月</td>
				<td align=center width="1%">57月</td>
				<td align=center width="1%">58月</td>
				<td align=center width="1%">59月</td>
				<td align=center width="1%">60月</td>
            </tr>

        </table>
	</div>
    <div style="overflow:scroll;height:70%;width:850px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="500%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
               <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>


            <tr  >
                        <td width="3%" align="center"><%=row.getValue("FA_CAT_NAME_1")%> </td>
                        <td width="4%" align="left"><%=row.getValue("FA_CAT_NAME_2")%></td>
                        <td width="3%" align="center"><%=String.valueOf(row.getValue("FA_CAT_NAME_3"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_1"))%> </td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_2"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_2"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_4"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_5"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_6"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_7"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_8"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_9"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_10"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_11"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_12"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_13"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_14"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_15"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_16"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_17"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_18"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_19"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_20"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_21"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_23"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_24"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_25"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_26"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_27"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_28"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_29"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_30"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_31"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_32"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_34"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_35"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_36"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_37"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_38"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_39"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_40"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_41"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_42"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_43"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_44"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_45"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_46"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_47"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_48"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_49"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_50"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_51"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_52"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_53"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_54"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_55"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_56"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_57"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_58"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_59"))%></td>
                        <td width="1%" align="center"><%=String.valueOf(row.getValue("DEPRN_COST_60"))%></td>
            </tr>
         <%
                    }
                }
            %>
        </table>
    </div>

</form>

</body>
<div style="position:absolute;top:480px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
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