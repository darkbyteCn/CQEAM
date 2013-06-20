<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.dzyh.dto.EamDhCatalogSetDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>组别详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>

</head>
<body onload="initPage();" onkeydown="autoExeFunction('do_SaveDZYH()');">
 <jsp:include page="/message/MessageProcess"/>
<%
    EamDhCatalogSetDTO eamDhCatalogSet = (EamDhCatalogSetDTO)request.getAttribute(WebAttrConstant.DZYH_TYPE_DATA);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EamDhCatalogSetServlet">
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="25%" align="right" height="22">类别编号：</td>
            <td width="50%" align="left" height="22">
				<input type="text" name="setCode" size="40" class="noemptyInput" style="width:100%" value="<%=eamDhCatalogSet.getSetCode()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">类别名称：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="setName" size="40" style="width:100%" value="<%=eamDhCatalogSet.getSetName()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="25%" align="right" height="22">失效日期：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="endDate" size="40" style="width:100%" value="<%=eamDhCatalogSet.getEndDate()%>"
                 title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(endDate,'')">
        </tr>
        
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDZYH(); return false;">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="catlogSetId" value="<%=eamDhCatalogSet.getCatlogSetId()%>">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script>

function initPage(){
}

function do_SaveDZYH() {
	var fieldNames = "setCode;setName";
	var fieldLabels = "类别编号;类别名称";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.catlogSetId.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_Back(){
	with(mainFrm){
		setCode.value = "";
		setName.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>