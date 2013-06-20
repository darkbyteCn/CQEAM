<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-18
  Function:返修申请（NEW）
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.constant.SparePROCConstant" %>
<%@ page import="com.sino.ams.spare.constant.SpareWebAction" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/spare/headerInclude.htm"%>
<html>
<head><title>备件返修申请（NEW）</title>
</head>
<body leftmargin="1" topmargin="1">

<%
    AmsItemTransHDTO itemTransHDTO = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String taskProp= StrUtil.nullToString(request.getParameter("taskProp"));
    boolean isFirstNode=taskProp.equals("")||taskProp.equals(FlowConstant.TASK_PROP_START);
    String sectionRight= StrUtil.nullToString(request.getParameter("sectionRight"));
    
%>
<form name="mainFrm" action="/servlet/com.sino.ams.spare.servlet.RepairBackServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>

<input type="hidden" name="act" value="">
<input type="hidden" name="flowAct" value="">
<input type="hidden" name="procName" value="<%=SparePROCConstant.REPAIRE_BACK_PROC%>">
<input type="hidden" name="transId" value="<%=itemTransHDTO.getTransId()%>">
<input type="hidden" name="transType" value="<%=itemTransHDTO.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=itemTransHDTO.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=itemTransHDTO.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=itemTransHDTO.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=itemTransHDTO.getFromOrganizationId()%>">
<input type="hidden" name="groupId" value="<%=user.getCurrGroupId()%>">
<input type="hidden" name="fromDept" value="<%=itemTransHDTO.getFromDept()%>">
<table border="1"  bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%">
                        <input type="text" name="transNo" value="<%=itemTransHDTO.getTransNo()%>" readonly style="width:80%" class="blueborderGray">
                    </td>
                    <td width="9%" align="right">申请公司：</td>
                    <td width="25%">
                        <input type="text" name="fromOrganizationName" value="<%=itemTransHDTO.getFromOrganizationName()%>" class="blueborderGray">
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">申请人：</td>
                    <td>
                        <input type="text" name="createdUser" value="<%=itemTransHDTO.getCreatedUser()%>" readonly   style="width:80%" class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly value="<%=itemTransHDTO.getCreationDate().getSimpleDate()%>" class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td colspan="2">
						<input type="text" name="transStatusName" readonly value="<%=itemTransHDTO.getTransStatusName()%>" class="blueborderGray">
					</td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="11">
						<textarea name="remark" rows="3" cols="" style="width:90%" class="blueBorder"><%=itemTransHDTO.getRemark()%></textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
<legend>
    <%if(isFirstNode){%>
    <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
    <img src="/images/button/deleteLine.gif" alt="删除行"  onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <%--<img src="/images/button/pasteData.gif" alt="粘贴" onclick="do_PasteExcelData();">--%>
    <%}%>
    <%--<img src="/images/button/saveTemp.gif" alt="保存" id="img3" onClick="do_save();">--%>
    <%if(isFirstNode&&!itemTransHDTO.getTransId().equals("")){%>
    <img src="/images/button/repeal.gif" alt="撤消" onClick="do_cancelOrder();">
    <%}%>
    <img src="/images/button/submit.gif" alt="提交" id="img4" onClick="do_submit();">
    <%if(!isFirstNode){%>
    <img src="/images/button/reject.gif" alt="退回" id="img4" onClick="do_back();">
    <img src="/images/button/viewFlow.gif" alt="查看流程" id="img5" onClick="viewFlow();">
    <%
        }
        if(sectionRight.equals("PRINT")){
    %>
    <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
    <%}%>
    <img src="/images/button/viewOpinion.gif" alt="查阅意见" onClick="viewOpinion();">

    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
