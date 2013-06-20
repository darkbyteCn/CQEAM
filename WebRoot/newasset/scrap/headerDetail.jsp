<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
%>  
<table border="0"class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input type="text" name="transNo" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input type="text" name="transTypeValue" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectCreateGroup();" ></td>
			        <td align=right width=8%>报废部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getFromDeptName()%>">
			        </td>
			    </tr>
			    <tr>
			        <td align=right width=8%>申请人：</td>
			        <td width=17%>
						<input type="text" name="created1" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getCreated()%>" >
					</td>
			        <td align=right>申请日期：</td>
			        <td>
						<input name="creationDate" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>资产种类：</td>
			        <td width=17%> 
			        	<input name="faContentName" readonly style="width:100%" class="select_style1" value="<%=headerDTO.getFaContentName()%>" size="20">
			        </td>
			    </tr>
			    <tr>
                    <td align=right width=8%>附件张数：</td>
			        <td width=17%><input name="accessSheet" readonly style=" width:100%" class="input_style2" value="<%=headerDTO.getAccessSheet()%>"></td>
                    <td width="8%" height="60" align="right">报废说明：</td>
			    	<td width="92%" height="60" colspan="6"><textarea name="createdReason" readonly style=" width:100%" class="input_style2"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table> 