<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserRightDTO"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
    String isThred = headerDTO.getThred();
    String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String orgId = userAccount.getOrganizationId()+"";
	String userId = userAccount.getUserId()+"";
	String provinceCode = headerDTO.getServletConfig().getProvinceCode();
	String provOrgId = headerDTO.getServletConfig().getProvinceOrgId()+"";
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
	String producedNewBarcode = headerDTO.getProducedNewBarcode();
	String attribute4 = headerDTO.getAttribute4();
    	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	AmsAssetsTransLineDTO lineDTO = null;
	String barcode = "";

String isTdProvinceUser=request.getAttribute("isTdProvinceUser").toString();//Td省公司用户
    String toOrgId = headerDTO.getToOrganizationId()+"";
    String currRoleName = headerDTO.getCurrRoleName();
    ServletConfigDTO servletConfig = headerDTO.getServletConfig();
    DTOSet userDTO = userAccount.getUserRight();
    String roleName = "";
    Map  userRightMap = new HashMap();
    for (int i = 0;i<userDTO.getSize();i++) {
        SfUserRightDTO userRight = (SfUserRightDTO)userDTO.getDTO(i);
        roleName = userRight.getRoleName();
        userRightMap.put(roleName,roleName);
    }
    boolean isDptMgr = userRightMap.containsValue("部门经理");
    String isGroupPID = request.getAttribute(AssetsWebAttributes.IS_GROUP_PID).toString();//是否市公司综合部流程组别
    String isFinanceGroup = request.getAttribute(AssetsWebAttributes.IS_FINANCE_GROUP).toString();//财务部
    String isSpecialGroup=request.getAttribute(AssetsWebAttributes.IS_SPECIAL_GROUP).toString();//实物部门/
%>
<head>
	<%
    	String titleText="";
    	String titleBar="";
    	SfUserDTO userApp = (SfUserDTO) SessionUtil.getUserAccount(request);
    	if ("Y".equalsIgnoreCase(userApp.getIsTd())) {
    		titleText=headerDTO.getTransTypeValue()+"(TD)";
    		titleBar=headerDTO.getTransTypeValue()+"(TD)";
    	} else {
    		titleText=headerDTO.getTransTypeValue();
    		titleBar=headerDTO.getTransTypeValue();
    	}
       
    %>
	<title><%=titleText%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<%@ include file="/flow/flowNoButton.jsp" %>
<form action="/servlet/com.sino.ams.newasset.servlet.DisOrderApproveServlet" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
     <%@ include file="/flow/flowPara.jsp"%>
<div id="searchDiv" style="position:absolute;top:30px;left:1px;width:100%">
 <table border="0" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input type="text" name="transNo" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input type="text" name="transTypeValue" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getFromGroupName()%>"></td>
			        <td align=right width=8%>报废部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getFromDeptName()%>">
			        </td>
			    </tr>
			    <tr>
			        <td align=right width=8%>申请人：</td>
			        <td width=17%>
						<input type="text" name="created1" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getCreated()%>" >
					</td>
			        <td align=right width=8%>申请日期：</td>
			        <td width=17%>
						<input name="creationDate" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right width=8%>公司名称：</td>
			        <td width=17%>
						<input name="userCompanyName" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>紧急程度：</td>
			        <td width=17%><input name="emergentLevel" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getEmergentLevel()%>"></td>
			    </tr>
			    <tr>
                    <td width=8% height="60px" align=right >附件张数：</td>
			        <td width=17% height="60px" ><input name="accessSheet" readonly style="width:100%;" class="input_style2" value="<%=headerDTO.getAccessSheet()%>"></td>
                    <td width="8%" height="60px" align="right">报废说明：</td>
			    	<td width="67%" height="60px" colspan="5"><textarea name="createdReason" readonly style="width:100%;height:100%" class="input_style2"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
</div>
<input type="hidden" name="faContentCode" value="<%=headerDTO.getFaContentCode()%>">
<input type="hidden" name="faContentName" value="<%=headerDTO.getFaContentName()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="isFinanceGroup" id=isFinanceGroup value="<%=isFinanceGroup%>">
<input type="hidden" name="isSpecialGroup" id=isSpecialGroup value="<%=isSpecialGroup%>">
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="isTd" value="<%=userApp.getIsTd()%>">
<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureNameIncludeTd(userApp.getIsTd())%>">
<input type="hidden" name="isTdProvinceUser" value="<%=isTdProvinceUser%>">

