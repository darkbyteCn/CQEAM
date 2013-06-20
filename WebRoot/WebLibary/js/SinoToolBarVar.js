var ArrAction0 = new Array(true, "取消", "action_cancel.gif", "取消", "do_Cancel");
var ArrAction1 = new Array(true, "暂存", "action_save.gif", "暂存", "do_Save");
var ArrAction2 = new Array(true, "提交", "action_sign.gif", "提交", "do_Complete");
var ArrAction3 = new Array(true, "退回", "arrow_pleft.gif", "退回", "do_Back");
var ArrAction4 = new Array(true, "特送", "action_guiview.gif", "特送", "do_SpecialSend");
var ArrAction5 = new Array(true, "流程查阅", "actn023.gif", "流程查阅", "do_ViewFlow") ;
var ArrAction6 = new Array(true, "阅示设定", "download.gif", "阅示设定", "do_SetReview");
var ArrAction7 = new Array(true, "阅示状态", "year.gif", "阅示状态", "do_ReviewStatus");
var ArrAction8 = new Array(true, "签收", "checkin.gif", "签收", "do_Sign");
var ArrAction9 = new Array(true, "任务信息", "bbsdoc2.gif", "任务信息", "do_ViewComment");

var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5, ArrAction6,
        ArrAction7, ArrAction8, ArrAction9);
var ArrSinoViews = new Array();
var ArrSinoTitles = new Array();

var AV_SENDBACK_MASK = 0x01;
var AV_SPECIALSEND_MASK = 0x02;
var AV_COMPLETE_MASK = 0x04;
var AV_SAVE_MASK = 0x08;
var AV_SIGN_MASK = 0x10;

var OP_SENDBACK_MASK = 0x04;
var OP_SPECIALSEND_MASK = 0x02;
var OP_VIEWFLOW_MASK = 0x08;
var OP_CONFIRM_MASK = 0x800;
var OP_FINISH_MASK = 0x400;

function doUnLoad() {
    if(!isSave) {
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseCancel?sf_actID="+
                           document.getElementById("sf_actID").value;
        document.forms[0].submit();
    }

//    opener.location.reload(true);
    if(opener) {
        opener.doReload();
    } else {
        if((buttonMask & SIGN_MASK) == SIGN_MASK) {
             window.location = "/servlet/com.sino.sinoflow.servlet.PendingTray";
        } else {
             window.location = "/servlet/com.sino.sinoflow.servlet.InTray";
        }
    }
}

function do_SetReview() {
    var resText = selectReviewDlg(document.getElementById("sf_taskid").value);
    eval("reviewArray = " + resText);
    var type = 0;
    type = new Number(reviewArray[0].type) + new Number(reviewArray[0].waitType) * 2;
    document.getElementById("sf_reviewType").value = type;
    Launch_ReviewUsers = reviewArray[0].users;
    document.getElementById("sf_reviewUsers").value = Launch_ReviewUsers;
    document.getElementById("sf_reviewQty").value = getCharCount(Launch_ReviewUsers, ';');
//                document.getElementById("sf_sendtouser").value = Launch_ReviewUsers.substr(0, Launch_ReviewUsers.indexOf("/"));
    setReviewUsers(document.getElementById("sf_actID").value, document.getElementById("sf_reviewType").value,
            document.getElementById("sf_reviewQty").value, Launch_ReviewUsers);
}

function do_ReviewStatus() {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetReviewStatus?sf_actID="+
            document.getElementById("sf_actID").value;
    var popscript;
    popscript = "dialogWidth:450px"
            + ";dialogHeight:250px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function do_Sign() {
    SFQuerySign();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    if(signAct() != "") {
        autoValue(AV_SIGN_MASK);
        alert("签收成功，案件转到在办箱！")
        ShowSinoButton(1);
        ShowSinoButton(2);
        var type = document.getElementById("sf_reviewType").value;
        if((completeMask & OP_SENDBACK_MASK) == OP_SENDBACK_MASK && (type == "0" || type == "")) {
            ShowSinoButton(3);
        }
        if((completeMask & OP_SPECIALSEND_MASK) == OP_SPECIALSEND_MASK && (type == "0" || type == "")) {
            ShowSinoButton(4);
        }
        if((completeMask & OP_SPECIALSEND_MASK) == OP_SPECIALSEND_MASK && (type == "0" || type == "")) {
            ShowSinoButton(4);
        }

        HideSinoButton(8);
    }
    SFPostSign();
    if(Error_Msg != "") {
        alert(Error_Msg);
    }
}

var isSave = false;

function do_Complete() {
    SFQueryComplete();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    var str = getNextTask();
    if(str != "") {
        autoValue(AV_COMPLETE_MASK);
        if(!validation())
            return;
        SFQuerySave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
        clearDivRight();

        document.getElementById("sf_nextTaskData").value = str;
        if(finishMessage() == "") {
            restoreNextTaskData();
            return;
        }
        saveInfo();
        SFPostSave();
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseComplete";
        document.forms[0].submit();
    }
}

