<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsSpareCategoryDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>备件分类确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
	<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
</style>
</head>
<script type="text/javascript">
    printTitleBar("备件分类确认")
</script>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
    AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) request.getAttribute(WebAttrConstant.SPARE_CATEGORY_DTO);
%>
<body topmargin="0" leftmargin="0">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form action="/servlet/com.sino.ams.spare.servlet.SpareConfirmServlet" method="post" name="mainFrm">
		<div align="center">
		<table width="60%" border="1" style="border-collapse: collapse" bordercolor="#666666" id="dataTable">
	         <tr>
				<td width="15%" align="right" rowspan="6">源信息：</td>
				<td width="15%" align="right">设备ID：</td>
				<td width="67%" ><input  class="readonlyInput" name="barcode1"  readonly style="width:100%" value="<%=dto.getBarcode()%>"></td>
			</tr>
	         <tr>
				<td width="15%" align="right">设备名称：</td>
				<td width="67%" ><input  class="readonlyInput" name="itemName1"  readonly style="width:100%" value="<%=dto.getItemName()%>"></td>
			</tr>
			<tr>
				<td align="right" width="15%">设备型号：</td>
				<td width="67%"><input class="readonlyInput"  type="text" name="itemSpec1" readonly value="<%=dto.getItemSpec()%>" style="width:100%"></td>
			</tr>
            <tr>
				<td align="right" width="15%">设备类型：</td>
				<td width="67%"><input class="readonlyInput"  type="text" name="itemCategory1" readonly value="<%=dto.getItemCategory()%>" style="width:100%"></td>
			</tr>
            <tr>
				<td align="right" width="15%">用途：</td>
				<td width="67%"><input class="readonlyInput"  type="text" name="spareUsage1" readonly value="<%=dto.getSpareUsage()%>" style="width:100%"></td>
			</tr>
            <tr>
				<td align="right" width="15%">厂商：</td>
				<td width="67%"><input class="readonlyInput"  type="text" name="vendorName1" readonly value="<%=dto.getVendorName()%>" style="width:100%"></td>
			</tr>
            <tr>
	            <td width="82%" colspan="3" align="center"><b>替换为</b></td>
	        </tr>
	         <tr>
				<td width="15%" align="right" rowspan="6">目标信息：</td>
				<td width="15%" align="right">设备ID：</td>
				<td width="67%" >
					<input type="text" name="barcode" class="readonlyInput" readonly value="" style="width:93%"><a href=# title="点击选择备件分类信息" class="linka" onClick="do_SelectItem(); ">[…]</a>
				</td>
			</tr>
	         <tr>
	            <td width="15%" align="right">设备名称：</td>
				<td width="67%"><input type="text" name="itemName" class="readonlyInput" readonly value="" style="width:100%" ></td>
			</tr>
			<tr>
	            <td align="right" width="15%">设备型号：</td>
				<td width="67%"><input type="text" name="itemSpec" class="readonlyInput" readonly value="" style="width:100%"></td>
			</tr>
            <tr>
	            <td align="right" width="15%">设备类型：</td>
				<td width="67%"><input type="text" name="itemCategory" class="readonlyInput" readonly value="" style="width:100%"></td>
			</tr>
            <tr>
	            <td align="right" width="15%">用途：</td>
				<td width="67%"><input type="text" name="spareUsage" class="readonlyInput" readonly value="" style="width:100%"></td>
			</tr>
            <tr>
	            <td align="right" width="15%">厂商：</td>
				<td width="67%"><input type="text" name="vendorName" class="readonlyInput" readonly value="" style="width:100%"></td>
			</tr>
            <tr>
	            <td colspan="3" align="center"><img src="/images/eam_images/confirm.jpg" onClick="do_unite(); return false;" alt="替换"><a style="cursor:'hand'" onClick="do_back();">&nbsp; <img src="/images/eam_images/back.jpg" alt="返回"></a></td>
			</tr>
	    </table>
    	</div>

<input type="hidden" name="act">
</form>
</body>
</html>
<script type="text/javascript">

function do_back() {
	with(mainFrm){
		mainFrm.barcode1.value = "";
		mainFrm.barcode.value = "";
		mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        submit();
	}
}

function do_SelectItem() {
    var lookUpName = "<%=LookUpConstant.BJ_SPARE_CATEGORY1%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var userPara = "barcode1=<%=dto.getBarcode()%>";
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainFrm");
        }
    }
}

function do_unite() {
	if(do_Validate()){
		with(mainFrm){
			mainFrm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
			submit();
		}
	}
}

function do_Validate(){
	var fieldNames = "barcode;itemName;itemSpec;itemCategory;spareUsage;vendorName";
	var fieldLabels = "设备ID;设备名称;设备型号;设备类型;用途;厂商";
	return formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
}
</script>