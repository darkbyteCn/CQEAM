<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<html>
<head>
<%
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    int topHeight = 194;
    if( dto.getSearchType().equals( WebAttrConstant.SEARCH_ADVANCE  ) ){
        topHeight = 200;
    }else{
        topHeight = 90;
    }
%>
    <title>实物台帐维护--查询数据</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('1.A');" onkeydown="autoExeFunction('do_Search();')">
<input type="hidden" name="helpId" value="">
<%=WebConstant.WAIT_TIP_MSG%>
<%=AssetsWebAttributes.EXPORT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>

<form action="<%=AssetsURLList.ITEM_MAINTAIN_SERVLET%>" method="post" name="mainFrm">
	<script type="text/javascript">
		var searchType = "<%= dto.getSearchType() %>";

	    var myArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search");
	    var myArrAction1 = new Array(true, "导出", "toexcel.gif", "导出", "do_ShowExcel");
	    var myArrAction11 = new Array(true, "批量更新", "action_save.gif", "点击更新设备", "do_UpdateItem");
	   	var myArrAction2 = new Array(true, "关闭", "action_cancel.gif", "关闭", "doClose");

	   	var myArrAction3 = new Array(true, "普通搜索", "actn023.gif", "普通搜索", "do_PtSearch");
	    var myArrAction4 = new Array(true, "高级搜索", "actn023.gif", "高级搜索", "do_AdSearch");

	   	if( searchType == "<%= WebAttrConstant.SEARCH_ADVANCE %>" ){
	   		ArrActions = new Array( myArrAction0,myArrAction3,myArrAction1,myArrAction11,myArrAction2 );
	   	}else{
	   		ArrActions = new Array( myArrAction0,myArrAction4,myArrAction1,myArrAction11,myArrAction2 );
	   	}

		printToolBar();

	    function doClose(){
	    	top.close();
	    }

	    function do_PtSearch(){
	    	document.getElementById("searchType").value = "";
	    	mainFrm.act.value = "";
	    	document.forms[0].submit();
	    }

	    function do_AdSearch(){
	    	document.getElementById("searchType").value = "<%= WebAttrConstant.SEARCH_ADVANCE %>";
	    	mainFrm.act.value = "";
	    	document.forms[0].submit();
	    }
	    
	    
		function do_SelectItemCode(){
		with(mainFrm){
			var lookUpName = "LOOK_UP_SYS_ITEM";
			var dialogWidth = 48;
			var dialogHeight = 30;
			var userPara = ""; 
			//"itemCategory=" + document.mainFrm.itemCategory.value;
			var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
			if (objs) {
			    var obj = objs[0];
				dto2Frm(obj, "mainFrm");
			} else {
				//itemCode.value = "";
				mainFrm.itemName.value = "";
				try{
				mainFrm.itemSpec.value = "";
				}catch(ex){}
			}
		}
	}
	</script>

    <table border="0" width="100%" class="queryTable">
