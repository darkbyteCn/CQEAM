<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by wwb.
  User: V-wangwenbin
  Date: 2007-10-10
  Time: 17:08:16
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>提交成功页面</title>
    <script type="text/javascript">
        var msg = '<%=StrUtil.nullToString(request.getAttribute("SUCCESS_MSG"))%>' ;
        if (msg != '') {
            alert(msg);
        }
        window.opener.document.forms[0].submit();
        window.close();
    </script>
</head>

<body>
</body>
</html>
