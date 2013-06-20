<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.sinoflow.dto.SfApplicationDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%
	SfApplicationDTO sad = (SfApplicationDTO)request.getAttribute(WebAttrConstant.APP_ATTR);
	if(sad == null){
		sad = new SfApplicationDTO();
	}
	List nl = (List)request.getAttribute("nl");
	if(nl == null)nl = new ArrayList();
%>

<html>
	<head>
		<title>应用定义</title>
        <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css"/>
		<link rel="stylesheet" href="/WebLibary/css/tab.css" type="text/css" />
	    <link rel="stylesheet" href="/WebLibary/css/selectMenu.css" type="text/css" /> 
        <link rel="stylesheet" href="/WebLibary/css/cms_css.css" type="text/css" />
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
		<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
		<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
		<script type="text/javascript" src="/WebLibary/js/jquery-1.2.6.js"></script> 
        <script type="text/javascript" src="/WebLibary/js/BarVar.js"></script> 
              
		<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
		<script type="text/javascript" src="/WebLibary/js/selectMenu.js"></script>
		
		<script language="javascript" type="text/javascript">
		var winstyle = "dialogWidth:20.1;dialogHeight:14.8;center:yes;status:no;help:no";
		    function printTool(){
		        var ArrAction = new Array("取消", "action_cancel.gif","cancel","取消");
		        var ArrAction1 = new Array("保存", "action_save.gif","save","保存");
		        var toolBar = new SinoPrintToolBar();            
		        toolBar.SinoActions = new Array(ArrAction,ArrAction1);
		        toolBar.imagePath = "../images/buttonbar/";
		        toolBar.titleStr = "应用定义";
		        toolBar.print();
		     }
		     printTool();
		</script>
		<script type="text/javascript">	
			function flowEven(tag){//流程事件
				tag == "flow" ? mainFrm.is_Flow_Process[0].checked = true : mainFrm.isFlowProcess[0].checked = true; 
			}
			
			function unFlowEven(tag){//非流程事件
				tag == "flow" ? mainFrm.is_Flow_Process[1].checked = true : mainFrm.isFlowProcess[1].checked = true;
			}
			
			function switchFlow(tag){//选择 流程，非流程
				if(tag == 'Y'){
					nd(unFlowArr);
					println(flowArr,'sm');
				}else if(tag == 'N'){
					nd(flowArr);
					println(unFlowArr,'sm');	
				}
			}
			
			function nd(obj){
				for(var i=0;i<obj.length;i++){
					document.all(obj[i][1]).style.display = "none";
				}
			}
		</script>
	</head>
	<jsp:include page="/message/MessageProcess"/>
	<body>
		<div id="sm" style="background-image:url(/images/table_bg1.jpg);">选项卡</div>
	    <script type="text/javascript">
	    	var yydy = true;
	    	
			<!--选项卡
			var flowArr = new Array(new Array("基本信息","基本信息"),
			                        new Array("工作栏定义","工作栏定义"),
			                        new Array("API定义","API定义"));
			                        
			var unFlowArr = new Array(new Array("非流程应用","非流程应用"));
			println(flowArr,'sm');
			//-->
		</script>
 <!-- ----------------------------------------------------------------------------------------- -->    
 	<div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0px" align="left">   
		<form name="mainFrm" method="post" action="">
			<div id="基本信息">
				<table borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr id="ifp_">
						<td width="30%">应用类型：</td>
						<td width="70%">
							<input type="radio" name="isFlowProcess" value="Y" onclick="switchFlow('Y');flowEven('flow')" checked>流程任务&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isFlowProcess" value="N" onclick="switchFlow('N');unFlowEven('flow')" <%if(sad.getIsFlowProcess().trim().equals("N")){ %> checked="checked" <%} %>>非流程任务
						</td>
					</tr>
					
					<tr id="pn_">
						<td>工程名称：</td>
						<td>
							<select name="projectName" onchange="getProcedure()" style="width:40%">
								<option value="">--请选择--</option>
									<%= request.getAttribute(WebAttrConstant.PROJECT_OPTION_STR) %>
							</select>
						</td>
					</tr>
					
					<tr id="pdn_">
						<td>过程名称：</td>
						<td>
							<select name="procedureName" style="width:40%">
								<option value="">--请选择--</option>
								<%= request.getAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION)%>
							</select>
						</td>
					</tr>
					
					<tr id="an_">
						<td width="40%">应用名称：</td>
						<td width="60%">
							<input type="text" name="appName" size="30" <%if(sad.getIsFlowProcess().trim().equals("Y")){ %>value="<%= sad.getAppName() %>"<%}%>/>
						</td>
					</tr>
					
					<tr id="cn_">
						<td>应用分类名称：</td>
						<td>
							<input type="text" name="categoryName" size="30" value="<%= sad.getCategoryName() %>">
						</td>
					</tr>
					
					<tr id="wt_">
						<td>应用显示窗口类型：</td>
						<td>
							<input type="radio" name="windowType" value="0" checked>右框架中&nbsp;&nbsp;&nbsp;
							<input type="radio" name="windowType" value="1" <%if(sad.getIsFlowProcess().trim().equals("Y") && sad.getWindowType() == 1){ %>checked<%} %>>新窗口
						</td>
					</tr>
					
					<tr id="cf_">
						<td>确认信息框：</td>
						<td>
							<input type="radio" name="confirmFinish" value="1" checked>允许&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="confirmFinish" value="0" <%if(sad.getIsFlowProcess().trim().equals("Y") && sad.getConfirmFinish() == 0){ %>checked<%} %>>禁止
						</td>
					</tr>
					
					<tr id="fm_">
						<td>完成时传送信息:</td>
						<td>
							<input type="radio" name="finishMessage" value="1" checked>允许&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="finishMessage" value="0" <%if(sad.getFinishMessage() == 0){ %>checked<%} %>>禁止
						</td>
					</tr>
					<tr id="vil_">
						<td>在任务列表中是否显示：</td>
						<td>
							<input type="radio" name="viewInList" value="Y" checked>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="viewInList" value="N" <%if(sad.getViewInList().trim().equals("N")){ %>checked<%} %>>否
						</td>
					</tr>
					<tr id="ao_">
						<td>允许操作项：</td>
						<td>
							<input type="checkbox" name="aOperation" value="1" <%if(nl.contains(new Integer(1))){%> checked="checked"<%}%> >取消
							<input type="checkbox" name="aOperation" value="2" <%if(nl.contains(new Integer(2))){%> checked="checked"<%}%> >特送
							<input type="checkbox" name="aOperation" value="4" <%if(nl.contains(new Integer(4))){%> checked="checked"<%}%> >退回
							<input type="checkbox" name="aOperation" value="8" <%if(nl.contains(new Integer(8))){%> checked="checked"<%}%> >查看流程
                            <input type="checkbox" name="aOperation" value="16" <%if(nl.contains(new Integer(16))){%> checked="checked"<%}%> >转送                            
                            <input type="checkbox" name="aOperation" value="32" <%if(nl.contains(new Integer(32))){%> checked="checked"<%}%> >提问
                        </td>
					</tr>

                    <tr id="url_">
                        <td>显示用缺省URL (抄送, 提问等使用)</td>
                        <td>
                            <input type="text" name="url" size="100" value="<%= sad.getUrl() %>"/>
                        </td>
                    </tr>

                    <tr id="adc_">
						<td>应用数据保存接口类名:</td>
						<td>
							<input type="text" name="appDataClass" size="30" value="<%= sad.getAppDataClass() %>"/>
						</td>
					</tr>
					
					<tr id="sm_">
						<td>应用数据读取接口类名:</td>
						<td>
							<input type="text" name="appDataSqlmodel" size="30" value="<%=sad.getAppDataSqlmodel() %>"/>
						</td>
					</tr>
				</table>
			</div>
		<!-- --------------------------------------------------------------------------------------------------------------------- -->
			<div id="工作栏定义" style="display:none;">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td colspan="2" bgcolor="#EFEFEF">工作栏三种分类对应表单域名</td>
						<td colspan="2" bgcolor="#EFEFEF">应用文档列入工作栏对应用表单域名</td>
					</tr>
					
					<tr>
						<td>主分类：</td>
						<td>
							<input type="text" name="sortColumn1" size="25" value="<%= sad.getSortColumn1() %>"/>
						</td>
						
						<td>关键字域名：</td>
						<td>
							<input type="text" name="appColumn1" size="25" value="<%= sad.getAppColumn1() %>"/>
						</td>
					</tr>
					
					<tr>
						<td>次分类：</td>
						<td>
							<input type="text" name="sortColumn2" size="25" value="<%= sad.getSortColumn2() %>"/>
						</td>
						
						<td>主题域名：</td>
						<td>
							<input type="text" name="appColumn2" size="25" value="<%= sad.getAppColumn2() %>"/>
						</td>
					</tr>
				
					<tr>
						<td>其它分类：</td>
						<td>
							<input type="text" name="sortColumn3" size="25" value="<%= sad.getSortColumn3() %>"/>
						</td>
						
						<td>其它域名：</td>
						<td>
							<input type="text" name="appColumn3" size="25" value="<%= sad.getAppColumn3() %>"/>
						</td>
					</tr>
				
					<tr>
						<td colspan="4" bgcolor="#EFEFEF">应用文档插入工作栏文档应用表单域名</td>
					</tr>
					<tr>
						<td>域名1：</td>
						<td>
							<input type="text" name="appColumn4" size="25" value="<%= sad.getAppColumn4() %>"/>
						</td>
						
						<td>域名4：</td>
						<td>
							<input type="text" name="appColumn7" size="25" value="<%= sad.getAppColumn7() %>"/>
						</td>
					</tr>
				
					<tr>
						<td>域名2：</td>
						<td>
							<input type="text" name="appColumn5" size="25" value="<%= sad.getAppColumn5() %>"/>
						</td>
						
						<td>域名5：</td>
						<td>
							<input type="text" name="appColumn8" size="25" value="<%= sad.getAppColumn8() %>"/>
						</td>
					</tr>
				
					<tr>
						<td>域名3：</td>
						<td>
							<input type="text" name="appColumn6" size="25" value="<%= sad.getAppColumn6() %>"/>
						</td>
						
						<td>域名6：</td>
						<td>
							<input type="text" name="appColumn9" size="25" value="<%= sad.getAppColumn9() %>"/>
						</td>
					</tr>
				</table>
			</div>
		<!-- ------------------------------------------------------------------------------ -->
			<div id="API定义" style="display:none;">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td colspan="2" bgcolor="#EFEFEF">过程状态域名</td>
						<td colspan="2" bgcolor="#EFEFEF">签收状态域名</td>
					</tr>
										
					<tr>
							<td>工程名称放置域：</td>
							<td>
								<input type="text" name="linkProjectNameField" size="25" value="<%= sad.getLinkProjectNameField() %>"/>
							</td>
							
							<td>是否签收：</td>
							<td>
								<input type="text" name="linkSignStatusField" size="25" value="<%= sad.getLinkSignStatusField() %>"/>
							</td>
						</tr>
					
						<tr>
							<td>工程描述：</td>
							<td>
								<input type="text" name="linkProjectDescField" size="25" value="<%= sad.getLinkProjectDescField() %>"/>
							</td>
							
							<td>签收时间：</td>
							<td>
								<input type="text" name="linkSignDateField" size="25" value="<%= sad.getLinkSignDateField() %>" />
							</td>
						</tr>
					
						<tr>
							<td>过程名称：</td>
							<td>
								<input type="text" name="linkProcedureNameField" size="25" value="<%= sad.getLinkProcedureNameField() %>"/>
							</td>
							
							<td>应转发时间：</td>
							<td>
								<input type="text" name="linkTaskDueField" size="25" value="<%= sad.getLinkTaskDueField() %>"/>
							</td>
						</tr>
					
					
					<tr>
						<td>过程描述：</td>
						<td>
							<input type="text" name="linkProcedureDescField" size="25" value="<%= sad.getLinkProcedureDescField() %>"/>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					
					<tr>
						<td colspan="2" bgcolor="#EFEFEF">任务状态域名</td>
						<td colspan="2" bgcolor="#EFEFEF">阅示状态域名</td>
					</tr>
					
						<tr>
							<td>当前任务名称：</td>
							<td>
								<input type="text" name="linkTaskNameField" size="25" value="<%= sad.getLinkTaskNameField()%>"/>
							</td>
							
							<td>阅示组别：</td>
							<td>
								<input type="text" name="linkReviewGroupField" size="25" value="<%= sad.getLinkReviewGroupField()%>"/>
							</td>
						</tr>
					
						<tr>
							<td>当前任务描述：</td>
							<td>
								<input type="text" name="linkTaskDescField" size="25" value="<%= sad.getLinkTaskDescField()%>"/>
							</td>
							
							<td>阅示角色：</td>
							<td>
								<input type="text" name="linkReviewRoleField" size="25" value="<%= sad.getLinkReviewRoleField()%>"/>
							</td>
						</tr>
					
						<tr>
							<td>当前任务预定时间：</td>
							<td>
								<input type="text" name="linkTaksDurationField" size="25" value="<%= sad.getLinkTaksDurationField() %>"/>
							</td>
							
							<td>所有可阅示用户：</td>
							<td>
								<input type="text" name="linkReviewUsersField" size="25" value="<%= sad.getLinkReviewUsersField() %>"/>
							</td>
						</tr>
					
						<tr>
							<td>当前任务指定组别：</td>
							<td>
								<input type="text" name="linkGroupField" size="25" value="<%= sad.getLinkGroupField() %>"/>
							</td>
							
							<td>当前为阅示状态：</td>
							<td>
								<input type="text" name="linkReviewStatusField" size="25" value="<%= sad.getLinkReviewStatusField() %>"/>
							</td>
						</tr>
					
					
					
						<tr>
							<td>当前任务指定角色：</td>
							<td>
								<input size="25" type="text" name="linkRoleFiled" value="<%= sad.getLinkRoleFiled() %>"/>
							</td>
							
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>		
					
						<tr>
							<td>当前角色所有用户：</td>
							<td>
								<input size="25" type="text" name="linkUsersField" value="<%= sad.getLinkUsersField() %>"/>
							</td>
							
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>		
					
					
					<tr>
						<td colspan="2" bgcolor="#EFEFEF">转发状态域名</td>
						<td colspan="2" bgcolor="#EFEFEF">传送信息状态域名</td>
					</tr>
					
					<tr>
						<td>转发工程：</td>
						<td>
							<input size="25" type="text" name="linkFromProjectField" value="<%= sad.getLinkFromProjectField() %>"/>
						</td>
						<td>传送信息：</td>
						<td>
							<input type="text" size="25" name="linkUserMessageField"  value="<%= sad.getLinkUserMessageField() %>"/>
						</td>
					</tr>
			
					
						<tr>
							<td>转发过程：</td>
							<td>
								<input size="25" type="text" name="linkFormProcedureField" value="<%= sad.getLinkFormProcedureField()%>"/>
							</td>
							
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>		
					
						<tr>
							<td>转发任务ID：</td>
							<td>
								<input size="25" type="text" name="linkFormTaskIdField"  value="<%= sad.getLinkFormTaskIdField() %>"/>
							</td>
							
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>		
					
						<tr>
							<td>转发人：</td>
							<td>
								<input size="25" type="text" name="linkFromUserField" value="<%= sad.getLinkFromUserField() %>"/>
							</td>
							
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>		
					
						<tr>
							<td>转发时间：</td>
							<td>
								<input size="25" type="text" name="linkFormDateField"  value="<%= sad.getLinkFormDateField() %>"/>
							</td>
							
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>		
					
					
					<tr>
						<td colspan="2" bgcolor="#EFEFEF">经办状态域名</td>
						<td colspan="2" bgcolor="#EFEFEF">预选会签状态域名</td>
					</tr>
					
						
					<tr>
						<td>继承组别：</td>
						<td>
							<input type="text" size="25" name="linkHandleGroupField" value="<%= sad.getLinkHandleGroupField() %>"/>
						</td>
					
						<td>转发紧急程度：</td>
						<td>
							<input type="text" size="25" name="linkDeliveryPriorityField" value="<%= sad.getLinkDeliveryPriorityField() %>"/>
						</td>
					</tr>		
					
					<tr>
						<td>经办人：</td>
						<td>
							<input type="text" size="25" name="linkHandlerField"  value="<%= sad.getLinkHandlerField() %>"/>
						</td>
					
						<td>下一任务会签组别或人员：</td>
						<td>
							<input type="text" size="25" name="linkNextCyclePropField"  value="<%= sad.getLinkNextCyclePropField() %>"/>
						</td>
					</tr>		
				</table>
			</div>
			<!-- 多选框的值 -->
			<input type="hidden" name="allowOperation"/>
			<input type="hidden" name="act"/>
			<input type="hidden" name="hiProjectName"/>
			<input type="hidden" name="hiGroupName"/>
			<input type="hidden" name="hiRoleName"/>
			
