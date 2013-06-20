<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  created by YS
  Date: 2007-09-28
  Time: 8:23:36
--%>

<html>

<head>
    <title>未巡检地点查询</title>
</head>

<body onkeydown="autoExeFunction('do_search()')">
 <%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
    String objectCategory = parser.getParameter("objectCategory");

%>
<form method = "post" name = "mainFrm">
    <script type = "text/javascript">
        printTitleBar("未巡检--地点信息")
    </script>
    <table width = "100%" border = "0">
        <tr>
 		<td align="right" width="10%">地点专业：</td>
        <td width="10%"><select style="width:100%" class="select_style1"
                    name="category"><%=request.getAttribute("CATEGORY")%>
        </select>
        </td>     
            <td width = "10%" align = "right"><img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询"></td>
            <td width = "10%" align = "center" ><img src = "/images/eam_images/export.jpg" id = "queryImg" style = "cursor:'hand'" onclick = "do_exportToExcel()" alt = "导出Excel"></td>
        </tr>
    </table>
    <input type = "hidden" name = "act" value = "<%=parser.getParameter("act")%>">
    <input type = "hidden" name = "objectCategory" value = "<%=objectCategory%>">

    <script type="text/javascript">
            var columnArr = new Array("地点编号","地点简称","所在地点","所属区县","巡检模式");
            var widthArr = new Array("10%","25%","30%","15%","20%");
            printTableHead(columnArr,widthArr);
        </script>

    <div style = "overflow-y:scroll;left:1px;width:100%;height:350">
        <table width = "100%" border = "1" bordercolor = "#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class = "dataTR">
                <td style = "word-wrap:break-word" height = "22" width = "10%" align = "center"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "25%" align = "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "30%" align = "center"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "15%" align = "center"><%=row.getValue("COUNTY_NAME")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "20%" align = "center"><%=row.getValue("ISALL")%></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

</form>


<%
    }
%>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page = "<%=URLDefineList.MESSAGE_PROCESS%>" flush = "true" />
</body>
</html>
<script type = "text/javascript">
    function do_search() {
       document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible" ;
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.LOCUS_SERVLET%>";
        mainFrm.submit();
    }
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.LOCUS_SERVLET%>";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
    }
         function doChecked() {
           var transType = document.getElementById("objectCategory")   ;
        dropSpecialOption(transType, '80');
    }
</script>