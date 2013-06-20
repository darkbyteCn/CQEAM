<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseUsesDTO" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  User: Zyun
  Date: 2007-10-15
  Time: 13:30:49
--%>
<html>
<head>
	<title>房屋详细信息</title>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);

    AmsHouseUsesDTO dto = (AmsHouseUsesDTO) request.getAttribute("AMSHOUSEUSESDTO");
    DTOSet set = (DTOSet) request.getAttribute("AMSHOUSEUSESDTODETAIL");
    if (dto == null) {
        dto = new AmsHouseUsesDTO();
    }
    AmsHouseInfoDTO housedto = (AmsHouseInfoDTO) request.getAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO);
    String houseUsage = (String) request.getAttribute(WebAttrConstant.HOUSE_USAGE_OPTION);

    String houseStatus = (String) request.getAttribute(WebAttrConstant.HOUSE_STATUS_OPTION);
    String action = parser.getParameter("act");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String barcode1 = userAccount.getCompanyCode() + '-';
%>
<script type="text/javascript">
    printTitleBar("房屋详细信息")
</script>
<body onload="set_value()">

<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet" name="mainForm" method="post">
<table border="0" width="100%">
<tr>
    <td align="center" colspan="4" width="100%" id="barcode11" style="color:red;visibility:hidden">对不起，该房屋条码已存在！</td>
</tr>
<tr>
    <td align="right" width="15%">房屋条码：</td>
    <td width="35%" align="left">
        <input name="barcode" type="text"
        <%
            if(!housedto.getSystemId().equals("")){
        %>
               readonly class="input_style2"
        <%
            }else{
        %>
               class="noEmptyInput" readonly
        <%
            }

        %>
               value="<%=housedto.getBarcode()%>" style="width:80%">
        <%
            if (housedto.getSystemId().equals("")) {
        %>
        <input type="button" value="生成" onClick="addBarcode();">
        <%
            }
        %>
    </td>
    <td align="right" width="15%">房产证号：</td>
    <td width="35%" align="left"><input type="text" name="houseCertificateNo" style="width:80%" class="input_style1" onblur="do_verify10();"
                                        value="<%=housedto.getHouseCertificateNo()%>">
    </td>
</tr>
<tr>
    <td align="right" width="15%">房屋类型：</td>
    <td width="35%" align="left"><input name="itemName" type="text" readonly class="input_style1"
                                        value="<%=housedto.getItemName()%>" style="width:80%"
                                        onclick="do_SelectSystemItem();"> <font color="red">*</font><a class="linka" style="cursor:'hand'"
                                                                            onclick="do_SelectSystemItem();">[…]</a>
    </td>
    <td align="right" width="15%">房屋状态：</td>
    <td width="35%" align="left"><select type="text" name="houseStatus" class="select_style1" style="width:80%"><%=houseStatus%>
    </select>
    </td>
</tr>

<tr>
    <td align="right" width="15%">房屋型号：</td>
    <td width="35%" align="left"><input name="itemSpec" type="text" readonly class="input_style2"
                                        value="<%=housedto.getItemSpec()%>" style="width:80%"
                                        onclick="do_SelectSystemItem();"></td>
    <%
        String yesChecked = "checked";
        String noChecked = "";
        if (housedto.getIsRent().equals("N")) {
            yesChecked = "";
            noChecked = "checked";
        }
    %>

    <td align="right">是否租赁资产：</td>
    <td><input type="radio" name="isRent" id="yes1" value="Y" <%=yesChecked%> onclick="do_show();"><label
            for="yes1">是</label>
        <input type="radio" name="isRent" id="no1" value="N"  <%=noChecked%> onclick="do_show();"><label
            for="no1">否</label></td>
