<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transStatus = headerDTO.getTransStatus();
	String transType = headerDTO.getTransType();
    String transTypeValue = headerDTO.getTransTypeValue();
    String orderVerbName = "";
    if(transTypeValue.length()  > 0 && transTypeValue.endsWith("单")){
        orderVerbName = transTypeValue.substring(0, transTypeValue.length() - 1);
    }
%>
<table border="0" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input name="transNo" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
			        	<% if ("ASS-DONATE".equals(transType) || "ASS-SELL".equals(transType)) {%>
			        	<input type="hidden" name="transTypeDefine" value="">
			        	<select name="transTypeValue" style="width:100%" class="select_style1"><%=headerDTO.getTransTypeValue()%></select>
						<%
				          } else {
				        %>
				        <input name="transTypeValue" class="input_style2" readonly style="width:100%; " value="<%=transTypeValue%>">
				        <%
				          }
				        %>
					</td>
					<td align=right width=8%>创建日期：</td>
			        <td width=17%>
						<input name="creationDate" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right width=8%>创 建 人：</td>
			        <td width=17%>
			        	<input name="create" class="input_style2" readonly style="width:100%;" value="<%=headerDTO.getCreated()%>" >
						<input type="hidden" name="fromGroupName" class="input_style2" readonly style="width:100%;" value="<%=headerDTO.getFromGroupName()%>" ></td>
			    </tr>
			    <tr>
			        <td align=right width=8%><%if("ASS-BORROW".equals(transType)) {%>借用人：<%} else if ("ASS-RETURN".equals(transType)){%>送还人：<%} else {%>经办人：<%}%></td>
			        <td width=17%>
						<input name="created" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCreated()%>">
					</td>
			        <td align=right><%if("ASS-BORROW".equals(transType)) {%>借用日期：<%} else if ("ASS-RETURN".equals(transType)){%>送还日期：<%} else {%>经办日期：<%}%></td>
			        <td>
						<input name="createdDate" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>"></td>
					<td align=right width=8%>公司名称：</td>
			        <td width=17%>
                        <input name="userCompanyName" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getFromCompanyName()%>"></td>
                    <td height="20">
                    <p align="right">单据状态：</td>
                    <td height="20">                    	
                        <input name="transStatusDesc" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getTransStatusDesc()%>" size="20"></td>
                </tr>
			    <tr>
					<td align=right width=8%>手机号码：</td>
			        <td width=17%>
						<input name="phoneNumber1" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getPhoneNumber()%>" size="20"></td>
			        <td align=right>电子邮件：</td>
			        <td>
						<input name="email1" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getEmail()%>" size="20"></td>
					<td align=right width=8%>部门：</td>
			        <td width=17%>
			        	<input name="fromDeptName" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getFromDeptName()%>" sleyouize="20"/>
			        	<input type="hidden" name="fromDept" value="<%=headerDTO.getFromDept() %>"/>
						<!--<select name="fromDept" style="width:100%" class="select_style1" onChange="do_ConfirmChange()"><%=headerDTO.getFromDeptOption()%></select>  -->
					</td>
			        <td align=right >资产种类：</td>
			        <td width=17%>
			        	<% if ("IN_PROCESS".equals(transStatus)) {%>
			        	<select name="faContentCode" style="width:100%" disabled class="select_style1"><%=headerDTO.getFaContentOption()%></select>
			        	<%
				           } else {
				        %>
				        <select name="faContentCode" style="width:100%" class="selectNoEmpty"><%=headerDTO.getFaContentOption()%></select>
				        <%
				           }
				        %>
			        </td><td><font color="red">*</font></td>
			    </tr>
			    <tr>
			        <%	if (!"IN_PROCESS".equals(transStatus)) {%>
			        <%  	if (!"APPROVED".equals(transStatus)) {  %>
			        <td align=right >实物管理部门<font color="red">*</font></td>
			        <td>
				        <select name="specialityDept" style="width:100%" class="selectNoEmpty" onmouseover="do_ProcessOptionWidth(this)" ><%=headerDTO.getSpecialityDeptOption()%></select>
			        <% 	   	} 
			        	}
			        %>
			        </td>
			    	
			    	<td align=right>紧急程度：</td>
			        <td >
			        	<% if ("IN_PROCESS".equals(transStatus)) {%>
						<select name="emergentLevel" class="select_style1" disabled style="width:100%" ><%=headerDTO.getEmergentLevelOption()%></select>
						<%
				           } else {
				        %>
				        <select name="emergentLevel" class="select_style1" style="width:100%" onchange="document.getElementById('sf_priority').value = this.value;"><%=headerDTO.getEmergentLevelOption()%></select>
						<%
				           }
				        %>
					</td>
                    <td align=right ><%=orderVerbName%>说明<font color="red">*</font></td>
					<%	if (!"IN_PROCESS".equals(transStatus)) {%>
					<%  	if (!"APPROVED".equals(transStatus)) {  %>
                    <td rowspan="2" colspan="3" height="40"><textarea name="createdReason" class="textareaNoEmpty"><%=headerDTO.getCreatedReason()%></textarea><font color="red">*</font></td>
					<% 		} else { %>
					<td rowspan="2" colspan="5" height="40"><textarea name="createdReason" class="textareaNoEmpty"><%=headerDTO.getCreatedReason()%></textarea><font color="red">*</font></td>
					<%		}
						} else { %>
					<td rowspan="2" colspan="5" height="40"><textarea name="createdReason" class="textareaNoEmpty"><%=headerDTO.getCreatedReason()%></textarea><font color="red">*</font></td>
			    	<% } %>
			    </tr>
			    <tr>
			    	<td>&nbsp;
			    	</td>
			    	<td>&nbsp;
			    	</td>
			    	<td>&nbsp;
			    	</td>
			    </tr>
			</table>
		</td>
	</tr>
</table>