<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseInfoDTO" %>
<%@ page import="com.sino.ams.system.house.dto.AmsItemFilesDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-15
  Time: 13:30:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>房屋详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsHouseInfoDTO housedto = (AmsHouseInfoDTO) request.getAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO);
    String action = parser.getParameter("act");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String  barcode3= userAccount.getCompanyCode()+'-';
//    String  barcodeNo2 = "-";
//    String  barcodeNo3 =
    System.out.println("(housedto.getIsRent()"+housedto.getIsRent());
%>
<script type="text/javascript">
    printTitleBar("房屋详细信息")
</script>
<body onload="initPage();">

<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet" name="mainForm" method="post">
<table border="0" width="100%">
<tr>
     <td align="center" colspan="2"  width="45%" id="barcodeNo11" style="color:red;visibility:hidden">对不起，该房屋条码已存在！</td>
</tr>
<tr>
    <td align="right" width="15%">房屋条码：</td>
    <td width="35%" align="left">
  <input name="barcode"  type="text"
<%
    if(!housedto.getSystemId().equals("")){
%>
    readonly  class = "readonlyInput"
<%
    }else{
%>
    class="noEmptyInput"
<%
    }
%>
        value="<%=housedto.getBarcode()%>" style="width:46%"
<%
    if(housedto.getSystemId().equals("")){
%>
         onblur="do_verifybarcodeNo();"
<%
    }
%>
          ><a  class="linka" style="cursor:'hand'" onclick="do_SelectbarcodeNo();">
<%
    if(housedto.getSystemId().equals("")){
%>
        […]
<%
    }
%>

    </a></td>
</tr>
<tr>
    <td align="right" width="15%">房屋名称：</td>
    <td width="35%" align="left"><input name="itemName" type="text" readonly class="noEmptyInput" value="<%=housedto.getItemName()%>" style="width:46%" onclick="do_SelectSystemItem();"><a  class="linka" style="cursor:'hand'" onclick="do_SelectSystemItem();">[…]</a></td>
</tr>

<tr>
    <td align="right" width="15%">房屋型号：</td>
    <td width="35%" align="left"><input name="itemSpec" type="text" readonly class="readonlyInput" value="<%=housedto.getItemSpec()%>" style="width:46%" onclick="do_SelectSystemItem();"></td>
</tr>
<tr>
    <td align="right" width="15%">所在地址：</td>
    <td width="35%" align="left"><input type="text" name="houseAddress" style="width:46%"  value="<%=housedto.getHouseAddress()%>">
    </td>
</tr>
<tr>
    <td align="right" width="15%">楼层：</td>
    <td width="35%" align="left"><input type="text" name="floorNumber"  style="width:46%" value="<%=housedto.getFloorNumber()%>" onblur="do_verify1();">
    </td>
</tr>
<tr>
    <td align="right" width="15%">房屋编号：</td>
    <td width="35%" align="left"><input name="houseNo" type="text"  value="<%=housedto.getHouseNo()%>" style="width:46%"></td>
</tr>
<tr>
    <td align="right" width="15%">房屋面积：</td>
    <td width="35%"><input name="houseArea" type="text"  value="<%=housedto.getHouseArea()%>"  style="width:46%" onblur="do_verify2();"></td>
</tr>
<tr>
    <td align="right" width="15%">面积单位：</td>
    <td width="35%"><select type="text" style="width:46%" name="areaUnit"><%=request.getAttribute(WebAttrConstant.AREA_UNIT_OPTION)%></select></td>