</tr>
<tr>
    <td align="right" width="15%">所在地点：</td>
    <td width="35%" align="left"><input type="text" name="houseAddress" style="width:80%" class="input_style2" readonly
                                        value="<%=housedto.getHouseAddress()%>"> <font color="red">*</font><a class="linka" style="cursor:'hand'"
                                                                                   onclick="do_selectAddress();">[…]</a>
    </td>
        <td align="right" width="15%" id="rent1">租金：</td>
        <td width="35%" id="rent2"><input name="rental" type="text" value="<%=housedto.getRental()%>" style="width:80%" class="input_style1"
                               onblur="do_verify3();"></td>
</tr>
<tr>
    <td align="right" width="15%">楼层：</td>
    <td width="35%" align="left"><input type="text" name="floorNumber" style="width:80%" class="input_style1"
                                        value="<%=housedto.getFloorNumber()%>" onblur="do_verify1();">
    </td>
    <td align="right" width="15%" id="rent3">租金单位：</td>
    <td width="35%" id="rent4" ><input type="text" style="width:80%" name="moneyUnit" class="input_style1" value="<%=housedto.getMoneyUnit()%>" onblur="do_verify11();" >
    </td>
</tr>
<tr>
    <td align="right" width="15%">房屋编号：</td>
    <td width="35%" align="left"><input name="houseNo" type="text" class="input_style1" value="<%=housedto.getHouseNo()%>" style="width:80%">
    </td>
    <td align="right" width="15%" id="rent5">出租人：</td>
    <td width="35%" align="left" id="rent6"><input name="rentPerson" type="text" class="input_style1" onblur="do_verify12();"
                                        value="<%=housedto.getRentPerson()%>" style="width:80%"> <font color="red">*</font></td>
</tr>
<tr>
    <td align="right" width="15%">房屋面积(㎡)：</td>
    <td width="35%"><input name="houseArea" type="text" value="<%=housedto.getHouseArea()%>" style="width:80%" class="input_style1"
                           onblur="do_verify2();"></td>
    <td align="right" width="15%" id="rent7">租赁日期：</td>
    <td width="35%" id="rent8"><input type="text" name="rentDate" value="<%=housedto.getRentDate()%>" style="width:80%" class="input_style2"
                           title="点击选择日期" readonly onclick="gfPop.fPopCalendar(rentDate)"><img
            src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(rentDate)"></td>
</tr>
<tr>
    <td width="15%" align="right" height="22"><font color="" size="2"><b>相关文件：</b></font></td>
    <td width="35%"><img src="/images/eam_images/attach.jpg" alt="附加文件" onClick="do_AttachFiles()"></td>
    <td align="right" id="rent9">截至日期：</td>
    <td width="35%" id="rent10">
        <input type="text" name="endDate" class="input_style2"
        <%
          if (StrUtil.isEmpty(housedto.getEndDate())) {
        %>
               value=""
        <%
          } else {
        %>
               value="<%=housedto.getEndDate()%>"
        <%
          }
        %>
               style="width:80%" title="点击选择日期" readonly onclick="gfPop.fEndPop(rentDate,endDate);"><img
            src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(rentDate,endDate);">
    </td>
