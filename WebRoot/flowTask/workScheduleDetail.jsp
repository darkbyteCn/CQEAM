<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<%@page import="com.sino.sinoflow.dto.SfWorkScheduleDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import= "com.sino.sinoflow.constant.URLDefineList"%>
<html>
  <head>
    <title>工作时间表</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
   	<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
	<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
	<script language="javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
	<script type="text/javascript" src="/WebLibary/js/selectMenu.js"></script>
    <script type="text/javascript">
    
    	function cancel(){
    		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet");
    	}
    	
    	function save(){
    		var fieldNames = "workScheduleName";
			var fieldLabels = "工作时间表名";
			var val =  formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);		
    		
    		if(val){
	    		
	    		if(mainFrm.workScheduleId.value!="0"){
	    			if(confirm("确认更新信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")){
	    				mainFrm.act.value = "<%= WebActionConstant.UPDATE_ACTION %>";
	    			}
	    		}else{
	    			mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
	    		}
	    		
		        mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet";
		        mainFrm.submit();
		     }
    	}
    	
    
    	 function printTool(){
	       var ArrAction = new Array("取消", "action_cancel.gif","cancel","c_t");
		   var ArrAction1 = new Array("保存", "action_save.gif","save","s_t");
		   var ArrAction2 = new Array("新增工作时间", "action_save.gif","saveGs","g_s");
		   var ArrAction3 = new Array("新增节假日", "action_save.gif","saveJr","j_r");
		   
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1,ArrAction2,ArrAction3);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "工作时间定义";
	        toolBar.print();
	    }
	    printTool();
	 </script>
  </head>
  <%
  	 SfWorkScheduleDTO skd = (SfWorkScheduleDTO)request.getAttribute(WebAttrConstant.WORKSCHEDULE_ATTR);
  	 if(skd == null){
  	 	skd = new SfWorkScheduleDTO(); 
  	 } 
  %>
  <jsp:include page="/message/MessageProcess"/>
  	<body onload="doLoad()">
  		<div>
  			<form name="mainFrm" method="POST">
  				<table width="60%">
  					<tr>
  						<td align="right" width="10%">工作时间总表:</td>
  						<td width="20%">
  							<input type="text" name="workScheduleName" style="width: 50%;" value="<%=skd.getWorkScheduleName() %>"/>
  						</td>
  					</tr>
  					<tr>
  						<td align="right">工作时间:</td>
  						<td width="20%">
  							<select name="workHourId" style="width: 50%;" onchange="onGZSJ();">
  								<option value="">--请选择--</option>
  								<%= request.getAttribute(WebAttrConstant.WORKHOUR_OPTION_STR) %>
  							</select>
  						</td>
  					</tr>
  					
  					<tr>
	  					<td valign="top" align="right"></td>
	  					<td>
	  						<select name="gzsj" multiple="multiple" size="9" style="width:50%;">
	  							
	  						</select>
	  					</td>
		  			</tr>
  					
  					<tr>
  						<td align="right">节假日:</td>
  						<td>
  							<select name="holidayName" style="width: 50%;" onchange="setJJR();">
  								<option value="">--请选择--</option>
  								<%= request.getAttribute(WebAttrConstant.HOLIDAYS_OPTION_STR) %>
  							</select>
  						</td>
  					</tr>
  					<tr>
						<td align="right">年份：</td>
						<td>
							<select name="year" style="width: 50%;" onchange="setNF();">
								<option value="">--请选择--</option>
							</select>
						</td>
					</tr>
					<tr>
	  					<td valign="top" align="right">日期：</td>
	  					<td>
	  						<select name="tempHolidays" multiple="multiple" size="8" style="width:50%;">
	  							
	  						</select>
	  					</td>
		  			</tr>
  				</table>
  				<input type="hidden" name="act" value=""/>
  				<input type="hidden" name="workScheduleId" value="<%=skd.getWorkScheduleId() %>"/>
	  		</form>
  		</div>
  			<%= WebConstant.WAIT_TIP_MSG%>
  	</body>
  </html>
  
  <script type="text/javascript">
  	<!--
  		function saveGs(){//新增工作时间事件
  			window.location.assign('<%= URLDefineList.WORK_HOUR_DETAIL%>');
  		}
  		
  		function saveJr(){//新增节假日事件
  			window.location.assign('<%= URLDefineList.HOLIDAYS_DETAIL%>');
  		}
  		
  		function setJJR(){//节假日变化，获取相对应的年份
  			if(mainFrm.holidayName.value == "") {
  				mainFrm.year.options.length = 1;
  				mainFrm.tempHolidays.options.length = 0;
  				return;
  			}
  			mainFrm.tempHolidays.options.length = 0;
  			mainFrm.year.options.length = 1;
  			var tp = JJR(mainFrm.holidayName.value);
  			for(i in tp){
  				mainFrm.year.add(new Option(tp[i].YEAR,tp[i].YEAR));
  			}
  		}
  		
  		function setNF(){//年份变化后获取相应的日期
  			if(mainFrm.year.value == "") {
  				mainFrm.tempHolidays.options.length = 0;
  				return;
  			}
  			var tp = NF(mainFrm.holidayName.value,mainFrm.year.value);
  			mainFrm.tempHolidays.options.length = 0;
  			for(i in tp){
  				var arr = tp[i].HOLIDAYS.split(';');
  				for(j in arr)
  					mainFrm.tempHolidays.add(new Option(arr[j],arr[j]));
			}  				
  		}
  		
  		function onGZSJ(){//获取详细工作时间
  			mainFrm.gzsj.options.length = 0; 
  			var tp = GZSJ(mainFrm.workHourId.value);
  			for(i in tp){
  				var sw = "上午："+tp[i].WORK_BEGIN_1+"-"+tp[i].WORK_END_1;
  				var xw = "下午："+tp[i].WORK_BEGIN_2+"-"+tp[i].WORK_END_2;
  				mainFrm.gzsj.add(new Option(trim(sw),sw));
  				mainFrm.gzsj.add(new Option(trim(xw),xw));
  				var fj = JX(tp[i].WORKING_DATE);
  				for(j in fj){
  					mainFrm.gzsj.add(new Option(replace(fj[j]),fj[j]));
  				}
  			}
  		}
  		
  		function JX(num){//分解星期
  		
  			var list = new Array();
			if (num == 0)
				return list;
			var index = 1;
			var reslut = 0;
			var count = 0;
			
			while (true) {
				if ((reslut = num & index) != 0) {
					list[list.length] = reslut;
					if (num == (count += reslut)) {
						return list;
					}
				}
				index *= 2;
			}
  		}
  		
  		function trim(mxh){//去除字符串空格
  			var newStr = "";
  			for(var i=0;i<mxh.length;i++){   
			  if(mxh.charAt(i)==" ")   
			  	newStr+=""   
			  else   
			  	newStr+=mxh.charAt(i)
			}   
			return newStr;
  		}
  		
  		function replace(ig){
  			switch (ig){
					case 1 : 
						return "星期一";
					case 2 :
						return "星期二";
					case 4 :
						return "星期三";
					case 8 :
						return "星期四";
					case 16 :
						return "星期五";
					case 32 :
						return "星期六";
					case 64 :
						return "星期日";
			}
  		}

        function doLoad() {
            onGZSJ();
            setJJR();
        }
      //-->
  </script>