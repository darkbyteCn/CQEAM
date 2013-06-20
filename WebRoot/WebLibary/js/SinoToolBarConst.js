var constImgPath="/images/buttonbar/";

var constTitleBarBGColor="#358BCE";
var constTitleBarHeight=22;
var constTitleBarFont="style=\"font-size: 10pt\" color=#FFFFFF";
var constTitleBarBg = "/cms_images/table_bg1.jpg";
var constTitleTdClass = "titleTdClass"; //add

var constButtonBarFrameHeadColor="#EFEFEF";
var constButtonBarBGColor="#EFEFEF";
var constButtonBarFrameBottomColor="#EFEFEF";
var constButtonBarHeight=20;
var constButtonHeadHeight=4;
var constButtonBottomHeight=4;

var constContentTitleBGColor="#EFEFEF";
var constContentTitleHeight=20;

var constIsXPStype=true;    //是否XP样式
var constTitleCheckBoxValue=false;   //标题行CheckBox值

var constTHeadClass = "headerTable";
  
var CONFIRMMESSAGE_MASK = 0x8000;
var FINISHMESSAGE_MASK = 0x4000;
var REVIEW_STATUS_MASK = 0x200;
var REVIEW_MASK = 0x100;
var CANCEL_MASK = 0x1;
var SPECIALSEND_MASK = 0x2;
var SENDBACK_MASK = 0x4;
var VIEWPROCESS_MASK = 0x8;
var SENDTO_MASK = 0x10;
var CYCLE_MASK = 0x20;
var CYCLE_STATUS = 0x40;
var SIGN_MASK = 0x400;
var SAVE_MASK = 0x800;
var COMPLETE_MASK = 0x1000;

//=================================================================
//printTitleBar(desc)
//显示提示条信息
//=================================================================

//=================================================================
//parameter 1:display action true or false
//parameter 2:button description
//parameter 3:gif name
//parameter 4:tip message
//parameter 5:function name
//=====================================================================
//var ArrAction1=new Array(true,"删除","del.gif","删除信息","del");
//var ArrAction2=new Array(true,"删除2","del.gif","删除信息2","del2");
//var ArrActions=new Array(ArrAction1,ArrAction2);

//=================================================================
//parameter 1:View description
//parameter 2:url 
//=====================================================================
//var ArrSinoView1=new Array("view1","url1");
//var ArrSinoView2=new Array("view2","url2");
//var ArrSinoViews=new Array(ArrSinoView1,ArrSinoView2);
//setViewName(desc);
//显示View描述,如：“页码”
//=================================================================

//=================================================================
//printToolBar
//显示按钮条信息，包括定义的ArrActions按钮和ArrSinoViews视图信息
//=================================================================

//=====================================================================
//parameter 1:Title description
//parameter 2:isCheckBox 
//parameter 3:width
//=====================================================================
//var ArrSinoTitle1=new Array("",true,"10%");
//var ArrSinoTitle2=new Array("name",false,"50%");
//var ArrSinoTitle3=new Array("group",false,"40%");
//var ArrSinoTitles=new Array(ArrSinoTitle1,ArrSinoTitle2,ArrSinoTitle3);
//printViewTitleHead();输出显示内容标题条的table信息
//printViewTitle();输出显示内容标题条
//printViewTitleBottom;//关闭显示内容标题条的table信息
//=====================================================================
	
	
//=================================================================
//ShowSinoButton(nPosition)
//显示第几个按钮，nPosition从0开始
//
//HideSinoButton(nPosition)
//隐藏第几个按钮，nPosition从0开始
//=================================================================