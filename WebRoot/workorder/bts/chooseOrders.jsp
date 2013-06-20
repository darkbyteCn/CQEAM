<%--
  User: zhoujs
  Date: 2007-9-22
  Time: 13:56:07
  Function: 选择要创建工单(当前用户)
--%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO"%>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderBatchDTO"%>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    String action =StrUtil.nullToString(request.getParameter("act"));

    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET);
    String projectOpt = (String) request.getAttribute(WebAttrConstant.PROJECT_OPTION);
    EtsObjectDTO etsObject=(EtsObjectDTO)request.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
    EtsWorkorderBatchDTO etsWorkorderBatch=(EtsWorkorderBatchDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO);
    EtsWorkorderDTO etsWorkorder=(EtsWorkorderDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_DTO);

    boolean isFirstNode=StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");
    String workorderTypeDesc = StrUtil.nullToString(request.getParameter("workorderTypeDesc"));
    String groupName = StrUtil.nullToString(request.getParameter("groupName"));

    String category = StrUtil.nullToString(request.getParameter("objectCategory"));
%>
<html>
<base target="_self">
<head>
    <title>自定义工单地点选择页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>

<link href="/WebLibary/css/view.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/eam.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
<script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_query()');">

<form name="mainFrm"  method="post" action="/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet">
    <script type="text/javascript">
        var ArrAction1 = new Array(true, "取消", "act_refresh.gif", "取消", "do_Cancel");
        var ArrAction2 = new Array(true, "生成工单", "del.gif", "生成工单", "do_GenOrders");
        var ArrAction3 = new Array(false, "新建基站", "act_refresh.gif", "生成工单", "newBS");
        var ArrActions = new Array(ArrAction1, ArrAction3, ArrAction2);
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

<table border="0" width="100%" align="left" class="queryTable" id="table1">
    <tr bgcolor="#ACCDFF">
        <td colspan="6"><span>&gt;&gt;&gt;工单基本信息</span></td>
    </tr>
    <tr>
        <td width="3%">&nbsp;</td>
        <td width="15%" align="right">工单类型： </td>
        <td width="28%" align="left"><input type="text" name="workorderTypeDesc" style="width:70%" value="<%=etsWorkorder.getWorkorderTypeDesc()%>" class="input_style2"></td>
        <td width="15%" align="right">接单部门:</td>
        <td width="28%"><input name="groupName" type="text" class="input_style2" style="width:70%" value="<%=etsWorkorder.getGroupName()%>" ></td>
        <td width="11%" align="center"></td>
    </tr>
    <tr>
        <td width="3%">&nbsp;</td>
        <td width="15%" align="right">开始时间： </td>
        <td width="28%">
            <input class="input_style1" name="startDate" type="text" style="width:70%" readonly="true" value="<%=StrUtil.nullToString(etsWorkorder.getStartDate())%>"><font color="red">*</font>
            <img src="/images/calendar.gif" width="16" height="15" alt="选择时间" id="calendar1"  onClick="getDateTime('mainFrm.startDate');">
        </td>
        <td width="15%" align="right">实施周期(天): </td>
        <td width="28%" align="left"><input name="implementDays" class="input_style1" style="width:70%" type="text" value="<%=etsWorkorder.getImplementDays()%>"> <font color="red">*</font> </td>
        <td width="11%" align="center"></td>
    </tr>
    <tr bgcolor="#BDD3FF">
        <td colspan="6"><span>&gt;&gt;&gt;地点信息查询</span></td>
    </tr>
    <tr>
        <td width="3%">&nbsp;</td>
        <td width="15%" align="right">所属工程：</td>
        <td width="28%" align="left"><input style="width:70%" name="prjName" value="<%=etsObject.getProjectName()%>" readonly="true" class="input_style2" > </td>
        <td width="15%" align="right">地点编号：</td>
        <td width="28%" align="left"><input type="text" class="input_style1" name="workorderObjectCode" style="width:70%" value="<%=etsObject.getWorkorderObjectCode()%>"></td>
        <td width="11%" align="center"></td>
    </tr>
    <tr>
        <td width="3%">&nbsp;</td>
        <td width="15%" align="right">地点名称：</td>
        <td width="28%" align="left"><input type="text" class="input_style1" name="workorderObjectName" style="width:70%" value="<%=etsObject.getWorkorderObjectName()%>"></td>
        <td width="15%" align="right">地点位置：</td>
        <td width="28%" align="left"><input type="text" class="input_style1" name="workorderObjectLocation" style="width:70%" value="<%=etsObject.getWorkorderObjectLocation()%>"></td>
        <td width="11%" align="center"><img src="/images/eam_images/search.jpg" alt="查询"  onClick="do_query();"></td>
    </tr>
