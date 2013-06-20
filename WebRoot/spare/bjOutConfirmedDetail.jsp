<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%@ include file="/spare/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%
    AmsItemAllocateHDTO dto = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    dto.getToOrganizationId();
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String typed = dto.getTransStatus();
    String typeName = "";
    if (typed.equals("ALLOTING")) {
        typeName = "调出确认";
    } else if (typed.equals("RECEIVING")) {
        typeName = "调入确认";
    }
%>
<html>
<head><title><%=typeName%>
</title>
</head>
<body leftmargin="0" topmargin="0">
<form name="mainForm" <%if (typed.equals("ALLOTING")) {%>
      action="/servlet/com.sino.ams.spare.servlet.BjOutConfirmedServlet"<%} else if (typed.equals("RECEIVING")) {%>
      action="/servlet/com.sino.ams.spare.servlet.BjInConfirmedServlet"<%}%> method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=dto.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=dto.getToOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="8%" align="right">单据号：</td>
                    <td width="15%"><input type="text" name="transNo" value="<%=dto.getTransNo()%>" readonly
                                           style="width:100%"
                                           class="blueborderGray">
                    </td>
                    <td width="8%" align="right">调出公司：</td>
                    <td width="15%"><input type="text" name="fromOrganizationName"
                                           value="<%=dto.getFromOrganizationName()%>"
                                           style="width:100%" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="8%" align="right">调出仓库：</td>
                    <td width="15%"><input type="text" name="fromObjectName"
                                           value="<%=dto.getFromObjectName()%>"
                                           style="width:100%" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="8%" align="right">单据状态：</td>
                    <td width="15%"><input type="text" name="transStatusName"
                                           value="<%=dto.getTransStatusName()%>"
                                           style="width:100%" readonly
                                           class="blueborderGray"></td>
                </tr>
                <tr height="22">
                    <td width="8%" align="right">申请人：</td>
                    <td width="15%"><input type="text" name="createdUser" value="<%=dto.getCreatedUser()%>"
                               readonly style="width:100%"
                               class="blueborderGray">
                    </td>
                    <td width="8%" align="right">调入公司：</td>
                    <td width="15%"><input type="text" name="toOrganizationName"
                                           value="<%=dto.getToOrganizationName()%>"
                                           style="width:100%" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="8%" align="right">调入仓库：</td>
                    <%
                        if (dto.getToObjectName().equals("")) {
                    %>
                    <td width="15%"><input type="text" name="toObjectName"
                                           value="<%=dto.getToObjectName()%>"
                                           style="width:80%"
                                           class="blueborderYellow">
                    <a href="#" class="linka" style="cursor:'hand'" onclick="do_selectToName2();">[…]</a>
                    </td>
                    <%
                        } else {
                    %>
                    <td width="15%"><input type="text" name="toObjectName"
                                           value="<%=dto.getToObjectName()%>"
                                           style="width:100%" readonly
                                           class="blueborderGray">
                    </td>
                    <%
                        }
                    %>

                    <td width="8%" align="right">创建日期：</td>
                    <td width="15%"><input type="text" name="creationDate"
                               value="<%=dto.getCreationDate()%>"
                               style="width:100%" readonly
                               class="blueborderGray">
                    </td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="8"><textarea name="remark" rows="3" cols="" style="width:100%"
                                               class="blueBorder"><%=dto.getRemark()%>
                    </textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
<%
	if (dto.getTransStatus().equals("ALLOTING")) {
%>
        <img src="/images/eam_images/ok.jpg" alt="调出确认" onClick="do_Confirm();">
<%
	} else if (dto.getTransStatus().equals("RECEIVING")) {
%>
        <img src="/images/eam_images/ok.jpg" alt="调入确认" onClick="do_Confirm();">
<%
	}
	if(!StrUtil.isEmpty(dto.getTransNo())){
%>
		<img src="/images/eam_images/print.jpg" alt="打印页面" onclick="do_print();">
<% 
	}
%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">

    </legend>
<%
	if (typed.equals("ALLOTING")) {
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:137px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22">
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">调出数量</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
<%
	} else if (typed.equals("RECEIVING")) {
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:137px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22">
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">调入数量</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
<%
	}
%>
	<div id="dataDiv" style="overflow:scroll;height:500px;width:1007px;position:absolute;top:160px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
	if (rows != null && !rows.isEmpty()) {
		Row row = null;
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
				<td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" value="<%=row.getValue("ITEM_NAME")%>" class="finput" readonly></td>
				<td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getValue("ITEM_SPEC")%>" class="finput" readonly></td>
				<td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput" readonly></td>
				<td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" value="<%=row.getValue("SPARE_USAGE")%>" class="finput" readonly></td>
				<td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" value="<%=row.getValue("VENDOR_NAME")%>" class="finput" readonly></td>
                <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" class="finput3" readonly></td>
                <td style="display:none">
                    <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">
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
</form>
<div id="$$$disableMsg$$$" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
	<table width="100%" height="100%" style="background-color:#FFFFFF;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50,finishOpacity=50,style=2)">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900"  height="60">
				<table width="100%" height="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td bgcolor="#FFFFCC" align="center"><font color="#008000" size="2">正在提交数据，请稍候......</font><img src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
		<tr>
			<td colspan="3"></td>
		</tr>
	</table>
</div>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
function do_Confirm() {
    var toObjectNo=document.mainForm.toObjectNo.value;
    if (toObjectNo == "") {
        alert("请选择调入仓库！");
        return false;
    }
    mainForm.act.value = "OUT";
    mainForm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_print() {
    var toObjectName = document.mainForm.toObjectName.value;
    if (toObjectName == "") {
        alert("请先选择调入仓库！");
        return false;
    }
    var headerId=document.mainForm.transId.value;
	var toObjectName=document.mainForm.toObjectName.value;
	var url="/servlet/com.sino.ams.spare.servlet.BjOutConfirmedServlet?act=print&transId="+headerId+"&toObjectName="+toObjectName;
	var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
	window.open(url, "", style);
}

function do_selectToName2() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BF2%>&objectCategory=71&organizationId="+mainForm.toOrganizationId.value;
    var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var users = window.showModalDialog(url, null, popscript);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            mainForm.toObjectName.value = users[0].workorderObjectName;
            mainForm.toObjectNo.value = users[0].workorderObjectNo;
        }
    }
}
</script>
</html>