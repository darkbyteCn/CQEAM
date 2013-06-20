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
<script type="text/javascript">
    printTitleBar("<%=headerDTO.getTransTypeValue()%>“<%=headerDTO.getTransNo()%>”详细信息");
    var btnCount = ArrActions.length;
    for(var i = 0; i < btnCount; i++){
        ArrActions[i][0] = (i == 0 );
    }
    ArrActions[2] = new Array(<%=headerDTO.getTransType().equals(AssetsDictConstant.ASS_RED)%>, "点击导出", "toexcel.gif", "点击导出", "do_ExportOrder");
    ArrActions[16][0] = true;
    printToolBar();
</script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%=AssetsURLList.ASSETS_TRANS_SERVLET%>" method="post" name="mainFrm">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<input type="hidden" name="transferType" value="<%= headerDTO.getTransferType()%>">
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
    //刷新OA待办
   // document.domain = "sn.cmcc";
//   document.domain.value="sn.cmcc";
    //window.opener.location.reload();
    window.focus();
    do_SetPageWidth();
    do_ComputeSummary();
}


function do_ComputeSummary(){
    var transferType = mainFrm.transferType.value;
    var transType = mainFrm.transType.value;
    if((transferType != "BTW_COMP") && (transType != "ASS-DIS")){
        return;
    }
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "ljjzalue", "jingeralue", "bfzbValue");
        var summaryCell = new Array("currentUnits", "cost", "sumDepreciation", "impairReserve", "deprnCost", "retirementCost");
        var idCount = idArr.length;
        var sumValueArr = new Array();
        for(var i = 0; i < rowCount; i++){
            var tr =  rows[i];
            for(var j = 0; j < idCount; j++){
                var node = getTrNode(tr, summaryCell[j]);
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


function do_Close(){
	window.close();
}
function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_ViewOpinion(){
	var appId = "<%=headerDTO.getTransId()%>";
	var tableName = "AMS_ASSETS_TRANS_HEADER";
	viewOpinion(appId, tableName);
}
function do_ExportOrder(){
    mainFrm.action = "<%=AssetsURLList.ORDER_QUERY_SERVLET%>";
	mainFrm.act.value = "<%=AssetsActionConstant.EPT_DTL_ACTION%>";
	mainFrm.submit();
}
function hejiFuc()
{	
	  var dataTable=document.getElementById("dataTable");
 	  var rows=dataTable.rows.length;   
	  var rows4Value=0; 
	  var rows5Value=0; 
	  var rows6Value=0; 
	  var intRows=parseInt(rows);
	  for(var i=0;i<rows;i++)
	  {
	  	 var a5=dataTable.rows[i].cells[4].innerHTML;
	  	 var a6=dataTable.rows[i].cells[5].innerHTML;
	  	 var a7=dataTable.rows[i].cells[6].innerHTML;
	  	 var inputId5=a5.toString().substring((a5.toString().indexOf("id=",0)+3),a5.toString().indexOf(" class",0))
	  	 var inputId6=a6.toString().substring((a6.toString().indexOf("id=",0)+3),a6.toString().indexOf(" class",0))
	  	 var inputId7=a7.toString().substring((a7.toString().indexOf("id=",0)+3),a7.toString().indexOf(" class",0))
	  	 var v5=parseFloat(document.getElementById(inputId5).value);
	  	 var v6=parseFloat(document.getElementById(inputId6).value);
	  	 var v7=parseFloat(document.getElementById(inputId7).value);
	  	 if(!isNaN(v5))
	  		 {
			rows4Value+=v5;
	  		 }
	  	 if(!isNaN(v6))
	  		 {
			rows5Value+=v6;
	  		 }
	  	 if(!isNaN(v7))
	  		 {
			rows6Value+=v7;
	  		 }
	  }
		document.getElementById("yuanzhiValue").value=rows4Value;
		document.getElementById("jingzhiValue").value=rows5Value;
		document.getElementById("bfzbValue").value=rows6Value;
}
</script>