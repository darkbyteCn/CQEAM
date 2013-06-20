<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>

<%
String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
if( null == allResName ){
	allResName = "个人信息维护";
}
%>
<%--
  User: Zyun
  Date: 2008-1-11
  Time: 14:50:00
--%>
<html>
<head>
    <title>个人信息维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<script type="text/javascript">
     printTitleBar("<%= allResName %>");
</script>

<body leftmargin="0" topmargin="0" onload="do_Init()" onkeydown="autoExeFunction('do_submit()');">
<%
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
	SfUserDTO sfuserDto = (SfUserDTO) request.getAttribute(WebAttrConstant.USER_ATTR);
%>
<form action="/servlet/com.sino.ams.system.user.servlet.ChangeUserPasswordServlet" method="post" name="mainFrm">
    <jsp:include page="/message/MessageProcess"/>
    <input type="hidden" name="act">
    <input type="hidden" name="name" value="">
    <input type="hidden" name="userId" value="<%=user.getUserId()%>">
    <table width="81%" border="0" align="center" class="">
        <tr>
            <td width="20%" align="right">登录名：</td>
            <td width="80%"><input type="text" name="loginName" class="input_style2" readonly style="width:80%"
                                   value="<%=user.getLoginName()%>">
                <br><label id="retMsg" style="color:red"></label>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">用户名：</td>
            <td width="80%"><input type="text" name="userName" class="input_style2" readonly style="width:80%"
                                   value="<%=user.getUsername()%>">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">办公电话：</td>
            <td width="80%"><input type="text" name = "officeTel" class="input_style1" size = "40" style = "width:80%"
                                   value="<%=sfuserDto.getOfficeTel()%>">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">传真号码：</td>
            <td width="80%"><input type="text" name = "fax" size = "40" class="input_style1" style = "width:80%"
                                   value="<%=sfuserDto.getFax() %>">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">移动电话：</td>
            <td width="80%"><input type="text" name = "mobilePhone" size = "40" class="input_style1" style = "width:80%"
                                   value="<%=sfuserDto.getMobilePhone()%>">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">E-mail：</td>
            <td width="80%"><input type="text" name = "email" size = "40" class="input_style1" style = "width:80%"
                                   value="<%=sfuserDto.getEmail() %>">
            </td>
        </tr>
        <%--<tr>--%>
            <%--<td width="20%" align="right">密码过期日期：</td>--%>
            <%--<td width="80%">--%>
            	<%--<input  readonly name="passwordDate" style="width:80%" class="input_style2" value="<%=StrUtil.nullToString(sfuserDto.getPasswordOverdue()) %>" onclick = "gfPop.fPopCalendar(passwordDate)"><img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(passwordDate)">--%>
            <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td width="20%" align="right">原密码：</td>
            <td width="80%"><input type="password" readonly class="readonlyInput" name="oldPswd" style="width:80%" value = "<%=sfuserDto.getPassword()%>"><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="20%" align="right">新密码：</td>
            <td width="80%"><input type="password" class="input_style1" value = "<%=sfuserDto.getPassword()%>" name="password" style="width:80%"
                                   onblur="checkPswd(this);"  value = ""><font color="red">*</font>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right"></td>
              <td width="80%">
                <font color="red">*密码长度应不小于8位,不大于20位,并且应为数字和字母的组合</font>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">确认密码：</td>
            <td width="80%"><input type="password" class="input_style1" value = "<%=sfuserDto.getPassword()%>" name="password" style="width:80%"  value = ""><font color="red">*</font></td>
        </tr>
        <tr>
            <td width="20%" align="right">是否需要短信提醒：</td>
            <td width="80%"><select class="select_style1" name="isSms" style="width:80%"><option value="Y">是</option><option value="N" <%if("N".equals(sfuserDto.getIsSms())){%>selected<%}%>>否</option></select></td>
        </tr>
    </table>
    <p align="center"><a style="cursor:'hand'" onclick="do_submit();"><img src="/images/eam_images/ok.jpg" alt="确定"></a></p>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    var loginNameWrong = false;
    function do_submit() {
        var password = document.getElementsByName("password");
        if (isEmpty(password[0].value) || isEmpty(password[1].value)) {
            alert("请输入密码！");
            return;
        }
        if (password[0].value != password[1].value) {
            alert("两次输入密码不一致！")
            return;
        }
        if (mainFrm.officeTel.value != "" && !isTelNumber(mainFrm.officeTel.value)) {
        alert("请输入正确的办公电话！")
        mainFrm.officeTel.focus();
        return;
    	}
	    if (mainFrm.fax.value != "" && !isTelNumber(mainFrm.fax.value)) {
	        alert("请输入正确的传真号码！")
	        mainFrm.fax.focus();
	        return;
	    }
	    if (mainFrm.mobilePhone.value != "" && !isMobile(mainFrm.mobilePhone.value)) {
	        alert("请输入正确的移动电话！");
	        mainFrm.mobilePhone.focus();
	        return;
	    }
	    if (mainFrm.email.value != "" && !isEmail(mainFrm.email.value)) {
	        alert("请输入正确的email地址！");
	        mainFrm.email.focus();
	        return;
	    }
	        document.mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
	        document.mainFrm.submit();
	}
    function do_Init() {
        document.oncontextmenu = function() {
            return false;
        }
    }
    function do_loginName_verify() {
        var loginName = document.mainFrm.loginName.value
        if (!isEmpty(loginName)) {
            var xmlhttp = createXMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4) {
                    if (xmlhttp.status == 200) {
                        if (xmlhttp.responseText == "0") {
                            document.getElementById("retMsg").innerText = "该登录名在系统中不存在";
                            loginNameWrong = true;
                        } else if (xmlhttp.responseText == "1") {
                            document.getElementById("retMsg").innerText = "该登录名有效";
                            loginNameWrong = false;
                        } else if (xmlhttp.responseText == "2") {
                            document.getElementById("retMsg").innerText = "该用户为MIS用户，您无法在本系统修改其密码";
                            loginNameWrong = true;
                            document.mainFrm.loginName.focus();
                        } else if (xmlhttp.responseText == "3") {
                            document.getElementById("retMsg").innerText = "您无法修改其他地市用户的密码";
                            loginNameWrong = true;
                            document.mainFrm.loginName.focus();
                        } else {
                            document.getElementById("retMsg").innerText = "验证登录名出错，请重试一次";
                            loginNameWrong = true;
                            document.mainFrm.loginName.focus();
                        }
                    } else {
                        alert(xmlhttp.status);
                    }
                }
            }
            xmlhttp.open("post", "/servlet/com.sino.ies.systemsetup.user.servlet.UserVerifyServlet", false);
            xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlhttp.send("loginName=" + loginName);
        } else {
            loginNameWrong = true;
            document.getElementById("retMsg").innerText = "登录名不能为空";
        }
    }
    function checkPswd(pswd) {
        var Expression = /(?=[!-~]{8,20})(?=[!-~]*[^0-9]+)(?=[!-~]*\d+)/;
        var objExp = new RegExp(Expression);
        if (!isEmpty(pswd.value) && !objExp.test(pswd.value)) {
            alert("密码复杂度不符合要求，请重新填写！");
            pswd.focus()
        }
    }
</script>