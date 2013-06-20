<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.db.conn.DBManager" %>
<%@ page import="com.sino.base.db.query.SimpleQuery" %>
<%@ page import="com.sino.base.db.sql.model.SQLModel" %>
<%@ page import="com.sino.base.exception.ContainerException" %>
<%@ page import="com.sino.base.exception.QueryException" %>
<%@ page import="com.sino.base.log.Logger" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="com.sino.sinoflow.model.SfTaskModel" %>
<%@ page import="com.sino.sinoflow.util" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<%

    String handlerStatus = request.getParameter("sf_handlerStatus");
    String handler = util.getJsonData(handlerStatus, "sf_handler:");
    String handlerGroup = util.getJsonData(handlerStatus, "sf_handlerGroup:");
    String plusGroup = util.getJsonData(handlerStatus, "sf_plusGroup");
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.MULTI_TASK_FLOW_ROWSET);
    String jsonFlowStr = "";
    boolean parallelFlow = true;
    try {
    if (rows != null && rows.getSize() > 0) {
        Row row;

//        jsonFlowStr = "[";
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
/*            if(i != 0) {
                jsonFlowStr += ", ";
            }
            jsonFlowStr += "{length:'1', taskID:'" + row.getStrValue("TASK_ID") + "', ";
            jsonFlowStr += "procName:'" + row.getStrValue("PROCEDURE_NAME") + "', ";
            jsonFlowStr += "taskName:'" + row.getStrValue("TASK_NAME") + "', ";
            jsonFlowStr += "taskDesc:'" + row.getStrValue("TASK_DESC") + "', ";
            jsonFlowStr += "groupName:'" + row.getStrValue("GROUP_NAME") + "', ";
            jsonFlowStr += "roleName:'" + row.getStrValue("ROLE_NAME") + "', ";
            jsonFlowStr += "taskDur:'"  + row.getStrValue("DURATION") + "', ";
            jsonFlowStr += "taskWorktype:'" + row.getStrValue("DURATION_UNIT") + "', ";
            jsonFlowStr += "taskCtlType:'" + row.getStrValue("CONTROL_TYPE") + "', ";
            jsonFlowStr += "taskCycleType:'" + row.getStrValue("CYCLE_TYPE") + "', ";
            jsonFlowStr += "taskApi:'" + row.getStrValue("API") + "', ";
            jsonFlowStr += "taskDivRight:'" + row.getStrValue("DIV_RIGHT") + "', ";
            jsonFlowStr += "taskDivHidden:'" + row.getStrValue("DIV_HIDDEN") + "', ";
            jsonFlowStr += "taskFlowType:'" + row.getStrValue("TASK_FLOW_TYPE") + "', ";
            if(row.getStrValue("FLOW_TYPE").equals("3")) {
                List handlerList = util.explodeToList(handler, ";");
                List reals = util.explodeToList(row.getStrValue("REAL_NAMES"), ";");
                List names = util.explodeToList(row.getStrValue("USERNAMES"), ";");
                boolean found = false;
                for(int j = 0; j < handlerList.size(); j++) {
                    String user = handlerList.get(j).toString().trim();
                    for(int k = 0; k < names.size(); k++) {
                        if(user.equals(names.get(k).toString().trim())) {
                            found = true;
                            jsonFlowStr += "realnames:'" + reals.get(j).toString().trim() + "', ";
                            jsonFlowStr += "usernames:'" + user + "', ";
                            break;
                        }
                    }
                    if(found)
                        break;
                }
                if(!found) {
                    jsonFlowStr += "realnames:'" + row.getStrValue("REAL_NAMES") + "', ";
                    jsonFlowStr += "usernames:'" + row.getStrValue("USERNAMES") + "', ";                    
                }
            } else {
                jsonFlowStr += "realnames:'" + row.getStrValue("REAL_NAMES") + "', ";
                jsonFlowStr += "usernames:'" + row.getStrValue("USERNAMES") + "', ";
            }
            jsonFlowStr += "flowProp:'" + row.getStrValue("FLOW_PROP") + "', ";
            jsonFlowStr += "flowID:'" + row.getStrValue("FLOW_ID") + "', ";
            jsonFlowStr += "flowDesc:'" + row.getStrValue("FLOW_DESC") + "', ";
            jsonFlowStr += "flowCode:'" + row.getStrValue("FLOW_CODE") + "', ";
            jsonFlowStr += "flowType:'" + row.getStrValue("FLOW_TYPE") + "', ";
            jsonFlowStr += "cycleQty:'0', ";
            jsonFlowStr += "cycleUser:'', ";
            jsonFlowStr += "cycleType:'', ";
            jsonFlowStr += "reviewQty:'0', ";
            jsonFlowStr += "reviewUsers:'', ";
            jsonFlowStr += "reviewType:''}";
*/            if(!row.getStrValue("FLOW_PROP").equals("1"))
                parallelFlow = false;
        }
//        jsonFlowStr += "]";
    }
    } catch (ContainerException ex) {
        Logger.logError(ex);
    }
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
<input type="hidden" name="sf_actID" value="" readonlye="readonly" />

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
<script language="JavaScript" src="/WebLibary/js/util.js">
</script>
<STYLE TYPE="text/css">
<!--
#menuDiv {position:relative; left:0; top:document.body.scrollHeight;  width:document.body.clientWidth; height:document.body.clientHeight-document.body.scrollHeight; 
				clip:rect(0,3000,3000,0); layer-background-color:white; background-color:white; overflow-x:auto;overflow-y:hidden;}
