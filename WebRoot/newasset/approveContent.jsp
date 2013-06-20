<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>

	<table border="1" width="100%" bordercolor="#226E9B">
		<tr class="headTR">
			<td align=center width="15%" height="20">审批人(办理人)</td>
			<td align=center width="22%" height="20">审批时间</td>
			<td align=center width="63%" height="20">审批意见</td>
		</tr>
<%
	RowSet rs = (RowSet) request.getAttribute(AssetsWebAttributes.APPROVE_CONTENT);
	if (rs != null && !rs.isEmpty()) {
		String userName = "";
		String agentUser = "";
		for (int i = 0; i < rs.getSize(); i++) {
			Row row = rs.getRow(i);
			userName = row.getStrValue("USER_NAME");
			agentUser = row.getStrValue("AGENT_USER_NAME");
			if(!agentUser.equals("")){
				userName += "(" + agentUser + ")";
			}
%>
		<tr>
			<td align=center width="15%"><input type="text" class="finput" readonly value="<%=userName%>"></td>
			<td align=center width="22%"><input type="text" class="finput2" readonly value="<%=row.getValue("APPROVE_TIME")%>"></td>
			<td align=center width="63%"><input type="text" class="finput" readonly value="<%=row.getValue("APPROVE_CONTENT")%>"></td>
		</tr>
<%
		}
	}
%>
</table>
