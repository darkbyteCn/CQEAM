<%@ page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.framework.security.dto.FilterConfigDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
<title>重新登录</title>
</head>
<html>
<base target="_self">
<body onload="mainFrm.loginName.focus();">
<p align="center"><jsp:include page="/message/MessageProcess"/></p>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
	FilterConfigDTO filterConfig = (FilterConfigDTO)application.getAttribute(WebConstant.FILTER_DTO);
%>
<form name="mainFrm" action="<%=filterConfig.getSessionServlet()%>" method="post">
    
<input type="hidden" name="act">

<TABLE cellSpacing=0 cellPadding=0 width=330 align=center border=0>
    <TBODY>
        <TR>
            <TD height="202" align=middle style="background-image:url(/images/eam_images/relog.jpg);" valign="top">
                <TABLE  width="100%" border=0 >
                    <TBODY>
                        <TR>
                            <TD height="85" align=middle colspan="4" width="330" ></TD>
                        </TR>
                        <TR>
                            <TD width=10 height=30 align="right">&nbsp;</TD>
                            <TD width=80 height=30 align="right">用户名：</TD>
                            <TD  height=30 align="right">
                                <input   id="<%=filterConfig.getLoginName()%>" size=22 name="<%=filterConfig.getLoginName()%>" value="<%=parser.getParameter(filterConfig.getLoginName())%>">
                             </TD>
                            <TD width=80 height=30 rowspan="2">&nbsp;&nbsp;<img src="/images/eam_images/login.jpg" onclick="do_Login();"></TD>
                            <TD width=10 height=30 align="right">&nbsp;</TD>
                        </TR>
                        <TR>
                            <TD width=10 height=30 align="right">&nbsp;</TD>
                            <TD width=80 height=30 align="right">密　码：</TD>
                            <TD  height=30 align="right">
                                <INPUT type="password"   id="<%=filterConfig.getLoginPwd()%>" size=22 name="<%=filterConfig.getLoginPwd()%>" >
                             </TD>
                            <TD width=10 height=30 align="right">&nbsp;</TD>
                        </TR>
                        <TR>
                            <TD width=10 height=22 align="right">&nbsp;</TD>
                            <TD width=80 height=22 align="right">&nbsp;</TD>
                            <%--<TD height=22 colspan="2"><a href="#" onclick="do_relog();">重新登录</a></TD>--%>
                            <TD height=22 colspan="2">&nbsp;</TD>
                            <TD width=10 height=22 align="right">&nbsp;</TD>
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
<script type="text/javascript">
    function do_Login() {
        var fields = "<%=filterConfig.getLoginName()%>";
        var labels = "登录名";
        var validateType = EMPTY_VALIDATE;
        if (formValidate(fields, labels, validateType)) {
            document.mainFrm.submit();
        }
    }

    function do_relog() {
        document.location.href="<%=filterConfig.getLoginUrl()%>";
    }
</script>