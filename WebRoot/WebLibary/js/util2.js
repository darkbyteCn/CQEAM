var multiParallelFlow = false;
var cycleStr = "";
var saveGroup = "";
var haveCycleUser = false;
var Launch_SelectUsers = "";
var groupChange = "";
var disError = false;

//从服务器取下一任务信息
function getNextTask2() {
    disError = false;
    groupChange = "";
    multiParallelFlow = false;
    cycleStr = "";
    saveGroup = "";
    var reviewArray;
    var type;
    var taskData = "";
    var count;
    clearLaunchPara();
    processReviewUsers = false;
    restoreInfo();
    var cycle = false;
    haveCycleUser = false;
    if (document.getElementById("sf_taskctl").value == "3") {
        if (document.getElementById("sf_reviewQty").value == "0") {
            var resText = "";
            Launch_ReviewUsers = "";
            SFGroupReview();
            if (!Launch_Continue) {
                if (Error_Msg != "")
                    alert(Error_Msg);
                return "";
            } else {
                if (Launch_ReviewUsers != "") {
                    resText = constructReviewUsers();
                }
            }
            var review = true;
            if (Launch_SendType == 2) {
                review = false;
            } else if (resText == "") {
                review = confirm("系统设置了阅示, 按确定选择阅示人, 按取消不进行阅示直接流到下一任务?");
                if (review) {
                    resText = selectReviewDlg(document.getElementById("sf_taskid").value);
                    if (resText == "")
                        return "";
                }
            }
            if (review) {
                eval("reviewArray = " + resText);
                type = new Number(reviewArray[0].type) + new Number(reviewArray[0].waitType) * 2;
                save_reviewType = document.getElementById("sf_reviewType").value;
                document.getElementById("sf_reviewType").value = type;
                var ReviewUsers = reviewArray[0].users;
                save_reviewUsers = document.getElementById("sf_reviewUsers").value;
                document.getElementById("sf_reviewUsers").value = ReviewUsers;
                save_reviewQty = document.getElementById("sf_reviewQty").value;
                document.getElementById("sf_reviewQty").value = getCharCount(ReviewUsers, ';');
                if (reviewArray[0].waitType == "1") {
                    taskData = selectFlow2(document.getElementById("sf_taskid").value, "", "0");
                    processReviewUsers = true;
                } else {
                    processReviewUsers = true;
                    alert("阅示设置为等待, 必须所有阅示人员阅示完毕才可完成, 可使用暫存保存信息！");
                }
                ShowSinoButton(7);
            } else {
                taskData = selectFlow2(document.getElementById("sf_taskid").value, "", "0");
            }
        } else {
            save_nextTaskData = document.getElementById("sf_nextTaskData").value;
            setFlowPara(save_nextTaskData);
            type = new Number(flowArray[0].reviewType);
            if ((type & 0x20) == 0x20) {
                var reviewQty = flowArray[0].reviewQty;
                if (reviewQty == "1") {
                    taskData = "[{length:'0', taskFlowType:'" + flowArray[0].taskFlowType + "', taskID:'"
                            + flowArray[0].taskID + "'}]";
                } else {
                    var allReviewUsers = flowArray[0].reviewUsers;
                    Launch_ReviewUsers = allReviewUsers.substring(allReviewUsers.indexOf(";") + 1,
                            allReviewUsers.length);

                    SFGroupReview();
                    if (!Launch_Continue) {
                        if (Error_Msg != "")
                            alert(Error_Msg);
                        return "";
                    } else {
                        if (Launch_ReviewUsers != "") {
                            resText = constructReviewUsers();
                        }
                    }
                    if (Launch_ReviewUsers != "") {
                        assignedFlowArray[0].reviewQty = getCharCount(Launch_ReviewUsers, ';');
                        assignedFlowArray[0].reviewUsers = Launch_ReviewUsers;
                        count = Launch_ReviewUsers.indexOf(";");
                        if (count > 0)
                            assignedFlowArray[0].usernames = Launch_ReviewUsers.substr(0, count);
                        else
                            assignedFlowArray[0].usernames = Launch_ReviewUsers;
                    }
                    assignedFlowArray[0].realnames = getRealname(assignedFlowArray[0].usernames) + "/" +
                            assignedFlowArray[0].groupName;
                    taskData = constructFlow(assignedFlowArray[0]);
                }
            } else {
                taskData = checkReviewStatus(document.getElementById("sf_actID").value);
            }
        }
    } else {
        var cycleCount = document.getElementById("sf_cycleQty").value;
        if (cycleCount == "" || cycleCount == "0" || cycleCount == "1") {
            if (document.getElementById("sf_taskctl").value == "2" && cycleCount == "0") {
                var tempFlowProp;
                eval("tempFlowProp = " + document.getElementById("sf_nextTaskData").value);
                tempFlowProp[0].flowType = "2";
                new extFlowObject.show();
                extFlowObject.setselectSubmitStatus(false);
                taskData = getReturnProp2(tempFlowProp[0]);
                if (taskData == "") {
                    //                    if(confirm("是否不进行会签?"))
                    taskData = selectFlow2(document.getElementById("sf_taskid").value, "", "0");
                } else {
                    cycle = true;
                }
                cycleStr = taskData;
            } else {
                save_cyelcQty = "0";
                save_cycleUsers = "";
                save_cycleType = "";
                taskData = selectFlow2(document.getElementById("sf_taskid").value, "", "0");
            }
        } else {
            save_nextTaskData = document.getElementById("sf_nextTaskData").value;
            setFlowPara(save_nextTaskData);
            var allCycleUsers = flowArray[0].cycleUser;
            var CycleUsers = allCycleUsers.substring(allCycleUsers.indexOf(";") + 1, allCycleUsers.length);
            Launch_CycleUsers = CycleUsers;
            Launch_CycleQueryUsers = CycleUsers;
            Launch_IsEndCycleView = false;
            Launch_CycleQueryType = "0";
            Launch_CycleType = flowArray[0].taskCycleType;
            SFQueryCycleReview();
            if (!Launch_Continue) {
                if (Error_Msg != "")
                    alert(Error_Msg);
                return "";
            }
            if (Launch_IsEndCycleView) {
                save_cycleQty = "0";
                save_cycleUsers = "";
                save_cycleType = "";
                taskData = selectFlow2(document.getElementById("sf_taskid").value, Launch_Code, "0");
            } else {
                CycleUsers = Launch_CycleUsers;
                if (CycleUsers != "") {
                    assignedFlowArray[0].cycleQty = getCharCount(CycleUsers, ';');
                    assignedFlowArray[0].cycleUser = CycleUsers;
                    count = CycleUsers.indexOf(";");
                    var cycUsers;
                    if (count > 0)
                        cycUsers = CycleUsers.substr(0, count);
                    else
                        cycUsers = CycleUsers;
                    cycUsers = cycUsers.replaceAll(",", ";");
                    if (count > 0)
                        assignedFlowArray[0].usernames = cycUsers;
                    else
                        assignedFlowArray[0].usernames = cycUsers;
                                        if(assignedFlowArray[0].cycleType == "1") {
                        assignedFlowArray[0].groupName = getGroupName(assignedFlowArray[0].usernames, document.getElementById("sf_role").value);
                    }    
                    assignedFlowArray[0].realnames = getRealname(assignedFlowArray[0].usernames) + "/" +
                            assignedFlowArray[0].groupName;
                    taskData = constructFlow(assignedFlowArray[0]);
                    cycle = true;
                } else {
                    save_cycleQty = "0";
                    save_cycleUsers = "";
                    save_cycleType = "";
                    taskData = selectFlow2(document.getElementById("sf_taskid").value, Launch_Code, "0");
                }
            }
            cycleStr = taskData;
        }
    }
    if (cycle) {
        fullSelect("[" + cycleStr + "]");
    }
    return taskData;
}

