<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.workorderDefine.dto.WorkorderDefineDTO" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <title>巡检自定义</title>
</head>

<body leftmargin="1" topmargin="0">
<%
	RequestParser parser = new RequestParser();
	parser.transData(request);
	WorkorderDefineDTO dto = (WorkorderDefineDTO) parser
			.getAttribute(QueryConstant.QUERY_DTO);
%>

<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet">
	<script type="text/javascript">
	    printTitleBar("巡检自定义编辑")
	</script>
    <jsp:include page="/message/MessageProcess"/>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="workorderDefineId" value="<%=dto.getWorkorderDefineId()%>">
    <table width="100%" border=0 cellpadding="2" cellspacing="0" class="editTable">
    <table width="80%" bordercolor="#666666" >
        <tr>
            <td width="30%" align="right">公司名称：</td>
            <td width="60%"><select class='select_style1' name="organizationId" disabled style="width:98%"><%=dto.getOrganizationName()%></select><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="30%" align="right">地点专业：</td>
            <td width="60%"><select class='select_style1' name="objectCategory" style="width:98%"><%=dto.getObjectCategoryOption()%></select><font color="red">*</font></td>
        </tr>
        <tr>
        	<td width="30%" align="right">市：</td>
            <td width="60%"><select class='select_style1' name="city" onchange="do_ChangeCounty(this);"  style="width:98%" ><%=dto.getCityOption()%></select><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="30%" align="right">区/县：</td>
            <td width="60%"><select class='select_style1' name="county" style="width:98%"  ><%=dto.getCountyOption()%></select><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="30%" align="right">成本中心(或区域)：</td>
            <td width="60%"><select class='select_style1' name="costCenterCode" style="width:98%"  ><%=dto.getCostCenterName()%></select></td>
        </tr>
        <tr>
	        <td width="30%" align="right">辅助信息：</td>
	        <td width="60%"><input name="auxiliaryInfo" type="text" id="auxiliaryInfo" style="width:98%" value="<%=dto.getAuxiliaryInfo()%>" class='input_style1'></td>
        </tr>
        <tr>
            <td width="30%" align="right">执行人：</td>
            <%-- <td width="60%"><select class='select_style1' name="implementBy" style="width:98%"><%=dto.getImplementByOption()%></select><font color="red">*</font></td> --%>
	        <td width="60%">
	        	<input style="width:93%" type="text" name="implementByName" class="input_style1" value="<%=dto.getImplementByName()%>">
	        		<a href="#" onclick="showUser(1)" title="点击选择执行人" class="linka"><font color="red">[…]</font></a>
	        	<input type="hidden" name="implementBy" value="<%=dto.getImplementBy()%>">
	        </td>
        </tr>
        <tr>
            <td width="30%" align="right">归档人：</td>
            <%-- <td width="60%"><select class='select_style1' name="checkoverBy" style="width:98%"><%=dto.getCheckoverByOption()%></select><font color="red">*</font></td> --%>
	        <td width="60%">
	        	<input style="width:93%" type="text" name="checkoverByName" class="input_style1" value="<%=dto.getCheckoverByName()%>">
	        		<a href="#" onclick="showUser(2)" title="点击选择归档人" class="linka"><font color="red">[…]</font></a>
				<input type="hidden" name="checkoverBy" value="<%=dto.getCheckoverBy()%>">
	        </td>
        </tr>
        <tr>
            <td width="30%" align="right">巡检周期：</td>
            <td width="60%"><select class='select_style1' name="workorderCycle" style="width:98%"  ><%=dto.getWorkorderCycleOption()%></select><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="30%" align="right">巡检自动创建触发时间：</td>
            <td width="60%">
	            <select class='select_style1' name="workorderTiggerTime" style="width:98%">
				<%
					for (int i = 1; i < 31; i++) {
				%>
				<option value="<%=i%>" <%=i==dto.getWorkorderTiggerTime() ? "selected" : ""%>><%=i %></option>
				<%
					}
				%>
				</select>号
			</td>
        </tr>
        <tr>
            <td width="30%" align="right">是否有效：</td>
            <td width="60%">
            	<select class='select_style1' name="enabled" style="width:98%"  >
            		<option value="Y">是</option>
					<option value="N" <%if (dto.getEnabled().equals("N")) {%>selected <%}%>>否</option>
            	</select>
           	</td>
        </tr>
    </table>
    <table align="center">
        <tr>
            <td align="center" height="22" colspan="2">
            	<img src="/images/eam_images/now_execute.jpg" onClick="do_nowExecute();return false;">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/save.jpg" onClick="do_Save();return false;">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/cancel.jpg" onClick="do_Back();return false;">
            </td>
        </tr>
    </table>
  </table>
</form>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">

	function do_nowExecute() {
	    var fieldNames = "objectCategory;city;county;implementBy;checkoverBy;workorderCycle";
		var fieldLabels = "地点专业;市;所属区县;执行人;归档人;巡检周期";
		if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
			mainFrm.act.value = "NOW_EXECUTE";
            mainFrm.organizationId.disabled = false;
       		mainFrm.submit();
		}
	}
	
    function do_Save() {
	    var fieldNames = "objectCategory;city;county;implementBy;checkoverBy;workorderCycle";
		var fieldLabels = "地点专业;市;所属区县;执行人;归档人;巡检周期";
		if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
			mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            mainFrm.organizationId.disabled = false;
       		mainFrm.submit();
	    }
    }
    
    function do_Back() {
	    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		mainFrm.action = "/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet";
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		mainFrm.submit();
	}
	
	function do_ChangeCounty(obj) {
		var url = "/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet";
		url += "?act=CHANGE_COUNTYS" ;
		url += "&city=" + obj.value;
		do_AddressProcessSimpleAjax(url, null);
	}
	
	/**
	 * 将返回的列表加入区县下拉框，
	 * 修改完成。
	 */
	function do_AddressProcessResponse(responseContent){
		mainFrm.county.outerHTML = "<select class=\"select_style1\" style=\"width:98%\" name=\"county\">" + responseContent + "</select>";
	}
	
	function showUser() {
        var lookUser = "LOOK_UP_USER";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }

	function showUser( selCategory ){
		var lookUpName = "LOOK_UP_USER";
		var dialogWidth = 44;
		var dialogHeight = 29;
		var organizationId = mainFrm.organizationId.value; 
		var userPara = "organizationId=" + organizationId ;
		
		var uId = "implementBy";
		var uName = "implementByName";
		if(selCategory == 1){
			uId = "implementBy";
			uName = "implementByName";
		} else if(selCategory == 2){
			uId = "checkoverBy";
			uName = "checkoverByName";
		}
		
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		 
		if (objs) {
			var obj = objs[0];
			document.getElementById(uId).value = obj["userId"];
			document.getElementById(uName).value = obj["userName"];
		} else {
			document.getElementById(uId).value = "";
			document.getElementById(uName).value = "";
		} 
	}

	
</script>
