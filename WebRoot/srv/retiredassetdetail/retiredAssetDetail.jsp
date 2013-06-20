<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.assetretire.dto.SrvRetiredAssetDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <title>报废资产读取服务</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>


</head>
<% SrvRetiredAssetDTO dtoOpt= (SrvRetiredAssetDTO) request.getAttribute(QueryConstant.QUERY_DTO); 
	String assetsType = dtoOpt.getAssetsType();
	String pageTitle = "同步报废资产信息（ODI）";
	
	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
		pageTitle = "同步TD报废资产信息（ODI）";
	}
 %>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();');">

<form action="/servlet/com.sino.soa.mis.srv.assetretire.servlet.TransRetiredAssetDetailServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="assetsType" value="<%=assetsType%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
       <tr>
            <td width="10%" align="right">资产账簿：</td>
            <td width="15%"><select class="noEmptyInput" name="bookTypeCode" id="bookTypeCode" style="width:100%" size="1"><%=dtoOpt.getOrgOption()%></select></td>
             <td width="10%" align="right">报废时间从：</td>
             <td width="20%"><input  name="dateRettredFrom"   style="width:70%" title="点击选择日期" value="<%=dtoOpt.getDateRettredFrom()%>" readonly class="readonlyInput" onclick="gfPop.fStartPop(dateRettredFrom, dateRettredTo);"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(dateRettredFrom, dateRettredTo);"></td>
             <td width="10%" align="right">报废时间到：</td>
             <td width="20%" ><input  name="dateRettredTo"   style="width:70%" title="点击选择日期" value="<%=dtoOpt.getDateRettredTo()%>" readonly class="readonlyInput" onclick="gfPop.fEndPop(dateRettredFrom, dateRettredTo);"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(dateRettredFrom, dateRettredTo);"></td>
            <td width="15%" align="center">
              <img src="/images/eam_images/synchronize.jpg" alt="点击同步" onclick="do_SubmitSyn();">
            </td>
        </tr>
    </table>

</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

    function do_SearchOrder() {
        document.mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }

    function do_SubmitSyn() {
        var bookTypeCode = document.getElementById("bookTypeCode").value;
        if (bookTypeCode == "") {
            alert("资产账簿不能为空！");
            return;
        }
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
        document.mainFrm.submit();
    }

    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>