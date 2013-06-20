/**
 * 【注意】：综合了较多操作和校验的Javascript函数库，由于时间的关系，目前该Javascript函数库
 * 尚未经过甚至初步的测试，一定还存在较多不足，希望大家多多使用，以发现其缺陷所在。调用示例
 * 尽管还没有，但每个函数的具体用途已经写得较为清楚。如果你有什么好的想法，也可加入其中，或
 * 者通过mshtang@163.com跟作者唐明胜联系。
 * 在本函数库经过测试成功之后，在今后的项目或者产品开发中就应最大程度地使用它，以节省我们的
 * 开发时间，让它发挥应有的光和热。
 * 本函数库目前分为七个部分，在下面简单介绍。如你需要了解某一部分的相应函数，可通过搜索【部分】
 * 两个字来定位到相应的位置，免去你的麻烦。
 * 第一部分：常量定义
 * 第二部分：字符串或数组操作
 * 第三部分：日期操作
 * 第四部分：下拉框操作
 * 第五部分：复选框操作
 * 第六部分：单选框操作
 * 第七部分：表单校验(该部分最需要完善，目前作者思路还不是很全)
 * 第八部分：为表单域赋值

 * 第九部分：为表单域赋值
 * CopyRight：上海辉拓公司版权所有2005~
 */


//===============================================================================================
//第一部分：以下定义本函数库用到的常量
//===============================================================================================

/**
 * 1.定义本Javascript函数库的普通常量
 */
var EMPTY_SPACE = " ";
//空格字符串
var NUM_STR = "0123456789";
//数字字符串;

/**
 * 2.定义本Javascript函数库所能接受的日期格式常量
 */

var DATE_STANDARD_PATTERN = "YYYYMMDD";
var DATE_LINE_PATTERN = "YYYY-MM-DD";
var DATE_SLOPE_PATTERN = "YYYY/MM/DD";
var DATE_DOT_PATTERN = "YYYY.MM.DD";
var DATE_CHINESE_PATTERN = "YYYY年MM月DD日";
var LIMIT_DATE_PATTERN = new Array(DATE_STANDARD_PATTERN, DATE_LINE_PATTERN, DATE_SLOPE_PATTERN, DATE_DOT_PATTERN, DATE_CHINESE_PATTERN);

/**
 * 3.定义本Javascript函数库所能支持的普通标准校验方式，以及标准提示信息
 */

var EMPTY_VALIDATE = "isEmpty";
var DATE_VALIDATE = "isFormatDate";
var EMAIL_VALIDATE = "isEmail";
var NUMBER_VALIDATE = "isNumber";
var INT_VALIDATE = "isInt";
var DOUBLE_VALIDATE = "isDouble";
var LENGTH_VALIDATE = "isLengthValid";
var POSITIVE_VALIDATE = "isPositiveNumber";
var POSITIVE_INT_VALIDATE = "isPositiveInteger";
var DISCOUNT_VALIDATE = "isDiscount";
var VALIDATE_TYPE_ARR = new Array(EMPTY_VALIDATE, DATE_VALIDATE, EMAIL_VALIDATE, NUMBER_VALIDATE, INT_VALIDATE, DOUBLE_VALIDATE, LENGTH_VALIDATE, POSITIVE_VALIDATE, POSITIVE_INT_VALIDATE, DISCOUNT_VALIDATE);

/**
 * 4.定义本Javascript函数库提供的标准提示信息
 */

var ALERT_MAG_HEAD = "输入非法，原因是：";
//提示信息开头部分；
var EMPTY_MESSAGE = "必须填写或选择，不能为空！";
var DATE_MESSAGE = "要求输入格式为$的日期。如果格式正确，请检查该输入日期是否不存在！";
var EMAIL_MESSAGE = "要求输入格式合法的Email！";
var NUMBER_MESSAGE = "要求输入合法数字！";
var INT_MESSAGE = "要求输入合法整数！";
var DOUBLE_MESSAGE = "要求输入数字，且其小数位数不超过$位！";
var LENGTH_MESSAGE = "要求输入内容的字符长度";
var POSITIVE_MSG = "要求输入的数字是正数";
var POSITIVE_INT_MSG = "要求输入的数字是正整数";
var DISCOUNT_MSG = "要求输入的数字在区间(0,1]内！";
var ALERT_MSG_ARR = new Array(EMPTY_MESSAGE, DATE_MESSAGE, EMAIL_MESSAGE, NUMBER_MESSAGE, INT_MESSAGE, DOUBLE_MESSAGE, LENGTH_MESSAGE, POSITIVE_MSG, POSITIVE_INT_MSG, DISCOUNT_MSG);

/**
 * 5.定义本Javascript函数库在进行日期校验时，采用的默认日期格式
 */
var DEFAULT_DATE_PATTERN = DATE_LINE_PATTERN;

/**
 * 6.定义标准字符常量
 */
