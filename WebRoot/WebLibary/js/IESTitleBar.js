var constTitleBarBGColor="#0073BF";          //河南请用 #0073BF ,西藏请用 #33336A
var constTitleBarHeight=20;
var constTitleBarFont="style=\"font-size: 10pt\" color=#FFFFFF";
/**
function printTitleBar(desc)
{
	 /**
	document.write("<table height="+constTitleBarHeight+" width=100% bgcolor="+constTitleBarBGColor+" border=0 cellpadding=0 cellspacing=0>\n");
	document.write("<tr>\n");
	document.write("<td nowrap align=left valign=bottom ><font  "+constTitleBarFont+"><b>>>"+desc+"</b></font> </td>\n");
	document.write("<td nowrap align=right valign=bottom ></td>\n");
	document.write("<td nowrap align=left valign=middle width=20><img src=\"/images/bull.gif\" width=16 height=16></td>\n");
	document.write("</tr></table>\n"); 
	**/
/**	
	document.write("<table class=\"titleBar\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
	document.write("<tr>");
    document.write("<td background=\"/WebLibary/image/right-07.jpg\">&nbsp;</td>");
    document.write("</tr>");
	 
  	document.write("<tr>");
	document.write("<td  align=\"right\" width=\"2%\"><img src=\"/WebLibary/image/top-01.jpg\" width=\"17\" height=\"17\" /></td>");
    document.write("<td width=\"98%\" class=\"font-lan\"><strong>" +desc+ "</strong></td> ");
  	document.write("</tr>");
	document.write("</table>");
	
	
}
**/
function printBtnBarsX_obj( obj ){
	if( null == obj ){
		obj = new Object();
	}
	printBtnBarsX2( obj.titles , obj.acts, obj.aTbWidth, null , null , obj.idnamesArry , obj.distance);
}
function printBtnBarsX2(titles, acts, aTbWidth, btnWidths, pTdWidths, idnamesArry , distance){
	var ptdWidth ;
	var len = titles.length;
	if( null == aTbWidth ){
		aTbWidth = "100%";
	}
	
	var tb_margin_left = "2%";
	
	if( null == distance ){
		distance = "&nbsp;";
	}
	/**
	if( null == btnWidths ){
		btnWidths = new Array( len );
	}
	if( null == tbWidths ){
		tbWidths = new Array( len );
	}
	if( null == pTdWidths ){
		pTdWidths = new Array( len );
	}
	**/
	document.write("<table class=\"btnBarXTb\" style=\"margin-left:" + tb_margin_left + ";\"width=\"" +aTbWidth+ "\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> ");
    document.write("  <tr> ");
	document.write("    <td width=\"95%\" align=\"left\"> ");
	var idName = null ;  
	for (var i = 0; i < len; i++) { 
	if (null == idnamesArry) {
			idName = "btn" + i;
		}else{ 
			idName = idnamesArry[i];
		} 
		printBtnBar2(titles[i], acts[i], idName , distance );
	}
	document.write("    </td>  "); 
	document.write("  </tr> ");
    document.write("</table> "); 
}


/**
 * SJ ADD
 * 按钮
 * @param {Object} title
 * @param {Object} btnWidth
 * @param {Object} tbWidth
 */
function printBtnBar2( title , act , idname ,distance ){ 
	var className = "btn_short";
	if( title.length >= 4 ){
		className = "btn_long";
	}
	if (null == idname) {
		idname = "noNameBtn";
	} 
	document.write(" <input name=\"button\" type=\"button\" id=\""+idname+"\" class=\""+className+"\"  align=\"left\" value=\""+title+"\" title=\""+title+"\" onclick=\"" + act + "\"/>");
	document.write( distance );
}

/**
 * SJ ADD
 * 按钮群( 横向 )
 * @param {Object} titles  按钮名
 * @param {Object} btnWidths 按钮宽度
 * @param {Object} tbWidth 大table宽度
 * @param {Object} tbWidths 小table宽度
 * @param {Object} pTdWidths table外td宽度
 */
