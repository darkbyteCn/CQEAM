<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.inv.storeman.bean.LookUpInvConstant" %>
<%@ page import="com.sino.nm.ams.instrument.constant.InstrumentLookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/nm/instrument/headerInclude.html"%>

<%--
  Created by MyEclipse.
  User: yushibo
  Date: 2008-12-30
  Time: 15:55:20
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>仪器仪表检修－登记</title>
</head>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	Row row = null;
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    AmsInstrumentEamYbChkMaintainDTO dto = (AmsInstrumentEamYbChkMaintainDTO) request.getAttribute(QueryConstant.QUERY_DTO); //针对Servlet里的dto.setXXX()方法写的
    //String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
%>
<body onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkMaintainServlet" name="mainFrm" method="post">
    <script type="text/javascript">
    	printTitleBar("仪器仪表检修－登记")
	</script>
	<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
        	<!--  
            <td width="8%" align="right">公司：</td>
            <td width="15%"><select style="width:120px;" name="organizationId"><%--=organizationId--%></select></td>
            </td>
            -->
            <td width="10%" align="right">目录编号：</td>
          	<td width="15%">
          	<input type="text" size="25" name="itemCategory2" value="<%=dto.getItemCategory2()%>"><a href="#" title="点击选择目录编号" class="linka" onclick="do_SelectItemCategory2();">[…]</a>
          	</td>   
            <td width="10%" align="right">条码编号：</td>
	        <td width="15%">
	        <input type="text" size="25" name="barcode1" value="<%=dto.getBarcode1()%>">
	        <!-- <a href="#" title="点击选择条码编号" class="linka" onclick="do_SelectBarcode();">[…]</a> -->
	        </td>
	        <td width="10%" align="right">任务：</td>
	        <td width="15%">
	        <input type="text" size="25" name="taskName" value="<%=dto.getTaskName()%>" class="noEmptyInput"><a href="#" title="点击选择任务批号" class="linka" onclick="do_SelectTaskId();">[…]</a>
	        <input type="hidden" name="taskId" value="<%=dto.getTaskId() %>">
	        </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <table class="queryHeadBg" border="0" width="100%">
    <tr>
    <td align="right" width="70%">
     批量设置：
	<input type="checkbox" name="allChkResult" id="allChkResult"><label for="allChkResult">检修结果</label>
	<input type="checkbox" name="allChkUser" id="allChkUser"><label for="allStartTime">检修人</label>
	<input type="checkbox" name="allChkDay" id="allChkDay"><label for="allChkDay">检修日期</label>
	</td>
	<td width="30%" align="center">
	<img src="/images/button/query.gif" alt="查询检修登记情况" onClick="do_Search(); return false;">
    <img src="/images/button/save.gif" alt="保存检修登记情况" onClick="do_Save(); return false;">
	</td>
    </tr>
    </table>
    <div id="headDiv">
    <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="10%" align="center">条码编号</td>
            <td width="10%" align="center">品名</td>
            <td width="15%" align="center">规格型号</td>
            <td width="10%" align="center">检修结果</td>
            <td width="8%" align="center">备注</td>
            <td width="10%" align="center">检修人</td>
            <td width="8%" align="center">检修日期</td>
            <td style="display:none">隐藏域所在列</td>
        </tr>
    </table>
</div>

    <div style="overflow-y:scroll;left:0px;width:100%;height:300px">
     
        <table width="100%" border="1" bordercolor="#666666" id="dataTable">
<%
	if (hasData) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr class="dataTR">
                <td height="22" width="10%"><input type="text" name="barcode" value="<%=row.getValue("BARCODE")%>" class="finput" readonly></td>
                <td height="22" width="10%"><input type="text" value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly></td>
                <td height="22" width="15%"><input type="text" value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly></td>
                <td height="22" width="10%"><input type="text" name="checkStatusName" id="checkStatusName<%=i %>" value="<%=row.getValue("CHECK_STATUS_NAME")%>" class="finputNoEmpty" readonly style="cursor: hand" onclick="do_SelectChkResult(this);" title="点击选择或更改本检修结果"></td>
                <td height="22" width="8%"><input type="text" name="remark" value="<%=row.getValue("REMARK")%>" class="finput"></td>
                <td height="22" width="10%"><input type="text" name="username" id="username<%=i %>" value="<%=row.getValue("USERNAME")%>" class="finputNoEmpty" readonly style="cursor: hand" onclick="do_SelectChkUser(this);" title="点击选择或更改本检修人"></td>
                <td height="22" width="8%" align="center"><input type="text" name="checkDate" id="checkDate<%=i %>" value="<%=row.getValue("CHECK_DATE")%>" class="finputNoEmpty3" readonly style="cursor: hand" onclick="gfPop.fPopCalendar(checkDate<%=i%>)" title="点击选择或更改本检修日期" onblur="do_SetLineDays(this)"></td>
                <td style="display:none">
                	<input type="hidden" name="checkUserId" id="checkUserId<%=i %>" value="<%=row.getValue("CHECK_USER_ID")%>" >
                	<input type="hidden" name="checkStatus" id="checkStatus<%=i %>" value="<%=row.getValue("CHECK_STATUS")%>" >
                	<input type="hidden" name="status" id="status<%=i %>" value="<%=row.getValue("ITEM_STATUS") %>">
                </td>
            </tr>
<%
		}     
	}
%>
        </table>
    </div>
</form>
<%
	if(hasData){
%>
	<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}	
%>
<%=WebConstant.WAIT_TIP_MSG%>

<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>

