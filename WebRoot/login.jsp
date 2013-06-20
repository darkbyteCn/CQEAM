<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.sino.base.calen.SimpleDate" %>
<%@ page import="com.sino.base.constant.calen.DateConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.FilterConfigDTO" %>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
    <title><%=SessionUtil.getServletConfigDTO(request).getSystemName()%></title>
    <style type="text/css">
        <!--
        body, td, th {
            font-size: 12px;
        }
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
        }
        img {
            cursor:pointer
        }
        -->
    </style>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
</head>
<%
    SimpleDate dateObj = new SimpleDate();
	dateObj.setDateValue(System.currentTimeMillis());
    dateObj.setDatePattern(DateConstant.CHINESE_PATTERN);
    RequestParser rp = new RequestParser();
    rp.transData(request);
	FilterConfigDTO filterConfig = SessionUtil.getFilterConfigDTO(application);
	ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(application);
	String loginName = filterConfig.getLoginName();
	String loginPwd = filterConfig.getLoginPwd();
    SfUserDTO userAccount =(SfUserDTO) SessionUtil.getUserAccount(request);
//    if (userAccount != null && userAccount.getUserId() > 0) {
//        String actionURL = "/servlet/com.sino.sinoflow.framework.security.servlet.LoginFrmServlet";
//        response.sendRedirect(actionURL);
//        return;
//    }
%>
<body bottomMargin="0" leftMargin="0" topMargin="0" rightMargin="0" onload="document.getElementById('<%=loginName%>').focus();"  onkeydown="autoExeFunction('do_Login()');">
<FORM name="mainFrm" action="<%=filterConfig.getLoginServlet()%>" method=post>
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="act">

<TABLE cellSpacing=0 cellPadding=0 width=1024 align=center border=0>
    <TBODY>
        <TR>
            <TD height="768" align=middle style="background-image:url(/images/eam_images/home.jpg);" valign="top">
                <TABLE  width="100%" border=0 >
                    <TBODY>
                        <TR>
                            <TD height="400" align=middle colspan="4" ></TD>
                        </TR>
                        <TR>
                            <TD width=475 height=30 align="right">&nbsp;</TD>
                            <TD width=80 height=30 align="right">用户名：</TD>
                            <TD  height=30 align="right">
                                <INPUT   id="<%=loginName%>" size=22 name="<%=loginName%>" value="<%=rp.getParameter(loginName)%>">
                             </TD>
                            <TD width=80 height=30 rowspan="2"><img src="/images/eam_images/login.jpg" onclick="do_Login();"></TD>
                            <TD width=260 height=30 align="right">&nbsp;</TD>
                        </TR>
                        <TR>
                            <TD width=475 height=30 align="right">&nbsp;</TD>
                            <TD width=80 height=30 align="right">密　码：</TD>
                            <TD  height=30 align="right">
                                <INPUT type="password"   id="<%=loginPwd%>" size=22 name="<%=loginPwd%>" >
                             </TD>
                            <TD width=260 height=30 align="right">&nbsp;</TD>
                        </TR>
                        <TR>
                            <TD width=475 height=22 align="right">&nbsp;</TD>
                            <TD width=80 height=22 align="right">&nbsp;</TD>
                            <TD width=340 height=22 colspan="2"><a href="#" onclick="getPswd();">忘记密码？请点击找回密码</a></TD>
                            <TD width=260 height=22 align="right">&nbsp;</TD>
                        </TR>
                    </TBODY>
                </TABLE>
            </TD>
        </TR>
    </TBODY>
</TABLE>


</FORM>
</BODY>
</HTML>
<script type="text/javascript" language="javascript">
    function do_Login() {
        var fields = "<%=loginName%>;<%=loginPwd%>";
        var labels = "登录名;登录密码";
        var validateType = EMPTY_VALIDATE;
        if (formValidate(fields, labels, validateType)) {
            mainFrm.submit();
        }
    }

    function do_Reset() {
        mainFrm.reset();
    }
    function getPswd() {
        var loginName = document.getElementById("<%=loginName%>").value;
        if (isEmpty(loginName)) {
            alert("请输入用户名!");
            return;
        }
        mainFrm.act.value = "GET_PASSWORD";
        mainFrm.submit();
    }
</script>