</table>

<div style="left:1px;width:100%;position:absolute;top:184px;overflow-y:scroll" class="crystalScroll" id="headDiv">
    <table class="headerTable" border=1 style="width:100%" cellpadding="0" cellspacing="0">
       <tr>
            <td height="22" width="4%" align="center"><input type="checkbox" class="headCheckbox" name="ctlBox" onclick="checkAll(this.name,'objNos_n')"></td>
            <td height="22" width="12%" align="center">地点编号</td>
            <td height="22" width="24%" align="center">地点名称</td>
            <td height="22" width="25%" align="center">地点位置</td>
            <td height="22" width="25%" align="center">所属工程</td>
            <td height="22" width="10%" align="center">接单部门</td>
        </tr>
    </table>
</div>

<%

	if(rows != null && !rows.isEmpty()){
%>
<div style="overflow-y:scroll;height:362;width:100%;;position:absolute;top:207px;left:0px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#9FD6FF" >
<%
        Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean canDistribute=row.getStrValue("CAN_DISTRIBUTE").equalsIgnoreCase("Y");
            if(canDistribute){
%>
			<tr class="dataTR" onclick="executeClick(this);">
				<td style="word-wrap:break-word" height="22" width="4%" align="center" class=""><input type="checkBox" name="objNos_n"  value="<%=row.getValue("WORKORDER_OBJECT_NO")%>"></td>
<%}else{%>
            <tr class="dataTR" title="该地点有未完成巡检工单,请尽快完成！">
				<td style="word-wrap:break-word" height="22" width="4%" align="center" class=""><input  disabled="true" type="checkBox" name="objNos_n"  value="<%=row.getValue("WORKORDER_OBJECT_NO")%>"></td>
    <%}%>
                <td style="word-wrap:break-word" height="22" width="12%" ><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
				<td style="word-wrap:break-word" height="22" width="24%" ><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
				<td style="word-wrap:break-word" height="22" width="25%" ><%=row.getValue("WORKORDER_OBJECT_LOCATION")%></td>
				<td style="word-wrap:break-word" height="22" width="25%" ><%=row.getValue("PROJECT_NAME")%></td>
				<td style="word-wrap:break-word" height="22" width="10%" ><%=etsWorkorder.getGroupName()%></td>
			</tr>
<%
		}
%>
    </table>
</div>

<%
    }
%>
<%=WebConstant.WAIT_TIP_MSG%>
</form>
</body>
</html>

<script type="text/javascript">
function do_query() {
    if(document.mainFrm.workorderObjectCode.value==""&&document.mainFrm.workorderObjectName.value==""&&document.mainFrm.workorderObjectLocation.value==""){
       alert("请输入查询条件");
        return;
    }
    mainFrm.act.value = "myQuery";
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.submit();
}
function do_Cancel() {
    window.close();
}
function do_GenOrders() {
    var fieldNames = "implementDays;startDate";
    var fieldLabels = "实施周期;开始时间";
    var emptyValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
    fieldNames = "implementDays";
    fieldLabels = "实施周期";
    var numberValid = formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE);
    var checkedCount = getCheckedBoxCount("objNos_n");
    var isValid = emptyValid && numberValid;
    if (isValid) {
        if (checkedCount < 1){
            alert("请至少选择一条纪录!");
        }else {
        mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        mainFrm.submit();}
    } else {
        return;
    }
}
</script>