function do_Save() {
    autoValue(AV_SAVE_MASK);
    if(!validation())
        return;
    SFQuerySave();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    clearDivRight();
    isSave = true;
    document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseSave";
    document.forms[0].submit();
}

function do_SaveReload() {
    autoValue(AV_SAVE_MASK);
    if(!validation())
        return;
    SFQuerySave();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    clearDivRight();
    isSave = true;
    document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseSaveReload";
    document.forms[0].submit();
}

function do_Cancel() {
    if(opener) {
        window.close();
    } else {
        if((buttonMask & SIGN_MASK) == SIGN_MASK) {
             window.location = "/servlet/com.sino.sinoflow.servlet.PendingTray";
        } else {
             window.location = "/servlet/com.sino.sinoflow.servlet.InTray";
        }        
    }
}

function doBeforeUnload() {
    if(!isSave) {
//        isSave = true;
        window.event.returnValue = "確认取消所有更改?";
    }
}

function do_appInit() {
    //空函数, 应用于 jsp 中可写同一函数取代
}

function doLoad() {
//    fillData(document.getElementById("sf_fillApiData").value);
    if(document.getElementById("sf_lock").value == "1")
        alert("此任务已被其他用戶打开, 数据已被锁定, 只能以只读方式打开数据!");
//    setDivVisibility();
    init_groups();
    do_SelectGroup();
    fillData(document.getElementById("sf_fillApiData").value);
//    setDivRight();
    if(document.getElementById("sf_comment").value != "") {
        alert(document.getElementById("sf_comment").value);
    } else {
        HideSinoButton(9);    
    }
    do_appInit();
    SFQueryOpen();
    if(!Launch_Continue) {
        alert(Error_Msg);
        doUnLoad();
        return;
    }
    var tst;
    tst = document.getElementById("sinoflow_load_data").value;
    if(tst != null && tst != "")
        fillData(tst);

    SFPostOpen();
}

function do_Back() {
    SFQueryComplete();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }

    autoValue(AV_SENDBACK_MASK);
    if(!validation())
        return;

    var str = getBackTask(document.getElementById("sf_actID").value);
    if(str.indexOf("SPLIT") >= 0) {
/*
        var back = confirm("此案件已并发到不同的用戶中, 如使用退回將同时退回这些人处理中的案件, 退回吗?");
        alert(back);
        if(back == false)
            return;
*/

        alert("此案件已并发到不同的用戶中, 不能退回!");
        return;        
    }
    if(str != "") {
        SFQuerySave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
        clearDivRight();
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseBack";
        document.forms[0].submit();
    }
}

function do_SpecialSend() {
    SFQueryComplete();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }

    autoValue(AV_SENDBACK_MASK);
    if(!validation())
        return;
    var sendTask = checkSpecialSend();

    if(sendTask != "") {
        SFQuerySave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
        clearDivRight();
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.ToTask";
        document.forms[0].submit();
    } else {

    }
}

function do_Message() {
/*
    var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    alert("openDocObj = " + openDocObj);
    openDocObj.EditDocument("/app/Validation Rules.doc");
*/
/*
    var wApp = new ActiveXObject("Word.Application.11");
    wApp.Visible = true ;

    wApp.Documents.Open("http://localhost:8080/app/Validation Rules.doc");

//    if( trackRevisions ){ //可以实现痕迹保留呢
         wApp.ActiveDocument.TrackRevisions = true ;
         wApp.ActiveDocument.ShowRevisions = false  ;
//    }else
//    {
//         wApp.ActiveDocument.TrackRevisions = false ;
//         wApp.ActiveDocument.ShowRevisions = false  ;           
//    }
*/
    window.open("http://localhost:8080/app/Validation Rules.doc");
}

function finishMessage() {
    if(document.getElementById("sf_reviewQty").value == "1")
        return "ok";
    var priority = "0";
    var comment = "";
    if((completeMask & OP_FINISH_MASK) == OP_FINISH_MASK) {
        var priorityStr = getPriorityDlg();
        if(priorityStr == "")
            return "";
        var priorityStruct;
        eval("priorityStruct = " + priorityStr);
        priority = priorityStruct.priority;
        comment = priorityStruct.message;
    }
    if((completeMask & OP_CONFIRM_MASK) == OP_CONFIRM_MASK) {
        if(isFlowEnd()) {
            document.getElementById("sfEnd").value = "1";
            if(deliveryStatusEndDlg() == "")
                return "";
        } else {
            if(deliveryStatusDlg() == "")
                return "";
        }
    }
    document.getElementById("sf_comment").value = comment;
    document.getElementById("sf_priority").value = priority;
    return "ok";
}

function isFlowEnd() {
    var prop;
    eval ("prop = " + document.getElementById("sf_nextTaskData").value);
    if(prop.length == 1) {
        if(prop[0].taskName == "STOP")
            return true;
    }
    return false;
}

function do_ViewComment() {
    alert(document.getElementById("sf_comment").value);
}
