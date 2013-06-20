<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
	<title>单据打印页面</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" style="overflow-x:scroll;">
<jsp:include page="/newasset/headerDetail.jsp" flush="true"/>
<div style="left:0px;width:100%" >
<fieldset id="ddd" style="border:0; position:relative;width:100%;">
    <legend>
		<table width="100%" id="buttonSet">
		    <tr>
		    	<td width="100%" align="left">
		    	    <form action="<%=AssetsURLList.ORDER_PRINT_SERVLET%>" name="mainFrm" method="post">
			        <img src="/images/eam_images/page_config.jpg" alt="打印设置" onClick="do_SetupPrint(); return false;">
			        <img src="/images/eam_images/print_view.jpg" alt="打印预览" onClick="do_PrevPrint(); return false;">
			        <img src="/images/eam_images/print.jpg" alt="打印" onClick="do_PrintOrder(); return false;">
			        <%
						AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
						String transferType = headerDTO.getTransferType(); 
						String transTypeTemp = headerDTO.getTransType(); 
						if(transTypeTemp.equals(AssetsDictConstant.ASS_DIS)){//报废	增加【查阅意见】
						%>
						 <img src="/images/eam_images/view_opinion.jpg" alt="查阅意见" onClick="do_ViewOpinion(); return false;">
						<%
						}
			            if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)
			            || ( !transTypeTemp.equals(AssetsDictConstant.ASS_SUB)
			               &&!transTypeTemp.equals(AssetsDictConstant.ASS_FREE)
			               &&!transTypeTemp.equals(AssetsDictConstant.ASS_RED)
			               )
			            ){//公司间调拨//报废或处置	
				            String transIdTemp = headerDTO.getTransId(); 
				            String printTypeTemp = headerDTO.getPrintType(); 
	                  %>
	                 
	                         
			        <img src="/images/eam_images/export.jpg" alt="导出" onClick="do_Export('<%=transIdTemp%>','<%=transTypeTemp%>','<%=printTypeTemp%>'); return false;">			        
			        <%} %>
			        <%--<img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close(); return false;">--%>
			        </form>
		    	</td>
		    </tr>
		</table>
    </legend>
	<jsp:include page="/newasset/printLineDetail.jsp" />
	<table>
		<tr><td>&nbsp</td></tr>
	</table>
	<jsp:include page="/newasset/approveContent.jsp" flush="true"/>
</fieldset>
</div>
<object id="webbrowser" width="0" height="0" classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
</body>
</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_SetupPrint(){
	webbrowser.execwb(8,0);
}

function do_PrevPrint(){
<%
	if(!transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
		document.getElementById("table1").style.display = "none";
<%
	}
%>

	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(7,0);
<%
	if(!transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
		document.getElementById("table1").style.display = "block";
<%
	}
%>

	document.getElementById("buttonSet").style.display = "block";
}

function do_PrintOrder(){
	document.getElementById("table1").style.display = "none";
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(6,6);
	document.getElementById("table1").style.display = "block";
	document.getElementById("buttonSet").style.display = "block";
}
function do_Export(transId,transType,printType) {
      //alert('公司间调拨导出'+transId+","+transType+","+printType);
      document.mainFrm.target = "_self";
      document.mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.OrderPrintServlet?act=EXPORT_ACTION&transType=" + transType+"&transId="+transId+"&printType=" + printType;           
      document.mainFrm.submit();
    //var url = "/servlet/com.sino.ams.newasset.servlet.OrderPrintServlet?act=EXPORT_ACTION&transType=" + transType+"&transId="+transId+"&printType=" + printType;       
    //alert(url);
    //var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no";
    //var newWin = window.open(url, 'printWin2', style);
	//newWin.focus();
}
function showDetail(transId){
    var transType = mainFrm.transType.value;
    var url = "/servlet/com.sino.ams.newasset.servlet.OrderPrintServlet?act=DETAIL_ACTION&transType=" + transType+"&transId="+transId;
    if(mainFrm.printType){
    	url += "&printType=" + getRadioValue("printType");
    }
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no";
    var newWin = window.open(url, 'printWin', style);
	newWin.focus();
}
function do_Close(){
	window.opener=null;
	window.close();
}
function do_ViewOpinion(){
	var appId = "<%=headerDTO.getTransId()%>";
	var tableName = "AMS_ASSETS_TRANS_HEADER";
	viewOpinion(appId, tableName);
}
</script>