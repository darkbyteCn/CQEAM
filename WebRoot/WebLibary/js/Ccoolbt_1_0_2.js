////////////////////////////////////////////////////////////
//
//	Component Cool button v 1.0
//	Author: Cui Riven
//	Date  : 10:25 2003-5-3
//	
//		Do not remove the following line:
//		Copyright (c) 2003 Riven Cui
//
/////////////////////////////////////////////////////////////

var CBT_CheckClassName = false; //显示是否检测class属性，如果为false,则会把页面中的所有控件设置成CoolBtn
var CBT_ClassName = "coolbtn"; //class name
var CBT_MAX_CONTROL_COUNT = 2000; //一个页面中的最大控件数
var CBT_INVOKE_ONLOAD = true; //指示是否在页面加载完毕后自动执行
var CBT_HANDLE_RIGHT_BUTTON = false; //指示是否响应右键
var CBT_MAJOR_VERSION = 1;
var CBT_MINOR_VERSION = 0;
var CBT_UPDATE_VERSION = 1;
/*+------------------------------------------
Class coolBtStyleClass()
Identified the cool button styles.
--------------------------------------------+*/
function coolBtStyleClass() {
/* All states */
this.bExtendable = true;
this.sTransparentColor = ""; //set a color for the button images (It's better to use a transparent gif instead)
this.sTextPadding = "10";  //set the padding width between the text and the button
this.sTextOtherStyles = "font-weight:bold;font-family:arial"; // set other css styles
this.bEnableTransition = false; // enable transition visual filter;
this.bEnableBlendTrans = true;
this.bEnableShadow = false;
this.bEnableGlow = false;
this.bEnableAlpha = false; //enable alpha filter (Added v1.0.1)
/* All states end */

/* Commom state */
this.sCommonRightImg = "../images/btn_n3.bmp"; //button right image
this.sCommonLeftImg  = "../images/btn_n1.bmp";  //button left image
this.sCommonMidImg   = "../images/btn_n2.bmp";
this.sTextColor = "#0066cc";
this.sTextSize = "9pt";
this.bCommonTransition = true;
this.iCommonTransition = 4;
this.fCommonTransitionDuration = 0.3;
this.bCommonBlendTrans = true;
this.fCommonBlendTransDuration = 0.5;
this.bCommonShadow = false;
this.sCommonShadowColor = "black";
this.iCommonShadowStrength = 2;
this.iCommonShadowDirection = 135; //bottom right;
this.bCommonGlow = true;
this.sCommonGlowColor = "yellow";
this.iCommonGlowStrength = 30;
this.bCommonAlpha = true; //enable common alpha filter (added v.1.0.1)
this.iCommonAlphaOpacity = 60; // Alpha value 0-100;
this.iCommonAlphaStyle = 0; // Alpha filter style 0-3;
/* Common state end */

/* Hover state */
this.sHoverRightImg = "../images/btn_f3.bmp"; //button right image
this.sHoverLeftImg  = "../images/btn_f1.bmp";  //button left image
this.sHoverMidImg   = "../images/btn_f2.bmp";
this.sTextHoverColor = "orange"; 
this.sTextHoverSize = "9pt"; // sets the font size of the text
this.sHoverCursor = "hand";
this.bHoverTransition = true;
this.iHoverTransition = 19;
this.fHoverTransitionDuration = 0.2;
this.bHoverBlendTrans = true;
this.fHoverBlendTransDuration = 0.2;
this.bHoverShadow = true;
this.sHoverShadowColor = "#2244aa";
this.iHoverShadowStrength = 2;
this.iHoverShadowDirection = 135; //bottom right;
this.bHoverGlow = true;
this.sHoverGlowColor = "blue";
this.iHoverGlowStrength = 10;
this.bHoverAlpha = true; //enable Hover alpha filter (added v.1.0.1)
this.iHoverAlphaOpacity = 100; // Alpha value 0-100;
this.iHoverAlphaStyle = 0; // Alpha filter style 0-3;
/* Hover state end */

/* Down state */
this.sDownRightImg = "../images/btn_d3.jpg"; //button right image
this.sDownLeftImg  = "../images/btn_d1.jpg";  //button left image
this.sDownMidImg   = "../images/btn_d2.jpg";
this.sTextDownColor = "gray"; 
this.sTextDownSize = "9pt"; // sets the font size of the text
this.sDownCursor = "default";
this.bDownTransition = true;
this.iDownTransition = 5;
this.fDownTransitionDuration = 0.1;
this.bDownBlendTrans = true;
this.fDownBlendTransDuration = 0.1;
this.bDownShadow = true;
this.sDownShadowColor = "gray";
this.iDownShadowStrength = 2;
this.iDownShadowDirection = 135; //bottom right;
this.bDownGlow = true;
this.sDownGlowColor = "yellow";
this.iDownGlowStrength = 5;
this.bDownAlpha = true; //enable down alpha filter (added v.1.0.1)
this.iDownAlphaOpacity = 70; // Alpha value 0-100;
this.iDownAlphaStyle = 0; // Alpha filter style 0-3;
/* Down state end */

/* disabled state */
this.sDisRightImg = "btn_u3.jpg"; //button right image
this.sDisLeftImg  = "btn_u1.jpg";  //button left image
this.sDisMidImg   = "btn_u2.jpg";
this.sTextDisColor = "white"; 
this.sTextDisSize = "9pt"; // sets the font size of the text
/* disabled state end */
}


