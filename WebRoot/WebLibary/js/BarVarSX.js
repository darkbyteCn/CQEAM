var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Cancel");
var ArrAction1 = new Array(true, "保存", "action_save.gif", "保存", "do_Save");
var ArrAction2 = new Array(true, "提交", "action_sign.gif", "提交", "do_Div_Complete_Start");
var ArrAction3 = new Array(false, "退回", "arrow_pleft.gif", "退回", "do_Back");
var ArrAction4 = new Array(false, "特送", "action_guiview.gif", "特送", "do_SpecialdoSend");
var ArrAction5 = new Array(true, "查阅流程", "actn023.gif", "查阅流程", "do_ViewFlow") ;
var ArrAction6 = new Array(false, "阅示设定", "download.gif", "阅示设定", "do_SetReview");
var ArrAction7 = new Array(false, "阅示状态", "year.gif", "阅示状态", "do_ReviewStatus");
var ArrAction8 = new Array(true, "签收", "checkin.gif", "签收", "do_Sign");
var ArrAction9 = new Array(false, "任务信息", "bbsdoc2.gif", "任务信息", "do_ViewComment");
var ArrAction1001 = new Array(false, "转送", "assign.gif", "转送", "do_forward");
var ArrAction10 = new Array(false, "版本比较", "bbsdoc2.gif", "版本比较", "do_CompareDocument");
var ArrAction11 = new Array(false, "生成合同正文", "bbsdoc2.gif", "生成合同正文", "do_CreateContractDoc");
var ArrAction12 = new Array(false, "撤销", "action_cancel.gif", "撤销", "do_CancelApply");
var ArrAction13 = new Array(false, "选择合同", "action_view.gif", "选择合同", "do_SelectContract");
var ArrAction14 = new Array(false, "打印", "print.gif", "打印", "do_Print");
var ArrAction15 = new Array(false, "查阅审批记录", "action_viewstatus.gif", "查阅审批记录", "do_ViewOpinion");
var ArrAction16 = new Array(false, "填写审批意见", "action_sign.gif", "填写审批意见", "inputOpinion");
var ArrAction17 = new Array(false, "完成阅示", "action_sign.gif", "完成阅示", "do_cc_Complete");
var ArrAction18 = new Array(false, "阅示并抄送", "action_sign.gif", "阅示并抄送", "do_cc_Send");
var ArrAction19 = new Array(false, "修订合同正文", "action_view.gif", "修订合同正文", "SW_EditContractDoc");
var ArrAction20 = new Array(false, "帮助", "action_help.gif", "帮助", "do_help");
var ArrAction21 = new Array(false, "删除", "action_cancel.gif", "删除", "do_Delete");
var ArrAction22 = new Array(false, "授权委托书", "bbsdoc2.gif", "授权委托书", "showProxyBook");
var ArrAction1002 = new Array(false, "抄送", "write.gif", "抄送", "do_Copy");
var ArrAction1003 = new Array(false, "提问", "question.gif", "提问", "do_Question");
var ArrAction1004 = new Array(false, "会签设定", "cycle_review.gif", "会签设定", "do_SetCycle");
var ArrAction1005 = new Array(false, "会签状态", "year.gif", "会签状态", "do_CycleStatus");
var ArrAction1006 = new Array(false, "附件管理", "clip.gif", "附件管理", "do_upload");
var ArrAction1007 = new Array(false, "附件查阅", "clip.gif", "附件查阅", "do_upload");

var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5,
        ArrAction6, ArrAction7, ArrAction8, ArrAction9, ArrAction1001, ArrAction10, ArrAction11, ArrAction12,
        ArrAction13, ArrAction14, ArrAction15, ArrAction16,ArrAction17,ArrAction18,ArrAction19,ArrAction20,
        ArrAction21, ArrAction22, ArrAction1002, ArrAction1003, ArrAction1004, ArrAction1005, ArrAction1006, ArrAction1007);
		
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
var OP_SENDTO_MASK = 0x10;
var OP_ASK_MASK = 0x20;
var OP_CONFIRM_MASK = 0x8000;
var OP_FINISH_MASK = 0x4000;
var CarbonCopyOpinion = "已阅";
var autoSign = false;

var isLoaded = false;
var autoSign = false;

