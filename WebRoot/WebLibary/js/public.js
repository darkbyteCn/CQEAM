// JavaScript Document
function switchImage(imgName, imgSrc) 
{
  if (document.images)
  {
    if (imgSrc != "none")
    {
      document.images[imgName].src = imgSrc;
    }
  }
}

var popUpWin=0;
function popUpWindow(URLStr, left, top, width, height)
{
  if(popUpWin)
  {
    if(!popUpWin.closed) popUpWin.close();
  }
  popUpWin = open(URLStr, 'popUpWin', 'toolbar=no,location=no,directories=no,status=no,menub ar=no,scrollbar=no,resizable=no,copyhistory=yes,width='+width+',height='+height+',left='+left+', top='+top+',screenX='+left+',screenY='+top+'');
}


var oWindow;
var iDialogHeight = 480;
var iDialogWidth = 770;
function showIt(sPage){
	showDialog(sPage, iDialogWidth);
}
function showItScrolling(sPage){
	iScrollDialogWidth = 571 + parseInt(document.body.offsetWidth - document.body.clientWidth);
	showDialog(sPage, iScrollDialogWidth);
}
function showDialog(sPage, iWidth){
	var sTitle;
	var sDialogArgs = "help:no;status:no;dialogHeight:" + iDialogHeight + "px;dialogWidth:" + iWidth + "px";
	try {
		sTitle = document.all[sPage.substr(0, sPage.length - 4)].innerText;
	}catch(e){
		sTitle = "&nbsp;"
	}
	if (oWindow != null) oWindow.close();
	oWindow = showModelessDialog(sPage, sTitle, sDialogArgs);
}

	
function popUpWindow(URLStr)
{
	var popUpWin=0;
	var left=0;
	var top=0;
	var width=760;
	var height=550
  if(popUpWin)
  {
    if(!popUpWin.closed) popUpWin.close();
  }
  popUpWin = open(URLStr, 'popUpWin', 'toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes,copyhistory=yes,width='+width+',height='+height+',left='+left+', top='+top+',screenX='+left+',screenY='+top+'');
}

var popUpWin=0;

function popUpWindow(URLStr, left, top, width, height)

{

  if(popUpWin)

  {

    if(!popUpWin.closed) popUpWin.close();

  }

  popUpWin = open(URLStr, 'popUpWin', 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width='+width+',height='+height+',left='+left+', top='+top+',screenX='+left+',screenY='+top+'');

}


