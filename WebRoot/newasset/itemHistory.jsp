<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body onload="initPage();" topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search()')">
<%
	DTOSet historys = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    ItemInfoHistoryDTO parameter = (ItemInfoHistoryDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    
    String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
    if( null == allResName ){
    	allResName = "设备变动历史查询";
    }
%>

<jsp:include page="/public/exportMsg.jsp"/>
<form action="/servlet/com.sino.ams.newasset.servlet.ItemInfoHistoryServlet" method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("<%= allResName %>");
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="8%" align="right">设备专业</td>
            <td width="12%"><select name="itemCategory" class="select_style1" style="width:100%"><%=parameter.getItemCategoryOption()%></select></td>
            <td  width="8%" align="right" >设备名称</td>
            <td width="12%" ><input type="text" name="itemName" class="input_style1" value="<%=parameter.getItemName()%>" style="width:100%"> </td>
            <td  width="8%" align="right" >责任部门</td>
			<td width="22%" ><select name="deptCode" class="select_style1" style="width:100%"><%=parameter.getDeptOption()%></select></td>
            <td  width="8%" align="right" >责任人</td>
            <td width="12%" ><input type="text" name="userName" class="input_style1" value="<%=parameter.getUserName()%>" style="width:100%"></td>
            <td  width="8%" align="center" >设备状态</td>
			<td width="22%" colspan="2"><select name="itemStatus" class="select_style1" style="width:100%"><%=parameter.getItemStatusOption()%></select></td>
        </tr>
        <tr>
            <td  align="right" >标签号</td>
            <td  ><input type="text" name="startBarcode" class="input_style1" value="<%=parameter.getStartBarcode()%>" style="width:100%"> </td>
            <td  align="right" >到</td>
            <td  ><input type="text" name="endBarcode" class="input_style1" value="<%=parameter.getEndBarcode()%>" style="width:100%"> </td>
            <td  align="right" >所在地点</td>
            <td  ><input type="text" name="workorderObjectCode" class="input_style1" value="<%=parameter.getWorkorderObjectCode()%>" style="width:100%"> </td>
            <td  align="right" >变动次数</td>
            <td  ><select name="changeTimes" class="select_style1" style="width:100%"><%=parameter.getChangeTimeOption()%></select></td>
            
        </tr>
        <tr>
            <td width="6%" align="right" >网络元素：</td>
            <td width="12%" ><input type="text" name="logNetEle" class="input_style1" value="<%=parameter.getLogNetEle()%>" style="width:100%"> </td>
            <td width="6%" align="right" >投资分类：</td>
            <td width="12%" ><input type="text" name="investCatName" class="input_style1" value="<%=parameter.getInvestCatName()%>" style="width:100%"> </td>
            <td width="6%" align="right" >业务平台：</td>
            <td width="22%" ><input type="text" name="opeName" class="input_style1" value="<%=parameter.getOpeName()%>" style="width:100%"> </td>
            <td width="6%" align="right" >网络层次：</td>
            <td width="12%" ><input type="text" name="lneName" class="input_style1" value="<%=parameter.getLneName()%>" style="width:100%"></td>
            <td  align="center" width="10%" colspan="2"><img src="/images/eam_images/search.jpg" style="cursor:pointer" onclick="do_Search();" alt="查询"></td>
            <td align="center" >
            	<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:pointer" alt="导出" onclick="do_ShowExcel()">
            	
            	<div id="ddDiv" style="position:absolute;z-index:2;top:130px;left:350px;background-color:azure;border:1px;width:300px;height:50px;text-align:center;visibility:hidden;">
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
            </td>     
        </tr>
        
        
    </table>

<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:94px;left:1px;width:100%">
	<table class="headerTable" border="1" width="200%" style="text-align:center">
		<tr style="height:23px">
			<td width="5%" height="22">标签号</td>
            <td width="5%" height="22">变动时间</td>
            <td width="3%" height="22">变动人</td>
            <td width="5%" height="22">单据号</td>

			<td width="5%" height="22">设备专业</td>
            <td width="6%" height="22">设备名称</td>

			<td width="6%" height="22">设备型号</td>
			
			<td width="4%" height="22">逻辑网络元素</td>
			<td width="3%" height="22">投资分类</td>
            <td width="3%" height="22">业务平台</td>
			<td width="3%" height="22">网络层次</td>
			
			<td width="8%" height="22">地点名称</td>
			<td width="6%" height="22">地点代码</td>

            <td width="7%" height="22">责任部门</td>
            <td width="3%" height="22">责任人</td>
            <td width="4%" height="22">责任人编号</td>

            <td width="3%" height="22">使用人</td>
            <td width="6%" height="22">旧目录代码</td>
            <td width="6%" height="22">目录代码</td>
            <td width="5%" height="22">目录描述</td>

            <td width="4%" height="22">设备状态</td>

		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:110px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if(historys != null && !historys.isEmpty()){
		int dataCount = historys.getSize();
        for(int i = 0; i < dataCount; i++){
			ItemInfoHistoryDTO history = (ItemInfoHistoryDTO)historys.getDTO(i);
%>
		<tr>
			<td width="5%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getBarcode()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
            <td width="5%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getCreationDate()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
            <td width="3%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getLogUser()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
            <td width="5%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getOrderNo()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="5%" height="22"><input type="text" class="finput" readonly value="<%=history.getItemCategoryName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getItemName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>

			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getItemSpec()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			
			<td width="4%" height="22"><input type="text" class="finput" readonly value="<%=history.getLogNetEle()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="3%" height="22"><input type="text" class="finput" readonly value="<%=history.getInvestCatName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="3%" height="22"><input type="text" class="finput" readonly value="<%=history.getOpeName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="3%" height="22"><input type="text" class="finput" readonly value="<%=history.getLneName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			
			<td width="8%" height="22"><input type="text" class="finput" readonly value="<%=history.getWorkorderObjectName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
            <td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getWorkorderObjectCode()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>

			<td width="7%" height="22"><input type="text" class="finput" readonly value="<%=history.getDeptName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="3%" height="22"><input type="text" class="finput" readonly value="<%=history.getUserName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="4%" height="22"><input type="text" class="finput" readonly value="<%=history.getEmployeeNumber()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>

			<td width="3%" height="22"><input type="text" class="finput" readonly value="<%=history.getMaintainUser()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getOldContentCode()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getContentCode()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>
			<td width="5%" height="22"><input type="text" class="finput" readonly value="<%=history.getContentName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>

            <td width="4%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getItemStatusName()%>" style="cursor:hand" onclick="do_ShowDetail('<%=history.getOrderDtlUrl()%>')"></td>

		</tr>
<%
		}
	}
%>
    </table>
</div>
    <input type="hidden" name="act"/>
    <input type="hidden" name="excelType"/>
</form>
<div id="pageNaviDiv" style="bottom:0px;left:0;">
<%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
function show(){
	document.getElementById("menuDiv").style.display = "display";
}

function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
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
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.excelType.value = type;
    mainFrm.submit();
    
    openExportMsgDiv();
	closeExportDiv();
}

function do_ShowDetail(url) {
    if (url == "" || url == null) return;
    var winName = "orderDtlWin";
    var factor = 0.9;
    var width = window.screen.availWidth * factor;
    var height = window.screen.availHeight * factor;
    var left = (window.screen.availWidth - width) / 2;
    var top = (window.screen.availHeight - height) / 2;
    style = "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes";
    window.open(url, winName, style);
}
</script>
