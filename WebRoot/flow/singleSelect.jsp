<%@ page import="com.sino.sinoflow.util"%>
<%@ page import="java.util.List" %>
<%--
  User: Yung,Kam Hing
  Date: 2008-9-08
  Time: 10:05:36
  Function:选择用户当前有专业的组别
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    String header = request.getParameter("header");
    String groupNames = request.getParameter("groups");
    if(groupNames == null) {
        groupNames = "";
    } else if(groupNames.startsWith("\"") || groupNames.startsWith("'")) {
        groupNames = groupNames.substring(1,groupNames.length()-1);
    }
%>
<html>
  <head>
      <title><%=header%></title>
   <script type="text/javascript">
       function do_select() {
           var idx = document.getElementById("sf_curGroupId").selectedIndex;
           if (idx > -1) {
               window.returnValue = document.getElementById("sf_curGroupId").options[idx].value;
               window.close();
           } else {
               alert("请" + "<%=header%>");
           }
       }

       function do_close() {
            var idx = document.getElementById("sf_curGroupId").selectedIndex;
            if (idx > -1) {
                window.returnValue = document.getElementById("sf_curGroupId").options[idx].value;
                window.close();
            } else {
                alert("请" + "<%=header%>");
            }
        }

       function do_cancel() {
           window.returnValue = "";
           window.close();
       }

   </script>
  </head>
<body>
  <!--form name="mainFrm" action=do_select() -->
    <table width="100%" border="0">
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>

    <tr valign="top">
        <td width="5%"></td>
      <td width="90%" colspan="2" height="191">
        <select name="sf_curGroupId" size="8" style="width:250px" ondblclick="do_select()">
        <%
            List groups = util.explodeToList(groupNames, ";");
            for(int i = 0; i < groups.size(); i++) {
                String group = groups.get(i).toString().trim();
            if(!groups.equals("")) {
        %>
        <option value="<%=group%>"><%=group%></option>
        <%
                }
            }
        %>
        </select>
      </td>
      </tr>
      </table>
      <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>        
      <tr>
      <td align="center"><input type="button" name="Submit" class="but2" value="确定" onClick="do_select()"></td>
       <td align="center"><input type="button" name="Cancel" class="but2" value="取消" onClick="do_cancel()"></td>
    </tr>
  </table>
  <!--/form-->
</body>
</html>