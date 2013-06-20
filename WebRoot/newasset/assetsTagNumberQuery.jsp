<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<body leftmargin="1" topmargin="0" onload="do_SetPageWidth();"  onkeydown="autoExeFunction('do_search()')" >
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AssetsTagNumberQueryDTO dto = (AssetsTagNumberQueryDTO) request.getAttribute("TAG_NUMBER");
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String assetsType =  reqParser.getParameter("assetsType");
	boolean hasData = (rows != null && !rows.isEmpty());
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	int orgId = userAccount.getOrganizationId();

	String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
	
%>
<form action="/servlet/com.sino.ams.newasset.servlet.AssetsTagNumberQueryServlet" name="mainFrm" method="post">
    <script type="text/javascript">
       printTitleBar( "<%= allResName %>" );
        
       var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_search(); return false;");
       var ArrAction1 = new Array(true, "打印大条码", "print.gif", "打印大条码", "do_Print(); return false;"); 
       var ArrAction2 = new Array(true, "打印小条码", "print.gif", "打印小条码", "do_Print_2(); return false;"); 
       var ArrAction3 = new Array(true, "导出", "toexcel.gif", "导出EXCLE或CSV", "do_ShowExcel(); return false;"); 
       var ArrActions = new Array(); 
       ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3  );  
       var ArrSinoViews = new Array();
       printToolBar();
        
    </script>