<%
    if( dto.getSearchType().equals( WebAttrConstant.SEARCH_ADVANCE )){
%>
        <tr height="22px">
            <td width="7%" align="right">设备专业：</td>
            <td width="28%"><select class="select_style1" name="itemCategory" style="width:100%"><%=dto.getItemCategoryOpt()%></select></td>
            <td width="9%" align="right">设备名称：</td>
            <td width="16%"><input class="input_style1" type="text" name="itemName" style="width:80%" value="<%=dto.getItemName()%>"><a href="" title="点击选择设备分类" onClick="do_SelectItemCode();return false">[…]</a></td>
            <td width="8%" align="right">资产标签：</td>
            <td width="12%"><input class="input_style1" type="text" name="fromBarcode" style="width:80%" value="<%=dto.getFromBarcode()%>"></td>
            <td width="8%" align="right">到：</td>
            <td width="12%"><input class="input_style1" type="text" name="toBarcode" style="width:80%" value="<%=dto.getToBarcode()%>"></td>
        </tr>
        <tr height="22px">
            <td  align="right">实物部门：</td>
            <td ><select class="select_style1" onchange="do_ProcessOptionWidth(this)" name="specialityDept" style="width:100%"><%=request.getAttribute("DEPT_OPTIONS2")%></select></td>
            <td  align="right">规格型号：</td>
            <td ><input class="input_style1" type="text" name="itemSpec" style="width:80%" value="<%=dto.getItemSpec()%>"></td>
            <td  align="right">资产创建日期：</td>
            <td ><input class="input_style2" type="text" name="creationDate" value="<%=dto.getCreationDate()%>" style="width:80%;cursor:pointer" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(creationDate, lastUpdateDate)"></td>
            <td  align="right">到：</td>
            <td ><input class="input_style2" type="text" name="lastUpdateDate" value="<%=dto.getLastUpdateDate()%>" style="cursor:pointer;width:80%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(creationDate, lastUpdateDate)"></td>
        </tr>
        <tr height="22px">
            <td  align="right">责任部门：</td> 
            <td ><select class="select_style1" onchange="do_ProcessOptionWidth(this)" name="responsibilityDept" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></td>
            <td  align="right">责任人：</td>
            <td ><input class="input_style1" type="text" name="responsibilityUserName" value="<%=dto.getResponsibilityUserName()%>" style="width:80%"><a href="" title="点击选择责任人" onclick="do_SelectPerson(); return false;">[…]</a></td>
            <td  align="right">启用日期：</td>
            <td ><input class="input_style2" type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:80%;cursor:pointer" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
            <td  align="right">到：</td>
            <td ><input class="input_style2" type="text" name="endDate" value="<%=dto.getEndDate()%>" style="cursor:pointer;width:80%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
        </tr>
        <tr height="22px">
            <td  align="right">使用部门：</td>
            <td ><select class="select_style1" onchange="do_ProcessOptionWidth(this)" name="maintainDept" style="width:100%"><%=request.getAttribute("DEPT_OPTIONS3")%></select></td>
            <td  align="right">使用人：</td>
            <td ><input class="input_style1" type="text" name="maintainUser" style="width:80%" value="<%=dto.getMaintainUser()%>"></td>
            <td  align="right">地点：</td>
            <td ><input class="input_style1" type="text" readonly="true" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" style="width:80%"><a href="" title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
            <td  align="right">厂商：</td>
            <td ><input class="input_style1" type="text" name="manufacturerName" value="<%=dto.getManufacturerName()%>" style="width:80%"><a style="cursor:pointer" onclick="do_selectNameManufacturer();">[…]</a></td>
        </tr>
        <tr height="22px">
            <td  align="right">设备状态：</td>
            <td ><select class="select_style1" name="itemStatus" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS)%></select></td>
            <td  align="right">资产种类：</td>
            <td ><select class="select_style1" name="financeProp" style="width:80%"><%=request.getAttribute("FINANCE_PROP_OPTION")%></select></td>
            <td  align="right">资产目录：</td>
            <td ><input type="text" class="input_style1" name="contentName" readonly="true" value="<%=dto.getContentName()%>" style="width:80%"><a href="" title="点击选择目录" onclick="do_SelectContent(); return false;">[…]</a></td>
            <td  align="right">项目名称：</td>
            <td ><input class="input_style1" type="text" name="projectName" readonly="true"  value="<%=dto.getProjectName()%>" style="width:80%;" title="点击选择项目"><a href="" title="点击选择项目" onclick="do_SelectProject(); return false;">[…]</a></td>
        </tr>
        <tr height="25px">
            <td  align="right">共享类型：</td>
            <td ><select class="select_style1" name="isShare" style="width:100%"><%=request.getAttribute("SHARE_OPTION")%></select></td>
            <td  align="right">逻辑网络元素：</td>
            <td ><input type="text" class="input_style1" name="logNetEle" value="<%=dto.getLogNetEle()%>" style="width:80%"><a href="" title="点击选择逻辑网络元素" onclick="do_SelectLne(); return false;">[…]</a></td>
            <td  align="right">投资分类：</td>
            <td ><input type="text" class="input_style1" name="investCatName" value="<%=dto.getInvestCatName()%>" style="width:80%"><a href="" title="点击选择投资分类" onclick="do_SelectCex(); return false;">[…]</a></td>
            <td  align="right">业务平台：</td>
            <td ><input type="text" class="input_style1" name="opeName" value="<%=dto.getOpeName()%>" style="width:80%"><a href="" title="点击选择业务平台" onclick="do_SelectOpe(); return false;">[…]</a></td>
        </tr>
        <tr height="22px">
            <td  align="right">共建类型：</td>
            <td ><select class="select_style1" name="constructStatus" style="width:100%"><%=request.getAttribute("CONSTRUCT_OPTION")%></select></td>
            <td  align="right">网络层次：</td>
            <td ><input type="text" class="input_style1" name="lneName" value="<%=dto.getLneName()%>" style="width:80%"><a href="" title="点击选择网络层次" onclick="do_SelectNle(); return false;">[…]</a></td>
            <td  align="right"></td>
            <td ></td>
            <td  align="right"></td>
            <td ></td>
        </tr>