function selectFlow2(taskid, flowCode, isCycle) {
    /*
     clearFlowSelect();
     clearGroupSelect();
     clearParticipantsSelect();
     new extFlowObject.setCommentValue("");
     */
    var selectFlowURL = "/servlet/com.sino.sinoflow.servlet.GetNextTaskCount?sf_taskID='"
            + taskid + "'&sf_flowCode='" + flowCode
            + "'&isCycle=" + isCycle + "&sf_handlerStatus='"
            + document.getElementById("sf_handlerStatus").value
            + "'&sf_actID=" + document.getElementById("sf_actID").value
            + "&event=" + document.getElementById("sf_event").value;
    makeRequest(selectFlowURL, getNextTaskCount2);
    return ajaxReturn;
}

//从服务器取下一节点信息，如果多于一个节点，弹出
function getNextTaskCount2() {
    try {
        ajaxReturn = "";
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR2') {
                alert("辅流设置为等待,必需所有辅流完成后才能完成!");
                ajaxReturn = "";
                return;
            } else if (resText.indexOf('ERROR') >= 0) {
                if (resText.indexOf('message') >= 0) {
                    var res;
                    eval("res = " + resText);
                    alert(res[0].message);
                } else {
                    alert("取下一任务流向出错，请重新尝试提交或通知系统管理员！");
                }
                ajaxReturn = "";
                return;
            }
            ajaxReturn = fullSelect(resText);
        }
    } catch(e) {
        alert("取下一任务流向出错！请通知系统管理员！" + e.message);
    }
}

function fullSelect(flowStr) {
    new extFlowObject.show();
    extFlowObject.setselectSubmitStatus(false);
    //    new extFlowObject.DisplayObj();

    var prop;
    eval("prop = " + flowStr);

    processTasks2(prop, flowStr);
    new extFlowObject.HideSelectObj();
}

function processTasks2(prop, str) {
    var retStr = "";
    var i;
    if (prop.length == 1) {
        if (prop[0].length) {
            retStr = processTasks2(prop[0], str.substring(1, str.length - 1));
            if (retStr == "")
                return retStr;
            return "[" + retStr + "]";
        }

        if (prop[0] && prop[0].taskName == "SPLIT")
            return processMultipleTask2(prop, str);

        retStr = processSingleTask2(prop[0], str.substring(1, str.length - 1));

        if (retStr == "" || retStr == "undefined")
            return "";
        else {
            var prop2;
            eval("prop2 = " + retStr);
            if (prop2) {
                if (!prop2.length && prop2.taskName == "JOIN") {
                    /*
                     //                    if(extFlowObject.GetParsonCount() <= 0) {
                     extFlowObject.removeAllParsonItem();
                     document.getElementById("toParson").parentNode.previousSibling.innerHTML = "等待办理人:";
                     var users = getParallelUsers(document.getElementById("sf_actID").value);
                     if(users.indexOf("usernames") >= 0) {
                     var userArr;
                     eval("userArr = " + users);
                     var realnames = userArr[0].realnames.split(";");
                     for(var i = 0; i < realnames.length; i++)
                     new extFlowObject.addparsonItem("", realnames[i]);
                     } else {
                     new extFlowObject.addparsonItem("", "");
                     }
                     //                    }
                     }else if(!prop2.length && prop2.taskName == "STOP"){
                     if(extFlowObject.GetParsonCount() <= 0) {
                     new extFlowObject.addparsonItem("", "交与办理人");
                     }
                     */
                    extFlowObject.setselectSubmitStatus(true);
                }
                return "[" + retStr + "]";
            } else {
                return "";
            }
        }
    } else {
        var taskName = prop[0].taskName;
        var condFlow = getFlowPropInArray(prop[1]).condFlow;
        if (taskName == "SPLIT") {
            fillFlowStr(str);
            return processMultipleTask2(prop[0], str);
        } else if ((taskName == "JOIN" || taskName == "STOP" || prop[0].taskFlowType == "4")
                && condFlow != '1') {
            var tasksStr = getFlowStr(str.substring(1, str.length - 1));
            var tempStr;
            retStr = "";
            for (i = 0; i < prop.length; i++) {
                if (prop[i].length)
                    tempStr = processTasks2(prop[i], tasksStr[i]);
                else {
                    if (tasksStr[0] != '[')
                        tempStr = processSingleTask2(prop[i], "[" + tasksStr + "]");
                    else
                        tempStr = processSingleTask2(prop[i], tasksStr); //tasksStr[i]);
                }
                if (tempStr == "")
                    return tempStr;
                if (retStr != "")
                    retStr += ",";
                retStr += tempStr;
            }
            return "[" + retStr + "]";
        } else {
            if (condFlow == "1") {
                return processConditionalTask2(prop, str);
            } else {
                tasksStr = getFlowStr(str.substring(1, str.length - 1));
                tempStr;
                retStr = "";
                for (i = 0; i < prop.length; i++) {
                    if (prop[i].length)
                        tempStr = processTasks2(prop[i], tasksStr[i]);
                    else
                        tempStr = processSingleTask2(prop[i], tasksStr[i]);
                    if (tempStr == "")
                        return tempStr;
                    if (retStr != "")
                        retStr += ",";
                    retStr += tempStr;
                }
                return "[" + retStr + "]";
            }
        }
    }
}

