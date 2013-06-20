<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.yj.ensure.dto.AmsYjCommunicateEnsureDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ include file="/newasset/headerInclude.htm" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>应急通信保障情况</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>

</head>
<body >
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
		<%
            AmsYjCommunicateEnsureDTO ensureDTO=(AmsYjCommunicateEnsureDTO)request.getAttribute("ENSURE");
            if(ensureDTO==null){
                ensureDTO=new AmsYjCommunicateEnsureDTO();
            }
            String action=(String)request.getAttribute("act");
            String formAction="/servlet/com.sino.ams.yj.ensure.servlet.AmsYjCommunicateEnsureServlet";
		%>
    <form name="mainFrm" method="post" action="<%=formAction%>">
			<script type="text/javascript">
    			printTitleBar("应急通信保障情况");
			</script>
			<input type="hidden" name="act" value="">
			<input type="hidden" name="communicateId" value="<%=ensureDTO.getCommunicateId()%>">
            <br>
        <table width="100%" height="100%">
            <tr>
                <td width="5%">&nbsp;</td>
                <td width="90%" valign="top">
			<table border="1" width="100%" id="table1" align="center">
		        <tr>
		          <td width="12%" align="right" nowrap bgcolor="#003366">&nbsp;</td>
		        	<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">公司：</td>
		            <td width="35%" align="left" height="22" nowrap>
                        <select name="organizationId" class="select_style1" style="width:100%"> <%=ensureDTO.getOrgOpt()%></select>	</td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">&nbsp;</td>
		            <td width="35%" align="left" height="22" nowrap>&nbsp;</td>
	            </tr>
		        <tr>
		          <td width="12%" align="center" nowrap bgcolor="#003366"><p>&nbsp;</p>	              </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">单位：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="deptName" value="<%=ensureDTO.getDeptName()%>" size="40" class="input_style1"  style="width:100%">       	</td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">通信保障名称：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input class="input_style1"  type="text" name="ensureName" size="40" style="width:100%" value="<%=ensureDTO.getEnsureName()%>" >  	</td>
           	   </tr>
		        <tr>
		          <td width="12%" align="center" nowrap bgcolor="#003366">&nbsp;</td>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">事件类型：</td>
		            <td width="35%" align="left" height="22" nowrap>
                        <select name="eventType" class="select_style1" style="width:100%">
                            <option value="政治经济事件类" <%=ensureDTO.getEventType().equals("政治经济事件类")?"selected":""%>>政治经济事件类</option>
                            <option value="节假日保障类" <%=ensureDTO.getEventType().equals("节假日保障类")?"selected":""%>>节假日保障类</option>
                            <option value="自然灾害类" <%=ensureDTO.getEventType().equals("自然灾害类")?"selected":""%>>自然灾害类</option>
                            <option value="事故灾难类" <%=ensureDTO.getEventType().equals("事故灾难类")?"selected":""%>>事故灾难类</option>
                            <option value="公共卫生事件类" <%=ensureDTO.getEventType().equals("公共卫生事件类")?"selected":""%>>公共卫生事件类</option>
                            <option value="社会安全事故类" <%=ensureDTO.getEventType().equals("社会安全事故类")?"selected":""%>>社会安全事故类</option>
                        </select>
                    </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">保障地点：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input class="input_style1"  type="text" name="ensureLocation" size="40" style="width:100%" value="<%=ensureDTO.getEnsureLocation()%>">		           	</td>
	           	</tr>
		        <tr>
		          <td width="12%" align="center" nowrap bgcolor="#003366">&nbsp;</td>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">保障时间从：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input  type="text" name="ensureDateFrom" style="width:100%" value="<%=ensureDTO.getEnsureDateFrom()%>" style="width:100%;cursor:hand" title="点击选择开始日期" readonly
                     class="input_style1"   onclick="gfPop.fStartPop(ensureDateFrom, ensureDateTo)"></td>

					<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">保障时间到：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="ensureDateTo" size="40" style="width:100%" value="<%=ensureDTO.getEnsureDateTo()%>" style="cursor:hand;width:100%" title="点击选择截至日期" readonly
                      class="input_style1"  onclick="gfPop.fEndPop(ensureDateFrom, ensureDateTo)">		            	</td>
	           	</tr>
		        <tr>
		          <td width="12%" align="center" nowrap bgcolor="#003366">人力投入</td>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">投入人数：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input class="input_style1"  type="text" name="manpowerQty"  value="<%=ensureDTO.getManpowerQty()%>"  style="width:100%"></td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">投入人次：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input class="input_style1"  type="text" name="manpowerTimes" value="<%=ensureDTO.getManpowerTimes()%>" style="width:100%"></td>
	            </tr>
		        <tr>
		          <td width="12%" align="center" nowrap bgcolor="#003366">应急车投入</td>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">应急车数量：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input class="input_style1"  type="text" name="comvanQty" style="width:100%" value="<%=ensureDTO.getComvanQty()%>">							            </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">应急车车次：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input class="input_style1"  name="comvanTimes" style="width:100%" value="<%=ensureDTO.getComvanTimes()%>"></td>
	            </tr>
		        <tr>
		          <td width="12%" align="center" nowrap bgcolor="#003366">其它应急通信设备投入</td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">应急通信设备数：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="equipmentQty" size="40" class="input_style1"  style="width:100%" value="<%=ensureDTO.getEquipmentQty()%>">		            </td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">应急通信设备套次：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="equipmentUnit" size="40" class="input_style1"  style="width:100%" value="<%=ensureDTO.getEquipmentUnit()%>">		                   </td>
	            </tr>
		        <tr>
		          <td width="12%" rowspan="4" align="center" nowrap bgcolor="#003366">通信损失及处置情况</td>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">通信阻断程度：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="blockDegree" size="40" class="input_style1"  style="width:100%" value="<%=ensureDTO.getBlockDegree()%>">		            </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">损失情况：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="lossCondition" style="width:100%" class="input_style1"  value="<%=ensureDTO.getLossCondition()%>">						</td>
	            </tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">应急保障措施：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="ensureMeasure"  class="input_style1"  value="<%=ensureDTO.getEnsureMeasure()%>"  style="width:100%">                		 </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">通信恢复情况及时间：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="recoverSituation" class="input_style1"  value="<%=ensureDTO.getRecoverSituation()%>" style="width:100%"> </td>
	            </tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">地方政府整体评价：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="governmentEvaluate" class="input_style1"  value="<%=ensureDTO.getGovernmentEvaluate()%>"  style="width:100%">                    </td>
					<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">事件原因及影响范围：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="reasonAffect" value="<%=ensureDTO.getReasonAffect()%>" class="input_style1"  style="width:100%" >		           	</td>
	           	</tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">存在的问题：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="question" style="width:100%" class="input_style1"  value="<%=ensureDTO.getQuestion()%>">								            </td>

					<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">未来防范措施：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="guardMeasure" value="<%=ensureDTO.getGuardMeasure()%>" class="input_style1"  style="width:100%" >		           	</td>
	           	</tr>
		  </table>
            <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
				<tr>
					<td align="center" nowrap>
                        <img src="/images/eam_images/save.jpg" alt="点击保存" onClick="do_save();">
                        <img src="/images/eam_images/back.jpg" alt="点击关闭" onClick="do_close();">
					</td>
				</tr>
			</table>
            </td><td width="5%">&nbsp;</td></tr></table>
		</form>
		<%=WebConstant.WAIT_TIP_MSG%>
	</body>
	<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
	</iframe>
