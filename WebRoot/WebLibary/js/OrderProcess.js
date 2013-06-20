//按钮指文件BarVarSX.js中的按钮。

var needAttachMenu = true;
var usedInProcedure = true;
var firstNode = false;
var enableSign = false;//是否开放签收按钮,对于需要开放该按钮的省份，需要配置为true。请实施人员注意

function do_ControlProcedureBtn() {
    var frm = document.forms[0];
    var fromPage = document.getElementById("fromPage");//用于特殊处理巡检工单按钮。
    if(usedInProcedure){
        var sfAttr = frm.sf_task_attribute3;
        var sfAttr5 = frm.sf_task_attribute5;
        if(fromPage && fromPage.value == "DETAIL_PAGE"){
            do_ControlOrderDetailPageBtn();
        } else {
            if (sfAttr) {
                var sfAttribute3 = sfAttr.value;
                var sfAttribute5 = sfAttr5.value;
                if (sfAttribute3 == "FILL_DATA") {//第一个节点
                    firstNode = true;
                    do_ControlFirstNodeBtn();
                } else if ( sfAttribute5.indexOf("ATTACHMENT") > -1 ) {//需要管理附件的节点
                    do_ControlOtherNodeBtn_ManageAttachment();
                } else {
                    do_ControlOtherNodeBtn();
                }
            } else {
                do_ControlOrderDetailPageBtn();
            }
        }
    } else {
        do_ControlCommonPageBtn();
    }
}

function do_ControlFirstNodeBtn() {
    var btnCount = ArrActions.length;
    var frm = document.forms[0];
    var primaryKey = frm.transId;
    if(!primaryKey){
        primaryKey = frm.batchId;
    }
    if(!primaryKey){
        primaryKey = frm.systemid;
    }
    var transId = primaryKey.value;
    for (var i = 0; i < btnCount; i++) {
        if (i <= 2 || i == 5) {
            ShowSinoButton(i);
        } else if (i == 13 || i == 16) {
            if (transId == "") {
                HideSinoButton(i);
            } else {
                ShowSinoButton(i);
            }
        } else if(i== 28){
            if(needAttachMenu){
                ShowSinoButton(i);
            } else {
                HideSinoButton(i);
            }
        } else {
            HideSinoButton(i);
        }
    }
}

/**
 * 需要上传附件的节点的按钮控制
 */
function do_ControlOtherNodeBtn_ManageAttachment(){
	var btnCount = ArrActions.length;
    for (var i = 0; i < btnCount; i++) {
        if (i == 0 || i == 2 || i == 3 || i == 5 || i == 16) {
            ShowSinoButton(i);
        } else if(i == 8){
            if(enableSign){
                ShowSinoButton(i);
            } else {
                HideSinoButton(i);
            }
        } else if(i == 28){
            if(needAttachMenu){
                ShowSinoButton(i);
            } else {
                HideSinoButton(i);
            }
        } else {
            HideSinoButton(i);
        }
    }
}

function do_ControlOtherNodeBtn() {
    var btnCount = ArrActions.length;
    for (var i = 0; i < btnCount; i++) {
        if (i == 0 || i == 2 || i == 3 || i == 5 || i == 16) {
            ShowSinoButton(i);
        } else if(i == 8){
            if(enableSign){
                ShowSinoButton(i);
            } else {
                HideSinoButton(i);
            }
        } else if(i == 29){
            if(needAttachMenu){
                do_ControlAttachViewBtn();
            } else {
                HideSinoButton(i);
            }
        } else {
            HideSinoButton(i);
        }
    }
}

function do_ControlOrderDetailPageBtn() {
    var btnCount = ArrActions.length;
    for (var i = 0; i < btnCount; i++) {
        if (i == 0 || i == 16) {
            ShowSinoButton(i);
        } else if(i == 29){
            if(needAttachMenu){
                do_ControlAttachViewBtn();
            } else {
                HideSinoButton(i);
            }
        } else {
            HideSinoButton(i);
        }
    }
}