</body>
</html>
<script type="text/javascript">
function do_Search() {
	if(mainFrm.taskId.value == "" || mainFrm.taskId.value == null){
		alert("请先选择任务!");
		return false;
	} else {
	    with (mainFrm) {
	        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	        act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	        submit();
	    }
    }
}

var effect = 0; //防止在没刷新页面之前重复提交
function do_Save() {
	if(effect == 0) {
		if(mainFrm.taskId.value == "" || mainFrm.taskId.value == null) {
			alert("请先选择任务!");
			return false;
		} else {
		    with (mainFrm) {
			    act.value = "<%=WebActionConstant.SAVE_ACTION%>";
			    submit();
		    }
	    }
    } else {
    	alert("请耐心等候，程序正在执行，不要重复提交!");
    }
    
    effect = 1; //防止在没刷新页面之前重复提交
}

//选择批号
function do_SelectTaskId() {
	var url="/servlet/com.sino.nm.ams.instrument.bean.InstrumentLookUpServlet?lookUpName=<%=InstrumentLookUpConstant.LOOK_UP_TASK_ID%>";
	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
	var taskIds = window.showModalDialog(url, null, popscript);
	if(taskIds) {
		mainFrm.taskId.value = taskIds[0].taskId;
		mainFrm.taskName.value = taskIds[0].taskName;
	}
}

//查找目录编号
function do_SelectItemCategory2() {
	var lookUpItemCategory2 = "<%=LookUpConstant.LOOK_UP_ITEM_CATEGORY2%>";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var itemCategory2s = getLookUpValues(lookUpItemCategory2, dialogWidth, dialogHeight);
	if(itemCategory2s){
		/*
		var itemCategory2 = null;
		for(var i=0; i<itemCategory2s.length; i++){
			itemCategory2 = itemCategory2s[i];
			dto2Frm(itemCategory2, "mainFrm");
		}
		*/
		document.forms[0].itemCategory2.value = itemCategory2s[0].catalogCode;
	}
}

//选择条码编号
function do_SelectBarcode(){
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_BARCODE%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].barcode.value = vendorNames[0].barcodeFlag;
       	//document.forms[0].userName.value = vendorNames[0].userName;
   	}
}

//选择检修日期
function do_SetLineDays(lineBox){
	if(!mainFrm.allChkDay.checked){
		return
	}
	var id = lineBox.id;
	var lineValue = lineBox.value;
	var fields = document.getElementsByName("checkDate");
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	for(var i = 0; i < fields.length; i++){
		if(fields[i].id == id){
			continue;
		}
		fields[i].value = lineValue;
	}
}

/**
  * 功能：选择检修结果
 */
function do_SelectChkResult(lineBox){
	var url="/servlet/com.sino.nm.ams.instrument.bean.InstrumentLookUpServlet?lookUpName=<%=InstrumentLookUpConstant.LOOK_UP_CHECK_RESULT%>";
	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
	var objs = window.showModalDialog(url, null, popscript);
	var textId = lineBox.id;
	var textName = lineBox.name;
	var idNumber = textId.substring(textName.length);
	var checkedProp = mainFrm.allChkResult.checked;
	var checkStatusVar = "checkStatus";
	var checkStatusNameVar = "checkStatusName";
	if(!checkedProp){
		if (objs) {
			var obj = objs[0];
			document.getElementById(checkStatusVar + idNumber).value = obj["checkStatus"];
			lineBox.value = obj["checkStatusName"];
		} else {
			document.getElementById(checkStatusVar + idNumber).value = "";
			lineBox.value = "";
		}
	} else {
		var obj = null;
		var emptyData = false;
		if (objs) {
			obj = objs[0];
		} else {
			emptyData = true;
		}
		var checkStatusNames = document.getElementsByName(checkStatusNameVar);
		var checkStatuss = document.getElementsByName(checkStatusVar);
		var dataCount = checkStatuss.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		
		for(var i = 0; i < dataCount; i++){
			if(emptyData){
				checkStatusNames[i].value = "";
				checkStatuss[i].value = "";
			} else {
				checkStatusNames[i].value = obj["checkStatusName"];
				checkStatuss[i].value = obj["checkStatus"];
			}
		}
	}
}

/**
  * 功能：选择检修人
 */
function do_SelectChkUser(lineBox){
	var url="/servlet/com.sino.nm.ams.instrument.bean.InstrumentLookUpServlet?lookUpName=<%=InstrumentLookUpConstant.LOOK_UP_CHECK_USER%>";
	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
	var objs = window.showModalDialog(url, null, popscript);
	var textId = lineBox.id;
	var textName = lineBox.name;
	var idNumber = textId.substring(textName.length);
	var checkedProp = mainFrm.allChkUser.checked;
	var userIdName = "checkUserId";
	var userName = "username";
	if(!checkedProp){
		if (objs) {
			var obj = objs[0];
			document.getElementById(userIdName + idNumber).value = obj["userId"];
			lineBox.value = obj["username"];
		} else {
			document.getElementById(userIdName + idNumber).value = "";
			lineBox.value = "";
		}
	} else {
		var obj = null;
		var emptyData = false;
		if (objs) {
			obj = objs[0];
		} else {
			emptyData = true;
		}
		var userNames = document.getElementsByName(userName);
		var userIds = document.getElementsByName(userIdName);
		var dataCount = userIds.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		for(var i = 0; i < dataCount; i++){
			if(emptyData){
				userNames[i].value = "";
				userIds[i].value = "";
			} else {
				userNames[i].value = obj["username"];
				userIds[i].value = obj["userId"];
			}
		}
	}
}
</script>