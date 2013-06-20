<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: sunny song
  Date: 2008-3-14
  Time: 7:55:54
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>TD资产变动直接同步</title>
    <link href="/WebLibary/css/css.css" type="text/css">
</head>
<%
	ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(application);
	String provinceCode = servletConfig.getProvinceCode();

	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);

    EamSyschronizeDTO dto = (EamSyschronizeDTO) request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);

	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;

%>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.td.synAssets.servlet.TdAssetsChangeSynServlet">
<jsp:include page="/message/MessageProcess"/>
    <script type="text/javascript">
        printTitleBar("TD资产变动直接同步")
    </script>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
        <tr>
            <td align="right" width="8%">公司名称：</td>
            <td colspan="3" width="40%"><select size="1" name="organizationId" style="width:100%"><%=dto.getOrganizationOpt()%></select></td>
            <td align="right" width="8%">EAM标签：</td>
            <td align="left" width="12%"><input type="text" name="newBarcode" value="<%=dto.getNewBarcode()%>" style="width:100%"></td>
            <td align="right" width="10%">EAM资产名称：</td>
            <td align="left" width="12%"><input type="text" name="nameTo" value="<%=dto.getNameTo()%>" style="width:100%"></td>
            <td colspan="2" width="10%">
                <img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出">
            </td>
        </tr>
        <tr>
            <td align="right" width="8%">EAM地点：</td>
            <td co width="20%"><input style="width:80%" type="text" name="newAssetsLocation" value="<%=dto.getNewAssetsLocation()%>"><a href="#" onclick="chooseSit()" title="点击选择地点" class="linka">[…]</a></td>
            <td align="right" width="8%">资产类别：</td>
            <td align="left" width="12%">
                <select size="1" name="netManger" style="width:100%">
                    <option>请选择
                    <option value="NET" <%=dto.getNetManger().equals("NET") ? "selected" : ""%> >网络类资产</option>
                    <option value="MANG" <%=dto.getNetManger().equals("MANG") ? "selected" : ""%>>管理类资产</option>
                </select>
			</td>
            <td align="right" width="8%">匹配人</td>
            <td align="left" width="12%"><input class="readonlyInput" type="text" name="matchUserName" value="<%=dto.getMatchUserName()%>" style="width:100%" onclick="do_selectUser();" title="点击选择用户" readonly></td>
            <td align="right" width="10%">项目名称：</td>
            <td align="left" width="12%"><input type="text" name="projectName" value="<%=dto.getProjectName()%>" style="width:100%" size="20"></td>
            <td width="5%" align="left" valign="bottom"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td width="5%" align="left" valign="bottom"><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="同步"></td>
        </tr>
    </table>
    <div id="headDiv" style="position:absolute;width:840px;overflow:hidden;top:70px;padding:0px; margin:0px;">
        <table width="270%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
            <tr height="22" onClick="executeClick(this)" style="cursor:hand">
                <td width="2%" align="center"><input type="checkbox" name="titleCheck" onPropertyChange="checkAll('titleCheck','subCheck')"></td>

				<td width="3%" align="center">资产编号</td>
                <td width="4%" align="center">MIS条码</td>
                <td width="4%" align="center">EAM条码</td>
				<td width="5%" align="center">公司名称</td>

                <td width="7%" align="center">MIS名称</td>
                <td width="7%" align="center">EAM名称</td>
                <td width="7%" align="center">MIS型号</td>
                <td width="7%" align="center">EAM型号</td>

                <td width="11%" align="center">MIS地点</td>
                <td width="11%" align="center">EAM地点</td>
                <td width="3%" align="center">MIS责任人</td>
                <td width="3%" align="center">EAM责任人</td>
                <td width="8%" align="center">MIS厂商</td>
                <td width="8%" align="center">EAM厂商</td>

                <td width="3%" align="center">启用日期</td>
                <td width="3%" align="center">资产原值</td>
                <td width="3%" align="center">匹配人</td>
            </tr>
        </table>
    </div>

    <div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:93px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="270%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed&#59;word-break:break-all">
<%
	if (rows != null && rows.getSize() > 0) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="2%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>

                <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSET_ID")%>"></td>
                <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
                <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
				<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY_NAME")%>"></td>

				<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
				<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
                <td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
                <td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>

				<td width="11%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
				<td width="11%"><input type="text" class="finput" readonly value="<%=row.getValue("LOCATION_CODE")%>"></td>
                <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("OLD_ASSIGNED_TO_NAME")%>"></td>
                <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("NEW_USER_NAME")%>"></td>
                <td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_MANUFACTURER")%>"></td>
                <td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("EAM_MANUFACTURER")%>"></td>

				<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
                <td width="3%"><input type="text"  class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
                <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("MATCH_USER_NAME")%>"></td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>

	<input type="hidden" name="matchUserId" value="<%=dto.getMatchUserId()%>">
	<input type="hidden" name="act">
	<input type="hidden" name="flag" value="0">

</form>
<div style="position:absolute;top:91%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
</html>

<script type="text/javascript">

function do_search() {
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_Export() {
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

/**
* 功能：同步数据
*/
function do_syschronize() {
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询再进行同步");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请先选择数据再进行同步");
		return;
	}
	mainFrm.flag.value="1";
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
	mainFrm.submit();
}

function chooseSit() {
	var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>";
	var dialogWidth = 47.5;
	var dialogHeight = 30;
	var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (projects) {
		mainFrm.newAssetsLocation.value = projects[0].workorderObjectLocation;
	}
}

function do_selectUser() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
	var obj = objs[0];
		mainFrm.matchUserId.value = obj["userId"];
		mainFrm.matchUserName.value = obj["userName"];
	} else {
		mainFrm.matchUserId.value = "";
		mainFrm.matchUserName.value = "";
	}
}

</script>