var sync = true;
var sf_handler_group = "";
var sf_plus_group = "";
var sf_handler = "";
var sf_project = "";
var sf_group = "";
var ajaxReturn = "";
var flowArray;
var assignedFlowArray;
var flowStrArray = new Array();
var assignedStrArray = new Array();

var VALID_NULL_MASK = 0x01;
var VALID_PATTERN_MASK = 0x02;
var VALID_SIZE_MASK = 0x04;

var Launch_FlowStr = "";
var processReviewUsers = false;
var setReviewStatus = false;
var setCycleUsers = false;

String.prototype.replaceAll = stringReplaceAll;
function stringReplaceAll(AFindText, ARepText) {
    var raRegExp = new RegExp(AFindText.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g, "\\$1"), "ig");
    return this.replace(raRegExp, ARepText);
}

function init_groups() {
    sf_handler_group = document.getElementById("sf_handlerGroup").value;
    sf_plus_group = document.getElementById("sf_plusGroup").value;
    sf_handler = document.getElementById("sf_handler").value;
    sf_project = document.getElementById("sf_project").value;
    sf_group = getMaskGroup(document.getElementById("sf_group").value, sf_handler_group, sf_plus_group);

    save_reviewQty = document.getElementById("sf_reviewQty").value;
    save_reviewType = document.getElementById("sf_reviewType").value;
    save_reviewUsers = document.getElementById("sf_reviewUsers").value;
    save_cycleQty = document.getElementById("sf_cycleQty").value;
    save_cycelType = document.getElementById("sf_cycleType").value;
    save_cycleUsers = document.getElementById("sf_cycleUsers").value;
}

function inList(src, compData, separator) {
    if (src.length == 0 || compData.length == 0)
        return false;
    var srcList = src.split(separator);
    for (var i = 0; i < srcList.length; i++) {
        var data = srcList[i];
        if (data.toUpperCase() == compData.toUpperCase()) {
            return true;
        }
    }
    return false;
}

function findMatchPattern(src) {
    var count = 1;
    var sPattern, cPattern;
    if (src == null)
        return null;
    if (src.charAt(0) == '[') {
        sPattern = '[';
        cPattern = ']';
    } else if (src.charAt(0) == '{') {
        sPattern = '{';
        cPattern = '}';
    } else {
        return null;
    }
    for (var i = 1; i < src.length; i++) {
        if (src.charAt(i) == cPattern)
            count--;
        if (count == 0)
            return src.substring(0, i + 1);
        if (src.charAt(i) == sPattern)
            count++;
    }
    return src;
}

function getFlowStr(src) {
    if (src == null || src == "")
        return null;
    var strArray = new Array();
    var flowCount = 0;
    var str = src;
    var temp;
    while (str != "") {
        temp = findMatchPattern(str);
        if (temp == null) {
            strArray[flowCount++] = str;
            str = "";
        } else {
            strArray[flowCount++] = temp;
            if (str.length < temp.length + 2)
                str = "";
            else
                str = str.substring(temp.length + 1, str.length);
        }
    }
    return strArray;
}

function getJsonData(status, attr) {
    if (status == null || status == "" || status == "''" || status == "\"\"") {
        return "";
    }
    var count = status.indexOf("}");
    var str;
    if (count >= 0)
        str = status.substring(0, count);
    else
        str = status;
    var temp = attr + "'";
    var start = str.indexOf(temp);
    if (start < 0)
        return "";
    start += temp.length;
    var end = str.substring(start, str.length).indexOf("'");
    return str.substring(start, start + end);
}

function getHandler(handlers, users, sep) {
    var hList = handlers.split(";");
    var retUsers = "";
    for (var i = hList.length - 1; i >= 0; i--) {
        if (inList(users, hList[i], sep)) {
            //            return hList[i];
            if (retUsers != "")
                retUsers += ";";
            retUsers += hList[i];
        }
    }
    return retUsers;
}

function getHandlerRealnames(handlers, prop) {
    var retRealnames = "";
    var realnames = prop.realnames.replaceAll(",", ";");
    var users = prop.usernames.replaceAll(",", ";");
    var hList = handlers.split(";");
    var uList = users.split(";");
    var rList = realnames.split(";");
    for (var i = 0; i < hList.length; i++) {
        for (var j = 0; j < uList.length; j++) {
            if (hList[i] == uList[j]) {
                if (retRealnames != "")
                    retRealnames += ";";
                retRealnames += rList[j];
            }
        }
    }
    return retRealnames;
}

function isMask(src) {
    var left;
    var i;
    i = src.indexOf(".");
    if (i < 0) {
        if (src == "*")
            return true;
        if (src == "***")
            return true;
        if (src == "+")
            return true;
        if (src == "+++")
            return true;
    } else {
        if (i != 0)
            left = src.substring(0, i);
        if (isMask(left))
            return true;
        return isMask(src.substring(i + 1, src.length));
    }
    return false;
}

function isSelectedMask(src) {
    var left;
    var i;
    i = src.indexOf(".");
    if (i < 0) {
        if (src == "*")
            return 2;
        if (src == "+")
            return 1;
    } else {
        if (i != 0)
            left = src.substring(0, i);
        var maskType = isSelectedMask(left);
        if (maskType != 0)
            return maskType;
        return isSelectedMask(src.substring(i + 1, src.length));
    }
    return 0;
}

function getCharCount(str, seperator) {
    if (str == null || str == "")
        return 0;
    var retVal = 0;
    for (var i = 0; i < str.length; i++) {
        if (str.charAt(i) == seperator)
            retVal++;
    }
    return retVal + 1;
}

//检查是不是要求选择组别, 返回 0 -- 不要求, 1 -- 要求选择单一组别, 2 -- 要求选择多组组别
function needGroupSelected(group) {
    var i;
    i = group.indexOf(".");
    if (i < 0) {
        if (group == "+" || group == "*")
            return 2;
        if (group == "+++") {
            if (sf_plus_group == "") {
                return 2;
            } else {
                return 0;
            }
        }
        if (group == "***") {
            if (sf_handler_group == "") {
                return 1;
            } else {
                return 0;
            }
        }
        return false;
    } else {
        return Math.max(needGroupSelected(group.substring(0, i)), needGroupSelected(group.substring(i + 1, group.length)));
    }
}

function checkGroupMask(proj, group, role) {
    if (group.indexOf("*") >= 0) {
        return groupSelected(proj, group, role, "0");
    } else if (group.indexOf("+") >= 0) {
        return groupSelected(proj, group, role, "1");
    } else {
        return group;
    }
}

function getMaskGroup(maskGroup, handlerGroup, plusGroup) {
    if (maskGroup.indexOf("*") < 0 && maskGroup.indexOf("+") < 0)
        return maskGroup;
    var srcLeft, handler, plus;
    var srcCount, handlerCount, plusCount;
    srcCount = maskGroup.indexOf(".");
    handlerCount = handlerGroup.indexOf(".");
    if (handlerCount >= 0) {
        handler = handlerGroup.substring(handlerCount + 1, handlerGroup.length);
    } else {
        handler = "";
    }
    plusCount = plusGroup.indexOf(".");
    if (plusCount >= 0) {
        plus = plusGroup.substring(plusCount + 1, plusGroup.length);
    } else {
        plus = "";
    }
    if (srcCount >= 0) {
        srcLeft = maskGroup.substring(0, srcCount);
        if (srcLeft == "+++") {
            if (plusCount >= 0) {
                return plusGroup.substring(0, plusCount) + "." + getMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                        handler, plus);
            } else {
                if (sf_plus_group == "") {
                    return "+." + getMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                } else {
                    return plusGroup + "." + getMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                }
            }
        } else if (srcLeft == "***") {
            if (handlerCount >= 0) {
                return handlerGroup.substring(0, handlerCount) + "." + getMaskGroup(maskGroup.substring(srcCount + 1,
                        maskGroup.length), handler, plus);
            } else {
                if (handlerGroup == "") {
                    return "*." + getMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                } else {
                    return handlerGroup + "." + getMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                }
            }
        } else {
            return srcLeft + "." + getMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                    handler, plus);
        }
    } else {
        if (maskGroup == "+++") {
            if (plusCount >= 0) {
                return plusGroup.substring(0, plusCount);
            } else {
                if (plusGroup == "") {
                    return "+";
                } else {
                    return plusGroup;
                }
            }
        } else if (maskGroup == "***") {
            if (handlerCount >= 0) {
                return handlerGroup.substring(0, handlerCount);
            } else {
                if (handlerGroup == "") {
                    return "*";
                } else {
                    return handlerGroup;
                }
            }
        } else {
            return maskGroup;
        }
    }
}

function setMaskGroup(maskGroup, handlerGroup, plusGroup) {
    if (maskGroup.indexOf("*") < 0 && maskGroup.indexOf("+") < 0)
        return maskGroup;
    var srcLeft, handler, plus;
    var srcCount, handlerCount, plusCount;
    srcCount = maskGroup.indexOf(".");
    handlerCount = handlerGroup.indexOf(".");
    if (handlerCount >= 0) {
        handler = handlerGroup.substring(handlerCount + 1, handlerGroup.length);
    } else {
        handler = "";
    }
    plusCount = plusGroup.indexOf(".");
    if (plusCount >= 0) {
        plus = plusGroup.substring(plusCount + 1, plusGroup.length);
    } else {
        plus = "";
    }
    if (srcCount >= 0) {
        srcLeft = maskGroup.substring(0, srcCount);
        if (srcLeft == "+++" || srcLeft == "+") {
            if (plusCount >= 0) {
                return plusGroup.substring(0, plusCount) + "." + setMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                        handler, plus);
            } else {
                if (plusGroup == "") {
                    return "+." + setMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                } else {
                    return plusGroup + "." + setMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                }
            }
        } else if (srcLeft == "***" || srcLeft == "*") {
            if (handlerCount >= 0) {
                return handlerGroup.substring(0, handlerCount) + "." + setMaskGroup(maskGroup.substring(srcCount + 1,
                        maskGroup.length), handler, plus);
            } else {
                if (handlerGroup == "") {
                    return "*." + setMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                } else {
                    return handlerGroup + "." + setMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                            handler, plus);
                }
            }
        } else {
            return srcLeft + "." + setMaskGroup(maskGroup.substring(srcCount + 1, maskGroup.length),
                    handler, plus);
        }
    } else {
        if (maskGroup == "+++" || maskGroup == "+") {
            if (plusCount >= 0) {
                return plusGroup.substring(0, plusCount);
            } else {
                if (plusGroup == "") {
                    return "+";
                } else {
                    return plusGroup;
                }
            }
        } else if (maskGroup == "***" || maskGroup == "*") {
            if (handlerCount >= 0) {
                return handlerGroup.substring(0, handlerCount);
            } else {
                if (handlerGroup == "") {
                    return "*";
                } else {
                    return handlerGroup;
                }
            }
        } else {
            return maskGroup;
        }
    }
}

function needSetGroup(str) {
    var index = str.indexOf("groupName:'");
    var tempStr = str;
    while (index >= 0) {
        tempStr = tempStr.substring(index + 11, tempStr.length);
        var end = tempStr.indexOf("'");
        if (end >= 0) {
            var checkStr = tempStr.substring(tempStr, end);
            var selectedMask = isSelectedMask(checkStr);
            if (selectedMask != 0) {
                return selectedMask;
            }
        }
        index = tempStr.indexOf("groupName:'");
    }
    return 0;
}

function groupSelected(proj, group, role, multiSelected) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetUserAllGroups?sf_project='"
            + proj + "'&sf_group='" + group.replaceAll("+", "%2B")
            + "'&sf_role='" + role + "'&multiSelected=" + multiSelected;
    var popscript;
    if (multiSelected == "0") {
        popscript = "dialogWidth:300px"
                + ";dialogHeight:330px"
                + ";center:yes;status:no;scroll:no;help:no";
    } else {
        popscript = "dialogWidth:600px"
                + ";dialogHeight:330px"
                + ";center:yes;status:no;scroll:no;help:no";
    }
    return window.showModalDialog(url, null, popscript);
}

