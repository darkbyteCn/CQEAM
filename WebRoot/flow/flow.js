//var submitMethod;
//主函数，页面调用的主函数
var mouse_x;
var mouse_y;
var submitHander;
var nextHiddenRight;
var flow_appId;
var flow_extendType;
var flow_attr1;
var flow_attr2;
var flow_attr3;
var flow_ou;
/**
 * paramObj 的属性说明：
 * orgId: ou暂时未启用
 * userId:申请人ID，暂时未启用
 * groupId:组别，如果流程定义中已经指定组别，可以不提供，否则需要提供
 * procdureName：流程名称，第一次提交必填，会根据流程名查找流程ID
 * appId：应用ID 扩展用，非必填
 * extendType：扩展类型，扩展用，非必填
 * attr1:扩展字段1 非必填
 * attr2:扩展字段2 非必填
 * attr3:扩展字段3 非必填\
 * flowCode:流程流向控制码
 * submitH:提交方法
 */
function assign(paramObj) {
    //orgId, userId, deptId, procdureName, flowCode, submitH
    var orgId = paramObj.orgId?paramObj.orgId:'';
    var userId = paramObj.userId?paramObj.userId:'';
    var deptId = paramObj.groupId?paramObj.groupId:'';
    var procdureName = paramObj.procdureName?paramObj.procdureName:'';
    var flowCode = paramObj.flowCode?paramObj.flowCode:'';
    var submitH = paramObj.submitH?paramObj.submitH:'';
    flow_appId = paramObj.appId?paramObj.appId:'';
    flow_extendType = paramObj.extendType?paramObj.extendType:'';
    flow_attr1 = paramObj.attr1?paramObj.attr1:'';
    flow_attr2 = paramObj.attr2?paramObj.attr2:'';
    flow_attr3 = paramObj.attr3?paramObj.attr3:'';
    flow_ou = orgId;
    disableDocument();
    showWaitDiv();
    if (event) {//有可能是没有这个值
        mouse_x = document.body.scrollLeft + event.clientX;
        mouse_y = document.body.scrollTop + event.clientY;
    } else {
        mouse_x = document.body.scrollLeft + 50;
        mouse_y = document.body.scrollTop + 50;
    }
    submitHander = submitH;
    document.getElementById('flowcurrDeptId').value = deptId;
    document.getElementById("flowProcName").value=procdureName;
    try {
        var currTaskId = document.getElementById("currTaskId").value;
        var procId = document.getElementById("flowprocId").value;
        var url = "/servlet/com.sino.flow.servlet.TaskFindServlet?currTaskId=" + currTaskId + "&procId=" + procId;
        var actId = document.getElementById("actId").value;
        var param = new ParamObject(orgId, deptId, procdureName, actId, flowCode);
        xmlHttp = getXmlHttpFlow(getNextTaskInfo);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.send(escape(param.toJSONString()));
    } catch(e) {
        alert("提交数据出错！请通知系统管理员。" + e.message);
        hiddenWaitDiv();
        cancelDisable();
    }
}
/**
 * orgId OU
 * procdureName ,流程名
 */
