<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  User: zhoujs
  Date: 2007-10-30
  Time: 10:22:03
  Function:Excel上传工单
--%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Excel工单上传</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>

    <script type="text/javascript">
            var ArrAction0 = new Array(true, "提交", "action_save.gif", "上传工单", "doSub");
            var ArrAction1 = new Array(true, "粘贴", "action_sign.gif", "粘贴", "aa");
            var ArrActions = new Array(ArrAction0, ArrAction1);
            var ArrSinoViews = new Array();
            var ArrSinoTitles = new Array();
    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<form name="mainFrm" action="/servlet/com.sino.pda.ExcelOrderSubmit" method="post" enctype="multipart/form-data">
    <script type="text/javascript">
        printTitleBar("Excel上传工单")
    </script>
   <input type="file" name="flName"><input type="button" name="sub" value="提交工单" onclick="doSub();"><br>
    <%=WebConstant.WAIT_TIP_MSG%>
</form>

</body>
<script type="text/javascript">
 
     function doSub() {
         document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
         mainFrm.submit();
     }
</script>
</html>
