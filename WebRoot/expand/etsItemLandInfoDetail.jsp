<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.expand.dto.EtsItemLandInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-15isAttachFile
  Time: 13:30:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>土地管理详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    EtsItemLandInfoDTO landdto = (EtsItemLandInfoDTO) request.getAttribute(WebAttrConstant.AMS_LAND_INFO_DTO);
    String action = parser.getParameter("act");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String barcode1 = userAccount.getCompanyCode() + '-';

%>
<script type="text/javascript">
    printTitleBar("土地管理详细信息")
</script>
<body leftmargin="0" topmargin="0" onload="set_value()">

<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet" name="mainForm" method="post">
<table border="0" width="100%">
    <tr>
        <td align="right" width="15%">土地条码：</td>
        <td width="35%" align="left">
            <input name="barcode" type="text"
            <%

                if(!landdto.getSystemId().equals("")){

            %>
                   readonly class="readonlyInput"
            <%

                }else{

            %>
                   class="noEmptyInput" readonly
            <%

                }

            %>
                   value="<%=landdto.getBarcode()%>" style="width:46%">
            <%

                if(landdto.getSystemId().equals("")){

            %>
                  <input type="button" value="生成" onClick="addBarcode();">
            <%

                }

            %>
        </td>
    </tr>
    <tr>
        <td align="right" width="15%">土地名称：</td>
        <td width="35%" align="left"><input name="itemName" type="text" readonly class="noEmptyInput"
                                            value="<%=landdto.getItemName()%>" style="width:46%" onclick="do_SelectSystemItem();"><a
                class="linka" style="cursor:'hand'" onclick="do_SelectSystemItem();">[…]</a></td>
    </tr>

    <tr>
        <td align="right" width="15%">土地型号：</td>
        <td width="35%" align="left"><input name="itemSpec" type="text" class="readonlyInput" value="<%=landdto.getItemSpec()%>"
                                      readonly      style="width:46%" onclick="do_SelectSystemItem();"></td>
    </tr>
    <tr>
        <td align="right" width="15%">土地面积：</td>
        <td width="35%"><input name="landArea" type="text" value="<%=landdto.getLandArea()%>" style="width:46%" onblur="do_verify2();">
        </td>

    </tr>
    <tr>
        <td align="right" width="15%">土地使用证号：</td>
        <td width="35%" align="left"><input type="text" name="landCertficateNo" style="width:46%"
                                            value="<%=landdto.getLandCertficateNo()%>" onblur="do_verify10();">
        </td>
    </tr>
    <tr>
        <td align="right" width="15%">所在地点：</td>
        <td width="35%" align="left"><input type="text" name="landAddress" style="width:46%" class="readonlyInput" readonly
                                            value="<%=landdto.getLandAddress()%>"><a class="linka" style="cursor:'hand'"
                                                                                     onclick="do_selectAddress();">[…]</a>
        </td>
    </tr>
    <tr>
        <td align="right" width="15%">地积单位：</td>
        <td width="35%"><select style="width:46%" name="areaUnit"><%=parser.getAttribute(WebAttrConstant.LAND_AREA_UNIT_OPTION)%>
        </select></td>
    </tr>

    <tr>
        <td width="15%" align="right" height="22"><font color="" size="2"><b>相关文件：</b></font></td>
        <td width="35%"><img src="/images/button/appendix.gif" alt="附加文件" onClick="do_AttachFiles()"></td>
    </tr>
    <%
        //   if(landdto.getSystemId().equals("")){
        String yesChecked = "checked";
        String noChecked = "";
        if (landdto.getIsRent().equals("N")) {
            yesChecked = "";
            noChecked = "checked";
        }
    %>
    <tr>
        <td align="right">是否租赁资产：</td>
        <td><input type="radio" name="isRent" id="yes1" value="Y" <%=yesChecked%> onclick="do_show();"><label for="yes1">是</label>
            <input type="radio" name="isRent" id="no1" value="N"  <%=noChecked%> onclick="do_show();"><label for="no1">否</label></td>
    </tr>