<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0"  class="queryTable">
	<tr>
	<tr>
		<td width="8%" align="right"> 公司名称：</td>
		<td width="17%"><select name="organizationId" id="organizationId" class="select_style1" style="width:80%"><%=request.getAttribute("OU")%></select></td>
		<td width="8%" align="right"> 资产类型：</td>
		<td width="17%">
			<select name="assetsType" class="select_style1" id="assetsType" style="width:80%">
			    <option value="MIS" <%=assetsType.equals("MIS") ? "selected" : ""%>>MIS资产标签号</option>
			    <option value="EAM" <%=assetsType.equals("EAM") ? "selected" : ""%>>EAM资产标签号</option>
			</select></td>
	    <td width="8%" align="right"> 项目编号：</td>
		<td width="17%">
		<input style="width:80%" type="text" name="projectNumber" class="input_style1"  value="<%=dto.getProjectNumber()%>" ><a href="" title="点击选择项目" onclick="do_SelectProject(); return false;">[…]</a></td>
		<td width="8%" align="right"> 项目名称：</td>
		<td width="17%">
		<input style="width:80%" type="text" name="projectName" class="input_style1"  value="<%=dto.getProjectName()%>" ><a href="" title="点击选择项目" onclick="do_SelectProject(); return false;">[…]</a></td>		
	</tr>
	<tr>
		<td width="8%" align="right"> 标签号：</td>
		<td width="17%"><input style="width:80%" class="input_style1"  name="tagNumber" value="<%=dto.getTagNumber()%>" type="text"></td>
		<td width="8%" align="right" >到：</td>
		<td width="17%"><input type="text" style="width:80%" class="input_style1"  name="toTagNumber" value="<%=dto.getToTagNumber()%>"> </td>
		<td width="8%" align="right"> 地点代码：</td>
		<td width="17%"><input style="width:80%" type="text" class="input_style1"  name="workorderObjectCode" value="<%=dto.getWorkorderObjectCode()%>"><a href="" onclick="do_SelectLocation(); return false;" title="点击选择盘点地点">[…]</a></td>
		<td width="8%" align="right">地点名称：</td>
		<td width="17%"><input style="width:80%" name="objectName" class="input_style1"  value="<%=dto.getObjectName()%>" type="text"><a href="" onclick="do_SelectLocation(); return false;" title="点击选择盘点地点">[…]</a></td>
	</tr>
	<tr>
		<td width="8%" align="right">启用日期：</td>
		<td width="17%"><input type="text" name="fromDate" value="<%=dto.getFromDate()%>"  style="width:80%" title="点击选择日期" readonly class="input_style2"   onclick="gfPop.fStartPop(fromDate, toDate)"></td>
		<td width="8%" align="right">到：</td>
		<td width="17%"><input type="text" name="toDate" value="<%=dto.getToDate()%>" style="width:80%" title="点击选择日期" readonly class="input_style2"  onclick="gfPop.fEndPop(fromDate, toDate)"></td>
		<td width="8%" align="right"> 创建日期：</td>
		<td width="17%">
		<input type="text" name="startDate" value="<%=dto.getStartDate()%>"  style="width:80%" title="点击选择日期" readonly class="input_style2"   onclick="gfPop.fStartPop(startDate, endDate)" ></td>
		<td width="8%">
		<p align="right">到：</td>
		<td width="17%">
		<input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:80%" title="点击选择日期" readonly class="input_style2"  onclick="gfPop.fEndPop(startDate, endDate)" ></td>
	</tr>
	<tr>
		<td width="8%" align="right">责任人：</td>
		<td width="17%"><input style="width:80%" name="responsibilityUser" class="input_style1"  value="<%=dto.getResponsibilityUser()%>" type="text"onClick="do_SelectPerson();" ><a href="" title="点击选择责任部门和责任人" onclick="do_SelectPerson(); return false;">[…]</a> </td>
		<td width="8%" align="right"> 责任部门：</td>
		<td width="17%"><input style="width:80%" name="responsibilityDept" class="input_style1"  value="<%=dto.getResponsibilityDept()%>" type="text"onClick="do_SelectPerson();" ><a href="" title="点击选择责任部门和责任人" onclick="do_SelectPerson(); return false;">[…]</a> </td>
		<td width="8%" align="right"> 资产名称：</td>
		<td width="17%"><input type="hidden" name="itemName" /><input style="width:80%" name="assetsDescription" class="input_style1"  value="<%=dto.getAssetsDescription()%>" type="text" onClick="do_SelectItemCode();" ><a href="" title="点击选择资产名称" onclick="do_SelectItemCode(); return false;">[…]</a> </td>
		<td width="8%" align="right"> 资产型号：</td>
		<td width="17%"><input type="hidden" name="itemSpec" /><input style="width:80%" name="modelNumber" class="input_style1"  value="<%=dto.getModelNumber()%>" type="text" onClick="do_SelectItemCode();" ><a href="" title="点击选择资产名称" onclick="do_SelectItemCode(); return false;">[…]</a> </td>
	</tr>
	<tr>
	    <td width="8%" align="right"> 成本中心：</td>
		<td width="17%"><select style="width:80%" class="select_style1" name="costCenter"><%=dto.getCostCenterOpt()%>"</select></td>
		<td width="8%" align="right"><!--  区域代码：--> </td>
		<td width="17%"><input style="width:80%" class="input_style1" name="areaCode" value="<%=dto.getAreaCode()%>" type="hidden"></td>
		<td width="50%" align="right" colspan="4"> 
		<%-- 
		<input type="radio" name="printType" checked="checked" value="1"/>大标签60*38mm<input type="radio" name="printType" value="2"/>小标签60*10mm&nbsp;
		<img src="/images/eam_images/search.jpg" alt="查询" onClick="do_search(); return false;">&nbsp;
		<img src="/images/eam_images/print.jpg" onClick="do_Print();" alt="打印条码">&nbsp;
		<img src="/images/eam_images/export.jpg" alt="导出EXCLE或CSV" onclick="do_ShowExcel()">--%></td>
	</tr>
