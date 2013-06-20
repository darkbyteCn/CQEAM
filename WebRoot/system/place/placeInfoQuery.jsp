<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.place.dto.PlaceInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>

<%@ include file="/newasset/headerInclude.htm" %>

<html>
<head>
    <title>第一段地点信息维护</title>
</head>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('do_Search()')">
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	PlaceInfoDTO dto = (PlaceInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String ou = (String)request.getAttribute(WebAttrConstant.CITY_OPTION);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	Row row = null;
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.place.servlet.PlaceInfoServlet">

<input type="hidden" name="checkedObjectNo" id="checkedObjectNo" value="">
<script type="text/javascript">
    printTitleBar("第一段地点信息维护");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");  
    var ArrActions = new Array(ArrAction0);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();

</script>
<table width="100%" border="0" bgcolor="#FFFFFF" id="table1">
    <tr>
        <td width="25%" align="right">地点第一段：</td>
        <td width="25%"><input class="input_style1" style="width:100%" type="text" name="flexValue" value=""></td>
        <td></td>
        <td width="25%" align="right">公司名称：</td>
        <td width="25%"><select name="companyCode" class="select_style1"
							style="width: 100%"><%=ou%></select></td>
        <td></td>
    </tr>
</table>
    <input type="hidden" name="act">
<div id="headDiv" style="overflow:hidden;position:absolute;top:80px;left:1px;width:150%">
    <table class="headerTable" border="1" style="width:100%">
        <tr height="20px" onClick="executeClick(this)" style="cursor:pointer">
			<td width="20%" align="center">公司名称</td>
			<td width="20%" align="center">成本中心</td>
            <td width="15%" align="center">区域代码</td>
            <td width="45%" align="center">区域描述</td> 
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:hidden;position:absolute;top:100px;left:1px;width:150%">
    <table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
 <%
		
 if (rows != null && !rows.isEmpty()) {
		for (int i = 0; i < rows.getSize(); i++) {
			 row=rows.getRow(i);
%>
		<tr height="23px" style="cursor:pointer" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="20%"  onclick="show_Detail('<%=row.getValue("FLEX_VALUE_ID")%>','<%=row.getValue("COMPANY_CODE")%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("COMPANY")%>"></td>
            <td width="20%" onclick="show_Detail('<%=row.getValue("FLEX_VALUE_ID")%>','<%=row.getValue("COMPANY_CODE")%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("COUNTY_NAME")%>"></td>
            <td width="15%" onclick="show_Detail('<%=row.getValue("FLEX_VALUE_ID")%>','<%=row.getValue("COMPANY_CODE")%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("FLEX_VALUE")%>"></td>
            <td width="45%" onclick="show_Detail('<%=row.getValue("FLEX_VALUE_ID")%>','<%=row.getValue("COMPANY_CODE")%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("DESCRIPTION")%>"></td>
		</tr>
<%  
	}
 }
%>

    </table>
</div>
 
</form>
	
<% 
    if (rows != null && !rows.isEmpty()) {
%>
<div style="position: absolute; bottom:1px; left: 0; right: 20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>

<%
    }
%>
 

</body>
</html>
<iframe width=174 height=179 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
}

function initPage(){
	do_SetPageWidth();
	do_SetControlBtnEnable();
}

function do_SetControlBtnEnable(){
	var enPic = 5;
	var disPic = 4;
        ShowSinoButton(disPic);
        HideSinoButton(enPic);
}

function do_SelectUser(){
	var lookUpName = "LOOK_UP_USER";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=" + mainFrm.organizationId.value;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.createdBy.value = obj["userId"];
		mainFrm.createdByName.value = obj["userName"];
	}
}


function do_Search() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}


 

function show_Detail(workorderObjectNo,companyCode) {
    var url = "/servlet/com.sino.ams.system.place.servlet.PlaceInfoServlet";
	url += "?act=<%=WebActionConstant.DETAIL_ACTION%>";
	url += "&flexValueId=" + workorderObjectNo;
	url += "&companyCode=" + companyCode;
    var factor = 0.7;
    var width = window.screen.availWidth * 0.5;
    var height = window.screen.availHeight * 0.5;
    var left = window.screen.availWidth * (1 - factor)/ 2;
    var top = window.screen.availHeight * (1 - factor)/ 2;
    var popscript = "width=" + width + "px,height=" + height + "px,top=" + top + "px,left=" + left + "px,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
    window.open(url, 'commObj', popscript);
}
 
var xmlHttp = null;

function do_ChangeCounty(obj) {
	var url = "/servlet/com.sino.ams.system.place.servlet.PlaceInfoServlet";
	url += "?act=CHANGE_COUNTY";
	url += "&organizationId=" + obj.value;
	do_ProcessSimpleAjax(url, null);
}

/**
 * 将返回的列表加入区县下拉框。
 * 修改完成。
 */
function do_ProcessResponse(responseContent){
	mainFrm.countyCode.outerHTML = "<select class=\"select_style1\" style=\"width:100%\" name=\"countyCode\">" + responseContent + "</select>";
}

function getLineObjectNo( checkObj ){
 	var objectNoObj = checkObj.parentElement.children[1]; 
 	return objectNoObj;
}



</script>