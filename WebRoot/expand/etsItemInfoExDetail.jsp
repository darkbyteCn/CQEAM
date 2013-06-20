<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.expand.dto.EtsItemInfoExDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.dzyh.constant.DzyhLookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>


<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>资产扩展详细信息(可更改)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>

</head>
<body onkeydown="autoExeFunction('do_SaveCar()');">
 <jsp:include page="/message/MessageProcess"/>
<%
    EtsItemInfoExDTO dto = (EtsItemInfoExDTO)request.getAttribute(WebAttrConstant.DZYH_DATA);
    
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.expand.servlet.EtsItemInfoExServlet">
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="20%" align="right" height="22">条码号：</td>
            <td width="20%" align="left" height="22">
				<input type="text" name="barcode" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=dto.getBarcode()%>">
				</td>
            <td width="20%" align="right" height="22">名称：</td>
            <td width="20%" align="left" height="22">
				<input type="text" name="itemName" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=dto.getItemName()%>">
				</td>
            <td width="20%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">型号：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="itemSpec" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=dto.getItemSpec()%>">
                </td>
                
            <td width="20%" align="right" height="22">折旧年限：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="lifeInYears" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=dto.getLifeInYears()%>">
            </td>
            <td width="20%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="20%" align="right" height="22">驾驶员：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="attribute4" style="width:100%" class="noemptyInput" value="<%=dto.getAttribute4()%>">
                </td>
            <td width="20%" align="right" height="22">启用日期：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="datePlacedInService" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=dto.getDatePlacedInService()%>">
                </td>
            <td width="20%" align="left" height="22"></td>
        </tr>
        <tr>
            
            <td width="20%" align="right" height="22">牌照：</td>
            <td width="20%" align="left" height="22">
				<input type="text" name="attribute1" style="width:100%" class="noemptyInput" value="<%=dto.getAttribute1()%>">
				</td>
            <td width="20%" align="right" height="22">责任人：</td>
            <td width="20%" align="left" height="22">
            <input type="text" name="employeeName" readonly="readonly" class="readonlyInput" style="width:100%" value="<%=dto.getEmployeeName()%>">
            </td>
            <td width="20%" colspan="3" align="left" height="22"></td>
        </tr>
        <tr><td width="100%" align="center" height="22" colspan="4">
        </td></tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/button/save.gif" alt="保存" onClick="do_SaveCar(); return false;">&nbsp;
                <img src="/images/button/back.gif" alt="返回" onClick="do_Back(); return false;">
                </td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="itemInfoExId" value="<%=dto.getItemInfoExId()%>">
    <input type="hidden" name="catalogSetName" value="">
</form>
<%=WebConstant.WAIT_TIP_MSG%>
</body>

<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script>
function do_SaveCar() {
	var fieldNames = "attribute1;attribute4";
	var fieldLabels = "牌照;驾驶员";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.itemInfoExId.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_Back(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	with(mainFrm){
		barcode.value = "";
		itemName.value = "";
		itemSpec.value = "";
		attribute1.value = "";
		attribute4.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>