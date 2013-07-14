<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.yearchecktaskmanager.dto.*"%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
	<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	<script type="text/javascript" src="/yearchecktaskmanager/yeartchecktask.js"></script>
	<style type="text/css">
		.finputNoEmpty {WIDTH:100%;BORDER-RIGHT: 0px ridge;
		BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; 
		BORDER-BOTTOM: 0px ridge;font-size: 12px;
		text-align:left;BACKGROUND-COLOR: #FFFF99;padding-left:4px;border:1px solid #2769a7;
		};
	</style>
		<%
		    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
			AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) request.getAttribute("ORDER_HEAD_DATA");
			AssetsYearCheckTaskBaseDateDTO baseDateDTO = (AssetsYearCheckTaskBaseDateDTO) request.getAttribute("ORDER_BASE_DATE");
			DTOSet lineSet = (DTOSet) request.getAttribute("ORDER_LINE_DATA_DETAIL");
			System.out.println("JSP:"+lineSet.getSize());
		%>
	</head>

	<body leftmargin="0" topmargin="0"
		onkeydown="autoExeFunction('do_Search();')"
		onload="initPage();helpInit('4.4.4');">
		<input type="hidden" name="helpId" value="">
		<jsp:include page="/message/MessageProcess" />
		<jsp:include page="/public/exportMsg.jsp" />
		
		<script>
		 printTitleBar("省公司下发年度盘点任务明细");
		</script>

		<form name="mainFrm" method="post"
			action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckTaskProvinceServlet">
  			<input type="hidden" name="act" value="">
  			<input type="hidden" name="baseDateId" value="<%=baseDateDTO.getBaseDateId() %>"/>
			<table width="90%" topmargin="0" border="0" style="width:90%;TABLE-LAYOUT:fixed;word-break:break-all">
			<tr>
					<td align=right width=10%>
						任务名称：
					</td>
					<td width=25%>
						<input name="taskName" class="input_style2" readonly style="width: 90%;" value="<%=headerDTO.getTaskName() %>">
					</td>

					<td align=right width=10%>
						任务编号：
					</td>
					<td width=25%>
						<input name="taskName" class="input_style2" readonly style="width: 90%;" value="<%=baseDateDTO.getChkYearTaskOrderNumber() %>">
					</td>
					<td align=right width=10%>
						资产大类：
					</td>
					<td width=25%>
						<select name="assetsBigClass" readonly id="assetsBigClass"  style="width: 90%;" class="select_style1"><%=request.getAttribute("ASSETS_BIG_CLASS")%></select>
					</td>
				</tr>

				<tr>
					<td width="10%" align="right">
						基准日期间 从：
					</td>
					<td width="90%">
						<input style="width: 90%" type="text" class="input_style2" 
							name="checkBaseDateFrom" value="<%=baseDateDTO.getCheckBaseDateFrom() %>"
							title="点击选择起始日期" readonly/>
					</td>
					<td width="10%" align="right">
						到：
					</td>
					<td width="90%">
						<input style="width: 90%" type="text" class="input_style2"
							name="checkBaseDateEnd" id="checkBaseDateEnd" value="<%=baseDateDTO.getCheckBaseDateEnd() %>" readonly/>
					</td>
						<td align=right width=10%>
						备注：
					</td>
					<td width="25%">
						<input style="width:90%" class="input_style2"  type="text" name="remark" value="<%=headerDTO.getRemark() %>"/>
					</td>
				</tr>
				
				<tr>
					<td width="10%" align="right">
						任务执行期间 从：
					</td>
					<td width="90%">
						<input style="width: 90%" type="text" class="input_style2"  
							name="checkTaskDateFrom" value="<%=baseDateDTO.getCheckTaskDateFrom() %>" readonly/>
					</td>
					
					<td width="10%" align="right">
						到：
					</td>
					<td width="90%">
						<input style="width: 90%" type="text" class="input_style2"  
							name="checkTaskDateEnd" value="<%=baseDateDTO.getCheckTaskDateEnd() %>" readonly/>
					</td>
				</tr>
			
		    </table>
         
        <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:111px;left:0px;width:100%">
		    <table class="eamHeaderTable" border="1" width="100%">
		        <tr height=23px>
		            <td align=center width="25%">盘点任务编码</td>
		            <td align=center width="15%">公司编码</td>
		            <td align=center width="15%">公司名称</td>
		            <td align=center width="35%">接收部门</td>
		            <td align=center width="15%">任务执行人</td>
					<td style="display:none">隐藏域字段</td>
		        </tr>
		    </table>
		</div>
         
    <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:133px;left:0px;height:310px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
            	if (lineSet == null || lineSet.isEmpty()) {
            %>
            <tr id="model" class="dataTR" style="display:none">
			    <td width="25%" style="cursor:pointer" ><input type="text" name="orderNumber" id="orderNumber0" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="companyCode" id="companyCode0"  class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="company" id="company0"   class="finput" readonly/></td>
                <td width="35%" style="cursor:pointer" ><input type="text" name="implementDeptName" id="implementDeptName0"   readonly class="finput"/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="implementName" id="implementName0"   readonly class="finput"/> </td>
	            <td style="display:none">
	                <input type="hidden" name="implementBy" id="implementBy0" value=""/>
	                <input type="hidden" name="implementOrganizationId" id="implementOrganizationId0" value=""/>
	                <input type="hidden" name="implementDeptId" id="implementDeptId0" value=""/>
	            </td>
             </tr>
            <%
                    }else{
                    	AssetsYearCheckTaskLineDTO lineDTO = null;
                        for (int i = 0; i < lineSet.getSize(); i++) {
                            lineDTO = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
            %>
              <tr id="model" class="dataTR">
			    <td width="25%" style="cursor:pointer" ><input type="text" name="orderNumber" id="orderNumber<%=i%>" value="<%=lineDTO.getOrderNumber() %>" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="companyCode" id="companyCode<%=i%>"  value="<%=lineDTO.getCompanyCode() %>" class="finput" readonly/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="company" id="company<%=i%>"  value="<%=lineDTO.getCompany() %>" class="finput" readonly/></td>
                <td width="35%" style="cursor:pointer" ><input type="text" name="implementDeptName" id="implementDeptName<%=i%>" value="<%=lineDTO.getImplementDeptName() %>"   readonly class="finput"/></td>
                <td width="15%" style="cursor:pointer" ><input type="text" name="implementName" id="implementName<%=i%>"  value="<%=lineDTO.getImplementName() %>"  readonly class="finput"/> </td>
	            <td style="display:none">
	                <input type="hidden" name="implementBy" id="implementBy<%=i%>" value=""/>
	                <input type="hidden" name="implementOrganizationId" id="implementOrganizationId<%=i%>" value=""/>
	                <input type="hidden" name="implementDeptId" id="implementDeptId<%=i%>" value=""/>
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

	//选择盘任务点接收人
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

	//下发盘点任务
	function do_send(){
       	var checkBaseDateFrom = document.getElementById("checkBaseDateFrom").value;
       	var checkBaseDateEnd = document.getElementById("checkBaseDateEnd").value;
		var checkTaskDateFrom = document.getElementById("checkTaskDateFrom").value;
       	var checkTaskDateEnd = document.getElementById("checkTaskDateEnd").value;
       	if(checkBaseDateFrom =="" || checkBaseDateEnd=="" || checkTaskDateFrom =="" || checkTaskDateEnd==""){
			alert("必填字段不能为空！");
			return;
        }
       	var rows = dataTable.rows;
       	var tr = rows[0];
    	if(rows){
    		if(rows.length == 0 ||(rows.length == 1 && tr.style.display == "none")){
				alert("请添加任务执行人，在下发！");
				return;
    		}
    	}
	    document.mainFrm.act.value = "DO_SEND";
		document.mainFrm.submit();
        
	}


	/**
	  * 功能：选择任务执行人
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

	//删除行
	function deleteLine() {
	    var tab = document.getElementById("dataTable");
	    deleteTableRow(tab, 'subCheck');
	}
	
</script>