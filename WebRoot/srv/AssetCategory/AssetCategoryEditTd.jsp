<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.td.srv.assetcategory.dto.SBFIFATdSrvAssetCategoryDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-9-7
  Time: 13:55:46
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>

</head>
<% SBFIFATdSrvAssetCategoryDTO dtoOpt= (SBFIFATdSrvAssetCategoryDTO) request.getAttribute(QueryConstant.QUERY_DTO);
	String pageTitle = "TD系统资产类别";
%>
 <%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();')">
<form action="<%=SrvURLDefineList.TD_SRV_ESSET_CATEGORY_SERVLET %>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="8%" align="right">资产账簿：</td>
            <td width="15%">
            	<select name="bookTypeCode" style="width:80%" size="1"><%=dtoOpt.getOrgOption()%></select>
            </td>
            <td width="8%" align="right">更新日期：</td>
            <td width="15%" colspan="2"><input  name="lastUpdateDate" style="width:40%" title="点击选择日期" value="<%=dtoOpt.getLastUpdateDate()%>" readonly class="readonlyInput" onclick="gfPop.fPopCalendar(lastUpdateDate);"></td>
       </tr>
        <tr>
            <td width="8%" align="right">组合代码：</td>
            <td width="15%"><input type="text"  name="concatenatedSegments" value="<%=dtoOpt.getConcatenatedSegments()%>" style="width:80%"></td>
            <td width="8%" align="right">应用领域：</td>
            <td width="15%"><input type="text"  name="segment1" value="<%=dtoOpt.getSegment1()%>" style="width:80%"></td>
        	<td width="15%"><img align="bottom" src="/images/eam_images/synchronize.jpg" alt="点击同步" onclick="do_SubmitSyn();"></td>
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

</script>
</html>