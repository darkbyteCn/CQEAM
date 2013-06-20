<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<%
	AmsAssetsCheckHeaderDTO chkOrder = (AmsAssetsCheckHeaderDTO) request.getAttribute(AssetsWebAttributes.CHECK_HEADER_DATA);
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.CHECK_LINE_DATAS);
%>
<body leftmargin="0" topmargin="0" onload="window.focus();">
<form name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("资产盘点管理>>盘点工单详细信息");
</script>
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="headerId" value="<%=chkOrder.getHeaderId()%>">
<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>


			<table width=100% border="1" bordercolor="#226E9B">
			    <tr>
			        <td align=right width="8%" height="22">盘点单号：</td>
			        <td width="17%" height="22"><input type="text" class="input_style2"  name="transNo" value="<%=chkOrder.getTransNo()%>" readonly style="width:100%; border-style: solid; border-width: 0"></td>
			        <td align=right width="8%" height="22">盘点部门：</td>
			        <td width="9%" height="22"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%;" name="checkDeptName" value="<%=chkOrder.getCheckDeptName()%>"></td>
			        <td align=right height="22" width="7%">地点编号：</td>
			        <td height="22" width="17%">
					<input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%; height:15" name="objectCode" readonly value="<%=chkOrder.getObjectCode()%>" size="20"></td>
			        <td align=right width="8%" height="22">盘点地点：</td>
			        <td width="25%" height="22"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="objectLocation" readonly value="<%=chkOrder.getObjectLocation()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">开始日期：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="startTime" value="<%=chkOrder.getStartTime()%>"></td>
			        <td align=right height="22" width="8%">执行周期：</td>
			        <td height="22" width="9%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="implementDays" value="<%=chkOrder.getImplementDays()%>"></td>
			        <td align=right height="22" width="7%">执行人：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="implementUser" value="<%=chkOrder.getImplementUser()%>"></td>
			        <td align=right width="8%" height="22">单据状态：</td>
			        <td width="25%" height="22"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="statusName" value="<%=chkOrder.getStatusName()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">创建时间：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="creationDate" value="<%=chkOrder.getCreationDate()%>"></td>
			        <td align=right height="22" width="8%">下发时间：</td>
			        <td height="22" width="9%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="distributeDate" value="<%=chkOrder.getDistributeDate()%>"></td>
			        <td align=right height="22" width="7%">下载时间：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2" style="border-style:solid; border-width:0; width:100%" name="downloadDate" value="<%=chkOrder.getDownloadDate()%>"></td>
			        <td align=right height="22" width="8%">上传时间：</td>
			        <td height="22" width="25%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="uploadDate" value="<%=chkOrder.getUploadDate()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">创建人：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="createdUser" value="<%=chkOrder.getCreatedUser()%>"></td>
			        <td align=right height="22" width="8%">下发人：</td>
			        <td height="22" width="9%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="distributeUser" value="<%=chkOrder.getDistributeUser()%>"></td>
			        <td align=right height="22" width="7%">下载人：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="downloadUser" value="<%=chkOrder.getDownloadUser()%>"></td>
			        <td align=right height="22" width="8%">上传人：</td>
			        <td height="22" width="25%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%" name="uploadUser" value="<%=chkOrder.getUploadUser()%>"></td>
			    </tr>
			    <tr>
			        <td align=right height="22" width="8%">归档时间：</td>
			        <td height="22" width="17%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%; " name="archivedDate" value="<%=chkOrder.getArchivedDate()%>"></td>
			        <td align=right height="22" width="8%">归档人：</td>
			        <td height="22" width="9%"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%; " name="archivedUser" value="<%=chkOrder.getArchivedUser()%>"></td>
			        <td align=right width="7%" height="22">任务描述：</td>
			        <td width=17% height="22"><input type="text" class="input_style2"  style="border-style:solid; border-width:0; width:100%; " name="taskDesc" value="<%=chkOrder.getTaskDesc()%>"></td>
			        <td align=right width="8%" height="22">　</td>
			        <td width="25%" height="22">　</td>
			    </tr>
			    <tr>
			        <td align=right height="44" width="8%">扫描专业：</td>
 			        <td height="44" width="17%"  ><input type="text" class="input_style2" style="width:100%" value="<%=chkOrder.getCheckCategoryName()%>" ></td>
			        <td align=right height="44" width="8%">差异原因：</td>
 			        <td height="44" width="1%" colspan="5" class="input_style2" ><%=StrUtil.htmlStrEncode(chkOrder.getDifferenceReason())%></td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
<fieldset style="border:1px solid #397DF3; position:absolute;top:185px;width:100%;height:72%">
    <legend>
		<img border="0" src="/images/eam_images/close.jpg" alt="关闭" onclick="self.close()">
    </legend>
<%
    if (lineSet != null && !lineSet.isEmpty()) {
		AmsAssetsCheckLineDTO lineDTO = null;
%>
<script type="text/javascript">
    var columnArr = new Array("标签号", "设备专业", "设备名称", "设备型号", "所在地点", "责任人", "责任部门");
    var widthArr = new Array("9%","6%","13%", "13%", "30%", "6%", "23%");
    printTableHead(columnArr,widthArr);
</script>
    <div style="width:100%;overflow-y:scroll;height:445px">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">

<%	    
		String barcode = "";
		for (int i = 0; i < lineSet.getSize(); i++) {
	        lineDTO = (AmsAssetsCheckLineDTO) lineSet.getDTO(i);
	        barcode = lineDTO.getBarcode();
%>
			<tr class="dataTR" onclick="executeClick(this)" title="点击查看条码“<%=barcode%>”的详细信息">
				<td width="9%" align="center" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=barcode%></td>
				<td width="6%" align="left" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=lineDTO.getItemCategory()%></td>
				<td width="13%" align="left" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=lineDTO.getItemName()%></td>
				<td width="13%" align="left" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=lineDTO.getItemSpec()%></td>
				<td width="30%" align="left" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=lineDTO.getWorkorderObjectLocation()%></td>
				<td width="6%" align="left" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=lineDTO.getResponsibilityUserName()%>&nbsp;</td>
				<td width="23%" align="left" onclick="do_ShowDetail('<%=barcode%>'); return false;"><%=lineDTO.getResponsibilityDeptName()%>&nbsp;</td>
			</tr>
<%
        }
%>
        </table>
    </div>
<%
    }
%>
</fieldset>
<input type="hidden" name="act">
</form>
</body>
</html>
<script>
function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
</script>
