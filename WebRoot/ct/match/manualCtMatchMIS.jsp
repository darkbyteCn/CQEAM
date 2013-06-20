<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.ct.dto.EtsFaAssetsDTO"%>
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
<%@ page import="com.sino.ams.ct.bean.LookUpCtConstant" %>
<%--
  Created by Yu.
  Date: 2008-12-08
  Time: 16:59:41
--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
<head>
	<title>村通资产匹配-MIS资产信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>

</head>
<%
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute("MIS_HEADER");
    //String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
    ServletConfigDTO configDto = SessionUtil.getServletConfigDTO(request);
    
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.ams.match.servlet.ManualCtMatchMIS" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("村通资产匹配-MIS资产信息")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act" value="<%=act%>">
    <table width="100%" class="queryHeadBg" id="tb" border = "0">
        <tr>
            <td width="15%" align="right">关键字：</td>
            <td width="30%"><input type="text" name="key" value="<%=dto.getKey()%>" style="width:95%"  class="blueBorder"></td>
            <td width="15%" align="right">地点：</td>
            <td width="30%">
				<input type="text" name="assetsLocation" value="<%=dto.getAssetsLocation()%>" style="width:80%" class="blueBorder"><a href="#" onClick="do_selectAssetsLocation();" title="点击选择地点"  class="linka">[…]</a>
            </td>
                
            <td width = "10%"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel"></td>
        </tr>
        <tr>
            <td width="15%" align="right">名称：</td>
            <td width="30%">
				<input type="text" name="assetsDescription" value="<%=dto.getAssetsDescription()%>" style="width:80%" class="blueBorder"><a href="#" onClick="do_selectAssetsDescription();" title="点击选择名称"  class="linka">[…]</a>
            </td>
            <td width="15%" align="right">型号：</td>
            <td width="30%">
				<input type="text" name="modelNumber" value="<%=dto.getModelNumber()%>" style="width:80%" class="blueBorder"><a href="#" onClick="do_selectModelNumber();" title="点击选择型号"  class="linka">[…]</a>
            </td>
            <td width="10%" align = "right"><img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()"></td>
        </tr>
    </table>
    <div  id="headDiv" style="overflow:hidden;position:absolute;top:66px;left:0px;width:487px">
        <table width="100%" border="1" class="headerTable"  title="点击自适应窗口宽度" onClick="do_SetPageWidth()">
            <tr height="22">
                <td align="center" width="2%"></td>
                <td align="center" width="8%">资产条码</td>
                <td align="center" width="10%">资产名称</td>
                <td align="center" width="8%">规格型号</td>
                <td align="center" width="6%">部门</td>
                <td align="center" width="6%">责任人</td>
                <td align="center" width="10%">地点</td>

                
                <td align="center" width="6%">启用日期</td>
                
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:75%;width:504px;position:absolute;top:89px;left:0px;height:490px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;" >
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
	if (rows != null && rows.getSize() > 0) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i); 
%>
            <tr onclick="executeClick(this);" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            	<!--  
                <td height="22" width="2%" align="center"><input type="radio" name="assetId" value="<%--=row.getValue("ASSET_ID")--%>;<%--=row.getValue("NO_MATCH_UNITS")--%>"></td>
                -->
                <td height="22" width="2%" align="center"><input type="radio" name="assetId" value="<%=row.getValue("ASSET_ID")%>"></td>
                <td height="22" width="8%"><input readonly class="finput2" value="<%=row.getValue("TAG_NUMBER")%>"></td>
                <td height="22" width="10%"><input readonly class="finput" value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
                <td height="22" width="8%"><input readonly class="finput" value="<%=row.getValue("MODEL_NUMBER")%>"></td>
                <td height="22" width="6%"><input readonly class="finput" value="<%=row.getValue("DEPT_NAME")%>"></td>
                <td height="22" width="6%"><input readonly class="finput" value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
                <td height="22" width="10%"><input readonly class="finput" value="<%=row.getValue("ASSETS_LOCATION")%>"></td>

                
                <td height="22" width="6%"><input readonly class="finput2" value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
                <!--  
                <td height="22" width="3%"><input readonly class="finput3" value="<%--=row.getValue("NO_MATCH_UNITS")--%>"></td>
                -->
            </tr>

<%
		}
	}
%>
        </table>
    </div>
</form>
<div style="position:absolute;top:600px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
<jsp:include page="/message/MessageProcess"/>
</body>
<script type="text/javascript">
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
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_LOCATION%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            document.mainForm.assetsLocation.value = projects[0].assetsLocation;
        }
    }
    function do_selectAssetsDescription(){
    	var url="/servlet/com.sino.ams.ct.bean.AMSCtLookUpServlet?lookUpName=<%=LookUpCtConstant.LOOK_UP_ETS_FA_CT_ASSETS_DESCRIPTION%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
       		document.forms[0].assetsDescription.value = vendorNames[0].assetsDescription;
    	}
	}
    function do_selectModelNumber() {
        var url = "/servlet/com.sino.ams.ct.bean.AMSCtLookUpServlet?lookUpName=<%=LookUpCtConstant.LOOK_UP_ETS_FA_CT_ASSETS_MODEL_NUMBER%>";
        var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
       		document.forms[0].modelNumber.value = vendorNames[0].modelNumber;
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
</script>
</html>