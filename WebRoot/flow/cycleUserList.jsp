<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.exception.ContainerException" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>

<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择会签组别或人员
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
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
</script>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    String cycleType = request.getParameter("sf_cycleType");
    RowSet cycleUsers = (RowSet) request.getAttribute(WebAttrConstant.CYCLE_USER_ROWSET);
%>
<html>
  <head>
      <%
          if(cycleType.equals("0")) {
      %>
      <title>选择会签人员</title>
      <%
          } else {
      %>
      <title>选择会签组别</title>
      <%
          }
      %>
  </head>
<body onbeforeunload=do_close()>
  <form name="mainFrm" action="">
      <script type="text/javascript">
          function do_close() {
              var sbox = document.getElementById("sf_selectedCycleUsers");
              if (sbox.options.length > 0) {
                  var retStr = "";
                  for(var i=0; i<sbox.options.length; i++) {
                      if(sbox.options[i] != null || sbox.options[i].value != "") {
                          if(retStr != "")
                               retStr += ";" + sbox.options[i].value;
                          else
                               retStr += sbox.options[i].value;
                      }
                  }
                  window.returnValue = retStr;
                  window.close();
              } else {
                  var type = <%=cycleType%>
                  if(type == "0")
                     alert("请选择会签人员！");
                  else
                     alert("请选择会签组别！");
              }
          }

      </script>
      <table width="100%" border="0">
	  	<tr valign="top">
		<td colspan="2">待选名单:</td>
		<td width="7%"></td>
		<td>选中名单:</td>
		</tr>
        <tr valign="top">
          <td colspan="2" height="191"><select multiple name="sf_curCycleUsers" size="8" style="width:250px" ondblclick="move(this.form.sf_curCycleUsers,this.form.sf_selectedCycleUsers)">
            <%
                  if (cycleUsers != null && cycleUsers.getSize() > 0) {
                      Row row;
                      String groupStr = "";
                      for (int i = 0; i < cycleUsers.getSize(); i++) {
                          try {
                              groupStr = "";
                              row = cycleUsers.getRow(i);
                              if(cycleType.equals("0")) {
                                 groupStr = row.getStrValue("USERNAME")+"/"+row.getStrValue("GROUP_NAME");
                              } else {
                                 groupStr = row.getStrValue("GROUP_NAME");
                              }
                          } catch(ContainerException e) {

                          }
              %>
            <option value="<%=groupStr%>"><%=groupStr%></option>
            <%
                      }
                  }
              %>
          </select></td>
          <td width="7%"><input type="button" class="but2" value=" >  " onClick="move(this.form.sf_curCycleUsers,this.form.sf_selectedCycleUsers)" name="B1">
              <br>
              <input type="button" class="but2" value=" <  " onClick="move(this.form.sf_selectedCycleUsers,this.form.sf_curCycleUsers)" name="B2">
              <br>
              <input type="button" class="but2" value=" >> " onClick="moveall(this.form.sf_curCycleUsers,this.form.sf_selectedCycleUsers)" name="B3">
              <br>
              <input type="button" class="but2" value=" << " onClick="moveall(this.form.sf_selectedCycleUsers,this.form.sf_curCycleUsers)" name="B4">
              <br>          </td>
          <td width="27%"><select multiple name="sf_selectedCycleUsers" size="8" style="width:250px" ondblclick="move(this.form.sf_selectedCycleUsers,this.form.sf_curCycleUsers)">
            </select>          </td>
          <td width="39%" height="191"><p>
              <input type="button" class="but2" name="Submit" value="确定" onClick="do_close()">
              <br>
              <br>
              <%--<input type="button" name="Submit2" value="取消" onClick="do_cancel()">--%>
          </p></td>
        </tr>
      </table>
  </form>
</body>
</html>