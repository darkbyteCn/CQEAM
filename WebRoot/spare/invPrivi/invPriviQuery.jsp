<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.constant.AMSActionConstant" %>
<%@ page import = "com.sino.ams.constant.LookUpConstant" %>
<%@ page import = "com.sino.ams.spare.invprivi.dto.AmsInvPriviDTO" %>
<%--
  created by YS
  Date: 2007-09-28
  Time: 8:23:36
--%>

<html>
<head>
    <title>仓库权限定义</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/LookUp.js"></script>

</head>

<body leftmargin = "0" rightmargin = "0" topmargin = "0">

<%=WebConstant.WAIT_TIP_MSG%>
<%

    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;

	AmsInvPriviDTO dto = (AmsInvPriviDTO)request.getAttribute(QueryConstant.QUERY_DTO);//针对Servlet里的dto.setXXX()方法写的  
%>
<form method = "post" name = "mainFrm" action = "/servlet/com.sino.ams.spare.invprivi.servlet.AmsInvPriviServlet">
    <script type = "text/javascript">
        printTitleBar("仓库权限定义")
    </script>

    <table width = "100%" border = "0" class = "queryHeadBg">
        <tr>
            <td width = "12%" align = "right">用户名：</td>
            <td width = "27%"><input type = "text" name = "executeUserName" readonly class = readonlyInput
                                     value = "<%=parser.getParameter("executeUserName")%>"
                                     style = "width:80%">
                <input type = "hidden" name = "executeUser" value = "<%=parser.getParameter("executeUser")%>">
                <a href = "#" onClick = "do_SelectName();" title = "点击选择用户" class = "linka">[…]</a></td>
            <td width = "14%" align = "right">仓库名称：</td>
            <td width = "14%"><select name = "executeInv"><%=request.getAttribute(WebAttrConstant.INV_OPTION)%></select>
            </td>
            <td width = "14%" align = "right">业务类型：</td>
            <td width = "18%"><select name="businessCategory" style="width: 100%" type="text"><%=dto.getBizCategoryOpt()%></select>
            </td>
            <td width = "2%" align = "center">
                <img src = "/images/button/query.gif" style = "cursor:'hand'" onclick = "do_search();" alt = "查询">&nbsp;&nbsp;&nbsp;
                <img src = "/images/button/save.gif" alt = "保存" onClick = "do_SavePrivi();">
            </td>
        </tr>
    </table>
    <div style = "/*overflow-x:scroll;*/width:100%">
        <div style = "left:0px;width:100%"><%--<div style = "left:0px;width:150%">--%>
            <table width = "100%" class = "headerTable" border = "1">
                <tr height = "20">
                    <td width = "10%" align = "center">用户名</td>
                    <td width = "15%" align = "center">仓库名称</td>
                    <td width = "10%" align = "center">业务类型</td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox1" onclick = "checkAll('controlBox1','invInBox')"> 设备入库 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox2" onclick = "checkAll('controlBox2','invOutBox')"> 设备出库 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox3" onclick = "checkAll('controlBox3','invQueryBox')"> 设备查询 </td>

                    <%--
                   <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox1" onclick = "checkAll('controlBox1','invApplyBox')"> 备件申领 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox2" onclick = "checkAll('controlBox2','invBadInBox')"> 坏件入库 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox3" onclick = "checkAll('controlBox3','invBadReturnBox')"> 坏件归还 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox4" onclick = "checkAll('controlBox4','invDiscardBox')">备件报废 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox5" onclick = "checkAll('controlBox5','invNewInBox')">新购入库 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox6" onclick = "checkAll('controlBox6','invOrderPrintBox')">单据打印 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox7" onclick = "checkAll('controlBox7','invOutBox')">备件出库 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox8" onclick = "checkAll('controlBox8','invQueryBox')">备件查询 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox9" onclick = "checkAll('controlBox9','invRcvInBox')">接收入库 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox10" onclick = "checkAll('controlBox10','invRepairInBox')">送修返还 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox11" onclick = "checkAll('controlBox11','invSendRepairBox')">备件送修 </td>
                    <td width = "7%" align = "center"><input type = "checkbox" name = "titleCheck" class = "headCheckbox" id = "controlBox12" onclick = "checkAll('controlBox12','invTransferBox')">备件调拨 </td>
                    --%>
                </tr>
            </table>
        </div>
        <!--<input type="hidden" name="act">-->

        <div style = " left:0px;width:100%; height:350px"><%-- <div style = " left:0px;width:150%; height:350px">--%>
            <table id = tb_checkfield width = "100%" border = "1" bordercolor = "#666666">
                <%
                    if (rows != null && rows.getSize() > 0) {
                        for (int i = 1; i <= rows.getSize(); i++) {
                            row = rows.getRow(i - 1);
                %>
                <tr height = "22" style = "cursor:'hand'" onMouseMove = "style.backgroundColor='#EFEFEF'"
                    onMouseOut = "style.backgroundColor='#ffffff'">
                    <td width = "10%" align = "center"><%=row.getValue("USERNAME")%></td>
                    <td width = "15%" align = "center"><%=row.getValue("INV_NAME")%></td>
                    <td width = "10%" align = "center"><%=row.getValue("BIZ_CATEGORY_NAME")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_IN_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_OUT_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_QUERY_BOX")%></td>
                   <%-- <td width = "10%" align = "center"><%=row.getValue("USERNAME")%></td>
                    <td width = "" align = "center"><%=row.getValue("INV_NAME")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_APPLY_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_BAD_IN_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_BAD_RETURN_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_DISCARD_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_NEW_IN_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_ORDER_PRINT_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_OUT_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_QUERY_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_RCV_IN_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_REPAIR_IN_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_SEND_REPAIR_BOX")%></td>
                    <td width = "7%" align = "center"><%=row.getValue("INV_TRANSFER_BOX")%></td>--%>
                    <INPUT TYPE = "hidden" name = "userId" id = "userId_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("USER_ID")%>">
                    <INPUT TYPE = "hidden" name = "invCode" id = "invCode_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_CODE")%>">
                    <INPUT TYPE = "hidden" name = "invIn" id = "invIn_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_IN")%>">
                    <INPUT TYPE = "hidden" name = "invOut" id = "invOut_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_OUT")%>">
                    <INPUT TYPE = "hidden" name = "invQuery" id = "invQuery_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_QUERY")%>">
                <%--
                    <INPUT TYPE = "hidden" name = "userId" id = "userId_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("USER_ID")%>">
                    <INPUT TYPE = "hidden" name = "invCode" id = "invCode_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_CODE")%>">
                    <INPUT TYPE = "hidden" name = "invApply" id = "invApply_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_APPLY")%>">
                    <INPUT TYPE = "hidden" name = "invBadIn" id = "invBadIn_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_BAD_IN")%>">
                    <INPUT TYPE = "hidden" name = "invBadReturn" id = "invBadReturn_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_BAD_RETURN")%>">
                    <INPUT TYPE = "hidden" name = "invDiscard" id = "invDiscard_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_DISCARD")%>">
                    <INPUT TYPE = "hidden" name = "invNewIn" id = "invNewIn_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_NEW_IN")%>">
                    <INPUT TYPE = "hidden" name = "invOrderPrint" id = "invOrderPrint_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_ORDER_PRINT")%>">
                    <INPUT TYPE = "hidden" name = "invOut" id = "invOut_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_OUT")%>">
                    <INPUT TYPE = "hidden" name = "invQuery" id = "invQuery_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_QUERY")%>">
                    <INPUT TYPE = "hidden" name = "invRcvIn" id = "invRcvIn_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_RCV_IN")%>">
                    <INPUT TYPE = "hidden" name = "invRepairIn" id = "invRepairIn_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_REPAIR_IN")%>">
                    <INPUT TYPE = "hidden" name = "invSendRepair" id = "invSendRepair_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_SEND_REPAIR")%>">
                    <INPUT TYPE = "hidden" name = "invTransfer" id = "invTransfer_<%=row.getValue("ROWNUM")%>" value = "<%=row.getValue("INV_TRANSFER")%>">
           --%>     </tr>
                <%
                    }
                %>
            </table>
        </div></div>
