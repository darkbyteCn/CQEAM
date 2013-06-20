function AjaxProcessor(actionURL, responseProcessor, isXMLResponse) {
    this.actionURL = actionURL;
    if(this.actionURL.indexOf("?") > -1){
        this.actionURL += "&"
    } else {
        this.actionURL += "?"
    }
    if(isXMLResponse){
        this.actionURL += "resType=AJAX_XML";
    } else{
        this.actionURL += "resType=AJAX_TEXT";
    }
    this.setSendData = function(sendData) {
        this.sendData = sendData;
    };
    this.addParameter = function(parameterName, parameterValue) {
        if(!this.sendData){
            this.sendData = "";
        }
        if(this.sendData != ""){
            this.sendData += "&";
        }
        this.sendData += parameterName + "=" + parameterValue;
    };
    this.setRequestMethod = function(requestMethod) {
        this.requestMethod = requestMethod;
    };
    this.setSynchronize = function(synchronize) {
        this.synchronize = synchronize;
    };
    this.getXMLRequest = function() {
        var objXmlHttp = null;
        if (navigator.userAgent.indexOf("Opera") >= 0) {
            alert("对不起！你的浏览器不支持该操作！");
            return null;
        }
        if (navigator.userAgent.indexOf("MSIE") >= 0) {
            var strName = "Msxml2.XMLHTTP";
            if (navigator.appVersion.indexOf("MSIE 5.5") >= 0) {
                strName = "Microsoft.XMLHTTP";
            }
            if (window.ActiveXObject) {
                objXmlHttp = new ActiveXObject(strName);
            } else if (window.XMLHttpRequest) {
                objXmlHttp = new XMLHttpRequest();
            }
        } else if (navigator.userAgent.indexOf("Mozilla") >= 0) {
            objXmlHttp = new XMLHttpRequest();
            return objXmlHttp;
        }
        return objXmlHttp;
    };
    var objXmlHttp = null;
    this.performTask = function() {
        if (this.actionURL == undefined || this.actionURL == null || this.actionURL == "") {
            alert("没有指定处理Ajax请求的URL！");
            return;
        }
        if (!this.sendData) {
            this.sendData = null;
        }
        if (this.synchronize == undefined || this.synchronize == null) {
            this.synchronize = false;
        }
        var synchronize = String(this.synchronize).toLowerCase();
        this.synchronize = (synchronize == "true");
        if (this.requestMethod == undefined || this.requestMethod == null || this.requestMethod == "") {
            this.requestMethod = "POST";
        } else {
            this.requestMethod = this.requestMethod.toUpperCase();
        }
        objXmlHttp = this.getXMLRequest();
        if (!objXmlHttp) {
            alert("创建XMLHttpRequest对象失败，请重试！");
            return;
        }
        objXmlHttp.onreadystatechange = this.processServerResponse;
        objXmlHttp.open(this.requestMethod, this.actionURL, this.synchronize);
        objXmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        objXmlHttp.send(this.sendData);
    };
    this.processServerResponse = function() {
        if (objXmlHttp.readyState == 4) {
            if (objXmlHttp.status == 200) {
                with (window) {
                    if (isXMLResponse) {
                        responseProcessor.apply(this, [objXmlHttp.responseXML]);
                    } else {
                        responseProcessor.apply(this, [objXmlHttp.responseText]);
                    }
                }
            } else {
                alert(objXmlHttp.responseText);
            }
        }
    };
}