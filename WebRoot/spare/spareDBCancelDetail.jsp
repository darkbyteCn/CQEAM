<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemAllocateHDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%
    AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String typed = amsItemTransH.getTransStatus();
    String typeName = "";
    if (typed.equals("ALLOTING")) {
        typeName = "调出撤销";
    } else if (typed.equals("RECEIVING")) {
        typeName = "调入确认";
    }
%>
<html>
<head><title><%=typeName%>
</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1">
<form name="mainForm" <%if (typed.equals("ALLOTING")) {%>
      action="/servlet/com.sino.ams.spare.servlet.SpareDBCancel"<%} else if (typed.equals("RECEIVING")) {%>
      action="/servlet/com.sino.ams.spare.servlet.BjInConfirmedServlet"<%}%> method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="submitFlag" value="0">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>" readonly
                                           style="width:80%"
                                           class="blueborderGray">
                    </td>
                    <%if (typed.equals("ALLOTING")) {%>
                    <td width="9%" align="right">调入公司：</td>
                    <td width="25%"><input type="text" name="toOrganizationName"
                                           value="<%=amsItemTransH.getToOrganizationName()%>"
                                           class="blueborderGray">
                    </td>
                    <%} else if (typed.equals("RECEIVING")) {%>
                    <td width="9%" align="right">调出公司：</td>
                    <td width="25%"><input type="text" name="fromOrganizationName"
                                           value="<%=amsItemTransH.getFromOrganizationName()%>"
                                           class="blueborderGray">
                    </td>
                    <%}%>
                </tr>
                <tr height="22">
                    <td align="right">申请人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                               readonly style="width:80%"
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly
                               value="<%=amsItemTransH.getCreationDate()%>"
                               class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td colspan="2"><input type="text" name="transStatusName" readonly
                                           value="<%=amsItemTransH.getTransStatusName()%>"
                                           class="blueborderGray"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="11"><textarea name="remark" rows="3" cols="" style="width:90%"
                                               class="blueBorder"><%=amsItemTransH.getRemark()%>
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
            if (amsItemTransH.getTransStatus().equals("ALLOTING")) {
        %>
        <img src="/images/eam_images/revoke.jpg" alt="调出撤销" onClick="do_cancel();">
        <%} else if (amsItemTransH.getTransStatus().equals("RECEIVING")) {%>
        <img src="/images/eam_images/ok.jpg" alt="调入确认" onClick="do_in();">
        <%
            }%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
        <%if(!StrUtil.isEmpty(amsItemTransH.getTransNo())){%>
          <img src="/images/eam_images/print.jpg" alt="打印页面" onclick="do_print();">
        <% }
        %>
    </legend>

    <%if (typed.equals("ALLOTING")) {%>
    <script type="text/javascript">
        var columnArr = new Array("设备名称", "设备型号", "设备类型", "用途", "厂商", "调出数量");
        var widthArr = new Array("10%", "15%", "10%", "10%", "10%", "5%");
        printTableHead(columnArr, widthArr);
    </script>

    <%} else if (typed.equals("RECEIVING")) {%>
    <script type="text/javascript">
        var columnArr = new Array("设备名称", "设备型号", "设备类型", "用途", "厂商", "调入数量");
        var widthArr = new Array("10%", "15%", "10%", "10%", "10%", "5%");
        printTableHead(columnArr, widthArr);
    </script>
    <%}%>
    <div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="10%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="10%" name="itemCategory" id="itemCategory0"></td>
                <td width="10%" name="spareUsage" id="spareUsage0"></td>
                <td width="10%" name="vendorName" id="vendorName0"></td>
                <td width="5%" align="center"><input type="text" name="quantity" id="quantity0" value="" class="blueborderYellow" readonly style="width:100%">
                </td>
                <td style="display:none">
                    <input type="hidden" name="detailId" id="detailId0" value="">
                    <input type="hidden" name="barcode" id="barcode0" value="">
                </td>
            </tr>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="10%" name="itemCategory" id="itemCategory<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td width="10%" name="spareUsage" id="spareUsage<%=i%>"><%=row.getValue("SPARE_USAGE")%>
                </td>
                <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td width="5%" align="center"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow" readonly style="width:100%;text-align:right">
                </td>
                <td style="display:none">
                    <input type="hidden" name="barcode" id="barcode1<%=i%>" value="<%=row.getValue("BARCODE")%>" >
                    <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">
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
function do_SelectObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
function do_SelectItem() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_FXSQ%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userPara = "organizationId=" +<%=user.getOrganizationId()%>;
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
function do_edit() {
    var va = false;
    var tab = document.getElementById("dataTable");
    var trcount;
    if (mainForm.transStatus.value == "SAVE_TEMP") {
        trcount = tab.rows.length
        for (var i = 0; i < trcount; i++) {
            var onqty = document.getElementById("onhandQty" + i).value;
            var qty = document.getElementById("quantity" + i).value
            alert(qty)
            alert(onqty)
            if (qty > onqty) {
                alert("请确认返修数量!");
                return va;
            } else {
                va = true;
                return va;
            }
        }
    }
    else
    {
        trcount = tab.rows.length + 1;
        for (var i = 1; i < trcount; i++) {
            var onqty = document.getElementById("onhandQty" + i).value;
            var qty = document.getElementById("quantity" + i).value
            alert(qty)
            alert(onqty)
            if (qty > onqty) {
                alert("请确认返修数量!");
                return;
            } else {
                va = true;
            }
        }
    }

    return va;
}
function do_SavePo(flag) {
    if (validateData()) {
        if (flag == 1) {
            document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            document.mainForm.submit();
        } else {
            var orgId = "<%=amsItemTransH.getFromOrganizationId()%>";
            var userId = "<%=amsItemTransH.getCreatedBy()%>";
            var groupId = document.mainForm.groupId.value;
            var procdureName = "备件返修申请流程";
            var flowCode = "";
            var paramObj = new Object();
            paramObj.orgId = orgId;
            paramObj.useId = userId;
            paramObj.groupId = groupId;
            paramObj.procdureName = procdureName;
            paramObj.flowCode = flowCode;
            paramObj.submitH = "submitH()";
            assign(paramObj);
        }
    }
}

function submitH() {
    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
    document.mainForm.submit();
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
function do_cancel() {
    if(confirm("确定要撤销吗？")){
        if (document.mainForm.submitFlag.value == "1") {
            alert("正在进行操作，请等待。");
            return;
        }
        document.mainForm.submitFlag.value="1";
        mainForm.act.value = "CANCEL";
        mainForm.submit();
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    }

}
function do_in() {
    mainForm.act.value = "OUT";
    mainForm.submit();
}
function do_print() {
      var headerId=document.mainForm.transId.value;
        var url="/servlet/com.sino.ams.spare.servlet.SpareDBCancel?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }
</script>
</html>