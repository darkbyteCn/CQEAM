<%@ page contentType="text/html; charset=gb2312" language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>    
<title>请确认</title>
</head>
<body>
<form id="form1" name="getPriority" method="post" action="">
<script type="text/javascript">
    function radioValue(radio) {
        var len = radio.length;
        for(var i = 0; i < len; i++) {
            if(radio[i].checked)
                return radio[i].value;
        }
        return "";
    }

    function do_close() {
        var priority = radioValue(document.getElementsByName("priority"));
        var message = document.getElementById("userMessage").value;
        window.returnValue = "{priority:'" + priority + "',message:'" + message + "'}";
        window.close();
    }

    function do_cancel() {
        window.returnValue = "";
        window.close();
    }
</script>
  <table width=100% border="0">
  <tr align="top">
  <td width="5%"></td>
  <td width="90%" align="left">
  <p>文件传送紧急程度:</p>
  </td>
  <td width="5%" align="left">
  <input type="button" name="Submit" value="确定" onClick="do_close()">
  </td>
  </tr>
  <tr align="top">
  <td width="5%"></td>
  <td align="left">
  <p>
    <input type="radio" name="priority" value="0" checked/>
    正常
      <input type="radio" name="priority" value="1" />
      平急
      <input type="radio" name="priority" value="2" />
      紧急
      <input type="radio" name="priority" value="3" />
      特急  </p>
  </td>
  <td align="left">
  <input type="button" name="Cancel" value="取消" onClick="do_cancel()">
  </td>
  </tr>
  <tr>
  <td width="5%"></td>
  <td>
  <br/>传送信息:<br/>
    <textarea name="userMessage" cols="40" rows="8"></textarea>
    <br />
  </td>
  </tr>
  </table>
</form>
</body>
</html>
