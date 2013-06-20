<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.sinoflow.base.util.FlowTaskTool" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<%@ page import="com.sino.sinoflow.framework.resource.dto.SfResDefineDTO" %>
<html>
	<head>
	    <title>资源详细信息</title>
	    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
        <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">	
		<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
		<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	    <script type="text/javascript" src="/WebLibary/js/SinoToolBarVar.js"></script>
	 
	  <script type="text/javascript">
        printTitleBar("系统栏目维护");
      </script>
	</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%
    SfResDefineDTO resource = (SfResDefineDTO) request.getAttribute(WebAttrConstant.RES_DATA);
%>
<div>
<form name="mainFrm" method="POST">
    <table border="0" width="100%" id="table3" class="detailTb">
        <tr>
            <td width="12%" align="right" height="22">栏目名称：</td>
            <td width="78%" align="left" height="22">
            	<input type="text" name="resName"  class="input_style1" style="width:100%" value="<%=FlowTaskTool.escapeHTML(resource.getResName())%>">
            </td>
            <td width="10%" align="left" height="22">&nbsp;<font color="red">*</font>　</td>
        </tr>
        <tr>
            <td height="22" align="right">栏目 URL：</td>
            <td height="22"><input type="text" name="resUrl"  class="input_style1" style="width:100%"  value="<%=resource.getResUrl()%>"></td>
            <td height="22">&nbsp;<font color="red">*</font>　</td>
        </tr>
        <tr>
            <td height="22" align="right">开发人员：</td>
            <td height="22"><input type="text"  class="input_style1" style="width:100%" name="principal" value="<%=resource.getPrincipal()%>"></td>
            <td height="22"></td>
        </tr>
        <tr>
            <td height="60" align="right">业务描述：</td>
            <td height="60">
            <textarea  name="businessDesc" class="input_style1" style="width:100%;height:100%"><%=resource.getBusinessDesc()%></textarea></td>
            <td height="60">&nbsp;<font color="red">*</font></td>
        </tr>
<%
	String yesChecked = "";
	String noChecked = "checked";
	String disableProp = "disabled";
	if(resource.getIsPopup().equals("Y")){
		yesChecked = "checked";
		noChecked = "";
		disableProp = "";
	}
%>
        <tr>
            <td height="22" align="right">是否弹出：</td>
            <td height="22">
                <input type="radio" name="isPopup" <%=yesChecked%> value="Y" onclick="do_Change(this)" id="isPopup_Y" checked><label for="isPopup_Y">是</label>
                <input type="radio" name="isPopup" <%=noChecked%> value="N" onclick="do_Change(this)" id="isPopup_N"><label for="isPopup_N">否</label>
            </td>
            <td height="22"></td>
        </tr>
        <tr>
            <td height="80" align="right">弹出脚本：</td>
            <td height="80"><textarea rows="2" name="popscript" class="input_style1" cols="20"  style="width:100%;height:100%" "<%=disableProp%>"><%=resource.getPopscript()%></textarea></td>
            <td height="80">　</td>
        </tr>
        <tr>
            <td height="22" align="right">排序编号：</td>
            <td height="22"><input type="text" name="sortNo"  class="input_style1" style="width:100%" value="<%=resource.getSortNo()%>" onblur="do_Check(this);"></td>
            <td height="22">&nbsp;<font color="red">*</font>　</td>
        </tr>
        <tr>
            <td height="22" align="right">上级栏目：</td>
            <td height="22">
				<select name="resParId"  class="select_style1" style="width:100%">
					<%=request.getAttribute(WebAttrConstant.RESOURCE_OPTION)%>
				</select>
            </td>
            <td height="22">&nbsp;<font color="red">*</font></td>
        </tr>
<%
	yesChecked = "checked";
	noChecked = "";
	if(resource.getEnabled().equals("N")){
		yesChecked = "";
		noChecked = "checked";
	}