-->
</STYLE>
<SCRIPT LANGUAGE="JavaScript">
<!--
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
    if(request.getParameter("sf_waitType").equals("0")) {
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
    try {
    if (rows != null && rows.getSize() > 0) {
        Row row;
        int i;
        jsonFlowStr = "[";
        for (i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
            String taskFlowType = row.getStrValue("TASK_FLOW_TYPE");
            String flowProp = row.getStrValue("FLOW_PROP");
            String prveTaskId = row.getStrValue("TASK_ID");
            boolean isSubPro = false;
            if(taskFlowType.equals("4")) {
                Connection conn = DBManager.getDBConnection();
                SQLModel sqlModel = (new SfTaskModel(null, null)).getSubflowFirstTaskModel(
                        row.getStrValue("PROJECT_NAME"), row.getStrValue("TASK_NAME"),
                        handlerGroup, plusGroup);
                SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
                simpleQuery.executeQuery();
                RowSet rs;
                if(simpleQuery.hasResult()) {
                    rs = simpleQuery.getSearchResult();
                    row = rs.getRow(0);
                    isSubPro = true;
                }
                DBManager.closeDBConnection(conn);
            }
            if(jsonFlowStr != "[") {
                jsonFlowStr += ", ";
            }
/*
            if(parallelFlow || (!parallelFlow && flowProp.equals("0"))) {
                jsonFlowStr += "{length:'1',";
            } else {
                if(isSubPro) {
                    int tempLen = Integer.parseInt(prveTaskId) * -1;
                    jsonFlowStr += "{length:'" + tempLen + "', ";
                } else {
                    jsonFlowStr += "{length:'-1', ";
                }
            }
*/
            jsonFlowStr += "{taskID:'" + row.getStrValue("TASK_ID") + "', ";
            jsonFlowStr += "procName:'" + row.getStrValue("PROCEDURE_NAME") + "', ";
            jsonFlowStr += "taskName:'" + row.getStrValue("TASK_NAME") + "', ";
            jsonFlowStr += "taskDesc:'" + row.getStrValue("TASK_DESC") + "', ";
            jsonFlowStr += "groupName:'" + row.getStrValue("GROUP_NAME") + "', ";
            jsonFlowStr += "roleName:'" + row.getStrValue("ROLE_NAME") + "', ";
            jsonFlowStr += "taskDur:'"  + row.getStrValue("DURATION") + "', ";
            jsonFlowStr += "taskWorktype:'" + row.getStrValue("DURATION_UNIT") + "', ";
            jsonFlowStr += "taskCtlType:'" + row.getStrValue("CONTROL_TYPE") + "', ";
            jsonFlowStr += "taskCycleType:'" + row.getStrValue("CYCLE_TYPE") + "', ";
            jsonFlowStr += "taskApi:'" + row.getStrValue("API") + "', ";
            jsonFlowStr += "taskDivRight:'" + row.getStrValue("DIV_RIGHT") + "', ";
            jsonFlowStr += "taskDivHidden:'" + row.getStrValue("DIV_HIDDEN") + "', ";
            jsonFlowStr += "taskFlowType:'" + row.getStrValue("TASK_FLOW_TYPE") + "', ";
            if(row.getStrValue("FLOW_TYPE").equals("3")) {
                List handlerList = util.explodeToList(handler, ";");
                List reals = util.explodeToList(row.getStrValue("REAL_NAMES"), ";");
                List names = util.explodeToList(row.getStrValue("USERNAMES"), ";");
                boolean found = false;
                for(int j = 0; j < handlerList.size(); j++) {
                    String user = handlerList.get(j).toString().trim();
                    for(int k = 0; k < names.size(); k++) {
                        if(user.equals(names.get(k).toString().trim())) {
                            found = true;
                            jsonFlowStr += "realnames:'" + reals.get(j).toString().trim() + "', ";
                            jsonFlowStr += "usernames:'" + user + "', ";
                            break;
                        }
                    }
                    if(found)
                        break;
                }
                if(!found) {
                    jsonFlowStr += "realnames:'" + row.getStrValue("REAL_NAMES") + "', ";
                    jsonFlowStr += "usernames:'" + row.getStrValue("USERNAMES") + "', ";
                }
            } else {
                jsonFlowStr += "realnames:'" + row.getStrValue("REAL_NAMES") + "', ";
                jsonFlowStr += "usernames:'" + row.getStrValue("USERNAMES") + "', ";
            }
            jsonFlowStr += "flowProp:'" + row.getStrValue("FLOW_PROP") + "', ";
            jsonFlowStr += "flowID:'" + row.getStrValue("FLOW_ID") + "', ";
            jsonFlowStr += "flowDesc:'" + row.getStrValue("FLOW_DESC") + "', ";
            jsonFlowStr += "flowCode:'" + row.getStrValue("FLOW_CODE") + "', ";
            jsonFlowStr += "flowType:'" + row.getStrValue("FLOW_TYPE") + "', ";
            jsonFlowStr += "cycleQty:'0', ";
            jsonFlowStr += "cycleUser:'', ";
            jsonFlowStr += "cycleType:'', ";
            jsonFlowStr += "reviewQty:'0', ";
            jsonFlowStr += "reviewUsers:'', ";
            jsonFlowStr += "reviewType:''}";
            String taskId = row.getStrValue("TASK_ID");
            String taskName = row.getStrValue("TASK_NAME");
            String groupName = row.getStrValue("GROUP_NAME");
            String roleName = row.getStrValue("ROLE_NAME");
            String realNames = row.getStrValue("REAL_NAMES");
            String userNames = row.getStrValue("USERNAMES");
%>
<tr height=18 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>
<%
    String maskGroup = util.getMaskGroup(groupName, handlerGroup, plusGroup);
    String flowType = row.getStrValue("FLOW_TYPE");
    if((flowType.equals("0") || (flowType.equals("3") && !handler.equals(""))) &&
        (parallelFlow || ((!parallelFlow) && flowProp.equals("0"))) &&
        (util.needGroupSelected(maskGroup) == 0)) {
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
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap><td  nowrap  onclick="SetFlowProp('<%=i%>')"><%=taskName%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap><td id="sendgroups" nowrap  onclick="SetFlowProp('<%=i%>')"><%=groupName%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap><td  nowrap  onclick="SetFlowProp('<%=i%>')"><%=roleName%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap><td id="sendusers" nowrap  onclick="SetFlowProp('<%=i%>')"><%=realNames%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap></td>
<%
        }
        jsonFlowStr += "]";
    }
    } catch (ContainerException ex) {
        Logger.logError(ex);
    } catch (QueryException ex) {
        Logger.logError(ex);
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
sf_project = document.getElementById("sf_project").value

menulayer = new DynLayer("menuDiv")
menulayer.slideInit()
iInit = true;
movemenulayer()
eval("flowArray = " + "<%=jsonFlowStr%>");
eval("assignedFlowArray = " + "<%=jsonFlowStr%>");
for(var i = 0; i < assignedFlowArray.length; i++) {
    assignedFlowArray[i].realnames = "";
    assignedFlowArray[i].usernames = "";
}
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

function checkTask(taskid) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    for(var i = 0; i < msgids.length; i++) {
        if(msgids[i].value == taskid) {
            msgids[i].checked = true;
            break;
        }
    }
}

function uncheckTask(taskid) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    for(var i = 0; i < msgids.length; i++) {
        if(msgids[i].value == taskid) {
            msgids[i].checked = false;
            break;
        }
    }
}

function setGroup(taskid, groups) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    var sendGroups=listform.all("sendgroups");
    for(var i = 0; i < msgids.length; i++) {
        if(msgids[i].value == taskid) {
            sendGroups[i].innerHTML=groups;
            break;
        }
    }
}

