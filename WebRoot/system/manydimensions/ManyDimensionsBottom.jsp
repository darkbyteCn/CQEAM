<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<title>多维度信息维护--新数据页</title></head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(request);
	String provinceCode = servletConfig.getProvinceCode();
%>
<form name="mainFrm">
    <div id="bb" style="overflow:hidden;position:absolute;top:0px;left:0px;width:100%">
        <table border=1 width="100%" class="headerTable">
            <tr height="22px">
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">逻辑网络元素</td>
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">投资分类</td>
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">业务平台</td>
                <td width="6%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">网络层次</td>
            </tr>
        </table>
    </div>
    <div style="overflow:hidden;width:100%;position:absolute;top:22px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <tr>
                <td width="6%"><input class="input_style2" type="text" name="logNetEle" style="width:100%" style="width:100%;cursor:hand" readonly title="点击选择逻辑网络元素" onClick="do_SelectLne();"></td>
                <td width="6%"><input class="input_style2" type="text" name="investCatName" style="width:100%" style="width:100%;cursor:hand" readonly title="点击选择投资分类" onClick="do_SelectCex();"></td>
                <td width="6%"><input class="input_style2" type="text" name="opeName" style="width:100%" style="width:100%;cursor:hand" readonly title="点击选择业务平台" onClick="do_SelectOpe();"></td>
                <td width="6%"><input class="input_style2" type="text" name="lneName" style="width:100%" style="width:100%;cursor:hand" readonly title="点击选择网络层次" onClick="do_SelectNle();"></td>

			</tr>
        </table>
    </div>
    <%--<div style="overflow:hidden;position:absolute;top:50px;left:1px;width:98.5%">--%>
        <%--<table border="0" width="100%">--%>
            <%--<tr>--%>
                <%--<td width="3%" align="center">选<br>中<br>的<br>条<br>码</td>--%>
                <%--<td width="97%" height="115">--%>
                <%--<textarea name="checkedData" id="checkedData" style="border:1px solid #3366EE; width:100%; height:100%" readonly></textarea></td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</div>--%>
    <input type="hidden" name="addressId" value="">
	<input type="hidden" name="responsibilityUser" value="">
	<input type="hidden" name="employeeNumber" value="">
	<input type="hidden" name="itemCode" value="">
	<input type="hidden" name="maintainDept" value="">
	<input type="hidden" name="responsibilityDeptName" value="">
	<input type="hidden" name="financePropName" value="">
	<input type="hidden" name="ROLL_BACK_DEPT" value="">
	<input type="hidden" name="workorderObjectCode" value="">
    <input type="hidden" name="manufacturerId" value="">
    <input type="hidden" name="contentCode" value="">
    <input type="text" name="lneId">
	<input type="text" name="cexId">
	<input type="text" name="opeId">
	<input type="text" name="nleId">
</form>
</body>
</html>

<script>

function do_SelectLne(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_LNE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.lneId.value = obj["amsLneId"];
	} else {
        mainFrm.logNetEle.value = "";
        mainFrm.lneId.value = "";
    }
}

function do_SelectCex(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_CEX%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.cexId.value = obj["amsCexId"];
	} else {
        mainFrm.investCatName.value = "";
        mainFrm.amsCexId.value = "";
    }
}

function do_SelectOpe(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_OPE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.opeId.value = obj["amsOpeId"];
	} else {
        mainFrm.opeName.value = "";
        mainFrm.amsOpeId.value = "";
    }
}

function do_SelectNle(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_NLE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.nleId.value = obj["amsLneId"];
	} else {
        mainFrm.lneName.value = "";
        mainFrm.amsLneId.value = "";
    }
}

function do_SelectSn(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_SN%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.snId.value = obj["snId"];
	} else {
        mainFrm.snName.value = "";
        mainFrm.snId.value = "";
    }
}
</script>