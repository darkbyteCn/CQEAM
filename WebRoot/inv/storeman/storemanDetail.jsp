<%@ include file="/inv/headerInclude.jsp"%>
<%@ include file="/inv/headerInclude.htm"%>
<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%--
  created by YSB
  Date: 2008-12-12
  Time: 10:58:39
--%>
<html>
  <head>
    <title>编辑仓管员信息</title>
  </head>
  
  <body text="000000" bgcolor="ffffff" leftmargin="0" topmargin="0" class="STYLE1" onload="window.focus();">
    <%
    	EamInvStoremanDTO dto = (EamInvStoremanDTO)request.getAttribute(QueryConstant.QUERY_DTO);//针对Servlet里的dto.setXXX()方法写的 
  		SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
		String organizationId = userAccount.getOrganizationId();
	%>
	<form action="" name="mainFrm" method="post">
		<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS %>" flush="true"></jsp:include>
		<table width="100%" border="0" style="position:absolute;top:20px">
			<tr>
				<td width="25%" align="right" height="22">仓库名称：</td>
            	<td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="workorderObjectName" size="40" class="noemptyInput" style="width:92%"
                       value="<%=dto.getWorkorderObjectName()%>" readonly="readonly">&nbsp;&nbsp;<a href="#" title="点击选择仓库" class="linka" onclick="do_SelectStore();">[…]</a>
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">仓库类型：</td>
            	<td width="50%" align="left" height="22" colspan="3">
            	<input type="text" name="invCategoryName" size="40" class="noemptyInput" style="width:100%"
                       value="<%=dto.getInvCategoryName()%>" readonly="readonly">
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">业务类型：</td>
            	<td width="50%" align="left" height="22" colspan="3">
            	<!--  
                <input type="text" name="businessCategory" size="40" class="noemptyInput" style="width:100%"
                       value="<%--=eamInvStoremanDTO.getValue1()--%>">
                -->
                <input type="text" name="bizCategoryName" size="40" class="noemptyInput" style="width:100%"
                       value="<%=dto.getBizCategoryName()%>" readonly="readonly">
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">仓管员：</td>
            	<td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="userName" size="40" class="noemptyInput" style="width:92%"
                       value="<%=dto.getUserName()%>" readonly="readonly">&nbsp;&nbsp;<a href="#" title="点击选择仓管员" class="linka" onclick="do_SelectStoreman();">[…]</a>
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">创建人：</td>
            	<td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="createdUser" size="40" class="noemptyInput" style="width:100%"
                       value="<%=dto.getCreatedUser()%>" readonly="readonly">
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">创建日期：</td>
            	<td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="creationDate" size="40" class="noemptyInput" style="width:100%"
                       value="<%=dto.getCreationDate()%>" readonly="readonly">
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">修改人：</td>
            	<td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="updatedUser" size="40" class="noemptyInput" style="width:92%"
                       value="<%=dto.getUpdatedUser()%>" readonly="readonly">&nbsp;&nbsp;<a href="#" title="点击选择修改人" class="linka" onclick="do_SelectUpdatedUser();">[…]</a>
            	</td>
            	<td width=" " align="left" height="22"></td>
			</tr>
			<tr>
				<td width="25%" align="right" height="22">修改日期：</td>
            	<td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="lastUpdateDate" size="40" class="noemptyInput" style="width:100%"
                       value="<%=dto.getLastUpdateDate()%>" readonly = "true">   
            	</td>
            	<td width="" align="left" height="22"></td>
			</tr>
			<tr>
				<td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onclick="do_submit();">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="取消" onclick="do_close();"></td>
			</tr>
		</table>
		<input type="hidden" name="act" value="<%=dto.getAct()%>">
		<%--parser.getParameter("act")--%>
		<input type="hidden" name="userId" value="<%=dto.getUserId() %>">
		<input type="hidden" name="workorderObjectNo" value="<%=dto.getWorkorderObjectNo() %>">
		<input type="hidden" name="lastUpdateBy">
		<input type="hidden" name="storemanId" value="<%=request.getParameter("storemanId") %>">
		<input type="hidden" name="codeExist" id="codeExist" value="N">
	</form>
  </body>
