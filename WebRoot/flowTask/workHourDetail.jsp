<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage=""%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<%@ page import="com.sino.sinoflow.user.dto.SfUserBaseDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@page import= "com.sino.sinoflow.constant.URLDefineList"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
		<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script type="text/javascript">
		<!--  
		 function printTool(){
		       var ArrAction = new Array("取消", "action_cancel.gif","cancel","c_t");
			   var ArrAction1 = new Array("保存", "action_save.gif","save","s_t");
		        var toolBar = new SinoPrintToolBar();            
		        toolBar.SinoActions = new Array(ArrAction,ArrAction1);
		        toolBar.imagePath = "../images/buttonbar/";
		        toolBar.titleStr = "工作时间定义";
		        toolBar.print();
		    }
		    printTool();
		-->
		</script>
	</head>

	<body>
		<jsp:include
			page="/message/MessageProcess"
			flush="true" />

		<div>
			<form action="" method="post" name="mainFrm">
			<table width="70%">
				<tr>
					<td width="10%"></td>	
					<td align="right">工作时间名：</td>
					<td>
						<input name="workHourName" type="text" style="width: 50%;"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="right">
						第一段工作时间：
					</td>
					<td>
						从
						<select name="bh1">
							<%for(int i=0;i<=23;i++){if(i<10){ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}} %>
						</select>
						时

						<select name="bm1">
							<% for(int i=0;i<=59;i++){ if(i<10){%>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%} }%>
						</select>
						分&nbsp;到


						<select name="eh1">
							<%for(int i=0;i<=23;i++){if(i<10){ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}} %>
						</select>
						时

						<select name="em1">
							<% for(int i=0;i<=59;i++){ if(i<10){%>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%} }%>
						</select>
						分
					</td>
				</tr>
				<tr>
					<td width="15%"></td>
					<td align="right">
						第二段工作时间：
					</td>
					<td>
						从
						<select name="bh2">
							<%for(int i=0;i<=23;i++){if(i<10){ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}} %>
						</select>
						时

						<select name="bm2">
							<% for(int i=0;i<=59;i++){ if(i<10){%>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%} }%>
						</select>
						分&nbsp;到


						<select name="eh2">
							<%for(int i=0;i<=23;i++){if(i<10){ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  						<%}} %>
						</select>
						时

						<select name="em2">
						
							<% for(int i=0;i<=59;i++){ if(i<10){%>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%}else{ %>
	  								<option value="<%=i%>"><%=i%></option>
	  							<%} }%>
						</select>
						分
					</td>
				</tr>
				<tr>
					<td width="15%"></td>
					<td align="right">
						工作日：
					</td>
					<td>	
						<input type="checkbox" name="cb" value="1"/>
						星期一
						<input type="checkbox" name="cb" value="2"/>
						星期二
						<input type="checkbox" name="cb" value="4"/>
						星期三
						<input type="checkbox" name="cb" value="8"/>
						星期四
						<input type="checkbox" name="cb" value="16"/>
						星期五
						<input type="checkbox" name="cb" value="32"/>
						星期六
						<input type="checkbox" name="cb" value="64"/>
						星期日
					</td>
				</tr>
			</table>
				<input type="hidden" name="workBegin1" />
	  			<input type="hidden" name="workEnd1" />
	  			<input type="hidden" name="workBegin2" />
	  			<input type="hidden" name="workEnd2" />
	  		  	<input type="hidden" name="workingDate" />
			</form>
		</div>

		<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
		<%= WebConstant.WAIT_TIP_MSG%>
	</body>
</html>

<script type="text/javascript">
<!--  
 	function cancel(){//取消事件
 		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet?act=<%= WebActionConstant.NEW_ACTION %>");
 	}
 	
 	function save(){//保存事件
 	
 		var fieldNames = "workHourName";
	    var fieldLabels = "工作时间名";
	    var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
		if (!isValid) {
			return;
		}
		
		var b1 = parseFloat(mainFrm.bh1.value+"."+mainFrm.bm1.value);
   		var e1 = parseFloat(mainFrm.eh1.value+"."+mainFrm.em1.value);
   		
   		var b2 = parseFloat(mainFrm.bh2.value+"."+mainFrm.bm2.value);
   		var e2 = parseFloat(mainFrm.eh2.value+"."+mainFrm.em2.value);
   		if(b1>=e1 || b2>=e2 || e1>=b2){
   			alert('选择的工作时间有冲突');
   			return;
   		}
   		
   		mainFrm.workBegin1.value = mainFrm.bh1.value+":"+mainFrm.bm1.value;
   		mainFrm.workEnd1.value = mainFrm.eh1.value+":"+mainFrm.em1.value;
   		mainFrm.workBegin2.value = mainFrm.bh2.value+":"+mainFrm.bm2.value;
   		mainFrm.workEnd2.value = mainFrm.eh2.value+":"+mainFrm.em2.value;
   		
   		mainFrm.workingDate.value = plusValue("cb");
   		
        mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfWorkHourServlet?act=<%= WebActionConstant.CREATE_ACTION %>";
        mainFrm.submit();
 	}
 	
-->
</script>