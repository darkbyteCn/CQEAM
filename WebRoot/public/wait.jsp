<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by HERRY.
  Date: 2008-5-6
  Time: 17:17:59
  function: 等待页面，调用方法请参考/workorder/orderProcessView.jsp  do_ShowDetail（）方法
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    String title = StrUtil.nullToString(request.getParameter("title"));
    String src = request.getParameter("src");
    src += "?";
    Enumeration emu = request.getParameterNames();
    String elementName = "";
    while (emu.hasMoreElements()) {
        elementName = (String) emu.nextElement();
        src += elementName + "=" + StrUtil.nullToString(request.getParameter(elementName)) + "&";
    }
%>
<html>
<head>
    <title><%=title%>
    </title>
    <script language="javascript">
        function openSrc() {
            wait.style.display = "none";
            ok.style.display = "";
        }
    </script>
</head>
<body leftmargin=0 topmargin=0>
<div id="wait"><BR>
    <center>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <!--<FONT  align="center"  color="black">正在打开页面，请稍候…… </FONT>-->
        <table border="1" width="37%" cellspacing="0" cellpadding="4" style="border-collapse: collapse"
               bgcolor="#FFFFEC" height="87">
            <tr>
                <td bgcolor="#3399FF" style="font-size:12px;color:#ffffff" height=24>数据载入中...</td>
            </tr>
            <tr>
                <td style="font-size:12px;line-height:200%" align=center>正在读取数据，请稍等...

                    <marquee style="border:1px solid #000000" direction="right" width="300" scrollamount="5"
                             scrolldelay="10" bgcolor="#ECF2FF">
                        <table cellspacing="1" cellpadding="0">
                            <tr height=8>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                                <td bgcolor=#3399FF width=8></td>
                                <td></td>
                            </tr>
                        </table>
                    </marquee>
                </td>
            </tr>
        </table>
    </center>
</div>
<div id="ok" style='display:none'>
    <iframe name='eee' FRAMEBORDER="0" MARGINWIDTH="0" MARGINHEIGHT="0" scrolling="yes" width="100%" height="100%"
            onload="openSrc()" src="<%=src%>"/>
</div>
</body>
</html>