function selectGroup(projName, groupName, roleName) {
    var url;
    //    url = "/servlet/com.sino.sinoflow.servlet.GetUserGroups?sf_project='"+projName+
    //          "'&sf_group='"+groupName.replaceAll("+","%2B")+"'&sf_role='"+roleName+"'";
    url = "/servlet/com.sino.ams.newasset.servlet.GroupChooseServlet?sf_project=" + projName +
            "&sf_group=" + groupName.replaceAll("+", "%2B") + "&sf_role=" + roleName;
    var popscript;
    popscript = "dialogWidth:350px"
            + ";dialogHeight:200px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function getGroupCycleUsers(proj, group, role) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetAllGroupsUsers?sf_project='"
            + proj + "'&sf_group='" + group.replaceAll("+", "%2B")
            + "'&sf_role='" + role + "'";
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:330px"
            + ";center:yes;status:no;scroll:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function assistFlowApi(flowNum, groups, participants, src) {
    var num = parseInt(flowNum);
    var namesStr = "";
    var assistStr = searchFlowStr(src, "flowProp:'2'");
    var names;
    if (groups != "") {
        if (participants == "") {
            if (num == getCharCount(groups, ';')) {
                namesStr = getGroupsUsersNames(sf_project, groups, getJsonData(assistStr, "roleName:"));
                eval("names = " + namesStr.substring(1, namesStr.length - 1));
            }
        }
    }
    if (participants != "") {
        var partiArray = participants.split(";");
        var groupArray = groups.split(";");
        if (groupArray.length != partiArray.length) {
            alert("错误:接口程序中辅流组别的数目与参与者数目不等!");
            return "";
        }
        eval("names = {realnames:'',usernames:''}");
        for (var i = 0; i < partiArray.length; i++) {
            if (names.realnames != "")
                names.realnames += ";";
            names.realnames += partiArray[i] + "/" + groupArray[i];
            if (names.usernames != "")
                names.usernames += ";";
            names.usernames += partiArray[i];
        }
    }
    if (names.usernames == "")
        return "";
    var flow;
    eval("flow = " + assistStr);
    flow.realnames = names.realnames;
    flow.usernames = names.usernames;
    return src.replace(assistStr, constructFlow(flow));
}

function searchFlowStrExcludeSplit(src, code) {
    var count = src.indexOf(code);
    if (count < 0)
        return "";
    var left = src.substring(0, count);
    var right = src.substring(count, src.length);
    var temp = left.substring(left.lastIndexOf("{"), left.length) +
            right.substring(0, right.indexOf("}") + 1);
    if (getJsonData(temp, "taskFlowType:") != "4") {
        // 非子流 => return
        if (getJsonData(temp, "taskName:") == "SPLIT" || getJsonData(temp, "taskName:" == "JOIN")) {
            temp = right.substring(right.indexOf("{"), right.length);
            return searchFlowStr(temp, code);
        } else {
            return temp;
        }
    }
    // 子流 => 返回下一个
    temp = right.substring(right.indexOf("{"), right.length);
    //    temp = temp.substring(0, temp.indexOf("}") + 1);
    return searchFlowStrExcludeSplit(temp, code);
}

function searchFlowStr(src, code) {
    var count = src.indexOf(code);
    if (count < 0)
        return "";
    var left = src.substring(0, count);
    var right = src.substring(count, src.length);
    var temp = left.substring(left.lastIndexOf("{"), left.length) +
            right.substring(0, right.indexOf("}") + 1);
    if (getJsonData(temp, "taskFlowType:") != "4") {
        // 非子流 => return
        return temp;
    }
    // 子流 => 返回下一个
    temp = right.substring(right.indexOf("{"), right.length);
    //    temp = temp.substring(0, temp.indexOf("}") + 1);
    return searchFlowStr(temp, code);
}

function parallelFlowApi(participants, src) {
    var partiArray = participants.split("\\");
    var str = src;
    for (var k = 0; k < partiArray.length; k++) {
        var count = partiArray[k].indexOf(":");
        if (count < 0) {
            alert("并流参与者对格式错误, 没有':'!")
            return "";
        }
        var code = "flowCode:'" + partiArray[k].substring(0, count) + "'";
        var parti = partiArray[k].substring(count + 1, partiArray[k].length);
        var flowStr = searchFlowStrExcludeSplit(str, code);
        if (flowStr == null || flowStr == "")
            continue;
        var flow;
        eval("flow = " + flowStr);
        /*
         if(flow.taskName == "SPLIT") {
         var tempStr = str.replace(flowStr, "");
         flowStr = searchFlowStr(tempStr, code);
         if(flowStr == null || flowStr == "")
         continue;
         eval("flow = " + flowStr);
         }
         */
        //        flow.usernames = parti.replaceAll(",",";");
        flow.usernames = parti;
        flow.realnames = "";
        var nameArr = parti.split(";")
        var realnameArr = Launch_ParallelGroups.split(";");
        for (var i = 0; i < nameArr.length; i++) {
            var nameArr2 = nameArr[i].split(",");
            var tempname = "";
            for (var j = 0; j < nameArr2.length; j++) {
                if (tempname == "") {
                    if (realnameArr[i])
                        tempname = getRealname(nameArr2[j]) + "/" + realnameArr[i];
                    else
                        tempname = getRealname(nameArr2[j]) + "/" + realnameArr[0]
                } else {
                    if (realnameArr[i])
                        tempname += "," + getRealname(nameArr2[j]) + "/" + realnameArr[i];
                    else
                        tempname += "," + getRealname(nameArr2[j]) + "/" + realnameArr[0];
                }
            }
            if (flow.realnames == "")
                flow.realnames = tempname;
            else
                flow.realnames += ";" + tempname;
        }
        str = str.replace(flowStr, constructFlow(flow));
        if (Launch_WaitType) {
            var waitIndex = str.indexOf("waitType:");
            if (waitIndex >= 0) {
                var waitStr = str.substring(waitIndex, waitIndex + 12);
                str = str.replace(waitStr, "waitType:'" + Launch_WaitType + "'");
            }
        }
    }
    return str;
}

function flowsSelectDlg(flowsStr) {
    var url;
    /*
     url = "/flow/flowsSelect.jsp?flowsStr=\""
     +flowsStr.replaceAll("+","%2B")
     +"\"&sf_handlerStatus='"
     +document.getElementById("sf_handlerStatus").value
     +"'&sf_project='"
     + sf_project + "'&sf_trayType=" + document.getElementById("sf_trayType").value;
     */
    var flowCode = getJsonData(flowsStr, "flowCode:");
    url = "/flow/flowsSelect.jsp?sf_flowCode="
            + flowCode
            + "&sf_actID="
            + document.getElementById("sf_actID").value
            + "&sf_handlerStatus='"
            + document.getElementById("sf_handlerStatus").value
            + "'&sf_project='"
            + sf_project + "'&sf_trayType=" + document.getElementById("sf_trayType").value;

    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:300px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function multipleFlowDlg(taskid) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetMultipleFlows?sf_taskID='"
            + taskid + "'&sf_handlerStatus='"
            + document.getElementById("sf_handlerStatus").value
            + "'&sf_project='"
            + sf_project + "'";
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:300px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function conditionalFlowDlg(str, bypass) {
    var url;
    url = "/flow/conditionalFlow.jsp?bypass='" + bypass + "'";
    var popscript;
    popscript = "dialogWidth:300px"
            + ";dialogHeight:200px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    var largeText = new Array();
    // using array because string can only pass 4096 characters but array do not have this limit
    largeText[0] = str;
    return window.showModalDialog(url, largeText, popscript);
}

function selectCycleUsers(proj, group, role) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetCycleUsers?sf_project='" +
            proj + "'&sf_group='" + group.replaceAll("+", "%2B")
            + "'&sf_role='" + role + "'";
    var popscript;
    popscript = "dialogWidth:480px"
            + ";dialogHeight:280px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function getPriorityDlg() {
    var url;
    url = "/flow/getPriority.jsp";
    var popscript;
    popscript = "dialogWidth:360px"
            + ";dialogHeight:210px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function copySelectDlg(project, group, role) {
    var url;
    url = "/flow/copySelect.jsp?project='" + sf_project + "'";
    var popscript;
    popscript = "dialogWidth:620px"
            + ";dialogHeight:430px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";
    var largeText = new Array();
    largeText[0] = project;
    largeText[1] = group;
    //    largeText[2] = role;
    largeText[2] = "";

    return window.showModalDialog(url, largeText, popscript);
}

function deliveryStatusDlg() {
    var url;
    url = "/flow/deliveryStatus.jsp";
    var popscript;
    popscript = "dialogWidth:425px"
            + ";dialogHeight:330px"
            + ";center:yes;status:no;scroll:no;scrollbars:no;help:no";
    return window.showModalDialog(url, document.getElementById("sf_nextTaskData").value, popscript);
}

function deliveryStatusEndDlg() {
    var url;
    url = "/flow/deliveryStatusEnd.jsp";
    var popscript;
    popscript = "dialogWidth:357px"
            + ";dialogHeight:200px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

var http_request = false;
var flag;

/*
 function getFlowCodeInArray(prop) {
 if(prop.length) {
 return getFlowCodeInArray(prop[0]);
 } else {
 return prop.flowCode;
 }
 }
 */

function getFlowPropInArray(prop) {
    if (prop.length) {
        return getFlowPropInArray(prop[0]);
    } else {
        return prop;
    }
}

function processConditionalTask(prop, str) {
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
            /*
             var flowCode = "";
             flowCode = getFlowCodeInArray(prop[i]);
             */
            var selProp = getFlowPropInArray(prop[i]);
            if (selProp.flowCode == code) {
                /*
                 retStr = selectFlow(document.getElementById("sf_taskid").value, code,"0");
                 if(retStr == "")
                 return retStr;
                 else {
                 if(retStr.charAt(0) != '[')
                 retStr = "[" + retStr + "]";
                 var prop;
                 prop = eval("prop = " + retStr);
                 return processTasks(prop, retStr);
                 }
                 */
                //                retStr = "[" + constructFlow(selProp) + "]";
                retStr = tasksStr[i];
                if (retStr.charAt(0) != '[')
                    retStr = "[" + retStr + "]";
                var propArr;
                eval("propArr = " + retStr);
                return processTasks(propArr, retStr);
            }
        }
    }
    retStr = conditionalFlowDlg(str, Launch_Code_Bypass);
    if (retStr == "")
        return retStr;
    else {
        var tempArray;
        eval("tempArray = " + retStr);
        var selProp = getFlowPropInArray(tempArray);
        Launch_Code = selProp.flowCode;
        return processTasks(tempArray, retStr);
    }
}

function processSingleTask(prop, str) {
    var taskName = prop.taskName;
    Launch_FlowStr = str;
    if (taskName == "JOIN" || taskName == "STOP" || prop.taskFlowType == "4") {
        return str;
    }
    return getReturnProp(prop, str);
}

