<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>省公司坏件应急归还</title>
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
     <style>
.approveInput{WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 1px ridge;font-size: 12px;text-align:left;}
</style>
</head>
<body leftmargin="1" topmargin="1">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String transNo = amsItemTransH.getTransNo();
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjjchgServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<table border="0" width = "100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1" border="0">
                <tr height="22">
                    <td width="9%" align="right"></td>
                    <td width="20%"></td>
                    <td width="12%" align="right"></td>
                    <td width="22%"><h3>省公司坏件应急归还</h3></td>
                    <td width="12%" align="right"></td>
                    <td width="22%"></td>
                </tr>
                 <tr height="22">
                     <td width="9%" align="right"></td>
                    <td width="20%"></td>
                    <td width="12%" align="right"></td>
                    <td width="22%"><h3></h3></td>
                   <td width="12%" align="right"></td>
                    <td width="22%"></td>
                </tr>
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%></td>
                    <td width="12%" align="right">来源仓库：</td>
                    <td width="22%"><%=amsItemTransH.getFromObjectName()%></td>
                    <td width="12%" align="right">目的仓库：</td>
                    <td width="22%"><%=amsItemTransH.getToObjectName()%></td>
                </tr>
                <tr height="22">
                    <td align="right">申请人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%></td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%></td>
                    <td align="right"></td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="11"><%=amsItemTransH.getRemark()%> </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
    <DIV align=center id='hiddenDiv'>
    <img src="/images/eam_images/page_config.jpg" alt="打印页面设置" onclick="printSetup();">
    <img src="/images/eam_images/print_view.jpg" alt="打印预览" onclick="printView();">
    <img src="/images/eam_images/print.jpg" alt="打印页面" onclick="do_print();">
    <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </DIV>
  <div style="left:1px;width:100%;overflow-y:visible" class="crystalScroll">
    <table  border="1" width="100%"  cellpadding="0" cellspacing="0" bordercolor="#666666">
        <tr height="30">
            <td width="10%" align="center">设备名称</td>
            <td width="15%" align="center">设备型号</td>
            <td width="10%" align="center">设备类型</td>
            <td width="10%" align="center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="5%" align="center">数量</td>
            <td width="10%" align="center">备注</td>
        </tr>
    </table>
</div>
<div style="width:100%;left:1px;margin-left:0;overflow-y:visible;" class="crystalScroll">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
        <%
            RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
            if (rows != null && !rows.isEmpty()) {
                int k = 0;

                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr id="mainTr<%=i%>" height="30" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
            <td width="10%" align="center"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="15%" align="center"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="10%" align="center"><%=row.getValue("ITEM_CATEGORY")%>
            </td>
            <td width="10%" align="center"><%=row.getValue("SPARE_USAGE")%>
            </td>
            <td width="10%" align="center"><%=row.getValue("VENDOR_NAME")%>
            </td>
            <td width="5%" align="center"><%=row.getValue("QUANTITY")%>
            </td>
            <td width="10%" align="center"><%=row.getValue("REMARK")%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
 <div style="left:1px;width:100%;position:relative;top:300px;">
<table border="0" bordercolor="#666666" style="left:1px;width:100%;">
    <tr height="22">
        <td width="8%" align="right">库管员：</td>
        <td width="12%"><%if(user.getOrganizationId() == 82) {%><img src="/images/fanjianqiang.jpg"><%}else{%><input type="text"  style="width:100%" readonly  class="approveInput" value=""><%}%></td>
        <td width="8%" align="right">运输人：</td>
        <td width="12%"><input type="text"  style="width:100%" readonly  class="approveInput" value=""></td>
        <td width="8%" align="right">接收人：</td>
        <td width="12%"><input type="text"  style="width:100%" readonly  class="approveInput" value=""></td>
        <td width="8%" align="right">审批人：</td>
        <td width="12%"><%if(user.getOrganizationId() == 82) {%><img src="/images/qianming.JPG"><%}else{%><input type="text"  style="width:100%"  readonly class="approveInput" value=""><%}%></td>
    </tr>
</table>
 </div>
</form>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
function do_print(){
    document.getElementById('hiddenDiv').style.visibility = 'hidden';
    document.getElementById("wb").execwb(6, 6);
    document.getElementById('hiddenDiv').style.visibility = 'visible';
}

function printSetup() {
    document.getElementById("wb").execwb(8, 1);
}

function printView() {
    document.getElementById('hiddenDiv').style.visibility = 'hidden';
    document.getElementById("wb").execwb(7, 1);
    document.getElementById('hiddenDiv').style.visibility = 'visible';
}
</script>
</html>
<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>