</table>
	<div id="ddDiv" style="position:absolute;z-index:2;top:160px;left:400px;background-color:azure;border:1px;width:300px;height:50px;text-align:center;visibility:hidden;">
		<table border = "0" width="100%">
		   <tr style="cursor:move;background:#0997F7;color:white;font:bold;height:20">
	            <td>&nbsp;&nbsp;<span key="PleaseSelectFunction"/></td>
	    	    <td align="right"><div style="padding-right:10px"><font face="webdings" style="cursor:hand" onclick="do_ShowExcel()">r</font></div></td>
	        </tr>
	       <tr>  
	           <td width="80%" nowrap="nowrap" align="center">
					<input type="button" value="导出EXCEL" onclick="do_Export('xls')"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		           	
	   						<input type="button" value="导出CSV" onclick="do_Export('csv')"/>
	           </td>
	       </tr>
		 </table>
		 <iframe   src="" frameborder="0"  style="position:absolute;   visibility:inherit;   top:0px;   left:0px;  width:expression(this.parentNode.offsetWidth);   height:expression(this.parentNode.offsetHeight);   z-index:-1;"></iframe> 		 
	</div>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="excelType"/>
    <input type="hidden" name="checkedBarcode"/>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:156px;left:1px;width:830px">
		<table class="headerTable" border="1" width="200%">
			<tr height="20px">
				<td align=center width="1%"><input type="checkbox" name="mainCheck" id="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
				<td align=center width="7%">标签号</td>
				<td align=center width="4%">打印次数</td>
				<td align=center width="10%">资产名称</td>
				<td align=center width="10%">资产型号</td>

				<td align=center width="6%">启用日期</td>
				<td align=center width="6%">责任人</td>
				<td align=center width="15%">责任部门</td>

				<td align=center width="15%">所属项目</td>
				<td align=center width="6%">成本中心</td>
				<td align=center width="10%">地点代码</td>
				<td align=center width="15%">地点名称</td>
				<td style="display: none"></td>
			</tr>
		</table>
	</div>

<div id="dataDiv" style="overflow:scroll;height:48%;width:847px;position:absolute;top:178px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (rows != null && rows.getSize() > 0) {
		Row row = null;
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
			String xx=row.getStrValue("DEPRECIATION_ACCOUNT");
			if(xx.indexOf(".")>-1){
				xx=xx.substring(xx.indexOf(".")+1);
				xx=xx.substring(0,xx.indexOf("."));
			}
%>
            <tr height="22" >
            	<td width="1%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("TAG_NUMBER")%>"></td>
                <td width="7%" align="center"><input type="text" name="barcodePrint" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
                <td align=center width="4%"><input type="text"  class="finput2" readonly value="<%=row.getValue("PRINT_NUM")%>"></td>
                <td width="10%" align="left"><input type="text" name="itemNamePrint" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
                <td width="10%" align="left"><input type="text" name="itemSpecPrint" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>

				<td width="6%" align="center"><input type="text" name="startDatePrint" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
                <td width="6%" align="left"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
                <td width="15%" align="left"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_DEPT")%>"></td>

				<td width="15%" align="left"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
                <td width="6%" align="center"><input type="text" class="finput" readonly value="<%=xx%>"></td>
                <td width="10%" align="left"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>

				<td width="15%" align="left"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
				<td style="display: none">
					<input type="hidden" name="companyNamePrint" value="<%=row.getValue("ORGNIZATION_NAME")%>" />
				</td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>