function processMultipleTask(prop, str) {
    if (prop.taskFlowType == "2") {
        // 并流
        Launch_ParallelParticipants = "";
        Launch_FlowStr = str;
        SFParallelFlow();
        if (!Launch_Continue) {
            if (Error_Msg != "") {
                alert(Error_Msg);
            }
            return "";
        }
        if (Launch_ParallelParticipants == "") {
            return flowsSelectDlg(str);
        } else {
            return parallelFlowApi(Launch_ParallelParticipants, str);
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
        // error => a
        alert("Task Flow Type Error!");
        return "";
    }
}

function processTasks(prop, str) {
    var retStr = "";
    var i;
    if (prop.length == 1) {
        if (prop[0].length) {
            retStr = processTasks(prop[0], str.substring(1, str.length - 1));
            if (retStr == "")
                return retStr;
            return "[" + retStr + "]";
        }
        if (prop[0].taskName == "SPLIT")
            return processMultipleTask(prop, str);
        retStr = processSingleTask(prop[0], str.substring(1, str.length - 1));
        if (retStr == "")
            return retStr;
        else
            return "[" + retStr + "]";
    } else {
        var taskName = prop[0].taskName;
        var condFlow = getFlowPropInArray(prop[1]).condFlow;
        if (taskName == "SPLIT") {
            return processMultipleTask(prop[0], str);
            //        } else if((taskName == "JOIN" || taskName == "STOP" || prop[0].taskFlowType == "4")
            //                && (str.charAt(1) != '[' && str.charAt(str.length - 2) != ']')) {
        } else if ((taskName == "JOIN" || taskName == "STOP" || prop[0].taskFlowType == "4")
                && condFlow != '1') {
            var tasksStr = getFlowStr(str.substring(1, str.length - 1));
            var tempStr;
            retStr = "";
            for (i = 0; i < prop.length; i++) {
                if (prop[i].length)
                    tempStr = processTasks(prop[i], tasksStr[i]);
                else
                    tempStr = processSingleTask(prop[i], tasksStr[i]);
                if (tempStr == "")
                    return tempStr;
                if (retStr != "")
                    retStr += ",";
                retStr += tempStr;
            }
            return "[" + retStr + "]";
        } else {
            /*
             if(taskName != 'STOP' || prop.length > 2)
             return processConditionalTask(prop, str);
             else {
             if(prop[1].taskName) {
             return processConditionalTask(prop, str);
             } else {
             tasksStr = getFlowStr(str.substring(1, str.length - 1));
             tempStr;
             retStr = "";
             for(i = 0; i < prop.length; i++) {
             if(prop[i].length)
             tempStr = processTasks(prop[i], tasksStr[i]);
             else
             tempStr = processSingleTask(prop[i], tasksStr[i]);
             if(tempStr == "")
             return tempStr;
             if(retStr != "")
             retStr += ",";
             retStr += tempStr;
             }
             return "[" + retStr + "]";
             }
             }
             */
            if (condFlow == "1") {
                return processConditionalTask(prop, str);
            } else {
                tasksStr = getFlowStr(str.substring(1, str.length - 1));
                tempStr;
                retStr = "";
                for (i = 0; i < prop.length; i++) {
                    if (prop[i].length)
                        tempStr = processTasks(prop[i], tasksStr[i]);
                    else
                        tempStr = processSingleTask(prop[i], tasksStr[i]);
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

//从服务器取下一节点信息，如果多于一个节点，弹出
function getNextTaskByAct() {
    try {
        ajaxReturn = "";
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR') {
                alert("取下一任务流向出错，请重新尝试提交或通知系统管理员！");
                ajaxReturn = "";
                return;
            }
            //            var resArray;
            //            eval("resArray = " + resText);
            //            ajaxReturn = processTasks(resArray, resText);
            ajaxReturn = resText;
        }
    } catch(e) {
        alert("取下一任务流向出错！请通知系统管理员！" + e.message);
    }
}

function getToTaskByInfo() {
    try {
        ajaxReturn = "";
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR') {
                alert("取特送任务出错，请重新尝试特送或通知系统管理员！");
                ajaxReturn = "";
                return;
            }
            //            var resArray;
            //            eval("resArray = " + resText);
            //            ajaxReturn = processTasks(resArray, resText);
            ajaxReturn = resText;
        }
    } catch(e) {
        alert("取特送任务出错！请通知系统管理员！" + e.message);
    }
}

//从服务器取下一节点信息，如果多于一个节点，弹出
function getNextTaskCount() {
    try {
        ajaxReturn = "";
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR') {
                alert("取下一任务流向出错，请重新尝试提交或通知系统管理员！");
                //                hiddenWaitDiv();
                //                cancelDisable();
                ajaxReturn = "";
                return;
            } else if (resText == 'ERROR2') {
                alert("辅流设置为等待,必需所有辅流完成后才能完成!");
                ajaxReturn = "";
                return;
            }
            /*
             switch(needSetGroup(resText)) {
             case 0: default:
             break;
             case 1:
             document.getElementById("sf_setPlusGroup").value = "1";
             break;
             case 2:
             document.getElementById("sf_setHandlerGroup").value = "1";
             break;
             }
             */
            var resArray;
            eval("resArray = " + resText);
            ajaxReturn = processTasks(resArray, resText);
        }
    } catch(e) {
        alert("取下一任务流向出错！请通知系统管理员！" + e.message);
        //        hiddenWaitDiv();
        //        cancelDisable();
    }
}

//从服务器取得阅示人员信息
function chkReviewStatus() {
    try {
        ajaxReturn = "";
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR') {
                alert("读取阅示人员信息错误，请重新尝试提交或通知系统管理员！");
                return;
            }
            var resArray;
            eval("resArray = " + resText);
            if (resArray[0].unread == "") {
                ajaxReturn = selectFlow(document.getElementById("sf_taskid").value, Launch_Code, "0");
            } else {
                alert("阅示人员並没有阅示完毕! 任务现在还不能完成！");
            }
        }
    } catch(e) {
        alert("读取阅示人员信息错误，请通知系统管理员！" + e.message);
    }
    return ajaxReturn;
}

//从服务器取得阅示人员列表
function reviewUsersReturn() {
    try {
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR') {
                alert("设置阅示人员错误，请重新尝试提交或通知系统管理员！");
            }
        }
    } catch(e) {
        alert("设置阅示人员错误，请通知系统管理员！" + e.message);
        sync = true;
    }
}

function ajaxFunctionNoErrorMsg() {
    try {
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText.indexOf("404") >= 0) {
                ajaxReturn = "";
                return;
            }
            if (resText.indexOf("ERROR") >= 0) {
                ajaxReturn = "";
                return;
            }
            ajaxReturn = resText;
        }
    } catch(e) {
        ajaxReturn = "";
    }
}

function ajaxFunction() {
    try {
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText.indexOf("404") >= 0) {
                alert("找不到签收 servlet，请通知系统管理员！");
                ajaxReturn = "";
                return;
            }
            if (resText.indexOf("ERROR") >= 0) {
                var res;
                eval("res = " + resText);
                alert(res[0].message);
                ajaxReturn = "";
                return;
            }
            ajaxReturn = resText;
        }
    } catch(e) {
        alert("服务器数据错误，请通知系统管理员！" + e.message);
        ajaxReturn = "";
    }
}

//从服务器取下一节点信息，如果多于一个节点，弹出
function getGroupUsersByGroups() {
    try {
        if (http_request.readyState == 4 || http_request.readyState == "complete") {
            var resText = http_request.responseText;
            if (resText == 'ERROR') {
                alert("取会签组别人员错误，请重新尝试提交或通知系统管理员！");
                //                hiddenWaitDiv();
                //                cancelDisable();
                sync = true;
                return;
            }
            var resArray;
            eval("resArray = " + resText);
            if (resArray[0].count < 1) { //如果没有找到下一个节点
                alert("没有找到会签组别人员，请通知系统管理员！");
                //                hiddenWaitDiv();
                //                cancelDisable();
                sync = true;
                return;
            }
            Launch_CycleUsers = resArray[0].value;
            sync = true;
        }
    } catch(e) {
        alert("取会签组别人员错误，请通知系统管理员！" + e.message);
        //        hiddenWaitDiv();
        //        cancelDisable();
        sync = true;
    }
}

function makeRequest(url, alertContents) {

    http_request = false;

    if (window.XMLHttpRequest) { // Mozilla, Safari,...
        http_request = new XMLHttpRequest();
        if (http_request.overrideMimeType) {
            http_request.overrideMimeType('text/xml');
        }
    } else if (window.ActiveXObject) { // IE
        try {
            http_request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
            }
        }
    }

    if (!http_request) {
        alert('不能启动 xml http 服务！');
        return false;
    }
    http_request.onreadystatechange = alertContents;
    http_request.open('POST', url, false);
    http_request.send(null);
    return true;
}

function makeRequestWithPara(url, para, alertContents) {

    http_request = false;

    if (window.XMLHttpRequest) { // Mozilla, Safari,...
        http_request = new XMLHttpRequest();
        if (http_request.overrideMimeType) {
            http_request.overrideMimeType('text/xml');
        }
    } else if (window.ActiveXObject) { // IE
        try {
            http_request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
            }
        }
    }

    if (!http_request) {
        alert('不能启动 xml http 服务！');
        return false;
    }
    http_request.onreadystatechange = alertContents;
    http_request.open('POST', url, false);
    http_request.send(para);
    return true;
}

function showCopyDlgByApp(appName, appDataId) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetCopyStatus?sf_appName='" +
            appName + "'&sf_appDataID=" + appDataId;
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:300px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function showOpinionDlgByApp(appName, appDataId) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetSaveStatus?sf_appName='" +
            appName + "'&sf_appDataID=" + appDataId;
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:300px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function showOpinionDlg() {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetSaveStatus?sf_caseID=" +
            document.getElementById("sf_caseID").value;
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
    return window.showModalDialog(url, null, style);
}

function selectParticipantDlg(flowData, selectedData, multSelected) {
    var url;
    //    url = "/flow/participantSelect.jsp?sf_jsonStr='" + flowData + "'&sf_selected='"
    //            + selectedData + "'&multi_selected=" + multSelected;
    url = "/flow/participantSelect.jsp?multi_selected=" + multSelected;
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:280px"
            + ";center:yes;status:no;scroll:no;help:no";
    var largeText = new Array();
    // using array because string can only pass 4096 characters but array do not have this limit
    largeText[0] = flowData;
    largeText[1] = selectedData;
    return window.showModalDialog(url, largeText, popscript);
}

function selectForwardParticipantDlg(flowData, hint) {
    var url;
    //    url = "/flow/participantSelect.jsp?sf_jsonStr='" + flowData + "'&sf_selected='"
    //            + selectedData + "'&multi_selected=" + multSelected;
    url = "/flow/forwardParticipantSelect.jsp";
    var popscript;
    popscript = "dialogWidth:450px"
            + ";dialogHeight:380px"
            + ";center:yes;status:no;scroll:no;help:no";
    var largeText = new Array();
    // using array because string can only pass 4096 characters but array do not have this limit
    largeText[0] = flowData;
    largeText[1] = hint;
    return window.showModalDialog(url, largeText, popscript);
}

function selectReviewDlg(taskid) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.GetReviewUsers?sf_taskID='"
            + taskid + "'&sf_project='"
            + sf_project + "'&sf_handlerStatus='"
            + document.getElementById("sf_handlerStatus").value + "'";
    var popscript;
    popscript = "dialogWidth:600px"
            + ";dialogHeight:300px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function selectCycleDlg(type, allUsers, selectedUsers) {
    var url;
    url = "/flow/cycleSelect.jsp";
    var popscript;
    popscript = "dialogWidth:630px"
            + ";dialogHeight:360px"
            + ";center:yes;status:no;scroll:no;help:no";
    var largeText = new Array();
    // using array because string can only pass 4096 characters but array do not have this limit
    if (type == "")
        type = "0";
    largeText[0] = type;
    largeText[1] = allUsers;
    largeText[2] = selectedUsers;
    return window.showModalDialog(url, largeText, popscript);
}

function selectCycleUsersDlg(actId, selectedUsers) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.selectCycleUsers?sf_actID=" + actId + "&users='"
            + selectedUsers + "'";
    var popscript;
    popscript = "dialogWidth:630px"
            + ";dialogHeight:360px"
            + ";center:yes;status:no;scroll:no;help:no";
    var users = window.showModalDialog(url, null, popscript);
    if (users == "") {
        return users;
    } else {
        var usersArr;
        //        eval("usersArr = " + users);
        usersArr = eval(users);
        return usersArr[0].users;
    }
}