function ParamObject(orgId, deptId, procdureName, actId, flowCode) {
    this.orgId = orgId;
    this.procdureName = procdureName;
    this.actId = actId;
    this.flowCode = flowCode;
    this.deptId = deptId;
}
//从服务器取下一节点信息，如果多于一个节点，弹出
function getNextTaskInfo() {
    try {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var resText = xmlHttp.responseText;
            if (resText == 'ERROR') {
                alert("提交数据失败，请重新尝试提交或通知系统管理员!");
                hiddenWaitDiv();
                cancelDisable();
                return;
            }
            var resArray = resText.parseJSON();
            if (resArray.length < 1) { //如果没有找到下一个节点
                alert("没有找到下一任务流向，请通知系统管理员。");
                hiddenWaitDiv();
                cancelDisable();
                return;
            }
            if (resArray.length == 1) { //如果下一个流向只有一个节点 ,不弹出窗口，继续取下一用户
                var taskObj = resArray[0];
                document.getElementById("flownextTaskId").value = taskObj.nextTaskId;
                document.getElementById("flownextDeptId").value = taskObj.nextDeptId;
                document.getElementById("flowprocId").value = taskObj.procId;
                nextHiddenRight = taskObj.nextHiddenRight;
                var nextTaskName = taskObj.nextTaskName;
                if (nextTaskName == '结束') {
                    eval(submitHander);
                } else {
                    fetchNextUsers(taskObj.nextTaskId, taskObj.nextDeptId, taskObj.procId);
                }
            } else {
                //step2:如果下一个节点多于两个，显示一个下拉框，让其选择
                //隐藏等待页面，显示，下一节点页面 ,如果只有一个就不用了
                hiddenWaitDiv();
                showNextTasksDiv();
                var taskSelect = document.getElementById("flowNextTasksSelect");
                removeOption(taskSelect);
                if (resArray.length >= 1) {
                    for (var i = 0; i < resArray.length; i++) {
                        var taskObj = resArray[i];
                        var vValue = taskObj.nextTaskId + "&&" + taskObj.nextDeptId + "&&" + taskObj.procId + "&&" + taskObj.nextTaskName + "&&" + taskObj.nextHiddenRight;
                        var vText = taskObj.flowDesc;
                        var vOption = new Option(vText, vValue);
                        taskSelect.add(vOption);
                    }
                }
            }
        }
    } catch(e) {
        alert("提交数据出错！请通知系统管理员。" + e.message);
        hiddenWaitDiv();
        cancelDisable();
    }
}
/**
 刷新收件箱
 */
function refreshInbox(userId) {
    if (document.getElementById('actId').value != '') {
        window.opener.document.forms[0].userId.value = userId;
        window.opener.document.forms[0].submit();
    }
}
/**
 查看审批意见
 appId,应用ID
 appTableName,应用表名
 这两个参数确定唯一性，如果还在流程的流转中，即能保存页面有actId,可以不传这两个参数
 */

function viewOpinion(appId, appTableName) {
    var url = "/servlet/com.sino.flow.servlet.ApproveContentServlet";
    if (appId) {
        url += "?appId=" + appId + "&appTableName=" + appTableName;
    } else {
        if (document.getElementById('actId')) {
            var actId = document.getElementById('actId').value;
            url += "?actId=" + actId;
        } else {
            alert("在页面中找不到actId隐藏域，请传参数查阅审批意见！");
            return;
        }
    }
    var factor = 0.8;
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
    window.showModalDialog(url, null, style);
}


//function viewOpinion(appId, appName) {
//    var url = "/servlet/com.sino.sinoflow.servlet.GetSaveStatus";
//    if(document.getElementById("sf_caseID")) {
//        var caseId = document.getElementById('sf_caseID').value;
//        url += "?sf_caseID=" + caseId;
//    } else if (appId) {
////        url += "?sf_appDataID=" + appId + "&sf_appName=" + appName;
//        url += "?sf_appDataID=" + appId + "&sf_appName=";
//    } else {
//        alert("在页面中找不到sf_caseID隐藏域，请传参数查阅审批意见！");
//    }
//    var factor = 0.8;
//    var dialogWidth = window.screen.availWidth * factor;
//    var dialogHeight = window.screen.availHeight * factor;
//    var dialogLeft = window.screen.availWidth * (1 - factor) / 2;
//    var dialogTop = window.screen.availHeight * (1 - factor) / 2;
//
//    var style = "dialogLeft:"+dialogLeft+"px;" +
//            "dialogTop:"+dialogTop+"px;" +
//            "dialogWidth:"+dialogWidth+"px;" +
//            "dialogHeight:"+dialogHeight+"px;" +
//            "toolbar:no;" +
//            "directories:no;" +
//            "status:no;" +
//            "menubar:no;" +
//            "scrollbars:no;" +
//            "revisable:no;" +
//            "resizable:yes";
//    window.showModalDialog(url, null, style);
//}

/**
 不通过时，添加审批意见
 */