function printBtnBarsX( titles , acts , aTbWidth , btnWidths , pTdWidths, idnamesArry )
{ 
  
	var ptdWidth ;
	var len = titles.length;
	if( null == aTbWidth ){
		aTbWidth = "100%";
	}
	
	var tb_margin_left = "2%";
	/**
	if( null == btnWidths ){
		btnWidths = new Array( len );
	}
	if( null == tbWidths ){
		tbWidths = new Array( len );
	}
	if( null == pTdWidths ){
		pTdWidths = new Array( len );
	}
	**/
	document.write("<table class=\"btnBarXTb\" style=\"margin-left:" + tb_margin_left + ";\"width=\"" +aTbWidth+ "\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> ");
    document.write("  <tr> ");
	
	var btnWidth = null ;  
	var pTdWidth = null ; 
	var idName = null ;  
	for (var i = 0; i < len; i++) {
		if (null != btnWidths) {
			btnWidth = btnWidths[i];
		}
		
		if (null == pTdWidths) {
			pTdWidth = Number(btnWidth) + 25;
		}
		else {
			pTdWidth = pTdWidths[i];
		} 
		if (null == idnamesArry) {
			idName = "btn" + i;
		}else{ 
			idName = idnamesArry[i];
		} 
		 document.write("    <td width=\"" + pTdWidth + "\"> ");
		 printBtnBar(titles[i], acts[i], btnWidth, idName );
		 document.write("    </td>  "); 
	}
	document.write("  </tr> ");
    document.write("</table> "); 
}

/**
 * SJ ADD
 * 按钮
 * @param {Object} title
 * @param {Object} btnWidth
 * @param {Object} tbWidth
 */
function printBtnBar( title , act , btnWidth ,idname  ){
	var tbWidth = 75 ;
	if( null == btnWidth ){
		btnWidth = "52";  
	} 
	if (null == idname) {
		idname = "noNameBtn";
	}
	tbWidth = Number(btnWidth) + 23;
	 
	document.write("	<table style=\"cursor:'hand';\" id=\"" + idname + "\" onclick=" + act + " width=\"" + tbWidth + "\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> ");
	document.write("      <tr> ");
	document.write("        <td width=\"12\" align=\"right\"><img src=\"/WebLibary/image/userinfo-button.jpg\" width=\"11\" height=\"25\" /></td> ");
	document.write("        <td width=\"" + btnWidth + "\" align=\"center\" background=\"/WebLibary/image/userinfo-button2.jpg\" class=\"font-lan\">" + title + "</td> ");
	document.write("        <td width=\"11\" align=\"left\"><img src=\"/WebLibary/image/userinfo-button1.jpg\" width=\"11\" height=\"25\" /></td> ");
	document.write("      </tr> ");
	document.write("    </table> ");
		 

}

/**
 * SJ ADD
 * 双grid ，但是不支持左右滚动 
 * @param {Object} headTbId
 * @param {Object} listTbId
 * @param {Object} topHeight
 * @param {Object} gridWidth
 */
function printUnitTable( obj ){ 
/**
	if( null == obj ){
		obj = new Object();
	}
	
	var headTbId ;
	var listTbId ;
	var hDivWidth = 0;
	var lDivWidth = 0;
	var hTopHeight = 0;
	var lTopHeight = 0;
	var topBetweenHeight = 0;
	var hTbWidth = 0;
	var lTbWidth = 0;
	
	 if( null == obj.topBetweenHeight ){
		topBetweenHeight = 21 ;
	}else{
		topBetweenHeight = obj.topBetweenHeight  ;
	}
	if( null == obj.tbWidth) {
		hTbWidth = 2000 ;
		lTbWidth = 2000 ;
	}else{ 
		hTbWidth = Number( obj.tbWidth )  ;
		lTbWidth = hTbWidth ;
	}
	
	if( null == obj.headTbId ){
		headTbId = "headTb";
	}else{
		headTbId = obj.headTbId;
	}
	if( null == obj.listTbId ){
		listTbId = "listTb";
	}else{
		listTbId = obj.listTbId;
	} 
	if( null == obj.topHeight) {
		hTopHeight = 125 ;
		lTopHeight = hTopHeight + topBetweenHeight ;
	}else{
		hTopHeight = Number( obj.topHeight )  ;
		lTopHeight = hTopHeight + topBetweenHeight ;
	}
	
	if( null == obj.divWidth) {
		hDivWidth = 805 ;
		lDivWidth = 822 ;
	}else{ 
		hDivWidth = Number( obj.divWidth )  ;
		lDivWidth = hDivWidth + 17 ;
	}
	
	
	var headHtml = document.getElementById( headTbId ).innerHTML;
	var listHtml = document.getElementById( listTbId ).innerHTML;
	var showHtml = "";
	showHtml += "<div style=\"width:"+hTbWidth+";overflow:hidden;position:absolute;left:24px;top:"+hTopHeight+"px;\"> ";
	showHtml += "  <table id=\"" + headTbId + "\" style=\"margin-left: 0px;\" class=\"printUnitTableHeader\"   border=\"0\" cellpadding=\"0\" width=\"100%\" cellspacing=\"1\" > ";
	showHtml +=  headHtml ;
	showHtml += " </table></div>";
	showHtml += "<div style=\"overflow:auto;height:60%;width:"+ lTbWidth +";position:absolute;top:"+lTopHeight+"px;left:24px;margin-left:0\"> ";
	showHtml += " <table id=\"listtb\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\"  class=\"printUnitTableList\" width=\"100%\"> ";
	showHtml +=  listHtml ;
	showHtml += " </table></div>";
	//alert( showHtml );
	document.write( showHtml ); 
	**/
	if( null == obj ){
		obj = new Object();
	}
    if (obj.isScroll == null) {
        obj.isScroll = false;
    }
	printUnitTableForScroll( obj );
}


