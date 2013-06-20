<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<html>

<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>有卡无物</title>
</head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
    AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    String disabled = request.getParameter("disabled");
    if (disabled == null) {
        disabled = "";
    }
%>
<form name="mainFrm" id="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CheckResultServletFive">
    <%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" border="0" class="queryTable">
        <tr>
            <td width="15%">公司名称：<%=dto.getCompanyName()%>
            </td>
            <%--<td width="15%">截止日期：<%=dto.getEndDate()%>--%>
            <!--</td>-->
            <% if (!disabled.equals("")) { //市用
            %>
            <td width="10%" align="center">成本中心：</td>
            <%--<td width="25%"><select name="costCenterCode" style="width:100%" <%=disabled%>><%=dto.getCostCenterOpt()%></select></td>--%>
            <td width="25%"><input type="text" name="costCenterName" value="<%=dto.getCostCenterName()%>" readonly class="input_style2"></td>
            <% } %>
            <td width="10%" align="center">关键字：</td>
            <td width="15%"><input class="input_style1" type="text" name="checkLocationName" value="<%=dto.getCheckLocationName()%>" style="width:80%"><%--<a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a>--%>
            </td>
            <td width="10%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
        </tr>
    </table>
    <input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
       <% if (disabled.equals("")) { //市用
            %>
    <input type="hidden" name="costCenterCode" value="<%=dto.getCostCenterCode()%>">
     <% } %>
    <input type="hidden" name="companyName" value="<%=dto.getCompanyName()%>">
    <input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
    <input type="hidden" name="analyseType" value="<%=AssetsDictConstant.CHECK_RESULT_5%>"><!--用于控制导出类型-->
    <input name="act" type="hidden">
    <input name="disabled" type="hidden" value="<%=disabled%>">
    <input name="costCode" type="hidden" value="<%=dto.getCostCode()%>">

    <input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
    <input type="hidden" name="fromBarcode" value="<%=dto.getFromBarcode()%>">
    <input type="hidden" name="toBarcode" value="<%=dto.getToBarcode()%>">
    <input type="hidden" name="creationDate" value="<%=dto.getCreationDate()%>">
    <input type="hidden" name="lastUpdateDate" value="<%=dto.getLastUpdateDate()%>">
</form>

<div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:26px;left:0px;width:100%" class="crystalScroll">

    <table class="eamHeaderTable" border="1" width="360%">
        <tr height="22">
            <td width="3%" align="center">资产标签</td>
            <td width="3%" align="center">应用领域</td>
            <td width="10%" align="center">资产类别</td>
            <td width="8%" align="center">资产名称</td>

            <td width="8%" align="center">资产型号</td>
            <td width="3%" align="center">资产数量</td>
            <td width="3%" align="center">计量单位</td>
            <td width="3%" align="center">员工号</td>

            <td width="3%" align="center">责任人</td>
            <td width="5%" align="center">地点代码</td>
            <td width="8%" align="center">地点名称</td>
            <td width="3%" align="center">创建日期</td>

            <td width="3%" align="center">启用日期</td>
            <td width="3%" align="center">原始成本</td>
            <td width="3%" align="center">当前成本</td>
            <td width="3%" align="center">资产净值</td>

            <td width="3%" align="center">减值准备累计</td>
            <td width="3%" align="center">资产残值</td>
            <td width="10%" align="center">折旧费用账户</td>
            <td width="3%" align="center">项目编号</td>

            <td width="8%" align="center">项目名称</td>
        </tr>
    </table>

</div>
<div style="overflow:scroll;height:510px;width:100%;position:absolute;top:49px;left:0px" align="left"
     onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="360%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (hasData) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="22">
            <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
            <td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY1")%>"></td>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
            <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

            <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
            <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("CURRENT_UNITS")%>"></td>
            <td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
            <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>

            <td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
            <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
            <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
            <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_CREATE_DATE")%>"></td>

            <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
            <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("ORIGINAL_COST")%>"></td>
            <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
            <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>

            <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("IMPAIR_RESERVE")%>"></td>
            <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("SCRAP_VALUE")%>"></td>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPRECIATION_ACCOUNT")%>"></td>
            <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_PROJECT_NUMBER")%>"></td>

            <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>

        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<%
    if (hasData) {
%>
<div style="position:absolute;top:580px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>
</body>

</html>
<script>
    function do_Search() {
        mainFrm.act.value = "<%=AMSActionConstant.QUERY_ACTION%>";
    <%
            if (!disabled.equals("")) { //市用
    %>
//        mainFrm.costCenterCode.disabled = false;
    <%
    }
    %>
        mainFrm.submit();
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }


    function do_SelectAddress() {
        var lookUpName = "LOOK_UP_ADDRESS";
        var dialogWidth = 55;
        var dialogHeight = 30;
        var userPara = "organizationId=<%=dto.getOrganizationId()%>";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (objs) {
            var obj = objs[0];
            mainFrm.checkLocationName.value = obj["toObjectName"];
        }
    }
</script>
