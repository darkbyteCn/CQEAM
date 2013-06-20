<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.report.dto.DeptAssetsReportDTO"%>
<%--
  User: 李轶
  Date: 2009-5-15
  Time: 14:14:09
--%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
<title>部门资产构成分布(重要低耗)</title>
 </head>
<meta http-equiv="content-type" content="text/html; charset=GBK"> 
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
    DeptAssetsReportDTO dto = (DeptAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.DHDeptAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    var deptAssetsType = "<%=dto.getDeptAssetsType()%>";
    printTitleBar("资产基础报表>>部门资产构成分布(重要低耗)")
</script>
<%
if (dto.getDeptAssetsType().equals("NEW")) {
%>
   <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="15%" align="left">
                <select class="select_style1" name="organizationId" style="width:50%" onchange="getDeptOpt();"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			<td width="10%" align="right">责任部门：</td>
            <td width="20%" align="left">
                <select class="select_style1" name="responsibilityDept" style="width:90%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
            </td>
            <td width="8%" align="right">年份：</td>
            <td width="7%"><select class="select_style1" style="width:100%" name="year"><%=yearOption%></select></td>
            <td width="8%" align="right">月份：</td>
            <td width="7%"><select class="select_style1" style="width:100%" name="month"><%=monthOption%></select></td>
            <td width="15%" align="right">
                <img src="/images/eam_images/search.jpg"  onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%
} else {
%>
    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="25%" align="left">
                <select class="select_style1" name="organizationId" style="width:75%" onchange="getDeptOpt();"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			<td width="10%" align="right">责任部门：</td>
            <td width="40%" align="left">
<%
    if (dto.getDeptAssetsType().equals("DEPT_LOSE")) {
%>
           <select class="select_style1" name="countyCode" style="width:80%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
<%
    } else {
%>
           <select class="select_style1" name="responsibilityDept" style="width:80%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
<%                
    }
%>
            </td>
            <td width="15%" align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%}%>
	<input name="act" type="hidden">
	<input name="compantName" type="hidden">
	<input name="responsibilityDeptName" type="hidden">
    <input type="hidden" name="deptAssetsType" value="<%=dto.getDeptAssetsType()%>">
</form>



<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:200%">
	<table class="eamDbHeaderTable" border="1" width="200%" height="40">
		<tr height="20">
			<td width="8%" align="center" rowspan="2" height="10">公司</td>
			<td width="20%" align="center" rowspan="2" height="10">责任部门</td>
            <td width="5%" align="center" rowspan="2" height="10">原值</td>
            <td width="5%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="5%" align="center" rowspan="2" height="10">净值</td>
            <td width="5%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="5%" align="center" rowspan="2" height="10">净额</td>
            
            <td width="5%" align="center" rowspan="2" height="10">当期折旧</td>
            <td width="5%" align="center" rowspan="2" height="10">资产数量</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总数比重</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="5%" align="center" height="10" rowspan="2">比上月增长率</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="7%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="3%" align="center" height="5">2006</td>
            <td width="3%" align="center" height="5">2007</td>
            <td width="3%" align="center" height="5">2008</td>
        </tr>
    </table>
</div>
			
<div id="dataDiv" style="overflow:scroll;height:350px;width:840px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String COMPANY="";
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("DEPT_NAME"));
            boolean isNew= COMPANY.equals(row.getStrValue("COMPANY"));
            COMPANY= row.getStrValue("COMPANY");
%>
        <tr height="22" <% if (bkColor){%> bgcolor="YELLOW" <%}%> >
			<td width="8%" ><%=isNew?"":COMPANY%></td>
			<td width="20%" align="right"  ><%=row.getValue("DEPT_NAME")%></td>
            <td width="5%" align="right"></td>
			<td width="5%" align="right"></td>
            <td width="5%" align="right"></td>
            <td width="5%" align="right"></td>
			<td width="5%" align="right"></td>
            <td width="5%" align="right"></td>
			<td width="5%" align="right"><%=row.getValue("ITEM_QTY")%></td>
			<td width="6%" align="right"><%=row.getValue("ASSETS_RATE")%></td>
            <td width="6%" align="right"></td>
            <td width="5%" align="right"></td>
            <td width="6%" align="right"></td>

            <td width="3%" align="right"></td>
            <td width="3%" align="right"></td>
            <td width="3%" align="right"></td>
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
<div style="position:absolute;top:465px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

<%--function do_ShowDetail(companyName, deptName){--%>
<%--	var url = "/servlet/com.sino.ams.system.rent.servlet.RentServlet";--%>
<%--    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';--%>
<%--	url += "?act=" + "<%=AssetsActionConstant.CHECK_ACTION%>";--%>
<%--	url += "&companyName=" + companyName + "&responsibilityDeptName=" + deptName;--%>
<%--    window.open(url, "POST", style);--%>
<%--}--%>

function do_SelectContent() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
            mainFrm.contentName.value = "";
            mainFrm.contentCode.value = "";
        }
}

var xmlHttp;
function getDeptOpt() {
    var organizationId = document.getElementById("organizationId").value ;
    var url = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=CHOOSE_GROUP&organizationId=" + organizationId;
    xmlHttp = GetXmlHttpObject(setDeptOpt);
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
    xmlHttp.send("a=1");
}

function setDeptOpt() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        if (xmlHttp.status == 200) {//成功
            var resText = xmlHttp.responseText;
            var resArray = resText.parseJSON();
            if (resArray == "ERROR") {
                alert(resText);
            } else {
                var littleCategoryObj = document.getElementById("responsibilityDept");
                littleCategoryObj.length = 0;
                var emptyOption = new Option("--请选择--", "");
                littleCategoryObj.add(emptyOption)
                if (resArray.length > 0) {
                    var retStr;
                    for (var i = 0; i < resArray.length; i++) {
                        retStr = resArray[i];
                        var arr = retStr.split("$");
                        var option = new Option(arr[1], arr[0]);
                        littleCategoryObj.add(option)
                    }
                }
            }
            xmlHttp = null;
        }
    }
}
</script>