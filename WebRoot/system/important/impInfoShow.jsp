<%@ page import="java.sql.Connection"%>
<%@ page import="com.sino.base.db.conn.DBManager"%>
<%@ page import="com.sino.base.db.sql.model.SQLModel"%>
<%@ page import="com.sino.base.db.query.SimpleQuery"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.important.dto.ImpInfoDTO" %>
<%@ page import="com.sino.ams.adjunct.model.FileMaintenanceModel" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>详细重要信息</title>    
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>
<script type="text/javascript">
    printTitleBar(" 详细重要信息");
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
%>
<body leftmargin="0" topmargin="0" style="overflow-y:scroll;"  onload="window.focus();" >

<%
    ImpInfoDTO dto = (ImpInfoDTO) request.getAttribute("IMP_INFO_DTO");
    //String docType = (String) request.getAttribute("IMP_TYPES");
    if (dto == null) {
        dto = new ImpInfoDTO();
    }

    Connection conn = DBManager.getDBConnection();
    SQLModel attachsModel = FileMaintenanceModel.getAttachesModel(parser.getParameter("publishId"));
    SimpleQuery attachsQuery = new SimpleQuery(attachsModel, conn);
    attachsQuery.executeQuery();
    RowSet attachs = attachsQuery.getSearchResult();
    int attachsNumber = attachs.getSize();
%>
<form action="" name="pmForm" method="post">
    <input type="hidden" name="publishId" value="<%=parser.getParameter("publishId")%>">
    <input type="hidden" name="forward">
    <table align="center" width="90%" border="0" readonly="readonly" style="background-color:#EFEFEF;">
        <tr>
          <td align=center height=73 valign=top style="font-size:10pt;color:#03005C;">
            <span class=title style="font:bolder 16pt;"><%=dto.getTitle()%></span>             
                <hr width=100% height=1 size="1">
                <%=dto.getPublishUserName()%>　
                <%=dto.getPublishDate()%>
           </td>     
        </tr>
        <tr align="left">
            <td >
            <pre width="80%" style="font-size:10pt;line-height: 180%;text-indent:2em;">
            <%=dto.getContents()%>
            </pre>
            </td>
        </tr>

        </table>
        <%
                String description="";
                String orderPkValue="";
                if (attachsNumber>0) {%>
                <div style="height:35px;"></div>
                <div  align="center" style="position:absolute; width:100%;">
                <table width="90%" border="2" align="center" bgcolor="#9999FF">
                <%}
                for(int i=0;i<attachsNumber;i++) {
                    Row bRow=attachs.getRow(i);
                    description=bRow.getValue("DESCRIPTION").toString();
                    orderPkValue=bRow.getValue("ORDER_PK_VALUE").toString();
                    if (i%2==0) { %>
                    <tr >
                    <% }
                    if (i==0) {%>
                        <td rowspan="<%=(attachsNumber+1)/2%>" align="center" width="10%"><p style="color:#FF3399;font-size: 16pt;font-weight: bold">附件</p></td>
                    <%}%>

                    <td><a href="#" class="content" onClick="downLoadAttach('<%=orderPkValue%>');" style="font-size:10pt;">&nbsp;<%=description%>&nbsp;</a></td>
                    <%
                     if ((i+1)%2==0) {%>
                    </tr>
                    <%}
                }
                if (attachsNumber>0) {%>
                    </table><div style="height:50px;"></div></div>
                <%}%>
</form>
<form action="" name="fileForm" method="post"><input type="hidden" name="forward"></form>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<iframe name="fileFrm" style="display:none"></iframe>
</html>
<script type="text/javascript">
    var flag;
    function do_reset() {
        flag = false;
        window.returnValue = flag;
        window.close();
    }
    function do_edit() {
        var publishId = document.pmForm.publishId.value;
        var url = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=edit&publishId=" + publishId + "&seeUserType=" + '<%=dto.getSeeUserType()%>';
        var popscript =getpopscript();
        window.open(url, '', popscript);
    }

    function downLoadAttach(orderPkValue) {
        fileForm.forward.value = "DOWNLOAD_ACTION";
        fileForm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?orderPkValue=" + orderPkValue;
        fileForm.target = "fileFrm";
        fileForm.submit();
    }

    function goFirst() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = 'visible';
        var url = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet?forward=";
        window.opener.location.href=url;

    }
</script>