</table>
<div id="rent" style="visibility:visible">
    <table border="0" width="100%">
        <%
            //    }
//    if(!landdto.getIsRent().equals("N")){
        %>
        <tr>
            <td align="right" width="15%">租金：</td>
            <td width="35%"><input name="rental" type="text" value="<%=landdto.getRental()%>" style="width:46%" onblur="do_verify3();">
            </td>
        </tr>
        <tr>
            <td align="right" width="15%">金额单位：</td>
            <td width="35%"><input type="text" style="width:46%" name="moneyUnit" value="<%=landdto.getMoneyUnit()%>" onblur="do_verify11();"></td>
        </tr>
        <tr>
            <td align="right" width="15%">出租人：</td>
            <td width="35%" align="left"><input name="rentPerson" type="text" class="noEmptyInput" value="<%=landdto.getRentPerson()%>"  onblur="do_verify12();"
                                                style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="15%">租赁日期：</td>
            <td width="35%"><input type="text" name="rentDate" 
                                       class="readonlyInput"
                                   value="<%=StrUtil.nullToString(landdto.getRentDate())%>"
                                   style="width:46%" title="点击选择日期"
                                   readonly onclick="gfPop.fPopCalendar(rentDate)"><img src="/images/calendar.gif" alt="点击选择日期"
                                                                                        onclick="gfPop.fPopCalendar(rentDate)"></td>
        </tr>

        <tr>
            <td align="right" width="15%">截至日期：</td>
            <td width="35%">
                <input type="text" name="endDate"      class="readonlyInput"
                <%

                  if (StrUtil.isEmpty(landdto.getEndDate())) {

                %>
                       value=""
                <%

                  } else {

                %>
                       value="<%=landdto.getEndDate()%>"
                <%

                  }

                %>
                       style="width:46%" title="点击选择日期" readonly onclick="gfPop.fEndPop(rentDate,endDate);"><img
                    src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(rentDate,endDate);">

            </td>
        </tr>

        <%--<tr>--%>
        <%--<td align="right" width="15%">付款方式：</td>--%>
        <%--<td width="35%"><select  name="payType" style="width:46%" ><%=parser.getAttribute(WebAttrConstant.PAY_TYPE_OPTION)%></select></td>--%>
        <%--</tr>--%>
        <tr>
            <td align="right" width="15%">租赁用途：</td>
            <td width="35%" align="left"><textarea name="rentUsage" type="text" value="<%=landdto.getRentUsage()%>"
                                                   style="width:46%"><%=landdto.getRentPerson()%></textarea></td>
        </tr>


        <%
            //        }
        %>

    </table>
</div>

<table border="0" width="100%">
    <tr>
        <td align="right" width="15%"></td>
        <td width="35%"></td>
    </tr>
    <tr>
        <td align="center" width="15%"></td>
        <td width="35%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img src="/images/button/save.gif" alt="保存土地管理信息" onClick="do_save(); return false;">
            <img src="/images/button/back.gif" alt="返回" onclick="do_Back();return false;"></td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="isExist">
<input type="hidden" name="itemCode" value="<%=landdto.getItemCode()%>">
<input type="hidden" name="systemId" value="<%=landdto.getSystemId()%>">
<input type="hidden" name="isAttachFile" value="<%=parser.getAttribute(WebAttrConstant.ATTACH_FILE_ATTR)%>">
<input type="hidden" name="addressId" value="<%=landdto.getAddressId()%>">
<input type="hidden" name="rentId" value="<%=landdto.getRentId()%>">
<input type="hidden" name="houseAddress">
</form>
</body>
<%--<iframe name="downTarget" src="" style="display:none"></iframe>--%>

<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>


