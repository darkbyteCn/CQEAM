<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-18
  Function:备件返修省公司分配界面
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.spare.constant.SparePROCConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/spare/headerInclude.htm" %>
<html>
<head><title>备件返修申请审批页面(NEW)</title>
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

    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
    String hiddenRight = StrUtil.nullToString(request.getParameter("hiddenRight"));
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String firstN = "";
    if (sectionRight.equals("0")) {
        firstN = "1";
    }
%>
<form name="mainFrm" action="/servlet/com.sino.ams.spare.servlet.RepairBackServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>

<input type="hidden" name="act" value="">
<input type="hidden" name="procName" value="<%=SparePROCConstant.REPAIRE_BACK_PROC%>">
<input type="hidden" name="firstN" value="<%=firstN%>">
<input type="hidden" name="barcode1" value="">
<input type="hidden" name="orgvalue" value="">
<input type="hidden" name="lineId1" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="fromDept" value="<%=amsItemTransH.getFromDept()%>">
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
                        if (sectionRight.equals("OUT")) {
                    %>
                    <td width="9%" align="right">调拨类型：</td>
                    <td><input type="radio" name="org" id="org1" checked value="0">只能省公司调拨</td>
                    <td><input type="radio" name="org" id="org2" value="1">允许其他地市调拨</td>
                    <%}%>
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

    <img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_complete();">
    <img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_back();">
    <img src="/images/button/lookno.gif" alt="查询详细信息" id="img1" onclick="do_search()">
    <%if (sectionRight.equals("PRINT")) {%>
    <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
    <%}%>
    <img src="/images/button/viewFlow.gif" alt="查阅流程" id="img5" onClick="viewFlow();">
    <img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="viewOpinion(); return false;">
    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
</legend>
<% if (sectionRight.equals("OUT")) {%>
<script type="text/javascript">
    var columnArr = new Array("checkbox", "设备名称", "设备型号", "设备类型", "用途", "厂商", "故障地点", "返修数量", "实分数量");
    var widthArr = new Array("2%", "10%", "15%", "10%", "10%", "10%", "10%", "5%", "5%");
    printTableHead(columnArr, widthArr);
</script>
<%} else {%>
<script type="text/javascript">
    var columnArr = new Array("checkbox", "设备名称", "设备型号", "设备类型", "用途", "厂商", "故障地点", "返修数量");
    var widthArr = new Array("2%", "10%", "15%", "10%", "10%", "10%", "10%", "5%");
    printTableHead(columnArr, widthArr);
</script>
<%}%>
<div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">

