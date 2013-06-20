<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant" %>
<%@ include file="/spare/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
  <head><title>备件出库</title>
  </head>
  <body leftmargin="1" topmargin="1" onload="initPage();">
  <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
  <%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
	String status = dto.getTransStatus();
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareCKServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
					<td width="9%" align="right">单据号：</td>
					<td width="20%"><%=dto.getTransNo()%></td>
					<td width="9%" align="right">出库原因：</td>
					<td width="25%"><%=dto.getSpareReasonD()%></td>
					<td width="9%" align="right">调出仓库：</td>
                    <td width="25%"><%=dto.getFromObjectName()%></td>
                </tr>
                <tr height="22">
                    <td width="9%" align="right">单据类型：</td>
					<td width="25%">备件出库单</td>
                    <td align="right">创建人：</td>
                    <td><%=dto.getCreatedUser()%></td>
                    <td align="right">创建日期：</td>
                    <td><%=dto.getCreationDate()%></td>
                </tr>
                 <tr height="22">
                    <td align="right">单据状态：</td>
                    <td><%=dto.getTransStatusName()%></td>
                    <td align="right">备 注：</td>
                    <td colspan="5" align="left"><textarea class="blueborder" cols="90" name="remark" style="width:90%" rows="2"><%=dto.getRemark()%></textarea></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<fieldset>
    <legend>
	<img src="/images/eam_images/print.jpg" alt="打印" onclick="do_Print();">
    <img src="/images/eam_images/close.jpg" alt="关闭" onClick="do_Close();">
    </legend>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:117px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this);" style="cursor:hand" title="点击全选或取消全选">
				<td width="3%" align="center"><input type="checkbox"  class="headCheckbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">可有量</td>
				<td width="5%" align="center">出库数量</td>
				<td width="10%" align="center">备注</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>

	<div id="dataDiv" style="overflow:scroll;height:540px;width:1007px;position:absolute;top:140px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (rows == null || rows.isEmpty()) {
%>
            <tr id="mainTr0" style="display:none">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"style="height:20px;margin:0;padding:0"></td>
				<td width="10%"><input type="text" name="itemName" id="itemName0" value="" class="finput" readonly></td>
				<td width="15%"><input type="text" name="itemSpec" id="itemSpec0" value="" class="finput" readonly></td>
				<td width="10%"><input type="text" name="itemCategory" id="itemCategory0" value="" class="finput" readonly></td>
	            <td width="10%"><input type="text" name="spareUsage" id="spareUsage0" value="" class="finput" readonly></td>
	            <td width="10%"><input type="text" name="vendorName" id="vendorName0" value="" class="finput" readonly></td>
                <td width="5%"><input type="text" name="onhandQty" id="onhandQty0" readonly value="" class="finput3"></td>
				<td width="5%"><input type="text" name="quantity" id="quantity0" value="" class="finputNoEmpty3"></td>
                <td width="10%"><input type="text" name="remarkl" id="remarkl0"   value="" class="finput"></td>
				<td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value=""><input type="hidden" name="spareId" id="spareId0" value="">
                    <input type="hidden" name="barcode" id="barcode0" value="">
                </td>
            </tr>
<%
	} else {
		Row row = null;
		String readProp = "";
		if(!(status.equals(DictConstant.SAVE_TEMP) || status.equals(""))){
			readProp = " readonly";
		}
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr id="mainTr<%=i%>">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
				<td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly></td>
				<td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput" readonly></td>
                <td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" value="<%=row.getValue("SPARE_USAGE")%>" class="finput" readonly></td>
				<td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" value="<%=row.getValue("VENDOR_NAME")%>" class="finput" readonly></td>
                <td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" readonly value="<%=row.getValue("ONHAND_QTY")%>" class="finput3"></td>
				<td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" class="finput3" <%=readProp%>></td>
                <td width="10%"><input type="text" name="remarkl" id="remarkl<%=i%>"   value="<%=row.getValue("REMARK")%>"  class="finput"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>"><input type="hidden" name="spareId" id="spareId<%=i%>" value="<%=row.getValue("SPARE_ID")%>">
                    <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
                </td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>
</fieldset>
	<input type="hidden" name="act" value="">
	<input type="hidden" name="flag" value="">
	<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
	<input type="hidden" name="transNo" value="<%=dto.getTransNo()%>">
	<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
	<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
	<input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
	<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
	<input type="hidden" name="toOrganizationId" value="<%=dto.getToOrganizationId()%>">
	<input type="hidden" name="applyGroup"  value="<%=dto.getApplyGroup()%>">
	<input type="hidden" name="fromDept" value="<%=dto.getFromDept()%>">
</form>
</body>
</html>

<script type="text/javascript">
function initPage() {
    do_SetPageWidth();
}

function do_Print() {
	var headerId=document.mainForm.transId.value;
	var url="/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=print&transId="+headerId;
	var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
	window.open(url, "", style);
}

function do_Close(){
	self.close();
}

function do_SaveOrder() {
    document.mainForm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    document.mainForm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}
</script>
