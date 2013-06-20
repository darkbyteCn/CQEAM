<%@ page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
  <head>
  	<link href="/WebLibary/css/style.css" rel="stylesheet" type="text/css" />
	<script type = "text/javascript" src = "/WebLibary/js/Constant.js"></script>
	<script type = "text/javascript" src = "/WebLibary/js/CommonUtil.js"></script>
	<script type = "text/javascript" src = "/WebLibary/js/FormValidate.js"></script>
	<script type = "text/javascript" src = "/WebLibary/js/FormProcess.js"></script>
	<script type = "text/javascript" src = "/WebLibary/js/SelectProcess.js"></script>
  </head>
  <jsp:include page = "/message/MessageProcess" flush = "true" />
  <%
      String selOpt2 = (String)request.getAttribute("selOpt");
      System.out.println(selOpt2);
  %>
  <body>
  	<table>
          <tr>
              <td width="10px"></td>
              <td colspan="2" width="350">
                  名称:&nbsp;
                  <input width="250px" type="text" name="selName" value="">
              </td>
          </tr>
          <tr>
              <td width="10px"></td>
              <td>
  				<select name="op" multiple="multiple" style="width: 200;height: 360;" ondblclick="ok();" onchange="groupChange();" title="双击选中">
  					<%=selOpt2%>
			  	</select>
  			</td>
  			<td valign="top">
  				<p><input type="button" value="确 定" onclick="ok();" class="xfBtn"/></p>
  				<p><input type="button" value="取 消" onclick="window.close();" class="xfBtn"/></p>
  			</td>
  		</tr>
  	</table>
  </body>
</html>
<script type="text/javascript">
	document.onkeydown=escWindow;

    function groupChange() {
        var selected = getSelectValue("op", ";");
        document.getElementById("selName").value = selected;
    }

    function ok(){
/*
        var selected = getSelectValue("op", ";");
		var arr = explode(selected, ";");
        alert("arr = " + arr);
*/
        var arr = explode(document.getElementById("selName").value, ";");
        window.returnValue = arr;
        window.close();
	}

    function escWindow() {
        if((event.keyCode==27))
		    window.close();
    }
</script>