function btDoMouseOver(oTable) {
if (oTable.disabled) {return}
if (event.fromElement) {
	if (checkIsChildNode(oTable,event.fromElement)) {return}
}

oStyle = oTable.oBtStyle;
//oTDText = oTable.firstChild.firstChild.childNodes[1];
if (oStyle.bEnableTransition && oStyle.bHoverTransition) {
	oTable.filters.RevealTrans.transition = oStyle.iHoverTransition;
	oTable.filters.RevealTrans.duration = oStyle.fHoverTransitionDuration;
	oTable.filters.RevealTrans.apply();
}
if (oStyle.bEnableBlendTrans && oStyle.bHoverBlendTrans) {
	oTable.filters.BlendTrans.duration = oStyle.fHoverBlendTransDuration;
	oTable.filters.BlendTrans.apply();
}

if (oStyle.bEnableShadow && oStyle.bHoverShadow) {
	oTable.filters.shadow.color = oStyle.sHoverShadowColor;
	oTable.filters.shadow.strength = oStyle.iHoverShadowStrength;
	oTable.filters.shadow.direction = oStyle.iHoverShadowDirection;
	oTable.filters.shadow.enabled = true;
}
else {
	if(oStyle.bEnableShadow) oTable.filters.shadow.enabled = false;
}

if (oStyle.bEnableGlow && oStyle.bHoverGlow) {
	oTable.filters.glow.color = oStyle.sHoverGlowColor;
	oTable.filters.glow.strength = oStyle.iHoverGlowStrength;
	oTable.filters.glow.enabled = true;
}
else {
	if(oStyle.bEnableGlow) oTable.filters.glow.enabled = false;
}

if (oStyle.bEnableAlpha && oStyle.bHoverAlpha) {
	oTable.filters.alpha.enabled = true;
	oTable.filters.alpha.opacity = oStyle.iHoverAlphaOpacity;
	oTable.filters.alpha.style = oStyle.iHoverAlphaStyle;
}
else {
	if(oStyle.bEnableGlow) oTable.filters.alpha.enabled = false ;
}


oTable.style.color = oStyle.sTextHoverColor;
oTable.style.fontSize = oStyle.sTextHoverSize;
oTable.style.cursor = oStyle.sHoverCursor;
oTbody = oTable.firstChild;
oTR = oTbody.firstChild;
oTR.childNodes[0].childNodes[0].src = oStyle.sHoverLeftImg; // set left image;
oTR.childNodes[2].childNodes[0].src = oStyle.sHoverRightImg; // set right image;
oTR.childNodes[1].style.backgroundImage = "url(" + oStyle.sHoverMidImg + ")"; // set Middle image;

if (oStyle.bEnableTransition && oStyle.bHoverTransition) {
	oTable.filters.RevealTrans.play();
}
if (oStyle.bEnableBlendTrans && oStyle.bHoverBlendTrans) {
	oTable.filters.BlendTrans.play();
}

}

