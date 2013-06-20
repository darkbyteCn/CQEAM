var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Cancel");
var ArrAction1 = new Array(false, "暂存", "action_save.gif", "暂存", "do_Save");
var ArrAction2 = new Array(true, "提交", "action_sign.gif", "提交", "do_Complete");
var ArrAction3 = new Array(true, "退回", "arrow_pleft.gif", "退回", "do_Back");
var ArrAction4 = new Array(true, "特送", "action_guiview.gif", "特送", "do_SpecialSend");
var ArrAction5 = new Array(true, "流程查阅", "actn023.gif", "流程查阅", "do_ViewFlow") ;
var ArrAction6 = new Array(true, "阅示设定", "download.gif", "阅示设定", "do_SetReview");
var ArrAction7 = new Array(true, "阅示状态", "year.gif", "阅示状态", "do_ReviewStatus");
var ArrAction8 = new Array(true, "签收", "checkin.gif", "签收", "do_Sign");
var ArrAction9 = new Array(true, "任务信息", "bbsdoc2.gif", "任务信息", "do_ViewComment");
var ArrAction1001 = new Array(true, "转送", "assign.gif", "转送", "do_forward");
var ArrAction10 = new Array(false, "版本比较", "bbsdoc2.gif", "版本比较", "do_CompareDocument");
var ArrAction11 = new Array(false, "生成合同正文", "bbsdoc2.gif", "生成合同正文", "do_CreateContractDoc");
var ArrAction12 = new Array(false, "撤销申请", "action_cancel.gif", "撤销申请", "do_CancelApply");
var ArrAction13 = new Array(false, "选择合同", "action_view.gif", "选择合同", "do_SelectContract");
var ArrAction14 = new Array(false, "打印", "print.gif", "打印", "do_Print");
var ArrAction15 = new Array(false, "查阅审批记录", "action_viewstatus.gif", "查阅审批记录", "showOpinionDlg");
var ArrAction16 = new Array(false, "填写审批意见", "action_sign.gif", "填写审批意见", "inputOpinion");
var ArrAction17 = new Array(false, "完成阅示", "action_sign.gif", "完成阅示", "do_cc_Complete");
var ArrAction18 = new Array(false, "阅示并抄送", "action_sign.gif", "阅示并抄送", "do_cc_Send");
var ArrAction19 = new Array(false, "修订合同正文", "action_view.gif", "修订合同正文", "SW_EditContractDoc");

var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5,
        ArrAction6, ArrAction7, ArrAction8, ArrAction9, ArrAction1001, ArrAction10, ArrAction11, ArrAction12,
        ArrAction13, ArrAction14, ArrAction15, ArrAction16,ArrAction17,ArrAction18,ArrAction19);
		
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
var OP_CONFIRM_MASK = 0x8000;
var OP_FINISH_MASK = 0x4000;
var CarbonCopyOpinion = "已阅";

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

        HideSinoButton(8);
    }
    SFPostSign();
    if(Error_Msg != "") {
        alert(Error_Msg);
    }
}

var isSave = false;
function do_App_Validate(){
    return true;
}

function do_Complete() {
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
        setNextFlowDesc();
        SFPostSave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
        clearDivRight();
        saveInfo();
        isSave = true;
//        if( do_InsertMark() ){
        	do_Complete_app();
//        }
    }
}

function do_cc_Complete(){
	document.getElementById("sf_opinion").value = CarbonCopyOpinion;
   	
	SFQueryComplete();
    
	if(!Launch_Continue) {
        if(Error_Msg != "")
            alert(Error_Msg);
        return;
    }
	
    var str = getNextTask();
	
    if (str != "") {
		autoValue(AV_COMPLETE_MASK);
		if (!validation()) 
			return;
		SFQuerySave();
		if (!Launch_Continue) {
			if (Error_Msg != "") 
				alert(Error_Msg);
			return;
		}
		
		document.getElementById("sf_nextTaskData").value = str;
        if (finishMessage() == "") {
			restoreNextTaskData();
			return;
		}
		setNextFlowDesc();
		SFPostSave();
		if (!Launch_Continue) {
			if (Error_Msg != "") 
				alert(Error_Msg);
			return;
		}
		
//		clearDivRight();
		saveInfo();
		isSave = true;
		document.getElementById("act").value = "CARBON_COPY";
		document.getElementById("sf_task_attribute3").value = "CC_COMPLETED";
		setFrmEnable(document.forms[0].name); 
	    document.forms[0].submit(); 
	}
}