<%
    } else {
%>
        <tr height="22px">
        	<td width="7%"  align="right">地点：</td>
            <td width="28%"  ><input class="input_style1" type="text" readonly="true" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" style="width:90%"><a href="" title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
            <td width="9%"  align="right">设备名称：</td>
            <td width="16%"  ><input class="input_style1" type="text" name="itemName" style="width:80%" value="<%=dto.getItemName()%>"></td>
            <td width="8%"  align="right">规格型号：</td>
            <td width="12%"  ><input class="input_style1" type="text" name="itemSpec" style="width:80%" value="<%=dto.getItemSpec()%>"></td>
            <td width="8%"  align="right">资产种类：</td>
            <td width="12%" ><select class="select_style1" name="financeProp" style="width:80%"><%=request.getAttribute("FINANCE_PROP_OPTION")%></select></td> 
        </tr>
        
        <tr height="22px">
       		<td  align="right">责任部门：</td>
            <td ><select class="select_style1" onchange="do_ProcessOptionWidth(this)"  name="responsibilityDept" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></td>
            <td  align="right">责任人：</td>
            <td ><input class="input_style1" type="text" name="responsibilityUserName" value="<%=dto.getResponsibilityUserName()%>" style="width:80%"><a href="" title="点击选择责任人" onclick="do_SelectPerson(); return false;">[…]</a></td>     
            <td  align="right">资产目录：</td>
            <td ><input type="text" class="input_style1" name="contentName" readonly="true" value="<%=dto.getContentName()%>" style="width:80%"><a href="" title="点击选择目录" onclick="do_SelectContent(); return false;">[…]</a></td>
            <td   align="right">项目名称：</td>
            <td ><input class="input_style1" type="text" name="projectName" readonly="true"  value="<%=dto.getProjectName()%>" style="width:80%;" title="点击选择项目"><a href="" title="点击选择项目" onclick="do_SelectProject(); return false;">[…]</a></td>
        </tr> 
         
        <tr height="22px" style="display: none;">
        	<td> 
        	<select class="select_style1" name="itemStatus" style="width:80%"><%=request.getAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS)%></select>
	        <select class="select_style1" name="isShare" style="width:80%"><%=request.getAttribute("SHARE_OPTION")%></select>
	        <select class="select_style1" name="constructStatus" style="width:80%"><%=request.getAttribute("CONSTRUCT_OPTION")%></select>
	        <input class="input_style1" type="text" name="fromBarcode" style="width:80%" value="<%=dto.getFromBarcode()%>">
        	<select class="select_style1" name="maintainDept" style="width:100%"><%=request.getAttribute("DEPT_OPTIONS3")%></select> 
        	<select class="select_style1" name="itemCategory" style="width:100%"><%=dto.getItemCategoryOpt()%></select>
        	<select class="select_style1" name="specialityDept" style="width:100%"><%=request.getAttribute("DEPT_OPTIONS2")%></select>
        	<input class="input_style1" type="text" name="toBarcode" style="width:80%" value="<%=dto.getToBarcode()%>">
        	<input class="input_style2" type="text" name="creationDate" value="<%=dto.getCreationDate()%>" style="width:80%;cursor:pointer" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(creationDate, lastUpdateDate)">
        	<input class="input_style2" type="text" name="lastUpdateDate" value="<%=dto.getLastUpdateDate()%>" style="cursor:pointer;width:80%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(creationDate, lastUpdateDate)">
       		<input type="hidden" name="maintainUser" value="<%= dto.getMaintainUser() %>"/> 
		    <input type="hidden" name="manufacturerName" value="<%= dto.getManufacturerName() %>"/> 
		    <input type="hidden" name="lneName" value="<%= dto.getLneName() %>"/>
		    <input type="hidden" name="logNetEle" value="<%= dto.getLogNetEle() %>"/>
		    <input type="hidden" name="investCatName" value="<%= dto.getInvestCatName() %>"/>
		    <input type="hidden" name="opeName" value="<%= dto.getOpeName() %>"/>  
		    <input type="hidden" name="startDate" value="<%= dto.getStartDate() %>"/>
		    <input type="hidden" name="endDate" value="<%= dto.getEndDate() %>"/>
		    </td>
        </tr>  
<%
    }
