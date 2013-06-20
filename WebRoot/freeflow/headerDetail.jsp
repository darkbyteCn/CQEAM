<%@ page import="com.sino.ams.freeflow.FreeFlowDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	FreeFlowDTO headerDTO = (FreeFlowDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	if(transType.equals(AssetsDictConstant.ASS_RED)){//资产调拨单
		String transferType = headerDTO.getTransferType();
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
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
						<input name="userDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getUserDeptName()%>"></td>
			        <td height="20">
					<p align="right">资产账簿：</td>
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
						<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
					</td>
			        <td align=right >资产大类：</td>
			        <td><input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20"></td>
			    </tr>
			    <tr>
					<td align=right width=8% align="right" height="40">调拨说明：　</td>
			        <td colspan="7"  height="40"><textarea name="createdReason" style="width:100%;height:100%" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  rows="1" cols="20"><%=headerDTO.getCreatedReason()%></textarea></td>
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
					<td width=8% height="40" align=right>资产大类：</td>
			        <td><input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20"></td>
			        <td width=8% height="40"align=right>调拨说明：</td>
			        <td width=67% height="40" colspan="6"><textarea name="createdReason" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" rows="1" cols="20"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
<%
		}
%>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="fromDept" value="<%=headerDTO.getFromDept()%>">
<%
	} else if(transType.equals(AssetsDictConstant.ASS_DIS)){//资产报废单
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input type="text" name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input type="text" name="transTypeValue" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectCreateGroup();" ></td>
			        <td align=right width=8%>报废部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
			        </td>
			    </tr>
			    <tr>
			        <td align=right width=8%>申请人：</td>
			        <td width=17%>
						<input type="text" name="created1" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreated()%>" >
					</td>
			        <td align=right>申请日期：</td>
			        <td>
						<input name="creationDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>资产大类：</td>
			        <td width=17%>
			        	<input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20">
			        </td>
			    </tr>
			    <tr>
			    	<td width="8%" height="60" align="right">报废说明：</td>
			    	<td width="92%" height="60" colspan="7"><textarea name="createdReason" readonly style="border-style:solid; border-width:0px; width:100%;height:100%; background-color:#F2F9FF"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<%
	} else if(transType.equals(AssetsDictConstant.ASS_CLR)){//资产处置单
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input type="text" name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input type="text" name="transTypeValue" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectCreateGroup();" ></td>
			        <td align=right width=8%>处置部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
			        </td>
			    </tr>
			    <tr>
			        <td align=right width=8%>申请人：</td>
			        <td width=17%>
						<input type="text" name="created1" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreated()%>" >
					</td>
			        <td align=right>申请日期：</td>
			        <td>
						<input name="creationDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>资产大类：</td>
			        <td width=17%>
			        	<input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20">
			        </td>
			    </tr>
			    <tr>
			    	<td width="8%" height="60" align="right">处置说明：</td>
			    	<td width="92%" height="60" colspan="7"><textarea name="createdReason" readonly style="border-style:solid; border-width:0px; width:100%;height:100%; background-color:#F2F9FF"><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<%
	} else if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置单
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input type="text" name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input type="text" name="transTypeValue" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
                    <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromCompanyName()%>"></td>
                    <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectFrmGroup();" ></td>

			    </tr>
			    <tr>
			        <td align=right width=8%>申请人：</td>
			        <td width=17%>
						<input type="text" name="created1" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreated()%>" >
					</td>
   			        <td align=right>电子邮箱：</td>
			        <td>
						<input name="email1" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getEmail()%>" size="20"></td>
                    <td align=right width=8%>部门名称：</td>
			        <td width=17%>
						<input name="userDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getUserDeptName()%>" size="20"></td>
                    <td align=right>申请日期：</td>
			        <td>
						<input name="creationDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreationDate()%>" ></td>
			    </tr>
                <tr>
                    <td align="right">资产账薄：</td>
			        <td >
						<input name="bookTypeName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"value="<%=headerDTO.getBookTypeName()%>" ></td>
                    <td align=right width=8%>资产大类：</td>
			        <td width=17%>
			        	<input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20">
			        </td>
                    <td align=right width=8%>闲置部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
			        </td>
                    <td align=right width=8%>附件张数：</td>
			        <td width=17%>
						<input name="accessSheet" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getAccessSheet()%>">
			        </td>
                </tr>
                <tr>
			    	<td width="8%" height="55" align="right">闲置说明：</td>
			    	<td width="92%" height="55" colspan="7"><textarea name="createdReason" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" ><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<%
	} else if(transType.equals(AssetsDictConstant.ASS_SHARE)){//资产共享单
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input type="text" name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input type="text" name="transTypeValue" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransTypeValue()%>">
					</td>
                    <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromCompanyName()%>"></td>
                    <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectFrmGroup();" ></td>

			    </tr>
			    <tr>
			        <td align=right width=8%>申请人：</td>
			        <td width=17%>
						<input type="text" name="created1" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreated()%>" >
					</td>
   			        <td align=right>电子邮箱：</td>
			        <td>
						<input name="email1" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getEmail()%>" size="20"></td>
                    <td align=right width=8%>部门名称：</td>
			        <td width=17%>
						<input name="userDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getUserDeptName()%>" size="20"></td>
                    <td align=right>申请日期：</td>
			        <td>
						<input name="creationDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getCreationDate()%>" ></td>
			    </tr>
                <tr>
                    <td align="right">资产账薄：</td>
			        <td >
						<input name="bookTypeName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"value="<%=headerDTO.getBookTypeName()%>" ></td>
                    <td align=right width=8%>资产大类：</td>
			        <td width=17%>
			        	<input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20">
			        </td>
                    <td align=right width=8%>共享部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
			        </td>
                    <td align=right width=8%>附件张数：</td>
			        <td width=17%>
						<input name="accessSheet" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getAccessSheet()%>">
			        </td>
                </tr>
                <tr>
			    	<td width="8%" height="55" align="right">共享说明：</td>
			    	<td width="92%" height="55" colspan="7"><textarea name="createdReason" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" ><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<%
	} else if(transType.equals(AssetsDictConstant.ASS_SUB)){//资产减值单
%>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=17%>
						<input name="transNo" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getTransNo()%>">
					</td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=17%>
						<input name="transTypeValue" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getTransTypeValue()%>">
					</td>
					<td align=right width=8%>创建日期：</td>
			        <td width=17%>
						<input name="creationDate" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>" ></td>
			        <td align=right width=8%>建单组别：</td>
			        <td width=17%>
						<input name="fromGroupName" class="fDtlInput" readonly style="width:100%" value="<%=headerDTO.getFromGroupName()%>"></td>
			    </tr>
			    <tr>
			        <td align=right width=8%>经办人：</td>
			        <td width=17%>
						<input name="created" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getCreated()%>">
					</td>
			        <td align=right>公司名称：</td>
			        <td>
						<input name="userCompanyName" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getFromCompanyName()%>"></td>
					<td align=right width=8%>部门名称：</td>
			        <td width=17%>
						<input name="userDeptName" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getUserDeptName()%>" size="20"></td>
			        <td height="20">
					<p align="right">资产账簿：</td>
			        <td height="20">
						<input name="bookTypeName" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getBookTypeName()%>" ></td>
			    </tr>
			    <tr>
					<td align=right width=8%>手机号码：</td>
			        <td width=17%>
						<input name="phoneNumber1" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getPhoneNumber()%>" size="20"></td>
			        <td align=right>电子邮件：</td>
			        <td>
						<input name="email1" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getEmail()%>" size="20"></td>
					<td align=right width=8%>减值部门：</td>
			        <td width=17%>
						<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFromDeptName()%>">
					</td>
			        <td align=right >资产大类：</td>
			        <td width=17%>
			        	<input name="faContentName" class="readonlyInput" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getFaContentName()%>" size="20">
			        </td>
			    </tr>
			    <tr>
					<td width=8% height="40" align=right>损耗名称：</td>
			        <td  height="40"><input name="lossesName" class="fDtlInput" style="width:100%; " value="<%=headerDTO.getLossesName()%>" size="20"></td>
			        <td  height="40" align=right>损耗日期：</td>
			        <td  height="40"><input name="lossesDate" class="fDtlInput" readonly style="width:100%; " value="<%=headerDTO.getLossesDate()%>" size="20"></td>
			        <td  height="40" align=right>减值说明：</td>
			        <td colspan="3"  height="40"><textarea name="createdReason" style="width:100%;height:100%" class="fDtlInput" readonly><%=headerDTO.getCreatedReason()%></textarea></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<%
	}
%>