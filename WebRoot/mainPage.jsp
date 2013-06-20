<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.sinoflow.framework.resource.dto.SfResDefineDTO" %>
<html>
<head>
    <title><%=SessionUtil.getServletConfigDTO(request).getSystemName()%></title>
</head>
<% 
//设置无缓存 
response.setHeader("progma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
%> 

<%
	SfResDefineDTO authorizedResource = (SfResDefineDTO)request.getAttribute(WebAttrConstant.FIRST_RESOURCE);
	String resourcePid = authorizedResource.getResId();
    String topUrl="";
//    String menuUrl="/servlet/com.sino.ams.security.servlet.MenuResourceAuthServlet?resourcePid="+resourcePid;
    String menuUrl="/servlet/com.sino.sinoflow.framework.resource.servlet.MenuResourceAuthServlet?resourcePid="+resourcePid;
    String homePage="/home.jsp";
    String home= StrUtil.nullToString(request.getParameter("home"));
//    System.out.println("home = " + home);
//    menuUrl="";
//    homePage="";
    if (home.equals("1")) {
        //山西：待办区域
        homePage="/servlet/com.sino.sinoflow.servlet.PendingTray2";
    }
%>
<frameset rows="131,*" framespacing="0" frameborder="no" border="0" onload="window.focus()">
	<frame src="/servlet/com.sino.ams.security.servlet.RootResourceAuthServlet" name="topFrame" target="contents" scrolling="no" noresize="noresize" >
    <frameset name="contentFrm" cols="158,9,*" framespacing="0" frameborder="no" border="0">
        <frame name="contents" src="<%=menuUrl%>" scrolling="no" noresize target="main">
        <frame name="bar" src="/t_l.htm" scrolling="no" noresize>
        <frameset rows="*,25" framespacing="0" frameborder="no" border="0" onload="window.focus()">
            <frame name="main" src="<%=homePage%>" scrolling="yes">
            <frame src="/bottom.html" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" />
        </frameset>
    </frameset>
</frameset>
<noframes>
<body>
<p>此网页使用了框架，但您的浏览器不支持框架。</p>
</body>
</noframes>
</html>