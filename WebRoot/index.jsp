<%@ page contentType="text/html;charset=GBK" language="java" isErrorPage="true" %>

<html>
  <head>
    <title></title>
  </head>
  
  <body onload="onLoad()">

  </body>

  <script language="javascript">
      function onLoad() {
          h = window.screen.height;
          w = window.screen.width;
          f1 = "top=0,left=0,width=" + w + ",height=" + h + ",toolbar=no,location=no,directories=no,status=no,menubar=no,scroll=no,scrollbars=no,resizable=yes";
          window.open("login.jsp","newwin", f1);
          window.opener=null;
          window.open( '' , '_self' );
          setTimeout("window.close()",1000);
      }
  </script>
</html>
