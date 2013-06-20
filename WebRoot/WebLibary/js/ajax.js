function GetXmlHttpObject(handler) {
    var objXmlHttp = null
    if (navigator.userAgent.indexOf("Opera") >= 0) {
        alert("对不起！你的浏览器不支持该操作！")
        return;
    }
    if (navigator.userAgent.indexOf("MSIE") >= 0) {
        var strName = "Msxml2.XMLHTTP"
        if (navigator.appVersion.indexOf("MSIE 5.5") >= 0) {
            strName = "Microsoft.XMLHTTP"
        }
        if (window.ActiveXObject) {
            objXmlHttp = new ActiveXObject(strName);
            objXmlHttp.onreadystatechange = handler;
            return objXmlHttp;
        } else if (window.XMLHttpRequest) {
            objXmlHttp = new XMLHttpRequest();
            objXmlHttp.onreadystatechange = handler
            return objXmlHttp;
        }
    }
    if (navigator.userAgent.indexOf("Mozilla") >= 0) {
        objXmlHttp = new XMLHttpRequest()
        objXmlHttp.onload = handler
        objXmlHttp.onerror = handler
        return objXmlHttp
    }
}

function createXMLHttpRequest() {
    var xmlhttp;
    try {
        xmlhttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlhttp = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
    return xmlhttp;
}
function requestAjaxWait(reqType, handler, obj, appCondi) {
    if (reqType == null || reqType == '') {
        alert("请确定请求类型，否则无法访问服务器!");
        return;
    }
    var url = "/servlet/com.sino.ams.newasset.servlet.AmsReqServlet?reqType=" + reqType ;
    if (appCondi && appCondi != null) {
        url += appCondi;
    }
    xmlHttp = GetXmlHttpObject(handler);
    xmlHttp.open('POST', url, false);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var param = null;
    if (obj && obj != null) {
        param = escape(obj.toJSONString());
    }
    xmlHttp.send(param);
}
function requestAjax(reqType, handler, obj, appCondi) {
    if (reqType == null || reqType == '') {
        alert("请确定请求类型，否则无法访问服务器!");
        return;
    }
    var url = "/servlet/com.sino.ams.newasset.servlet.AmsReqServlet?reqType=" + reqType ;
    if (appCondi && appCondi != null) {
        url += appCondi;
    }
   xmlHttp = GetXmlHttpObject(handler);
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var param = null;
    if (obj && obj != null) {
        param = escape(obj.toJSONString());
    }
    xmlHttp.send(param);
}
function getRet(xmlHttp) {
    var resText = xmlHttp.responseText;
    var arr = resText.split("$##$");
    if (arr.length == 2 && arr[0] == 'ERROR') {
        alert("请求失败！详细原因:" + arr[1]);
        return 'ERROR';
    } else {
        return resText;
    }
}