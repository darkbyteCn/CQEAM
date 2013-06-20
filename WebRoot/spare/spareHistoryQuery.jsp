<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.SpareHistoryDTO" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件业务单据历史信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')" onload="do_drop()">
<%
    SpareHistoryDTO dto = (SpareHistoryDTO) request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
%>
<form action="/servlet/com.sino.ams.spare.servlet.SpareHistoryServlet" name="mainForm" method="post">
<script type="text/javascript">
    printTitleBar("备件业务单据历史信息")
</script>
<table border="0" width="100%" cellspacing="0" cellpadding="0" style="background-color:#efefef">
    <tr>
        <td align="right" width="8%">公司：</td>
        <td width="15%"><select class="select_style1" style="width:80%" name="organizationId"><%=organizationId%>
        </select></td>
        <td align="right" width="8%">单据号：</td>
        <td align="left" width="15%"><input type="text" name="transNo" class="input_style1" style="width:80%" value="<%=dto.getTransNo()%>"></td>
        <td width=8% align="right"> 单据类型：</td>
        <td width="15%"><select name="transType" id="transType" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_TYPE)%>
        </select></td>
        <td align="right" width="8%">设备名称：</td>
        <td align="left" width="15%"><input type="text" name="itemName" class="input_style1" style="width:80%" value="<%=dto.getItemName()%>"></td>
    </tr>
    <tr>
        <td align="right" width="8%">规格型号：</td>
        <td align="left" width="15%"><input type="text" name="itemSpec" class="input_style1" style="width:80%" value="<%=dto.getItemSpec()%>"></td>
        <td align="right" width="8%">设备类型：</td>
        <td align="left" width="15%"><input type="text" name="itemCategory" class="input_style1" style="width:80%" value="<%=dto.getItemCategory()%>"></td>
        <td align="right" width="8%">用途：</td>
        <td align="left" width="15%"><input type="text" name="spareUsage" class="input_style1" style="width:80%" value="<%=dto.getSpareUsage()%>"></td>
        <td align="right" width="8%">厂商：</td>
        <td width="15%" align="left"><select name="vendorId" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%>
        </select></td>
    </tr>
    <tr>
        <td align="right" width="8%">创建日期：</td>
        <td>
            <input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:80%" title="点击选择开始日期" readonly class="input_style1"
                   onclick="gfPop.fStartPop(startDate, endDate)">
            <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate)">
        </td>
        <td align="right" width="8%">到：</td>
        <td>
            <input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:80%" title="点击选择截止日期" readonly class="input_style1"
                   onclick="gfPop.fEndPop(startDate, endDate)">
            <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(startDate, endDate)">
        </td>
        <td align="right" width="8%">创建人：</td>
        <td align="left" width="15%"><input type="text" name="createdUser" class="input_style1" style="width:80%" value="<%=dto.getCreatedUser()%>"></td>

    </tr>

    <tr>
        <td align="right" width="8%">来源公司：</td>
        <td align="left" width="15%"><select name="fromCompany" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.CITY_OPTION)%>
        </select></td>
        <td align="right" width="8%">目的公司：</td>
        <td width="15%" align="left"><select name="toCompany" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.OU_OPTION2)%>
        </select></td>
        <td align="right" colspan=4><img src="/images/eam_images/search.jpg" alt="查询" onClick="do_search(); return false;">
        <img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()"></td>
    </tr>
</table>
<input type="hidden" name="act" value="">

<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:112px;left:0px;width:100%" class="crystalScroll">
    <table border=1 width="250%" class="headerTable">
        <tr class=headerTable height="20px">
            <td align=center width=6%>单据号</td>
            <td align=center width=3%>单据类型</td>
            <td align=center width=2%>ID号</td>
            <td align=center width=5%>设备名称</td>
            <td align=center width=5%>规格型号</td>
            <td align=center width=5%>设备类型</td>
            <td align=center width=5%>用途</td>
            <td align=center width=5%>厂商</td>
            <td align=center width=2%>发生数量</td>
            <td align=center width=4%>创建日期</td>
            <td align=center width=6%>来源仓库</td>
            <td align=center width=6%>目的仓库</td>
            <td align=center width=4%>来源公司</td>
            <td align=center width=4%>目的公司</td>
            <td align=center width=3%>创建人</td>
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:scroll;height:300px;width:100%;position:absolute;top:132px;left:0px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (rows != null && rows.getSize() > 0) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="6%" align="center"><%=row.getValue("TRANS_NO")%>
            </td>
            <td width="3%" align="center"><%=row.getValue("TRANS_TYPE_NAME")%>
            </td>
            <td width="2%" align="center"><%=row.getValue("BARCODE")%>
            </td>
            <td width="5%" align="center"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="5%" align="center"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="5%" align="center"><%=row.getValue("ITEM_CATEGORY")%>
            </td>
            <td width="5%" align="center"><%=row.getValue("SPARE_USAGE")%>
            </td>
            <td width="5%" align="center"><%=row.getValue("VENDOR_NAME")%>
            </td>
            <td width="2%" align="center"><%=row.getValue("QUANTITY")%>
            </td>
            <td width="4%" align="center"><%=row.getValue("CREATION_DATE")%>
            </td>
            <td width="6%" align="center"><%=row.getValue("FROM_OBJECT_NAME")%>
            </td>
            <td width="6%" align="center"><%=row.getValue("TO_OBJECT_NAME")%>
            </td>
            <td width="4%" align="center"><%=row.getValue("FROM_COMPANY")%>
            </td>
            <td width="4%" align="center"><%=row.getValue("TO_COMPANY")%>
            </td>
            <td width="3%" align="center"><%=row.getValue("CREATED_USER")%>
            </td>

        </tr>
        <%
                }
            }
        %>
    </table>
</div>

</form>
<%
    if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:435px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_drop() {
        var transType = document.getElementById("transType");
        dropSpecialOption(transType, 'BJBFS;BJFP');
    }
</script>