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
/*
    String jsonFlowStr = request.getParameter("sf_jsonStr");
    if(jsonFlowStr.startsWith("\"") || jsonFlowStr.startsWith("'")) {
        jsonFlowStr = jsonFlowStr.substring(1,jsonFlowStr.length()-1);
    }
    if(jsonFlowStr.charAt(0) != '[')
        jsonFlowStr = "[" + jsonFlowStr + "]";

    int uIndex = jsonFlowStr.indexOf("', usernames:");
    if(uIndex < 0)
        uIndex = jsonFlowStr.indexOf("',usernames:");
    int rIndex = jsonFlowStr.indexOf("', flowProp:");
    if(rIndex < 0)
        rIndex = jsonFlowStr.indexOf("',flowProp:");
    String parti_realnames = "";
    String parti_usernames = "";
    if(uIndex > 0) {
        parti_realnames = jsonFlowStr.substring(jsonFlowStr.indexOf("realnames:'") + 11, uIndex);
    }
    if(rIndex > 0) {
        parti_usernames = jsonFlowStr.substring(jsonFlowStr.indexOf("usernames:'") + 11, rIndex);
    }

    String selectedUsers = request.getParameter("sf_selected");
    String selected_realnames;
    String selected_usernames;
    if(selectedUsers.startsWith("\"") || selectedUsers.startsWith("'")) {
        selectedUsers = selectedUsers.substring(1,selectedUsers.length()-1);
    }
    selected_realnames = "";
    selected_usernames = "";

    uIndex = selectedUsers.indexOf("', usernames:");
    rIndex = selectedUsers.indexOf("'}");
    if(uIndex > 0) {
        selected_realnames = selectedUsers.substring(selectedUsers.indexOf("realnames:'") + 11, uIndex);
    }
    if(rIndex > 0) {
        selected_usernames = selectedUsers.substring(selectedUsers.indexOf("usernames:'") + 11, rIndex);
    }
*/    
    String multiSelected = request.getParameter("multi_selected");
    if(multiSelected == null || multiSelected.equals(""))
        multiSelected = "0";
