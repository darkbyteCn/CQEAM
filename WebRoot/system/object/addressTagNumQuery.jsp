<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>

<%@ include file="/newasset/headerInclude.htm" %>

<html>
<head>
    <title>生成已有地址标签号</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('do_Search()')" style="background-color:#eeeeee">
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
	EtsObjectDTO dto = (EtsObjectDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    Row row = null;
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.object.servlet.AddressTagNumberServlet">
<script type="text/javascript">
    printTitleBar("生成已有地址标签号")
</script>
<table width="100%" border="0" bgcolor="#eeeeee">
    <tr>
        <td width="9%" align="right">公司名称：</td>
        <td width="18%"><select style="width:100%" name="organizationId" onchange="do_ChangeCounty(this);"><%=dto.getOrganizationOption()%></select></td>
        <td width="9%" align="right">地点编号：</td>
        <td width="15%"><input style="width:100%" type="text" name="workorderObjectCode" value="<%=dto.getWorkorderObjectCode()%>"></td>
        <td width="9%" align="right">创建日期：</td>
        <td width="10%"><input style="width:100%" type="text" name="startDate" value="<%=dto.getStartDate()%>" title="点击选择起始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"></td>
        <td width="10%"><input style="width:100%" type="text" name="endDate" value="<%=dto.getEndDate()%>" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"></td>
        <td width="9%" align="right">有效状态：</td>
        <td width="13%"><select style="width:100%" name="enabled" id="enabled"><%=dto.getEnableOption()%></select></td>
    </tr>
    <tr>
		<td width="9%" align="right">所在区县：</td>
		<td width="18%"><select style="width:100%" name="countyCode" ><%=dto.getCountyOption()%></select></td>
		<td width="9%" align="right">地点名称：</td>
		<td width="15%"><input name="workorderObjectName" style="width:100%" value = "<%=dto.getWorkorderObjectName()%>"></td>
		<td width="9%" align="right">创建人：</td>
		<td width="20%" colspan="2"><input style="width:85%" type="text" name="createdBy" value="<%=dto.getCreatedBy()%>"><a href=""  title="点击选择地点" onclick="do_SelectUser(); return false;">[…]</a></td>
		<td width="9%" align="right">地点专业：</td>
		<td width="13%"><select style="width:100%" name="objectCategory"><%=dto.getObjCategoryOption()%></select></td>
    </tr>
    <tr>
    	<td width="9%" align="right">行政区划：</td>
		<td width="71%" colspan = "4"><select style="width:35.1%" name="areaType" ><%=dto.getAreaTypeOption() %></select></td>
		<td  align="right" colspan = "4">
			<img src="/images/eam_images/search.jpg" style="cursor:hand" onclick="do_Search();" title="查询">
			<img src="/images/eam_images/export.jpg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">
			
		</td>
    </tr>
</table>


<input type="hidden" name="act">    
<div id="headDiv" style="overflow:hidden;position:absolute;top:92px;left:1px;width:150%">
    <table class="headerTable" border="1" style="width:150%">
        <tr height="20" onClick="executeClick(this)" style="cursor:hand">

			<td width="7%" align="center">公司名称</td>
            <td width="11%" align="center">地点代码</td>
            <td width="23%" align="center">地点名称</td>

            <td width="15%" align="center">所属区县</td>
            <td width="6%" align="center">地点专业</td>
            <td width="6%" align="center">创建人</td>

			<td width="6%" align="center">创建日期</td>
			<td width="6%" align="center">失效日期</td>
			<td width="6%" align="center">上次更新人</td>
			<td width="6%" align="center">上次更新日期</td>
			<td width="8%" align="center">行政区划</td>
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:scroll;height:62%;width:150%;position:absolute;top:115px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (hasData) {
		String objectNo = "";
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
			objectNo = row.getStrValue("WORKORDER_OBJECT_NO");
%>
        <tr height="20" style="cursor:hand" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">

            <td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
            <td width="11%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="23%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>

            <td width="15%"><input type="text" class="finput" readonly value="<%=row.getValue("COUNTY_NAME")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("OBJECT_CATEGORY_NAME")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("CREATION_USER")%>"></td>

            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("DISABLE_DATE")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("UPDATED_USER")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("LAST_UPDATE_DATE")%>"></td>
			<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("VALUE")%>"></td>
		</tr>
<%
	    }   
	}
%>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div id="navigatorDiv" style="position:absolute;top:448px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}	
%>
<%--</fieldset>--%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
}

function do_SetImageProp(){
	var enabled = mainFrm.enabled.value;
	var enPic = document.getElementById("enablePic");
	var disPic = document.getElementById("disablePic");
	if(enabled == "Y"){
		enPic.style.display = "none";
		disPic.style.display = "";
	} else if(enabled == "N"){
		enPic.style.display = "";
		disPic.style.display = "none";
	} else {
		enPic.style.display = "none";
		disPic.style.display = "none";
	}
}

function do_SelectUser(){
	var lookUpName = "LOOK_UP_USER";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=" + mainFrm.organizationId.value;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.createdBy.value = obj["userName"];
	}
}


function do_Search() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}


function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}


function show_Detail(workorderObjectNo) {
    var url = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
	url += "?act=<%=WebActionConstant.DETAIL_ACTION%>";
	url += "&workorderObjectNo=" + workorderObjectNo;
    var popscript = "width=500,height=300,top=100,left=250,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
    window.open(url, 'commObj', popscript);
}



var xmlHttp = null;

function do_ChangeCounty(obj) {
	var url = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
	url += "?act=CHANGE_COUNTY"
	url += "&organizationId=" + obj.value;
	do_ProcessSimpleAjax(url, null);
}

/**
 * 将返回的列表加入区县下拉框，由贾龙川继续。
 * 修改完成。
 */
function do_ProcessResponse(responseContent){
	mainFrm.countyCode.outerHTML = "<select style=\"width:100%\" name=\"countyCode\">" + responseContent + "</select>";
}
</script>