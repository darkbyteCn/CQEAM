var factor = 0.6;
var g_dialogWidth = window.screen.availWidth * factor;
var g_dialogHeight = window.screen.availHeight * factor;

function lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    //var url = "/servlet/com.sino.ams.newasset.bean.AssetsLookUpServlet?lookUpName=" + lookUpName;
    var url = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.CheckTaskLookUpServlet?lookUpName=" + lookUpName;
	if(userPara != "undefined" && userPara != null){
        url += "&" + userPara;
    }
    dialogWidth = g_dialogWidth;
    dialogHeight = g_dialogHeight;
    var popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:yes;help:no;resizable:yes";
//			window.open(url);
    return window.showModalDialog(url, null, popscript);
}

//任务下发个性化
function appendDTO2Table_AYC(tab, dtoObj, useOldData, uniqueField1,uniqueField2) {
    if (uniqueField1 == "undefined" || uniqueField1 == null) {
        return;
    }
    if (uniqueField2 == "undefined" || uniqueField2 == null) {
        return;
    }
    
    if (typeof(dtoObj[uniqueField1]) != "undefined" && typeof(dtoObj[uniqueField2]) != "undefined" ) {
        var dataExist = false;
        var newAssets = dtoObj[uniqueField1];
        var newAssets2 =  dtoObj[uniqueField2];
        var objs = document.getElementsByName(uniqueField1);
        var objs2 = document.getElementsByName(uniqueField2);
        if (objs) {
            if (objs.length) {
                for (var i = 0; i < objs.length; i++) {
                    if (objs[i].value == newAssets && objs2[i].value == newAssets2) {
                        dataExist = true;
                        break;
                    }
                }
            } else {
                if (objs.value == newAssets && objs2.value == newAssets2 ) {
                    dataExist = true;
                }
            }
        }
        
        if (!dataExist) {
        	appendDTORow_AYC(tab, dtoObj, useOldData);
        }
    } else {
    	appendDTORow_AYC(tab, dtoObj, useOldData);
    }
}

