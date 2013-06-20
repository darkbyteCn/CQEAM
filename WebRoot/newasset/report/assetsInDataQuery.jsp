<%@ page import="com.sino.ams.newasset.report.dto.ReportInDataDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-14
  Time: 16:59:12
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<title>报表数据统一维护</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage(),showPeriod();">
<%
    ReportInDataDTO dto = (ReportInDataDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
   printTitleBar("管理指标类报表-->>报表数据统一维护")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
        <%
            if(dto.getKpi() == true){
        %>
            <td width="10%" align="right">公司名称：</td>
			<td width="20%"><select name="companyCode" style="width:90%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select></td>
        <%
            }
        %>
            <td width="10%" align="right">报表类型</td>
            <td width="20%">
                <select name="managerGuideType" style="width:100%">
                <%=request.getAttribute(AssetsWebAttributes.MANAGE_INDICATORS_OPTION)%>
                </select>
            </td>
            <td width="10%" align="right">会计期间：</td>
            <input id="period1" name="period1" type="hidden" value="<%=dto.getPeriod()%>"/>
			<td width="12%" align="right">
				<select fieldLabel="会计期间" name="period" id="period" style="width:100%;text-align:">
					<option value="">----请选择----</option>
					<option value="201201">2012-01</option>
					<option value="201202">2012-02</option>
					<option value="201203">2012-03</option>
					<option value="201204">2012-04</option>
					<option value="201205">2012-05</option>
					<option value="201206">2012-06</option>
					<option value="201207">2012-07</option>
					<option value="201208">2012-08</option>
					<option value="201209">2012-09</option>
					<option value="201210">2012-10</option>
					<option value="201211">2012-11</option>
					<option value="201212">2012-12</option>
					<option value="201301">2013-01</option>
					<option value="201302">2013-02</option>
					<option value="201303">2013-03</option>
					<option value="201304">2013-04</option>
					<option value="201305">2013-05</option>
					<option value="201306">2013-06</option>
					<option value="201307">2013-07</option>
					<option value="201308">2013-08</option>
					<option value="201309">2013-09</option>
					<option value="201310">2013-10</option>
					<option value="201311">2013-11</option>
					<option value="201312">2013-12</option>
					<option value="201401">2014-01</option>
					<option value="201402">2014-02</option>
					<option value="201403">2014-03</option>
					<option value="201404">2014-04</option>
					<option value="201405">2014-05</option>
					<option value="201406">2014-06</option>
					<option value="201407">2014-07</option>
					<option value="201408">2014-08</option>
					<option value="201409">2014-09</option>
					<option value="201410">2014-10</option>
					<option value="201411">2014-11</option>
					<option value="201412">2014-12</option>
					<option value="201501">2015-01</option>
					<option value="201502">2015-02</option>
					<option value="201503">2015-03</option>
					<option value="201504">2015-04</option>
					<option value="201505">2015-05</option>
					<option value="201506">2015-06</option>
					<option value="201507">2015-07</option>
					<option value="201508">2015-08</option>
					<option value="201509">2015-09</option>
					<option value="201510">2015-10</option>
					<option value="201511">2015-11</option>
					<option value="201512">2015-12</option>
					<option value="201601">2016-01</option>
					<option value="201602">2016-02</option>
					<option value="201603">2016-03</option>
					<option value="201604">2016-04</option>
					<option value="201605">2016-05</option>
					<option value="201606">2016-06</option>
					<option value="201607">2016-07</option>
					<option value="201608">2016-08</option>
					<option value="201609">2016-09</option>
					<option value="201610">2016-10</option>
					<option value="201611">2016-11</option>
					<option value="201612">2016-12</option>
				</select>
			</td>
            <td width="20%" align="right">
                <img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
                <img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
            </td>
		</tr>
	</table>
	<input type="hidden" name="act" value="">
	<input type="hidden" name="isKpi" value="<%=dto.getKpi()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
        <tr height="22">
            <td width="10%" align="center">报表类型</td>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">创建时间</td>
        </tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            if(dto.getKpi()){
%>
            <tr height="22" style="cursor:hand" onClick="do_ShowDetailByPaste('<%=row.getValue("PERIOD")%>', '<%=row.getValue("COMPANY_CODE")%>', '<%=row.getValue("KPI_CODE")%>')">
<%
            } else {
%>
            <tr height="22" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("REPORT_ID")%>')">
<%
            }
%>
            <td width="10%" align="center"><%=row.getValue("REPORT_TYPE")%></td>
            <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
            <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
            <td width="10%" align="center"><%=row.getValue("CREATION_DATE")%></td>
        </tr>
<%
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:430px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
}

function do_Search(){
	var period = mainFrm.period.value;
	if(period == null || period == ""){
		alert("会计期间不能为空！");
		mainFrm.period.focus();
		return;
	}
	var dates=mainFrm.period.value.split("-",3);
	if (dates.length==3) {
		mainFrm.period.value=dates[0]+dates[1];
	}	
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_CreateOrder() {
    if(mainFrm.managerGuideType){
        if(mainFrm.managerGuideType.value == ""){
			alert("请先选择报表类型！");
			mainFrm.managerGuideType.focus();
			return;
		}
        if(mainFrm.period.value == "" || mainFrm.period.value == null){
            alert("会计期不能为空");
            return;
        }
        if("<%=dto.getKpi()%>" == "true"){
            if(mainFrm.companyCode.value == null || mainFrm.companyCode.value == ""){
                alert("所属公司不能为空！");
                return;
            }
        }
        do_CreateData();
    }
}

function do_CreateData() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet";
    mainFrm.submit();
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet";
    mainFrm.submit();
}

function do_ShowDetail(reportId) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?reportId=" + reportId;
    mainFrm.submit();
}

function do_ShowDetailByPaste(period, companyCode, kpiCode) {
    if('<%=user.isProvinceUser()%>' == 'false'){
        if(companyCode != '<%=user.getCompanyCode()%>'){
        alert("您无权操作本条记录，请选择您所属的公司！");
        return;
        }    
    }
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?period=" + period + "&companyCode=" + companyCode + "&managerGuideType=" + kpiCode;
    mainFrm.submit();
}
function showPeriod(){
	if(document.getElementById("period1").value!=""){
		document.getElementById("period").value=document.getElementById("period1").value
	}
}


</script>