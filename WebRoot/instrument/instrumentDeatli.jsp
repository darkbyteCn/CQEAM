<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-10-19
  Time: 10:36:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>仪器仪表维护详细信息页面</title>
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
<jsp:include page="/message/MessageProcess"/>
<body topMargin=0 leftMargin=0>
<script type="text/javascript">
    printTitleBar("仪器仪表维护详细信息页面")
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
    String action = parser.getParameter("act");
    SfUserDTO sfUser = (SfUserDTO) SessionUtil.getUserAccount(request);


%>
<table border="0">
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet" name="mainForm" method="post">
  <tr>
        <td align="center" colspan="2"  width="45%" id="barcodeNo11" style="color:red;visibility:hidden">对不起，该仪器仪表条码已存在！</td>
                </tr>
<td align="right" width="15%">仪器仪表条码:</td>
<%--<td width="35%">--%>
    <%--<input name="barcode" type="text"--%>
    <%--<%--%>
        <%--if(!dto.getSystemid().equals("")){--%>
    <%--%>--%>
           <%--readonly class="readonlyInput"--%>
    <%--<%--%>
        <%--}else{--%>
    <%--%>--%>
           <%--class="noEmptyInput" readonly--%>
    <%--<%--%>
        <%--}--%>
    <%--%>--%>
           <%--value="<%=dto.getBarcode()%>" style="width:55%">--%>
    <%--<%--%>
        <%--if (dto.getSystemid().equals("")) {--%>
    <%--%>--%>
    <%--<input type="button" value="生成" onClick="addBarcode();">--%>
    <%--<%--%>
        <%--}--%>
    <%--%>--%>
<%--</td>--%>
              <td width="35%"><input name="barcode"  type="text" id="barcode" value="<%=dto.getBarcode()%>" class='noEmptyInput'
            <% if(dto.getSystemid().equals("")){%>
               onblur="do_verifybarcodeNo();"
              <%} else{%>
              readonly
             <%}%>
                                   style="width:55%">
            </td>
<tr>
    <td align="right" width="15%">仪器仪表类型:</td>
    <td width="35%"><input type="text" name="" class="noEmptyInput" readonly value="仪器仪表" style="width:55%">

    </td>
</tr>
<tr>
    <td align="right" width="15%">仪器仪表名称:</td>
    <td width="35%"><input type="text" name="itemName" value="<%=dto.getItemName()%>"
    <%
      if (dto.getSystemid().equals("")) {
    %>
                           class="noEmptyInput" readonly
    <%
        }else{
    %>
                           class="readonlyInput" readonly
    <%
        }
    %>
                           style="width:55%">
       <%
            if (StrUtil.isEmpty(dto.getSystemid())) {
        %>
        <a class="linka" style="cursor:'hand'" onclick="selectSysitem();">[…]</a>
        <%
            }
        %>
    </td>
</tr>
<tr>
    <td align="right" width="15%">规格型号:</td>
    <td width="35%"><input name="itemSpec" type="text"
    <%
      if (dto.getBarcode().equals("")) {
    %>
                           class="readonlyInput" readonly
    <%
        }else{
    %>
                           class="readonlyInput" readonly
    <%
        }
    %>
                           value="<%=dto.getItemSpec()%>"
                           style="width:55%"></td>
</tr>
<tr>
    <td align="right" width="15%">供应商:</td>
    <td width="35%"><input name="itemQty" type="hidden" value="1" >
        <input name="vendorName" type="text" class="readonlyInput" readonly
                           value="<%=dto.getVendorName()%>" style="width:55%"></td>
</tr>
<tr>
    <td align="right" width="15%">数量:</td>
    <td width="35%"><input name="itemQty" type="text" class="noEmptyInput" value="<%=dto.getItemQty()%>" style="width:55%" onblur="do_verify();"></td>
</tr>
<tr>
    <td align="right" width="15%">仪器仪表用途:</td>
    <td width="35%"><textarea rows="5" cols="35" onblur="do_verify1()" name="instruUsage"><%=dto.getInstruUsage()%></textarea></td>
</tr>
<tr>
    <td align="right" width="15%">责任部门:</td>
    <td width="35%"><input name="responsibilityDept" type="text" class="noEmptyInput" readonly
                           value="<%=dto.getResponsibilityDept()%>" style="width:55%"><a class="linka" style="cursor:'hand'"
                                                                                         onclick="do_selectDept();">[…]</a></td>
</tr>
<tr>
    <td align="right" width="15%">责任人:</td>
    <td width="35%"><input name="username" type="text" class="noEmptyInput" readonly value="<%=dto.getResponsibilityName()%>"
                           style="width:55%">
    </td>
</tr>
<tr>
    <td align="right" width="15%">使用部门:</td>
    <td width="35%"><input name="maintainDeptName" type="text" class="noEmptyInput" readonly
                           value="<%=dto.getMaintainDeptName()%>" style="width:55%"><a class="linka" style="cursor:'hand'"
                                                                                       onclick="lookDept();">[…]</a></td>