function appendDTORow_AYC(tab, dtoObj, useOldData) {
    if (!tab) {
        alert("表格对象不存在，系统无法处理");
        return;
    }
    if (!tab.rows) {
        alert("表格对象没有参考数据，系统无法处理");
        return;
    }
    if (!dtoObj) {
        alert("DTO数据传输对象为空，系统无法处理");
        return;
    }
    if (useOldData == "undefined" || useOldData == null) {
        useOldData = false;
    }
    var rows = tab.rows;
    var rowCount = rows.length;
    var row = rows[rowCount - 1];
    var newRow = null;
    if (rowCount == 1 && row.style.display == "none") {
        newRow = row;
    } else {
        newRow = row.cloneNode(true);
    }
    var children = newRow.childNodes;
    var child = null;
    var tagName = "";
    var nodeType = "";
    var srcId = "";
    var newId = "";
    var fieldNodes = null;
    var node = null;
    var nodeHtm = "";
    var foundDate = false;
    var index = -1;
    var elementName = "";
    var cellValue = "";
    for (var i = 0; i < children.length; i++) {//单元格循环
        child = children[i];
        tagName = child.tagName;
        if (tagName.toUpperCase() != "TD") {
            continue;
        }
        elementName = child.name;
        if (elementName) {
            var breaked = false;
            for (prop in dtoObj) {
                if (elementName == String(prop)) {
                    child.innerText = dtoObj[prop];
                    breaked = true;
                    break;
                }
            }
            if (!breaked && !useOldData) {
                child.innerText = "";
            }
        } else {
            cellValue = "";
            fieldNodes = child.childNodes;
            if (fieldNodes.length == 1 && fieldNodes[0].nodeName == "#text") {
                child.innerText = "";
            } else {
                for (var j = 0; j < fieldNodes.length; j++) {//单元格内表单域循环
                    node = fieldNodes[j];
                    srcId = node.id;
                    tagName = node.tagName;
                    nodeType = node.type;
                    elementName = node.name;
                    if (!srcId) {
                        continue;
                    }
                    index = srcId.indexOf(elementName);
                    if (index != 0) {
                        alert("表单域的ID必须以名称开头，请检查表单域“" + srcId + "”");
                        return;
                    }
                    var num = srcId.substring(elementName.length);
                    newId = elementName + (Number(num) + 1);
                    node.id = newId;
                    if (tagName == "SELECT") {
                        for (prop in dtoObj) {
                            if (elementName == String(prop)) {
                                selectSpecialOptionByItem(node, dtoObj[prop]);
                                break;
                            }
                        }

                    } else {
                        var breaked = false;
                        for (prop in dtoObj) {
                            if (elementName == String(prop)) {
                                node.value = dtoObj[prop];
                                breaked = true;
                                break;
                            }
                        }
                        if (!breaked && !useOldData && !foundDate) {
                            node.value = "";
                        }
                    }
                    if (nodeType == "checkbox" || nodeType == "radio") {
                        node.checked = false;
                    }
                    nodeHtm = node.outerHTML;
                    if (nodeHtm.indexOf("gfPop") > -1) {
                        foundDate = true;
                    }
                }
                if (foundDate) {//对日期特殊处理，因为其函数参数的问题
                    nodeHtm = node.outerHTML;
                    var oldGfPopHtm = findGfPop(nodeHtm);
                    var newGfPopHtm = new String(oldGfPopHtm);
                    var fromIndex = oldGfPopHtm.indexOf("(") + 1;
                    var middleIndex = oldGfPopHtm.indexOf(",");
                    var endIndex = oldGfPopHtm.indexOf(")");
                    var startDate = "";
                    var endDate = "";
                    var startValue = false;
                    var endValue = false;
                    if (middleIndex != -1) {
                        startDate = oldGfPopHtm.substring(fromIndex, middleIndex);
                        endDate = oldGfPopHtm.substring(middleIndex + 1, endIndex);
                        endDate = replaceStr(endDate, " ", "");
                    } else {
                        startDate = oldGfPopHtm.substring(fromIndex, endIndex);
                    }
                    if (isValidDate(startDate)) {
                        startValue = true;
                    }
                    if (isValidDate(endDate)) {
                        endValue = true;
                    }
                    var srcId = "";
                    if (middleIndex != -1) {
                        if (!startValue) {
                            srcId = getNumFromText(startDate);
                        } else {
                            srcId = getNumFromText(endDate);
                        }
                    } else {
                        if (!startValue) {
                            srcId = getNumFromText(startDate);
                        }
                    }
                    var newStartDate = "";
                    var newEndDate = "";
                    if (srcId != "") {
                        var index1 = -1;
                        var index2 = -1;
                        if (middleIndex != -1) {
                            if (!startValue) {
                                index1 = startDate.indexOf(srcId);
                            }
                            if (!endValue) {
                                index2 = endDate.indexOf(srcId);
                            }
                            if (startValue) {
                                newStartDate = startDate;
                            } else {
                                newStartDate = startDate.substring(0, index1) + (Number(srcId) + 1);
                            }
                            if (endValue) {
                                newEndDate = endDate;
                            } else {
                                newEndDate = endDate.substring(0, index2) + (Number(srcId) + 1);
                            }
                        } else {
                            if (!startValue) {
                                index1 = startDate.indexOf(srcId);
                                newStartDate = startDate.substring(0, index1) + (Number(srcId) + 1);
                            } else {
                                newStartDate = startDate;
                            }
                        }
                    } else {
                        if (startValue) {
                            newStartDate = startDate;
                        } else {
                            newStartDate = startDate + "1";
                        }
                        if (endValue) {
                            newEndDate = endDate;
                        } else {
                            newEndDate = endDate + "1";
                        }
                    }
                    newGfPopHtm = newGfPopHtm.replace(startDate, newStartDate);
                    newGfPopHtm = newGfPopHtm.replace(endDate, newEndDate);
                    nodeHtm = nodeHtm.replace(oldGfPopHtm, newGfPopHtm);
                    node.outerHTML = nodeHtm;
                    foundDate = false;
                }
            }
        }
    }
    newRow.style.display = "block";
    row.parentNode.appendChild(newRow);
}

//删除type类型的任务行
function deleteTableRow_AYC(tab, checkboxName,type) {
    if (!tab || !checkboxName) {
        return;
    }
    var rowCount = tab.rows.length;
    if (rowCount == 0) {
        //alert("不存在要删除的行。");
        return;
    }
    var boxArr = getCheckBoxOfType(checkboxName,type);
    var chkCount = boxArr.length;
    if (chkCount < 1) {
       // alert("请先选择要删除的行！");
        return;
    }
    //if (confirm("确定要删除选中的行吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")) {
        var chkObj = null;
        for (var i = 0; i < chkCount; i++) {
            chkObj = boxArr[i];
            if (tab.rows.length > 1) {
                delTableRow(tab, chkObj);
            } else {
                clearContent(tab, chkObj);
                tab.rows[0].style.display = "none";
            }
        }
   // }
}

//获取type类型的任务的CheckBox
function getCheckBoxOfType(groupCheckboxName,type) {
    var checkedBox = new Array();
    var allCheckObj = document.all[groupCheckboxName];
    var types = document.getElementsByName("orderType");
    var checkboxLength = allCheckObj.length;
    if (checkboxLength) {
        if (checkboxLength) {
            for (var i = 0; i < checkboxLength; i++) {
            	if(types[i].value == type ){
            		 checkedBox.push(allCheckObj[i]);
            	}
                //if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) {
                //    checkedBox.push(allCheckObj[i]);
                //}
            }
        }
    } else {
    	if(types.value == type){
    		checkedBox.push(allCheckObj);
    	}
    		//if(allCheckObj.checked){
        	//	checkedBox.push(allCheckObj);
      	//}
    }
    return checkedBox;
}