var STANDARD_STR = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.+-*/`~!@#$%^&*()_=|\\;:'\"<>,?/";

/**
 * 7.为表单域赋值的函数需要的变量
 */
var FORM_FIELD_ENDUE_STR = "";
var FIELD_NAME_VALUE_SPLITOR = "$$$$$";
var FIELD_SPLITOR = "$$$$$$$";
/**
 * 8.对页面点击修改或删除数据库记录时的提示信息
 */
var UPDATE_MSG = "一次只能显示或修改一条记录，不能不选或多选。";
var DELETE_CHECK_MSG = "没有选中要删除的记录，一次可以删除一条或多条记录，但不能不选。";
var DELETE_MSG = "数据删除后可能无法恢复，确定要删除吗？要继续请点击“确定”按钮，否则点击“取消”按钮。";
var SAVE_MSG = "数据保存后可能无法恢复，确定要保存吗？要继续请点击“确定”按钮，否则点击“取消”按钮。";
var CANCEL_MSG = "确定放弃本次工作吗？继续请点击“确定”按钮，否则请点击“取消”按钮！";

//===============================================================================================
//第二部分：以下用于字符串的相关操作
//===============================================================================================
/**
 * 功能：构造新的字符串，其值为count个str相连
 * srcStr：源字符串
 * count：源字符串的个数
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
 * srcStr：源字符串
 * subStr：子字符串
 */
function getContainNum(srcStr, subStr) {
    var tempStr = "";
    var containNumber = 0;
    if (srcStr != "" && subStr != "" && srcStr.length >= subStr.length) {
        var index = -1;
        while ((index = srcStr.indexOf(subStr)) != -1) {
            containNumber++;
            srcStr = srcStr.substring(index + subStr.length);
        }
    }
    return containNumber;
}
/**
 * 功能：判断srcStr中是否包含subStr
 * srcStr：源字符串
 * subStr：子字符串
 */
function contains(srcStr, subStr) {
    return (containNum(srcStr, subStr) > 0);
}
/**
 * 功能：将字符串前后空格去掉
 * srcStr：源字符串
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
 * srcStr：源字符串
 */
function isEmpty(srcStr) {
    srcStr = unescape(srcStr);
    return (srcStr == "" || trim(srcStr) == "");
}
/**
 * 功能：判断某字符串是否是正确的Email.
 * srcStr：源字符串
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
 * 功能：判断某字符串str是否以subStr开始
 * srcStr：源字符串
 * subStr：子字符串
 * ignoreCase：是否忽略大小写
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
 * srcStr：源字符串
 * subStr：子字符串
 * ignoreCase：是否忽略大小写
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
 * srcStr：源字符串
 */
function isNumber(srcStr) {
    return (srcStr != "" && !isNaN(srcStr));
}
/**
 * 功能：检查某字符串srcStr是否是数字，且小数点后的位数在指定的位数dotNum之内
 * srcStr：源字符串
 * dotNum：小数点后的位数
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
 * srcStr：源字符串
 */
function isInt(srcStr) {
    return (isNumber(srcStr) && srcStr % 1 == 0);
}
/**
 * 功能：格式化数字
 * srcStr：源字符串
 * pattern：目标格式，例如00.00
 */
function formatNum(srcNum, pattern) {
    var returnNum = srcNum;
    if (getContainNum(pattern, ".") <= 1) {
        var tempStr = new String(srcNum);
        var sourceLen = tempStr.length;
        var patterLen = pattern.length;
        var patternIndex = pattern.indexOf(".");
        var sourceIndex = tempStr.indexOf(".");
        var leftObjectLen = (patternIndex == -1) ? patterLen : patternIndex;
        var rightObjectLen = (patternIndex == -1) ? 0 : patterLen - patternIndex - 1;
        var leftNum = (sourceIndex != -1) ? tempStr.substring(0, sourceIndex) : tempStr;
        var rightNum = (sourceIndex != -1) ? tempStr.substring(sourceIndex + 1) : "";
        var minusLen = 0;
        if (patternIndex == -1) {
            minusLen = leftObjectLen - leftNum.length;
            tempStr = (minusLen > 0) ? getStrByCount("0", minusLen) + tempStr : tempStr
        } else {
            if (sourceIndex != -1) {
                minusLen = leftObjectLen - leftNum.length;
                leftNum = (minusLen > 0) ? getStrByCount("0", minusLen) + leftNum : leftNum;
                minusLen = rightObjectLen - rightNum.length;
                rightNum = (minusLen > 0) ? rightNum + getStrByCount("0", minusLen) : rightNum;
                rightNum = (minusLen < 0) ? rightNum.substring(0, rightObjectLen) : rightNum;
            } else {
                minusLen = leftObjectLen - leftNum.length;
                leftNum = (minusLen > 0) ? getStrByCount("0", minusLen) + leftNum : leftNum;
                minusLen = rightObjectLen;
                rightNum = getStrByCount("0", minusLen);
            }
            tempStr = leftNum + "." + rightNum;
        }
        returnNum = tempStr;
    }
    return tempStr;
}

/**
 * 功能：格式化小数,有四舍五入功能
 * src：源字符串
 * pos：保留小数点后几位，如2
 */
function formatFloat(src, pos) {
    return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
}

/**
 * 功能：判断某字符串是否正数
 * srcStr：源字符串
 */
function isPositiveNumber(srcStr) {
    return (isNumber(srcStr) && Number(srcStr) > 0);
}

/**
 * 功能：判断某字符串是否正整数
 * srcStr：源字符串
 */
function isPositiveInteger(srcStr) {
    return (isPositiveNumber(srcStr) && isInt(srcStr));
}


/**
 * 功能：判断某字符串是否在某数组中
 * srcArr：源数组
 * srcStr：源字符串
 * ignoreCase：是否忽略大小写
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
 * srcArr：源数组
 * srcStr：源字符串
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
 * srcArr：源数组
 */
function getNumFromText(srcStr) {
    var retStr = "";
    if (!isEmpty(srcStr)) {
        var strLength = srcStr.length;
        var thisChar = "";
        for (var i = 0; i < strLength; i++) {
            thisChar = srcStr.charAt(i);
            if (!isNaN(thisChar)) {
                retStr += thisChar;
            }
        }
    }
    return retStr;
}
/**
 * 功能：将srcStr中的所有oldStr全部替换成newStr
 * srcStr：源字符串
 * oldStr：原子字符串
 * newStr：新子字符串
 */
function replaceStr(srcStr, oldStr, newStr) {
    var retStr = srcStr;
    if (oldStr != "") {
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
 * srcStr：源字符串
 * maxLength：最大允许长度
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
 * srcStr：源字符串
 * minLength：最小允许长度
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
 * srcStr：源字符串
 * minLength：最小允许长度
 * maxLength：最大允许长度
 */
function isLengthValid(srcStr, minLength, maxLength) {
    srcStr = unescape(srcStr);
    return (!isLengthExtend(srcStr, maxLength) && !isLengthLess(srcStr, minLength));
}

/**
 * 功能：将指定值添加到数组
 * arr：目标数组
 * item：新值
 */
function add2Arr(arr, item) {
    if (arr == null) {
        return;
    }
    arr[arr.length] = item;
    return arr.length;
}


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

function explode(srcStr, spanStr) {
    var str = trim(srcStr);
    if (str == '') {
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
 * array：源数组
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
//===============================================================================================
//第三部分：以下处理日期操作
//===============================================================================================

/**
 * 功能：判断某字符串是否8位数字标准日期
 * srcStr：源字符串
 */
function isStandardDate(srcStr) {
    var retVal = false;
    if (srcStr.length == 8 && isInt(srcStr)) {
        var year = srcStr.substring(0, 4);
        var month = srcStr.substring(4, 6);
        var date = srcStr.substring(6, 8);
        var maxDate = 31;
        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                maxDate = 29;
            } else {
                maxDate = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDate = 30;
        }
        if (month > 0 && month < 13 && date > 0 && date <= maxDate) {
            retVal = true;
        }
    }
    return retVal;
}
/**
 * 功能：从日期中获得格式
 * srcDate：源字符串
 */
function getDatePattern(srcDate) {
    var retPattern = "";
    var numberDate = getNumFromText(srcDate);
    if (isStandardDate(numberDate)) {
        var year = numberDate.substring(0, 4);
        var month = numberDate.substring(4, 6);
        var date = numberDate.substring(6);
        retPattern = replaceStr(srcDate, year, "YYYY");
        retPattern = replaceFirst(retPattern, month, "MM");
        retPattern = replaceStr(retPattern, date, "DD");
    }
    return retPattern;
}
/**
 * 功能：判断日期格式是否合法
 * datePattern：日期格式
 */
function isValidDatePattern(datePattern) {
    return isExistInArr(LIMIT_DATE_PATTERN, datePattern, true);
}
/**
 * 功能：判断某字符串是否属于本系统认可的格式化日期
 * srcDate：日期字符串
 */
function isValidDate(srcDate) {
    var tempDate = getNumFromText(srcDate);
    var datePattern = getDatePattern(srcDate);
    return (isStandardDate(tempDate) && isValidDatePattern(datePattern));
}
/**
 * 功能：判断某字符串是否属于本系统认可的格式化日期,且其格式为指定的格式
 * srcDate：日期字符串
 * datePattern：指定的日期格式
 */
function isFormatDate(srcDate, datePattern) {
    var retVal = false;
    srcDate = unescape(srcDate);
    var tempDate = getNumFromText(srcDate);
    var tempPattern = getDatePattern(srcDate);
    retVal = isStandardDate(tempDate);
    if (retVal) {
        retVal = isValidDatePattern(datePattern);
        if (retVal) {
            retVal = (tempPattern == datePattern);
        }
    }
    return retVal;
}
/**
 * 功能：将原日期转换为8位数字标准格式日期
 * formatDate：符合本系统规定的任意格式日期字符串
 */
function getStandardDate(formatDate) {
    var retStr = getNumFromText(formatDate);
    if (!isStandardDate(retStr)) {
        retStr = "";
    }
    return retStr;
}
/**
 * 功能：从8位数字日期中获取年份数字
 * srcStr：日期字符串
 */
function getYearStr(srcStr) {
    return srcStr.substring(0, 4);
}
/**
 * 功能：从8位数字日期中获取月份数字
 * srcStr：日期字符串
 */
function getMonthStr(srcStr) {
    return srcStr.substring(4, 6);
}
/**
 * 功能：从8位数字日期中获取日期数字
 * srcStr：日期字符串
 */
function getDateStr(srcStr) {
    return srcStr.substring(6);
}
/**
 * 功能：将原日期转换成目标格式日期
 * srcDate：日期字符串
 * datePattern：目标日期格式
 */
function getFormatDate(srcDate, datePattern) {//此方法已能正常运行
    var retStr = srcDate;
    srcDate = getStandardDate(srcDate);
    if (isStandardDate(srcDate) && isValidDatePattern(datePattern)) {
        var year = getYearStr(srcDate);
        var month = getMonthStr(srcDate);
        var date = srcDate.substring(6);
        retStr = replaceStr(datePattern, "YYYY", year);
        retStr = replaceStr(retStr, "MM", month);
        retStr = replaceStr(retStr, "DD", date);
    }
    return retStr;
}


/**
 * 功能：求得两个日期之间的天数
 * dateStr1：日期字符串1
 * dateStr2：日期字符串2
 */
function getInterValDays(dateStr1, dateStr2) {
    var interDays = 0;
    dateStr1 = getStandardDate(dateStr1);
    dateStr2 = getStandardDate(dateStr2);
    if ((isStandardDate(dateStr1) && isStandardDate(dateStr2))) {
        var startDate = new Date(getYearStr(dateStr1), getMonthStr(dateStr1) - 1, getDateStr(dateStr1));
        var endDate = new Date(getYearStr(dateStr2), getMonthStr(dateStr2) - 1, getDateStr(dateStr2));
        interDays = Math.abs(startDate.getTime() - endDate.getTime());
        interDays = Math.floor(daysBetween / (1000 * 60 * 60 * 24));
    }
    return interDays;
}
/**
 * 功能：进行日期的调整
 * dateStr：日期字符串
 * datePattern：调整后的日期格式
 * datePattern：调整天数
 */
function adjustDate(dateStr, datePattern, adjustDays) {
    dateStr = getStandardDate(dateStr);
    if (isStandardDate(dateStr) && isInt(adjustDays)) {
        var date = new Date(getYearStr(dateStr), getMonthStr(dateStr) - 1, getDateStr(dateStr));
        adjustDays *= (24 * 60 * 60 * 1000);
        date = new Date(date.getTime() + adjustDays * 1);
        var tempMonth = date.getMonth() + 1;
        tempMonth = (tempMonth < 10) ? "0" + tempMonth : tempMonth;
        var tempDate = date.getDate();
        tempDate = (tempDate < 10) ? "0" + tempDate : tempDate;
        dateStr = date.getFullYear() + tempMonth + tempDate;
        if (isValidDatePattern(datePattern)) {
            dateStr = getFormatDate(dateStr, datePattern);
        }
    }
    return dateStr;
}
/**
 * 功能：以指定格式返回今天日期
 * datePattern：指定的日期格式
 */
function getToday(datePattern) {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var today = year + month + day;
    if (isValidDatePattern(datePattern)) {
        today = datePattern.toLowerCase();
        ;
        today = replaceStr(today, "YYYY", year);
        today = replaceStr(today, "YYYY", month);
        today = replaceStr(today, "DD", day);
    }
    return today;
}
/**
 * 功能：计算某年某月的天数
 * year：年份
 * month：月份
 */
function getDaysInMonth(year, month) {
    var days = 31;
    year = Number(year);
    month = Number(month);
    if (month < 1 || month > 12) {
        days = -1;
    }
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        days = 30;
    }
    if (month == 2) {
        days = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
    }
    return days;
}

/**
 * 功能：判断某字符串是否0到1之间的数字。
 * val：源字符串
 */
function isDiscount(val) {
    return (isNumber(val) && val > 0 && val <= 1);
}

//===============================================================================================
//第四部分：以下处理下拉框操作
//===============================================================================================
/**
 * 功能：将某个select组件下的所有元素复制到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function copyAllOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, false, true);
}

/**
 * 功能：以对象的形式进行复制操作，将第一个select组件下的所有元素复制到第二个select组件中
 * fromSelect：源select组件
 * toSelect：目标select组件
 */
function copyObjOptions(fromObj, toObj) {
    if (fromObj.hasChildNodes()) {
        var option = null;
        for (var i = 0; i < fromObj.length; i++) {
            option = new Option(fromObj.options[i].text, fromObj.options[i].value);
            if (haveChild(toObj, option)) {
                alert("该选项已经存在，不允许重复添加！");
                continue;
            }
            toObj.options[toObj.length] = option;
        }
    }
}
/**
 * 功能：将某个select组件下的选中元素复制到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function copySelectedOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, true, true);
}
/**
 * 功能：将某个select组件下的所有元素移动到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function moveAllOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, false, false);
}

/**
 * 功能：将某个select组件下的选中元素移动到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 */
function moveSelectedOption(fromSelect, toSelect) {
    processOption(fromSelect, toSelect, true, false);
}

/**
 *【注】：本函数是为上述四个函数而写的，一般调用时不需要调用本函数，调用上面的函数即可
 * 功能：将某个select组件下的元素移动或者复制到第二个select组件中
 * fromSelect：源select组件名称
 * toSelect：目标select组件名称
 * allOrSelected：true表示选中元素，false表示所有元素
 * copyOrMove：true表示复制元素，false表示移动元素
 */
function processOption(fromSelect, toSelect, allOrSelected, copyOrMove) {
    var selects = document.getElementsByTagName("select");
    var fromObj = null;
    var toObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == fromSelect) {
            fromObj = selects[i];
        } else if (selects[i].name == toSelect) {
            toObj = selects[i];
        }
        if (fromObj != null && toObj != null) {
            break;
        }
    }
    if (fromObj.hasChildNodes()) {
        var option = null;
        for (var i = 0; i < fromObj.length; i++) {
            if (allOrSelected && !fromObj.options[i].selected) {
                continue;
            }
            option = new Option(fromObj.options[i].text, fromObj.options[i].value);
            if (haveChild(toObj, option)) {
                alert("该选项已经存在，不允许重复添加！");
                return;
            }
            if (!copyOrMove) {
                fromObj.remove(i);
                i--;
            }
            toObj.options[toObj.length] = option;
        }
    }
}
/**
 * 功能：判断某个select组件是否含有某个Option选项
 * selObj：select组件对象
 * optObj：option组件对象
 */
function haveChild(selObj, optObj) {
    var retVal = false;
    var optValue = optObj.value;
    var optText = optObj.text;
    var opt = null;
    for (var i = 0; i < selObj.length; i++) {
        opt = selObj.item(i);
        if (opt.value == optValue && opt.text == optText) {
            retVal = true;
            break;
        }
    }
    return retVal;
}
/**
 * 功能：select中某指定选项的下标
 * select：select组件
 * code：待查找的选项值
 */
function indexInOptions(select, code) {
    var selObj = select;
    for (var i = 0; i < selObj.length; i++) {
        if (selObj.options[i].value == code) {
            return i;
        }
    }
    return -1;
}
/**
 * 功能：删除某个select组件下的元素
 * select：select组件名
 * allOrSelected：true表示选中元素，false表示所有元素
 */
function dropOption(selectName, allOrSelected) {
    var selObj = document.getElementById(selectName);
    if (selObj) {
        for (var i = 0; i < selObj.length; i++) {
            if (allOrSelected) {
                if (selObj.options[i].selected) {
                    selObj.remove(i);
                    i--;
                }
            } else {
                selObj.remove(i);
                i--;
            }
        }
    }

}

/**
 * 功能：删除某个select组件下的选中元素
 * selectName：select组件名
 */
function dropSelectedOption(selectName) {
    dropOption(selectName, true);
}
/**
 * 功能：删除某个select组件下的所有元素
 * selectName：select组件名
 */
function dropAllOption(selectName) {
    dropOption(selectName, true);
}
/**
 * 功能：清除下拉列表中对应值选项
 * selectName：select组件名
 * optionValue：特定项值
 */
function dropSpecialOption(selectName, optionValue) {
    var optionObj = document.all[selectName].options;
    var selectedValueArr = optionValue.split(";");
    if (selectedValueArr) {
        var valueCount = selectedValueArr.length;
        var tempValue = "";
        for (var i = 0; i < optionObj.length; i++) {
            tempValue = optionObj[i].value;
            for (var j = 0; j < valueCount; j++) {
                if (tempValue == selectedValueArr[j]) {
                    optionObj[i] = null;
                    i--;
                    break;
                }
            }
        }
    }
}
/**
 * 功能：清除下拉列表中对应值选项
 * selectName：select组件名
 * optionValue：特定项值
 */
function dropSpecialOption(select, optionValue) {
    var optionObj = select.options;
    var selectedValueArr = optionValue.split(";");
    if (selectedValueArr) {
        var valueCount = selectedValueArr.length;
        var tempValue = "";
        for (var i = 0; i < optionObj.length; i++) {
            tempValue = optionObj[i].value;
            for (var j = 0; j < valueCount; j++) {
                if (tempValue == selectedValueArr[j]) {
                    optionObj[i] = null;
                    i--;
                    break;
                }
            }
        }
    }
}
/**
 * 功能：将某个select组件下的所有选项都选中
 * selectName：select组件名
 */
function selectAll(selectName) {
    var selects = document.getElementsByTagName("select");
    var selObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == selectName) {
            selObj = selects[i];
            break;
        }
    }
    for (var i = 0; i < selObj.length; i++) {
        selObj.options[i].selected = true;
    }
}

/**
 * 功能：将某个select组件下的所有选项置于非选中状态
 * selectName：select组件名
 */
function unSelectAll(selectName) {
    var selects = document.getElementsByTagName("select");
    var selObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == selectName) {
            selObj = selects[i];
            break;
        }
    }
    for (var i = 0; i < selObj.length; i++) {
        selObj.options[i].selected = false;
    }
}

function unSelectAllById(selectId) {
    var select = document.getElementById(selectId);
    for (var i = 0; i < select.length; i++) {
        select.options[i].selected = false;
    }
}

/**
 * 功能：将某个select组件下的所有选项的选中状态置反
 * selectName：select组件名
 */
function reverseSeletStatus(selectName) {
    var selects = document.getElementsByTagName("select");
    var selObj = null;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name == selectName) {
            selObj = selects[i];
            break;
        }
    }
    for (var i = 0; i < selObj.length; i++) {
        selObj.options[i].selected = !selObj.options[i].selected;
    }
}
/**
 * 功能：获取下拉列表中被选中项的值。应用于下拉框可多选和单选的场合。
 * selectName：select组件名
 * splitor：分隔符
 */

function getSelectValue(selectName, splitor) {
    var retVal = "";
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    splitor = (splitor == null) ? ";" : splitor;
    if (optionCount) {
        for (var i = 0; i < optionCount; i++) {
            if (optionObj[i].selected && !isEmpty(optionObj[i].value)) {
                retVal += optionObj[i].value + splitor;
            }
        }
        retVal = retVal.substring(0, retVal.length - splitor.length);
    }
    return retVal;
}
/**
 * 获得选中的项目数
 * shaffer
 */
function getSelectedCount(selectName) {
    var retVal = 0;
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    if (optionCount) {
        for (var i = 0; i < optionCount; i++) {
            if (optionObj[i].selected) {
                retVal++;
            }
        }
    }
    return retVal;
}
/**
 * 功能：获取下拉列表中被选中项的文本。应用于下拉框可多选和单选的场合。
 * selectName：select组件名
 * splitor：分隔符
 */
function getSelectText(selectName, splitor) {
    var retVal = "";
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    splitor = (splitor == null) ? ";" : splitor;
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].selected && !isEmpty(optionObj[i].value)) {
            retVal += optionObj[i].text + splitor;
        }
    }
    return (endsWith(retVal, splitor)) ? retVal.substring(0, retVal.length - splitor.length) : retVal;
}

/**
 * 功能：获取下拉列表中特定项的文本
 * selectName：select组件名
 * optionValue：特定项值
 */
function getSpecialOptionText(selectName, optionValue) {
    var retVal = "";
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].value == optionValue) {
            retVal += optionObj[i].text;
            break;
        }
    }
    return retVal;
}
function getSpecialOptionTextByObj(select, optionValue) {
    var retVal = "";
    var optionObj = select.options;
    var optionCount = optionObj.length;
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].value == optionValue) {
            retVal += optionObj[i].text;
            break;
        }
    }
    return retVal;
}
/**
 * 功能：使下拉列表中对应值处于选中状态
 * selectName：select组件名
 * optionValue：特定项值
 */
function selectSpecialOption(selectName, optionValue) {
    var optionObj = document.all[selectName].options;
    var optionCount = optionObj.length;
    var selectedValueArr = optionValue.split(";");
    var valueCount = selectedValueArr.length;
    var tempValue = "";
    for (var i = 0; i < optionCount; i++) {
        tempValue = optionObj[i].value;
        for (var j = 0; j < valueCount; j++) {
            if (tempValue == selectedValueArr[j]) {
                optionObj[i].selected = true;
                break;
            }
        }
    }
}

function selectSpecialOptionByItem(select, optionValue) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var selectedValueArr = optionValue.split(";");
    var valueCount = selectedValueArr.length;
    var tempValue = "";
    for (var i = 0; i < optionCount; i++) {
        tempValue = optionObj[i].value;
        for (var j = 0; j < valueCount; j++) {
            if (tempValue == selectedValueArr[j]) {
                optionObj[i].selected = true;
                break;
            }
        }
    }
}

/**
 * 功能：使下拉列表中对应Text的Option处于选中状态
 * selectName：select组件对象
 * optionValue：特定项值
 */
function makeOptionsSelected(select, optionText) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var selectedTextArr = optionText.split(";");
    var textCount = selectedTextArr.length;
    var tempText = "";
    for (var i = 0; i < optionCount; i++) {
        tempText = optionObj[i].text;
        for (var j = 0; j < textCount; j++) {
            if (tempText == selectedTextArr[j]) {
                optionObj[i].selected = true;
                break;
            }
        }
    }
}
function makeOptionSelected(select, optionText) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var tempText = "";
    for (var i = 0; i < optionCount; i++) {
        tempText = optionObj[i].text;
        if (tempText == optionText) {
            optionObj[i].selected = true;
            break;
        }
    }
}
function makeOptionSelectedByValue(select, optionValue) {
    var optionObj = select.options;
    var optionCount = optionObj.length;
    var tempText = "";
    for (var i = 0; i < optionCount; i++) {
        tempText = optionObj[i].value;
        if (tempText == optionValue) {
            optionObj[i].selected = true;
            break;
        }
    }
}

//===============================================================================================
//第五部分：以下处理复选框操作
//===============================================================================================

/**
 * 功能：由一个控制性复选框checkBoxName控制另一组复选框checkBoxGroup
 * checkBoxName：控制性复选框组件名
 * checkBoxGroup：被控制性复选框组件名
 */
function checkAll(checkBoxName, checkBoxGroup) {
    var checkAttr = document.all[checkBoxName].checked;
    try {
        isChecked = checkAttr;
        if (!setCheckBoxState(checkBoxGroup, isChecked)) {
            document.all[checkBoxName].checked = !isChecked;
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
        if (groupCount > 200) {
            if (confirm("数据量过大，全选可能需要较长时间，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")) {
                for (var i = 0; i < groupCount; i++) {
                    if (checkGroup[i].type == "checkbox") {
                        checkGroup[i].checked = isChecked;
                    }
                }
            } else {
                retVal = false;
            }
        } else {
            for (var i = 0; i < groupCount; i++) {
                if (checkGroup[i].type == "checkbox") {
                    checkGroup[i].checked = isChecked;
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
        if (checkboxObj.checked) {
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
        if (allCheckObj.checked) {
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
function getSelectedRadioIndex(radioName) {
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

//===============================================================================================
//第七部分：以下为表单域普通校验的综合处理函数
//===============================================================================================
/**
 *【注】：暂时陷于文本域，其他域还未操作
 * 功能：将制定名称的表单的各个域中的值进行格式化，即去除各域值前后的空格
 * formName：表单名称
 */
function trimForm(formName) {
    var formList = document.forms;
    var frmObj = null;
    for (var i = 0; i < formList.length; i++) {
        frmObj = formList.item(i);
        if (frmObj.name == formName) {
            break;
        }
    }
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

/**
 * 功能：如果要对表单域进行日期校验，可采用此方法设置希望的日期格式，仅能有一个日期格式生效。
 * datePattern：日期格式
 */
function setDatePattern(datePattern) {
    if (isValidDatePattern(datePattern)) {
        DEFAULT_DATE_PATTERN = datePattern;
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
 * 功能：有效性检查，返回true表示通过校验，false表示有非法输入存在
 * fieldNames：校验组件名称，多个之间组合以半角分号连接
 * fieldLabels：校验组件中文标识，多个之间组合以半角分号连接。
 * validateType：校验类型，一次仅能执行一种校验，目前支持的校验类型见前面常量定义部分说明。
 */
function formValidate(fieldNames, fieldLabels, validateType) {
    var fieldNameArr = fieldNames.split(";");
    var fieldLabelArr = fieldLabels.split(";");
    exceptionValidate(fieldNameArr, fieldLabelArr);
    var retVal = true;
    var fieldName = "";
    var fieldType = "";
    var fieldValue = "";
    var items = null;
    var minLength = -1;
    var maxLength = -1;
    var nameCount = fieldNameArr.length;
    var alertMsg = "";
    var hasError = false;
    //是否有其他错误发生。
    var dotNum = -1;
    if (index != -1) {
        var index = findIndexOfArr(VALIDATE_TYPE_ARR, validateType);
        var funName = VALIDATE_TYPE_ARR[index];
        for (var i = 0; i < nameCount; i++) {
            fieldName = fieldNameArr[i];
            if (isEmpty(fieldName)) {
                continue;
            }
            if (!document.all[fieldName]) {
                hasError = true;
            }
            if (hasError) {
                alertMsg = "负责编写“" + location.href + "”页面或该Servlet导向的页面存在错误，请检查名为“" + fieldName + "”的表单域是否存在！";
                retVal = false;
                break;
            }
            items = document.all[fieldName];
            var isArr = false;
            try {
                isArr = eval('items.length && items[0].type');
            } catch(Exception) {
            }
            if (isArr) {
                fieldType = items[0].type;
            } else {
                fieldType = items.type;
            }
            if (fieldType == "radio") {
                fieldValue = getRadioValue(fieldName);
            } else if (fieldType == "checkbox") {
                fieldValue = getCheckBoxValue(fieldName);
            } else if (fieldType.indexOf("select") > -1) {
                fieldValue = getSelectValue(fieldName, ";");
            } else {
                fieldValue = document.all[fieldName].value
            }
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
                    hasError = true;
                    alertMsg = "负责该页编写的程序员调用方法存在错误！";
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
                    hasError = true;
                    alertMsg = "负责该页编写的程序员调用方法存在错误！";
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
                if (retVal) {
                    if (funName == "isEmpty") {//
                        retVal = !eval(funName + "('" + escape(fieldValue) + "')")
                    } else {
                        if (!isEmpty(fieldValue)) {
                            retVal = eval(funName + "('" + escape(fieldValue) + "')");
                        }
                    }
                }
            }
            if (!retVal) {
                if (!hasError) {
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
                }
                alert(alertMsg);
                if (fieldType != "checkbox" && fieldType != "radio" && fieldType.indexOf('select') == -1) {
                    if (!document.all[fieldName].readOnly && !document.all[fieldName].disabled) {
                        document.all[fieldName].select();
                        document.all[fieldName].focus();
                    }
                } else {
                    var fieldCount = null;
                    try {
                        fieldCount = document.all[fieldName].length;
                    } catch(Exception) {
                    }
                    if (fieldCount) {
                        if (!document.all[fieldName][0].readOnly && !document.all[fieldName][0].disabled) {
                            document.all[fieldName][0].focus();
                        }
                    } else {
                        if (!document.all[fieldName].readOnly && !document.all[fieldName].disabled) {
                            document.all[fieldName].focus();
                        }
                    }
                }
                break;
            } else {
                document.all[fieldName].value = trim(fieldValue);
            }
        }
    }
    return retVal;
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
 * fieldIds：校验组件ID，多个之间组合以半角分号连接
 * fieldLabels：校验组件中文标识，多个之间组合以半角分号连接。
 * validateType：校验类型，一次仅能执行一种校验，目前支持的校验类型见前面常量定义部分说明。
 */

function formValidateById(fieldIds, fieldLabels, validateType) {
    var fieldIdArr = fieldIds.split(";");
    var fieldLabelArr = fieldLabels.split(";");
    exceptionValidate(fieldIdArr, fieldLabelArr);
    var retVal = true;
    var fieldId = "";
    var fieldType = "";
    var fieldValue = "";
    var items = null;
    var minLength = -1;
    var maxLength = -1;
    var idCount = fieldIdArr.length;
    var alertMsg = "";
    var dotNum = -1;
    if (index != -1) {
        var index = findIndexOfArr(VALIDATE_TYPE_ARR, validateType);
        var funName = VALIDATE_TYPE_ARR[index];
        for (var i = 0; i < idCount; i++) {
            fieldId = fieldIdArr[i];
            if (isEmpty(fieldId)) {
                continue;
            }
            items = document.getElementById(fieldId);
            if (!items) {
                continue;
            }
            fieldType = items.type;
            fieldValue = items.value;
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
                    items.value = "";
                    items.select();
                    items.focus();
                } else {
                    items.focus();
                }
                break;
            } else {
                items.value = trim(fieldValue);
            }
        }
    }
    return retVal;
}


/**
 * 功能：有效性检查，返回true表示通过校验，false表示有非法输入存在。尤其适用于多行数据表单域名相同的情况。
 * fieldIds：校验组件名称，多个之间组合以半角分号连接
 * fieldLabels：校验组件中文标识，多个之间组合以半角分号连接。
 * validateType：校验类型，一次仅能执行一种校验，目前支持的校验类型见前面常量定义部分说明。
 */

function formValidate2(fieldIds, fieldLabels, validateType) {
    var fieldIdArr = fieldIds.split(";");
    var fieldLabelArr = fieldLabels.split(";");
    exceptionValidate(fieldIdArr, fieldLabelArr);
    var retVal = true;
    var fieldId = "";
    var fieldType = "";
    var fieldValue = "";
    var items = null;
    var minLength = -1;
    var maxLength = -1;
    var idCount = fieldIdArr.length;
    var alertMsg = "";
    var dotNum = -1;
    if (index != -1) {
        var index = findIndexOfArr(VALIDATE_TYPE_ARR, validateType);
        var funName = VALIDATE_TYPE_ARR[index];
        for (var i = 0; i < idCount; i++) {
            fieldId = fieldIdArr[i];
            if (isEmpty(fieldId)) {
                continue;
            }
            items = document.getElementById(fieldId);
            if (!items) {
                continue;
            }
            fieldType = items.type;
            fieldValue = items.value;
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
                    items.value = "";
                    items.select();
                    items.focus();
                } else {
                    items.focus();
                }
                break;
            } else {
                items.value = trim(fieldValue);
            }
        }
    }
    return retVal;
}

/**
 * 功能：用于内部方法的异常检查，不提供给程序员使用
 *
 */
function exceptionValidate(arr1, arr2) {
    if (arr1.length != arr2.length || arr2 == null) {
        alert("传入的参数不配对，如果你看到此对话框，请将此信息告诉开发人员");
        return false;
        //抛给程序员的信息，属异常情况
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
                //alert(fieldName+"--->"+fieldValue);
                //fieldObj.value = fieldValue;
            }
        }
    }
}

/**
 *功能:设置表单内所有元素不可用
 *参数:表单名
 */
function setFrmDisable(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
        frmObj.elements[i].disabled = true;
    }
}

/**
 *功能:设置表单内所有元素可用
 *参数:表单名
 */
function setFrmEnable(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
        document.form1.elements[i].disabled = false;
    }
}
/**
 * 功能：在指定层中显示页面元素pageObj的alt值。
 * divId：层的ID号；
 * pageObj：页面元素对象
 *
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
 * 功能：将表中选定行删除。
 * tableId：表的ID；
 * radioName：单选按钮名称。
 *
 */
function delTableLine(tableId, radioName) {
    var radioCount = getRadioCount(radioName);
    var hasChecked = false;
    if (radioCount > 1) {
        var radioObj = null;
        for (var i = 0; i < radioCount; i++) {
            radioObj = document.all[radioName][i];
            if (radioObj.checked) {
                document.all(tableId).deleteRow(i);
                if (i > 0 && document.all[radioName][i - 1]) {
                    document.all[radioName][i - 1].checked = true;
                }
                hasChecked = true;
                break;
            }
        }
    } else if (radioCount == 1) {
        if (document.all[radioName].checked || document.all[radioName][0].checked || document.getElementById(radioName).checked) {
            document.all[tableId].deleteRow(0);
            hasChecked = true;
        }
    }
    if (!document.all[radioName]) {         //如果删除了全部的行，则将计数器清０
        count1 = 0;
    }
    if (!hasChecked) {
        alert("没有选中欲删除的行。");
    }
}


/**
 * 功能：将表中选定行删除。
 * tableId：表的ID；
 * trName:  行ID的前缀
 * checkboxName：复选框名称。
 */
function deleteLines(tableId, trName, checkboxName) {
    var mtlTable = document.getElementById(tableId);
    var count = mtlTable.rows.length;
    if (count == 0) {
        alert("不存在要删除的行。");
        return;
    }
    var selectedRows = getCheckedBoxCount(checkboxName);
    if (selectedRows < 1) {
        alert("请先选择要删除的行！");
        return;
    }
    if (confirm("确定要删除选中的行吗？")) {
        for (var i = count - 1; i >= 0; i--) {
            var rowObj = mtlTable.rows[i];
            var trId = rowObj.id;
            var lastNumOfId = trId.substring(trName.length, trId.length);
            if (document.getElementById(checkboxName + lastNumOfId).checked) {
                mtlTable.deleteRow(i);
            }
        }
    }
}
/**
 * 功能：用于检查详细信息展示或修改时选中的记录数。
 * checkboxName：代表记录的复选框名称
 *
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
 * checkboxName：代表记录的复选框名称
 *
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
 *
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
 *
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
 *
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
 * 功能：自动合并表格的相邻行的text相同的cells。
 * tableID 表格ID
 * columnNumber 需要合并的列数，从左开始
 */
function autoSpan(tableID, columnNumber) {
    var obj = document.all[tableID];
    var head = new Array(columnNumber);
    var rows = obj.rows.length; 
    if (rows > 1) {
        var cols = obj.rows[0].cells.length;
        for (j = 0; j < columnNumber; j++) {
            head[j] = obj.rows[0].cells[j].innerText;
        }
        for (i = 1; i < rows; i++) {
            var flag = 0;
            for (j = 0; j < columnNumber; j++) {
                if (head[j] == obj.rows[i].cells[j].innerText && flag == 0) {
                    obj.rows[i].cells[j].innerText = "";
                } else {
                    flag = 1;
                    head[j] = obj.rows[i].cells[j].innerText;
                    //continue;
                }
            }
        }
        for (j = columnNumber; j >= 0; j--) {
            var cell = obj.rows[0].cells[j];
            var span = 1;
            for (i = 1; i < rows; i++) {
                if (obj.rows[i].cells[j].innerText == "") {
                    span++;
                    obj.rows[i].deleteCell(j);
                    cell.rowSpan = span;
                } else {
                    cell = obj.rows[i].cells[j];
                    span = 1;
                }
            }
        }
    }
}

/**
 * 功能：自动合并表格的相邻行的text相同的cells。
 * tableID 表格ID
 * columnNumber 需要合并的列数，从左开始
 */
function autoSpan2(tableID, columnNumber , rows ) {
    var obj = document.all[tableID];
    var head = new Array(columnNumber);
    //var rows = obj.rows.length; 
    if (rows > 1) {
        var cols = obj.rows[0].cells.length;
        for (j = 0; j < columnNumber; j++) {
            head[j] = obj.rows[0].cells[j].innerText;
        }
        for (i = 1; i < rows; i++) {
            var flag = 0;
            for (j = 0; j < columnNumber; j++) {
                if (head[j] == obj.rows[i].cells[j].innerText && flag == 0) {
                    obj.rows[i].cells[j].innerText = "";
                } else {
                    flag = 1;
                    head[j] = obj.rows[i].cells[j].innerText;
                    //continue;
                }
            }
        }
		for (j = 0; j < columnNumber; j++) {
        //for (j = columnNumber; j >= 0; j--) {
            var cell = obj.rows[0].cells[j];
            var span = 1;
            for (i = 1; i < rows; i++) {
                if (obj.rows[i].cells[j].innerText == "") {
                    span++;
                    obj.rows[i].deleteCell(j);
                    cell.rowSpan = span;
                } else {
                    cell = obj.rows[i].cells[j];
                    span = 1;
                }
            }
        }
    }
}

/**
 * 功能：自动合并表格的相邻行的text相同的cells。
 * tableID 表格ID
 * fromNumber 合并开始的列数，
 * toNumber 合并结束的列数，
 */
function autoFromToSpan(tableId, fromNumber, toNumber) {
    var tab = document.getElementById(tableId);
    var head = new Array(toNumber - fromNumber + 1);
    var rows = tab.rows.length;
    if (rows > 1) {
        var cols = tab.rows[0].cells.length;
        for (var j = fromNumber; j <= toNumber; j++) {
            head[j - fromNumber] = getCellValue(tab.rows[0].cells[j - 1]);
            
        }
        for (var i = 1; i < rows; i++) {
            var flag = 0;
            for (var j = fromNumber; j <= toNumber; j++) {
                if (head[j - fromNumber] == getCellValue(tab.rows[i].cells[j - 1]) && flag == 0) {
                    tab.rows[i].cells[j - 1].innerHTML = "";
                } else {
                    flag = 1;
                    head[j - fromNumber] = getCellValue(tab.rows[i].cells[j - 1]);
                }
            }
        }
        
        for (var j = toNumber; j >= fromNumber; j--) {
            var cell = tab.rows[0].cells[j - 1];
            var span = 1;
            for (var i = 1; i < rows; i++) {
                if (tab.rows[i].cells[j - 1].innerHTML == "") {
                    span++;
                    tab.rows[i].deleteCell(j - 1);
                    cell.rowSpan = span;
                } else {
                    cell = tab.rows[i].cells[j - 1];
                    span = 1;
                }
            }
        }
        document.write(tab.innerHTML);
    }
}
/**
 * 功能：得到表格中指定列的值
 * cellObj：表格中的列对象
 */
function getCellValue(cellObj) {
    var cellValue = '';
    if (cellObj.childNodes.length > 0) {
        if (typeof(cellObj.childNodes[0].value) == "undefined") {
            cellValue = cellObj.innerText;
        } else {
            cellValue = cellObj.childNodes[0].value;
        }
    }
    return cellValue;
}
/**
 * 功能：在文本域在上onkeydown事件，刚该文本域只能输入浮点数
 * value 表单值
 * 但应当允许用户进行删除操作.
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
 * 功能：在文本域在上onkeyup事件，刚该文本域只能输入浮点数
 * value 表单值
 * 但应当允许用户进行删除操作.
 */
function floatOnly(obj, value) {
    var result = value;
    var strTemp = "0123456789,.";
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

/**
 * 功能：在文本域onkeyup/onblur事件，该文本域能输入浮点数或整数, 且支持千位分隔符","格式
 * bFlag：true--以千位分隔符形式显示；false--不以千位分隔符形式显示
 * 存入数据库时用StrUtil.replaceStr(value,",","",true) 将千位分隔符去掉
 */


function autoSplit(obj, value, bFlag) {   
    var result = value;
    var strTemp = "0123456789.,";
    var num = 0;
    for (var i = 0; i < result.length; i++){
         var j = strTemp.indexOf(result.charAt(i));
         if (j == -1){
             result = result.substring(0, i) + result.substring(i, result.length - 1);
             i--;
         }
         if(result.charAt(i)==","){
                result = result.substring(0, i) + result.substring(i + 1, result.length);
         }
         if(result.charAt(i)=="."){    //小数点后不能再有小数点和千位分隔符
            if(i==0){
               result = "";
            }
            var n = result.substring(i + 1, result.length).indexOf(".");
            if (n != -1){
                result = result.substring(0, i + n + 1);
            }
            n = result.substring(i + 1, result.length).indexOf(",");
            if (n != -1){
                result = result.substring(0, i + n + 1);
            }
            for (var j = i+1; j < result.length; j++){         //去掉小数点后面的非法输入
                 var n = strTemp.indexOf(result.charAt(j));
                 if (n == -1){
                   result = result.substring(0, j) + result.substring(j, result.length - 1);
                   j--;
                 }
               }
            if((i==result.length-1)&&(event.type=="blur")){     // 整数不能以小数点结束，onblur事件
                result = result.substring(0,i);
            }
            num = i;
            break;
        } else{
                num = result.length;
          }
    }

    if(bFlag){         //需要千位分隔符
        if((num > 3)&&(num < 7)){
             result = result.substring(0,num - 3) + "," + result.substring(num - 3,num) + result.substring(num,result.length);
        } else if((num > 6)&&(num < 10)){
             result = result.substring(0,num - 6) + "," + result.substring(num - 6,num - 3) + "," + result.substring(num - 3,num) + result.substring(num,result.length);
        } else if((num > 9)&&(num < 13)){
             result = result.substring(0,num - 9) + "," + result.substring(num - 9,num - 6) + "," + result.substring(num - 6, num - 3) + "," + result.substring(num - 3,num) + result.substring(num,result.length);
        } else if((num > 12)&&(num < 16)){
             result = result.substring(0,num - 12) + "," + result.substring(num - 12,num - 9) + "," + result.substring(num - 9,num - 6) + "," + result.substring(num - 6, num - 3) + "," + result.substring(num - 3,num) + result.substring(num,result.length);
        }
   }
    obj.value = result
}
/**
 * 功能：按下enter键时自动执行指定无参函数。
 * 例如：autoExeFunction(do_selectVendors());
 */
function autoExeFunction(functionName) {
    if (event.keyCode == 13) {
        eval(functionName);
    }
}

/**
 * 功能：按上下键时自动将光标跳到下一个对象
 *      但如果中间有删除行则会在删除行的位置不起作用，有待改进
 */
function moveCursor(objName, id) {
    var obj = document.getElementById(objName + id);
    var nextId = -1;
    var tempObj;
    //    alert("event.keycode="+event.keyCode+"!")
    if (event.keyCode == 38) { //up
        nextId = Number(id) - 1;
    } else if (event.keyCode == 40) { //down
        nextId = Number(id) + 1;
    }
    //    alert("nextId="+nextId)
    if (nextId >= 0) {
        tempObj = document.getElementById(objName + nextId);
        if (tempObj) {
            tempObj.focus();
        }
    }
}

/**************************************************金额(人民币)大小写转换*******************************************/

/**
 * 功能：将数字金额转换为大写中文金额。
 */
function toChineseCapital(num)
{
    if (isNaN(num) || num > Math.pow(10, 12))   return   ""
    var cn = "零壹贰叁肆伍陆柒捌玖"
    var unit = new Array("拾佰仟", "分角")
    var unit1 = new Array("万亿", "")
    var numArray = num.toString().split(".")
    var start = new Array(numArray[0].length - 1, 2)

    function toChinese(num, index)
    {
        var num = num.replace(/\d/g, function   ($1)
        {
            return   cn.charAt($1) + unit[index].charAt(start-- % 4 ? start % 4 : -1)
        })
        return   num
    }

    for (var i = 0; i < numArray.length; i++)
    {
        var tmp = ""
        for (var j = 0; j * 4 < numArray[i].length; j++)
        {
            var strIndex = numArray[i].length - (j + 1) * 4
            var str = numArray[i].substring(strIndex, strIndex + 4)
            var start = i ? 2 : str.length - 1
            var tmp1 = toChinese(str, i)
            tmp1 = tmp1.replace(/(零.)+/g, "零").replace(/零+$/, "")
            tmp1 = tmp1.replace(/^壹拾/, "拾")
            tmp = (tmp1 + unit1[i].charAt(j - 1)) + tmp
        }
        numArray[i] = tmp
    }

    numArray[1] = numArray[1] ? numArray[1] : ""
    numArray[0] = numArray[0] ? numArray[0] + "圆" : numArray[0],numArray[1] = numArray[1].replace(/^零+/, "")
    numArray[1] = numArray[1].match(/分/) ? numArray[1] : numArray[1] + "整"
    return   numArray[0] + numArray[1]
}

function toNumberCase(num)
{
    var numArray = new Array()
    var unit = "亿万圆$"
    for (var i = 0; i < unit.length; i++)
    {
        var re = eval("/" + (numArray[i - 1] ? unit.charAt(i - 1) : "") + "(.*)" + unit.charAt(i) + "/")
        if (num.match(re))
        {
            numArray[i] = num.match(re)[1].replace(/^拾/, "壹拾")
            numArray[i] = numArray[i].replace(/[零壹贰叁肆伍陆柒捌玖]/g, function   ($1)
            {
                return   "零壹贰叁肆伍陆柒捌玖".indexOf($1)
            })
            numArray[i] = numArray[i].replace(/[分角拾佰仟]/g, function   ($1)
            {
                return   "*" + Math.pow(10, "分角   拾佰仟   ".indexOf($1) - 2) + "+"
            }).replace(/^\*|\+$/g, "").replace(/整/, "0")
            numArray[i] = "(" + numArray[i] + ")*" + Math.ceil(Math.pow(10, (2 - i) * 4))
        }
        else   numArray[i] = 0
    }
    return   eval(numArray.join("+"))
}

function getpopscript(widthParm,heightParm)
 {
     var width=800;
     var height=580;
     if (arguments.length==2) {
        width=widthParm;
        height=heightParm
     }
     var left = (window.screen.availWidth-10-width )/2;
     var top = (window.screen.availHeight-30-height )/2;
     var popscript ='width=' +width+ ',height=' +height+ ',left=' +left+ ',top=' +top+','+'toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
     return popscript;
 }