<%--
  User: zhoujs
  Date: 2007-9-22
  Time: 13:56:07
  Function: 选择要创建工单(可多选)
--%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO"%>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderBatchDTO" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    String action =StrUtil.nullToString(request.getParameter("act"));
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String projectOpt = (String) request.getAttribute(WebAttrConstant.PROJECT_OPTION);
    EtsObjectDTO etsObject=(EtsObjectDTO)request.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
    EtsWorkorderBatchDTO etsWorkorderBatch=(EtsWorkorderBatchDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO);
    EtsWorkorderDTO etsWorkorder=(EtsWorkorderDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_DTO);
	String countName=(String)request.getAttribute("COUNT_NAME_OPTIONS");
	String countOptions = (String)request.getAttribute("COUNT_OPTIONS");
    boolean isFirstNode=StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");
    String workorderTypeDesc = StrUtil.nullToString(request.getParameter("workorderTypeDesc"));
    String groupName = StrUtil.nullToString(request.getParameter("groupName"));

    String category = StrUtil.nullToString(request.getParameter("objectCategory"));
    String costCenterCode = StrUtil.nullToString(request.getParameter("costCenterCode"));
    String costCenterName = StrUtil.nullToString(request.getParameter("costCenterName"));
%>
<html>
<base target="_self">
<head>
    <title>选择工单地点页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>

<%--<link href="/WebLibary/css/view.css" rel="stylesheet" type="text/css">--%>
<%--<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">--%>
<link href="/WebLibary/css/eam.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/jquery-1.2.6.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
<script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="1" topmargin="0" onload="do_initPage()" onkeydown="autoExeFunction('do_query()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm"  method="post" action="/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet">
    <script type="text/javascript">
        var ArrAction1 = new Array(true, "取消", "del.gif", "取消", "do_Cancel");
        var ArrAction2 = new Array(true, "生成工单", "action_sign.gif", "生成工单", "do_GenOrders");
        var ArrAction3 = new Array(false, "新建基站", "act_refresh.gif", "生成工单", "newBS");
        var ArrAction4 = new Array(true, "查询", "action_view.gif", "查询", "do_query");
        var ArrActions = new Array(ArrAction1, ArrAction3, ArrAction2, ArrAction4);
        var ArrSinoViews = new Array();
        printTitleBar("添加工单");
        printToolBar();
    </script>

    <input type="hidden" name="isFirstNode" value="<%=isFirstNode%>">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="<%=etsWorkorder.getGroupId()%>">
    <input type="hidden" name="distributeGroup" value="<%=etsWorkorder.getDistributeGroup()%>">
    <input type="hidden" name="prjId" value="<%=etsObject.getProjectId()%>">
    <input type="hidden" name="workorderType" value="<%=etsWorkorderBatch.getWorkorderType()%>">
    <input type="hidden" name="workorderBatch" value="<%=etsWorkorderBatch.getWorkorderBatch()%>">
    <input type="hidden" name="objectCategory" value="<%=category%>">
    <input type="hidden" name="costCenterCode" value="<%=costCenterCode%>">