</tr>
<%--<tr>--%>
<%--<td align="right" width="5%">仪器仪表使用人:</td>--%>
<%--<td width="35%"><input name="maintainUser" readonly class="readonlyInput" type="text"  value="<%=dto.getMaintainUser()%>" style="width:55%"><a class="linka" style="cursor:'hand'"  onclick="do_selectUserName();">[…]</a></td>--%>
<%--</tr>--%>

<tr>
<td align="right" width="5%">仪器仪表使用人:</td>
<td width="35%"><input name="maintainUser"   type="text" onblur="do_verify2()" value="<%=dto.getMaintainUser()%>" style="width:55%"></td>
</tr>
<tr>
    <td align="right" width="15%">使用地点:</td>
    <td width="35%"><input name="addressloc" type="text" class="noEmptyInput" readonly
                           value="<%=dto.getAddressloc()%>" style="width:55%"><a class="linka" style="cursor:'hand'"
                                                                                 onclick="lookAddressId();">[…]</a></td>
</tr>
<tr>
    <td align="right" width="15%">单价:</td>
    <td width="35%"><input name="unitPrice" type="text" value="<%=dto.getUnitPrice()%>" style="width:55%" onblur="do_verifyUnitP();"></td>
</tr>
<%

//           if(!StrUtil.isEmpty(dto.getSystemid())){

%>
<tr>
    <td align="right" width="15%">仪器仪表性能:</td>
    <td width="35%"><input name="remark" type="text" onblur="do_verify3()" value="<%=dto.getRemark()%>" style="width:55%"></td>
</tr>
<tr>
    <td align="right" width="15%">仪器仪表状态:</td>
    <td width="35%"><select style="width:55%" name="itemStatus">
        <option value="NORMAL" <%=dto.getItemStatus().equals("NORMAL") ? "selected" : ""%>>正常</option>
        <option value="BORROW" <%=dto.getItemStatus().equals("BORROW") ? "selected" : ""%>>借用</option>
        <option value="SEND_REPAIR" <%=dto.getItemStatus().equals("SEND_REPAIR") ? "selected" : ""%>>送修</option>
        <option value="DISCARDED" <%=dto.getItemStatus().equals("DISCARDED") ? "selected" : ""%>>报废</option>
    </select></td>
</tr>
<%

//              }

%>
<tr>
    <td align="right"></td>
    <td align="left">
        <img src="/images/eam_images/save.jpg" alt="保存仪器" onClick="do_save(); return false;">
        <img src="/images/eam_images/cancel.jpg" alt="取消" onclick="do_concel();return false;"></td>
</tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="itemCategory" value="INSTRUMENT">
<input type="hidden" name="userId" value="<%=dto.getUserId()%>">
<input type="hidden" name="userId2" value="<%=dto.getUserId2()%>">
<%--<input type="hidden" name="barcode" value="<%=dto.getBarcode()%>">--%>
<input type="hidden" name="itemCode" value="<%=dto.getItemCode()%>">
<input type="hidden" name="vendorId" value="<%=dto.getVendorId()%>">
<input type="hidden" name="addressId" value="<%=dto.getAddressId()%>">
<input type="hidden" name="systemid" value="<%=dto.getSystemid()%>">
<input type="hidden" name="maintainDept" value="<%=dto.getMaintainDept()%>">
<input type="hidden" name="groupId" value="<%=dto.getGroupId()%>">
<input type="hidden" name="deptId" value="<%=dto.getDeptId()%>">
</form>
</body>
</html>
<script type="text/javascript">
function do_savePlan() {
    var fieldNames = "barcode;itemName;responsibilityDept;username;maintainDeptName;addressloc";
    var fieldLabels = "条码;仪器仪表名称;责任部门;责任人;使用部门;使用地点";
    var validateType = EMPTY_VALIDATE;
    var isValid = formValidate(fieldNames, fieldLabels, validateType);
    if (isValid) {
        if (mainForm.systemid.value == "") {
            mainForm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
        }
        else {
            mainForm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        }
        mainForm.submit();
    }

}
var xmlHttp;
function do_barcode() {
    /*   mainForm.act.value = "barcode";
    mainForm.submit();*/
}
function do_concel() {
    with (mainForm) {
        window.close();
        act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        submit();
    }
}
function do_selectName() {
    //alert(mainForm.itemCategory.value);
    var lookUpName = "<%=LookUpConstant.LOOK_UP_SYS_ITEM%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userPara = "itemCategory=" + mainForm.itemCategory.value;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
    }
}
function do_selectVerdorName() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_PURVEY%>";
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

function do_save() {
    if (isBar()){
        var fieldNames = "barcode;itemName;itemQty;responsibilityDept;username;maintainDeptName;addressloc";
        var fieldLabels = "仪器仪表条码;仪器仪表名称;数量;责任部门;责任人;使用部门;使用地点";
        var validateType = EMPTY_VALIDATE;
        var isValid = formValidate(fieldNames, fieldLabels, validateType);
        if (isValid) {
            if (mainForm.systemid.value == "") {
                mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            }
            else {
                mainForm.act.value = "<%=AMSActionConstant.INSTEAD_ACTION%>";
            }
            mainForm.submit();
        }
    }
}

