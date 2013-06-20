<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/inv/headerInclude.jsp"%>
<%@ include file="/inv/headerInclude.htm"%>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.inv.dzyh.dto.EamDhBillLDTO" %>

<html>
<head>
    <title>低值易耗品出库－台账查询出库</title>
    <style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>

<body leftmargin = "0" rightmargin = "0" topmargin = "0">

<%

    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    Row row = null;
	EamDhBillLDTO dto = (EamDhBillLDTO)request.getAttribute(QueryConstant.QUERY_DTO); //针对Servlet里的dto.setXXX()方法写的  
%>
<form method="post" name="mainFrm">
    <script type = "text/javascript">
        printTitleBar("低值易耗品出库－台账查询出库")
    </script>

    <table width = "100%" border = "0" class = "queryHeadBg">
        <tr>
            <td width = "12%" align = "right">仓库：</td>
            <td width="16%" align="right">
				<input type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName() %>" style="width:80%"><a href="#" title="点击选择仓库" class="linka" onclick="do_SelectStore();">[…]</a>
			</td>
            <td width = "14%" align = "right">目录编号：</td>
            <td width = "14%">
            	<input type="text" name="catalogValueId" value="<%=dto.getCatalogValueId()%>" style="width:80%"><a href="#" title="点击选择目录编号" class="linka" onclick="do_SelectCatalogValueId();">[…]</a>
            </td>
            <td width = "14%" align = "right">品名：</td>
            <td width = "18%">
            	<input type="text" name="itemName" value="<%=dto.getItemName()%>" style="width:80%">
            </td>
            <td width = "2%" align = "center">
                <img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询">
            </td>
        </tr>
    </table>
    
    <div style = "/*overflow-x:scroll;width:100%*/">
		
        <div style="overflow-y:scroll;overflow-x:scroll;left:0px;width:100%;height:390px">
        <!--  
        <script type="text/javascript">
        	var columnArr = new Array("目录编号","条码号", "品名", "规格型号", "数量", "单价","使用部门","领用人","地点","保管人","领用日期","厂家","备注");
        	var widthArr = new Array("6%", "10%", "8%", "8%", "5%","5%","8%","8%","8%","8%","8%","10%","12%");
        	printTableHead(columnArr, widthArr);
    	</script>
    	-->
			<table width="160%" border="1" bordercolor="#666666">
			    
			   <tr height = "20" class = "headerTable">
                    <td width = "6%" align = "center">目录编号</td>
                    <td width = "10%" align = "center">条码号</td>
                    <td width = "8%" align = "center">品名</td>
                    <td width = "8%" align = "center">规格型号</td>
                    <td width = "5%" align = "center">数量</td>
                    <td width = "5%" align = "center">单价</td>
                    <td width = "8%" align = "center">使用部门</td>
                    <td width = "8%" align = "center">领用人</td>
                    <td width = "8%" align = "center">地点</td>
                    <td width = "8%" align = "center">保管人</td>
                    <td width = "8%" align = "center">领用日期</td>
                    <td width = "10%" align = "center">厂家</td>
                    <td width = "12%" align = "center">备注</td>
               </tr>
               
