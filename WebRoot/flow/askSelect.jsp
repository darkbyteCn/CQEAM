<%@ page contentType="text/html;charset=GBK" language="java"%>
<%--
  User: Yung, Kam Hing
  Date: 2010-11-12
  Time: 10:05:36
  Function:选择提问人员
--%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setCharacterEncoding("gbk");
    String askUserOptions = (String)request.getAttribute("askUserOption");
%>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarVar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<html>
	<head>
		<title>提问</title>
		<link href="/WebLibary/css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body onload="" onbeforeunload="doBeforeUnload()">
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
		
		          function do_close() {
                      if(document.getElementById("askContent").value == "") {
                          alert("请填写问题內容!");
                          return;
                      }
                      if(document.getElementById("askUser").value == "" || document.getElementById("askUser").value.indexOf("请选择") >= 0) {
                          alert("请选择答复人!");
                          return;
                      }
                      window.returnValue = "{askUser:'" + document.getElementById("askUser").value + "',askMsg:'"
                              + document.getElementById("askContent").value + "'}";
                      window.close();
                  }
		
		          function do_cancel() {
		              window.returnValue = "";
		              window.close();
		          }
		
		          function doBeforeUnload() {
		              if(!window.returnValue)
		                window.returnValue = "";
		          }

                  function selectFlow() {
                      var strg = "<select id='askUser'>";
                      var str1 = "<option value=''>--请选择--</option>";
                      var str2 = "</select>";

                      try{
                          var crs = new clientRowSet();
                          crs.modelClassName = "com.sino.sinoflow.user.model.SfGroupModel";
                          crs.methodName = "getOptionFlowUserModel";
                          crs.methodParameterName = new Array(window.dialogArguments);
                          var res = crs.send_request();
                           var optionStr = "";
                           for(var i = 0;i<res.length;i++){
                              optionStr += "<option value='"+res[i].LOGIN_NAME+"'>"+res[i].USERNAME+"</option>";
                           }
                           optionStr = strg+str1+optionStr+str2;
                           document.getElementById("user").innerHTML = optionStr;
                     }catch(e){
                         alert(e);
                     }
                  }

                  function selectDept() {
                      var strg = "<select id='askUser'>";
                      var str1 = "<option value=''>--请选择--</option>";
                      var str2 = "</select>";

                      try{
                          var crs = new clientRowSet();
                          crs.modelClassName = "com.sino.sinoflow.user.model.SfGroupModel";
                          crs.methodName = "getOptionDeptUserModel";
                          var res = crs.send_request();
                           var optionStr = "";
                           for(var i = 0;i<res.length;i++){
                              optionStr += "<option value='"+res[i].LOGIN_NAME+"'>"+res[i].USERNAME+"</option>";
                           }
                           optionStr = strg+str1+optionStr+str2;
                           document.getElementById("user").innerHTML = optionStr;
                     }catch(e){
                         alert(e);
                     }
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
															选择答复人
														</td>
													</tr>
												</table>
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td class="state" colspan="3">
                                                            <input type="radio" name="askType" id="askType" value="0" onclick="selectFlow()" checked />流经人员
                                                            <input type="radio" name="askType" id="askType" value="1" onclick="selectDept()" />本部门
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="state" id="user" colspan="3">
                                                            答复人&nbsp;<select id="askUser"><%=askUserOptions %></select>
                                                        </td>
                                                    </tr>
													<tr valign="top">
														<td colspan="2" class="captionLeft">
															<br />
															问题:
														</td>
													</tr>
													<tr valign="top">
														<td colspan="5">
															<textarea name="askContent" style="width: 100%;" rows="5" class="blueborder"></textarea>
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