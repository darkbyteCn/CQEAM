<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    Map map = (Map)request.getAttribute(AssetsWebAttributes.FINCE_PROP_MAPS);
%>
<title>实物台账查询</title>
</head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.newasset.servlet.ItemMaintainServlet2" method="post" name="mainFrm">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="flag" value="0">
<table bgcolor="#E9EAE9" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
	<tr height="25px">
		<td width="7%" align="right">设备专业：</td>
		<td width="25%"><select name="itemCategory" style="width:100%"><%=dto.getItemCategoryOpt()%></select></td>
		<td width="7%" align="right">设备名称：</td>
		<td width="16%"><input type="text" name="itemName" style="width:85%" value="<%=dto.getItemName()%>"><a href=""  title="点击选择设备名称" onclick="do_SelectItemName(); return false;">[…]</a></td>
		<td width="7%" align="right">标签号：</td>
		<td width="14%"><input type="text" name="fromBarcode" style="width:100%" value="<%=dto.getFromBarcode()%>"></td>
        <td width="10%" align="center">到</td>
		<td width="14%"><input type="text" name="toBarcode" style="width:100%" value="<%=dto.getToBarcode()%>"></td>
    </tr>
	<tr height="25px">
		<td width="7%" align="right">责任部门：</td>
		<td width="25%"><select name="responsibilityDept" style="width:100%" onchange="do_PassDeptName(this);"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></td>
        <td width="7%" align="right">责任人：</td>
        <td width="16%"><input type="text" name="responsibilityUserName" value="<%=dto.getResponsibilityUserName()%>" style="width:85%" readonly><a href="" title="点击选择责任人" onclick="do_SelectPerson(); return false;">[…]</a></td>
        <td width="7%" align="right">地点：</td>
		<td width="14%"><input type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" style="width:100%"></td>
        <td width="10%" ><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a>资产类型：</td>
		<td width="14%"><select name="financeProp" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.FINANCE_PROP_OPTION)%></select></td>
	</tr>
    <tr height="25px">
        <td width="7%" align="right">共享共建状态：</td>
		<td width="25%">
             <select name="shareStaus" style="width:100%"><%=dto.getShareOption()%></select>
        </td>
        <td width="7%" align="right">厂商：</td>
        <td width="16%">
            <input type="text" name="manufacturerName" value="<%=dto.getManufacturerName()%>" style="width:85%" readonly><a style="cursor:'hand'" onclick="do_selectNameManufacturer();">[…]</a>
            <input type="hidden" name="manufacturerId" value="">
        </td>
        <td width="7%" align="right">资产目录：</td>
		<td width="14%">
            <input type="text" name="contentName" value="<%=dto.getContentName()%>" style="width:100%">
            <input type= "hidden" name = "contentCode" value = "">
        </td>
        <td width="10%" align="left"><a href=""  title="点击选择目录" onclick="do_SelectContent(); return false;">[…]</a>设备状态：</td>
		<td width="14%"><select name="itemStatus" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS)%></select></td>
    </tr>
    <tr height="25px">
		<td width="7%" align="right">代维公司：</td>
		<td width="25%"><select name="maintainCompany" style="width:100%"><%=dto.getMaintainCompanyOpt()%></select></td>
		<td width="7%" align="right">项目名称：</td>
		<td width="16%">
            <input type="text" name="projectName" value="<%=dto.getProjectName()%>" style="width:85%" readonly><a href="" title="点击选择项目" onclick="do_SelectProject(); return false;">[…]</a>
            <input type= "hidden" name = "projectNumber" value = "">
        </td>
        <td width="7%" align="right">额定功率：</td>
		<td width="14%" colspan="2">
            <input type="text" name="power" style="width:25%" value="<%=dto.getPower()%>">&nbsp;
            是否正在盘点：<select name="isCheck" style="width:25%">
                            <option value="">--请选择--</option>
                            <option value="N" <%=dto.getCheck().equals("N")?"selected":""%>>未盘点</option>
                            <option value="Y" <%=dto.getCheck().equals("Y")?"selected":""%>>正在盘点</option>
                        </select>
		</td>
        <td width="24%" align="right" >
            <img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();">
            <img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();">
		</td>
	</tr>
</table>
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:100px;left:0px;width:828px">
	<table border=1 width="400%" class="headerTable">
		<tr class=headerTable height="20px">
			<td align=center width=3%>标签号</td>
			<td align=center width=2%>设备分类</td>
			<td align=center width=4%>设备名称</td>
			<td align=center width=4%>设备型号</td>
			<td align=center width=2%>计量单位</td>
			<td align=center width=2%>使用年限</td>
			<td align=center width=3%>启用日期</td>
            <td align=center width=2%>设备状态</td>
            <td align=center width=2%>资产类型</td>
			<td align=center width=4%>地点代码</td>
			<td align=center width=7%>地点简称</td>
			<td align=center width=3%>地点位置</td>
			<td align=center width=4%>所在区县</td>
			<td align=center width=2%>责任人</td>
			<td align=center width=2%>员工编号</td>
			<td align=center width=7%>责任部门</td>
			<td align=center width=2%>使用人</td>
			<td align=center width=5%>使用部门</td>
			<td align=center width=4%>项目名称</td>
			<td align=center width=3%>项目编号</td>
			<td align=center width=5%>厂商名称</td>
			<td align=center width=3%>厂商代码</td>
            <td align=center width=3%>资产目录代码</td>
			<td align=center width=3%>资产目录描述</td>
			<td align=center width=2%>共享共建状态</td>
            <td align=center width=2%>额定功率</td>
			<td align=center width=3%>逻辑网络元素</td>
			<td align=center width=3%>投资分类</td>
			<td align=center width=3%>业务平台</td>
            <td align=center width=2%>网络层次</td>
     </tr>
