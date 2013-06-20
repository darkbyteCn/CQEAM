<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/newasset/headerInclude.htm" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>应急保障队伍维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>

</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String teamId = parser.getParameter("teamId");
    String teamName = parser.getParameter("teamName");
    String orgOption = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yj.servlet.AmsYjTeamServlet">
    <script type="text/javascript">
        printTitleBar("战备应急保障队伍维护")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <input type="hidden" name="act">
        <tr>
            <td width="8%" align="right">公司名称：</td>
            <td width="15%"><select class="select_style1" style="width:100%" name="organizationId"><%=orgOption%>
            </select></td>
            <td width="6%" align="right">队伍号：</td>
            <td width="10%"><input class="input_style1" style="width:100%" type="text" name="teamId" value="<%=teamId%>"></td>
            <td width="8%" align="right">队伍名称：</td>
            <td width="15%"><input class="input_style1" style="width:100%" type="text" name="teamName" value="<%=teamName%>"></td>
            <td width="20%" align="center" nowrap>
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
                <img src="/images/eam_images/new.jpg" alt="新增" onClick="do_add();">
                <img src="/images/eam_images/export.jpg" style="cursor:'hand'"  onclick="do_Export();" title="导出到Excel">
                <img src="/images/eam_images/export.jpg" style="cursor:'hand'"  onclick="do_export_team();" title="导出队伍统计">
            </td>
        </tr>
    </table>

      <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="10%" align="center">公司名称</td>
                <td width="4%" align="center">队伍号</td>
                <td width="10%" align="center">队伍名称</td>
                <td width="6%" align="center">企业负责人</td>
                <td width="6%" align="center">手机</td>
                <td width="6%" align="center">队伍人数</td>
                <td width="10%" align="center">队伍基本情况及特点</td>
                <!--<td width="6%" align="center">创建人</td>-->
                <!--<td width="10%" align="center">创建日期</td>-->
                <!--<td width="6%" align="center">更新人</td>-->
                <!--<td width="10%" align="center">更新日期</td>-->
                <td width="10%" align="center">失效日期</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;left:0px;width:100%;height:350px">
        <table width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" onclick="show_detail('<%=row.getValue("TEAM_ID")%>')">
                <td width="10%" align="left"><%=row.getValue("ORGANIZATION_NAME")%></td>
                <td width="4%" align="center"><%=row.getValue("TEAM_ID")%></td>
                <td width="10%" align="left"><%=row.getValue("TEAM_NAME")%></td>
                <td width="6%" align="left"><%=row.getValue("RESPONSIBILITY_USER")%></td>
                <td width="6%" align="left"><%=row.getValue("TEL")%></td>
                <td width="6%" align="left"><%=row.getValue("QUANTITY")%></td>
                <td width="10%" align="left"><%=row.getValue("SITUATION")%></td>
                <%--<td width="6%" align="left"><%=row.getValue("CREATE_USER")%>--%>
                <!--</td>-->
                <%--<td width="10%" align="left"><%=row.getValue("CREATION_DATE")%>--%>
                <!--</td>-->
                <%--<td width="6%" align="left"><%=row.getValue("LAST_UPDATE_USER")%>--%>
                <!--</td>-->
                <%--<td width="10%" align="left"><%=row.getValue("LAST_UPDATE_DATE")%>--%>
                <td width="10%" align="left"><%=row.getValue("DISABLE_DATE")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

</body>
</html>

<script type="text/javascript">

    function do_search() {
        var teamId= document.getElementsByName("teamId")[0];
        if(teamId.value!=""){
              if(isNaN(teamId.value))
             {
              alert('"队伍号" 请输入数字！');
              teamId.value="";
              teamId.focus();
              return false;   
             }
          }
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(teamId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjTeamServlet?teamId=" + teamId;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    function do_export_team() {                  //导出execl
        document.mainFrm.act.value = "TEAM";
        document.mainFrm.submit();
    }

</script>