<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import = "com.sino.ams.print.dto.BarcodeReceiveDTO" %>
<%@ page import = "com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Author: 李轶
  Date: 2009-4-28
--%>
<html>
<head>
    <title>标签领用维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("标签领用维护");
</script>

<body leftmargin="0" topmargin="0">
<%
    BarcodeReceiveDTO brDTO = (BarcodeReceiveDTO) request.getAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO);
	SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgaOption = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
	String deptOption = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
%>
<form action="/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet" method="post" name="mainFrm">
    <jsp:include page="/message/MessageProcess"/>
    <input type="hidden" name="act">
<%--    <input type="hidden" name="name" value="">--%>
<%--    <input type="hidden" name="userId" value="<%=user.getUserId()%>">--%>
    <table width="81%" border="0" align="center" class="queryTable">
        <tr>
        	<input type = "hidden" name = "barcodeReceiveId" value = "<%=brDTO.getBarcodeReceiveId() %>">
            <td width="20%" align="right">起始标签：</td>
            <td width="80%" colspan = "3"><input type="text" name="fromBarcode" class="input_style1" style="width:80%"
                                   value="<%=brDTO.getFromBarcode() %>"><font color="red">*</font>
                <br><label id="retMsg" style="color:red"></label>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">结束标签：</td>
            <td width="80%" colspan = "3"><input type="text" name="toBarcode" class="input_style1" style="width:80%"
                                   value="<%=brDTO.getToBarcode()%>"><font color="red">*</font>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">标签数量：</td>
            <td width="22%">
            	<input type="text" name = "barcodeQty" size = "40" readonly class="input_style1"  style = "width:80%" value="<%=brDTO.getBarcodeQty() %>" onclick = "do_getBarcodeQty();"><font color="red">*</font>
            </td>
            <%--<td width="15%" align="right">标签打印次数：</td>--%>
            <%--<td width="40%">--%>
            	<%--<input type="text" name = "barcodePrintNum" size = "20"  class="noEmptyInput"  style = "width:61%" value="<%=brDTO.getBarcodePrintNum() %>">--%>
            <%--</td>--%>
        </tr>
        
        <tr>
            <td width="20%" align="right">所属地市：</td>
            <td width="80%" colspan = "3">
            	<select style="width:80%" name="organizationId" class="select_style1" onchange="getDeptOpt();"><%=orgaOption%></select>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">领用部门：</td>
            <td width="80%" colspan = "3">
				<select style="width:80%" name="receiveDept" class="select_style1" onclick = "do_SelectDept(); return false;"><%=deptOption%></select>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">领用人：</td>
            <td width="80%" colspan = "3">
            	<input type = "hidden" name = "receiveUser" value = "<%=brDTO.getReceiveUser() %>">
            	<input type="text" name = "receiveUserName" size = "40"class="input_style1" style = "width:80%"
                                   value="<%=brDTO.getReceiveUserName() %>" onclick="do_SelectUser(mainFrm.receiveUser, mainFrm.receiveUserName); return false;" title="点击选择领用人"><font color="red">*</font>
                            <a href="" onclick="do_SelectUser(mainFrm.receiveUser, mainFrm.receiveUserName); return false;" title="点击选择领用人">[...]</a>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">领用日期：</td>
            <td width="80%" colspan = "3">
            	<input  readonly name="receiveDate" style="width:80%" class="input_style2" value="<%=brDTO.getReceiveDate()  %>" onclick="gfPop.fPopCalendar(receiveDate)">
            	<img src="/images/calendar.gif" alt="点击选择领用日期" onclick="gfPop.fPopCalendar(receiveDate)">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">打印日期：</td>
            <td width="80%" colspan = "3">
            	<input  readonly name="printDate" class="input_style2" style="width:80%" value="<%=brDTO.getPrintDate() %>" onclick="gfPop.fPopCalendar(printDate)">
            	<img src="/images/calendar.gif" alt="点击选择打印日期" onclick="gfPop.fPopCalendar(printDate)">
			</td>
        </tr>
        <tr>						
            <td width="20%" align="right">打印人：</td>
            <td width="80%" colspan = "3">
            	<input type = "hidden" name = "printUser"  value = "<%=user.getUserId() %>" >
            	<input name="printUserName" style="width:80%" value = "<%=user.getUsername() %>"  class="input_style2" readonly>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">领用原因：</td>
            <td width="80%" colspan = "3">
            	<textarea name="receiveRemark" cols = ""class="input_style1" rows = "4" style="width:80%" ><%=brDTO.getReceiveRemark() %></textarea>
            </td>
        </tr>
    </table>
    <p align="center">
    	<img src="/images/eam_images/save.jpg" alt="保存" onclick="do_Save(); return false;">&nbsp;
    	<%
    		if(!brDTO.getBarcodeReceiveId().equals("")){    		
    	%>
    		<img src="/images/eam_images/delete.jpg" alt="删除" onclick="do_Delete(); return false;">&nbsp;
    	<%
    		}
    	%>
        <img src="/images/eam_images/back.jpg" alt="取消" onclick="history.go(-1);">
    </p>
    
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
	do_SetImageProp();
}
	
	function do_SelectDept(){
		var organizationId = document.getElementsByName("organizationId")[0].value;
		if(organizationId == ""){
			alert("请先选择所属地市！");
			mainFrm.organizationId.focus();
		}		
	}
	
	function do_Delete() {
	    var roleId = mainFrm.barcodeReceiveId.value;
	    if (confirm("确认删除该标签领用记录吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
	        mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
	        document.mainFrm.submit();
	    }
	}
	

	//得到打印标签数量
	function do_getBarcodeQty(){
		var fieldNames = "fromBarcode;toBarcode";
        var fieldLabels = "起始标签;结束标签";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        if (isValid) {
            var fromBarcode = mainFrm.fromBarcode.value;
            var toBarcode = mainFrm.toBarcode.value;
            var Expression = /^[0-9]{4}-[0-9]{8}$/;
            var objExp=new RegExp(Expression);
            if(!objExp.test(fromBarcode)){
                alert("“起始标签”不合法，请输入正确格式！");
                mainFrm.fromBarcode.focus();
                return;
            }
            if(!objExp.test(toBarcode)){
                alert("“结束标签”不合法，请输入正确格式！");
                mainFrm.toBarcode.focus();
                return;
            }
            var fromBarcodeCompanyCode = fromBarcode.substring(0,4);
            var toBarcodeCompanyCode = toBarcode.substring(0,4);
            if(fromBarcodeCompanyCode != toBarcodeCompanyCode){
                alert("“起始标签”与“结束标签”所隶属公司不一致！");
                mainFrm.barcodeQty.value = "";
                return;
            }
            fromBarcode = fromBarcode.substring(7, fromBarcode.length);
            toBarcode = toBarcode.substring(7, toBarcode.length);
            var barcodeQty = parseFloat(toBarcode) - parseFloat(fromBarcode) + 1;
            mainFrm.barcodeQty.value = barcodeQty;
        }
	}

    var loginNameWrong = false;
    function do_Save() {
        var fieldNames = "fromBarcode;toBarcode;barcodeQty;receiveUserName;printUserName";
        var fieldLabels = "起始标签;结束标签;标签数量;领用人;打印人";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        if (isValid) {
            var fromBarcode = mainFrm.fromBarcode.value;
            var toBarcode = mainFrm.toBarcode.value;
            var Expression = /^[0-9]{4}-[0-9]{8}$/;
            var objExp=new RegExp(Expression);
            if(!objExp.test(fromBarcode)){
                alert("“起始标签”不合法，请输入正确格式！");
                mainFrm.fromBarcode.focus();
                return;
            }
            if(!objExp.test(toBarcode)){
                alert("“结束标签”不合法，请输入正确格式！");
                mainFrm.toBarcode.focus();
                return;
            }
            var fromBarcodeCompanyCode = fromBarcode.substring(0,4);
            var toBarcodeCompanyCode = toBarcode.substring(0,4);
            if(fromBarcodeCompanyCode != toBarcodeCompanyCode){
                alert("“起始标签”与“结束标签”所隶属公司不一致！");
                mainFrm.barcodeQty.value = "";
                return;
            }
            if(mainFrm.barcodeReceiveId.value != ""){
                document.mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }else{
                document.mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            }
            document.mainFrm.submit();
        }
	}

	/******************************************
	 *	Function: 根据指定的OU,查询该OU下的用户	  *
	 ******************************************/
	function do_SelectUser(userId, userName){
	    if(mainFrm.receiveDept.value != ""){
	    	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER_BY_ORGANAZATION%>";
			var dialogWidth = 44;
			var dialogHeight = 31;
			var userPara = "organizationId=" + document.getElementsByName("organizationId")[0].value;
			var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		    if (users) {
		        var user = users[0];
		        userName.value = user["userName"];
		        userId.value = user["userId"];	        	        
		    } else {
		        userId.value = "";
		        userName.value = "";
		    }
		}else{
			alert("请先选择领用部门!");
		}
	}


var xmlHttp;
    function getDeptOpt() {
        var organizationId = document.getElementById("organizationId").value ;
        var url = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=CHOOSE_GROUP&organizationId=" + organizationId;
       
        xmlHttp = GetXmlHttpObject(setDeptOpt);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("a=1");
    }

    function setDeptOpt() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            if (xmlHttp.status == 200) {
                var resText = xmlHttp.responseText;
                var resArray = resText.parseJSON();
                if (resArray == "ERROR") {
                    alert(resText);
                } else {
                    var littleCategoryObj = document.getElementById("receiveDept");
                    
                    littleCategoryObj.length = 0;
                    var emptyOption = new Option("--请选择--", "");
                    littleCategoryObj.add(emptyOption)
                    if (resArray.length > 0) {
                        var retStr;
                        for (var i = 0; i < resArray.length; i++) {
                            retStr = resArray[i];
                            var arr = retStr.split("$");
                            var option = new Option(arr[1], arr[0]);
                            littleCategoryObj.add(option)
                        }
                    }
                }
                xmlHttp = null;
            }
        }
    }

    

/**
 * 将返回的列表加入区县下拉框。
 * 修改完成。
 */
function do_ProcessResponse(responseContent){
	mainFrm.countyCode.outerHTML = "<select style=\"width:100%\" name=\"countyCode\">" + responseContent + "</select>";
}
</script>