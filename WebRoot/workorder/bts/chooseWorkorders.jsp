<%--
  User: zhoujs
  Date: 2007-9-22
  Time: 13:56:07
  Function: ѡ��Ҫ��������(�ɶ�ѡ)
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
    <title>ѡ�񹤵��ص�ҳ��</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<meta charset="GBK"/>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
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
        var ArrAction1 = new Array(true, "ȡ��", "del.gif", "ȡ��", "do_Cancel");
        var ArrAction2 = new Array(true, "���ɹ���", "action_sign.gif", "���ɹ���", "do_GenOrders");
        var ArrAction3 = new Array(false, "�½���վ", "act_refresh.gif", "���ɹ���", "newBS");
        var ArrAction4 = new Array(true, "��ѯ", "action_view.gif", "��ѯ", "do_query");
        var ArrActions = new Array(ArrAction1, ArrAction3, ArrAction2, ArrAction4);
        var ArrSinoViews = new Array();
        printTitleBar("��ӹ���");
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
        <td colspan="8" width="100%"><span>&gt;&gt;&gt;����������Ϣ</span></td>
    </tr>
    <tr>
        <td width="10%" align="right">�������ͣ�</td>
        <td width="15%" align="left"><input type="text" name="workorderTypeDesc" style="width:70%" value="<%=etsWorkorder.getWorkorderTypeDesc()%>" class="input_style2"></td>
        <td width="10%" align="right">�ӵ����ţ�</td>
        <td width="15%"><input name="groupName" type="text" style="width:70%" value="<%=etsWorkorder.getGroupName()%>" class="input_style2"></td>
        <td width="10%" align="right">��ʼʱ�䣺</td>
        <td width="15%">
            <input class="input_style2" name="startDate" type="text" style="width:70%" readonly="true" value="<%=StrUtil.nullToString(etsWorkorder.getStartDate())%>">
            <img src="/images/calendar.gif" width="16" height="15" alt="ѡ��ʱ��" id="calendar1"  onClick="getDateTime('mainFrm.startDate');">
        </td>
        <td width="10%" align="right">ʵʩ����(��)��</td>
        <td width="15%" align="left"><input name="implementDays" onkeydown="intOnlyOnkeyDown(this.value);" class="inputNoEmpty" style="width:70%" type="text" value="<%=etsWorkorder.getImplementDays()%>"><font color="red">*</font>  </td>
    </tr>
    <tr bgcolor="#BDD3FF">	
        <td colspan="8" width="100%"><span>&gt;&gt;&gt;�ص���Ϣ��ѯ</span></td>
    </tr>
    <tr>
    <%
    	if(!workorderTypeDesc.equals("Ѳ��")){
    %>
    	<td width="10%" align="right">�������̣�</td>
        <td width="15%" align="left"><input style="width:70%" name="prjName" value="<%=etsObject.getProjectName()%>" readonly="true" class="input_style1" > </td>
        <td width="10%" align="right">�ص��ţ�</td>
        <td width="15%" align="left"><input type="text" class="input_style1" name="workorderObjectCode" style="width:70%" value="<%=etsObject.getWorkorderObjectCode()%>"></td>
        <td width="10%" align="right">�ص����ƣ�</td>
        <td width="15%" align="left"><input type="text" name="workorderObjectName" class="input_style1" style="width:70%" value="<%=etsObject.getWorkorderObjectName()%>"></td>
        <td width="10%" align="right">�豸״̬��</td>
        <td width="15%" align="left"><select style="width:70%" name="itemStatus"><option value="">ȫ��</option><option value="TO_ASSETS">��ת��</option></select></td>
    </tr>
    <tr>
    	<td align=right width="7%" height="18">�ɱ����ģ�</td>
        <td width="15%" height="18">
        <input type="text" name="costCenterName" class="input_style1" readonly style="width:100%;cursor:hand" value="<%=etsObject.getCostCenterName()%>" title="���ѡ�����ġ��ɱ����ġ�" onClick="chooseCostCenter()" size="20"> </td> 
        <%-- <a href="#" onclick="chooseCostCenter();" title="���ѡ��ɱ�����"><font color="blue"><%=WebConstant.CHOOSE_DICT%></font></a> --%>
    <%	}else{
    %>
    		<%--    <td width="10%" align="right">�������̣�</td>
        <td width="15%" align="left"><input style="width:70%" name="prjName" value="<%=etsObject.getProjectName()%>" readonly="true" class="input_style1" > </td>--%>
        <td width="10%" align="right">�ص��ţ�</td>
        <td width="15%" align="left"><input type="text" class="input_style1" name="workorderObjectCode" style="width:70%" value="<%=etsObject.getWorkorderObjectCode()%>"></td>
        <td width="10%" align="right">�ص����ƣ�</td>
        <td width="15%" align="left"><input type="text" name="workorderObjectName" class="input_style1" style="width:70%" value="<%=etsObject.getWorkorderObjectName()%>"></td>
        <td width="10%" align="right">�豸״̬��</td>
        <td width="15%" align="left"><select style="width:70%" name="itemStatus"><option value="">ȫ��</option><option value="TO_ASSETS">��ת��</option></select></td>
        <td align=right width="7%" height="18">�ɱ����ģ�</td>
        <td width="15%" height="18">
        <input type="text" name="costCenterName" class="input_style1" readonly style="width:100%;cursor:hand" value="<%=etsObject.getCostCenterName()%>" title="���ѡ�����ġ��ɱ����ġ�" onClick="chooseCostCenter()" size="20"> </td> 
        <%-- <a href="#" onclick="chooseCostCenter();" title="���ѡ��ɱ�����"><font color="blue"><%=WebConstant.CHOOSE_DICT%></font></a> --%>
    </tr>
    <tr>
    <%	}
     %>

        <td width="10%" align="right">�������ƣ�</td>
        <td width="15%" align="left">
	        <select name="countyCodeShi" onchange="jilian(this.value);" style="width:70%">
	        	<%=countName%>
	        </select> 
        </td>
        <td width="10%" align="right">�������ƣ�</td>
        <td width="15%" align="left"><select id="selectData" name="countyCodeXian" style="width:70%"><%=countOptions%></select></td>
        <td width="10%"></td>
        <td width="15%"></td>
    </tr>
