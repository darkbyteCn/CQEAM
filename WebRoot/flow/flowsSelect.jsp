<%@ page import="com.sino.sinoflow.util" %>
<%@ page import="com.sino.sinoflow.utilities.SqlSearch" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>
<HEAD>
<script language="JavaScript" src="/WebLibary/js/util.js">
</script>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>    
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<%

    String trayType = request.getParameter("sf_trayType");
    String handlerStatus = request.getParameter("sf_handlerStatus");
    String handler = util.getJsonData(handlerStatus, "sf_handler:");
    String handlerGroup = util.getJsonData(handlerStatus, "sf_handlerGroup:");
    String plusGroup = util.getJsonData(handlerStatus, "sf_plusGroup");
    String jsonFlowStr = request.getParameter("flowsStr");
    if(jsonFlowStr.charAt(0) == '\'' || jsonFlowStr.charAt(0) == '\"') {
        jsonFlowStr = jsonFlowStr.substring(1, jsonFlowStr.length() - 1);
    }

    List flowList;
    if(jsonFlowStr.charAt(0) == '[')
        flowList = util.getFlowStr(jsonFlowStr.substring(1, jsonFlowStr.length() - 1));
    else
        flowList = util.getFlowStr(jsonFlowStr);
    String waitType;
    try {
        waitType = SqlSearch.getJoinWait(Integer.parseInt(util.getJsonData((String)flowList.get(0), "taskID:")));
    } catch(Exception e) {
        waitType = "1";
    }
    boolean parallelFlow = util.getJsonData((String)flowList.get(0), "taskFlowType:").equals("2");

    String project = request.getParameter("sf_project");
    if(project.startsWith("\"") || project.startsWith("'")) {
        project = project.substring(1,project.length()-1);
    }
    if(parallelFlow) {
%>
<TITLE>选择并流</TITLE>
<%
    } else {
%>
<TITLE>选择辅流</TITLE>
<%
    }
%>
<input type="hidden" name="sf_handler" value="<%=handler%>" readonly="readonly" />
<input type="hidden" name="sf_handlerGroup" value="<%=handlerGroup%>" readonly="readonly" />
<input type="hidden" name="sf_plusGroup" value="<%=plusGroup%>" readonly="readonly" />
<input type="hidden" name="sf_project" value="<%=project%>" readonly="readonly" />
<input type="hidden" name="sf_actID" value="" readonly="readonly" />
<STYLE TYPE=text/css>
A:link {color : black; text-decoration : none; font-size: 9pt}
A:visited {color : black; text-decoration : none; font-size: 9pt}
A:active {color : black; text-decoration : none; font-size: 9pt}
A:hover {color : black; text-decoration : none; font-size: 9pt}
TD,TH {color : black; text-decoration : none; font-size: 9pt}
INPUT {color : black; text-decoration : none; font-size: 9pt}
SELECT {color : black; text-decoration : none; font-size: 9pt}
TEXTAREA {color : black; text-decoration : none; font-size: 9pt}
</STYLE>
</HEAD>
<BODY topmargin=0 rightMargin=0 leftmargin=0 bottommargin=0 scroll=no bgcolor="white" onLoad="init()" onResize="window_onresize()">
<form name="listform" action="/servlet/com.sino.sinoflow.servlet.InTray" method="POST">
<script language="JavaScript" src="/WebLibary/js/dynlayer.js">
</script>
<STYLE TYPE="text/css">
<!--
#menuDiv {position:relative; left:0; top:document.body.scrollHeight;  width:document.body.clientWidth; height:document.body.clientHeight-document.body.scrollHeight; 
				clip:rect(0,3000,3000,0); layer-background-color:white; background-color:white; overflow-x:auto;overflow-y:hidden;}
-->
</STYLE>
<SCRIPT LANGUAGE="JavaScript">
<!--
window.returnValue = "";
setTimeout("initTimer()", 3000)
var resizeTimerID = 0;
var iReadWindow = false;
var iHRatio = 0;
var iHRatio2 = 0;
var iListH = 0;
var iListReadH = 0;
var iMenuH = document.body.scrollHeight;
var iInit = false;
function initTimer() {
	iReadWindow = (eval(parent) && eval(parent.document) && eval(parent.document.body) && eval(top.topframe));
	if (iReadWindow) {
		iListH = document.body.clientHeight;
		iListReadH = parent.document.body.clientHeight;
		iHRatio = (iListH)*100/(iListReadH);
	}
}

