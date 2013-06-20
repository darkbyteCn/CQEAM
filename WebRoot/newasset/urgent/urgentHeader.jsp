<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO"%>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@page import="com.sino.framework.security.bean.SessionUtil"%>
<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil
			.getUserAccount(request);

	UrgentHeaderDTO headerDTO = null;
	UrgentDTO urgentDTO = null;
	urgentDTO = (UrgentDTO) request
			.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);

	headerDTO = urgentDTO.getHeaderDTO();
	
	String mustStyle = "noEmptyInput"; 
%>
<table width=100% border="0">
	<tr>
		<td align=right width=8%>
			单据号：
		</td>
		<td width=13%>
			<input type="text" name="transNo" class="input_style2" readonly
				style="width: 85%" value="<%=headerDTO.getTransNo()%>">
		</td>
		<td align=right width=8%>
			单据类型：
		</td>
		<td width=13%>
			<input type="text" name="transTypeValue" class="input_style2"
				readonly style="width: 85%"
				value="<%=headerDTO.getTransTypeValue()%>">
		</td>   
		<td align=right width=8%>
			单据状态：
		</td>
		<td width=20%>
			<input type="text" name="transStatusName" class="input_style2"
				readonly style="width: 90%"
				value="<%=headerDTO.getTransStatusName()%>">
		</td>  
	</tr>
	<tr>
		<td align=right width=8%>
			创建部门：
		</td>
		<td width=13%>
			<input name="fromDeptName" class="input_style2" readonly
				style="width: 85%;" value="<%=headerDTO.getFromDeptName()%>">
		</td> 
		<td align=right width=8%>
			创建日期：
		</td>
		<td width=13%>
			<input name="creationDate" class="input_style2" readonly
				style="width: 85%;" value="<%=headerDTO.getCreationDate()%>">
		</td>
		<td align=right width=8%>
			创建人：
		</td>
		<td width=13%>
			<input type="text" name="created" class="input_style2" readonly
				style="width: 90%" value="<%=headerDTO.getCreated()%>">
		</td> 
	</tr>
	<tr>
		<td align=right width=8%>
			调出地点：
		</td>
		<td width=13%>
			<input type="text" name="fromObjectName" class="<%= mustStyle %>" readonly onClick="do_SelectFromLocation()" title="点击选择或更改调出地点" 
				style="width: 85%" value="<%=headerDTO.getFromObjectName()%>">
		</td> 
		<td align=right>
			调出执行人： 
		</td>
		<td>
			<input name="implementByName" class="<%= mustStyle %>" readonly onClick="do_SelectImplement()" title="点击选择或更改调出执行人" 
				style="width: 85%;" value="<%=headerDTO.getImplementByName()%>">
		</td>
		<td align=right width=8%>
			归档人：
		</td>
		<td >
			<input name="archivedByName" class="<%= mustStyle %>" readonly onClick="do_SelectUser(3)" title="点击选择或更改归档人" 
				style="width: 90%;" value="<%=headerDTO.getArchivedByName()%>">
		</td> 
	</tr>
	<tr>
		<td align=right width=8%>
			调入地点： 
		</td>
		<td width=13%>
			<input type="text" name="toObjectName" class="<%= mustStyle %>" readonly onClick="do_SelectToLocation()" title="点击选择或更改调入地点" style="width: 85%" value="<%=headerDTO.getToObjectName()%>">
			<a href= "#" onClick="do_ChoseLocDesc()" title = "点机选择或更改组合调入地点" class="linka" >[…]</a>
		</td> 
		<td align=right>
			调入执行人： 
		</td>
		<td>
			<input name="toImplementByName" class="<%= mustStyle %>" readonly onClick="do_SelectToImplement()" title="点击选择或更改调入执行人"
				style="width: 85%;" value="<%=headerDTO.getToImplementByName()%>">
		</td>
	</tr>
	<tr> 
		<td width="8%" height="60" align="right">
			备注：
		</td>
		<td  height="60" colspan="7">
			<textarea name="createdReason" style="width: 100%; height: 100%"
				class="input_style1"><%=headerDTO.getCreatedReason()%></textarea>
		</td>
	</tr>
</table>