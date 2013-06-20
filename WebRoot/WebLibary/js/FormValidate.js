/**
 * @author mshtang
 */

//===============================================================================================
//第九部分：本部分为表单验证所需要的函数
//===============================================================================================

/**
 * 功能：如果要对表单域进行日期校验，可采用此方法设置希望的日期格式，仅能有一个日期格式生效。
 * datePattern：日期格式
 */
function setDatePattern(datePattern) {
    if (isValidDatePattern(datePattern)) {
        DEFAULT_DATE_PATTERN = datePattern.toUpperCase();
    } else {
        alert("设置了本函数库不可识别的日期格式，注意，本系统所能支持的日期格式为：“" + DATE_STANDARD_PATTERN + "”、 “" + DATE_LINE_PATTERN + "”、“" + DATE_SLOPE_PATTERN + "”、“" + DATE_DOT_PATTERN + "”以及“" + DATE_CHINESE_PATTERN + "”");
    }
}

/**
 * 注意事项如下：
 * 1.对于日期的校验，应当先设置日期格式，如果不设置，则默认校验格式为DATE_LINE_PATTERN，
 * 2.对于双精度类型数字的校验方式，fieldLabels传入方式示例："金额$2;重量$3"，表示表单域“金额”的输入接受两位小数以内的数字，
 * 表单域“重量”接受三位小数以内的数字；
 * 3.对于长度的校验方式，fieldLabels传入方式示例："6$用户姓名$20;0$用户住址$100;"，表示表单域“用户姓名”的字符长度必须在6到
 * 20之间，含边界；表单域“用户住址”的字符长度必须在0到100之间，即允许表单域“用户住址”不输入数据，如果输入数据，则长度必须
 * 在100个字符(含)以内；
 */

/**
 * 功能：有效性检查，返回true表示通过校验，false表示有非法输入存在。尤其适用于多行数据表单域名相同的情况。
 * @param {Object} fieldNames 校验组件名称，多个之间组合以半角分号连接
 * @param {Object} fieldLabels 校验组件中文标识，多个之间组合以半角分号连接。
 * @param {Object} validateType 校验类型，一次仅能执行一种校验，目前支持的校验类型见前面常量定义部分说明。
 */