</tr>
<%
   if(!housedto.getIsRent().equals("N")){
    if(housedto.getSystemId().equals("")){
      String yesChecked = "checked";
	  String noChecked = "";
	  if(housedto.getIsRent().equals("N")){
		yesChecked = "";
		noChecked = "checked";
	}
%>
    <tr>
        <td height="31" align="right">是否租赁资产：</td>
        <td ><input type="radio" name="isRent" id="yes1"  value="Y" <%=yesChecked%>><label for="yes1">是</label>
            <input type="radio" name="isRent" id="no1" value="N"  <%=noChecked%>><label for="no1">否</label></td>

<%
    }

%>
<tr>
    <td align="right" width="5%">租金：</td>
    <td width="35%"><input name="rental" type="text"  value="<%=housedto.getRental()%>"   style="width:46%" onblur="do_verify3();"></td>
</tr>
<tr>
    <td align="right" width="5%">租金单位：</td>
    <td width="35%"><input type="text" style="width:46%" name="moneyUnit" value="<%=housedto.getMoneyUnit()%>"><a class="linka" style="cursor:'hand'" onclick="do_selectNameAddress();"></a>
    </td>
</tr>
<tr>
    <td align="right" width="15%">出租人：</td>
    <td width="35%" align="left"><input name="rentPerson" type="text" class="noEmptyInput" value="<%=housedto.getRentPerson()%>"  style="width:46%"></td>
</tr>
<tr>
    <td align="right" width="15%">租赁日期：</td>
    <td width="35%"><input type="text" name="rentDate"  value="<%=housedto.getRentDate()%>" style="width:46%" title="点击选择日期" readonly  onclick="gfPop.fPopCalendar(rentDate)"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(rentDate)"></td>
</tr>

<tr>
    <td align="right">截至日期：</td>
    <td width="35%">
        <input type="text" name="endDate"
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
        style="width:46%" title="点击选择日期" readonly  onclick="gfPop.fEndPop(rentDate,endDate);"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(rentDate,endDate);">

    </td>
</tr>
<tr>
    <td align="right" width="15%">付款方式：</td>
    <td width="35%"><select type="text" name="payType" style="width:46%" ><%=request.getAttribute(WebAttrConstant.PAY_TYPE_OPTION)%></select>
    </td>
</tr>

<%
    DTOSet files = (DTOSet) request.getAttribute(WebAttrConstant.ATTACH_FILES);
    if (files != null && !files.isEmpty()) {
%>
        <tr><td width="25%" align="right" height="22">相关文件：</td>
            <td width="50%" align="left" height="22" colspan="3">
<%
    int fileCount = files.getSize();
    for (int i = 0; i < fileCount; i++) {
        AmsItemFilesDTO file = (AmsItemFilesDTO) files.getDTO(i);
%>
                <a href="" onClick="do_DownLoad(<%=file.getSystemId()%>); return false;"><%=file.getFileDesc()%></a>
<%
        }
%>
            </td>
        </tr>
<%
    }
%>
<%
    }
%>

<tr>
    <td align="center"></td>
    <td> &nbsp;&nbsp;&nbsp;
        <img src="/images/eam_images/save.jpg" alt="保存设备信息"  onClick="do_save(); return false;"> &nbsp;
