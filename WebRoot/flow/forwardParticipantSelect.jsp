<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.sino.sinoflow.util" %>
<%@ page import="java.util.List" %>
<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择会签组别或人员
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("gbk");
%>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<script type ="text/javascript">
    var sortitems = 1;  // Automatically sort items within lists? (1 or 0)
    var flowProp;
</script>
<html>
  <head>
      <title>选择经办人</title>
      <link rel="stylesheet" type="text/css" href="/WebLibary/cms_css/cms_css.css">
  </head>
<body onload="fill_select()" onunload="doUnload()">
  <form name="mainFrm" action="">
      <script type="text/javascript">
          var arg = window.dialogArguments;
          var partiStr = arg[0];
          if(partiStr.charAt(0) == '[')
              partiStr = partiStr.substring(1, partiStr.length - 1);
          eval("flowProp = " + partiStr);
          var hint = arg[1];

          function fill_select() {
              var partiBox = document.getElementById("sf_curReviewUsers")
              var i;
              var opt;
              var parti_usernames =  getJsonData(partiStr, "usernames:").replaceAll(",", ";");
              var parti_realnames = getJsonData(partiStr, "realnames:").replaceAll(",", ";");
              var partiUsersArr = parti_usernames.split(";");
              var partiRealsArr = parti_realnames.split(";")
              for(i = 0; i < partiUsersArr.length; i++) {
                  opt = new Option();
                  opt.value = partiUsersArr[i];
                  opt.text = partiRealsArr[i];
                  partiBox.options[partiBox.options.length] = opt;
              }
              document.getElementById("approveContentSS").value = hint;
          }

          function do_close() {
              var fbox = document.getElementById("sf_curReviewUsers");
              var i;
              if(fbox.options.selectedIndex < 0) {
                  alert("请选择经办人！");
                  return;
              }
              var userStr = fbox.options[fbox.options.selectedIndex].value;
              var realStr = fbox.options[fbox.options.selectedIndex].text;
              window.returnValue = "[{realnames:'" + realStr + "', usernames:'" + userStr +"', opinion:'" +
                                   document.getElementById("approveContentSS").value + "'}]";
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr><td width="20%"><br></td><td><br></td></tr>
        <tr align="top">
		<td class="text_gray3" align="right">任务:</td>
		<td class="text_gray3"><script type="text/javascript">document.writeln(flowProp.taskName)</script></td>
		</tr>
        <!--tr align="top">
		<td class="text_gray3" align="right">组别:</td>
		<td class="text_gray3"><script type="text/javascript">document.writeln(flowProp.groupName);</script></td>
		</tr>
        <tr align="top">
		<td class="text_gray3" align="right">角色:</td>
		<td class="text_gray3"><script type="text/javascript">document.writeln(flowProp.roleName);</script>
        </tr-->
          <tr><td><br></td><td><br></td></tr>
          <tr valign="top">
        <td class="text_gray3" align="right"><br/>待选名单:</td>
          <td colspan="2" ><select name="sf_curReviewUsers" size="8" style="width:300px" onchange='document.getElementById("approveContentSS").select();document.getElementById("Submit").focus()'>
          </select></td>
        </tr>
          <tr><td><br></td><td><br></td></tr>
          <tr>
              <td align="right" class="text_gray3">请填写意见：</td>
                  <td>
                      <label>
                      <textarea name="approveContentSS" style="width:300px" rows="5"></textarea>
                  </label>
                  </td>
          </tr>
      </table>
      <br>
      <table width="100%">
          <tr>
              <td width="25%"></td>
              <td align="center" width="20%">
              <input type="button" name="Submit" id="Submit" class="but2" value="确定" onClick="do_close()">
              </td>
              <td width="10%"></td>
              <td align="center" width="20%">
              <input type="button" name="Cancel" id="Cancel" class="but2" value="取消" onClick="do_cancel()">
              </td>
              <td width="25%"></td>
          </tr>
      </table>
  </form>
</body>
</html>