function doUnLoad() {
    if(!isSave) {
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseCancel?sf_actID="+
                           document.getElementById("sf_actID").value;
//        document.forms[0].action = "/servlet/com.test.servlet.AppCaseCancel?sf_actID="+
//                             document.getElementById("sf_actID").value;
        document.forms[0].submit();
    }

//    opener.location.reload(true);
    if(opener) {
        if(typeof opener.doReload != "undefined"){
            opener.doReload();
        } else {
            if(typeof opener.parent.frames("main").doReload != "undefined")
                opener.parent.frames("main").doReload();
        }
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
    if(!isLoaded && !autoSign) {
        alert("页面数据没有完全载入, 暫时不能签收");
        return;
    }
    SFQuerySign();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    if(signAct().indexOf("Success") >= 0) {
        autoValue(AV_SIGN_MASK);
//        alert("签收成功，用户接收此案件！")
        ShowSinoButton(1);
        ShowSinoButton(2);
        var type = document.getElementById("sf_reviewType").value;
        if((completeMask & OP_SENDBACK_MASK) == OP_SENDBACK_MASK && (type == "0" || type == "")) {
            ShowSinoButton(3);
        }
        if((completeMask & OP_SPECIALSEND_MASK) == OP_SPECIALSEND_MASK && (type == "0" || type == "")) {
            ShowSinoButton(4);
        }

        HideSinoButton(8);
    } else {
        alert("签收失败,此案件可能已被其他用户签收");
        window.close();
        return;
    }
    SFPostSign();
    if(Error_Msg != "") {
        alert(Error_Msg);
    }
}

var isSave = false;
function do_AppValidate(){
    return true;
}

function do_ProcessEmergent(){
    var emergentLevel = document.forms[0].emergentLevel;
    if(emergentLevel && emergentLevel.tagName == "SELECT"){
        document.getElementById('sf_priority').value = emergentLevel.value;
    } else if(emergentLevel && emergentLevel.tagName == "INPUT"){
        var valueArr = new Array(0, 1, 2, 3);
        var lebelArr = new Array("正常", "急", "加急", "特急");
        for(var i = 0; i < lebelArr.length; i++){
            if(lebelArr[i] == emergentLevel.value){
                document.getElementById('sf_priority').value = valueArr[i];
                break;
            }
        }
    }
}

function do_Div_Complete_Start() {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能完成");
        return;
    }
    do_ProcessEmergent();
    var copyUsers = "";
    if(document.getElementById("sf_caseID").value.indexOf(":") > 0) {
        isSave = true;
        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseComplete?send='0'";
        document.forms[0].submit();
        return;
    }

    //此处添加内容是内蒙合同项目为"变更、解除、作废、移交、借阅"等流程专门添加。
    //目的在于使得页面验证工作在选人之前进行，（注意:原来在选人之后的验证工作已经注销）
    //其它地方可根据需要进行斟酌,作者盖连功。
    //关联AppStandard.js中147行
    var isAppValidatePass=false;		//通过OR NO
    var sfProcedureValue=document.getElementById("sf_procedure").value;
    if (sfProcedureValue.indexOf("盘点") >= 0){
        if(document.getElementById("sf_task_attribute1").value=='from'){
           isAppValidatePass=do_AppValidate(true);
        if(!isAppValidatePass){return;}			//验证不通过则停止
        }

    }else if ( sfProcedureValue.indexOf("预报废") < 0 && sfProcedureValue.indexOf("报废") >= 0){
          if(document.getElementById("sf_task_attribute1").value=='from'){
        isAppValidatePass=do_AppValidate();
    	if(!isAppValidatePass){return;}	}		//验证不通过则停止
    }else if (sfProcedureValue.indexOf("巡检") >= 0){
          if(document.getElementById("sf_task_attribute1").value=='from'){
        isAppValidatePass=do_AppValidate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
              }
    }else if (sfProcedureValue.indexOf("非资产") >= 0){
          if(document.getElementById("sf_task_attribute1").value=='from'){
        isAppValidatePass=do_AppValidate();
    	if(!isAppValidatePass){return;}			//验证不通过则停止
              }
    }else{
    	//验证不通过则停止, 验证请编写覆盖 do_AppValidate()
    	if(document.getElementById("sf_task_attribute3").value=='FILL_DATA' || document.getElementById("sf_task_attribute1").value=='from' ){
	    	isAppValidatePass=do_AppValidate();
	    	if(!isAppValidatePass){
	    		return;
	    	}			
   		}
    }
    
    SFQueryComplete();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    getNextTask2();
}
function do_Div_Complete_End(str) {
    autoValue(AV_COMPLETE_MASK);
//    if(!validation())
//        return;
    SFQuerySave();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
/*
    inputOpinion();
    if(document.getElementById("sf_opinion").value == "undefined") {
        document.getElementById("sf_opinion").value = "";
        return;            
    }
*/
    document.getElementById("sf_nextTaskData").value = str;
    //alert("next = " + document.getElementById("sf_nextTaskData").value);
    if(finishMessage() == "") {
        restoreNextTaskData();
        return;
    }

    SFPostSave();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    clearDivRight();
//    saveNextTaskUsers();
    saveInfo();
    isSave = true;
    //alert(document.getElementById("flowCode"));
    do_Complete_app_yy();
}

