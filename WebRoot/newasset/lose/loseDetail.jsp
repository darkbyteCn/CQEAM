<%@page import="com.sino.ams.newasset.lose.constant.LoseURLListConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@page import="com.sino.ams.newasset.lose.dto.LoseHeaderDTO"%>
<%@page import="com.sino.ams.newasset.lose.dto.LoseDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %> 
<html>
<%  
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	
	LoseHeaderDTO headerDTO = null;
	LoseDTO loseDTO = null;
	loseDTO = (LoseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = loseDTO.getHeaderDTO();
    DTOSet lines = loseDTO.getLines(); 
    String transId = headerDTO.getTransId();
%>
<head>
	<title>资产挂失单</title>
	 
    <script type="text/javascript" src="/WebLibary/js/test.js"></script> 
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
	<script type="text/javascript" src="/WebLibary/js/api.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	
	<style >
		FORM {
		    margin-top: 0;
		    margin-bottom: 0;
		}
	</style>
	<script type="text/javascript">
	    printToolBar();
	</script>
	
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" >

<jsp:include page="/message/MessageProcess"/>

<form action="<%= LoseURLListConstant.LOSE_QUERY_SERVELT %>" method="post" name="mainFrm" >
<input type="hidden" name="act" value="DETAIL_ACTION" />
<input type="hidden" name="transId" value="<%=headerDTO.getTransId() %>">
<div id="searchDiv" style="position:absolute;top:28px;left:1px;width:100%">
	<jsp:include page="/newasset/lose/loseHeader.jsp"></jsp:include> 
</div>
<!-- <fieldset style="border:1px solid #397DF3; position:absolute;top:143px;width:100%;height:70%"> -->
<%--
<fieldset style="border:0; position:relative;width:100%;height:80%">
    <legend>
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="do_Close(); return false;">
	</legend> 
</fieldset>
 --%>
<jsp:include page="/newasset/lose/loseLine.jsp">
	<jsp:param name="pageType" value="1" />
</jsp:include>  
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function initPage() {
    window.focus();
	do_SetPageWidth(); 
	setFrmReadonly("mainFrm");
	do_ControlProcedureBtn();
	//var createdReason = document.getElementById( "createdReason" );
	//createdReason.className = "input_style2";
} 
  
/**
 *功能:设置表单内所有元素不可用
 *参数:表单名
 */
function setFrmReadonly(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
    	var obj = frmObj.elements[i];
    	var objType = obj.type;
        var fieldType = obj.type;
        obj.readOnly = true; 
        if( objType == "text" || objType == "password" || objType == "textarea" ){
        	obj.onclick = null;
        } 
        
        if( fieldType == "checkbox" ){
        	obj.disabled = true;
        }
    }
}

</script>