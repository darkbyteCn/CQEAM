<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-4-8
  Time: 11:53:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<head>
    <title>设置新折旧年限</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<body topmargin=0 leftmargin=0>
<form name="mainForm" action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsChangeZJYearsServlet"  method="post">
    <table width=100% align="center">
        <tr>
            <td>
                <script>
                    printTitleBar("请设置新折旧年限")
                </script>
            </td>
        </tr>
        <tr>
            <td align=left>
                <img src="/images/eam_images/ok.jpg" alt="确定" onclick="javascript:add()">
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" name="years" value="">
            </td>
        </tr>

    </table>
</form>
<script language="javascript">
     function add(){
         var years = document.mainForm.years.value;
         if(years == ""){
             document.mainForm.years.value = "0";
         }
         window.returnValue=document.mainForm.years.value;
         window.close();
     }
</script>
</body>

</html>