function do_ControlCommonPageBtn() {
    var btnCount = ArrActions.length;
    for (var i = 0; i < btnCount; i++) {
        if (i == 0 || i == 1) {
            ShowSinoButton(i);
        } else if(i== 28){
            if(needAttachMenu){
                ShowSinoButton(i);
            } else {
                HideSinoButton(i);
            }
        } else {
            HideSinoButton(i);
        }
    }
}


function do_ControlAttachViewBtn(){
    var primaryKey = do_getPrimaryKey();
    if(primaryKey != ""){
        var actionURL = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet";
        actionURL += "?forward=GET_ATTACH_COUNT";
        actionURL += "&orderPkName=" + primaryKey;
        var ajaxProcessor = new AjaxProcessor(actionURL, do_CallbackAttachViewBtn, false);
        ajaxProcessor.performTask();
    } else {
        HideSinoButton(29);
    }
}


function do_CallbackAttachViewBtn(resText){
    if(Number(resText) > 0){
        var divId="sinoDiv29";
        var btnDiv = document.getElementById(divId);
        var btnStr = btnDiv.innerHTML;
        var newLabel = "附件查阅(" + resText + ")";
        btnStr = replaceStr(btnStr, "附件查阅", newLabel);
        btnDiv.innerHTML = btnStr;
        ShowSinoButton(29);
    }
}

function do_CancelApply() {
    if (confirm("确定撤消本单据吗？继续请点击“确定”按钮，否则请点击“取消”按钮！")) {
        isSave = true;
        var frm = document.forms[0];
        if(frm.act){
            frm.act.value = "CANCEL_ACTION";
        } else {
            frm.flowSaveType.value="FLOW_CANCEL";
        }
        frm.sf_opinion.value = "创建人撤销单据";
        frm.sf_flowDesc.value = frm.sf_opinion.value;
        frm.submit();
        var transTypeValue = "";
        var transType = frm.transTypeValue;
        if(!transType){
            transType = frm.workorderTypeDesc;
        }
        if(!transType){
            transType = frm.orderTypeName;
        }
        if (transType) {
            transTypeValue = transType.value;
        } else {
            transTypeValue = "流程单据";
        }
        var orderNo = "";
        var transNo = frm.transNo;
        if(!transNo){
            transNo = frm.workorderBatch;
        }
        if(!transNo){
            transNo = frm.batchNo;
        }
        if (transNo) {
            orderNo = transNo.value;
        }
        var messageDiv = document.getElementById("$$$publicMessage$$$");
        if (messageDiv) {
            messageDiv.style.visibility = "hidden";
        }
        document.getElementById("hintTD").innerHTML = "<font color=\"#008000\" size=\"2\">正在撤销"
            + transTypeValue
            + orderNo
            + "，请稍候......</font><img src=\"/images/wait.gif\">";
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    }
}

function do_ViewOpinion() {
	var primaryKey = do_getPrimaryKey_Obj();
    if(!primaryKey){
        alert("页面没有指定应用的主键，请通知技术支持人员。");
        return;
    }
    var tableName = "AMS_ASSETS_TRANS_HEADER";
    viewOpinion(primaryKey.value, tableName);
}