function do_selectDept() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPUSER%>";
    var dialogWidth = 48;
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
//    alert(document.mainForm.deptId.value);
}

function selectSysitem() {
    //alert(mainForm.itemCategory.value);
    var lookUpName = "<%=LookUpConstant.LOOK_UP_INSTR_ITEM%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var userPara = "itemCategory=" + mainForm.itemCategory.value;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
    }
}

function addBarcode() {
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
//
//function handleReadyStateChange1() {
//    if (xmlHttp.readyState == 4) {
//        if (xmlHttp.status == 200) {
//            unescape(xmlHttp.responseText);
//            document.mainForm.barcode.value = xmlHttp.responseText;
//        } else {
//            alert(xmlHttp.status);
//        }
//    }
//}

function do_verify() {
    var fieldNames = "itemQty";
    var fieldLabels = "数量";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
    }
}

function do_verify1() {
    var fieldNames = "instruUsage";
    var fieldLabels = "0$仪表用途$256";
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
}

function do_verify2() {
    var fieldNames = "maintainUser";
    var fieldLabels = "0$仪表使用人$256";
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
}

function do_verify3() {
    var fieldNames = "remark";
    var fieldLabels = "0$仪表性能$255";
    if (!formValidate(fieldNames, fieldLabels, LENGTH_VALIDATE)) {
    }
}

function do_verifyUnitP() {
    var fieldNames = "unitPrice";
    var fieldLabels = "单价";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}

function do_selectUserName() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_USER1%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    //LOOKUP传参数 必须和DTO中一致
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    //       alert(users);
    if (users) {
        var user = null;
        //        for (var i = 0; i < users.length; i++) {
        //            user = users[i];
        //            dto2Frm(user, "mainForm");
        document.mainForm.maintainUser.value = users[0].username;
        document.mainForm.userId2.value = users[0].userId;
        //        }
    } else {
        document.mainForm.maintainUser.value = "";
        document.mainForm.userId2.value = "";
    }

}

function lookDept() {          //查找使用部门
    var lookUpName = "<%=LookUpConstant.LOOK_UP_DEPT%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var userPara = "organizationId=<%=sfUser.getOrganizationId()%>";
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (projects) {
        var user = null;
        for (var i = 0; i < projects.length; i++) {
            mainForm.maintainDept.value = projects[0].groupId;
            mainForm.maintainDeptName.value = projects[0].groupname;
        }
    }
    //    alert(mainForm.vendorCode.value);
}


function lookAddressId() {    //查找地点
    <%--var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";--%>
    var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (projects) {
        var user = null;
        for (var i = 0; i < projects.length; i++) {

            mainForm.addressId.value = projects[0].addressId;
            mainForm.addressloc.value = projects[0].workorderObjectName;
//            alert(projects[0].addressId);
        }
    }
    //    alert(mainForm.vendorCode.value);
}


//var xmlHttp;
function do_verifybarcodeNo() {
    //if () {
        var url = "";
        var segment1 = document.mainForm.barcode.value;
        createXMLHttpRequest();
        if (document.mainForm.barcode.value) {
            url = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet?act=verifyBarcode&barcode=" + document.mainForm.barcode.value;
            xmlHttp.onreadystatechange = handleReadyStateChange1;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
   // }
}
//
//function isBar() {
//     var str = document.mainForm.barcode.value;
//     var patrn = /^(\d{4}\-)?\d{8}$/;
//     if (patrn.exec(str)) {
//         return true;
//     } else{
//         alert("请输入正确的条码！");
//         document.mainForm.barcode.focus();
//         document.mainForm.barcode.style.color = "red";
//         return false;
//     }
// }


function isBar() {
     var str = document.mainForm.barcode.value;
     var patrn = /^(\d{4}\-YB)?\d{6}$/;
     if (patrn.exec(str)) {
         return true;
     } else{
         alert("请输入“四位公司代码+-YB+六位数字”的条码！例：“4110-YB123456”。");
         document.mainForm.barcode.focus();
         document.mainForm.barcode.style.color = "red";
         return false;
     }
 }


//
//function createXMLHttpRequest() {     //创建XMLHttpRequest对象
//try {
//    xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
//} catch(e) {
//    try {
//        xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
//    } catch(e) {
//        try {
//            xmlHttp = new XMLHttpRequest();
//        } catch(e) {
//            alert("创建XMLHttpRequest对象失败！");
//        }
//    }
//}
//}

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'Y') {
                document.mainForm.barcode.style.color = "red";
                document.getElementById("barcodeNo11").style.visibility = "visible"
                document.mainForm.barcode.focus();
            } else {
                document.mainForm.barcode.style.color = "black";
                document.getElementById("barcodeNo11").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

</script>