<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.ams.print.dto.BarcodeReceiveDTO" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%--
  Author: 李轶
  Date: 2009-5-14
  Time: 20:30:51
  To change this template use File | Settings | File Templates.
--%>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>标签号打印确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/style/js/calendar.js"></script>
    
<style type="text/css">
        <!--
        .STYLE1 {
            color: #0033FF
        }
        -->
</style>
</head>
<body>
 <jsp:include page="/message/MessageProcess"/>
<%
    BarcodeReceiveDTO dto = (BarcodeReceiveDTO)request.getAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO);
%>
<form name="mainFrm" method="POST">
<fieldset style="margin-left:0;height:450px">
   <legend><span class="STYLE1">标签号打印确认</span></legend>
    <table width="50%" align="center">
        <tr>
            <td width="10%" align="right" height="22">标签号：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="fromBarcode" style="width:60%" class="noEmptyInput" value="<%=dto.getFromBarcode()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">打印确认次数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="barcodePrintNum" readonly class="readonlyInput" style="width:60%" value="<%=dto.getBarcodePrintNum()%>">
            </td>
        </tr>

        <tr>
            <td width="50%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/search.jpg" style="cursor:hand" onclick="do_Search();" title="查询">&nbsp;
                <img src="/images/eam_images/confirm.jpg"  style="cursor:'hand'" onClick="do_Confirm(); return false;" alt="标签号打印确认">&nbsp;
                <img src="/images/eam_images/cancel.jpg" onClick="do_back();" alt="后退">
        </tr>
    </table>
</fieldset>
    <input type="hidden" name="act" value="">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script>
function do_Confirm() {
    var fieldNames = "fromBarcode";;
    var fieldLabels = "标签号";
    var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
    if (isValid) {
        var fromBarcode = mainFrm.fromBarcode.value;
        var Expression = /^[0-9]{4}-[0-9]{8}$/;
        var objExp=new RegExp(Expression);
        if(!objExp.test(fromBarcode)){
            alert("“标签号”不合法，请输入正确格式！");
            mainFrm.fromBarcode.focus();
            return;
        }
        var action = "<%=AssetsActionConstant.CONFIRM_ACTION%>";
		mainFrm.act.value = action;
		mainFrm.action = "/servlet/com.sino.ams.print.servlet.BarcodePrintServlet";
		mainFrm.submit();
    }
}

function do_Search() {
    var fieldNames = "fromBarcode";;
    var fieldLabels = "标签号";
    var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
   if (isValid) {
        var fromBarcode = mainFrm.fromBarcode.value;
        var Expression = /^[0-9]{4}-[0-9]{8}$/;
        var objExp=new RegExp(Expression);
        if(!objExp.test(fromBarcode)){
            alert("“标签号”不合法，请输入正确格式！");
            mainFrm.fromBarcode.focus();
            return;
        }
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
   }
}

function do_back() {
    window.close();
}
</script>