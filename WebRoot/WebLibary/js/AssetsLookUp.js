var factor = 0.8;
var g_dialogWidth = window.screen.availWidth * factor;
var g_dialogHeight = window.screen.availHeight * factor;

function lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.ams.newasset.bean.AssetsLookUpServlet?lookUpName=" + lookUpName;
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


//���ܣ����ô��ڱ�������ݵ���Ҫ�����Ĳ�Ŀ�ȣ���ʱ�����ʲ������֣��Ժ�ɿ������ӵ���˾��������
function do_SetPageWidth(){
	var headerWidth = document.body.clientWidth - 21;
	var dataWidth = document.body.clientWidth - 4;
	if(document.getElementById("headDiv")){
		document.getElementById("headDiv").style.width = headerWidth;
	}
	if(document.getElementById("dataDiv")){
		document.getElementById("dataDiv").style.width = dataWidth;
	}
}
//���ܣ���window���ڷ�����С�䶯��ʱ������ִ����������
window["onresize"] = function() {
	do_SetPageWidth();
};

function do_Close(){
	if(confirm("��ȷ���Ѿ���������Ĺ���������������ȷ������ť�������ȡ������ť���ڵ�ǰҳ��")){
		self.close();
		if(window.opener){
			var frm = opener.document.forms[0];
			if(frm){
				if(frm.act){
					frm.act.value = "QUERY_ACTION";
				}
				frm.submit();
			}
		}
	}
}

function do_AddressProcessSimpleAjax(url, sendData){
	do_AddressProcessAjax(url, "POST", true, sendData);
}
function do_OrgProcessSimpleAjax(url, sendData){
	do_OrgProcessAjax(url, "POST", true, sendData);
}

function do_AddressProcessAjax(url, reqMethod, isSynchronize, sendData) {//����XMLHttpRequest����
	if(!url || url == null || url == ""){
		alert("û��ָ�������URL��");
		return;
	}
	if(!sendData){
		sendData = null;
	}

	if(!isSynchronize){
		isSynchronize = true;
	}
	if(!reqMethod){
		reqMethod = "POST";
	} else {
		reqMethod = reqMethod.toUpperCase();
	}
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("����XMLHttpRequest����ʧ�ܣ�");
            }
        }
    }
	if(!xmlHttp){
		alert("����XMLHttpRequest����ʧ�ܣ������ԣ�");
		return;
	}
	xmlHttp.onreadystatechange = handleReadyStateChanges;
	xmlHttp.open(reqMethod, url, isSynchronize);
	xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xmlHttp.send(sendData);
}

function do_OrgProcessAjax(url, reqMethod, isSynchronize, sendData) {//����XMLHttpRequest����
	if(!url || url == null || url == ""){
		alert("û��ָ�������URL��");
		return;
	}
	if(!sendData){
		sendData = null;
	}

	if(!isSynchronize){
		isSynchronize = true;
	}
	if(!reqMethod){
		reqMethod = "POST";
	} else {
		reqMethod = reqMethod.toUpperCase();
	}
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("����XMLHttpRequest����ʧ�ܣ�");
            }
        }
    }
	if(!xmlHttp){
		alert("����XMLHttpRequest����ʧ�ܣ������ԣ�");
		return;
	}
	xmlHttp.onreadystatechange = handleOrgStateChanges;
	xmlHttp.open(reqMethod, url, isSynchronize);
	xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xmlHttp.send(sendData);
}
function handleReadyStateChanges() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
			do_AddressProcessResponse(xmlHttp.responseText);
        } else {
            alert(xmlHttp.status);
        }
    }
}
function handleOrgStateChanges() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
			do_OrgProcessResponse(xmlHttp.responseText);
        } else {
            alert(xmlHttp.status);
        }
    }
}



function do_ProcessSimpleAjax(url, sendData){
	do_ProcessAjax(url, "POST", true, sendData);
}

function do_ProcessAjax(url, reqMethod, isSynchronize, sendData) {//����XMLHttpRequest����
	if(!url || url == null || url == ""){
		alert("û��ָ�������URL��");
		return;
	}
	if(!sendData){
		sendData = null;
	}

	if(!isSynchronize){
		isSynchronize = true;
	}
	if(!reqMethod){
		reqMethod = "POST";
	} else {
		reqMethod = reqMethod.toUpperCase();
	}
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("����XMLHttpRequest����ʧ�ܣ�");
            }
        }
    }
	if(!xmlHttp){
		alert("����XMLHttpRequest����ʧ�ܣ������ԣ�");
		return;
	}
	xmlHttp.onreadystatechange = handleReadyStateChange;
	xmlHttp.open(reqMethod, url, isSynchronize);
	xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xmlHttp.send(sendData);
}

function handleReadyStateChange() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
			do_ProcessResponse(xmlHttp.responseText);
        } else {
            alert("����ʧ�ܣ�����ϵ����Ա��");
        }
    }
}


function lookUpSamplingValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.ams.sampling.bean.SamplingLookUpServlet?lookUpName=" + lookUpName;
    if(userPara != "undefined" && userPara != null){
        url += "&" + userPara;
    }
    dialogWidth = g_dialogWidth;
    dialogHeight = g_dialogHeight;
    var popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
//			window.open(url);
    return window.showModalDialog(url, null, popscript);
}

function lookUpYearAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.ams.yearchecktaskmanager.util.AssetsYearLookUpServlet?lookUpName=" + lookUpName;
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