function movemenulayer() {
	if (!iInit)
		init();
	menulayer.setWidth(document.body.clientWidth)
	menulayer.setHeight(document.body.clientHeight-iMenuH)
}
function window_onresize() {
	movemenulayer()
}
//-->
</SCRIPT>
<table height=20 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF
border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>
<tr align="top">
<td width="7%" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"></td>
</tr>
</table>
<table height=20 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF
border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>
<tr align="top">
<td width="3%" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"></td>
<td width="7%" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT">  结束选项:</td>
<td width="15%" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT">
<select name="waitType" style="width:150px">
<%
    if(waitType.equals("0")) {
%>
<option value="0" selected="selected">等待</option>
<option value="1">不等待</option>
<%
    } else {
%>
<option value="0">等待</option>
<option value="1" selected="selected">不等待</option>
<%
    }
%>
</select>
<td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="right">
<input type="button" name="Submit" value="确定" onClick="do_close()">
<input bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap type="button" name="Cancel" value="取消" onClick="do_cancel()">
</td>
<td width="3%" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"></td>
</tr>
</table>
<table height=20 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF
border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>
<tr align="top">
<td width="7%" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"></td>
</tr>
</table>
<style type="text/css">
table th {white-space:nowrap;}
table td {white-space:nowrap;}
</style>
<DIV id=menuDiv>
<table height=20 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF 
border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer;position:relative;'>
<THEAD>
<tr>
<th nowrap width=3%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 设置</td></tr>
</table>
<!--th nowrap width=3%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> &nbsp;</td></tr>
</table>
</th-->
<th nowrap width=7%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 类型</td></tr>
</table>
</th>
<th nowrap width=15%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 任务</td></tr>
</table>
</th>
<th nowrap width=15%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 组别</td></tr>
</table>
</th>
<th nowrap width=15%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 角色</td></tr>
</table>
</th>
<th nowrap width=30%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 参与者</td></tr>
</table>
</th>
</tr>
</THEAD>
<%
    for (int i = 0; i < flowList.size(); i++) {
        String flow = (String)flowList.get(i);
%>
<script type="text/javascript">
    flowStrArray[<%=i%>] = "<%=flow%>";
    assignedStrArray[<%=i%>] = "<%=flow%>";
</script>
<%
        boolean multiFlow = false;
        if(flow.charAt(0) == '[') {
            multiFlow = true;
        }
        String taskName = util.getJsonData(flow, "taskName:");
        String taskFlowType = util.getJsonData(flow, "taskFlowType:");
        String flowProp = util.getJsonData(flow, "flowProp:");
        if(taskName.equals("SPLIT") && !multiFlow) {
            // split 或 子流
            continue;
        }
        if(multiFlow) {
            if(taskFlowType.equals("4")) {
                List newFlowList = util.getFlowStr(flow.substring(1, flow.length() - 1));
                if(newFlowList.size() > 1) {
                    flow = (String)newFlowList.get(1);
                    taskName = util.getJsonData(flow, "taskName:");
//                    taskFlowType = util.getJsonData(flow, "taskFlowType:");
                }
            }
        }
 //       String prevTaskId = util.getJsonData(flow, "taskID:");
        String taskId = util.getJsonData(flow, "taskID:");
        String groupName = util.getJsonData(flow, "groupName:");
        String roleName = util.getJsonData(flow, "roleName:");
        String realNames = util.getJsonData(flow, "realnames:");
//        String userNames = util.getJsonData(flow, "usernames:");
%>
<tr height=18 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>
<%
        String maskGroup = util.getMaskGroup(groupName, handlerGroup, plusGroup);
        String flowType = util.getJsonData(flow, "flowType:");
        if(((flowType.equals("0") || (flowType.equals("3") && !handler.equals(""))) &&
            (parallelFlow || ((!parallelFlow) && flowProp.equals("0"))) &&
            (util.needGroupSelected(maskGroup) == 0)) && (!realNames.equals("")) &&
            (trayType.equals("0") || (trayType.equals("1") && realNames.indexOf(",") < 0))) {
%>
<td><input type=checkbox name="msgid" STYLE='cursor:pointer' value="<%=taskId%>" disabled="disabled" checked="checked"></td>
<%
        } else {
%>
<td><input type=checkbox name="msgid" STYLE='cursor:pointer' value="<%=taskId%>" disabled="disabled"></td>
<%
        }
%>
<%
        if(parallelFlow) {
%>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap>并流</td></tr></table></font></td>
<%
        } else {
            if(flowProp.equals("0")) {
%>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap>主流</td></tr></table></font></td>
<%
            } else {
%>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap>辅流</td></tr></table></font></td>
<%
            }
        }
%>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap><td nowrap  onclick="checkFlowProp('<%=i%>')"><%=taskName%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap><td id="sendgroups" nowrap  onclick="checkFlowProp('<%=i%>')"><%=groupName%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap><td nowrap  onclick="checkFlowProp('<%=i%>')"><%=roleName%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap><td id="sendusers" nowrap  onclick="checkFlowProp('<%=i%>')"><%=realNames%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap></td>
<%
    }
