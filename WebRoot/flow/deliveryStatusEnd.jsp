<%@ page contentType="text/html; charset=gb2312" language="java" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
<title>请确认</title>
<style type="text/css">
<!--
.STYLE1 {font-size: 12px}
-->
</style>
</head>
<body onbeforeunload="doUnload()" >
<form id="form1" name="getPriority" method="post" action="">
    <script type="text/javascript">
        function do_close() {
            window.returnValue = "ok";
            window.close();
        }

        function do_cancel() {
            window.returnValue = "";
            window.close();
        }

        function doUnload() {
            if(!window.returnValue)
              window.returnValue = "";
        }
        
    </script>
  <table border="0">
  <tr align="top">
  <td width="84%" align="left">
  <p class="STYLE1">本案件将办理完毕, 是否办结?</p>  </td>
  <td width="16%" align="left">
  <input type="button" class="but2" name="Submit" value="确定" onClick="do_close()">
  </td>
  </tr>
  <tr align="top">
  <td align="left"><span class="STYLE1">如按“确定”按扭, 案件将办理结束, 系统归档; 如按“取消”按扭, 将回到案件办理界面, 您可以继续办理。</span>  </td>
  <td align="left">
  <input type="button" class="but2" name="Cancel" value="取消" onClick="do_cancel()">
  </td>
  </tr>
  </table>
</form>
</body>
</html>