function do_cc_Send(){
	var oldCopyOrgId = document.getElementById("copyOrgIds").value;	 
	var deptObj = new deptartmentObj();
	deptObj.optionType = 2;
	deptObj.selectType = 3;
	var deptNames = "";
	var pers = "";
	var retOrgObjs = forward(deptObj);
	
	if (null == retOrgObjs || retOrgObjs == undefined){
	}else{
	   var objCount = retOrgObjs.length;
	   var retOrgObjTemp = new departmentSelect.retDeptObj();
	   	for(var i = 0; i < objCount; i++) {
	       retOrgObjTemp = retOrgObjs[i];
		   if(retOrgObjTemp.retType == undefined || 
		      retOrgObjTemp.retType == null){
			  deptNames += retOrgObjTemp.deptName + ",";	
		   }else if(retOrgObjTemp.retType == "person"){
		   	  pers += retOrgObjTemp.loginName + "," + retOrgObjTemp.deptName + ";";
		   } 
		}    	
		
		document.getElementById("carbonCopyPers").value = pers;
		document.getElementById("carbonCopyDepts").value = deptNames;
		CarbonCopyOpinion = "已阅示,并抄送相关人员阅知!";
		do_cc_Complete();	
	}    
}

//插入水印
function do_InsertMark(){
   return true;
}
//打印
function do_Print(){
    return true;
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
    saveInfo();
    isSave = true;
    do_Save_app();
}

/*
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
*/

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
        window.event.returnValue = "确认取消所有更改?";
    }
}

function do_appInit() {
    //空函数, 应用于 jsp 中可写同一函数取代
}

