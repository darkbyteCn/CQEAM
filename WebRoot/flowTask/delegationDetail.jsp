<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.framework.security.bean.SessionUtil" %>
<%@ page import = "com.sino.sinoflow.user.dto.SfUserBaseDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.sinoflow.dto.SfDelegationDTO"%>
<%
	SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(request);
	SfDelegationDTO sdd = (SfDelegationDTO)request.getAttribute(WebAttrConstant.DELEGATION_ATT);
	if(sdd==null)sdd = new SfDelegationDTO();
%>
<html>
  <head>
    <title>delegation</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/cms_css/cms_css.css">
    <script type="text/javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
    <script type="text/javascript" src="/WebLibary/js/calendar.js"></script>
    
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script> 
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarVar.js"></script>
    <script type="text/javascript"> 
    	<!-- 
        printTitleBar("委派定义");
        var myArrAction0 = new Array(true, "取消", "action_cancel.gif", "取消", "cancel");
        var myArrAction1 = new Array(true, "保存", "action_save.gif", "保存", "save");
        ArrActions = new Array(myArrAction0, myArrAction1);
        printToolBar(); 
        //-->
	 </script>
	 
    <script type="text/javascript">
    
    	function cancel(){
    		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfDelegationServlet");
    	}
    	
    	function save(){
    		var temp = mainFrm.statusCtl;
    		if(!temp[0].checked && !temp[1].checked){
    			alert("输入非法，原因是：“委派狀态”未选择");
    			return;
    		}
    		if(mainFrm.delegateTo.value==""){
    			alert("输入非法，原因是：“委派给”未选择");
    			return;
    		}
    		mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
    		
    		if(mainFrm.delegationId.value != "0"){
				if(confirm("确认更新信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")){
					mainFrm.act.value ="<%= WebActionConstant.UPDATE_ACTION %>";
				}else{
					return;
				}	
			} 
	        mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfDelegationServlet";
	        mainFrm.submit();
    	} 
	 </script>
  </head>
   <body>
   		<div>
   			<form action="" method="POST" name="mainFrm">
	 			<table class="detailTb"> 
	 				<tr>
	 					<td width="20%"></td>
	 					<td align="right">委派人：</td>
	 					<td><%= sdd.getDelegationId() <= 0 ? user.getUsername() : sdd.getSName()%></td>
	 				</tr>
	 				<tr>
	 					<td width="20%"></td>
	 					<td align="right">委派狀态：</td>
	 					<td>
	 						<input type="radio" name="statusCtl" value="0" <%if(sdd.getStatusCtl() == 0){%> checked="checked" <%}%>/>禁用&nbsp;&nbsp;&nbsp;
	 						<input type="radio" name="statusCtl" value="1"<%if(sdd.getStatusCtl() == 1){%> checked="checked" <%}%>/>启用
	 					</td>
	 				</tr>
	 				<tr>
	 					<td width="20%"></td>
	 					<td align="right">委派给：</td>
	 					<td>
	 						<select name="delegateTo" style="width:150px">
	 							<option value="">--请选择--</option>
	 							<%=request.getAttribute(WebAttrConstant.USER_OPTION_STR)%>
	 						</select>
	 					</td>
	 				</tr>
	 				<tr>
	 					<td width="20%"></td>
	 					<td align="right">开始时间：</td>
	 					<td>
	 						<input type="text" name="startDate" size="25" value="<%=sdd.getStartDate()%>"
	 						 style="width:100%" title="点击选择开始日期" readonly class="readonlyInput" onClick="gfPop.fStartPop(startDate, endDate)">
	 						
	 					</td>
	 				</tr>
	 				<tr>
	 					<td width="20%"></td>
	 					<td align="right">结束时间：</td>
	 					<td>
	 						<input type="text" name="endDate" size="25" value="<%=sdd.getEndDate() %>"
	 						style="width:100%" title="点击选择结束日期" readonly class="readonlyInput" onClick="gfPop.fEndPop(startDate, endDate)">
	 					</td>
	 				</tr>
	 			</table>
	 			<input type="hidden" name="userId" value="<%= user.getUserId() %>"/>
	 			<input type="hidden" name="delegationId" value="<%=sdd.getDelegationId() %>"/>
	 			<input type="hidden" name="act"/>
  			</form>
  		</div>
  		<%=WebConstant.WAIT_TIP_MSG%>
  		
  		<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
  	</body>
  </html>