function setParticipants(taskid, users) {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    var sendUsers=listform.all("sendusers");
    for(var i = 0; i < msgids.length; i++) {
        if(msgids[i].value == taskid) {
            sendUsers[i].innerHTML=users; 
            break;
        }
    }
}
function SetFlowProp(index) {
    var flowProp = flowArray[index];
    var selectedProp = assignedFlowArray[index];
    if(selectedProp.realnames == "") {
        selectedStr = "";
    } else {
        selectedStr = "[{realnames:'" + selectedProp.realnames + "', ";
        selectedStr += "usernames:'" + selectedProp.usernames + "'}]";
    }
    var participants;
    var names;
    var str, selectedStr;
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
        assignedFlowArray[index].groupName = groupMask;
        setGroup(flowProp.taskID, flowProp.groupName);

        participants = getGroupsUsersNames(sf_project, flowProp.groupName, flowProp.roleName);
        if(participants == "")
            return;
        eval("names = " + participants);
        flowProp.realnames = names[0].realnames;
        flowProp.usernames = names[0].usernames;
    }
    str = "[" + constructFlow(flowProp, "") + "]";
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
                        assignedFlowArray[index].cycleQty = getCharCount(names[0].usernames, ';');
                        assignedFlowArray[index].cycleUser = names[0].usernames;
                        assignedFlowArray[index].cycleType = flowProp.taskCycleType;
                        assignedFlowArray[index].realnames = names[0].realnames.substr(0, names[0].realnames.indexOf("/"));
                        var count = names[0].usernames.indexOf(";");
                        if(count > 0)
                            assignedFlowArray[index].usernames = names[0].usernames.substr(0, names[0].usernames.indexOf(";"));
                        else
                            assignedFlowArray[index].usernames = names[0].usernames;                        
                        setParticipants(flowProp.taskID, names[0].realnames)
                    }
                    break;
                }
            }
        case '0': case '1': default:
            var length = parseInt(flowProp.length);
            if(length < 0) {
                // 辅流
                participants = selectParticipantDlg(str, selectedStr, "1");
            } else {
                participants = selectParticipantDlg(str, selectedStr, "0");
            }
            if(participants != "") {
                checkTask(flowProp.taskID);
                eval("names = " + participants);
                assignedFlowArray[index].realnames = names[0].realnames;
                assignedFlowArray[index].usernames = names[0].usernames;
                setParticipants(flowProp.taskID, names[0].realnames);
            }
            break;