function formValidate(fieldNames, fieldLabels, validateType) {
    var retVal = true;
    var fieldNameArr = fieldNames.split(";");
    var fieldLabelArr = fieldLabels.split(";");
    if(!exceptionValidate(fieldNameArr, fieldLabelArr)){
		retVal = false;
		return retVal;
	}
	var fieldName = "";
    var fieldType = "";
    var fieldValue = "";
    var fieldObj = null;
    var minLength = -1;
    var maxLength = -1;
    var alertMsg = "";
    var dotNum = -1;
	var hasBreaked = false;
	var hasError = false;
	var index = findIndexOfArr(VALIDATE_TYPE_ARR, validateType);
	if(index != -1){
		var funName = VALIDATE_TYPE_ARR[index];
		for(var i = 0; i < fieldNameArr.length; i++){
			fieldName = fieldNameArr[i];
			var fieldObjs = document.getElementsByName(fieldName);
			if(!fieldObjs || !fieldObjs.length || fieldObjs.length == 0){
				hasError = true;
				retVal = false;
				alertMsg = "欲检查的表单域名“"
				 	+ fieldName
				  	+ "”不存在，请检查页面“"
				   	+ location.href 
					+ "”或该Servlet导向的页面。";
					break;
			}
			for(var j = 0; j < fieldObjs.length; j++){
				fieldObj = fieldObjs[j];
	            fieldType = fieldObj.type;
	            fieldValue = fieldObj.value;
	            if (funName == "isLengthValid") {
	                var tempArr = fieldLabelArr[i].split("$");
	                if (tempArr.length == 3) {
	                    minLength = tempArr[0];
	                    maxLength = tempArr[2];
	                    fieldLabelArr[i] = tempArr[1];
	                    if (minLength > 0 || !isEmpty(fieldValue)) {
	                        retVal = eval(funName + "('" + escape(fieldValue) + "', '" + minLength + "', '" + maxLength + "')");
	                    }
	                } else {
	                    alert("负责该页编写的程序员调用方法存在错误！");
	                    retVal = false;
	                }
	            } else if (funName == "isDouble") {
	                var tempArr = fieldLabelArr[i].split("$");
	                if (tempArr.length == 2) {
	                    fieldLabelArr[i] = tempArr[0];
	                    dotNum = Number(tempArr[1]);
	                    if (!isEmpty(fieldValue)) {
	                        retVal = eval(funName + "('" + escape(fieldValue) + "', '" + dotNum + "')");
	                    }
	                } else {
	                    alert("负责该页编写的程序员调用方法存在错误！");
	                    retVal = false;
	                }
	            } else if (funName == "isFormatDate") {
	                if (!isEmpty(fieldValue)) {
	                    retVal = eval(funName + "('" + escape(fieldValue) + "', '" + DEFAULT_DATE_PATTERN + "')");
	                }
	            } else if (funName == "isPositiveNumber") {
	                if (!isEmpty(fieldValue)) {
	                    retVal = eval(funName + "('" + escape(fieldValue) + "')");
	                }
	            } else if (funName == "isPositiveInteger") {
	                if (!isEmpty(fieldValue)) {
	                    retVal = eval(funName + "('" + escape(fieldValue) + "')");
	                }
	            } else if (funName == "isDiscount") {
	                if (!isEmpty(fieldValue)) {
	                    retVal = eval(funName + "('" + escape(fieldValue) + "')");
	                }
	            } else {
	                if (funName == "isEmpty") {//
	                    retVal = !eval(funName + "('" + escape(fieldValue) + "')")
	                } else {
	                    if (!isEmpty(fieldValue)) {
	                        retVal = eval(funName + "('" + escape(fieldValue) + "')");
	                    }
	                }
	            }
	            if (!retVal) {
	                if (funName == "isLengthValid") {
	                    if (minLength == 0) {
	                        alertMsg = ALERT_MAG_HEAD + "“" + fieldLabelArr[i] + "”" + ALERT_MSG_ARR[index] + "最大不超过“" + maxLength + "”";
	                    } else {
	                        alertMsg = ALERT_MAG_HEAD + "“" + fieldLabelArr[i] + "”" + ALERT_MSG_ARR[index] + "最小为“" + minLength + "”，最大为“" + maxLength + "”";
	                    }
	                } else {
	                    if (funName == "isDouble") {
	                        ALERT_MSG_ARR[index] = replaceStr(ALERT_MSG_ARR[index], "$", "“" + dotNum + "”");
	                    } else if (funName == "isFormatDate") {
	                        ALERT_MSG_ARR[index] = replaceStr(ALERT_MSG_ARR[index], "$", "“" + DEFAULT_DATE_PATTERN + "”");
	                    }
	                    alertMsg = ALERT_MAG_HEAD + "“" + fieldLabelArr[i] + "”" + ALERT_MSG_ARR[index];
	                }
	                if (funName == "isEmpty") {
	                    if (fieldType.indexOf("select") == -1) {
	                        alertMsg = alertMsg.replace("或选择", "");
	                    } else {
	                        alertMsg = alertMsg.replace("填写或", "");
	                    }
	                }
	                alert(alertMsg);
	                if (fieldType != "checkbox" && fieldType != "radio" && fieldType.indexOf('select') == -1) {
	                    fieldObj.value = "";
	                    fieldObj.select();
	                    fieldObj.focus();
	                } else {
	                    fieldObj.focus();
	                }
					hasBreaked = true;
	                break;
	            } else {
	                fieldObj.value = trim(fieldValue);
	            }				
			}
			if(hasBreaked){
				break;
			}
		}
		if(hasError){
			alert(alertMsg);
		}
	}
    return retVal;
}


/**
 * 功能：用于内部方法的异常检查，不提供给程序员使用
 *
 */
function exceptionValidate(arr1, arr2) {
	var isValid = true;
	if(arr1 == "undefined"){
		isValid = false;
	} else if(arr2 == "undefined"){
		isValid = false;
	} else if(arr1 == null || arr2 == null) {
        isValid = false;
    } else if(arr1.length != arr2.length){
		alert("传入的参数不配对，如果你看到此对话框，请将此信息告诉开发人员");
		isValid = false;
	}
	return isValid;
}

/**
 *检查输入文本的长度,传入的均为数组,一个表单域也必须传入数组
**/
function valdateLength(fieldNames, fieldLabels, fieldLength){
    var isValid = true;
    for (var i = 0; i < fieldNames.length; i++) {
        if (document.getElementById(fieldNames[i]).value.length > fieldLength[i]) {
            alert(fieldLabels[i] + "的长度不能超过" + fieldLength[i] + "字符!");
            isValid = false;
            break;
        }
    }
    return isValid;
}