</html>

<script>
	//取消
    function do_close() {
        window.close();
    }
    
	//保存
    function do_save() {
         var deptName = document.getElementsByName("deptName")[0];
         var ensureName = document.getElementsByName("ensureName")[0];
        if(deptName.value==null || deptName.value=="" ){
           alert("单位名称不能为空！");
           deptName.focus();
           return false;
        }
        if(ensureName.value==null || ensureName.value=="" ){
           alert("通信保障名称不能为空!");
           ensureName.focus();
           return false;
        }
        var manpowerQty = document.getElementsByName("manpowerQty")[0];
        var manpowerTimes = document.getElementsByName("manpowerTimes")[0];
        var comvanQty = document.getElementsByName("comvanQty")[0];
        var comvanTimes = document.getElementsByName("comvanTimes")[0];
        var equipmentQty = document.getElementsByName("equipmentQty")[0];
        var reg = /^[0-9]+$/;   
	      if(manpowerQty.value!=""&&!reg.test(manpowerQty.value)){   
	        alert('"投入人数" 请输入数字！');   
	        manpowerQty.value = "";   
	        manpowerQty.focus();   
	        return false;   
	      }     
	      if(manpowerTimes.value!=""&&!reg.test(manpowerTimes.value)){   
	        alert('"投入人次" 请输入数字！');   
	        manpowerTimes.value = "";   
	        manpowerTimes.focus();   
	        return false;   
	      }   
	      if(comvanQty.value!=""&&!reg.test(comvanQty.value)){   
	        alert('"应急车数量" 请输入数字！');   
	        comvanQty.value = "";   
	        comvanQty.focus();   
	        return false;   
	      }   
	      if(comvanTimes.value!=""&&!reg.test(comvanTimes.value)){   
	        alert('"应急车车次" 请输入数字！');   
	        comvanTimes.value = "";   
	        comvanTimes.focus();   
	        return false;   
	      }   
	      if(equipmentQty.value!=""&&!reg.test(equipmentQty.value)){   
	        alert('"应急通信设备数" 请输入数字！');   
	        equipmentQty.value = "";   
	        equipmentQty.focus();   
	        return false;   
	      }   
    
        with (mainFrm) {
            if (confirm("确认保存信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
                mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                mainFrm.action = "<%=formAction%>";
                mainFrm.submit();
            }
        }
    }



</script>