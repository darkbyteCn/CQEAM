<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="com.sino.sinoflow.user.dto.SfUserBaseDTO" %>
<%@ page import="com.sino.base.util.ReflectionUtil" %>

<%
    SfUserBaseDTO user = (SfUserBaseDTO) request.getAttribute(WebAttrConstant.USER_ATTR);
    if (user == null) user = new SfUserBaseDTO();
    String str = StrUtil.nullToString(request.getAttribute("str"));
    String strDept = StrUtil.nullToString(request.getAttribute("strDept"));
    String strRole = StrUtil.nullToString(request.getAttribute("strRole"));
    ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(request);
    String sysAdminRole = servletConfig.getSysAdminRole();
    String startDate ="";
    String endDate ="";
    String startDateT ="";
    String endDateT ="";
    String userName = user.getUsername();
    if(user.getStartDate()!=null){
     	startDate = user.getStartDate().getCalendarValue();
     	if(!"1900-01-01".equals(startDate)){startDateT=startDate;}
   	}
    if(user.getEndDate()!=null){
        endDate =user.getEndDate().getCalendarValue();
        if(!"1900-01-01".equals(endDate)){endDateT=endDate;}
    }
    
%>

<html>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>用户详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarVar.js"></script>
    <script language="javascript" src="/WebLibary/js/tab.js"></script>
    <script type="text/javascript" src="/WebLibary/js/help.js"></script>
    <script type="text/javascript">
        var winstyle = "dialogWidth:20.1;dialogHeight:14.8;center:yes;status:no;help:no";
    </script>
  	<script type="text/javascript">
    var rightArr = new Array();
       
    var winstyle = "dialogWidth:35.1;dialogHeight:17.8;center:yes;status:no";

    var ArrAction0 = new Array(true, "保存", "action_save.gif", "保存", "do_SaveUser");
    var ArrAction1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Back");
    var ArrActions = new Array( ArrAction0,ArrAction1);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
          </script>
</head>
 
<body onload="init();helpInit('8.1.3');">
<input type="hidden" name="helpId" value="">

<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    var tabBox = new TabBox("tttt")
    tabBox.addtab("baseInfo", "用户基本信息")
    tabBox.addtab("deptInfo", "部门资产权限设置")
    tabBox.addtab("catInfo", "专业资产权限设置")
    printTitleBar("系统用户维护");
    printToolBar();
//     tabBox.init();
</script>
<form name="mainFrm" method="post" action="">
    <div style="overflow-y:scroll;width:100%;left:1px;height:450px" align="center">
    <div id="deptInfo" style="display:none;">
      <table border = "0" width = "100%" id = "table3">
    <tr>
       <td width = "15%" align = "right">部门资产查看权限：</td>
            <td width = "45%"> <select name = "deptList" size = "2"
                    style = "height:200;width:100%">
            </select></td>
            <td width = "15%">
                <p><input name = "dept_add" type = "button" onclick = "add_dept()" value = "增加部门"></p>
            <p><input name = "dept_delete" type = "button" onclick = "remove_dept()" value = "删除部门"></p>
            </td>
        <td ></td>

    </tr>
          </table>
</div>
<div id="catInfo" style="display:none;">
    <table border = "0" width = "100%" id = "table2">
        <tr height="30"></tr>
        <tr></tr>
        <tr>
    <td width = "15%" align = "right">专业资产查看权限：</td>
            <td width="25">
                <input type="radio" style="width:60%" name="manRadio" id="manRadio" title="管理类资产" value="1" <%=user.getCategoryCode().equals("MGR-ASSETS")?"checked":""%>>管理
                <input type="radio" style="width:60%" name="manRadio" id="manRadio1" title="网络类资产" value="2" <%=user.getCategoryCode().equals("NET-ASSETS")?"checked":""%>>网络
                </td>
          <td width="15">   </td>
             <td width="15">        </td>
        </tr>
        </table>
