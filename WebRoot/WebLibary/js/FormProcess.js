/**
 * @author mshtang
 */

//===============================================================================================
//第七部分：以下为表单域普通校验的综合处理函数
//===============================================================================================

/**
 * 功能：将指定名称的表单的各个域中的值进行格式化，即去除各域值前后的空格
 * @param {Object} formName 表单名称
 */
function trimForm(formName) {
    var frmObj = eval('document.' + formName);;
    var allObj = frmObj.all;
    var len = allObj.length;
    var tmpVal = "";
    var objType = "";
    for (var i = 0; i < len; i++) {
        objType = allObj[i].type;
        if (objType == "text" || objType == "password" || objType == "textarea") {
            tmpVal = allObj[i].value;
            tmpVal = trim(tmpVal);
            allObj[i].value = tmpVal;
        }
    }
}

//===============================================================================================
//第八部分：为表单域赋值
//===============================================================================================

/**
 * 功能：用于为表单域赋值。适用场合：当要查看某条记录详细信息时。
 * 后台数据由类com.sino.base.web.FormData产生。在需要该方法执行
 * 的页面，程序员需要执行两种操作。一种是后台数据的产生类，即FormData，
 * 另一种操作是在相应的页面导入本函数库。
 *
 */
function initFrmValue() {
    var fieldArr = FORM_FIELD_ENDUE_STR.split(FIELD_SPLITOR);
    var totalFields = fieldArr.length;
    var tmpArr = null;
    var fieldName = "";
    var fieldValue = "";
    var fieldType = "";
    var fieldObj = null;
    for (var i = 0; i < totalFields; i++) {
        tmpArr = fieldArr[i].split(FIELD_NAME_VALUE_SPLITOR);
        if (tmpArr.length >= 2) {
            fieldName = tmpArr[0];
            fieldValue = tmpArr[1];
            //王艳伟11.20修改，解决元素数量问题
            fieldObj = document.getElementsByName(fieldName);
            if (fieldObj.length > 0) {
                if (fieldObj.length && fieldObj[0].type) {
                    fieldType = fieldObj[0].type;
                } else {
                    fieldType = fieldObj.type;
                }
                if (fieldType == "checkbox") {
                    makeBoxChecked(fieldName, fieldValue);
                } else if (fieldType == "radio") {
                    makeRadioChecked(fieldName, fieldValue);
                } else if (fieldType.indexOf("select") > -1) {
                    selectSpecialOption(fieldName, fieldValue);
                } else {
                    fieldObj[0].value = fieldValue;
                }
            }
        }
    }
}

/**
 * 功能:设置表单内所有元素不可用
 * @param {Object} frm 表单名
 */
function setFrmDisable(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
        frmObj.elements[i].disabled = true;
    }
}

/**
 * 功能:设置表单内所有元素可用
 * @param {Object} frm 表单名
 */
function setFrmEnable(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
        frmObj.elements[i].disabled = false;
    }
}

/**
 * 功能：用于检查详细信息展示或修改时选中的记录数。
 * @param {Object} checkboxName 代表记录的复选框名称
 */
function validateDetailCheck(checkboxName) {
    var retVal = true;
    var checkedCount = getCheckedBoxCount(checkboxName);
    if (checkedCount != 1) {
        alert(UPDATE_MSG);
        retVal = false;
    }
    return retVal;
}

/**
 * 功能：用于检查删除时选中的记录数。
 * @param {Object} checkboxName 代表记录的复选框名称
 */
function validaeDeleteCheck(checkboxName) {
    var retVal = true;
    var checkedCount = getCheckedBoxCount(checkboxName);
    if (checkedCount == 0) {
        alert(DELETE_CHECK_MSG);
        retVal = false;
    }
    return retVal;
}

/**
 * 功能：用于确认是否删除。
 */
function confirmDelete() {
    var retVal = false;
    if (confirm(DELETE_MSG)) {
        retVal = true;
    }
    return retVal;
}