<table border="0" width="100%" align="left" id="table1">
    <tr bgcolor="#ACCDFF">
        <td colspan="8" width="100%"><span>&gt;&gt;&gt;工单基本信息</span></td>
    </tr>
    <tr>
        <td width="10%" align="right">工单类型：</td>
        <td width="15%" align="left"><input type="text" name="workorderTypeDesc" style="width:70%" value="<%=etsWorkorder.getWorkorderTypeDesc()%>" class="input_style2"></td>
        <td width="10%" align="right">接单部门：</td>
        <td width="15%"><input name="groupName" type="text" style="width:70%" value="<%=etsWorkorder.getGroupName()%>" class="input_style2"></td>
        <td width="10%" align="right">开始时间：</td>
        <td width="15%">
            <input class="input_style2" name="startDate" type="text" style="width:70%" readonly="true" value="<%=StrUtil.nullToString(etsWorkorder.getStartDate())%>">
            <img src="/images/calendar.gif" width="16" height="15" alt="选择时间" id="calendar1"  onClick="getDateTime('mainFrm.startDate');">
        </td>
        <td width="10%" align="right">实施周期(天)：</td>
        <td width="15%" align="left"><input name="implementDays" onkeydown="intOnlyOnkeyDown(this.value);" class="inputNoEmpty" style="width:70%" type="text" value="<%=etsWorkorder.getImplementDays()%>"><font color="red">*</font>  </td>
    </tr>
    <tr bgcolor="#BDD3FF">	
        <td colspan="8" width="100%"><span>&gt;&gt;&gt;地点信息查询</span></td>
    </tr>
    <tr>
    <%
    	if(!workorderTypeDesc.equals("巡检")){
    %>
    	<td width="10%" align="right">所属工程：</td>
        <td width="15%" align="left"><input style="width:70%" name="prjName" value="<%=etsObject.getProjectName()%>" readonly="true" class="input_style1" > </td>
        <td width="10%" align="right">地点编号：</td>
        <td width="15%" align="left"><input type="text" class="input_style1" name="workorderObjectCode" style="width:70%" value="<%=etsObject.getWorkorderObjectCode()%>"></td>
        <td width="10%" align="right">地点名称：</td>
        <td width="15%" align="left"><input type="text" name="workorderObjectName" class="input_style1" style="width:70%" value="<%=etsObject.getWorkorderObjectName()%>"></td>
        <td width="10%" align="right">设备状态：</td>
        <td width="15%" align="left"><select style="width:70%" name="itemStatus"><option value="">全部</option><option value="TO_ASSETS">待转资</option></select></td>
    </tr>
    <tr>
    	<td align=right width="7%" height="18">成本中心：</td>
        <td width="15%" height="18">
        <input type="text" name="costCenterName" class="input_style1" readonly style="width:100%;cursor:hand" value="<%=etsObject.getCostCenterName()%>" title="点击选择或更改“成本中心”" onClick="chooseCostCenter()" size="20"> </td> 
        <%-- <a href="#" onclick="chooseCostCenter();" title="点击选择成本中心"><font color="blue"><%=WebConstant.CHOOSE_DICT%></font></a> --%>
    <%	}else{
    %>
    		<%--    <td width="10%" align="right">所属工程：</td>
        <td width="15%" align="left"><input style="width:70%" name="prjName" value="<%=etsObject.getProjectName()%>" readonly="true" class="input_style1" > </td>--%>
        <td width="10%" align="right">地点编号：</td>
        <td width="15%" align="left"><input type="text" class="input_style1" name="workorderObjectCode" style="width:70%" value="<%=etsObject.getWorkorderObjectCode()%>"></td>
        <td width="10%" align="right">地点名称：</td>
        <td width="15%" align="left"><input type="text" name="workorderObjectName" class="input_style1" style="width:70%" value="<%=etsObject.getWorkorderObjectName()%>"></td>
        <td width="10%" align="right">设备状态：</td>
        <td width="15%" align="left"><select style="width:70%" name="itemStatus"><option value="">全部</option><option value="TO_ASSETS">待转资</option></select></td>
        <td align=right width="7%" height="18">成本中心：</td>
        <td width="15%" height="18">
        <input type="text" name="costCenterName" class="input_style1" readonly style="width:100%;cursor:hand" value="<%=etsObject.getCostCenterName()%>" title="点击选择或更改“成本中心”" onClick="chooseCostCenter()" size="20"> </td> 
        <%-- <a href="#" onclick="chooseCostCenter();" title="点击选择成本中心"><font color="blue"><%=WebConstant.CHOOSE_DICT%></font></a> --%>
    </tr>
    <tr>
    <%	}
     %>

        <td width="10%" align="right">地市名称：</td>
        <td width="15%" align="left">
	        <select name="countyCodeShi" onchange="jilian(this.value);" style="width:70%">
	        	<%=countName%>
	        </select> 
        </td>
        <td width="10%" align="right">区县名称：</td>
        <td width="15%" align="left"><select id="selectData" name="countyCodeXian" style="width:70%"><%=countOptions%></select></td>
        <td width="10%"></td>
        <td width="15%"></td>
    </tr>
</table>  
<input type="hidden" name="excel" value="">  
</form>

<div id="buttonDiv" style="position:absolute;top:155px;left:1px;width:100%">
   <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入查询"  onClick="do_excel();">  
   <span id="warn"></span> 
</div>

<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:180px;left:1px;width:100%">
    <table id="headTable" class="headerTable" border=1 style="width:100%" cellpadding="0" cellspacing="0">
       <tr>
            <td height="22" width="3%" align="center"><input type="checkbox" class="headCheckbox" name="ctlBox" onclick="checkAll(this.name,'objNos_n')"></td>
            <td height="22" width="16%" align="center">地点编号</td>
            <td height="22" width="50%" align="center">地点名称</td>
            <td height="22" width="16%" align="center">所属工程</td>
            <td height="22" width="15%" align="center">接单部门</td>
        </tr>
    </table>
