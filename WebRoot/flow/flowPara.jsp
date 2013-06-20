<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.sinoflow.dto.SfActInfoDTO" %>
<%@ page import="com.sino.sinoflow.user.dto.SfUserBaseDTO" %>
<%@ page import="com.sino.sinoflow.util" %>
<%@ page import="org.json.JSONException" %>
<%

    String isNew2 = (String)request.getAttribute("SINOFLOW_NEW_CASE");
    String outStr;
    String taskStr = "";
    String trayType = "";
    SfActInfoDTO actInfo = null;
    String appField = "";
    String token = "";
    String sf_event = "";

    try {
        actInfo = info;

        token = f.get("token").toString();
        appField = appl.getSortColumn1() + ";" + appl.getSortColumn2() + ";" +
            appl.getSortColumn3() + ";" + appl.getAppColumn1() + ";" + appl.getAppColumn2() +
            ";" + appl.getAppColumn3() + ";" + appl.getAppColumn4() + ";" + appl.getAppColumn5() +
            ";" + appl.getAppColumn6() + ";" + appl.getAppColumn7() + ";"  + appl.getAppColumn8() +
            ";" + appl.getAppColumn9();
        int cycleQty = actInfo.getSfactCycleQty();
        trayType = f.get("trayType").toString();
        //如果狀态为提问, 就要把 sf_event 设定为答复, 使得完成时可以送回提问人
        if(actInfo.getSfactActStatus() == 5)
            sf_event = "5";
%>

<input type="hidden" name="sf_token" value="<%=token%>" readonly="readonly" />
<input type="hidden" name="flow_group_id" value="" readonly="readonly" />
<input type="hidden" name="flow_group_name" value="" readonly="readonly" />
<input type="hidden" name="app_dept_code" value="" readonly="readonly" />
<input type="hidden" name="app_dept_name" value="" readonly="readonly" />
<input type="hidden" name="sf_project" value="<%=actInfo.getSfactProjName()%>" readonly="readonly" />
<input type="hidden" name="sf_procedure" value="<%=actInfo.getSfactProcName()%>" readonly="readonly" />
<input type="hidden" name="sf_procedureid" value="<%=actInfo.getSfactProcId()%>" readonly="readonly" />
<input type="hidden" name="sf_taskid" value="<%=actInfo.getSfactTaskId()%>" readonly="readonly" />
<input type="hidden" name="sf_taskname" value="<%=actInfo.getSfactTaskName()%>" readonly="readonly" />
<input type="hidden" name="sf_taskctl" value="<%=actInfo.getSfactTaskCtl()%>" readonly="readonly" />
<%
    if(actInfo.getSfactCommentQty() <=0 ||
            (actInfo.getSfactCommentType() & 0x20) == 0) {
%>
        <input type="hidden" name="sf_flowDesc" value="<%=actInfo.getSfactApplMsg()%>" readonly="readonly" />
<%
    } else {
%>
        <input type="hidden" name="sf_flowDesc" value="<%=actInfo.getSfactCommentApplMsg()%>" readonly="readonly" />
<%
    }
%>
<%
    if(actInfo.getSfactCaseId().indexOf(":") >= 0) {
        SfUserBaseDTO sfUser = (SfUserBaseDTO) SessionUtil.getUserAccount(request);
%>
<input type="hidden" name="sf_group" value="<%=sfUser.getGroupName()%>" readonly="readonly" />
<%
    } else {
%>
<input type="hidden" name="sf_group" value="<%=util.getMaskGroup(actInfo.getSfactTaskGroup(), actInfo.getSfactHandlerGroup(), actInfo.getSfactPlusGroup())%>" readonly="readonly" />
<%
    }
%>
<input type="hidden" name="sf_setHandlerGroup" value="0" readonly="readonly" />
<input type="hidden" name="sf_handlerGroup" value="<%=actInfo.getSfactHandlerGroup()%>" readonly="readonly" />
<input type="hidden" name="sf_setPlusGroup" value="0" readonly="readonly" />
<input type="hidden" name="sf_plusGroup" value="<%=actInfo.getSfactPlusGroup()%>" readonly="readonly" />
<input type="hidden" name="sf_handler" value="<%=actInfo.getSfactHandler()%>" readonly="readonly" />
<input type="hidden" name="sf_role" value="<%=actInfo.getSfactTaskRole()%>" readonly="readonly" />
<input type="hidden" name="sf_actID" value="<%=actInfo.getSfactActId()%>" readonly="readonly" />
<input type="hidden" name="sf_caseID" value="<%=actInfo.getSfactCaseId()%>" readonly="readonly" />
<input type="hidden" name="sf_appDataID" value="<%=actInfo.getSfactApplId()%>" readonly="readonly" />
<input type="hidden" name="sf_appID" value="<%=actInfo.getSfactAppdefId()%>" readonly="readonly" />
<input type="hidden" name="sf_comment" value="<%=actInfo.getSfactUserMsg()%>" readonly="readonly" />
<input type="hidden" name="sf_priority" value="0" readonly="readonly" />
<input type="hidden" name="sf_reviewQty" value="<%=actInfo.getSfactCommentQty()%>" readonly="readonly" />
<input type="hidden" name="sf_reviewUsers" value="<%=actInfo.getSfactCommentUsers()%>" readonly="readonly" />
<input type="hidden" name="sf_reviewType" value="<%=actInfo.getSfactCommentType()%>" readonly="readonly" />
<input type="hidden" name="sf_cycleQty" value="<%=cycleQty%>" readonly="readonly" />
<input type="hidden" name="sf_cycleUsers" value="<%=actInfo.getSfactCycleUsers()%>" readonly="readonly" />
<input type="hidden" name="sf_cycleType" value="<%=actInfo.getSfactCycleType()%>" readonly="readonly" />
<input type="hidden" name="sf_task_attribute1" value="<%=actInfo.getSfactTaskAttribute1()%>" readonly="readonly" />
<input type="hidden" name="sf_task_attribute2" value="<%=actInfo.getSfactTaskAttribute2()%>" readonly="readonly" />
<input type="hidden" name="sf_task_attribute3" value="<%=actInfo.getSfactTaskAttribute3()%>" readonly="readonly" />
<input type="hidden" name="sf_task_attribute4" value="<%=actInfo.getSfactTaskAttribute4()%>" readonly="readonly" />
<input type="hidden" name="sf_task_attribute5" value="<%=actInfo.getSfactTaskAttribute5()%>" readonly="readonly" />
<%
            if(actInfo.getSfactTaskCtl() == 2 || actInfo.getSfactTaskCtl() == 3) {
                taskStr = "[{taskID:'" + actInfo.getSfactTaskId() + "', ";
                taskStr += "procName:'" + actInfo.getSfactProcName() + "', ";
                taskStr += "taskName:'" + actInfo.getSfactTaskName() + "', ";
                taskStr += "taskDesc:'" + actInfo.getSfactTaskDesc() + "', ";
                taskStr += "groupName:'" + actInfo.getSfactTaskGroup() + "', ";
                taskStr += "roleName:'" + actInfo.getSfactTaskRole() + "', ";
                taskStr += "taskDur:'"  + actInfo.getSfactTaskDuration() + "', ";
                taskStr += "taskWorktype:'" + actInfo.getSfactTaskWorkType() + "', ";
                taskStr += "taskCtlType:'" + actInfo.getSfactTaskCtl() + "', ";
                taskStr += "taskCycleType:'" + actInfo.getSfactTaskCycleType() + "', ";
                taskStr += "taskApi:'" + actInfo.getSfactTaskApiName() + "', ";
                taskStr += "taskDivRight:'" + actInfo.getSfactTaskDivRight() + "', ";
                taskStr += "taskDivHidden:'" + actInfo.getSfactTaskHidden() + "', ";
                taskStr += "taskFlowType:'" + actInfo.getSfactTaskType() + "', ";
                String users = actInfo.getSfactCycleUsers();
                int count = users.indexOf(";");
                if(count > 0)
                    taskStr += "usernames:'" + users.substring(0, count) + "', ";
                else
                    taskStr += "usernames:'" + users + "', ";
                taskStr += "flowProp:'0', ";
                taskStr += "flowID:'', ";
                taskStr += "flowCode:'', ";
                taskStr += "flowDesc:'" + actInfo.getSfactApplMsg() + "', ";
                taskStr += "flowHint:'" + actInfo.getSfactApplMsg() + "', ";
                taskStr += "flowType:'0', ";
                taskStr += "cycleQty:'" + cycleQty + "', ";
                taskStr += "cycleUser:'" + actInfo.getSfactCycleUsers() + "', ";
                taskStr += "cycleType:'" + actInfo.getSfactTaskCycleType() + "', ";
                taskStr += "reviewQty:'" + actInfo.getSfactCommentQty() + "', ";
                taskStr += "reviewUsers:'" + actInfo.getSfactCommentUsers() + "', ";
                taskStr += "reviewType:'" + actInfo.getSfactCommentType() + "'}]";
            }

                outStr = f.get("loadStr").toString(); 
            } catch (ClassCastException ex) {
            	outStr = "";
            } catch(JSONException ex) {
                outStr = "";
            }
            String handler = "[{sf_handler:'" + actInfo.getSfactHandler() +
                        "', sf_handlerGroup:'" + actInfo.getSfactHandlerGroup() + "', sf_plusGroup:'"
                        + actInfo.getSfactPlusGroup() + "'}]";
