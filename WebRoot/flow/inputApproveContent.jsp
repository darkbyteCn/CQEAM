<%--
  User: Herry
  Date: 2009-4-13
  Time: 17:13:55
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("gbk");
    String flowDesc = request.getParameter("flowDesc");
    if(flowDesc == null) {
        flowDesc = "";
    } else if(flowDesc.startsWith("\"") || flowDesc.startsWith("'")) {
        flowDesc = flowDesc.substring(1,flowDesc.length()-1);
    }
%>
<html>
<head>
<title>填写审批意见</title>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
</head>
<body onLoad="doLoad()" onunload="doUnload()">
<table width="100%" height="100%">
  <tr height="40px">&nbsp;</tr>
  <tr height="20px">
    <td width="5%"></td>
    <td valign="bottom" colspan="2">请填写意见：</td>
    <td width="5%"></td>
  </tr>
  <tr>
    <td width="5%"></td>
    <td valign="top" colspan="2"><textarea name="approveContent" style="width:100%;" rows="15" class="blueborder"><%=flowDesc%></textarea></td>
    <td width="5%"></td>
  </tr>
  <tr>
    <td width="5%"></td>
    <td align="center"><input type="button" value="确定" class="but4" onClick="retVal();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" class="but4" value="取消" onClick="doCancel()"/></td>
    <td width="5%"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
<script type="text/javascript">
      function retVal(){
          window.returnValue = document.getElementById("approveContent").value;
          window.close();
      }

      function doLoad() {
          document.getElementById("approveContent").select();
      }

      function doCancel() {
          window.close();
      }

      function doUnload() {
          if(!window.returnValue)
            window.returnValue = "";
      }
  </script>
</html>