%>
    </table>

    <input name="searchType" id="searchType" type="hidden" value="<%=dto.getSearchType()%>">

    <input type="hidden" name="excelType"/>
    <input type="hidden" name="isExp" value="0">
    <input type="hidden" name="act" value="">

    <input type="hidden" name="itemCode" value="">
    <input type="hidden" name="addressId" value="">
    <input type="hidden" name="responsibilityUser" value="">
    <input type="hidden" name="employeeNumber" value="">
    <input type="hidden" name="responsibilityDeptName" value="">
    <input type="hidden" name="maintainDeptName" value="">
    <input type="hidden" name="workorderObjectCode" value="<%=dto.getWorkorderObjectCode()%>">
    <input type="hidden" name="manufacturerId" value="">
    <input type="hidden" name="projectNumber" value="<%=dto.getProjectNumber()%>">
    <input type="hidden" name="contentCode" value="<%=dto.getContentCode()%>">
    <input type="hidden" name="lneId" value="">
    <input type="hidden" name="cexId" value="">
    <input type="hidden" name="opeId" value="">
    <input type="hidden" name="nleId" value="">
    <input type="hidden" name="flag" value="0">
    <input type="hidden" name="remark1" value="">
    <input type="hidden" name="remark2" value="">
    <input type="hidden" name="itemUnit" value="">
    <input type="hidden" name="actualQty" value="">


<%@ include file="/newasset/searchParameterBack.html" %>
</form>

<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:<%=topHeight %>px;left:0px;width:100%">
    <table border=1 width="500%" class="eamHeaderTable" id="$$$headerTable$$$">
        <tr height="23px" onclick="executeClick(this)" style="cursor:pointer" title="点击全选或全不选">
            <td align=center width="1%"><input type="checkbox" name="mainCheck" id="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
            <td align=center width=2%>资产标签</td>
            <td align=center width=2%>设备专业</td>

            <td align=center width=3%>设备名称</td>
            <td align=center width=4%>规格型号</td>
            <td align=center width=2%>计量单位</td>

            <td align=center width=1%>数量</td>
            <td align=center width=2%>资产种类</td>
            <td align=center width=2%>设备状态</td>

            <td align=center width=3%>地点编号</td>
            <td align=center width=5%>地点名称</td>
            <td align=center width=1%>责任人</td>

            <td align=center width=2%>员工编号</td>
            <td align=center width=5%>责任部门</td>
            <td align=center width=1%>使用人</td>

            <td align=center width=4%>使用部门</td>
            <td align=center width=4%>实物部门</td>
            <td align=center width=4%>项目名称</td>

            <td align=center width=2%>项目编号</td>
            <td align=center width=3%>厂商名称</td>
            <td align=center width=2%>实际数量</td>

            <td align=center width=3%>资产目录代码</td>
            <td align=center width=5%>资产目录描述</td>
            <td align=center width=2%>共享类型</td>

            <td align=center width=2%>共建类型</td>
            <td align=center width=2%>逻辑网络元素</td>
            <td align=center width=2%>投资分类</td>

            <td align=center width=2%>业务平台</td>
            <td align=center width=2%>网络层次</td>
            <td align=center width=2%>资产使用年限</td>

            <td align=center width=2%>资产启用日期</td>
            <td align=center width=2%>资产创建日期</td>
            <td align=center width=2%>资产原值</td>

            <td align=center width=2%>资产残值</td>
            <td align=center width=2%>资产减值准备</td>
            <td align=center width=2%>资产累计折旧</td>

            <td align=center width=2%>资产净额</td>
            <td align=center width=3%>备注</td>
            <td align=center width=3%>备注一</td>
            <td align=center width=3%>备注二</td>
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:scroll;width:100%;position:absolute;top:<%=topHeight+21%>px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="500%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (hasData) {
                Row row = null;
                String barcode = "";
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    barcode = row.getStrValue("BARCODE");
        %>
        <tr class="dataTR" ondblclick="do_ShowLog('<%=row.getValue("BARCODE")%>')" style="cursor:pointer" title="双击查看该设备修改历史">
            <td width="1%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
            <td width=2%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getStrValue("BARCODE")%>"></td>
            <td width=2%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_CATEGORY_NAME")%>"></td>

            <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
            <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>

            <td width=1%><input type="text" name="currentUnits" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_QTY")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("FINANCE_PROP_VALUE")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ITEM_STATUS_NAME")%>"></td>

            <td width=3%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width=5%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
            <td width=1%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>

            <td width=2%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
            <td width=5%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("RESPONSIBILITY_DEPT_NAME")%>"></td>
            <td width=1%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MAINTAIN_USER")%>"></td>

            <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MAINTAIN_DEPT_NAME")%>"></td>
            <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("SPECIALITY_DEPT_NAME")%>"></td>
            <td width=4%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>

            <td width=2%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>
            <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("MANUFACTURER_NAME")%>"></td>
            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ACTUAL_QTY")%>"></td>

            <td width=3%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("CONTENT_CODE")%>"></td>
            <td width=5%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("CONTENT_NAME")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("IS_SHARE")%>"></td>

            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("CONSTRUCT_STATUS")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LOG_NET_ELE")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("INVEST_CAT_NAME")%>"></td>

            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("OPE_NAME")%>"></td>
            <td width=2%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LNE_NAME")%>"></td>
            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>

            <td width=2%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
            <td width=2%><input type="text" class="finput2" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ASSETS_CREATE_DATE")%>"></td>
            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("ORIGINAL_COST")%>"></td>

            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("SCRAP_VALUE")%>"></td>
            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("IMPAIR_RESERVE")%>"></td>
            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("DEPRN_RESERVE")%>"></td>

            <td width=2%><input type="text" class="finput3" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>
            <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("REMARK")%>"></td>
            <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("REMARK1")%>"></td>
            <td width=3%><input type="text" class="finput" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')" readonly value="<%=row.getValue("REMARK2")%>"></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<div id="ddDiv" style="position:absolute;z-index:2;top:130px;left:350px;background-color:azure;border:1px;width:300px;height:50px;text-align:center;visibility:hidden;">
	<table border = "0" width="100%">
	   <tr style="background:#0997F7;color:white;font:bold;height:20">
            <td>&nbsp;&nbsp;<span key="PleaseSelectFunction"/></td>
    	    <td align="right"><div style="padding-right:10px"><font face="webdings" style="cursor:pointer" onclick="do_ShowExcel()">r</font></div></td>
        </tr>
       <tr>
           <td width="80%" nowrap="nowrap" align="center">
				<input type="button" value="导出EXCEL" onclick="do_Export('xls')"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   						<input type="button" value="导出CSV" onclick="do_Export('csv')"/>
           </td>
       </tr>
	 </table>
	 <iframe   src="" frameborder="0"  style="position:absolute;   visibility:inherit;   top:0px;   left:0px;  width:expression(this.parentNode.offsetWidth);   height:expression(this.parentNode.offsetHeight);   z-index:-1;"></iframe> 