function checkSpecialSend() {
    var getSendTaskURL = "/servlet/com.sino.sinoflow.servlet.GetSendTask?projName='"
            + sf_project + "'&procName='" + document.getElementById("sf_procedure").value
            + "'&nowTask='" + document.getElementById("sf_taskname").value + "'&procId='"
            + document.getElementById("sf_procedureid").value + "'";

    h = window.screen.height;
    w = window.screen.width;
    var f1 = "dialogWidth:" + w
            + ";dialogHeight:" + h
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(getSendTaskURL, null, f1);

    /*
     var popscript;
     popscript = "dialogWidth:800px"
     + ";dialogHeight:600px"
     + ";center:yes;status:no;scrollbars:no;help:no";
     return window.showModalDialog(getSendTaskURL, null, popscript);
     */
}

function getParallelComment(column) {
    var createCaseURL = "/servlet/com.sino.sinoflow.servlet.GetParallelComment?sf_actID='"
            + document.getElementById("sf_actID").value + "'&sf_column=" + column;
    makeRequest(createCaseURL, ajaxFunction);
    return ajaxReturn;
}

function completeCaseWithOpinion(actId, group, user, flowCode, comment) {
    var createCaseURL = "/servlet/com.sino.sinoflow.servlet.CompleteCaseWithOpinion?actId='"
            + actId + "'&group='" + group + "'&toUser='" + user + "'&flowCode='" + flowCode
            + "'&comment='" + comment + "'";
    makeRequest(createCaseURL, ajaxFunction);
    return ajaxReturn;
}

// return: "" -- error, same actId -- 结束, others -- 下一任务 actId
function completeCase(actId, group, user, flowCode, comment) {
    var createCaseURL = "/servlet/com.sino.sinoflow.servlet.CompleteCase?actId='"
            + actId + "'&group='" + group + "'&toUser='" + user + "'&flowCode='" + flowCode
            + "'&comment='" + comment + "'";
    makeRequest(createCaseURL, ajaxFunction);
    return ajaxReturn;
}

// return: "" -- error, others -- 新产生流程的 case ID
function createCaseById(appId, group, user, tray, appDataId, keyword, subject) {
    var createCaseURL = "/servlet/com.sino.sinoflow.servlet.CreateCase?sf_appID='"
            + appId + "'&group='" + group + "'&user='" + user + "'&tray=" + tray + "&appDataId='" +
            appDataId + "'&keyword='" + keyword + "'&subject='" + subject + "'";
    makeRequest(createCaseURL, ajaxFunction);
    return ajaxReturn;
}

// return: "" -- error, others -- 新产生流程的 case 
function createCaseByName(appName, group, user, tray, appDataId, keyword, subject) {
    var createCaseURL = "/servlet/com.sino.sinoflow.servlet.CreateCase?sf_appName='"
            + appName + "'&group='" + group + "'&user='" + user + "'&tray=" + tray + "&appDataId='" +
            appDataId + "'&keyword='" + keyword + "'&subject='" + subject + "'";
    makeRequest(createCaseURL, ajaxFunction);
    return ajaxReturn;
}

function getBackTask(actId) {
    var backURL = "/servlet/com.sino.sinoflow.servlet.GetBackTask?sf_actID="
            + actId;
    makeRequest(backURL, ajaxFunction);
    return ajaxReturn;
}

function checkReviewStatus(actId) {
    var checkStatusURL = "/servlet/com.sino.sinoflow.servlet.CheckReviewStatus?sf_actID="
            + actId;
    makeRequest(checkStatusURL, chkReviewStatus);
    return ajaxReturn;
}

function getServerTime() {
    var getTimeURL = "/servlet/com.sino.sinoflow.servlet.GetServerTime";
    makeRequest(getTimeURL, ajaxFunction);
    return ajaxReturn;
}

function getServerMaskGroups(proj, group, role) {
    var getMaskURL = "/servlet/com.sino.sinoflow.servlet.GetAllGroups?sf_project='"
            + proj + "'&sf_group='" + group.replaceAll("+", "%2B")
            + "'&sf_role='" + role + "'";
    makeRequest(getMaskURL, ajaxFunction);
    return ajaxReturn;
}

function getGroupNameByDeptName(deptName) {
    var getGroupsURL = "/servlet/com.sino.cms.util.GetGroupsByDeptServlet?dept='" + deptName + "'";
    makeRequest(getGroupsURL, ajaxFunction);
    return ajaxReturn;
}

function getRealname(loginName) {
    var getRealnameURL = "/servlet/com.sino.sinoflow.servlet.GetRealname?name='" + loginName + "'";
    makeRequest(getRealnameURL, ajaxFunction);
    return ajaxReturn;
}

function getRealnames(loginName) {
    var getRealnameURL = "/servlet/com.sino.sinoflow.servlet.GetRealnames?names='" + loginName + "'";
    makeRequest(getRealnameURL, ajaxFunction);
    return ajaxReturn;
}

function getCycleRealnames(loginName, role) {
    var getRealnameURL = "/servlet/com.sino.sinoflow.servlet.GetCycleRealnames?names='" + loginName + "'&role='" + role + "'";
    makeRequest(getRealnameURL, ajaxFunction);
    return ajaxReturn;
}

function getLoginName(id) {
    var getLoginNameURL = "/servlet/com.sino.sinoflow.servlet.GetLoginName?id='" + id + "'";
    makeRequest(getLoginNameURL, ajaxFunction);
    return ajaxReturn;
}

function getGroupName(loginName, role) {
    var getRealnameURL = "/servlet/com.sino.sinoflow.servlet.GetGroupName?name='" + loginName + "'&role='" + role + "'";
    makeRequest(getRealnameURL, ajaxFunction);
    return ajaxReturn;
}

function getHandlerName(caseId, group, role) {
    var getHandlerURL = "/servlet/com.sino.sinoflow.servlet.GetHandler?sf_caseID='" + caseId +
            "'&sf_group='" + group + "'&sf_role='" + role + "'";
    makeRequest(getHandlerURL, ajaxFunctionNoErrorMsg);
    return ajaxReturn;
}

function signAct() {
    var signActURL = "/servlet/com.sino.sinoflow.servlet.SignAct?sf_actID="
            + document.getElementById("sf_actID").value;
    makeRequest(signActURL, ajaxFunction);
    return ajaxReturn;
}

function getGroupsUsersNames(proj, groups, role) {
    var getGroupUsersURL = "/servlet/com.sino.sinoflow.servlet.GetCycleGroupUsers?sf_project='"
            + proj + "'&sf_groups='" + groups.replaceAll("+", "%2B") + "'&sf_role='"
            + role + "'";
    makeRequest(getGroupUsersURL, ajaxFunction);
    return ajaxReturn;
}

function getParallelUsers(actId) {
    var getParallelUsersURL = "/servlet/com.sino.sinoflow.servlet.GetParallelUsersServlet?sf_actId='"
            + actId + "'";
    makeRequest(getParallelUsersURL, ajaxFunction);
    return ajaxReturn;
}

function selectFlowByAct(actid, flowCode, isCycle) {
    var selectFlowURL = "/servlet/com.sino.sinoflow.servlet.GetNextTaskByAct?sf_actID='"
            + actid + "'&sf_flowCode='" + flowCode
            + "'&isCycle=" + isCycle + "&event=" + document.getElementById("sf_event").value;
    makeRequest(selectFlowURL, getNextTaskByAct);
    return ajaxReturn;
}

function toTask(taskName) {
    var toTaskURL = "/servlet/com.sino.sinoflow.servlet.GetToTaskInfo?sf_actID='"
            + document.getElementById("sf_actID").value + "'&fromTask='"
            + document.getElementById("sf_taskname").value + "'&toTask='"
            + taskName + "'&procId='" + document.getElementById("sf_procedureid").value
            + "'&handlerGroup='" + document.getElementById("sf_handlerGroup").value + "'";
    makeRequest(toTaskURL, ajaxFunction);
    if (ajaxReturn != "") {
        var resArray;
        eval("resArray = " + ajaxReturn);
        ajaxReturn = processTasks(resArray, ajaxReturn);
    }
    return ajaxReturn;
}

function selectFlow(taskid, flowCode, isCycle) {
    var selectFlowURL = "/servlet/com.sino.sinoflow.servlet.GetNextTaskCount?sf_taskID='"
            + taskid + "'&sf_flowCode='" + flowCode
            + "'&isCycle=" + isCycle + "&sf_handlerStatus='"
            + document.getElementById("sf_handlerStatus").value
            + "'&sf_actID=" + document.getElementById("sf_actID").value
            + "&event=" + document.getElementById("sf_event").value;
    makeRequest(selectFlowURL, getNextTaskCount);
    return ajaxReturn;
}

function setReviewUsers(actId, type, qty, users) {
    var setReviewUsersURL = "/servlet/com.sino.sinoflow.servlet.SetReviewUsers?sf_actID="
            + actId + "&sf_reviewType=" + type
            + "&sf_reviewQty=" + qty + "&sf_reviewUsers='"
            + users + "'&sf_trayType=" + document.getElementById("sf_trayType").value;
    makeRequest(setReviewUsersURL, reviewUsersReturn);
    return ajaxReturn;
}

function getCycleGroupUsers(groups, role) {
    var cycleGroupUserURL = "/servlet/com.sino.sinoflow.servlet.GetCycleGroupUsers?sf_project='"
            + sf_project + "'&sf_groups='"
            + groups.replaceAll("+", "%2B") + "'&sf_role='" + role + "'";
    makeRequest(cycleGroupUserURL, getGroupUsersByGroups);
    return ajaxReturn;
}

function getNextFlowsInfo(taskId) {
    var setReviewUsersURL = "/servlet/com.sino.sinoflow.servlet.GetNextFlowsInfo?sf_taskID="
            + taskId;
    makeRequest(setReviewUsersURL, ajaxFunction);
    return ajaxReturn;
}

function constructReviewUsers() {
    var str;

    if (Launch_ReviewUsers == "") {
        return "";
    }
    str = "[{method:";
    if (Launch_SendType == "") {
        str += "0";
    } else {
        str += Launch_SendType;
    }
    str += ",type:";
    if (Launch_WorkType == "") {
        str += "0";
    } else {
        str += Launch_WorkType;
    }
    str += ",waitType:";
    if (Launch_WaitType == "") {
        str += "0";
    } else {
        str += Launch_WaitType;
    }
    str += ",users:'" + Launch_ReviewUsers + "'}]";
    return str;
}

var save_cycleQty = "0";
var save_cycleUsers = "";
var save_cycleType = "";
var save_reviewQty = "0";
var save_reviewUsers = "";
var save_reviewType = "";
var save_nextTaskData = "";