function do_Back_app() {
    isSave = true;
    var frm = document.forms[0];
    setFrmEnable(frm.name);
    if(frm.act){
        frm.act.value = "REJECT_ACTION";
    } else if(frm.flowSaveType){
        frm.flowSaveType.value="FLOW_BACK";
    }
    frm.submit();
    var transTypeValue = "";
    var transType = frm.transTypeValue;
    if(!transType){
        transType = frm.workorderTypeDesc;
    }
    if(!transType){
        transType = frm.orderTypeName;
    }
    if (transType) {
        transTypeValue = transType.value;
    } else {
        transTypeValue = "流程单据";
    }
    var orderNo = "";
    var transNo = frm.transNo;
    if(!transNo){
        transNo = frm.workorderBatch;
    }
    if(!transNo){
        transNo = frm.batchNo;
    }
    if (transNo) {
        orderNo = transNo.value;
    }
    var messageDiv = document.getElementById("$$$publicMessage$$$");
    if (messageDiv) {
        messageDiv.style.visibility = "hidden";
    }
    document.getElementById("hintTD").innerHTML = "<font color=\"#008000\" size=\"2\">正在退回"
        + transTypeValue
        + orderNo
        + "，请稍候......</font><img src=\"/images/wait.gif\">";
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_ShowDetail(obj){
    var barcode = "";
    if(obj){
        if(obj.tagName){
            if(obj.tagName == "INPUT"){
                barcode = obj.value;
            } else {
                while(obj.tagName != "TR"){
                    obj = obj.parentNode;
                }
                if(obj.tagName == "TR"){
                    obj = getTrNode(obj, "barcode");
                    if(obj){
                        barcode = obj.value;
                    }
                }
            }
        } else {
            barcode = obj;
        }
    }
    if(barcode && barcode != ""){
        var factor = 0.8;
        var width = window.screen.availWidth * factor;
        var height = window.screen.availHeight * factor;
        var left = window.screen.availWidth * (1 - factor) / 2;
        var top = window.screen.availHeight * (1 - factor) / 2;
        var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=DETAIL_ACTION&barcode=" + barcode;
        var winName = "assetsWin";
        var style = "width="
            + width
            + ",height="
            + height
            + ",left="
            + left
            + ",top="
            + top;
        window.open(url, winName, style);
    }
}

var excelType = null;

function do_ImportExcelData(){
    if(!excelType){
        excelType = "1";
    }
    var url = "";
    if(excelType == "1")  {
        url = "/workorder/bts/upFile.jsp";
    } else if(excelType == "2")  {
        url = "/newasset/newSite/upFile.jsp";
    } else if(excelType == "3")  {
    	url = "/workorder/bts/upFile2.jsp";
    } else if(excelType == "4")  {
    	url = "/newasset/newSite/upFileSecond.jsp";
    } else if(excelType=="5"){
    	url = "/workorder/bts/upFile3.jsp";//零购转资
    } else if(excelType=="6"){
    	url = "/workorder/bts/upFile4.jsp";//零购转资
    }
    
    var factor = 0.5;
    var dialogWidth = window.screen.availWidth * factor;
    var dialogHeight = window.screen.availHeight * factor;
    var dialogStyle = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
    return window.showModalDialog(url,"",dialogStyle);
}


function do_getPrimaryKey(){
    var primaryKeyValue = "";
    var primaryKey = do_getPrimaryKey_Obj();
    if(primaryKey){
        primaryKeyValue = primaryKey.value;
    }
    return primaryKeyValue;
}

function do_getPrimaryKey_Obj(){
	var primaryKey = null;
    var frm = document.forms[0];
    if( frm ){
    	primaryKey = frm.transId;
	    if(!primaryKey){
	        primaryKey = frm.batchId;
	    }
	    if(!primaryKey){
	        primaryKey = frm.systemid;
	    }
	    if(!primaryKey){
	        primaryKey = frm.systemId;
	    }
	    if(!primaryKey){
	        primaryKey = frm.publishId;
	    }
    }else{
        primaryKey = document.getElementById("transId");
         if(!primaryKey){
	        primaryKey = document.getElementById("batchId");
	    }
	    if(!primaryKey){
	        primaryKey = document.getElementById("systemid");
	    }
	    if(!primaryKey){
	        primaryKey = document.getElementById("systemId");
	    }
	    if(!primaryKey){
	        primaryKey  = document.getElementById("publishId");
	    }
    }
    return primaryKey;
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
            if(summaryCells[i - colSpan + 1]){
                summaryCells[i - colSpan + 1].style.width = cell.style.width;
            }
        }
    }
}
