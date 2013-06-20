<%@ page contentType = "text/html; charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<head>
    <meta http-equiv = "Content-Type" content = "text/html; charset=gb2312" />
    <title>代维公司维护</title>
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
<form name = "mainFrm" method = "POST" action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainCompanyServlet">
    <script type = "text/javascript">
        printTitleBar("代维公司维护")
    </script>
    <table border = "0" width = "100%" id = "table1" >
        <tr>
            <td width = "7%" align = "right">公司名称：</td>
            <td width = "10%"><input type = "text" name = "name" value = "<%=reqParser.getParameter("name")%>"
                                    class="input_style1"  style = "width:100%"></td>
            <td width = "6%" align = "right">联系人：</td>
            <td width = "10%"><input type = "text" name = "contactPeople" value = "<%=reqParser.getParameter("contactPeople")%>"
                                    class="input_style1" style = "width:100%"></td>
            <td width = "7%" align = "right">所属区县：</td>
            <td width = "10%"><select name = "countyCode" class="select_style1"><%=request.getAttribute(WebAttrConstant.COUNTY_OPTION)%></select>
            </td>
            <td width = "20%" align = "right">
              <img src = "/images/eam_images/search.jpg"  alt= "查询公司"  onClick = "do_SearchCompany(); return false;">&nbsp;
              <img src = "/images/eam_images/new.jpg"     alt= "点击新增"  onClick = "do_CreateCompany(); return false;">&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
    </table>
    <input type = "hidden" name = "act" value = "<%=reqParser.getParameter("act")%>">
    <input type = "hidden" name = "companyId" value = "">
    <script type = "text/javascript">
        var columnArr = new Array("公司名称", "联系人", "联系人电话", "办公电话", "所属公司", "所在区县");
        var widthArr = new Array("20%", "15%", "15%", "15%", "15%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style = "overflow-y:scroll; width:100%;height:302px; margin-left:0px">
        <table width = "100%" border = "1">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class = "dataTR" onclick = "do_ShowDetail('<%=row.getValue("COMPANY_ID")%>'); return false;">
                <td width = "20%" align = "center"><%=row.getValue("NAME")%></td>
                <td width = "15%" align = "center"><%=row.getValue("CONTACT_PEOPLE")%></td>
                <td width = "15%" align = "center"><%=row.getValue("CONTACT_TELEPHONE")%></td>
                <td width = "15%" align = "center"><%=row.getValue("OFFICE_TELEPHONE")%></td>
                <td width = "15%" align = "center"><%=row.getValue("COMPANY")%></td>
                <td width = "10%" align = "center"><%=row.getValue("COUNTY_NAME")%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
    %>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
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

    function do_ShowDetail(companyId) {       //查询详细信息
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.companyId.value = companyId;
        mainFrm.submit();
    }
</script>