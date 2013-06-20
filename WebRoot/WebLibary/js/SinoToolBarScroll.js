var resizeTimerScrollID = 0;
var iScrollReadWindow = false;
var iScrollHRatio = 0;
var iScrollHRatio2 = 0;
var iScrollListH = 0;
var iScrollListReadH = 0;
var iScrollMenuH = 0;
var iScrollInit = false;
var constJudgeHeight=50;
var buttonMask = 0;
var completeMask = 0;

function setScrollBarJudgeHeight(h)
{
	constJudgeHeight=h;
}
function setButtonMask(mask) {
    buttonMask = mask;
}
function printToolBar()
{
	var nCount=ArrActions.length;
	document.write("<DIV Id=\"sinoButtonAll\" Style=\"Display:''\">\n");
	printButtonHead();
	
	for (i=0 ;i< nCount;i++){
		setButton(i,ArrActions[i][2],ArrActions[i][1],ArrActions[i][3],ArrActions[i][4]);
	}
	printViewList();
	printButtonBottom();
	document.write("</div>");

    if(document.getElementById("sf_caseID")) {
        if(document.getElementById("sf_caseID").value.indexOf(":") >= 0) {
            for (i=1 ;i< nCount;i++){
                ArrActions[i][0] = false;
            }
        } else {
            if((buttonMask & CANCEL_MASK) == CANCEL_MASK) {
                ArrActions[0][0] = false;
            }
            if((buttonMask & SPECIALSEND_MASK) == SPECIALSEND_MASK) {
                ArrActions[4][0] = false;
            }
            if((buttonMask & SENDBACK_MASK) == SENDBACK_MASK) {
                ArrActions[3][0] = false;
            }
            if((buttonMask & VIEWPROCESS_MASK) == VIEWPROCESS_MASK) {
                ArrActions[5][0] = false;
            }
            if((buttonMask & SENDTO_MASK) == SENDTO_MASK) {
                ArrActions[10][0] = false;
            }
            if((buttonMask & REVIEW_MASK) == REVIEW_MASK) {
                ArrActions[6][0] = false;
            }
            if((buttonMask & REVIEW_STATUS_MASK) == REVIEW_STATUS_MASK) {
                ArrActions[7][0] = false;
            }
            if((buttonMask & FINISHMESSAGE_MASK) == FINISHMESSAGE_MASK) {
                ArrActions[9][0] = false;
            }
            if((buttonMask & SIGN_MASK) == SIGN_MASK) {
                ArrActions[8][0] = false;
            }
            if((buttonMask & SAVE_MASK) == SAVE_MASK) {
                ArrActions[1][0] = false;
            }
            if((buttonMask & COMPLETE_MASK) == COMPLETE_MASK) {
                ArrActions[2][0] = false;
            }
            if((buttonMask & CYCLE_MASK) == CYCLE_MASK) {
                ArrActions[26][0] = false;
            }
            if((buttonMask & CYCLE_STATUS) == CYCLE_STATUS) {
                ArrActions[27][0] = false;
            }
        }
    }
    for (i=0 ;i< nCount;i++){
		if(ArrActions[i][0]==false){
			HideASinoButton(i);
		}
	}
	CheckSinoButton();
}
function setViewName(v){ varSinoViewName=v;}

function printTitleBar(desc)
{
	document.write("<table height="+constTitleBarHeight+" width=100% bgcolor="+constTitleBarBGColor+" border=0 cellpadding=0 cellspacing=0>\n");
	document.write("<tr>\n");
	document.write("<td nowrap align=left valign=bottom >&nbsp;<font  "+constTitleBarFont+"><b>>>"+desc+"</b></font> </td>\n");
	document.write("<td nowrap align=right valign=bottom ></td>\n");
	document.write("<td nowrap align=left valign=middle width=20><img src='"+constImgPath+"bull.gif' width=16 height=16></td>\n");
	document.write("</tr></table>\n");
}

function setButton(nPosition,imgFile,desc,tip,jsFun){
	printButton(nPosition,imgFile,desc,tip,jsFun);}