%>
<tr height=18 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>
<td></td>
</tr>
</table>
</DIV>
<script language=javascript>
var outcount=2;

function init() {
    sf_handler_group = document.getElementById("sf_handlerGroup").value;
    sf_plus_group = document.getElementById("sf_plusGroup").value;
    sf_handler = document.getElementById("sf_handler").value;
    sf_project = document.getElementById("sf_project").value;

menulayer = new DynLayer("menuDiv")
menulayer.slideInit();
iInit = true;
movemenulayer();

eval("flowArray = " + "<%=jsonFlowStr%>");
eval("assignedFlowArray = " + "<%=jsonFlowStr%>");
clearNames(assignedFlowArray);

}

function GetSelectedKey()
{
	var listform=document.listform;
	var arrRet=new Array();
        var msgids=listform.all("msgid");

	var n=0;
	if(msgids ==null)
		return null;
	if(!eval(msgids.length))
	{
		if(msgids.checked){
			arrRet[n] = msgids.value;
			return arrRet;
			//return strRet=msgids.value;
		}else{
			return null;
		}
	}
	for(var i=0; i<msgids.length; i++)
	{
      		if((msgids[i] != null)&&(msgids[i].name=="msgid"))
      		{
      			if(msgids[i].checked)
      			{
      				arrRet[n]=msgids[i].value;
      				n=n+1;
      			}	
      		}

	}
	return arrRet;
}

function isTask(taskStr, taskid) {
    var taskArray;
    eval("taskArray = " + taskStr);
    for(var i = 0; i < taskArray.length; i++) {
        if(taskArray[i].length) {
            if(isTask(constructFlowStr(taskArray[i]), taskid))
                return true;
        } else {
            if(taskArray[i].taskID == taskid)
                return true;
        }
    }
    return false;
}

function checkTask(taskid) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    if(msgids.length) {
        for(var i = 0; i < msgids.length; i++) {
            if(msgids[i].value == taskid) {
                msgids[i].checked = true;
                break;
            }
        }
    } else {
        msgids.checked = true;
    }
}

function uncheckTask(taskid) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    if(msgids.length) {
        for(var i = 0; i < msgids.length; i++) {
            if(msgids[i].value == taskid) {
                msgids[i].checked = false;
                break;
            }
        }
    } else {
        msgids.checked = false;
    }
}

function setGroup(taskid, groups) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    var sendGroups=listform.all("sendgroups");
    if(msgids.length) {
        for(var i = 0; i < msgids.length; i++) {
            if(msgids[i].value == taskid) {
                sendGroups[i].innerHTML=groups;
                break;
            }
        }
    } else {
        sendGroups.innerHTML=groups;
    }
}

function setParticipants(taskid, users) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    var sendUsers=listform.all("sendusers");
    if(msgids.length) {
        for(var i = 0; i < msgids.length; i++) {
            if(msgids[i].value == taskid) {
                sendUsers[i].innerHTML=users;
                break;
            }
        }
    } else {
        sendUsers.innerHTML=users;
    }
}