<!-- -------------------------------非流程定义--------------------------------------------------- -->			
			<div id="非流程应用" style="display:none">
				<table  borderColor=#666666 cellSpacing=0 borderColorDark=#ffffff cellPadding=2 width="90%" border=1 align="center">
					<tr>
						<td width="50%">应用类型：</td>
						<td width="50%">
							<input type="radio" name="is_Flow_Process" value="Y" onclick="switchFlow('Y');flowEven();" checked />流程任务&nbsp;&nbsp;&nbsp;
							<input type="radio" name="is_Flow_Process" value="N" onclick="switchFlow('N');unFlowEven()" <%if(sad.getIsFlowProcess().trim().equals("N")){ %> checked="checked" <%} %>/>非流程任务
						</td>
					</tr>
					
					<tr>
						<td width="40%">应用名称：</td>
						<td width="60%">
							<input type="text" name="app_Name" size="30" <%if(sad.getIsFlowProcess().trim().equals("N")){ %>value='<%= sad.getAppName() %>'<%}%>/>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">工程名称：&nbsp;
							<select name="project_Name" onchange="optionChage()" style="width:40%">
								<option value="">--请选择--</option>
									<%= request.getAttribute(WebAttrConstant.PROJECT_OPTION_STR) %>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>组别名称：</td>
						<td>角色名称：</td>
					</tr>
					<tr>
						<td align="right">
							<select name="group_Name" style="width:100%" multiple="multiple" size="5">
								<%
									String[] gn = sad.getGroupName().split(";");
									for(int i=0;i<gn.length;i++){
										if(!gn[i].equals("")){
								%>
									<option value="<%=gn[i] %>"><%=gn[i] %></option>		
								<%}}%>
							</select>
							<input type="button" onclick="add_group()" value="添加"/>
							<input type="button" onclick="remove_group()" value="删除"/>
						</td>
						<td align="right">
							<select name="role_Name" style="width:100%" multiple="multiple" size="5">
								<%
									String[] rn = sad.getRoleName().split(";");
									for(int i=0;i<rn.length;i++){
										if(!rn[i].equals("")){
								%>
									<option value="<%=rn[i] %>"><%=rn[i] %></option>		
								<%}}%>
							</select>
							<input type="button" onclick="add_role()" value="添加"/>
							<input type="button" onclick="remove_role()" value="删除"/>
						</td>
					</tr>
					
					<tr>
						<td>应用显示窗口类型：</td>
						<td>
							<input type="radio" name="window_Type" value="0" checked>右框架中&nbsp;&nbsp;&nbsp;
							<input type="radio" name="window_Type" value="1" <%if(sad.getWindowType() == 1){ %> checked="checked" <%} %>>新窗口
						</td>
					</tr>
					
					<tr>
						<td>在任务列表中是否显示：</td>
						<td>
							<input type="radio" name="view_In_List" value="Y" checked>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="view_In_List" value="N" <%if(sad.getViewInList().trim().equals("N")){ %> checked="checked" <%} %>>否
						</td>
					</tr>
					
					<tr>
						<td width="40%">URL：</td>
						<td width="60%">
							<input type="text" name="url" size="30" value="<%= sad.getUrl() %>"/>
						</td>
					</tr>
				</table>
			</div>
			<input type="hidden" name="appId" value="<%=sad.getAppId() %>"/>
		</form>
	<div>
	<%= WebConstant.WAIT_TIP_MSG%>
	</body>