%>
            
<input type='hidden' name='sinoflow_load_data' value='<%=outStr%>' readonly='readonly' />
<input type='hidden' name='sf_handlerStatus' value = "<%=handler%>" readonly='readonly' />
<input type='hidden' name='sf_nextTaskData' value = "<%=taskStr%>" readonly='readonly' />
<input type='hidden' name='sf_end' value = "0" readonly='readonly' />
<input type='hidden' name='sf_trayType' value='<%=trayType%>' readonly='readonly' />
<input type='hidden' name='sf_userID' value='<%=user.getUserId()%>' readonly='readonly' />
<input type='hidden' name='sf_appField' value='<%=appField%>' readonly='readonly' />
<input type='hidden' name='sf_appFieldValue' value='' readonly='readonly' />
<input type='hidden' name='sf_appMask' value="<%=bMask%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn1' value="<%=actInfo.getSfactApplColumn1()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn2' value="<%=actInfo.getSfactApplColumn2()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn3' value="<%=actInfo.getSfactApplColumn3()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn4' value="<%=actInfo.getSfactApplColumn4()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn5' value="<%=actInfo.getSfactApplColumn5()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn6' value="<%=actInfo.getSfactApplColumn6()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn7' value="<%=actInfo.getSfactApplColumn7()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn8' value="<%=actInfo.getSfactApplColumn8()%>" readonly='readonly' />
<input type='hidden' name='sf_appColumn9' value="<%=actInfo.getSfactApplColumn9()%>" readonly='readonly' />
<input type='hidden' name='sf_object' value='<%=fstr%>' readonly='readonly' />
<input type='hidden' name='sf_opinion' value='' readonly='readonly' />
<input type='hidden' name='sf_copyFlag' value='' readonly='readonly' />
<input type='hidden' name='sf_copyUsers' value='' readonly='readonly' />
<input type='hidden' name='sf_copyMsg' value='' readonly='readonly' />
<input type='hidden' name='sf_isNew' value='<%=isNew2%>' readonly='readonly' />
<input type='hidden' name='sf_nextTaskUsers' value='' readonly='readonly' />
<input type='hidden' name='sf_event' value='<%=sf_event%>' readonly='readonly' />
<input type='hidden' name='sf_eventUser' value='' readonly='readonly' />
<input type='hidden' name='sf_fromDate' value='<%=actInfo.getSfactFromDate()%>' readonly='readonly' />
<%
    if(actInfo.getSfactTaskUrl().trim().equals("")) {
%>
<input type='hidden' name='sf_url' value='<%=actInfo.getSfactUrl()%>' readonly='readonly' />
<%
    } else {
%>
<input type='hidden' name='sf_url' value='<%=actInfo.getSfactTaskUrl()%>' readonly='readonly' />
<%
    }
%>


<input type='hidden' name='sf_store1' value='' readonly='readonly' />
<input type='hidden' name='sf_store2' value='' readonly='readonly' />
<input type='hidden' name='sf_store3' value='' readonly='readonly' />
<input type='hidden' name='sf_store4' value='' readonly='readonly' />
<input type='hidden' name='sf_store5' value='' readonly='readonly' />

    <link rel="stylesheet" type="text/css" href="/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="/WebLibary/exj/ext/ext-base.js"></script>
<script type="text/javascript" src="/WebLibary/exj/ext/ext-all.js"></script>   
<script language="javascript" src="/WebLibary/exj/ext/SessionProvider.js"></script>
<script language="javascript" src="/WebLibary/exj/ext/layout.js"></script>
<script type="text/javascript" src="/WebLibary/exj/shared/examples.js"></script>  
<script type="text/javascript" src="/extjs/ux/MultiSelect.js"></script>
<script type="text/javascript" src="/extjs/ux/ItemSelector.js"></script>

    