var xmlHttp;
var orderPkName;
function editAttach(orderPkNameObj, orderTable, seqName) {
    
    if (isEmpty(orderPkNameObj.value)) { 
    	getOrderPkName(orderPkNameObj, seqName);
    } else {
        orderPkName = orderPkNameObj.value;
    }  
    orderPkName = orderPkNameObj.value; 
    
    var url = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=EDIT_ACTION&frmAction=MANAGE&orderPkName=" + orderPkName + "&orderTable=" + orderTable;
    var width=800;
    var height=580;
    var left = (window.screen.availWidth-10-width )/2;
    var top = (window.screen.availHeight-30-height )/2;
    var popscript ='width=' +width+ ',height=' +height+ ',left=' +left+ ',top=' +top+','+'toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=yes';
    window.open(url, "pickMtlWin"+orderPkName, popscript);
}
function showAttach(orderPkNameObj) {
    var url = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=QUERY_ACTION&orderPkName=" + orderPkNameObj.value;
    var style = "width=620px,height=380,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no";
    window.open(url, "pickMtlWin", style);
}
function getOrderPkName(orderPkNameObj, seqName) {
	xmlHttp = createXMLHttpRequest();
    var url = "/servlet/com.sino.ams.adjunct.servlet.GetPkNameServlet?seqName=" + seqName;
    xmlHttp.onreadystatechange = function() {
        setOrderPkName(orderPkNameObj)
    };
    xmlHttp.open('POST', url, false);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(null);
}
function setOrderPkName(orderPkNameObj) {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'ERROR') {
                alert('ERROR！');
            } else {
                orderPkName = xmlHttp.responseText;
                orderPkNameObj.value = xmlHttp.responseText;
            }
        }
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