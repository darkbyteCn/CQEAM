/**
 * @author mshtang
 */
//===============================================================================================
//第二部分：以下用于字符串的相关操作
//===============================================================================================

/**
 * 功能：构造新的字符串，其值为count个str相连
 * @param {Object} srcStr 源字符串
 * @param {Object} count 源字符串的个数
 */
function getStrByCount(srcStr, count) {
    var retStr = "";
    for (var i = 0; i < count; i++) {
        retStr += srcStr;
    }
    return retStr;
}

/**
 * 功能：获取srcStr中含有子字符串subStr的个数
 * @param {Object} srcStr 源字符串
 * @param {Object} subStr 子字符串
 */
function getContainNum(srcStr, subStr) {
    var tempStr = "";
    var strCount = 0;
    if (srcStr != "" && subStr != "" && srcStr.length >= subStr.length) {
        var index = -1;
        while ((index = srcStr.indexOf(subStr)) != -1) {
            strCount++;
            srcStr = srcStr.substring(index + subStr.length);
        }
    }
    return strCount;
}

/**
 * 功能：判断srcStr中是否包含subStr
 * @param {Object} srcStr 源字符串
 * @param {Object} subStr 子字符串
 */
function contains(srcStr, subStr) {
    return (containNum(srcStr, subStr) > 0);
}

/**
 * 功能：将字符串前后空格去掉
 * @param {Object} srcStr 源字符串
 */
function trim(srcStr) {
    while (srcStr.charAt(0) == " ") {
        srcStr = srcStr.substring(1);
    }
    while (srcStr.charAt(srcStr.length - 1) == " ") {
        srcStr = srcStr.substring(0, srcStr.length - 1);
    }
    return srcStr;
}
/**
 * 功能：判断某字符串是否为空或全空格，用于需要非空校验的地方
 * @param {Object} srcStr 源字符串
 */

function isEmpty(srcStr) {
	var retVal = true;
	if(srcStr != "undefined"){
	    srcStr = unescape(srcStr);
		retVal = (srcStr == "" || trim(srcStr) == "");
	}
    return retVal;
}

/**
 * 功能：判断某字符串是否是正确的Email.
 * @param {Object} srcStr 源字符串
 */
function isEmail(srcStr) {
    var retVal = false;
    srcStr = trim(srcStr);
    if (!isEmpty(srcStr) && srcStr.length > 5 && getContainNum(srcStr, "@") == 1) {
        var index = srcStr.indexOf(".");
        if (index > 0) {
            retVal = true;
        }
    }
    return retVal;
}

/**
 * 功能：判断某字符串是否是正确的移动电话
 * @param {Object} srcStr 源字符串
 */
function isMobile(srcStr){
    var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
    if (!patrn.exec(srcStr)) return false
    return true
}

 /**
 * 功能：校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
 * @param {Object} srcStr 源字符串
 */
function isTelNumber(srcStr)
{
//var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?(\d){1,12})+$/;
var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
if (!patrn.exec(srcStr)) return false
return true
}

/**
 * 功能：判断某字符串str是否以subStr开始
 * @param {Object} srcStr 源字符串
 * @param {Object} subStr 子字符串
 * @param {Object} ignoreCase 是否忽略大小写
 */
function startsWith(srcStr, subStr, ignoreCase) {
    if (ignoreCase) {
        srcStr = srcStr.toLowerCase();
        subStr = subStr.toLowerCase();
    }
    return (srcStr.length >= subStr.length && srcStr.substring(0, subStr.length) == subStr);
}

/**
 * 功能：判断某字符串str是否以subStr结束
 * @param {Object} srcStr 源字符串
 * @param {Object} subStr 子字符串
 * @param {Object} ignoreCase 是否忽略大小写
 */
function endsWith(srcStr, subStr, ignoreCase) {
    if (ignoreCase) {
        srcStr = srcStr.toLowerCase();
        subStr = subStr.toLowerCase();
    }
    return (srcStr.length >= subStr.length && srcStr.substring(srcStr.length - subStr.length) == subStr);
}

/**
 * 功能：检查某字符串是否是数字
 * @param {Object} srcStr 源字符串
 */
function isNumber(srcStr) {
    return (srcStr != "" && !isNaN(srcStr));
}

/**
 * 功能：检查某字符串srcStr是否是数字，且小数点后的位数在指定的位数dotNum之内
 * @param {Object} srcStr 源字符串
 * @param {Object} dotNum 小数点后的位数
 */
function isDouble(srcStr, dotNum) {
    var retVal = false;
    srcStr = unescape(srcStr);
    if (isNumber(srcStr)) {
        retVal = true;
        var index = srcStr.indexOf(".");
        if (index != -1) {
            srcStr = srcStr.substring(index + 1);
            retVal = (srcStr.length <= dotNum);
        }
    }
    return retVal;
}

