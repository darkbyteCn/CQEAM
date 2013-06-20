
/*
 * 功能：在页面上输入toolbar效果
 * time:2008.8.18
 * edition:1.0
 */

//往页面上输出工具条
function SinoPrintToolBar()
{

    //设置图片路径
    this.imagePath = "";

    //设置标题内容
    this.titleStr = "";

    //设置toolbar数组
    this.SinoActions;

    //没置treeView标题数组
	this.treeViewTitle;

	//设置treeView标题数组元素的宽度
	this.treeViewWidth;

	//设置treeView align=left
	this.align = "left";

	this.constTitleBarBGColor="#0073BF";
	this.constTitleBarHeight=26;
	this.constTitleBarFont="style=\"font-size: 10pt\" color=#014f8a";
	this.constTitleBarBg = "/cms_images/table_bg1.jpg";
	this.constTitleTdClass = "titleTdClass"; //add
    // 返回标题HTML代码
    this.PrintToolbarTitle = function(){
	   var innerHtml = "";
	   innerHtml=innerHtml+"<table height=22 width=100% bgcolor='#358BCE'>"; //原色 #0073bf
	   innerHtml=innerHtml+"<tr>";
	   innerHtml=innerHtml+"<td nowrap align=left valign=bottom>&nbsp;<font color='white'><b>&gt;&gt;&nbsp;"+this.titleStr+"</b></font></td>";
	   innerHtml=innerHtml+"<td nowrap align=right valign=bottom></td>";
	   innerHtml=innerHtml+"<td nowrap align=left valign=middle width=20><img src='"+this.imagePath+"bull.gif' width=16 height=16></td>";
	   innerHtml=innerHtml+"</tr>";
	   innerHtml=innerHtml+"</table>";
	   return innerHtml;
    }

    //返回上隔线HTML代码
    this.PrintToolBarUpLine = function(){

        var innerHtml = "";
        innerHtml+="<TABLE height=4 cellSpacing=0 borderColorDark=#808080 cellPadding=0 width='100%' bgColor=#efefef borderColorLight=#808080 border=1>";
        innerHtml+="<TBODY><TR>";
		innerHtml+="<TD noWrap borderColorLight=#808080 bgColor=#efefef borderColorDark=#808080></TD>";
		innerHtml+="</TR></TBODY></TABLE>";
	    return innerHtml;
    }

    //返回toolbar HTML代码
     this.PrintToolBarMenus = function(){

	     if(this.SinoActions != null && this.SinoActions.length > 0){
		   var innerHtml="";
		   innerHtml+="<table cellSpacing=0 cellPadding=0 width=\"100%\" bgColor=#efefef height=12><tr> <td borderColorLight=#efefef borderColorDark=#efefef>";
	       innerHtml+="<table height=12 cellSpacing=0 borderColorDark=#808080 cellPadding=0 bgColor=#efefef borderColorLight=#ffffff border=1>";
	       innerHtml+="<tr borderColorLight=#efefef borderColorDark=#efefef><td>";
	       innerHtml+="<table><tr borderColorLight=#efefef borderColorDark=#efefef><td borderColorLight=#efefef borderColorDark=#efefef>";
	       innerHtml+="<img src='"+this.imagePath+"toolbar1.gif'></td></tr></table></td>";

		   for(i=0;i<this.SinoActions.length;i++)
		   {

			 	innerHtml+="<td>";

			 	innerHtml+="<table bgColor=#FFFFFF ";
			 	innerHtml+="  onmouseup=\"javascript:this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'\"  ";
			 	innerHtml+="  onmousedown=\"javascript:this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'\"  ";
			 	innerHtml+="  onmouseover=\"javascript:this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';\"  ";
			 	innerHtml+="  onmouseout=\"javascript:this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';\"  ";
			 	innerHtml+="  height=22 cellSpacing=0 borderColorDark='#efefef' borderColorLight=#efefef border=1 ";
			 	innerHtml+=" style=\"CURSOR: hand\" cellSpacing=0 cellPadding=0><tbody><tr>" ;

	            innerHtml+="<td onclick='"+this.SinoActions[i][2]+"()' ";
	          	innerHtml+=" noWrap id='"+this.SinoActions[i][3]+"' vAlign='center' noWrap";
	            innerHtml+=" borderColorLight='#efefef' align='middle' bgColor='#efefef' borderColorDark='#efefef' >";
	          	innerHtml+="<IMG hspace='1' align='absMiddle' ";
	          	innerHtml+="src='"+this.imagePath + this.SinoActions[i][1]+"' ";
	            innerHtml+=" width='16' height='16'>&nbsp;"+this.SinoActions[i][0];
	            innerHtml+="&nbsp;</td>"

	            innerHtml+="</tr><tbody></table>";

	            innerHtml+="</td>";

	            innerHtml+="<td>";
	            innerHtml+="<table><tr><td><img src='"+this.imagePath+"toolbar2.gif'></td></tr></table>";
	            innerHtml+="</td>";
	      }
	     	 innerHtml+="</tr></table>";
	      	innerHtml+="</td></tr></table>";
		  }
		  return innerHtml;
    }

	 // 返回下隔线HTML代码
    this.PrintToolBarDownLine = function(){

        var innerHtml = "";
        innerHtml+="<TABLE height=5 cellSpacing=0 borderColorDark=#808080 cellPadding=0 width='100%' bgColor=#efefef borderColorLight=white border=1>";
        innerHtml+="<TBODY><TR>";
		innerHtml+="<TD noWrap   borderColorDark=#808080></TD>";
		innerHtml+="</TR></TBODY></TABLE>";
	    return innerHtml;
    }

    //返回TreeView HTML代码
    this.PrintTreeView = function(){
    	 var innerHtml="";
        if(this.treeViewTitle != null && this.treeViewTitle.length>0){
	        innerHtml += "<table style=\"CURSOR: pointer\" height=20 cellSpacing=0 borderColorDark=#efefef ";
	        innerHtml += " cellPadding=0 width=\"100%\" bgColor=white borderColorLight=#ffffff border=0 >";
	        innerHtml += "<tr>";
	        /*
	        innerHtml += "<th noWrap width='"+this.treeViewWidth[0]+"'>";
	        innerHtml += "<table height=20 cellSpacing=0 borderColorDark=#808080 cellPadding=0 width=\"100%\" bgColor=#efefef borderColorLight=#ffffff border=1>";
	        innerHtml += "<tr><td noWrap borderColorLight=#efefef align=middle bgColor=#efefef borderColorDark=#efefef>"+this.treeViewTitle[0]+"</td></tr></table>";
	        innerHtml += "</th>";

	        innerHtml += "<th noWrap width='2%'>";
	        innerHtml += "<table height=20 cellSpacing=0 borderColorDark=#808080 cellPadding=0 width=\"100%\" bgColor=#efefef borderColorLight=#ffffff border=1>";
	        innerHtml += "<tr><td  noWrap borderColorLight=#efefef align=middle bgColor=#efefef borderColorDark=#efefef>";
	        innerHtml += "<img height=12 width=12 src ='"+this.imagePath+"t_check.gif'></td></tr></table>";
	        innerHtml += "</th>";
	          */

	        for(var i=0;i<this.treeViewTitle.length;i++){
	            innerHtml += "<th noWrap width='"+this.treeViewWidth[i]+"'>";
	            innerHtml += "<table height=20 cellSpacing=0 borderColorDark=#808080 cellPadding=0 width=\"100%\" bgColor=#efefef borderColorLight=#ffffff border=1>";
	            innerHtml += "<tr><td noWrap borderColorLight=#efefef align="+this.align+" bgColor=#efefef borderColorDark=#efefef>"+this.treeViewTitle[i]+"</td></tr></table>";
	            innerHtml += "</th>";
	        }
	        innerHtml += "</tr>";
	        innerHtml += "</table>";
        }
         return innerHtml;
    }

    //打印输出
    this.print = function(){
      	var str = this.PrintToolbarTitle();
            str += this.PrintToolBarUpLine();
            str += this.PrintToolBarMenus();
            str += this.PrintToolBarDownLine();
            str += this.PrintTreeView();
            document.write(str);
    }

    this.printNoTitle = function(){
        var str = this.PrintToolBarUpLine();
            str += this.PrintToolBarMenus();
            str += this.PrintToolBarDownLine();
            str += this.PrintTreeView();
            document.write(str);
    }
}

