<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body onload="initPage();" topmargin="0" leftmargin="0">
<%
	String barcode = request.getParameter("barcode");
	DTOSet historys = (DTOSet) request.getAttribute(AssetsWebAttributes.BARCODE_HISTORY_DATA);
    String pageTitle = "标签号变动历史：" + barcode;
%>
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
    var btnCount = ArrActions.length;
    for(var i = 0; i < btnCount; i++){
        ArrActions[i][0] = (i == 0 || i == 1);
    }
    ArrActions[0] = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
    ArrActions[1] = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
    printToolBar();
</script>
<form action="/servlet/com.sino.ams.newasset.servlet.AmsItemInfoHistoryServlet" method="post" name="mainFrm">
<input type="hidden" name="act">
<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:50px;left:1px;width:100%">
	<table class="headerTable" border="1" width="120%" style="text-align:center">
		<tr>
			<td width="8%" height="22">标签号</td>
			<td width="12%" height="22">变动时间</td>
			<td width="14%" height="22">变动单据</td>
			<td width="6%" height="22">设备专业</td>
			<td width="8%" height="22">设备名称</td>
			<td width="8%" height="22">设备型号</td>
			<td width="15%" height="22">所在地点</td>
			<td width="8%" height="22">地点代码</td>
			<td width="6%" height="22">责任人</td>
			<td width="15%" height="22">责任部门</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
	if(historys != null && !historys.isEmpty()){
%>    
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = historys.getSize();
		AmsItemInfoHistoryDTO history = null;
        AmsItemInfoHistoryDTO hdto = (AmsItemInfoHistoryDTO)historys.getDTO(0);//用来导出变动历史报表
        for(int i = 0; i < dataCount; i++){
			history = (AmsItemInfoHistoryDTO)historys.getDTO(i);
%>
		<tr>
			<td width="8%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getBarcode()%>"></td>
            <td width="12%" height="22" align="center"><input type="text" class="finput" readonly value="<%=history.getCreationDate()%>"></td>
			<td width="14%" height="22"><%=history.getOrderNo()%></td>
			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getItemCategoryName()%>"></td>
			<td width="8%" height="22"><input type="text" class="finput" readonly value="<%=history.getItemName()%>"></td>
			<td width="8%" height="22"><input type="text" class="finput" readonly value="<%=history.getItemSpec()%>"></td>
			<td width="15%" height="22"><input type="text" class="finput" readonly value="<%=history.getWorkorderObjectName()%>"></td>
			<td width="8%" height="22"><input type="text" class="finput" readonly value="<%=history.getAddressNo()%>"></td>
			<td width="6%" height="22"><input type="text" class="finput" readonly value="<%=history.getResponsibilityUserName()%>"></td>
			<td width="15%" height="22"><input type="text" class="finput" readonly value="<%=history.getResponsibilityDeptName()%>"></td>
		</tr>
<%
		}
%>
<input type="hidden" name="barcode" value="<%=hdto.getBarcode()%>">
    </table>
<%
	}
%>
</div>
</form>
</body>
</html>
<script type="text/javascript">

function do_Close(){
    self.close();
}

function do_ResizePage(){
	var larg = 0;
	var altez = 0;
    var factor = 0.9;
    var left = 0;
    var top = 0;
	if (document.layers){
		larg = screen.availWidth * factor;
		altez = screen.availHeight * factor;
    } else{
		larg = screen.availWidth * factor;
		altez = screen.availHeight * factor;
	}
    left = larg / 18;
    top = altez / 18;
	self.resizeTo(larg,altez);
	self.moveTo(left, top);
}

function initPage(){
	window.focus();
    do_ResizePage();
	do_SetPageWidth();
}
function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}
</script>