/**
 * 功能：检查某字符串是否整数
 * @param {Object} srcStr 源字符串
 */
function isInt(srcStr) {
    return (isNumber(srcStr) && srcStr % 1 == 0);
}

/**
 * 功能：格式化小数,有四舍五入功能
 * @param {Object} srcNum 源字符串
 * @param {Object} pos 保留小数点后几位，如2
 */
function formatNum(srcNum, pos) {
    return Math.round(srcNum * Math.pow(10, pos)) / Math.pow(10, pos);
}


/**
 * 功能：判断某字符串是否正数
 * @param {Object} srcStr 源字符串
 */
function isPositiveNumber(srcStr) {
    return (isNumber(srcStr) && Number(srcStr) > 0);
}

/**
 * 功能：判断某字符串是否正整数
 * param {Object} srcStr：源字符串
 */
function isPositiveInteger(srcStr) {
    return (isPositiveNumber(srcStr) && isInt(srcStr));
}


/**
 * 功能：判断某字符串是否在某数组中
 * @param {Object} srcArr 源数组
 * @param {Object} srcStr 源字符串
 * @param {Object} ignoreCase 是否忽略大小写
 */
function isExistInArr(srcArr, srcStr, ignoreCase) {
    var retVal = false;
    var tempLength = srcArr.length;
    if (ignoreCase) {
        srcStr = srcStr.toLowerCase();
    }
    for (var i = 0; i < tempLength; i++) {
        if (ignoreCase) {
            srcArr[i] = srcArr[i].toLowerCase();
        }
        if (srcArr[i] == srcStr) {
            retVal = true;
            break;
        }
    }
    return retVal;
}

/**
 * 功能：返回某字符串在某数组中的索引值，不存在则返回-1
 * @param {Object} srcArr 源数组
 * @param {Object} srcStr 源字符串
 */
function findIndexOfArr(srcArr, srcStr) {
    var retVal = -1;
    var tempLength = srcArr.length;
    for (var i = 0; i < tempLength; i++) {
        if (srcArr[i] == srcStr) {
            retVal = i;
            break;
        }
    }
    return retVal;
}

/**
 * 功能：从某字符串中获取数字组成的字符
 * @param {Object} srcStr 源数组
 */
function getNumFromText(srcStr) {
    var retStr = "";
    if (!isEmpty(srcStr)) {
        var strLength = srcStr.length;
        var thisChar = "";
        for (var i = 0; i < strLength; i++) {
            thisChar = srcStr.charAt(i);
            if (isInt(thisChar)) {
                retStr += thisChar;
            }
        }
    }
    return retStr;
}


/**
 * 功能：将srcStr中的所有oldStr全部替换成newStr
 * @param {Object} srcStr 源字符串
 * @param {Object} oldStr 原子字符串
 * @param {Object} newStr 新子字符串
 */
function replaceStr(srcStr, oldStr, newStr) {
    var retStr = srcStr;
    if(oldStr != ""){
	    var finalStr = srcStr
	    var tempStr = "";
	    var index = -1;
	    while ((index = srcStr.indexOf(oldStr)) != -1) {
	        tempStr += finalStr.substring(0, index) + newStr;
	        finalStr = finalStr.substring(index + oldStr.length);
	        srcStr = srcStr.substring(index + oldStr.length);
	    }
	    if (finalStr != "") {
	        tempStr += finalStr;
	    }
	    retStr = (tempStr != "") ? tempStr : retStr;
	  }
    return retStr;
}

/**
 * 功能：将srcStr中的第一个oldStr全部替换成newStr
 * @param {Object} srcStr 源字符串
 * @param {Object} oldStr 原子字符串
 * @param {Object} newStr 新子字符串
 */
function replaceFirst(srcStr, oldStr, newStr) {
    var retStr = "";
    var index = srcStr.indexOf(oldStr);
    if (index > -1) {
        retStr = srcStr.substring(0, index) + newStr;
        retStr += srcStr.substring(index + oldStr.length);
    } else {
        retStr = srcStr;
    }
    return retStr;
}

/**
 * 功能：检查某字符串的长度是否超出最大长度
 * @param {Object} srcStr 源字符串
 * @param {Object} maxLength 最大允许长度
 */
function isLengthExtend(srcStr, maxLength) {
    var subStr = "";
    var doubleByteCount = 0;
    for (var i = 0; i < srcStr.length; i++) {
        subStr = srcStr.charAt(i);
        if (STANDARD_STR.indexOf(subStr) == -1) {
            doubleByteCount++;
        }
    }
    return (srcStr.length > (maxLength - doubleByteCount));
}

