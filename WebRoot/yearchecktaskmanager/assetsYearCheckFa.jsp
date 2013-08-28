<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@page import="com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO"%>
<%@page import="com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO"%>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>


<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    DTOSet errorSet = (DTOSet) request.getAttribute("errorDTOSet");
    int orgId = userAccount.getOrganizationId();
    boolean hasData = false;
    if (lineSet != null && !lineSet.isEmpty()) {
        hasData = true;
    }
%>

<html>
<style>

FORM {
    margin-top: 0;
    margin-bottom: 0;
}
</style>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/yearchecktaskmanager/exportOrder.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('2.1.1');" onkeydown="autoExeFunction('do_Search()');">
<script type="text/javascript">
    printTitleBar("非实地盘点资产确认");
</script> 
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckFaServlet" > 
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="exportType" value="">
<table width="100%" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <tr>
        <td width="10%" align="right">资产名称：</td>
        <td width="20%"><input class="input_style1" type="text" name="assetsDescription" style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
        <td width="10%" align="right">资产型号：</td>
        <td width="20%"><input class="input_style1" type="text" name="modelNumber" style="width:100%" value="<%=dto.getModelNumber()%>"></td>
        <td width="10%" align="right">标签号：</td>
        <td width="20%"><input class="input_style1" type="text" name="barcode" style="width:100%" value="<%=dto.getBarcode()%>"></td>
    </tr>
    <tr>
        <td align=right width="10%" >任务名称：</td>
        <td width="20%" >
            <input type="text" name="orderName" class="finputNoEmpty" readonly style="width:80%" value="<%=dto.getOrderName()%>" size="20">
						<a href="" title="点击选择任务" onclick="chooseTask(); return false;">[…]</a>
		</td>
		<td width="10%" align="right">任务编号：</td>
        <td width="20%">
          <input class="input_style1" type="text" name="orderNumber" readonly style="width:100%" value="<%=dto.getOrderNumber()%>">
        </td>
        <td width="10%" align="right">任务类型：</td>
        <td width="20%">
          <input class="input_style1" type="text" name="orderTypeName" readonly  style="width:100%" value="<%=dto.getOrderTypeName()%>">
        </td>
        
    </tr>
</table>
       
<input type="hidden" name="act" value="">
<%--<input type="hidden" name="orderNumber" value="<%=dto.getOrderNumber()%>">--%>
<input type="hidden" name="orderType" value="<%=dto.getOrderType()%>">
<input type="hidden" name="excel" value="">
<input type="hidden" name="excelPath"  value="" >
<input type="hidden" name="typeStr" value="">


<div id="buttonDiv" style="position:absolute;top:70px;left:1px;width:100%">
       <img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:pointer" onclick="do_Search();" title="查询">
       <img src="/images/eam_images/confirm.jpg" id="confirmImg" style="cursor:pointer" onclick="do_Confirm();" title="确认设备">&nbsp;
       <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"  onClick="do_excel();">
       <img src="/images/eam_images/export.jpg" alt="Excel导出" onclick="do_ExportData()">
       <!-- 2013-07-04 Jeffery-->
      <!-- <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">-->
       <%
       if (errorSet != null) {
           if (!errorSet.isEmpty()) {
       %>
        <img src="/images/eam_images/detail_info.jpg" alt="查看资产标签号未导入成功详细信息"  onClick="do_Transfer();">
        <%
           }}
        %>
	    统一设置：
      <input type="checkbox" name="setAllStatus" id="setAllStatus"><label for="setAllStatus">确认状态</label>
</div>


</form>
<input type="hidden" name="helpId" value=""> 
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:95px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="100%">
        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
                <%--<td align=center width="3%">
                  <input type="checkbox" readonly="true" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')">
                </td>
			    --%><td align=center width="20%">资产标签号</td>
				<td align=center width="20%">资产名称</td>
				<!-- <td align=center width="6%">生产厂商</td> -->
				<!-- <td align=center width="6%">规格型号</td> -->
				<!--<td align=center width="10%">应用领域描述</td> -->
				<td align=center width="20%">资产类别描述</td>
				<!--<td align=center width="10%">资产类别编号</td>
				<td align=center width="10%">地点区划分类描述</td>
				<td align=center width="10%">地点编码</td>
				<td align=center width="10%">地点描述</td>
				<!--<td align=center width="6%">是否共建设备</td>-->
				<!--<td align=center width="6%">是否共享设备</td>-->
				<!--<td align=center width="6%">业务平台名称</td>-->
				<!--<td align=center width="6%">网络层次名称</td>-->
                <td align=center width="20%">确认状态</td>
                <td align=center width="20%">备注</td>
			<td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>