function printButtonHead()
{
	if (constIsXPStype)
	{
		document.write("<table height="+constButtonHeadHeight+" background="+constImgPath+"titlebar_xphead.gif border=0 cellpadding=0 cellspacing=0 width=100% >\n");
		document.write("<tr><td nowrap background="+constImgPath+"titlebar_xphead.gif ></td></tr></table>\n");
		document.write("<table height="+constButtonBarHeight+" width=100% background="+constImgPath+"toolbar_xp_bg.gif border=0 cellpadding=0 cellspacing=0>\n");
		document.write("<tr><td >\n");
		document.write("<table background="+constImgPath+"titlebar_xphead.gif border=0 cellpadding=0 cellspacing=0>\n");
		document.write("<tr background="+constImgPath+"toolbar_xp_bg.gif>\n");		
	}else{
		document.write("<table height="+constButtonHeadHeight+" bgcolor="+constButtonBarFrameHeadColor+" bordercolorlight=#808080 bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%>\n");
		document.write("<tr><td ></td></tr></table>\n");
		document.write("<table height="+constButtonBarHeight+" width=100% bgcolor="+constButtonBarBGColor+" border=0 cellpadding=0 cellspacing=0>\n");
		document.write("<tr>\n");
		document.write("<td >\n");
		document.write("<table bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0>\n");
		document.write("<tr bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF >\n");
	}
	
}


function printButtonBottom()
{
	if (constIsXPStype)
	{
		document.write("<table height="+constButtonBottomHeight+" background="+constImgPath+"titlebar_xpBottom.gif bordercolordark=#808080 border=0 cellpadding=0 cellspacing=0 width=100%>\n");
		document.write("<tr><td nowrap background="+constImgPath+"titlebar_xpBottom.gif></td></tr></table>\n");
		document.write("</tr></table></td></tr></table>\n");
	}else{
		document.write("<table height="+constButtonBottomHeight+" bgcolor="+constButtonBarFrameBottomColor+" bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%>\n");
		document.write("<tr><td ></td></tr></table>\n");
		document.write("</tr></table></td></tr></table>\n");
	}
}
function printVerLine(nPosition)
{
	var imgFile="toolbar_xp2.gif";
	if (constIsXPStype)
	{
		if (nPosition==0){
			imgFile="toolbar_xp1.gif";
		}
		document.write("<td nowrap>\n");
		document.write("<table height="+constButtonBarHeight+" border=0 cellpadding=0 cellspacing=0>\n");
		document.write("<tr><td nowrap align=center background="+constImgPath+"toolbar_xp_bg.gif valign=middle><img src="+constImgPath+imgFile+"  height=20></td></tr>\n");
		document.write("</table>\n");
		document.write("</td>\n");
	}else{
		document.write("<td nowrap>\n");
		document.write("<table height="+constButtonBarHeight+" border=0 cellpadding=0 cellspacing=0>\n");
		document.write("<tr><td nowrap align=center bgcolor=#EFEFEF valign=middle><img src="+constImgPath+"toolbar1.gif width=3 height=20></td></tr>\n");
		document.write("</table>\n");
		document.write("</td>\n");
	}
}
function mOver(menuItem) {
   menuItem.background=constImgPath+"titlebar_xpMove.gif";
   menuItem.borderColorDark='#FFFFFF';
   menuItem.borderColorLight='#808080'
}
function mOut(menuItem) {
   menuItem.background=constImgPath+"toolbar_xp_bg.gif";
}