function saveOpinion(){
    //空实现。
}

function setCopy(){
    //空实现。
}

function do_Complete_app_yy(){
    alert("请负责本页开发的人员在页面实现方法do_Complete_app_yy");
}

function do_Complete() {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能完成");
        return;
    }
    if(!do_App_Validate()) {
        return;
    }
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
/*        
        inputOpinion();
        if(document.getElementById("sf_opinion").value == "undefined") {
            document.getElementById("sf_opinion").value = "";
            return;
        }
*/
        document.getElementById("sf_nextTaskData").value = str;
        if(finishMessage() == "") {
            restoreNextTaskData();
            return;
        }
        saveOpinion();
        SFPostSave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
//        clearDivRight();
        saveInfo();
        isSave = true;
        do_Complete_app()
//        if( do_InsertMark() ){
//        	do_Complete_app();
//        }
    }
}

function do_Save() {
try{
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能保存");
        return;
    }
    do_ProcessEmergent();
    autoValue(AV_SAVE_MASK);
    if(!validation()) return;
    SFQuerySave();
    if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
    clearDivRight();
    saveInfo();
    SFPostSave();
    isSave = true;
    do_Save_app();
    }catch(ex){ alert( ex.message ); }
}

function do_Cancel() {
    var canClose = true;
    if(firstNode){
        canClose = confirm("确认关闭本单据吗？");
    }
    if (canClose) {
        if (window.opener) {
            window.close();
        } else {
            if ((buttonMask & SIGN_MASK) == SIGN_MASK) {
                window.location = "/servlet/com.sino.sinoflow.servlet.PendingTray";
            } else {
                window.location = "/servlet/com.sino.sinoflow.servlet.InTray";
            }
        }
    }
}

function doBeforeUnload() {
    if(window.opener != null && !isSave) {
//        window.event.returnValue = "确认取消所有更改?";
    }
}

function do_appInit() {
    //空函数, 应用于 jsp 中可写同一函数取代
}

function doLoad() {
//    if(ArrActions[8][0] == true || document.getElementById("sf_event").value == "5" || document.getElementById("sf_event").value == "4") {
        autoSign = true;
        do_Sign();
//    }

    if(document.getElementById("sf_caseID").value.indexOf(":") >= 0  || document.getElementById("sf_copyFlag").value == "1") {
        var nCount=ArrActions.length;
        for (var j=1 ;j< nCount;j++){
            HideSinoButton(j);
        }
//        ShowSinoButton(24);
    }
//    fillData(document.getElementById("sf_fillApiData").value);
    if(document.getElementById("sf_lock").value == "1")
        alert("此任务已被其他用户打开, 数据已被锁定, 只能以只读方式打开数据!");
    //setDivVisibility();  //下面的流程按钮不灰色了]
    init_groups();
    if (document.getElementById("sf_isNew").value=="1") {
        do_SelectGroup();
    }
    fillData(document.getElementById("sf_fillApiData").value);
    //setDivRight();  //下面的流程按钮不灰色了
    if(document.getElementById("sf_comment").value != "") {
        //alert(document.getElementById("sf_comment").value);
    } else {
//        HideSinoButton(9);
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

    isLoaded = true;
}

function do_Back() {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能退回");
        return;
    }
    SFQueryGoBack();
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
        var back = confirm("此案件已并发到不同的用户中, 如使用退回將同时退回这些人处理中的案件, 退回吗?");
        alert(back);
        if(back == false)
            return;
*/

        alert("此案件已并发到不同的用户中, 不能退回!");
        return;        
    }
    if(str != "") {
        var opinion = backOpinionDlg();
        if(opinion == undefined || opinion == "" || opinion == "undefined")
            return;
        document.getElementById("sf_opinion").value = opinion;
        SFQuerySave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
        clearDivRight();
        document.getElementById("sf_event").value = "";; 
        saveInfo();
        isSave = true;
        do_Back_app();
    }
}