function processConditionalTask2(prop, str) {
    // conditional flow
    SFQueryConditionalFlow();
    if (!Launch_Continue) {
        if (Error_Msg != "")
            alert(Error_Msg);
        return "";
    }
    var code = Launch_Code;
    var retStr;
    if (code != "") {
        var tasksStr = getFlowStr(str.substring(1, str.length - 1));
        for (var i = 0; i < prop.length; i++) {

            var selProp = getFlowPropInArray(prop[i]);
            if (selProp.flowCode == code) {
                if(code.indexOf("[O]") >= 0 || code.indexOf("[Y]") >= 0) {
                    if(Launch_Hint != selProp.flowDesc)
                        continue;
                }
                retStr = tasksStr[i];
                if (retStr.charAt(0) != '[')
                    retStr = "[" + retStr + "]";
                var propArr;
                eval("propArr = " + retStr);
                //                fillFlowStr(str);
                fillFlowStr(retStr);
                return processTasks2(propArr, retStr);
            }
        }
    }
    retStr = fillFlowStr(str);
    if (retStr == "")
        return retStr;
    else {
        return retStr;
    }
}

function processSingleTask2(prop, str) {
    fillFlowStr(str);
    var flowCount = extFlowObject.GetFlowCount();
    var commentValue = extFlowObject.getCommentValue();

    if (flowCount == 1 && commentValue == "") {
        //        new extFlowObject.selectFlowItem(0);
        var opinion = getJsonData(str, "flowHint:");
        new extFlowObject.setCommentValue(opinion);
        new extFlowObject.setCommentSelect();
    }
    if (document.getElementById("sf_opinion") && document.getElementById("sf_opinion").value != "") {
        opinion = document.getElementById("sf_opinion").value;
        new extFlowObject.setCommentValue(opinion);
    }
    extFlowObject.setselectSubmitStatus(true);
    var taskName = prop.taskName;
    Launch_FlowStr = str;
    if (taskName == "JOIN" || taskName == "STOP" || prop.taskFlowType == "4") {
        /*
         if(taskName == "STOP") {
         extFlowObject.setTaskValue("流程结束!");
         } else if(taskName == "JOIN") {
         extFlowObject.setTaskValue("审批完成,等待其他人员审批结束!");

         //            if(extFlowObject.GetParsonCount() <= 0) {
         extFlowObject.removeAllParsonItem();
         document.getElementById("toParson").parentNode.previousSibling.innerHTML = "等待办理人:";
         var users = getParallelUsers(document.getElementById("sf_actID").value);
         if(users.indexOf("usernames") >= 0) {
         var userArr;
         eval("userArr = " + users);
         var realnames = userArr[0].realnames.split(";");
         for(var i = 0; i < realnames.length; i++)
         new extFlowObject.addparsonItem("", realnames[i]);
         } else {
         new extFlowObject.addparsonItem("", "");
         }
         //            }
         }
         */
        return str;
    }
    var ret = getReturnProp2(prop, str);
    return ret;
}

function processMultipleTask2(prop, str) {
    if (prop.taskFlowType == "2") {
        // 并流
        Launch_ParallelParticipants = "";
        Launch_FlowStr = str;
        SFParallelFlow();
        if (!Launch_Continue) {
            if (Error_Msg != "") {
                alert(Error_Msg);
            }

            new extFlowObject.selectFlowItem(-1);
            extFlowObject.setselectSubmitStatus(false);
            return "";
        }
        if (Launch_ParallelParticipants == "") {
            /*
             var tempFlowStr = flowsSelectDlg(str);
             var parallelStr;
             eval("parallelStr = " + tempFlowStr);
             var usernames = "";
             var realnames = "";
             var pCount = 0;
             for(var i = 0; i < parallelStr.length; i++) {
             if(parallelStr[i].taskName == "SPLIT")
             continue
             if(Launch_ParallelParticipants == "") {
             Launch_ParallelParticipants = parallelStr[i].flowCode + ":" + parallelStr[i].usernames;
             } else {
             Launch_ParallelParticipants += "\\" + parallelStr[i].flowCode + ":" + parallelStr[i].usernames;
             }
             if(Launch_ParallelGroups == "") {
             Launch_ParallelGroups = parallelStr[i].groupName;
             } else {
             Launch_ParallelGroups += ";" + parallelStr[i].groupName;
             }
             if(usernames == "") {
             usernames = parallelStr[i].usernames;
             realnames = parallelStr[i].realnames;
             } else {
             usernames += ";" + parallelStr[i].usernames;
             realnames += ";" + parallelStr[i].realnames;
             }
             pCount++;
             }
             if(pCount > 1)
             multiParallelFlow = true;
             if(tempFlowStr.charAt[0] == '[')
             tempFlowStr = tempFlowStr.substring(1, tempFlowStr.length - 1);
             fillMultiGroupStr(Launch_ParallelGroups, "");
             fillParticipants("{usernames:'" + usernames + "',realnames:'" + realnames + "'}");
             //            doGroupSelect();
             new extFlowObject.setselectSubmitStatus(true);
             return tempFlowStr;
             } else {
             var flowStr;
             if(Launch_ParallelParticipants.indexOf("\\") > 0) {
             multiParallelFlow = true;
             flowStr = parallelFlowApi(Launch_ParallelParticipants, str);
             fillMultiGroupStr(Launch_ParallelGroups, "");
             var pArr = Launch_ParallelParticipants.split("\\");
             var gArr = Launch_ParallelGroups.split(";");
             var usernames = "";
             var realnames = "";
             for(var i = 0; i < pArr.length; i++) {
             var pContent = pArr[i];
             var pList = pContent.split(":");
             if(usernames == "") {
             usernames = pList[1];
             realnames = getRealname(pList[1]) + "/" + gArr[i];
             } else {
             usernames += ";" + pList[1];
             realnames += ";" + getRealname(pList[1]) + "/" + gArr[i];
             }
             }
             fillParticipants("{usernames:'" + usernames + "',realnames:'" + realnames + "'}");
             new extFlowObject.setselectSubmitStatus(true);
             return flowStr;
             } else {
             flowStr = parallelFlowApi(Launch_ParallelParticipants, str);
             var tempStr = searchFlowStr(flowStr, "username");

             fillMultiGroupStr(Launch_ParallelGroups, tempStr);
             doGroupSelect();
             }
             return flowStr;
             */
            var flowStr = flowsSelectDlg(str);
            if (flowStr == "") {
                return "";
            }
            var tempStr = searchFlowStr(flowStr, "username");
            var realnames = getJsonData(tempStr, "realnames:")
            var realArr = realnames.split(";");
            if (realArr.length <= 1) {
                var names = realnames.split(",");
                var nameArr = names[0].split("/");
                fillMultiGroupStr(nameArr[1], tempStr);
            } else {
                var groups = ""
                for (var i = 0; i < realArr.length; i++) {
                    var realname = realArr[i];
                    var nameArr = realname.split("/");
                    if (groups == "")
                        groups = nameArr[1];
                    else
                        groups += ";" + nameArr[1];
                }
                fillMultiGroupStr(groups, tempStr);
            }
            fillParticipants(tempStr);
//            if (doGroupSelect() == "")
//                return "";
            var opinion = getJsonData(flowStr, "flowHint:");
            new extFlowObject.setCommentValue(opinion);
            extFlowObject.setselectSubmitStatus(true);
            return flowStr;
        } else {
            var flowStr = parallelFlowApi(Launch_ParallelParticipants, str);
            var tempStr = searchFlowStr(flowStr, "username");

            fillMultiGroupStr(Launch_ParallelGroups, tempStr);
            if (doGroupSelect() == "")
                return "";
            return flowStr;
        }
    } else if (prop.taskFlowType == "3") {
        // 辅流
        Launch_AssistFlows = "";
        Launch_AssistGroups = "";
        Launch_AssistParticipants = "";
        SFQueryAssistFlow();
        if (!Launch_Continue) {
            if (Error_Msg != "")
                alert(Error_Msg);
            return "";
        }
        if (Launch_AssistGroups == "") {
            return flowsSelectDlg(str);
        } else {
            return assistFlowApi(Launch_AssistFlows, Launch_AssistGroups, Launch_AssistParticipants, str);
        }
    } else {
        alert("Task Flow Type Error!");
        return "";
    }
}

