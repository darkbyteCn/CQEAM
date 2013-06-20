/**
 * @author mshtang
 */

//===============================================================================================
//第五部分：以下处理复选框操作
//===============================================================================================

/**
 * 功能：由一个控制性复选框checkBoxName控制另一组复选框checkBoxGroup
 * checkBoxName：控制性复选框组件名

 * checkBoxGroup：被控制性复选框组件名

 */
function checkAll(checkBoxName, checkBoxGroup) {
	var objs = document.all[checkBoxName];
	if(!objs){
		return;
	}
	var checkAttr = true;
	if(objs.length){
		var obj = null;
		for(var i = 0; i < objs.length; i++){
			obj = objs[i];
			if(obj.type == "checkbox"){
				checkAttr = obj.checked;
				break;
			}
		}
	} else {
		checkAttr = objs.checked;
	}
    try {
        if (!setCheckBoxState(checkBoxGroup, checkAttr)) {
            document.all[checkBoxName].checked = !checkAttr;
        }
    } catch(exception) {
        alert(exception);
    }
}
function setCheckBoxState(checkBoxGroup, isChecked) {
    var retVal = true;
    var checkGroup = document.all[checkBoxGroup];
    if (!checkGroup) {
        return retVal;
    }
    var groupCount = checkGroup.length;
    if (groupCount) {
        var i = 0;
        var chkObj = null;
        if (groupCount > 200) {
            if (confirm("数据量过大，全选可能需要较长时间，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")) {
                for (i = 0; i < groupCount; i++) {
                    chkObj = checkGroup[i];
                    if (chkObj.type == "checkbox" && !chkObj.disabled) {
                        chkObj.checked = isChecked;
                    }
                }
            } else {
                retVal = false;
            }
        } else {
            for (i = 0; i < groupCount; i++) {
                chkObj = checkGroup[i];
                if (chkObj.type == "checkbox" && !chkObj.disabled) {
                    chkObj.checked = isChecked;
                }
            }
        }
    } else {
        checkGroup.checked = isChecked;
    }
    return retVal;
}
/**
 * 功能：获取一组复选框checkBoxName中被选中的值，值之间用splitor分开
 * checkBoxName：复选框组件名
 * splitor：分隔符
 */
function getCheckBoxValue(checkBoxName, splitor) {
    var checkBox = document.all[checkBoxName];
    splitor = (splitor == null) ? ";" : splitor;
    var retVal = "";
    var multipleCheck = 0;
    try {
        if (checkBox) {
            if (checkBox.type == "checkbox" && checkBox.checked) {
                retVal = checkBox.value;
            }
            for (var i = 0; i < checkBox.length; i++) {
                if (checkBox[i].type == "checkbox" && checkBox[i].checked) {
                    retVal += checkBox[i].value + splitor;
                    multipleCheck++;
                }
            }
        }
    } catch(exception) {
    }
    if (multipleCheck > 0) {
        retVal = retVal.substring(0, retVal.length - splitor.length);
    }
    return retVal;
}


/**
 * 功能：获取某一组复选框checkBoxName中的数目
 * checkBoxName：复选框组件名
 * splitor：分隔符
 */
function getCheckBoxCount(checkBoxName) {
    var checkBox = document.all[checkBoxName];
    var checkedCount = 0;
    try {
        if (checkBox) {
            checkedCount = (checkBox.type == "checkbox") ? 1 : 0;
            for (var i = 0; i < checkBox.length; i++) {
                if (checkBox[i].type == "checkbox") {
                    checkedCount++;
                }
            }
        }
    } catch(exception) {
    }
    return checkedCount;
}


/**
 * 功能：获取某一组复选框checkBoxName中被选中的数目
 * checkBoxName：复选框组件名
 * splitor：分隔符
 */
function getCheckedBoxCount(checkBoxName) {
    var checkBox = document.all[checkBoxName];
    var checkedCount = 0;
    try {
        if (checkBox) {
            checkedCount = (checkBox.type == "checkbox" && checkBox.checked) ? 1 : 0;
            for (var i = 0; i < checkBox.length; i++) {
                if (checkBox[i].type == "checkbox" && checkBox[i].checked) {
                    checkedCount++;
                }
            }
        }
    } catch(exception) {
    }
    return checkedCount;
}