</table>  
<input type="hidden" name="excel" value="">  
</form>

<div id="buttonDiv" style="position:absolute;top:155px;left:1px;width:100%">
   <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel�����ѯ"  onClick="do_excel();">  
   <span id="warn"></span> 
</div>

<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:180px;left:1px;width:100%">
    <table id="headTable" class="headerTable" border=1 style="width:100%" cellpadding="0" cellspacing="0">
       <tr>
            <td height="22" width="3%" align="center"><input type="checkbox" class="headCheckbox" name="ctlBox" onclick="checkAll(this.name,'objNos_n')"></td>
            <td height="22" width="16%" align="center">�ص���</td>
            <td height="22" width="50%" align="center">�ص�����</td>
            <td height="22" width="16%" align="center">��������</td>
            <td height="22" width="15%" align="center">�ӵ�����</td>
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
            <tr class="dataTR" title="�õص���δ���Ѳ�칤��,�뾡����ɣ�">
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
       alert("�������ѯ����");
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
        alert("��ʼʱ�����ѡ�񣬲���Ϊ�ա�");
        return;
    }

    var implementDays = mainFrm.implementDays.value;
    if(isEmpty(implementDays)){
        alert("ʵʩ���ڱ�����д������Ϊ�ա�");
        return;
    }
    if(!isPositiveInteger(implementDays)){
        mainFrm.implementDays.value = "";
        mainFrm.implementDays.focus();
        alert("ʵʩ���ڱ�����д������");
        return;
    }
    var chkObj = mainFrm.$$$CHECK_BOX_HIDDEN$$$;
    if(!chkObj || chkObj.value == ""){
        alert("��δѡ��ص㣬�������ɹ���");
        return;
    }
    mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
    mainFrm.submit();
    var hintDiv = document.getElementById("$$$waitTipMsg$$$");
    var divHTML = hintDiv.innerHTML;
    divHTML = divHTML.replace("�����������ݣ����Ժ�", "�������ɹ��������Ժ�");
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