%>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<script type ="text/javascript">
    var sortitems = 1;  // Automatically sort items within lists? (1 or 0)

    function move(fbox,tbox) {
        if("<%=multiSelected%>" == "2" && tbox == document.getElementById("sf_selectedReviewUsers")) {
            var groupName = new Array();
            var tempStr = "";
            for(var i = 0; i < tbox.options.length; i++) {
                tempStr = tbox.options[i].text;
                tempStr = (tempStr.split("/"))[1];
                groupName[i] = tempStr;
            }
            var select = new Array();
            for(var i=0; i<fbox.options.length; i++) {
                if(fbox.options[i].selected && fbox.options[i].value != "") {
                    tempStr = fbox.options[i].text;
                    tempStr = (tempStr.split("/"))[1];
                    for(var j = 0; j < groupName.length; j++) {
                        if(groupName[j] == tempStr) {
                            alert("同一组别只能选择一人");
                            BumpUp(fbox);
                            return;
                        }
                    }
                    groupName[groupName.length] = tempStr;
                    var no = new Option();
                    no.value = fbox.options[i].value;
                    no.text = fbox.options[i].text;
                    tbox.options[tbox.options.length] = no;
                    fbox.options[i].value = "";
                    fbox.options[i].text = "";
                }                
             }
        } else {
            if(tbox == document.getElementById("sf_selectedReviewUsers")) {
                if("<%=multiSelected%>" == "0") {
                    if(tbox.options.length >= 1) {
                        if(fbox.options.length != 0 && fbox.selectedIndex >= 0) {
                            alert("只能选择一个经办人");
                        }
                        return;
                    }
                }
            }
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
        }
        BumpUp(fbox);
    }

    function moveall(fbox,tbox) {
        if("<%=multiSelected%>" == "2" && tbox == document.getElementById("sf_selectedReviewUsers")) {
            alert("同一组别只能选择一人!");
            return;
        }
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
<title>选择经办人</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/cms_css/cms_css.css">
<style type="text/css">
<!--
.STYLE1 {font-size: 12px}
-->
</style>
</head>
<body onLoad="fill_select()" onbeforeunload="doBeforeUnload()">
<form name="mainFrm" action="">
  <script type="text/javascript">
          var arg = window.dialogArguments;
          var partiStr = arg[0];
          if(partiStr.charAt(0) == '[')
              partiStr = partiStr.substring(1, partiStr.length - 1);
          eval("flowProp = " + partiStr);

          function fill_select() {
              var selectedStr = arg[1];
              if(selectedStr.charAt(0) == '[')
                  selectedStr = selectedStr.substring(1, selectedStr.length - 1);
              var selected;
              if(selectedStr == "")
                  selected = "";
              else
                  selected = selectedStr;

              var partiBox = document.getElementById("sf_curReviewUsers")
              var selectBox = document.getElementById("sf_selectedReviewUsers");
              var i;
              var opt;
              var parti_usernames =  getJsonData(partiStr, "usernames:").replaceAll(",", ";");
              var parti_realnames = getJsonData(partiStr, "realnames:").replaceAll(",", ";");
              var selected_usernames;
              if(selected == "")
                  selected_usernames = "";
              else
                  selected_usernames = getJsonData(selected, "usernames:").replaceAll(",", ";");
              var selected_realnames;
              if(selected == "")
                  selected_realnames = "";
              else
                  selected_realnames = getJsonData(selected, "realnames:").replaceAll(",", ";");

              var partiUsersArr = parti_usernames.split(";");
              var partiRealsArr = parti_realnames.split(";")
              for(i = 0; i < partiUsersArr.length; i++) {
                  if(inList(selected_usernames, partiUsersArr[i], ";")) {
                      continue;
                  }
                  opt = new Option();
                  opt.value = partiUsersArr[i];
                  opt.text = partiRealsArr[i];
                  partiBox.options[partiBox.options.length] = opt;
              }
              if(selected != "") {
                  var selectedUsersArr = selected_usernames.split(";");
                  var selectedRealsArr = selected_realnames.split(";");
                  for(i = 0; i < selectedUsersArr.length; i++) {
                      opt = new Option();
                      opt.value = selectedUsersArr[i];
                      opt.text = selectedRealsArr[i];
                      selectBox.options[selectBox.options.length] = opt;
                  }
              }
          }

          function do_close() {
              var sbox = document.getElementById("sf_selectedReviewUsers");
              var fbox = document.getElementById("sf_curReviewUsers");
              var i;
              for(i = 0; i < fbox.options.length; i++) {
                  var tempStr = fbox.options[i].text;
                  if(tempStr == "")
                    continue;
                  tempStr = (tempStr.split("/"))[1];
                  var find = false;
                  for(var j = 0; j < sbox.options.length; j++) {
                      var tempStr2 = sbox.options[j].text
                      if(tempStr2 == "")
                        continue;
                      tempStr2 = (tempStr2.split("/"))[1];
                      if(tempStr2 == tempStr) {
                          find = true;
                          break;
                      }
                  }
                  if(!find) {
                      alert("每一组别必须选一人!");
                      return;
                  }
              }
              if (sbox.options.length > 0) {
                  var userStr = "";
                  var realStr = "";
                  for(i=0; i<sbox.options.length; i++) {
                      if(sbox.options[i] != null || sbox.options[i].value != "") {
                          if(userStr == "")
                               userStr = sbox.options[i].value;
                          else
                               userStr += ";" + sbox.options[i].value;
                          if(realStr == "")
                               realStr = sbox.options[i].text;
                          else
                               realStr += ";" + sbox.options[i].text;
                      }
                  }
                  window.returnValue = "[{realnames:'" + realStr + "', usernames:'" + userStr +"'}]";
                  window.close();
              } else {
                  alert("请选择经办人！");
              }
          }

          function do_cancel() {
              window.returnValue = "";
              window.close();
          }

          function doBeforeUnload() {
              if(!window.returnValue)
                window.returnValue = "";
          }

      </script>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="top">
      <td width="9%"><span class="STYLE1">任务:</span></td>
      <td width="21%"><span class="STYLE1">
        <script type="text/javascript">document.writeln(flowProp.taskName)</script>
        </span></td>
      <td width="8%"></td>
      <td align="right"></td>
    </tr>
    <tr align="top">
      <td><span class="STYLE1">组别:</span></td>
      <td width="21%"><span class="STYLE1">
        <script type="text/javascript">document.writeln(flowProp.groupName);</script>
        </span></td>
      <td width="8%"></td>
      <td align="right"></td>
    </tr>
    <tr align="top" style="display:none;">
      <td><span class="STYLE1">角色:</span></td>
      <td width="21%"><script type="text/javascript">document.writeln(flowProp.roleName);</script>
    </tr>
    <tr valign="top">
      <td colspan="2"><br/>
        <span class="STYLE1">待选名单:</span></td>
      <td width="8%"></td>
      <td><br/>
        <span class="STYLE1">选中名单:</span></td>
    </tr>
    <tr valign="top">
      <%
              if(multiSelected.equals("0")) {
          %>
      <td colspan="2" ><select name="sf_curReviewUsers" size="8" style="width:235px" ondblclick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)">
          <%
              } else {
          %>
          <td colspan="2" >
          <select multiple name="sf_curReviewUsers" size="8" style="width:250px" ondblclick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)">
          <%
                }
            %>
        </select></td>
      <td width="8%" align="center" ><br>
        <input type="button" class="but4" value=" >  " onClick="move(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)" name="B1">
        <br>
		<br>
        <input type="button" value=" <  " class="but4" onClick="move(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)" name="B2">
        <br>
        <%
                  if(!multiSelected.equals("0")) {
              %>
        <input type="button" value=" >> " class="but4" onClick="moveall(this.form.sf_curReviewUsers,this.form.sf_selectedReviewUsers)" name="B3">
        <br>
        <input type="button" value=" << " class="but4" onClick="moveall(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)" name="B4">
        <br>
        <%
                  }
              %>
      </td>
      <td width="27%" align="left"><select multiple name="sf_selectedReviewUsers" size="8" style="width:230px" ondblclick="move(this.form.sf_selectedReviewUsers,this.form.sf_curReviewUsers)">
        </select>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td></td>
      <td align="right"></td>
      <td align="center"><input type="button" name="Submit" id="Submit" class="but2" value="确定" onClick="do_close()">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="Cancel" id="Cancel" class="but2" value="取消" onClick="do_cancel()"></td>
      <td align="left"></td>
    </tr>
  </table>
</form>
</body>
</html>
