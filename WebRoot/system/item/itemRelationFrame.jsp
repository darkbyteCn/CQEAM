<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by HERRY.
  Date: 2007-11-22
  Time: 15:17:11
--%>
<html>
<%
    String priv = StrUtil.nullToString(request.getParameter("priv"));
    String title = "";
    if (priv.equals("SETUP")) {
        title = "维护";
    } else if (priv.equals("QUERY")) {
        title = "查询";
    }
%>
<head><title>设备关系<%=title%>框架</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<%=WebConstant.WAIT_TIP_MSG%>
<body>
<script type="text/javascript">
    printTitleBar("设备关系<%=title%>")
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible"
</script>

<input type="hidden" name="priv" value="<%=priv%>">
<iframe name="left"  frameborder="1" border="0" marginwidth="0" src="/servlet/com.sino.ams.system.item.servlet.ItemRelationLeft"
                height="100%" width="22%" />
<iframe name="itemMain" frameborder="1" border="0" marginwidth="0" scrolling="auto" noresize src="/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet"
                height="100%" width="78%"/>
</body>
<!--<frameset cols="163,*" border="0" frameborder="0" marginwidth="0" framespacing="0px">
            <frame name="left" target="itemMain" frameborder="0" border="0" marginwidth="0" src="/servlet/com.sino.ams.system.item.servlet.ItemRelationLeft">
            <frame name="itemMain" frameborder="0" border="0" marginwidth="0" scrolling="auto" noresize src="/servlet/com.sino.ams.system.item.servlet.ItemRelationServlet">
</frameset>-->

</html>