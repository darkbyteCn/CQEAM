<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-3-31
  Time: 11:14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();

		String transferType = headerDTO.getTransferType();
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse:collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input name="transTypeValue" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
					<td align=right width=8%>创建日期：</td>
			        <td width=17%>
						<input name="creationDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreationDate()%>" >
					</td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectFrmGroup();" >
					</td>
			    </tr>
			    <tr>
			        <td align=right width=8%>经办人：</td>
			        <td width=17%>
						<input name="created" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreated()%>">
					</td>
			        <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>部门名称：</td>
			        <td width=17%>
						<input name="userDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>"></td>
			        <td height="20">
					<p align="right">资产帐簿：</td>
			        <td height="20">
						<input name="bookTypeName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getBookTypeName()%>" ></td>
			    </tr>
<%
		if(!transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
			    <tr>
					<td align=right width=8%>手机号码：</td>
			        <td width=17%>
						<input name="phoneNumber" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getPhoneNumber()%>"></td>
			        <td align=right>电子邮件：</td>
			        <td>
						<input name="email" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getEmail()%>"></td>
					<td align=right width=8%>调出部门：</td>
			        <td width=17%>
						<input name="fromDept" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
					</td>
			        <td align=right >实物大类：</td>
			        <td><input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20"></td>
                </tr>
			    <tr>
			    	<td align=right>紧急程度：</td>
			        <td >
			        	<input name="emergentLevel" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" class="input_style2" value="<%=headerDTO.getEmergentLevel()%>">
			        </td>
					<td align=right width=8% align="right" height="40">调拨说明：　</td>
			        <td colspan="5"  height="40"><textarea name="createdReason" style="width:100%;height:100%" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  rows="1" cols="20"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
<%
		} else {
%>
			    <tr>
					<td align=right width=8%>手机号码：</td>
			        <td width=17%>
						<input name="phoneNumber" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getPhoneNumber()%>"></td>
			        <td align=right>电子邮件：</td>
			        <td>
						<input name="email" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getEmail()%>"></td>
			        <td align=right>调出公司：</td>
			        <td>
						<input name="fromCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>调入公司：　</td>
			        <td width=17%>
						<input name="toCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getToCompanyName()%>">
					</td>
			    </tr>
			    <tr>
					<td width=8% height="40" align=right>实物大类：</td>
			        <td><input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20"></td>
                    <td align=right >附件张数：</td>
			        <td><input name="accessSheet" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getAccessSheet()%>"></td>
                    <td width=8% height="40"align=right>调拨说明：</td>
			        <td width=67% height="40" colspan="5"><textarea name="createdReason" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" rows="1" cols="20"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
<%
		}
%>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="fromDept" value="<%=headerDTO.getFromDept()%>">
<input type="hidden" name="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="isThred" value="<%=headerDTO.getThred()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
