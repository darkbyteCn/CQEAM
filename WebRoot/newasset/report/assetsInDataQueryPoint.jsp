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
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
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
       
            <td width="10%" align="right">公司名称：</td>
			<td width="20%"><select onchange="setOrgName(this)"name="organizationId" style="width:90%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select></td>
    
            <td width="10%" align="right">报表类型</td>
            <td width="20%">
                <select name="managerGuideType" style="width:100%">
                <%=request.getAttribute(AssetsWebAttributes.MANAGE_INDICATORS_OPTION)%>
                </select>
            </td>
            <td width="10%" align="right">会计期间：</td>
			<td width="10%" align="left">
                <input type="text" name="period" style="width:100%" value="<%=StrUtil.nullToString(dto.getPeriod())%>" title="点击选择会计期" readonly class="noEmptyInput" onclick="gfPop.fPopCalendar(period)">
            </td>
            <td width="20%" align="right">
                <img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
                <img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
            </td>
		</tr>
	</table>
	<input type="hidden" name="act" value="">
	<input type="hidden" name="isKpi" value="<%=dto.getKpi()%>">
	<input type="hidden" name="isPoint" value="true">
	<input type="text" name="orgName">
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
            <tr height="22" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("REPORT_ID")%>','<%=row.getValue("COMPANY")%>')">
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
function setOrgName(obj){
	var opt=obj.options;
	for(i=0;i<opt.length;i++){
		if(opt[i].selected){
			mainFrm.orgName.value=opt[i].text;
			break;
		}
	}
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
	mainFrm.act.value = "POINT_AT_ACTION_QUERY";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_CreateOrder() {
if(mainFrm.organizationId.value == null || mainFrm.organizationId.value == ""){
                alert("所属公司不能为空！");
                return;
            }
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
      
            
        
        var dates=mainFrm.period.value.split("-",3);
		if (dates.length==3) {
		mainFrm.period.value=dates[0]+dates[1];
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

function do_ShowDetail(reportId,orgName) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.orgName.value=orgName;
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


</script>