<%--
  Created by Herry.
  Date: 2008-7-22
  Time: 10:10:33
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>首次登录系统或密码已过有效期，更改密码</title>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <style type="text/css">
        BODY, TD, INPUT {
            FONT-SIZE: 12px;
        }
    </style>
</head>
<%
    String loginName = request.getParameter("loginName");
%>
<body onload="document.getElementById('password').focus();" onkeydown="autoExeFunction('do_submit()');">

<form action="/servlet/com.sino.ams.system.user.servlet.FirstLoginServlet" name="mainForm" id="mainForm" method="post">
    <input type="hidden" name="loginName" value="<%=loginName%>">
    <table border="1" width="502" cellspacing="1" id="table1" style="border-collapse: collapse" bordercolor="#9FD6FF"
           bgcolor="#F2F9FF" align="center">
        <tr>
            <td>
                <table width="500" align="center">
                    <tr height="40">
                        <td colspan="3">您是首次登录系统或密码已过有效期，请修改密码：</td>
                    </tr>
                    <tr height="30">
                        <td align="right" width="30%">
                            用户名：
                        </td>
                        <td width="30%">
                            <%=loginName%>
                        </td>
                        <td width="40%"></td>
                    </tr>
                    <tr height="30">
                        <td align="right">
                            <font color="red">*</font>新密码：
                        </td>
                        <td>
                            <input type="password" name="password" onblur="checkPswd();">
                        </td>
                        <td><label id="label1" style="color:red"></label></td>
                    </tr>
                    <tr height="30">
                        <td align="right">
                            <font color="red">*</font>确认密码：
                        </td>
                        <td>
                            <input type="password" name="cpassword" onblur="confirmPswd();">
                        </td>
                        <td><label id="label2" style="color:red;display:none">您两次输入的登录密码不一致</label></td>
                    </tr>
                    <tr height="30">
                        <td colspan="1" align="right"><input type="button" value="确定" onclick="do_submit();"></td>
                        <td colspan="1" align="center"><input type="reset" value="重置" ></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="color:red" align="center">注：密码不允许为空。</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    var flag = "N";
    function checkPswd() {
        var pswd = mainForm.password.value;
        var label1 = document.getElementById("label1");
        if (pswd == "eamprd") {
            label1.innerText = "新密码不能为初始密码";
        } else {
            label1.innerText = "";
            var Expression = /(?=[!-~]{6,20})(?=[!-~]*[^0-9]+)(?=[!-~]*\d+)/;
            var objExp=new RegExp(Expression);
            if(objExp.test(pswd)){
                flag = "Y";
            }else{
                label1.innerText = "密码长度不小于6位,不大于20位,并且应为数字和字母的组合";
            }
        }
    }
    function confirmPswd() {
        var pswd = mainForm.password.value;
        var cpswd = mainForm.cpassword.value;
        if (pswd != cpswd) {
            document.getElementById("label2").style.display = "block";
        } else {
            document.getElementById("label2").style.display = "none";
        }
        window.setTimeout("confirmPswd();", 2000);
    }
    function do_submit() {
        if(flag == "Y"){
            var pswd = mainForm.password.value;
            var cpswd = mainForm.cpassword.value;
            if (pswd != "" && pswd == cpswd) {
                mainForm.submit();
            }
        }
    }

</script>
</html>