</form>
<div style = " left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

<jsp:include page = "/servlet/com.sino.framework.servlet.MessageProcessServlet" flush = "true" />
</body>
</html>
<script type = "text/javascript">
    function do_search() {
        checkpoint = do_checkNotNull()
        if (checkpoint == 1) {
        <%--mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";--%>
            mainFrm.action = "/servlet/com.sino.ams.spare.invprivi.servlet.AmsInvPriviServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
            mainFrm.submit();
        }
        else {
            alert("至少需要一个查询条件");
        }

    }
    function changePrivi(chk) {
        var cbId = chk.id;
        cbId = cbId.replace("Box", "");
        var hiddenObj = document.getElementById(cbId);
        if (chk.checked) {
            hiddenObj.value = 1;
        } else {
            hiddenObj.value = 0;
        }
    }
    function do_SavePrivi() {
    <%--mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";--%>
        mainFrm.action = "/servlet/com.sino.ams.spare.invprivi.servlet.AmsInvPriviServlet?act=<%=WebActionConstant.SAVE_ACTION%>";
        mainFrm.submit();
    }
    function do_SelectName() {
        document.mainFrm.executeUserName.value = "";
        document.mainFrm.executeUser.value = "";
        document.mainFrm.executeInv.value = "";
        var lookUpName = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }
    function do_checkNotNull() {
        var checkPoint = 0;
        if (document.mainFrm.executeUserName.value != null && document.mainFrm.executeUserName.value != "") {
            checkPoint = 1;

        }
        if (document.mainFrm.executeInv.value != null && document.mainFrm.executeInv.value != "") {
            checkPoint = 1;
        }
        if (document.mainFrm.businessCategory.value != null && document.mainFrm.businessCategory.value != "") {
        	checkPoint = 1;
        }
        return  checkPoint;
    }
</script>