function getReturnProp2(flowProp) {
    var participants;
    var names;
    var str;
    setPlusGroup = false;
    setHandlerGroup = false;
    Launch_NextGroup = flowProp.groupName;
    if (needGroupSelected(flowProp.groupName) && Launch_ParallelGroups == "" && !(flowProp.flowType == "2" && flowProp.taskCycleType == "1")) {
        var groupMask;
        Launch_HandleGroup = "";
        SFQueryGroup();
        if (!Launch_Continue) {
            return "";
        }
        if(Launch_HandleGroup.indexOf("*") >= 0) {
            groupMask = Launch_HandleGroup;
            flowProp.groupName = Launch_HandleGroup;
            groupChange = Launch_HandleGroup;
            Launch_HandleGroup = "";
        } else if (Launch_HandleGroup != "" && Launch_HandleGroup.indexOf(";") < 0) {
            groupMask = Launch_HandleGroup;
        } else {
            if (flowProp.groupName.indexOf("*") >= 0) {
                if (saveGroup == "") {
                    if(Launch_HandleGroup.indexOf(";") >= 0) {
                        groupMask = Launch_HandleGroup;
                    } else {
//                        saveGroup = checkGroupMask(sf_project, flowProp.groupName, flowProp.roleName);
//                        groupMask = saveGroup;
                        groupMask = flowProp.groupName;
                    }
                }
            } else
                groupMask = flowProp.groupName;
        }
        if (groupMask == "")
            return "";
        if (flowProp.groupName.indexOf("+") >= 0) {
            setPlusGroup = true;
            plusGroup = groupMask;
            document.getElementById("sf_setPlusGroup").value = "1";
        } else if (flowProp.groupName.indexOf("*") >= 0 && Launch_GroupSet) {
            setHandlerGroup = true;
            handlerGroup = groupMask;
            document.getElementById("sf_setHandlerGroup").value = "1";
            if(groupMask.indexOf(";") < 0 || groupMask.indexOf(",") < 0) {
                document.getElementById("sf_handlerGroup").value = groupMask;
                document.getElementById("sf_handlerStatus").value = "[{sf_handler:'" + document.getElementById("sf_handler").value +
                    "', sf_handlerGroup:'" + groupMask + "', sf_plusGroup:'" +
                    document.getElementById("sf_plusGroup").value + "'}]";
            }
        }
        flowProp.groupName = groupMask;
        Launch_NextGroup = flowProp.groupName;

        if (flowProp.groupName.indexOf(";") < 0 && flowProp.groupName.indexOf(",") < 0
                && flowProp.groupName.indexOf("*") < 0 && flowProp.groupName.indexOf("+") < 0) {
            participants = getGroupsUsersNames(sf_project, flowProp.groupName, flowProp.roleName);
            if (participants == "")
                return "";
            eval("names = " + participants);
            flowProp.realnames = names[0].realnames;
            flowProp.usernames = names[0].usernames;
        }
        var tempStr = "[" + constructFlow(flowProp) + "]";
        fillGroupStr(tempStr);
        if (flowProp.groupName.indexOf(";") < 0 && flowProp.groupName.indexOf(",") < 0
                && flowProp.groupName.indexOf("*") < 0 && flowProp.groupName.indexOf("+") < 0) {
            doGroupSelect();
        }
        if(needGroupSelected(flowProp.groupName) == 2 || (extFlowObject.GetDepartmentCount() > 0 && extFlowObject.GetParsonCount() == 0))
            extFlowObject.setselectSubmitStatus(false);  
        return "";
    }

    str = "[" + constructFlow(flowProp) + "]";
    if (flowProp.groupName.indexOf(";") < 0 && flowProp.groupName.indexOf(",") < 0 && flowProp.groupName.indexOf("+") < 0) {
        var dCount = extFlowObject.GetDepartmentCount();
        if (dCount <= 0) {
            fillGroupStr(str);
            if (doGroupSelect() == "")
                return "";
            if (cycleStr != "")
                return cycleStr;
            else
                return str;
        } else {
        }
    } else {
    }

    var cycle = false;
    switch (flowProp.flowType) {
        case '2':
            if (flowProp.taskFlowType == "3") {
                alert("辅流第一个任务不能是会签, 请通知系统管理员！");
                break;
            } else {
                if (flowProp.taskCtlType == "2") {
                    if (flowProp.taskCycleType == "1") {
                        var level = getCharCount(flowProp.groupName, '.');
                        var searchGroup = "";
                        for (var i = 0; i < level; i++) {
                            if (searchGroup != "")
                                searchGroup += ".";
                            searchGroup += "*";
                        }
                        var namesStr = getGroupsUsersNames(sf_project, searchGroup, flowProp.roleName);
                        if(namesStr == "")
                            return "";
                        var groupNames;
                        eval("groupNames = " + namesStr);
                        Launch_CycleQueryUsers = groupNames[0].usernames;
                    } else {
                        Launch_CycleQueryUsers = flowProp.usernames;
                    }
                    Launch_IsEndCycleView = false;
                    if (flowProp.flowType == "2") {
                        Launch_CycleQureyType = "1";
                    } else {
                        Launch_CycleQueryType = "0";
                    }
                    Launch_CycleType = flowProp.taskCycleType;
                    //                    Launch_CycleUsers = "";
                    SFQueryCycleReview();
                    if (!Launch_Continue) {
                        if (Error_Msg != "")
                            alert(Error_Msg);
                        return "";
                    }
                    if (!Launch_IsEndCycleView) {
                        if (Launch_CycleUsers != "") {
                            participants = "[{realnames:'" + getCycleRealnames(Launch_CycleUsers, flowProp.roleName) + "',usernames:'"
                                    + Launch_CycleUsers + "'}]";
                            cycle = true;
                        } else if (flowProp.taskCycleType == "0" || flowProp.taskCycleType == "") {
                            if (!haveCycleUser) {
                                participants = selectCycleUsers(sf_project, flowProp.groupName, flowProp.roleName);
                            } else
                                participants = "";
                            if (participants == "") {
                                Launch_CycleUsers = "";
                                new extFlowObject.setselectSubmitStatus(false);
                                /*                                do_selectCancel();
                                 extFlowObject.close();
                                 */
                                haveCycleUser = false;
                                return "";
                            } else {
                                cycle = true;
                            }
                        } else {
                            var checkGroup;
                            if (flowProp.groupName.indexOf(".") < 0)
                                checkGroup = "*";
                            else {
                                checkGroup = flowProp.groupName.substring(0, flowProp.groupName.lastIndexOf(".") + 1)
                                        + "*";
                            }
//                            var cycleGroups = groupSelected(sf_project, checkGroup, flowProp.roleName, "1");
//                            participants = getGroupsUsersNames(sf_project, cycleGroups, flowProp.roleName);
                            participants = getGroupCycleUsers(sf_project, checkGroup, flowProp.roleName);
                            if(participants == "") {
                                new extFlowObject.setselectSubmitStatus(false);
                                return;
                            }
                            eval("names = " + participants);
                            flowProp.usernames = names[0].usernames.split(";")[0];
                            flowProp.realnames = names[0].realnames.split(";")[0];
                            flowProp.groupName = flowProp.realnames.split("/")[1];
                            var tempFlowStr = constructFlow(flowProp);
                            new extFlowObject.setSelectFlowValue(tempFlowStr);                            
                            clearGroupSelect();
                            fillGroupStr("[" + tempFlowStr + "]");
                        }
                        if (participants != "") {
                            eval("names = " + participants);
                            flowProp.cycleQty = getCharCount(names[0].usernames, ';');
                            flowProp.cycleUser = names[0].usernames;
                            Launch_CycleUsers = flowProp.cycleUser;
                            flowProp.cycleType = flowProp.taskCycleType;
                            var count = names[0].realnames.indexOf(";");
                            if (count > 0)
                                flowProp.realnames = names[0].realnames.substr(0, count);
                            else
                                flowProp.realnames = names[0].realnames;
                            count = names[0].usernames.indexOf(";");
                            var cycUsers;
                            if (count > 0)
                                cycUsers = names[0].usernames.substr(0, count);
                            else
                                cycUsers = names[0].usernames;
                            cycUsers = cycUsers.replaceAll(",", ";");
                            if (count > 0)
                                flowProp.usernames = cycUsers;
                            else
                                flowProp.usernames = cycUsers;
                            cycle = true;
                        }
                        break;
                    }
                }
            }
        // 设定出错, 比如说设了预选会签者?s?]有设会签, 所以当一般处理
        case '1': default:
        if (flowProp.flowProp == "2") {
            // 辅流
            participants = selectParticipantDlg(str, "", "1");
        } else {
            Launch_DistributeUser = "";
            SFQueryDistribute();
            if (!Launch_Continue) {
                if (Error_Msg != "")
                    alert(Error_Msg);
                return "";
            }
            if(Launch_DistributeUser == "") {
                if(flowProp.usernames == "") {
                    participants = getUsers(flowProp.groupName,  flowProp.roleName);
                } else if(flowProp.usernames.indexOf(",") >= 0) {
                    participants = "[{realnames:'" + flowProp.realnames + "', usernames:'"
                        + flowProp.usernames + "'}]";
                } else
                    participants = "[{realnames:'" + flowProp.realnames + "', usernames:'"
                        + flowProp.usernames + "'}]";
            } else if(Launch_DistributeUser.indexOf("-") == 0) {
                var users;
                if(flowProp.usernames == "") {
                    users = getUsers(flowProp.groupName,  flowProp.roleName);
                } else {
                    users = flowProp.usernames;
                }
                eval("usersArr = " + users);
                var tempUsernamesArr = usersArr[0].usernames.replaceAll(",",";").split(";");
                var tempRealnamesArr = usersArr[0].realnames.replaceAll(",",";").split(";");
                var tempUsernames = "";
                var tempRealnames = "";
                var tempDisUsersArr = Launch_DistributeUser.substring(1).replaceAll(",",";").split(";");
                for(var j = 0; j < tempUsernamesArr.length; j++) {
                    var found = false;
                    for(var k = 0; k < tempDisUsersArr.length; k++) {
                        if(tempDisUsersArr[k].toUpperCase() == tempUsernames[j].toUpperCase()) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        if(tempUsernames == "") {
                            tempUsernames = tempUsernamesArr[j];
                            tempRealnames = tempRealnamesArr[j];
                        } else {
                            tempUsernames += "," + tempUsernamesArr[j];
                            tempRealnames += "," + tempRealnamesArr[j];
                        }
                    }
                }
                if(tempUsernames.indexOf(",") >= 0) {
                    participants = selectParticipantDlg("[{taskName:'" + flowProp.taskName + "', groupName:'" +
                            flowProp.groupName + "', roleName:'" + flowProp.roleName + "', realnames:'" + tempRealnames +
                            "', usernames:'" + tempUsernames + "', flowProp:'0'}]", "", "0");
                } else if(tempUsernames == "" && !disError) {
                    alert("分发用户为空, 请检查接口程序与用户权限设定!");
                    disError = true;
                    return "";
                } else {
                    participants = "[{realnames:'" + tempRealnames + "', usernames:'"
                        + tempUsernames + "'}]";
                }
            } else {
                Launch_DistributeUser = Launch_DistributeUser.replaceAll(";", ",");
                var realName = "";
                var userArr = Launch_DistributeUser.split(",");
                for (var ri = 0; ri < userArr.length; ri++) {
                    if (realName == "")
                        realName = getRealname(userArr[ri]) + "/" + flowProp.groupName;
                    else
                        realName += "," + getRealname(userArr[ri]) + "/" + flowProp.groupName;
                }
                participants = "[{realnames:'" + realName + "', usernames:'"
                        + Launch_DistributeUser + "'}]";
            }
        }
        if (participants != "") {
            eval("names = " + participants);
            flowProp.realnames = names[0].realnames;
            flowProp.usernames = names[0].usernames;
        }
        break;
        case '0':
            break;
        case '3':
            var handler = getHandler(sf_handler, flowProp.usernames, ",");
            if (flowProp.groupName == document.getElementById("sf_group").value && flowProp.roleName == document.getElementById("sf_role").value
                    && document.getElementById("sf_taskctl").value == "1") {
                flowProp.realnames = document.getElementById("sf_user").value + "/" + flowProp.groupName;
                flowProp.usernames = document.getElementById("sf_loginName").value;
            } else if (handler == "" || handler.indexOf(";") >= 0) {
                if (flowProp.usernames.indexOf(",") >= 0 || flowProp.usernames.indexOf(";") >= 0) {
                    var handlerName = getHandlerName(document.getElementById("sf_caseID").value, flowProp.groupName, flowProp.roleName);
                    var handlerRealnames;
                    if(handlerName == "" || !inList(sf_handler, handlerName, ";")) {
                        var usersStr;
                        if(handlerName != "") {
                            handlerRealnames = getRealname(handlerName, flowProp, ";") + "/" + flowProp.groupName;
                            usersStr = "[{taskName:'" + flowProp.taskName + "', groupName:'" + flowProp.groupName +
                                      "', roleName:'" + flowProp.roleName + "', ";
                            usersStr += "realnames:'" + handlerRealnames + "', usernames:'" + handlerName + "', flowProp:'0'}]";
                        } else {
                            usersStr = getUsers(flowProp.groupName,  flowProp.roleName);
                        }

                        eval("names = " + usersStr);
                        flowProp.realnames = names[0].realnames.replaceAll(";", ",");
                        flowProp.usernames = names[0].usernames.replaceAll(";", ",");
                    } else {
                        handlerRealnames = getHandlerRealnames(handlerName, flowProp, ";");
                        flowProp.realnames = handlerRealnames;
                        flowProp.usernames = handlerName;
                    }
                } else {
                    participants = getUsers(flowProp.groupName,  flowProp.roleName);
                    eval("names = " + participants);
                    flowProp.realnames = names[0].realnames.replaceAll(";", ",");
                    flowProp.usernames = names[0].usernames.replaceAll(";", ",");
                }
            } else {
                flowProp.realnames = getRealname(handler) + "/" + flowProp.groupName;
                flowProp.usernames = handler;
            }
            break;
    }
    if (participants == "")
        return "";

    if (flowProp.usernames == "") {
        alert("组别:" + flowProp.groupName + ",角色:" + flowProp.roleName + " 没有设置用户, 请通知管理员!");
        new extFlowObject.setselectSubmitStatus(false);
        return "";
    } else {
        new extFlowObject.setselectSubmitStatus(true);
        var pCount = extFlowObject.GetParsonCount();
        if (pCount <= 0) {
            var tempStr = constructFlow(flowProp);
            if (tempStr.charAt(0) != '[')
                tempStr = "[" + tempStr + "]";
            fillParticipantsStr(tempStr);
            //alert(flowProp.usernames);
            if (flowProp.usernames.indexOf(";") >= 0) {
                doParticipantsSelect();
            } else if (flowProp.usernames.indexOf(",") < 0) {
                doParticipantsSelect();
            }
        } else if (pCount == 1) {
            doParticipantsSelect();
        }
    }
    var retFlowStr = constructFlow(flowProp);
    if (cycle)
        cycleStr = retFlowStr;
    return retFlowStr;
}

