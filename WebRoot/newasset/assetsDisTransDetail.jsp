<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String titleText = headerDTO.getTransTypeValue() + "¡°"+headerDTO.getTransNo()+"¡±ÏêÏ¸ÐÅÏ¢";
%>
<head>
	<title><%=titleText%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
<script type="text/javascript">
    printTitleBar("<%=titleText%>");
    printToolBar();
</script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%=AssetsURLList.ASSETS_TRANS_SERVLET%>" method="post" name="mainFrm">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<input type="hidden" name="transId" value="<%= headerDTO.getTransId()%>">

<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:50px;left:1px;width:100%">
<jsp:include page="/newasset/headerDetail.jsp" flush="true"/>
</div>
<jsp:include page="/newasset/transLineDetail.jsp" flush="true"/>
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
    do_ProcessTableAlign();
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

function do_Close(){
    window.close();
}

function do_ComputeSummary(){
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        if(rowCount == 0){
            return;
        }
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "ljjzalue", "jingeralue", "bfzbValue");
        var summaryCell = new Array("currentUnits", "cost", "sumDepreciation", "impairReserve", "deprnCost", "retirementCost");
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
            if(!node){
                continue;
            }
            if(j == 0){
                node.value = sumValueArr[j];
            } else {
                node.value = formatNum(sumValueArr[j], 2);
            }
        }
    }
}

function do_ProcessTableAlign(){
    var summaryTable = document.getElementById("summaryTable");
    var headTable = document.getElementById("headTable");
    var rows = headTable.rows;
    var lastRow = rows[rows.length - 1];
    var cells = lastRow.cells;
    var cellCount = cells.length;
    var summaryRow = summaryTable.rows[0];
    var summaryCells = summaryRow.cells;
    var firstSummaryCell = summaryCells[0];
    var colSpan = firstSummaryCell.colSpan;
    var firstWidth = 0;
    summaryTable.style.width = headTable.style.width;
    for(var i = 0; i < cellCount; i++){
        var cell = cells[i];
        if(i < colSpan){
            firstWidth += cell.offsetWidth;
            if(i == colSpan - 1){
                firstSummaryCell.style.width = firstWidth;
            }
        } else {
            summaryCells[i - colSpan + 1].style.width = cell.style.width;
        }
    }
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
</script>