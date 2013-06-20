var menubarsum = 0;
var menusmallicon = false;
var bodywidth = document.body.clientWidth-2;
var bodywidth2 = 0;
var menubarheight = 0;
var menuspeed = 10;
var menuinc = 100;
var scrollspeed = 100;
var scrollinc = 60;
var menuchoose = 0;
var iconX = new Array(menubarsum);
//alert(menubarsum);

var menuIconWidth = new Array(menubarsum);
var menuIconHeight = new Array(menubarsum);
var menuscroll = 0;
var iconareaheight = 0;
var iconrightpos = 0;
var maxscroll = 0;
var scrolling = false;
var scrollTimerID = 0;
var resizeTimerID = 0;
var menuLeftMargin = 8;

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// TOC code
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////
var imgBookClose ="/SinoFile/img/pub/xp_navcollapse.gif"; //"bssctoc1.gif";
var imgBookOpen  ="/SinoFile/img/pub/xp_navexpand.gif"; //"bssctoc2.gif";

var  elTocArray = new Array();
var  elTocArrayNum = 0;
var  MaxParentNum = 0;

function init() {
	if (menubarsum > 0) {
		menubarheight = 22
		menulayer = new Array(menubarsum)
		iconlayer = new Array(menubarsum)
		barlayer = new Array(menubarsum)
		uplayer = new Array(menubarsum)
		downlayer = new Array(menubarsum)
		for (var i=0; i<menubarsum; i++) {
			menulayer[i] = new DynLayer("menulayer" + i + "Div")
			menulayer[i].slideInit()
			iconlayer[i] = new DynLayer("iconlayer" + i + "Div", "menulayer" + i + "Div")
			iconlayer[i].slideInit()
			if (menuIconWidth[i] > document.body.clientWidth) {
				iconlayer[i].setWidth(menuIconWidth[i])
				if (menusmallicon) 
					iconX[i] = 0
				else
					iconX[i] = (document.body.clientWidth - menuLeftMargin - menuIconWidth[i])/2
			} else {
				iconlayer[i].setWidth(document.body.clientWidth - menuLeftMargin)
				iconX[i] = 0
			}
			iconlayer[i].moveTo(iconX[i], menubarheight)
			barlayer[i] = new DynLayer("barlayer" + i + "Div", "menulayer" + i + "Div")
			barlayer[i].slideInit()
			uplayer[i] = new DynLayer("uplayer" + i + "Div", "menulayer" + i + "Div")
			uplayer[i].slideInit()
			downlayer[i] = new DynLayer("downlayer" + i + "Div", "menulayer" + i + "Div")
			downlayer[i].slideInit()
		}
		menureload()
	}
	if(MaxParentNum>0){
		TocExpandIt('Book0');
	}
}

/* 滑动函数，在点击表格时触发 */
function menubarpush(num) {
	if (num != menuchoose && num >= 0 && num < menubarsum) {
	
		iconlayer[menuchoose].moveTo(iconX[menuchoose],menubarheight);
		menuscroll = 0;
		scrolling = false;
	
		for (var i=0; i <=num; i++) {
		    menulayer[i].slideTo(0, i*menubarheight, menuinc, menuspeed);
		}
		nAdCornerOriginY = document.body.clientHeight;
		nAdCornerOriginY += document.body.scrollTop;
		for (var i=menubarsum-1; i>num; i--) {
			nAdCornerOriginY -= menubarheight;
			menulayer[i].slideTo(0,nAdCornerOriginY, menuinc, menuspeed);
		}
		menuchoose = num;
		menuscrollbar();
	}
}


function menureload() {
	nAdCornerOriginY = document.body.clientHeight;
	nAdCornerOriginY += document.body.scrollTop;
	for (var i=menubarsum-1; i>menuchoose; i--) {
		nAdCornerOriginY -= menubarheight
		menulayer[i].moveTo(0, nAdCornerOriginY)
	}
	for (var i=0; i<menubarsum; i++) {
		barlayer[i].setWidth(document.body.clientWidth)
		if (menuIconWidth[i] > document.body.clientWidth) {
			iconlayer[i].setWidth(menuIconWidth[i])
			if (menusmallicon) 
				iconX[i] = 0
			else
				iconX[i] = (document.body.clientWidth - menuLeftMargin - menuIconWidth[i])/2
		} else {
			iconlayer[i].setWidth(document.body.clientWidth - menuLeftMargin)
			iconX[i] = 0
		}
		iconlayer[i].moveX(iconX[i], menubarheight)
	}
	
	
	menuscrollbar()
}