function ShowSinoButton(nPosition)
{
    var nCount=ArrActions.length;
	
	if (nPosition>nCount){
		return false;
	}
	
	var DivId="sinoDiv"+nPosition;
	document.all[DivId].style.display='';
	CheckSinoButton();
	return true;
}
function HideSinoButton(nPosition){
	var retValue=HideASinoButton(nPosition);
	CheckSinoButton();
	return retValue;
}
function HideASinoButton(nPosition)
{
	var nCount=ArrActions.length;
	
	if (nPosition>=nCount){
		return false;
	}
	
	var DivId="sinoDiv"+nPosition;

	document.all[DivId].style.display='none';
	return true;
}
function CheckSinoButton()
{
	var nCount=ArrActions.length;
	var hasButton=false;
	for(i=0 ;i<nCount;i++){
		if (document.all["sinoDiv"+i].style.display=='') hasButton=true;
	}
	
	var DivId="sinoButtonAll";
	if (hasButton){
		document.all[DivId].style.display='';
	}else{
		document.all[DivId].style.display='none';
	}
}
function printButton(nPosition,imgFile,desc,tip,jsFun)
{
	if (constIsXPStype)
	{
		document.write("<td nowrap Id=\"sinoDiv"+nPosition+"\" Style=\"Display:''\"><table background="+constImgPath+"toolbar_xp_bg.gif border=0 cellpadding=0 cellspacing=0 ><tr>");
		printVerLine(nPosition);
		document.write("<td nowrap  align=absmiddle  >\n");
		document.write("<table height="+constButtonBarHeight+" background="+constImgPath+"toolbar_xp_bg.gif border=0 cellpadding=0 cellspacing=0 \n");
		if (jsFun.indexOf("(")>0){
			document.write("onClick=\"javascript:"+jsFun+"\" STYLE='cursor:hand'>\n");
		}else{
			document.write("onClick=\"javascript:"+jsFun+"()\" STYLE='cursor:hand'>\n");
		}
		document.write("<tr nowrap ><td onMouseOver=\"javascript:mOver(this);\" onMouseOut=\"javascript:mOut(this);\" align=center background="+constImgPath+"toolbar_xp_bg.gif valign=middle>\n");
		document.write("&nbsp;<img src=\""+constImgPath+imgFile+"\" width=16 height=16 alt=\""+tip+"\" align=absmiddle> <font color=#000000 title=\""+desc+"\">"+desc+"</font> &nbsp;</td></tr>\n");
		document.write("</table>\n");
		document.write("</td>\n");
	}else{
		document.write("<td nowrap Id=\"sinoDiv"+nPosition+"\" Style=\"Display:''\"><table bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0 ><tr>");
		printVerLine(nPosition);
		document.write("<td nowrap  align=absmiddle>\n");
		document.write("<table height="+constButtonBarHeight+" bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0\n");
		document.write("onMouseOver=\"javascript:this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';\"\n");
		document.write("onMouseOut=\"javascript:this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';\"\n");
		document.write("onMouseDown=\"javascript:this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'\"\n");
		document.write("onMouseUp=\"javascript:this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'\"\n");
		if (jsFun.indexOf("(")>0){
			document.write("onClick=\"javascript:"+jsFun+"\" STYLE='cursor:hand'>\n");
		}else{
			document.write("onClick=\"javascript:"+jsFun+"()\" STYLE='cursor:hand'>\n");
		}
		document.write("<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>\n");
		document.write("&nbsp;<img src=\""+constImgPath+imgFile+"\" width=16 height=16 alt=\""+tip+"\" align=absmiddle> <font color=#000000 title=\""+desc+"\">"+desc+"</font>&nbsp;</td></tr>\n");
		document.write("</table>\n");
		document.write("</td>\n");
	}
	document.write("</tr></table></td>");
}

function printViewList(){
	if (ArrSinoViews.length<1){ return;}
	printVerLine();
	
	if (constIsXPStype)
	{
		document.write("<td align=absmiddle noWrap>\n");
		document.write("<table border=0 background="+constImgPath+"toolbar_xp_bg.gif cellPadding=0 cellSpacing=0 height="+constButtonBarHeight+"  >\n");
		document.write("<tbody><tr>\n");
		document.write("<td  align=center background="+constImgPath+"toolbar_xp_bg.gif align=middle noWrap vAlign=middle>&nbsp;"+varSinoViewName+"\n");
		document.write("</td>  \n");
		document.write("<td align=middle  noWrap vAlign=middle>\n");
		document.write("<select accessKey=\"v\" name=\"viewSelect\" onchange=\"doChangeView(viewSelect.value)\">\n");

	}else{	
		document.write("<td align=absmiddle noWrap>\n");
		document.write("<table border=1 borderColorDark=#EFEFEF borderColorLight=#EFEFEF cellPadding=0 cellSpacing=0 height="+constButtonBarHeight+"  >\n");
		document.write("<tbody><tr>\n");
		document.write("<td align=middle noWrap vAlign=middle>"+varSinoViewName+"\n");
		document.write("</td>  \n");
		document.write("<td align=middle noWrap vAlign=middle>\n");
		document.write("<select accessKey=\"v\" name=\"viewSelect\" onchange=\"doChangeView(viewSelect.value)\">\n");
	}
	nCount=ArrSinoViews.length;
	
	for(var i=0;i<nCount;i++)
	{
		document.write("<option selected value=\""+ArrSinoViews[i][1]+"\">"+ArrSinoViews[i][0]+"</option>\n");
	}
	document.write("</select></td></tr></tbody></table></td>\n");
}

function printViewTitleHead()
{
	nCount=ArrSinoTitles.length;
	if (nCount<1) return;
	
	document.write("<table height="+constContentTitleHeight+" width=99% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF \n");
	document.write("border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>\n");	
}

