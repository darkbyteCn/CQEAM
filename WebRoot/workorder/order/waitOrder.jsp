<%--
  User: zhoujs
  Date: 2007-10-30
  Time: 16:34:21
  Function:
--%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK"%>


<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
String val=request.getParameter("val");
%>
<html>
<head>
<title>待归档工单.</title>
<script language="javascript">
	function openSrc(){
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
<table border="1" width="37%" cellspacing="0" cellpadding="4" style="border-collapse: collapse" bgcolor="#FFFFEC" height="87">
 <tr>
  <td bgcolor="#3399FF" style="font-size:12px;color:#ffffff" height=24>数据载入中...</td>
 </tr>
 <tr>
  <td style="font-size:12px;line-height:200%" align=center>正在读取数据，请稍等...

  <marquee style="border:1px solid #000000" direction="right" width="300" scrollamount="5" scrolldelay="10" bgcolor="#ECF2FF">
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
  </tr></table></marquee></td>
 </tr>
</table>
</center>

</div>
<div id="ok" style='display:none'>
<iframe name='eee' FRAMEBORDER="0" MARGINWIDTH="0" MARGINHEIGHT="0" scrolling="yes" width="100%" height="100%" onload="javascript:openSrc()" src='/servlet/com.sino.ams.workorder.servlet.OrderDiffServlet?systemid=<%=val%>'/>
</div>
</body>
</html>
