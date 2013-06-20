var gsSplit = "-";
// separator of date string, AT LEAST one char.
var giDatePos = 2;
// date format sequence  0: D-M-Y ; 1: M-D-Y; 2: Y-M-D
var gbPadZero = true;
// whether to pad the digits with 0 in the left when less than 10.
var giMonthMode = 0;
// month format 0: digits ; 1: full name from gMonths; >2: abbreviated month name in specified length.
var gbShortYear = false;
// year format   true: 2-digits; false: 4-digits
var gbAutoPos = true;
// enable auto-adpative positioning or not
var gbPopDown = true;
// true: pop the calendar below the dateCtrl; false: pop above if gbAutoPos is false.
var gbAutoClose = true;
// whether to close the calendar after selecting a date.
var gPosOffset = [0,0];
// Offsets used to adjust the pop-up postion, [leftOffset, topOffset].
var gbFixedPos = false;
// true: pop the calendar absolutely at gPosOffset; false: pop it relatively.

// ---- Common Options ----

var gMonths = ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
var T_sun = "<a href='' onclick='fShowAbout(); return false'><font color='white'>日</font></a>";
var T_mon = "<a href='' onclick='fShowAbout(); return false'><font color='white'>一</font></a>";
var T_tun = "<a href='' onclick='fShowAbout(); return false'><font color='white'>二</font></a>";
var T_wen = "<a href='' onclick='fShowAbout(); return false'><font color='white'>三</font></a>";
var T_thu = "<a href='' onclick='fShowAbout(); return false'><font color='white'>四</font></a>";
var T_fri = "<a href='' onclick='fShowAbout(); return false'><font color='white'>五</font></a>";
var T_sat = "<a href='' onclick='fShowAbout(); return false'><font color='white'>六</font></a>";
//var gWeekDay = ["日","一","二","三","四","五","六"];
var gWeekDay = [T_sun,T_mon,T_tun,T_wen,T_thu,T_fri,T_sat];
// weekday caption from Sunday to Saturday
var tmp_date = new Date();
var tmp_year = tmp_date.getFullYear();
var startYear = tmp_year - 100;
var endYear = tmp_year + 100;
var gBegin = [startYear,1,1];
// calendar date range begin from [Year,Month,Date]. Using gToday here will make it start from today.
var gEnd = [endYear,12,31];
// calendar date range end at [Year,Month,Date]
var gsOutOfRange = "抱歉，不能选择超出范围的日期!";
// out-of-date-range error message. If set to "", no alerts will popup on such error.
var guOutOfRange = null;
// the background image url for the out-range dates. e.g. "outrange.gif"

var gbEuroCal = true;
// true: ISO-8601 calendar layout - Monday is the 1st day of week; false: US layout - Sunday is the 1st day of week.

var gcCalBG = "#ffffff";
// the background color of the outer calendar panel.
var guCalBG = null;
//  the background image url for the inner table.
var gcCalFrame = "#272D2A";
// the background color of the inner table, showing as a frame.
var gsInnerTable = "border=0 cellpadding=1 cellspacing=1";
// HTML tag properties of the inner <table> tag, which holds all the calendar cells.
var gsOuterTable = "border=0 cellpadding=1 cellspacing=1";
// HTML tag properties of the outmost container <table> tag, which holds the top, middle and bottom sections.

var gbHideTop = false;
// true: hide the top section; false: show it according to the following settings
var giDCStyle = 0;
// the style of month-controls in top section.	0: use predefined html dropdowns & gsNavPrev/Next; 1: use gsCalTitle & gsNavPrev/Next; 2: use only gsCalTitle;
var gsCalTitle = "gMonths[gCurMonth[1]-1]+' '+gCurMonth[0]";
// dynamic statement to be eval()'ed as the title when giDCStyle>0.
var gbDCSeq = false;
// (effective only when giDCStyle is 0) true: show month box before year box; false: vice-versa;
var gsYearInBox = "i";
// dynamic statement to be eval()'ed as the text shown in the year box. e.g. "'A.D.'+i" will show "A.D.2001"
var gsNavPrev = "<INPUT type='button' value='&lt;' title='上一月' class='MonthNav' onclick='fPrevMonth();this.blur();'>";
// the content of the left month navigator
var gsNavNext = "<INPUT type='button' value='&gt;' title='下一月' class='MonthNav' onclick='fNextMonth();this.blur();'>";
// the content of the right month navigator

