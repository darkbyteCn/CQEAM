<%@page import="com.sino.ams.newasset.lease.constant.LeaseURLListConstant"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseLineDTO"%>
<%@page import="com.sino.ams.newasset.lease.constant.LeaseAppConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="java.util.List"%>
<%@ page import="com.sino.ams.system.user.dto.*"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseHeaderDTO"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %> 
<html>
<%  
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	
	LeaseHeaderDTO headerDTO = null;
	LeaseDTO leaseDTO = null;
	leaseDTO = (LeaseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = leaseDTO.getHeaderDTO();
    DTOSet lines = leaseDTO.getLines(); 
    String transId = headerDTO.getTransId();
%>
<head>
	<title>资产续租单</title>
	 
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
<form action="<%= LeaseURLListConstant.LEASE_QUERY_SERVELT %>" method="post" name="mainFrm" >
<input type="hidden" name="act" value="DETAIL_ACTION" />
<input type="hidden" name="transId" value="<%=headerDTO.getTransId() %>">

<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<jsp:include page="/newasset/lease/leaseHeader.jsp"></jsp:include> 
		</td>
	</tr>
</table> 

<%--
<jsp:include page="/newasset/approveContent.jsp" flush="true"/>
<div id="searchDiv" style="width:100%">
<img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="window.close(); return false;">
</div>
<fieldset style="border:1px solid #397DF3; position:absolute;width:100%;height:80%">
    <legend>
        
    </legend>
 --%>   
<!-- <fieldset style="border:1px solid #397DF3; position:absolute;top:143px;width:100%;height:70%"> -->
	<jsp:include page="/newasset/lease/leaseLine.jsp">
		<jsp:param name="pageType" value="1" />
	</jsp:include> 
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</form>
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
        	obj.onclick = function(){
        		return false;
        	};
        	
        	obj.onblur = function(){
        		return false;
        	};
        	 
        	if( obj.className == "noEmptyInput" ){
        		obj.className = "finput2";
        	}
        } 
        
        if( fieldType == "checkbox" || obj.tagName == "SELECT" ){
        	obj.disabled = true;
        }
    }
}

</script>