</html>

<script type="text/javascript">
	<!--
	var pro = mainFrm.project_Name;
	var gro = mainFrm.group_Name;
	var rol = mainFrm.role_Name;
	var pgrArr = new Array("",new Array(),new Array());	//保存工程,组,角色关系
	
	
	function getProName(){
		var proName;
		var pn = pro.options;				
		for(var i=0;i< pn.length;i++){
			if(pn[i].selected){
				proName = pn[i].innerText;
				break;
			}
		}
		return proName;
	}
	
	function setFrmValue(){//设置工程下拉列表值，允许操作项 多选框的值
		var pn = mainFrm.projectName.options;		
		changeValue(pn);
		var ao = mainFrm.aOperation;
		var num = 0;
		for(var i=0;i<ao.length;i++){
			if(ao[i].checked){
				 num += parseInt(ao[i].value);
			}	
		}
		mainFrm.allowOperation.value = num;		
	}
	
	function optionChage(){//非流程任务，工程选项变动
		if(pgrArr[0] != getProName()){
			gro.options.length = 0;
			rol.options.length=0;
			pgrArr = new Array("",new Array(),new Array());       	
        	pgrArr[0] = getProName();
			return;
		}
	}
	
	function add_group(){//添加组别
		var proName = getProName();
		var retuenValue = window.showModalDialog("/system/showOptions.jsp", getGroup(proName), winstyle);
		if (retuenValue) {
        	var arr = explode(retuenValue, ";");
       		arr =add_Option(gro, arr);
       		for(var i in arr){
  				pgrArr[1][pgrArr[1].length] = arr[i];
  			}
       	}	
	}
	
	function remove_group(){//移除组别
		var da = delete_option(gro);
		for(var i in da){
			for(var j in pgrArr[1]){
				if(da[i]==pgrArr[1][j]){
					pgrArr[1].splice(j,1);
				}
			}
		}
	}
	
	function add_role(){//添加角色
		var proName = getProName();
		var retuenValue = window.showModalDialog("/system/showOptions.jsp", getRole(proName), winstyle);
		if (retuenValue) {
       	 	var arr = explode(retuenValue, ";");
       	 	arr =add_Option(rol, arr);
       	 	for(var i in arr){
  				pgrArr[2][pgrArr[2].length] = arr[i];
  			}
    	}
	}
	
	function remove_role(){//移除角色
		var da = delete_option(rol);
		for(var i in da){
			for(var j in pgrArr[2]){
				if(da[i]==pgrArr[2][j]){
					pgrArr[2].splice(j,1);
				}
			}
		}
	}
	
	function formatStr(){//将组别，角色格式化为字符串
		var str = "";
		for(var i=0;i<pgrArr[1].length;i++){
			str += pgrArr[1][i]+";";
		}
		mainFrm.hiGroupName.value = str.substring(0,str.length-1);
		
		str = "";
		for(var i=0;i<pgrArr[2].length;i++){
			str += pgrArr[2][i]+";";
		}
		mainFrm.hiRoleName.value = str.substring(0,str.length-1);
		mainFrm.hiProjectName.value = pgrArr[0];
	}
	
	function validate(){//验证提交表单
	
		if(mainFrm.isFlowProcess[0].checked){
			var fieldNames = "projectName;procedureName;appName";
			var fieldLabels = "工程名;过程名;应用名称";
			if(!formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
				return false;
			}
		}
		if(mainFrm.isFlowProcess[1].checked){
			var fieldNames = "project_Name;app_Name";
			var fieldLabels = "工程名;应用名";
			if(!formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)){
				return false;
			}
		}
		return true;
	}
	
	
	function save(){//保存事件
		try{
			if(!validate()){
				return;
			}
			if(mainFrm.isFlowProcess[1].checked){
				mainFrm.appName.value = mainFrm.app_Name.value;
				formatStr();	
			}
		
			setFrmValue();
			mainFrm.act.value ="<%= WebActionConstant.CREATE_ACTION %>";
			if(mainFrm.appId.value != "0"){
				if(confirm("确认更新信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")){
					mainFrm.act.value ="<%= WebActionConstant.UPDATE_ACTION %>";
				}else{
					return;
				}	
			} 
			mainFrm.action="/servlet/com.sino.sinoflow.servlet.SfApplicationServlet";
			mainFrm.submit();
		}catch(e){}
			
	}	
	function cancel(){//撤消事件
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApplicationServlet");
	}
	
			
	function addArr(obj){//将下拉列表的项添加到数组中
		var tpArr = new Array();
		for(var i=0;i<obj.options.length;i++){
			tpArr[i]=obj.options[i].text;
		}
		return tpArr;
	}
	function init(){//查看详细，初使化页面
		if(mainFrm.isFlowProcess[1].checked){
			pgrArr[0] = getProName();
			pgrArr[1] = addArr(mainFrm.group_Name);
			pgrArr[2] = addArr(mainFrm.role_Name);
			
			mainFrm.isFlowProcess[1].onclick();
		}
	}
  		init();
	//-->
</script>