<%
  if(!housedto.getIsRent().equals("N")){
%>
        <img src="/images/eam_images/attach.jpg" alt="附加文件" onClick="do_AttachFiles()">&nbsp;
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

<input type= "hidden" name = "saved"  >

<input type="hidden" name="isAttachFile" value="<%=parser.getAttribute(WebAttrConstant.ATTACH_FILE_ATTR)%>">
</form>
</body>
<iframe name="downTarget" src="" style="display:none"></iframe>

<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script type="text/javascript">
function do_save() {
    var barcode1 = "<%=barcode3%>";
    var barcode = document.mainForm.barcode.value;
    //    alert(barcode);
    if (barcode == barcode1) {
        alert("请输入房屋条码信息");
        document.mainForm.barcode.style.color ="red";
        document.mainForm.barcode.focus();
    } else {
        var fieldNames = "barcode;itemSpec";
        var fieldLabels = "房屋条码;房屋型号";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        var systemId = mainForm.systemId.value;
        var has = mainForm.has.value;
        if (isValid) {
//             mainForm.isAttachFile.value = "true";
//            mainForm.saved.value ="H";
            with (mainForm) {
                if (has.value == "") {
                    act.value = "<%=WebActionConstant.CREATE_ACTION%>";
                } else {
                    act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                }
                action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
                mainForm.target = "_self";
                submit();
            }
        }
    }
}

function do_Back() {
    with (mainForm) {
        act.value = "";
        action = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
        mainForm.target = "_self";
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
    if (mainForm.has.value == "") {
        var fieldNames = "barcode;itemSpec";
        var fieldLabels = "房屋条码;房屋型号";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        if (isValid) {
            mainForm.isAttachFile.value = "true";
            do_save();
        }
    } else {
        var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.NEW_ACTION%>&barcode=<%=housedto.getBarcode()%>"+ "&type=HOUSE";
        var style = "height=300,width=400,top=200,left=400";
        var winName = "uploadWin";
        window.open(url, winName, style);
    }
}

function do_DownLoad(systemId) {
    var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.DOWNLOAD_ACTION%>&systemId=" + systemId;
    mainForm.action = url;
    mainForm.target = "downTarget";
    mainForm.submit();
}

function initPage() {
    var attachFile = mainForm.isAttachFile.value;
    if (attachFile == "true") {
        var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.NEW_ACTION%>&barcode=<%=housedto.getBarcode()%>"+"&type=HOUSE";      //保存操作
        var style = "height=300,width=400,top=200,left=400";
        var winName = "uploadWin";
        window.open(url, winName, style);
    }
}


//function do_verify(obj) {
//    var barcode = obj.value ;
////    alert(barcode);
//    if (barcode.indexOf("-") != -1) {
//    } else {
//        alert("请输入带‘-’的数字");
//        obj.focus();
//        return false;
//    }
//}


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

function do_verify3() {
    var fieldNames = "rental";
    var fieldLabels = "租金";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
//        alert("租金必须为正数字！");
    }
}

function do_verify9() {
    var barcode1 = "<%=barcode3%>";
    var barcode = document.mainForm.barcode.value;
    var barcode2 = barcode.substring(0, 5);
    //    alert(barcodeNo1) ;
    //    alert(barcodeNo2);
    if (barcode2 == barcode1) {
        var barcode3 = barcode.substring(5);
        //        alert(barcodeNo3);
        //        alert(barcodeNo3.length);
        if (barcode3.length == 8) {
//            alert(barcodeNo3.length);
            for (var i = 0; i < barcode3.length; i++)
            {
                if (barcode3.charAt(i) < '0' || barcode3.charAt(i) > '9')
                {
                    alert("在" + barcode1 + "后输入8位只能是“数字”！");
                    document.mainForm.barcode.focus();
                    return false;
                }
            }

            //                 alert(isNaN(barcodeNo3));
            //            if (isNaN(barcodeNo3)) {
            //
            //            } else {
            //                alert("请在"+barcodeNo1+"后输入8位“数字”！");
            ////                document.mainForm.barcode.focus();
            //                return false;
            //            }
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

//           var fieldNames = "barcode";
//    var fieldLabels = "房屋条码";
//    if (!formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
//    } else {
//        var fieldNames = "barcode";
//        var fieldLabels = "0$房屋条码7$";
//        if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
//        }
//    }
var xmlHttp;
function do_verifybarcodeNo() {
    do_verify9()
    var url = "";
    var segment1 = document.mainForm.barcode.value;
    createXMLHttpRequest();
    if (document.mainForm.barcode.value) {
        url = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?act=verifyBarcodeNo&barcode=" + document.mainForm.barcode.value;
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
                document.getElementById("barcodeNo11").style.visibility = "visible"
                document.mainForm.barcode.focus();
            } else {
                document.mainForm.isExist.value = 'N';
                document.getElementById("barcodeNo11").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

function do_SelectbarcodeNo(){
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


</script>