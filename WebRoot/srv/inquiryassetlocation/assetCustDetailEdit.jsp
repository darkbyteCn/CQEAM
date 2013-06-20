<%@ page import="srv.ams.inquiryassetlocation.dto.SrvAssetLocationDTO" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>

</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();')">

<form action="<%=SrvURLDefineList.SRV_ASSET_LOCATION_SERVLET %>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("查询资产地点同步");
</script>
    <input type="hidden" name="act" value="">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="10%" align="right">所属部门：</td>
            <td width="10%"><input type="text"  class="noEmptyInput" name="segment1" style="width:100%" ></td>
            <td width="10%" align="right">地点代码：</td>
            <td width="10%"><input type="text"  name=segment2 style="width:100%" ></td>
            <td width="10%" align="right">使用状态：</td>
            <td width="10%"><input type="text"  name="segment3" style="width:100%" ></td>
            <td width="10%" align="right">资产地点编码组合：</td>
            <td width="10%"><input type="text"  name="locationCombinationCode" style="width:100%" ></td>
        </tr>
         <tr>
         
            <td width="10%" align="right">资产地点名称：</td>
            <td width="10%"><input type="text"  name="locationCombinationName" style="width:100%" ></td>
            <td width="10%" align="right">是否启用：</td>
            <td width="10%"><input type="text"  name="enabledFlag" style="width:100%" ></td>
            <td width="10%" align="right">最后更新日期：</td>
            <td width="30%" colspan="1">
				<input  name="lastUpdateDate"   style="width:70%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fPopCalendar(lastUpdateDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fPopCalendar(lastUpdateDate)">
           	</td>
           	  <td width="10%" align="right">最后更新结束日期：</td>
            <td width="30%" colspan="1">
				<input  name="endLastUpDate"   style="width:70%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fPopCalendar(endLastUpDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fPopCalendar(endLastUpDate)">
           	</td>
        	
        </tr>
        <tr>
        <td width="15%"  align="center" colspan="8">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
            <img src="/images/button/synchronize.gif" alt="点击同步" onclick="do_SubmitSyn();">
			</td>
        </tr>
    </table>

</form>
<%
	DTOSet ds=(DTOSet)request.getAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU);

	
 %>
<div id="headDiv" style="overflow:hidden;position:absolute;top:90px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px">
			<td align=center width="10%">资产地点编码组合</td>
			<td align=center width="8%">资产地点名称</td>      
			<td align=center width="8%">启用标识</td>
		</tr>
	</table>
</div>
<%
	if(ds!=null&&ds.getSize()>0){		
 %>
<div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:120px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%      SrvAssetLocationDTO dto=new SrvAssetLocationDTO();

		for(int i=0;i<ds.getSize();i++){
		 dto=(SrvAssetLocationDTO)ds.getDTO(i);
		
 %>
        <tr class="dataTR" >

			<td align="right" width="10%"><%=dto.getLocationCombinationCode() %></td>
			<td align="right" width="8%"><%=dto.getLocationCombinationName() %></td>
			<td align="right" width="8%"><%=dto.getEnabledFlag() %></td>
			
<%	
		}
 } else{
 %>
 <div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
 <%
 	}
  %>
    </table>
</div>

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
    mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
    mainFrm.submit();
}

</script>
</html>