</legend>
<div id="headDiv" style="overflow:hidden;position:absolute;top:128px;left:1px;width:1257px">
    <table class="headerTable" border="1" width="100%">
        <tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td width="4%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
            <td width="10%" align="center">设备名称</td>
            <td width="15%" align="center">设备型号</td>
            <td width="10%" align="center">设备类型</td>
            <td width="10%" align="center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="10%" align="center">故障地点</td>
            <td width="5%" align="center">返修数量</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:500px;width:1275px;position:absolute;top:150px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#666666" id="dataTable" cellpadding="1" cellspacing="0" style="table-layout:fixed;word-break:break-all">
        <%
            RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
            if (rows == null || rows.isEmpty()) {
                int k = 0;
        %>
        <tr id="mainTr0" style="display:none" onMouseMove="this.style.backgroundColor='#EFEFEF'" onMouseOut="this.style.backgroundColor='#FFFFFF'">
            <td width="4%" align="center">
				<input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%"><input readonly  style="width:100%" class="noneborderInput" name="itemName" id="itemName0"></td>
            <td width="15%"><input readonly  style="width:100%" class="noneborderInput" name="itemSpec" id="itemSpec0"></td>
            <td width="10%"><input readonly  style="width:100%" class="noneborderInput" name="itemCategory" id="itemCategory0"></td>
            <td width="10%"><input readonly  style="width:100%" class="noneborderInput" name="spareUsage" id="spareUsage0"></td>
            <td width="10%"><input readonly  style="width:100%" class="noneborderInput" name="vendorName" id="vendorName0"></td>
            <td width="10%">
				<input type="text" name="troubleLoc" id="troubleLoc0" value="" class="blueBorder" style="width:100%;text-align:left">
            </td>
            <td width="5%" align="center">
				<input type="text" name="quantity" id="quantity0" value="" class="blueborderYellow" onblur="validateData2(this);" style="width:100%">
            </td>
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId0" value="">
                <input type="hidden" name="onhandQty" id="onhandQty0" value="">
                <input type="hidden" name="serialNo" id="serialNo0" value="">
                <input type="hidden" name="barcode" id="barcode0" value="">
            </td>
        </tr>
        <%
        } else {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr id="mainTr<%=i%>" onMouseMove="this.style.backgroundColor='#EFEFEF'"  onMouseOut="this.style.backgroundColor='#FFFFFF'">
            <td width="4%" align="center">
				<input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0">
            </td>
            <td width="10%"><input class="noneborderInput" name="itemName" id="itemName<%=i%>" value="<%=row.getValue("ITEM_NAME")%>"> </td>
            <td width="15%"><input class="noneborderInput" name="itemSpec" id="itemSpec<%=i%>" value="<%=row.getValue("ITEM_SPEC")%>"> </td>
            <td width="10%"><input class="noneborderInput" name="itemCategory" id="itemCategory<%=i%>" value="<%=row.getValue("ITEM_CATEGORY")%>"> </td>
            <td width="10%"><input class="noneborderInput" name="spareUsage" id="spareUsage<%=i%>" value="<%=row.getValue("SPARE_USAGE")%>"> </td>
            <td width="10%"><input class="noneborderInput" name="vendorName" id="vendorName<%=i%>" value="<%=row.getValue("VENDOR_NAME")%>"> </td>
            <td width="10%" align="center">
				<input type="text" name="troubleLoc" id="troubleLoc<%=i%>" value="<%=row.getValue("TROUBLE_LOC")%>" class="blueBorder" style="width:100%;text-align:left">
			</td>
            <td width="5%" align="center">
                <input type="text" name="quantity" id="quantity<%=i%>"  value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" onblur="validateData2(this);" style="width:100%;text-align:right">
            </td>
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                <input type="hidden" name="onhandQty" id="onhandQty<%=i%>" value="<%=row.getValue("QUANTITY")%>">
                <input type="hidden" name="serialNo" id="serialNo<%=i%>" value="<%=row.getValue("SERIAL_NO")%>">
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
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">

    function do_save(){
        document.mainFrm.act.value="<%=WebActionConstant.SAVE_ACTION%>";
        document.mainFrm.flowAct.value="<%=DictConstant.FLOW_SAVE%>";
        document.mainFrm.submit();
    }
    
    function do_back(){
        document.mainFrm.act.value="<%=WebActionConstant.REJECT_ACTION%>";
        document.mainFrm.submit();
    }

    function do_SelectItem() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_FXSQ%>";
        var dialogWidth = 65;
        var dialogHeight = 30;
        var userPara = "organizationId=" +<%=user.getOrganizationId()%>;

        var retValues = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);

        if (retValues) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < retValues.length; i++) {
                data = retValues[i];
    //                appendDTO2Table(tab, data, false, "barcode");
                appendDTORow(tab,data);
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

    function getvalues() {
        var tab = document.getElementById("dataTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
    }

    function do_submit() {
        var paramObj = new Object();
        paramObj.orgId = "<%=itemTransHDTO.getFromOrganizationId()%>";
        paramObj.useId = "<%=itemTransHDTO.getCreatedBy()%>";
        paramObj.groupId = document.mainFrm.fromDept.value;
        paramObj.procdureName ="<%=SparePROCConstant.REPAIRE_BACK_PROC%>";
//        paramObj.flowCode = flowCode;
        paramObj.submitH = "submitH()";
        assign(paramObj);
    }

    function submitH() {
        document.mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        document.mainFrm.flowAct.value="<%=DictConstant.FLOW_COMPLETE%>"
        document.mainFrm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainFrm.submit();
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

    function validateData2(obj) {
        var validate = false;
        var fieldNames = "quantity";
        var fieldLabels = "数量";
        var validateType = POSITIVE_INT_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
        do_change(obj);
        return validate;
    }

    function do_change(obj) {
        var id = obj.id.substring(8, obj.id.length);
        var qtyObj = document.getElementById("quantity" + id);
        var onhandQty = document.getElementById("onhandQty" + id).value;
        if (Number(qtyObj.value) > Number(onhandQty)) {
            alert("返修数不能大于待修数量，请重新输入！");
            qtyObj.focus();
        }
    }

    function do_print() {
        var headerId = document.mainFrm.transId.value;
        var url = "/servlet/com.sino.ams.spare.servlet.SpareDiffServlet?act=print&transId=" + headerId;
        var style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }
    
    function do_PasteExcelData() {
        var headerTable = document.getElementById("headerTable");
        var dataTable = document.getElementById("dataTable");
        pasteData(headerTable, dataTable);
    }

function do_cancelOrder() {
	if(confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")){
		document.mainFrm.act.value = "<%=SpareWebAction.CANCEL_ACTION%>";
		document.mainFrm.submit();
	}
}
</script>
</html>