/**
 * 功能：用于确认是否放弃本次工作。
 */
function confirmCancel() {
    var retVal = false;
    if (confirm(CANCEL_MSG)) {
        retVal = true;
    }
    return retVal;
}

/**
 * 功能：将一个表单中的域值复制到另一表单同名域中。
 * @param {Object} srcForm 源表单
 * @param {Object} objForm 目标表单
 */
function copyFrmValue(srcForm, objForm) {
    var srcFrm = srcForm;
    if (!srcFrm.name) {
        srcFrm = document.forms[srcForm];
    }
    if (!srcFrm.name) {
        srcFrm = document.getElementById[srcForm];
    }
    var objFrm = objForm;
    if (!objFrm.name) {
        objFrm = document.forms[objForm];
    }
    if (!objFrm.name) {
        objFrm = document.getElementById[objForm];
    }
    if (!srcFrm || !objFrm) {
        alert("传入的参数非法，不存在指定的表单对象");
        return;
    }
    var srcFields = srcFrm.all;
    var objFields = objFrm.all;
    var srcField = null;
    var srcType = "";
    var fieldName = "";
    var standardCopyType = new Array("text", "textarea", "password", "hidden", "select");
    for (var i = 0; i < srcFields.length; i++) {
        srcField = srcFields[i];
        srcType = srcField.type;
        fieldName = srcField.name;
        if (!srcType || !isExistInArr(standardCopyType, srcType, true) && srcType.indexOf("select") == 1) {
            continue;
        }
        if (!objFrm.elements(fieldName)) {
            continue;
        }
        objFrm.elements(fieldName).value = srcField.value;
    }
}

/**
 * 功能：在文本域在上onkeydown事件，刚该文本域只能输入浮点数
 * @param {Object} value 表单值
 */
function floatOnly(value) {
    if ((event.keyCode == 110) | (event.keyCode == 190)) {
        if (value.match(/\.\d*/g, '.')) {
            event.returnValue = false;
        }
    }
    if (event.keyCode == 45) {
        event.returnValue = false;
    }
    if (((event.keyCode >= 48) & (event.keyCode <= 57)) | ((event.keyCode <= 105) & (event.keyCode >= 96)) | (event.keyCode == 110) | (event.keyCode == 190) | (event.keyCode == 8) | (event.keyCode == 37) | (event.keyCode == 39))
    //               0             9               9             0               .             .            backspace           left         right
    {
    } else if (event.keyCode == 46) {
        event.returnValue = true;
    } else {
        event.returnValue = false;
    }
}

/**
 * 功能：按下enter键时自动执行指定无参函数。例如：autoExeFunction(do_selectVendors());
 * @param {Object} functionName
 */
function autoExeFunction(functionName) {
    if (event.keyCode == 13) {
        eval(functionName);
    }
}

/**
 * 功能：按上下键时自动将光标跳到下一个对象。但如果中间有删除行则会在删除行的位置不起作用，有待改进
 * @param {Object} objName
 * @param {Object} id
 */
function moveCursor(objName, id){
    var obj = document.getElementById(objName+id);
    var nextId = -1;
    var tempObj;
    if(event.keyCode==38){ //up
        nextId = Number(id)-1;
    }else if(event.keyCode==40){ //down
        nextId = Number(id)+1;
    }
    if(nextId >= 0 ){
        tempObj = document.getElementById(objName+nextId);
        if(tempObj){
            tempObj.focus();
        }
    }
}

/**
 * 功能：在指定层中显示页面元素pageObj的alt值。
 * @param {Object} divId 层的ID号；
 * @param {Object} pageObj 页面元素对象
 * @param {Object} addText 附加显示文本
 */