</tr>
</table>
<fieldset>
    <legend>
        <img src="/images/button/addLine.gif" alt="添加行" onclick=" do_addLine();" >
    </legend>
    <table id=tb_order width="100%" border="1">
    <%
        if (set == null || set.isEmpty()) {
    %>
        <tr>
            <td width=16% align="right">用途：</td>
            <td width=16%><select style="width:100%" name="usage" class="select_style1"><%=houseUsage%>
            </select></td>
            <td align="right" width=16%>面积：</td>
            <td width=16% align="left">
                <input type="text" name="area" class="input_style1" value="<%=dto.getArea()%>" onblur="do_verify13();">
            </td>
            <td align="right" width=16%>备注：</td>
            <td width=16% align="left">
                <input type="text" name="remark" class="input_style1" value="<%=dto.getRemark()%>" onblur="do_verify14();">
            </td>
            <td style="display:none">
                <input type="hidden" name="usage1" value="<%=dto.getUsage()%>">
            </td>
        </tr>
        <tr>
            <td width=16% align="right">用途：</td>
            <td width=16%><select style="width:100%" class="select_style1" name="usage"><%=houseUsage%>
            </select></td>
            <td align="right" width=16%>面积：</td>
            <td width=16% align="left">
                <input type="text" name="area" class="input_style1" value="<%=dto.getArea()%>" onblur="do_verify13();">
            </td>
            <td align="right" width=16%>备注：</td>
            <td width=16% align="left">
                <input type="text" name="remark" class="input_style1" value="<%=dto.getRemark()%>" onblur="do_verify14();">
            </td>
            <td style="display:none">
                <input type="hidden" name="usage1" value="<%=dto.getUsage()%>">
            </td>
        </tr>
        <tr>
            <td width=16% align="right">用途：</td>
            <td width=16%><select style="width:100%" class="select_style1" name="usage"><%=houseUsage%>
            </select></td>
            <td align="right" width=16%>面积：</td>
            <td width=16% align="left">
                <input type="text" name="area" class="input_style1" value="<%=dto.getArea()%>" onblur="do_verify13();">
            </td>
            <td align="right" width=16%>备注：</td>
            <td width=16% align="left">
                <input type="text" name="remark" class="input_style1" value="<%=dto.getRemark()%>" onblur="do_verify14();">
            </td>
            <td style="display:none">
                <input type="hidden" name="usage1" value="<%=dto.getUsage()%>">
            </td>
        </tr>
        <tr>
            <td width=16% align="right">用途：</td>
            <td width=16%><select style="width:100%" class="select_style1" name="usage"><%=houseUsage%>
            </select></td>
            <td align="right" width=16%>面积：</td>
            <td width=16% align="left">
                <input type="text" name="area" class="input_style1" value="<%=dto.getArea()%>" onblur="do_verify13();">
            </td>
            <td align="right" width=16%>备注：</td>
            <td width=16% align="left">
                <input type="text" name="remark" class="input_style1" value="<%=dto.getRemark()%>" onblur="do_verify14();">
            </td>
            <td style="display:none">
                <input type="hidden" name="usage1" value="<%=dto.getUsage()%>">
            </td>
        </tr>

    <%
    } else {
        for (int i = 0; i < set.getSize(); i++) {
            AmsHouseUsesDTO dto1 = (AmsHouseUsesDTO) set.getDTO(i);
    %>
        <tr id="mainTr<%=i%>">
            <td width=16% align="right">用途：</td>
            <td width=16%><select style="width:100%" class="select_style1" id="usage<%=i%>" name="usage"><%=houseUsage%>
            </select></td>
            <td align="right" width=16%>面积：</td>
            <td width=16% align="left">
                <input type="text" name="area" class="input_style1" value="<%=dto1.getArea()%>" onblur="do_verify13();">
            </td>
            <td align="right" width=16%>备注：</td>
            <td width=16% align="left">
                <input type="text" name="remark" class="input_style1" value="<%=dto1.getRemark()%>" onblur="do_verify14();">
            </td>
            <td style="display:none">
                <input type="hidden" name="usage1" value="<%=dto1.getUsage()%>"><input type="hidden" name="id" value="<%=i%>">
            </td>
        </tr>
    <% }
    }%>
        </table>
</fieldset>
<table width="100%" border="0">
    <tr>
        <td align="center" width="15%"></td>
        <td width="35%"> &nbsp;&nbsp;&nbsp;
            <img src="/images/eam_images/save.jpg" alt="保存设备信息"
                 onclick="do_save();" <%--onClick="do_save(); return false;"--%>> &nbsp;
            <%
                if (!housedto.getSystemId().equals("")) {
            %>
            <%
                }
            %>
            <img src="/images/eam_images/back.jpg" alt="返回" onclick="do_Back();return false;"></td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="isExist">
