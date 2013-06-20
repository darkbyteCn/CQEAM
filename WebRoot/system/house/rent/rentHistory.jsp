
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.house.dto.AmsHouseInfoDTO" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-18
  Time: 15:57:18
  Functin:租赁房屋土地维护。
--%>
<html>
<head>
	<title>租赁房屋土地历史信息</title>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    AmsHouseInfoDTO housedto = (AmsHouseInfoDTO) request.getAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO);
%>
<body onkeydown="autoExeFunction('do_search()');" topMargin=0 leftMargin=0>
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.house.servlet.RentHouseSearchServlet?act=QUERY_ACTION" name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("租赁房屋土地历史信息")
</script>
<input type="hidden" name="act" value="<%=action%>">
<script type="text/javascript">
    var columnArr = new Array("条码","名称","型号", "地点","是否基站","类型","房产证号","土地证号","土地性质","总租金","出租单位","联系人","联系电话","起始日期","截至日期");
    var widthArr = new Array("7%", "12%","12%","15%","5%","5%","5%","5%","5%","5%","3%","3%","5%","5%","5%");
    printTableHead(columnArr, widthArr);
</script>
<%--
<div id="headDiv" style="overflow:hidden;position:absolute;top:97px;left:1px;width:840px">
    <table class="headerTable" border="1" width="150%" >
        <tr>
            <td height="22" width="7%" align="center">条码</td>
            <td height="22" width="14%" align="center">名称</td>
            <td height="22" width="15%" align="center">型号</td>
            <td height="22" width="18%" align="center">地点</td>
            <td height="22" width="8%" align="center">是否基站</td>
            <td height="22" width="10%" align="center">类型</td>
            <td height="22" width="6%" align="center">房产证号</td>
            <td height="22" width="6%" align="center">土地证号</td>
            <td height="22" width="6%" align="center">土地性质</td>
            <td height="22" width="5%" align="center">起始日期</td>
            <td height="22" width="5%" align="center">截至日期</td>
        </tr>

    </table>
</div>--%>

<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:355px;width:100%;left:1px;margin-left:0px" align="left">
<%--<div id="dataDiv" style="overflow:scroll;height:66%;width:857px;position:absolute;top:121px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">--%>
    <!--<table width="100%" border="1" bordercolor="#666666" id="dataTab">-->
        <table width="100%" border="1" bordercolor="#666666" >
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);

//                if(!StrUtil.isEmpty())
        %>
        <tr class="dataTR" onclick="show_detail('<%=row.getValue("BARCODE")%>','<%=row.getValue("SYSTEMID")%>','<%=row.getValue("OFFICE_USAGE")%>','<%=row.getValue("OFFICE_TYPE")%>','<%=row.getValue("RENT_ID")%>')">
            <td height="22" width="7%" align="center"><%=row.getValue("BARCODE")%>
            </td>
            <td height="22" width="12%" align="left"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td height="22" width="12%" align="left"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td height="22" width="15%" align="left"><%=row.getValue("HOUSE_ADDRESS")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("OFFICE_USAGE")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("OFFICE_TYPE")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("HOUSE_CERTIFICATE_NO")%>
            </td>
            <td height="22" width="5%" align="left"><%=row.getValue("LAND_CERTFICATE_NO")%>
            </td>
            <td height="22" width="5%" align="center"><%=row.getValue("LAND_TYPE")%>
            </td>
            <td height="22" width="5%" align="center"><%=row.getValue("RENT_FEE")%>
            </td>
            <td height="22" width="3%" align="center"><%=row.getValue("RENT_UNIT")%>
            </td>
            <td height="22" width="3%" align="center"><%=row.getValue("CONTACT_PERSON")%>
            </td>
            <td height="22" width="5%" align="center"><%=row.getValue("CONTACT_PHONE")%>
            </td>
             <td height="22" width="5%" align="center"><%=row.getValue("RENT_START_DATE")%>
            </td>
             <td height="22" width="5%" align="center"><%=row.getValue("RENT_END_DATE")%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</form>
<div  style="position:absolute;top:458px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">

</script>