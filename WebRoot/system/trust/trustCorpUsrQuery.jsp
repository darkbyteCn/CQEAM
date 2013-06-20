<%@ page contentType = "text/html; charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import = "com.sino.base.util.StrUtil" %>
<head>
    <meta http-equiv = "Content-Type" content = "text/html; charset=gb2312" />
    <title>代维公司人员查询</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/eam.css">  
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
</head>

<body leftmargin = "1" topmargin = "0" onkeydown = "autoExeFunction('do_SearchResource()');">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page = "/message/MessageProcess" />
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
%>
<form name = "mainFrm" method = "POST" action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainPeopleServlet">
    <script type = "text/javascript">
        printTitleBar("代维公司人员查询")
    </script>
    <table border = "0" width = "100%" id = "table1" >
        <tr>
            <td width = "15%" align = "right">维护人员姓名：</td>
            <td width = "15%"><input type = "text" name = "userName" value = "<%=reqParser.getParameter("contactPeople")%>" class="input_style1" style = "width:100%"></td>
            <td width = "10%" align = "right">所属公司：</td>
            <td width = "30%"><select name = "companyId" class="select_style1" ><%=request.getAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION)%></select></td>
            <td width = "8%" align = "right"><img src = "/images/eam_images/search.jpg" alt = "查询人员" onClick = "do_SearchCompany(); return false;"></td>
            <td width = "8%" align = "center"><img src = "/images/eam_images/new.jpg" alt = "点击新增" onClick = "do_CreateCompany(); return false;"></td>
        </tr>
    </table>
    <input type = "hidden" name = "act" value = "<%=reqParser.getParameter("act")%>">
    <input type = "hidden" name = "userId" value = "">
    <script type = "text/javascript">
        var columnArr = new Array("代维人员姓名", "代维人员固话", "代维人员移动电话", "代维人员电子邮箱", "所属代维公司");
        var widthArr = new Array("10%", "15%", "15%", "20%", "20%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style = "overflow-y:scroll; width:100%;left:1px;margin-left:0px" align = "left">
        <table width = "100%" border = "1" >

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class = "dataTR" onclick = "do_ShowDetail('<%=row.getValue("USER_ID")%>'); return false;">
                <td width = "10%" align = "center"><%=row.getValue("USER_NAME")%></td>
                <td width = "15%" align = "center"><%=row.getValue("USER_TELEPHONE")%></td>
                <td width = "15%" align = "center"><%=row.getValue("USER_MOBILE_PHONE")%></td>
                <td width = "20%" align = "center"><%=row.getValue("EMAIL")%></td>
                <td width = "20%" align = "center"><%=row.getValue("COMPANY_NAME")%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

    <%
        }
    %></form>
<div style = "position:absolute;top:408px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>

<script language = "javascript">

    function do_SearchCompany() {        //查询条件查询
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_CreateCompany() {        //新增公司
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(userId) {       //查询详细信息
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.userId.value = userId;
        mainFrm.submit();
    }
</script>