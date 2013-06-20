<%@ page import="com.sino.ams.freeflow.AmsAssetsFreeDTO"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>闲置资产查询</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsFreeDTO dto = (AmsAssetsFreeDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.freeflow.AmsAssetsFreeServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产闲置管理>>闲置资产查询")
</script>
	<table width="100%" border="0" class="queryHeadBg">

		<tr>
            <td width="10%" align="right">标签号：</td>
            <td width="20%"><input name="barcode" style="width:80%" type="text" value="<%=dto.getBarcode()%>" ></td>
            <td width="10%" align="right">资产名称：</td>
			<td width="20%"><input name="itemName" style="width:80%" type="text" value="<%=dto.getItemName()%>" >
                <input type="hidden" name=item_code value="<%=dto.getItemCode()%>"><a href=# title="点击选择资产名称" class="linka" onclick="do_SelectSpec();">[…]</a>
            </td>

            <td width="3%"><input type="hidden" name="itemSpec"  style="width:80%" value="<%=dto.getItemSpec()%>">
                 </td>
            <td width="7%" align="right">地点：</td>
		<td width="14%"><input type="text" name="workorderObjectName" value="" style="width:100%" readonly></td>
        <td width="10%" ><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a>
            <td width="20%"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="130%">
		<tr height="22">
			<td width="8%" align="center">公司</td>
            <td width="23%" align="center">部门名称</td>
            <td width="13%" align="center">资产名称</td>
            <td width="16%" align="center">资产型号</td>
            <td width="8%" align="center">资产编号</td>
            <td width="8%" align="center">标签号</td>
            <td width="24%" align="center">地点名称</td>

		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:360px;width:857px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="130%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="23">
			<td width="8%"><%=row.getValue("COMPANY")%></td>
            <td width="23%"><%=row.getValue("DEPT_NAME")%></td>
            <td width="13%"><%=row.getValue("ITEM_NAME")%></td>
			<td width="16%" align="right"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="8%" align="right"><%=row.getValue("ASSET_NUMBER")%></td>
			<td width="8%" align="right"><%=row.getValue("BARCODE")%></td>
            <td width="24%" ><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>

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
<div style="position:absolute;top:468px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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