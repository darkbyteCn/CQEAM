var factor = 0.8;
var g_dialogWidth = window.screen.availWidth * factor;
var g_dialogHeight = window.screen.availHeight * factor;

function getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=" + lookUpName;
    if(userPara != "undefined"){
        url += "&" + userPara;
    }
    dialogWidth = g_dialogWidth;
    dialogHeight = g_dialogHeight;
    var popscript;
    popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
    return window.showModalDialog(url, null, popscript);
}

function getSpareLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.nm.spare2.bean.SpareLookUpServlet?lookUpName=" + lookUpName;
    if(userPara != "undefined"){
        url += "&" + userPara;
    }
    dialogWidth = g_dialogWidth;
    dialogHeight = g_dialogHeight;
    var popscript;
    popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
    return window.showModalDialog(url, null, popscript);
}


/**
 * 功能：工单部分的lookUp应用
 */
function LookUpOrderValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.ams.workorder.util.OrderLookUpServlet?lookUpName=" + lookUpName;
    if(userPara != "undefined"){
        url += "&" + userPara;
    }
    dialogWidth = g_dialogWidth;
    dialogHeight = g_dialogHeight;
    var popscript;
    popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
    return window.showModalDialog(url, null, popscript);
}