function checkFlowProp(index) {
    var flowProp = getProcessTask(flowArray[index]);
    if(flowProp.taskName == "SPLIT") {
        var flowStr = flowStrArray[index];
        var flows = flowsSelectDlg(flowStr);
        if(flows != "") {
            assignedStrArray[index] = flows;
            checkTask(flowProp.taskID);
        }
        return;
    }
    var selectedProp = getProcessTask(assignedFlowArray[index]);
    var oldStr = constructFlow(selectedProp);
    if(selectedProp.realnames == "") {
        selectedStr = "";
    } else {
        selectedStr = "[{realnames:'" + selectedProp.realnames + "', ";
        selectedStr += "usernames:'" + selectedProp.usernames + "'}]";
    }
    var participants;
    var names;
    var str, selectedStr;
    var taskGroup = flowProp.groupName;
    if(needGroupSelected(flowProp.groupName)) {
//        flowProp.groupName = getMaskGroup(flowProp.groupName);
        var groupMask = checkGroupMask(sf_project, flowProp.groupName, flowProp.roleName);
        if(groupMask == "")
            return;
        if(flowProp.groupName.indexOf("+") >= 0) {
            setPlusGroup = true;
            plusGroup = groupMask;
        } else if(flowProp.groupName.indexOf("*") >= 0) {
            setHandlerGroup = true;
            handlerGroup = groupMask;
        }
        flowProp.groupName = groupMask;
        selectedProp.groupName = groupMask;
        setGroup(flowProp.taskID, flowProp.groupName);

//        participants = getGroupsUsersNames(sf_project, flowProp.groupName, flowProp.roleName);
        participants = getGroupsUsersNames(sf_project, groupMask, flowProp.roleName);
        if(participants == "")
            return;
        selectedStr = participants;
        eval("names = " + participants);
        flowProp.realnames = names[0].realnames;
        flowProp.usernames = names[0].usernames;
    } else {
        participants = "[{realnames:'" + flowProp.realnames + "',usernames:'" + flowProp.usernames + "'}]";
    }
    str = "[" + constructFlow(flowProp) + "]";
    switch(flowProp.flowType) {
        case '2':
            if(flowProp.taskFlowType == "3") {
                alert("辅流第一个任务不能是会簽, 请通知系统管理员！");
            } else {
                if(flowProp.taskCtlType == "2") {
                    if(flowProp.taskCycleType == "0" || flowProp.taskCycleType == "") {
                        participants = selectCycleUsers(sf_project, flowProp.groupName, flowProp.roleName);
                    } else {
                        var checkGroup;
                        if(flowProp.groupName.indexOf(".") < 0)
                            checkGroup = "*";
                        else {
                            checkGroup = flowProp.groupName.substring(0, flowProp.groupName.lastIndexOf(".") + 1)
                                    + "*";
                        }
                        var cycleGroups = groupSelected(sf_project, checkGroup, flowProp.roleName, "1");
                        participants = getGroupsUsersNames(sf_project, cycleGroups, flowProp.roleName);
                    }
                    if(participants != "") {
                        checkTask(flowProp.taskID);
                        eval("names = " + participants);
                        selectedProp.cycleQty = getCharCount(names[0].usernames, ';');
                        selectedProp.cycleUser = names[0].usernames;
                        selectedProp.cycleType = flowProp.taskCycleType;
                        var count = names[0].realnames.indexOf(";");
                        if(count > 0)
                            selectedProp.realnames = names[0].realnames.substr(0, names[0].realnames.indexOf("/"));
                        else
                            selectedProp.realnames = names[0].realnames;
                        count = names[0].usernames.indexOf(";");
                        if(count > 0)
                            selectedProp.usernames = names[0].usernames.substr(0, names[0].usernames.indexOf(";"));
                        else
                            selectedProp.usernames = names[0].usernames;
                        constructFlows(index, oldStr, selectedProp);
                        setParticipants(flowProp.taskID, names[0].realnames)
                    }
                    break;
                }
            }
        case '0': case '1': default:
            if(flowArray[0].taskFlowType == "3") {
                // flowArray[0] => Split task, taskFlowType == 3 => 辅流
                if(isSelectedMask(taskGroup) == 1)
                    participants = selectParticipantDlg(str, selectedStr, "2");
                else
                    participants = selectParticipantDlg(str, selectedStr, "1");
            } else if(flowArray[0].taskFlowType == "2") {
                if(flowProp.usernames.indexOf(",") > 0 && "<%=trayType%>" == "1" || selectedStr == "") {
                    selectedStr = "";
                    var realArr = flowProp.realnames.split(";");
                    var userArr = flowProp.usernames.split(";");
                    var real = "";
                    var user = "";
                    for(var i = 0; i < realArr.length; i++) {
                        if(realArr[i].indexOf(",") < 0) {
                            if(real != "")
                                real += ";";
                            real += realArr[i];
                            if(user != "")
                                user += ";";
                            user += userArr[i];
                        }
                    }
                    selectedStr = "[{realnames:'" + real + "', ";
                    selectedStr += "usernames:'" + user + "'}]";
                    if(isSelectedMask(taskGroup) == 1)
                        participants = selectParticipantDlg(str, selectedStr, "2");
                    else {
//                        if("<%=flowList.size()%>" == "1")
                        if(!document.listform.all("msgid").length)
                            participants = selectParticipantDlg(str, selectedStr, "1");
                        else
                            participants = selectParticipantDlg(str, selectedStr, "0");
                    }
                }
             } else {
                if(flowProp.usernames.indexOf(",") > 0) {
                    if((flowProp.flowType == '0' && "<%=trayType%>" == 1) || flowProp.flowType == '1')
                        participants = selectParticipantDlg(str, selectedStr, "0");
                }
            }
            if(participants != "") {
                checkTask(flowProp.taskID);
                eval("names = " + participants);
                selectedProp.realnames = names[0].realnames;
                selectedProp.usernames = names[0].usernames;
                constructFlows(index, oldStr, selectedProp);
                setParticipants(flowProp.taskID, names[0].realnames);
            }
            break;
/*        case '0':
            assignedFlowArray[index].realnames = flowProp.realnames;
            assignedFlowArray[index].usernames = flowProp.usernames;
            break;
*/        case '3':
//            if(getHandler(sf_handler, flowProp.realnames, ";") != "") {
            if(getHandler(sf_handler, flowProp.usernames, ";") != "") {
//                alert("流程设置了直送经办人, 此任务不需要设置经办人！");
            } else {
                participants = selectParticipantDlg(str, selectedStr, "0");
                if(participants != "") {
                    checkTask(flowProp.taskID);
                     eval("names = " + participants);
                    selectedProp.realnames = names[0].realnames;
                    selectedProp.usernames = names[0].usernames;
                    constructFlows(index, oldStr, selectedProp);
                    setParticipants(flowProp.taskID, names[0].realnames);
                }
            }
            break;
    }
}