function doLoad() {
//    fillData(document.getElementById("sf_fillApiData").value);
//    if(document.getElementById("sf_task_attribute3").value == "FIRST_TASK")
//        ShowSinoButton(13);
    if(document.getElementById("sf_lock").value == "1")
        alert("此任务已被其他用户打开, 数据已被锁定, 只能以只读方式打开数据!");
//    setDivVisibility();
    init_groups();
    do_SelectGroup();
    fillData(document.getElementById("sf_fillApiData").value);
//    setDivRight();
//    if(document.getElementById("sf_comment").value != "") {
        //alert(document.getElementById("sf_comment").value);
//    } else {
//        HideSinoButton(9);
//    }
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

function add_op(){
      var url = "/dz/flow/addOpinion.jsp";
    var style = "dialogleft:200px;dialogtop:200px;dialogwidth:300px;dialogheight:180px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
    var approveContent = window.showModalDialog(url, null, style);
        if(approveContent==''||approveContent==null){
           approveContent="不同意"; 
        }
    if (approveContent) {
        document.getElementById('sf_opinion').value = approveContent;
         return true;
    } else {
        return false;
    }
}
function do_Back() {
    add_op();
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
        SFQuerySave();
        if(!Launch_Continue) {
            if(Error_Msg != "")
                alert(Error_Msg);
            return;
        }
//        clearDivRight();
        saveInfo();
        isSave = true;
        do_Back_app();
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
            document.getElementById("sf_end").value = "1";
	     
//            if (Launch_Code != "CANCEL_APPLY") {
		   if (deliveryStatusEndDlg() == "") return "";
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
    document.getElementById("sf_comment").value = comment;
    document.getElementById("sf_priority").value = priority;
    return "ok";
}

function isFlowEnd() {
    var prop;
    eval ("prop = " + document.getElementById("sf_nextTaskData").value);
    if(!prop.length) {
        if(prop.taskName == "STOP")
            return true;
        else
            return false;
    } else if(prop.length == 1) {
        if(prop[0].taskName == "STOP")
            return true;
        else {
        	var tempStr = getTaskName(prop[0]);
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
    }
    var taskName;
    for(var i = 0; i < prop.length; i++) {
        taskName = getTaskName(prop[i]);
        if(taskName != "STOP")
            return false;
    }
    return true;
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

/**
 * 功能：撤销申请执行该操作后，流程单据状态为已撤销
 */
function do_CancelApply(){
    alert("撤销申请执行该操作后，流程单据状态为已撤销");
    alert("你之所以看到该提示，是因为你还没有覆盖该方法");
}

function do_forward() {
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
    taskUsers = "[{taskName:'" + document.getElementById("sf_taskname").value +
            "', groupName:'" + document.getElementById("sf_group").value + "', roleName:'" +
            document.getElementById("sf_role").value + "', realnames:'" + realnames + "', usernames:'" +
            usernames + "', flowProp:'0'}]";
    var users = selectParticipantDlg(taskUsers, "", "0");
    if(users == "")
        return "";

    var prop;
    eval("prop = " + users);

    document.getElementById("sf_nextTaskData").value = "[{taskName:'" + document.getElementById("sf_taskname").value +
            "', groupName:'" + document.getElementById("sf_group").value + "', roleName:'" +
            document.getElementById("sf_role").value + "', realnames:'" + prop[0].realnames + "', usernames:'" +
            prop[0].usernames + "', flowProp:'0', flowHint:'" + document.getElementById("sf_flowDesc").value +
            "', flowDesc:'" + document.getElementById("sf_flowDesc").value + "'}]";
    if(finishMessage() == "") {
        restoreNextTaskData();
        return;
    }
    setNextFlowDesc();

    isSave = true;
    eval("task = " + users);
    setFrmEnable(document.forms[0].name);
    document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.CaseForward?toUser='"
            + task[0].usernames + "'";
    document.forms[0].submit();
}

function inputOpinion(message){
    if(!isReviewUser()) {
        var styleStr = "dialogWidth:500px;dialogHeight:400px;scroll:no;center:yes;status:no;";
        return window.showModalDialog(
            "/flow/inputApproveContent.jsp?flowDesc='" + message + "'",null,styleStr);
    }
}

function do_SpecialSend_app(toTask) {
//        document.forms[0].action = "/servlet/com.sino.sinoflow.servlet.ToTask";
//        document.forms[0].action = "/servlet/test.servlet.AppToTask";
    setFrmEnable(document.forms[0].name);
        document.forms[0].action = "/servlet/test.servlet.AppCaseComplete";
        document.forms[0].submit();
}

function deliveryStatusAndMessageDlg(message) {
    var url;
    url = "/flow/deliveryStatus.jsp?flowDesc='" + message + "'";
    var popscript;
    popscript = "dialogWidth:425px"
            + ";dialogHeight:330px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";
    var largeText = new Array();
    largeText[0] = document.getElementById("sf_nextTaskData").value;
    largeText[1] = "{project:'" + sf_project + "',group:" + document.getElementById("sf_group").value
        + "',role:'" + document.getElementById("sf_role").value + "'}";
    return window.showModalDialog(url, largeText, popscript);
}

function multiGroupSelected() {
    var url;
    var groups = "省公司.+;省飞达公司.+";
    var fstr = searchFlowStr(Launch_FlowStr, "username");
    var flowProp;
    eval("flowProp = " + fstr);
    var role = flowProp.roleName;
    url = "/servlet/com.sino.sinoflow.servlet.GetMultiGroups?project='"
            + sf_project + "'&groups='" + groups.replaceAll("+","%2B")
            + "'&role='" + role + "'&multiSelected=" + 1;
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:300px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}