<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.ams.net.reportforms.dto.SitusStatisticsDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  User: wangzp    
  Date: 2011-12-08
  Functon; 备件申请于接收差异报表；
--%>
<html>
<head>
    <title>备件申请与接收报表</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsItemTransLDTO situsdto = (AmsItemTransLDTO) request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    String objectCategory = (String) request.getAttribute(WebAttrConstant.INV_OPTION);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.spare.servlet.SpareDiffServlet">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("备件申请与接收差异报表")
</script>
<!--<table width="100%" border="1" bgcolor="#EFEFEF">-->
 <table width="100%" border="0" bgcolor='#EFEFEF' cellpadding="2" cellspacing="0" >
    <tr>
        <td align="right" width="8%">公司：</td>
        <td width="17%"><select class="select_style1" style="width:80%"  name="organizationId"><%=organizationId%></select></td>
        <td align="right" width="8%">设备名称：</td>
        <td width="17%"><input class="input_style1" style="width:80%" type="text" name="itemName" value="<%=situsdto.getItemName()%>"></td>
        <td align="right" width="8%">设备型号：</td>
        <td width="17%"><input class="input_style1" style="width:80%" type="text" name="itemSpec" value="<%=situsdto.getItemSpec()%>"></td>
        <td align="right" width="1%"></td>
        <td width="8%"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_Export()"></td>
    </tr>
    <tr>
        <td align="right" width="8%">设备类型：</td>
        <td width="17%"><input class="input_style1" style="width:80%" type="text" name="itemCategory" value="<%=situsdto.getItemCategory()%>"></td>
        <td align="right" width="8%">用途：</td>
        <td width="17%"><input class="input_style1" style="width:80%" type="text" name="spareUsage" value="<%=situsdto.getSpareUsage()%>"></td>
        <td align="right" width="8%">厂商：</td>
        <%--<td width="17%"><input style="width:80%" type="text" name="vendorName" value="<%=situsdto.getVendorName()%>">--%>
        <!--</td>-->
        <td width="17%"><select name="vendorId" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select>
        </td>
        <td align="right" width="1%"></td>
        <td width="8%"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"  alt="点击查询" onClick="do_search(); return false;"></td>
    </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("公司","设备名称","设备型号","设备类型","用途", "厂商","申请数量","实发数量","差异");
            var widthArr = new Array("10%","10%","10%","10%","10%","10%","10%","10%","10%");
            printTableHead(columnArr,widthArr);
        </script>
<input type="hidden" name="act">
<div style="overflow-y:scroll;left:0px;width:100%;height:325px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && rows.getSize() > 0) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="10%" align="center" ><%=row.getValue("ORGNIZATION_NAME")%></td>
            <td width="10%" align="center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="10%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="10%" align="center"><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width="10%" align="center"><%=row.getValue("SPARE_USAGE")%></td>
            <td width="10%" align="center" ><%=row.getValue("VENDOR_NAME")%></td>
            <td width="10%" align="center" ><%=row.getValue("APPLY_NUMBER")%></td>
            <td width="10%" align="center" ><%=row.getValue("REC_NUMBER")%></td>
            <td width="10%" align="center" class="hyperLinkStyle"><%=row.getValue("DIF_NUM")%></td>
        </tr>
<%
	    }     }
%>
    </table>
</div>
</form>
<div style="right:20px;top:400px;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>

</body>

</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareDiffServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareDiffServlet";
    mainFrm.submit();
}
</script>