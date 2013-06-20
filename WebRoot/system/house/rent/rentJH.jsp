<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseUsesDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  User: Zyun
  Date: 2007-10-15
  Time: 13:30:49
--%>
<html>
<head><title>租赁房屋土地维护</title>
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
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
</head>
<%
    AmsHouseUsesDTO dto = (AmsHouseUsesDTO) request.getAttribute("AMSHOUSEUSESDTO");
    if (dto == null) {
        dto = new AmsHouseUsesDTO();
    }
    AmsHouseInfoDTO housedto = (AmsHouseInfoDTO) request.getAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO);
    String action = dto.getAct();
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String barcode1 = userAccount.getCompanyCode() + '-';
    System.out.println("(housedto.getIsRent()" + housedto.getIsRent());
%>
<script type="text/javascript">
    printTitleBar("租赁房屋土地维护-基站-房屋")
</script>
<body  topMargin=0 leftMargin=0>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet" name="mainForm" method="post">
<table border="1" width="100%">
<tr>
    <td align="right" width="15%">条码：</td>
    <td width="35%" align="left">
        <input name="barcode" type="text"  readonly class="readonlyInput" value="<%=housedto.getBarcode()%>" style="width:80%">
    </td>
    <td align="right" width="15%">总租金：</td>
    <td width="35%" align="left"><input name="rentFee" type="text" class="noEmptyInput" value="<%=housedto.getRentFee()%>"   style="width:80%" onblur="do_verify3();"></td>
</tr>
<tr>
    <td align="right" width="15%">名称：</td>
    <td width="35%" align="left"><input name="itemName" type="text" readonly class="noEmptyInput"   value="<%=housedto.getItemName()%>" style="width:80%"><a
            class="linka" style="cursor:'hand'"  onclick="do_SelectSystemItem();">[…]</a>
    </td>
    <td align="right" width="15%">出租单位：</td>
    <td width="35%" align="left"><input name="rentUnit" type="text"  value="<%=housedto.getRentUnit()%>" class="noEmptyInput"  style="width:80%" ></td>
</tr>
<tr>
    <td align="right" width="15%">型号：</td>
    <td width="35%" align="left"><input name="itemSpec" type="text" readonly class="readonlyInput"  value="<%=housedto.getItemSpec()%>" style="width:80%"></td>
    <%
        //   if(housedto.getSystemId().equals("")){
        String yesChecked = "checked";
        String noChecked = "";
        if (housedto.getIsRent().equals("N")) {
            yesChecked = "";
            noChecked = "checked";
        }
    %>
    <td align="right">联系人：</td>
    <td ><input name="contactPerson" type="text"  value="<%=housedto.getContactPerson()%>"  style="width:80%" ></td>
</tr>
   <td align="right" width="15%">房产证号：</td>
    <td width="35%" align="left"><input type="text" name="houseCertificateNo" style="width:80%"
                                        value="<%=housedto.getHouseCertificateNo()%>">
    </td>
     <td align="right" width="15%">联系电话：</td>
    <td width="35%" align="left"><input name="contactPhone"  type="text"  value="<%=housedto.getContactPhone()%>"   style="width:80%" >
    </td>
<tr>
    <td align="right" width="15%">房屋面积(㎡)：</td>
    <td width="35%" align="left"><input name="houseArea" type="text" value="<%=housedto.getHouseArea()%>" style="width:80%"   onblur="do_verify2();"></td>
    <td align="right" width="15%" >租赁起始日期：</td>
    <td width="35%" ><input type="text" name="rentStartDate" class="noEmptyInput" value="<%=housedto.getRentStartDate()%>" style="width:80%" title="点击选择日期" readonly  onclick="gfPop.fPopCalendar(rentStartDate)"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(rentStartDate)"></td>
</tr>
<tr>
    <td align="right" width="15%">所在地点：</td>
    <td width="35%" align="left"><input type="text" name="houseAddress" style="width:80%" class="noEmptyInput" readonly
                                        value="<%=housedto.getHouseAddress()%>"><a class="linka" style="cursor:'hand'"
                                                                                   onclick="do_selectAddress();">[…]</a>
    </td>
    <td align="right" width="15%" >租赁截至日期：</td>
    <td width="35%" ><input type="text" name="rentEndDate"
<%
  if (StrUtil.isEmpty(housedto.getRentEndDate())) {
%>
       value=""
<%
  } else {
%>
       value="<%=housedto.getRentEndDate()%>"
<%
  }
%>
        style="width:80%" title="点击选择日期" readonly class="noEmptyInput" onclick="gfPop.fEndPop(rentStartDate,rentEndDate);"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(rentStartDate,rentEndDate);">

    </td>
</tr>
<tr>   
    <td align="right" width="15%">合同号：</td>
    <td width="35%" align="left"><input name="contractName" type="text" value="<%=housedto.getContractName()%>" style="width:80%">
    </td>
    <td align="right" width="15%"></td>
    <td width="35%" align="left"></td>
