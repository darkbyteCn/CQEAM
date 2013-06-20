<%@ page contentType="text/html;charset=GBK" language="java"%>
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
<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
<script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<script type="text/javascript">
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
        fbox.options.selectedIndex = -1;
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
		<title>选择抄送人</title>
		<link href="/WebLibary/css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body onload="fill_data();fill_users()" onbeforeunload="doBeforeUnload()">
		<form name="mainFrm" method="post" action="">
			<script type="text/javascript">
		          var winstyle = "dialogWidth:25;dialogHeight:16.5;center:yes;status:no;scroll:no;";
		
		          var arg = window.dialogArguments;
		          var projectName = arg[0];
		          var groupName = arg[1];
		          var roleName = arg[2];
		
		          function fill_data() {
		              document.getElementById("projectName").value = projectName;
		              document.getElementById("groupName").value = groupName;
		              document.getElementById("roleName").value = roleName;
		          }
		
		          function fill_users() {
		              clear_users();
		              projectName = document.getElementById("projectName").value;
		              groupName = document.getElementById("groupName").value;
		              roleName = document.getElementById("roleName").value;
		              var participants = getGroupsUsersNames(projectName, groupName, roleName);
		              var partiArr;
		              eval("partiArr = " + participants);
		              partiArr[0].usernames = partiArr[0].usernames.replaceAll(",", ";");
		              partiArr[0].realnames = partiArr[0].realnames.replaceAll(",", ";");
		              var usersArr = partiArr[0].usernames.split(";");
		              var realsArr = partiArr[0].realnames.split(";");
		              var partiBox = document.getElementById("sf_curCopyUsers")
		              for(i = 0; i < usersArr.length; i++) {
		                  opt = new Option();
		                  opt.value = usersArr[i];
		                  opt.text = realsArr[i];
		                  partiBox.options[partiBox.options.length] = opt;
		              }              
		          }
		
		          function clear_users() {
		              var partiBox = document.getElementById("sf_curCopyUsers")
		              partiBox.options.length = 0;
		          }
		
		          function do_close() {
		              var sbox = document.getElementById("sf_selectedCopyUsers");
		              var i;
		              if (sbox.options.length > 0) {
		                  var userStr = "";
		//                  var realStr = "";
		                  for(i=0; i<sbox.options.length; i++) {
		                      if(sbox.options[i] != null || sbox.options[i].value != "") {
		                          if(userStr == "")
		                               userStr = sbox.options[i].value;
		                          else
		                               userStr += ";" + sbox.options[i].value;
		/*
		                          if(realStr == "")
		                               realStr = sbox.options[i].text;
		                          else
		                               realStr += ";" + sbox.options[i].text;
		*/
		                      }
		                  }
		//                  window.returnValue = "[{realnames:'" + realStr + "', usernames:'" + userStr +"'}]";
		                  window.returnValue = "{copyUsers:'" + userStr + "',copyMsg:'"
		                          + document.getElementById("copyContent").value + "'}";
		                  window.close();
		              } else {
		                  alert("请选择抄送人！");
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
		
		          function do_search() {
		              clear_users();
		              projectName = document.getElementById("projectName").value;
		              groupName = document.getElementById("groupName").value;
		              roleName = document.getElementById("roleName").value;
		              var participants = getGroupsUsersNames(projectName, groupName, roleName);
		              var partiArr;
		              eval("partiArr = " + participants);
		              partiArr[0].usernames = partiArr[0].usernames.replaceAll(",", ";");
		              partiArr[0].realnames = partiArr[0].realnames.replaceAll(",", ";");
		              var usersArr = partiArr[0].usernames.split(";");
		              var realsArr = partiArr[0].realnames.split(";");
		              var partiBox = document.getElementById("sf_curCopyUsers")
		              var str = document.getElementById("username").value;
		              for(i = 0; i < usersArr.length; i++) {
		                  if(realsArr[i].indexOf(str) >= 0) {
		                      opt = new Option();
		                      opt.value = usersArr[i];
		                      opt.text = realsArr[i];
		                      partiBox.options[partiBox.options.length] = opt;
		                  }
		              }
		
		/*
		              var sbox = document.getElementById("sf_curCopyUsers");
		              var str = document.getElementById("username").value;
		              for(var i=0; i<sbox.options.length; i++) {
		                  if(sbox.options[i].text.indexOf(str) < 0) {
		                      sbox.options[i].value = "";
		                      sbox.options[i].text = "";
		                  }
		              }
		              BumpUp(sbox);
		*/
		          }
		
		          function delSelect() {
		              var sbox = document.getElementById("sf_selectedCopyUsers");
		              for(var i=0; i<sbox.options.length; i++) {
		                  if(sbox.options[i].selected) {
		                      sbox.options[i].value = "";
		                      sbox.options[i].text = "";
		                  }
		              }
		              BumpUp(sbox);
		              sbox.options.selectedIndex = -1;
		          }
		
		          function do_selectGroup() {
		              var returnValue = window.showModalDialog("/system/showOptions.jsp", getGroup(mainFrm.projectName.value), winstyle);
		              if (returnValue) {
		                  document.getElementById("groupName").value = returnValue;
		                  fill_users();
		              }
		          }
		
		          function do_selectRole() {
		              var returnValue = window.showModalDialog("/system/showOptions.jsp", getRole(mainFrm.projectName.value), winstyle);
		              if (returnValue) {
		                  if(returnValue.indexOf("*") >= 0)
		                    returnValue = "";
		                  document.getElementById("roleName").value = returnValue;
		                  fill_users();
		              }
		          }
		
		/*
		          function add_Arr(project,group,arr){//将选中的下拉列表，添加到数组中
		
		              if(group == null){
			              for(var i = 0;i < arr.length;i++){
			    	          var tempArr = new Array(project.value,arr[i]);
			    	          proGroupArr[proGroupArr.length] = tempArr;
			              }
		              }else{
		    	          for(var i = 0;i < arr.length;i++){
		    		          var tempArr = new Array(project.value,group.value,arr[i]);
		    		          proGroupRoleArr[proGroupRoleArr.length] = tempArr;
		    	          }
		              }
		          }
		*/                
		    </script>
			<input type="hidden" name="projectName" id="projectName" value="">
			<table width="100%" height="100%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="8" height="8" valign="top" background="/images/bd1.gif" style="background-repeat: no-repeat;">
					</td>
					<td height="8" background="/images/bd2.gif" style="background-repeat: repeat-x;">
					</td>
					<td width="8" height="8" valign="top" background="/images/bd3.gif" style="background-repeat: no-repeat;">
					</td>
				</tr>
				<tr>
					<td background="/images/bd4.gif">
						&nbsp;
					</td>
					<td align="center" valign="top" bgcolor="#cedeee" style="border: 1px solid #434948; background-color: #FFFFFF">
						<!-- 基本信息/操作区      -->
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center">
									<table width="99%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="8" height="8" style="background: url(/images/fr1.gif) right bottom no-repeat;">
												<img src="/images/blank.gif" width="8" height="8">
											</td>
											<td style="background: url(/images/fr2.gif) bottom repeat-x;">
												<img src="/images/blank.gif" width="1" height="1">
											</td>
											<td width="8" height="8" style="background: url(/images/fr3.gif) bottom left no-repeat;">
												<img src="/images/blank.gif" width="8" height="8">
											</td>
										</tr>
										<tr>
											<td style="background: url(/images/fr4.gif) right repeat-y">
												&nbsp;
											</td>
											<td bgcolor="#f3f9ef">
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="25">
															<img src="/images/state2.png" width="16" height="15">
														</td>
														<td class="state">
															选择抄送人
														</td>
													</tr>
												</table>
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr align="top">
														<td width="9%" class="caption">
															组别:
														</td>
														<td width="21%" class="content">
															<input type="text" name="groupName" id="groupName" value="" class="baseInput" />
														</td>
														<td width="8%">
															<input type="button" name="selectGroup" id="selectGroup" class="xfBtn" value="选择组别" onclick="do_selectGroup()">
														</td>
														<td align="right">
															<input type="button" name="Submit" id="Submit" class="xfBtn" value="确 定" onClick="do_close()">
														</td>
													</tr>
													<tr align="top">
														<td class="caption">
															角色:
														</td>
														<td width="21%" class="content">
															<input type="text" name="roleName" id="roleName" value="" class="baseInput" />
														</td>
														<td width="8%">
															<input type="button" name="selectRole" id="selectRole" class="xfBtn" value="选择角色" onclick="do_selectRole()">
														</td>
														<td align="right">
															<input type="button" name="Cancel" id="Cancel" class="xfBtn" value="取 消" onClick="do_cancel()">
														</td>
													</tr>
													<tr align="top">
														<td class="caption">
															姓名:
														</td>
														<td width="21%" class="content">
															<input type="text" name="username" id="username" value="" class="baseInput" />
														</td>
														<td width="8%">
															<input type="button" name="search" id="search" class="xfBtn" value="查    询" onclick="do_search()">
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2" class="captionLeft">
															<br />
															待选名单:
														</td>
														<td width="8%"></td>
														<td class="captionLeft">
															<br />
															&nbsp;选中名单:
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2">
															<select multiple name="sf_curCopyUsers" size="8" style="width: 250px" ondblclick="move(this.form.sf_curCopyUsers,this.form.sf_selectedCopyUsers)">
															</select>
														</td>
														<td width="8%" align="center">
															<br>
															<input type="button" value="   >    " class="xfBtn" onClick="move(this.form.sf_curCopyUsers,this.form.sf_selectedCopyUsers)" name="B1">
															<br>
															<input type="button" value="   >>   " class="xfBtn" onClick="moveall(this.form.sf_curCopyUsers,this.form.sf_selectedCopyUsers)" name="B3">
															<br>
															<input type="button" value="选中删除" class="xfBtn" onclick="delSelect()" name="DEL" align="right">
															<br>
														</td>
														<td colspan="1" width="27%" align="right">
															<select multiple name="sf_selectedCopyUsers" size="8" style="width: 250px" ondblclick="move(this.form.sf_selectedCopyUsers,this.form.sf_curCopyUsers)">
															</select>
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2" class="captionLeft">
															<br />
															抄送信息:
														</td>
													</tr>
													<tr valign="top">
														<td colspan="5">
															<textarea name="copyContent" style="width: 100%;" rows="5" class="blueborder"></textarea>
														</td>
													</tr>
												</table>
											</td>
											<td style="background: url(/images/fr5.gif) left repeat-y;">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td style="background: url(/images/fr6.gif) top right no-repeat">
												<img src="/images/blank.gif" width="8" height="8">
											</td>
											<td style="background: url(/images/fr7.gif) top repeat-x">
												<img src="/images/blank.gif" width="1" height="1">
											</td>
											<td style="background: url(/images/fr8.gif) top left no-repeat;">
												<img src="/images/blank.gif" width="8" height="8">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-- 基本信息/操作区      完-->
					</td>
					<td background="/images/bd5.gif">
						<img src="/images/blank.gif" width="1" height="1">
					</td>
				</tr>
				<tr>
					<td width="8" height="8" valign="top" background="/images/bd6.gif" style="background-repeat: no-repeat;">
					</td>
					<td height="8" background="/images/bd7.gif" style="background-repeat: repeat-x;">
					</td>
					<td width="8" height="8" valign="top" background="/images/bd8.gif" style="background-repeat: no-repeat;">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>