function constructFlows(index, oldStr, prop) {
    if(assignedStrArray[index].charAt(0) == '[') {
        assignedStrArray[index] = assignedStrArray[index].replace(oldStr, constructFlow(prop));
    } else {
        assignedStrArray[index] = constructFlow(prop);
    }
    return assignedStrArray[index];
}

function do_close() {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    var count = 0;
    if(msgids.length) {
        for(var i = 0; i < msgids.length; i++) {
            if(!msgids[i].checked) {
                if(!confirm("没有设置所有的流向，是否只启动已设置的流向！"))
                    return;
            } else {
                count++;
            }
        }
    } else {
        if(!msgids.checked) {
            alert("没有设置下一任务组别或参与者!");
            return;
        } else
            count = 1;
    }
    if(count == 0) {
        alert("没有设置下一任务组别或参与者，任务不能完成！");
        return;
    }
    var waitType;
    var idx = document.getElementById("waitType").selectedIndex;
    if (idx > -1)
        waitType = document.getElementById("waitType").options[idx].value;
    var str = "[";
    for(i = 0; i < assignedStrArray.length; i++) {
        var prop = getProcessTask(assignedFlowArray[i]);
        if(prop.taskName == "SPLIT") {
            if(str != "[") {
                str += ",";
            }
            var waitStr = "waitType:'"
            count = assignedStrArray[i].indexOf(waitStr);
            if(count > 0) {
                str += assignedStrArray[i].replace(assignedStrArray[i].substring(count, count + waitStr.length + 1),
                        "waitType:'" + waitType);
            } else {
                str += assignedStrArray[i];
            }
        } else {
            var flowIgnore = false;
            if(msgids.length) {
                for(j = 0; j < msgids.length; j++) {
                    if((!msgids[j].checked) && msgids[j].value == prop.taskID) {
                        flowIgnore = true;
                        break;
                    }
                }
            } else {
                if((!msgids.checked) && msgids.value == prop.taskID) {
                    flowIgnore = true;
                    break;
                }
            }
            if(!flowIgnore) {
                if(str != "[") {
                    str += ",";
                }
                if(prop.usernames == "") {
                    var prop2 = getProcessTask(flowArray[i]);
                    prop.usernames = prop2.usernames;
                    prop.realnames = prop2.realnames;
                }
                str += assignedStrArray[i].replace(getProcessStr(assignedStrArray[i]),
                        constructFlows(i, assignedStrArray[i], prop));
            }
        }
    }
    str += "]";
    window.returnValue = str;
    window.close();
}

function do_cancel() {
    window.returnValue = "";
    window.close();
}

   </script>
</form>
</BODY>
</HTML>
