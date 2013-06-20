<%--
  User: yuyao
  Date: 2007-9-26
  Time: 17:24:50
--%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.web.ele.dto.EtsObjectEleDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>基站用电维护详细信息</title>
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
    printTitleBar("基站用电维护详细信息")
</script>

<body>
<jsp:include page="/message/MessageProcess"/>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    EtsObjectEleDTO dto = (EtsObjectEleDTO) request.getAttribute(WebAttrConstant.OBJECT_ELE_DTO);
    String action = parser.getParameter("act");
%>
<form action="/servlet/com.sino.ams.web.ele.servlet.EtsObjectEleServlet" name="mainForm" method="post">
    <table border="0" width="100%">
        <tr>
            <td align="right" width="5%">基站名称：</td>
            <td width="35%"><input type="text" name="workorderObjectName" class="readonlyInput" readonly style="width:43%"
                                   value="<%=dto.getWorkorderObjectName()%>"><a
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
            <td align="right" width="5%">会计期间：</td>
            <td width="35%"><input name="period" class="noemptyInput" type="text" value="<%=dto.getPeriod()%>" style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="5%">电表读数：</td>
            <td><input name="ammeterReading" type="text" value="<%=dto.getAmmeterReading()%>" style="width:46%"></td>
        </tr>
         <tr>
            <td align="right" width="5%">用电量：</td>
            <td><input name="quantity" type="text" class="noemptyInput" value="<%=dto.getQuantity()%>" onblur="do_check1();" style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="5%">单价：</td>
            <td><input name="unitPrice" type="text" class="noemptyInput" value="<%=dto.getUnitPrice()%>" onblur="do_check2();" style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="5%">最后更新人：</td>
            <td><input name="lastUpdateBy" type="text" class="readonlyInput" value="<%=dto.getLastUpdateBy()%>" readonly style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="5%">最后更新日期：</td>
            <td><input name="lastUpdateDate" type="text" class="readonlyInput" value="<%=dto.getLastUpdateDate()%>" readonly style="width:46%"></td>
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
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="workorderObjectNo" value="<%=dto.getWorkorderObjectNo()%>">
    <input type="hidden" name="systemid" value="<%=dto.getSystemid()%>">

</form>
</body>
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
                dto2Frm(user, "mainForm");
            }
        }
    }
    function do_savePlan() {
        var fileNames = "workorderObjectName;period;quantity;unitPrice";
        var fileLables = "基站名称 ;会计期间; 用电量 ;单价";
        var validateType = EMPTY_VALIDATE;
        var isValid = formValidate(fileNames, fileLables, validateType);
        if (isValid) {
            if (mainForm.systemid.value == "") {
                mainForm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            } else {
                mainForm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
            mainForm.submit();
        }
    }
    function do_concel() {
        with (mainForm) {
            window.close();
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            submit();
        }
    }
    function do_check1() {

            var filedNames = "quantity";
            var fieldLabels = "用电量";
            if (!formValidate(filedNames, fieldLabels, POSITIVE_VALIDATE)) {
            }
        }
  function do_check2(){
            var filedNames = "unitPrice";
            var fieldLabels = "单价";
            if (!formValidate(filedNames, fieldLabels, POSITIVE_VALIDATE)) {
            }
        }
</script>