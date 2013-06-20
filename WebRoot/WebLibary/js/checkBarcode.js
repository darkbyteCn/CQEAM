/**
 * 用于检查要出库的条码是否还在该仓库中
 * 要求:
 *     1.页面上要有 fromObjectNo 这个隐藏域
 *     2.页面上条码的名字应该为 barcode
 *     3.页面上要打印 WebConstant.WAIT_TIP_MSG (提示信息)
 *     4.页面最下方请增加一个div,用于显示错误信息,id应为 showMsg ,如:<div id=\"showMsg\" style=\"color:red\"></div>
 *     5.页面提交动作执行的 function 应为: do_submit(),提交按钮的 onclick 事件改为 checkBarcode();
 *     6.需同时加载另外两个JS文件: /WebLibary/js/ajax.js
 *                              /WebLibary/js/json.js
 **/
var xmlHttp;
function checkBarcode() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    var barcodes = document.getElementsByName("barcode");
    var barcodeArr = new Array();
    for (var i = 0; i < barcodes.length; i++) {
        barcodeArr[i] = barcodes[i].value;
    }
    var qtys = document.getElementsByName("quantity");
    var quantityArr = new Array();
    for (var i = 0; i < qtys.length; i++) {
        quantityArr[i] = qtys[i].value;
    }
    var objectNo = document.getElementById("fromObjectNo").value;

    var url = "/servlet/com.sino.ams.bean.CheckBarCodeServlet?objectNo=" + objectNo;
    xmlHttp = GetXmlHttpObject(do_check);
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
    xmlHttp.send(barcodeArr.toJSONString()+"$$$"+quantityArr.toJSONString());
}
function do_check() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var resText = xmlHttp.responseText;
        if (resText == "ERROR") {
            alert(resText);
        } else if (resText == "OK") {
            eval("do_submit()");
        } else {
            document.getElementById("showMsg").innerText = "以下物料编码可用量不足，无法处理，请重新选择:" + resText;
        }
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "hidden";
        xmlHttp = null;
    }
}