/**
 * 功能：检查某字符串的长度是否小于最小长度
 * @param {Object} srcStr 源字符串
 * @param {Object} minLength 最小允许长度
 */
function isLengthLess(srcStr, minLength) {
    var subStr = "";
    var doubleByteCount = 0;
    for (var i = 0; i < srcStr.length; i++) {
        subStr = srcStr.charAt(i);
        if (STANDARD_STR.indexOf(subStr) == -1) {
            doubleByteCount++;
        }
    }
    return (srcStr.length < (minLength - doubleByteCount));
}

/**
 * 功能：检查某字符串长度是否在允许值范围内
 * @param {Object} srcStr 源字符串
 * @param {Object} minLength 最小允许长度
 * @param {Object} maxLength 最大允许长度
 */
function isLengthValid(srcStr, minLength, maxLength) {
    srcStr = unescape(srcStr);
    return (!isLengthExtend(srcStr, maxLength) && !isLengthLess(srcStr, minLength));
}

/**
 * 功能：将指定值添加到数组
 * @param {Object} arr 目标数组
 * @param {Object} newValue 新值
 */
function add2Arr(arr, newValue) {
    if (arr == null) {
        return;
    }
    arr[arr.length] = newValue;
    return arr;
}

/**
 * 功能：将数组中指定索引元素删除，并返回删除值
 * @param {Object} arr 数组
 * @param {Object} index 索引值
 */
function removeFromArrByIndex(arr, index) {
    if (arr == null || arr.length == 0) {
        return null;
    }
    if (arr.length - 1 < index) {
        return null;
    }
    returnValue = arr[index];
    arr.splice(index, 1);
    return returnValue;
}

/**
 * 功能：将数组中等于指定值的第一个元素删除，并返回该值。
 * @param {Object} arr 数组
 * @param {Object} item 指定值
 */
function removeFromArr(arr, item) {
    if (arr == null || arr.length == 0) {
        return null;
    }
    index = -1;
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == item) {
            index = i;
            break;
        }
    }
    if (index < 0) {
        return null;
    }
    return removeFromArrByIndex(arr, index);
}

/**
 * 功能：将指定字符串用分割字符串分割成数组
 * @param {Object} srcStr 指定字符串
 * @param {Object} spanStr 分割字符串
 */
function explode(srcStr, spanStr) {
    if(srcStr == undefined || srcStr == null || srcStr == ""){
        return null;
    }
    var str = trim(srcStr);
    if (str == "") {
        return new Array();
    }
    if (spanStr == null) {
        return new Array();
    }

    var ArrDes = new Array();
    var i = 0;
    var find = str.indexOf(spanStr);
    var spanlen = spanStr.length;
    while (find >= 0) {
        ArrDes[i] = str.substring(0, find);
        str = str.substr(find + spanlen);
        find = str.indexOf(spanStr);
        i++;
    }
    if (trim(str) != '') {
        ArrDes[i] = str;
    }
    return ArrDes;
}

/**
 * 功能：将数组arr的元素用指定连接字符串连接
 * @param {Object} arr 数组
 * @param {Object} spanStr 连接字符
 */
function implode(arr, spanStr) {
    var str = "";
    if (arr == null || arr.length <= 0) {
        return "";
    }
    for (i = 0; i < arr.length - 1; i++) {
        str += arr[i] + spanStr;
    }
    str += arr[i];
    return str;
}

/**
 * 功能：将数组中重复的值以数组的形式返回
 * @param {Object} array 源数组
 */
function retRepeatInArray(array) {
    var retArray = new Array();
    var arrayLength = -1;
    for (var i = 0; i < array.length; i++) {
        var sign = array[i];
        for (var j = i + 1; j < array.length; j++) {
            if (sign == array[j]) {
                arrayLength++;
                retArray[arrayLength] = sign;
                break;
            }
        }
    }
    return retArray;
}

/**
 * 功能：判断某字符串是否0到1之间的数字。
 * val：源字符串
 */
function isDiscount(val) {
    return (isNumber(val) && val > 0 && val <= 1);
}

/**
 * 功能：在文本域在上onkeyup事件，刚该文本域只能输入浮点数
 * value 表单值
 * 但应当允许用户进行删除操作.
 */
function floatOnly(obj, value) {
    var result = value;
    var strTemp = "0123456789.";
    for (var i = 0; i < result.length; i++)
    {
        var j = strTemp.indexOf(result.charAt(i));
        if (j == -1)
        {
            result = result.substring(0, i) + result.substring(i, result.length - 1);
            i--;
        }
    }
    obj.value = result
}

function patchCreateOrUpdate( id ){
	return ( id != "" && id >0 );
}