var gbHideBottom = false;
// true: hide the bottom section; false: show it with gsBottom.
var gsBottom = "<table border='0' cellspacing='0' cellpadding='0' align='center'><tr><td><input type='button' value='清除' onclick='gdCtrl.value=\"\";if(this.blur)this.blur();fHideCal();return false;'></td><td width=60 align=center><input type='button' value='今天' onclick='if(this.blur)this.blur();if(!fSetDate(gToday[0],gToday[1],gToday[2]))alert(\"抱歉，不能选择今天！\");return false;'></td><td><input type='button' value='关闭' onclick='fHideCal();return false;'></td></tr></table>";
// the content of the bottom section.

var giCellWidth = 22;
// calendar cell width;
var giCellHeight = 14;
// calendar cell height;
var giHeadHeight = 16;
// calendar head row height;
var giWeekWidth = 22;
// calendar week-number-column width;
var giHeadTop = 1;
// calendar head row top offset;
var giWeekTop = 0;
// calendar week-number-column top offset;

var gcCellBG = "#336699";
// default background color of the cells. Use "" for transparent!!!
var gsCellHTML = "";
// default HTML contents for days without any agenda, usually an image tag.
var guCellBGImg = "";
// url of default background image for each calendar cell.
var gsAction = " ";
// default action to be eval()'ed on everyday except the days with agendas, which have their own actions defined in agendas.
var gsDays = "dayNo";
// the dynamic statement to be eval()'ed into each day cell.

var giWeekCol = -1;
// -1: disable week-number-column;  0~7: show week numbers at the designated column.
var gsWeekHead = "#";
// the text shown in the table head of week-number-column.
var gsWeeks = "weekNo";
// the dynamic statement to be eval()'ed into the week-number-column. e.g. "'week '+weekNo" will show "week 1", "week 2" ...

var gcWorkday = "#FFFFCC";
// Workday font color
var gcSat = "#FF7777";
// Saturday font color
var gcSatBG = "#336699";
// Saturday background color
var gcSun = "#FF7777";
// Sunday font color
var gcSunBG = "#336699";
// Sunday background color

var gcOtherDay = "silver";
// the font color of days in other months; It's of no use when giShowOther is set to hide.
var gcOtherDayBG = gcCellBG;
// the background color of days in other months. when giShowOther set to hiding, it'll substitute the gcOtherDay.
var giShowOther = 2;
// control the look of days in OTHER months. 1: show date & agendas effects; 2: show selected & today effects; 4: hide days in previous month; 8: hide days in next month; 16: when set with 4 and/or 8, the days will be visible but not selectable.  NOTE: values can be added up to create mixed effects.

var gbFocus = true;
// whether to enable the gcToggle highlight whenever mouse pointer focuses over a calendar cell.
var gcToggle = "#9CCFFF";
// the highlight color for the focused cell

var gcFGToday = "#FFFFFF";
// the font color for today
var gcBGToday = "#336699";
// the background color for today
var guTodayBGImg = "";
// url of image as today's background
var giMarkToday = 1;
// Effects for today - 0: nothing; 1: set background color with gcBGToday; 2: draw a box with gcBGToday; 4: bold the font; 8: set font color with gcFGToday; 16: set background image with guTodayBGImg; - they can be added up to create mixed effects.

var gcFGSelected = "white";
// the font color for the selected date
var gcBGSelected = "#DB5141";
// the background color for the selected date
var guSelectedBGImg = "";
// url of image as background of the selected date
var giMarkSelected = 2;
// Effects for selected date - 0: nothing; 1: set background color with gcBGSelected; 2: draw a box with gcBGSelected; 4: bold the font; 8: set font color with gcFGSelected; 16: set background image with guSelectedBGImg; - they can be added up to create mixed effects.

var gbBoldAgenda = true;
// whether to boldface the dates with agendas.
var gbInvertBold = false;
// true: invert the boldface effect set by gbBoldAgenda; false: no inverts.
var gbShrink2fit = true;
// whether to hide the week line if none of its day belongs to the current month.
var gdSelect = [2003,4,19];
// default selected date in format of [year, month, day]; [0,0,0] means no default date selected.
var giFreeDiv = 0;
// The number of absolutely positioned layers you want to customize, they will be named as "freeDiv0", "freeDiv1"...
var gAgendaMask = [-1,-1,-1,null,null,-1,null];
// Set the relevant bit to -1 to keep the original agenda info of that bit unchanged, otherwise the new value will substitute the one defined in agenda.js.

