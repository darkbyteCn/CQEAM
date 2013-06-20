<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseHeaderDTO"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %> 
<html>
<%  
	LeaseHeaderDTO headerDTO = null;
	LeaseDTO leaseDTO = null;
	leaseDTO = (LeaseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	headerDTO = leaseDTO.getHeaderDTO();
%>
<head>
	<title>资产续租单据打印页面</title>	 
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
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" >
<form name="mainFrm" >
<jsp:include page="/newasset/lease/leaseHeader.jsp" flush="true"/>
<jsp:include page="/newasset/approveContent.jsp" flush="true"/>
<fieldset style="border:0; position:relative;width:100%;height:80%">
    <legend>
		<table width="100%" id="buttonSet">
		    <tr>
		    	<td width="100%" align="left">
			        <img src="/images/eam_images/page_config.jpg" alt="页面设置" onClick="do_SetupPrint(); return false;">
			        <img src="/images/eam_images/print_view.jpg" alt="打印预览" onClick="do_PrevPrint(); return false;">
			        <img src="/images/eam_images/print.jpg" alt="打印" onClick="do_PrintOrder(); return false;">
			        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close(); return false;">
		    	</td>
		    </tr>
		</table>
    </legend>
	<jsp:include page="/newasset/lease/leasePrintLine.jsp" flush="true"/> 
</fieldset> 
</form>
<object id="webbrowser" width="0" height="0" classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
</body>
</html>
<script type="text/javascript">
function initPage() {
    window.focus();
	do_SetPageWidth(); 
	setFrmReadonly("mainFrm");
	var createdReason = document.getElementById( "createdReason" );
	createdReason.className = "input_style2";
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


function do_SetupPrint(){
	webbrowser.execwb(8,0);
}

function do_PrevPrint(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(7,0);
	document.getElementById("buttonSet").style.display = "block";
}

function do_PrintOrder(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(6,6);
	document.getElementById("buttonSet").style.display = "block";
}

function do_Close(){
	window.opener=null;
	window.close();
}
</script>