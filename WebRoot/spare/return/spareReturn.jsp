<%--
  User: zhoujs
  Date: 2008-3-23 21:17:41
  Function:送修返还单界面
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>

<%@ include file="/spare/headerInclude.htm"%>

<html>
<head><title>备件送修返还</title>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
    boolean isCompleted= dto.getTransStatus().equals("COMPLETED");
%>
<body topMargin=1 leftMargin=1 onload="initPage();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form action="/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet" name="mainForm" method="post">
<table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="24">
                    <td width="10%" align="right">单据号：</td>
                    <td width="23%"><input type="text" name="transNo" class="blueborderGray" readonly style="width:100%" value="<%=dto.getTransNo()%>"></td>
                    <td width="10%" align="right">来源库存：</td>
                    <td width="23%"><input type="text" name="fromObjectName" class="blueborderYellow" style="width:88%" readonly value="<%=dto.getFromObjectName()%>"><a href="#" class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a></td>
                    <td width="10%" align="right">目的库存：</td>
                    <td width="23%"><input type="text" name="toObjectName" class="blueborderYellow" style="width:88%" readonly value="<%=dto.getToObjectName()%>"><a href="#" class="linka" style="cursor:'hand'" onclick="do_selectToName();">[…]</a>
                    </td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">入库原因：</td>
                    <td width="23%"><input type="text" name="reason" class="blueborder" value="<%=dto.getReason()%>" style="width:100%"></td>
                    <td width="10%" align="right">创建人：</td>
                    <td width="23%"><input type="text" name="createdUser" class="blueborderGray" readonly value="<%=dto.getCreatedUser()%>" style="width:100%"></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="23%"><input type="text" name="creationDate" class="blueborderGray" readonly value="<%=dto.getCreationDate()%>" style="width:100%"></td>
                </tr>
                <tr>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="23%"><input type="text" name="transStatusName" class="blueborderGray" readonly value="<%=dto.getTransStatusName()%>" style="width:100%"></td>
                    <td width="10%" align="right">厂商：</td>
                    <td width="23%"><select name="vendorId" class="blueborderYellow" style="width:100%" onChange="do_Change()"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>
                    <td width="10%" align="right">备注：</td>
                    <td width="23%"><input type="text" name="remark" class="blueborder" style="width:100%"  value="<%=dto.getRemark()%>"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if (!dto.getTransStatus().equals(DictConstant.COMPLETED) && dto.getCreatedBy().equals(userAccount.getUserId())) {
        %>
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_add();">
        <img src="/images/button/deleteLine.gif" alt="删除行" onClick="do_Delete();">
        <%--<img src="/images/button/save.gif" alt="保存" onClick="do_save()">--%>
        <img src="/images/button/ok.gif" alt="确定" onClick="do_ok()">
        <%
            }
        %>
               <%if(dto.getTransStatus().equals(DictConstant.COMPLETED)){%>
    <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
     <%}%>
        <img src="/images/button/close.gif" alt="关闭" onclick="window.close()">
    </legend>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:105px;left:1px;width:990px">
		<table class="headerTable" border="1" width="100%">
			<tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
				<td width="3%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
				<td width="10%" align="center">设备名称</td>
				<td width="15%" align="center">设备型号</td>
				<td width="10%" align="center">设备类型</td>
				<td width="10%" align="center">用途</td>
				<td width="10%" align="center">厂商</td>
                <%if (!isCompleted) {%>
                <td width="5%" align="center">送修数量</td>
                <%}%>
                <td width="5%" align="center">返还数量</td>
				<td width="10%" align="center">备注</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:500px;width:1007px;position:absolute;top:128px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (set == null || set.isEmpty()) {
%>
             <tr id="mainTr0" style="display:none">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" style="height:20px;margin:0;padding:0"></td>
				<td width="10%"><input type="text" name="itemName" id="itemName0" value="" class="finput" readonly></td>
				<td width="15%"><input type="text" name="itemSpec" id="itemSpec0" value="" class="finput" readonly></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory0" value="" class="finput" readonly></td>
                <td width="10%"><input type="text" name="spareUsage" id="spareUsage0" value="" class="finput" readonly></td>
				<td width="10%"><input type="text" name="vendorName" id="vendorName0" value="" class="finput" readonly></td>
                 <%if(!isCompleted){%>
                <td width="5%"><input type="text" name="onhandQty" id="onhandQty0" readonly value="" class="finput3"></td>
                 <%}%>
                <td width="5%"><input type="text" name="quantity" id="quantity0" onblur="checkQty(this);" value="" class="finputNoEmpty3"></td>
                <td width="10%"><input type="text" name="remarkl" id="remarkl0" value="" class="finput"></td>
                <td style="display:none">
                    <input type="hidden" name="barcode" id="barcode0" value="">
                </td>
            </tr>
<%
	} else {
		AmsItemTransLDTO dto1 = null;
		for (int i = 0; i < set.getSize(); i++) {
			dto1 = (AmsItemTransLDTO) set.getDTO(i);
%>
            <tr id="mainTr<%=i%>">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" style="height:20px;margin:0;padding:0"></td>
                <td width="10%"><input type="text" name="itemName" id="itemName<%=i%>" value="<%=dto1.getItemName()%>" class="finput" readonly></td>
				<td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" value="<%=dto1.getItemSpec()%>" class="finput" readonly></td>
                <td width="10%"><input type="text" name="itemCategory" id="itemCategory<%=i%>" value="<%=dto1.getItemCategory()%>" class="finput" readonly></td>
                <td width="10%"><input type="text" name="spareUsage" id="spareUsage<%=i%>" value="<%=dto1.getSpareUsage()%>" class="finput" readonly></td>
				<td width="10%"><input type="text" name="vendorName" id="vendorName<%=i%>" value="<%=dto1.getVendorName()%>" class="finput" readonly></td>
                <%--<td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" value="<%=dto1.getDxkQty()%>" class="finput3" readonly></td>--%>
                <%if(!isCompleted){%>
                <td width="5%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" value="<%=dto1.getQuantity()%>" class="finput3" readonly></td>
                <%}%>

                <td width="5%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=dto1.getQuantity()%>" class="finputNoEmpty3" readonly  onblur="checkQty(this);"></td>
                <td width="10%"><input type="text" name="remarkl" id="remarkl<%=i%>" value="<%=dto1.getRemark()%>" class="finput" readonly></td>
                <td style="display:none">
                    <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=dto1.getBarcode()%>">
                </td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>
</fieldset>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="fromObjectNo" value="<%=dto.getToObjectNo()%>">
<!--<input type="hidden" name="objectNo" value="">-->
<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
<input type="hidden" name="fromOrgnizationId" value="<%=dto.getFromOrganizationId()%>">
<input type="hidden" name="submitFlag" value="0">
<!--<input type="hidden" name="objectCategory" value="73">-->
</form>
</body>
</html>
<script type="text/javascript">
    <%--function do_add() {--%>
        <%--<%--var lookUpName = "<%=LookUpConstant.LOOK_UP_SPARE%>";--%>
        <%--<%--var dialogWidth = 50;--%>
        <%--<%--var dialogHeight = 30;--%>
        <%--<%--//LOOKUP传参数 必须和DTO中一致--%>
        <%--<%--var userPara="&orgnizationId="+mainForm.fromOrgnizationId+"&object_no="+mainForm.fromObjectNo;--%>
        <%--<%--var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);--%>
         <%--var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=%>&orgnizationId="+mainForm.fromOrgnizationId.value+"&objectCategory="+mainForm.objectCategory.value;--%>
    <%--var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";--%>
    <%--/*   window.open(url);*/--%>
    <%--var assets = window.showModalDialog(url, null, popscript);--%>
        <%--var tab = document.getElementById("dataTable")--%>
        <%--if (users) {--%>
            <%--var user = null;--%>
            <%--for (var i = 0; i < users.length; i++) {--%>
                <%--user = users[i];--%>
                <%--//if (!isItemExist(user)) {--%>
                <%--appendDTORow(tab, user);--%>
                <%--//增加整行信息时候用到的方法}--%>
                <%--//                }--%>
            <%--}--%>
        <%--}--%>
    <%--}--%>
function initPage() {
    do_SetPageWidth();
}


    function do_add() {
        var fromObjectNo = document.mainForm.fromObjectNo.value;
        var vendorId = mainForm.vendorId.value;
        var toObjectNo = mainForm.toObjectNo.value;
        if(fromObjectNo == ""){
            alert("请先选择来源仓库!");
            return;
        }
        if(toObjectNo == ""){
            alert("请先选择目的仓库!");
            return;
        }
        if (vendorId == "") {
            alert("请选择厂商!");
            document.mainForm.vendorId.focus();
            return ;
        }
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_FXRK%>&organizationId=<%=userAccount.getOrganizationId()%>&objectNo="+fromObjectNo+"&vendorId="+vendorId;
        var popscript = "dialogWidth:65;dialogHeight:30;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
        var items = window.showModalDialog(url, null, popscript);
        if (items) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < items.length; i++) {
                data = items[i];
                if(!isItemExist(data)){
//                appendDTORow(tab, data);
                    appendDTO2Table(tab, data, false, "barcode");
                }
            }
        }
    }
    function isItemExist(itemObj) {
        var retVal = false;
        var tabObj = document.getElementById("dataTable");
        if (tabObj.rows) {
            var trObjs = tabObj.rows;
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
    function do_selectName() {
        if (document.mainForm.fromObjectNo.value != "") {
            var count=getCheckBoxCount("subCheck");
            if (count>0) {
                var tab = document.getElementById("dataTable");
                 if(!(count==1&&tab.rows[0].style.display == 'none')){
                     alert("请先删除数据，再重新选择仓库！");
                     return;
                 }
            }
        }
        var toObjectName = mainForm.toObjectName.value;
        if (toObjectName != "") {
            document.mainForm.toObjectNo.value = "";
            document.mainForm.toObjectName.value = "";
        }
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_SXFH%>&objectCategory=73";
        var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var users = window.showModalDialog(url, null, popscript);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                mainForm.fromObjectName.value = users[0].workorderObjectName;
                mainForm.fromObjectNo.value = users[0].workorderObjectNo;
//                mainForm.objectNo.value = users[0].workorderObjectNo;
            }
        }
    }

     function do_selectToName() {
        var fromObjectNo = document.mainForm.fromObjectNo.value;
        var fromObjectName = document.mainForm.fromObjectName.value;
        if (fromObjectNo == "") {
            alert("请先选择来源仓库！");
            return ;
        }
        if (fromObjectName == "省公司送修库") {
            var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BF%>&objectCategory=71";
            var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
            var users = window.showModalDialog(url, null, popscript);
            if (users) {
                var user = null;
                for (var i = 0; i < users.length; i++) {
                    mainForm.toObjectName.value = users[0].workorderObjectName;
                    mainForm.toObjectNo.value = users[0].workorderObjectNo;
    //                mainForm.objectNo.value = users[0].workorderObjectNo;
                }
            }
        } else {
            var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BF3%>";
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

    }

    function do_Delete() {
        var tab = document.getElementById("dataTable");
        deleteTableRow(tab, 'subCheck');
    }
    function do_save() {
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>";
        mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        mainForm.submit();
    }
    function do_ok() {
        var value1 = mainForm.toObjectName.value;
        if (value1 == "") {
            alert("请选择目的库存！");
        } else {
            if (getvalues()) {
                if (validateData()) {
                    if (document.mainForm.submitFlag.value == "1") {
                        alert("正在进行操作，请等待。");
                        return;
                    }
                    document.mainForm.submitFlag.value="1";
                    mainForm.transStatus.value = "<%=DictConstant.CREATE%>";
                    mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                    mainForm.submit();
                }
            }
        }
    }
    function getvalues() {
        var tab = document.getElementById("dataTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
    }

   function checkQty(obj){
        var id = obj.id.substring(8,obj.id.length);
        var qtyObj = document.getElementById("quantity"+id);
        var onhandQty = document.getElementById("onhandQty"+id).value;
        if(Number(qtyObj.value)>Number(onhandQty)){
            alert("返还数量不能大于送修数量，请重新输入！");
            qtyObj.focus();
        }
    }

    function validateData(){
        var validate = false;
        var fieldNames = "quantity";
        var fieldLabels = "返还数量";
        var validateType = EMPTY_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
        if(validate){
            validateType = POSITIVE_INT_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }

 function do_print() {
    var headerId=document.mainForm.transId.value;
    var url="/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet?act=print&transId="+headerId;
    var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
    window.open(url, "", style);
}

function do_Change() {
    var rows = dataTable.rows;
    var rowCount = rows.length;
    if (rowCount > 1 || (rowCount == 1 && rows[0].style.display != "none")) {
        alert("改变厂商将删除已选择的数据！");
        deleteRow(dataTable);
    }
}
</script>