</div>
<%

	if(rows != null && !rows.isEmpty()){
%>
<div id="dataDiv" style="overflow:scroll;height:46%;width:100%;position:absolute;top:178px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1"  style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean canDistribute=row.getStrValue("CAN_DISTRIBUTE").equalsIgnoreCase("Y");
            if(canDistribute){
%>
			<tr class="dataTR" onclick="executeClick(this);">
				<td width="3%" align="center"><%=row.getValue(QueryConstant.CHECK_BOX_PROP)%></td>
<%
            } else {
%>
            <tr class="dataTR" title="该地点有未完成巡检工单,请尽快完成！">
				<td width="3%" align="center" id="disableTR<%=i%>"><%=row.getValue(QueryConstant.CHECK_BOX_PROP)%></td>
<%
            }
%>
                <td width="16%" ><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
				<td width="50%" ><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
				<td width="16%" ><%=row.getValue("PROJECT_NAME")%></td>
				<td width="15%" ><%=etsWorkorder.getGroupName()%></td>
			</tr>
<%
		}
%>
    </table>
</div>
<div id="pageNaviDiv"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
}
%>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    do_SetPageWidth();
    disableCheckBox();
}

function disableCheckBox(){
    var dataTable = document.getElementById("dataTable");
    if(dataTable){
        var rows = dataTable.rows;
        var rowCount = rows.length;
        if(rowCount > 0){
            for(var i = 0; i < rowCount; i++){
                var td = rows[i].cells[0];
                if(td.id && td.id != ""){
                    var chkObj = td.childNodes[0];
                    chkObj.disabled = true;
                }
            }
        }
    }
}

function do_query() {
	/**
    if(document.mainFrm.workorderObjectCode.value==""&&document.mainFrm.workorderObjectName.value==""&&document.mainFrm.countyName.value==""){
       alert("请输入查询条件");
       return;
    }
    **/
    document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    document.mainFrm.submit();
}

function chooseCostCenter() {
    var lookUpName = "<%=LookUpConstant.COST_CENTER%>";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (projects) {
        dto2Frm(projects[0], "mainFrm");
    } else {
        document.mainFrm.costCenterCode.value = "";
        document.mainFrm.costCenterName.value = "";
    }
}


function do_excel() {
	excelType = "3";
    var returnValue=do_ImportExcelData();
    if (returnValue) {
        document.mainFrm.excel.value = returnValue;
        document.mainFrm.act.value = "excel";
        document.mainFrm.submit();
    }
}

function do_Cancel() {
    window.close();
}

function intOnlyOnkeyDown(obj) {
    var k = window.event.keyCode;
    if ((k >= 48 && k <= 57) || (k >= 96 && k <= 105) || k == 8) {
    	
    } else {
        window.event.returnValue = false;
    }
}

function do_GenOrders() {
    var startDate = mainFrm.startDate.value;
    if(isEmpty(startDate)){
        alert("开始时间必须选择，不能为空。");
        return;
    }

    var implementDays = mainFrm.implementDays.value;
    if(isEmpty(implementDays)){
        alert("实施周期必须填写，不能为空。");
        return;
    }
    if(!isPositiveInteger(implementDays)){
        mainFrm.implementDays.value = "";
        mainFrm.implementDays.focus();
        alert("实施周期必须填写正整数");
        return;
    }
    var chkObj = mainFrm.$$$CHECK_BOX_HIDDEN$$$;
    if(!chkObj || chkObj.value == ""){
        alert("尚未选择地点，不能生成工单");
        return;
    }
    mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
    mainFrm.submit();
    var hintDiv = document.getElementById("$$$waitTipMsg$$$");
    var divHTML = hintDiv.innerHTML;
    divHTML = divHTML.replace("正在请求数据，请稍候", "正在生成工单，请稍候");
    hintDiv.innerHTML = divHTML;
    hintDiv.style.visibility = "visible";
}
function jilian(obj){
	$.ajax({
    url: '/servlet/com.sino.ams.workorder.servlet.ChooseCountyNameServlet?code='+obj + '&countyCodeXian=' + document.getElementById("selectData").value,
    type: 'POST',
    success: function (data)
    {
		var obj=document.getElementById("selectData");
		obj.outerHTML = "<select id='selectData' name='countyCodeXian' style='width:70%'>"+data+"</select>";
    }
});
}
</script>


