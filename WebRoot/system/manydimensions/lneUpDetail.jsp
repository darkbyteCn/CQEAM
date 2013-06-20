<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <title>逻辑网络元素属性新增</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body onload="oo();">
 <jsp:include page="/message/MessageProcess"/>
<%
	AmsElementMatchDTO aemDTO = (AmsElementMatchDTO) request.getAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO);
%>

<form name="mainFrm" method="POST">
 	<script language="javascript">
 		printTitleBar("基本信息维护>>逻辑网络元素属性维护--修改");
	 	var ArrAction0 = new Array(true, "保存", "action_save.gif", "保存", "do_Save()");
	    var ArrAction1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "history.go(-1);");
	    var ArrActions = new Array(ArrAction0,ArrAction1);
	    var ArrSinoViews = new Array();
	    printToolBar();
    </script>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:15%">   
	    <table border="0"  width="100%" id="table1" align = "center">
	    <%
		RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
		boolean hasData = (rows != null && !rows.isEmpty());
		if (hasData) {
				Row row = null;
				for (int i = 0; i < rows.getSize(); i++) {
					row = rows.getRow(i);
		%>
	    	<tr>
	            <td width="30%" align="right" height="22">网络专业1：&nbsp;</td>
	             <td width="50%" align="left" height="22">
	            	<input type="text" name = "netCategory1" size = "50" class="input_style1" value="<%=row.getValue("NET_CATEGORY1")%>">
	            </td>
	            <td><font color="red">*</font></td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">网络专业2：&nbsp;</td>
	            <td width="50%" align="left" height="22">
	              <input type="text" name="netCategory2" class="input_style1" size="50" value="<%=row.getValue("NET_CATEGORY2")%>">
	            </td>
	            <td><font color="red">*</font></td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">网元编码：&nbsp;</td>
	             <td width="50%" align="left" height="22">
	            	<input type="text" name = "netUnitCode" size = "50" class="input_style1" onblur="do_ValidateCode();" value="<%=row.getValue("NET_UNIT_CODE")%>">
	            </td>
	            <td><font color="red">*</font></td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">逻辑网络元素：&nbsp;</td>
	            <td width="50%" align="left" height="22"><input type="text" name="logNetEle" class="input_style1" size="50" value="<%=row.getValue("LOG_NET_ELE")%>"></td>
	            <td><font color="red">*</font></td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">英文缩写：&nbsp;</td>
	            <td width="50%" align="left" height="22"><input type="text" class="input_style1" name="engAb" size="50" value="<%=row.getValue("ENG_AB")%>"></td>
	        </tr>
	        <tr>
	        	<td align="right" width="15%">成本属性：&nbsp;</td>
	        	<input id="costType1" name="costType1" type="hidden" value="<%=row.getValue("COST_TYPE")%>"/>
				<td><select name="costType" id="costType" >
					<option value="">-请选择-</option>
					<option value="偏短期增量成本">偏短期增量成本</option>
					<option value="偏长期增量成本">偏长期增量成本</option>
					<option value="偏固定成本">偏固定成本</option>
					</select>
				</td>
	        </tr>
	        <tr>
	        	<input type="hidden" name = "amsLneId" value="<%=row.getValue("AMS_LNE_ID")%>">
	        	</tr>
	        <%
			    }
		}
	%>
	     </table>
		<input type="hidden" name="act" value="">
	</div>
</form>
</body>
</html>
<script>
function do_Save() {
	var fieldNames = "netCategory1;netCategory2;netUnitCode;logNetEle";
        var fieldLabels = "网络专业1;网络专业2;网元编码;逻辑网络元素";
        if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
            with (mainFrm) {
				mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
				mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.LneServlet";
				mainFrm.submit();
            }
   	 	}
}
var xmlHttp = null;

function do_ValidateCode() {
	if(mainFrm.netUnitCode.value != ""){
		var url = "/servlet/com.sino.ams.system.manydimensions.servlet.LneServlet";
		url += "?act=UPDATE_ACTION"
		url += "&netUnitCode=" + mainFrm.netUnitCode.value;
		do_ProcessSimpleAjax(url, null);
	}
}
function oo()
{
	if(document.getElementById("costType1").value!=""){
		document.getElementById("costType").value=document.getElementById("costType1").value
	}
}

function do_ProcessResponse(responseContent){
	with(document){
		if(responseContent == "Y"){
			alert("您所输入的网元编码已存在！请重新输入！");
			mainFrm.netUnitCode.value = "";
			mainFrm.netUnitCode.focus();
		} 
	}
}
</script>