<input type="hidden" name="toGroup" id="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="groupPid" id="groupPid" value="<%=headerDTO.getGroupPid()%>">
<input type="hidden" name="groupProp" id="groupProp" value="<%=headerDTO.getGroupProp()%>">
<input type="hidden" name="provinceCode" value="<%=provinceCode%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="isProvinceUser" id="isProvinceUser" value="<%=userApp.isProvinceUser()%>">
<input type="hidden" name="isMtlAssetsManager" id="isMtlAssetsManager" value="<%=userApp.isMtlAssetsManager()%>">
<input type="hidden" name="oneGroup" id="oneGroup" value="省公司.系统管理组;省公司.综合部">
<input type="hidden" name="twoGroup" id="twoGroup" value="省公司.系统管理组;省公司.综合部">
<input type="hidden" name="flowCode" value="">
     <%--<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">--%>
        <%--<table class="headerTable" border="1" width="140%">--%>
            <%--<tr height="23px" onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">--%>
                <%--<td align=center width="8%">资产标签</td>--%>
                <%--<td align=center width="8%">资产编号</td>--%>
                <%--<td align=center width="8%">报废类型</td>--%>
                <%--<td align=center width="8%">资产名称</td>--%>
                <%--<td align=center width="8%">资产型号</td>--%>
                <%--<td align=center width="7%">资产原值</td>--%>
                <%--<td align=center width="7%">净值</td>--%>
                <%--<td align=center width="7%">报废成本</td>--%>
                <%--<td align=center width="9%">启用日期</td>--%>
                <%--<td align=center width="10%">所在地点</td>--%>
                <%--<td align=center width="5%">责任人</td>--%>
                <%--<td align=center width="10%">责任部门</td>--%>
                <%--<td align=center width="9%">网元编号</td>--%>
            <%--</tr>--%>
		<%--</table>--%>
	<%--</div>--%>
	<%--<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">--%>
        <%--<table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">--%>
<%--<%--%>

		<%--if (lineSet != null && !lineSet.isEmpty()) {--%>
			<%--for (int i = 0; i < lineSet.getSize(); i++) {--%>
				<%--lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);--%>
				<%--barcode = lineDTO.getBarcode();--%>
                <%--if ((currRoleName.equals(servletConfig.getMtlAssetsMgr()) && (userAccount.getOrganizationId()+"").equals("82")) || (currRoleName.equals("资产会计") && (userAccount.getOrganizationId()+"").equals("82")) || attribute4.equals("FIND_ASSET")) {--%>
<%--%>--%>
            <%--<tr class="dataTR">--%>
<%--<%if (!attribute4.equals("FIND_ASSET")) {%>--%>
                <%--<td width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=barcode%>" <%=lineDTO.getRemark().equals("")?"checked":""%>></td>--%>
<%--<%}%>--%>
                <%--<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="rejectType" id="rejectType<%=i%>" class="finput" readonly value="<%=lineDTO.getRejectTypeName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="7%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="7%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
                <%--<td width="7%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getRetirementCost()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
                <%--<td width="9%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="10%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="5%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
				<%--<td width="10%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
                <%--<td width="9%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
            <%--</tr>--%>

<%--<%--%>
               <%--} else {--%>
<%--%>--%>
            <%--<tr class="dataTR" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">--%>
				<%--<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="rejectType" id="rejectType<%=i%>" class="finput" readonly value="<%=lineDTO.getRejectTypeName()%>" ></td>--%>
                <%--<td width="8%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>--%>
				<%--<td width="8%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>--%>
				<%--<td width="7%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>--%>
				<%--<td width="7%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>--%>
                <%--<td width="7%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getRetirementCost()%>"></td>--%>
                <%--<td width="9%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>--%>
				<%--<td width="10%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>"></td>--%>
				<%--<td width="5%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>--%>
				<%--<td width="10%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>--%>
                <%--<td width="9%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>--%>
            <%--</tr>--%>
<%--<%--%>
               <%--}--%>
			<%--}--%>
		<%--}%>--%>
        <%--</table>--%>
        <%--<table width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">--%>
            <%--<tr height=20px>--%>
                <%--<td width="40%" rowspan="2" colspan="5" align="right">合计</td>--%>
                <%--<td width="7%" align="center">资产原值</td>--%>
                <%--<td width="7%" align="center">净值</td>--%>
                <%--<td width="7%" align="center">报废成本</td>--%>
                <%--<td width="9%"></td>--%>
                <%--<td width="10%"></td>--%>
                <%--<td width="5%"></td>--%>
                <%--<td width="10%"></td>--%>
                <%--<td width="9%"></td>--%>
            <%--</tr>--%>
            <%--<tr height=20px>--%>
                <%--<td width="7%"><input readonly="true" class="finput2" id="yuanzhiValue"></td>--%>
                <%--<td width="7%"><input readonly="true" class="finput2" id="jingzhiValue"></td>--%>
                <%--<td width="7%"><input readonly="true" class="finput2" id="bfcbValue"></td>--%>
                <%--<td width="9%"></td>--%>
                <%--<td width="10%"></td>--%>
                <%--<td width="5%"></td>--%>
                <%--<td width="10%"></td>--%>
                <%--<td width="9%"></td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</div>--%>
<%
//	String widthArr[] = { "7%" , "3%" , "7%" , "8%" , "6%"
//						, "3%" , "3%" , "3%"  , "3%" , "3%"
//						, "3%" , "3%" , "3%" , "3%" , "3%"
//						, "5%" , "8%" , "6%" , "8%" , "4%"
//						, "8%" };

    String widthArr[] = { "7%" , "3%" , "7%" , "8%" , "6%"
                        , "3%" , "3%" , "3%"  , "3%" , "3%"
                        , "3%" , "3%" , "3%" , "3%" , "3%"
                        , "5%" , "8%" , "6%" , "8%" , "4%"
                        , "8%" };

	int widthIndex = 0;
