<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.spare.dto.SpareOrderDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>备件业务单据查询</title>
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
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    SpareOrderDTO dto = (SpareOrderDTO) request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.spare.servlet.SpareOrderServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("备件单据综合查询")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td align="right" width="8%">公司：</td>
            <td width="15%"><select class="select_style1" style="width:80%" name="organizationId"><%=organizationId%>
            </select></td>
            <td align="right" width="8%">单据号：</td>
            <td align="left" width="15%"><input type="text" name="transNo" class="input_style1" style="width:80%"
                                                value="<%=dto.getTransNo()%>"></td>
            <td align="right" width="8%">单据状态：</td>
            <td width="15%"><select class="select_style1" name="transStatus" id="transStatus"
                                    style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%>
            </select></td>
            <td width=8% align="right"> 单据类型：</td>
            <td width="15%"><select name="transType" id="transType"
                                    class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_TYPE)%>
            </select></td>
        </tr>
        <tr>
            <td align="right" width="8%">备件名称：</td>
            <td width="15%"><input class="input_style1" style="width:80%" name="itemName" type="text" value="<%=dto.getItemName()%>"><a href="#"
                                                                                                                   onClick="SelectBJ(); "
                                                                                                                   title="点击选择生产厂家"
                                                                                                                   class="linka">[…]</a></td>
            <td align="right" width="8%">规格型号：</td>
            <td align="left" width="15%"><input type="text" name="itemSpec" class="input_style1" style="width:80%"
                                                value="<%=dto.getItemSpec()%>"></td>
            <td width=8% align="right">类型：</td>
            <td width="15%"><input type="text" name="itemCategory" class="input_style1" style="width:80%" value="<%=dto.getItemCategory()%>"></td>
            <td width="8%" align="right">用途：</td>
            <td width="15%" align="left"><input type="text" name="spareUsage" class="input_style1" style="width:80%" value="<%=dto.getSpareUsage()%>"></td>
        </tr>
        <tr>
            <td width="" align="right">创建日期：</td>
            <td><input type="text" name="startDate" value="<%=dto.getStartDate()%>"
                       style="width:80%" title="点击选择开始日期" readonly class="input_style1"
                       onclick="gfPop.fStartPop(startDate, endDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate)">
            </td>
            <td width="" align="right">到：</td>
            <td><input type="text" name="endDate" value="<%=dto.getEndDate()%>"
                       style="width:80%" title="点击选择截止日期" readonly class="input_style1"
                       onclick="gfPop.fEndPop(startDate, endDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(startDate, endDate)">
            </td>
            <td width="8%" align="right">厂商：</td>
            <td width="15%"><select name="vendorId" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></td>
            <td colspan=2 align="right"><img src="/images/eam_images/search.jpg" alt="查询" onClick="do_search(); return false;"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()"></td>
        </tr>
        <tr>
            <td width="8%" align="right">创建人：</td>
            <td width="15%" align="left"><input type="text" name="createdUser" class="input_style1" style="width:80%" value="<%=dto.getCreatedUser()%>"></td>
        </tr>
    </table>
    <input type="hidden" name="transId" value="<%=dto.getTransId()%>">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <script type="text/javascript">
        var columnArr = new Array("单据号", "创建人", "创建日期", "单据状态", "单据类型");
        var widthArr = new Array("20%", "10%", "10%", "10%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:300px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>','<%=row.getValue("TRANS_TYPE")%>')">
                <td width="20%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("CREATED_USER")%>
                </td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("CREATION_DATE"))%>
                </td>
                <td width="10%" align="center"><%=row.getValue("TRANS_STATUS_NAME")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("TRANS_TYPE_NAME")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="navigatorDiv" style="position:absolute;top:435px;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
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
        var transType = document.getElementById("transType")   ;
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transType, 'BJBFS;BJFP');
        dropSpecialOption(transStatus, 'ASSIGNED;APPROVED;CONFIRMD;RECEIVED;CANCELED;CREATE;DISTRIBUTED;IN_PROCESS;REJECTED');
    }
    <%--function do_ShowDetail(transId, transType) {--%>
        <%--mainForm.transId.value = transId;--%>
        <%--mainForm.transType.value = transType;--%>
        <%--var url = "/servlet/com.sino.ams.spare.servlet.SpareOrderServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId + "&transType=" + transType;--%>
        <%--var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";--%>
        <%--window.open(url, "instrum", popscript);--%>
    <%--}--%>

    function SelectVendorId() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                dto2Frm(vendorName, "mainForm");
            }
        }
    }
    function SelectBJ() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJ_SPARE_CATEGORY1%>";
        var popscript = "dialogWidth:55;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                        document.mainForm.itemName.value =  vendorName.itemName;
                        document.mainForm.itemSpec.value =  vendorName.itemSpec;
            }
        }
    }


    function do_ShowDetail(primaryKey, transType) {
        var url = "/servlet/com.sino.ams.spare.servlet.AmsItemTransHServlet";
        if (transType == "<%=DictConstant.BJCK%>") {  //备件出库  --  z
            url = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet";
        } else if (transType == "<%=DictConstant.BJFK%>") {  //坏件入库 -- jc
            url = "/servlet/com.sino.ams.spare.servlet.BjfkServlet";
        } else if (transType == "<%=DictConstant.BJRK%>") {  // 备件入库 --jc
            url = "/servlet/com.sino.ams.spare.servlet.AmsItemTransHServlet";
        } else if (transType == "<%=DictConstant.BJBF%>") {   //备件报废  --  z
            url = "/servlet/com.sino.ams.spare.servlet.SpareBFServlet";
        } else if (transType == "<%=DictConstant.BJSX%>") {   //送修      --z
            url = "/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet";
        } else if (transType == "<%=DictConstant.BJFH%>") {   //送修返还  -- z
            url = "/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet";
        } else if (transType == "<%=DictConstant.BJPD%>") {   //盘点  -- z
            url = "/servlet/com.sino.ams.spare.servlet.SpareOrderServlet";
        } else if (transType == "<%=DictConstant.BJSL%>") {   //申领单  -- y
            url = "/servlet/com.sino.ams.spare.servlet.SpareAttemperServlet";
        } else if (transType == "<%=DictConstant.FXSQ%>") {   //返修申请单  -- y
            url = "/servlet/com.sino.ams.spare.servlet.SpareDiffServlet";
        } else if (transType == "<%=DictConstant.BJDB%>") {   //调拨单  -- y
            url = "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet";
        }
        url += "?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + primaryKey;
        if (transType == "<%=DictConstant.BJSX%>") {
          url = "/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet?act=LOOK&transId=" + primaryKey;
        }
        if (transType == "<%=DictConstant.BJCK%>") {
          url = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=look&transId=" + primaryKey;
        }
        if (transType == "<%=DictConstant.JCHG%>") {
          url = "/servlet/com.sino.ams.spare.servlet.BjjchgServlet?act=DETAIL_ACTION&transId=" + primaryKey;
        }
        if (transType == "<%=DictConstant.HJGH%>") {
          url = "/servlet/com.sino.ams.spare.servlet.BjhjghServlet?act=DETAIL_ACTION&transId=" + primaryKey;
        }
        var popscript = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", popscript);
    }
</script>