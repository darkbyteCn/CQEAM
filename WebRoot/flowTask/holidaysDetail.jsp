<%@ page contentType = "text/html; charset=GBK" language = "java" errorPage = "" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import = "com.sino.sinoflow.user.dto.SfUserBaseDTO" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@page import= "com.sino.sinoflow.constant.URLDefineList"%>

<html>
	<head>
		<title>节假日</title>
		<link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
		<script type = "text/javascript" src = "/WebLibary/js/Constant.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/CommonUtil.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/FormValidate.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/FormProcess.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/SelectProcess.js"></script>
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

	<body onkeypress="keyDown()">
	<jsp:include page = "/message/MessageProcess" flush = "true" />

		<div>
			<form name="mainFrm" method="post">
				<table width="60%">
					<tr>
						<td align="right">节假日名称：</td>
						<td>
							<input type="text" name="name" style="width: 40%;"/>
						</td>
					</tr>
					<tr>
						<td align="right">节假日年份：</td>
						<td>
							<input type="text" name="year" style="width: 40%;"/>
						</td>
					</tr>
					<tr>
	  					<td valign="top" align="right">节假日：</td>
	  					<td>
	  						<select name="tempHolidays" multiple="multiple" size="12" style="width:40%;">
	  							
	  						</select>
	  					</td>
		  			</tr>
		  			<tr>
		  				<td></td>
	  					<td>
	  						<input type="text" name="holidaysText" size="30" style="width: 40%;"/>
	  					</td>
	  				</tr>
	  				<tr>
	  					<td></td>
	  					<td>
	  						说明输入格式为XX.XX，如10.01
	  					</td>
	  				</tr>
		  			<tr>
		  				<td></td>
		  				<td>&nbsp;&nbsp;&nbsp;
	  						<input type="button" value="添加" onclick="addValue()"/>
	  						&nbsp;
	  						<input type="button" value="删除" onclick="delValue()"/>
	  					</td>
	  				</tr>
				</table>
				<input type="hidden" name="holidays" value=""/>
			</form>
		</div>
		
		

		<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
		<%=WebConstant.WAIT_TIP_MSG%>
	</body>
</html>

<script type="text/javascript">
<!--
	function addValue(){//将文本值添加到下拉列表
		var temp = mainFrm.holidaysText.value;
		if(temp == ""){
			return;
		}
		if(temp.indexOf(".") == -1){
			alert("输入非法，原因是：“输入格式有误“");
			return;
		}
		if(isNaN(temp)){
			alert("输入非法，原因是：“输入不是有效日期“");
			return;
		}
		
		mainFrm.tempHolidays.add(new Option(temp,temp));
		mainFrm.holidaysText.value = "";
		mainFrm.holidaysText.focus();
	}
	function delValue(){//删除值
  		dropOption("tempHolidays", true);
  	}
  		
	function keyDown(){
		if (event.keyCode == 13) {
         	addValue();
     	}
	}
  		
 	function cancel(){//取消事件
 		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet?act=<%= WebActionConstant.NEW_ACTION %>");
 	}
 	
 	function save(){//保存事件
	 	var fieldNames = "name;year";
	    var fieldLabels = "节假日名称;节假日年份";
	    var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
		if (!isValid) {
			return;
		}
 		if(isNaN(mainFrm.year.value) || mainFrm.year.value.length != 4){
			alert("输入非法，原因是：“输入不是有效年份“");
			return;
		}
		
	    	assignValue();
 			mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfHolidaysServlet?act=<%=WebActionConstant.CREATE_ACTION%>";
	   	 	mainFrm.submit();
 	}
 	
	function assignValue(){//拼接时间字符串值
		var str = "";
		for(var i=0;i<mainFrm.tempHolidays.length;i++){
			str += mainFrm.tempHolidays.options[i].value+";";	
		}
		str = str.substring(0,str.length-1);
		mainFrm.holidays.value = str;
	}
-->
</script>