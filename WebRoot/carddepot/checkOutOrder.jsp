<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.carddepot.constant.cardDepotConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>

<html>

<head><title>出库</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
</head>
<%
    String depotDefineListOption0 = request.getAttribute("depotDefineListOption0").toString();
    String depotDefineListOption1 = request.getAttribute("depotDefineListOption1").toString();
    String cardDefineListOption = request.getAttribute("cardDefineListOption").toString();
%>
<body leftmargin="1" topmargin="1">
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>

<form name="mainForm" action="/servlet/com.sino.ams.carddepot.servlet.YsOrderHeaderServlet" method="post">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="orderType" value="<%=cardDepotConstant.STOCK_OUT%>">
    <table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">

        <script type="text/javascript">
            printTitleBar("出库")
        </script>
        <tr></tr>
        <tr></tr>
        <tr></tr>
        <tr>
            <td>
                <table width="100%" id="table2" cellspacing="1">
                    <tr height="22">
                        <td width="10%" align="right">领料单位:</td>
                        <td width="20%"><select name="materialTo" style="width :100%" class="noborderYellow"><%=depotDefineListOption1%></select></td>
                        <td width="10%" align="right">统一设置仓库:</td>
                        <td width="20%"><select name="depotAll" id="depotAll" style="width :100%" onchange="do_changeDepotAll()" class="noborderYellow"><%=depotDefineListOption0%></select></td>


                        <td width="40%" align="center">NO：提交后生成</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <fieldset>
        <legend>物料信息
            <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_save();">
            <%--<img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">--%>
        </legend>

        <script type="text/javascript">
            var columnArr = new Array("仓库", "品名", "现有量", "申领数", "剩余量", "备注");
            var widthArr = new Array("20%", "20%", "10%", "10%", "10%", "30%");
            printTableHead(columnArr, widthArr);
        </script>

        <div style="overflow-y:scroll;width:100%;left:1px;margin-left:0"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
            <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
                <%
                    for (int i = 0; i < 6; i++) {
                %>
                <tr id="mainTr<%=i%>">
                    <td width="20%" align="center"><select onchange="do_getQuantity(<%=i%>)" name="materialFrom" id="materialFrom<%=i%>" style="width :100%" class="noborderYellow"><%=depotDefineListOption0%></select></td>
                    <td width="20%" align="center"><select onchange="do_getQuantity(<%=i%>)" name="cardId" id="cardId<%=i%>" style="width :100%" class="noborderYellow"><%=cardDefineListOption%></select></td>
                    <td width="10%" align="center"><input type="text" readonly name="oldQuantity" id="oldQuantity<%=i%>" value="" class="noneborderInput" style="width:100%;text-align:center"></td>
                    <td width="10%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" value="" class="noborderYellow" style="width:100%;text-align:center" onchange="do_setQuantity();"></td>
                    <td width="10%" align="center"><input type="text" readonly name="decQuantity" id="decQuantity<%=i%>" value="" class="noneborderInput" style="width:100%;text-align:center" onchange="do_setQuantity();"></td>
                    <td width="30%" align="center"><input type="text" name="remark" id="remark<%=i%>" value="" class="noborderYellow" style="width:100%;text-align:left"></td>
                </tr>
                <%
                    }
                %>
                <tr>

                    <td width="20%" align="center">合计</td>
                    <td width="20%" align="center"></td>
                    <td width="10%" align="center"></td>
                    <td width="10%" align="center"><input type="text" readonly id="allDecQuantity" value="" class="noneborderInput" style="width:100%;text-align:center"></td>
                    <td width="10%" align="center"></td>
                    <td width="30%" align="center"></td>
                </tr>
            </table>
        </div>
    </fieldset>
</form>
</body>
<script type="text/javascript">
function do_setQuantity() {
    var materialTo = document.getElementById("materialTo").value;
    var length = document.getElementsByName("quantity").length;
    document.getElementById("allDecQuantity").value = 0;
    for (i = 0; i < length; i++) {
        var materialFrom = document.getElementById("materialFrom" + i).value;
        var quantity = document.getElementById("quantity" + i).value;
        var oldQuantity = document.getElementById("oldQuantity" + i).value
        var decQuantity = oldQuantity - quantity;
        if (quantity == "") {
        } else if (materialTo == materialFrom) {
            alert("同库转移将引起统计错误！");
            document.getElementById("quantity" + i).value = "";
            break;
        } else if (decQuantity < 0) {
            alert("申请数不能大于现有量！");
            document.getElementById("quantity" + i).value = "";
            break;
        } else if (quantity < 0) {
            alert("申请数必须>0");
            document.getElementById("quantity" + i).value = "";
            break;
        } else {
            document.getElementById("decQuantity" + i).value = decQuantity;
        }


        document.getElementById("allDecQuantity").value =
        Number(document.getElementById("allDecQuantity").value) + Number(document.getElementById("quantity" + i).value);
    }

}
//-- getQuantity
function do_getQuantity(i) {

    var url = "";
    var cardId = document.getElementById("cardId" + i).value;
    var depotId = document.getElementById("materialFrom" + i).value;
    createXMLHttpRequest();
    if (cardId != "" && depotId != "") {
        url = "/servlet/com.sino.ams.carddepot.servlet.YsOrderHeaderServlet?act=GET_QTY_ACTION" +
              "&cardId=" + cardId +
              "&depotId=" + depotId +
              "&rowId=" + i;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}
function createXMLHttpRequest() {     //创建XMLHttpRequest对象
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
}

//checkCode
function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            var resArr = resText.split("@@@");
            var elemName = "oldQuantity" + resArr[1];
            document.getElementById(elemName).value = resArr[0];
            do_setQuantity();
        } else {
            alert(xmlHttp.status);
        }
    }
}
function do_save() {
    var qtyArry = document.getElementsByName("quantity") ;
    for (var i = 0; i < 6; i++) {
        if (qtyArry[i].value != "") {   //第一次检查
            do_setQuantity();
        }
        if (qtyArry[i].value != "") {   //第二次检查，两次不冲突
            document.mainForm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            document.mainForm.submit();
            break;
        }
    }
}
function do_changeDepotAll() {
    var idx = document.getElementById("depotAll").options.selectedIndex;
    for (var i = 0; i < 6; i++) {
        document.getElementById("materialFrom" + i).options.selectedIndex = idx;
        document.getElementById("oldQuantity" + i).value = "";
        document.getElementById("quantity" + i).value = "";
        document.getElementById("decQuantity" + i).value = "";
    }
    do_getList();
}

function do_getList() {
    var cardId = document.getElementById("depotAll").value;
    var paras = "&materialFrom=" + cardId ;
    requestAjax("GET_CARD_OPER_INFO", do_setOperListOption, null, paras);
}
function do_setOperListOption() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var ret = getRet(xmlHttp);
        if (ret != 'ERROR' && ret != '') {
            for (var m = 0; m < 6; m++) {
                document.getElementById("cardId" + m).outerHTML =
                "<select onchange='do_getQuantity(" + m + ");' name =cardId id = cardId" + m + " style='width :100%' class='noborderYellow' > "
                    + ret + "</select>";
                do_getQuantity(m);
            }
        }
    }
}
</script>
</html>