function fillFlowStr(flowString) {
    var count = extFlowObject.GetFlowCount();
    if (count != 0) return;
    var prop;
    eval("prop = " + flowString);

    var flowsStr = "";
    if (flowString.charAt(0) == '[') {
        flowsStr = flowString.substring(1, flowString.length - 1);
    } else {
        flowsStr = flowString;
    }

    var flows = "";
    if (prop.length && prop.length == 1) {
        new extFlowObject.addFlowItem(flowsStr, getJsonData(flowsStr, "flowDesc:"), getJsonData(flowsStr, "flowDesc:"));
        new extFlowObject.selectFlowItem(0);
    } else {
        if ((prop[0] && (prop[0].taskName == "SPLIT" || prop[0].taskName == "JOIN" || (prop[0].taskName == "STOP" && prop[1].condFlow != "1"))) && prop[0].flowDesc.indexOf("同意 - ") != 0) {
            new extFlowObject.addFlowItem(flowsStr, getJsonData(flowsStr, "flowDesc:"), getJsonData(flowsStr, "flowDesc:"));
        } else {
            flows = getFlowStr(flowsStr);
            SFQueryConditionalFlow();
            for (var i = 0; i < flows.length; i++) {
                var flowCode = getJsonData(flows[i], "flowCode:");
                if (Launch_Code_Bypass != "") {
                    if (inList(Launch_Code_Bypass, flowCode, ";"))
                        continue;
                }

                if (flows[i].length != 0) {
                    var split = flows[i].substring(0, 1);
                    if (split == "[") {
                        flows[i] = flows[i].substring(1, flows[i].length);
                    }

                    split = flows[i].substring(flows[i].length - 1, flows[i].length);
                    if (split == "]") {
                        flows[i] = flows[i].substring(0, flows[i].length - 1);
                    }
                }
                new extFlowObject.addFlowItem(flows[i], getJsonData(flows[i], "flowDesc:"), getJsonData(flows[i], "flowDesc:"));
                if (flows.length == 1) {
                    new extFlowObject.selectFlowItem(0);
                }
                if (Launch_Code == flowCode) {
                    new extFlowObject.selectFlowItem(i);
                    flowsStr = doFlowSelect();
                }
            }
        }
    }

    //    if(extFlowObject.getSelectFlowValue() == "") {
    //        extFlowObject.selectFlowItem(0);
    //    }
    return flowsStr;
}