</div>
<%
    if (hasData) {
%>
<div id="pageNaviDiv" style="position:absolute;top:577px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no"
        frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
function initPage() { 
	initCheckBox();

    do_SetPageWidth();
    do_TransData();
    do_FormatQuantity();
    
    //设置下拉框的值title start
    var specialityDeptObj = document.getElementById("specialityDept");
    var responsibilityDeptObj = document.getElementById("responsibilityDept");
    var maintainDeptObj = document.getElementById("maintainDept");
    
    if( specialityDeptObj ){
    	setTitle(specialityDeptObj);
    }
    
    if( responsibilityDeptObj ){
    	setTitle(responsibilityDeptObj);
    }
    
    if( maintainDeptObj ){
    	setTitle(maintainDeptObj);
    }
    //设置下拉框的值title end
    
}

function initCheckBox(){
	var checkBoxs = document.getElementsByName( "subCheck" );
	for( var i=0 ; i<checkBoxs.length; i++ ){
		checkBoxs[i].onclick = function(){
			try{
				checkBoxChangeColor( this );
			}catch(ex){
				alert( ex.message );
			}
		} 
	}
	
	var mainCheckObj = document.getElementById( "mainCheck" );
	mainCheckObj.onclick = function(){
		checkAll( mainCheckObj.name, 'subCheck');
		for( var i=0 ; i<checkBoxs.length; i++ ){
			checkBoxChangeColor( checkBoxs[i] );
		}
	}
}

function checkBoxChangeColor(checkObj){
	var clickTR = checkObj.parentNode;
    while(clickTR.tagName != "TR"){
        clickTR = clickTR.parentNode;
    }
    var tds = clickTR.childNodes;
	if( checkObj.checked ){
		for( var i=0 ; i<tds.length; i++ ){
			tds[i].style.backgroundColor = 'lightblue'; //#EFEFEF pink
		}
		clickTR.style.backgroundColor = 'lightblue';
	}else{
		for( var i=0 ; i<tds.length; i++ ){
			tds[i].style.backgroundColor = '#FFFFFF'; 
		}
		clickTR.style.backgroundColor = '#FFFFFF';
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

function do_TransData() {
    var transTarget = null;
    if (!parent.updateDataFrm.document) {
        return;
    }
    if (!parent.updateDataFrm.document.mainFrm) {
        return;
    }
    if (!parent.updateDataFrm.document.mainFrm.checkedData) {
        return;
    }
    transTarget = parent.updateDataFrm.document.mainFrm.checkedData;
    if (!mainFrm.$$$CHECK_BOX_HIDDEN$$$) {
        transTarget.value = "";
    } else {
        var barcodes = new String(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value);
        barcodes = replaceStr(barcodes, "BARCODE:", "");
        barcodes = replaceStr(barcodes, "$$$", "  ");
        parent.updateDataFrm.document.mainFrm.checkedData.value = barcodes;
    }
}

function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_SelectAddress() {
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

function do_SelectProject() {
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var userParameters = "multipleChose=true";
    var returnValues = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userParameters);
    var project = new Object();
    project["projectName"] = "";
    project["projectNumber"] = "";
    if (returnValues) {
        var valueCount = returnValues.length;
        for (var i = 0; i < valueCount; i++) {
            var returnValue = returnValues[i];
            project["projectName"] += "'" + returnValue["projectName"] + "'";
            project["projectNumber"] += "'" + returnValue["projectNumber"] + "'";
            if(i < valueCount - 1){
                project["projectName"] += ", ";
                project["projectNumber"] += ", ";
            }
        }
    }
    dto2Frm(project, "mainFrm");
}

function do_SelectPerson() {
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
    var dialogWidth = 47;
    var dialogHeight = 30;
    var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = users[0];
        mainFrm.responsibilityUserName.value = user["userName"];
    } else {
        mainFrm.responsibilityUserName.value = "";
    }
}


function do_UpdateItem() {
    if (!mainFrm.$$$CHECK_BOX_HIDDEN$$$) {
        alert("请先执行查询操作，选中相应资产后再保存！");
        return;
    }
    if (mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == "") {
        alert("没有选中一条资产，不能执行保存操作！");
        return;
    }

    do_BackSearchParameter();//备份查询参数，以便提交后重新恢复
    var fieldNames = "itemCode;itemName;itemSpec;";
    fieldNames += "addressId;workorderObjectCode;workorderObjectName;";
    fieldNames += "responsibilityUser;employeeNumber;responsibilityUserName;";
    fieldNames += "responsibilityDept;responsibilityDeptName;";
    fieldNames += "maintainUser;";
    fieldNames += "maintainDept;maintainDeptName;";
    fieldNames += "manufacturerId;contentCode;contentName;isShare;itemStatus;constructStatus;";
    fieldNames += "lneId;cexId;opeId;nleId;";
    fieldNames += "specialityDept;";
    fieldNames += "remark1;remark2;actualQty;itemUnit;";
    clearFieldValue(fieldNames);

    var srcFrm = parent.updateDataFrm.document.mainFrm;
    copyFrmValue(srcFrm, mainFrm);
    do_CopySelectResDeptData();
    do_CopySelectMaintainDeptData();
    var totalContent = "";
    with (mainFrm) {
        totalContent += itemCode.value;
        totalContent += itemName.value;
        totalContent += itemSpec.value;
        totalContent += addressId.value;
        totalContent += workorderObjectCode.value;
        totalContent += workorderObjectName.value;
        totalContent += responsibilityDept.value;
        totalContent += responsibilityDeptName.value;
        totalContent += responsibilityUser.value;
        totalContent += employeeNumber.value;
        totalContent += responsibilityUserName.value;
        totalContent += maintainUser.value;
        totalContent += maintainDept.value;
        totalContent += maintainDeptName.value;
        totalContent += manufacturerId.value;
        totalContent += contentCode.value;
        totalContent += contentName.value;
        totalContent += isShare.value;
        totalContent += constructStatus.value;
        totalContent += itemStatus.value;
        totalContent += lneId.value;
        totalContent += cexId.value;
        totalContent += opeId.value;
        totalContent += nleId.value;
        totalContent += specialityDept.value;
        totalContent += remark1.value;
        totalContent += remark2.value;
        totalContent += actualQty.value;
        totalContent += itemUnit.value;
        if (totalContent == "") {
            alert("没有修改选中设备的任何信息，不能保存！");
            return;
        }
        if (confirm("确保要更新选中设备的数据吗？继续请点击“确定”按钮？否则请点击“取消”按钮")) {
            document.mainFrm.flag.value = "1";
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            act.value = "<%=AssetsActionConstant.UPDATE_ACTION%>";
            submit();
        } else {
            do_RollbackData();
        }
    }
}

var newDeptValue = "";
function do_CopySelectResDeptData() {
    var deptCode = parent.updateDataFrm.document.mainFrm.responsibilityDept.value;
    var deptName = parent.updateDataFrm.document.mainFrm.responsibilityDeptName.value;
    var deptOpt = new Option(deptName, deptCode);
    var deptObj = mainFrm.responsibilityDept;
    var deptVal = deptOpt.value;
    if (!haveChild(deptObj, deptOpt)) {
        var option = new Option(deptOpt.text, deptOpt.value);
        newDeptValue = deptOpt.value;
        deptObj.options[deptObj.length] = option;
    }
    selectSpecialOptionByItem(deptObj, deptOpt.value);
    if (deptOpt.value != "") {
        mainFrm.responsibilityDeptName.value = deptOpt.text;
    }
}

var newMaintainDeptValue = "";
function do_CopySelectMaintainDeptData() {
    var deptCode = parent.updateDataFrm.document.mainFrm.maintainDept.value;
    var deptName = parent.updateDataFrm.document.mainFrm.maintainDeptName.value;
    var deptOpt = new Option(deptName, deptCode);
    var deptObj = mainFrm.maintainDept;
    if (!haveChild(deptObj, deptOpt)) {
        var option = new Option(deptOpt.text, deptOpt.value);
        newMaintainDeptValue = deptOpt.value;
        deptObj.options[deptObj.length] = option;
    }
    selectSpecialOptionByItem(deptObj, deptOpt.value);
    if (deptOpt.value != "") {
        mainFrm.maintainDeptName.value = deptOpt.text;
    }
}


/**
 * 功能：保留数据，以便用于数据恢复
 */
var oriDeptValue = mainFrm.responsibilityDept.value;
var oriItemName = mainFrm.itemName.value;
var oriItemSpec = mainFrm.itemSpec.value;
var oriItemCode = mainFrm.itemCode.value;
var oriResponsibilityUserName = mainFrm.responsibilityUserName.value;
var oriResponsibilityUser = mainFrm.responsibilityUser.value;
var oriEmployeeNumber = mainFrm.employeeNumber.value;
var oriAddressId = mainFrm.addressId.value;
var oriObjectCode = mainFrm.workorderObjectCode.value;
var oriObjectName = mainFrm.workorderObjectName.value;
var oriMaintainUser = mainFrm.maintainUser.value;
var oriMaintainDeptValue = mainFrm.maintainDept.value;
var oriSpecialityDeptValue = mainFrm.specialityDept.value;
var oriContentCodeValue = mainFrm.contentCode.value;
var oriContentNameValue = mainFrm.contentName.value;
var oriManufacturerIdValue = mainFrm.manufacturerId.value;
var oriManufacturerNameValue = mainFrm.manufacturerName.value;
var oriIsShareValue = mainFrm.isShare.value;
var oriConstructStatusValue = mainFrm.constructStatus.value;
var oriLneIdValue = mainFrm.lneId.value;
var oriLogNetEleValue = mainFrm.logNetEle.value;
var oriCexIdValue = mainFrm.cexId.value;
var oriInvestCatNameValue = mainFrm.investCatName.value;
var oriOpeIdValue = mainFrm.opeId.value;
var oriOpeNameValue = mainFrm.opeName.value;
var oriNleIdValue = mainFrm.nleId.value;
var oriLneNameValue = mainFrm.lneName.value;
function do_RollbackData() {
    mainFrm.itemName.value = oriItemName;
    mainFrm.itemSpec.value = oriItemSpec;
    mainFrm.itemCode.value = oriItemCode;
    mainFrm.responsibilityUserName.value = oriResponsibilityUserName;
    mainFrm.responsibilityUser.value = oriResponsibilityUser;
    mainFrm.employeeNumber.value = oriEmployeeNumber;
    mainFrm.addressId.value = oriAddressId;
    mainFrm.workorderObjectCode.value = oriObjectCode;
    mainFrm.workorderObjectName.value = oriItemName;
    mainFrm.maintainUser.value = oriMaintainUser;
    mainFrm.contentCode.value = oriContentCodeValue;
    mainFrm.contentName.value = oriContentNameValue;
    mainFrm.manufacturerId.value = oriManufacturerIdValue;
    mainFrm.manufacturerName.value = oriManufacturerNameValue;
    mainFrm.lneId.value = oriLneIdValue;
    mainFrm.logNetEle.value = oriLogNetEleValue;
    mainFrm.cexId.value = oriCexIdValue;
    mainFrm.investCatName.value = oriInvestCatNameValue;
    mainFrm.opeId.value = oriOpeIdValue;
    mainFrm.opeName.value = oriOpeNameValue;
    mainFrm.nleId.value = oriNleIdValue;
    mainFrm.lneName.value = oriLneNameValue;

    var deptObj = mainFrm.responsibilityDept;
    selectSpecialOptionByItem(deptObj, oriDeptValue);
    if (newDeptValue != "") {
        dropSpecialOption(deptObj, newDeptValue);
    }

    var maintainDeptObj = mainFrm.maintainDept;
    selectSpecialOptionByItem(maintainDeptObj, oriMaintainDeptValue);
    if (newMaintainDeptValue != "") {
        dropSpecialOption(deptObj, newMaintainDeptValue);
    }

    var specialityDeptObj = mainFrm.specialityDept;
    selectSpecialOptionByItem(specialityDeptObj, oriSpecialityDeptValue);

    var isShareObj = mainFrm.isShare;
    selectSpecialOptionByItem(isShareObj, oriIsShareValue);

    var constructStatusObj = mainFrm.constructStatus;
    selectSpecialOptionByItem(constructStatusObj, oriConstructStatusValue);
}

function do_ShowLog(barcode) {
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

function do_SelectItemName() {
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ITEMNAME%>";
    var dialogWidth = 45;
    var dialogHeight = 30;
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (objs) {
        var obj = objs[0];
        dto2Frm(obj, "mainFrm");
    } else {
        mainFrm.itemName.value = "";
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
function do_SelectContent() {
    var lookUpName = "LOOK_UP_CONTENT";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var userParameters = "multipleChose=true";
    var returnValues = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userParameters);
    var content = new Object();
    content["contentName"] = "";
    content["contentCode"] = "";
    if (returnValues) {
        var valueCount = returnValues.length;
        for (var i = 0; i < valueCount; i++) {
            var returnValue = returnValues[i];
            content["contentName"] += "'" + returnValue["contentName"] + "'";
            content["contentCode"] += "'" + returnValue["contentCode"] + "'";
            if(i < valueCount - 1){
                content["contentName"] += ", ";
                content["contentCode"] += ", ";
            }
        }
    }
    dto2Frm(content, "mainFrm");
}

function do_SelectLne(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_LNE%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
	} else {
        mainFrm.logNetEle.value = "";
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
	} else {
        mainFrm.investCatName.value = "";
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
	} else {
        mainFrm.opeName.value = "";
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
	} else {
        mainFrm.lneName.value = "";
    }
}


function do_ShowExcel() {
	var _d = document.getElementById("ddDiv");
	var left = event.clientX;
	var top = event.clientY;
	_d.style.position = "absolute";
	_d.style.top = top + event.srcElement.offsetHeight - 10;
	_d.style.left = left - 80;
	if (_d.style.visibility == "hidden") {
		_d.style.visibility = "visible";
	}else {
		_d.style.visibility = "hidden";
	}
}

function do_Export(type) {
    var isExp = document.mainFrm.isExp.value;
    mainFrm.excelType.value = type;
    if (isExp == 0) {
        //        document.getElementById("$$$exportTipMsg$$$").style.visibility = "visible";
        document.mainFrm.isExp.value = "1";
        mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
        
        openExportMsgDiv();
        closeExportDiv();
    } else {
        alert("如果导出未完成，请稍后。如果导出完成，请重新查询后再导出。");
    }
}

function do_BackSearchParameter() {
    var frm = document.forms[0];
    var elements = frm.elements;
    var elementCount = elements.length;
    for (var i = 0; i < elementCount; i++) {
        var element = elements[i];
        var backName = element.name;
        if (backName.lastIndexOf("Back") == -1) {
            continue;
        }
        var srcName = backName.substring(0, backName.length - 4);
        element.value = frm.elements[srcName].value;
    }
}

function setTitle( selectObj ){
	var str = ""; 
	var optionObj = selectObj.options;
    var optionCount = optionObj.length; 
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].selected && !isEmpty(optionObj[i].value)) {
            str = optionObj[i].text ;
            break;
        }
    } 
	selectObj.title = str;
}

</script>