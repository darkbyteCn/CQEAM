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
		<%=WebConstant.WAIT_TIP_MSG%>
		<input type="hidden" name="helpId" value="">
		<jsp:include page="/message/MessageProcess" />
		<jsp:include page="/public/exportMsg.jsp" />
		
		<script>
		 printTitleBar("部门下发盘点任务");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskDeptServlet">
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
					<%
						if(!headerDTO.getParentOrderNumber().equals("")){
					%>
					<td width="20%">
						<input name="parentOrderNumber" id="parentOrderNumber" class="finputNoEmpty" value ="<%=headerDTO.getParentOrderNumber() %>"
						 readonly style="width: 95%;" >
						
					</td>
					<%
						}else{
					%>
					<td width="20%">
						<input name="parentOrderNumber" id="parentOrderNumber" class="finputNoEmpty" 
						 readonly style="width: 95%;" value="" onclick="do_selectParentOrder(); return false;">
						
					</td>
					<%
						}
					%>
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
						<input style="width: 95%" type="text" class="finputNoEmpty"  id="parentOrderName"
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
						<input style="width: 95%" type="text" class="finputNoEmpty"  id="parentOrderTypeName"
							name="parentOrderTypeName" value="<%=headerDTO.getParentOrderTypeName() %>" readonly/>
						<input type="hidden" name="parentOrderType" id="parentOrderType" value="<%=headerDTO.getParentOrderType() %>"/>
					</td>
					
				</tr>
				
		    </table>
			
        <div id="buttonDiv" style="position:absolute;top:110px;left:1px;width:100%">
           <img src="/images/eam_images/choose.jpg" alt="选择地市部门" onClick="do_selectDeptOrPerson(); return false;">
           <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
           <img src="/images/eam_images/send.jpg" alt="下发盘点任务" onClick="do_send(); return false;">
         </div>
         
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:131px;left:0px;width:100%">
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
         
    <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:155px;left:0px;height:310px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
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

	function checkMyForm(){
       	var parentOrderNumber = document.getElementById("parentOrderNumber").value;
       	if( parentOrderNumber== ""){
           	alert("父级编号不能为空！");
           	return false;
       	}
        return true;
	}
	//下发盘点任务
	function do_send(){
		if(!checkMyForm()){
			return;
	    }
       	var rows = dataTable.rows;
       	var tr = rows[0];
    	if(rows){
    		if(rows.length == 0 ||(rows.length == 1 && tr.style.display == "none")){
				alert("请添加任务执行人，在下发！");
				return;
    		}
    	}
	    document.mainFrm.act.value = "DO_SEND";
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.submit();
        
	}
	//选择父级编号
	function do_selectParentOrder(){
		//alert("111");
		var dialogWidth = 52;
		var dialogHeight = 29;
		var lookUpName = "LOOK_UP_PARENT_ORDER_LEVE_3";
		var userPara=""
		var datas = lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (datas) {
			 var obj = datas[0];
			 document.getElementById("parentOrderNumber").value=obj["parentOrderNumber"];
			 document.getElementById("parentOrderName").value=obj["parentOrderName"];
			 document.getElementById("parentOrderTypeName").value=obj["parentOrderTypeName"];
			 document.getElementById("parentOrderType").value=obj["parentOrderType"];
			} 
	}
	/**
	*根据选择类型，决定查询
	*/
	function do_selectDeptOrPerson(){

       	var parentOrderNumber = document.getElementById("parentOrderNumber").value;
       	var parentOrderType =   document.getElementById("parentOrderType").value;
       	if(parentOrderNumber=="" ){
			alert("请选择父级任务编号，在添加任务接收人！");
			return false;
        }
		var lookUpName = "";
        	if(parentOrderType == "<%=AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE%>" ){
				//软件类
        		 //alert("非实地资产，资产种类[软件类],查找执行人");
        		 lookUpName = "DEPT_NON_ADDRESS_SOFTEWARE";
            }else if(parentOrderType == "<%=AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT%>" ){
				//客户端类
            	// alert("非实地资产,资产种类[客户端类]，查找执行人");
            	 lookUpName = "DEPT_NON_ADDRESS_CLIENT";
            }else if(parentOrderType == "<%=AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE%>" ){
            	// alert("非实地资产,资产种类[光缆、杆路及管道]，查执行人");
            	 lookUpName = "DEPT_NON_ADDRESS_PIPELINE";
            }
            else if(parentOrderType == "<%=AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS%>" ){
            	//实地无线
        	 //alert("实地无线资产，查找查找接收人");
        	  lookUpName = "DEPT_ADDRESS_FOR_CHECK_PERSON";
            }else if(parentOrderType == "<%=AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS%>"){
            	//实地非无线
        	   //alert("实地非无线资产，查找执行人");
        	   lookUpName = "DEPT_ADDRESS_FOR_DEPT";
           }
        
        var dialogWidth = 52;
		var dialogHeight = 29;
		var userPara="";
		var assets = lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (assets) {
				for (var i = 0; i < assets.length; i++) {
					data = assets[i];
					//for(prop in data){
					//	alert(prop+","+data[prop]);
				    //}
					appendDTO2Table(dataTable, data, false, "ImplementBy");
				}
		}
		 
	}

	/**
	  * 功能：选择任务执行人
	 */
	function do_selectCityPerson() {
		var dialogWidth = 52;
		var dialogHeight = 29;
		var lookUpName = "LOOK_UP_CITY_MANAGER";
		var userPara="";
		var assets = lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (assets) {
				for (var i = 0; i < assets.length; i++) {
					data = assets[i];
					appendDTO2Table(dataTable, data, false, "companyCode");
				}
		} 
	}

	//删除行
	function deleteLine() {
	    var tab = document.getElementById("dataTable");
	    deleteTableRow(tab, 'subCheck');
	}
	
</script>