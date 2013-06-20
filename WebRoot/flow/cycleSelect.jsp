<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.exception.ContainerException"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<%--
  User: Yung, Kam Hing
  Date: 2009-8-17
  Time: 15:05:36
  Function:选择会签组别或人员
--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
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

    function up() {
        var box = document.getElementById("sf_selectedCycleUsers");
        var index = box.options.selectedIndex;
        if(index <= 0)
            return;
        var text = box.options[index].text;
        var value = box.options[index].value;
        box.options[index].text = box.options[index - 1].text;
        box.options[index].value = box.options[index - 1].value;
        box.options[index - 1].text = text;
        box.options[index - 1].value = value;
        box.options.selectedIndex = index - 1;
    }

    function down() {
        var box = document.getElementById("sf_selectedCycleUsers");
        var index = box.options.selectedIndex;
        if(index >= box.options.length - 1 || index < 0)
            return;
        var text = box.options[index].text;
        var value = box.options[index].value;
        box.options[index].text = box.options[index + 1].text;
        box.options[index].value = box.options[index + 1].value;
        box.options[index + 1].text = text;
        box.options[index + 1].value = value;
        box.options.selectedIndex = index + 1;
    }
</script>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

    String type = (String)request.getAttribute("CYCLE_TYPE_INFO");
    String allUsers = (String)request.getAttribute("CYCLE_ALLUSERS_INFO");
    String selectedUsers = (String)request.getAttribute("CYCLE_SELECTEDUSERS_INFO");
    boolean haveType = true;
    if(type == null) {
        haveType = false;
    } else {
        if(selectedUsers == null)
            selectedUsers = "";
    }
%>
<html>
	<head>
		<title>选择会签人员</title>
		<link href="/WebLibary/css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body onload="onLoad()" onbeforeunload="doBeforeUnload()">
		<form name="mainFrm" action="">
			<script type="text/javascript">
		          var arg = window.dialogArguments;
		          var type;
                  if(<%=haveType%>)
                    type = "<%=type%>";
                  else
                    type = arg[0];
                  var cycleAllUsers;
                  if(<%=haveType%>)
                    cycleAllUsers = "<%=allUsers%>";
                  else
                    cycleAllUsers = arg[1];
                  var cycleAllReals = "";
                  if(cycleAllUsers != "")
		            cycleAllReals= getRealnames(cycleAllUsers);
		          var cycleRemainUsers;
                  if(<%=haveType%>)
                    cycleRemainUsers = "<%=selectedUsers%>";
                  else
                    cycleRemainUsers = arg[2];
                  var cycleRemainReals;
		          if(cycleRemainUsers != "")
		            cycleRemainReals = getRealnames(cycleRemainUsers);
		
		          var cycleType;
		          if(type == "0") {
		            cycleType = "人员会签";
		          } else {
		            cycleType = "组别会签";
		          }
		
		          function fill_select() {
		              var cycleBox = document.getElementById("sf_curCycleUsers")
		              var selectBox = document.getElementById("sf_selectedCycleUsers");
		
		              var cycleUsersArr = cycleAllUsers.split(";");
		              var cycleRealsArr = cycleAllReals.split(";");
		              var i;
		              var boxUsers = "";
		              for(i = 0; i < cycleUsersArr.length; i++) {
		                  if(inList(cycleRemainUsers, cycleUsersArr[i], ";") || inList(boxUsers, cycleUsersArr[i], ";")) {
		                      continue;
		                  }
		                  opt = new Option();
		                  opt.value = cycleUsersArr[i];
//                          opt.text = cycleRealsArr[i];
                          var realArr = cycleRealsArr[i].split("/");
                          opt.text = realArr[0];
                          cycleBox.options[cycleBox.options.length] = opt;
		                  if(boxUsers != "")
		                    boxUsers += ";"
		                  boxUsers += cycleUsersArr[i];
		              }
		              if(cycleRemainUsers != "") {
		                  var selectedUsersArr = cycleRemainUsers.split(";");
		                  var selectedRealsArr = cycleRemainReals.split(";");
		                  for(i = 0; i < selectedUsersArr.length; i++) {
		                      opt = new Option();
		                      opt.value = selectedUsersArr[i];
//		                      opt.text = selectedRealsArr[i];
                              var selectArr = selectedRealsArr[i].split("/");
                              opt.text = selectArr[0];
                              selectBox.options[selectBox.options.length] = opt;
		                  }
		              }
		          }
		
		          function do_close() {
		              var retStr = "[{users:";
		              var sbox = document.getElementById("sf_selectedCycleUsers");
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
		                  retStr = "[{users:''}]";
		                  window.returnValue = retStr;
		                  window.close();
		              }
		          }
		
		          function do_cancel() {
		              window.returnValue = "";
		              window.close();
		          }
		
		          function onLoad() {
		              document.getElementById("cycleType").value = cycleType;
		              fill_select();
		          }

                  function doBeforeUnload() {
                      if(!window.returnValue)
                        window.returnValue = "";
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
															会签设定
														</td>
													</tr>
												</table>
												<table width="100%" border="0">
													<tr align="top">
														<td width="9%" class="caption">
															会签类型:
														</td>
														<td width="21%" class="content">
															<input type="text" name="cycleType" class="xfNoBorderAllCL" readonly="readonly" style="border: 0">
														</td>
														<td width="8%"></td>
														<td align="right">
															<input type="button" name="Submit" class="xfBtn" value="确 定" onClick="do_close()">
														</td>
													</tr>
													<tr align="top">
														<td></td>
														<td width="21%">
														</td>
														<td width="8%"></td>
														<td align="right">
															<input type="button" name="Submit" class="xfBtn" value="取 消" onClick="do_cancel()">
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2" class="captionLeft">
															待选名单:
														</td>
														<td width="8%"></td>
														<td class="captionLeft">
															选中名单:
														</td>
													</tr>
													<tr valign="top">
														<td colspan="2" height="150">
															<select multiple name="sf_curCycleUsers" size="10" style="width: 250px" ondblclick="move(this.form.sf_curCycleUsers,this.form.sf_selectedCycleUsers)">
															</select>
														</td>
														<td width="8%">
															<br>
															<input type="button" value=" >  " class="xfBtn" onClick="move(this.form.sf_curCycleUsers,this.form.sf_selectedCycleUsers)" name="B1">
															<br>
															<input type="button" value=" <  " class="xfBtn" onClick="move(this.form.sf_selectedCycleUsers,this.form.sf_curCycleUsers)" name="B2">
															<br>
															<input type="button" value=" >> " class="xfBtn" onClick="moveall(this.form.sf_curCycleUsers,this.form.sf_selectedCycleUsers)" name="B3">
															<br>
															<input type="button" value=" << " class="xfBtn" onClick="moveall(this.form.sf_selectedCycleUsers,this.form.sf_curCycleUsers)" name="B4">
															<br>
														</td>
														<td width="27%" align="right">
															<select multiple name="sf_selectedCycleUsers" size="10" style="width: 250px" ondblclick="move(this.form.sf_selectedCycleUsers,this.form.sf_curCycleUsers)">
															</select>
														</td>
                                                        <td>
                                                            &nbsp;<input type="button" value="" onClick="up()" name="UP" style="width:16px;height:14px;background:url(/images/loginMain/arrow_u.gif);background-repeat:no-repeat;">
                                                            <br>
                                                            &nbsp;<input type="button" value="" onClick="down()" name="DWON" style="width:16px;height:14px;background:url(/images/loginMain/arrow_d.gif);background-repeat:no-repeat;">
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