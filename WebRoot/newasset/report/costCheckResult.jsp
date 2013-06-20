<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>盘点结果报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();');" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String orgId = Integer.toString(userAccount.getOrganizationId());
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	String checkType = dto.getCheckTpye();
	boolean hasData = (rows != null && !rows.isEmpty());

    int TOTAL_COUNT = 0;
    int NEED_COUNT = 0;
    int NOT_NEED_COUNT = 0;
    int SCANED_COUNT = 0;
    int IDENTICAL_COUNT = 0;
    int UNMATCHED_COUNT = 0;
    int IDENTICAL_RATE_1 = 0;
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CostCheckResultServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产分析报表-->>盘点结果统计");
</script>
	<table width="100%" border="0" class="queryTable">
		<tr>
            <td width="8%" align="right">统计类型：</td>
			<td width="12%" colspan="3">
                <select class="select_style2" size="1" name="checkTpye" style="width:100%" onchange="do_ChangeType()">
                    <option value="cost" <%=checkType.equals("cost")?"selected":""%>>按成本中心统计</option>
                    <option value="dept" <%=checkType.equals("dept")?"selected":""%>>按部门统计</option>
                </select>
            </td>
			<td width="8%" align="right" id="dis1">成本中心：</td>
			<td width="12%" colspan="3" id="dis2"><select class="select_style2" size="1" name="costCenterCode" style="width:100%"><%=dto.getOrgOpt()%></select></td>
            <td width="4%" align="right" id="dis3">部门：</td>
			<td width="12%" colspan="3" id="dis4"><select class="select_style2" size="1" name="deptCode" style="width:100%"><%=dto.getDeptOpt()%></select></td>
			<!--  <td width="20%"><img alt="点击查询" border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();"></td>
			-->
		</tr>
        <tr>
			<td width="8%" align="right">创建日期：</td>
            <td width="12%"><input class="" type="text" name="creationDate" value="<%=dto.getCreationDate()%>" style="width:100%;cursor:hand" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(creationDate, lastUpdateDate)"></td>
            <td width="4%" align="center">到</td>
            <td width="12%"><input class="" type="text" name="lastUpdateDate" value="<%=dto.getLastUpdateDate()%>" style="cursor:hand;width:100%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(creationDate, lastUpdateDate)"></td>
            <td width="8%" align="right">盘点日期：</td>
            <td width="12%"><input class="" type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:100%;cursor:hand" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
            <td width="4%" align="center">到</td>
            <td width="12%"><input class="" type="text" name="endDate" value="<%=dto.getEndDate()%>" style="cursor:hand;width:100%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
			<td width="20%" align="right">
			<img alt="点击查询" border="0" src="/images/eam_images/search.jpg" onclick="do_Search();">
			<img src="/images/eam_images/export.jpg" style="cursor:'hand'"  onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
    </table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden" value="<%=userAccount.getCompany()%>">
	<input name="organizationId" type="hidden" value="<%=orgId%>">
	<input type="hidden" name="analyseType" value="<%=AssetsDictConstant.ANALYZE_CATEGORY_4%>">
    <input name="costCenterName" type="hidden" value="">
    <input name="costCode" type="hidden" value="">
</form>


<div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:70px;left:0px;width:100%" class="crystalScroll">
	<table class="eamHeaderTable" border="1" width="100%" style="table-layout:fixed;word-break:keep-all;" >
		<tr height="22">
            <%
                if (checkType.equals("cost")) {
            %>
            <td width="14%" align="center">成本中心</td>
            <%
                } else if (checkType.equals("dept")) {
            %>
            <td width="14%" align="center">部门</td>
            <%
                } 
            %>
			<td width="9%" align="center">MIS资产数量</td>
			<td width="11%" align="center">MIS需PDA扫描数量</td>
			<td width="13%" align="center">MIS无需PDA扫描数量</td>
			<td width="10%" align="center">已盘点总量</td>
			<td width="10%" align="center">已盘MIS数量</td>
			<td width="10%" align="center">已盘实物数量</td>
			<td width="12%" align="center" style="word-break:keep-all;overflow:hidden;">盘点率<!--(按MIS数)--></td>
			<!--<td width="12%" align="center">帐实相符率(按盘点数)</td>-->
		</tr>
	</table>
</div>
<div style="overflow:scroll;height:550px;width:100%;position:absolute;top:93px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="table-layout:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
             TOTAL_COUNT+=Integer.parseInt(row.getStrValue("TOTAL_COUNT"));
             NEED_COUNT+=Integer.parseInt(row.getStrValue("NEED_COUNT"));
             NOT_NEED_COUNT+=Integer.parseInt(row.getStrValue("NOT_NEED_COUNT"));
             SCANED_COUNT+=Integer.parseInt(row.getStrValue("SCANED_COUNT"));
             IDENTICAL_COUNT+=Integer.parseInt(row.getStrValue("IDENTICAL_COUNT"));
             UNMATCHED_COUNT+=Integer.parseInt(row.getStrValue("UNMATCHED_COUNT"));
