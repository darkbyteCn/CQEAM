<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by HERRY.
  Date: 2007-11-26
  Time: 16:22:41
--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
<head><title>MIS资产信息(TD)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
     <script language="javascript" src="/WebLibary/js/calendar.js"></script>
     <script language="javascript" src="/WebLibary/js/datepicker.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>

</head>
<%
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute("MIS_HEADER");
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
    ServletConfigDTO configDto = SessionUtil.getServletConfigDTO(request);
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.td.match.servlet.ManualMatchTdMIS" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("MIS资产信息(TD)")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act" value="<%=act%>">
    <table width="100%" class="queryHeadBg" id="tb" border = "0">
        <tr>
            <td width="13%" align="right">关键字：</td>
            <td width="25%"><input type="text" name="key" value="<%=dto.getKey()%>" style="width:95%"  class="blueBorder"></td>
<% if (configDto.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {   %>
      		<td width="13%" align="right">责任部门：</td>
      		<td width="49%" colspan="2"><input type="text" class="blueBorder" name="responsibilityDept" style="width:78%" value ="<%=dto.getResponsibilityDept()%>"><a href="#"  onClick="SelectDeptName(); "  class="linka">[…]</a></td>
<%}else{%>
           	<td width="13%" align="right">区县：</td>
            <td width="49%" colspan="2"><select name="countyCodeMis" style="width:88%"><%=countyOption%></select ></td>
<%}
%>
            
        </tr>
        <tr>
            <td width="13%" align="right">责任人：</td>
            <td width="25%">
				<input type="text" name="assignedToName" value="<%=dto.getAssignedToName()%>" style="width:80%" class="blueBorder"><a href="#" onClick="do_SelectUser();" title="点击选择责任人" class="linka">[…]</a>
            </td>
            <td width="13%" align="right">地点：</td>
            <td width="49%" colspan="2">
				<input type="text" name="assetsLocation" value="<%=dto.getAssetsLocation()%>" style="width:83%" class="blueBorder"><a href="#" onClick="do_selectAssetsLocation();" title="点击选择地点"  class="linka">[…]</a>
            </td>
            
        </tr>
         <tr>
           	<td width="13%" align="right">开始时间： </td>
        	<td width="25%">
            	<input  name="fromDate" type="text" style="width:80%" readonly="true" value="<%=StrUtil.nullToString(dto.getFromDate())%>">
            	<img src="/images/calendar.gif" width="16" height="15" alt="选择时间" id="calendar1"  onClick="getDateTime( 'mainForm.fromDate');">
        	</td>
            <td width="7%" align="right">到：</td>
            <td width="22%">
            	<input name="toDate" type="text" style="width:80%" readonly="true" value="<%=StrUtil.nullToString(dto.getToDate())%>">
            	<img src="/images/calendar.gif" width="16" height="15" alt="选择时间" id="calendar2"  onClick="getDateTime( 'mainForm.toDate');">
        	</td>
        	
        	
        	<td width="33%" align="right" >
                <img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()">
                <img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel">
            </td>
        	
        	
    </tr>
    </table>
    <div  id="headDiv" style="overflow:hidden;position:absolute;top:90px;left:0px;width:487px">
        <table width="330%" border="1" class="headerTable" >
            <tr height="22">
                <td align="center" width="2%"></td>
                <td align="center" width="8%">标签号</td>
                <td align="center" width="10%">资产名称</td>
                <td align="center" width="13%">资产型号</td>
                <td align="center" width="10%">地点编号</td>
                <td align="center" width="14%">地点</td>

                <td align="center" width="4%">责任人</td>
                <td align="center" width="6%">启用日期</td>
                <td align="center" width="3%">数量</td>
                <td align="center" width="13%">项目名称</td>
                <td align="center" width="6%">创建日期</td>
				<td align="center" width="6%">资产原值</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;width:504px;position:absolute;top:113px;left:0px;height:72%" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;" >
        <table width="330%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
	if (rows != null && rows.getSize() > 0) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr onclick="executeClick(this);" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td height="22" width="2%" align="center"><input type="radio" name="assetId" value="<%=row.getValue("ASSET_ID")%>;<%=row.getValue("NO_MATCH_UNITS")%>"></td>
                <td height="22" width="8%"><input readonly class="finput2" value="<%=row.getValue("TAG_NUMBER")%>"></td>
                <td height="22" width="10%"><input readonly class="finput" value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
                <td height="22" width="13%"><input readonly class="finput" value="<%=row.getValue("MODEL_NUMBER")%>"></td>
                <td height="22" width="10%"><input readonly class="finput2" value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
                <td height="22" width="14%"><input readonly class="finput" value="<%=row.getValue("ASSETS_LOCATION")%>"></td>

                <td height="22" width="4%"><input readonly class="finput" value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
                <td height="22" width="6%"><input readonly class="finput2" value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
                <td height="22" width="3%"><input readonly class="finput3" value="<%=row.getValue("NO_MATCH_UNITS")%>"></td>
                <td height="22" width="13%"><input readonly class="finput" value="<%=row.getValue("PROJECT_NAME")%>"></td>
                <td height="22" width="6%"><input readonly class="finput2" value="<%=row.getValue("ASSETS_CREATE_DATE")%>"></td>
                <td height="22" width="6%"><input readonly class="finput3" value="<%=row.getValue("COST")%>"></td>
            </tr>

<%
		}
	}
%>
        </table>
    </div>
</form>
<div style="position:absolute;bottom:1px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
	window.attachEvent("onresize",do_SetPageWidth)
    function init() {
        if (document.getElementById("dataTable")) {
            document.getElementById("scrollTb").height = document.getElementById("dataTable").offsetHeight;
        }
    }
    function do_query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.forms[0].act.value = "<%=WebActionConstant.QUERY_ACTION%>"
        document.forms[0].submit();
    }
    
    function do_export() {
//        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.forms[0].act.value = "<%=WebActionConstant.EXPORT_ACTION%>"
        document.forms[0].submit();
    }

    function do_selectAssetsLocation() {
        document.mainForm.assetsLocation.value = "";
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_TD_ASSETS_LOCATION%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            document.mainForm.assetsLocation.value = projects[0].assetsLocation;
        }
    }
    function do_SelectUser() {
        var projects = getLookUpValues("LOOK_UP_USER", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            //            dto2Frm(projects[0], "form1");
            document.mainForm.assignedToName.value = projects[0].executeUserName;
            //        document.mainForm.workorderObjectNo.value = projects[0].workorderObjectNo;
        } else {
            document.mainForm.assignedToName.value = "";
        }
    }
    window["onscroll"] = function() {
        if (document.getElementById('scrollDiv')) {
            //    if(/safari/i.test(navigator.userAgent)){
            document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
            //    }else{
            //        document.getElementById('scrollDiv').style.left=document.documentElement.scrollLeft+document.getElementById("scrollDiv").offsetHeight/3+"px";
            //    }
        }
    }
    window["onresize"] = function() {
        if (document.getElementById('scrollDiv')) {
            document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
        }
    }

    
function SelectDeptName(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
       document.forms[0].responsibilityDept.value = vendorNames[0].deptName;
    }
}
</script>

</html>
