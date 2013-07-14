<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.dto.*"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
	<head>
		<%--
            Function:		地市下发盘点任务
         --%>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	<script type="text/javascript" src="/yearchecktaskmanager/yeartchecktask.js"></script>
		<%
		    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
		    AssetsYearCheckTaskBaseDateDTO cityBaseDate =(AssetsYearCheckTaskBaseDateDTO) request.getAttribute("CITY_BASE_DATE");
			String cityBaseDateIsExists = (String) request.getAttribute("CITY_BASE_DATE_IS_EXISTS");
			AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) request.getAttribute("ORDER_HEAD_DATA");
			AssetsYearCheckTaskBaseDateDTO baseDateDTO = (AssetsYearCheckTaskBaseDateDTO) request.getAttribute("ORDER_BASE_DATE");
			DTOSet lineSet = (DTOSet) request.getAttribute("ORDER_LINE_DATA");
		%>
	</head>

	<body leftmargin="0" topmargin="0"
		onkeydown="autoExeFunction('do_Search();')"
		onload="initPage();helpInit('4.4.4');">
		<input type="hidden" name="helpId" value="">
		<jsp:include page="/message/MessageProcess" />
		<jsp:include page="/public/exportMsg.jsp" />
		
		<script>
		 printTitleBar("地市下发盘点任务");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskCityServlet">
  			<input type="hidden" name="act" value="">
  			<input type="hidden" name="baseDateId" value="<%=baseDateDTO.getBaseDateId() %>"/>
  			<input type="hidden" name="cityBaseDateIsExists" value="<%=cityBaseDateIsExists %>"/>
			<table border="1" topmargin="0" width="100%" bordercolor="#666666" id="table3" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<tr>
				<td colspan="6" class="mainTitleClass" ><B>&nbsp;任务配置信息-->非实地资产下发规则</B></td>
			</tr>
			</table>
			<table width="100%" topmargin="0" border="1" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<tr>
			    <td align=right width="15%">
						[软件类]下发方式：
			   </td>
			  <td width="20%">
			     <input name="softWareMethodName" id="softWareMethodName" class="input_style2" readonly style="width: 95%;" value="<%=cityBaseDate.getSoftWareMethodName() %>">
			  </td>
			  <td align=right width="15%">
						[客户端]下发方式：
			   </td>
			  <td width="20%">
			  	<input name="clientMethodName" id="clientMethodName" class="input_style2" readonly style="width: 95%;" value="<%=cityBaseDate.getClientMethodName() %>">
			  </td>
			  <td align=right width="15%">
						[光缆、杆路及管道类]下发方式：
			   </td>
			  <td width="20%">
			  	<input name="pipeLineMethodName" id="pipeLineMethodName" class="input_style2" readonly style="width: 95%;" value="<%=cityBaseDate.getPipeLineMethodName() %>">
			  </td>
			  <td>
			      <input type="hidden" name="softWareMethod" value="<%=cityBaseDate.getSoftWareMethod() %>"/>
  			      <input type="hidden" name="clientMethod" value="<%=cityBaseDate.getClientMethod() %>"/>
  			     <input type="hidden" name="pipeLineMethod" value="<%=cityBaseDate.getPipeLineMethod() %>"/>
			    
			  </td>
			</tr>
			<tr>
				<td colspan="6">
				<table border="1" topmargin="0" width="100%" bordercolor="#666666" id="table3" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			      <tr>
				   <td class="mainTitleClass" ><B>&nbsp;任务基本信息-->1.下发非实地;2.下发实地</B></td>
			     </tr>
			     </td>
			   </table>
			</tr>
			<tr>
					<td align=right width="15%">
						下发人：
					</td>
					<td width="20%">
						<input name="createPerson" class="input_style2" readonly style="width: 95%;" value="<%=userAccount.getUsername() %>">
					</td>

					<td align=right width="15%">
						资产类型：
					</td>
					<td width="20%">
						<select name="assetsType" id="assetsType"  style="width: 95%;" class="select_style1"><%=request.getAttribute("ASSETS_CLASS")%></select>
					</td>
					<td align=right width="15%">
						非实地资产种类：
					</td>
					<td width="20%">
						<select name="nonAddressCategory" id="nonAddressCategory" style="width: 95%;" class="select_style1"><%=request.getAttribute("NON_ADDRESS_CATEGORY")%></select>
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						省基准日期间 从：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2" 
							name="checkBaseDateFrom" value="<%=baseDateDTO.getCheckBaseDateFrom() %>"
							title="点击选择起始日期" readonly/>
					</td>
					<td width="15%" align="right">
						到：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"
							name="checkBaseDateEnd" id="checkBaseDateEnd" value="<%=baseDateDTO.getCheckBaseDateEnd() %>" readonly/>
					</td>
					<td width="15%" align="right">
						地市基准日：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkBaseDateCity" id="checkBaseDateCity" value="<%=cityBaseDate.getCheckBaseDateCity() %>"
							title="点击选择日期" readonly />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						任务执行期间 从：
					</td>
					<td width="90%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkTaskDateFrom" value="<%=baseDateDTO.getCheckTaskDateFrom() %>"
							readonly/>
					</td>
					
					<td width="10%" align="right">
						到：
					</td>
					<td width="90%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkTaskDateEnd" value="<%=baseDateDTO.getCheckTaskDateEnd() %>"
						    readonly/>
					</td>
					<td align=right width="15%">
						父级任务编号：
					</td>
					<td width="20%">
						<input name="parentOrderNumber" id="parentOrderNumber" class="finputNoEmpty"  value="<%=headerDTO.getParentOrderNumber() %>"
						 readonly style="width: 95%;" value="" onclick="do_selectParentOrder(); return false;">
						
					</td>
				</tr>
				
		    </table>
			
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:181px;left:0px;width:100%">
		    <table class="eamHeaderTable" border="1" width="100%">
		        <tr height=23px onClick="executeClick(this)">
		            <td align=center width="15%">盘点任务编码</td>
		            <td align=center width="15%">任务名称</td>
		            <td align=center width="35%">接收部门</td>
		            <td align=center width="15%">任务执行人</td>
		            <td align=center width="15%">任务类型</td>
					<td style="display:none">隐藏域字段</td>
		        </tr>
		    </table>
		</div>
         
    <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:205px;left:0px;height:310px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
            	if (lineSet == null || lineSet.isEmpty()) {
            %>
            <tr id="model" class="dataTR" style="display:none">
			    <td width="15%" style="cursor:pointer" ><input type="text" name="orderNumber" id="orderNumber0" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="orderName" id="orderName0"  class="finput" readonly/></td>
                <td width="35%" style="cursor:pointer" ><input type="text" name="implementDeptName" id="implementDeptName0"   readonly class="finput"/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="implementName" id="implementName0"   readonly class="finput" /> </td>
	            <td width="15%" style="cursor:pointer" ><input type="text" name="orderType" id="orderType0"   class="finput" readonly/></td>
	            <td style="display:none">
	                <input type="hidden" name="implementBy" id="implementBy0" value=""/>
	                <input type="hidden" name="implementOrganizationId" id="implementOrganizationId0" value=""/>
	                <input type="hidden" name="implementDeptId" id="implementDeptId0" value=""/>
	            </td>
             </tr>
            <%
                    }else{
                    	AssetsYearCheckTaskLineDTO lineDTO = null;
                        for (int i = 0; i < lineSet.getSize(); i++) {
                            lineDTO = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
            %>
              <tr id="model" class="dataTR">
			    <td width="15%" style="cursor:pointer" ><input type="text" name="orderNumber" id="orderNumber<%=i%>" value="<%=lineDTO.getOrderNumber() %>" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="orderName" id="orderName<%=i%>"  value="<%=lineDTO.getOrderName() %>" class="finput" readonly/></td>
                <td width="35%" style="cursor:pointer" ><input type="text" name="implementDeptName" id="implementDeptName<%=i%>" value="<%=lineDTO.getImplementDeptName() %>"  readonly class="finput"/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="implementName" id="implementName<%=i%>"  value="<%=lineDTO.getImplementName() %>" readonly class="finput"/> </td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="orderTypeName" id="orderTypeName0" value="<%=lineDTO.getOrderTypeName() %>"  class="finput" readonly/></td>
                
	            <td style="display:none">
	                <input type="hidden" name="implementBy" id="implementBy<%=i%>" value=""/>
	                <input type="hidden" name="implementOrganizationId" id="implementOrganizationId<%=i%>" value=""/>
	                <input type="hidden" name="implementDeptId" id="implementDeptId<%=i%>" value=""/>
	                <input type="hidden" name="orderType" id="orderType<%=i%>"  class="finput" readonly/></td>
	            </td>
            </tr>
            <%
                }
            }
        %>
        </table>
    </div>  
 </form>
</body>
	<iframe width=174 height=189 name="gToday:normal:calendar.js"
		id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;">
	</iframe>
</html>

<script language="javascript">
function initPage(){
		do_SetPageWidth();
	}
</script>