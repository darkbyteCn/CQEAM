<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.dzyh.dto.EtsItemInfoDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.dzyh.constant.DzyhActionConstant"%>
<jsp:directive.page import="com.sino.ams.dzyh.constant.DzyhLookUpConstant"/>


<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>低值易耗品详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>

</head>
<body onkeydown="autoExeFunction('do_SaveDzyh()');">
 <jsp:include page="/message/MessageProcess"/>
<%
	String parameter=request.getParameter("dzyh");

    String category2=(String)request.getAttribute(WebAttrConstant.DZYH_CATEGORY2_OPT);
    String itemName=(String)request.getAttribute(WebAttrConstant.DZYH_ITEM_NAME_OPT);
    String itemSpec=(String)request.getAttribute(WebAttrConstant.DZYH_ITEM_SPEC_OPT);
    String deptName=(String)request.getAttribute(WebAttrConstant.DZYH_RESPONSIBILITY_DEPT_OPT);
    String userName=(String)request.getAttribute(WebAttrConstant.DZYH_RESPONSIBILITY_USER_OPT);
    String address=(String)request.getAttribute(WebAttrConstant.DZYH_ADDRESS_NAME_OPT);
    
    EtsItemInfoDTO etsItemInfoDto = (EtsItemInfoDTO)request.getAttribute(WebAttrConstant.DZYH_DATA);
    
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EtsSystemItemInfoServlet">
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="20%" align="right" height="22">目录编号：</td>
            <td width="20%" align="left" height="22">
				<%--<input type="text" name="eiiItemCategory2" style="width:100%" value="<%=etsItemInfoDto.getEiiItemCategory2()%>">
				--%>
				<select name="eiiItemCategory2" style="width:100%"><%=category2 %></select>
				</td>
            <td width="20%" align="right" height="22">条码号：</td>
            <td width="20%" align="left" height="22">
				<input type="text" name="barcode" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=etsItemInfoDto.getBarcode()%>">
				</td>
            <td width="20%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">品&nbsp;&nbsp;名：</td>
            <td width="20%" align="left" height="22">
            <%--<input type="text" name="eiiItemName" style="width:100%" value="<%=etsItemInfoDto.getEiiItemName()%>">
				--%>
				<select name="eiiItemName" style="width:100%"><%=itemName %></select>
                </td>
            <td width="20%" align="right" height="22">规格型号：</td>
            <td width="20%" align="left" height="22">
				<%--<input type="text" name="eiiItemSpec" style="width:100%" value="<%=etsItemInfoDto.getEiiItemSpec()%>">
				--%>
				<select name="eiiItemSpec" style="width:100%"><%=itemSpec %></select>
				</td>
            <td width="20%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="20%" align="right" height="22">数量：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="itemQty" style="width:100%" value="<%=etsItemInfoDto.getItemQty()%>">
                </td>
            <td width="20%" align="right" height="22">单价：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="price" style="width:100%" value="<%=etsItemInfoDto.getPrice()%>">
                </td>
            <td width="20%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">使用部门：</td>
            <td width="20%" align="left" height="22">
            <%--<input type="text" name="eiiDeptName" style="width:100%" value="<%=etsItemInfoDto.getEiiDeptName()%>">
				--%>
				<select name="eiiDeptName" style="width:100%"><%=deptName %></select>
            <td width="20%" align="right" height="22">领用日期：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="lastLocChgDate" style="width:100%" value="<%=etsItemInfoDto.getLastLocChgDate()%>">
            </td>
            <td width="20%" colspan="3" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">领用人：</td>
            <td width="20%" align="left" height="22">
				<%--<input type="text" name="eiiUserName" style="width:100%" value="<%=etsItemInfoDto.getEiiUserName()%>">
				--%>
				<select name="eiiUserName" style="width:100%"><%=userName %></select>
				</td>
            <td width="20%" align="right" height="22">保管人：</td>
            <td width="20%" align="left" height="22">
				<input type="text" name="maintainUser" style="width:100%" value="<%=etsItemInfoDto.getMaintainUser()%>">
				</td>
            <td width="20%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">地点：</td>
            <td width="20%" align="left" height="22">
				<%--<input type="text" name="eiiWorkorderObjectName" style="width:100%" value="<%=etsItemInfoDto.getEiiWorkorderObjectName()%>">
				--%>
				<select name="eiiWorkorderObjectName" style="width:100%"><%=address %></select>
				</td>
            <td width="20%" align="right" height="22">厂家：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="attribute3" style="width:100%" value="<%=etsItemInfoDto.getAttribute3()%>">
				</td>
            <td width="20%" align="left" height="22">
            </td>
        </tr>
        <tr>
        <td width="20%" align="right" height="22">备注：</td>
        <td colspan="3" align="left" height="22">
        <textarea rows="5" cols="150"><%=etsItemInfoDto.getRemark()%></textarea></td>
        </tr>
        <tr><td width="100%" align="center" height="22" colspan="4">
        </td></tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
<%if(!parameter.equals(DzyhActionConstant.CHAXUN_ACTION)){ %>
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDzyh(); return false;">&nbsp;
                <img src="/images/eam_images/change_history.jpg" alt="变动历史" onClick="do_ChangeDzyh(); return false;">&nbsp;
<%} %>
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_Back(); return false;">
                </td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="systemid" value="<%=etsItemInfoDto.getSystemid()%>">
	<input type="hidden" name="dzyh" value="<%=parameter%>">
    <input type="hidden" name="catalogSetName" value="">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script>
<%--

function initPage(){
	var selObj = mainFrm.catalogSetId;
	var catalogSetName = selObj.options[selObj.selectedIndex].text;
	var startIndex = catalogSetName.indexOf("(") + 1;
	var endIndex = catalogSetName.length - 1;
	catalogSetName = catalogSetName.substring(startIndex, endIndex);
	mainFrm.catalogSetName.value = catalogSetName;
}
--%>
function do_SaveDzyh() {
	var fieldNames = "barcode;eiiItemName;eiiDeptName;eiiUserName;maintainUser";
	var fieldLabels = "条码号;品名;使用部门;领用人;保管人";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.systemid.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_ChangeDzyh(){
	var lookUpName = "<%=DzyhLookUpConstant.LOOK_UP_CHANGE_DZYH%>";
	var url="/servlet/com.sino.ams.dzyh.servlet.DzyhChangeLookUpServlet?lookUpName="+lookUpName;
	var dialogWidth = 85;
	var dialogHeight = 40;
	var popscript = "dialogWidth:"
            + dialogWidth
            + ";dialogHeight:"
            + dialogHeight
            + ";center:yes;status:no;scrollbars:yes;help:no";
//			window.open(url);
    return window.showModalDialog(url, null, popscript);
//	mainFrm.submit();
}

function do_Back(){
	with(mainFrm){
		eiiItemCategory2.value = "";
		barcode.value = "";
		eiiItemName.value = "";
		eiiItemSpec.value = "";
		eiiDeptName.value = "";
		maintainUser.value = "";
		eiiWorkorderObjectName.value="";
		attribute3.value = "";
		lastLocChgDate.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>