function addApproveContent(flowCode) {
    var url = "/flow/addOpinion.jsp";
    var style = "dialogleft:200px;dialogtop:200px;dialogwidth:300px;dialogheight:180px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
	var alreadyContent = document.getElementById('approveContent').value;
	if(flowCode){
		var lastFlowCode = document.getElementById('lastFlowCode').value;
		if(flowCode == "9"){//通过
			if(lastFlowCode != "9" || alreadyContent == ""){
				alreadyContent = "同意";
			}
		} else {
			if(lastFlowCode == "9" || alreadyContent == ""){
				alreadyContent = "不同意";
			}
		}
		document.getElementById('lastFlowCode').value = flowCode;
	}
    var approveContent = window.showModalDialog(url, alreadyContent, style);
    if (approveContent) {
        document.getElementById('approveContent').value = approveContent;
    }
}
//将整个页面Disabled
//用来存放原始属性
function disableDocument() {
    //disable整个页面
    var hideDiv = document.getElementById("flowhideDiv");
    //hideDiv.style.filter = "progid:DXImageTransform.Microsoft.Alpha (opacity=10,finishOpacity=100,style=2)";
    hideDiv.style.visibility = 'visible';
}
//取消Disable
function cancelDisable() {
    var hideDiv = document.getElementById("flowhideDiv");
    hideDiv.style.visibility = 'hidden';
}
function showWaitDiv() {
    //弹出等待DIV
    var winHeight = document.body.clientHeight;
    // alert(winHeight);
    var waitDiv = document.getElementById("flowWaitDiv");
    waitDiv.style.top = winHeight / 2
    waitDiv.style.visibility = 'visible';
}
//隐藏等待页面
function hiddenWaitDiv() {
    var waitDiv = document.getElementById("flowWaitDiv");
    waitDiv.style.visibility = 'hidden';
}
function getXmlHttpFlow(handler) {
    var objXmlHttp = null
    if (navigator.userAgent.indexOf("Opera") >= 0) {
        alert("对不起！你的浏览器不支持该操作！")
        return;
    }
    if (navigator.userAgent.indexOf("MSIE") >= 0) {
        var strName = "Msxml2.XMLHTTP"
        if (navigator.appVersion.indexOf("MSIE 5.5") >= 0) {
            strName = "Microsoft.XMLHTTP"
        }
        try {
            objXmlHttp = new ActiveXObject(strName)
            objXmlHttp.onreadystatechange = handler
            return objXmlHttp
        } catch(e) {
            alert("Error. Scripting for ActiveX might be disabled")
            return
        }
    }
    if (navigator.userAgent.indexOf("Mozilla") >= 0) {
        objXmlHttp = new XMLHttpRequest();
        objXmlHttp.onload = handler
        objXmlHttp.onerror = handler
        return objXmlHttp
    }
}
//显示下一流向的DIV
function showNextTasksDiv() {
    var nextTaskDiv = document.getElementById("flowNextTaskDiv");
    nextTaskDiv.style.position = 'absolute';
    nextTaskDiv.style.left = mouse_x;
    nextTaskDiv.style.top = mouse_y;
    nextTaskDiv.style.visibility = 'visible';
}
function hideNextTasksDiv() {
    var nextTaskDiv = document.getElementById("flowNextTaskDiv");
    nextTaskDiv.style.visibility = 'hidden';
}
function removeOption(vselect) {
    if (vselect.length > 1) {
        //因为remove一个掉了，length就会减短，所以应该反过来remove
        for (var i = vselect.length - 1; i > 0; i--) {
            vselect.remove(i);
        }
    }
}
//将nextTasks返回到页面。
function getFlowTasks() {
    //step1:将必要的信息赋给页面的表单域
    var nextTaskSelect = document.getElementById("flowNextTasksSelect");
    var nextTaskValue = nextTaskSelect.options[nextTaskSelect.selectedIndex].value;
    if (nextTaskValue == "") {
        alert("请先选择下一任务流向，然后点击确定！");
        return;
    }
    var nextTaskArr = nextTaskValue.split("&&");
    var nextTaskId = nextTaskArr[0];
    var nextDeptId = nextTaskArr[1];
    var procId = nextTaskArr[2];
    var nextTaskName = nextTaskArr[3];
    nextHiddenRight = nextTaskArr[4];
    document.getElementById("flownextTaskId").value = nextTaskId;
    document.getElementById("flownextDeptId").value = nextDeptId;
    document.getElementById("flowprocId").value = procId;
    //显示等待页面，
    hideNextTasksDiv();
    disableDocument();
    showWaitDiv();
    if (nextTaskName == '结束') {
        eval(submitHander);
    } else {
        //step2:根据下一节点，取办理人
        fetchNextUsers(nextTaskId, nextDeptId, procId);
    }
}
//去服务器取下一办理人
function fetchNextUsers(nextTaskId, nextDeptId, procId) {
    try {
        var url = "/servlet/com.sino.flow.servlet.ApproveUserFindServlet" ;
        var nextTask = new Object();
        nextTask.nextTaskId = nextTaskId;
        nextTask.nextDeptId = nextDeptId;
        nextTask.procId = procId;
        nextTask.appId = flow_appId;
        nextTask.extendType = flow_extendType;
        nextTask.attr1 = flow_attr1;
        nextTask.attr2 = flow_attr2;
        nextTask.attr3 = flow_attr3;
        nextTask.ou = flow_ou;
        xmlHttp = getXmlHttpFlow(getNextUsers);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.send(nextTask.toJSONString());
    } catch(e) {
        alert("提交数据失败，请尝试重新提交或通知系统管理员！" + e.message);
        hiddenWaitDiv();
        cancelDisable();
    }
}
//服务器返回下一办理人
function getNextUsers() {
    try {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var resText = xmlHttp.responseText;
            if (resText == 'ERROR') {
                alert("提交数据失败，请重新尝试提交或通知系统管理员!");
                hiddenWaitDiv();
                cancelDisable();
                return;
            }
            var resArray = resText.parseJSON();
            if (resArray.length < 1) {
                alert("没有找到下一任务办理人，请通知系统管理员。");
                hiddenWaitDiv();
                cancelDisable();
                return;
            }
            if (resArray.length == 1) {//如果长度为1，直接提交审批
                var userObj = resArray[0];
                document.getElementById("flownextUserId").value = userObj.nextUserId;
                document.getElementById("flownextPositionId").value = userObj.nextPositionId;
                eval(submitHander);
            } else {
                //查看下一节点是否为多用户办理，如果是多用户办理，也不用弹出页面。
                var hiddenArr = nextHiddenRight.split(";");
                //
                if (hiddenArr[0] == 'both') {//定时
                    //下一些办理人用分号分隔；
                    var nextUserIds;
                    //下一些办理人职位用分号分隔
                    var nextPositionIds;

                    for (var i = 0; i < resArray.length; i++) {
                        var userObj = resArray[i];
                        if (i == 0) {
                            nextUserIds = userObj.nextUserId;
                            nextPositionIds = userObj.nextPositionId;
                        } else {
                            nextUserIds = nextUserIds + ";" + userObj.nextUserId;
                            nextPositionIds = nextPositionIds + ";" + userObj.nextPositionId;
                        }
                    }
                    document.getElementById("flownextUserId").value = nextUserIds;
                    document.getElementById("flownextPositionId").value = nextPositionIds;
                    //提交页面。
                    eval(submitHander);
                } else {
                    /*下个节点有多个人，但应用也有可能像指定某个流向一样指定了人。如果指定到了人就不用弹
                        出下拉框，直接提交.
                        具体指定到谁，由应用去实现这个接口appointPerson();
                        该方法返回指定的userId
                        eg:
                        function appointPerson(){
                            return userId;
                        }
                    */
                    var isAppointPerson = false;
                    if (typeof(appointPerson) == "function") {
                        //指定到人的userId;
                        var appointPersonUserId = appointPerson();
                        if (appointPersonUserId != "") {
                            for (var i = 0; i < resArray.length; i++) {
                                var userObj = resArray[i];
                                if (userObj.nextUserId == appointPersonUserId) {
                                    document.getElementById("flownextUserId").value = userObj.nextUserId;
                                    document.getElementById("flownextPositionId").value = userObj.nextPositionId;
                                    isAppointPerson = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (isAppointPerson) {//如果有指定的人提交页面。
                        eval(submitHander);
                    } else {
                        //step2:如果下一个节点多于两个，显示一个下拉框，让其选择
                        //隐藏等待页面，显示，下一节点页面 ,如果只有一个就不用了
                        hiddenWaitDiv();
                        showNextUserDiv();
                        var userSelect = document.getElementById("flowNextUsersSelect");
                        removeOption(userSelect);
                        if (resArray.length >= 1) {
                            for (var i = 0; i < resArray.length; i++) {
                                var userObj = resArray[i];
                                var vValue = userObj.nextUserId + "&&" + userObj.nextPositionId;
                                var vText = userObj.nextUserName;
                                var vOption = new Option(vText, vValue);
                                userSelect.add(vOption);
                            }
                        }
                    }
                }
            }
        }
    } catch(e) {
        alert("提交数据出错！请通知系统管理员。" + e.message);
        hiddenWaitDiv();
        cancelDisable();
    }
}
function showNextUserDiv() {
    var nextTaskDiv = document.getElementById("flowNextUserDiv");
    nextTaskDiv.style.position = 'absolute';
    nextTaskDiv.style.left = mouse_x;
    nextTaskDiv.style.top = mouse_y;
    nextTaskDiv.style.visibility = 'visible';
}
function hideNextUserDiv() {
    var nextTaskDiv = document.getElementById("flowNextUserDiv");
    nextTaskDiv.style.visibility = 'hidden';
}
function getFlowUsers() {
    //step1:将必要的信息赋给页面的表单域
    var nextUserSelect = document.getElementById("flowNextUsersSelect");
    var nextUserValue = nextUserSelect.options[nextUserSelect.selectedIndex].value;
    if (nextUserValue == "") {
        alert("请先选择下一办理人，然后点击确定！");
        return;
    }
    var arr = nextUserValue.split("&&");
    document.getElementById("flownextUserId").value = arr[0];
    document.getElementById("flownextPositionId").value = arr[1];
    //显示等待页面，
    hideNextUserDiv();
    disableDocument();
    showWaitDiv();
    //window.event = submitHander;
    eval(submitHander);
}
//签收申请，当当前用户多于一个时。
var flowopenMethod = ""//签收完后打开收件箱的方法
function signApply() {
    try {
        var actId = document.getElementById("actId").value;
        var signFlag = document.getElementById("flowSignFlag").value;
        if (signFlag == 'Y') {
            return;
            //如果已经签收，就不用签收了
        }
        disableDocument();
        showWaitDiv();
        var url = '/servlet/com.sino.flow.servlet.SignApplyServlet?actId=' + actId ;
        xmlHttp = getXmlHttpFlow(receiveFromSignApply);
        xmlHttp.open('GET', url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.send(null);
    } catch(e) {
        alert("签收失败！");
        hiddenWaitDiv();
        cancelDisable();
    }
}
//signApply（）方法发出请求，该方法接收服务器端返回的响应
function receiveFromSignApply() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var resText = xmlHttp.responseText;
        if (resText == '1') {//签收成功
            document.getElementById("flowSignFlag").value = 'Y';
            alert("签收成功！");
        } else if (resText == '0') {//有人已经签收
            alert("已经有人签收了该申请！请重新刷新收件箱！");
        } else if (resText == 'ERROR') {//服务器抛出异常
            alert("系统出错，请通知系统管理员！");
        }
        //刷新收件箱
        if (window.opener && window.opener.document && window.opener.document.forms[0]) {
            window.opener.document.forms[0].submit();
        }
        hiddenWaitDiv();
        cancelDisable();
    }
}
//查阅流程
function viewFlow() {
    var procName=document.getElementById("procdureName").value;
    var actId = document.getElementById("actId").value;
    if ((actId =="")&&(procName=="")) {
        alert("没有找到流程的信息");
        return;
    }
//    var url = "/servlet/ViewFlowServlet?actId=" + actId;
    var url = "/servlet/com.sino.flow.servlet.ViewFlow?actId=" + actId+"&procName="+procName;
    var style = "width=1024,height=768,top=0,left=0";
    var winName = "flowDetail";
    window.open(url, winName, style);
}
//流程撤消或流程退回前调用，此时，不需要去后台找下一办理人，只需要把当前部门ID赋值。Didsabled整个页面，弹出等等页面
function assignDeptId(deptId) {
    try {
        disableDocument();
        showWaitDiv();
        document.getElementById('flowcurrDeptId').value = deptId;
    } catch(e) {
        alert("提交出错！");
        hiddenWaitDiv();
        cancelDisable();
    }
}


