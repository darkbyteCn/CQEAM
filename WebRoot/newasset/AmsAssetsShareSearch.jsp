<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.freeflow.AmsAssetsFreeDTO"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.base.web.CheckBoxProp" %>

<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>共享资产查询</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsFreeDTO dto = (AmsAssetsFreeDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsShareServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产共享管理>>共享资产查询")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
            <td width="5%" align="right">标签号：</td>
            <td width="15%"><input name="barcode" style="width:80%" type="text" value="<%=dto.getBarcode()%>" ></td>
            <td width="6%" align="right">资产名称：</td>
			<td width="17%"><input name="itemName" style="width:70%" type="text" value="<%=dto.getItemName()%>" >
                <input type="hidden" name=item_code value="<%=dto.getItemCode()%>"><a href=AmsAssetsShareSearch.jsp#
                                                                                      title="点击选择规资产名称" class="linka" onclick="do_SelectSpec();">[…]</a>
            </td>

            <input type="hidden" name="itemSpec"  style="width:80%" value="<%=dto.getItemSpec()%>">
            <td width="6%" align="right">地点：</td>
			<td width="20%"><input type="text" name="workorderObjectName" value="" style="width:75%" readonly><a href="../assets"
                                                                                                                 title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
	        <td width="6%" align="right">共享状态：</td>
			<td width="12%">
				<select name = "shareStatus" style="width:80%"><%=request.getAttribute("SHARE_STATUS") %></select>
			</td>
            <td width="20%">
            	<img border="0" src="/images/button/distribute.gif" width="63" height="18" align="right" onclick="do_Distribute();">
            	<img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">



<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:0px;width:140%">
	<table class="headerTable" border="1" width="140%">
		<tr height=22px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td width="2%" align="center"><input type="checkbox" name="subCheck" class="headCheckbox" id="controlBox" onclick="checkAll('subCheck','barcode')"></td>
			<td width="10%" align="center">公司名称</td>
            <td width="20%" align="center">部门名称</td>
            <td width="11%" align="center">资产名称</td>
            <td width="8%" align="center">资产型号</td>
            <td width="8%" align="center">资产编号</td>
            <td width="8%" align="center">标签号</td>
            <td width="28%" align="center">地点名称</td>
            <td width="9%" align="center">共享状态</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:75%;width:140%;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="20px" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="2%" align="center"><input type="checkbox" name="barcode" value="<%=row.getValue("BARCODE")%>"></td>
			<td width="10%"><%=row.getValue("COMPANY")%></td>
            <td width="20%"><%=row.getValue("DEPT_NAME")%></td>
            <td width="11%"><%=row.getValue("ITEM_NAME")%></td>
			<td width="8%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="8%" align="center"><%=row.getValue("ASSET_NUMBER")%></td>
			<td width="8%" align="center"><%=row.getValue("BARCODE")%></td>
            <td width="28%" ><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
			<td width="9%" align="center"><%=row.getValue("SHARE_STATUS") %></td>
		</tr>
<%
		}
	}
%>

	</table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:460px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Distribute(){
	var shareStatus = mainFrm.shareStatus.value;
	if(shareStatus == null || shareStatus == ""){
		alert("您未选择要分配的共享状态!");
		return;		
	}
	var barcodes = document.getElementsByName("barcode");
	for(var x = 0; x < barcodes.length; x++){
		if(barcodes[x].value != null && barcodes[x].value != "" && barcodes[x].checked == true){
			mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>"	
			mainFrm.submit();
			return;
		}
	}
	alert("你未选择要分配的记录，请选择后在进行分配！");
}

function do_SelectSpec() {
    var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
    var dialogWidth = 50.5;
    var dialogHeight = 30;
    var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
    if (specs) {
        var spec = null;
        for (var i = 0; i < specs.length; i++) {
            spec = specs[i];
            dto2Frm(spec, "mainFrm");
        }
    }
}
function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);

    if (objs) {
        var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.workorderObjectName.value = obj["workorderObjectLocation"];
	} else {
        mainFrm.workorderObjectName.value = "";
    }
}
</script>