</div>
    <div id="baseInfo" style ="width:100%;position: absolute;left:1px">
        <table border="1" bordercolor="#666666" width="98%" id="containTable" align="center" class="detailTb">
            <tr>
                <td>
                    <table border="0" width="100%" id="table1" align="center" class="detailTb">
                        <tr>
                            <td width="10%" align="right" height="22">公司名称：</td>
                            <td width="25%" align="left" height="22">
                                <select style="width:95%" name="orgId"><%=request.getAttribute("OU_OPTIONS")%></select><font color="#FF0000">*</font>
                            </td>
                            <td width="10%" align="right" height="22">用户姓名：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="username" class="input_style1"  style="width:95%" value="<%=user.getUsername()%>"><font color="#FF0000">*</font>
                            </td>
                            <td width="10%" align="right" height="22">显示序号：</td>
                            <td width="20%" align="left" height="22">
                                <input type="text" name="displayOrder" class="input_style1"  style="width:95%" value="<%=user.getDisplayOrder()%>">
                            </td>
                        </tr>
                        <tr>
                            <td width="10%" align="right" height="22">登录帐号：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="loginName" class="input_style1"  style="width:95%" value="<%=user.getLoginName()%>"><font color="#FF0000">*</font>
                            </td>
                            <td width="10%" align="right" height="22">登录密码：</td>
                            <td width="25%" align="left" height="22">
                                <input type="password" name="password" class="input_style1"  style="width:95%" value="<%=user.getPassword()%>"><font color="#FF0000">*</font>
                            </td>
                            <td width="10%" align="right" height="22">OA账号：</td>
                            <td width="20%" align="left" height="22">
                                <input type="text" name="oaName" class="input_style1"  style="width:95%" value="<%=StrUtil.nullToString(ReflectionUtil.getProperty(user, "oaName"))%>">
                            </td>
                        </tr>
                        <tr>
                            <td width="10%" align="right" height="22">员工编号：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="employeeNumber" class="input_style1"  style="width:95%" value="<%=user.getEmployeeNumber()%>">
                            </td>
                            <td width="10%" align="right" height="22">办公电话：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="officeTel" class="input_style1" style="width:95%" value="<%=user.getOfficeTel()%>">
                            </td>
                            <td width="10%" align="right" height="22">E-Mail：</td>
                            <td width="20%" align="left" height="22">
                                <input type="text" name="email" class="input_style1" style="width:95%" value="<%=user.getEmail()%>">
                            </td>
                        </tr>
                        <tr>
                            <td width="10%" align="right" height="22">传真号码：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="fax" class="input_style1" style="width:95%" value="<%=user.getFax()%>">
                            </td>
                            <td width="10%" align="right" height="22">移动电话：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="mobilePhone" class="input_style1" style="width:95%" value="<%=user.getMobilePhone()%>">
                            </td>
                            <td width="10%" align="right" height="22">工作时间：</td>
                            <td width="20%" align="left" height="22">
                                <select name="workScheduleId" style="width:95%">
                                    <option value="">--请选择--</option>
                                    <%=request.getAttribute("workTime") %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td width="10%" align="right" height="22">是否失效：</td>
                            <td width="25%" align="left" height="22">
                                <select name="enabled" style="width:95%">
                                    <%=request.getAttribute("enabledOptionString") %>
                                </select><font color="#FF0000">*</font>
                            </td>
                            <td width="10%" align="right" height="22">生效时间：</td>
                            <td width="25%" align="left" height="22">
                                <input type="text" name="startDate" class="input_style1" style="width:95%"  readonly="readonly"
                                onclick="gfPop.fStartPop(startDate, endDate)" value="<%=startDateT%>">
                            </td>
                            <td width="10%" align="right" height="22">失效时间：</td>
                            <td width="20%" align="left" height="22">
                                <input type="text" name="endDate" class="input_style1" style="width:95%"  readonly="readonly"
                                onclick="gfPop.fEndPop(startDate,endDate)" value="<%=endDateT%>">
                            </td>
                        </tr>
                    </table>
                    <table width="100%" border="0">
                        <tr>
                            <td width="35%" colspan="2" align="center" class="erji">工程</td>
                            <td width="35%" colspan="2" align="center" class="erji">组别</td>
                            <td width="30%" colspan="2" align="center" class="erji">角色</td>
                        </tr>
                        <tr style="height:220px">
                            <td width="10%" align="center">
                                <p><input  name="project_add" type="button" onclick="add_Project();" value="增加工程"></p>
                                <p><input  name="project_delete" type="button" onclick="remove_Project();" value="删除工程"></p>
                            </td>
                            <td width="25%" align="left">
                                <select name="projectName" size="2" id="projectList" style="height:100%;width:95%" onchange="show_Group();">
                                </select>
                            </td>

                            <td  width="10%" align="center">
                                <p><input  name="group_add" type="button" onclick="add_Group();" value="增加组别"></p>
                                <p><input  name="group_delete" type="button" onclick="remove_Group();" value="删除组别"></p>
                            </td>

                            <td  width="25%" align="left">
                                <select name="groupName" size="2" id="groupList" onchange="show_Role();" style="height:100%;width:95%;">
                                </select>
                            </td>

                            <td  width="10%" align="center">
                                <p><input  name="role_add" type="button" onclick="add_Role();" value="增加角色"></p>
                                <p><input  name="role_delete" type="button" onclick="remove_Role();" value="删除角色"></p>
                            </td>
                            <td  width="20%" align="left">
                                <select name="roleName" size="2" multiple id="roleList" style="height:100%;width:95%;">
                                </select>
                            </td>
                        </tr>
                       <tr align="center">
                          <td align="center" height="22" colspan="6"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
        <input type="hidden" name="act" value="">
        <input type="hidden" name="userId" value="<%=user.getUserId()%>">
        <input type="hidden" name="rightChanged" value="false">
        <input type="hidden" name="str" value="<%= str %>"/>
        <input type="hidden" name="strDept" value="<%= strDept %>"/>
        <input type="hidden" name="strRole" value="<%= strRole %>"/>
        <input type="hidden" name="strRemark" value=""/>
    </div>
