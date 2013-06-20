<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%--<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>--%>
<html>
<style>
    .resizeDivClass{
    text-align:right;
    width:3px;
    margin:0px 0 0px 0;
    border:0px;
    float:right;
    cursor:e-resize;
    }

</style>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>系统用户维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarResize.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
    <script type="text/javascript" src="/WebLibary/js/help.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>

<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onload="doChecked();helpInit('8.1.3');" onkeydown="do_check()">
<input type="hidden" name="helpId" value="">

<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String loginName = StrUtil.nullToString(request.getParameter("loginName"));
    String username = StrUtil.nullToString(request.getParameter("username"));
    String action = StrUtil.nullToString(request.getParameter("act"));
%>

<form name="mainFrm" method="post" action="/servlet/com.sino.sinoflow.user.servlet.SfUserServlet">
    <script language="javascript">
        printTitleBar("系统用户维护");
    </script> 
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="userId" value="">
    <table border="0" width="100%"  id="table1">

        <tr> 
            <td width="8%"  align="right">公司名称：</td>
            <td width="12%"><select name="orgId" class="select_style1" style="width:100%" onchange="projectNameChange();"><%=request.getAttribute("OU_OPTIONS")%></select> </td>
            <td width="8%" align="right">登录账号：</td>
            <td width="12%">
              <input type="text" name="loginName" class="input_style1" style="width:100%" value="<%=loginName%>">
            </td>
            <td width="8%" align="right">真实姓名：</td>
            <td width="12%">
              <input type="text" name="username" class="input_style1" style="width:100%" value="<%=username%>">
            </td>
            <td width="8%" align="center">
             <img align="middle" src="/images/eam_images/search.jpg" alt="查询用户" onclick="do_SearchUser(); return false;">
            </td>
        </tr>
        <tr>
            <td width="8%" align="right">工程名称：</td>
            <td width="12%">
            	<select class="select_style1" style="width:100%" name="projectName" onchange="projectNameChange();">
            		<option value="">--请选择--</option>
            		<%= request.getAttribute(WebAttrConstant.PROJECT_OPTION_STR) %>
            	</select>
            </td>

            <td width="8%" align="right">组别名称：</td>
            <td width="12%" id="gp">
            	<select class="select_style1" style="width:100%" name="groupName" onchange="groupNameChange()">
            		<%= request.getAttribute(WebAttrConstant.GROUP_OPTION_STR_SELECT) %>
            	</select>
            </td>

            <td width="8%"  align="right">角色名称：</td>
            <td width="12%" id="rn">
            	<select class="select_style1" style="width:100%" name="roleName">
            		<%= request.getAttribute(WebAttrConstant.ROLE_OPTION_STR_SELECT) %>
            	</select>
            </td>

            <td width="8%" align="center">
            <img align="middle" src="/images/eam_images/new.jpg" alt="点击新增" onclick="do_CreateUser(); return false;">
           </td>
        </tr>
    </table>
    <script type="text/javascript">
        var columnArr = new Array("登录帐号", "用户姓名", "员工编号", "移动电话", "传真", "电子邮件");
        var widthArr = new Array("16%", "12%", "16%", "16%", "12%", "28%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div id="dataDiv" style="overflow-y:scroll;height:300px;width:100%;left:1px;margin-left:0px;" align="left" onscroll="document.getElementById('headDiv').scrollLeft=this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" style="table-layout:fixed;">
    <!--<table id=theObjTable>
    <tr bgcolor=azure>
    <td valign=top width="16%">
    <font class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></font>
    登录帐号</td>
    <td valign=top width="12%">
    <font class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></font>
    用户姓名</td>
    <td valign=top width="16%">
    <font class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></font>
    员工编号</td>
        <td valign=top width="16%">
        <font class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></font>
        移动电话</td>
        <td valign=top width="12%">
        <font class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></font>
        传真</td>
        <td valign=top width="28%">
        <font class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></font>
        电子邮件</td>
        </tr>-->


            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("USER_ID")%>'); return false;">
                <td height="21" width="16%"><%=row.getValue("LOGIN_NAME")%></td>
                <td height="21" width="12%"><%=row.getValue("USERNAME")%></td>
                <td height="21" width="16%"><%=row.getValue("EMPLOYEE_NUMBER")%></td>
                <td height="21" width="16%"><%=row.getValue("MOBILE_PHONE")%></td>
                <td height="21" width="12%"><%=row.getValue("FAX")%></td>
                <td height="21" width="28%"><%=row.getValue("EMAIL")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="pageNaviDiv">
 <%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>

<script language="javascript">

    function do_SearchUser() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet";
        mainFrm.submit();
    }

    function do_CreateUser() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet";
        mainFrm.submit();
    }

    function do_ShowDetail(userId) {
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&userId=" + userId;
        mainFrm.submit();
    }

    function doChecked() {
        do_SetPageWidth();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchUser();
        }
    }


	/* 工程名发生变化,查询相应的组别，以及角色返回,实现菜单联动 */
    function projectNameChange() {
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		var strg = "<select name='groupName' class=\"input_1\" style='width:100%'>";
		var strr = "<select name='roleName' class=\"input_1\" style='width:100%'>";
		var str1 = "<option value=''>--请选择--</option>";
		var str2 = "</select>";
	 	var pName = mainFrm.projectName.value;
	 	var orgId = mainFrm.orgId.value;
		if(pName == -1){
			document.getElementById("gp").innerHTML = str+str2;
			document.getElementById("rn").innerHTML = str+str2;
			return;
		}
		
		try{
            var crs = new clientRowSet();
            crs.modelClassName = "com.sino.sinoflow.user.model.SfGroupModel";
            crs.methodName = "getOptionGroupModel";
            crs.methodParameterName = new Array(pName, orgId);
            var res = crs.send_request();
 	    	var optionStr = "";
     		for(var i = 0;i<res.length;i++){
				optionStr += "<option value='"+res[i].GROUP_NAME+"'>"+res[i].GROUP_NAME+"</option>"; 
 			}
         	optionStr = strg+str1+optionStr+str2;
         	document.getElementById("gp").innerHTML = optionStr;
         	
         	crs.modelClassName = "com.sino.sinoflow.user.model.SfRoleModel";
            crs.methodName = "getRoleOptionModel";
            crs.methodParameterName = new Array(mainFrm.projectName.value);
            
           	res = crs.send_request();
 	    	var optionStr = "";
     		for(var i = 0;i<res.length;i++){
				optionStr += "<option value='"+res[i].ROLE_NAME+"'>"+res[i].ROLE_NAME+"</option>"; 
 			}
         	optionStr = strr+str1+optionStr+str2;
         	document.getElementById("rn").innerHTML = optionStr;
         	
       }catch(e){
           alert(e);
       }
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "hidden";
	}

</script>