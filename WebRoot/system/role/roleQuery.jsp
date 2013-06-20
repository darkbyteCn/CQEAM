<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>系统角色维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/help.js"></script>

</head>

<body leftmargin="1" topmargin="0" onload="helpInit('8.1.1');">
<input type="hidden" name="helpId" value="">

<%
    String roleId = StrUtil.nullToString(request.getParameter("roleId"));
    String roleName = StrUtil.nullToString(request.getParameter("roleName"));
    String roleDesc = StrUtil.nullToString(request.getParameter("roleDesc"));
    String action = StrUtil.nullToString(request.getParameter("act"));
%>
<form name="mainFrm" method="POST" action="">
    <script type="text/javascript">
        printTitleBar("系统角色维护");
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">
    <jsp:include page="/message/MessageProcess"/>
    <table border="0" width="100%" id="queryTable" class="queryTable">
        <tr>
            <td width="10%" align="right">角色名称：</td>
            <td width="15%"><input type="text" class="input_style1" name="roleName" style="width:100%" value="<%= roleName %>"></td>
            
            <td width="10%" align="right">角色描述：</td>
            <td width="15%"><input type="text" class="input_style1" name="roleDesc"  style="width:100%" value="<%= roleDesc %>"></td>
            
            <td width="10%" align="right">是否可见：</td>
			<td width="15%">
				<select name="enabled"  class="select_style1" style="width:100%">
					<option value="Y">是</option>
					<option value="N">否</option>
				</select>
			</td>
            <td width="25%" align="right">
                <img align="middle" src="/images/eam_images/search.jpg" alt="查询角色" onclick="do_SearchRole();">
                <img align="middle" src="/images/eam_images/new.jpg" alt="新增角色" onclick="do_CreateRole();">
            </td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("角色名称", "角色描述", "排序号");
        var widthArr = new Array("35%", "50%", "15%");
        printTableHead(columnArr, widthArr);
    </script>

<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px">
    <table width="100%" border="1">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("ROLE_ID")%>'); return false;">
            <td style="word-wrap:break-word" height="20" width="35%"><%=row.getValue("ROLE_NAME")%></td>
            <td style="word-wrap:break-word" height="20" width="50%"><%=row.getValue("ROLE_DESC")%></td>
            <td style="word-wrap:break-word" height="20" width="15%"></td>
        </tr>
        <%
            }  }
        %>
    </table>
</div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>

<script type="text/javascript">

    function do_SearchRole() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfRoleServlet";
        mainFrm.submit();
    }

    function do_CreateRole() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfRoleServlet";
        mainFrm.submit();
    }

    function do_ShowDetail(roleId) {
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfRoleServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&roleId=" + roleId;
        mainFrm.submit();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchRole();
        }
    }
    
    function checkAll(args,args1){
    	//打印表格头是的事件
    }
    
    function executeClick(args){
    	//打印表格头是的事件
    }
</script>