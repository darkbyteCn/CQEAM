<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.sinoflow.dto.SfApiDTO"%>
<%@page import="java.util.List,java.util.ArrayList"%>
<%@page import="com.sino.base.util.FlowTaskTool"%>
    
   <%
	SfApiDTO sad = (SfApiDTO)request.getAttribute(WebAttrConstant.API_ATTR);
	if(sad == null){
		sad = new SfApiDTO();
	}
	List list = (List)request.getAttribute("nl");
	if(list == null)list = new ArrayList();
%>
    
<html>
  <head>
    <title>接口定义</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css"/>
    <script type="text/javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript">

        var selectedFun = -1;
        function cancel(){
    		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApiServlet");
    	}
    	
    	function save(){
            if(mainFrm.ifs.value != -1) {
                var temp = mainFrm.ifs.value;
                document.getElementsByName(temp).item(1).value=mainFrm.area.value;
            }

            if(!validate()){
    			return;
    		}
            mainFrm.apiControl.value = plusValue("ch");
    		changeValue(mainFrm.projectName);
//    		changeValue(mainFrm.procedureName);
	    	mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
	    	
	    	if(mainFrm.apiId.value != "0"){
				if(confirm("确认更新信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")){
					mainFrm.act.value ="<%= WebActionConstant.UPDATE_ACTION %>";
				}else{
					return;
				}	
			} 
            mainFrm.projectName.disabled = false;
            mainFrm.apiName.disabled = false;
//            mainFrm.procedureName.disabled = false;
//            mainFrm.taskTid.disabled = false;
	        mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfApiServlet";
	        mainFrm.submit();
    	}

    	 function printTool(){
	       var ArrAction = new Array("取消", "action_cancel.gif","cancel","取消");
		   var ArrAction1 = new Array("保存", "action_save.gif","save","保存");
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "接口维护";
	       	toolBar.print();
	    }
	    printTool();
	 </script>
  </head>
  	<jsp:include page="/message/MessageProcess"/>
  <body>
  	<form action="" method="post" name="mainFrm">
  		<div>
			<table align="center" width="60%" border="1" bordercolor="#666666">
				<tr>
					<td>工程名称：
						<select style="width:40%" name="projectName" onchange="getApi()">
							<option>--请选择--</option>
							<%=request.getAttribute(WebAttrConstant.PROJECT_OPTION_STR)%>
						</select>
					</td>
				</tr>	
		
				<!--<tr>
					<td>过程名称：
						<select style="width:40%" onchange="getTask2()" name="procedureName">
							<option>--请选择--</option>
							<%= request.getAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION)%>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>任务名称：
						<select style="width:40%" name="taskTid">
							<option>--请选择--</option>
							<%= request.getAttribute(WebAttrConstant.PROCEDURE_TASK_OPTION_STR)%>
						</select>
					</td>
				</tr>-->
				
				<tr>
					<!--<td>程序名称：
						<input type="text" name="apiName" size="48" value="<%= sad.getApiName() %>"/>
					</td>-->
                    <td height="33px">程序名称：
                        <span style="position:absolute;overflow:hidden;width:218px">
                         <select style="width:220px" name="apiNameS" id="apiNameS" onchange="apiSelected()">
                            <option value="">--请选择--</option>
                            <%= request.getAttribute(WebAttrConstant.API_OPTION_STR) %>
                        </select>
                        </span>
                        <span style="position:absolute;width:200px;">
                        <input type="text" name="apiName" id="apiName" value="" style="width:200px;border:1pt;margin:1">
                        </span>
                      </td>

                </tr>
				
				<tr>
					<td>接口事件选择：</td>
				</tr>
				
				<tr>
					<td>
						<table>
							<tr>
								<td>
									<input type="checkbox" name="ch" id="sfqueryopen" value="1" onclick="boxClick(this)" <%if(list.contains(new Integer(1))){%>checked<%}%>/>SFQueryOpen
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfpostopen" value="2" onclick="boxClick(this)" <%if(list.contains(new Integer(2))){%>checked<%}%>/>SFPostOpen
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfquerysign" value="4" onclick="boxClick(this)" <%if(list.contains(new Integer(4))){%>checked<%}%>/>SFQuerySign
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfpostsign" value="8" onclick="boxClick(this)" <%if(list.contains(new Integer(8))){%>checked<%}%>/>SFPostSign
								</td>
							</tr>
							<tr>
								<td>
									<input type="checkbox" name="ch" id="sfquerycomplete" value="16" onclick="boxClick(this)" <%if(list.contains(new Integer(16))){%>checked<%}%>/>SFQueryComplete
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfgroupreview" value="32" onclick="boxClick(this)" <%if(list.contains(new Integer(32))){%>checked<%}%>/>SFGroupReview
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfquerycyclereview" value="64" onclick="boxClick(this)" <%if(list.contains(new Integer(64))){%>checked<%}%>/>SFQueryCycleReview
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfqueryconditionalflow" value="128" onclick="boxClick(this)" <%if(list.contains(new Integer(128))){%>checked<%}%>/>SFQueryConditionalFlow
								</td>
							</tr>
							<tr>
								<td>
									<input type="checkbox" name="ch" id="sfquerygroup" value="256" onclick="boxClick(this)" <%if(list.contains(new Integer(256))){%>checked<%}%>/>SFQueryGroup
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfqueryassistflow" value="512" onclick="boxClick(this)" <%if(list.contains(new Integer(512))){%>checked<%}%>/>SFQueryAssistFlow
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfquerydistribute" value="1024" onclick="boxClick(this)" <%if(list.contains(new Integer(1024))){%>checked<%}%>/>SFQueryDistribute
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfquerygoback" value="2048" onclick="boxClick(this)" <%if(list.contains(new Integer(2048))){%>checked<%}%>/>SFQueryGoBack
								</td>
							</tr>	
							<tr>
								<td>
									<input type="checkbox" name="ch" id="sfquerysave" value="4096" onclick="boxClick(this)" <%if(list.contains(new Integer(4096))){%>checked<%}%>/>SFQuerySave
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfpostsave" value="8192" onclick="boxClick(this)" <%if(list.contains(new Integer(8192))){%>checked<%}%>/>SFPostSave
								</td>
								<td>
									<input type="checkbox" name="ch" id="sfparallelflow" value="16384" onclick="boxClick(this)" <%if(list.contains(new Integer(16384))){%>checked<%}%>/>SFParallelFlow
								</td>
							</tr>	
						</table>
					</td>
				</tr>
				<tr>
					<td>接口程序选择：
						<select name="ifs" style="width:40%" onchange="ifsChange()">
							<option value="-1">--请选择一个接口程序--</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<!--<textarea tabindex="0" rows="15" cols="120" name="area" onmouseout="areaOnMouseOut()"></textarea>-->
                        <textarea tabindex="0" rows="15" cols="120" name="area"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="act"/>
		<input type="hidden" name="apiControl" value="<%= FlowTaskTool.escapeHTML(String.valueOf(sad.getApiControl())) %>">
		<input type="hidden" name="sfqueryopen" value="<%= FlowTaskTool.escapeHTML(sad.getSfqueryopen()) %>"/>
		<input type="hidden" name="sfpostopen" value="<%= FlowTaskTool.escapeHTML(sad.getSfpostopen()) %>"/>
		<input type="hidden" name="sfquerysign" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerysign()) %>"/>
		<input type="hidden" name="sfpostsign" value="<%= FlowTaskTool.escapeHTML(sad.getSfpostsign()) %>"/>
		
		<input type="hidden" name="sfquerycomplete" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerycomplete())%>"/>
		<input type="hidden" name="sfgroupreview" value="<%= FlowTaskTool.escapeHTML(sad.getSfgroupreview()) %>"/>
		<input type="hidden" name="sfquerycyclereview" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerycyclereview()) %>"/>
		<input type="hidden" name="sfqueryconditionalflow" value="<%= FlowTaskTool.escapeHTML(sad.getSfqueryconditionalflow()) %>"/>

		<input type="hidden" name="sfquerygroup" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerygroup()) %>"/>
		<input type="hidden" name="sfqueryassistflow" value="<%= FlowTaskTool.escapeHTML(sad.getSfqueryassistflow()) %>"/>
		<input type="hidden" name="sfquerydistribute" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerydistribute()) %>"/>
		<input type="hidden" name="sfquerygoback" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerygoback()) %>"/>
		
		<input type="hidden" name="sfquerysave" value="<%= FlowTaskTool.escapeHTML(sad.getSfquerysave()) %>"/>
		<input type="hidden" name="sfpostsave" value="<%= FlowTaskTool.escapeHTML(sad.getSfpostsave()) %>"/>
		<input type="hidden" name="sfparallelflow" value="<%= FlowTaskTool.escapeHTML(sad.getSfparallelflow()) %>"/>
		
		<input type="hidden" name="apiId" value="<%= sad.getApiId() %>"/>
        <input type="hidden" name="enabled" value="<%=sad.getEnabled()%>" />
    </form>
	<%= WebConstant.WAIT_TIP_MSG%>
  </body>