function do_SpecialSend() {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能特送");
        return;
    }
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
    if(sendTask && sendTask != "") {
        var str = toTask(sendTask);
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
/*
            inputOpinion();
            if(document.getElementById("sf_opinion").value == "undefined") {
                document.getElementById("sf_opinion").value = "";
                return;
            }
*/
            document.getElementById("sf_nextTaskData").value = str;
            if(finishMessage() == "") {
                restoreNextTaskData();
                return;
            }
            setNextFlowDesc();
            SFPostSave();
            if(!Launch_Continue) {
                if(Error_Msg != "")
                    alert(Error_Msg);
                return;
            }
            saveInfo();
            isSave = true;
            do_SpecialSend_app();
        }
    }
}

function finishMessage() {
    if(document.getElementById("sf_reviewQty").value == "1")
        return "ok";
//    var priority = "0";
    var comment = "";
    if((completeMask & OP_FINISH_MASK) == OP_FINISH_MASK) {
        var priorityStr = getPriorityDlg();
        if(priorityStr == "")
            return "";
        var priorityStruct;
        eval("priorityStruct = " + priorityStr);
        document.getElementById("sf_priority").value = priorityStruct.priority;
        comment = priorityStruct.message;
    }
    if((completeMask & OP_CONFIRM_MASK) == OP_CONFIRM_MASK) {
        if(isFlowEnd()) {
            document.getElementById("sf_end").value = "1";
	     
//            if (Launch_Code != "CANCEL_APPLY") {
//		   if (deliveryStatusEndDlg() == "") return "";
//	     }else{}
        } else {
            var taskStr = document.getElementById("sf_nextTaskData").value;
            var prop;
            eval("prop = " + taskStr);
            var taskName = getTaskName(prop);
            if(taskName != "") {
                if(deliveryStatusAndMessageDlg( getJsonData(document.getElementById("sf_nextTaskData").value, "flowHint:")) == "")
                    return "";
            } else {
                var temp = inputOpinion(getJsonData(document.getElementById("sf_nextTaskData").value, "flowHint:"));
                if(temp == "")
                    return "";
                else
                    document.getElementById("sf_opinion").value = temp;
            }
        }
    }
    if(isFlowEnd())
        document.getElementById("sf_end").value = "1";
    document.getElementById("sf_comment").value = comment;
//    document.getElementById("sf_priority").value = priority;
    return "ok";
}

function isFlowEnd() {
    var prop;
    var tempStr = document.getElementById("sf_nextTaskData").value;
    while(tempStr.charAt(0) == '[')
        tempStr = tempStr.substring(1, tempStr.length - 1);
    tempStr = "[" + tempStr + "]";
    eval ("prop = " + tempStr);
    if(!prop.length) {
        if(prop.taskName == "STOP")
            return true;
        else
            return false;
    } else if(prop.length == 1) {
        if(prop[0].taskName == "STOP")
            return true;
        else {
 	tempStr = getTaskName(prop[0]);
            if(tempStr.indexOf(";") < 0){
                return false;
            } else {
                var tempArr = tempStr.split(";");
                for(var j = 0; j < tempArr.length; j++) {
                    if(tempArr[j] != "STOP")
                        return false;
                }
                return true;
            }
        }
    } else {
        if(prop[prop.length - 1].taskName == 'STOP') {
            return true;
        } else {
            return false;
        }
    }
}

function do_ViewComment() {
    alert(document.getElementById("sf_comment").value);
}

