<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/match/prematch/headerInclude.jsp"%>
<%@ include file="/match/prematch/headerInclude.htm"%>

<html>
<body leftmargin="0" topmargin="0">
<%
	String act = request.getParameter("act");
	if(act == null){
		act = "";
	}
%>
<jsp:include page="/message/MessageProcess"/>
<iframe name="amsFrm" src="/servlet/com.sino.ams.prematch.servlet.PaMatchAMSServlet?act=<%=act%>" height="100%" width="50%" scrolling="no"  border="0" frameborder="0"></iframe>
<iframe name="misFrm" src="/servlet/com.sino.ams.prematch.servlet.PaMatchMISServlet?act=<%=act%>" height="100%" width="50%" scrolling="no"  border="0" frameborder="0"></iframe>
<form name="mainFrm" action="/servlet/com.sino.ams.prematch.servlet.AmsPaMatchServlet" method="post">
	<input type="hidden" name="systemId" value="">
	<input type="hidden" name="tagNumber" value="">
	<input type="hidden" name="remark" value="ÊÖ¹¤Æ¥Åä">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>
<script>

function do_ManualMatch(systemId, tagNumber){
	mainFrm.systemId.value = systemId;
	mainFrm.tagNumber.value = tagNumber;
	mainFrm.act.value = "<%=AMSActionConstant.CREATE_ACTION%>";
	mainFrm.submit();
}
</script>