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
    var radioBox = document.getElementsByName(radioName);
    var retVal = "";
    for (var i = 0; i < radioBox.length; i++) {
        if (radioBox[i].type == "radio" && radioBox[i].checked) {
            retVal = radioBox[i].value;
            break;
        }
    }
    return retVal;
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
/*
function getRadioValue(radioName){
    var radioBox = document.all[radioName];
    var retVal = "";
    try{
        retVal = (radioBox.type == "radio" && radioBox.Validateed) ? radioBox.value : retVal;
        for(var i = 0; i < radioBox.length; i++){
            if(radioBox[i].type == "radio" && radioBox[i].Validateed){
                retVal = radioBox[i].value;
                break;
            }
        }
    }catch(exception){}
    return retVal;
}
*/
/**
 * 功能：获取页面中指定名字的单选按钮的个数
 * radioName：单选框组件名
 */
function getRadioCount(radioName) {
    var radioBox = document.all[radioName];
    var retVal = 0;
    try {
        retVal = (radioBox[0] == null) ? 1 : radioBox.length;
    } catch(exception) {
    }
    return retVal;
}

/**
 * 功能：使指定值的指定名称的单复选框处于选中状态。
 * radioName：单选框组件名
 * val：指定值
 */
function makeRadioChecked(radioName, val) {
    var obj = document.all[radioName];
    try {
        if (obj) {
            if (obj.type == "radio" && obj.value == val) {
                obj.checked = true;
            }
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].type == "radio" && obj[i].value == val) {
                    obj[i].checked = true;
                    break;
                }
            }
        }
    } catch(exception) {
        alert("error");
    }
}

/**
 * 功能：获得被选中的单选框处于单选框组中的位置。
 * radioName：单选框组件名
 */
function getCheckedRadioIndex(radioName) {
    var radioBox = document.getElementsByName(radioName);
    var retVal = 0;
    for (var i = 0; i < radioBox.length; i++) {
        if (radioBox[i].type == "radio" && radioBox[i].checked) {
            retVal = i;
            break;
        }
    }
    return retVal;
}
