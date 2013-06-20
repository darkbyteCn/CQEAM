<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.part.dto.AmsSpareCategoryDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>备件设备分类维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>

<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsSpareCategoryDTO partNoDTO = (AmsSpareCategoryDTO) request.getAttribute(WebAttrConstant.SPARE_CATEGORY_DTO);
%>
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet">
    <script type="text/javascript">
        printTitleBar("备件设备分类维护")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="8%" align="right">设备名称：</td>
            <td width="15%" align="left"><input class="input_style1" style="width:80%" name="itemName" value="<%=partNoDTO.getItemName()%>"></td>
            <td width="8%" align="right">设备型号：</td>
            <td width="15%" align="left"><input class="input_style1" style="width:80%" type="text" name="itemSpec" value="<%=partNoDTO.getItemSpec()%>"></td>
            <td width="8%" align="right">设备类型：</td>
            <td width="15%" align="left"><input class="input_style1" style="width:80%" name="itemCategory" value="<%=partNoDTO.getItemCategory()%>"></td>
            <td width="10%" align="right"><img src="/images/eam_images/new_add.jpg" style="cursor:'hand'" onclick="do_add();" alt="新增"></td>
        </tr>
        <tr>
            <td width="8%" align="right">厂商：</td>
            <td width="15%" align="left"><select name="vendorId" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>
            <td align="right" width="8%">ID号：</td>
            <td width="15%"><input class="input_style1" style="width:80%" type="text" name="barcode" value="<%=partNoDTO.getBarcode()%>"></td>
            <td width="8%" align="right">用途：</td>
            <td width="15%" align="left"><input class="input_style1" style="width:80%" type="text" name="spareUsage" value="<%=partNoDTO.getSpareUsage()%>"></td>
            <td width="10%" align="right"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_Export()"></td>
        </tr>
        <tr>
            <td width="8%" align="right">创建日期：</td>
            <td width="15%" align="left"><input type="text" name="startDate" value="<%=partNoDTO.getStartDate()%>" class="input_style1" style="width:80%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate);"></td>
            <td width="8%" align="right">到：</td>
            <td width="15%" align="left"><input type="text" name="endDate" value="<%=partNoDTO.getEndDate()%>" class="input_style1" style="width:80%" title="点击选择截至日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop(startDate, endDate);"></td>
            <td width="8%" align="right">创建人：</td>
            <td width="15%" align="left"><input class="input_style1" style="width:80%" type="text" name="createdBy" value="<%=partNoDTO.getCreatedByName()%>"></td>
            <td width="10%" align="right"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="3%" align="center">ID号</td>
                <td width="10%" align="center">设备名称</td>
                <td width="13%" align="center">设备型号</td>
                <td width="8%" align="center">设备类型</td>
                <td width="8%" align="center">用途</td>
                <td width="10%" align="center">厂商</td>
                <td width="5%" align="center">单位</td>
                <td width="5%" align="center">创建人</td>
                <td width="6%" align="center">创建日期</td>
                <td width="6%" align="center">是否有效</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">

    <div style="overflow-y:scroll;left:0px;width:100%;height:300px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("BARCODE")%></td>
                <td width="10%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_NAME")%></td>
                <td width="13%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="8%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_CATEGORY")%></td>
                <td width="8%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("SPARE_USAGE")%></td>
                <td width="10%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("VENDOR_NAME")%></td>
                <td width="5%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ITEM_UNIT")%></td>
                <td width="5%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("USERNAME")%></td>
                <td width="6%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("CREATION_DATE")%></td>
                  <td width="6%" align="left"
                    onclick="show_detail('<%=row.getValue("BARCODE")%>')"><%=row.getValue("ENABLED")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>

<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
        mainFrm.submit();
    }

    function show_detail(barcode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet?barcode=" + barcode;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
        mainFrm.submit();
    }

    function do_disabled() {             //批量失效
        var checkedCount = getCheckedBoxCount("workorderObjectNos");
        if (checkedCount < 1) {
            alert("请至少选择一行数据！");
            return;
        } else {
            do_verifyObjectNos();

        }
    }

    function do_efficient() {             //批量生效
        var checkedCount = getCheckedBoxCount("workorderObjectNos");
        if (checkedCount < 1) {
            alert("请至少选择一行数据！");
            return;
        } else {
            if (confirm(ENABLE_MSG)) {
                mainFrm.act.value = "<%=AMSActionConstant.EFFICIENT_ACTION%>";
                mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
                mainFrm.submit();
            }
        }
    }


    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.spare.part.servlet.AmsSpareCategoryServlet";
        mainFrm.submit();
    }
    function do_import() {
        var url = "/onnet/importOnNet.jsp";
        var popscript = 'width=870,height=700,top=1,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, 'onnet', popscript);
    }
</script>