//             IDENTICAL_RATE_1+=Integer.parseInt(row.getStrValue("IDENTICAL_RATE_1"));

%>
        <%
            if (checkType.equals("cost")) {
        %>
        <tr height="22" title="点击查看本本公司盘点详细情况" style="cursor:hand" onClick="do_ShowDetail('<%=orgId%>', '<%=row.getValue("COST_CENTER_CODE")%>', '<%=row.getValue("COST_CENTER_NAME")%>')">
            <td width="14%" ><%=row.getValue("COST_CENTER_NAME")%></td>
        <%
            } else if (checkType.equals("dept")) {
        %>
        <tr height="22">
            <td width="14%" ><%=row.getValue("COST_CENTER_NAME")%></td>
        <%
            }
        %>
			<td width="9%" align="right"><%=row.getValue("TOTAL_COUNT")%></td>
			<td width="11%" align="right"><%=row.getValue("NEED_COUNT")%></td>
			<td width="13%" align="right"><%=row.getValue("NOT_NEED_COUNT")%></td>
			<td width="10%" align="right"><%=row.getValue("SCANED_COUNT")%></td>
			<td width="10%" align="right"><%=row.getValue("IDENTICAL_COUNT")%></td>
			<td width="10%" align="right"><%=row.getValue("UNMATCHED_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("IDENTICAL_RATE_1")%></td>
			<%--<td width="12%" align="right"><%=row.getValue("IDENTICAL_RATE_2")%></td>--%>
		</tr>
<%
		}
	}
    if(hasData){
%>
        <tr bgcolor="yellow" >
            <td width="14%" ><B>汇总</B></td>
			<td width="9%" align="right"><B><%=TOTAL_COUNT%></B></td>
			<td width="11%" align="right"><B><%=NEED_COUNT%></B></td>
			<td width="13%" align="right"><B><%=NOT_NEED_COUNT%></B></td>
			<td width="10%" align="right"><B><%=SCANED_COUNT%></B></td>
			<td width="10%" align="right"><B><%=IDENTICAL_COUNT%></B></td>
			<td width="10%" align="right"><B><%=UNMATCHED_COUNT%></B></td>
            <%
                float aa = 0;
                if (NEED_COUNT>0) {
                    aa=((float) (100 * IDENTICAL_COUNT)) / (float) NEED_COUNT;
                }
                BigDecimal dbVal = new BigDecimal(aa);
                BigDecimal result = dbVal.setScale(2, BigDecimal.ROUND_HALF_UP);
            %>
                <td width="12%" align="right"><B><%=result%>%</B></td>
        </tr>
        <%}%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:660px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	do_SetPageWidth();
    var checkTpye = document.mainFrm.checkTpye.value;
    if (checkTpye == "cost" || checkTpye == "") {
        document.getElementById('dis1').style.display = "block";
        document.getElementById('dis2').style.display = "block";
        document.getElementById('dis3').style.display = "none";
        document.getElementById('dis4').style.display = "none";
    } else if (checkTpye == "dept") {
        document.getElementById('dis1').style.display = "none";
        document.getElementById('dis2').style.display = "none";
        document.getElementById('dis3').style.display = "block";
        document.getElementById('dis4').style.display = "block";
    }
}

function do_ChangeType(){
	var checkTpye = document.mainFrm.checkTpye.value;
    if (checkTpye == "cost") {
        document.getElementById('dis1').style.display = "block";
        document.getElementById('dis2').style.display = "block";
        document.getElementById('dis3').style.display = "none";
        document.getElementById('dis4').style.display = "none";
    } else if (checkTpye == "dept") {
        document.getElementById('dis1').style.display = "none";
        document.getElementById('dis2').style.display = "none";
        document.getElementById('dis3').style.display = "block";
        document.getElementById('dis4').style.display = "block";
    }
}


function do_Search(){
	document.mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	document.mainFrm.target = "_self";
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CostCheckResultServlet";
	document.mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    document.mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	document.mainFrm.target = "_self";
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CostCheckResultServlet";
    document.mainFrm.submit();
}

function do_ShowDetail(organizationId, costCenter, costCenterName){
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CheckResultFrmServlet";
     document.mainFrm.costCode.value = costCenter;
     document.mainFrm.costCenterName.value = costCenterName;
    document.mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
    var selObj = document.mainFrm.costCenterCode;
    selectSpecialOptionByItem(selObj, costCenter);
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    document.mainFrm.target = "assWin";
    document.mainFrm.submit();
}
</script>