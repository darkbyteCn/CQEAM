<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.web.bts.dto.EtsObjectFixfeeDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-25
  Time: 16:32:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>基站信息维护详细信息</title>
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
<script type="text/javascript">
    printTitleBar("基站信息维护详细信息")
</script>
<body>
<jsp:include page="/message/MessageProcess"/>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    EtsObjectFixfeeDTO dto = (EtsObjectFixfeeDTO) request.getAttribute(WebAttrConstant.OBJECT_BTS_DTO);
    String action = parser.getParameter("act");
%>
<form action="/servlet/com.sino.ams.web.bts.servlet.EtsObjectFixfeeServlet" name="detForm" method="post">
    <table border="0" width="100%">
        <tr>
            <td align="right" width="5%">基站名称：</td>
            <td width="35%"><input type="text" name="workorderObjectName" style="width:45%"  class="readonlyInput"   readonly
                                   value="<%=dto.getWorkorderObjectName()%>"><a href="#"
                    class="linka"
                    style="cursor:'hand'"
                    onclick="do_selectName();">[…]</a>
            </td>

        </tr>
        <tr>
            <td align="right" width="5%">备注：</td>
            <td><textarea rows="10" cols="63" name="remark"><%=dto.getRemark()%></textarea></td>
        </tr>
        <tr>
            <td align="right" width="5%">维修费：</td>
            <td width="15%"><input name="amount" type="text" value="<%=dto.getAmount()%>" style="width:46%"onblur="do_check1();" class="noemptyInput"></td>
        </tr>
        <tr>
            <td align="right" width="5%">维修日期：</td>
            <td width="15%"><input type="text" name="fixDate" value="<%=dto.getFixDate()%>" style="width:46%"   class="readonlyInput"
                                   title="点击选择日期" readonly  onclick="gfPop.fPopCalendar(fixDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(fixDate)"></td>

        </tr>
        <tr>
            <td></td>
            <td align="left">
                <img src="/images/eam_images/save.jpg" alt="保存维修信息"
                     onClick="do_savePlan(); return false;">
                <img src="/images/eam_images/back.jpg" alt="返回"
                     onclick="do_concel();return false;"></td>
        </tr>
    </table>
    <input type="hidden" name="systemId" value="<%=dto.getSystemId()%>">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="workorderObjectNo" value="<%=dto.getWorkorderObjectNo()%>">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_selectName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "detForm");
            }
        }
    }
    function do_savePlan() {
        var fileNames = "workorderObjectName;amount;fixDate";
        var fileLables = "基站名称 ;维修费; 维修日期";
        var validateType = EMPTY_VALIDATE;
        var isValid = formValidate(fileNames, fileLables, validateType);
        if (isValid) {
            if (detForm.systemId.value == "") {

                detForm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            } else {

                detForm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
            detForm.submit();
        }

    }
    function do_concel() {
        with (detForm) {
            window.close();
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            submit();
        }
    }
    function do_check1(){
        var fieldNames = "amount";
        var fieldLabels =  "维修费";
        if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
//        alert("维修费必须为正数！");
    }
    }
</script>