<%
    if (sectionRight.equals("OUT")) {
%>
<table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0"
       style="TABLE-LAYOUT:fixed;word-break:break-all">
    <%
        RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
        if (rows == null || rows.isEmpty()) {
    %>
    <tr id="mainTr0" style="display:none" onMouseMove="this.style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'" onclick="do_allot(this);">
        <td width="2%" align="center">
            <input type="checkbox" name="subCheck" id="subCheck10" style="height:20px;margin:0;padding:0">
            <input type="hidden" name="organizationId" id="organizationId0">
            <input type="hidden" name="holdCount" id="holdCount0">
        </td>
        <td width="10%" name="itemName" id="itemName10"></td>
        <td width="15%" name="itemSpec" id="itemSpec10"></td>
        <td width="10%" name="itemCategory" id="itemCategory10"></td>
        <td width="10%" name="spareUsage" id="spareUsage10"></td>
        <td width="10%" name="vendorName" id="vendorName10"></td>
        <td width="10%">
            <input type="text" name="reasons" id="reasons10" value="" class="blueBorder" readonly
                   style="width:100%;text-align:left">
        </td>
        <td width="5%" align="center">
            <input type="text" name="quantity" id="quantity10" readonly value="" class="blueborderGray"
                   style="width:100%;text-align:right">
        </td>
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
    <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="this.style.backgroundColor='#FFFFFF'" onclick="do_allot(this);">
        <td width="2%" align="center">
            <input type="checkbox" name="subCheck" id="subCheck1<%=i%>" style="height:20px;margin:0;padding:0">
            <input type="hidden" name="organizationId" id="organizationId<%=i%>">
            <input type="hidden" name="holdCount" id="holdCount<%=i%>">
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
        <td width="10%" align="center">
            <input type="text" name="reasons1" id="reasons<%=i%>" value="<%=row.getValue("REASONS")%>"
                   class="blueBorder" readonly style="width:100%;text-align:left">
        </td>
        <td width="5%" align="center">
            <input type="text" name="quantity" id="quantity1<%=i%>" readonly value="<%=row.getValue("QUANTITY")%>"
                   class="blueborderYellow" style="width:100%;text-align:right">
        </td>
        <td width="5%"><input type="text" name="actualQty" value="<%=row.getValue("ACTUAL_QTY")%>" readonly
                              class="blueborderGray" style="width:100%"></td>
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
<table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0"
       style="TABLE-LAYOUT:fixed;word-break:break-all">
    <%
        RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
        if (rows == null || rows.isEmpty()) {
    %>
    <tr id="mainTr0" style="display:none" onMouseMove="this.style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#FFFFFF'">
        <td width="2%" align="center">
            <input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0">
        </td>
        <td width="10%" name="itemName" id="itemName0"></td>
        <td width="15%" name="itemSpec" id="itemSpec0"></td>
        <td width="10%" name="itemCategory" id="itemCategory0"></td>
        <td width="10%" name="spareUsage" id="spareUsage0"></td>
        <td width="10%" name="vendorName" id="vendorName0"></td>
        <td width="10%">
            <input type="text" name="reasons" id="reasons0" value="" class="blueBorder" readonly
                   style="width:100%;text-align:left">
        </td>
        <td width="5%" align="center">
            <input type="text" name="quantity" id="quantity0" value="" class="blueborderYellow"
                   style="width:100%;text-align:right" <%if(sectionRight.equals("0")){%>onblur="checkQty();"<%}else{%>
                   readonly<%}%>>
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
        onMouseOut="this.style.backgroundColor='#FFFFFF'">
        <td width="2%" align="center">
            <input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0">
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
        <td width="10%" align="center">
            <input type="text" name="reasons" id="reasons<%=i%>" value="<%=row.getValue("REASONS")%>" class="blueBorder"
                   readonly style="width:100%;text-align:left">
        </td>
        <td width="5%" align="center">
            <input type="text" name="quantity" id="quantity<%=i%>"
                   <%if(sectionRight.equals("0")){%>onblur="checkQty();"<%}else{%> readonly<%}%>
                   value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" style="width:100%;text-align:right">
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
</fieldset>
</form>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
function do_complete() {//提交
    var paramObj = new Object();
    paramObj.orgId = "<%=user.getOrganizationId()%>";
    paramObj.useId = "<%=user.getUserId()%>";
//        paramObj.groupId = document.mainFrm.fromDept.value;
    paramObj.procdureName = "<%=SparePROCConstant.REPAIRE_BACK_PROC%>";
    paramObj.flowCode = "";
    paramObj.submitH = "submitH()";
    assign(paramObj);
}


function do_select() {
    //        var url = "/workorder/bts/upFile.jsp";
    var url = "/workorder/bts/upFile.jsp";
    var dialogStyle = "dialogWidth=250px;dialogHeight=131px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
    var reval = window.showModalDialog(url, "", dialogStyle);
}

function do_selectFlow1() {
    disablefxsqDocument();
    showNextfxsqTasksDiv();
}
function do_showfxsq() {
}


function subPanduan() {    //判断是否提交省公司备品备件管理员审批
    //       var  reval = "";
    //       var  situs = "SCANDING";
    //         var nextTaskSelect = document.getElementById("flowNextTasksSelect");
    //    var nextTaskValue = nextTaskSelect.options[nextTaskSelect.selectedIndex].value;
    var reval = document.getElementById("fxsqNextTasksSelect").value;
    if (reval == "") {
        alert("请先选择下一任务流向，然后点击确定！");
        return;
    }
//        alert(reval);
    if (reval == "1") {   //提交pda扫描
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
    <%--paramObj.groupId = "<%=user.getCurrGroupId()%>";--%>
        paramObj.groupId = document.mainFrm.fromDept.value;
        paramObj.procdureName = "备件返修申请流程";
        paramObj.flowCode = "SCANING";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    } else if (reval == "2") {  //提交备件部门领导审批
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
    <%--paramObj.groupId = "<%=user.getCurrGroupId()%>";--%>
        paramObj.groupId = document.mainFrm.fromDept.value;
        paramObj.procdureName = "备件返修申请流程";
        paramObj.flowCode = "2";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    } else if (reval == "3") {   //提交省公司备件管理员审批
        do_verifyTransNo();
    }
    hideFxsqTasksDiv();
    cancelFxsqDisable();
}


function province() {
    var paramObj = new Object();
    paramObj.orgId = "<%=user.getOrganizationId()%>";
    paramObj.useId = "<%=user.getUserId()%>";
    paramObj.groupId = document.mainFrm.fromDept.value;
    paramObj.procdureName = "备件返修申请流程";
    paramObj.flowCode = "1";
    paramObj.submitH = "submitH()";
    assign(paramObj);
}

function hideFxsqTasksDiv() {
    var nextTaskDiv = document.getElementById("fxsqNextTaskDiv");
    nextTaskDiv.style.visibility = 'hidden';
}

function cancelFxsqDisable() {
    var hideDiv = document.getElementById("fxsqHideDiv");
    hideDiv.style.visibility = 'hidden';
}

function do_back() {//退回
    document.mainFrm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
    addApproveContent();
    document.mainFrm.submit();
}

function submitH() {
    var actVal = "";
    if (document.mainFrm.firstN.value == "1") {
        actVal = "firstN";
    } else {
        actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
    }
    document.mainFrm.act.value = actVal;
    document.mainFrm.firstN.value = "";
    document.mainFrm.submit();
}

function do_Approve1() {
    document.mainFrm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
    document.mainFrm.submit();
}
function do_allot(obj) {
    var barcode;
    var transId;
    var lineId;
    var qty;
    var org;
    org = (document.getElementById("org1").checked) ? 0 : 1;
    barcode = obj.cells[1].childNodes[0].value;
    transId = document.mainFrm.transId.value;
    lineId = obj.cells[9].childNodes[0].value;
    qty = obj.cells[7].childNodes[0].value;
    var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "ALLOT" + "&barcode1=" + barcode + "&transId=" + transId + "&lineId1=" + lineId + "&sqty=" + qty + "&orgvalue=" + org;
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url, "bjOrder", popscript);
}
function do_search() {
    var transId;
    transId = mainFrm.transId.value;
    var url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "SEARCH" + "&transId=" + transId ;
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url, "bjOrder", popscript);
}

function do_check() { //检查申请数量和扫描数量数否一致


}

//将整个页面Disabled
//用来存放原始属性
function disablefxsqDocument() {
    //disable整个页面
    var hideDiv = document.getElementById("fxsqHideDiv");
    hideDiv.style.visibility = 'visible';
}

//显示下一流向的DIV
function showNextfxsqTasksDiv() {
    var nextTaskDiv = document.getElementById("fxsqNextTaskDiv");
    nextTaskDiv.style.position = 'absolute';

    if (event) {//有可能是没有这个值
        mouse_x = document.body.scrollLeft + event.clientX;
        mouse_y = document.body.scrollTop + event.clientY;
    } else {
        mouse_x = document.body.scrollLeft + 50;
        mouse_y = document.body.scrollTop + 50;
    }
    nextTaskDiv.style.left = mouse_x;
    nextTaskDiv.style.top = mouse_y;
    nextTaskDiv.style.visibility = 'visible';
}


var xmlHttp;
function do_verifyTransNo() {
    var url = "";
    createXMLHttpRequest();
    if (document.mainFrm.transId.value) {
        url = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=DO_EQUALS&transId=" + document.mainFrm.transId.value;
        xmlHttp.onreadystatechange = handleReadyStateChange1;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

function createXMLHttpRequest() {     //创建XMLHttpRequest对象
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
}

function handleReadyStateChange1() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'Y') {
                province();
            } else {
                alert("请确认申请数量和扫描数量！");
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

function checkQty() {
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
    var headerId = document.mainFrm.transId.value;
    var url = "/servlet/com.sino.ams.spare.servlet.SpareDiffServlet?act=print&transId=" + headerId;
    var style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, "", style);
}

</script>
</html>