<%@ page contentType="text/html; charset=gb2312" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<title>请确认</title>
</head>
<body onload="doLoad()" onunload="doUnload()" >
<form id="form1" name="getPriority" method="post" action="">
  <script type="text/javascript">
        function doLoad() {
            var arg = window.dialogArguments;
            var prop;
            eval("prop = " + arg);
            var tempStr = getTaskName(prop);
            var tempArr = tempStr.split(";");
            var i;
            var task = "";
            for(i = 0; i < tempArr.length; i++) {
                if(tempArr[i] == "SPLIT") {
                    continue;
                } else if(tempArr[i] == "JOIN") {
                    continue;
                }
                if(task == "")
                    task = tempArr[i];
                else
                    task += ";" + tempArr[i];
            }
            
            tempStr = getGroupName(prop);
            tempArr = tempStr.split(";");
            var group = "";
            for(i = 0; i < tempArr.length; i++) {
                if(tempArr[i] != "undefined") {
                    if(group == "")
                        group = tempArr[i];
                    else
                        group += ";" + tempArr[i];
                }
            }

            tempStr = getRoleName(prop);
            tempArr = tempStr.split(";");
            var role = "";
            for(i = 0; i < tempArr.length; i++) {
                if(tempArr[i] != "undefined") {
                    if(role == "")
                        role = tempArr[i];
                    else
                        role += ";" + tempArr[i];
                }
            }

            tempStr  = getParticipant(prop);
            tempArr = tempStr.split("\r\n");
            var participant = "";
            for(i = 0; i < tempArr.length; i++) {
                if(tempArr[i] != "undefined" && tempArr[i] != "") {
                    if(participant == "")
                        participant = tempArr[i] + "\r\n";
                    else
                        participant += tempArr[i] + "\r\n";
                }
            }

            if(task == "" && group == "" && role == "" && participant == "") {
                do_close();
            } else {
                document.getElementById("taskName").value = task;
                document.getElementById("groupName").value = group;
                document.getElementById("roleName").value = role;
                document.getElementById("participants").value = participant;
            }
            document.getElementById("approveContent").select();
        }

        function getTaskName(taskProp) {
            if(!taskProp.length) {
                if(taskProp.taskName)
                    return taskProp.taskName;
/*              if(taskProp.taskDesc)
                    return taskProp.taskDesc
*/                else
                    return "";
            }
            var name = "";
            for(var i = 0; i < taskProp.length; i++) {
                var prop = taskProp[i];
                var taskName = getTaskName(prop);
                if(taskName != "") {
                    if(name == "")
                        name += taskName;
                    else
                        name += ";" + taskName;
                }
            }
            return name;
        }

        function getGroupName(taskProp) {
            if(!taskProp.length) {
                if(taskProp.groupName)
                    return taskProp.groupName;
                else
                    return "";
            }
            var name = "";
            for(var i = 0; i < taskProp.length; i++) {
                var prop = taskProp[i];
                var groupName = getGroupName(prop);
                if(groupName != "") {
                    if(name == "")
                        name += groupName;
                    else
                        name += ";" + groupName;
                }
            }
            return name;
        }

        function getRoleName(taskProp) {
            if(!taskProp.length) {
                if(taskProp.roleName)
                    return taskProp.roleName;
                else
                    return "";
            }
            var name = "";
            for(var i = 0; i < taskProp.length; i++) {
                var prop = taskProp[i];
                var roleName = getRoleName(prop);
                if(roleName != "") {
                    if(name == "")
                        name += roleName;
                    else
                        name += ";" + roleName;
                }
            }
            return name;
        }

        function getParticipant(taskProp) {
            if(!taskProp.length)
                return taskProp.realnames;
            var name = "";
            for(var i = 0; i < taskProp.length; i++) {
                var prop = taskProp[i];
                var realname = getParticipant(prop);
                if(realname && realname != "") {
                    name += realname + "\r\n";
                }
            }
            return name;
        }

        function do_close() {
            window.returnValue = document.getElementById("approveContent").value;
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
    <tr> 
      <td class="blue" align="right"><img src="/image/ie-02.jpg" width="29" height="25" /></td>
	  <td class="blue" align="left">本案件将发送到：</td>
    </tr>
    <tr>
      <td width="24%" height="25" align="right" class="text_gray3">任务：</td>
      <td width="76%"><input name="taskName" type="text" readonly="readonly" class="input_5" id="taskName" /></td>
    </tr>
    <tr>
      <td height="100%" align="right" class="text_gray3">组别：</td>
      <td><input name="groupName" type="text" readonly="readonly" class="input_5" id="groupName" /></td>
    </tr>
    <tr style="display:none">
      <td height="100%" align="right" class="text_gray3" style="display:none">角色：</td>
      <td ><input name="roleName" type="text" readonly="readonly" class="input_5" id="roleName" /></td>
    </tr>
    <tr>
      <td  align="right" class="text_gray3">办理人：</td>
      <td><label>
        <textarea name="participants" cols="45" rows="5" readonly="readonly" class="input_5" id="participants"></textarea>
        </label>
      </td>
    </tr>
    <tr>
      <td  align="right" class="text_gray3">请填写意见：</td>
      <td><label>
        <textarea id="approveContent" name="approveContent" cols="45" rows="8" class="input_11" ><%=flowDesc%></textarea>
        </label>
      </td>
    </tr>
    <tr><td>&nbsp;</td><tr>
    <tr>
	<td></td>
      <td align="left"><input name="Submit" type="button" class="but4" id="button" value="确定" onClick="do_close()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input name="Cancel" type="button" class="but4" id="button2" value="取消" onClick="do_cancel()"/></td>
    </tr>
  </table>
</form>
</body>
</html>