function scroll_initTimer() {
	iScrollReadWindow = (eval(parent) && eval(parent.document) && eval(parent.document.body) && eval(top.topframe)) ? true : false;
	if (iScrollReadWindow) {
		iScrollListH = document.body.clientHeight;
		iScrollListReadH = parent.document.body.clientHeight;
		iScrollHRatio = (iScrollListH)*100/(iScrollListReadH);
	}
}
function scroll_init() {
	//hidden ie scrollbar
	document.body.style.overflow='hidden';
	
	menulayer = new DynLayer("menuDiv")
	menulayer.slideInit()
	iScrollInit = true;
	scroll_movemenulayer();

}
function scroll_movemenulayer() {
	if (iScrollInit == false)
		scroll_init();
		
	menulayer.setWidth(document.body.clientWidth);

	menulayer.setHeight(document.body.clientHeight-iScrollMenuH)

}
function scroll_window_onresize() {
	scroll_movemenulayer()
}
function printViewTitleBottom()
{
	nCount=ArrSinoTitles.length;
	
	if (nCount<1) return;
	
	
	document.write("</table>\n");	
	document.write("</DIV>");

	iScrollMenuH =constButtonBarHeight+constJudgeHeight;	
	setTimeout("scroll_initTimer()", 3000)
	
	iScrollListReadH=document.body.scrollHeight;
	
	scroll_init();
}
function printViewTitle()
{
//zzj
	document.write("<script language=\"javascript\" src=\"/WebLibary/js/dynlayer.js\"></script>");
	document.write( "<STYLE TYPE=\"text/css\">");
	document.write( "<!--");
	document.write( "#menuDiv {position:absolute; left:0; top:document.body.scrollHeight;  width:document.body.clientWidth; height:document.body.clientHeight-document.body.scrollHeight; ");
	document.write( "				clip:rect(0,3000,3000,0); layer-background-color:white; background-color:white; overflow:auto;}");
	document.write( "-->");
	document.write( "</STYLE>");

	//document.write("<DIV id=menuDiv>");
	
	nCount=ArrSinoTitles.length;
	if (nCount<1) return;
	
	document.write("<THEAD >\n");
	
	document.write("<tr>\n");
	for(i=0;i<nCount;i++)
	{
		if (ArrSinoTitles[i][1]){
			document.write("<th nowrap>\n");
			document.write("<table height="+constContentTitleHeight+" bgcolor="+constContentTitleBGColor+" bordercolorlight=#FFFFFF bordercolordark=#808080 \n");
			document.write("border=1 cellpadding=0 cellspacing=0 width=100%  onMouseDown=\"javascript:this.borderColorDark='#FFFFFF';this.borderColorLight='#505050'\"\n");
			document.write(" onMouseOut=\"javascript:this.borderColorDark='#808080';this.borderColorLight='#FFFFFF'\"\n");
			document.write(" onMouseUp=\"javascript:this.borderColorDark='#808080';this.borderColorLight='#FFFFFF'\"\n");
			document.write(" onClick=\"javascript:onCheckBoxClick()\" STYLE='cursor:hand'>\n");
			document.write("<tr><td bgcolor="+constContentTitleBGColor+" bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align=center> <img name=\"sinoCheckBox\" src=\""+constImgPath+"t_uncheck.gif\" width=12 height=12></td></tr>\n");
			document.write("</table>\n");
			document.write("</th>\n");	
		}else{
			document.write("<th nowrap width="+ArrSinoTitles[i][2]+">\n");
			document.write("<table height="+constContentTitleHeight+" bgcolor="+constContentTitleBGColor+" bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100% >\n");
			document.write("<tr><td bgcolor="+constContentTitleBGColor+" bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align=\"LEFT\"> "+ArrSinoTitles[i][0]+"</td></tr>\n");
			document.write("</table>\n");
			document.write("</th>\n");
		}
	}
	document.write("</tr>\n");
	document.write("</THEAD>\n");

	document.write("</table>\n");
	document.write("<DIV id=menuDiv>");
	
	document.write("<table id=menuTable height="+constContentTitleHeight+" width=99% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF \n");
	document.write("border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>\n");
	
	
}

function onCheckBoxClick()
{
	if (constTitleCheckBoxValue){
		document.sinoCheckBox.src=constImgPath+"t_uncheck.gif";
		constTitleCheckBoxValue=false;
	}else{
		document.sinoCheckBox.src=constImgPath+"t_check.gif";
		constTitleCheckBoxValue=true;
	}
	CheckListEx();
}

window.onresize=function(){scroll_window_onresize();};