function btDoMouseOut(oTable) {
if (oTable.disabled) {return}
if (event.toElement) {
	if (checkIsChildNode(oTable,event.toElement)) {return}
}

oStyle = oTable.oBtStyle;
//oTDText = oTable.firstChild.firstChild.childNodes[1];
if (oStyle.bEnableTransition && oStyle.bCommonTransition) {
	oTable.filters.RevealTrans.transition = oStyle.iCommonTransition;
	oTable.filters.RevealTrans.duration = oStyle.fCommonTransitionDuration;
	oTable.filters.RevealTrans.apply();
}
if (oStyle.bEnableBlendTrans && oStyle.bCommonBlendTrans) {
	oTable.filters.BlendTrans.duration = oStyle.fCommonBlendTransDuration;
	oTable.filters.BlendTrans.apply();
}
if (oStyle.bEnableShadow && oStyle.bCommonShadow) {
	oTable.filters.shadow.color = oStyle.sCommonShadowColor;
	oTable.filters.shadow.strength = oStyle.iCommonShadowStrength;
	oTable.filters.shadow.direction = oStyle.iCommonShadowDirection;
	oTable.filters.shadow.enabled = true;
}
else {
	if(oStyle.bEnableShadow) oTable.filters.shadow.enabled = false;
}

if (oStyle.bEnableGlow && oStyle.bCommonGlow) {
	oTable.filters.glow.color = oStyle.sCommonGlowColor;
	oTable.filters.glow.Strength = oStyle.iCommonGlowStrength;
	oTable.filters.glow.enabled = true;
}
else {
	if(oStyle.bEnableGlow) oTable.filters.glow.enabled = false;
}

if (oStyle.bEnableAlpha && oStyle.bCommonAlpha) {
	oTable.filters.alpha.enabled = true;
	oTable.filters.alpha.opacity = oStyle.iCommonAlphaOpacity;
	oTable.filters.alpha.style = oStyle.iCommonAlphaStyle;
}
else {
	if(oStyle.bEnableGlow) oTable.filters.alpha.enabled = false ;
}


oTable.style.color = oStyle.sTextColor;
oTable.style.fontSize = oStyle.sTextSize;
oTbody = oTable.firstChild;
oTR = oTbody.firstChild;
oTR.childNodes[0].childNodes[0].src = oStyle.sCommonLeftImg; // set left image;
oTR.childNodes[2].childNodes[0].src = oStyle.sCommonRightImg; // set right image;
oTR.childNodes[1].style.backgroundImage = "url(" + oStyle.sCommonMidImg + ")"; // set Middle image;

if (oStyle.bEnableTransition && oStyle.bCommonTransition) {
	oTable.filters.RevealTrans.play();
}
if (oStyle.bEnableBlendTrans && oStyle.bCommonBlendTrans) {
	oTable.filters.BlendTrans.play();
}
}