</tr>
<tr>
    <td width="15%" align="right" height="22"><font color="" size="2"><b>相关文件：</b></font></td>
    <td width="35%"><select size="2" multiple style="width:80%" name="affix" id="affix"
                            ondblclick="onaffix(); return false;">
        <%=request.getAttribute(WebAttrConstant.ATTACH_FILES)%>
    </select><img src="/images/eam_images/attach.jpg" alt="附加文件" onClick="do_AttachFiles()">&nbsp;<img
            src="/images/eam_images/delete.jpg" alt="删除文件" onclick="remove_AttachFiles()"></td>
    <td align="right" id="rent9" >备注：</td>
    <td width="35%" id="rent10" rowspan="2"><textarea name= "hremark" cols="30" rows="4"><%=housedto.getHremark()%></textarea></td>
</tr>
</table>
    <table width="100%" border="0">
        <tr>
            <td align="center" width="15%"></td>
            <td width="35%">
              <%if(StrUtil.isEmpty(housedto.getLook())){ %>
                <img src="/images/eam_images/save.jpg" alt="保存信息" onclick="do_save();"> &nbsp;
             <%}%>
                <img src="/images/eam_images/close.jpg" alt="返回" onclick="do_Back();return false;"></td>
        </tr>
    </table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="isExist">
<input type="hidden" name="itemCode" value="<%=housedto.getItemCode()%>">
<input type="hidden" name="systemId" value="<%=housedto.getSystemId()%>">
<input type="hidden" name="has" value="<%=housedto.getHas()%>">
<input type="hidden" name="rentId" value="<%=housedto.getRentId()%>">
<input type="hidden" name ="officeUsage" value="基站">
<input type="hidden" name ="officeType" value="房屋">
<input type="hidden" name="addressId">
<input type="hidden" name="saved">
<input type="hidden" name="isAttachFile" value="<%=request.getAttribute(WebAttrConstant.ATTACH_FILE_ATTR)%>">
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
        var fieldNames = "itemName;rentFee;rentUnit;rentStartDate;rentEndDate;houseAddress";
        var fieldLabels = "名称;总租金;出租单位;租赁起始日期;租赁截至日期;所在地点";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        var systemId = mainForm.systemId.value;
        if(isValid){
        var has = mainForm.has.value;
            selectAll("affix");
//                if (mainForm.barcode.value == "") {
                    <%--mainForm.act.value = "<%=WebActionConstant.PASS_ACTION%>";--%>
//                } else {

                    mainForm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
//                }

                mainForm.action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet";
                window.top.opener.do_search();
                mainForm.submit();
           }
}

function do_temp() {
    var barcode = document.mainForm.barcode.value;
        var fieldNames = "barcode;itemSpec";
        var fieldLabels = "条码;型号";
        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        var systemId = mainForm.systemId.value;
        var has = mainForm.has.value;
            selectAll("affix");
           mainForm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
           mainForm.action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?temp=Y";
           window.top.opener.do_search();
//           window.top.opener.location.reload();
            mainForm.submit();

//         window.top.main.document.do_search();
//         window.top.main.document.do_search();

//	     window.top.opener.document.forms[0].submit();
        <%--}else{--%>
       <%--if (confirm("改变“基站类型”或“土地房屋类型”将导致数据丢失，确定吗？")) {--%>
           <%--var barcode = document.mainForm.barcode.value;--%>
           <%--var fieldNames = "barcode;itemSpec";--%>
           <%--var fieldLabels = "条码;型号";--%>
           <%--var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);--%>
           <%--var systemId = mainForm.systemId.value;--%>
           <%--var has = mainForm.has.value;--%>
           <%--selectAll("affix");--%>
           <%--mainForm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";--%>
           <%--mainForm.action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet?temp=Y";--%>
           <%--mainForm.submit();--%>
       <%--}--%>
//      }
}


function  changed(){
    var retVal = false;
    var bts = mainForm.bts.value;
    var groupId = mainForm.category.value;
    if((bts=="基站")&&(groupId=="房屋")){
        retVal = true;
    }
    return retVal;
}

function do_verifyLandArea() {
    var fieldNames = "occupyArea";
    var fieldLabels = "土地面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("面积必须为正数字！");
    }
}


function do_select() {
    selectAll("affix");
}
function do_Back() {
//    with (mainForm) {
//        act.value = "";
//        action = "/servlet/com.sino.ams.system.house.servlet.RentHouseInfoServlet";
//        submit();
//    }
      window.close();
}


function do_SelectSystemItem() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_HOUSE%>";
    <%--var lookUpName = "<%=LookUpConstant.LOOK_UP_SYS_ITEM%>";--%>
    var dialogWidth = 35;
    var dialogHeight = 30;
    //LOOKUP传参数 必须和DTO中一致
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (users) {
        var user = null;
//        for (var i = 0; i < users.length; i++) {
            user = users[0];
            dto2Frm(user, "mainForm");
//        }
    }
}