</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:460px;width:860px;position:absolute;top:120px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="400%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		Row row = null;
        String barcode = "";
        for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
            barcode = row.getStrValue("BARCODE");
%>
        <tr class="dataTR" ondblclick="do_ShowLog('<%=row.getValue("BARCODE")%>')" style="cursor:hand" title="双击查看该设备修改历史">
		  <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getStrValue("BARCODE")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_CATEGORY_NAME")%>"></td>
          <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
		  <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
	      <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("START_DATE")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_STATUS_NAME")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("FINANCE_PROP_VALUE")%>"></td>
          <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
          <td width=7%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
		  <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("COUNTY_NAME")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
          <td width=7%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("RESPONSIBILITY_DEPT_NAME")%>"></td>
		  <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MAINTAIN_USER")%>"></td>
          <td width=5%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MAINTAIN_DEPT_NAME")%>"></td>
          <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>
		  <td width=5%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MANUFACTURER_NAME")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MANUFACTURER_CODE")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("CONTENT_CODE")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("CONTENT_NAME")%>"></td>
		  <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("SHARE_STATUS_NAME")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("POWER")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LOG_NET_ELE")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("INVEST_CAT_NAME")%>"></td>
          <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("OPE_NAME")%>"></td>
          <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LNE_NAME")%>"></td>

		</tr>
<%
		}
	}
%>
    </table>
</div>

<div id="headDiv" style="overflow:hidden;position:absolute;top:625px;left:0px;width:100%">
        <table border=1 width="100%" class="headerTable">
            <tr height="22px">
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">资产</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">TD</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">在建工程</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">租赁</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">重要低值易耗</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">备件</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">村通、代管资产</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">屏蔽</td>
                <td width="10%" align="center" style="color: #FFFFFF" background="/images/bg_01.gif">未知</td>
            </tr>
        </table>
</div>
<div id="dataDiv" style="overflow:hidden;height:170px;width:100%;position:absolute;top:648px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <tr>
                <td width="10%"><input type="text" name="assets" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("ASSETS")).equals("")?"0":map.get("ASSETS")%>"></td>
                <td width="10%"><input type="text" name="tdAssets" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("TD_ASSETS")).equals("")?"0":map.get("TD_ASSETS")%>"></td>
                <td width="10%"><input type="text" name="prjMtl" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("PRJ_MTL")).equals("")?"0":map.get("PRJ_MTL")%>"></td>
                <td width="10%"><input type="text" name="rentAssets" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("RENT_ASSETS")).equals("")?"0":map.get("RENT_ASSETS")%>"></td>
                <td width="10%"><input type="text" name="dhAssets" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("DH_ASSETS")).equals("")?"0":map.get("DH_ASSETS")%>"></td>
                <td width="10%"><input type="text" name="apare" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("SPARE")).equals("")?"0":map.get("SPARE")%>"></td>
                <td width="10%"><input type="text" name="dgAssets" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("DG_ASSETS")).equals("")?"0":map.get("DG_ASSETS")%>"></td>
                <td width="10%"><input type="text" name="others" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("OTHERS")).equals("")?"0":map.get("OTHERS")%>"></td>
                <td width="10%"><input type="text" name="unknow" style="width:100%" readonly class="readonlyInput" value="<%=(map==null)?"":(map.get("UNKNOW")).equals("")?"0":map.get("UNKNOW")%>"></td>
            </tr>
        </table>
    </div>
<%
    if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:585px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
</html>
<script type="text/javascript">
function initPage(){
    do_SetPageWidth();
}

function validateBarcode(){
    var fromBarcode = mainFrm.fromBarcode.value;
    if(fromBarcode == null || fromBarcode == ""){
        return true;
    }
    var Expression = /!|@|#|&|\$/;
//    /^[A-Za-z]+$/;   /^[0-9]{4}-[0-9]{8}$/
    var objExp=new RegExp(Expression);
    if(objExp.test(fromBarcode) || !isInt(fromBarcode)){
        alert("“标签号”不合法，请输入正确格式！");
        mainFrm.fromBarcode.focus();
        return false;
    }
    return true;
}

function do_Search() {
//    if(validateBarcode()){
        mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
//    }
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

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

function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (objs) {
        var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.workorderObjectName.value = obj["workorderObjectLocation"];
	} else {
        mainFrm.workorderObjectName.value = "";
    }
}

function do_SelectProject(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    } else {
        mainFrm.projectName.value = "";
        mainFrm.projectNumber.value = "";
    }
}

function do_selectNameManufacturer() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_MANUFACTURER%>";
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
            mainFrm.manufacturerId.value = "";
            mainFrm.manufacturerName.value = "";
        }
}

function do_SelectPerson(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
	var dialogWidth = 47;
	var dialogHeight = 30;
	var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(users){
		var user = users[0];
		mainFrm.responsibilityUserName.value = user["userName"];
    } else {
        mainFrm.responsibilityUserName.value = "";
    }
}

function do_SelectItemName(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ITEMNAME%>";
	var dialogWidth = 35;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    } else {
        mainFrm.itemName.value = "";
    }
}

function do_ShowLog(barcode){
	var url = "<%=AssetsURLList.ITEM_MAINTAIN_SERVLET%>";
	url += "?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&barcode=" + barcode;
	var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
	window.open(url, 'applyInFlowWin', style);
}

function do_ShowDetail(barcode) {
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
}
</script>