<% 
    if (hasData) {
%>
<div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:91px;left:0px;height:485px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
EtsItemYearCheckLineDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (EtsItemYearCheckLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
%>
    <tr class="dataTR" onclick="executeClick(this)">
        <%--<td width="3%" align="center">
        <input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>" readonly="true">
        </td> 
        --%><td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="barcode" id="barcode<%=i%>" type="text" class="finput2" readonly value="<%=barcode%>">
        </td>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="assetsDescription" id="assetsDescription<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getAssetsDescription()%>">
        </td>
        <%--
        <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="maintainUser" id="maintainUser<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getMaintainUser()%>">
        </td>
        <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="modelNumber" id="modelNumber<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getModelNumber()%>">
        </td>
        <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="faCategory1" id="faCategory1<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getFaCategory1()%>">
        </td>
        --%>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="contentName" id="contentName<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getContentName()%>">
        </td>
        <%--
        <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="contentCode" id="contentCode<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getContentCode()%>">
        </td>
        <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getWorkorderObjectLocation()%>">
        </td>
        <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="workorderObjectCode" id="workorderObjectCode<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getWorkorderObjectCode()%>">
        </td>
        <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="workorderObjectName" id="workorderObjectName<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getWorkorderObjectName()%>">
        </td>
        <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="constructStatus" id="constructStatus<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getConstructStatus()%>">
        </td>
        <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="isShare" id="isShare<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getShare()%>">
        </td>
        <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="opeId" id="opeId<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getOpeId()%>">
        </td>
        <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="nleId" id="nleId<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getNleId()%>">
        </td>
        --%>
        <td width="20%" align="left" >
              <select name="checkStatus" id="checkStatus<%=i%>" onchange="do_SetCheckCategory(this);" class="finputNoEmpty" style="width: 100%"><%=lineDTO.getCheckStatusOption()%></select>
        </td>
        <td width="20%" align="left" >
              <input name="notes" id="notes<%=i%>"  class="finput" style="width: 100%"><%=lineDTO.getNotes()%></input>
        </td>
		<td style="display:none">
		</td>

    </tr>
 
<% 
		}
%>
</table>
</div>
<%--<div id="pageNaviDiv"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
--%><%
    }
%>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<iframe style="display:none" src="" name="downFrm"></iframe>
<script type="text/javascript">

//query
function do_Search() {
	if(mainFrm.orderName.value == ""){
		alert("请先选择盘点任务，再执行本操作。");
		chooseTask();
		return;
	}
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckFaServlet";
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

//import
function do_excel() {
	if(mainFrm.orderName.value == ""){
		alert("请先选择盘点任务，再执行本操作。");
		chooseTask();
		return;
	}
    var returnValue=do_ImportExcel();
    if (returnValue) {
    	 mainFrm.excelPath.value = returnValue;
        document.mainFrm.act.value = "excel";
        document.mainFrm.submit();
    }
}

//导出
function do_ExportData() {
	if(mainFrm.orderName.value == ""){
		alert("请先选择盘点任务，再执行本操作。");
		chooseTask();
		return;
	}
    var exportType = "";
    if (confirm("是否导出任务下非实地资产？导出请点击“确定”按钮，否则请点击“取消”按钮")) {
        exportType = "EXPORT_ASSETS";
    } else {
    	return false;
    }
    if (exportType == "") {
        return;
    }
    mainFrm.exportType.value = exportType;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    mainFrm.act.value = "EXPORT";
    mainFrm.target = "downFrm";
    mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckFaServlet";
    mainFrm.submit();
}

//确认状态
function do_Confirm() {
   	//var checkArr1 = document.getElementsByName("subCheck");
   	//alert(""+checkArr1.length);
 	var checkArr = document.getElementsByName("barcode");
   	var length   = checkArr.length; 
   	//alert(""+length);
   	var flag     =false;
   	var Str="";//界面获取DTO集合对象
   	if(length==0){
	    alert("请选择数据后确认");
   	}else{
   		for(var i = 0; i < checkArr.length-1; i++) {
	            //if(checkArr[i].checked) {
	            var checkStatus=document.getElementById("checkStatus" + i).value;
	            if(checkStatus==null||checkStatus==""){
	            }else {
	            	var barcode=document.getElementById("barcode" + i).value;
				        //var checkStatus=document.getElementById("checkStatus" + i).value;
			        var notes=document.getElementById("notes" + i).value;
			        var str=barcode+","+checkStatus+","+notes;
				    Str+=str+";";
		            flag=true;
		        }
	           //} 
	    }
	    if(flag==false){
			alert("沒有选中任何数据!");
		}else {
		    //mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearCheckFaServlet?Str="+Str;
		    document.mainFrm.typeStr.value=Str;
		    document.mainFrm.act.value = "CONFIRM_ACTION";
		    document.mainFrm.submit();
		    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";//<!-- 2013-07-04 Jeffery-->
		}
   	}
}

//资产确认状态全选
function do_SetCheckCategory(lineBox){
	if(!mainFrm.setAllStatus.checked){
		return;
	}
	var objs = document.getElementsByName("checkStatus");
	var dataCount = objs.length;
	var selectedVal = lineBox.value;
	if(objs && objs.length){		
	
		var chkObj = null;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < dataCount; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			chkObj = objs[i];
			selectSpecialOptionByItem(chkObj, selectedVal);
		}
	}
} 

function initPage() {
    do_SetPageWidth();
}

//查询盘点任务列表
function chooseTask() {
	var lookUpName = "LOOK_UP_NO_ADDRESS_TASK";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var userPara = "";
    var costCenters = lookUpYearAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
    if (costCenters) {
        dto2Frm(costCenters[0], "mainFrm");
    } else {
        document.mainFrm.orderNumber.value = "";
        document.mainFrm.orderName.value = "";
        document.mainFrm.orderType.value = "";
    }
}

function do_Transfer() {
	   var width = screen.width-10;
	   var height = screen.height-60;   
	   window.open("/yearchecktaskmanager/assetsYearCheckFaError.jsp","","left=0,top=0,width="+width+",height="+height+",title=yes,scrollbars=yes,resizable=no,location=no,toolbar=no, menubar=no"); 
}

function deleteLine() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
}

</script>
