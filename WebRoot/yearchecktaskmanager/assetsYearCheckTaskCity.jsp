<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.dto.*"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
	<head>
		<%--
            Function:		�����·��̵�����
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
		 printTitleBar("�����·��̵�����");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskCityServlet">
  			<input type="hidden" name="act" value="">
  			<input type="hidden" name="baseDateId" value="<%=baseDateDTO.getBaseDateId() %>"/>
  			<input type="hidden" name="cityBaseDateIsExists" value="<%=cityBaseDateIsExists %>"/>
			<table border="1" topmargin="0" width="100%" bordercolor="#666666" id="table3" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<tr>
				<td colspan="6" class="mainTitleClass" ><B>&nbsp;����������Ϣ-->��ʵ���ʲ��·�����</B></td>
			</tr>
			</table>
			<table width="100%" topmargin="0" border="1" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			<%
					if(cityBaseDate==null){
			%>
			<tr>
			    <td align=right width="15%">
						[�����]�·���ʽ��
			   </td>
			  <td width="20%">
				<select name="softWareMethod" id="softWareMethod"  style="width: 95%;" class="select_style1"><%=request.getAttribute("SOFT_METHOD")%></select>
			  </td>
			  <td align=right width="15%">
						[�ͻ���]�·���ʽ��
			   </td>
			  <td width="20%">
				<select name="clientMethod" id="clientMethod"  style="width: 95%;" class="select_style1"><%=request.getAttribute("CLIENT_METHOD")%></select>
			  </td>
			  <td align=right width="15%">
						[���¡���·���ܵ���]�·���ʽ��
			   </td>
			  <td width="20%">
				<select name="pipeLineMethod" id="pipeLineMethod"  style="width: 95%;" class="select_style1"><%=request.getAttribute("PIPELINE_METHOD")%></select>
			  </td>
			</tr>
			<%
					}else{
			%>
			<tr>
			    <td align=right width="15%">
						[�����]�·���ʽ��
			   </td>
			  <td width="20%">
			     <input name="softWareMethodName" id="softWareMethodName" class="input_style2" readonly style="width: 95%;" value="<%=cityBaseDate.getSoftWareMethodName() %>">
			  </td>
			  <td align=right width="15%">
						[�ͻ���]�·���ʽ��
			   </td>
			  <td width="20%">
			  	<input name="clientMethodName" id="clientMethodName" class="input_style2" readonly style="width: 95%;" value="<%=cityBaseDate.getClientMethodName() %>">
			  </td>
			  <td align=right width="15%">
						[���¡���·���ܵ���]�·���ʽ��
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
			<%
					}
			%>
			<tr>
				<td colspan="6">
				<table border="1" topmargin="0" width="100%" bordercolor="#666666" id="table3" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
			      <tr>
				   <td class="mainTitleClass" ><B>&nbsp;���������Ϣ</B></td>
			     </tr>
			     </td>
			   </table>
			</tr>
			<tr>
					<td align=right width="15%">
						�·��ˣ�
					</td>
					<td width="20%">
						<input name="createPerson" class="input_style2" readonly style="width: 95%;" value="<%=userAccount.getUsername() %>">
					</td>

					<td align=right width="15%">
						�ʲ����ͣ�
					</td>
					<td width="20%">
						<select name="assetsType" id="assetsType"  style="width: 95%;" class="select_style1"><%=request.getAttribute("ASSETS_CLASS")%></select>
					</td>
					<td align=right width="15%">
						��ʵ���ʲ����ࣺ
					</td>
					<td width="20%">
						<select name="nonAddressCategory" id="nonAddressCategory" style="width: 95%;" class="select_style1"><%=request.getAttribute("NON_ADDRESS_CATEGORY")%></select>
					</td>
				</tr>
				<tr>
					<td width="15%" align="right">
						ʡ��׼���ڼ� �ӣ�
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2" 
							name="checkBaseDateFrom" value="<%=baseDateDTO.getCheckBaseDateFrom() %>"
							title="���ѡ����ʼ����" readonly/>
					</td>
					<td width="15%" align="right">
						����
					</td>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"
							name="checkBaseDateEnd" id="checkBaseDateEnd" value="<%=baseDateDTO.getCheckBaseDateEnd() %>" readonly/>
					</td>
					<td width="15%" align="right">
						���л�׼�գ�
					</td>
					<%
					if(cityBaseDate!=null){
					%>
					<td width="20%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkBaseDateCity" id="checkBaseDateCity" value="<%=cityBaseDate.getCheckBaseDateCity() %>"
							title="���ѡ������" readonly />
					</td>
					<%
					}else{
					%>
					<td width="20%">
						<input style="width: 95%" type="text" class="finputNoEmpty"  
							name="checkBaseDateCity" value=""
							title="���ѡ������" readonly onclick="gfPop.fPopCalendar(checkBaseDateCity)"/>
					</td>
					<%
					}
					%>
				</tr>
				
				<tr>
					<td width="10%" align="right">
						����ִ���ڼ� �ӣ�
					</td>
					<td width="90%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkTaskDateFrom" value="<%=baseDateDTO.getCheckTaskDateFrom() %>"
							title="���ѡ����ʼ����" readonly/>
					</td>
					
					<td width="10%" align="right">
						����
					</td>
					<td width="90%">
						<input style="width: 95%" type="text" class="input_style2"  
							name="checkTaskDateEnd" value="<%=baseDateDTO.getCheckTaskDateEnd() %>"
							title="���ѡ���������" readonly/>
					</td>
					<td align=right width="15%">
						���������ţ�
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
						<input name="parentOrderNumber" id="parentOrderNumber" class="finputNoEmpty"  value="<%=fromremainParentOrderNumber %>"
						 readonly style="width: 95%;" >
						
					</td>
					<% 
					  }
					%>
				</tr>
				
		    </table>
			
        <div id="buttonDiv" style="position:absolute;top:180px;left:1px;width:100%">
           <img src="/images/eam_images/choose.jpg" alt="ѡ����в���" onClick="do_selectDeptOrPerson(); return false;">
           <img src="/images/eam_images/delete_line.jpg" alt="ɾ����" onClick="deleteLine(); return false;">
           <img src="/images/eam_images/send.jpg" alt="�·��̵�����" onClick="do_send(); return false;">
         </div>
         
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:201px;left:0px;width:100%">
		    <table class="eamHeaderTable" border="1" width="100%">
		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="���ȫѡ��ȡ��ȫѡ">
		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
		            <td align=center width="15%">�̵��������</td>
		            <td align=center width="15%">��������</td>
		            <td align=center width="35%">���ղ���</td>
		            <td align=center width="15%">����ִ����</td>
		            <td align=center width="15%">��������</td>
					<td style="display:none">�������ֶ�</td>
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

	//ѡ��������������
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
			alert("�����ֶβ���Ϊ�գ�");
			return false;
        }
        if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS%>" ){
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory == ""){
        		alert("�ʲ�����ѡ��[��ʵ���ʲ�]ʱ����ʵ���ʲ����಻��Ϊ�գ�");
    			return felse;
            }
        }else{
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory != ""){
					alert("ʵ���ʲ�������ѡ��[��ʵ���ʲ�����]��");
					return;
            }
        }
        return true;
	}
	//�·��̵�����
	function do_send(){
		 var startDate=document.mainFrm.checkBaseDateFrom.value;
	     var endDate=document.mainFrm.checkBaseDateEnd.value;
	     var cityBaseDate=document.mainFrm.checkBaseDateCity.value;

	     if(startDate<cityBaseDate&&cityBaseDate<endDate){
		 }else {
			alert("���л�׼�ձ�����ʡ��׼���ڼ���");
			return;
	     }
		
		if(!checkMyForm()){
			return;
	    }
       	var rows = dataTable.rows;
       	var tr = rows[0];
    	if(rows){
    		if(rows.length == 0 ||(rows.length == 1 && tr.style.display == "none")){
				alert("���������ִ���ˣ����·���");
				return;
    		}
    	}
	    document.mainFrm.act.value = "DO_SEND";
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.submit();
        
	}
	//ѡ�񸸼����
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
	/**
	*����ѡ�����ͣ�������ѯ
	*/
	function do_selectDeptOrPerson(){
		var cityBaseDate = document.getElementById("checkBaseDateCity").value;
       	var assetsType = document.getElementById("assetsType").value;
       	var parentOrderNumber = document.getElementById("parentOrderNumber").value;

       	var softWareMethod = document.getElementById("softWareMethod").value;
       	var clientMethod = document.getElementById("clientMethod").value;
       	var pipeLineMethod = document.getElementById("pipeLineMethod").value;
       	
		if(softWareMethod=="" ||clientMethod=="" || pipeLineMethod=="" ){
			alert("�״��·�����ǰ����������[��ʵ���ʲ��·�����]��");
			return false;
		}
       	
       	if(cityBaseDate == "" || assetsType=="" || parentOrderNumber=="" ){
			alert("����д��Ҫ�ֶκ��������������ˣ�");
			return false;
        }
		if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS%>" ){
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory == ""){
        		alert("�ʲ�����ѡ��[��ʵ���ʲ�]ʱ����ʵ���ʲ����಻��Ϊ�գ�");
    			return felse;
            }
        }else{
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory != ""){
					alert("ʵ���ʲ�������ѡ��[��ʵ���ʲ�����]��");
					return;
            }
        }
		var lookUpName = "";
        if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS%>" ){ //��ʵ���ʲ�
        	var nonAddressCategory = document.getElementById("nonAddressCategory").value;
        	if(nonAddressCategory == "<%=AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_SOFTWIRE%>" ){
				//�����
				if(softWareMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
					alert("��ʵ���ʲ����ʲ�����[�����],�·���ʽ[���й���Ա],����[���й���Ա]");
					lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_1";
				}else if(softWareMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
					 alert("��ʵ���ʲ����ʲ�����[�����],�·���ʽ[�����ʲ�����Ա],����[�����ʲ�����Ա]");
	        		 lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_2";
				}else if( softWareMethod =="<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
					alert("��ʵ���ʲ����ʲ�����[�����],�·���ʽ[�ʲ������˻��ض���Ա],����[�ʲ������˻��ض���Ա]");
	        		 lookUpName = "CITY_NON_ADDRESS_SOFTEWARE_3";
				}
        		
            }else if(nonAddressCategory == "<%=AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_CLIENT%>" ){
				//�ͻ�����
            	if(clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
					alert("��ʵ���ʲ�,�ʲ�����[�ͻ�����],�·���ʽ[���й���Ա],����[���й���Ա]");
					lookUpName = "CITY_NON_ADDRESS_CLIENT_1";
				}else if(clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
					 alert("��ʵ���ʲ�,�ʲ�����[�ͻ�����],�·���ʽ[�����ʲ�����Ա],����[�����ʲ�����Ա]");
	        		 lookUpName = "CITY_NON_ADDRESS_CLIENT_2";
				}else if( clientMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
					alert("��ʵ���ʲ�,�ʲ�����[�ͻ�����],�·���ʽ[�ʲ������˻��ض���Ա],����[�ʲ������˻��ض���Ա]");
	        		 lookUpName = "CITY_NON_ADDRESS_CLIENT_3";
				}
            }else if(nonAddressCategory == "<%=AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_PIPELINE%>" ){
                //
            	if(pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER%>" ){
					alert("��ʵ���ʲ�,�ʲ�����[���¡���·���ܵ�],�·���ʽ[���й���Ա],����[���й���Ա]");
					lookUpName = "CITY_NON_ADDRESS_PIPELINE_1";
				}else if(pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER%>" ){
					 alert("��ʵ���ʲ�,�ʲ�����[���¡���·���ܵ�],�·���ʽ[�����ʲ�����Ա],����[�����ʲ�����Ա]");
	        		 lookUpName = "CITY_NON_ADDRESS_PIPELINE_2";
				}else if( pipeLineMethod == "<%=AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON%>" ){
					alert("��ʵ���ʲ�,�ʲ�����[���¡���·���ܵ�],�·���ʽ[�ʲ������˻��ض���Ա],����[�ʲ������˻��ض���Ա]");
	        		 lookUpName = "CITY_NON_ADDRESS_PIPELINE_3";
				}
            }
        }else if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_WIRELESS%>" ){
            	//ʵ������
        	 alert("ʵ�������ʲ�������[�̵�������]");
        	  lookUpName = "CITY_ADDRESS_FOR_CHECK_PERSON";
        	 
        }else if(assetsType == "<%=AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_NON_WIRELESS%>"){
            	//ʵ�ط�����
        	   alert("ʵ�ط������ʲ�������[�����ʲ�����Ա]");
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
	  * ���ܣ�ѡ������ִ����
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

	//ɾ����
	function deleteLine() {
	    var tab = document.getElementById("dataTable");
	    deleteTableRow(tab, 'subCheck');
	}
	
</script>