%>
<div id="headDiv" style="overflow: hidden; left: 1px; width: 100%">
    <table id="headTable" class="headerTable" border="1" width="220%">
        <tr height=23px>
            <td align=center width="1%">
                <input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')">
            </td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">标签号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产编号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">报废类型</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产名称</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产型号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">重要资产</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">数量</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">单位</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产原值</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">累计折旧</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">累计减值</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">净额</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">报废成本</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">剩余月数</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">启用日期</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产目录</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">所在地点</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">网元编号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">责任部门</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">责任人</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">备注</td>
            <td style="display: none">
                隐藏域字段
            </td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow: scroll; height: 400px; width: 100%;top: 48px; left: 1px;" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="220%" border="1" bordercolor="#666666" style="TABLE-LAYOUT: fixed; word-break: break-all">
<%
    if (lineSet != null && !lineSet.isEmpty()) {
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
                widthIndex = 0;
    %>
        <tr class="dataTR">
            <td width="1%" align="center">
                <input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer">
                <input type="text" name="rejectTypeName" id="rejectTypeName<%=i%>" class="finput2" readonly value="<%=lineDTO.getRejectTypeName()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="importantFlag" id="importantFlag<%=i%>" class="finput2" readonly value="<%=lineDTO.getImportantFlag()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput" readonly value="<%=lineDTO.getCurrentUnits()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput2" readonly value="<%=lineDTO.getUnitOfMeasure()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="sumDepreciation" id="sumDepreciation<%=i%>" class="finput3" readonly value="<%=lineDTO.getSumDepreciation()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="impairReserve" id="impairReserve<%=i%>" class="finput3" readonly value="<%=lineDTO.getImpairReserve()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right">
                <input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finputNoEmpty" value="<%=lineDTO.getRetirementCost()%>" onchange="do_setQuantity();">
            </td>
             <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="deprnLeftMonth" id="deprnLeftMonth<%=i%>" class="finput2" readonly value="<%=lineDTO.getDeprnLeftMonth()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getOldFaCategoryCode()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left"><input type="text" name="netUnit" id="netUnit<%=i%>" class="finput" value="<%=lineDTO.getNetUnit() %>"></td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>">
            </td>
              <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
            <td style="display: none">
                <input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
                <input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
            </td>
        </tr>
<%
        }
    }
    widthIndex = 6;
%>
    </table>


    <table id="summaryTable" width="220%" border="1" bordercolor="#666666" style="TABLE-LAYOUT: fixed; word-break: break-all">
        <tr height=23px>
            <td align=center width="35%" colspan="7">合计</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="numValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="yuanzhiValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="ljzjValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="ljjzalue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="jingeralue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="bfzbValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex] %>"></td>
            <td style="display: none" width="0"></td>
        </tr>
    </table>
</div>
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</body>
</html>

<script type="text/javascript">

var xmlHttp = null;

function initPage(){
    window.focus();
    do_SetPageWidth();
    doLoad();
    do_FormatQuantity();
	do_ComputeSummary();
    do_ControlProcedureBtn();
    do_ProcessTableAlign();
}

function do_ComputeSummary(){
    var transferType = mainFrm.transferType.value;
    var transType = mainFrm.transType.value;
    if((transferType != "BTW_COMP") && (transType != "ASS-DIS")){
        return;
    }
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "ljjzalue", "jingeralue", "bfzbValue");
        var summaryCell = new Array("currentUnits", "cost", "sumDepreciation", "impairReserve", "deprnCost", "retirementCost");
        var idCount = idArr.length;
        var sumValueArr = new Array();
        for(var i = 0; i < rowCount; i++){
            var tr =  rows[i];
            for(var j = 0; j < idCount; j++){
                var node = getTrNode(tr, summaryCell[j]);
                if(!sumValueArr[j]){
                    sumValueArr[j] = 0;
                }
                sumValueArr[j] += Number(node.value);
            }
        }
        for(j = 0; j < idCount; j++){
            node = document.getElementById(idArr[j]);
            if(j == 0){
                node.value = sumValueArr[j];
            } else {
                node.value = formatNum(sumValueArr[j], 2);
            }
        }
    }
}

function do_FormatQuantity(){
    var tab = document.getElementById("dataTable");
    if(tab){
        var rows = tab.rows;
        if(rows){
            for(var i = 0; i < rows.length; i++){
                var tr = rows[i];
                var node = getTrNode(tr, "currentUnits");
                if(node){
                    var currentUnits = node.value;
                    currentUnits = formatNum(currentUnits, 0);
                    node.value = currentUnits;
                }
            }
        }
    }
}

function do_Complete_app_yy() {
	if(true){
        try{
            var actObj = document.getElementById("act");
			actObj.value = "APPROVE_ACTION";
			document.forms[0].submit();
			document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
		}catch(ex){
			alert( ex.message );
		}finally{
		}
	}
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
</script>
