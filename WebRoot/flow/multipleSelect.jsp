<%@ page import="com.sino.sinoflow.util" %>
<%@ page import="java.util.List" %>

<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择会签组别或人员
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
    String selectedGroups = request.getParameter("selectedGroups");
    if(selectedGroups == null) {
        selectedGroups = "";
    } else if(selectedGroups.startsWith("\"") || selectedGroups.startsWith("'")) {
        selectedGroups = selectedGroups.substring(1,selectedGroups.length()-1);
    }
%>
<script type ="text/javascript">
    var sortitems = 1;  // Automatically sort items within lists? (1 or 0)

    function move(fbox,tbox) {
        for(var i=0; i<fbox.options.length; i++) {
            if(fbox.options[i].selected && fbox.options[i].value != "") {
                var no = new Option();
                no.value = fbox.options[i].value;
                no.text = fbox.options[i].text;
                tbox.options[tbox.options.length] = no;
                fbox.options[i].value = "";
                fbox.options[i].text = "";
            }
        }
        BumpUp(fbox);
    }

    function moveall(fbox,tbox) {
        for(var i=0; i<fbox.options.length; i++) {
            if(fbox.options[i].value != "") {
                var no = new Option();
                no.value = fbox.options[i].value;
                no.text = fbox.options[i].text;
                tbox.options[tbox.options.length] = no;
                fbox.options[i].value = "";
                fbox.options[i].text = "";
            }
        }
        BumpUp(fbox);
    }

    function BumpUp(box) {
        for(var i=0; i<box.options.length; i++) {
            if(box.options[i].value == "")  {
                for(var j=i; j<box.options.length-1; j++)  {
                    box.options[j].value = box.options[j+1].value;
                    box.options[j].text = box.options[j+1].text;
                }
                var ln = i;
                break;
            }
        }
        if(ln < box.options.length)  {
            box.options.length -= 1;
            BumpUp(box);
        }
    }

    var flowProp;
</script>
<html>
  <head>
      <title><%=header%></title>
  </head>
<body>
  <form name="mainFrm" action="">
      <script type="text/javascript">
          function do_close() {
              var sbox = document.getElementById("sf_selectedReviewUsers");
              if (sbox.options.length > 0) {
                  var groupStr = "";
                  for(var i=0; i<sbox.options.length; i++) {
                      if(sbox.options[i] != null || sbox.options[i].value != "") {
                          if(groupStr == "")
                               groupStr = sbox.options[i].value;
                          else
                               groupStr += ";" + sbox.options[i].value;
                      }
                  }
                  window.returnValue = groupStr;
                  window.close();
              } else {
                  alert("请" + "<%=header%>" + "！");
              }
          }

          function do_cancel() {
              window.returnValue = "";
              window.close();
          }

      </script>
      <table width="100%" border="0">
	  	<tr valign="top">
		<td colspan="2">待选名单:</td>
		<td width="5%"></td>
		<td>选中名单:</td>
		</tr>
        <tr valign="top">
          <td colspan="2" height="230"><select multiple name="sf_curReviewUsers" size="12" style="width:250px" ondblclick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)">
            <%
                  List groupList = util.explodeToList(groupNames, ";");
                  List selGroups = util.explodeToList(selectedGroups, ";");
                  for(int i = 0; i < groupList.size(); i++) {
                      String group = groupList.get(i).toString().trim();
                      boolean found = false;
                      for(int j = 0; j < selGroups.size(); j++)  {
                          if(group.equals(selGroups.get(j).toString().trim())) {
                            found = true;
                            break;
                          }
                      }
                      if(!found) {
                          String displayStr = groupList.get(i).toString().trim();

              %>
            <option value="<%=displayStr%>"><%=displayStr%></option>
            <%
                      }
                  }
              %>
          </select></td>
          <td width="4%" align="center"><input type="button" class="but2" value=" >  " onClick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)" name="B1">
              <br>
              <input type="button" class="but2" value=" <  " onClick="move(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)" name="B2">
              <br>
             <input type="button" class="but2" value=" >> " onClick="moveall(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)" name="B3">
              <br>
              <input type="button" class="but2" value=" << " onClick="moveall(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)" name="B4">
              <br>
          </td>
          <td width="27%" align="right"><select multiple name="sf_selectedReviewUsers" size="12" style="width:250px" ondblclick="move(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)">
              <%
                    List showGroups = util.explodeToList(selectedGroups, ";");
                    if (showGroups != null && showGroups.size() > 0) {
                        String displayStr;
                        for (int i = 0; i < showGroups.size(); i++) {
                            displayStr = showGroups.get(i).toString().trim();
                             if(!displayStr.equals("")) {

                %>
              <option value="<%=displayStr%>"><%=displayStr%></option>
              <%
                           }
                        }
                    }
                %>
          </select>          </td>
         </tr>
          <tr align="top">
        <td align="right">
        <input type="button" name="Submit" class="but2" value="确定" onClick="do_close()">
        </td>
        <td width="20%"></td>
        <td>
        <input type="button" name="Cancel" class="but2" value="取消" onClick="do_cancel()">
        </td>
        </tr>
        <tr><td></td></tr>
      </table>
  </form>
</body>
</html>