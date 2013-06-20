<%@ page import="com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO" %>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	EtsBarcodePrintHistoryDTO dto=(EtsBarcodePrintHistoryDTO)request.getAttribute(AssetsWebAttributes.ITEM_INFO_DTO);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body onload="initPage();" leftmargin="0" topmargin="0">
<script type="text/javascript">
    printTitleBar("“<%=dto.getBarcode()%>”的打印历史信息") ;
</script>


<div id="headDiv" style="overflow:hidden;position:absolute;top:21px;left:1px;width:828px">
<table border="0" width="100%" bordercolor="#666666" id="table3">
    <tr style="text-align:center; height:22px">
        <td width="50%" align="center">打印时间</td>
        <td width="50%" align="center">打印人</td>
    </tr>
</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:90%;width:845px;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
    DTOSet lines = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if (lines != null && !lines.isEmpty()) {
		for (int i = 0; i < lines.getSize(); i++) {
			EtsBarcodePrintHistoryDTO logDTO = (EtsBarcodePrintHistoryDTO)lines.getDTO(i);
%>
        <tr class="dataTR">
          <td width="50%" align="center"><%=logDTO.getPrintTime()%></td>
          <td width="50%"><%=logDTO.getUsername()%></td>
        </tr>
<%
        }
    }
%>
    </table>
</div>
</body>
</html>
<script type="text/javascript">
function initPage(){
	window.focus();
	do_SetPageWidth();
}
</script>