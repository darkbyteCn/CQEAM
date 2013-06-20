<%@ page import="com.sino.sinoflow.util" %>
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
    String copyId = util.getReqPara(request, "sf_copyID");
    String opinion = util.getReqPara(request, "opinion");
    if(opinion == null)
        opinion = "";
%>
<html>
<head>
<title>填写意见</title>
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
    <td valign="top" colspan="2"><textarea name="opinion" style="width:100%;" rows="15" class="blueborder"><%=opinion%></textarea></td>
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
</table>
<input type="hidden" id="copyId" value="<%=copyId%>" />
</body>
<script type="text/javascript">
      function retVal(){
          var url;
          url = "/servlet/com.sino.sinoflow.servlet.SetCopyOpinion?copyId=<%=copyId%>&opinion='"
                  + document.getElementById("opinion").value + "'";
          makeRequest(url, ajaxFunction);
          window.returnValue = document.getElementById("opinion").value;
          window.close();
      }

      function doLoad() {
          document.getElementById("opinion").select();
      }

      function doCancel() {
          window.close();
      }

      function doUnload() {
          if(!window.returnValue)
            window.returnValue = "";
      }

      function makeRequest(url, alertContents) {

          http_request = false;

          if (window.XMLHttpRequest) { // Mozilla, Safari,...
              http_request = new XMLHttpRequest();
              if (http_request.overrideMimeType) {
                  http_request.overrideMimeType('text/xml');
              }
          } else if (window.ActiveXObject) { // IE
              try {
                  http_request = new ActiveXObject("Msxml2.XMLHTTP");
              } catch (e) {
                  try {
                      http_request = new ActiveXObject("Microsoft.XMLHTTP");
                  } catch (e) {}
              }
          }

          if (!http_request) {
              alert('不能启动 xml http 服务！');
              return false;
          }
          http_request.onreadystatechange = alertContents;
          http_request.open('POST', url, false);
          http_request.send(null);
          return true;
      }

      function ajaxFunction() {
          try {
              if (http_request.readyState == 4 || http_request.readyState == "complete") {
                  var resText = http_request.responseText;
                  if(resText.indexOf("404") >= 0) {
                      alert("找不到签收 servlet，请通知系统管理员！");
                      ajaxReturn = "";
                      return;
                  }
                  if (resText.indexOf("ERROR") >= 0) {
                      var res;
                      eval("res = " + resText);
                      alert(res[0].message);
                      ajaxReturn = "";
                      return;
                  }
                  ajaxReturn = resText;
             }
          } catch(e) {
              alert("服务器数据错误，请通知系统管理员！" + e.message);
              ajaxReturn = "";
          }
      }
  </script>
</html>
