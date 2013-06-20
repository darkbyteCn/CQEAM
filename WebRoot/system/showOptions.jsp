<%--
  User: zhoujs
  Date: 2007-9-23
  Time: 17:32:17
  Function:
--%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>


<html><head><title>请选择</title>
<meta content="text/html; charset=GBK" http-equiv=Content-Type>
<script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script language="javascript">
    function init() {
      var mySelect = document.all('mySelect');
      var optionArr= window.dialogArguments;
      if(optionArr!=null){
	   	  for(i=0;i<optionArr.length;i++){
			var index = mySelect.options.length;
	         mySelect.options[index] = new Option(optionArr[i].text,optionArr[i].value);
             mySelect.options[index].selected=optionArr[i].selected;
	      }
	  }
    }

    function do_cancel() {
        window.close();
    }

    function do_select() {
        var returnValue=getSelectValue('mySelect',';');
        window.returnValue=returnValue;
        window.close()
    }

    function do_check() {
        if (event.keyCode == 26) {
            do_select();
        }else if(event.keyCode==54){
            do_cancel();
        }        
    }
</script>
</head>

<body bgColor="#E4E4E4" bottomMargin=0  leftMargin=10.5 topMargin=10 onload="init();" onkeydown="do_check();">
<form name= "form1">
  <table width="100%" border="0">
    <tr valign="top">
      <td colspan="2" height="191">
        <select name="mySelect" size="12" style="width:450" multiple ondblclick="do_select()">
        </select>
      </td>
      <td width="10%" height="191">
        <p>
          <input type="button" name="Submit" value="确定" onClick="do_select()"><BR><br>
          <input type="button" name="Submit2" value="取消" onClick="do_cancel()">
        </p>
      </td>
    </tr>
  </table>
</form>
</BODY>
</HTML>

