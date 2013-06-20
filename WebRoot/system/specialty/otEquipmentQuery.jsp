<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.system.specialty.dto.OtherDTO" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
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
    OtherDTO dto = (OtherDTO) request.getAttribute(WebAttrConstant.ETS_ITEM_DTO);

%>
<head><title>其它设备查询页面</title>
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
<body onkeydown="autoExeFunction('do_SearchPlan()');" onload="initPage();">
<form action="/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet" name="workForm" method="post">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    printTitleBar("其它设备查询页面")
</script>
<table border="0" width="100%" class="queryHeadBg">
     <tr>
        <td width="8%" align="right">设备名称：</td>
        <td width="15%" align="left"><input type="text" name="itemName" style="width:80%" value="<%=reqParser.getParameter("itemName")%>">
        </td>
        <td width="7%" align="right">标签号：</td>
        <td width="15%" align="left"><input type="text" name="barcode1" style="width:80%" value="<%=reqParser.getParameter("barcode1")%>">
        </td>
         <td width="6%">
        </td>
         <td width="7%" align= "right">
            <img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()">
        </td>
        <td width=12% align="right">
         <img src="/images/button/efficient.gif" alt="生效" onclick="do_Enable();return false">
         <img src="/images/eam_images/disable.jpg" alt="失效" onclick="do_Disable();return false">
        </td>
    </tr>
    <tr>
        <td align="right" width="8%">供应商：</td>
        <td width="10%" align="left"><input type="text" name="vendorName" style="width:80%" readonly  value="<%=reqParser.getParameter("vendorName")%>"><a class="linka" onclick="do_selectName();">[…]</a></td>
        <td align="right">地点：</td>
        <td width="15%" align="left"><input type="text" name = "workorderObjectName" style="width:80%" value = "<%=dto.getWorkorderObjectName()%>"><a href = # title = " 点击选择地点" class= "linka" onclick = "do_SelectObjct();">[…]</a></td>
        <td width="6%" align = "right">是否失效：</td>
        <td width="7%"><select style="width:100%" name="disable">
                <option>请选择</option>
                <option value="是" <%=dto.getDisable().equals("是") ? "selected" : ""%> >已失效</option>
                <option value="否" <%=dto.getDisable().equals("否") ? "selected" : ""%>>未失效</option>
            </select></td>
        <td align="right">
             <img src="/images/eam_images/search.jpg" alt="查询设备"   onClick="do_SearchPlan(); return false;">
             <img src="/images/eam_images/new_add.jpg" alt="新增设备"   onClick="do_CreatePlan('<%=dto.getItemCategory()%>'); return false;">
        </td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="planId" value="">
<input type="hidden" name="vendorId">
<input type="hidden" name="loginName">
<input type="hidden" name="itemCategory" value="<%=dto.getItemCategory()%>">

   <div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:0px;width:840px">
    <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="3%" align="center" style="padding:0"><input type="checkbox" name="controlBox"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('controlBox','systemid')">
            </td>
            <td width="10%" align="center">标签号</td>
            <td width="12%" align="center">设备名称</td>
            <td width="12%" align="center">规格型号</td>
            <td width="8%" align="center">设备类型</td>
            <td width="13%" align="center">地点编号</td>
            <td width="18%" align="center">地点简称</td>
            <td width="9%" align="center">失效日期</td>
        </tr>
    </table>
</div>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
  <div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:92px;left:0px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#666666" id="dataTab">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("SYSTEMID")%>','<%=dto.getItemCategory()%>')">
            <td width="3%" align="center"><input type="checkbox" name="systemid" id="systemid<%=i%>"
                                                 value="<%=row.getValue("SYSTEMID")%>">
                <input type=hidden name=systemid1 value="<%=row.getValue("SYSTEMID")%>">
                <input type=hidden name=isCancel id="isCancel<%=i%>" value="">
            </td>
            <td height="22" width="10%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("BARCODE1")%>">
            </td>
            <td height="22" width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>">
            </td>
            <td height="22" width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>">
            </td>
            <td height="22" width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_CATE_GORY_DESC")%>">
            </td>
            <td height="22" width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>">
            </td>
            <td height="22" width="18%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>">
            </td>
            <td height="22" width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("DISABLE_DATE")%>">
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</form>
<div style="position:absolute;top:458px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<iframe name="downFrm" src="" style="display:none"></iframe>
<script type="text/javascript">
    function initPage() {
    window.focus();
    do_SetPageWidth();
//    do_SetDiffColor();
    }
    function do_SetDiffColor() {
    var orderStatus = mainFrm.orderStatus.value;
    if (orderStatus == "<%=AssetsDictConstant.CHK_STATUS_UPLOADED%>") {
        var dataTab = document.getElementById("dataTable");
        if (!dataTab || !dataTab.rows) {
            return;
        }
        var rows = dataTab.rows;
        var row = null;
        var cells = null;
        var cell = null;
        var node = null;
        var sysObj = null;
        var scanObj = null;
        for (var i = 0; i < rows.length; i++) {
            sysObj = document.getElementById("systemStatusName" + i);
            scanObj = document.getElementById("scanStatusName" + i);
            if (sysObj.value == "有" && sysObj.value != scanObj.value) {
                row = rows[i];
                cells = row.cells;
                for (var j = 0; j < cells.length - 2; j++) {
                    if (j == 0 || j == cells.length - 4 || j == cells.length - 3) {
                        cell = cells[j];
                        node = cell.childNodes[0];
                        node.style.backgroundColor = "#FF6600";
                        //#CCFF99
                    }
                }
            }
            if (sysObj.value == "无" && sysObj.value != scanObj.value) {
                row = rows[i];
                cells = row.cells;
                for (var j = 0; j < cells.length - 2; j++) {
                    if (j == 0 || j == cells.length - 4 || j == cells.length - 3) {
                        cell = cells[j];
                        node = cell.childNodes[0];
                        node.style.backgroundColor = "#CCFF99";
                        //#CCFF99
                    }
                }
            }
        }
    }
}

    function do_SearchPlan() {
        with (workForm) {
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            submit();
        }
    }

    function do_CreatePlan(itemCategory) {
        workForm.itemCategory.value = itemCategory;
        var url = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet?act=<%=WebActionConstant.NEW_ACTION%>&itemCategory=" + itemCategory;
        var popscript = 'width=700,height=600,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, 'planWin', popscript);
    }

    function do_ShowDetail(systemid, itemCategory) {
        if (event.srcElement.name == 'systemid') {
            return;
        }
        workForm.itemCategory.value = itemCategory;
        workForm.systemid.value = systemid;
        var url = "/servlet/com.sino.ams.system.specialty.servlet.OtEqVindicateServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&systemid=" + systemid + "&itemCategory=" + itemCategory;
        var popscript = 'width=700,height=600,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';

        window.open(url, '', popscript);
        workForm.submit();
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
        }else{
            workForm.vendorId.value = "";
            workForm.vendorName.value = "";
        }
    }

    function do_Enable() {
        var checkCount = getCheckedBoxCount("systemid");
        if (checkCount < 1) {
            alert("请选择一条记录，然后生效！");
        }
        else {
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


     function do_SelectObjct() {
        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 48.5;
        var dialogHeight = 30;
        var objs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (objs) {
            var obj = null;
            for (var i = 0; i < objs.length; i++) {
                obj = objs[i];
               document.workForm.workorderObjectName.value= obj.workorderObjectName;
            }
        }
    }
</script>