function do_ViewFlow() {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.ViewFlowServlet?actId="+
            document.getElementById("sf_actID").value + "&project='" + sf_project +
            "'&proc='" + document.getElementById("sf_procedure").value + "'&task='" +
            document.getElementById("sf_taskname").value + "'&procId='" +
            document.getElementById("sf_procedureid").value + "'";
//    var popscript;
//    popscript = "dialogWidth:1000px"
//            + ";dialogHeight:px"
//            + ";center:yes;status:no;scrollbars:no;help:no";

    h = window.screen.height;
    w = window.screen.width;
//    f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,scroll=yes,resizable=yes";

    var f1 = "dialogWidth:" + w
            + ";dialogHeight:" + h
            + ";center:yes;status:no;scrollbars:no;help:no";

    return window.showModalDialog(url, null, f1);

}

function do_forward() {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能转送");
        return;
    }
    var taskUsers = getGroupsUsersNames(document.getElementById("sf_project").value,
            document.getElementById("sf_group").value, document.getElementById("sf_role").value);
    var task;
    eval("task = " + taskUsers);
    var userArr = task[0].usernames.split(",");
    var realArr = task[0].realnames.split(",");
    var realnames = "";
    var usernames = "";
    if(realArr.length == 1) {
        alert("没有其他用户可转送!");
        return;
    }
    for(var i = 0; i < realArr.length; i++) {
        var realname = realArr[i].split("/");
        if(realname[0] != document.getElementById("sf_user").value) {
            if(realnames != "")
                realnames += ",";
            realnames += realArr[i];
            if(usernames != "")
                usernames += ",";
            usernames += userArr[i];
        }
    }
    taskUsers = "{taskName:'" + document.getElementById("sf_taskname").value +
            "', groupName:'" + document.getElementById("sf_group").value + "', roleName:'" +
            document.getElementById("sf_role").value + "', realnames:'" + realnames + "', usernames:'" +
            usernames + "', flowProp:'0'}";
//    var users = selectForwardParticipantDlg(taskUsers,document.getElementById("sf_flowDesc").value);
    var users = selectForwardParticipantDlg(taskUsers,document.getElementById("myOpinion").value);
    if(users == "")
        return "";

    var prop;
    eval("prop = " + users);
    if(prop[0].opinion != "") {
//        document.getElementById("sf_opinion").value = "转送:" + prop[0].opinion;
        document.getElementById("sf_opinion").value = prop[0].opinion;
    }

    document.getElementById("sf_nextTaskData").value = "{taskName:'" + document.getElementById("sf_taskname").value +
            "', groupName:'" + document.getElementById("sf_group").value + "', roleName:'" +
            document.getElementById("sf_role").value + "', realnames:'" + prop[0].realnames + "', usernames:'" +
            prop[0].usernames + "', flowProp:'0', flowHint:'" + document.getElementById("sf_flowDesc").value +
            "', flowDesc:'" + document.getElementById("sf_flowDesc").value + "', flowCode:''}";
    
    var nameArr = prop[0].realnames.split("/");
    document.getElementById("sf_store3").value = nameArr[0];

    if(finishMessage() == "") {
        restoreNextTaskData();
        return;
    }
    setNextFlowDesc();
    document.getElementById("sf_event").value = "3";
    document.getElementById("sf_eventUser").value = prop[0].usernames;
    saveInfo();

    isSave = true;
    eval("task = " + users);
    setFrmEnable(document.forms[0].name);
/*
    document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseForward?toUser='"
            + task[0].usernames + "'";
    document.forms[0].submit();
*/
    do_Complete_app();
}

function inputOpinion(message){
    if(!isReviewUser()) {
        var styleStr = "dialogWidth:500px;dialogHeight:400px;scroll:no;center:yes;status:no;";
        return window.showModalDialog(
            "/flow/inputApproveContent.jsp?flowDesc='" + message + "'",null,styleStr);
    }
}

function do_SpecialSend_app(toTask) {
    if(!isLoaded) {
        alert("页面数据没有完全载入, 暫时不能特送");
        return;
    }
    do_Complete_app();
}

function deliveryStatusAndMessageDlg(message) {
    var url;
    url = "/flow/deliveryStatus.jsp?flowDesc='" + message + "'";
    var popscript;
    popscript = "dialogWidth:425px"
            + ";dialogHeight:330px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";
    document.getElementById("sf_opinion").value = window.showModalDialog(url, document.getElementById("sf_nextTaskData").value, popscript);
    return document.getElementById("sf_opinion").value;
}

