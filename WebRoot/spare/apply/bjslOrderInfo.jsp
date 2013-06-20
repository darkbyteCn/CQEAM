<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>备件申领详细页面</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<%
    RequestParser rp = new RequestParser();
    rp.transData(request);
    String sectionRight = rp.getParameter("sectionRight");
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareAttemperServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="barcode1" value="">
<input type="hidden" name="orgvalue" value="">
<input type="hidden" name="lineId1" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">申请公司：</td>
                    <td width="25%"><%=amsItemTransH.getFromOrganizationName()%>
                    </td>
                     <%
                    if(sectionRight.equals("ALLOCATE")){
                    %>
                    <td width="9%" align="right">调拨类型：</td>

                   <td ><input type="radio" name="org" id="org1" checked  value="0">只能省公司调拨</td>
                   <td ><input type="radio" name="org" id="org2" value="1">允许其他地市调拨</td>
                    <%
                        }%>
                </tr>
                <tr height="22">
                    <td align="right">申请人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td colspan="2"><%=amsItemTransH.getTransStatusName()%>
                    </td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="11"><%=amsItemTransH.getRemark()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
<legend>
     <%if (!StrUtil.isEmpty(amsItemTransH.getTransNo())) {
         if (!amsItemTransH.getTransNo().equals(WebAttrConstant.ORDER_NO_AUTO_PRODUCE)) {
     %>
            <img src="/images/eam_images/print.jpg" alt="打印页面" onclick="do_print();">
     <%}}%>
    <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
</legend>
<% if (sectionRight.equals("ALLOCATE")) {%>
<script type="text/javascript">
    var columnArr = new Array("checkbox", "设备名称", "设备型号", "设备类型", "用途", "厂商", "申领数量", "实分数量");
    var widthArr = new Array("2%", "10%", "15%", "10%", "10%", "10%", "5%", "5%");
    printTableHead(columnArr, widthArr);
</script>
<%} else {%>
<script type="text/javascript">
    var columnArr = new Array("checkbox","设备名称", "设备型号", "设备类型", "用途", "厂商", "申领数量");
    var widthArr = new Array("2%","10%", "15%", "10%", "10%", "10%", "5%");
    printTableHead(columnArr, widthArr);
</script>
<%}%>

<div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">