function btDoMouseDown(oTable) {
if (oTable.disabled) {return}
if (event.button == 2 && !CBT_HANDLE_RIGHT_BUTTON) {return}
oStyle = oTable.oBtStyle;
//oTDText = oTable.firstChild.firstChild.childNodes[1];
if (oStyle.bEnableTransition && oStyle.bDownTransition) {
	oTable.filters.RevealTrans.transition = oStyle.iDownTransition;
	oTable.filters.RevealTrans.duration = oStyle.fDownTransitionDuration;
	oTable.filters.RevealTrans.apply();
}
if (oStyle.bEnableBlendTrans && oStyle.bDownBlendTrans) {
	oTable.filters.BlendTrans.duration = oStyle.fDownBlendTransDuration;
	oTable.filters.BlendTrans.apply();
}
if (oStyle.bEnableShadow && oStyle.bDownShadow) {
	oTable.filters.shadow.color = oStyle.sDownShadowColor;
	oTable.filters.shadow.strength = oStyle.iDownShadowStrength;
	oTable.filters.shadow.direction = oStyle.iDownShadowDirection;
	oTable.filters.shadow.enabled = true;
}
else {
	if(oStyle.bEnableShadow) oTable.filters.shadow.enabled = false ;
}


if (oStyle.bEnableGlow && oStyle.bDownGlow) {
	oTable.filters.glow.enabled = true;
	oTable.filters.glow.color = oStyle.sDownGlowColor;
	oTable.filters.glow.strength = oStyle.iDownGlowStrength;
}
else {
	if(oStyle.bEnableGlow) oTable.filters.glow.enabled = false ;
}

if (oStyle.bEnableAlpha && oStyle.bDownAlpha) {
	oTable.filters.alpha.enabled = true;
	oTable.filters.alpha.opacity = oStyle.iDownAlphaOpacity;
	oTable.filters.alpha.style = oStyle.iDownAlphaStyle;
}
else {
	if(oStyle.bEnableGlow) oTable.filters.alpha.enabled = false ;
}


oTable.style.color = oStyle.sTextDownColor;
oTable.style.fontSize = oStyle.sTextDownSize;
oTable.style.cursor = oStyle.sDownCursor;
oTbody = oTable.firstChild;
oTR = oTbody.firstChild;
oTR.childNodes[0].childNodes[0].src = oStyle.sDownLeftImg; // set left image;
oTR.childNodes[2].childNodes[0].src = oStyle.sDownRightImg; // set right image;
oTR.childNodes[1].style.backgroundImage = "url("+oStyle.sDownMidImg+")"; // set Middle image;

if (oStyle.bEnableTransition && oStyle.bDownTransition) {
	oTable.filters.RevealTrans.play();
}
if (oStyle.bEnableBlendTrans && oStyle.bDownBlendTrans) {
	oTable.filters.BlendTrans.play();
}

}

function btDoMouseUp(oTable) {
if (oTable.disabled) {return}
btDoMouseOver(oTable); // simply call the btDoMouseOver() function to reset the state to hover
}

function checkIsChildNode(parentNode,childNode) {
return parentNode.contains(childNode);
}

function getButtonHtml(oOriginBt,oStyle){
if (typeof(oOriginBt)!="object") {return}
sText = oOriginBt.value;
sId = oOriginBt.id+"_coolBt";
sIcon = oOriginBt.cbtIcon;
sToolTip = oOriginBt.title;
sHtm ='<a name=coolButton onselectstart="return false"  ';
//sHtm +=' onclick="this.childNodes(0).onClickOrigin()" '; //removed in ver 1.0.2
sHtm +=' onclick="' + oOriginBt.id +'.click()" ';
sHtm +='>';
sHtm += '<table border=0 cellpadding="0" cellspacing="0" ';
sHtm += ' title="'+sToolTip+'" ';
sHtm +='onMouseover=\"btDoMouseOver(this);this.onMouseOverOrigin()\" ' ;
sHtm +='onMouseout=\"btDoMouseOut(this);this.onMouseOutOrigin()\" ' ;
sHtm +='onMouseDown=\"btDoMouseDown(this);this.onMouseDownOrigin()\" ';
sHtm +='onMouseUp=\"btDoMouseUp(this);this.onMouseUpOrigin()\" ';
sHtm +='onContextMenu="return false" ';
sHtm +='onSelectStart="return false" ';
sHtm +='style="cursor:hand;';
sHtm +='font-size:'+oStyle.sTextSize+";";
sHtm +='display:inline ;';
sHtm +='color:'+oStyle.sTextColor+";";
sHtm +=oStyle.sTextOtherStyles +";";
sHtm +='filter:';
	if (oStyle.sTransparentColor!="" && oStyle.sTransparentColor!=null) {
		sHtm +='chroma(color='+oStyle.sTransparentColor+') ';
	}

	if (oStyle.bEnableTransition) {
		sHtm +='revealTrans(transition='+oStyle.iCommonTransition+',Duration='+oStyle.fCommonTransitionDuration+') ';
	}
	if (oStyle.bEnableBlendTrans) {
		sHtm +=' blendTrans(Duration='+oStyle.fCommonBlendTransDuration+') ';
	}
	if (oStyle.bEnableShadow) {
		sHtm +='shadow(Color='+oStyle.sCommonShadowColor;
		sHtm +=',enabled='+oStyle.bCommonShadow;
		sHtm +=',strength='+oStyle.iCommonShadowStrength+',Direction='+oStyle.iCommonShadowDirection+') ';
	}
	if (oStyle.bEnableGlow) {
		sHtm +='glow(Color='+oStyle.sCommonGlowColor+',';
		sHtm +='enabled='+oStyle.bCommonGlow+',';
		sHtm +='Strength='+oStyle.iCommonGlowStrength+') ';
	}
	if (oStyle.bEnableAlpha) { // added in v1.0.1
		sHtm +=' alpha(opacity='+oStyle.iCommonAlphaOpacity+',';
		sHtm +='enabled=' + oStyle.bCommonAlpha+',';
		sHtm +='style=' + oStyle.iCommonAlphaStyle + ')';
	}
sHtm +=';width:10px;height:10px" ';
sHtm +=" id='"+sId+"'>";
sHtm +='<tr valign=middle><td width="2">'
sHtm +='<img src="'+oStyle.sCommonLeftImg+'"  border=0>';
sHtm +='</td>';
sHtm +='<td NOWRAP ';
sHtm +='style="overflow-Y:hidden;';
sHtm +='background-image:url('+oStyle.sCommonMidImg+');background-repeat:repeat-x ;';
sHtm +='padding-left:'+oStyle.sTextPadding+';';
sHtm +='padding-right:'+oStyle.sTextPadding+';';
sHtm +='overflow-x : visible;'
sHtm +=';height:10px';
sHtm +=';';
sHtm +='"';
sHtm +=' align=center parentId="'+sId+'">';
	if (sIcon!="" && sIcon!=null) {
		sHtm += "<img src='"+sIcon+"' border=0 align=absmiddle> ";
	}
sHtm +='<span name=cbtLabel>';
sHtm += ""; 
sHtm +='</span>';
sHtm +='</td>';
sHtm +='<td width="2">';
sHtm +='<img src="'+oStyle.sCommonRightImg+'"  border=0>';
sHtm +='</td>';
sHtm +='</tr>';
sHtm +='</table>';
sHtm +='</a>';
return sHtm;
}

