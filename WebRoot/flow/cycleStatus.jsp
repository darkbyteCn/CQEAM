<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.exception.ContainerException"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<%@ page import="com.sino.sinoflow.util"%>
<%@ page import="java.util.List"%>
<%--
  User: Yung, Kam Hing
  Date: 2009-8-17
  Time: 10:05:36
  Function:选择会签组别或人员
--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	String cycleInfo = (String)request
			.getAttribute(WebAttrConstant.CYCLE_USERS_INFO);
	String finishedUsers = util.getJsonData(cycleInfo, "finished:");
	String unfinishedUsers = util.getJsonData(cycleInfo, "unfinished:");
	String cycleType = util.getJsonData(cycleInfo, "type:");
	if(cycleType.equals("0")){
		cycleType = "人员会签";
	}else{
		cycleType = "组别会签";
	}
%>
<html>
	<head>
		<title>会签状态</title>
		<link href="/WebLibary/css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body onbeforeunload=do_close()>
		<form name="mainFrm" action="">
			<script type="text/javascript">
		    	function do_close() {
		        	window.close();
		        }
		
		    </script>
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
															会签状态
														</td>
													</tr>
												</table>
												<table width="100%" border="0">
													<tr align="top">
														<td width="10%" class="caption">
															会签类型:
														</td>
														<td width="10%" class="content"><%=cycleType%></td>
														<td align="right">
															<input type="button" name="Submit" class="xfBtn" value="确 定" onClick="do_close()">
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2" class="captionLeft">
															等待会签:
														</td>
														<td class="captionLeft">
															会签完毕:
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2" height="135">
															<select name="sf_waitForCycle" size="8" style="width: 200px" disabled="disabled">
																<%
																	List unreadArray = util.explodeToList(unfinishedUsers, ";");
																	String userStr;
																	for(int i = unreadArray.size() - 1; i >= 0; i--){
																		userStr = unreadArray.get(i).toString().trim();
																%>
																<option value="<%=userStr%>"><%=userStr%></option>
																<%
																	}
																%>
															</select>
														</td>
														<td width="20%" align="right">
															<select name="sf_cycleFinished" size="8" style="width: 200px" disabled="disabled">
																<%
																	List readArray = util.explodeToList(finishedUsers, ";");
																	String user;
																	for(int i = 0; i < readArray.size(); i++){
																		user = readArray.get(i).toString().trim();
																%>
																<option value="<%=user%>"><%=user%></option>
																<%
																	}
																%>
															</select>
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