<%
    if (sectionRight.equals("ALLOCATE")) {
%>
<table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
    <%
        RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
        if (rows == null || rows.isEmpty()) {
    %>
    <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" onclick="do_allot(this)">
        <td width="10%" name="itemName" id="itemName10"></td>
        <td width="15%" name="itemSpec" id="itemSpec10"></td>
        <td width="10%" name="itemCategory" id="itemCategory10"></td>
        <td width="10%" name="spareUsage" id="spareUsage10"></td>
        <td width="10%" name="vendorName" id="vendorName10"></td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity10" readonly value="" class="blueborderGray" style="width:100%;text-align:right"></td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId10" value="">
            <input type="hidden" name="barcode" id="barcode10" value="">
        </td>
    </tr>
    <%
    } else {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
    %>
    <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" onclick="do_allot(this)">
        <td width="2%" align="center">
            <input type="checkbox" name="subCheck" id="subCheck1<%=i%>" style="height:20px;margin:0;padding:0">
            <input type="hidden" name="organizationId" id="organizationId<%=i%>">
            <input type="hidden" name="holdCount" id="holdCount<%=i%>">
            <input type="hidden" name="">
        </td>
        <td width="10%" name="itemName" id="itemName1<%=i%>"><%=row.getValue("ITEM_NAME")%>
        </td>
        <td width="15%" name="itemSpec" id="itemSpec1<%=i%>"><%=row.getValue("ITEM_SPEC")%>
        </td>
        <td width="10%" name="itemCategory" id="itemCategory1<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
        </td>
        <td width="10%" name="spareUsage" id="spareUsage1<%=i%>"><%=row.getValue("SPARE_USAGE")%>
        </td>
        <td width="10%" name="vendorName" id="vendorName1<%=i%>"><%=row.getValue("VENDOR_NAME")%>
        </td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity1<%=i%>" readonly value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right">
        </td>
        <td width="5%"><input type="text" name="actualQty" value="<%=row.getValue("ACTUAL_QTY")%>" readonly class="blueborderGray" style="width:100%"></td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId1<%=i%>" value="<%=row.getValue("LINE_ID")%>">
            <input type="hidden" name="barcode" id="barcode1<%=i%>" value="<%=row.getValue("BARCODE")%>">
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<%
} else {
%>
<table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
    <%
        RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
        if (rows == null || rows.isEmpty()) {
    %>
    <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'">
        <td width="10%" name="itemName" id="itemName0"></td>
        <td width="15%" name="itemSpec" id="itemSpec0"></td>
        <td width="10%" name="itemCategory" id="itemCategory0"></td>
        <td width="10%" name="spareUsage" id="spareUsage0"></td>
        <td width="10%" name="vendorName" id="vendorName0"></td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity0" readonly value="" class="blueborderYellow" style="width:100%;text-align:right">
        </td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId0" value="">
            <input type="hidden" name="barcode" id="barcode0" value="">
        </td>
    </tr>
    <%
    } else {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
    %>
    <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'">
        <td width="2%" align="center">
            <input type="checkbox" name="subCheck" id="subCheck1<%=i%>" style="height:20px;margin:0;padding:0">
            <input type="hidden" name="organizationId" id="organizationId<%=i%>">
            <input type="hidden" name="holdCount" id="holdCount<%=i%>">
            <input type="hidden" name="">
        </td>
        <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
        </td>
        <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
        </td>
        <td width="10%" name="itemCategory" id="itemCategory<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
        </td>
        <td width="10%" name="spareUsage" id="spareUsage<%=i%>"><%=row.getValue("SPARE_USAGE")%>
        </td>
        <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
        </td>
        <td width="5%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" readonly value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right">
        </td>
        <td style="display:none">
            <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
            <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">

        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<%
    }
%>
</div>
    <%--<DIV align=center id='hiddenDiv' >--%>
        <%--<img src="/images/button/pageSet.gif" alt="打印页面设置" onclick="printSetup();">--%>
        <%--<img src="/images/button/printView.gif" alt="打印预览" onclick="printView();">--%>
        <%--<img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">--%>
        <%--<img src="/images/button/close.gif" alt="关闭" onClick="window.close();">--%>
     <%--</DIV>--%>
</fieldset>
<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
</form>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
    function do_Approve(flag) {
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = "<%=user.getCurrGroupId()%>";
        paramObj.procdureName = "备件申领流程";
        paramObj.flowCode = "";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    }
    function do_Approve2() {
        mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        addApproveContent();
        mainForm.submit();
    }
    function submitH() {
        var actVal = "";

        actVal = "<%=WebActionConstant.APPROVE_ACTION%>";


        document.mainForm.act.value = actVal;
        document.mainForm.submit();
    }
    function do_Approve1() {
        document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        document.mainForm.submit();
    }
    function do_allot(obj) {
        var barcode;
        var transId;
        var lineId;
        var qty;
        var org;
        org = (document.getElementById("org1").checked) ? 0 : 1;
        barcode = obj.cells[1].childNodes[0].value;
        transId = mainForm.transId.value;
        qty = obj.cells[6].childNodes[0].value;
        lineId = obj.cells[8].childNodes[0].value;
        var url = "/servlet/com.sino.ams.spare.servlet.BjslApproveServlet?act=" + "ALLOT" + "&barcode1=" + barcode + "&transId=" + transId + "&lineId1=" + lineId+"&sqty="+qty+"&orgvalue="+org;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "bjOrder", popscript);
    }
    function do_search() {
        var transId;
        transId = mainForm.transId.value;
        var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "SEARCH" + "&transId=" + transId ;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "bjOrder", popscript);
    }

 function do_print() {
      var headerId=document.mainForm.transId.value;
        var url="/servlet/com.sino.ams.spare.servlet.SpareAttemperServlet?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }
</script>
</html>