package com.sino.rds.share.constant;


public interface RDSDictionaryList {
    String TRUE_VALUE = "Y";
    String FALSE_VALUE = "N";

    String REPORT_TYPE = "REPORT_TYPE";
    String VIEW_LOCATION = "VIEW_LOCATION";
    String H_ALIGN = "H_ALIGN";
    String INPUT_TYPE = "INPUT_TYPE";
    String NULL_ABLE = "NULL_ABLE";
    String CALENDAR_TYPE = "CALENDAR_TYPE";
    String CALENDAR_TYPE_START = "START";
    String CALENDAR_TYPE_END = "END";
    String CALENDAR_TYPE_INTERVAL = "INTERVAL";
    String DATA_SRC_TYPE = "DATA_SRC_TYPE";
    String DRILL_DOWN_TYPE = "DRILL_DOWN_TYPE";
    String SUPPORT_DRILL_DOWN = "SUPPORT_DRILL_DOWN";
    String COMPUTE_EXPRESSION = "COMPUTE_EXPRESSION";
    String DATE_PATTERN = "DATE_PATTERN";
    String NUMBER_PATTERN = "NUMBER_PATTERN";
    String NUMBER_PATTERN_THOUSAND = "1";//千位分隔符
    String NUMBER_PATTERN_PERCENT = "2";//百分比
    String NUMBER_PATTERN_CURRENCY = "3";//人民币
    String NUMBER_PATTERN_INTEGER = "4";//取整数
    String NUMBER_PATTERN_PERMILLAGE = "5";//千分比

    String DISPLAY_FLAG = "DISPLAY_FLAG";
    String DISPLAY_FLAG_Y = "Y";
    String DISPLAY_FLAG_N = "N";
    String DISPLAY_TYPE = "DISPLAY_TYPE";
    String SUPPORT_SUB_SUMMARY = "SUPPORT_SUB_SUMMARY";
    String COUNT_PAGES = "COUNT_PAGES";

    String DRILL_TYPE_REPORT = "EXIST_REPORT";
    String DRILL_TYPE_URL = "OTHER_URL";


    String DATA_SRC_TYPE_TABLE = "TABLE";
    String DATA_SRC_TYPE_VIEW = "VIEW";
    String DATA_SRC_TYPE_SQL = "SQL";
    String REPORT_TYPE_LIST = "1"; //简单数据查询列表报表
    String REPORT_TYPE_INTERSECT = "2";//双向分组不定交叉报表
    String REPORT_TYPE_FIXED_ROWS = "3";//纵向分组固定交叉报表
    String REPORT_TYPE_FIXED_COLS = "4";//横向分组固定交叉报表
    String REPORT_TYPE_FIXED_BOTH = "5";//横向分组固定交叉报表

    String VIEW_LOCATION_ABOVE = "ABOVE";
    String VIEW_LOCATION_LEFT = "LEFT";

    String H_ALIGN_LEFT = "LEFT";
    String H_ALIGN_CENTER = "CENTER";
    String H_ALIGN_RIGHT = "RIGHT";

    String INPUT_TYPE_TEXT = "TEXT";
    String INPUT_TYPE_CALENDAR = "CALENDAR";
    String INPUT_TYPE_LOV = "LOV";
    String INPUT_TYPE_LOOKUP = "LOOKUP";
    String INPUT_TYPE_URL = "URL";
    String INPUT_TYPE_SESSION = "SESSION";
    String INPUT_TYPE_HIDDEN = "HIDDEN";
    String LOV_TYPE = "LOV_TYPE";
    String LOV_TYPE_SQL = "SQL";
    String LOV_TYPE_CONS = "CONS";
    String LOV_TYPE_ACCOUNT = "ACCOUNT_PERIOD";


    String AJAX_RESPONSE_XML = "AJAX_XML";
    String AJAX_RESPONSE_TXT = "AJAX_TXT";
    String SESSION_PREFIX = "SESSION.";
    String DISPLAY_TYPE_ALL = "DISPLAY_ALL";
    String DISPLAY_TYPE_PAGE = "SPLIT_PAGES";

    String SUM_POSITION = "SUM_POSITION";
    String POSITION_BOTTOM = "BOTTOM";
    String POSITION_LEFT = "LEFT";
    String POSITION_RIGHT = "RIGHT";
    String POSITION_BOT_AND_RIG = "BOT_AND_RIG";
    String POSITION_BOT_AND_LEF = "BOT_AND_LEF";

    String CHECK_STATUS_NOT_CHECKED = "0";//尚未检查
    String CHECK_STATUS_SUCCESS = "1";//检查成功
    String CHECK_STATUS_FAILURE = "2";//检查失败
}