<input type="hidden" name="itemCode" value="<%=housedto.getItemCode()%>">
<input type="hidden" name="systemId" value="<%=housedto.getSystemId()%>">
<input type="hidden" name="has" value="<%=housedto.getHas()%>">
<input type="hidden" name="rentId" value="<%=housedto.getRentId()%>">
<input type="hidden" name="addressId">
<input type="hidden" name="saved">

<input type="hidden" name="isAttachFile" value="<%=parser.getAttribute(WebAttrConstant.ATTACH_FILE_ATTR)%>">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
function do_save() {
    var barcode1 = "<%=barcode1%>";
    var barcode = document.mainForm.barcode.value;
    var rety = do_check();
    if (rety) {
        var fieldNames = "barcode;itemSpec";
        var fieldLabels = "房屋条码;房屋型号";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        var systemId = mainForm.systemId.value;
        var has = mainForm.has.value;
        if (isValid) {
//            selectAll("affix");
            with (mainForm) {
                if (systemId.value == "") {
                    act.value = "<%=WebActionConstant.CREATE_ACTION%>";
                } else {
                    act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                }
                action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
                //                mainForm.target = "_self";
                submit();
            }
        }
        //    }
    } else {
        alert("租赁房屋必须有租赁人！");
    }
}
function do_select() {
    selectAll("affix");
}
function do_Back() {
    with (mainForm) {
        act.value = "";
        action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
        submit();
    }
}


function do_SelectSystemItem() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_HOUSE%>";
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
    if (document.mainForm.barcode.value !== "") {
        var affix = document.getElementById("affix");
         var url="/servlet/com.sino.ams.newasset.servlet.FileServlet?transId=" +
                  mainForm.barcode.value + "&fileType=" + 'RENT' + "&docType=" + 'UPLOAD'+"&transType="+'ASS_RENT'+"&transStatus="+'';
        var style = "width=620px,height=380,top=100,left=100,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no";
        window.open(url, "pickMtlWin", style);
        if (retVal) {
            var arr = retVal.split("$");
            if (cf1(arr[1])) {
                var option = new Option(arr[0], retVal);
                affix.add(option)
            }
        }
    } else {
        alert("请先“生成”条码！");
    }
}

function remove_AttachFiles() {
    dropSelectedOption("affix");
}

function do_DownLoad(filePath) {

    var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.DOWNLOAD_ACTION%>&filePath=" + filePath;
    mainForm.action = url;
    mainForm.submit();
}

function initPage() {
    var attachFile = mainForm.isAttachFile.value;
    if (attachFile == "true") {
        var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.NEW_ACTION%>&barcode=<%=housedto.getBarcode()%>" + "&type=HOUSE";
        var style = "height=300,width=400,top=200,left=400";
        var winName = "uploadWin";
        window.open(url, winName, style);
    }
}
function do_verify1() {
    var floorNumber = mainForm.floorNumber.value;
    var fieldNames = "floorNumber";
    var fieldLabels = "楼层";
    if (!formValidate(fieldNames, fieldLabels, NUMBER_VALIDATE)) {
        //        alert("楼层必须为数字！");
    } else if (floorNumber > 150) {
        alert("楼层不可能为“" + floorNumber + "”吧！")
    }
}

function do_verify2() {
    var fieldNames = "houseArea";
    var fieldLabels = "房屋面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("房屋面积必须为正数字！");
    }
}

