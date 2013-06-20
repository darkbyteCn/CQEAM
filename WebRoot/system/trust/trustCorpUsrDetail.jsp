<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.trust.dto.AmsMaintainPeopleDTO" %>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>代维公司人员详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<body>
<jsp:include page="/message/MessageProcess"/>
<%
    AmsMaintainPeopleDTO mainCopUsr = (AmsMaintainPeopleDTO) request.getAttribute(WebAttrConstant.MAINTAIN_CORP_USR_ATTR);
    String userId=mainCopUsr.getUserId();
    RequestParser parser = new RequestParser();
    parser.transData(request);
    
%>
<form name="mainFrm" method="POST">
    <script type = "text/javascript">
        printTitleBar("代维公司人员查询")
    </script>
    <table border="0" width="100%" id="table1">
        <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
        <tr>
            <td width="25%" align="right" height="22">姓&nbsp&nbsp名：</td>
            <td width="50%" align="left" height="22" colspan="3"><input type="text" name="userName" size="40"
                                                                        class="input_style1" style="width:100%"
                                                                        value="<%=mainCopUsr.getUserName() %>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font> </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">固定电话：</td>
            <td width="50%" align="left" height="22" colspan="3">
            <input type="text" name="userTelephone" size="40"  class="input_style1" style="width:100%"  value="<%=mainCopUsr.getUserTelephone()  %>">
            </td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">移动电话：</td>
            <td width="50%" align="left" height="22" colspan="3"><input type="text" name="userMobilePhone"
                                                                        size="40"
                                                                        class="input_style1" style="width:100%"
                                                                        value="<%= mainCopUsr.getUserMobilePhone()%>"
                    ></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">EMAIL：</td>
            <td width="50%" align="left" height="22" colspan="3">
            <input type="text" name="email" size="40"  class="input_style1"  style="width:100%"  value="<%= mainCopUsr.getEmail()%>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">所属代维公司：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <select name="companyId"  class="select_style1"   style="width:100%"><%=request.getAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION)%></select>
            </td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveMaintainCompany(); return false;">&nbsp;
                <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_DeleteMaintainCompany(); return false;">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_Back(); return false;"></td>
        </tr>

    </table>

    <input type="hidden" name="userId" value="<%=userId %>">
</form>
</body>

<script>
    function do_SaveMaintainCompany() {
        //输入校验 -- 非空
        var fieldNames = "userName;userMobilePhone;companyId";
        var fieldLabels = "姓名;移动电话;所属代维公司";
        var validateType = EMPTY_VALIDATE;
        var isValid = formValidate(fieldNames, fieldLabels, validateType);
        var userMobilePhone = document.mainFrm.userMobilePhone.value;
        if (!isPositiveNumber(userMobilePhone)) {
            alert("移动电话必须为正数！");
            document.mainFrm.userMobilePhone.focus();
            return false;
        }
        if (userMobilePhone.length != 11) {
            alert("移动电话必须为11位！");
            document.mainFrm.userMobilePhone.focus();
            return false;
        }
        if (isValid) {

            //输入校验 -- 长度
            fieldNames = "userName;userMobilePhone";
            fieldLabels = "0$姓名$20;0$移动电话$11";
            validateType = LENGTH_VALIDATE;
            isValid = formValidate(fieldNames, fieldLabels, validateType);
            if (isValid) {

                //输入校验 -- 数字
                fieldNames = "userTelephone;userMobilePhone";
                fieldLabels = "固定电话;移动电话";
                validateType = NUMBER_VALIDATE;
                isValid = formValidate(fieldNames, fieldLabels, validateType);
                
                 //输入校验 --EMAIL
                fieldNames = "email";
                fieldLabels = "电子邮件";
                validateType = EMAIL_VALIDATE;
                isValid = formValidate(fieldNames, fieldLabels, validateType);

                if (isValid) {
                    var action = "<%=WebActionConstant.CREATE_ACTION%>";
                    if (mainFrm.userId.value!='') {
                        action = "<%=WebActionConstant.UPDATE_ACTION%>";
                    }
                    mainFrm.act.value = action;
                    mainFrm.action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainPeopleServlet";
                    mainFrm.submit();
                }
            }
        }
    }

    function do_DeleteMaintainCompany() {

        if (confirm("确认删除该角色吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainPeopleServlet";
            mainFrm.submit();
        }
    }


    function do_Back() {

        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainPeopleServlet";
        mainFrm.submit();
    }

</script>
</html>