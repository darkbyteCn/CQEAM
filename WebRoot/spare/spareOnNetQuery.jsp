<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: wangzp
  Date: 2011-12-08
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>备件库存量查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body onkeydown="autoExeFunction('do_search()')" onload="do_init();">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsItemTransLDTO situsdto = (AmsItemTransLDTO) request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    String objectCategory = (String) request.getAttribute(WebAttrConstant.INV_OPTION);
    String count = StrUtil.nullToString(request.getAttribute("TATOL_COUNT1"));
    String count1 = StrUtil.nullToString(request.getAttribute("TATOL_COUNT2"));
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.spare.servlet.SpareOnHandServlet">

<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("备件库存量查询")
</script>
<!--<table width="100%" border="1" bgcolor="#EFEFEF">-->
 <table width="100%" border=0 bgcolor='#EFEFEF' cellpadding="2" cellspacing="0" >
    <tr>
        <td align="right" width="8%">公司：</td>
        <td width="15%"><select class="select_style1" style="width:100%"  name="organizationId"><%=organizationId%></select></td>
        <td align="right" width="8%">仓库类型：</td>
        <td width="15%"><select class="select_style1" style="width:100%"  name="objectCategory"><%=objectCategory%></select></td>
        <td align="right" width="8%">仓库：</td>
        <td width="15%"><input class="input_style1" style="width:80%" type="text" name="workorderObjectName" value="<%=situsdto.getWorkorderObjectName()%>"><a href="#" class="linka" style="cursor:'hand'" onclick="do_selectToName();">[…]</a></td>
        <td width="8%" align="right">厂商：</td>
        <td width="15%" align="left"><select name="vendorId" class="select_style1" style="width:100%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>
        <td width="8%"><a style="cursor:'hand'"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_Export()"></a></td>
    </tr>
     <tr>
         <td align="right" width="8%">设备名称：</td>
         <td width="15%"><input class="input_style1" style="width:100%" type="text" name="itemName" value="<%=situsdto.getItemName()%>"></td>
         <td align="right" width="8%">设备型号：</td>
         <td width="15%"><input class="input_style1" style="width:100%" type="text" name="itemSpec" value="<%=situsdto.getItemSpec()%>"></td>
         <td align="right" width="8%">设备类型：</td>
         <td width="15%"><input class="input_style1" style="width:80%" type="text" name="itemCategory" value="<%=situsdto.getItemCategory()%>"></td>
         <td align="right" width="8%">用途：</td>
        <td width="15%"><input class="input_style1" style="width:100%" type="text" name="spareUsage" value="<%=situsdto.getSpareUsage()%>"></td>
        <td width="8%"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"  alt="点击查询" onClick="do_search(); return false;"></a></td>
    </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("公司","仓库类型","ID号","厂商","设备类型","用途","设备名称","设备型号", "仓库", "库存量","可用量");
            var widthArr = new Array("10%","10%","5%","10%","10%","10%","10%","10%","15%","5%","5%");
            printTableHead(columnArr,widthArr);
        </script>
<input type="hidden" name="act">
<input type="hidden" name="barcode">
<div style="overflow-y:scroll;left:0px;width:100%;height:300px">
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
            <td width="10%" align="center" ><%=row.getValue("INV_TYPE")%></td>
            <td width="5%" align="center" ><%=row.getValue("BARCODE")%></td>
            <td width="10%" align="center"><%=row.getValue("VENDOR_NAME")%></td>
            <td width="10%" align="center"><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width="10%" align="center"><%=row.getValue("SPARE_USAGE")%></td>
            <td width="10%" align="center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="10%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="15%" align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            <td width="5%" align="center" class="hyperLinkStyle"><%=row.getValue("QUANTITY")%></td>
            <td width="5%" align="center" class="hyperLinkStyle" ><%=row.getValue("USERABLE_QTY")%></td>
        </tr>
<%
	    }     }
%>
    </table>
</div>
</form>
<div>【库存量共：<%=count%>】【可用量共：<%=count1%>】</div>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareOnHandServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareOnHandServlet";
    mainFrm.submit();
}

function do_init(){
        var objectCategory = document.getElementById("objectCategory");
//        dropSpecialOption(objectCategory, '77');
        dropSpecialOption(objectCategory, 'WebApplication/spare/spareOnNetQuery.jsp ');
}

function do_selectToName() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_CK%>&organizationId="+document.mainFrm.organizationId.value;
    var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var users = window.showModalDialog(url, null, popscript);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            mainFrm.workorderObjectName.value = users[0].workorderObjectName;
        }
    }
}
</script>