<%--
  Created by HERRY.
  Date: 2008-10-14
  Time: 17:50:47
--%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsNoMatchDTO" %>
<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>报废资产查询</title>
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
<body leftmargin="0" topmargin="0">
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')" >
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute("FA_ASSETS_DTO");
//    out.print(dto);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.DiscardedAssetsServlet" name="mainForm" method="post">

    <script type="text/javascript">
        printTitleBar("报废资产查询")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td width=8% align="right"> 公司名称：</td>
            <td width="15%"><select name="organizationId" id="organizationId"
                                    style="width:100%"><%=request.getAttribute("OU")%>
            </select></td>

            <td width="8%" align="right">条码：</td>

            <td width="10%"><input type="text" name="tagNumber" value="<%=dto.getTagNumber()%>" style="width:100%"></td>

            <td width="8%" align="right">报废日期：</td>
            <td  width="15%"><input type="text" name="startDate" value="<%=dto.getStartDate()%>"
                       style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                       onclick="gfPop.fStartPop(startDate, endDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate)">
            </td>
            <td width="8%" align="right">到：</td>
            <td width="15%"><input type="text" name="endDate" value="<%=dto.getSQLEndDate()%>"
                       style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                       onclick="gfPop.fEndPop(startDate, endDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(startDate, endDate)">
            </td>
        </tr>
        <tr>
            <td align="right">资产名称：</td>
            <td><input type="text" name="assetsDescription" value="<%=dto.getAssetsDescription()%>" style="width:100%"></td>
            <td align="right">规格型号：</td>
            <td><input type="text" name="modelNumber" value="<%=dto.getModelNumber()%>" style="width:100%"></td>
            <td align="right">原责任人：</td>
            <td><input type="text" name="assignedToName" value="<%=dto.getAssignedToName()%>" style="width:80%"></td>
            <td width=10% align="right" colspan=2><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
        </tr>
    </table>
    <%--<input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <script type="text/javascript">
        var columnArr = new Array("公司名称", "资产标签", "资产名称","资产型号","资产类别","原责任人","启用日期","报废日期");
        var widthArr = new Array("10%", "10%",  "15%","15%", "10%",  "10%", "10%", "10%");
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
            <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="10%" align="center"><%=row.getValue("ORGANIZATION_NAME")%> </td>
                <td width="10%" align="left"><%=row.getValue("TAG_NUMBER")%></td>
                <td width="15%"><%=String.valueOf(row.getValue("ASSETS_DESCRIPTION"))%></td>
                <td width="15%"><%=String.valueOf(row.getValue("MODEL_NUMBER"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("FA_CATEGORY_CODE"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("ASSIGNED_TO_NAME"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("DATE_PLACED_IN_SERVICE"))%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("RETIRE_DATE"))%></td>

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