function do_AttachFiles() {
    //    if (mainForm.systemId.value == "") {
    //        var fieldNames = "barcode;itemSpec";
    //        var fieldLabels = "条码;型号";
    //        var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
    //        if (isValid) {
    //            mainForm.isAttachFile.value = "true";
    //            do_save();
    //        }
    //    } else {
<%--var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.NEW_ACTION%>&barcode=<%=housedto.getBarcode()%>"+ "&type=HOUSE";--%>
    //        var style = "height=300,width=400,top=200,left=400";
    //        var winName = "uploadWin";
    //        window.open(url, winName, style);
    //    }
    if (document.mainForm.barcode.value !== "") {
        //        alert(document.mainForm.barcode.value);
        var affix = document.getElementById("affix");
        var url = "/system/house/uploadItemFile.jsp?barcode=" + document.mainForm.barcode.value;
        var retVal = window.showModalDialog(url, "", "dialogWidth:20;dialogHeight:13;center:yes;status:no;scrollbars:no;help:no");
        if (retVal) {
            //            alert(retVal);
            var arr = retVal.split("$");
            //            alert(arr[0]);
            //            alert(arr[1]);
            if (cf1(arr[1])) {
                //                alert(999999999999999999999999999999999999999);
                var option = new Option(arr[0], retVal);
                affix.add(option)
            }
            //            var option = new Option(arr[0], retVal);
            //            affix.add(option)
        }
    } else {
        alert("请先“生成”条码！");
    }
}

function remove_AttachFiles() {
    //   var affix = document.getElementById("affix");
    dropSelectedOption("affix");
}

function do_DownLoad(filePath) {

    var url = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet?act=<%=WebActionConstant.DOWNLOAD_ACTION%>&filePath=" + filePath;
    mainForm.action = url;
    //    mainForm.target = "downTarget";
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
    var fieldLabels = "面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("面积必须为正数字！");
    }
}

function do_verify6() {
    var fieldNames = "businessArea";
    var fieldLabels = "营业用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("面积必须为正数字！");
    }
}

function do_verify4() {
    var fieldNames = "produceHosuseArea";
    var fieldLabels = "生产机房用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("面积必须为正数字！");
    }
}

function do_verify5() {
    var fieldNames = "produceBaseArea";
    var fieldLabels = "生产基站用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("面积必须为正数字！");
    }
}

function do_verify7() {
    var fieldNames = "officeArea";
    var fieldLabels = "办公用面积";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("面积必须为正数字！");
    }
}
function do_verify3() {
    var fieldNames = "rentFee";
    var fieldLabels = "总租金";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
        //        alert("租金必须为正数字！");
    }
}

function do_verify9() {
    var barcode1 = "<%=barcode1%>";
    var barcode = document.mainForm.barcode.value;
    var barcode2 = barcode.substring(0, 5);
    //    alert(barcode1) ;
    //    alert(barcode2);
    if (barcode2 == barcode1) {
        var barcode3 = barcode.substring(5);
        //        alert(barcode3);
        //        alert(barcode3.length);
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

            //                 alert(isNaN(barcode3));
            //            if (isNaN(barcode3)) {
            //
            //            } else {
            //                alert("请在"+barcode1+"后输入8位“数字”！");
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
//    var fieldLabels = "条码";
//    if (!formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
//    } else {
//        var fieldNames = "barcode";
//        var fieldLabels = "0$条码7$";
//        if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
//        }
//    }
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
            document.getElementById("rent").style.visibility = "visible";
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
            document.getElementById("rent").style.visibility = "hidden"
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
    //      alert(filePath[0]);
    //      alert(filePath[1]);
        if(filePath[1]){
    do_DownLoad(filePath[1]);
   }
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

<%--if (document.mainForm.barcode.value == "") {
    alert("请先生成条码");
}
else {
    //现有行
    var rowCount = tb_order.rows.length - 1 ;

    if (rowCount < 4) {
        //添加一行
        var newTr = tb_order.insertRow(rowCount);

        //添加两列
        var newTd0 = newTr.insertCell();
        var newTd1 = newTr.insertCell();
        var newTd2 = newTr.insertCell();
        var newTd3 = newTr.insertCell();

        //设置列内容和属性
        newTd0.width = "16%";
        newTd1.width = "16%";
        newTd2.width = "16%";
        newTd3.width = "16%";

        newTd0.innerHTML = /*<OPTION VALUE = PO_NUM@@@PO_HEADER_ID >*/
//                        "<select class = select2 name = houseUsage id = houseUsage" + rowCount +
//                               "  ><OPTION>--请选择--</OPTION><OPTION>办公</OPTION></select>";
               "<select  class = select2 name = houseUsage id = houseUsage" + rowCount +"><%=houseUsage%></select>";
        newTd1.innerHTML = '<input style="width:100%"  type = "text" name = "houseArea" id = "houseArea' + rowCount + '" value = "" class = input2>';
        newTd2.innerHTML = '<input  style="width:100%" type = "text" name = "areaUnit" id = "areaUnit' + rowCount + '" value = "" class = input2>';
        newTd3.innerHTML = '<input  style="width:100%" type = "text" name = "remark" id = "remark' + rowCount + '" value = "" class = input2>';
    } else {
        alert("最多可添加4行");
    }
    //更新订单列表
  //  do_getPoListOption(rowCount);
}--%>
}
var focusRow = 0;
function do_getPoListOption(i) {

    focusRow = i;
    requestAjax("", do_setPoListOption, null, null);

}
</script>