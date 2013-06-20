<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import = "com.sino.framework.security.bean.SessionUtil" %>
<%@ page import = "com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="com.sino.sinoflow.dto.SfDelegationDTO"%>
<%@ page import="com.sino.sinoflow.user.dto.SfUserBaseDTO"%>
<%
	SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(request);
	SfDelegationDTO sdd = (SfDelegationDTO)request.getAttribute(WebAttrConstant.DELEGATION_ATT);
	if(sdd==null)sdd = new SfDelegationDTO();
%>
<html>
  <head>
    <title>delegation</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script> 
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarVar.js"></script>
    <script type="text/javascript"> 
        printTitleBar("委派定义");
        var myArrAction0 = new Array(true, "取消", "action_cancel.gif", "取消", "cancel");
        var myArrAction1 = new Array(true, "保存", "action_save.gif", "保存", "save");
        ArrActions = new Array(myArrAction0, myArrAction1);
        printToolBar(); 
	 </script>
  </head>
   <body>
   		<div align="center">
   			<form action="" method="POST" name="mainFrm">
	 			<table style="width:100%" align="center"> 
	 				<tr>
	 					<td width="15%"></td>
	 					<td width="20%" align="right">委派人：</td>
	 					<td width="20%"><%= sdd.getDelegationId() <= 0 ? user.getUsername() : sdd.getSName()%></td>
                         <td width="45%"></td>
	 				</tr>
	 				<tr>
	 					<td width="15%"></td>
	 					<td width="20%" align="right">委派狀态：</td>
	 					<td width="20%">
	 						<input type="radio" name="statusCtl" value="0" <%if(sdd.getStatusCtl() == 0){%> checked="checked" <%}%>/>禁用&nbsp;&nbsp;&nbsp;
	 						<input type="radio" name="statusCtl" value="1"<%if(sdd.getStatusCtl() == 1){%> checked="checked" <%}%>/>启用
	 					</td>
                         <td width="45%"></td>
	 				</tr>
	 				<tr>
	 					<td width="15%"></td>
	 					<td width="20%" align="right">委派给：</td>
	 					<td width="20%">
	 						<select name="delegateTo" style="width:100%">
	 							<option value="">--请选择--</option>
	 							<%=request.getAttribute(WebAttrConstant.USER_OPTION_STR)%>
	 						</select>
	 					</td>
                         <td width="45%"></td>
	 				</tr>
	 				<tr>
	 					<td width="15%"></td>
	 					<td width="20%" align="right">开始时间：</td>
	 					<td width="20%">
	 						<input type="text" name="startDate"  value="<%=sdd.getStartDate()%>" style="width:100%" title="点击选择开始日期" readonly class="blueborderYellow" onClick="gfPop.fStartPop(startDate, endDate)">
	 					</td>
                         <td width="45%"></td>
	 				</tr>
	 				<tr>
	 					<td width="15%"></td>
	 					<td width="20%" align="right">结束时间：</td>
	 					<td width="20%">
	 						<input type="text" name="endDate" value="<%=sdd.getEndDate() %>" style="width:100%" title="点击选择结束日期" readonly class="blueborderYellow" onClick="gfPop.fEndPop(startDate, endDate)">
	 					</td>
                         <td width="45%"></td>
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


<script type="text/javascript">
function cancel(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    window.location.assign("/servlet/com.sino.sinoflow.servlet.SfDelegationServlet2");
}

function save(){
	var startDate = mainFrm.startDate.value;
	var endDate = mainFrm.endDate.value;
	if (startDate == "" || endDate =="" ) {
		alert("请输入开始或结束时间");
		return;
	}
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
    mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfDelegationServlet2";
    mainFrm.submit();
}
</script>