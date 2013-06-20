<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-18
  Function: 备件调拨超时统计明细。
  --%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>派发出库单</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
      <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    String cityOption = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="groupId" value="">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%></td>
                    <td width="9%" align="right">调出地市：</td>
                    <td width="25%"><%=amsItemTransH.getFromOrganizationName()%></td>
                    <td width="9%" align="right">调入地市：</td>
                    <td width="25%"><%=amsItemTransH.getToOrganizationName()%></td>
                </tr>
                <tr height="22">
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%></td>
                    <td align="right">调出仓库：</td>
                    <td><%=amsItemTransH.getFromObjectName()%></td>
                    <td align="right">调入仓库：</td>
                    <td><%=amsItemTransH.getToObjectName()%></td>
                </tr>
                <tr>
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%></td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%></td>
                    <td align="right">备注：</td>
                    <td><%=amsItemTransH.getRemark()%></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
<legend>
    <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
    <%--<img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve2();">--%>
    <%--<img src="/images/button/viewFlow.gif" alt="查阅流程" id="img5" onClick="viewFlow();">--%>
    <%--<img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="viewOpinion(); return false;">--%>
     <DIV align=center id='hiddenDiv' >
        <%--<img src="/images/button/pageSet.gif" alt="打印页面设置" onclick="printSetup();">--%>
        <%--<img src="/images/button/printView.gif" alt="打印预览" onclick="printView();">--%>
        <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
     </DIV>
    <%--<img src="/images/button/close.gif" alt="关闭" onClick="window.close();">--%>
</legend>
<script type="text/javascript">
    var columnArr = new Array("checkbox", "设备名称", "设备型号", "设备类型", "用途", "厂商",  "调出方现有数量", "调拨数量");
    var widthArr = new Array("2%", "10%", "15%", "10%", "10%", "10%",  "10%", "5%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
        <%
            RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
            if (rows == null || rows.isEmpty()) {
        %>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%" name="itemName" id="itemName0"></td>
            <td width="15%" name="itemSpec" id="itemSpec0"></td>
            <td width="10%" name="itemCategory" id="itemCategory0"></td>
            <td width="10%" name="spareUsage" id="spareUsage0"></td>
            <td width="10%" name="vendorName" id="vendorName0"></td>
            <td width="5%" align="center"><input type="text" name="onhandQty" id="onhandQty" value="" class="noborderGray" readonly style="width:100%;text-align:right">
            </td>
            <td width="5%" align="center"><input type="text" name="quantity" id="quantity0" value="" class="blueborderYellow" style="width:100%">
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
            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0">
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
            <td width="10%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>" value="<%=row.getValue("ONHAND_QTY")%>" class="noborderGray" readonly style="width:100%;text-align:right">
            </td>
            <td width="5%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right">
            </td>
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">
                <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">

            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</fieldset>
<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
</form>
</body>
<script type="text/javascript">
       function do_Approve(flag) {
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = "<%=user.getCurrGroupId()%>";
        paramObj.procdureName = "备件调拨流程";
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
    function init() {
        <%--var fromOu = "<%=amsItemTransH.getFromOrganizationId()%>";--%>
        <%--var toOu = "<%=amsItemTransH.getToOrganizationId()%>";--%>
        <%--selectSpecialOption("fromOrganizationId", fromOu);--%>
        <%--selectSpecialOption("toOrganizationId", toOu);--%>
    }
    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            //            dto2Frm(projects[0], "form1");
            document.mainForm.toObjectName.value = projects[0].workorderObjectName;
            document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
            document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
        }
    }
    function do_SelectItem() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_DB%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var userPara = "organizationId=" + mainForm.fromOrganizationId.value;

        //LOOKUP传参数 必须和DTO中一致
        var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);

        if (assets) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < assets.length; i++) {
                data = assets[i];
                if (!isItemExist(data)) {
                    appendDTORow(tab, data);
                }
            }
        }
    }
   function isItemExist(itemObj) {
    var retVal = false;
    var tab = document.getElementById("dataTable");
    if (tab.rows) {
        var trObjs = tab.rows;
        var trCount = trObjs.length;
        var trObj = null;
        var itemValue = itemObj.barcode;
        var rowValue = null;
        for (var i = 0; i < trCount; i++) {
            trObj = trObjs[i];
            rowValue = trObj.cells[1].childNodes[0].value;
            if (itemValue == rowValue) {
                retVal = true;
            }
        }
    }
    return retVal;
}
    function validateData() {
        var validate = false;
        var fieldNames = "quantity";
        var fieldLabels = "数量";
        var validateType = EMPTY_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
        if (validate) {
            validateType = POSITIVE_INT_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }
  function do_print() {
      var headerId=document.mainForm.transId.value;
        var url="/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }
</script>
</html>