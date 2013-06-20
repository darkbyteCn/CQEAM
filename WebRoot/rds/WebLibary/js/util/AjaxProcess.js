function AjaxProcessor(actionURL, responseProcessor, isXMLResponse) {
    this.actionURL = actionURL;
    if (this.actionURL.indexOf("?") > -1) {
        this.actionURL += "&"
    } else {
        this.actionURL += "?"
    }
    if (isXMLResponse) {
        this.actionURL += "resType=AJAX_XML";
    } else {
        this.actionURL += "resType=AJAX_TEXT";
    }
    this.setSendData = function(sendData) {
        this.sendData = sendData;
    };
    this.setDataFrm = function(dataFrm) {
        if (dataFrm) {
            for (var i = 0; i < dataFrm.length; i++) {
                dataFrm.elements[i].disabled = false;
            }
            this.dataFrm = dataFrm;
        }
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
        var sendData = getFormSubmitData(this.dataFrm);
        if (sendData != "") {
            if (!this.sendData) {
                this.sendData = sendData;
            } else {
                if (this.sendData != "") {
                    this.sendData += "&";
                }
                this.sendData += sendData;
            }
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
        objXmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
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

function RDSAjaxProcessor(responseProcessor, isXMLResponse) {
    var actionURL = "/rds/ajaxProcessAction.do";
    var ajaxProcessor = new AjaxProcessor(actionURL, responseProcessor, isXMLResponse);
    var localSendData = "";

    this.setSendData = function(sendData) {
        if(sendData){
            if (localSendData != "") {
                if (localSendData.lastIndexOf("&") != localSendData.length - 1) {
                    localSendData += "&";
                }
            }
            if(sendData.indexOf("&") == 0){
                sendData = sendData.substring(1);
            }
        }
        localSendData += sendData;
    };

    this.setServiceClass = function(serviceClass) {
        if (serviceClass) {
            if(localSendData != ""){
                if (localSendData.lastIndexOf("&") != localSendData.length - 1) {
                    localSendData += "&";
                }
            }
            localSendData += "serviceClass=" + serviceClass;
        }
    };

    this.setMethodName = function(methodName) {
        if (methodName) {
            if(localSendData != ""){
                if (localSendData.lastIndexOf("&") != localSendData.length - 1) {
                    localSendData += "&";
                }
            }
            localSendData += "methodName=" + methodName;
        }
    };

    this.setStrutsFrm = function(frmName) {
        if (frmName) {
            if(localSendData != ""){
                if (localSendData.lastIndexOf("&") != localSendData.length - 1) {
                    localSendData += "&";
                }
            }
            localSendData += "frmName=" + frmName;
        }
    };

    this.setListFieldName = function(listFieldName) {
        if (listFieldName) {
            if(localSendData != ""){
                if (localSendData.lastIndexOf("&") != localSendData.length - 1) {
                    localSendData += "&";
                }
            }
            localSendData += "listFieldName=" + listFieldName;
        }
    };

    this.setDataFrm = function(dataFrm) {
        ajaxProcessor.setDataFrm(dataFrm);
    };

    this.setRequestMethod = function(requestMethod) {
        ajaxProcessor.setRequestMethod(requestMethod);
    };

    this.setSynchronize = function(synchronize) {
        ajaxProcessor.setSynchronize(synchronize);
    };

    this.performTask = function() {
        ajaxProcessor.setSendData(localSendData);
        ajaxProcessor.performTask();
    };

    this.getAjaxProcessor = function() {
        ajaxProcessor.setSendData(localSendData);
        return ajaxProcessor;
    };
}

function getFormSubmitData(frmObj) {
    var sendData = "";
    if (frmObj) {
        var addedTimes = 0;
        for (var i = 0; i < frmObj.length; i++) {
            var field = frmObj[i];
            if (field.name == "") {
                continue;
            }
            if (addedTimes > 0) {
                sendData += "&";
            }
            var fieldType = field.type;
            if(fieldType == "checkbox" || fieldType == "radio"){
                if(!field.checked){
                    continue;
                }
            }
            addedTimes++;
            sendData += field.name;
            sendData += "=";
            sendData += field.value;
        }
    }
    return sendData;
}