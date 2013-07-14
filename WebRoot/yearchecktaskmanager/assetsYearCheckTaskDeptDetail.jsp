<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.dto.*"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
	<head>
		<%--
            Function:部门
         --%>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	<script type="text/javascript" src="/yearchecktaskmanager/yeartchecktask.js"></script>
		<%
		    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
			String cityBaseDate =(String) request.getAttribute("CITY_BASE_DATE");
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
		 printTitleBar("部门下发盘点任务");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskCityServlet">
  			<input type="hidden" name="act" value="">
  			<input type="hidden" name="baseDateId" value="<%=baseDateDTO.getBaseDateId() %>"/>
  			<input type="hidden" name="cityBaseDateIsExists" value="<%=cityBaseDateIsExists %>"/>
			<table width="100%" topmargin="0" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<tr>
					<td align=right width="15%">
						下发人：
					</td>
					<td width="20%">
						<input name="createPerson" class="input_style2" readonly style="width: 95%;" value="<%=userAccount.getUsername() %>">
					</td>
					<td width="15%" align="right">
						本地市盘点基准日：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="cityBaseDate" id="cityBaseDate" value="<%=cityBaseDate %>" readonly />
					</td>
					<td align=right width="15%">
						父级任务编号：
					</td>
					<td width="20%">
						<input name="parentOrderNumber" id="parentOrderNumber" class="input_style2"
						 readonly style="width: 95%;" value="<%=headerDTO.getParentOrderNumber() %>" >
						
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
					    省基准日期间 从：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2" 
							name="checkBaseDateFrom" value="<%=baseDateDTO.getCheckBaseDateFrom() %>" readonly/>
					</td>
					<td width="15%" align="right">
						到：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"
							name="checkBaseDateEnd" id="checkBaseDateEnd" value="<%=baseDateDTO.getCheckBaseDateEnd() %>" readonly/>
					</td>
					<td align=right width="15%">
						父级任务名称：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"  id="parentOrderName"
							name="parentOrderName" value="<%=headerDTO.getParentOrderName() %>" readonly/>
					</td>
				</tr>
				
				<tr>
					<td width="10%" align="right">
						任务执行期间 从：
					</td>
					<td width="90%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkTaskDateFrom" value="<%=baseDateDTO.getCheckTaskDateFrom() %>"
							title="点击选择起始日期" readonly/>
					</td>
					
					<td width="10%" align="right">
						到：
					</td>
					<td width="90%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkTaskDateEnd" value="<%=baseDateDTO.getCheckTaskDateEnd() %>"
							title="点击选择截至日期" readonly/>
					</td>
					<td align=right width="15%">
						父级任务类型：
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"  id="parentOrderTypeName"
							name="parentOrderTypeName" value="<%=headerDTO.getParentOrderTypeName() %>" readonly/>
						<input type="hidden" name="parentOrderType" id="parentOrderType" value="<%=headerDTO.getParentOrderType() %>"/>
					</td>
					
				</tr>
				
		    </table>
			
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:111px;left:0px;width:100%">
		    <table class="eamHeaderTable" border="1" width="100%">
		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
		            <td align=center width="15%">盘点任务编码</td>
		            <td align=center width="15%">任务名称</td>
		            <td align=center width="35%">接收部门</td>
		            <td align=center width="15%">任务执行人</td>
		            <td align=center width="15%">任务类型</td>
					<td style="display:none">隐藏域字段</td>
		        </tr>
		    </table>
		</div>
         
    <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:135px;left:0px;height:310px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
            	if (lineSet == null || lineSet.isEmpty()) {
            %>
            <tr id="model" class="dataTR" style="display:none">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
			    <td width="15%" style="cursor:pointer" ><input type="text" name="orderNumber" id="orderNumber0" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="orderName" id="orderName0"  class="finput" readonly/></td>
                <td width="35%" style="cursor:pointer" ><input type="text" name="implementDeptName" id="implementDeptName0"   readonly class="finput"/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="implementName" id="implementName0"   readonly class="finput" /> </td>
	            <td width="15%" style="cursor:pointer" ><input type="text" name="orderTypeName" id="orderTypeName0"   class="finput" readonly/></td>
	            <td style="display:none">
	                <input type="hidden" name="implementBy" id="implementBy0" value=""/>
	                <input type="hidden" name="implementOrganizationId" id="implementOrganizationId0" value=""/>
	                <input type="hidden" name="implementDeptId" id="implementDeptId0" value=""/>
	                <input type="hidden" name="orderType" id="orderType0" value=""/>
	            </td>
             </tr>
            <%
                    }else{
                    	AssetsYearCheckTaskLineDTO lineDTO = null;
                        for (int i = 0; i < lineSet.getSize(); i++) {
                            lineDTO = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
            %>
              <tr id="model" class="dataTR">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="orderNumber"></td>
			    <td width="15%" style="cursor:pointer" ><input type="text" name="orderNumber" id="orderNumber<%=i%>" value="<%=lineDTO.getOrderNumber() %>" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="orderName" id="orderName<%=i%>"  value="<%=lineDTO.getOrderName() %>" class="finput" readonly/></td>
                <td width="35%" style="cursor:pointer" ><input type="text" name="implementDeptName" id="implementDeptName<%=i%>" value="<%=lineDTO.getImplementDeptName() %>"  readonly class="finput"/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="implementName" id="implementName<%=i%>"  value="<%=lineDTO.getImplementName() %>" readonly class="finput"/> </td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="orderTypeName" id="orderTypeName<%=i%>"  value="<%=lineDTO.getOrderTypeName() %>" class="finput" readonly/></td>
	            <td style="display:none">
	                <input type="hidden" name="implementBy" id="implementBy<%=i%>" value=""/>
	                <input type="hidden" name="implementOrganizationId" id="implementOrganizationId<%=i%>" value=""/>
	                <input type="hidden" name="implementDeptId" id="implementDeptId<%=i%>" value=""/>
	                <input type="hidden" name="orderType" id="orderType0" value=""/>
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
var dataTable = document.getElementById("dataTable");

	function initPage(){
		do_SetPageWidth();
	}

	function do_create(){
		document.mainFrm.act.value = "DO_CREATE";
	    document.mainFrm.submit();
	}
	
</script>