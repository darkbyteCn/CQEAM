<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.place.dto.PlaceInfoDTO" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <title>第一段地点信息维护</title>
    <style type="text/css" media="screen">
			body {
				font: 11px arial;
			}
			.suggest_link {
				background-color: #FFFFFF;
				padding: 0px 6px 2px 6px;
			}
			.suggest_link_over {
				background-color: #CCCCCC;
				padding: 0px 6px 2px 6px;
			}
			#search_suggest {
				position: absolute; 
				background-color: #FFFFFF; 
				text-align: left			
			}		
		</style>
</head>
<script type="text/javascript" src="/WebLibary/js/jquery-1.2.6.js"></script>
<%
RequestParser reqParser = new RequestParser();
reqParser.transData(request);
RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
String ou = (String)request.getAttribute(WebAttrConstant.CITY_OPTION);
%>
<body leftmargin="0" topmargin="0" leftmargin="0" onload="oo();">
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.place.servlet.PlaceInfoServlet">
<script type="text/javascript">
    printTitleBar("第一段地点信息维护");
    ArrActions0 = new Array(true, "保存", "action_save.gif", "保存", "do_SaveObject");
    ArrActions1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
    var ArrActions = new Array(ArrActions0,ArrActions1);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();
</script>
    <table width="90%" align="center" border="0" bordercolor="#666666" >
    <input type="hidden" name="act"/>
     <%
		
 if (rows != null && !rows.isEmpty()) {
		for (int i = 0; i < rows.getSize(); i++) {
			Row row=rows.getRow(i);
%>
        <tr>
            <td width="25%" align="right">公司名称：</td>
        	<td width="50%">
        	<select id="companyCode" name="companyCode" class="select_style1" onchange="jilian(this.value);" style="width: 100%"><%=ou%></select></td>
			<td><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="20%" align="right">成本中心：</td>
            <td width="50%" colspan="3"><select id="selectData" name="countyCode"  style="width:100%"><option value="<%=row.getValue("COUNTY_CODE")%>"><%=row.getValue("COUNTY_NAME")%></option></select></td>
            <td><input type="hidden" name="flexValueId" value="<%=row.getValue("FLEX_VALUE_ID")%>" /></td>
        </tr>
        <tr id="bts">
            <td width="20%" align="right" >地点第一段：</td>
            <td width="50%" colspan="3"> <input disabled="true" name="btsNo" type="text" id="btsNo" value="<%=row.getValue("FLEX_VALUE")%>" class='input_style1' style="width:100%" ></td>
            <td></td>
        </tr>
        <tr id="bts">
            <td align="right" width="20%">区域描述：</td>
            <td width="50%" colspan="3"> <input disabled="true" name="btsNo" type="text" id="btsNo" value="<%=row.getValue("DESCRIPTION")%>" class='input_style1' style="width:100%" ></td>
            <td></td>
        </tr>
    <%  
	}
 }
%>
    </table> 
     
</form>
<jsp:include page="/message/MessageProcess"/> 
	 
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">

function suggestOver(div_value) {
	div_value.className = 'suggest_link_over';
}

function suggestOut(div_value) {
	div_value.className = 'suggest_link';
}

function setSearch(value) {
	document.getElementById('location').value = value;
	document.getElementById('search_suggest').innerHTML = '';
	
	var county = mainFrm.county.value;
	var city = mainFrm.city.value;
	var countyCode = mainFrm.countyCode.value;
	var organizationId = mainFrm.organizationId.value;
	var location = document.getElementById('location').value
	var currCitySelectText = document.all.city.options[document.all.city.selectedIndex].text;
	var currCountySelectText = document.all.county.options[document.all.county.selectedIndex].text;
	var currcountyCodeSelectText = document.all.countyCode.options[document.all.countyCode.selectedIndex].text;
	var temp = 0;
	if(countyCode !=""){
		mainFrm.workorderObjectName.value =currcountyCodeSelectText+"."+currCitySelectText+currCountySelectText+location+"."+temp+temp+temp;
	}
}

function do_Cancle(){
	document.getElementById('search_suggest').innerHTML = '';
}

function do_Close() {
	if(confirm("请确保已经保存了你的工作！继续请点击“确定”按钮，否则请点击“取消”按钮！")){
		self.close();
	}
}

function do_SaveObject() {
		if(confirm("请确保已经保存了你的工作！继续请点击“确定”按钮，否则请点击“取消”按钮！")){
			mainFrm.act.value="SAVE_ACTION";
			mainFrm.submit();
			self.close();
			opener.do_Search();
			}
}

function choosePrj() {
	document.getElementById('search_suggest').innerHTML = '';
    var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
    var dialogWidth = 30;
    var dialogHeight = 20;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
        dto2Frm(projects[0], "mainFrm");
    }
}


function do_ShowCountyOption(resText){
    mainFrm.county.outerHTML = "<select class=\"select_style1\"  style=\"width:100%\" name=\"county\">" + resText + "</select>";
}

 

 

 

/**
 * 将返回的列表加入所属区域下拉框
 * 修改完成。
 */

function do_ResponseOrgChange(resText){
    var resArr = resText.split("&");
    if(resArr[0] != "EMPTY_CONTENT"){
        mainFrm.countyCode.outerHTML = "<select  name=\"countyCode\"  class=\"select_style1\" style=\"width:100%\" onchange=\"do_ProduceWorkOrderObjectCode()\">" + resArr[0] + "</select>";
    }
    if(resArr[1] != "EMPTY_CONTENT"){
        selectSpecialOptionByItem(mainFrm.isTd, resArr[1]);
    }
}

 

function do_ProduceWorkOrderObjectCode(){
    var areaValue = mainFrm.countyCode.value;
    var objectCat = mainFrm.objectCategory.value;
    var organizationId = mainFrm.organizationId.value;
    if(objectCat != "" && areaValue != ""){
        var actionURL = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
        var userParameters = "act=GET_MAX_OBJECT_CODE";
        userParameters += "&organizationId=" + organizationId;
        userParameters += "&objectCategory=" + objectCat;
        userParameters += "&countyCode=" + areaValue;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_ShowWorkOrderObjectCode, false);
        ajaxProcessor.setSendData(userParameters);
        ajaxProcessor.performTask();
    }
}

function do_ShowWorkOrderObjectCode(resText){
    mainFrm.workorderObjectCode.value = resText;
}
function oo()
{
	var oo=document.getElementById("companyCode").value;
	jilian(oo);
}
function jilian(obj){
	$.ajax({
    url: '/servlet/com.sino.ams.system.place.servlet.PlaceGetCountyServlet?companyCode='+obj+ '&countyCode=' + document.getElementById("selectData").value,
    type: 'POST',
    success: function (data)
    {
		var obj=document.getElementById("selectData");
		obj.outerHTML = "<select id='selectData' name='countyCode' style='width:100%'>"+data+"</select>";
    }
});
}
</script>