<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
	<title>资产自定义查询</title>
<head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_CommonQuery();')">
<%
	DTOSet fields = (DTOSet)request.getAttribute(AssetsWebAttributes.COMM_QUERY_HEAD);
	DTOSet datas = (DTOSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if (datas != null && !datas.isEmpty()){
		hasData = true;
	}
	String headerDivTopPx = (String)request.getAttribute(AssetsWebAttributes.HEADER_DIV_TOP);
	String dataDivTopPx = (String)request.getAttribute(AssetsWebAttributes.DATA_DIV_TOP);
	String tableWidthPx = (String)request.getAttribute(AssetsWebAttributes.TABLE_WIDTH);
	String tdWidthPx = (String)request.getAttribute(AssetsWebAttributes.TD_WIDTH);
	String dataDivHeightPx = (String)request.getAttribute(AssetsWebAttributes.DATA_DIV_HEIGHT);
%>
<form name="mainFrm" action="<%=AssetsURLList.CUST_QRY_SERVLET%>" method="post">
<%=request.getAttribute(AssetsWebAttributes.CUST_QUERY_PARA)%>
<input type="hidden" name="act" value=""><br>
<img src="/images/eam_images/search.jpg" onclick="do_CommonQuery()">&nbsp;
<img src="/images/eam_images/export.jpg" alt="导出" onClick="do_ExportAssets();">

<div id="headDiv" style="overflow:hidden;position:absolute;top:<%=headerDivTopPx%>;left:1px;width:800px">
	<table class="headerTable" border="1" width="<%=tableWidthPx%>">
		<tr height="22px">
<%
	AmsAssetsCommQueryDTO fieldDTO = null;
	int fieldCount = fields.getSize();
	for(int i = 0; i < fieldCount; i++){
		fieldDTO = (AmsAssetsCommQueryDTO)fields.getDTO(i);
%>		
			<td align=center width="<%=tdWidthPx%>"><%=fieldDTO.getFieldDesc()%></td>
<%
	}
%>			
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:<%=dataDivHeightPx%>;width:817px;position:absolute;top:<%=dataDivTopPx%>;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
	AmsAssetsAddressVDTO data = null;
	String fieldName = "";
	if (hasData){
%>
	<table id="dataTable" width="<%=tableWidthPx%>" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
		for(int i = 0; i < datas.getSize(); i++){
			data = (AmsAssetsAddressVDTO)datas.getDTO(i);
%>
		<tr class="dataTR" onclick="do_ShowDetail('<%=ReflectionUtil.getProperty(data, "barcode")%>')">
<%
			for(int j = 0; j < fieldCount; j++){
				fieldDTO = (AmsAssetsCommQueryDTO)fields.getDTO(j);
				fieldName = fieldDTO.getFieldName();
				fieldName = StrUtil.getJavaField(fieldName);
%>
			<td align=center width="<%=tdWidthPx%>"><input readonly type="text" class="finput" value="<%=ReflectionUtil.getProperty(data, fieldName)%>"></td>
<%		
			}
%>					
		</tr>
<%	
		}
%>
	</table>
<%		
	}
%>	
</div>
</form>
<%
	if (hasData){
%>
<div style="position:absolute;top:570px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div></body>
<%
	}
%>
</html>
<script type="text/javascript">
function do_CommonQuery(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_ExportAssets(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(barcode){
	if(barcode == ""){
		alert("自定义显示字段中不含“标签号”，无法显示该资产详细信息。");
		return;
	}
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
</script>
