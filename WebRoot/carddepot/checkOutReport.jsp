<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.calen.SimpleDate" %>
<%@ page import="com.sino.base.constant.calen.DateConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.ams.carddepot.constant.cardDepotConstant" %>
<%@ page import="com.sino.ams.carddepot.dto.YsOrderHeaderDTO" %>
<%@ page import="com.sino.ams.carddepot.dto.YsOrderLineDTO" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>

<html>

<head><title>单据</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
</head>
<%
    SimpleDate dateObj = new SimpleDate();
    dateObj.setDateValue(System.currentTimeMillis());
    dateObj.setDatePattern(DateConstant.CHINESE_PATTERN);
    YsOrderHeaderDTO headerDTO = (YsOrderHeaderDTO) request.getAttribute("headerDTO");
    DTOSet ds = (DTOSet) request.getAttribute("lineDTOs");
    int quentityAcount =0;
%>
<body leftmargin="1" topmargin="1">
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>

<form name="mainForm" action="/servlet/com.sino.ams.others.servlet.NoBarcodeServlet" method="post">
<input type="hidden" name="act" value="">

<table border="0" bordercolor="#9FD6FF" class="detailHeader" id="table1">

    <%
        String orderType = headerDTO.getOrderType();
        if (orderType.equals(cardDepotConstant.STOCK_OUT)) {
    %><tr><td align="center"><h2>实物出库凭证</h2></td></tr><tr>
    <td>
        <table width="90%" id="table21" cellspacing="0">
            <tr height="22">
                <td width="10%" align="right">领物单位:</td>
                <td width="20%"><%=headerDTO.getMaterialToName()%></td>
                <td width="10%"></td>
                <%--<td width="20%" align="center"><%=dateObj.getDateValue()%></td>--%>
                <td width="20%" align="center"><%=headerDTO.getCreateDate()%></td>
                <td width="30%" align="center">NO：<%=headerDTO.getHeaderNo()%></td>
            </tr>
        </table>
    </td>
</tr><%
} else if (orderType.equals(cardDepotConstant.GODOWN_ENTRY)) {
    YsOrderLineDTO dto = (YsOrderLineDTO) ds.getDTO(0);
%><tr><td align="center"><h2>实物入库凭证</h2></td></tr>
  <tr>
    <td>
        <table width="90%" id="table22" cellspacing="0">
            <tr height="22">
                <td width="10%" align="right">目的库:</td>
                <td width="20%"><%=dto.getMaterialFromName()%></td>
                <td width="10%"></td>
                <%--<td width="20%" align="center"><%=dateObj.getDateValue()%></td>--%>
                <td width="20%" align="center"><%=headerDTO.getCreateDate()%></td>
                <td width="30%" align="center">NO：<%=headerDTO.getHeaderNo()%></td>
            </tr>
        </table>
    </td>
</tr><%
} else if (orderType.equals(cardDepotConstant.CANCELLING_STOCKS)) {
     YsOrderLineDTO dto = (YsOrderLineDTO) ds.getDTO(0);
%><tr><td align="center"><h2>实物退库凭证</h2></td></tr><tr>
    <td>
        <table width="90%" id="table23" cellspacing="0">
            <tr height="22">
                <td width="10%" align="right">退库单位:</td>
                <td width="20%"><%=dto.getMaterialFromName()%></td>
                <td width="10%"></td>
                <%--<td width="20%" align="center"><%=dateObj.getDateValue()%></td>--%>
                <td width="20%" align="center"><%=headerDTO.getCreateDate()%></td>
                <td width="30%" align="center">NO：<%=headerDTO.getHeaderNo()%></td>
            </tr>
        </table>
    </td>
</tr><%
    }
%>

</table>
<!--<fieldset>-->
<!--<legend>物料信息 </legend>-->
<table width="90%" class="headerTable" border=1>
    <tr height="22">
        <td align="center" width="30%">品名</td>
        <td align="center" width="10%">数量</td>
        <td align="center" width="8%">单位</td>
        <td align="center" width="10%">单价</td>
        <td align="center" width="32%">备注</td>
    </tr>
</table>
<div style="width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="90%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
        <%

            for (int i = 0; i < ds.getSize(); i++) {
                YsOrderLineDTO lineDto = (YsOrderLineDTO) ds.getDTO(i);
                quentityAcount += lineDto.getQuantity().intValue();
        %>
        <tr height="22">
            <td width="30%" align="center"><input readonly type=text name=cardName value="<%=lineDto.getCardName()%>" class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="10%" align="center"><input readonly type="text" name="outQuantity" value="<%=lineDto.getQuantity()%>" class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="8%" align="center"><input readonly type=text name=cardUnit value="<%=lineDto.getCardUnit()%>" class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="10%" align="center"><input readonly type=text name=cardPrice value="<%=lineDto.getCardPrice()%>" class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="32%" align="center"><input readonly type="text" name="remark" value="<%=lineDto.getRemark()%>" class="noborderYellow" style="width:100%;text-align:left">
            </td>
        </tr>
        <%
            }
            for (int i = 0; i < 6 - ds.getSize(); i++) {
        %>
        <tr height="22">
            <td width="30%" align="center"><input readonly type=text name=cardName class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="10%" align="center"><input readonly type="text" name="outQuantity" class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="8%" align="center"><input readonly type=text name=cardUnit class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="10%" align="center"><input readonly type=text name=cardPrice class="noborderYellow" style="width:100%;text-align:center"></td>
            <td width="32%" align="center"><input readonly type="text" name="remark" class="noborderYellow" style="width:100%;text-align:left">
            </td>
        </tr>
        <%
            }
        %>
        <tr height="22">
            <td width="30%" align="center">合计</td>
            <td width="10%" align="center"><%=quentityAcount%></td>
            <td width="8%"></td>
            <td width="10%" align="center"></td>
            <td width="32%" align="center"></td>
        </tr>
    </table>
</div>
<!--</fieldset>-->
<table border="0" bordercolor="#9FD6FF" class="detailHeader" id="table3" width=90%>
    <tr height="22">
        <td width="25%">MIS管理员:</td>
        <td width="25%">审核人:</td>
        <td width="25%">实物管理员:</td>
<%
    if (orderType.equals(cardDepotConstant.CANCELLING_STOCKS)) {
%>
        <td width="25%">退料人:</td>
<%

    } else {
%>
        <td width="25%">领料人:</td>
<%
    }
%>

    </tr>
</table>
</form>
</body>
</html>