//从服务器取下一任务信息
function getNextTask() {
    var reviewArray;
    var type;
    var taskData = "";
    var count;

    clearLaunchPara();
    processReviewUsers = false;
    restoreInfo();
    if (document.getElementById("sf_taskctl").value == "3") {
        //        if(document.getElementById("sf_reviewType").value & )
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
                //                setReviewUsers(document.getElementById("sf_actID").value, document.getElementById("sf_reviewType").value,
                //                        document.getElementById("sf_reviewQty").value, ReviewUsers);
                if (reviewArray[0].waitType == "1") {
                    taskData = selectFlow(document.getElementById("sf_taskid").value, "", "0");
                    //                        setReviewUsers(document.getElementById("sf_actID").value, document.getElementById("sf_reviewType").value,
                    //                                document.getElementById("sf_reviewQty").value, ReviewUsers);
                    processReviewUsers = true;
                } else {
                    //                    setReviewUsers(document.getElementById("sf_actID").value, document.getElementById("sf_reviewType").value,
                    //                            document.getElementById("sf_reviewQty").value, ReviewUsers);
                    processReviewUsers = true;
                    alert("阅示设置为等待, 必须所有阅示人员阅示完毕才可完成, 可使用暫存保存信息！");
                }
                //                HideASinoButton(6);
                ShowSinoButton(7);
            } else {
                taskData = selectFlow(document.getElementById("sf_taskid").value, "", "0");
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
                // 会签
                //                taskData = selectFlow(document.getElementById("sf_taskid").value, Launch_Code, "1");
                /*
                 setFlowPara(document.getElementById("sf_nextTaskData").value);
                 flowArray[0].flowType = "2";   // 选会签者
                 taskData = SetReturnProp(0);
                 */
                var tempFlowProp;
                eval("tempFlowProp = " + document.getElementById("sf_nextTaskData").value);
                tempFlowProp[0].flowType = "2";
                taskData = getReturnProp(tempFlowProp[0]);
                if (taskData == "") {
                    if (confirm("是否不进行会签?"))
                        taskData = selectFlow(document.getElementById("sf_taskid").value, "", "0");
                }
            } else {
                //                document.getElementById("sf_cycleQty").value = "0";
                //                document.getElementById("sf_cycleUsers").value = "";
                //                document.getElementById("sf_cycleType").value = "";
                save_cyelcQty = "0";
                save_cycleUsers = "";
                save_cycleType = "";
                taskData = selectFlow(document.getElementById("sf_taskid").value, "", "0");
            }
        } else {

            //            var count = new Number(cycleCount);
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
                //                document.getElementById("sf_cycleQty").value = "0";
                //                document.getElementById("sf_cycleUsers").value = "";
                //                document.getElementById("sf_cycleType").value = "";
                save_cycleQty = "0";
                save_cycleUsers = "";
                save_cycleType = "";
                taskData = selectFlow(document.getElementById("sf_taskid").value, Launch_Code, "0");
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
                    assignedFlowArray[0].realnames = getRealname(assignedFlowArray[0].usernames) + "/" +
                            assignedFlowArray[0].groupName;
                    taskData = constructFlow(assignedFlowArray[0]);
                } else {
                    //                   document.getElementById("sf_cycleQty").value = "0";
                    //                   document.getElementById("sf_cycleUsers").value = "";
                    //                   document.getElementById("sf_cycleType").value = "";
                    save_cycleQty = "0";
                    save_cycleUsers = "";
                    save_cycleType = "";
                    taskData = selectFlow(document.getElementById("sf_taskid").value, Launch_Code, "0");
                }
            }
        }
    }
    return taskData;
}

