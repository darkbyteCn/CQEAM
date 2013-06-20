<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.dzyh.dto.EamDhBillHDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>低值易耗信息新增页面</title>
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
    EamDhBillHDTO eamDhBillDto = (EamDhBillHDTO)request.getAttribute(WebAttrConstant.DZYH_DATA);
    
    String billStatus=(String)request.getAttribute(WebAttrConstant.DZYH_BILL_STATUS_OPT);
	String organization=(String)request.getAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EamDhBillHServlet">
    <table border="0" width="100%" id="table1">
    <tr><td>&nbsp;</td></tr>
        <tr>
            <td width="25%" align="right" height="22">公司：</td>
            <td width="50%" align="left" height="22">
				<select name="orgId" style="width:100%" class="noemptyInput"><%=organization%></select>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">单据编号：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="billNo" size="40" class="noemptyInput" style="width:100%" value="<%=eamDhBillDto.getBillNo()%>"></td>
            <td width="25%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="25%" align="right" height="22">单据状态：</td>
            <td width="50%" align="left" height="22">
                <select name="billStatus" style="width:100%" class="noemptyInput"><%=billStatus %></select>
        </tr>
        
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDZYH(); return false;">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="billHeaderId" value="<%=eamDhBillDto.getBillHeaderId()%>">
</form>
</body>
<script>

function initPage(){
}

function do_SaveDZYH() {
	var fieldNames = "orgId;billNo;billStatus";
	var fieldLabels = "公司;单据编号;单据状态";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.billHeaderId.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_Back(){
	with(mainFrm){
		orgId.value = "";
		billNo.value = "";
		billStatus.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>