</form>
<%
	if (hasData) {
%>
<div id="pageNaviDiv" style="position:absolute;top:95%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>

</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
function do_search() {
	mainFrm.target = "_self";
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_ShowExcel() {
	var _d = document.getElementById("ddDiv");
	var left = event.clientX;
	var top = event.clientY;
	_d.style.position = "absolute";
	_d.style.top = top + event.srcElement.offsetHeight;
	_d.style.left = left - 280;
	if (_d.style.visibility == "hidden") {
		_d.style.visibility = "visible";
	}else {
		_d.style.visibility = "hidden";
	}
}

function do_Export(type) {
	mainFrm.target = "_self";
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.excelType.value = type;
	mainFrm.submit();
	
	openExportMsgDiv();
	closeExportDiv();
}

function do_SelectLocation(){
	var assetsData = mainFrm.assetsType.value;
	with(mainFrm){
		var dialogHeight = 30;
		if(assetsData == "EAM"){
			var lookUpName = "LOOK_UP_ADDRESS";
			var dialogWidth = 55;
			userPara = "organizationId=<%=orgId%>";
			var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
			if (objs) {
				var obj = objs[0];
				workorderObjectCode.value = obj["workorderObjectCode"];
				objectName.value = obj["toObjectName"];
			}
		} else {
			var lookUpName = "LOOK_UP_MISLOC";
			var dialogWidth = 48;
			var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
			if (objs) {
				var obj = objs[0];
				workorderObjectCode.value = obj["assetsLocationCode"];
				objectName.value = obj["assetsLocation"];
			}
		}
	}
}

function do_SelectProject(){
	var lookUpName = "LOOKUP_PROJECT";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    }
}

function do_SelectItemCode(){
		with(mainFrm){
			var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_SYS_ITEM%>";
			var dialogWidth = 48;
			var dialogHeight = 30;
			var userPara = "itemCategory=" ;//设备专业
			var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
			if (objs) {
			    var obj = objs[0];
			    //itemName  assetsDescription
			    //itemSpec modelNumber
				dto2Frm(obj, "mainFrm");
				mainFrm.assetsDescription.value=mainFrm.itemName.value;
				mainFrm.modelNumber.value=mainFrm.itemSpec.value;
			} else {
			}
		}
	}

function do_SelectPerson(){
	with(mainFrm){
		var deptCode = "";
//		if(deptCode == ""){
//			alert("请先选择责任部门，再选择责任人");
//			return;
//		}
		var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PERSON%>";
		var dialogWidth = 47;
		var dialogHeight = 30;
		var userPara = "deptCode=" + deptCode;
		var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if(users){
			var user = users[0];
		    //responsibilityUserName.value = user["userName"];
			//employeeNumber.value = user["employeeNumber"];
			//responsibilityDeptName.value = user["deptName"];
			
			//responsibilityUser.value = user["employeeId"];
			//responsibilityDept.value = user["deptCode"];
			responsibilityUser.value = user["userName"];
			responsibilityDept.value = user["deptName"];
		} else {
			//responsibilityUserName.value = "";
            //responsibilityDeptName.value = "";
            
            responsibilityUser.value = "";
			responsibilityDept.value = "";
		}
	}
}


function do_Print() { 
    	var checkedValue = getCheckBoxValue( "subCheck" , "," );
    	if( checkedValue == "" ){
    		alert( "请选中需要打印条码的记录!" );
    		return false;
    	}
    	document.getElementById("checkedBarcode").value = checkedValue;
    	
    	var isPrint = false;
    	var printMsg = "";
    	
    	/**
    	var allCheckObj = document.getElementsByName("subCheck");
    	var printNum = 0;
    	for (var i = 0; i < allCheckObj.length; i++) {
            if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) { 
               	  printNum = getLinePrintCount(allCheckObj[i]).value;
               	  //alert( printNum );
               	  if( Number( printNum ) > 0 ){
               	  	  isPrint = true;
               	  	  printMsg = allCheckObj[i].value + "打印了" + printNum + "次. " ;
               	  	  break;
               	  }
            }
        } 
        
        if( isPrint ){
        	if( !confirm( "所选条码中存在已打印条码，是否继续?" ) ){
        		//window.open( "print" );
			    return;
        	} 
        } 
        **/
        
        //window.open( 'about:blank', 'newwindow', 'height=500,width=800,top=120,left=120,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
       	mainFrm.act.value = "PRINT_BARCODE_ACTION";
		mainFrm.target = "_blank";
	    mainFrm.submit(); 
    	mainFrm.target = "_self";
	}
	
	function do_Print_2() { 
    	var checkedValue = getCheckBoxValue( "subCheck" , "," );
    	if( checkedValue == "" ){
    		alert( "请选中需要打印条码的记录!" );
    		return false;
    	}
    	document.getElementById("checkedBarcode").value = checkedValue;
    	
    	var isPrint = false;
    	var printMsg = "";
    	
    	/**
    	var allCheckObj = document.getElementsByName("subCheck");
    	var printNum = 0;
    	for (var i = 0; i < allCheckObj.length; i++) {
            if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) { 
               	  printNum = getLinePrintCount(allCheckObj[i]).value;
               	  //alert( printNum );
               	  if( Number( printNum ) > 0 ){
               	  	  isPrint = true;
               	  	  printMsg = allCheckObj[i].value + "打印了" + printNum + "次. " ;
               	  	  break;
               	  }
            }
        } 
        
        if( isPrint ){
        	if( !confirm( "所选条码中存在已打印条码，是否继续?" ) ){
        		//window.open( "print" );
			    return;
        	} 
        } 
        **/
        
        //window.open( 'about:blank', 'newwindow', 'height=500,width=800,top=120,left=120,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
       	mainFrm.act.value = "PRINT_BARCODE_ACTION2";
		mainFrm.target = "_blank";
	    mainFrm.submit(); 
    	mainFrm.target = "_self";
	}
</script>