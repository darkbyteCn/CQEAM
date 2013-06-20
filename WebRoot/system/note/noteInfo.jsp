<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.note.dto.AmsRentDeadlineDTO" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.util.CalendarUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-22
  Time: 15:11:10
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>租期提醒设置</title>
</head>
<script type="text/javascript">
    printTitleBar("租期提醒设置")
</script>
<%
    AmsRentDeadlineDTO noteDTO = (AmsRentDeadlineDTO) request.getAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String accountTime = CalendarUtil.getCurrDate();
    
%>

<body  leftmargin="0">
<jsp:include page="/message/MessageProcess"/>
<form action="" method="post" name="mainFrm">
    <input type="hidden" name="act">
    <input type="hidden" name="isExist">
    <input type="hidden" name="deadlineId" value="<%=noteDTO.getDeadlineId()%>">
    <table width="50%" align="center">
 <tr>
     <td align="center" colspan="2"  width="45%" id="barcode11" style="color:red;visibility:hidden">对不起，租赁资产条码已存在！</td>
</tr>
        <tr>
            <td width="15%" align="right">租赁资产条码：</td>
            <td width="35%"><input  readonly class="input_style2" type="text" style="width:90%"  name="barcode" value="<%=noteDTO.getBarcode()%>" onchange="do_verifybarcode();">
<%
   if(noteDTO.getDeadlineId().equals("")){
%>
                <a  class="linka" style="cursor:'hand'" onclick="do_SelectDay();">[…]</a>
<%
    }
%>                
                </td>
        </tr>
        <tr>
            <td width="15%" align="right">名称：</td>
            <td width="35%"><input readonly class="input_style2" type="text" style="width:90%"  name="itemName" value="<%=noteDTO.getItemName()%>"></td>
        </tr>
        <tr>
            <td width="15%" align="right">描述：</td>
            <td width="35%"><input readonly class="input_style2" type="text" style="width:90%"  name="itemSpec" value="<%=noteDTO.getItemSpec()%>"></td>
        </tr>
        <tr>
            <td align="right">截至日期：</td>
            <td><input type="text" style="width:90%" readonly name="endDate" value="<%=noteDTO.getEndDate()%>"  class="input_style2" ></td>
        </tr>

        <tr>
            <td align="right">提前通知天数：</td>
            <td width="25%"><input class="input_style1" name="noticeBefore" style="width:90%" value="<%=noteDTO.getNoticeBefore()%>" onblur="do_verify()"> <font color="red">*</font>
            </td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="3">
                <img  src="/images/eam_images/save.jpg" onClick="do_save();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/back.jpg" onClick="do_back();">&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
function do_back() {
    mainFrm.name.value = "";
    mainFrm.deadlineId.value = "";
    mainFrm.action = "/servlet/com.sino.ams.system.note.servlet.AmsRentDeadlineServlet";
    mainFrm.submit();
}
function do_delete() {
    document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
    document.mainFrm.action = "/servlet/com.sino.ams.system.note.servlet.AmsRentDeadlineServlet?deadlineId" + document.mainFrm.deadlineId.value;
    document.mainFrm.submit();
}

function do_save() {
    var fieldNames = "barcode;noticeBefore";
    var fieldLabels = "租赁资产条码;提前通知天数";
    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
        with (mainFrm) {
            act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            if (deadlineId.value != "") {
                act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
            action = "/servlet/com.sino.ams.system.note.servlet.AmsRentDeadlineServlet";
            submit();
        }
    }
}

function do_verify() {
    var fieldNames = "noticeBefore";
    var fieldLabels = "提前通知天数";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_INT_VALIDATE)) {
//        alert("提前通知天数必须为正整数！");
    }
}

function do_SelectDay() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_DAY%>";
    var dialogWidth = 35;
    var dialogHeight = 30;
    //LOOKUP传参数 必须和DTO中一致
    var notes = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (notes) {
        var user = null;
        for (var i = 0; i < notes.length; i++) {
            user = notes[i];
            dto2Frm(user, "mainFrm");
            do_verifybarcode();
        }
    }
}

function do_verify3() {
    var fieldNames = "rental";
    var fieldLabels = "租金";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
//        alert("租金必须为正数字！");
    }
}



var xmlHttp;
function do_verifybarcode() {
    var url = "";
    var segment1 = document.mainFrm.barcode.value;
    createXMLHttpRequest();
    if (document.mainFrm.barcode.value) {
        url = "/servlet/com.sino.ams.system.house.servlet.AmsLandInfoServlet?act=verifyBarcode&barcode=" + document.mainFrm.barcode.value;
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
                document.mainFrm.isExist.value = 'Y';
                document.getElementById("barcode11").style.visibility = "visible"
                document.mainFrm.barcode.focus();
            } else {
                document.mainFrm.isExist.value = 'N';
                document.getElementById("barcode11").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
</script>