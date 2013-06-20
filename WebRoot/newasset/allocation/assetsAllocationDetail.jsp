<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-3-31
  Time: 10:45:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
<script type="text/javascript">
    printToolBar();
</script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%=AssetsURLList.ASSETS_ALLOCATION_SERVLET%>" method="post" name="mainFrm">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<input type="hidden" name="transId" value="<%= headerDTO.getTransId()%>">
<jsp:include page="/message/MessageProcess"/>
	<jsp:include page="/newasset/allocation/assetsAllocationHeaderDetail.jsp" flush="true"/>
<div id="searchDiv" style="position:absolute;top:120px;left:0px;width:100%">
</div>
<jsp:include page="/newasset/allocation/assetsAllocationLineDetail.jsp" flush="true"/>
</form>
</body>
</html>
<script type="text/javascript">
function initPage() {
    window.focus();
    do_SetPageWidth();
    do_FormatQuantity();
    do_ComputeSummary();
    do_ControlProcedureBtn();
}

function do_FormatQuantity(){
    var tab = document.getElementById("dataTable");
    if(tab){
        var rows = tab.rows;
        if(rows){
            for(var i = 0; i < rows.length; i++){
                var tr = rows[i];
                var node = getTrNode(tr, "currentUnits");
                if(node){
                    var currentUnits = node.value;
                    currentUnits = formatNum(currentUnits, 0);
                    node.value = currentUnits;
                }
            }
        }
    }
}

function do_ExportOrder(){
    mainFrm.action = "<%=AssetsURLList.ORDER_QUERY_SERVLET%>";
	mainFrm.act.value = "<%=AssetsActionConstant.EPT_DTL_ACTION%>";
	mainFrm.submit();
}

function do_ComputeSummary(){
    var transferType = "<%=headerDTO.getTransferType()%>";
    var transType = mainFrm.transType.value;
    if((transferType != "BTW_COMP") && (transType != "ASS-DIS")){
        return;
    }
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        if(rowCount == 0){
            return;
        }
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "jzzbValue", "canzhiValue", "jingerValue");
        var summaryCell = new Array("currentUnits", "cost", "depreciation", "impairReserve", "scrapValue", "deprnCost");
        var idCount = idArr.length;
        var sumValueArr = new Array();
        for(var i = 0; i < rowCount; i++){
            var tr =  rows[i];
            for(var j = 0; j < idCount; j++){
                var node = getTrNode(tr, summaryCell[j]);
                if(!node){
                    continue;
                }
                if(!sumValueArr[j]){
                    sumValueArr[j] = 0;
                }
                sumValueArr[j] += Number(node.value);
            }
        }
        for(j = 0; j < idCount; j++){
            node = document.getElementById(idArr[j]);
            if(j == 0){
                node.value = sumValueArr[j];
            } else {
                node.value = formatNum(sumValueArr[j], 2);
            }
        }
    }
}

function do_Cancel(){
    self.close();
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}

</script>