</html>
<script type="text/javascript">
function do_submit() {
    var fieldNames = "workorderObjectName;invCategoryName;bizCategoryName;userName;createdUser;creationDate;updatedUser;lastUpdateDate";
    var fieldLabels = "仓库名称;仓库类别;业务类型;仓管员;创建人;创建日期;修改人;修改日期";
    
    //var objectCode = "<%=dto.getWorkorderObjectNo()%>" ;
	/*
    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
        mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.userName.value != "") {
            mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        }
       
        
        var codeExist = document.getElementById("codeExist").value;
        if (codeExist == "N") {
            var categoryExist = document.getElementById("categoryExist").value;
            if (categoryExist == "N") {
                mainFrm.action = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet";
                mainFrm.submit();
                opener.location.href = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
            } else {
                alert("该类型仓库已存在");
            }
        } else {
            alert("该仓库代码已存在");
        }
    }
    */
    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
    	if (mainFrm.userName.value != "") {
            mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        }
    }
    mainFrm.action = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet";
    mainFrm.submit();
    opener.location.href = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=<%=WebActionConstant.QUERY_ACTION%>";   
}

function do_close() {
    window.close();
    opener.location.href = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
}

function do_SelectStoreman() {
	/*
	var lookUpName = "<%=LookUpConstant.LOOK_UP_USER_ID%>";
	var dialogWidth = 50;
	var dialogHeight = 30;	
	var userPara = "organizationId=<%=organizationId%>";
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if(objs.lenght > 0){
		//dto2Frm(objs[0], "mainFrm");
		mainFrm.userId.value = objs[0]["userId"];
		mainFrm.userName.value = objs[0]["userName"];
		alert("asdfasdsf::::" + mainFrm.userId.value + "----------" + mainFrm.userName.value);
	}
	*/

	var  url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_USER_ID%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
       var vendorName = null;
       document.forms[0].userId.value = vendorNames[0].userId; 
       document.forms[0].userName.value = vendorNames[0].userName;
    }
    //window.returnValue = "";
}

function do_SelectStore(){
	/*
	var lookUpStore = "<%=LookUpConstant.LOOK_UP_WORKORDER_OBJECT_NO%>";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var stores = getLookUpValues(lookUpStore, dialogWidth, dialogHeight);
	if(stores){
		mainFrm.workorderObjectNo.value = objs[0]["workorderObjectNo"];
		mainFrm.workorderObjectName.value = objs[0]["workorderObjectName"];
	}
	
	if(stores){
		var store = null;
		for(var i=0; i<stores.length; i++){
			store = stores[i];
			dto2Frm(store, "mainFrm");
		}
	}
	*/
	
	var  url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
       var vendorName = null;
       document.forms[0].workorderObjectNo.value = vendorNames[0].workorderObjectNo;
       document.forms[0].workorderObjectName.value = vendorNames[0].workorderObjectName;
       document.forms[0].invCategoryName.value = vendorNames[0].invCategoryName;
       document.forms[0].bizCategoryName.value = vendorNames[0].bizCategoryName;
       checkObjectNo();
    }
    
}

function do_SelectUpdatedUser(){
	/*
	var lookUpName = "<%=LookUpConstant.LOOK_UP_UPDATED_USER%>";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=organizationId%>";
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if(objs){
		mainFrm.lastUpdateBy.value = objs[0]["lastUpdateBy"];
		mainFrm.updatedUser.value = objs[0]["updatedUser"];
		
	}
	*/
	
	var  url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_UPDATED_USER%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
       var vendorName = null;
       document.forms[0].lastUpdateBy.value = vendorNames[0].lastUpdateBy;
       document.forms[0].updatedUser.value = vendorNames[0].updatedUser;
    }
}

//---------------------------------------------------------------------------------------------------

var xmlHttp;

//---------------------------------------------------------------------------------------------------

//-- checkObjectNo
function checkObjectNo() {

    var url = "";
    var objCode = document.getElementById("workorderObjectNo").value;
    xmlHttp = createXMLHttpRequest();
    if (objCode != "") {
        url = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=CHECK_ACTION&workorderObjectNo=" + objCode;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

//checkNo
function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            if (resText == "<%=WebAttrConstant.CODE_EXIST%>") {
                document.getElementById("codeExist").value = "Y";
                document.getElementById("tipMsg").style.visibility = "visible";
            } else if (resText == '<%=WebAttrConstant.CODE_NOT_EXIST%>') {
                document.getElementById("codeExist").value = "N";
                document.getElementById("tipMsg").style.visibility = "hidden";
            }          
        } else {
            alert(xmlHttp.status);
        }
    }
}
</script>