function changeAllToCoolBt(oStyle) {
var inputs = document.getElementsByTagName("INPUT");
if (inputs.length >  CBT_MAX_CONTROL_COUNT) {
alert("本页面的按钮(或类似控件)数量太多（共"+inputs.length+"个），不能应用外观。");
return false;
}

if (!oStyle || typeof(oStyle).toString().toLowerCase() !="object") 
{
oStyle = new coolBtStyleClass(); // if user has not defined a style object , use the default one;
}

var bts = new Array();
var j=0;
window.status = "Cool buttons! 正在检测页面...";
for (var i=0;i<=inputs.length-1;i++) {
window.status = "Cool buttons! 正在检测页面..." + parseInt(i/inputs.length*100)+"%";
if (!CBT_CheckClassName) {
	if (inputs[i].type == "button" || inputs[i].type=="reset" || inputs[i].type=="submit") {
		if (inputs[i].id == "" || inputs[i].id==null) {inputs[i].id = getRandomId()}
		bts[j] = inputs[i];
		j++;
	}
}
else {
	if (inputs[i].type == "button" && inputs[i].className.toLowerCase()==CBT_ClassName.toLowerCase()) {
		if (inputs[i].id == "" || inputs[i].id==null) {inputs[i].id = getRandomId()}
		bts[j] = inputs[i];
		j++;
	}
}
}
for (i=0;i<=bts.length-1;i++) {
var bt = bts[i];
var oBtStyle;
if (typeof(eval(window[bt.cbtStyle]))=="object") {
oBtStyle = eval(window[bt.cbtStyle]);
}
else {
oBtStyle = oStyle;
}

window.status = "正在更改样式..."+parseInt(i/bts.length*100)+"%";
var btId = bt.id;
	if (document.getElementById(btId+"_coolBt")==null) {
	//tx1.value = getButtonHtml(bt);
	bt.insertAdjacentHTML("beforeBegin",getButtonHtml(bt,oBtStyle));
	bt.style.display = "none";
	coolBt = document.getElementById(btId+"_coolBt");
	coolBt.onClickOrigin = bt.onclick;
	coolBt.onMouseOverOrigin = bt.onmouseover;
	coolBt.onMouseOutOrigin = bt.onmouseout;
	coolBt.onMouseDownOrigin = bt.onmousedown;
	coolBt.onMouseUpOrigin = bt.onmouseup;
	coolBt.oBtStyle = oBtStyle;
	CBT_ChangeBtValue(bt);
	if (bt.disabled) {CBT_ChangeDisableState(bt)}
	else {coolBt.disabled = false}
	bt.onPropertyChangeOrigin = bt.onpropertychange;
	bt.onpropertychange = originBtChangeHandler;
	bt.setDisable = originBtSetDisable;
	//btDoMouseOut(coolBt);
	continue;
	}

	if (document.getElementById(btId+"_coolBt")!=null && document.getElementById(btId+"_coolBt").style.display=="none") {
	oTargetBt = document.getElementById(btId+"_coolBt")
	oTargetBt.style.display="inline";
	bt.style.display = "none";
	continue;
	}

}
window.status = "完成";
return true;
}

