<%@page import="com.sino.ams.newasset.urgenttrans.constant.UrgentURLListConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %> 
<html>
<%  
	//SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	UrgentHeaderDTO headerDTO = null;
	UrgentDTO leaseDTO = null;
	leaseDTO = (UrgentDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = leaseDTO.getHeaderDTO();
    String transId = headerDTO.getTransId(); 
%>
<head>
	<title>资产紧急调拨单</title>  
</head>
<body leftmargin="0" topmargin="0" onload="initPage();"  >
<form action="<%= UrgentURLListConstant.URGENT_ARCHIVE_SERVELT %>" method="post" name="mainFrm"> 
<input type="hidden" name="act" value="<%= AssetsActionConstant.ARCHIVE_ACTION %>">  
<input type="hidden" name="transId"  value="<%= transId %>" >
<jsp:include page="/message/MessageProcess"/>
<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<jsp:include page="/newasset/urgent/archive/urgentDetailHeader.jsp"></jsp:include> 
		</td>
	</tr>
</table>  
<fieldset style="border:1px solid #397DF3; position:absolute;top:143px;width:100%;height:70%">
    <legend>  
    	 <img src="/images/eam_images/archive.jpg" alt="归档" onClick="do_Archive(); return false;">
	</legend> 
	<jsp:include page="/newasset/urgent/archive/urgentLine.jsp"></jsp:include> 
</fieldset>
</form>

<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<script type="text/javascript">
function initPage() {
	do_SetPageWidth();	 
}  

function do_Archive(){
	document.forms[0].submit();
}
</script>