<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>

<%--
  created by Zyun
  Date: 2007-09-26
  Time: 8:23:30
  update by 王志鹏
  Date: 2011-03-31
--%>

<html>
<head>
    <title>重要信息发布</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_Search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String publishUserId =  StrUtil.nullToString(request.getParameter("publishUserId"));
    String title =  StrUtil.nullToString(request.getParameter("title"));
%>
<form  action="/servlet/com.sino.ams.important.servlet.ImpInfoServlet" name="impForm" method="post">
  <script type="text/javascript">
    printTitleBar("重要信息发布");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");
    var ArrAction1 = new Array(true, "新增", "action_edit.gif", "新增", "do_add()");
    var ArrActions = new Array(ArrAction0, ArrAction1);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();
  </script>
<div id="searchDiv">
    <table width="100%" border="0" >
        <tr>
            <td width="8%" align="right">发布人：</td>
            <td width="17%"><input type="text" name="publishUserId" class="input_style1" style="width:80%"  value="<%=publishUserId %>"></td>
            <td width="8%" align="right">发布标题：</td>
            <td width="17%"><input type="text" name="title" class="input_style1" style="width:80%" value="<%=title%>"></td>
            <td width="8%"  align="right">创建日期：</td>
            <td width="17%" ><input type="text" name="publishStartDate" value="<%=parser.getParameter("publishStartDate")%>" style="width:100%" title="点击选择开始日期" readonly class="input_style1" onclick="gfPop.fStartPop(publishStartDate, publishEndDate)"></td>
            <td width="8%" align="right">到：</td>
            <td width="17%"><input type="text" name="publishEndDate" value="<%=parser.getParameter("publishEndDate")%>" style="width:100%" title="点击选择截至日期" readonly class="input_style1" onclick="gfPop.fEndPop(publishStartDate, publishEndDate)"></td>
        </tr>
    </table>
</div>
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="100%">
        <tr height="23px">
            <td width="5%" align="center">序号</td>
            <td width="30%" align="center">发布标题</td>
            <td width="25%" align="center">发布人</td>
            <td width="10%" align="center">发布时间</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">    
<input type="hidden" name="forward">
<div id="dataDiv" style="overflow:scroll;height:68%;width:100%;position:absolute;top:45px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%        
    if (rows != null && !rows.isEmpty()) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
 %>                    
        <tr class="dataTR" style="cursor:pointer" onclick="show_user_detail('<%=row.getValue("PUBLISH_ID")%>')">
            <td width="5%" height="22" align="right"><%=i+1 %></td>
            <td width="30%" height="22" align="left"><%=row.getValue("TITLE") %></td>
            <td width="25%" height="12" align="left"><%=row.getValue("PUBLISH_USER_NAME") %></td>
            <td width="10%" height="22" align="center"><%=row.getValue("PUBLISH_DATE") %></td>
        </tr>
<%
        }
    }
%>
    </table>
</div>
    <input type="hidden" name="act" id="act">
    </form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">

    function show_user_detail(publishId) {
       var url = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=edit&publishId=" + publishId ;
        do_OpenURL(url);
    }

    function do_Search() {
        document.impForm.forward.value = "show_info";
        document.impForm.submit();
    }

    function do_add() {
        var url = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=CREATE_ACTION";
        do_OpenURL(url);
    }

    function do_OpenURL(url){
        var factor = 0.6;
        var width = window.screen.availWidth * factor;
        var height = window.screen.availHeight * factor;
        var left = window.screen.availWidth * (1 - factor) / 2;
        var top = window.screen.availHeight * (1 - factor) / 2;
        var winName = "publishWin";
        var style = "width="
            + width
            + ",height="
            + height
            + ",left="
            + left
            + ",top="
            + top;
        window.open(url, winName, style);
    }
</script>