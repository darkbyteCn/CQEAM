/**
 * @author mshtang
 */

//===============================================================================================
//第三部分：以下处理日期操作
//===============================================================================================

/**
 * 功能：判断某字符串是否8位数字标准日期
 * @param {Object} srcStr 源字符串
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
 * @param {Object} strDate 字符串日期
 */
function getDatePattern(strDate) {
    var retPattern = "";
    var numberDate = getNumFromText(strDate);
    if (isStandardDate(numberDate)) {
        var year = numberDate.substring(0, 4);
        var month = numberDate.substring(4, 6);
        var date = numberDate.substring(6);
        retPattern = replaceStr(strDate, year, "YYYY");
        retPattern = replaceFirst(retPattern, month, "MM");
        retPattern = replaceStr(retPattern, date, "DD");
    }
    return retPattern;
}

/**
 * 功能：判断日期格式是否合法
 * @param {Object} datePattern 日期格式
 */
function isValidDatePattern(datePattern) {
    return isExistInArr(LIMIT_DATE_PATTERN, datePattern, true);
}

/**
 * 功能：判断某字符串是否属于本系统认可的格式化日期
 * @param {Object} strDate 字符串日期
 */
function isValidDate(strDate) {
    var tempDate = getNumFromText(strDate);
    var datePattern = getDatePattern(strDate);
    return (isStandardDate(tempDate) && isValidDatePattern(datePattern));
}

/**
 * 功能：判断某字符串是否属于本系统认可的格式化日期,且其格式为指定的格式
 * @param {Object} strDate 字符串日期
 * @param {Object} datePattern 指定的日期格式
 */
function isFormatDate(strDate, datePattern) {
    var retVal = false;
    strDate = unescape(strDate);
    var tempDate = getNumFromText(strDate);
    var tempPattern = getDatePattern(strDate);
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
 * @param {Object} formatDate 符合本系统规定的任意格式日期字符串
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
 * @param {Object} strDate 字符串日期
 */
function getYearStr(strDate) {
    return strDate.substring(0, 4);
}

/**
 * 功能：从8位数字日期中获取月份数字
 * @param {Object} strDate 字符串日期
 */
function getMonthStr(strDate) {
    return strDate.substring(4, 6);
}

/**
 * 功能：从8位数字日期中获取日期数字
 * @param {Object} strDate 字符串日期
 */
function getDateStr(strDate) {
    return strDate.substring(6);
}

/**
 * 功能：将原日期转换成目标格式日期
 * @param {Object} strDate 字符串日期
 * @param {Object} datePattern 日期格式
 */
function getFormatDate(strDate, datePattern) {
    var retDate = getStandardDate(strDate);
	datePattern = datePattern.toUpperCase();
    if (isStandardDate(retDate) && isValidDatePattern(datePattern)) {
        var year = getYearStr(retDate);
        var month = getMonthStr(retDate);
        var date = retDate.substring(6);
        retDate = replaceStr(datePattern, "YYYY", year);
        retDate = replaceStr(retDate, "MM", month);
        retDate = replaceStr(retDate, "DD", date);
    }
    return retDate;
}

/**
 * 功能：求得两个日期之间的天数
 * @param {Object} dateStr1 日期字符串1
 * @param {Object} dateStr2 日期字符串2
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
 * 功能：调整日期
 * @param {Object} dateStr 日期字符串
 * @param {Object} datePattern 目标日期格式
 * @param {Object} adjustDays 调整天数
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
 * @param {Object} datePattern 指定的日期格式
 */
function getToday(datePattern) {
	var today = "";
    if (isValidDatePattern(datePattern)) {
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
	    today = String(year) + String(month) + String(day);
		today = getFormatDate(today, datePattern);
    }
    return today;
}

/**
 * 功能：计算某年某月的天数
 * @param {Object} year 年份
 * @param {Object} month 月份
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