function do_help(){
   var currApp = document.getElementById("currAppName");
   var node = document.getElementById("sf_task_attribute3");
   
 
   
   if( (currApp != null) && (node != null) ){
        var currAppName = currApp.value;
	 var nodeName = node.value;
	 
	 if(nodeName != ""){
	    var helpUrl = "/help/appNodehelpAction.do?";
	    helpUrl += "appName=" + currAppName + "&nodeName=" + nodeName;
	    
	    var h = window.screen.height;
           var w = window.screen.width;
           var f1 = "top=0,left=0,width=" + w +  ",height=" + h + ",titlebar,scrollbars=yes,scroll=yes,resizable=yes,toolbar=no";
	    
	    window.open(helpUrl,"_blank",f1);
	 }else{}
   }else{}
}

function getLevel2Group(){
	var url = "/servlet/com.sino.ams.bean.GetLevel2GroupServlet?group=" + document.getElementById("sf_group").value;
	makeRequest(url, ajaxFunction);
	if(ajaxReturn.indexOf("ERROR") >= 0) {
		return "";
	}
	return ajaxReturn;
}


function getZeroGroup(costCenterCode, companyCode){
	var url = "/servlet/com.sino.ams.workorder.servlet.getGrouNameByZero?costCenterCode=" + costCenterCode+"&companyCode="+companyCode;
	makeRequest(url, ajaxFunction);
	if(ajaxReturn.indexOf("ERROR") >= 0) {
		return "";
	}
	return ajaxReturn;
}

function isLevel2Group(){
	var url = "/servlet/com.sino.ams.bean.IsLevel2GroupServlet?group='"
            + sf_group.replaceAll("+", "%2B") + "'&users='" + sf_handler + "'";
	makeRequest(url, ajaxFunction);
	if(ajaxReturn.indexOf("ERROR") >= 0) {
		return "";
	}
	return ajaxReturn;
}

function getSpecialityGroup(assetNo){
    var url = "/servlet/com.sino.ams.bean.GetSpecialityGroupServlet?no=" + assetNo;
	makeRequest(url, ajaxFunction);
	if(ajaxReturn.indexOf("ERROR") >= 0) {
		return "";
	}
	return ajaxReturn;
}

function isSpeciality(){
	var url = "/servlet/com.sino.ams.bean.IsSpecialityServlet?group='"
            + sf_group.replaceAll("+", "%2B") + "'&users='" + sf_handler + "'";
	makeRequest(url, ajaxFunction);
	if(ajaxReturn.indexOf("ERROR") >= 0) {
		return "";
	}
	return ajaxReturn;
}

function getLgoinNameByEmpId(empId) {
    var url = "/servlet/com.sino.ams.bean.GetLoginNameByEmpIdServlet?id="
            + empId;
    makeRequest(url, ajaxFunction);
    if(ajaxReturn.indexOf("ERROR") >= 0) {
        return "";
    }
    return ajaxReturn;
}

function do_Copy() {
    var copyUsers = copySelectDlg2();
    if(copyUsers != "") {
        var copyArr;
        eval("copyArr = " + copyUsers);
        document.getElementById("sf_copyUsers").value = copyArr.copyUsers;
        document.getElementById("sf_copyMsg").value = copyArr.copyMsg;
        do_Div_Complete_Start();
    }
}

function copySelectDlg2() {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.CopySelectServlet";
    var popscript;
    popscript = "dialogWidth:620px"
            + ";dialogHeight:430px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";

    return window.showModalDialog(url, null, popscript);
}

function do_Question() {
    var askUser = askSelectDlg();
    if(askUser != "") {
        var askArr;
        eval("askArr = " + askUser);
        document.getElementById("sf_event").value = "4";
        document.getElementById("sf_eventUser").value = askArr.askUser;
//        document.getElementById("sf_opinion").value = askArr.askMsg;
        document.getElementById("myOpinion").value = askArr.askMsg;
        document.getElementById("sf_opinion").value = document.getElementById("myOpinion").value;

        saveInfo();
        isSave = true;
        do_Complete_app();
    }
}

function askSelectDlg() {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.AskSelectServlet?sf_actID='"
            + document.getElementById("sf_actID").value + "'";
    var popscript;
    popscript = "dialogWidth:420px"
            + ";dialogHeight:230px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";

    var largeText = new Array();
    largeText[0] = document.getElementById("sf_actID").value;
    largeText[1] = document.getElementById("myOpinion").value;
    return window.showModalDialog(url, largeText, popscript);
}