<% 
	if(hasData) {
		for(int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%> 
				<tr height="20" style="cursor: 'hand'" onmousemove="style.backgroundColor='#EFEFEF'" onmouseout="style.backgroundColor='#ffffff'">			
					<td width="6%" align="left"><%=row.getValue("ITEM_CATEGORY2")%></td>
					<td width="10%" align="left"><%=row.getValue("BARCODE")%></td>
					<td width="8%" align="right"><%=row.getValue("ITEM_NAME")%></td>
					<td width="8%" align="right"><%=row.getValue("ITEM_SPEC") %></td>
					<td width="5%" align="left"><%=row.getValue("ITEM_QTY")%></td>
					<td width="5%" align="left"><%=row.getValue("PRICE")%></td>
					<td width="8%" align="left" onclick="do_SelectDept(this);">
					<input type="text" name="deptName" id="<%=row.getValue("RESPONSIBILITY_DEPT") %>" value="<%=row.getValue("DEPT_NAME")%>" readonly="readonly" class="linka" alt="点击选择使用部门"/>	
					<input type="hidden" name="catalogValueId" value="<%=row.getValue("CATALOG_VALUE_ID") %>">
					<input type="hidden" name="barcode" value="<%=row.getValue("BARCODE") %>">
					</td>
					<td width="8%" align="center" onclick="do_SelectUser(this);">
					<input type="text" name="userName" id="<%=row.getValue("RESPONSIBILITY_USER") %>" value="<%=row.getValue("USER_NAME")%>" readonly="readonly" class="linka" alt="点击选择领用人">
					<input type="hidden" name="catalogValueId" value="<%=row.getValue("CATALOG_VALUE_ID") %>">
					<input type="hidden" name="barcode" value="<%=row.getValue("BARCODE") %>">		
					</td>
					<td width="8%" align="center" onclick="do_SelectAddressId(this);">
					<input type="text" name="workorderObjectNoName" id="<%=row.getValue("ADDRESS_ID") %>" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>" readonly="readonly" class="linka" alt="点击选择地点">	
					<input type="hidden" name="catalogValueId" value="<%=row.getValue("CATALOG_VALUE_ID") %>">
					<input type="hidden" name="barcode" value="<%=row.getValue("BARCODE") %>">		
					</td>
					<td width="8%" align="center">
					<input type="text" name="maintainUser" value="<%=row.getValue("MAINTAIN_USER") %>" onfocus="do_SaveOldUser(this);" onchange="do_UpdateMaintainUser(this);" alt="填写保管人">
					<input type="hidden" name="catalogValueId" value="<%=row.getValue("CATALOG_VALUE_ID") %>">
					<input type="hidden" name="barcode" value="<%=row.getValue("BARCODE") %>">
					</td>
					<td width="8%" align="center">
					<input type="text" name="lastLocChgDate" value="<%=row.getValue("LAST_LOC_CHG_DATE") %>" onfocus="do_SaveDate(this);" onchange="do_UpdateLastLocChgDate(this);" alt="请用一般日期格式填写领用日期">
					<input type="hidden" name="catalogValueId" value="<%=row.getValue("CATALOG_VALUE_ID") %>">
					<input type="hidden" name="barcode" value="<%=row.getValue("BARCODE") %>">
					</td>
					<td width="10%" align="right">
					<input type="text" value="<%=row.getValue("ATTRIBUTE3")%>" readonly="readonly" class="finput">
					</td>
					<td width="12%" align="right">
					<input type="text" value="<%=row.getValue("REMARK")%>" readonly="readonly" class="finput">
					</td>
				</tr>
<% 
		}
	}
%>
			</table>
		</div>
		
		</div>

		<input name="act" type="hidden">
		<!--  
		<input type="hidden" name="responsibilityDept" value="">
		<input type="hidden" name="responsibilityUser" value="">
		<input type="hidden" name="workorderObjectNo">
		-->
	</form>
<%
	if(hasData){
%>
	<div style="position:absolute;top:470px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
	<%=WebConstant.WAIT_TIP_MSG%>
	<jsp:include page="/message/MessageProcess" flush="true"></jsp:include>
<%
	}	