function do_verify6() {
    var fieldNames = "businessArea";
    var fieldLabels = "营业用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}
function do_verify4() {
    var fieldNames = "produceHosuseArea";
    var fieldLabels = "生产机房用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}

function do_verify5() {
    var fieldNames = "produceBaseArea";
    var fieldLabels = "生产基站用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}

function do_verify7() {
    var fieldNames = "officeArea";
    var fieldLabels = "办公用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}
function do_verify3() {
    var fieldNames = "rental";
    var fieldLabels = "租金";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}
function do_verify14(){
    return;
    var fieldNames = "remark";
    var fieldLabels = "0$备注$512";
    alert("1");
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
    alert("2");
}

function do_verify13() {
    var fieldNames = "area";
    var fieldLabels = "面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
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
    var fieldNames = "houseCertificateNo";
    var fieldLabels = "0$房产证号$30";
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
    //    alert(document.mainForm.barcode.value);
    do_verify9()
    var url = "";
    var segment1 = document.mainForm.barcode.value;
    createXMLHttpRequest();
    if (document.mainForm.barcode.value) {
        url = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?act=verifyBarcode&barcode=" + document.mainForm.barcode.value;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
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
    var show = document.mainForm.isRent
    for (var i = 0; i < show.length; i++) {
        if (show[0].checked) {
             document.getElementById("rent1").style.display="" ;
             document.getElementById("rent2").style.display="" ;
             document.getElementById("rent3").style.display="" ;
             document.getElementById("rent4").style.display="" ;
             document.getElementById("rent5").style.display="" ;
             document.getElementById("rent6").style.display="" ;
             document.getElementById("rent7").style.display="" ;
             document.getElementById("rent8").style.display="" ;
             document.getElementById("rent9").style.display="" ;
             document.getElementById("rent10").style.display="" ;
            //document.getElementById("rent").style.visibility = "visible";
        } else {
             document.getElementById("rent1").style.display="none" ;
             document.getElementById("rent2").style.display="none" ;
             document.getElementById("rent3").style.display="none" ;
             document.getElementById("rent4").style.display="none" ;
             document.getElementById("rent5").style.display="none" ;
             document.getElementById("rent6").style.display="none" ;
             document.getElementById("rent7").style.display="none" ;
             document.getElementById("rent8").style.display="none" ;
             document.getElementById("rent9").style.display="none" ;
             document.getElementById("rent10").style.display="none" ;
            //document.getElementById("rent").style.visibility = "hidden"
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
    }
}

function onaffix() {
    var filePathm = document.getElementById("affix").value ;
    var filePath = filePathm.split("$");
    do_DownLoad(filePath[1]);

}

function addBarcode() {
    do_creatBarcode();
}

var xmlHttp;
function do_creatBarcode() {
    var url = "";
    xmlHttp = createXMLHttpRequest();
    url = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?act=creatBarcode";
    xmlHttp.onreadystatechange = handleReadyStateChange1;
    xmlHttp.open("post", url, true);
    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp.send(null);
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

function cf1(pathvalue) {
    var retVal = true;
    var values = new Array();
    var affix1 = document.getElementById("affix");
    for (var i = 0; i < affix1.length; i++) {
        var paths = (affix1.options[i].value);
        var path = paths.split("$");
        values[i] = path[1];
    }
    //     alert(values);
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
        if ((show[0].checked) && (rentPerson == "")) {
            //               alert("租赁土地必须有租赁人！");
            ret = false;
        }
    }
    //    alert(ret);
    return ret;
}
function set_value() {
    var tbObj = document.getElementById("tb_order");
    var rows = tbObj.rows;
    var rowlength = rows.length;
    var row ;
    var usage ;
    var selObj;
    for (var i = 0; i < rowlength; i++) {
        row = rows[i];
        usage = row.cells[6].childNodes[0].value;
        selObj = row.cells[1].childNodes[0];
        if (usage != "") {
            selectSpecialOptionByItem(selObj, usage);
        }
    }
    do_show();
}


function do_addLine() {
    var rowCount = document.getElementById("tb_order").rows.length - 1 ;
    if (rowCount < 6) {
        var tbObj = document.getElementById("tb_order");
        var rs = tbObj.rows;
        var count = rs.length
        var row0 = rs[count - 1]
        var newRow = row0.cloneNode(true);
        row0.appendChild(newRow)
    } else {
        alert("最多可添加6行");
    }
}
</script>