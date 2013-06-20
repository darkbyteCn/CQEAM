/**
 * @author mshtang
 */

//===============================================================================================
//第六部分：以下处理单选框操作
//===============================================================================================

/**
 * 功能：获取指定组名的单选框中被选中项的值
 * radioName：单选框组件名
 */

//王艳伟 05-09-15 修改

function getRadioValue(radioName) {
    var retVal = "";
    var radioBox = document.getElementsByName(radioName);
    if (radioBox) {
        if (radioBox.length) {
            for (var i = 0; i < radioBox.length; i++) {
                if (radioBox[i].type == "radio" && radioBox[i].checked) {
                    retVal = radioBox[i].value;
                    break;
                }
            }
        } else {
            if (radioBox.type == "radio" && radioBox.checked) {
                retVal = radioBox.value;
            }
        }
    }
    return retVal;
}

function getCheckedRadio(radioName) {
    var obj = null;
    var radioBox = document.getElementsByName(radioName);
    if (radioBox) {
        if (radioBox.length) {
            for (var i = 0; i < radioBox.length; i++) {
                if (radioBox[i].type == "radio" && radioBox[i].checked) {
                    obj = radioBox[i];
                    break;
                }
            }
        } else {
            if (radioBox.type == "radio" && radioBox.checked) {
                obj = radioBox;
            }
        }
    }
    return obj;
}
/**
 * 功能：获取指定单选框对象中被选中项的值
 * radioObj：单选对象
 */
function getRadioValueByObj(radioObj) {
    var retVal = "";
    if (radioObj.length) {
        for (var i = 0; i < radioObj.length; i++) {
            if (radioObj[i].type == "radio" && radioObj[i].checked) {
                retVal = radioObj[i].value;
                break;
            }
        }
    } else {
        if (radioObj.checked) {
            retVal = radioObj.value;
        }
    }
    return retVal;
}


/**
 * 功能：获取页面中指定名字的单选按钮的个数
 * radioName：单选框组件名
 */
function getRadioCount(radioName) {
    var radioBox = document.getElementsByName(radioName);
    var retVal = 0;
    try {
        if (radioBox) {
            if (radioBox.length) {
                retVal = radioBox.length;
            }
        }
    } catch(exception) {
        alert(exception);
    }
    return retVal;
}

/**
 * 功能：使指定值的指定名称的单复选框处于选中状态。
 * radioName：单选框组件名
 * val：指定值
 */
function makeRadioChecked(radioName, val) {
    var obj = document.getElementsByName(radioName);
    try {
        if (obj) {
            if (obj.length) {
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].type == "radio" && obj[i].value == val) {
                        obj[i].checked = true;
                        break;
                    }
                }
            } else {
                if (obj.type == "radio" && obj.value == val) {
                    obj.checked = true;
                }
            }
        }
    } catch(exception) {
        alert(exception);
    }
}

/**
 * 功能：清除单选按钮中的选定状态。
 * radioName：单选框组件名
 */
function clearRadioChecked(radioName) {
    var obj = document.getElementsByName(radioName);
    try {
        if (obj) {
            if (obj.length) {
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].type == "radio") {
                        obj[i].checked = false;
                    }
                }
            } else {
                if (obj.type == "radio") {
                    obj.checked = false;
                }
            }
        }
    } catch(exception) {
        alert(exception);
    }
}

/**
 * 功能：获得被选中的单选框处于单选框组中的位置。
 * radioName：单选框组件名
 */
function getCheckedRadioIndex(radioName) {
    var radioBox = document.getElementsByName(radioName);
    var retVal = 0;
    if (radioBox) {
        if (radioBox.length) {
            for (var i = 0; i < radioBox.length; i++) {
                if (radioBox[i].type == "radio" && radioBox[i].checked) {
                    retVal = i;
                    break;
                }
            }
        }
    }
    return retVal;
}

function disableRadio(radioName) {
    do_ProcessRadioDisableAttribute(radioName, true);
}

function enableRadio(radioName) {
    do_ProcessRadioDisableAttribute(radioName, false);
}

function do_ProcessRadioDisableAttribute(radioName, disableAttr){
    var radioBox = document.getElementsByName(radioName);
    if (radioBox) {
        for (var i = 0; i < radioBox.length; i++) {
            if (radioBox[i].type == "radio") {
                radioBox[i].disabled = disableAttr;
            }
        }
    }
}
