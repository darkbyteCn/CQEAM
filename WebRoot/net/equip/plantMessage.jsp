<%@ page contentType="text/html; charset=GBK" language="java"  %>
<%@ page import="com.sino.ams.net.equip.dto.PlantMessageDTO"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/DateProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
<%
	PlantMessageDTO item = (PlantMessageDTO)request.getAttribute(WebAttrConstant.ASSETS_DATA);
%>
<style>
.finput {BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
</style>
</head>
<body onload="window.focus()" leftmargin="0" topmargin="0">
<script type="text/javascript">
    printTitleBar("设备详细信息")
</script>
<table border="1" width="100%" style="border-collapse: collapse" id="table2" bordercolor="#3366EE">
	<tr>
		<td>
			<table border="1" width="100%" bordercolor="#666666" id="table3">
				<tr>
					<td width="15%" align="right">标签号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="barcode" readonly value ="<%=item.getBarcode()%>"></td>
					<td width="15%" align="right">设备专业：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="itemCategory" readonly value ="<%=item.getItemCategory()%>"></td>
				</tr>

				<tr>
					<td width="15%" align="right">设备名称：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="itemName" readonly value ="<%=item.getItemName()%>"></td>
					<td width="15%" align="right">规格型号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="itemSpec" readonly value ="<%=item.getItemSpec()%>"></td>
				</tr>

                <tr>
					<td width="15%" align="right">生产厂家：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="vendorName" readonly value ="<%=item.getVendorName()%>"></td>
					<td width="15%" align="right">使用年限：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="years" readonly value ="<%=item.getYears()%>"></td>
				</tr>

                <tr>
					<td width="15%" align="right">项目编号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="segment1" readonly value ="<%=item.getSegment1()%>"></td>
					<td width="15%" align="right">项目名称：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="name" readonly value ="<%=item.getName()%>"></td>
				</tr>

                <tr>
					<td width="15%" align="right">责任部门：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="deptName" readonly value ="<%=item.getDeptName()%>"></td>
					<td width="15%" align="right">责任人：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="userName" readonly value ="<%=item.getUserName()%>"></td>
				</tr>

                <tr>
					<td width="15%" align="right">地点简称：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="workorderObjectName" readonly value ="<%=item.getWorkorderObjectName()%>"></td>
					<td width="15%" align="right">所在地点：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="workorderObjectLocation" readonly value ="<%=item.getWorkorderObjectLocation()%>"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<p align="center"><img border="0" src="/images/eam_images/close.jpg" onclick="self.close()"></p>

</body>
</html>