/**
 * SJ ADD
 * 双grid ，但是不支持左右滚动 
 * @param {Object} headTbId
 * @param {Object} listTbId
 * @param {Object} topHeight
 * @param {Object} gridWidth
 */
function printUnitTableForScroll( obj ){ 
/**
 * obj.mLeft         左边距离
 * obj.headTbId      头 table ID
 * obj.listTbId 	 列表table ID
 * obj.divWidth      头div宽度
 * obj.lTbHeight	 列表table高度
 * obj.topHeight	 头的绝对高度
 * obj.topBetweenHeight 头和列表高度间隔
 * obj.tbWidth	     table宽度
 *  
 */ 
	var DEFAULT_TOP_BETWEEN_HEIGHT = 22;
	var DEFAULT_H_TB_ID = "headTb";
	var DEFAULT_L_TB_ID = "listTb";
	var DEFAULT_M_LEFT_PERCENT = "2%";
	var DEFAULT_M_LEFT = "12px";
	var DEFAULT_L_TB_HEIGHT = "65%";
	var DEFAULT_L_TB_WIDTH = 2000;
	var DEFAULT_L_TB_WIDTH_PERCENT = "100%";
	var DEFAULT_H_TOP_HEIGHT = 125; 
	var DEFAULT_H_DIV_WIDTH_PERCENT = "96%";
	var DEFAULT_H_DIV_WIDTH = "96%";
	var DEFAULT_H_L_BETWEEN_WIDTH = 17;
	
	var SCROLL_STYLE = "crystalScroll";
	if( null == obj ){
		obj = new Object();
	}
	if (obj.isScroll == null) {
        obj.isScroll = true;
    }

	var mLeft ;  
    var headTbId ;  //头 table ID
	var listTbId ;  //列表table ID
	var hDivWidth = 0; //头div宽度
	var lDivWidth = 0;
	var hTopHeight = 0;
	var lTopHeight = 0;
	var topBetweenHeight = 0;
	var hTbWidth = 0;
	var lTbWidth = 0;
	var lTbHeight = 0;
	
	if( null == obj.mLeft ){
		mLeft = DEFAULT_M_LEFT_PERCENT ;
	}else{
		mLeft = obj.mLeft;
	}
	
	var scrollType = "overflow-y:scroll;";
	if( obj.isScroll ){
		scrollType = "overflow:scroll;";
	}
	
	if( null == obj.lTbHeight ){
		lTbHeight = DEFAULT_L_TB_HEIGHT ;
	}else{
		lTbHeight = obj.lTbHeight;
	}
	if( null == obj.topBetweenHeight ){
		topBetweenHeight = DEFAULT_TOP_BETWEEN_HEIGHT ;
	}else{
		topBetweenHeight = obj.topBetweenHeight  ;
	}
	if( null == obj.tbWidth) {
		if( obj.isScroll ){
			hTbWidth = DEFAULT_L_TB_WIDTH ;
			lTbWidth = DEFAULT_L_TB_WIDTH ;
		}else{
			hTbWidth = DEFAULT_L_TB_WIDTH_PERCENT ;
			lTbWidth = DEFAULT_L_TB_WIDTH_PERCENT ;
		}
		
	}else{ 
		hTbWidth = Number( obj.tbWidth )  ;
		lTbWidth = hTbWidth ;
	}
	
	if( null == obj.headTbId ){
		headTbId = DEFAULT_H_TB_ID;
	}else{
		headTbId = obj.headTbId;
	}
	if( null == obj.listTbId ){
		listTbId = DEFAULT_L_TB_ID;
	}else{
		listTbId = obj.listTbId;
	} 
	if( null == obj.topHeight) {
		hTopHeight = DEFAULT_H_TOP_HEIGHT ;
		lTopHeight = hTopHeight + topBetweenHeight ;
	}else{
		hTopHeight = Number( obj.topHeight )  ;
		lTopHeight = hTopHeight + topBetweenHeight ;
	}
	
	var hDivWidth_per ; 
	if( null == obj.divWidth) {
		hDivWidth_per = DEFAULT_H_DIV_WIDTH_PERCENT ; 
	}else{ 
		hDivWidth_per = obj.divWidth;
		/**
		try {
			//alert( t );
			if (t.indexOf("%") != -1) {
				//LIBO Modify
				var clientWidth = Number(document.body.clientWidth);
				
				var numwidth = (Number(obj.divWidth.substring(0, obj.divWidth.length - 1)) / 100) * (clientWidth - 65);
				hDivWidth = numwidth * 100 / clientWidth + "%";
				lDivWidth = (numwidth + 17) * 100 / clientWidth + "%";
			}
			else {
				hDivWidth = Number(obj.divWidth);
				lDivWidth = hDivWidth + 17;
			}
		}catch( ex ){
			alert( ex.message );
			hDivWidth = Number(obj.divWidth);
			lDivWidth = hDivWidth + 17;
		} ***/
	} 
	var t = hDivWidth_per + "" ;
	if( t.indexOf("%") != -1 ){
		var clientWidth = Number(document.body.clientWidth);
		var numwidth = (Number( hDivWidth_per.substring(0, hDivWidth_per.length - 1)) / 100) * (clientWidth);
		if( clientWidth == 0 ){
            hDivWidth = "94%";
            lDivWidth = DEFAULT_H_DIV_WIDTH ;
        }else{
            hDivWidth = (numwidth - DEFAULT_H_L_BETWEEN_WIDTH ) * 100 / clientWidth + "%";
            lDivWidth = numwidth * 100 / clientWidth + "%";
        } 
        mLeft = ( 100 - hDivWidth_per.substring(0, hDivWidth_per.length - 1) ) /2  + "%";
		/**
		var clientWidth = Number(document.body.clientWidth);
				
		var numwidth = (Number( hDivWidth_per.substring(0, hDivWidth_per.length - 1)) / 100) * (clientWidth - 65);
		hDivWidth = numwidth * 100 / clientWidth + "%";
		lDivWidth = (numwidth + 17) * 100 / clientWidth + "%";
		**/
	}else{
		hDivWidth = Number(obj.divWidth);
		lDivWidth = hDivWidth + DEFAULT_H_L_BETWEEN_WIDTH ;
		mLeft = DEFAULT_M_LEFT ;
	}
	//alert( mLeft );
 	//alert( hDivWidth );
 	//alert( lDivWidth );
	 
	var listObj = document.getElementById( listTbId );
	var headObj = document.getElementById( headTbId );
	var headHtml = headObj.innerHTML;
	var listHtml = listObj.innerHTML;
	listObj.id = "abandonListId";
	listObj.innerText = null;
	headObj.id = "abandonHeadId";
	headObj.innerText = null;
	var showHtml = "";
	showHtml += "<div id=\"headDiv\" style=\"margin-left:"+ mLeft +";margin-right:"+ mLeft +";width:"+hDivWidth+";overflow:hidden;position:absolute;top:"+hTopHeight+"px;\"> ";
	showHtml += "  <table id=\"" + headTbId + "\" style=\"margin-left: 0px;\" class=\"printUnitTableHeader\"   border=\"0\" cellpadding=\"0\" width=\""+hTbWidth+"\" cellspacing=\"1\" > ";
	showHtml +=  headHtml ;
	showHtml += " </table></div>";
	showHtml += "<div class=\""+SCROLL_STYLE+"\" style=\"margin-left:"+ mLeft +";margin-right:"+ mLeft +";"+scrollType+";height:"+lTbHeight+";width:"+ lDivWidth +";position:absolute;top:"+lTopHeight+";\" ";
	//alert( obj.isScroll );
	if( obj.isScroll ){
		showHtml += " onscroll=\"document.getElementById('headDiv').scrollLeft = this.scrollLeft;\"> ";
	}else{
		showHtml += " > ";
	} 
	showHtml += " <table id=\"" + listTbId + "\" border=\"0\" style=\"margin-left: 0px;\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\"  class=\"printUnitTableList\" width=\""+lTbWidth+"\" > ";
	showHtml +=  listHtml ;
	showHtml += " </table></div>";
	//alert( showHtml );
	document.write( showHtml ); 
}
