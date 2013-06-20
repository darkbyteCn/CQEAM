<%@ page import="com.sino.ams.freeflow.AmsAssetsFreeDTO"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="java.math.BigDecimal"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<title>闲置资产统计</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsFreeDTO dto = (AmsAssetsFreeDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String orgaOption = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.freeflow.AmsAssetsFreeServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产基础报表>>闲置资产统计")
</script>
	<table width="100%" border="0">
		<tr>
			<input type = "hidden" name = "accessType" value = "ASS_REPORT">
            <td width="10%" align="right">公司：</td>
            <td width="18%">
        		<select class="select_style1" style="width:100%" name="organizationId" onchange="getDeptOpt();"><%=orgaOption%></select>
       		</td> 
            <td width="10%" align="right">资产名称：</td>
			<td width="18%"><input class="input_style1" name="itemName" style="width:80%" type="text" value="<%=dto.getItemName()%>" ></td>
            <td width="10%" align="right">规格型号：</td>
            <td width="18%"><input class="input_style1" name="itemSpec"  style="width:80%" value="<%=dto.getItemSpec()%>"></td>
            <td width="16%" align="right">
            	<img src="/images/eam_images/search.jpg" onclick="do_Search();">
            	<img src="/images/eam_images/export.jpg" onclick="do_Export();"  alt="导出到Excel">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:100%">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="4%" align="center">序号</td>
			<td width="12%" align="center">地市公司</td>
            <td width="15%" align="center">资产名称</td>
            <td width="9%" align="center">规格型号</td>
            <td width="9%" align="center">资产编号</td>
            <td width="12%" align="center">资产标签号</td>
            <td width="4%" align="center">数量</td>
            <td width="8%" align="center">价值</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:360px;width:100%;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		Double sumCost = 0d;
        Long sumCount = 0L;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="23">
			<td width="4%" align="center"><%=row.getValue("ROWNUM")%></td>
			<td width="12%" align="center"><%=row.getValue("COMPANY")%></td>
            <td width="15%" align="center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="9%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
			<td width="9%" align="center"><%=row.getValue("ASSET_ID")%></td>
            <td width="12%" align="center"><%=row.getValue("BARCODE")%></td>
			<td width="4%" align="right"><%=row.getValue("CURRENT_UNITS")%></td>
            <td width="8%" align="right"><%=row.getValue("COST")%></td>
		</tr>
<%
			sumCount += Long.parseLong(row.getValue("CURRENT_UNITS").toString());
			if(row.getValue("COST") != null && !"".equals(row.getValue("COST"))){
				sumCost += Double.parseDouble(row.getValue("COST").toString());
			}
		}
%>
		<tr height="22">
			<td width="8%" colspan = "6" height= "22" align = "left">合计：</td>			
			<td width="6%"  align="right"><%=sumCount %></td>
			<td width="6%" align="right"><%=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost) %></td>	
		</tr>
<%        		
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

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}
</script>