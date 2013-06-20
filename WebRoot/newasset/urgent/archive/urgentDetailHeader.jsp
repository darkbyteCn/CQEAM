<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%> 
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.framework.security.bean.SessionUtil"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil
			.getUserAccount(request);

	UrgentHeaderDTO headerDTO = null;
	UrgentDTO urgentDTO = null;
	urgentDTO = (UrgentDTO) request
			.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);

	headerDTO = urgentDTO.getHeaderDTO();
%>
<table width=100% border="0">
	<tr>
		<td align=right width=8%>
			单据号：
		</td>
		<td width=13%>
			<input type="text" name="transNo" class="input_style1" readonly
				style="width: 85%" value="<%=headerDTO.getTransNo()%>">
		</td>
		<td align=right width=8%>
			单据类型：
		</td>
		<td width=13%>
			<input type="text" name="transTypeValue" class="input_style1"
				readonly style="width: 85%"
				value="<%=headerDTO.getTransTypeValue()%>">
		</td>   
		<td align=right width=8%>
			单据状态：
		</td>
		<td width=20%>
			<input type="text" name="transStatusName" class="input_style1"
				readonly style="width: 90%"
				value="<%=headerDTO.getTransStatusName()%>">
		</td>  
	</tr>
	<tr>
		<td align=right width=8%>
			创建部门：
		</td>
		<td width=13%>
			<input name="fromDeptName" class="input_style1" readonly
				style="width: 85%;" value="<%=headerDTO.getFromDeptName()%>">
		</td> 
		<td align=right width=8%>
			创建日期：
		</td>
		<td width=13%>
			<input name="creationDate" class="input_style1" readonly
				style="width: 85%;" value="<%=headerDTO.getCreationDate()%>">
		</td>
		<td align=right width=8%>
			创建人：
		</td>
		<td width=13%>
			<input type="text" name="created" class="input_style1" readonly
				style="width: 90%" value="<%=headerDTO.getCreated()%>">
		</td> 
	</tr>
	<tr>
		<td align=right width=8%>
			调出地点：
		</td>
		<td width=13%>
			<input type="text" name="fromObjectName" class="input_style1" readonly  
				style="width: 85%" value="<%=headerDTO.getFromObjectName()%>">
		</td> 
		<td align=right>
			调出执行人： 
		</td>
		<td>
			<input name="implementByName" class="input_style1" readonly  
				style="width: 85%;" value="<%=headerDTO.getImplementByName()%>">
		</td>
		<td align=right width=8%>
			归档人：
		</td>
		<td >
			<input name="archivedByName" class="input_style1" readonly
				style="width: 90%;" value="<%=headerDTO.getArchivedByName()%>">
		</td> 
	</tr>
	<tr>
		<td align=right width=8%>
			调入地点： 
		</td>
		<td width=13%>
			<input type="text" name="toObjectName" class="input_style1" readonly 
				style="width: 85%" value="<%=headerDTO.getToObjectName()%>">
		</td> 
		<td align=right>
			调入执行人： 
		</td>
		<td>
			<input name="toImplementByName" class="input_style1" readonly 
				style="width: 85%;" value="<%=headerDTO.getToImplementByName()%>">
		</td>
	</tr>
	<tr> 
		<td width="8%" height="60" align="right">
			备注：
		</td>
		<td  height="60" colspan="7">
			<textarea name="createdReason" readonly style="width: 100%; height: 100%"
				class="input_style1"><%=headerDTO.getCreatedReason()%></textarea>
		</td>
	</tr>
</table>