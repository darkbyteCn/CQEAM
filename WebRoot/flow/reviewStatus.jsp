<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.exception.ContainerException" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="com.sino.sinoflow.util" %>
<%@ page import="java.util.List" %>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择会签组别或人员
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    RowSet reviewStatus = (RowSet) request.getAttribute(WebAttrConstant.REVIEW_STATUS_ROWSET);
    Row row;
    String allUsers = "";
    String unreadUsers = "";
    int type;
    try {
        for(int i = 0; i < reviewStatus.getSize(); i++) {
            row = reviewStatus.getRow(i);
            type = Integer.parseInt(row.getStrValue("SFACT_COMMENT_TYPE"));
            if((type & 0x20) == 0x20) {
                if(unreadUsers.equals("")) {
                    unreadUsers += row.getStrValue("SFACT_COMMENT_USERS");
                } else {
                    unreadUsers += ";" + row.getStrValue("SFACT_COMMENT_USERS");
                }
            } else {
                allUsers += row.getStrValue("SFACT_COMMENT_USERS");
            }
        }
    } catch(ContainerException e) {

    }
    String readUsers = util.getReadUsers(allUsers, unreadUsers);
%>
<html>
  <head>
      <title>阅示状态</title>
  </head>
<body onbeforeunload=do_close()>
  <form name="mainFrm" action="">
      <script type="text/javascript">
          function do_close() {
              window.close();
          }

      </script>
      <table width="100%" border="0">
	  	<tr align="top">
		<td width="18%">阅示类型:</td>
		<td width="18%">顺序处理</td>
		<td align="right">
		<input type="button" name="Submit" value="确定" onClick="do_close()">
		</td>
		</tr>
		<tr align="top">
		<td>结束选项:</td>
		<td width="18%">等待</td>
		</tr>
	  	<tr valign="top">
		<td colspan="2">等待阅示:</td>
		<td>阅示完毕:</td>
		</tr>
        <tr valign="top">
          <td colspan="2" height="191"><select name="sf_waitForReview" size="8" style="width:200px" disabled="disabled">
              <%
                  List unreadArray = util.explodeToList(unreadUsers, ";");
                  String userStr;
                  for(int i = 0; i < unreadArray.size(); i++) {
                      userStr = unreadArray.get(i).toString().trim();
                %>
              <option value="<%=userStr%>"><%=userStr%></option>
              <%
                  }
              %>
           </select></td>
          <td width="20%" align="right"><select name="sf_reviewFinished" size="8" style="width:200px" disabled="disabled">
              <%
                  List readArray = util.explodeToList(readUsers, ";");
                  String user;
                  for(int i = 0; i < readArray.size(); i++) {
                      user = readArray.get(i).toString().trim();
                %>
              <option value="<%=user%>"><%=user%></option>
              <%
                  }
              %>
          </select>          </td>
        </tr>
      </table>
  </form>
</body>
</html>