function do_SetCycle() {
    var allUsers;
    if(document.getElementById("sf_cycleType").value == "0" || document.getElementById("sf_cycleType").value == "") {
        allUsers = getGroupsUsersNames(sf_project, document.getElementById("sf_group").value,
                document.getElementById("sf_role").value);
        var cycleArr;
        eval("cycleArr = " + allUsers);
        allUsers = cycleArr[0].usernames.replaceAll(",",";");
    } else {
        var level = getCharCount(document.getElementById("sf_group").value, '.');
        var searchGroup = "";
        for(var i = 0; i < level; i++) {
            if(searchGroup != "")
                searchGroup += ".";
            searchGroup += "*";
        }
        allUsers = getGroupsUsersNames(sf_project, searchGroup, document.getElementById("sf_role").value);
        var cycleArr;
        eval("cycleArr = " + allUsers);
        allUsers = cycleArr[0].usernames.replaceAll(",",";");
    }
    var cycleUsers;
    if(Launch_CycleUsers == "")
        cycleUsers = document.getElementById("sf_cycleUsers").value;
    else
        cycleUsers = Launch_CycleUsers;
    var userArr = cycleUsers.split(";");
    var loginName = document.getElementById("sf_loginName").value;
    cycleUsers = "";
    for(var j = 0; j < userArr.length; j++) {
        var user = userArr[j];
//        if(user == loginName)
//            continue;
        if(cycleUsers != "")
            cycleUsers += ";";
        cycleUsers += userArr[j];
    }

    var cycleType = document.getElementById("sf_cycleType").value;
    var resText = selectCycleDlg(cycleType, allUsers, cycleUsers);
    if(resText == "")
        return;
    var cycleArray;
    eval("cycleArray = " + resText);
//    document.getElementById("sf_cycleType").value = cycleArray[0].type;
    Launch_CycleType = cycleType;
    Launch_CycleQueryType = "0";
    Launch_CycleUsers = cycleArray[0].users;
    Launch_CycleQueryUsers = Launch_CycleUsers;
    if(Launch_CycleUsers == "")
        Launch_IsEndCycleView = true;
    document.getElementById("sf_cycleUsers").value = Launch_CycleUsers;
    document.getElementById("sf_cycleQty").value = getCharCount(Launch_CycleUsers, ';');
    document.getElementById("sf_cycleType").value = cycleType;
//    setCycleUsers(document.getElementById("sf_actID").value, document.getElementById("sf_cycleType").value,
//            document.getElementById("sf_cycleQty").value, Launch_CycleUsers);
    setCycleUsers = true;
}

function do_CycleStatus() {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetCycleStatus?sf_actID="+
            document.getElementById("sf_actID").value;
    var popscript;
    popscript = "dialogWidth:440px"
            + ";dialogHeight:260px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function saveNextTaskUsers() {
    var names = "";
    for(var i = 0; i < extFlowObject.getSelectedParsonCount(); i++) {
        var name = extFlowObject.getSelectparsonValue(i,2);
        var nameArr = name.split("/");
        if(names == "") {
            names = nameArr[0];
        } else {
            names += "," + nameArr[0];
        }
    }
    document.getElementById("sf_store3").value = names;
}

function myAlert( str ){
	//alert( str );
}

function backOpinionDlg() {
    var url;
    url = "/flow/backOpinion.jsp";
//    var popscript;
//    popscript = "dialogWidth:420px"
//            + ";dialogHeight:130px"
//            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";

    var factor = 0.45;
    var dialogWidth = window.screen.availWidth * factor;
    var dialogHeight = window.screen.availHeight * factor;
    var dialogLeft = window.screen.availWidth * (1 - factor) / 2;
    var dialogTop = window.screen.availHeight * (1 - factor) / 2;

    var style = "dialogLeft:"+dialogLeft+"px;" +
            "dialogTop:"+dialogTop+"px;" +
            "dialogWidth:"+dialogWidth+"px;" +
            "dialogHeight:"+dialogHeight+"px;" +
            "toolbar:no;" +
            "directories:no;" +
            "status:no;" +
            "menubar:no;" +
            "scrollbars:no;" +
            "revisable:no;" +
            "resizable:yes";
    return window.showModalDialog(url, null, style);
}

