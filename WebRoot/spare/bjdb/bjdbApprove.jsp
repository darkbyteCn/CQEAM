<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>派发出库单</title>
<script type="text/javascript">
    printToolBar();
</script>
</head>
<%@ include file="/flow/flowNoButton.jsp" %>
<body leftmargin="1" topmargin="1" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemAllocateHDTO dto = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.BjdbServlet" method="post">
<%@ include file="/flow/flowPara.jsp" %>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transNo" value="<%=dto.getTransNo()%>">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=dto.getTransNo()%>
                    </td>
                    <td width="9%" align="right">调出地市：</td>
                    <td width="25%"><%=dto.getFromOrganizationName()%>
                    </td>
                    <td width="9%" align="right">调入地市：</td>
                    <td width="25%"><%=dto.getToOrganizationName()%>
                    </td>
                </tr>
                <tr height="22">

                    <td align="right">单据状态：</td>
                    <td><%=dto.getTransStatusName()%></td>
                    <td align="right">调出仓库：</td>
                    <td><%=dto.getFromObjectName()%></td>
                    <td align="right">调入仓库：</td>
                    <td><%=dto.getToObjectName()%></td>
                </tr>
                <tr>
                    <td align="right">创建人：</td>
                    <td><%=dto.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=dto.getCreationDate()%>
                    </td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="5"><textarea name="remark" rows="3" cols="" style="width:100%" class="blueBorder"><%=dto.getRemark()%></textarea></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>

	<div id="aa" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:144px;left:0px;width:100%">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
				<td width="2%" align="center"><input type="checkbox" class="headCheckbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
				<td width="5%" align="center">可用数量</td>
				<td width="5%" align="center">调拨数量</td>
			</tr>
		</table>
	</div>
	<div id="bb" style="overflow:scroll;height:500px;width:100%;position:absolute;top:168px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
	if (rows != null && !rows.isEmpty()) {
	Row row = null;
	String readOnlyProp = "";
	if(!sectionRight.equals("OUT")){
		readOnlyProp = " readonly";
	}
	for (int i = 0; i < rows.getSize(); i++) {
		row = rows.getRow(i);
%>
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
                <td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" readonly value="<%=row.getValue("ITEM_NAME")%>" class="finput"></td>
                <td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" readonly value="<%=row.getValue("ITEM_SPEC")%>" class="finput"></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" readonly value="<%=row.getValue("ITEM_CATEGORY")%>" class="finput"></td>
				<td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" readonly value="<%=row.getValue("SPARE_USAGE")%>" class="finput"></td>
                <td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" readonly value="<%=row.getValue("VENDOR_NAME")%>" class="finput"></td>
				<td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" readonly value="<%=row.getValue("ONHAND_QTY")%>" class="finput"></td>
                <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" readonly value="<%=row.getValue("QUANTITY")%>" class="finput"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">
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
</body>
<script type="text/javascript">

    function do_Approve(flag) {
		addApproveContent(flag);
        var paramObj = new Object();
        paramObj.orgId = "<%=userAccount.getOrganizationId()%>";
        paramObj.useId = "<%=userAccount.getUserId()%>";
        paramObj.groupId = "";
        paramObj.procdureName = "备件调拨流程";
        paramObj.flowCode = flag;
        paramObj.submitH = "submitH()";
        mainForm.act.value = "<%=WebActionConstant.APPROVE_ACTION%>";
		if(flag == 9){
			assign(paramObj);
		} else {
			mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
			if(confirm("确定要退回调拨申请吗？继续请点击“确定”按钮，否则请点击“取消”按钮")){
				submitH();
			}
		}
    }

    function submitH() {
        mainForm.submit();
    }









    function do_Print() {
        var headerId=mainForm.transId.value;
//        var url= "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet?act=print&transId="+headerId;
        var url= "/servlet/com.sino.ams.spare.servlet.BjdbServlet?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }

    function initPage() {
		do_SetPageWidth();
        doLoad();
        HideSinoButton(1);
        HideSinoButton(3);
        HideSinoButton(8);
        <%
        if (dto.getAttribute1().equals("PRINT")) {
        %>
        ShowSinoButton(15);
        <%
        }
        %>
    }

    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=userAccount.getOrganizationId()%>");
        if (projects) {
            mainForm.toObjectName.value = projects[0].workorderObjectName;
            mainForm.toObjectNo.value = projects[0].workorderObjectNo;
            mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
        }
    }
    function do_SelectItem() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_DB%>";
        var dialogWidth = 65;
        var dialogHeight = 30;
        var userPara = "organizationId=" + mainForm.fromOrganizationId.value;

        //LOOKUP传参数 必须和DTO中一致
        var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);

        if (assets) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < assets.length; i++) {
                data = assets[i];
                if (!isItemExist(data)) {
                    appendDTORow(tab, data);
                }
            }
        }
    }
    function isItemExist(itemObj) {
        var retVal = false;
        var tab = document.getElementById("dataTable");
        if (tab.rows) {
            var trObjs = tab.rows;
            var trCount = trObjs.length;
            var trObj = null;
            var itemValue = itemObj.barcode;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                rowValue = trObj.cells[1].childNodes[0].value;
                if (itemValue == rowValue) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }
    function validateData() {
        var validate = false;
        var fieldNames = "quantity";
        var fieldLabels = "数量";
        var validateType = EMPTY_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
        if (validate) {
            validateType = POSITIVE_INT_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }

    function do_Complete_app_yy() {
        document.mainForm.act.value = "<%=WebActionConstant.APPROVE_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainForm.submit();
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    }
</script>
</html>