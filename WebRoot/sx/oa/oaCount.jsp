<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>
 <HEAD>
  <TITLE> 待办统计 </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
 </HEAD>

<body BGCOLOR="FFFFFF"  leftmargin="0" topmargin="0">
 <%
     int  count=(Integer)request.getAttribute(ReqAttributeList.INBOX_COUNT);
     String url="/servlet/com.sino.sso.servlet.PortalLoginServlet?home=1";
 %>
<center>
    <table width="95%" border="0" cellspacing="0" cellpadding="0">
        <tr><td style='height:10px'></td></tr>
        <tr>
            <td align="center" valign="top" >
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr align="left">
                        <td width="1%" align="left"><img src="/images/arrow_oa.gif"></td>
                        <td align="left" NOWRAP>&nbsp;<a  href="#"
                        onclick="doStep('<%=url%>')">资产管理 【<font style="color:red;font-weight:bold;"><%=count%></font>】 件</a></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</center>
</body>
</HTML>
