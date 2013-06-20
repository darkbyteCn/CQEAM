<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.transTaskInfo.dto.TransTaskInfoDTO" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
</head>
<body leftmargin="0" topmargin="0"  onload="initPage();" onkeydown="autoExeFunction('query();');">
<%
    TransTaskInfoDTO dto = (TransTaskInfoDTO) request.getAttribute(QueryConstant.QUERY_DTO);
	String assetsType = StrUtil.nullToString(dto.getAssetsType());
	String pageTitle = "同步项目任务信息";
	
	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
		pageTitle = "同步TD项目任务信息";
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="<%=SrvURLDefineList.TRANS_TASKINFO_SERVLET%>" method="post" name="mainFrm">
<script type="text/javascript">
     printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="assetsType" value="<%=assetsType%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td align="right" width="10%" align="right">项目编号：</td>
            <td align="left" width="20%">
             <input name="projectNum" id="projectNum" style="width:100%" class="readonlyInput"  value="<%=dto.getProjectNum()%>">
             <%-- <select class="noEmptyInput" name="projectNum" id="projectNum" style="width:100%" size="1"><%=dto.getProjectNum()%></select>--%>
            <td>
            <td align="right" width="6%">开始时间：</td>
             <td align="left" style="width:20%">
                <input type="text" name="stratLastUpdateDate" value="<%=dto.getStratLastUpdateDate()%>" style="width:60%" title="点击选择日期" readonly
                       class="readonlyInput" onclick="gfPop.fStartPop(stratLastUpdateDate, endLastUpdateDate);"><img
                    src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(stratLastUpdateDate, endLastUpdateDate);">
            </td>
            <td align="right" width="6%">结束时间：</td>
            <td align="left" style="width:20%">
                <input type="text" name="endLastUpdateDate" value="<%=dto.getEndLastUpdateDate()%>" style="width:60%" title="点击选择日期" readonly
                       class="readonlyInput" onclick="gfPop.fEndPop(stratLastUpdateDate, endLastUpdateDate);"><img
                    src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(stratLastUpdateDate, endLastUpdateDate);">
            </td>
            <td width="15%" align="left">
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
		if(mainFrm.transferType){
			mainFrm.transferType.disabled = false;
		}
	    mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
	}
	
	function do_SubmitSyn() {
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
	    mainFrm.submit();
	}
	
	function initPage() {
        do_SetPageWidth();
    }
    
</script>
</html>