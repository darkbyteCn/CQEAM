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
			
			String fromremainParentOrderNumber = (String)request.getAttribute("fromRemain_parentOrderNumber");
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
		 printTitleBar("地市下发盘点任务");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskCityServlet">
  			<input type="hidden" name="act" value="">
  			<input type="hidden" name="stepFlag" value="1"/>
  			<input type="hidden" name="rowsCount" value="0"/>
  			<input type="hidden" name="baseDateId" value="<%=baseDateDTO.getBaseDateId() %>"/>
  			<input type="hidden" name="cityBaseDateIsExists" value="<%=cityBaseDateIsExists %>"/>
			<table border="1" topmargin="0" width="100%" bordercolor="#666666" id="table3" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<tr>
				<td class="mainTitleClass" ><B>&nbsp;任务基本信息</B></td>
			</tr>
			</table>
			<table width="100%" topmargin="0" border="1" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<!-- 
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
				 -->
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
					<%
					if(cityBaseDate!=null){
					%>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkBaseDateCity" id="checkBaseDateCity" value="<%=cityBaseDate.getCheckBaseDateCity() %>"
							title="点击选择日期" readonly />
					</td>
					<%
					}else{
					%>
					<td width="20%">
						<input style="width: 95%" type="text" class="finputNoEmpty"  
							name="checkBaseDateCity" value=""
							title="点击选择日期" readonly onclick="gfPop.fPopCalendar(checkBaseDateCity)"/>
					</td>
					<%
					}
					%>
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
						父级任务编号：
					</td>
					<%
					  if(fromremainParentOrderNumber ==null){
					%>
					<td width="20%">
						<input name="parentOrderNumber" id="parentOrderNumber" class="finputNoEmpty"  value="<%=headerDTO.getParentOrderNumber() %>"
						 readonly style="width: 95%;" onclick="do_selectParentOrder(); return false;">
						
					</td>
					<%
					  }else{
					%>
					<td width="20%">
						<input name="parentOrderNumber" id="parentOrderNumber" class="input_style2"  value="<%=fromremainParentOrderNumber %>"
						 readonly style="width: 95%;" >
						
					</td>
					<% 
					  }
					%>
				</tr>
			  <tr>
				 <td colspan="6">
				 <table border="1" topmargin="0" width="100%" bordercolor="#666666" id="table3" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			      <tr>
				   <td colspan="6" class="mainTitleClass" ><B>&nbsp;任务配置信息-->任务下发和规则</B></td>
			     </tr>
			     </td>
			   </table>
			</tr>
			<tr>
				 <td colspan="6">
				 <table width="100%" topmargin="0" border="1" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			    <tr>
				<td colspan="2" align="right" width="20%">
					 <input type="button" name="bnonasoft"  value="非实地软件任务添加" onClick="do_selectRec('1');return false;"/>
				</td>
				<td  colspan="2" align="right" width="20%">
					<input type="button" name="bnonapipe" disabled="true" value="非实地管线任务添加" onClick="do_selectRec('2');return false;"/>
				</td>
				<td  colspan="2" align="right" width="20%"> 
					<input type="button" name="bnonaclient" disabled="true" value="非实地客户端任务添加" onClick="do_selectRec('3');return false;"/>
				</td>
				<td  colspan="2" align="right" width="20%"> 
					<input type="button" name="baw" disabled="true" value="实地无线任务添加" onClick="do_selectRec('4');return false;"/>
				</td>
				<td  colspan="2" align="right" width="20%"> 
					<input type="button" name="banw" disabled="true" value="实地非无线任务添加" onClick="do_selectRec('5');return false;"/>
				</td>
			</tr>
			<%
					if(cityBaseDate==null){
			%>
			<tr>
			    <td align=right width="10%">
						非实地[软件类]：
			   </td>
			  <td width="10%">
				<select name="softWareMethod" id="softWareMethod"  class="select_style1" onchange="do_selectChange('NON-ADDRESS-SOFTWARE'); return false;" ><%=request.getAttribute("SOFT_METHOD")%></select>
			  </td>
			  <td align="right" width="10%">
						非实地[管线类]：
			   </td>
			  <td  width="10%">
				<select name="pipeLineMethod" id="pipeLineMethod" disabled="true" class="select_style1" onchange="do_selectChange('NON-ADDRESS-PIPELINE'); return false;" ><%=request.getAttribute("PIPELINE_METHOD")%></select>
			  </td>
			  <td align=right width="10%">
						非实地[客户端]：
			   </td>
			  <td width="10%">
				<select name="clientMethod" id="clientMethod"  disabled="true"  class="select_style1" onchange="do_selectChange('NON-ADDRESS-CLIENT'); return false;" ><%=request.getAttribute("CLIENT_METHOD")%></select>
			  </td>
			  <td align="right" width="10%">
			             实地[无线类]:
			  </td>
			  <td width="10%">
			  	<input name="addressWireLess" class="input_style2" value="按盘点责任人"  readonly  >
			  </td>
			  <td align="right" width="10%">
			               实地[非无线类]:
			  </td>
			  <td width="10%">
			  	<input name="addressNonWireLess" class="input_style2" value="按部门资产管理员"  readonly  >
			  </td>
			</tr>
			<%
					}else{
			%>
			<tr>
			    <td align="right" width="10%">
						非实地[软件类]：
			   </td>
			  <td width="10%">
			     <input name="softWareMethodName" id="softWareMethodName" class="input_style2" readonly  value="<%=cityBaseDate.getSoftWareMethodName() %>">
			  </td>
			  <td align="right" width="10%">
						非实地[管线类]：
			   </td>
			  <td align="right"  width="10%">
			  	<input name="pipeLineMethodName" id="pipeLineMethodName" class="input_style2" readonly  value="<%=cityBaseDate.getPipeLineMethodName() %>">
			  </td>
			  <td align="right" width="10%">
						非实地[客户端]：
			   </td>
			  <td width="10%">
			  	<input name="clientMethodName" id="clientMethodName" class="input_style2" readonly  value="<%=cityBaseDate.getClientMethodName() %>">
			  </td>
			  <td width="10%">
			             实地[无线类]:
			  </td>
			  <td align="right" width="10%">
			  	<input name="addressWireLess" class="input_style2" value="按盘点责任人"  readonly  >
			  </td>
			  <td width="10%">
			               实地[非无线类]:
			  </td>
			  <td width="10%">
			  	<input name="addressNonWireLess" class="input_style2" value="按部门资产管理员"  readonly  >
			  </td>
			  <td style="display:none">
			      <input type="hidden" name="softWareMethod" value="<%=cityBaseDate.getSoftWareMethod() %>"/>
  			      <input type="hidden" name="clientMethod" value="<%=cityBaseDate.getClientMethod() %>"/>
  			     <input type="hidden" name="pipeLineMethod" value="<%=cityBaseDate.getPipeLineMethod() %>"/>
			    
			  </td>
			</tr>
			<%
					}
			%>
			     </table>
			</tr>
			
		    </table>
			
        <div id="buttonDiv" style="position:absolute;top:190px;left:1px;width:100%">
           <!-- 
           <img src="/images/eam_images/choose.jpg" alt="选择地市部门" onClick="do_selectDeptOrPerson(); return false;">
           -->
           <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
           <img src="/images/eam_images/confirm.jpg" alt="确认" onClick="do_save(); return false;">
           <img src="/images/eam_images/send.jpg" alt="下发盘点任务" onClick="do_send(); return false;">
        </div>
         
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:211px;left:0px;width:100%">
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
         
    <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:225px;left:0px;height:310px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
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
	                <input type="hidden" name="orderType" id="orderType<%=i %>" value=""/>
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

	//选择盘任务点接收人
	function do_SelectPerson(lineBox){
		var objName = lineBox.name;
		var objId = lineBox.id;
		var idNumber = objId.substring(objName.length);
		var companyCode=document.getElementById("companyCode" + idNumber).value;
		var lookUpName = "LOOK_UP_MANAGER";
		var userPara="companyCode="+companyCode;
		var dialogWidth = 50;
		var dialogHeight = 28;
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
		if (objs) {
			var obj = objs[0];
			document.getElementById("receivdBy" + idNumber).value=obj["userId"];
			document.getElementById("receivdByName" + idNumber).value=obj["userName"];
		}
	}

	function checkMyForm(){
		var cityBaseDate = document.getElementById("checkBaseDateCity").value;
       	var assetsType = document.getElementById("assetsType").value;
       	var parentOrderNumber = document.getElementById("parentOrderNumber").value;
       	
       	if(cityBaseDate == "" || assetsType=="" || parentOrderNumber=="" ){
			alert("必填字段不能为空！");
			return false;
        }
        if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS%>" ){
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory == ""){
        		alert("资产类型选择[非实地资产]时，非实地资产种类不能为空！");
    			return felse;
            }
        }else{
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory != ""){
					alert("实地资产，无需选择[非实地资产种类]！");
					return;
            }
        }
        return true;
	}
	//保存
	function do_save(){
		var stepFlag = document.mainFrm.stepFlag.value;//下发顺序标识
		var bnonasoft = document.mainFrm.bnonasoft ;//1
		var bnonapipe = document.mainFrm.bnonapipe ;//2
		var bnonaclient =  document.mainFrm.bnonaclient;//3
	    var baw = document.mainFrm.baw;//4
		var banw =  document.mainFrm.banw;//5

		var softWareMethod = document.mainFrm.softWareMethod;//软件类select
		var pipeLineMethod = document.mainFrm.pipeLineMethod;//管线类select
		var clientMethod = document.mainFrm.clientMethod ;//客户端select
		
		//var rowsCount = document.mainFrm.rowsCount.value;//任务行数
	  	//var rows = dataTable.rows;
      	//var tr = rows[0];
	   //	if(rows){
	   	//	if(rows.length == 0 ||(rows.length == 1 && tr.style.display == "none")){
		//			alert("请添加任务后，在确认！");
		//			return;
	   	//	}else{
	   	//		document.mainFrm.rowsCount.value = rows.length;
		//   	}
	   //	}	
	   		
		if(stepFlag == "1"){
			bnonasoft.disabled = "true";
			softWareMethod.disabled = "true";
			bnonapipe.disabled = "";
			pipeLineMethod.disabled = "";
			document.mainFrm.stepFlag.value = "2";
		}else if(stepFlag == "2"){
			bnonapipe.disabled = "true";
			pipeLineMethod.disabled = "true";
			bnonaclient.disabled = "";
			clientMethod.disabled = "";
			document.mainFrm.stepFlag.value = "3";
		}else if(stepFlag == "3"){
			bnonaclient.disabled = "true";
			clientMethod.disabled = "true";
			baw.disabled = "";
			document.mainFrm.stepFlag.value = "4";
		}else if(stepFlag =="4"){
			baw.disabled = "true";
			banw.disabled = "";
			document.mainFrm.stepFlag.value = "5";
		}else if(stepFlag =="5"){
			banw.disabled = "true";
			document.mainFrm.stepFlag.value = "finished";
		}
	}
	
	//下发任务
	function do_send(){
		
		var stepFlag=document.mainFrm.stepFlag.value;
		if(stepFlag != "finished"){
			alert("请依次将5种任务全部添加完毕后在下发！");
			return false;
		}
		 
      	var rows = dataTable.rows;
      	var tr = rows[0];
   	    if(rows){
   		  if(rows.length == 0 ||(rows.length == 1 && tr.style.display == "none")){
				alert("请添加任务执行人，在下发！");
				return;
   		   }
   	    }
   	    var softWareMethod = document.mainFrm.softWareMethod;//软件类select
		var pipeLineMethod = document.mainFrm.pipeLineMethod;//管线类select
		var clientMethod = document.mainFrm.clientMethod ;//客户端select
		softWareMethod.disabled = "";
		pipeLineMethod.disabled = "";
		clientMethod.disabled = "";
		
	    document.mainFrm.act.value = "DO_SEND";
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.submit();
       
	}
	//下发盘点任务
	function do_send_bak(){
		 var startDate=document.mainFrm.checkBaseDateFrom.value;
	     var endDate=document.mainFrm.checkBaseDateEnd.value;
	     var cityBaseDate=document.mainFrm.checkBaseDateCity.value;

	     if( cityBaseDate >= startDate&& cityBaseDate <= endDate  ){
		 }else {
			alert("地市基准日必须在省基准日期间内！");
			return;
	     }
		
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
		var lookUpName = "LOOK_UP_PARENT_ORDER_LEVE_2";
		var userPara=""
		var datas = lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (datas) {
			var obj = datas[0];
			document.getElementById("parentOrderNumber").value=obj["parentOrderNumber"];
			} 
		
	}

	//分类添加任务执行人
	function do_selectRec(type){
		var cityBaseDate = document.getElementById("checkBaseDateCity").value;
       	var parentOrderNumber = document.getElementById("parentOrderNumber").value;
       	
       	if(cityBaseDate == "" || parentOrderNumber=="" ){
			alert("请先填写基本信息！");
			return false;
        }
        
        var startDate=document.mainFrm.checkBaseDateFrom.value;
	    var endDate=document.mainFrm.checkBaseDateEnd.value;
	    var cityBaseDate=document.mainFrm.checkBaseDateCity.value;
	    if(cityBaseDate >= startDate&& cityBaseDate <= endDate ){
		}else {
		    alert("地市基准日必须在省基准日期间内！");
			return false;
	    }

	    var softWareMethod = document.getElementById("softWareMethod").value;
       	var clientMethod = document.getElementById("clientMethod").value;
       	var pipeLineMethod = document.getElementById("pipeLineMethod").value;

		var lookUpName = "";
		if(type =="1"){
			if(softWareMethod ==""){
				alert("请先选择下发方式，在进行任务添加！");
				return false;
			}
			if(softWareMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
				//alert("非实地资产，资产种类[软件类],下发方式[地市管理员],查找[地市管理员]");
				lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_1";
			}else if(softWareMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
				// alert("非实地资产，资产种类[软件类],下发方式[部门资产管理员],查找[部门资产管理员]");
        		 lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_2";
			}else if( softWareMethod =="<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
				//alert("非实地资产，资产种类[软件类],下发方式[资产责任人或特定人员],查找[资产责任人或特定人员]");
        		 lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_3";
			}
		}else if(type == "2"){
			if(pipeLineMethod == ""){
				alert("请先选择下发方式，在进行任务添加！");
				return false;
			}
			if(pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
				//alert("非实地资产,资产种类[光缆、杆路及管道],下发方式[地市管理员],查找[地市管理员]");
				lookUpName = "CITY_NON_ADDRESS_PIPELINE_1";
			}else if(pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
				// alert("非实地资产,资产种类[光缆、杆路及管道],下发方式[部门资产管理员],查找[部门资产管理员]");
        		 lookUpName = "CITY_NON_ADDRESS_PIPELINE_2";
			}else if( pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
				//alert("非实地资产,资产种类[光缆、杆路及管道],下发方式[资产责任人或特定人员],查找[资产责任人或特定人员]");
        		 lookUpName = "CITY_NON_ADDRESS_PIPELINE_3";
			}
	    }else if(type == "3"){
		    if(clientMethod == ""){
		    	alert("请先选择下发方式，在进行任务添加！");
				return false;
			 }
	    	if(clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
				//alert("非实地资产,资产种类[客户端类],下发方式[地市管理员],查找[地市管理员]");
				lookUpName = "CITY_NON_ADDRESS_CLIENT_1";
			}else if(clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
				// alert("非实地资产,资产种类[客户端类],下发方式[部门资产管理员],查找[部门资产管理员]");
        		 lookUpName = "CITY_NON_ADDRESS_CLIENT_2";
			}else if( clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
				//alert("非实地资产,资产种类[客户端类],下发方式[资产责任人或特定人员],查找[资产责任人或特定人员]");
        		 lookUpName = "CITY_NON_ADDRESS_CLIENT_3";
			}
		}else if(type == "4"){
		    lookUpName = "CITY_ADDRESS_FOR_CHECK_PERSON";
		}else if(type =="5"){
		    lookUpName = "CITY_ADDRESS_FOR_DEPT";
		}
		
        var dialogWidth = 52;
		var dialogHeight = 29;
		var userPara="";
		var assets = lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (assets) {
			for (var i = 0; i < assets.length; i++) {
				data = assets[i];
			    // for(prop in data){
			    // alert(prop+","+data[prop]);
			    //}
				//orderType implementBy 根据任务类型和接收人过滤，已经添加了，就不用在添加了
				appendDTO2Table_AYC(dataTable, data, false,"implementBy","orderType");
			}
		}
	}
	
	/**
	*根据选择类型，决定查询， 过时方法
	*/
	function do_selectDeptOrPerson(){
		var cityBaseDate = document.getElementById("checkBaseDateCity").value;
       	var assetsType = document.getElementById("assetsType").value;
       	var parentOrderNumber = document.getElementById("parentOrderNumber").value;

       	var softWareMethod = document.getElementById("softWareMethod").value;
       	var clientMethod = document.getElementById("clientMethod").value;
       	var pipeLineMethod = document.getElementById("pipeLineMethod").value;
       	
		if(softWareMethod=="" ||clientMethod=="" || pipeLineMethod=="" ){
			alert("首次下发任务前，请先配置[非实地资产下发规则]！");
			return false;
		}
       	
       	if(cityBaseDate == "" || assetsType=="" || parentOrderNumber=="" ){
			alert("请填写必要字段后，在添加任务接收人！");
			return false;
        }
		if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS%>" ){
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory == ""){
        		alert("资产类型选择[非实地资产]时，非实地资产种类不能为空！");
    			return felse;
            }
        }else{
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory != ""){
					alert("实地资产，无需选择[非实地资产种类]！");
					return;
            }
        }
		var lookUpName = "";
        if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS%>" ){ //非实地资产
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory == "<%=AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_SOFTWIRE%>" ){
				//软件类
				if(softWareMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
					alert("非实地资产，资产种类[软件类],下发方式[地市管理员],查找[地市管理员]");
					lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_1";
				}else if(softWareMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
					 alert("非实地资产，资产种类[软件类],下发方式[部门资产管理员],查找[部门资产管理员]");
	        		 lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_2";
				}else if( softWareMethod =="<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
					alert("非实地资产，资产种类[软件类],下发方式[资产责任人或特定人员],查找[资产责任人或特定人员]");
	        		 lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_3";
				}
        		
            }else if(nonAddressCategory == "<%=AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_CLIENT%>" ){
				//客户端类
            	if(clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
					alert("非实地资产,资产种类[客户端类],下发方式[地市管理员],查找[地市管理员]");
					lookUpName = "CITY_NON_ADDRESS_CLIENT_1";
				}else if(clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
					 alert("非实地资产,资产种类[客户端类],下发方式[部门资产管理员],查找[部门资产管理员]");
	        		 lookUpName = "CITY_NON_ADDRESS_CLIENT_2";
				}else if( clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
					alert("非实地资产,资产种类[客户端类],下发方式[资产责任人或特定人员],查找[资产责任人或特定人员]");
	        		 lookUpName = "CITY_NON_ADDRESS_CLIENT_3";
				}
            }else if(nonAddressCategory == "<%=AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_PIPELINE%>" ){
                //
            	if(pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
					alert("非实地资产,资产种类[光缆、杆路及管道],下发方式[地市管理员],查找[地市管理员]");
					lookUpName = "CITY_NON_ADDRESS_PIPELINE_1";
				}else if(pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
					 alert("非实地资产,资产种类[光缆、杆路及管道],下发方式[部门资产管理员],查找[部门资产管理员]");
	        		 lookUpName = "CITY_NON_ADDRESS_PIPELINE_2";
				}else if( pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
					alert("非实地资产,资产种类[光缆、杆路及管道],下发方式[资产责任人或特定人员],查找[资产责任人或特定人员]");
	        		 lookUpName = "CITY_NON_ADDRESS_PIPELINE_3";
				}
            }
        }else if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_WIRELESS%>" ){
            	//实地无线
        	 alert("实地无线资产，查找[盘点责任人]");
        	  lookUpName = "CITY_ADDRESS_FOR_CHECK_PERSON";
        	 
        }else if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_NON_WIRELESS%>"){
            	//实地非无线
        	   alert("实地非无线资产，查找[部门资产管理员]");
        	   lookUpName = "CITY_ADDRESS_FOR_DEPT";
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
					if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_WIRELESS%>"){
						appendDTO2Table(dataTable, data, false, "implementDeptId");
					}else{
						appendDTO2Table(dataTable, data, false, "ImplementBy");
					}
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

	//当用户改变非实地下发方式时，删除之前已经添加过的该类型的接收人
	function do_selectChange(type){
		//alert("method="+type);
		//删除type 类型的行
		var tab = document.getElementById("dataTable");
		deleteTableRow_AYC(tab, 'subCheck',type);
		
	}
	
</script>