function checkParallelFlow(flowsStr) {
    var prop;
    eval("prop = " + flowsStr);
    if (prop.length == 1) {

    }
}

function fillMultiGroupStr(groups, flowStr) {
    var dCount = extFlowObject.GetDepartmentCount();
    if (dCount != 0) return;

    var groupArr = groups.split(";");

    for (var i = 0; i < groupArr.length; i++) {
        new extFlowObject.addDepartmentItem(flowStr, groupArr[i], groupArr[i]);
    }

    new extFlowObject.selectAllDepartmentItem(1);
    //    if(extFlowObject.getSelectFlowValue() == "") {
    //        extFlowObject.selectFlowItem(0);
    //    }
}

function fillGroupStr(flowString) {
    var dCount = extFlowObject.GetDepartmentCount();
    if (dCount != 0) return;
    var prop;
    eval("prop = " + flowString);
    var flowsStr = "";
    if (flowString.charAt(0) == '[') {
        flowsStr = flowString.substring(1, flowString.length - 1);
    } else {
        flowsStr = flowString;
    }

    var flows = "";
    if (prop.length == 1) {
        flows = flowsStr;
        var text;
        if(groupChange != "")
            text = groupChange;
        else
            text = getJsonData(flows, "groupName:");
        if ((text.indexOf("*") < 0) && (text.indexOf(";") < 0) && (text.indexOf(",") < 0)) {
            new extFlowObject.addDepartmentItem(flows, text, text);
            new extFlowObject.selectDepartmentItem(0);
        } else {
            var split = "";
            if (Launch_HandleGroup.indexOf(";") >= 0)
                text = Launch_HandleGroup;
            if ((text.indexOf("*") != -1) && (text.indexOf("***") < 0) && Launch_HandleGroup == "") {
                text = getServerMaskGroups(sf_project, text, getJsonData(flows, "roleName:"));
                split = ";";
            } else if (text.indexOf(";") != -1) {
                split = ";";
            } else if (text.indexOf(",") != -1) {
                split = ",";
            }
            var groupArr = text.split(split);
            for (var i = 0; i < groupArr.length; i++) {
                new extFlowObject.addDepartmentItem(flows, groupArr[i], groupArr[i]);
            }
            if (groupArr.length == 1)
                new extFlowObject.selectDepartmentItem(0);
        }
    }
    //    if(extFlowObject.getSelectFlowValue() == "") {
    //        extFlowObject.selectFlowItem(0);
    //    }
}

