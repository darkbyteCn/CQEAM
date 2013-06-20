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
    String deptNameOptions = (String)request.getAttribute("copyDeptOption");
%>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
<script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarVar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
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
	<body onload="fill_users()" onbeforeunload="doBeforeUnload()">
		<form name="mainFrm" method="post" action="">
        <script>
            var Arr0 = new Array(true, "取消", "action_cancel.gif", "取消", "do_cancel");
            var Arr1 = new Array(true, "确定", "action_sign.gif", "确定", "do_close");
            var ArrActions = new Array(Arr0, Arr1);
            printToolBar();

            String.prototype.replaceAll = stringReplaceAll;
            function stringReplaceAll(AFindText,ARepText){
                var raRegExp = new RegExp(AFindText.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"ig");
                return this.replace(raRegExp,ARepText);
            }
        </script>
			<script type="text/javascript">
		          var winstyle = "dialogWidth:25;dialogHeight:16.5;center:yes;status:no;scroll:no;";
		
		          function fill_users() {
                      clear_users();
		              var participants = getDeptUsers(document.getElementById("deptName").value);
		              var partiArr;
		              eval("partiArr = " + participants);
                      if(partiArr[0].usernames == "")
                        return;
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
                      if(document.getElementById("copyContent").value == "") {
                          alert("请填写抄送信息");
                          return;
                      }
                      var sbox = document.getElementById("sf_selectedCopyUsers");
		              var i;
		              if (sbox.options.length > 0) {
		                  var userStr = "";
		                  for(i=0; i<sbox.options.length; i++) {
		                      if(sbox.options[i] != null || sbox.options[i].value != "") {
		                          if(userStr == "")
		                               userStr = sbox.options[i].value;
		                          else
		                               userStr += ";" + sbox.options[i].value;
		                      }
		                  }
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
/*
		          function do_search() {
		              clear_users();
		              var participants = getDeptUsers(document.getElementById("deptName").value);
		              var partiArr;
		              eval("partiArr = " + participants);
                      if(partiArr[0].usernames == "")
                        return;
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
		          }
*/
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

                  function delAll() {
                      var sbox = document.getElementById("sf_selectedCopyUsers");
                      for(var i=0; i<sbox.options.length; i++) {
                          sbox.options[i].value = "";
                          sbox.options[i].text = "";
                      }
                      BumpUp(sbox);
                      sbox.options.selectedIndex = -1;
                  }

                  function getDeptUsers(dept) {
                      var getDeptUsersURL = "/servlet/com.sino.sinoflow.servlet.GetDeptUsers?dept='"
                          + dept + "'";
                      makeRequest(getDeptUsersURL, ajaxFunction);
                      return ajaxReturn;
                  }

                  var ajaxReturn = "";
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
            </script>
			<input type="hidden" name="projectName" id="projectName" value="">
			<table width="100%" height="90%" align="center" border="0" cellspacing="0" cellpadding="0">
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
                                                    <tr>
                                                        <td class="state" colspan="3">
                                                            部门&nbsp;<select id="deptName" onchange="fill_users();"><%=deptNameOptions %></select>
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
															<select multiple name="sf_curCopyUsers" size="12" style="width: 250px" ondblclick="move(this.form.sf_curCopyUsers,this.form.sf_selectedCopyUsers)">
															</select>
														</td>
														<td width="8%" align="center">
															<br>
                                                            <br>
                                                            <input type="button" value=" 添加 " class="btn" onClick="move(this.form.sf_curCopyUsers,this.form.sf_selectedCopyUsers)" name="B1">
															<br>
                                                            <br>
                                                            <input type="button" value=" 删除 " class="btn" onClick="delSelect()" name="B3">
															<br>
                                                            <br>
                                                            <input type="button" value=" 全删 " class="btn" onclick="delAll()" name="DEL" align="right">
															<br>
                                                            <br>
                                                        </td>
														<td colspan="1" width="27%" align="right">
															<select multiple name="sf_selectedCopyUsers" size="12" style="width: 250px" ondblclick="move(this.form.sf_selectedCopyUsers,this.form.sf_curCopyUsers)">
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