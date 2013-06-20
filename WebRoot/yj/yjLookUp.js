//获取应急战备资源
function getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.ams.yj.bean.YjLookUpServlet?lookUpName=" + lookUpName;
    if(userPara != "undefined"){
        url += "&" + userPara;
    }
    var popscript;
    popscript = "dialogWidth:"
            + dialogWidth
            + ";dialogHeight:"
            + dialogHeight
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}