<script type="text/javascript">
function do_save() {
var barcode1 = "<%=barcode1%>";
    //    var barcode = document.mainForm.barcode.value;
    //        alert(barcode);
    //    if (barcode == barcode1) {
    //        alert("请输入土地条码信息");
    //        document.mainForm.barcode.style.color = "red";
    //        document.mainForm.barcode.focus();
    //    } else {
//    var rentPerson = document.mainForm.rentPerson.value;
//    var itemSpec = document.mainForm.itemSpec.value;
//
//    if (document.getElementById("rent").style.visibility == "visible" && rentPerson == "") {
//        //            if{
//        alert("租赁土地必须有租赁人！");
//        //            }
//    } else {
//     var rentPerson = document.mainForm.rentPerson.value;
    var rety= do_check();	//检查租赁土地必须有租赁人！
    if(rety) {
//        alert(do_check);
//         selectAll("affix");
        var fieldNames = "barcode;itemName";
        var fieldLabels = "土地条码;土地名称";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        if (isValid) {
        with (mainForm) {
            if (systemId.value == "") {
                act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            } else {
                act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
            action = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet";
//            mainForm.target = "_self";
            submit();
                        }
            //        }
        }
    }else{
        alert("租赁土地必须有出租人！");
    }
}

function do_Back() {
    with (mainForm) {
        act.value = "";
        action = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet";
//        mainForm.target = "_self";
        submit();
    }
}


function do_SelectSystemItem() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_LAND%>";
    var dialogWidth = 35;
    var dialogHeight = 30;
    //LOOKUP传参数 必须和DTO中一致
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
    }
}

function do_AttachFiles() {
    <%--if (mainForm.systemId.value == "") {
        var fieldNames = "barcode";
        var fieldLabels = "条码";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        if (isValid) {
            mainForm.isAttachFile.value = "true";
            do_save();
        }
    } else {
        var url = "/servlet/com.sino.ams.expand.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.NEW_ACTION%>&barcode=<%=landdto.getBarcode()%>" + "&type=LAND";
        var style = "height=300,width=500,top=300,left=400";
        var winName = "uploadWin";
        window.open(url, winName, style);
    }--%>
    if(document.mainForm.barcode.value!==""){
        //var affix = document.getElementById("affix");
        var url="/servlet/com.sino.ams.expand.servlet.FileServlet?transId=" + mainForm.barcode.value + "&docType=" + 'UPLOAD'+ "&transStatus="+'';
        var style = "width=620px,height=380,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no";
        window.open(url, "pickMtlWin", style);
        /*
        if(retVal){
            var arr = retVal.split("$");
            if(cf1(arr[1])) {
                var option = new Option(arr[0], retVal);
                 affix.add(option)
            }
        }*/
        
    }else{
       alert("请先“生成”条码！");
    }
}

function onaffix(){
   var filePathm = document.getElementById("affix").value ;
    var  filePath = filePathm.split("$");
    do_DownLoad(filePath[1]);
}

function do_DownLoad(systemId) {
    var url = "/servlet/com.sino.ams.expand.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.DOWNLOAD_ACTION%>&filePath=" + filePath;
    mainForm.action = url;
//    mainForm.target = "downTarget";
    mainForm.submit();
}

function initPage() {
    var attachFile = mainForm.isAttachFile.value;
    if (attachFile == "true") {
        var url = "/servlet/com.sino.ams.expand.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.NEW_ACTION%>&barcode=<%=landdto.getBarcode()%>";
        var style = "height=300,width=500,top=300,left=400";
        var winName = "uploadWin";
        window.open(url, winName, style);
    }
}


function do_verify(obj) {
    var barcode = obj.value ;
    //    alert(barcode);
    if (barcode.indexOf("-") != -1) {
    } else {
        alert("请输入带‘-’的数字");
        obj.focus();
        return false;
    }
}

function do_verify2() {
    var fieldNames = "landArea";
    var fieldLabels = "土地面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("房屋面积必须为正数字！");
    }
}

function do_verify3() {
    var fieldNames = "rental";
    var fieldLabels = "租金";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("租金必须为正数字！");
    }
}