function originBtSetDisable(bVal) {
this.disabled = bVal;
CBT_ChangeDisableState(this);
}

function originBtChangeHandler() {
var obj = event.srcElement;
//alert(obj.id+"  "+event.propertyName);
switch(event.propertyName.toString().toLowerCase()) {
case "value":CBT_ChangeBtValue(obj);break;
case "disabled":CBT_ChangeDisableState(obj);break;
}
}

function CBT_ChangeBtValue(originBt) {
var oBt = CBT_GetTargetBtn(originBt);
oBt.firstChild.firstChild.childNodes[1].getElementsByTagName("SPAN")[0].innerText = originBt.value;
}

function CBT_ChangeDisableState(originBt) {
var oBt = CBT_GetTargetBtn(originBt);
oBt.disabled = eval(originBt.disabled);
//alert(oBt.id);
//alert(oBt.disabled);
if (oBt.disabled) {
var oStyle = oBt.oBtStyle;
oBt.firstChild.firstChild.childNodes[2].firstChild.src = oStyle.sDisRightImg;
oBt.firstChild.firstChild.childNodes[0].firstChild.src = oStyle.sDisLeftImg;
oBt.firstChild.firstChild.childNodes[1].style.backgroundImage = "url("+oStyle.sDisMidImg+")" ;
oBt.style.color = oStyle.sTextDisColor;
oBt.style.fontSize = oStyle.sTextDisSize;
}
else {
btDoMouseOut(oBt);
}
}

function CBT_GetTargetBtn(originBt) {
return document.getElementById(originBt.id+"_coolBt");
}


function unChangeCoolBt() {
inputs = document.getElementsByTagName("INPUT");
if (inputs.length > CBT_MAX_CONTROL_COUNT) {
alert("本页面的按钮(或类似控件)数量太多（共"+inputs.length+"个），没有应用外观。");
return;
}
var bts = new Array();
j=0;
for (i=0;i<=inputs.length-1;i++) {
 if (inputs[i].type == "button" || inputs[i].type=="reset" || inputs[i].type=="submit") {
bts[j] = inputs[i];
j++;
}
}
for (i=0;i<=bts.length-1;i++) {
bt = bts[i];
window.status = "正在更改样式..."+parseInt(i/bts.length*100)+"%";
btId = bt.id;
	if (document.getElementById(btId+"_coolBt")==null) {
	continue;
	}

	if (eval(btId+"_coolBt")!=null) {
	eval(btId+"_coolBt").style.display="none";
	bt.style.display = "";
	continue;
	}

}
window.status = "完成"
}

function getRandomId() {
var srcStr = "abcdefghijklmnopqrstuvwxyz0123456789";
var idLength = 25;
var idHeader = "CBT_";
var index = 0;
var returnStr = idHeader;
for (i=0;i<=idLength;i++) {
index = Math.floor(Math.random()*srcStr.length);
returnStr += srcStr.charAt(index);
}
return returnStr;
}

if (CBT_INVOKE_ONLOAD && window) {
window.attachEvent("onload",function(){changeAllToCoolBt()
} );

}