</form>
<%= WebConstant.WAIT_TIP_MSG%>
		<iframe width=174 height=189 name="gToday:normal:calendar.js"
			id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;">
		</iframe>
</body>
</html>
<script type="text/javascript">
var enabled = mainFrm.enabled.value;
var roleListOld = "";
function do_SaveUser() {// 保存用户信息
    formatStr();
    formatDeptStr();
    var date = new Date();
    var strRemark = "操作者：" + '<%=userName %>' + "，操作时间：" + date.toLocaleDateString() + "；";
    if (mainFrm.loginName.value != '<%=user.getLoginName() %>') {
    	strRemark += "登陆帐号、原值：" + '<%=user.getLoginName() %>' + "，现值：" + mainFrm.loginName.value + "；";
    } 
    if (mainFrm.username.value != '<%=user.getUsername() %>') {
    	strRemark += "用户姓名、原值：" + '<%=user.getUsername() %>' + "，现值：" + mainFrm.username.value + "; ";
    } 
    if (mainFrm.oaName.value != '<%=StrUtil.nullToString(ReflectionUtil.getProperty(user, "oaName"))%>') {
    	strRemark += "OA账号、原值：" + '<%=StrUtil.nullToString(ReflectionUtil.getProperty(user, "oaName"))%>' + "，现值：" + mainFrm.oaName.value + "; ";
    }
    if (mainFrm.officeTel.value != '<%=user.getOfficeTel() %>') {
    	strRemark += "办公电话、原值：" + '<%=user.getOfficeTel() %>' + "，现值：" + mainFrm.officeTel.value + "; ";
    } 
    if (mainFrm.fax.value != '<%=user.getFax() %>') {
    	strRemark += "传真号码、原值：" + '<%=user.getFax() %>' + "，现值：" + mainFrm.fax.value + "; ";
    } 
    if (mainFrm.password.value != '<%=user.getPassword() %>') {
    	strRemark += "密码、原值：" + '<%=user.getPassword() %>' + "，现值：" + mainFrm.password.value + "; ";
    } 
    if (mainFrm.mobilePhone.value != '<%=user.getMobilePhone() %>') {
   		strRemark += "移动电话、原值：" + '<%=user.getMobilePhone() %>' + "，现值：" + mainFrm.mobilePhone.value + "; ";
    } 
    if (mainFrm.email.value != '<%=user.getEmail() %>') {
    	strRemark += "E-mail、原值：" + '<%=user.getEmail() %>' + "，现值：" + mainFrm.email.value + "; ";
    } 
    if (mainFrm.enabled.value != enabled) {
    	strRemark += "是否失效、现值：" + mainFrm.enabled.value + "; ";
    }
    if (mainFrm.str.value != '<%=str%>') {
    	strRemark += "权限、原值：" + '<%=str %>' + "，现值：" + mainFrm.str.value + "; ";
    }
    mainFrm.strRemark.value = strRemark.substring(0,strRemark.length-1);
    if (validForm()) {
       if(validPassword()){
	        var action = "<%=WebActionConstant.CREATE_ACTION%>";
	        if ( patchCreateOrUpdate( mainFrm.userId.value ) ) {
	            if (confirm("确认更新该用户吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
	                action = "<%=WebActionConstant.UPDATE_ACTION%>";
	            } else {
	                return;
	            }
	        }
	        mainFrm.act.value = action;	
	        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet";
	
	        mainFrm.submit();
       }
    }
}


function validPassword() {
   var validate=true;
   if(mainFrm.password.value != '<%=user.getPassword() %>'){
		if(mainFrm.password.value.length>=8){
			var reg= /(?=[!-~]{8,20})(?=[!-~]*[^0-9]+)(?=[!-~]*\d+)/;
			if (!reg.exec(mainFrm.password.value)) {
				alert("密码中必须包含字母和数字!");
				validate=false;
			}
		}else{
			alert("密码不得少于8位");
			validate=false;
		}
	}
	return validate;
}


function validForm() {
    var fieldNames = "orgId;loginName;username;password";
    var fieldLabels = "OU;登录账号;用户姓名;用户密码";
    return formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
}
function do_DeleteUser() {//删除用户
    var userId = mainFrm.userId.value;
    if (userId != "") {
        mainFrm.userId.value = "";
        mainFrm.loginName.value = "";
        mainFrm.username.value = "";
        if (confirm("确认删除信息吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet?userId=" + userId;
            mainFrm.submit();
        }
    }
}

function do_Back() {//返回
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet";
    mainFrm.submit();
}

var proGroupArr = new Array();
var proGroupRoleArr = new Array();
var deptArr = new Array();
var deptCodeArr=new Array();
var project_Obj = document.getElementById("projectList");
var group_Obj = document.getElementById("groupList");
var dept_Obj = document.getElementById("deptList");
var role_Obj = document.getElementById("roleList");


function add_Project() {
    var retuenValue = window.showModalDialog("/system/showOptions.jsp", getProject(), winstyle);
    if (retuenValue) {
        arr = explode(retuenValue, ";");
        add_Option(project_Obj, arr);
    }
}

function add_Group() {
    var project = mainFrm.projectName.value;
    if (project == "") {
        alert("请选择工程");
        return;
    }
    var orgId = mainFrm.orgId.value;
    var retuenValue = window.showModalDialog("/system/showOptions.jsp", getGroup(project, orgId), winstyle);
    if (retuenValue) {
        arr = explode(retuenValue, ";");
        var addArr = add_Option(group_Obj, arr);
        add_Arr(project_Obj, null, addArr);
        show_Role();
    }
}
 function add_dept() {

    var orgId = mainFrm.orgId.value;
    var retuenValue = window.showModalDialog("/system/showOptions.jsp", getDept( orgId), winstyle);
    if (retuenValue) {
        arr = explode(retuenValue, ";");
        var addArr = add_Option(dept_Obj, arr);
         for (var i = 0; i < addArr.length; i++) {
            var tempArr = new Array(null, addArr[i]);
            deptArr[deptArr.length] = tempArr;
        }
//        add_Arr(null, null, addArr);
    }
}

function roleSelectAll(){
	for(var i=0; i < mainFrm.roleList.length; i++) {
		mainFrm.roleList.options[i].selected = true;
	}
}
function roleUnSelectAll(){
	for(var i=0; i< mainFrm.roleList.length; i++) {
		mainFrm.roleList.options[i].selected = false;
	}
}

function add_Role() {
	
    var project = mainFrm.projectName.value;
    var sysAdminRole = "<%=sysAdminRole%>";
    if (project == "" || mainFrm.groupName.value == "") {
        alert("未选择工程与组别");
        return;
    }

    var retuenValue = window.showModalDialog("/system/showOptions.jsp", getRole(project, sysAdminRole), winstyle);
    if (retuenValue) {
        arr = explode(retuenValue, ";");
        var addArr = add_Option(role_Obj, arr);
        add_Arr(project_Obj, group_Obj, addArr);
    }
}

function remove_Group() {//移除选中的组别
    var del = delete_option(group_Obj);
    delete_Arr(null, group_Obj, null, del);
    group_Obj.onchange();
}
function remove_Project() {//移除选中的项目
    var del = delete_option(project_Obj);
    delete_Arr(project_Obj, null, null, del);
    project_Obj.onchange();
}

function remove_dept() {//移除选中的组别
    var del = delete_option(dept_Obj);
    delete_Arr(null, dept_Obj, null, del);
    dept_Obj.onchange();
}

function remove_Role() {//移除选中的角色

    var del = delete_option(role_Obj);
    delete_Arr(null, null, role_Obj, del);
}


function show_Group() {//显示组别
    if (proGroupArr != null) {
        group_Obj.options.length = 0;
        role_Obj.options.length = 0;
        for (var i = 0; i < proGroupArr.length; i++) {
            if (proGroupArr[i][0] == project_Obj.value) {
                group_Obj.add(new Option(proGroupArr[i][1], proGroupArr[i][1]));
            }
        }
    }
}

function show_Role() {//显示角色
    if (proGroupRoleArr != null) {
        role_Obj.options.length = 0;
        for (var i = 0; i < proGroupRoleArr.length; i++) {
            if (proGroupRoleArr[i][0] == project_Obj.value &&
                proGroupRoleArr[i][1] == group_Obj.value) {
                role_Obj.add(new Option(proGroupRoleArr[i][2], proGroupRoleArr[i][2]));
            }
        }
    }
}

function delete_Arr(project, group, role, args) {//移除保存在数组中的选项

    if (project != null && group == null && role == null) {
        for (var i = 0; i < args.length; i++) {
            for (var j = 0; j < proGroupArr.length; j++) {
                if (proGroupArr[j][0] == args[i]) {
                    proGroupArr.splice(j, 1);
                    j--;
                }
            }

            for (var j = 0; j < proGroupRoleArr.length; j++) {
                if (proGroupRoleArr[j][0] == args[i]) {
                    proGroupRoleArr.splice(j, 1);
                    j--;
                }
            }
        }
    } else if (group != null && project == null && role == null) {
        for (var i = 0; i < args.length; i++) {
            for (var j = 0; j < proGroupRoleArr.length; j++) {
                if (proGroupRoleArr[j][0] == project_Obj.value &&
                    proGroupRoleArr[j][1] == args[i]) {
                    proGroupRoleArr.splice(j, 1);
                    j--;
                }
            }

            for (var j = 0; j < proGroupArr.length; j++) {
                if (proGroupArr[j][0] == project_Obj.value
                        && proGroupArr[j][1] == args[i]) {
                    proGroupArr.splice(j, 1);
                }
            }
        }
    } else if (role != null && group == null && project == null) {
        for (var i = 0; i < args.length; i++) {
            for (var j = 0; j < proGroupRoleArr.length; j++) {
                if (proGroupRoleArr[j][0] == project_Obj.value &&
                    proGroupRoleArr[j][1] == group_Obj.value &&
                    proGroupRoleArr[j][2] == args[i]) {
                    proGroupRoleArr.splice(j, 1);
                    break;
                }
            }
        }
    }
}

function add_Arr(project, group, arr) {//将选中的下拉列表，添加到数组中

	//roleListOld = mainFrm.roleList.value;

    if (group == null) {
        for (var i = 0; i < arr.length; i++) {
            var tempArr = new Array(project.value, arr[i]);
            proGroupArr[proGroupArr.length] = tempArr;
        }
    } else {
        for (var i = 0; i < arr.length; i++) {
            var tempArr = new Array(project.value, group.value, arr[i]);
            proGroupRoleArr[proGroupRoleArr.length] = tempArr;
        }
    }
}
 function formatDeptStr() {//将数组的数格式化为字符串，并将值赋予隐藏域
    if (deptArr.length < 0) {
        return;
    }
    var str = "";
    for (var i in deptArr) {
        str += deptArr[i] + ";"
    }
    str = str.substring(0, str.length - 1);
    mainFrm.strDept.value = str;
}
function formatStr() {//将数组的数格式化为字符串，并将值赋予隐藏域
    if (proGroupRoleArr.length < 0) {
        return;
    }
    var str = "";
    for (var i in proGroupRoleArr) {
        str += proGroupRoleArr[i][0] + "," + proGroupRoleArr[i][1] + "," + proGroupRoleArr[i][2] + ";"
    }
    str = str.substring(0, str.length - 1);
    mainFrm.str.value = str;
}
function unDeptFromatStr() {//将字符串还原成数组
    var str = mainFrm.strDept.value;
    var argArr = str.split(";");
    for (var i = 0; i < argArr.length; i++) {
         var arr = argArr[i].split(",");
       /* if (deptArr.length == 0) {
            deptArr[deptArr.length] = new Array(arr[0], arr[0]);
        } else {
             deptArr[i][0]=arr[i][0];
             deptArr[deptArr.length]= new Array(arr[i], arr[0]);
        }*/
        if (deptArr.length == 0) {
            deptArr[deptArr.length] = new Array(arr[0]);
        } else {
           for (var j = 0; j < deptArr.length; j++) {
                if (arr[0] == deptArr[j]) {
                    break;
                }
                if (j == deptArr.length - 1) {
                    deptArr[deptArr.length] = new Array(arr[0]);
                }
            }
        }


    }
}
function unFromatStr() {//将字符串还原成数组
    var str = mainFrm.str.value;
    var argArr = str.split(";");

    for (var i = 0; i < argArr.length; i++) {
        var arr = argArr[i].split(",");
        if (proGroupArr.length == 0) {
            proGroupArr[proGroupArr.length] = new Array(arr[0], arr[1]);
        } else {
            for (var j = 0; j < proGroupArr.length; j++) {
                if (arr[0] == proGroupArr[j][0] && arr[1] == proGroupArr[j][1]) {
                    break;
                }
                if (j == proGroupArr.length - 1) {
                    proGroupArr[proGroupArr.length] = new Array(arr[0], arr[1]);
                }
            }
        }
    }

    for (var i in argArr) {
        var temp2 = argArr[i].split(",");
        proGroupRoleArr[proGroupRoleArr.length] = new Array(temp2[0], temp2[1], temp2[2]);
    }
}

function init() {
    selectSpecialOptionByItem(mainFrm.orgId, "<%=user.getOrganizationId()%>");
    if (mainFrm.str.value == "") {
        return;
    }

    unFromatStr();
    unDeptFromatStr();
    for (var i = 0; i < deptArr.length; i++) {
        if (dept_Obj.length == 0) {
            dept_Obj.add(new Option(deptArr[i],null));
        } else {
            dept_Obj.add(new Option(deptArr[i],null));
        }
    }
    for (var i = 0; i < proGroupArr.length; i++) {
        if (project_Obj.length == 0) {
            project_Obj.add(new Option(proGroupArr[i][0], proGroupArr[i][0]));
        } else {
            for (var j = 0; j < project_Obj.length; j++) {
                if (proGroupArr[i][0] == project_Obj[j].value) {
                    break;
                }
                if (j == project_Obj.length - 1) {
                    project_Obj.add(new Option(proGroupArr[i][0], proGroupArr[i][0]));
                }
            }
        }
    }
}
</script>