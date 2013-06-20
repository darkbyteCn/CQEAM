<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.soa.td.srv.AssetPeriodStatus.dto.TdSrvAssetPeriodStatusDTO" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>

</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('query();')">
 <%
 	TdSrvAssetPeriodStatusDTO periodStatusDTO=(TdSrvAssetPeriodStatusDTO)request.getAttribute(QueryConstant.QUERY_DTO);
  	String assetsType = periodStatusDTO.getAsstesType();
 	String pageTitle = "同步资产会计期";
 	
 	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
 		pageTitle = "同步TD资产会计期";
 	}
 %>
 <%=WebConstant.WAIT_TIP_MSG%>
 <jsp:include page="/message/MessageProcess"/>
<form action="<%=SrvURLDefineList.TD_SRV_ASSET_PERIOD_SERVLET1%>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="assetsType" value="<%=assetsType%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="8%" align="right">资产账簿：</td>
            <td width="20%"><select class="noemptyinput" name="bookTypeCode" style="width:40%"><%=periodStatusDTO.getBookOption()%></select> </td>
            <td width="15%">
  <%
  	if(!(AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
  %>
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
  <%
  	}
  %>
            <img src="/images/eam_images/synchronize.jpg" alt="点击同步" onclick="do_SubmitSyn();">
			</td>
		</tr>
    </table>

</form>
<%
	DTOSet ds=(DTOSet)request.getAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU);
	if(!(AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px">
			<td align=center width="10%">资产账簿</td>
			<td align=center width="10%">期间名称</td>       
			<td align=center width="10%">启始日期</td>
			<td align=center width="10%">截止日期</td>
  			<td align=center width="10%">期间打开日期</td>
			<td align=center width="10%">期间关闭日期</td>
			<td align=center width="10%">是否传送总账</td>
			<td align=center width="10%">会计期间状态</td>
		</tr>
	</table>
</div>
<%
	if(ds!=null&&ds.getSize()>0){
%>
<div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	TdSrvAssetPeriodStatusDTO dto=new TdSrvAssetPeriodStatusDTO();

		for(int i=0;i<ds.getSize();i++){
		 dto=(TdSrvAssetPeriodStatusDTO)ds.getDTO(i);
%>
        <tr class="dataTR" >

			<td align="center" width="10%"><%=dto.getBookTypeCode() %></td>
			<td align="center" width="10%"><%=dto.getPeriodName()%></td>
			<td align="center" width="10%"><%=dto.getStartDate()%> </td>
			<td align="center" width="10%"><%=dto.getEndDate() %></td>
			<td align="center" width="10%"><%=dto.getPeriodOpenDate() %></td>
			<td align="center" width="10%"><%=dto.getPeriodCloseDate() %></td>
			<td align="center" width="10%"><%=dto.getGlTransferFlag() %></td>
			<td align="center" width="10%"><%=dto.getPeriodStatus() %></td>
<%	
		}
 } else{
 %>
 <div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
 <%
 	}
 	}
  %>
    </table>
</div>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

	function do_SearchOrder() {
		var bookTypeCode = document.getElementById("bookTypeCode").value;
		if(bookTypeCode ==""){
			alert("资产账簿不能为空！");
			return false;
		}
		if(mainFrm.transferType){
			mainFrm.transferType.disabled = false;
		}
	    mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
	}
	
	function do_SubmitSyn() {
		var bookTypeCode = document.getElementById("bookTypeCode").value;
		if(bookTypeCode ==""){
			alert("资产账簿不能为空！");
			return false;
		}
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
	    mainFrm.submit();
	}
	
    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>