/*        case '0':
            assignedFlowArray[index].realnames = flowProp.realnames;
            assignedFlowArray[index].usernames = flowProp.usernames;
            break;
*/        case '3':
            if(getHandler(sf_handler, flowProp.realnames, ";") != "")
                alert("流程设置了直送经办人, 此任务不需要设置经办人！");
            else {
                participants = selectParticipantDlg(str, selectedStr, "0");
                if(participants != "") {
                    checkTask(flowProp.taskID);
                     eval("names = " + participants);
                    assignedFlowArray[index].realnames = names[0].realnames;
                    assignedFlowArray[index].usernames = names[0].usernames;
                    setParticipants(flowProp.taskID, names[0].realnames);
                }
            }
            break;
    }
}

function do_close() {
    var listform=document.listform;
    var msgids=listform.all("msgid");
    var count = 0;
    for(var i = 0; i < msgids.length; i++) {
        if(!msgids[i].checked) {
            if(!confirm("没有设置所有的流，是否只启动已设置的流！"))
                return;
        } else {
            count++;
        }
    }
    if(count == 0) {
        alert("流没有设置，任务不能完成，请设置流！");
        return;
    }
    var nextTaskData = "[";
    var waitType;
    var idx = document.getElementById("waitType").selectedIndex;
    if (idx > -1)
        waitType = document.getElementById("waitType").options[idx].value;
    for(i = 0; i < msgids.length; i++) {
        if(msgids[i].checked) {
            if(assignedFlowArray[i].usernames == "") {
                assignedFlowArray[i].usernames = flowArray[i].usernames;
                assignedFlowArray[i].realnames = flowArray[i].realnames;
            }
            if(nextTaskData != "[")
                nextTaskData += ",";
            nextTaskData += constructFlow(assignedFlowArray[i], waitType);
        }
    }
    nextTaskData += "]";
    window.returnValue = nextTaskData;
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