%>        
        <tr>
            <td height="22" align="right">是否有效：</td>
            <td height="22">
            	<input type="radio" name="enabled" value="Y" id="enabled_Y" <%=yesChecked%>><label for="enabled_Y">是</label>
                <input type="radio" name="enabled" value="N" id="enabled_N" <%=noChecked%>><label for="enabled_Y">否</label>
            </td>
            <td height="22"></td>
        </tr>
<%
	yesChecked = "checked";
	noChecked = "";
	if(resource.getVisible().equals("N")){
		yesChecked = "";
		noChecked = "checked";
	}
	
%>        
        <tr>
            <td height="22" align="right">是否可见：</td>
            <td height="22">
            	<input type="radio" name="visible" value="Y" id="visible_Y" <%=yesChecked%> checked><label for="visible_Y">是</label>
                <input type="radio" name="visible" value="N" id="visible_N" <%=noChecked%>><label for="visible_Y">否</label>
            </td>
            <td height="22" align="right"></td>
        </tr>   
        <tr>
            <td height="22" align="right">开发完成：</td>
            <td height="22"><%=resource.getIsFinished()%></td>
            <td height="22"></td>
        </tr>  
            <tr align="center">
              <td align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg"    onClick="do_SaveResource();return false;">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/cancel.jpg"  onClick="do_Back();return false;">&nbsp;&nbsp;&nbsp;
                <%--<img src="/images/eam_images/delete.jpg"  onClick="do_deleteResource();return false;">&nbsp;&nbsp;&nbsp;--%>
                </td>
            </tr>
    </table>
	
    <input type="hidden" name="resId" value="<%=resource.getResId()%>">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="show" value="show">
    <input type="hidden" name="systemId" value="<%=resource.getSystemId()%>">
    <input type="hidden" name="isInner" value="<%=resource.getIsInner()%>">
</form>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
function getEnabled(){
	var e = mainFrm.enabled;
	for(var i=0;i<e.length;i++){
		if(e[i].checked){
			return e[i].value;
		}
	}
}
function do_SaveResource() {
	var fieldNames = "resName";
	var fieldLabels = "栏目名称";
	mainFrm.resParId.disabled = false;
	var isInner = document.mainFrm.isInner.value;
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	var postData = false;
	if (isValid) {
		postData = true;
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
		if (mainFrm.resId.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
			var enabled = getEnabled();
			if(enabled == "N"){
				if(!confirm("失效本栏目将同时失效其子栏目，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
					postData = false;
				}
			} else if(enabled == "Y"){
				var resId = mainFrm.resId.value;
				if(resId.indexOf(".") > -1){
					if(!confirm("生效本栏目将同时生效其直系父栏目，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
						postData = false;
					}
				}
			}
		}
		if(postData){
			mainFrm.act.value = action;
			mainFrm.action = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet";
			mainFrm.submit();
		}
	}
}
function do_deleteResource() {
    mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
    mainFrm.action = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet";
    mainFrm.submit();
}


function do_Back() {
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.resName.value = "";
	mainFrm.resUrl.value = "";
	mainFrm.resId.value = "";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.action = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet";
	mainFrm.submit();
}

function do_Select() {
	var isInner = mainFrm.isInner.value;
	if(isInner=='Y'){
		mainFrm.resName.readOnly = true;
		mainFrm.resUrl.readOnly = true;
		mainFrm.sortNo.readOnly = true;
		mainFrm.resParId.disabled = true;
		document.getElementById("img2").style.display="none";
	}
}


function do_Check(object) {
	if (object.value != '') {
		if (!isNumber(object.value)) {
			alert("请输入合法数字！");
			object.value = "";
			object.focus();
		} else {
			return true;
		}
	}
}



function do_Change(radio) {
	var isPopup = radio.value;
	if(isPopup == 'Y'){
		mainFrm.popscript.disabled = false;
	} else {
		var popValue = mainFrm.popscript.value;
		if(!isEmpty(popValue)){
			if(confirm("选择不弹出将清除已有弹出脚本，是否继续？")){
				mainFrm.popscript.value = "";
				mainFrm.popscript.disabled = true;
			} else {
				document.getElementById("isPopup_Y").checked = true;
			}
		} else {
			mainFrm.popscript.disabled = true;
		}
	}
}
</script>