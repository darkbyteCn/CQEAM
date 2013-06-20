<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseHeaderDTO"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseDTO"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.framework.security.bean.SessionUtil"%>
<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil
			.getUserAccount(request);

	LeaseHeaderDTO headerDTO = null;
	LeaseDTO leaseDTO = null;
	leaseDTO = (LeaseDTO) request
			.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);

	headerDTO = leaseDTO.getHeaderDTO();
	String transStatus = headerDTO.getTransStatus();
%>
<table width=100% border="0">
	<tr>
		<td align=right width=8%>
			单据号：
		</td>
		<td width=13%>
			<input type="text" name="transNo" class="input_style2" readonly
				style="width: 100%" value="<%=headerDTO.getTransNo()%>">
		</td>
		<td align=right width=8%>
			单据类型：
		</td>
		<td width=13%>
			<input type="text" name="transTypeValue" class="input_style2"
				readonly style="width: 100%"
				value="<%=headerDTO.getTransTypeValue()%>">
		</td>  
		<td align=right width=8%>
			公司名称：
		</td>
		<td width=13%>
			<input name="fromCompanyName" class="input_style2" readonly
				style="width: 100%;" value="<%=headerDTO.getFromCompanyName()%>">
		</td> 
		<td align=right width=8%>
			创建日期：
		</td>
		<td width=13%>
			<input name="creationDate" class="input_style2" readonly
				style="width: 80%;" value="<%=headerDTO.getCreationDate()%>">
		</td>
		
	</tr>
	<tr>
		<td align=right width=8%>
			经办人：
		</td>
		<td width=13%>
			<input type="text" name="created" class="input_style2" readonly
				style="width: 100%" value="<%=headerDTO.getCreated()%>">
		</td> 
		<td align=right>
			手机号码：
		</td>
		<td>
			<input name="phoneNumber" class="input_style2" readonly
				style="width: 100%;" value="<%=headerDTO.getPhoneNumber()%>">
		</td>
		<td align=right width=8%>
			电子邮件：
		</td>
		<td >
			<input name="email" class="input_style2" readonly
				style="width: 100%;" value="<%=headerDTO.getEmail()%>">
		</td>
		<td align=right>紧急程度：</td>
        <td >
			<% if ("IN_PROCESS".equals(transStatus)||"APPROVED".equals(transStatus)) {%>
			<select name="emergentLevel" class="select_style1" disabled style="width:80%" ><%=headerDTO.getEmergentLevelOption()%></select>
			<%
	          } else {
	        %>
	        <select name="emergentLevel" class="select_style1" style="width:80%"  onchange="document.getElementById('sf_priority').value = this.value;"><%=headerDTO.getEmergentLevelOption()%></select>
			<%
	          }
	        %>
		</td> 
	</tr>
	<tr> 
		<td width="8%" height="20" align="right">
			说明：
		</td>
		<td  height="40" rowspan="2" colspan="6">
			<textarea name="createdReason" style="width: 95%; height: 100%"
				class="input_style1"><%=headerDTO.getCreatedReason()%></textarea>
		</td><td><font color="red">*</font></td>
	</tr>
	<tr> 
		<td >&nbsp;
		</td>
	</tr>
</table>