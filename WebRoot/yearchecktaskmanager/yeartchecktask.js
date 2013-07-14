var factor = 0.6;
var g_dialogWidth = window.screen.availWidth * factor;
var g_dialogHeight = window.screen.availHeight * factor;

function lookUpCheckTaskValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    //var url = "/servlet/com.sino.ams.newasset.bean.AssetsLookUpServlet?lookUpName=" + lookUpName;
    var url = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.CheckTaskLookUpServlet?lookUpName=" + lookUpName;
	if(userPara != "undefined" && userPara != null){
        url += "&" + userPara;
    }
    dialogWidth = g_dialogWidth;
    dialogHeight = g_dialogHeight;
    var popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:yes;help:no;resizable:yes";
//			window.open(url);
    return window.showModalDialog(url, null, popscript);
}