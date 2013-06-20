<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.sino.base.calen.SimpleDate" %>
<%@ page import="com.sino.base.constant.calen.DateConstant" %>
<%@ page import="com.sino.framework.security.dto.*" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<title><%=SessionUtil.getServletConfigDTO(request).getSystemName()%></title>
	<link href="css/ems.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="/WebLibary/js/globe.js"></script>
	<script language="javascript" src="/WebLibary/js/public.js"></script>
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
	<style>
	a{ color:#000; text-decoration:none;}
	a:hover{ color:#3366FF;}
    <!--
    body, td, th {
        font-size: 12px;
    }
    body {
        margin-left: 0;
        margin-top: 0;
        margin-right: 0;
        margin-bottom: 0;
        background-repeat:no-repeat   ;
        background-position:center
    }
    img {
        cursor:pointer
    }
    -->
	</style>
	<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="WebBrowser"	width="0"></object>
<%
    SimpleDate dateObj = new SimpleDate();
	dateObj.setDateValue(System.currentTimeMillis());
    dateObj.setDatePattern(DateConstant.CHINESE_PATTERN);
	FilterConfigDTO filterConfig = SessionUtil.getFilterConfigDTO(application);
	String loginName = filterConfig.getLoginName();
	String loginPwd = filterConfig.getLoginPwd();
	ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(application);
%>
</head>
<body leftMargin="0" topMargin="0" bgcolor="#142664" onload="mainFrm.<%=filterConfig.getLoginName()%>.focus();" onkeydown="autoExeFunction('do_Login()');">
<form name="mainFrm" action="/servlet/com.sino.ams.system.user.servlet.AmsLoginServlet" method=post>
<jsp:include page="/message/MessageProcess"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	  <tr>
	    <td align="center" style="color: #FFFFFF"><table width="833" height="480" style="background:url(<%=servletConfig.getLoginImage()%>)" border="0" cellpadding="0" cellspacing="0">
	        <tr>
	          <td align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td width="54%" height="217"></td>
	                <td width="17%"></td>
	                <td width="29%"></td>
	              </tr>
	              <tr>
	                <td height="24"></td>
	                <td><input name="<%=loginName%>" id="<%=loginName%>" type="text" style="width:95%;height:85%"></td>
	                <td rowspan="2"><img id="btn" src="/images/login_btn.gif" onMouseOver="login_hover()" onMouseOut="login_out();" style="border:0px; height:51px; width:51px;cursor:hand;" onClick="do_Login();"></td>
	              </tr>
	              <tr>
	                <td height="24"></td>
	                <td><input name="<%=loginPwd%>" id="<%=loginPwd%>" type="password" style="width:95%;height:85%"></td>
	              </tr>
	              <tr>
	                <td height="24"></td>
	                <td>[ <a href="#" onclick="getPswd();">忘记密码？</a>]</td>
	                <td>　</td>
	              </tr>
	            </table></td>
	        </tr>
	      </table>
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td align="center" class="copyright" style="color: #FFFFFF">建议用1024*768 或 1280*1024分辨率下使用</td>
	        </tr>
	      </table>
	      </td>
	  </tr>
	</table>
	<input type="hidden" name="act">
</form>	
</body>
</html>
<script language="javascript">
function do_Login(){
	var fields = "<%=loginName%>;<%=loginPwd%>";
	var labels = "登录名;登录密码";
	var validateType = EMPTY_VALIDATE;
	if(formValidate(fields, labels, validateType)){
		var width = screen.width - 10;
		var height = screen.height-60;
        var style = "width=" + width + ",height=" + height + ",top=0,left=0";
        window.open("/public/waiting.htm", "loginFrm", style);
        mainFrm.target = "loginFrm";
        mainFrm.submit();
		WebBrowser.ExecWB(45,1);
	}
}

function do_Reset(){
	mainFrm.reset();
}

function getPswd(){
    var loginName = document.getElementById("<%=loginName%>").value;
    if(isEmpty(loginName)){
        alert("请输入用户名!");
        return;
    }
    mainFrm.act.value = "GET_PASSWORD";
    mainFrm.submit();
}
</script>