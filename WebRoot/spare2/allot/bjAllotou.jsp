<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-8
  Time: 8:21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>所有地市</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>
<%
    String transId = StrUtil.nullToString(request.getParameter("transId"));
    String transNo = StrUtil.nullToString(request.getParameter("transNo"));
//    String detailId = StrUtil.nullToString(request.getParameter("detailId"));
    String itemCode = request.getParameter("itemCode");
    String itemAmount = request.getParameter("itemAmount");
    String barcode = StrUtil.nullToString(request.getParameter("barcode"));
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.ALLOT_D_DTO);

%>
<%--<%if (!StrUtil.isEmpty(transId)) {%>
<script type="text/javascript">
    alert(<%=transId%>);
    window.top.transId.value = <%=transId%>
</script>
<%}%>--%>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="0" topmargin="0">
<%--<script type="text/javascript">
    alert(window.top.getElementsByName("abc").value);   //可以直接在页面上弹出
</script>--%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.allot.servlet.AmsBjsAllotouServlet" method="post">

<input type="hidden" name="actId" id="actId" value="">
<input type="hidden" name="taskProp" id="taskProp" value="">
<input type="hidden" name="currTaskId" id="currTaskId"
       value="">
<input type="hidden" name="prevTaskId" id="prevTaskId"
       value="">
<input type="hidden" name="prevUserId" id="prevUserId"
       value="">
<input type="hidden" name="prevUserName" id="prevUserName"
       value="">
<input type="hidden" name="prevPositionId" id="prevPositionId"
       value="">
<input type="hidden" name="prevPositionName" id="prevPositionName"
       value="">
<input type="hidden" name="isHandUser" id="isHandUser"
       value="">
<input type="hidden" name="sectionRight" value="<%=sectionRight%>">
<input type="hidden" name="hiddenRight" value="">
<input type="hidden" name="signFlag" id="flowSignFlag" value="">

<input type="hidden" name="nextTaskId" id="flownextTaskId">
<input type="hidden" name="nextDeptId" id="flownextDeptId">
<input type="hidden" name="nextDeptName" id="flownextDeptName">
<input type="hidden" name="nextPositionId" id="flownextPositionId">
<input type="hidden" name="nextPositionName" id="flownextPositionName">
<input type="hidden" name="nextUserId" id="flownextUserId">
<input type="hidden" name="nextUserName" id="flownextUserName">
<input type="hidden" name="procId" id="flowprocId" value="">
<input type="hidden" name="currDeptId" id="flowcurrDeptId">
<input type="hidden" name="currDeptName" id="flowcurrDeptName">
<input type="hidden" name="approveOpinion" id="approveOpinion">
<input type="hidden" name="procName" id="flowProcName">

<table class="headertable" width="100%" border="1">
    <tr height="20">
        <td width="40%" align="center">地市</td>
        <td width="20%" align="center">可用量</td>
        <td width="20%" align="center">分配数量</td>
    </tr>
</table>
<table width="100%" border="1" id="mtlTable" borderColor="#9FD6FF">
    <%
        RowSet rows = (RowSet) request.getAttribute("OU_ITEM_COUNT");
        int count = 0;
        int quty = 0;
        if (rows != null && !rows.isEmpty()) {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                quty = row.getStrValue("HOLD_COUNT").equals("")?0:Integer.parseInt(row.getStrValue("HOLD_COUNT"));

    %>
    <%
        if (sectionRight.equals("OUT")) {
            if (quty > 0) {
    %>

    <tr id="xhTr<%=i%>" onclick="get_barcode('<%=i%>')" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#ffffff'"    >
        <td width="40%" name="company" id="company<%=i%>"><%=row.getValue("COMPANY")%>
        </td>

        <td width="20%"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                               value="<%=row.getValue("NOW_COUNT")%>"
                               class="noborderGray" readonly style="width:100%;text-align:center">
        </td>
        <td width="20%"><input type="text" name="holdCount" id="holdCount<%=i%>" onblur="do_load(this)"
        <%-- <%if(sectionRight.equals("OUT")){%> onclick="get_bacode(this)"<%}%>--%>
                               value="<%=row.getValue("HOLD_COUNT")%>"
                               style="width:100%;text-align:center">
        </td>

        <td style="display:none">
            <input type="hidden" name="organizationId" id="organizationId<%=i%>"
                   value="<%=row.getValue("ORGANIZATION_ID")%>">
            <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">


        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
        onMouseOut="style.backgroundColor='#ffffff'"
            >
        <td width="40%" name="company" id="company<%=i%>"><%=row.getValue("COMPANY")%>
        </td>

        <td width="20%"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                               value="<%=row.getValue("NOW_COUNT")%>"
                               class="noborderGray" readonly style="width:100%;text-align:center">
        </td>
        <td width="20%"><input type="text" name="holdCount" id="holdCount<%=i%>" onblur="do_load(this)"
        <%-- <%if(sectionRight.equals("OUT")){%> onclick="get_bacode(this)"<%}%>--%>
                               value="<%=row.getValue("HOLD_COUNT")%>"
                               style="width:100%;text-align:center">
        </td>

        <td style="display:none">
            <input type="hidden" name="organizationId" id="organizationId<%=i%>"
                   value="<%=row.getValue("ORGANIZATION_ID")%>">

        </td>
    </tr>
    <%
                }
            }
        }
    %>