/**
 * 功能：获取某一组复选框groupCheckboxName中被选中的项的所有值
 * groupCheckboxName：复选框组件名
 * return 数组
 */
function getCheckedBoxValue(groupCheckboxName) {
    var selectedValue = new Array();
    var checkboxObj = document.all[groupCheckboxName];
    var checkboxLength = checkboxObj.length;
    if (checkboxLength) {
        if (checkboxLength) {
            for (var i = 0; i < checkboxLength; i++) {
                if (checkboxObj[i].type == "checkbox" && checkboxObj[i].checked) {
                    selectedValue.push(checkboxObj[i].value);
                }
            }
        }
    } else {
    		if(checkboxObj.checked){
        		selectedValue.push(checkboxObj.value);
      	}
    }
    return selectedValue;
}

/**
 * 功能：获取某一组复选框groupCheckboxName中被选中的项的所有对象
 * groupCheckboxName：复选框组件名
 * return 数组
 */
function getCheckedBox(groupCheckboxName) {
    var checkedBox = new Array();
    var allCheckObj = document.all[groupCheckboxName];
    var checkboxLength = allCheckObj.length;
    if (checkboxLength) {
        if (checkboxLength) {
            for (var i = 0; i < checkboxLength; i++) {
                if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) {
                    checkedBox.push(allCheckObj[i]);
                }
            }
        }
    } else {
    		if(allCheckObj.checked){
        		checkedBox.push(allCheckObj);
      	}
    }
    return checkedBox;
}

/**
 * 功能：使指定值的指定名称的复选框处于选中状态。多个值之间用分号隔开
 * checkBoxName：复选框组件名
 * val：指定值
 */
function makeBoxChecked(checkBoxName, val) {
    var checkBox = document.all[checkBoxName];
    var valueArr = val.split(";");
    try {
        if (checkBox) {
            if (checkBox.type == "checkbox" && checkBox.value == val) {
                checkBox.checked = true;
            }
            for (var i = 0; i < checkBox.length; i++) {
                for (var j = 0; j < valueArr.length; j++) {
                    if (checkBox[i].type == "checkbox" && checkBox[i].value == valueArr[j]) {
                        checkBox[i].checked = true;
                        break;
                    }
                }
            }
        }
    } catch(exception) {
    }
}
/**
 *功能:反向选择指定复选框
 * checkBoxName：复选框组件名
 */
function reverseCheck(checkBoxName) {
    var obj = eval('document.' + checkBoxName);
    for (var i = 0; i < obj.length; i++) {
        obj[i].checked = !obj[i].checked;
    }
}

/**
 *功能:反向选择指定复选框 (一个)
 * checkBoxName：复选框组件ID,如果是NAME,则必需是唯一值
 */
function simpleRevCheck(checkBoxName) {
    var obj = document.getElementById(checkBoxName);
    obj.checked = !obj.checked;
}
/**
 * 功能：得到选中复选框的值（返回唯一值，既把重复值过滤）
 * checkBoxName：复选框组件NAME
 */
function getDistinctCheckedBoxValue(checkBoxName) {
    var returnValues = new Array();
    var checkedValues = new Array();
    var checkedBoxes = document.getElementsByName(checkBoxName);
    var returnIndex = -1;
    var checkedIndex = -1;
    for (var i = 0; i < checkedBoxes.length; i++) {
        if (checkedBoxes[i].checked) {
            checkedValues[++checkedIndex] = checkedBoxes[i].value;
        }
    }
    for (var i = 0; i < checkedValues.length; i++) {
        if (i == 0) {
            returnValues[++returnIndex] = checkedValues[i];
        } else {
            if (checkedValues[i] == returnValues[returnIndex]) {
                continue;
            } else {
                returnValues[++returnIndex] = checkedValues[i];
            }
        }
    }
    return returnValues;
}