</html>

<script type="text/javascript">

	<!--
		 function boxClick(obj){//复选框事件，将所选的checkbox值添加到下拉列表
		 	if(obj.checked){
		 		mainFrm.ifs.add(new Option(obj.id,obj.id));
                mainFrm.ifs.selectedIndex = mainFrm.ifs.length-1;
                ifsChange();
                mainFrm.area.focus();
             }else{
		 		var opts = mainFrm.ifs.options;
		 		for(var i=0;i<mainFrm.ifs.length;i++){
		 			if(opts[i].value == obj.id) {
		 				var v = document.getElementsByName(obj.id).item(1);
                         if(i == mainFrm.ifs.selectedIndex)
                            mainFrm.area.value = "";
                         v.value = "";
                         opts.remove(i);
                         mainFrm.ifs.selectedIndex = mainFrm.ifs.length - 1;
                         ifsChange();
                         return;
		 			}
		 		}
		 	}
            if(mainFrm.ifs.selectedIndex == 0) {
                mainFrm.area.readOnly = true;
            } else {
                mainFrm.area.readOnly = false;
            }
         }
		 
		 function ifsChange(){//事件改变
             if(mainFrm.ifs.selectedIndex == 0) {
                 mainFrm.area.readOnly = true;
             } else {
                 mainFrm.area.readOnly = false;
             }
             if(selectedFun != -1) {
                var temp = selectedFun;
                 document.getElementsByName(temp).item(1).value = mainFrm.area.value;
             }
             selectedFun = mainFrm.ifs.value;
             if(mainFrm.ifs.value == -1){
		 	 	mainFrm.area.value = "";
		 	 	return;
		 	 } else {
                 var temp = mainFrm.ifs.value;
                 mainFrm.area.value=document.getElementsByName(temp).item(1).value;
             }
         }
 /*
		 function areaOnMouseOut(){//鼠标移出文本域时保存文本域的值
		 	if(mainFrm.area.length<=0 || mainFrm.ifs.value == -1) return;
		 	var temp = mainFrm.ifs.value; 
		 	document.getElementsByName(temp).item(1).value=mainFrm.area.value;
		 }
*/

        function apiSelected(){
            document.getElementById('apiName').value=document.getElementById('apiNameS').options[document.getElementById('apiNameS').selectedIndex].value;
        }

         function validate(){
//		 	var fieldNames = "projectName;procedureName;taskName;apiNam";
//			var fieldLabels = "工程名;过程名;任务名;程序名称";
            if(mainFrm.ifs.length == 1) {
                alert("没有选择任何接口程序!");
                return false;
            }
            for(var i = 1; i < mainFrm.ifs.length; i++) {
                var temp = mainFrm.ifs.options[i].value;
                if(document.getElementsByName(temp).item(1).value == "") {
                    alert("接口程序 " + temp + " 为空, 如不需要此接口程序, 请不要选此接口程序!");
                    return false;
                }
            }
//            var fieldNames = "projectName;procedureName;taskTid";
//            var fieldLabels = "工程名;过程名;任务名";
             var fieldNames = "projectName;apiName"
             var fieldLabels = "工程名;程序名称"; 
            return formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
		 }

        function init(){//初使化页面
            for(var i=0;i<mainFrm.ch.length;i++){
				if(mainFrm.ch[i].checked){
					mainFrm.ifs.add(new Option(mainFrm.ch[i].id,mainFrm.ch[i].id));
				}
			}
			if(mainFrm.ifs.options.length>1){
				mainFrm.ifs.options[1].selected = true;
				mainFrm.ifs.onchange();
			}

            if(mainFrm.ifs.selectedIndex == 0)
                mainFrm.area.readOnly = true;
            else
                mainFrm.area.readOnly = false;
            selectedFun = mainFrm.ifs.value;
            
//            if(mainFrm.taskTid.value != "") {
            if(mainFrm.apiNameS.value != "") {
                mainFrm.projectName.disabled = true;
//                mainFrm.procedureName.disabled = true;
//                mainFrm.taskTid.disabled = true;
                mainFrm.apiName.value = mainFrm.apiNameS.value;
                mainFrm.apiName.disabled = true;
                mainFrm.apiNameS.disabled = true;
            }
        }
		init();
		
	//-->
</script>