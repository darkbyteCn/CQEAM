<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/sampling/headerInclude.jsp"%>
<%@ include file="/sampling/headerInclude.htm"%>

<%
	AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) request.getAttribute(SamplingWebAttributes.ORDER_DTO);
	DTOSet orderLines = (DTOSet) request.getAttribute(SamplingWebAttributes.ORDER_LINES);
%>
<body leftmargin="0" topmargin="0" onload="window.focus();">
<form name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("资产盘点管理>>盘点工单详细信息");
</script>
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="headerId" value="<%=dto.getHeaderId()%>">
<table border="0" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="1" bordercolor="#226E9B">
			    <tr>
			        <td align=right height="22" width="7%">任务编号：</td>
			        <td height="22" width="17%"><input type="text" name="taskNo" class="input_style2" style="width:100%" readonly value="<%=dto.getTaskNo()%>"></td>
			        <td align=right width="8%" height="22">任务名称：</td>
			        <td width="25%" height="22"><input type="text" name="taskName" class="input_style2" style="width:100%" readonly value="<%=dto.getTaskName()%>"></td>
			        <td align=right height="22" width="7%">开始日期：</td>
			        <td height="22" width="17%"><input type="text" name="startDate" class="input_style2" style="width:100%" readonly value="<%=dto.getStartDate()%>"></td>
			        <td align=right width="8%" height="22">截止日期：</td>
			        <td width="25%" height="22"><input type="text" name="endDate" class="input_style2" style="width:100%" readonly value="<%=dto.getEndDate()%>"></td>
			    </tr>
			    <tr>
			        <td align=right width="8%" height="40">任务描述</td>
			        <td width="34%" height="40" colspan="3"><textarea name="taskDesc" class="input_style2" style="width:100%" readonly style="width:100%; height:100%"><%=dto.getTaskDesc()%></textarea></td>
			        <td align=right height="40" width="8%">工单批备注</td>
			        <td height="40" width="45%" colspan="3"><textarea name="batchRemark" class="input_style2" style="width:100%" readonly style="width:100%; height:100%"><%=dto.getBatchRemark()%></textarea></td>
			    </tr>
			    <tr>
			        <td align=right width="8%" height="22">抽查单号：</td>
			        <td width="17%" height="22"><input type="text" name="orderNo" class="input_style2" style="width:100%" readonly value="<%=dto.getOrderNo()%>"></td>
			        <td align=right width="8%" height="22">工单批号：</td>
			        <td width="9%" height="22"><input type="text" name="batchNo" class="input_style2" style="width:100%" readonly value="<%=dto.getBatchNo()%>"></td>
			        <td align=right height="22" width="7%">地点编号：</td>
			        <td height="22" width="17%"><input type="text" name="samplingLocationCode" class="input_style2" style="width:100%" readonly value="<%=dto.getSamplingLocationCode()%>"></td>
			        <td align=right width="8%" height="22">地点名称：</td>
			        <td width="25%" height="22"><input type="text" name="samplingLocationName" class="input_style2" style="width:100%" readonly value="<%=dto.getSamplingLocationName()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">开始日期：</td>
			        <td height="22" width="17%"><input type="text" name="startTime" class="input_style2" style="width:100%" readonly value="<%=dto.getStartTime()%>"></td>
			        <td align=right height="22" width="8%">执行周期：</td>
			        <td height="22" width="9%"><input type="text" name="implementDays" class="input_style2" style="width:100%" readonly value="<%=dto.getImplementDays()%>"></td>
			        <td align=right height="22" width="7%">执行人：</td>
			        <td height="22" width="17%"><input type="text" name="implementUser" class="input_style2" style="width:100%" readonly value="<%=dto.getImplementUser()%>"></td>
			        <td align=right width="8%" height="22">单据状态：</td>
			        <td width="25%" height="22"><input type="text" name="orderStatusValue" class="input_style2" style="width:100%" readonly value="<%=dto.getOrderStatusValue()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">创建人：</td>
			        <td height="22" width="17%"><input type="text" name="createdUser" class="input_style2" style="width:100%" readonly value="<%=dto.getCreatedUser()%>"></td>
			        <td align=right height="22" width="8%">下发人：</td>
			        <td height="22" width="9%"><input type="text" name="distributeUser" class="input_style2" style="width:100%" readonly value="<%=dto.getDistributeUser()%>"></td>
			        <td align=right height="22" width="7%">下载人：</td>
			        <td height="22" width="17%"><input type="text" name="downloadUser" class="input_style2" style="width:100%" readonly value="<%=dto.getDownloadUser()%>"></td>
			        <td align=right height="22" width="8%">上传人：</td>
			        <td height="22" width="25%"><input type="text" name="uploadUser" class="input_style2" style="width:100%" readonly value="<%=dto.getUploadUser()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">创建时间：</td>
			        <td height="22" width="17%"><input type="text" name="creationDate" class="input_style2" style="width:100%" readonly value="<%=dto.getCreationDate()%>"></td>
			        <td align=right height="22" width="8%">下发时间：</td>
			        <td height="22" width="9%"><input type="text" name="distributeDate" class="input_style2" style="width:100%" readonly value="<%=dto.getDistributeDate()%>"></td>
			        <td align=right height="22" width="7%">下载时间：</td>
			        <td height="22" width="17%"><input type="text" name="downloadDate" class="input_style2" style="width:100%" readonly value="<%=dto.getDownloadDate()%>"></td>
			        <td align=right height="22" width="8%">上传时间：</td>
			        <td height="22" width="25%"><input type="text" name="uploadDate" class="input_style2" style="width:100%" readonly value="<%=dto.getUploadDate()%>"></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<fieldset style="border:1px solid #226E9B; position:absolute;top:185;width:100%;height:490px">
    <legend>
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="self.close()">
    </legend>
    <div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:23px;left:0px;width:100%" class="crystalScroll">
	    <table class="eamDbHeaderTable" border=1 style="width:140%">
	        <tr height="20px">
	            <td align=center width="10%" rowspan="2">设备条码</td>
	            <td align=center width="42%" colspan="7">系统属性</td>
	            <td align=center width="42%" colspan="7">扫描属性</td>
	            <td align=center width="6%" colspan="2">状态</td>
	        </tr>
	        <tr height="20px" class="eamDbHeaderTr">
	            <td align=center width="6%">设备专业</td>
	            <td align=center width="6%">设备名称</td>
	            <td align=center width="6%">设备型号</td>
	            
	            <td align=center width="6%">责任人</td>
	            <td align=center width="6%">责任部门</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="6%">使用人</td>
	            
	            <td align=center width="6%">设备专业</td>
	            <td align=center width="6%">设备名称</td>
	            <td align=center width="6%">设备型号</td>
	            
	            <td align=center width="6%">责任人</td>
	            <td align=center width="6%">责任部门</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="6%">使用人</td>
	            
	            <td align=center width="3%">系统</td>
	            <td align=center width="3%">扫描</td>
	        </tr>
	    </table>
	</div>
	<div style="overflow:scroll;height:440px;width:100%;position:absolute;top:65px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	int itemCount = orderLines.getSize();
	AmsAssetsSamplingLineDTO item = null;
	for(int i = 0; i < itemCount; i++){
		item = (AmsAssetsSamplingLineDTO)orderLines.getDTO(i);
%>
            <tr class="dataTR">
                <td width="10%"><input type="text" readonly class="finput2" value="<%=item.getBarcode()%>"></td>

                <td width="6%"><input type="text" readonly class="finput2" value="<%=item.getItemCategoryValue()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getItemName()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getItemSpec()%>"></td>
                
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getResponsibilityUserName()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getResponsibilityDeptName()%>"></td>
                <td width="6%"><input type="text" readonly class="finput2" value="<%=item.getStartDate()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getMaintainUser()%>"></td>
                
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getScanItemCategoryValue()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getScanItemName()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getScanItemSpec()%>"></td>
                
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getScanResponsibilityUserName()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getScanResponsibilityDeptName()%>"></td>
                <td width="6%"><input type="text" readonly class="finput2" value="<%=item.getScanStartDate()%>"></td>
                <td width="6%"><input type="text" readonly class="finput" value="<%=item.getScanMaintainUser()%>"></td>
                
                <td width="3%"><input type="text" readonly class="finput" value="<%=item.getSystemStatus()%>"></td>
                <td width="3%"><input type="text" readonly class="finput" value="<%=item.getScanStatus()%>"></td>
            </tr>
<%
	}
%>
        </table>
    </div>
	<input type="hidden" name="act">
</fieldset>	


</form>
</body>
</html>
<script>
function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=SamplingActions.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
</script>
