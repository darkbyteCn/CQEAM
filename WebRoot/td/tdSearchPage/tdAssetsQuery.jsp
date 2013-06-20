<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>TD资产台账</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	String pageTitle = request.getParameter("pageTitle");
	if(pageTitle == null){
		pageTitle = "TD系统接口-->>TD资产台账";
	}
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.td.assetsSearch.servlet.TdAssetsQueryServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>")
</script>
	<table border="0" width="100%" bgcolor="#EFEFEF">
		<tr height="20">
			<td width="8%" align="right"> 公司名称：</td>
			<td width="15%">
				<select name="organizationId" id="organizationId" style="width:100%" size="1"><%=dto.getOrgOption()%></select>
			</td>
			<td width="8%" align="right"> 资产标签：</td>
			<td width="15%">
				<input style="width:100%" name="tagNumber" value="<%=dto.getTagNumber()%>" type="text" ></td>
			<td width="8%" align="right"> 原MIS标签：</td>
			<td width="15%">
				<input style="width:100%" name="misTagNumber" value="<%=dto.getMisTagNumber()%>" type="text" size="20" ></td>
			<td width="8%" align="right"> 资产名称：</td>
			<td width="15%">
				<input style="width:100%" name="assetsDescription" value="<%=dto.getAssetsDescription()%>" type="text" >
			</td>
			<td width="8%">
				<p align="right">
				<img src="/images/eam_images/search.jpg" alt="查询" onClick="do_Search(); return false;"></td>
		</tr>
		<tr height="20">
			<td width="8%" align="right">启用日期：</td>
			<td width="15%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:100%" title="点击选择开始日期" readonly class="readonlyInput"  onclick="gfPop.fStartPop(startDate, endDate)" >
			</td>
			<td width="8%" align="right">到：</td>
			<td width="15%">
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:100%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)" >
			</td>
			<td width="8%" align="right">创建日期：</td>
			<td width="15%">
				<input type="text" name="startCreationDate" value="<%=dto.getStartCreationDate()%>"  style="width:100%" title="点击选择开始日期" readonly class="readonlyInput"  onclick="gfPop.fStartPop(startCreationDate, endCreationDate)" >
			</td>
			<td width="8%" align="right">到：</td>
			<td width="15%">
				<input type="text" name="endCreationDate" value="<%=dto.getEndCreationDate()%>" style="width:100%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startCreationDate, endCreationDate)" >
			</td>
			<td width="8%">
				　</td>
		</tr>
        <tr height="20">
			<td width="8%" align="right"> 成本中心：</td>
			<td width="15%">
				<input style="width:80%" name="costCenterCode" value="<%=dto.getCostCenterCode()%>" type="text" ><a href="" onclick="do_SelectCostCenter(); return false;" title="点击选择成本中心">[…]</a>
			</td>
			<td width="8%" align="right"> 项目名称：</td>
			<td width="15%">
				<input style="width:80%" type="text" name="projectName" value="<%=dto.getProjectName()%>" ><a href="" onclick="do_SelectProject(); return false;" title="点击选择项目">[…]</a>
			</td>
			<td width="8%" align="right"> 地点代码：</td>
			<td width="15%">
				<input type="text" name="assetsLocationCode" value="<%=dto.getAssetsLocationCode()%>" style="width:80%"><a href="" onclick="do_SelectAddress(); return false;" title="点击选择地点">[…]</a>
			</td>
			<td width="8%" align="right">责任人：</td>
			<td width="15%">
				<input style="width:80%" name="assignedToName" value="<%=dto.getAssignedToName()%>" type="text" ><a href="" onclick="do_SelectPerson(); return false;" title="点击选择责任人">[…]</a>
			</td>
			<td width="8%" align="right">
				<img src="/images/eam_images/export.jpg" alt="导出" onclick="do_ShowExcel()">
				
				<div id="ddDiv" align="center" style="position:absolute;z-index:2;top:260px;left:450px;background-color:azure;border:1px;width:300px;height:50px;text-align:center;visibility:hidden;">
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
        <tr height="20">
			<td width="8%" align="right">应用领域：</td>
			<td width="15%">
				<input type="text" name="faCategory1" value="<%=dto.getFaCategory1()%>" style="width:100%">
			</td>
			<td width="8%" align="right">资产类别：</td>
			<td width="15%">
				<input type="text" name="faCategory2" value="<%=dto.getFaCategory2()%>" style="width:100%">
			</td>
			<td width="8%" align="right">&nbsp;</td>
			<td width="15%">			</td>
			<td width="8%" align="right">&nbsp;</td>
			<td width="15%">
				&nbsp;
			</td>
			<td width="8%">
				　</td>
		</tr>

	</table>
	<input readonly name="act" type="hidden">
	<input type="hidden" name="excelType"/>
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:120px;left:1px;width:840px">
	<table class="headerTable" border="1" width="500%">
		<tr height="22">
			<td width="3%" align="center">资产标签</td>
			<td width="3%" align="center">原MIS标签</td>
			<td width="2%" align="center">资产编号</td>
			<td width="2%" align="center">应用领域</td>
			<td width="3%" align="center">资产类别代码</td>
			<td width="9%" align="center">资产类别</td>

			<td width="6%" align="center">资产名称</td>
			<td width="6%" align="center">资产型号</td>
			<td width="1%" align="center">数量</td>
			<td width="1%" align="center">单位</td>
			<td width="3%" align="center">地点代码</td>

			<td width="7%" align="center">地点位置</td>
			<td width="2%" align="center">责任人</td>
			<td width="2%" align="center">员工号</td>
			<td width="2%" align="center">项目编号</td>
			<td width="4%" align="center">项目名称</td>
			
			<td width="2%" align="center">项目类型</td>
			<td width="1%" align="center">年限</td>
			<td width="2%" align="center">启用日期</td>
			<td width="2%" align="center">创建日期</td>
			
			<td width="2%" align="center">资产原值</td>			
			<td width="2%" align="center">累计折旧</td>
			<td width="2%" align="center">资产净值</td>
			<td width="2%" align="center">累计减值准备</td>
			<td width="2%" align="center">净额</td>
			<td width="2%" align="center">资产残值</td>
			

			<td width="3%" align="center">公司名称</td>
			<td width="3%" align="center">资产账簿代码</td>
			<td width="3%" align="center">资产账簿名称</td>
			<td width="9%" align="center">折旧账户代码</td>
			<td width="11%" align="center">折旧账户名称</td>
		</tr>
	</table>
