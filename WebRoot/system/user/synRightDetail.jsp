<%@ page import="com.sino.ams.system.user.dto.AmsSynRightDTO" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2009-6-12
  Time: 11:57:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
      <title>资产同步权限详细信息</title>
      <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
      <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
  </head>
  <body leftmargin="1" topmargin="0" onload="doChecked();">
  <%
      AmsSynRightDTO dto = (AmsSynRightDTO) request.getAttribute(WebAttrConstant.GROUP_ATTR);

  %>
  <form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet">
    <script language="javascript">
        printTitleBar("资产同步权限详细信息");
    </script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="userId" value="<%=dto.getUserId()%>">
    <table border="0" width="100%" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="12%" align="right">用户名：</td>
            <td width="15%"><input type="text" name="userName" readonly class="input_style2" readonly style="width:100%" value="<%=dto.getUserName()%>"  <% if(dto.getUserId()==0){%>onclick="do_selectName()" <%}%>></td>
            <td width="12%" align="right">登录名：</td>
            <td width="15%">
                <input type="text" name="loginName" readonly class="input_style2" readonly style="width:100%" value="<%=dto.getLoginName()%>" <% if(dto.getUserId()==0){%>onclick="do_selectName()" <%}%>>
            </td>
            <td width="20%"></td>
        </tr>
        <tr>
    </tr>

    </table>
      <table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="40%" align="center"><span lang="zh-cn">可选地市</span></td>
		<td width="20%" align="center">操作</td>
		<td width="40%" align="center">已选地市</td>
	</tr>
</table>
<hr size="1" color="#3366EE">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr height="450">
		<td width="40%">
			<select name="allOrganizationId" class="select_style1" style="width:100%;height:100%" multiple size="20" ondblclick="do_AddRole()" title="双击选择该地市">
				<%=request.getAttribute(WebAttrConstant.ALL_ROLE_OPTION)%>
			</select>
		</td>
		<td  width="20%" align="center">
			<img src="/images/eam_images/new.jpg" onClick="do_AddRole();"><p>
			<img src="/images/eam_images/add_all.jpg" onClick="do_AddAllRole();"><p>
			<img src="/images/eam_images/delete.jpg" onClick="do_RemoveRole()"><p>
			<img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAllRole()"><p>
			<img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SavePrivi(); return false;">
		</td>
		<td width="40%">
			<select name="organizationId" class="select_style1" style="width:100%;height:100%" multiple size="20" ondblclick="do_RemoveRole()" title="双击移出该地市">
				<%=request.getAttribute(WebAttrConstant.VIEW_ROLE_OPTION)%>
			</select>
		</td>
	</tr>
</table>
  </form>
  </body>
</html>
<script type="text/javascript">
       function doChecked() {
		do_SetPageWidth();
    }

       function do_Save() {
           if(mainFrm.userId.value==""){
             alert("请选择用户!")  ;
               return;
           }
           var action = "<%=WebActionConstant.UPDATE_ACTION%>";
           mainFrm.act.value = action;
           mainFrm.action = "/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet";
           mainFrm.submit();

       }
       function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_USER_RIGHT%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
        var users = window.showModalDialog(url, null, popscript);

        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
            mainFrm.userId.value = '';
            mainFrm.userName.value = '';
            mainFrm.loginName.value = '';
        }
       }
    function do_AddRole(){
	moveSelectedOption("allOrganizationId", "organizationId");
}

function do_AddAllRole(){
	moveAllOption("allOrganizationId", "organizationId");
}

function do_RemoveRole(){
	moveSelectedOption("organizationId", "allOrganizationId");
}

function do_RemoveAllRole(){
	moveAllOption("organizationId", "allOrganizationId");
}

function do_SavePrivi(){
	selectAll("organizationId");
		mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
		mainFrm.submit();

}
</script>