var giResizeDelay = 150;
// delay in milliseconds before resizing the calendar panel. Calendar may have incorrect initial size if this value is too small.
var gbFlatBorder = false;
// flat the .CalCell css border of any agenda date by setting it to solid style. NOTE: it should always be set to false if .CalCell has no explicit border size.
var gbInvertBorder = false;
// true: invert the effect caused by gbFlatBorder; false: no change.
var gbShareAgenda = false;
// if set to true, a global agenda store will be created and used to share across calendars. Check tutorials for details.


///////////// Dynamic holiday calculations /////////////////////////
// This function provides you a flexible way to make holidays of your own.
// It will be called whenever the calendar engine needs the agenda info of a specific date, and the date is passed in as (y,m,d);
// With the date in hand, just do whatever you want to check to validate whether it is a desired holiday;
// Finally you should return an agenda array like [message, action, bgcolor, fgcolor, bgimg, boxit, html] to tell the engine how to render it.
////////////////////////////////////////////////////////////////////
function fHoliday(y, m, d) {
    var r = fGetEvent(y, m, d);
    // get agenda event.
    if (r) return r;    // ignore the following holiday checking if the date has already been set by the above addEvent functions. Of course you can write your own code to merge them instead of just ignoring.

    // you may have sophisticated holiday calculation set here, following are only simple examples.
    /*if (m==1&&d==1)
         r=[" Jan 1, "+y+" \n Happy New Year! ","","#87ceeb","red"];
     else if (m==12&&d==25)
         r=[" Dec 25, "+y+" \n Merry X'mas! ",null,"#87ceeb","red"];	// show a line-through effect
     else*/
    if (y == 1980 && m == 4 && d == 1) {
        //var date=getDateByDOW(y,4,5,6);	// Memorial day is the last Monday of May
        //if (d==date) r=["April "+d+", "+y+"  hawk's birthday ",gsAction,"#9900FF","red"];	// use default action
        r = ["April " + d + ", " + y + "  Sylvia's birthday ",gsAction,"#CC99FF","red"];
    }

    return r;
    // if r is null, the engine will just render it as a normal day.
}


// -- You may also put your self-defined functions here if required, like the following two which are used in the above examples. --
function popup(url, framename) {    // popup an url in the designated window, you may delete it if no need.
    var w = parent.open(url, framename, "top=200,left=200,width=400,height=200,scrollbars=1,resizable=1");
    if (w && url.split(":")[0] == "mailto") w.close();
    else if (w && !framename) w.focus();
}

function getDateByDOW(y, m, q, n) { // return the actual date of the q-th n-day in the specific month (y,m), you may delete it if no need.
    // n: 0 - Sunday, 1 - Monday ... 6 - Saturday
    // q: 1 - 5 ( 5 denotes the last n-day )
    var dom = new Date(y, m - 1, 1).getDay();
    var d = 7 * q - 6 + n - dom;
    if (dom > n) d += 7;
    if (d > fGetDays(y)[m]) d -= 7;
    return d;
}

function fOnChange(y, m, d) {
    return false;
    // return true to cancel the change.
}

function fStartPop(startc, endc) {
    var sd = fParseDate(endc.value);
    if (!sd) sd = gEnd;
    fPopCalendar(startc, [gBegin,sd,sd]);
}

function fEndPop(startc, endc) {
    var sd = fParseDate(startc.value);
    if (!sd) sd = gBegin;
    fPopCalendar(endc, [sd,gEnd,sd]);
}

/******
****  弹出选择日期控件，控制 currentc所选日期值在startc和endc的值之间
****  isBeforeNow 默认为false, 为true时，可以选择当前日期以前的日期;否则不可以
**/
function fMiddlePop(currentc, isBeforeNow, startc, endc) {
    var flag,sd,ed;

    var today = new Date();
    var curDate = fParseDate(today.getYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate());

    if (startc) {
        sd = fParseDate(startc.value);
        if (!sd) sd = gBegin;
    } else {
        sd = gBegin;
    }

    if (endc) {
        ed = fParseDate(endc.value);
        if (!ed) ed = gEnd;
    } else {
        ed = gEnd;
    }

    if (!isBeforeNow) {
        flag = false;
    } else {
        flag = isBeforeNow;
    }
    if (flag == false) {
        if (sd < curDate) {
            sd = curDate;
        }
    }
    fPopCalendar(currentc, [sd,ed,sd])

}