function fillParticipantsStr(flowString) {
    var pCount = extFlowObject.GetParsonCount();
    if (pCount < 0) return;

    var prop;
    eval("prop = " + flowString);
    var flowsStr = "";
    if (flowString.charAt(0) == '[') {
        flowsStr = flowString.substring(1, flowString.length - 1);
    } else {
        flowsStr = flowString;
    }

    var flows = "";

    if (prop.length == 1) {

        flows = flowsStr;
        var text = getJsonData(flows, "realnames:");
        var value = getJsonData(flows, "usernames:");

        if (text.indexOf(";") < 0 && text.indexOf(",") < 0) {
            new extFlowObject.addparsonItem(value, text);
            extFlowObject.selectparsonItem(0);
            extFlowObject.selectAllParsonItem(1);
        } else if (value.indexOf(";") >= 0) {
            var textArr = text.split(";");
            var valueArr = value.split(";");
            for (var i = 0; i < valueArr.length; i++) {
                new extFlowObject.addparsonItem(valueArr[i], textArr[i]);
            }
            extFlowObject.selectAllParsonItem(1);
        } else {
            var textArr = text.split(",");
            var valueArr = value.split(",");
            for (var i = 0; i < valueArr.length; i++) {
                new extFlowObject.addparsonItem(valueArr[i], textArr[i]);
                extFlowObject.selectparsonItem(0);
            }
            extFlowObject.selectAllParsonItem(2);
        }
    }
    if (document.getElementById("sf_trayType").value == "0" && getJsonData(flowString, "flowCode:").indexOf("@") > 0) {
        extFlowObject.selectAllParsonItem(1);
    }
}

function fillParticipants(flowString) {
    var prop;
    eval("prop = " + flowString);
    var flowsStr = "";
    if (flowString.charAt(0) == '[') {
        flowsStr = flowString.substring(1, flowString.length - 1);
    } else {
        flowsStr = flowString;
    }

    var flows = "";

    flows = flowsStr;
    var text = getJsonData(flows, "realnames:");
    var value = getJsonData(flows, "usernames:");

    if (text.indexOf(";") < 0 && text.indexOf(",") < 0) {
        new extFlowObject.addparsonItem(value, text);
        extFlowObject.selectparsonItem(0);
        extFlowObject.selectAllParsonItem(1);
    } else if (value.indexOf(";") >= 0) {
        var textArr = text.split(";");
        var valueArr = value.split(";");
        for (var i = 0; i < valueArr.length; i++) {
            new extFlowObject.addparsonItem(valueArr[i], textArr[i]);
        }
        extFlowObject.selectAllParsonItem(1);
    } else {
        var textArr = text.split(",");
        var valueArr = value.split(",");
        for (var i = 0; i < valueArr.length; i++) {
            new extFlowObject.addparsonItem(valueArr[i], textArr[i]);
            extFlowObject.selectparsonItem(0);
        }
        extFlowObject.selectAllParsonItem(2);
    }
    if (document.getElementById("sf_trayType").value == "0") {
        extFlowObject.selectAllParsonItem(1);
    }
}

function clearFlowSelect() {
    new extFlowObject.removeAllFlowItem();
}

function clearGroupSelect() {
    new extFlowObject.removeAlldepartmentItem();
}

function clearParticipantsSelect() {
    new extFlowObject.removeAllParsonItem();
}

