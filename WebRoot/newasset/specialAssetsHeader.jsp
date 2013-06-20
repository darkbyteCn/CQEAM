<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	if(transType.equals(AssetsDictConstant.ASS_RED)){
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input name="transNo" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input name="transTypeValue" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getTransTypeValue()%>">
					</td>
					<td align=right width=8%>创建日期：</td>
			        <td width=17%>
						<input name="creationDate" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" class="noEmptyInput" readonly style="width:100%; cursor:hand" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectCreateGroup();" ></td>
			    </tr>
			    <tr>
			        <td align=right width=8%>经办人：</td>
			        <td width=17%>
						<input name="created" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getCreated()%>">
					</td>
			        <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>部门名称：</td>
			        <td width=17%>
						<input name="userDeptName" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getUserDeptName()%>" size="20"></td>
			        <td height="20">
					<p align="right">资产帐簿：</td>
			        <td height="20">
						<input name="bookTypeName" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getBookTypeName()%>" ></td>
			    </tr>		    
			    <tr>
					<td align=right width=8%>手机号码：</td>
			        <td width=17%>
						<input name="phoneNumber1" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getPhoneNumber()%>" size="20"></td>
			        <td align=right>电子邮件：</td>
			        <td>
						<input name="email1" class="readonlyInput" readonly style="width:100%; " value="<%=headerDTO.getEmail()%>" size="20"></td>
					<td align=right width=8%>调出部门：</td>
			        <td width=17%>
						<select name="fromDept" style="width:100%" class="noEmptyInput" onChange="do_ConfirmChange()"><%=headerDTO.getFromDeptOption()%></select>
					</td>
			        <td align=right >资产类型：</td>
			        <td width=17%>
			        	<select name="assetsType" style="width:100%" class="noEmptyInput" ><%=headerDTO.getAssetsTypeOption()%></select>
			        </td>
			    </tr>
			    <tr>
					<td align=right width=8% align="right" height="40">调拨说明：　</td>
			        <td colspan="7"  height="40"><textarea name="createdReason" style="width:100%;height:100%" class="noEmptyInput" rows="1" cols="20"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			    <input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">
    
			</table>
		</td>
	</tr>
</table>
<%
	}
%>