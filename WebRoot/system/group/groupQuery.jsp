<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>组别查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">		
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/help.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>

<body leftmargin="1" topmargin="0" onload="doChecked();helpInit('8.1.2');" onkeydown="autoExeFunction('do_SearchGroup();');">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String groupName = reqParser.getParameter("groupName");
    String groupDesc = reqParser.getParameter("groupDesc");
    String action = reqParser.getParameter("act");
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.sinoflow.user.servlet.SfGroupServlet">
    <script language="javascript">
        printTitleBar("系统组别维护");
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">

    <table border="0" width="100%" id="table1">
        <tr>
            <td width="12%" align="right">组别名称：</td>
            <td width="15%"><input type="text" name="groupName"  class="input_style1" style="width:100%" value="<%=groupName%>"></td>

            <td width="12%" align="right">组别描述：</td>
            <td width="15%"><input type="text" name="groupDesc"  class="input_style1" style="width:100%" value="<%=groupDesc%>"></td>

            <td width="8%" align="center">
             <img align="middle" src="/images/eam_images/new.jpg" alt="点击新增" onclick="do_CreateGroup(); return false;">
            </td>
        </tr>
        <tr>
           <td width="11%" align="right">是否有效：</td>
            <td width="10%">
                <select name="enabled" id="enabled"  class="select_style1" style="width:100%">
                    <option value="Y" <%if (reqParser.getParameter("enabled").equals("Y")) {%> selected <%}%>>
                        是
                    </option>
                    <option value="N" <%if (reqParser.getParameter("enabled").equals("N")) {%> selected <%}%>>
                        否
                    </option>
                </select>
            </td>
            
            <td width="12%" align="right">项目名称：</td>
            <td width="15%">
            	<select  class="select_style1" style="width:100%" name="projectName">
            		<option value="">--请选择--</option>
            		<%= request.getAttribute(WebAttrConstant.PROJECT_OPTION_STR) %>
            	</select>
            </td>          
            <td width="8%" align="center">
            <img align="middle" src="/images/eam_images/search.jpg" alt="查询组别" onclick="do_SearchGroup(); return false;">
            </td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("项目名称","组别名称", "上级组别", "组别描述", "是否有效");
        var widthArr = new Array("20%", "20%", "20%", "25%", "15%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div id="dataDiv" style="overflow-y:scroll;height:300px;width:100%;left:1px;margin-left:0px">
        <table width="100%" border="1">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("GROUP_ID")%>'); return false;">
                <td style="word-wrap:break-word" height="22" width="20%"><%=row.getValue("PROJECT_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="20%"><%=row.getValue("GROUP_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="20%"><%=row.getValue("PARENT_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="25%"><%=row.getValue("GROUP_DESC")%></td>
                <td style="word-wrap:break-word" height="22" width="15%"><%=row.getValue("ENABLED")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
    <%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">
    function do_SearchGroup() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfGroupServlet";
        mainFrm.submit();
    }

    function do_CreateGroup() {
        mainFrm.groupId.value = "";
        mainFrm.groupName.value = "";
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfGroupServlet";
        mainFrm.submit();
    }

    function do_ShowDetail(groupId) {
        mainFrm.action = "/servlet/com.sino.sinoflow.user.servlet.SfGroupServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&groupId=" + groupId;
        mainFrm.submit();
    }

    function doChecked() {
        do_SetPageWidth();
    	if(mainFrm.projectName.value == -1){
    		mainFrm.projectName.value = "";
    	}
        var select = document.getElementById("enabled");
        for (var i = 0; i < select.length; i++) {
            if (select.options[i].value == document.mainFrm.enabled.value) {
                select.options[i].selected = true;
            }
        }
        refreshTree();
    }

    function refreshTree() {
        var action = mainFrm.act.value;
        if (action == "<%=WebActionConstant.UPDATE_ACTION%>" || action == "<%=WebActionConstant.DELETE_ACTION%>" || action == "<%=WebActionConstant.CREATE_ACTION%>") {
            parent.contents.location.reload();
            parent.parent.banner.location = "/servlet/com.sino.sinoflow.framework.resource.servlet.ResourceTreeServlet";
        }
    }

    function do_check() {
   
        if (event.keyCode == 13) {
            do_SearchGroup();
        }
    }
    
    function checkAll(args,args1){
    	//打印表格头是的事件
    }
    
    function executeClick(args){
    	//打印表格头是的事件
    }
</script>