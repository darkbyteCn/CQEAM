/**
 * @author mshtang
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
var UPDATE_CHECK_MSG = "没有选中要更新的记录，一次可以更新一条或多条记录，但不能不选。";
var DELETE_MSG = "数据删除后可能无法恢复，确定要删除吗？要继续请点击“确定”按钮，否则点击“取消”按钮。";
var CANCEL_MSG = "确定放弃本次工作吗？继续请点击“确定”按钮，否则请点击“取消”按钮！";
var ENABLE_MSG = "确认生效吗？继续请点“确定”按钮，否则请点“取消”按钮。"
var DISABLE_MSG = "确认失效吗？继续请点“确定”按钮，否则请点“取消”按钮。";
