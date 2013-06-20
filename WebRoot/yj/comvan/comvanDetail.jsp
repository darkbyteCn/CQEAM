<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.yj.comvan.dto.AmsYjComvanDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.yj.constant.YJWebAttribute" %>
<%@ page import="com.sino.ams.yj.constant.YjLookUpConstant" %>
<%@ include file="/newasset/headerInclude.htm" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>应急通信车信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/yj/yjLookUp.js"></script>
</head>
<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
		<%
			RequestParser reqParser = new RequestParser();
		    reqParser.transData(request);
            AmsYjComvanDTO  comvanDTO=(AmsYjComvanDTO)request.getAttribute(YJWebAttribute.COMVAN);
            if (comvanDTO==null) {
                comvanDTO=new AmsYjComvanDTO();
            }
            String action=(String)request.getAttribute("act");
            String formAction="/servlet/com.sino.ams.yj.comvan.servlet.AmsYjComvanServlet";
		%>
    <form name="mainFrm" method="post" action="<%=formAction%>">
			<script type="text/javascript">
    			printTitleBar("应急通信车信息");
			</script>
			<input type="hidden" name="act" value="">
			<input type="hidden" name="comvanId" value="<%=comvanDTO.getComvanId()%>">
			<input type="hidden" name="resourceId" value="<%=comvanDTO.getResourceId()%>">
            <br>
      <div id="headDiv" style="overflow:hidden;position:absolute;top:10%">
        <table width="100%" height="100%" class="queryTable">
            <tr>
                <td width="5%">&nbsp;</td>
                <td width="90%" valign="top">
			<table border="1" width="100%" id="table1" align="center">
		        <tr>
		          <td width="12%" align="right" nowrap bgcolor="#003366">&nbsp;</td>
		        	<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">储备单位：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="deptName" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getDeptName()%>">	</td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">公司名称</td>
		            <td width="35%" align="left" height="22" nowrap>
		              <select disabled  name="orgnizationId" class="select_style1" style="width:100%">
		                <%=comvanDTO.getOrgOpt()%>
		              </select>
		            </td>
	            </tr>
		        <tr>
		          <td width="12%" rowspan="6" align="center" nowrap bgcolor="#003366"><p>车</p>
	              <p>体</p>
	              <p>及</p>
	              <p>改</p>
	              <p>装</p></td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">生产商：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="manufacturer" value="<%=comvanDTO.getManufacturer()%>" size="40" class="input_style1" style="width:100%"   class="input2">       	</td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">型号：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="model" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getModel()%>" >  	</td>
           	   </tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">车辆改装厂：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="refitFirm" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getRefitFirm()%>" >		            </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">整车长度(mm)：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="length" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getLength()%>">		           	</td>
	           	</tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">整备质量(吨)：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="quality" class="input_style1" style="width:100%" value="<%=comvanDTO.getQuality()%>">		  </td>

					<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">天线桅杆形式：</td>
		           	<td width="35%" align="left" height="22" nowrap>
                           <select name="antennaMastForm" class="select_style1" style="width:100%">
                            <option value="液压" <%=comvanDTO.getAntennaMastForm().equals("液压")?"selected":""%>>液压</option>
                            <option value="气压" <%=comvanDTO.getAntennaMastForm().equals("气压")?"selected":""%>>气压</option>
                            <option value="电动" <%=comvanDTO.getAntennaMastForm().equals("电动")?"selected":""%>>电动</option>
                            <option value="手动" <%=comvanDTO.getAntennaMastForm().equals("手动")?"selected":""%>>手动</option>
                        </select>
                    </td>
	           	</tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">是否有油机：</td>
		            <td width="35%" align="left" height="22" nowrap>
                        <select name="hasOilengine" class="select_style1" style="width:100%">
                            <option value="是" <%=comvanDTO.getHasOilengine().equals("是")?"selected":""%>>是</option>
                            <option value="否" <%=comvanDTO.getHasOilengine().equals("否")?"selected":""%>>否</option>
                        </select>
                    </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">现有车牌照：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="licensePlate" value="<%=comvanDTO.getLicensePlate()%>" class="input_style1" style="width:100%"></td>
	            </tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">车架号：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="frameNumber" class="input_style1" style="width:100%" value="<%=comvanDTO.getFrameNumber()%>">							            </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">长×宽×高(mm)：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input name="lWH" style="width:100%" class="input_style1" value="<%=comvanDTO.getLWH()%>"></td>
	            </tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">资产原值(万元)：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="originalCost" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getOriginalCost()%>" >		            	 </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">&nbsp;</td>
		            <td width="35%" align="left" height="22" nowrap>&nbsp;</td>
	            </tr>
		        <tr>
		          <td width="12%" rowspan="2" align="center" nowrap bgcolor="#003366">BTS</td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">厂家：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="btsManufacturer" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getBtsManufacturer()%>">		            </td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">型号：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="btsModel" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getBtsModel()%>">		                   </td>
	            </tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">载频分配：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="carrierFrequencyvAllocate" size="40" class="input_style1" style="width:100%" value="<%=comvanDTO.getCarrierFrequencyvAllocate()%>">		            </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">总载频数：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="carrierFrequencyvQty" class="input_style1" style="width:100%" value="<%=comvanDTO.getCarrierFrequencyvQty()%>">						</td>
	            </tr>
		        <tr>
		          <td width="12%" align="right" nowrap bgcolor="#003366">&nbsp;</td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">是否安装BSC：</td>
		            <td width="35%" align="left" height="22" nowrap>
                         <select name="installedBsc" class="select_style1"  style="width:100%">
                            <option value="是" <%=comvanDTO.getInstalledBsc().equals("是")?"selected":""%>>是</option>
                            <option value="否" <%=comvanDTO.getInstalledBsc().equals("否")?"selected":""%>>否</option>
                        </select>
                    </td>

		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">安装的其他GSM系统网元：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="otherGsmUnit" value="<%=comvanDTO.getOtherGsmUnit()%>" class="input_style1" style="width:100%"> </td>
	            </tr>
		        <tr>
		          <td width="12%" rowspan="2" align="center" nowrap bgcolor="#003366">传输</td>
		            <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">传输方式：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="transForm"  value="<%=comvanDTO.getTransForm()%>"  class="input_style1" style="width:100%">
                    </td>
					<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">设备型号：</td>
		           	<td width="35%" align="left" height="22" nowrap>
		           		<input type="text" name="transItemModel" value="<%=comvanDTO.getTransItemModel()%>" class="input_style1" style="width:100%" >		           	</td>
	           	</tr>
		        <tr>
		          <td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">带宽：</td>
		            <td width="35%" align="left" height="22" nowrap>
		            	<input type="text" name="bandwidth" class="input_style1" style="width:100%"  value="<%=comvanDTO.getBandwidth()%>">
                    </td>

					<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">是否有卫星传输：</td>
		           	<td width="35%" align="left" height="22" nowrap>
                         <select name="hasSatelliteTransmissions" class="select_style1" style="width:100%">
                            <option value="是" <%=comvanDTO.getHasSatelliteTransmissions().equals("是")?"selected":""%>>是</option>
                            <option value="否" <%=comvanDTO.getHasSatelliteTransmissions().equals("否")?"selected":""%>>否</option>
                        </select>
                    </td>
	           	</tr>
		        <tr>
		          <td align="right" nowrap bgcolor="#003366">&nbsp;</td>
		          <td height="22" align="right" nowrap bgcolor="#00CCFF">可提供业务种类：</td>
		          <td align="left" height="22" nowrap><input type="text" name="typeOfTraffic" value="<%=comvanDTO.getTypeOfTraffic()%>" class="input_style1" style="width:100%" ></td>
		          <td height="22" align="right" nowrap bgcolor="#00CCFF">&nbsp;</td>
		          <td align="left" height="22"  nowrap>&nbsp;</td>
	          </tr>
	            <tr>
	              <td rowspan="2" align="center" nowrap bgcolor="#003366"><p>近3年内的</p>
                  <p>使用情况</p></td>
	              <td height="22" align="right" nowrap bgcolor="#00CCFF">使用次数：</td>
	              <td align="left" height="22" nowrap><input class="input_style1" type="text" name="useTimes" value="<%=comvanDTO.getUseTimes()%>" style="width:100%" ></td>
	              <td height="22" align="right" nowrap bgcolor="#00CCFF">使用时提供的业务：</td>
	              <td align="left" height="22"  nowrap><input class="input_style1" type="text" name="usedTraffic" value="<%=comvanDTO.getUsedTraffic()%>" style="width:100%" ></td>
              </tr>
                <tr>
                  <td height="22" align="right" nowrap bgcolor="#00CCFF">主要使用场景及地点：</td>
                  <td align="left" height="22" nowrap><input class="input_style1" type="text" name="useScene" value="<%=comvanDTO.getUseScene()%>" style="width:100%" ></td>
                  <td height="22" align="right" nowrap bgcolor="#00CCFF">应急战备资源：</td>
                  <td align="left" height="22"  nowrap><input class="input_style1" type="text" name="equipmentName" value="<%=comvanDTO.getEquipmentName()%>" style="width:100%" readonly title="点击选择资源" class="readonlyInput" onclick="choose_resource();" ></td>
                </tr>
				<tr>
				  <td width="12%" align="right" nowrap bgcolor="#003366">&nbsp;</td>
		        	<td width="12%" height="22" align="right" nowrap bgcolor="#00CCFF">其他说明：</td>
					<td align="left" height="22" style="word-break:break-all" colspan="4" nowrap>
						<textarea name="remark" rows="3" class="input_style1" style="width:96%"><%=comvanDTO.getRemark()%></textarea></td>
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
            </div>
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

	//更新
    function do_save() {
        var deptName = document.getElementsByName("deptName")[0];
        if(deptName.value==null || deptName.value=="" ){
          alert("请输入储备单位!");
          deptName.focus();
          return false;
        }
        var length =  document.getElementsByName("length")[0];
        var quality = document.getElementsByName("quality")[0];
        var originalCost = document.getElementsByName("originalCost")[0];
        var useTimes = document.getElementsByName("useTimes")[0];
        var carrierFrequencyvQty = document.getElementsByName("carrierFrequencyvQty")[0];
          var reg = /^[0-9]+$/;   
	      if(length.value!=""&&!reg.test(length.value)){   
	        alert('"整车长度" 请输入数字！');   
	        length.value = "";   
	        length.focus();   
	        return false;   
	      }  
	         
//	      if(quality.value!=""&&!reg.test(quality.value)){   
//	        alert('"整备质量(吨)" 请输入数字！');   
//	        quality.value = "";   
//	        quality.focus();   
//	        return false;   
//	      }   
	      
          if(quality.value!=""){
              if(isNaN(quality.value))
             {
              alert('"整备质量(吨)" 请输入数字！');
              quality.value="";
              quality.focus();
              return false;   
             }
          }
      
//	      if(originalCost.value!=""&&!reg.test(originalCost.value)){   
//	        alert('"资产原值(万元)" 请输入数字！');   
//	        originalCost.value = "";   
//	        originalCost.focus();   
//	        return false;   
//	      }   
	      
         if(originalCost.value!=""){
              if(isNaN(originalCost.value))
             {
              alert('"资产原值(万元)" 请输入数字！');
              originalCost.value="";
              originalCost.focus();
              return false;   
             }
          }
	      
	      if(useTimes.value!=""&&!reg.test(useTimes.value)){   
	        alert('"使用次数" 请输入数字！');   
	        useTimes.value = "";   
	        useTimes.focus();   
	        return false;   
	      }   
	      if(carrierFrequencyvQty.value!=""&&!reg.test(carrierFrequencyvQty.value)){   
	        alert('"总载频数" 请输入数字！');   
	        carrierFrequencyvQty.value = "";   
	        carrierFrequencyvQty.focus();   
	        return false;   
	      }   
        
        with (mainFrm) {
            if (confirm("确认保存信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
                mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                mainFrm.action = "<%=formAction%>";
                //var index = document.getElementById("orgnization").selectedIndex;
                //var org = document.getElementById("orgnization").options[document.getElementById("orgnization").selectedIndex].value;
                //document.mainFrm.orgnizationId.value = org; 
                mainFrm.submit();
            }
        }
    }
    
    /**
     *选择应急战备资源
     */
    function choose_resource() {
        var lookUpName = "<%=YjLookUpConstant.LOOK_UP_RESOURCE%>";
        var dialogWidth = 50.6;
        var dialogHeight = 30;
        var retValue = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (retValue) {
            document.mainFrm.resourceId.value=retValue[0].resourceId;
            document.mainFrm.equipmentName.value=retValue[0].equipmentName;
        }
    }   
    
	/* 
	* 判断是否为数字，是则返回true,否则返回false 
	*/ 
	function f_check_number(obj){   
      var reg = /^[0-9]+$/;   
      if(obj.value!=""&&!reg.test(obj.value)){   
        alert('只能输入数字！');   
        obj.value = "";   
        obj.focus();   
        return false;   
      }   
    } 
	
	
</script>