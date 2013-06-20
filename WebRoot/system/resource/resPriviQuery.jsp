<%@ page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.sinoflow.base.util.FlowTaskTool"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant,com.sino.sinoflow.framework.resource.dto.SfResDefineDTO"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>栏目权限查询</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
		<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
		<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
        <script type="text/javascript"  src="/WebLibary/js/clientRowSet.js"></script>
        <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
        <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
        <script type="text/javascript"  src="/WebLibary/js/OperationProjectGroupRole.js"></script>
	</head>
	<body leftmargin="1" topmargin="0" onload="initPri();">
    <form name="mainFrm" action="/servlet/com.sino.sinoflow.framework.resource.servlet.SfResPrivsServlet" method="post">
    <script type="text/javascript">
                printTitleBar("栏目权限维护");
    </script>
    <%
            SfResDefineDTO resource = (SfResDefineDTO) request.getAttribute(WebAttrConstant.RES_DATA);
        %>
		<table border="0" width="100%" id="table1">
			<tr height="22">
				<td width="15%"  align="right">栏目名称：</td>
				<td width="55%"  align="left">
					<input type="text" name="resName" readonly class="input_style2" style="width:100%" value="<%=FlowTaskTool.escapeHTML(resource.getResName())%>">
				</td>
				<td width="20%"  align="right">
				    <img src="/images/eam_images/save.jpg"    onClick="do_SavePrivi();">
				</td>
                <td width="10%">&nbsp;</td>
			</tr>
		</table>
        <br>
			<div align="center">
				<hr size="1" color="#3366EE" width="100%">
				<table border="0" width="60%" cellspacing="0" cellpadding="0">
                    <tr>
						<td width="40%" align="center">
							<span lang="zh-cn">已选组别</span>
						</td>
						<td width="20%" align="center">	</td>
						<td width="40%" align="center">
							已选角色
						</td>
					</tr>
					<tr height="450">
						<td width="40%">
							<!--<select name="allRole" class="input_1" style="width: 100%; height: 100%" multiple
								size="20" ondblclick="do_AddRole()" title="双击选择该角色">
								<%=request.getAttribute(WebAttrConstant.ALL_ROLE_OPTION)%>-->
                            <select name="groupName" class="select_style1" style="width: 100%;height:75%" multiple
                                size="20" onchange="showRole();" title="双击移除该角色">
                                <%=request.getAttribute("group")%>
							</select>
						</td>
						<td width="20%" align="center" valign="middle">
							<!--<img src="/images/eam_images/new.jpg" onClick="do_AddRole();">
							<p>
								<img src="/images/eam_images/add_all.jpg" onClick="do_AddAllRole();">
							<p>
								<img src="/images/eam_images/delete.jpg" onClick="do_RemoveRole()">
							<p>
								<img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAllRole()">-->
                            <input type="button" value="添加组别" onclick="addGroup();" class="xfBtn"/><p>
                            <input type="button" value="删除组别" onclick="removeGroup();" class="xfBtn"/><p>
                            <input type="button" value="添加角色" onclick="addRole();" class="xfBtn"/><p>
                            <input type="button" value="删除角色" onclick="removeRole();" class="xfBtn"/><p>
                        </td>
						<td width="40%">
							<select name="roleName" class="select_style1" style="width: 100%; height:75%" multiple
								size="20" ondblclick="removeRole()" title="双击移出该角色">
								<!--<%=request.getAttribute(WebAttrConstant.VIEW_ROLE_OPTION)%>-->
							</select>
						</td>
					</tr>
				</table>
			</div>
			<input type="hidden" name="systemId" value="<%=resource.getSystemId()%>">
			<input type="hidden" name="resId" value="<%=resource.getResId()%>">
            <input type="hidden" name="hiValue" value=""/>
			<input type="hidden" name="act">
		</form>
	</body>
</html>
<script>

function do_SavePrivi(){
	mainFrm.hiValue.value=formatStr(selArr);
	var index = mainFrm.resId.value.indexOf(".");
	if(index > -1){
		var resName = mainFrm.resName.value;
		if(confirm("栏目“"+resName+"”为子栏目，直系上级栏目将自动拥有所设权限。是否继续？")){
			mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
			mainFrm.submit();
		}
	} else {
		mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
		mainFrm.submit();
	}
}


</script>

<script type="text/javascript">
<!--
	var winS = "center:yes;dialogWidth:17;dialogHeight:25;status:no;scroll:no";
	var selArr = new Array(<%= request.getAttribute("arrData") %>);
	var group_Obj = mainFrm.groupName;
	var role_Obj = mainFrm.roleName;

	function addGroup(){
		var url = "/servlet/com.sino.sinoflow.user.servlet.SfGroupServlet?act=selOpt";
		var returnArr = window.showModalDialog(url, null, winS);
		if(returnArr){
			add_Option(group_Obj, returnArr);
		}
        group_Obj.onchange();
    }

	function removeGroup(){
		var groupValue = group_Obj.value;
		for(var i=0;i<selArr.length;i++){
			if(groupValue == selArr[i][0]){
				selArr.splice(i,1);
				i--;
			}
		}

		delete_option(group_Obj);
		group_Obj.onchange();
	}

	function addRole(){
		if(group_Obj.value == ''){
			alert("未选择组别");
			return;
		}
		var url = "/servlet/com.sino.sinoflow.user.servlet.SfRoleServlet?act=selOpt";
		var returnArr = window.showModalDialog(url, null, winS);
		if(returnArr){
			var arr = add_Option(role_Obj, returnArr);
			addArr(arr);
		}
	}

	function removeRole(){
		var arr = delete_option(role_Obj);
		var groupValue = group_Obj.value;
		for(var i in arr){
			for(var j in selArr){
				if(selArr[j][0]==groupValue&&selArr[j][1]==arr[i]){
					selArr.splice(j,1);
					break;
				}
			}
		}
	}

	function addArr(arr){//将当前选中的组别以及对应的角色添加到数组中
		var groupValue = group_Obj.value;
		if(selArr.length==0){
			for(var i in arr){
				selArr.push(new Array(groupValue,arr[i]));
			}
		}else{
			for(var i in arr){
				for(var j in selArr){
					if(groupValue == selArr[j][0] && arr[i]==selArr[j][1]){
						break;
					}else if(selArr.length-1==j){
						selArr.push(new Array(groupValue,arr[i]));
					}
				}
			}
		}
	}

	function showRole(){
		var groupValue = group_Obj.value;
        role_Obj.options.length=0;
		for(var i in selArr){
			if(selArr[i][0]==groupValue){
				role_Obj.add(new Option(selArr[i][1],selArr[i][1]));
			}
		}
	}

	function formatStr(arr){
		if(arr.length == 0) return "";
		var str = "";
		for(var i=0;i<arr.length-1;i++){
			str+=arr[i][0]+","+arr[i][1]+";"
		}
		str+=arr[arr.length-1][0]+","+arr[arr.length-1][1];
		return str;
	}

    function initPri(){
        var group=document.getElementById("groupName");
        if(group.length>0){
            group[0].selected=true;
            showRole();
        }
    }
//-->
</script>