function showObjValue(divId, pageObj, addText) {
    var divObj = document.getElementById(divId);
    var top = pageObj.offsetTop;
    //TT控件的定位点高
    var height = pageObj.clientHeight;
    //TT控件本身的高
    var left = pageObj.offsetLeft;
    //TT控件的定位点宽
    var objType = pageObj.type;
    //TT控件的类型
    while (pageObj = pageObj.offsetParent) {
        top += pageObj.offsetTop;
        left += pageObj.offsetLeft;
    }
    divObj.style.top = top + height;
    if (objType != "image") {
        divObj.style.top = top + height + 6;
    }
    if (left > 400) {
        left = 400;
    }
    divObj.style.left = left;
    //层的 X 坐标
    divObj.style.display = "block";
    //层显示
    if (addText != "") {
        divObj.innerText = addText + "：" + event.srcElement.alt;
    } else {
        divObj.innerText = event.srcElement.alt;
    }
}

/**
 * 功能：将DTO对象相应属性的值赋予指定名称表单的对应域
 * @param {Object} dto DTO对象
 * @param {Object} formName 表单名
 */
function dto2Frm(dto, formName){
var frmObj = eval("document." + formName);
var children = frmObj.elements;
var child = null;
var childType = null;
var fieldName = "";

    for(var i = 0; i < children.length; i++){

        child = children[i];
				childType = child.type;
				fieldName = child.name;

        if(childType!=undefined)
        {
            if(childType == "text" || childType == "textarea" || childType== "hidden"){
                for(prop in dto){
                    if(fieldName == String(prop)){
                        child.value = dto[prop];
                    }
                }
            } else if(childType.indexOf("select") > -1){
                for(prop in dto){
                    if(fieldName == String(prop)){
                        selectSpecialOptionByItem(child, dto[prop]);
                    }
                }
            }
        }
    }
}

/**
 * 功能：判断指定的表单域值是否全空
 * @param {Object} fieldNames 表单域名，多个域名用半角分号连接
 * @return 全空则返回true，否则返回false
 */
function isAllEmapty(fieldNames){
	var retValue = true;
	if(!fieldNames){
		alert("没有指定表单域");
		return false;
	}
	var fieldArr = fieldNames.split(";");
	if(fieldArr){
		var fieldValue = ""
		var fieldName = "";
		var fields = null;
		var fieldCount = 0;
		var hasBreaked = false;
		for(var i = 0; i < fieldArr.length; i++){
			fieldName = fieldArr[i];
			fields = document.getElementsByName(fieldName);
			if(!fields){
				continue;
			}
			if(fields.length){
				fieldCount = fields.length;
				for(var j = 0; j < fieldCount; j++){
					fieldValue += fields[j].value;
					if(fieldValue != ""){
						retValue = false;
						break;
					}
				}
				if(!retValue){
					break;
				}
			} else {
				fieldValue += fields.value;
				if(fieldValue != ""){
					retValue = false;
					break;
				}
			}
		}
	}
	return retValue;
}

/**
 * 功能：清除表单指定域的值
 * @param {Object} fieldNames 表单域名，多个域名用半角分号连接
 */
function clearFieldValue(fieldNames){
	if(!fieldNames){
		alert("没有指定表单域");
		return;
	}
	var fieldArr = fieldNames.split(";");
	if(fieldArr){
		var fieldName = "";
		var fields = null;
		var fieldCount = 0;
		for(var i = 0; i < fieldArr.length; i++){
			fieldName = fieldArr[i];
			fields = document.getElementsByName(fieldName);
			if(!fields){
				continue;
			}
			if(fields.length){
				fieldCount = fields.length;
				for(var j = 0; j < fieldCount; j++){
					fields[j].value = "";
				}
			} else {
				fields.value = "";
			}
		}
	}
}



/**
 *功能:设置表单内所有元素不可用
 *参数:表单名
 */
function setFrmReadonly(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
    	var obj = frmObj.elements[i];
    	var objType = obj.type;
        var fieldType = obj.type;
        obj.readOnly = true; 
        if( objType == "text" || objType == "password" || objType == "textarea" ){
        	obj.onclick = null;
        } 
        
        if( fieldType == "checkbox" ){
        	obj.disabled = true;
        }
    }
} 