function do_verify12(){
    var fieldNames = "rentPerson";
    var fieldLabels = "0$出租人$200";
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
}

function do_verify11(){
    var fieldNames = "moneyUnit";
    var fieldLabels = "0$租金单位$20";
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
}

function do_verify10(){
    var fieldNames = "landCertficateNo";
    var fieldLabels = "0$土地证号$30";
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
}

function do_verify9() {
    var barcode1 = "<%=barcode1%>";
    var barcode = document.mainForm.barcode.value;
    var barcode2 = barcode.substring(0, 5);
    if (barcode2 == barcode1) {
        var barcode3 = barcode.substring(5);
        if (barcode3.length == 8) {
            //            alert(barcode3.length);
            for (var i = 0; i < barcode3.length; i++)
            {
                if (barcode3.charAt(i) < '0' || barcode3.charAt(i) > '9')
                {
                    alert("在" + barcode1 + "后输入8位只能是“数字”！");
                    document.mainForm.barcode.focus();
                    return false;
                }
            }
        } else {
            alert("请在" + barcode1 + "后输入“8位”的数字！");
            document.mainForm.barcode.focus();
            return false;
        }
    } else {
        alert("请输入" + barcode1 + "加上8位数字！");
        document.mainForm.barcode.focus();
        return false;
    }
}

var xmlHttp;
function do_verifybarcode() {
    do_verify9()
    var url = "";
    var segment1 = document.mainForm.barcode.value;
    createXMLHttpRequest();
    if (document.mainForm.barcode.value) {
        url = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet?act=verifyBarcode&barcode=" + document.mainForm.barcode.value;
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

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'Y') {
                document.mainForm.isExist.value = 'Y';
                document.getElementById("barcode11").style.visibility = "visible"
                document.mainForm.barcode.focus();
            } else {
                document.mainForm.isExist.value = 'N';
                document.getElementById("barcode11").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

function do_Selectbarcode() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_BARCODENO%>";
    var dialogWidth = 51;
    var dialogHeight = 30;
    //LOOKUP传参数 必须和DTO中一致
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
    }
}

function do_show() {
    var show = document.mainForm.isRent;
    for (var i = 0; i < show.length; i++) {
        if (show[0].checked) {
            document.getElementById("rent").style.visibility = "visible";
        } else {
            document.getElementById("rent").style.visibility = "hidden"
        }
    }
}

function do_selectAddress() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_ADDRESS%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
        document.mainForm.landAddress.value = document.mainForm.houseAddress.value;
    } else {
        document.mainForm.landAddress.value = "";
        document.mainForm.addressId.value = "";
    }
}

function addBarcode(){
  do_creatBarcode();
}
//var xmlHttp;
function do_creatBarcode() {
    var url = "";
    createXMLHttpRequest();
        url = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?act=creatBarcode";
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
}

function set_value() {
    do_show();
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

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            unescape(xmlHttp.responseText);
            document.mainForm.barcode.value = xmlHttp.responseText;
        } else {
            alert(xmlHttp.status);
        }
    }
}

function cf1(pathvalue){
    var retVal = true;
    var values = new Array();
    var affix1 = document.getElementById("affix");
    for (var i = 0; i < affix1.length; i++) {
        var paths = (affix1.options[i].value);
        var path = paths.split("$");
        values[i] = path[1];
    }
    for (var j = 0; j < values.length; j++) {
        if (values[j] == pathvalue) {
            alert("附件中的文件不能相同！");
            retVal = false;
        }
    }
    return retVal;
}

function do_check() {
    var ret = true;
    var rentPerson = document.mainForm.rentPerson.value;
    var show = document.mainForm.isRent;
    for (var i = 0; i < show.length; i++) {
        if ((show[0].checked) && (rentPerson=="")) {
            alert("租赁土地必须有租赁人！");
            ret = false;
        }
    }
    return ret;
}

function remove_AttachFiles(){
    dropSelectedOption("affix");
}
</script>

</html>