function do_selectFinish() {
    extFlowObject.setselectSubmitStatus(false);
    if (multiParallelFlow) {
        do_multiSelectFinish();
        return;
    }
    if (extFlowObject.getSelectFlowValue() == "") {
        extFlowObject.selectFlowItem(0);
    }
    var flowStr = extFlowObject.getSelectFlowValue();
    if (document.getElementById("sf_trayType").value == "0" && getJsonData(flowStr, "taskName:") != "SPLIT") {
        extFlowObject.selectAllParsonItem();
        var usernames = "";
        var realnames = "";
        for (var i = 0; i < extFlowObject.getSelectedParsonCount(); i++) {
            if (usernames == "") {
                usernames = extFlowObject.getSelectparsonValue(i, 1);
                realnames = extFlowObject.getSelectparsonValue(i, 2);
            } else {
                usernames += "," + extFlowObject.getSelectparsonValue(i, 1);
                realnames += "," + extFlowObject.getSelectparsonValue(i, 2);
            }
        }
        if (usernames != "") {
            var activeStr = searchFlowStr(flowStr, "username");
            var prop;

            eval("prop = " + activeStr);
            prop.usernames = usernames;
            prop.realnames = realnames;
            Launch_SelectUsers = realnames;
            var tempStr = constructFlow(prop);
            flowStr = flowStr.replace(activeStr, tempStr);
        }
        if (extFlowObject.getCommentValue() != "") {
            document.getElementById("sf_opinion").value = extFlowObject.getCommentValue();
        } else {
            document.getElementById("sf_opinion").value = getJsonData(flowStr, "flowHint:");
        }
        if (document.getElementById("sf_opinion").value.len() > 128) {
            Launch_Continue = false;
            alert("填写意见超出长度限制！");
            extFlowObject.setselectSubmitStatus(true);
            return;
        }
        addInfoWin.hide();
        if (cycleStr && cycleStr != "")
            flowStr = cycleStr;
        do_Div_Complete_End(flowStr);
        return;
    }
    var index = flowStr.indexOf("groupName:");

    if (index >= 0) {
        var usernames = "";
        var realnames = "";

        for (var i = 0; i < extFlowObject.getSelectedParsonCount(); i++) {
            if (usernames == "") {
                usernames = extFlowObject.getSelectparsonValue(i, 1);
                realnames = extFlowObject.getSelectparsonValue(i, 2);
            } else {
                usernames += ";" + extFlowObject.getSelectparsonValue(i, 1);
                realnames += ";" + extFlowObject.getSelectparsonValue(i, 2);
            }
        }

        var activeStr = searchFlowStr(flowStr, "username");
        var prop;

        eval("prop = " + activeStr);
        if (prop.groupName.indexOf("*") >= 0) {
            var tempGroup = extFlowObject.getSelectDepartmentText();
            if (tempGroup.indexOf(";") < 0)
                prop.groupName = tempGroup;
            if(Launch_GroupSet) {
                if(Launch_HandleGroup == "") {
                    Launch_HandleGroup = tempGroup;
                    document.getElementById("sf_handlerGroup").value = tempGroup;
                    document.getElementById("sf_handlerStatus").value = "[{sf_handler:'" + document.getElementById("sf_handler").value +
                        "', sf_handlerGroup:'" + tempGroup + "', sf_plusGroup:'" +
                        document.getElementById("sf_plusGroup").value + "'}]";
                } else if(Launch_HandleGroup.indexOf(";") >= 0 || Launch_HandleGroup.indexOf(",") >= 0) {
                    document.getElementById("sf_handlerGroup").value = tempGroup;
                    document.getElementById("sf_handlerStatus").value = "[{sf_handler:'" + document.getElementById("sf_handler").value +
                        "', sf_handlerGroup:'" + tempGroup + "', sf_plusGroup:'" +
                        document.getElementById("sf_plusGroup").value + "'}]";
                }
            }
        }

        prop.usernames = usernames;
        prop.realnames = realnames;
        Launch_SelectUsers = realnames;

        var tempStr = constructFlow(prop);

        flowStr = flowStr.replace(activeStr, tempStr);
    }

    var tempArray;
    eval("tempArray = " + flowStr);

    var selProp = getFlowPropInArray(tempArray);
    Launch_Code = selProp.flowCode;
    if (extFlowObject.getCommentValue() != "") {
        document.getElementById("sf_opinion").value = extFlowObject.getCommentValue();
    } else {
        document.getElementById("sf_opinion").value = getJsonData(flowStr, "flowHint:");
    }
    if (document.getElementById("sf_opinion").value.len() > 128) {
        Launch_Continue = false;
        alert("填写意见超出长度限制！")
        extFlowObject.setselectSubmitStatus(true);
        return;
    }
    addInfoWin.hide();
    if (cycleStr != "")
        flowStr = cycleStr;
    do_Div_Complete_End(flowStr);

    return;
}

function do_multiSelectFinish() {
    flowStr = extFlowObject.getSelectFlowValue();
    if (flowStr.charAt[0] != '[')
        flowStr = "[" + flowStr + "]";
    var index = flowStr.indexOf("groupName:");

    var pFlowStr;
    eval("pFlowStr = " + flowStr);
    for (var i = 0; i < pFlowStr.length; i++) {
        var prop = pFlowStr[i];
        if (prop.taskName == "SPLIT")
            continue;
        var selUsername;
        var selRealname;
        for (var j = 0; j < extFlowObject.getSelectedParsonCount(); j++) {
            selRealname = extFlowObject.getSelectparsonValue(j, 2);
            if (prop.realnames.indexOf(selRealname) >= 0) {
                flowStr = flowStr.replace(prop.realnames, selRealname);
                flowStr = flowStr.replace(prop.usernames, extFlowObject.getSelectparsonValue(j, 1));
                break;
            }
        }
    }

    if (extFlowObject.getCommentValue() != "")
        document.getElementById("sf_opinion").value = extFlowObject.getCommentValue();
    else
        document.getElementById("sf_opinion").value = getJsonData(flowStr, "flowHint:");

    addInfoWin.hide();

    if (cycleStr != "")
        flowStr = cycleStr;
    do_Div_Complete_End(flowStr);

    return;
}

function do_selectCancel() {
    clearFlowSelect();
    clearGroupSelect();
    clearParticipantsSelect();
    new extFlowObject.setCommentValue("");
}

function doFlowSelect() {
    clearGroupSelect();
    clearParticipantsSelect();
    cycleStr = "";
    Launch_ParallelGroups = "";

    var flowStr = extFlowObject.getSelectFlowValue();

    var opinion = getJsonData(flowStr, "flowHint:");
    new extFlowObject.setCommentValue(opinion);
    if (flowStr.charAt(0) != '[')
        flowStr = "[" + flowStr + "]";
    var prop;

    eval("prop = " + flowStr);
    Launch_Code = prop[0].flowCode;

    return processTasks2(prop, flowStr);
}

function doGroupSelect() {
    clearParticipantsSelect();

    var flowStr = extFlowObject.getSelectDepartmentValue();
    var groupName = getJsonData(flowStr, "groupName:");
    var prop = null;

    if ((groupName.indexOf("*") >= 0 && groupName.indexOf("***") < 0 && Launch_ParallelGroups == "") || groupName.indexOf(";") >= 0) {
        var group = extFlowObject.getSelectDepartmentText();
        var participants = getGroupsUsersNames(sf_project, group,
                getJsonData(flowStr, "roleName:"));
        var prop;
        eval("prop = " + flowStr);
        prop.groupName = group;
        var prop2;
        eval("prop2 = " + participants);
        prop.usernames = prop2[0].usernames;
        prop.realnames = prop2[0].realnames;
        flowStr = constructFlow(prop);
    }

    if (flowStr.charAt(0) != '[') flowStr = "[" + flowStr + "]";
    try {
        eval("prop = " + flowStr);
    } catch(e) {
        return "";
    }
    var ret = processTasks2(prop, flowStr);
    return ret;
}

function doParticipantsSelect() {
    flowStr = extFlowObject.getSelectparsonValue(0, 1);
    return flowStr;
}

String.prototype.len = function() {
    return this.replace(/[^\x00-\xff]/g, "**").length
} 

function getUsers(groups, role) {
    var cycleGroupUserURL = "/servlet/com.sino.sinoflow.servlet.GetCycleGroupUsers?sf_project='"
        + sf_project + "'&sf_groups='"
        + groups.replaceAll("+","%2B") + "'&sf_role='" + role + "'";
    makeRequest(cycleGroupUserURL, ajaxFunction);
    return ajaxReturn;
}