function _tagSinoFlowObject(appID, actID, setHandlerGroup, handlerGroup, project, procedure, flowDesc,
                            priority, comment, group, reviewQty, reviewUsers, reviewType, cycleQty, cycleUsers, cycleType,
                            trayType, setPlusGroup, nextTaskData, appFieldValue, userID, attribute1, attribute2, attribute3,
                            attribute4, attribute5, copyUsers, copyMsg, caseId, nextTaskUsers, opinion, event, eventUser) {
    this.sf_appID = appID;
    this.sf_actID = actID;
    this.sf_setHandlerGroup = setHandlerGroup;
    this.sf_handlerGroup = handlerGroup;
    this.sf_project = project;
    this.sf_procedure = procedure;
    this.sf_flowDesc = flowDesc;
    this.sf_priority = priority;
    this.sf_comment = comment;
    this.sf_group = group;
    this.sf_reviewQty = reviewQty;
    this.sf_reviewUsers = reviewUsers;
    this.sf_reviewType = reviewType;
    this.sf_cycleQty = cycleQty;
    this.sf_cycleUsers = cycleUsers;
    this.sf_cycleType = cycleType;
    this.sf_trayType = trayType;
    this.sf_setPlusGroup = setPlusGroup;
    this.sf_nextTaskData = nextTaskData;
    this.sf_appValue = appFieldValue;
    this.sf_userID = userID;
    this.sf_task_attribute1 = attribute1;
    this.sf_task_attribute2 = attribute2;
    this.sf_task_attribute3 = attribute3;
    this.sf_task_attribute4 = attribute4;
    this.sf_task_attribute5 = attribute5;
    this.copyUsers = copyUsers;
    this.copyMsg = copyMsg;
    this.caseID = caseId;
    this.sf_nextTaskUsers = nextTaskUsers;
    this.sf_opinion = opinion;
    //event: '', '0' - normal event, '1': caseBack, '2': caseForward, '3': caseAsk, '4': caseAmswer, '5': special send
    this.sf_event = event;
    this.sf_eventUser = eventUser;

    /*
     this.Parse = function(VarTypeUser)
     {
     for (var i=0;i<VarTypeUser.length;i++)
     VarTypeUser[i] = String.fromCharCode(VarTypeUser.charCodeAt(i)^0xFFFF);
     this.UserId = VarTypeUser.substr(0,4); //0-4
     this.UserName = VarTypeUser.substr(5); //4--
     }

     this.ToString = function()
     {
     var str = this.UserId.toString();
     while (str.length<5)
     {
     str += "\u0000";
     }
     str += this.UserName;
     for (var i=0;i<str.length;i++)
     {
     str[i] = String.fromCharCode(str.charCodeAt(i)^0xFFFF);
     }
     return str;
     }
     */
}
function saveInfo() {
    if (processReviewUsers)
        saveReviewUsers();
    if (save_cycleQty) {
        if (save_cycleQty)
            document.getElementById("sf_cycleQty").value = save_cycleQty;
        else
            document.getElementById("sf_cycleQty").value = "0";
        if (save_cycleUsers)
            document.getElementById("sf_cycleUsers").value = save_cycleUsers;
        else
            document.getElementById("sf_cycleUsers").value = "";
        if (save_cycleType)
            document.getElementById("sf_cycleType").value = save_cycleType;
        else
            document.getElementById("sf_cycleType").value = "";
        save_cycleQty = "0";
        save_cycleUsers = save_cycleType = null;
    }
    if (save_reviewQty) {
        if (save_reviewQty)
            document.getElementById("sf_reviewQty").value = save_reviewQty;
        else
            document.getElementById("sf_reviewQty").value = "0";
        if (save_reviewUsers)
            document.getElementById("sf_reviewUsers").value = save_reviewUsers;
        else
            document.getElementById("sf_reviewUsers").value = "";
        if (save_reviewType)
            document.getElementById("sf_reviewType").value = save_reviewType;
        else
            document.getElementById("sf_reviewType").value = "";
        save_reviewQty = "0";
        save_reviewUsers = save_reviewType = "";
    }
    var sinoflowObject = new _tagSinoFlowObject("", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    sinoflowObject.sf_appID = document.getElementById("sf_appID").value;
    sinoflowObject.sf_actID = document.getElementById("sf_actID").value;
    sinoflowObject.sf_setHandlerGroup = document.getElementById("sf_setHandlerGroup").value;
    sinoflowObject.sf_handlerGroup = document.getElementById("sf_handlerGroup").value;
    sinoflowObject.sf_project = document.getElementById("sf_project").value;
    sinoflowObject.sf_procedure = document.getElementById("sf_procedure").value;
    sinoflowObject.sf_flowDesc = document.getElementById("sf_flowDesc").value;
    sinoflowObject.sf_priority = document.getElementById("sf_priority").value;
    sinoflowObject.sf_comment = document.getElementById("sf_comment").value;
    sinoflowObject.sf_group = document.getElementById("sf_group").value;
    sinoflowObject.sf_reviewQty = document.getElementById("sf_reviewQty").value;
    sinoflowObject.sf_reviewUsers = document.getElementById("sf_reviewUsers").value;
    sinoflowObject.sf_reviewType = document.getElementById("sf_reviewType").value;
    sinoflowObject.sf_cycleQty = document.getElementById("sf_cycleQty").value;
    sinoflowObject.sf_cycleUsers = document.getElementById("sf_cycleUsers").value;
    sinoflowObject.sf_cycleType = document.getElementById("sf_cycleType").value;
    sinoflowObject.sf_trayType = document.getElementById("sf_trayType").value;
    sinoflowObject.sf_setPlusGroup = document.getElementById("sf_setPlusGroup").value;
    sinoflowObject.sf_nextTaskData = document.getElementById("sf_nextTaskData").value;
    sinoflowObject.sf_userID = document.getElementById("sf_userID").value;
    sinoflowObject.sf_task_attribute1 = document.getElementById("sf_task_attribute1").value;
    sinoflowObject.sf_task_attribute2 = document.getElementById("sf_task_attribute2").value;
    sinoflowObject.sf_task_attribute3 = document.getElementById("sf_task_attribute3").value;
    sinoflowObject.sf_task_attribute4 = document.getElementById("sf_task_attribute4").value;
    sinoflowObject.sf_task_attribute5 = document.getElementById("sf_task_attribute5").value;
    sinoflowObject.sf_copyUsers = document.getElementById("sf_copyUsers").value;
    sinoflowObject.sf_copyMsg = document.getElementById("sf_copyMsg").value;
    sinoflowObject.sf_caseID = document.getElementById("sf_caseID").value;
    sinoflowObject.sf_nextTaskUsers = document.getElementById("sf_nextTaskUsers").value;
    sinoflowObject.sf_opinion = document.getElementById("sf_opinion").value;
    sinoflowObject.sf_event = document.getElementById("sf_event").value;
    sinoflowObject.sf_eventUser = document.getElementById("sf_eventUser").value;
    var fieldList = document.getElementById("sf_appField").value.split(";");
    var fieldValue = "";
    for (var i = 0; i < fieldList.length; i++) {
        if (i != 0)
            fieldValue += "&;&";
        if (fieldList[i] == null || fieldList[i] == "")
            continue;
        if (document.getElementById(fieldList[i]))
            fieldValue += document.getElementById(fieldList[i]).value;
        else {
            alert("表单中不存在应用定义里的域 " + fieldList[i] + " !");
        }
    }
    sinoflowObject.sf_appValue = fieldValue;
    var appFieldValue = sinoflowObject.toJSONString();
    document.getElementById("sf_appFieldValue").value = appFieldValue;
}

var setHandlerGroup = false;
var setPlusGroup = false;
var plusGroup;
var handlerGroup;

function getReturnProp(flowProp) {
    var participants;
    var names;
    var str;
    setPlusGroup = false;
    setHandlerGroup = false;
    Launch_NextGroup = flowProp.groupName;
    if (needGroupSelected(flowProp.groupName)) {
        //        flowProp.groupName = getMaskGroup(flowProp.groupName);
        var groupMask;
        Launch_HandleGroup = "";
        SFQueryGroup();
        if (!Launch_Continue) {
            return "";
        }
        if(Launch_HandleGroup.indexf("*") >= 0) {
            groupMask = checkGroupMask(sf_project, Launch_HandleGroup, flowProp.roleName);
            Launch_HandleGroup = "";
        } else if (Launch_HandleGroup != "") {
            groupMask = Launch_HandleGroup;
        } else {
            groupMask = checkGroupMask(sf_project, flowProp.groupName, flowProp.roleName);
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
        }
        flowProp.groupName = groupMask;
        Launch_NextGroup = flowProp.groupName;

        participants = getGroupsUsersNames(sf_project, flowProp.groupName, flowProp.roleName);
        if (participants == "")
            return "";
        eval("names = " + participants);
        flowProp.realnames = names[0].realnames;
        flowProp.usernames = names[0].usernames;
    }
    str = "[" + constructFlow(flowProp) + "]";
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
                    Launch_CycleUsers = "";
                    SFQueryCycleReview();
                    if (!Launch_Continue) {
                        if (Error_Msg != "")
                            alert(Error_Msg);
                        return "";
                    }
                    if (!Launch_IsEndCycleView) {
                        if (Launch_CycleUsers != "") {
                            participants = "[{realnames:'" + Launch_CycleUsers + "',usernames:'"
                                    + Launch_CycleUsers + "'}]";
                        } else if (flowProp.taskCycleType == "0" || flowProp.taskCycleType == "") {
                            participants = selectCycleUsers(sf_project, flowProp.groupName, flowProp.roleName);
                        } else {
                            var checkGroup;
                            if (flowProp.groupName.indexOf(".") < 0)
                                checkGroup = "*";
                            else {
                                checkGroup = flowProp.groupName.substring(0, flowProp.groupName.lastIndexOf(".") + 1)
                                        + "*";
                            }
                            var cycleGroups = groupSelected(sf_project, checkGroup, flowProp.roleName, "1");
                            participants = getGroupsUsersNames(sf_project, cycleGroups, flowProp.roleName);
                        }
                        if (participants != "") {
                            eval("names = " + participants);
                            flowProp.cycleQty = getCharCount(names[0].usernames, ';');
                            flowProp.cycleUser = names[0].usernames;
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
            if (Launch_DistributeUser == "") {
            	  if (flowProp.usernames == "") {
                        var users = getUsers(flowProp.groupName,  flowProp.roleName);
                        var usersArr;
                        eval("usersArr = " + users);
                        participants = selectParticipantDlg("[{taskName:'" + flowProp.taskName + "', groupName:'" +
                        		flowProp.groupName + "', roleName:'" + flowProp.roleName + "', realnames:'" + usersArr[0].realnames +
                        		"', usernames:'" + usersArr[0].usernames + "', flowProp:'0'}]", "", "0");
                } else if (flowProp.usernames.indexOf(",") >= 0 || flowProp.usernames.indexOf(";") >= 0) {
                    participants = selectParticipantDlg("[{taskName:'" + flowProp.taskName + "', groupName:'" +
                            flowProp.groupName + "', roleName:'" + flowProp.roleName + "', realnames:'" + flowProp.realnames +
                            "', usernames:'" + flowProp.usernames + "', flowProp:'0'}]", "", "0");

                    //                        participants = selectParticipantDlg(str, "", "0");
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
                        	if(tempDisUsersArr[k].toUpperCase()  == tempUsernamesArr[j].toUpperCase() ) {
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
                    if(tempUsernames.indexOf(";") >= 0) {
                        participants = selectParticipantDlg("[{taskName:'" + flowProp.taskName + "', groupName:'" +
                        		flowProp.groupName + "', roleName:'" + flowProp.roleName + "', realnames:'" + tempRealnames +
                        		"', usernames:'" + tempUsernames + "', flowProp:'0'}]", "", "0");                        
                    } else if(tempUsernames == "") {
                        alert("分发用户为空, 请检查接口程序与用户权限设定!");
                        return "";
                    } else {
                        participants = "[{realnames:'" + tempRealnames + "', usernames:'"
                            + tempUsernames + "'}]";
                    }	
            } else {
                Launch_DistributeUser = Launch_DistributeUser.replaceAll(";", ",");
                var realName = "";
                var userArr = Launch_DistributeUser.split(",");
                if(userArr.length == 1) {
                    realName = getRealname(userArr[0]) + "/" + flowProp.groupName;
                    participants = "[{realnames:'" + realName + "', usernames:'"
                            + Launch_DistributeUser + "'}]";
                } else {
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
                //                alert("流程设置了直送经办人, 但发现经办人不在所设定的参与者中! 改为选择参与者!");
                if (flowProp.usernames.indexOf(",") >= 0 || flowProp.usernames.indexOf(";") >= 0) {
                    var handlerName = getHandlerName(document.getElementById("sf_caseID").value,
                            flowProp.groupName, flowProp.roleName);
                    var handlerRealnames;
                    if (handlerName == "" || !inList(sf_handler, handlerName, ";")) {
                        var usersStr;
                        if (handler != "") {
                            handlerRealnames = getHandlerRealnames(handler, flowProp, ";");
                            usersStr = "[{taskName:'" + flowProp.taskName + "', groupName:'" + flowProp.groupName +
                                    "', roleName:'" + flowProp.roleName + "', ";
                            usersStr += "realnames:'" + handlerRealnames + "', usernames:'" + handler + "', flowProp:'0'}]";
                        } else {
                            usersStr = str;
                        }
                        participants = selectParticipantDlg(usersStr, "", "0");
                        if (participants == "")
                            return "";
                        eval("names = " + participants);
                        //                  handler = names[0].usernames;
                        flowProp.realnames = names[0].realnames;
                        flowProp.usernames = names[0].usernames;
                    } else {
                        handlerRealnames = getHandlerRealnames(handlerName, flowProp, ";");
                        flowProp.realnames = handlerRealnames;
                        flowProp.usernames = handlerName;
                    }
                }
            } else {
                //                flowProp.realnames = handler + "/" + flowProp.groupName;
                flowProp.realnames = getRealname(handler) + "/" + flowProp.groupName;
                flowProp.usernames = handler;
            }
            break;
    }
    if (participants == "")
        return "";
    if (document.getElementById("sf_trayType").value == "1") {
        if (flowProp.usernames.indexOf(",")) {
            var nameArr = flowProp.usernames.split(";");
            var realArr = flowProp.realnames.split(";");
            var groupArr = flowProp.groupName.split(";");
            for (var i = 0; i < nameArr.length; i++) {
                if (nameArr[i].indexOf(",") < 0)
                    continue;
                usersStr = "[{taskName:'" + flowProp.taskName + "', taskDesc:'" + flowProp.taskDesc + "', groupName:'" + groupArr[i]
                        + "', roleName:'" + flowProp.roleName + "', realnames:'"
                        + realArr[i] + "', usernames:'" + nameArr[i] + "', flowProp:'0'}]";
                participants = selectParticipantDlg(usersStr, "", "0");
                if (participants == "") {
                    return "";
                }
                eval("names = " + participants);
                flowProp.usernames = flowProp.usernames.replace(nameArr[i], names[0].usernames);
                flowProp.realnames = flowProp.realnames.replace(realArr[i], names[0].realnames);
            }
        }
    }
    if (flowProp.usernames == "") {
        alert("组别:" + flowProp.groupName + ",角色:" + flowProp.roleName + " 没有设置用户, 请通知管理员!");
        return "";
    }
    return constructFlow(flowProp);
}
/*
 function SetReturnProp(index) {
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
 setPlusGroup = false;
 setHandlerGroup = false;
 if(needGroupSelected(flowProp.groupName)) {
 //        flowProp.groupName = getMaskGroup(flowProp.groupName);
 var groupMask;
 Launch_HandleGroup = "";
 SFQueryGroup();
 if(Launch_HandleGroup != "") {
 groupMask = Launch_HandleGroup;
 } else {
 groupMask = checkGroupMask(sf_project, flowProp.groupName, flowProp.roleName);
 }
 if(groupMask == "")
 return "";
 if(flowProp.groupName.indexOf("+") >= 0) {
 setPlusGroup = true;
 plusGroup = groupMask;
 } else if(flowProp.groupName.indexOf("*") >= 0) {
 setHandlerGroup = true;
 handlerGroup = groupMask;
 }
 flowProp.groupName = groupMask;
 assignedFlowArray[index].groupName = groupMask;

 participants = getGroupsUsersNames(sf_project, flowProp.groupName, flowProp.roleName);
 if(participants == "")
 return "";
 eval("names = " + participants);
 flowProp.realnames = names[0].realnames;
 flowProp.usernames = names[0].usernames;
 }
 str = "[" + constructFlow(flowProp) + "]";
 switch(flowProp.flowType) {
 case '2':
 if(flowProp.taskFlowType == "3") {
 alert("辅流第一个任务不能是会??, 请通知系统管理员！");
 break;
 } else {
 if(flowProp.taskCtlType == "2") {
 if(flowProp.taskCycleType == "1") {
 var level = getCharCount(flowProp.groupName, '.');
 var searchGroup = "";
 for(var i = 0; i < level; i++) {
 if(searchGroup != "")
 searchGroup += ".";
 searchGroup += "*";
 }
 var namesStr = getGroupsUsersNames(sf_project, searchGroup, flowProp.roleName);
 var groupNames;
 eval("groupNames = " + namesStr);
 Launch_CycleQueryUsers = groupNames[0].usernames;
 } else {
 Launch_CycleQueryUsers = flowProp.usernames;
 }
 Launch_IsEndCycleView = false;
 if(flowProp.flowType == "2") {
 Launch_CycleQureyType = "1";
 } else {
 Launch_CycleQueryType = "0";
 }
 Launch_CycleType = flowProp.taskCycleType;
 SFQueryCycleReview();
 if(!Launch_Continue) {
 if(Error_Msg != "")
 alert(Error_Msg);
 return "";
 }
 if(!Launch_IsEndCycleView) {
 if(Launch_CycleUsers != "") {
 participants = "[{realnames:'" + Launch_CycleUsers + "',usernames:'"
 + Launch_CycleUsers + "'}]";
 } else if(flowProp.taskCycleType == "0" || flowProp.taskCycleType == "") {
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
 }
 break;
 }
 }
 }
 // 设定出错, 比如说设了预选会签者而没有设会签, 所以当一般处理
 case '1': default:
 if(flowProp.flowProp == "2") {
 // 辅流
 participants = selectParticipantDlg(str, selectedStr, "1");
 } else {
 Launch_DistributeUser = "";
 SFQueryDistribute();
 if(!Launch_Continue) {
 if(Error_Msg != "")
 alert(Error_Msg);
 return "";
 }
 if(Launch_DistributeUser == "")
 participants = selectParticipantDlg(str, selectedStr, "0");
 else
 participants = "[{realnames:'" + Launch_DistributeUser + "', usernames:'"
 + Launch_DistributeUser + "'}]";
 }
 if(participants != "") {
 eval("names = " + participants);
 assignedFlowArray[index].realnames = names[0].realnames;
 assignedFlowArray[index].usernames = names[0].usernames;
 }
 break;
 case '0':
 assignedFlowArray[index].realnames = flowProp.realnames;
 assignedFlowArray[index].usernames = flowProp.usernames;
 break;
 case '3':
 if(getHandler(sf_handler, flowProp.realnames, ";") != "")
 alert("流程设置了直送经办人, 此任务不需要设置经办人！");
 else {
 participants = selectParticipantDlg(str, selectedStr, "0");
 if(participants != "") {
 eval("names = " + participants);
 assignedFlowArray[index].realnames = names[0].realnames;
 assignedFlowArray[index].usernames = names[0].usernames;
 }
 }
 break;
 }
 if(participants == "")
 return "";
 return constructFlow(assignedFlowArray[index]);
 }
 */

function setHandlerGroupInArray(prop) {
    if (!setHandlerGroup)
        return;
    for (var i = 0; i < prop.length; i++) {
        if (prop[i].length) {
            setHandlerGroupInArray(prop[i]);
        } else {
            var taskName = prop.taskName;
            if (taskName == "SPLIT" || taskName == "JOIN" || taskName == "STOP")
                continue;
            else if (prop.taskFlowType == "4")
                continue;
            prop[i].groupName = handlerGroup;
        }
    }
}

function restoreNextTaskData() {
    if (save_nextTaskData) {
        document.getElementById("sf_nextTaskData").value = save_nextTaskData;
        save_nextTaskData = null;
    }
}

function setFlowPara(data) {
    eval("flowArray = " + data);
    eval("assignedFlowArray = " + data);
    flowStrArray = getFlowStr(data);
    assignedStrArray = getFlowStr(data);
    setHandlerGroupInArray(flowArray);
    setHandlerGroupInArray(assignedFlowArray);
    clearNames(assignedFlowArray);
}

function constructFlow(flow) {
    var str = "";
    if (flow == null || flow == "")
        return "";
    str += "{taskID:'" + flow.taskID + "', ";
    str += "procName:'" + flow.procName + "', ";
    str += "taskName:'" + flow.taskName + "', ";
    str += "taskDesc:'" + flow.taskDesc + "', ";
    str += "groupName:'" + flow.groupName + "', ";
    str += "roleName:'" + flow.roleName + "', ";
    str += "taskDur:'" + flow.taskDur + "', ";
    str += "taskWorktype:'" + flow.taskWorktype + "', ";
    str += "taskCtlType:'" + flow.taskCtlType + "', ";
    str += "taskCycleType:'" + flow.taskCycleType + "', ";
    str += "taskApi:'" + flow.taskApi + "', ";
    str += "taskDivRight:'" + flow.taskDivRight + "', ";
    str += "taskDivHidden:'" + flow.taskDivHidden + "', ";
    str += "taskFlowType:'" + flow.taskFlowType + "', ";
    str += "realnames:'" + flow.realnames + "', ";
    str += "usernames:'" + flow.usernames + "', ";
    str += "flowProp:'" + flow.flowProp + "', ";
    str += "flowID:'" + flow.flowID + "', ";
    str += "flowDesc:'" + flow.flowDesc + "', ";
    str += "flowCode:'" + flow.flowCode + "', ";
    str += "flowHint:'" + flow.flowHint + "', ";
    str += "flowType:'" + flow.flowType + "', ";
    str += "cycleQty:'" + flow.cycleQty + "', ";
    str += "cycleUser:'" + flow.cycleUser + "', ";
    str += "cycleType:'" + flow.cycleType + "', ";
    str += "reviewQty:'" + flow.reviewQty + "', ";
    str += "reviewUsers:'" + flow.reviewUsers + "', ";
    str += "reviewType:'" + flow.reviewType;
    if (setPlusGroup) {
        str += "', plusGroup:'" + plusGroup;
    }
    if (setHandlerGroup) {
        str += "', handlerGroup:'" + handlerGroup;
    }
    /*
     if(waitType != null && waitType != "") {
     str += "', waitType:'" + waitType;
     }
     */
    str += "'}";
    return str;
}

function do_SelectGroup() {
    var group = document.getElementById("sf_group").value;
//    if (document.getElementById("sf_caseID").value != "" && sf_handlerGroup!="") {
////
//        return;
//    }
//    if (isMask(group)) {
	
	
	var fromExcel = "";
	if( document.getElementById("fromExcel") ){
	 	fromExcel = document.getElementById("fromExcel").value;
	}
	
	if( fromExcel && fromExcel == "Y" ){
		var retGroup;
		if( document.getElementById("fromGroupName").value == "" ){
	        retGroup = selectGroup(document.getElementById("sf_project").value, group,
	                document.getElementById("sf_role").value);
	        if (retGroup) { 
	//            if (isMask(document.getElementById("sf_group").value) && Launch_GroupSet) {
	                document.getElementById("sf_setHandlerGroup").value = "1";
	
	                var retGroups = retGroup["fromGroup"].split(",");
	                document.getElementById("flow_group_id").value = retGroup["fromGroup"];
	                //document.getElementById("fromGroup").value = retGroup["fromGroup"];
	                document.getElementById("flow_group_name").value = retGroup["fromGroupName"];
	                document.getElementById("app_dept_code").value = retGroup["deptCode"];
	                //document.getElementById("fromDept").value = retGroup["deptCode"];
	                document.getElementById("app_dept_name").value = retGroup["deptName"];
					//document.getElementById("userDeptName").value = retGroup["deptName"];
					
	                document.getElementById("sf_handlerGroup").value = retGroup["fromGroupName"];
	                document.getElementById("sf_handlerStatus").value = "[{sf_handler:'" + document.getElementById("sf_handler").value +
	                        "', sf_handlerGroup:'" + retGroup["fromGroupName"] + "', sf_plusGroup:'" +
	                        document.getElementById("sf_plusGroup").value + "'}]";
	                 
	                if( document.forms[0].sf_group.value == "*.*" ){        
	                	document.forms[0].sf_group.value = document.getElementById("flow_group_name").value;
	                	sf_group = document.getElementById("flow_group_name").value;
	                } 
	                
	                setHandlerGroup = true;
	                handlerGroup = retGroup["fromGroupName"];
	                //alert( handlerGroup );
	//            }
	        } else {
	            isSave = true;
	            do_Cancel();
	        }        
		}else{
			document.getElementById("sf_setHandlerGroup").value = "1";
			
			document.getElementById("flow_group_name").value = document.getElementById("fromGroupName").value;
	   		document.getElementById("flow_group_id").value = document.getElementById("fromGroup").value;
	   		document.getElementById("app_dept_code").value = document.getElementById("fromDept").value;
	   		document.getElementById("app_dept_name").value = document.getElementById("fromDeptName").value;
			document.getElementById("sf_handlerGroup").value = document.getElementById("fromGroupName").value;
	        document.getElementById("sf_handlerStatus").value = "[{sf_handler:'" + document.getElementById("sf_handler").value +
	                        "', sf_handlerGroup:'" + document.getElementById("fromGroupName").value + "', sf_plusGroup:'" +
	                        document.getElementById("sf_plusGroup").value + "'}]";
	        
	        if( document.forms[0].sf_group.value == "*.*" ){        
            	document.forms[0].sf_group.value = document.getElementById("flow_group_name").value;
            	sf_group = document.getElementById("flow_group_name").value;
            } 
            
	        setHandlerGroup = true;
	        handlerGroup = document.getElementById("fromGroupName").value;
        }
        
	}else{ 
        var retGroup;
        retGroup = selectGroup(document.getElementById("sf_project").value, group,
                document.getElementById("sf_role").value);

        if (retGroup) { 
//            if (isMask(document.getElementById("sf_group").value) && Launch_GroupSet) {
                document.getElementById("sf_setHandlerGroup").value = "1";

                var retGroups = retGroup["fromGroup"].split(",");
                document.getElementById("flow_group_id").value = retGroup["fromGroup"];
                //document.getElementById("fromGroup").value = retGroup["fromGroup"];
                document.getElementById("flow_group_name").value = retGroup["fromGroupName"];
                document.getElementById("app_dept_code").value = retGroup["deptCode"];
                //document.getElementById("fromDept").value = retGroup["deptCode"];
                document.getElementById("app_dept_name").value = retGroup["deptName"];
				//document.getElementById("userDeptName").value = retGroup["deptName"];
				
                document.getElementById("sf_handlerGroup").value = retGroup["fromGroupName"];
                document.getElementById("sf_handlerStatus").value = "[{sf_handler:'" + document.getElementById("sf_handler").value +
                        "', sf_handlerGroup:'" + retGroup["fromGroupName"] + "', sf_plusGroup:'" +
                        document.getElementById("sf_plusGroup").value + "'}]";
                 
                if( document.forms[0].sf_group.value == "*.*" ){        
                	document.forms[0].sf_group.value = document.getElementById("flow_group_name").value;
                	sf_group = document.getElementById("flow_group_name").value;
                } 
                
                setHandlerGroup = true;
                handlerGroup = retGroup["fromGroupName"];
                //alert( handlerGroup );
//            }
        } else {
            isSave = true;
            do_Cancel();
        }
      }
//    }
}

function radioValue(radio) {
    if (!radio.length && radio.type.toLowerCase() == 'radio') {
        if (radio.checked)
            return radio.value;
        else
            return "";
    }
    if (radio[0].tagName.toLowerCase() != 'input' || radio[0].type.toLowerCase() != 'radio') {
        return "";
    }
    var len = radio.length;
    for (var i = 0; i < len; i++) {
        if (radio[i].checked)
            return radio[i].value;
    }
    return "";
}

function radioSelected(radio, value) {
    if (!radio.length && radio.type.toLowerCase() == 'radio') {
        if (radio.value == value)
            radio.checked = true;
        return;
    }
    if (radio[0].tagName.toLowerCase() != 'input' || radio[0].type.toLowerCase() != 'radio') {
        return;
    }
    var len = radio.length;
    for (var i = 0; i < len; i++) {
        if (radio[i].value == value) {
            radio[i].checked = true;
            return;
        }
    }
}

function checkboxValue(checkbox) {
    if (!checkbox.length && checkbox.type.toLowerCase() == 'checkbox') {
        if (checkbox.checked)
            return checkbox.value;
        else
            return "";
    }
    if (checkbox[0].tagName.toLowerCase() != 'input' || checkbox[0].type.toLowerCase() != 'checkbox') {
        return "";
    }
    var len = checkbox.length;
    var str = "";
    for (var i = 0; i < len; i++) {
        if (checkbox[i].checked) {
            if (str != "")
                str += ";";
            str += checkbox[i].value;
        }
    }
    return str;
}

function checkboxSelected(checkbox, value) {
    if (!checkbox.length && checkbox.type.toLowerCase() == 'checkbox') {
        if (checkbox.value == value)
            checkbox.checked = true;
        return;
    }
    if (checkbox[0].tagName.toLowerCase() != 'input' || checkbox[0].type.toLowerCase() != 'checkbox') {
        return;
    }
    var len = checkbox.length;
    for (var i = 0; i < len; i++) {
        if (checkbox[i].value == value) {
            checkbox[i].checked = true;
            return;
        }
    }
}

function selValue(sel) {
    if (sel == null || sel.tagName.toLowerCase() != "select" || sel.options.length < 1)
        return "";
    var len = sel.options.length;
    for (var i = 0; i < len; i++) {
        if (sel.options[i].selected)
            return sel.options[i].value;
    }
    return "";
}

function selSelected(sel, value) {
    if (sel == null || sel.tagName.toLowerCase() != "select" || sel.options.length < 1)
        return;
    var len = sel.options.length;
    for (var i = 0; i < len; i++) {
        if (sel.options[i].value == value) {
            sel.options[i].selected = true;
            return;
        }
    }
}

function getValue(fieldName) {
    var field = document.getElementById(fieldName);
    if (!field)
        return "";
    var fieldType;
    if (field.length) {
        fieldType = field[0].tagName.toLowerCase();
        if (fieldType == "input") {
            fieldType = field[0].type.toLowerCase();
        }
    } else {
        fieldType = field.tagName.toLowerCase();
        if (fieldType == "input") {
            fieldType = field.type.toLowerCase();
        }
    }
    if (fieldType == "text" || fieldType == "hidden" || fieldType == "textarea") {
        return field.value;
    } else if (fieldType == "radio") {
        field = document.getElementsByName(fieldName);
        return radioValue(field);
    } else if (fieldType == "checkbox") {
        field = document.getElementsByName(fieldName);
        return checkboxValue(field);
    } else if (fieldType == "select") {
        field = document.getElementsByName(fieldName);
        return selValue(field);
    }
    return "";
}

function autoValue(status) {
    var element = document.getElementById("sf_autoValue");
    if (!element)
        return;
    var value = element.value;
    if (value == null || value == "")
        return;
    var autoArr;
    eval("autoArr = " + value);
    if (autoArr.length == 0)
        return;
    var str = "";
    for (var i = 0; i < autoArr.length; i++) {
        var av = autoArr[i];
        if (status == (av.aon & status)) {
            if (str == "") {
                str = "[{name:'";
            } else {
                str += ",{name:'";
            }
            var avalue = "";
            switch (parseInt(av.value)) {
                case 0:
                    avalue = document.getElementById("sf_user").value;
                    break;
                case 1:
                    avalue = getServerTime();
                    var count = avalue.indexOf(" ");
                    if (count >= 0)
                        avalue = avalue.substring(0, count);
                    break;
                case 2:
                    avalue = document.getElementById("sf_group").value;
                    break;
            }
            if (av.atype == "0") {
                var fvalue = getValue(av.name);
                if (fvalue != "")
                    fvalue += ";"
                avalue = fvalue + avalue;
            }
            str += av.name + "',value:'" + avalue + "'}";
        }
    }
    if (str != "") {
        str += "]";
        fillData(str);
    }
}

function fillData(dataStr) {
    if (dataStr == null || dataStr == "")
        return;
    var i;
    var fieldType,fieldName, fieldValue;
    var dataArr;
    if (dataStr.indexOf("\"type\"") >= 0 || dataStr.indexOf("'type'") >= 0)
        dataArr = eval("(" + dataStr + ")");
    else
        eval("dataArr = " + dataStr);
    for (i = 0; i < dataArr.length; i++) {
        fieldName = dataArr[i].name;
        fieldValue = dataArr[i].value;
        if (dataArr[i].type)
            fieldType = dataArr[i].type.toLowerCase();
        else {
            var element = document.getElementsByName(fieldName);
            if (!element)
                continue;
            if (element.length) {
                if (!element[0].tagName)
                    continue;
                fieldType = element[0].tagName.toLowerCase();
                if (fieldType == "input") {
                    fieldType = element[0].type.toLowerCase();
                }
            } else {
                if (!element.tagName)
                    continue;
                fieldType = element.tagName.toLowerCase();
                if (fieldType == "input") {
                    fieldType = element.type.toLowerCase();
                }
            }
        }
        if (fieldType == "text" || fieldType == "hidden" || fieldType == "textarea") {
            document.getElementById(fieldName).value = fieldValue;
            //            alert("filedName = " + fieldName + ", filedValue = " + fieldValue);
        } else if (fieldType == "radio") {
            radioSelected(document.getElementsByName(fieldName), fieldValue);
        } else if (fieldType == "checkbox") {
            checkboxSelected(document.getElementsByName(fieldName), fieldValue);
        } else if (fieldType == "select") {
            selSelected(document.getElementById(fieldName), fieldValue);
        }
    }

}

function clearNames(srcArray) {
    for (var i = 0; i < srcArray.length; i++) {
        if (srcArray[i].length) {
            clearNames(srcArray[i]);
        } else {
            srcArray[i].realnames = "";
            srcArray[i].usernames = "";
        }
    }
}

function getProcessStr(src) {
    if (src.charAt(0) != '[')
        return src;
    var strArray = getFlowStr(src.substring(1, src.length - 1));
    for (var i = 0; i < strArray.length; i++) {
        var str = strArray[i];
        if (str.charAt(0) == '[') {
            var temp = getProcessStr(str.substring(1, str.length - 1));
            if (temp != "")
                return temp;
        } else {
            var taskName = getJsonData(str, "taskName:");
            if (!(taskName == "JOIN" || taskName == "STOP" || getJsonData(str, "taskFlowType:") == "4"))
                return str;
        }
    }
    return "";
}

function getProcessTask(prop) {
    if (!prop.length) {
        // not array => return;
        if (prop.taskID)
            return prop;
        else
            return null;
    }
    if (prop.length == 1) {
        if (prop[0].length) {
            return getProcessTask(prop[0]);
        } else {
            return prop;
        }
    } else {
        // array, check next level
        for (var i = 0; i < prop.length; i++) {
            if (prop[i].length) {
                var temp = getProcessTask(prop[i]);
                if (temp != null)
                    return temp;
            } else {
                var taskName = prop[i].taskName;
                if (!(taskName == "JOIN" || taskName == "STOP" || prop[i].taskFlowType == "4"))
                    return prop[i];
            }
        }
    }
    return null;
}

function getSize(str) {
    var sum = 0;
    for (var i = 0; i < str.length; i++) {
        if ((str.charCodeAt(i) >= 0) && (str.charCodeAt(i) <= 255))
            sum = sum + 1;
        else
            sum = sum + 2;
    }
    return sum;
}

function sizeCheck(name, csize) {
    var value = getValue(name);
    var fsize = getSize(value);
    var chksize = parseInt(csize);
    return (fsize <= chksize);
}

function patternCheck(name, patt) {
    var value = getValue(name);
    var count = 0;
    var pattChar;
    var val;
    if (patt == "#?")
        return !isNaN(value);
    for (var i = 0; i < patt.length; i++) {
        if (count >= value.length)
            return false;
        pattChar = patt.charAt(i);
        if (pattChar == '?') {
            count++;
            //            continue;
        } else if (pattChar == '*') {
            if (i == patt.length - 1) {
                // last pattern char => remain data as '*'
                return true;
            }
            while (count < (value.length - 1) && value.charAt(count + 1) != patt.charAt(i + 1))
                count++;
            if (count >= value.length)
                return false;
            else
                count++;
        } else if (pattChar == '#') {
            // 数字
            val = value.charAt(count);
            if ('0' > val || val > '9') {
                return false;
            }
            count++;
        } else if (pattChar == '%') {
            // 英文
            val = value.charAt(count);
            if (!(('A' <= val && val <= 'Z') || ('a' <= val && val <= 'z')))
                return false;
            count++;
        } else if (pattChar == '^') {
            // 中文
            var code = value.charCodeAt(count);
            if (code < 0x4e00 || code > 0x9fa5)
                return false;
            count++;
        } else {
            if (pattChar != value.charAt(count))
                return false;
            count++;
        }
    }
    return (count == value.length);
}

function validateField(validField) {
    var fieldName = validField.name;
    if ((validField.vtype & VALID_NULL_MASK) == VALID_NULL_MASK) {
        if (getValue(fieldName) == "") {
            alert("合法性检查错误! " + validField.desc + "中无数值!");
            return false;
        }
    }
    if ((validField.vtype & VALID_SIZE_MASK) == VALID_SIZE_MASK) {
        //        var type = validField.stype;
        var size = validField.size;
        if (!sizeCheck(fieldName, size)) {
            alert("合法性检查错误! " + validField.desc + "中数据大小不对, 长度必须小于或等于" + size);
            return false;
        }
    }
    if ((validField.vtype & VALID_PATTERN_MASK) == VALID_PATTERN_MASK) {
        if (!patternCheck(fieldName, validField.pattern)) {
            alert("合法性检查错误! " + validField.desc + "中格式必须为 '" + validField.pattern + "'!");
            return false;
        }
    }
    return true;
}

function validation() {
    var element = document.getElementById("sf_validation");
    if (!element)
        return true;
    var str = element.value;
    if (str == null || str == "")
        return true;
    var validArr;
    eval("validArr = " + str);
    if (validArr.length == 0)
        return true;
    for (var i = 0; i < validArr.length; i++) {
        if (!validateField(validArr[i]))
            return false;
    }
    return true;
}

function clearDivRight() {
    var allDiv = document.getElementsByTagName("DIV");
    for (var i = 0; i < allDiv.length; i++) {
        var compDiv = allDiv.item(i);
        if (compDiv.id.indexOf("app_") == 0)
            continue;
        var cNodes = compDiv.childNodes;
        for (var p in cNodes) {
            setRight(cNodes[p], false);
        }
    }
}

function setRight(element, flag) {
    if (!element.tagName)
        return;
    var tagName = element.tagName.toUpperCase();
    if (tagName != "TABLE" && tagName != "TBODY" && tagName != "TR" && tagName != "TD") {
        if (tagName == "IMG" || tagName == "img") {
            if (flag)
                element.onclick = "";
        } else {
            element.readOnly = flag;
            element.disabled = flag;
        }
    }
    if (element.hasChildNodes()) {
        for (var p in element.childNodes) {
            setRight(element.childNodes[p], flag);
        }
    }
}

function setDivRight() {
    var hDiv = document.getElementById("sf_divRight").value;
    var allDiv = document.getElementsByTagName("DIV");
    for (var i = 0; i < allDiv.length; i++) {
        var compDiv = allDiv.item(i);
        if (compDiv.id.indexOf("app_") == 0)
            continue;
        if (!inList(hDiv, compDiv.id, ";") && compDiv.id != "sinoButtonAll") {
            var cNodes = compDiv.childNodes;
            for (var p in cNodes) {
                setRight(cNodes[p], true);
            }
        } else if (inList(hDiv, compDiv.id, ";")) {
            var cNodes = compDiv.childNodes;
            for (var p in cNodes) {
                setRight(cNodes[p], false);
            }
        }
    }
    var hArr = hDiv.split(";");
    var elem;
    for (var i = 0; i < hArr.length; i++) {
        elem = document.getElementById(hArr[i]);
        if (!elem)
            continue;
        if (elem.tagName == "DIV" || elem.tagName == "div")
            continue;
        setRight(elem, false);
    }
}

function setDivVisibility() {
    var hDiv = document.getElementById("sf_divHidden").value;
    var allDiv = document.getElementsByTagName("DIV");
    for (var i = 0; i < allDiv.length; i++) {
        var compDiv = allDiv.item((i));

        var isInList = inList(hDiv, compDiv.id, ";")
        if ((compDiv.id.indexOf("$$$") == 0 || compDiv.id.indexOf("app_") == 0 || compDiv.id.length == 0) && !isInList)
            continue;
        if (isInList) {
            compDiv.style.visibility = "hidden";
            compDiv.style.display = "none";
        } else {
            compDiv.style.visibility = "visible";
            compDiv.style.display = "block";
        }
    }
    var hArr = hDiv.split(";");
    var elem;
    for (var i = 0; i < hArr.length; i++) {
        elem = document.getElementById(hArr[i]);
        if (!elem)
            continue;
        if (elem.tagName == "DIV" || elem.tagName == "div")
            continue;
        elem.style.visibility = "hidden";
        elem.style.display = "none";
    }
}

function trim(mxh) {//去除字符串空格
    var newStr = "";
    for (var i = 0; i < mxh.length; i++) {
        if (mxh.charAt(i) == " ")
            newStr += ""
        else
            newStr += mxh.charAt(i)
    }
    return newStr;
}

function getNextTaskName() {
    var taskName = getJsonData(document.getElementById("sf_nextTaskData").value, "taskName:");
}

function getParameter(obj, para) {
    return obj.get(para).toString();
}

function getTaskName(taskProp) {
    if (!taskProp.length) {
        if (taskProp.taskName) {
            if (taskProp.taskName == "JOIN")
                return "";
            else if (taskProp.taskName == "SPLIT")
                return "";
            else
                return taskProp.taskName;
        } else
            return "";
    }
    var name = "";
    for (var i = 0; i < taskProp.length; i++) {
        var prop = taskProp[i];
        var taskName = getTaskName(prop);
        if (taskName != "") {
            if (name == "")
                name += taskName;
            else
                name += ";" + taskName;
        }
    }
    return name;
}

function setNextFlowDesc() {
    if (document.getElementById("sf_opinion").value != "")
        return;
    var flowDesc = getJsonData(document.getElementById("sf_nextTaskData").value, "flowHint:");
    document.getElementById("sf_opinion").value = flowDesc;
}

function clearLaunchPara() {
    Launch_SendType = "";
    Launch_WorkType = "";
    Launch_WaitType = "";
    Launch_ReviewUsers = "";
    Launch_IsEndCycleView = "";
    if (!setCycleUsers)
        Launch_CycleUsers = "";
    Launch_CycleType = "";
    Launch_CycleQureyType = "";
    Launch_CycleQueryUsers = "";
    Launch_Code = "";
    Launch_GroupSet = true;
    Launch_HandleGroup = "";
    Launch_ParallelParticipants = "";
    Launch_ParallelGroups = "";
    Launch_AssistFlows = "";
    Launch_AssistGroups = "";
    Launch_DistributeUser = "";
    Launch_TaskName = "";
    Launch_BacktoUserName = "";
    Launch_Code_Bypass = "";
}

function restoreInfo() {
    document.getElementById("sf_reviewUsers").value = save_reviewUsers;
    document.getElementById("sf_reviewQty").value = save_reviewQty;
    document.getElementById("sf_reviewType").value = save_reviewType;
    document.getElementById("sf_cycleUsers").value = save_cycleUsers;
    document.getElementById("sf_cycleQty").value = save_cycleQty;
    document.getElementById("sf_cycleType").value = save_cycleType;
}

function isReviewUser() {
    if (document.getElementById("sf_reviewQty").value == "" || document.getElementById("sf_reviewQty").value == "0" ||
            (document.getElementById("sf_reviewType").value & 0x20) == 0)
        return false;
    else
        return true;
}

function saveReviewUsers() {
    setReviewUsers(document.getElementById("sf_actID").value, document.getElementById("sf_reviewType").value,
            document.getElementById("sf_reviewQty").value, document.getElementById("sf_reviewUsers").value);
}

/*
 function detect() {
 var handle = null;
 try{
 handle = window.open('','','width=1,height=1,left=0,top=0,scrollbars=no');
 do{if(null == handle || true == handle.closed){alert('IE已禁止弹出窗口，可能导致无法完成操作，请退出再把此站点设为允许弹出窗口！');break;};handle.close();handle=null;}while(false);
 }catch(ex){}
 if(null != handle)try{if(!handle.closed)handle.close();}catch(ex){};
 }
 */