%>
</body>
</html>
<script type = "text/javascript">
    function do_search() {
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhBillLServlet";
        mainFrm.submit();
    }
    
    function do_SelectStore() {
    	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
        	document.forms[0].workorderObjectName.value = vendorNames[0].workorderObjectName;
    	}
    }
    
    function do_SelectCatalogValueId() {
    	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_CATALOG_VALUE_ID%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
        	document.forms[0].catalogValueId.value = vendorNames[0].catalogValueId;
    	}
    }
    
    function do_SelectDept(obj) {
    	var oldDept = obj.childNodes[0].id;
    	var catalogValueId = obj.childNodes[2].value;
    	var barcode = obj.childNodes[4].value;
    	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_RESPONSIBILITY_DEPT%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
        	//document.forms[0].responsibilityDept.value = vendorNames[0].responsibilityDept;
        	var newDept = vendorNames[0].responsibilityDept;
        	obj.childNodes[0].value = vendorNames[0].deptName;
        	updateDept(catalogValueId, barcode, oldDept, newDept);
    	}
    }
    
    function do_SelectUser(obj) {
    	var oldUser = obj.childNodes[0].id;
    	var catalogValueId = obj.childNodes[2].value;
    	var barcode = obj.childNodes[4].value;
    	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_RESPONSIBILITY_USER%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
        	//document.forms[0].responsibilityUser.value = vendorNames[0].responsibilityUser;
        	var newUser = vendorNames[0].responsibilityUser;
        	obj.childNodes[0].value = vendorNames[0].userName;
        	updateUser(catalogValueId, barcode, oldUser, newUser);
    	}
    }
    
    function do_SelectAddressId(obj) {
    	var oldAddressId = obj.childNodes[0].id;
    	var catalogValueId = obj.childNodes[2].value;
    	var barcode = obj.childNodes[4].value;
    	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO_NAME%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var vendorNames = window.showModalDialog(url, null, popscript);
    	if(vendorNames){
        	var vendorName = null;
        	//document.forms[0].workorderObjectNo.value = vendorNames[0].workorderObjectNo;
        	//var newAddressId = vendorNames[0].workorderObjectNo;
        	var newAddressId = vendorNames[0].addressId;
        	obj.childNodes[0].value = vendorNames[0].workorderObjectName;
        	updateAddressId(catalogValueId, barcode, oldAddressId, newAddressId);
    	}
    }
    
    var oldMaintainUser = "";
    function do_SaveOldUser(obj) {
    	oldMaintainUser = obj.value;
    	//return oldUser;
    }
    
    function do_UpdateMaintainUser(obj) {
    	//当失去焦点的时候触发Action
    	var newMaintainUser = obj.value;
    	var newMaintainUserNode = obj.nextSibling;
    	
    	var catalogValueId = newMaintainUserNode.nextSibling.value;
    	var catalogValueIdNode = newMaintainUserNode.nextSibling;
    	
    	var barcodeNode = catalogValueIdNode.nextSibling;
    	var barcode = barcodeNode.nextSibling.value;
    	
    	updateMaintainUser(catalogValueId, barcode, oldMaintainUser, newMaintainUser);
    }
    
    var oldDate = "";
    function do_SaveDate(obj) {
    	oldDate = obj.value;
    }
    
    function do_UpdateLastLocChgDate(obj) {
    	//当失去焦点的时候触发Action
    	var newDate = obj.value;
    	var newDateNode = obj.nextSibling;
    	
    	var catalogValueId = newDateNode.nextSibling.value;
    	var catalogValueIdNode = newDateNode.nextSibling;
    	
    	var barcodeNode = catalogValueIdNode.nextSibling;
    	var barcode = barcodeNode.nextSibling.value;
    	
    	updateLastLocChgDate(catalogValueId, barcode, oldDate, newDate);
    }
    
    /*
    function changePrivi(chk) {
        var cbId = chk.id;
        cbId = cbId.replace("Box", "");
        var hiddenObj = document.getElementById(cbId);
        if (chk.checked) {
            hiddenObj.value = 1;
        } else {
            hiddenObj.value = 0;
        }
    }
     
    function do_SelectName() {
        document.mainFrm.executeUserName.value = "";
        document.mainFrm.executeUser.value = "";
        document.mainFrm.executeInv.value = "";
        var lookUpName = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }
    
    function do_checkNotNull() {
        var checkPoint = 0;
        if (document.mainFrm.executeUserName.value != null && document.mainFrm.executeUserName.value != "") {
            checkPoint = 1;

        }
        if (document.mainFrm.executeInv.value != null && document.mainFrm.executeInv.value != "") {
            checkPoint = 1;
        }
        return  checkPoint;
    }
    */


    
//---------------------------------------------------------------------------------------------------

var xmlHttp;

//---------------------------------------------------------------------------------------------------

