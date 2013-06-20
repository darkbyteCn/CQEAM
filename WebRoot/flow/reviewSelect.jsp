<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.exception.ContainerException" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择会签组别或人员
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
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

    RowSet reviewUsers = (RowSet) request.getAttribute(WebAttrConstant.REVIEW_USER_ROWSET);
%>
<html>
  <head>
      <title>选择阅示人员</title>
  </head>
<body>
  <form name="mainFrm" action="">
      <script type="text/javascript">
          function do_close() {
              var retStr = "[{method:";
              var reviewMethod = document.getElementById("review_method");
              retStr += reviewMethod.options[reviewMethod.selectedIndex].value;
              var reviewType = document.getElementById("review_type");
              retStr += ",type:" + reviewType.options[reviewType.selectedIndex].value;
              var reviewWaitType = document.getElementById("review_waitType");
              retStr += ",waitType:" + reviewWaitType.options[reviewWaitType.selectedIndex].value + ",users:";
              var sbox = document.getElementById("sf_selectedReviewUsers");
              if (sbox.options.length > 0) {
                  var userStr = "";
                  for(var i=0; i<sbox.options.length; i++) {
                      if(sbox.options[i] != null || sbox.options[i].value != "") {
                          if(userStr != "")
                               userStr += ";" + sbox.options[i].value;
                          else
                               userStr += sbox.options[i].value;
                      }
                  }
                  retStr += "'"+userStr + "'}]";
                  window.returnValue = retStr;
                  window.close();
              } else {
                  alert("请选择阅示人员！");
              }
          }

          function do_cancel() {
              window.returnValue = "";
              window.close();
          }

      </script>
      <table width="100%" border="0">
	  	<tr align="top">
		<td width="9%">阅示方式:</td>
		<td width="21%">
		<select name="review_method" style="width:150px">
		<option value="0" selected="selected">工作栏</option>
		<option value="1">电子邮件</option> 
		</select>
		</td>
		<td width="8%"></td>
		<td align="right">
		<input type="button" name="Submit" value="确定" onClick="do_close()">
		</td>
		</tr>
		<tr align="top">
		<td>阅示类型:</td>
		<td width="21%">
		<select name="review_type" style="width:150px">
		<option value="0" selected="selected">顺序处理</option>
		<option value="1">同时处理</option> 
		</select>
		</td>
        <td width="8%"></td>
        <td align="right">
        <input type="button" name="Submit" value="取消" onClick="do_cancel()">
        </td>
		</tr>
		<tr align="top">
		<td>结束选项:</td>
		<td width="21%">
		<select name="review_waitType" style="width:150px">
		<option value="0" selected="selected">等待</option>
		<option value="1">不等待</option>
		</select>
		</tr>
	  	<tr valign="top">
		<td colspan="2">待选名单:</td>
		<td width="8%"></td>
		<td>选中名单:</td>
		</tr>
        <tr valign="top">
          <td colspan="2" height="250"><select multiple name="sf_curReviewUsers" size="8" style="width:250px" ondblclick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)">
            <%
                  if (reviewUsers != null && reviewUsers.getSize() > 0) {
                      Row row;
                      String groupStr = "";
                      String displayStr = "";
                      for (int i = 0; i < reviewUsers.getSize(); i++) {
                          try {
                              groupStr = "";
                              row = reviewUsers.getRow(i);
                              displayStr = row.getStrValue("USERNAME") + "/" + row.getStrValue("GROUP_NAME");
                              groupStr = row.getStrValue("LOGIN_NAME");
                          } catch(ContainerException e) {

                          }
              %>
            <option value="<%=groupStr%>"><%=displayStr%></option>
            <%
                      }
                  }
              %>
          </select></td>
          <td width="8%"><input type="button" value=" >  " onClick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)" name="B1">
              <br>
              <input type="button" value=" <  " onClick="move(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)" name="B2">
              <br>
              <input type="button" value=" >> " onClick="moveall(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)" name="B3">
              <br>
              <input type="button" value=" << " onClick="moveall(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)" name="B4">
          <br>          </td>
          <td width="27%" align="right"><select multiple name="sf_selectedReviewUsers" size="8" style="width:250px" ondblclick="move(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)">
          </select>          </td>
          <td width="35%" height="250"><p>
              
              <br>
              <br>
              <%--<input type="button" name="Submit2" value="取消" onClick="do_cancel()">--%>
          </p></td>
        </tr>
      </table>
  </form>
</body>
</html>