function menuscrollbar() {
	iconareaheight = document.body.clientHeight-menubarheight*(menubarsum);
	iconrightpos = document.body.clientWidth-16-4-menuLeftMargin;
	maxscroll = menuIconHeight[menuchoose] - iconareaheight
	
	
	
	if (maxscroll > 0) {
		if (menuscroll > 0) {
			uplayer[menuchoose].moveTo(iconrightpos, menubarheight+4) 
		} else {
			uplayer[menuchoose].moveTo(-20-menuLeftMargin, 0)
		}
		if (menuscroll < maxscroll) {
			downlayer[menuchoose].moveTo(iconrightpos, iconareaheight+2)
		} else {
			downlayer[menuchoose].moveTo(-20-menuLeftMargin, 0)
		}
	} else {
		if (menuscroll <= 0) 
			uplayer[menuchoose].moveTo(-20-menuLeftMargin, 0)
		downlayer[menuchoose].moveTo(-20-menuLeftMargin, 0)
	}
}


function menuscrollup() {
	if (menuscroll > 0) {
		scrolling = true
		menuscroll -= scrollinc
		iconlayer[menuchoose].moveTo(iconX[menuchoose], menubarheight-menuscroll)
		
		scrollTimerID = setTimeout("menuscrollup()", scrollspeed)
	} else {
		menuscrollstop()	
	}
	menuscrollbar()
	
}

function menuscrolldown() {
	if (menuscroll < maxscroll) {
		scrolling = true
		menuscroll += scrollinc
		if (menuscroll < maxscroll) {
			iconlayer[menuchoose].moveTo(iconX[menuchoose], menubarheight-menuscroll)
		} else {
			iconlayer[menuchoose].moveTo(iconX[menuchoose], menubarheight-maxscroll)
		}
		
		scrollTimerID = setTimeout("menuscrolldown()", scrollspeed)
	} else {
		menuscrollstop()	
	}

	menuscrollbar()
	
}

function menuscrollstop() {
	scrolling = false
	if (scrollTimerID) {
		clearTimeout(scrollTimerID)
		scrollTimerID = 0;
	}
	
}





function TocWriteClassStyle()
{
	document.write("<STYLE TYPE='text/css'>");
	document.write(".parent {font-size:10pt;}");
	document.write("P {font-family:'宋体,Arial'; font-size:9pt; margin-top:0pt; margin-bottom:0pt;}");
	document.write("PRE {font-family:'Arial'; font-size:8pt; margin-top:0pt; margin-bottom:0pt;}");
	document.write(".child {display:none}");
	document.write("A:link {text-decoration: none; color: 000000}");
	document.write("A:visited {text-decoration: none; color: 333333}");
	document.write("A:active {text-decoration: none; color: red}");
	document.write("A:hover {text-decoration: none; color: red}");
	document.write("</STYLE>");
	return;
}

//Assign the layer's visibility at initialize the Page
function TocInitPage()
{
	// Select the first hyperlink
	var tempColl = document.all.tags("A");
	if (tempColl.length > 0) {
		tempColl(0).focus();
	}
	return;
}

function TocShowLayer(whichEl, bShow)
{
	if (bShow) {
		whichEl.visibility = "show";
	} else {
		whichEl.visibility = "hide";
	}
	for (var i=0; i<whichEl.document.layers.length; i++) {
		var whichChildEl = whichEl.document.layers[i];
		ShowLayer(whichChildEl, bShow);
	}
}

function TocExpandAll()
{
	for (var i=0; i<MaxParentNum; i++) {
		if(document.all("Book"+i+"Child") != null){
			var whichEl = eval("Book"+i+"Child");
			var whichIm = document.images["Book"+i];
			whichEl.style.display = "block";
			whichIm.src = imgBookOpen;
		}
	}
	return;
}

function TocCollapseAll()
{
	for (var i=0; i<MaxParentNum; i++) {
		if(document.all("Book"+i+"Child") != null){
			var whichEl = eval("Book"+i+"Child");
			var whichIm = document.images["Book"+i];
			whichEl.style.display = "none";
			whichIm.src = imgBookClose;
		}
	}
	return;
}


//Expand a layer
function TocExpandIt(elId)
{
	TocCollapseAll();
	var child = TocExpandIE(elId, true, false);
	menuIconHeight[menuchoose] = iconlayer0Div.scrollHeight + 0;
	menuscrollbar();
	return;
}


////////////////////////////////////////////////////////////////
//Functions for IE
function TocExpandIE(elId, bChangeImg, bForceOpen)
{
	var whichEl = eval(elId + "Child");
	var whichIm = document.images[elId];

	if (whichEl == null) {
		return null;
	}

	if ((whichEl.style.display != "block") || bForceOpen) {
		whichEl.style.display = "block";
		if (bChangeImg) {
			whichIm.src = imgBookOpen;		
		}
	} else {
		whichEl.style.display = "none";
		if (bChangeImg) {
			whichIm.src = imgBookClose;
		}
	}
	return whichEl;
}