//-- updateDept
function updateDept(catalogValueId, barcode, oldDept, newDept) {
    var url = "";
    //var dept = document.getElementById("responsibilityDept").value;
    xmlHttp = createXMLHttpRequest();
    if (newDept != "") {
        url = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhBillLServlet?act=UPDATEDEPT_ACTION&catalogValueId=" + catalogValueId + "&barcode=" + barcode + "&oldDept=" + oldDept + "&newDept=" + newDept;
        xmlHttp.onreadystatechange = handleReadyStateChangeDept;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

//updateresponsibilityDept
function handleReadyStateChangeDept() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            //alert(resText);     
        } else{
        	alert(xmlHttp.status);
        }
    }
}

//-- updateUser
function updateUser(catalogValueId, barcode, oldUser, newUser) {
    var url = "";
    //var dept = document.getElementById("responsibilityDept").value;
    xmlHttp = createXMLHttpRequest();
    if (newUser != "") {
        url = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhBillLServlet?act=UPDATEUSER_ACTION&catalogValueId=" + catalogValueId + "&barcode=" + barcode + "&oldUser=" + oldUser + "&newUser=" + newUser;
        xmlHttp.onreadystatechange = handleReadyStateChangeUser;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

//updateresponsibilityDept
function handleReadyStateChangeUser() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            //alert(resText);     
        } else{
        	alert(xmlHttp.status);
        }
    }
}

//-- updateAddressId
function updateAddressId(catalogValueId, barcode, oldAddressId, newAddressId) {
    var url = "";
    //var dept = document.getElementById("responsibilityDept").value;
    xmlHttp = createXMLHttpRequest();
    if (newAddressId != "") {
        url = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhBillLServlet?act=UPDATEADDRESSID_ACTION&catalogValueId=" + catalogValueId + "&barcode=" + barcode + "&oldAddressId=" + oldAddressId + "&newAddressId=" + newAddressId;
        xmlHttp.onreadystatechange = handleReadyStateChangeAddressId;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

//updateresponsibilityDept
function handleReadyStateChangeAddressId() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            //alert(resText);     
        } else{
        	alert(xmlHttp.status);
        }
    }
}

//-- updateMaintainUser
function updateMaintainUser(catalogValueId, barcode, oldMaintainUser, newMaintainUser) {
	var url = "";
	xmlHttp = createXMLHttpRequest();
    if (newMaintainUser != "") {
        url = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhBillLServlet?act=UPDATEMAINTAINUSER_ACTION&catalogValueId=" + catalogValueId + "&barcode=" + barcode + "&oldMaintainUser=" + oldMaintainUser + "&newMaintainUser=" + newMaintainUser;
        xmlHttp.onreadystatechange = handleReadyStateChangeMaintainUser;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    } else {
    	alert("保管人不允许为空!");
    }
}

//updateMaintainUser
function handleReadyStateChangeMaintainUser() {
	if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            //alert(resText);     
        } else{
        	alert(xmlHttp.status);
        }
    }
}

//-- updateLastLocChgDate
function updateLastLocChgDate(catalogValueId, barcode, oldDate, newDate) {
	var url = "";
	xmlHttp = createXMLHttpRequest();
	var validate = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
	
    if (validate.test(newDate)) {   
    //(!/^19\d\d\-[0-1]\d\-[0-3]\d$/.test(birth) && !/^20[0-1]\d\-[0-1]\d\-[0-3]\d$/.test(birth))
    
        url = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhBillLServlet?act=UPDATELASTLOCCHGDATE_ACTION&catalogValueId=" + catalogValueId + "&barcode=" + barcode + "&oldDate=" + oldDate + "&newDate=" + newDate;
        xmlHttp.onreadystatechange = handleReadyStateChangeLastLocChgDate;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    } else {
    	alert("请按正确的时间格式填写(YYYY-MM-DD)");
    }
}

//updateLastLocChgDate
function handleReadyStateChangeLastLocChgDate() {
	if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            //alert(resText);     
        } else{
        	alert(xmlHttp.status);
        }
    }
}
</script>