</div>		
<div id="dataDiv" style="overflow:scroll;height:70%;width:857px;position:absolute;top:143px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="500%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22">
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("MIS_TAG_NUMBER")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
			<td width="2%" align="center"><input class="finput" readonly value="<%=row.getValue("FA_CATEGORY1")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT2")%>"></td>
			<td width="9%" align="center"><input class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>

			<td width="6%" align="center"><input class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
			<td width="6%" align="center"><input class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td width="1%" align="center"><input class="finput3" readonly value="<%=row.getValue("CURRENT_UNITS")%>"></td>
			<td width="1%" align="center"><input class="finput" readonly value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>

			<td width="7%" align="center"><input class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
			<td width="2%" align="center"><input class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>
			<td width="4%" align="center"><input class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>

			<td width="2%" align="center"><input class="finput" readonly value="<%=row.getValue("PROJECT_TYPE")%>"></td>
			<td width="1%" align="center"><input class="finput3" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("ASSETS_CREATE_DATE")%>"></td>
			
			<td width="2%" align="center"><input class="finput3" readonly value="<%=row.getValue("COST")%>"></td>   <!-- 原值 -->			
			<td width="2%" align="center"><input class="finput3" readonly value="<%=row.getValue("DEPRN_RESERVE")%>"></td>
			<td width="2%" align="center"><input class="finput3" readonly value="<%=row.getValue("NET_ASSET_VALUE")%>"></td>
			<td width="2%" align="center"><input class="finput3" readonly value="<%=row.getValue("IMPAIR_RESERVE")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>
			<td width="2%" align="center"><input class="finput3" readonly value="<%=row.getValue("SCRAP_VALUE")%>"></td>

			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("BOOK_TYPE_CODE")%>"></td>
			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("BOOK_TYPE_NAME")%>"></td>
			<td width="9%" align="center"><input class="finput2" readonly value="<%=row.getValue("DEPRECIATION_ACCOUNT")%>"></td>
			<td width="11%" align="center"><input class="finput" readonly value="<%=row.getValue("DEPRECIATION_ACCOUNT_NAME")%>"></td>
		</tr>
<%
		}
	}
%>	
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:95%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
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
	_d.style.left = left - 80;
	if (_d.style.visibility == "hidden") {
		_d.style.visibility = "visible";
	}else {
		_d.style.visibility = "hidden";
	}
}

function do_Export(type) {
/*
	if(!document.$$$WebGridSystemFrm$$$){
		alert("请先执行查询，再导出");
		return;
	}
	var totalRecord = Number($$$WebGridSystemFrm$$$.$$$WebGridTotalRecord$$$.value);
	if(totalRecord > 5000){
		alert("当前条件下记录数过多，请输入相应条件精简记录数后再导出");
		return;
	}
*/
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.excelType.value = type;
    mainFrm.submit();
}

function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_TDLOC%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.assetsLocationCode.value = obj["assetsLocationCode"];
	}
}

function do_SelectPerson(){
	var lookUpName = "LOOK_UP_PERSON";
	var dialogWidth = 47;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(objs){
		var obj = objs[0];
		mainFrm.assignedToName.value = obj["userName"];
	}
}

function do_SelectProject() {
	var lookUpName = "LOOKUP_PROJECT2";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.projectName.value = obj["projectName"];
	}
}

function do_SelectCostCenter(){
	var lookUpName = "LOOK_UP_COST";
    var userPara="organizationId="+document.mainFrm.organizationId.value;
    var dialogWidth = 50;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
	if (objs) {
		var obj = objs[0];
		document.mainFrm.costCenterCode.value = obj["costCode"];
	}
}
</script>