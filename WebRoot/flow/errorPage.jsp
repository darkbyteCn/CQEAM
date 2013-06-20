<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.sinoflow.constant.ReqAttributeList" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=gb2312" %>
<%@ page isErrorPage="true" %>
<html>
<head>
<title>错误信息</title>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    String msg = (String) request.getAttribute(ReqAttributeList.ERROR_MSG);
    if (StrUtil.isEmpty(msg)) {
        if (exception != null) {
            msg = exception.getMessage();
        } else {
            msg = "流程转向错误，请通知系统管理员";
        }
    }

%>
<body bgcolor="EDE3C9">

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="50">&nbsp;</td>
    </tr>
    <tr>
      
    <td> 
      <table width="440" border="1" cellspacing="0" cellpadding="0" align="center" bordercolor="#000000">
        <tr bordercolor="EDE3C9"> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/error.jpg" width="440" height="23"></td>
              </tr>
              <tr bgcolor="EBD59C"> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10%"><img src="/images/jinghao.jpg"></td>
                      <td width="79%" class="font2">系统错误信息：</td>
                      <td width="11%"></td>
                      </tr>
                      <tr>
                      <td width="10%"></td>
                      <td width="79%" align='center' class="font2"><%=msg%> </td>
                      <td width="11%"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr bgcolor="EBD59C"> 
                <td>
                  <div align="center"><img src="/images/ok.jpg" width="96" height="25" STYLE="cursor:'hand'" onclick="window.close()"></div>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
  </table>
</body>

</html>
