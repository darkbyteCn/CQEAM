<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>

</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('query();')">
<%
	SrvAssetLocationDTO dtoOpt = (SrvAssetLocationDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String assetsType = dtoOpt.getAssetsType();
	String pageTitle = "同步资产地点";
	
	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
		pageTitle = "同步TD资产地点";
	} 
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="<%=SrvURLDefineList.SRV_ASSET_LOCATION_SERVLET1 %>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="assetsType" value="<%=assetsType%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="10%" align="right">所属部门：</td>
            <td width="20%"><input type="text"  class="noEmptyInput" name="segment1" value="<%=dtoOpt.getSegment1()%>">(例如：890000)</td>
            <td width="10%" align="right">最后更新开始日期：</td>
            <td width="12%">
             <input  name="lastUpdateDate"   style="width:100%" title="点击选择日期" value="<%=dtoOpt.getLastUpdateDate()%>" readonly class="readonlyInput" onclick="gfPop.fStartPop(lastUpdateDate, endLastUpDate)">
             </td>
             <td width="10%" align="right">最后更新结束日期：</td>
             <td width="12%">
             <input  name="endLastUpDate"   style="width:100%" title="点击选择日期"  value="<%=dtoOpt.getEndLastUpDate()%>" readonly class="readonlyInput" onclick="gfPop.fEndPop(lastUpdateDate, endLastUpDate)">
             </td>
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
    function open_Alt(){
       if (document.getElementById("segment1").value=="") {
           alert("此处请输入地点代码第一段!");
       }
    }

	function do_SearchOrder() {
		var segment1 = document.getElementById("segment1").value;
		var lastUpdateDate = document.getElementById("lastUpdateDate").value;
		var endLastUpDate = document.getElementById("endLastUpDate").value;
		if(segment1 ==""){
			alert("所属部门不能为空！");
			return false;
		}
		if(lastUpdateDate ==""&& endLastUpDate ==""){
			alert("最近更新开始日期和结束日期不能同时为空！")
			return false;
		}
		if(mainFrm.transferType){
			mainFrm.transferType.disabled = false;
		}
	    mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
	}
	
	function do_SubmitSyn() {
		var segment1 = document.getElementById("segment1").value;
		var lastUpdateDate = document.getElementById("lastUpdateDate").value;
		var endLastUpDate = document.getElementById("endLastUpDate").value;
		if(segment1 ==""){
			alert("所属部门不能为空！");
			return false;
		}
		if(lastUpdateDate ==""&& endLastUpDate ==""){
			alert("最近更新开始日期和结束日期不能同时为空！")
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