</table>
<%--<table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable1" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <input type="hidden" name="barcode" id="barcode0">
        </td>
    </tr>
    <%
        if (set != null && set.getSize() > 0) {
            for (int i = 0; i < set.getSize(); i++) {
                AmsBjsAllotDDto dto1 = (AmsBjsAllotDDto) set.getDTO(i);
    %>
    <tr>
        <td><input type="hidden" name="barcode" id="barcode<%=i%>"
                   value="<%=dto1.getBarcode()%>"></td>
    </tr>
    <%
            }
        }
    %>
</table>--%>

<input type="hidden" name="itemCode" value="<%=itemCode%>">
<input type="hidden" name="itemAmount" value="<%=itemAmount%>">
<input type="hidden" name="barcode" value="<%=barcode%>">
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="transNo" value="<%=transNo%>">
<%--<input type="hidden" name="detailId" value="<%=detailId%>">--%>
<%-- <input type="hidden" name="transId" value="<%=dto.getTransId()%>">--%>
<input type="hidden" name="lineId">
<input type="hidden" name="act">
<input type="hidden" name="count" id="count" value="<%=count%>">
<input type="hidden" name="value1" id="value1" value="">

</form>
</body>
</html>
<script type="text/javascript">
    function get_bacode(obj) {
        var lookUpName = "<%=LookUpConstant.SELECT_BARCODE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var userPara = "itemCode=" + mainForm.itemCode.value;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var tab = document.getElementById("mtlTable")
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                appendDTORow(tab, user);
            }
        }
    }
    function do_load(obj) {

        var count1 = obj.value;
        var count2 = mainForm.itemAmount.value;
        var countsObj = document.getElementsByName("holdCount");
        var totalCount = 0;
        for (var i = 0; i < countsObj.length; i++) {
            var count = countsObj[i].value;
            if (count != "") {
                totalCount += Number(count);
            }
        }
        if (count1 > count2) {
            alert("请确认数量，分配数量不能超过现有量!");
        }
        else {
            if (totalCount > count2) {
                alert("请确认数量，分配总数量不能超过现有量!");
            } else {
                mainForm.value1.value = 0;
            }
        }


    }
    function get_barcode(obj) {
        var hh = document.getElementById("detailId" + obj).value;
        var rows = document.getElementById("mtlTable").rows;
       /* for (var i = 0; i < rows.length; i++) {
            rows[i].style.backgroundColor = "#FFFFFF"
        }
        rows[obj].style.backgroundColor = "#9FD6FF";*/
        var url = "/servlet/com.sino.ams.spare2.allot.servlet.AmsBjsAllotouServlet?act=" + "barcode" + "&itemCode=" + mainForm.itemCode.value + "&transId=" + mainForm.transId.value + "&detailId=" + hh;
        //         var popscript;
        //    popscript = "dialogWidth:1020"
        //
        //            + ";dialogHeight:700"
        //
        //            + ";center:yes;status:no;scrollbars:no;help:no";
        var popscript = 'width=1020,height=700,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        //        window.showModalDialog(url, null, popscript);
        window.open(url, 'planWin', popscript);

    }
    function do_y() {
        var countsObj = document.getElementsByName("holdCount");
        var totalCount = 0;
        for (var i = 0; i < countsObj.length; i++) {
            var count = countsObj[i].value;
            if (count != "") {
                totalCount += Number(count);
            }
        }

    }
    function do_ll() {
        alert(mainForm.sectionRight.value);
    }
</script>