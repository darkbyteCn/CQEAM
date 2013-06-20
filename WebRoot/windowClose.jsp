<%--
  User: zhoujs
  Date: 2007-9-27
  Time: 22:17:51
  Function: 转向关闭页面，可传入返回参数
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<html>
<script type="text/javascript">
    <%
 String retValue=StrUtil.nullToString(request.getParameter("retValue"));
 if(!retValue.equals("")){
    if(retValue.equals("order")){%> //工单提交时从收件箱或在办箱提交时刷新
      window.top.opener.document.forms[0].submit();
    <%

    }else if(retValue.equals("refresh")){ %>
     window.top.opener.location.reload();
    <%}else{
    %>
    window.returnValue = "<%=retValue%>";
    <%
           }
           }
    %>
    window.top.close();
</script>
</html>