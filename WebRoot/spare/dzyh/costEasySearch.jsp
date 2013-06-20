<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.spare.dzyh.dto.CostEasyDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-12-20
  Time: 11:57:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    CostEasyDTO dto = (CostEasyDTO) request.getAttribute(WebAttrConstant.DZYH_DTO);
%>
<head><title>低值易耗品查询</title>
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
</head>
<body onkeydown="autoExeFunction('do_SearchPlan()');">
<form action="/servlet/com.sino.ams.spare.dzyh.servlet.CostEasyServlet" name="workForm" method="post">
    <%=WebConstant.WAIT_TIP_MSG%>
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
    <script type="text/javascript">
        printTitleBar("低值易耗品查询")
    </script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td width="8%" align="right">低值易耗品标签号：</td>
            <td width="15%" align="left"><input type="text" name="barcode" style="width:80%"
                                                value="<%=dto.getBarcode()%>">
            </td>
            <td width="8%" align="right">低值易耗品名称：</td>
            <td width="15%" align="left"><input type="text" name="itemName" style="width:80%"
                                                value="<%=dto.getItemName()%>">
            </td>
            <td width=10% align="left">
                <img src="/images/button/new.gif" alt="新增低值易耗品"
                     onClick="do_CreatePlan('<%=dto.getItemCategory()%>'); return false;"><img src="/images/button/toExcel.gif" alt="导出数据" onclick="do_export()">
            </td>
        </tr>
        <tr>
            <td align="right" width="8%">厂商：</td>
            <td width="10%" align="left"><input type="text" name="manufacturerName" style="width:80%" readonly    class="readonlyinput"
                                                value="<%=reqParser.getParameter("manufacturerName")%>"><a class="linka" style="cursor:'hand'"
                                                                                                     onclick="do_selectNameManufacturer();">[…]</a>
                <input type="hidden" name="manufacturerId" value="">
            </td>
            <td align="right">规格型号：</td>
            <td align="left"><input type="text" name="itemSpec" style="width:80%" value="<%=dto.getItemSpec()%>">
            </td>
            <td align="left">
                <img src="/images/button/query.gif" alt="查询低值易耗品" onClick="do_SearchPlan(); return false;">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="planId" value="">
    <input type="hidden" name="vendorId">
    <input type="hidden" name="loginName">
    <input type="hidden" name="itemCategory" value="<%=dto.getItemCategory()%>">
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="10%" align="center">低值易耗品标签号</td>
                <td width="15%" align="center">低值易耗品名称</td>
                <td width="15%" align="center">规格型号</td>
                <td width="10%" align="center">设备类型</td>
                <td width="5%" align="center">数量</td>
                <td width="25%" align="center">地点名称</td>
            </tr>
        </table>
    </div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <%--<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("SYSTEMID")%>','<%=dto.getItemCategory()%>')">--%>
            <tr >
                <td height="22" width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td height="22" width="10%"><%=row.getValue("ITEM_CATE_GORY_DESC")%>
                </td>
                <td height="22" width="5%"><%=row.getValue("ITEM_QTY")%>
                </td>
                <td height="22" width="25%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
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
<div style="position:absolute;top:92%;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<iframe name="downFrm" src="" style="display:none"></iframe>
<script type="text/javascript">

    function do_SearchPlan() {
        with (workForm) {
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            submit();
        }
    }

    function do_CreatePlan(itemCategory) {
        workForm.itemCategory.value = itemCategory;
        var url = "/servlet/com.sino.ams.spare.dzyh.servlet.CostEasyServlet?act=<%=WebActionConstant.NEW_ACTION%>&itemCategory=" + itemCategory;
        var popscript = 'width=600,height=600,top=100,left=250,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no';
        window.open(url, 'planWin', popscript);
    }

    function do_ShowDetail(systemid, itemCategory) {
        if (event.srcElement.name == 'systemid') {
            return false;
        }
        workForm.itemCategory.value = itemCategory;
        var url = "/servlet/com.sino.ams.spare.dzyh.servlet.CostEasyServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&systemid=" + systemid + "&itemCategory=" + itemCategory;
        var popscript = 'width=600,height=400,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, '', popscript);
//        workForm.submit(); 
    }

    function do_selectName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "workForm");
            }
        } else {
            workForm.vendorId.value = "";
            workForm.vendorName.value = "";
        }
    }

    function do_Enable() {
        var checkCount = getCheckedBoxCount("systemid");
        if (checkCount < 1) {
            alert("请选择一条记录，然后生效！");
        }else {
            if (confirm(ENABLE_MSG)) {
                workForm.act.value = "<%=AMSActionConstant.INURE_ACTION%>";
                workForm.submit();
            }
        }
    }

    function do_Disable() {
        var checkCount = getCheckedBoxCount("systemid");
        if (checkCount < 1) {
            alert("请选择一条记录，然后失效！");
        }
        else {
            if (confirm(DISABLE_MSG)) {
                workForm.act.value = "<%=AMSActionConstant.DISABLED_ACTION%>";
                workForm.submit();
            }
        }
    }

    function do_export() {
        workForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        workForm.submit();
    }

    function do_selectNameManufacturer() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_MANUFACTURER%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "workForm");
            }
        } else {
            workForm.manufacturerId.value = "";
            workForm.manufacturerName.value = "";
        }
}
</script>