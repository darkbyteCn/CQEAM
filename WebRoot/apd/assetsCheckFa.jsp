<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%@ page import="com.sino.ams.apd.dto.AmsAssetsCheckOrderDTO"%>
<%@ page import="com.sino.ams.apd.dto.EtsItemCheckDTO"%>


<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsAssetsCheckOrderDTO dto = (AmsAssetsCheckOrderDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
   // RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
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
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('2.1.1');" onkeydown="autoExeFunction('do_Search()');">
<script type="text/javascript">
    printTitleBar("非实地盘点资产确认");
</script> 
<form name="mainFrm" method="post" action="" > 
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="transUser" value="<%=dto.getTransUser()%>">
<input type="hidden" name="transName" value="<%=dto.getTransName()%>">
<input type="hidden" name="sendType" value="<%=dto.getSendType()%>">
<input type="hidden" name="sendValue" value="<%=dto.getSendValue()%>">
<input type="hidden" name="creationType" value="<%=dto.getCreationType()%>">
<input type="hidden" name="createValue" value="<%=dto.getCreateValue()%>">
<table width="100%" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <tr>
        <td width="8%" align="right">资产名称：</td>
        <td width="14%"><input class="input_style1" type="text" name="assetsDescription" style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
        <td width="8%" align="right">资产型号：</td>
        <td width="14%"><input class="input_style1" type="text" name="modelNumber" style="width:100%" value="<%=dto.getModelNumber()%>"></td>
        <td width="8%" align="right">标签号：</td>
        <td width="14%"><input class="input_style1" type="text" name="barcode" style="width:100%" value="<%=dto.getBarcode()%>"></td>
        <td width="24%" align="right">
           <img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:pointer" onclick="do_Search();" title="查询">&nbsp;<img src="/images/eam_images/confirm.jpg" id="confirmImg" style="cursor:pointer" onclick="do_Confirm();" title="确认设备">&nbsp;
        </td>
    </tr>
</table>
       
<input type="hidden" name="act" value="">


<div id="buttonDiv" style="position:absolute;top:40px;left:1px;width:100%">
    
				统一设置：
      		   <input type="checkbox" name="setAllStatus" id="setAllStatus"><label for="setAllStatus">资产状态</label>
    </div>


</form>
<input type="hidden" name="helpId" value=""> 
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:60px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="100%">
        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="20%">标签号</td>
            <td align=center width="17%">资产编号</td>
            <td align=center width="20%">资产名称</td>
            <td align=center width="20%">资产型号</td>
            <td align=center width="20%">资产状态</td>
			<td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>

<% 
    if (hasData) {
%>
<div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:71px;left:0px;height:485px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
            EtsItemCheckDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (EtsItemCheckDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
%>
    <tr class="dataTR" onclick="executeClick(this)">
        <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>"></td>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="itemStatus" id="itemStatus<%=i%>" type="text" class="finput2" readonly name="barcode" value="<%=barcode%>"></td>
        <td width="17%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
             <input  name="itemStatus" id="itemStatus<%=i%>" type="text" class="finput2" readonly value="<%=lineDTO.getItemStatus()%>"></td>
        <td width="20%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
             <input  name="itemStatus" id="itemStatus<%=i%>" type="text" class="finput" readonly value="<%=lineDTO.getItemStatus()%>"></td>
        <td width="20%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
             <input  name="itemStatus" id="itemStatus<%=i%>" type="text" class="finput" readonly value="<%=lineDTO.getItemStatus()%>"></td>
        <td width="20%" align="left" >
              <select name="isNorMal" id="isNorMal<%=i%>" onchange="do_SetCheckCategory(this);" class="finput" style="width: 100%"><%=lineDTO.getItemLevelOption()%></select>
        </td>
		<td style="display:none">
		</td>

    </tr>
 
<% 
		}
%>
</table>
</div>
<div id="pageNaviDiv"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<iframe style="display:none" src="" name="downFrm"></iframe>
<script type="text/javascript">

function do_Search() {
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.apd.servlet.AmsAssetsCheckFaServlet";
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Confirm() {
	var objs=document.getElementsByName("subCheck");
	alert(""+objs.length);
	var flag=false;
	for(var i=0;i<objs.length;i++){
		if(objs[i].checked){
			flag=true;
			//if(i==objs.length-1){
			//	arrar+=objs[i].value;
			//}else {
			//	arrar+=objs[i].value+",";
			//}
		}
	}

	if(flag==false){
		alert("qqq");
	}
	
   
}

function do_SetCheckCategory(lineBox){
	if(!mainFrm.setAllStatus.checked){
		return;
	}
	var objs = document.getElementsByName("isNorMal");
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
</script>
