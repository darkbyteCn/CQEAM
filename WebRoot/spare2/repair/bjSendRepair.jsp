<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.spare2.bean.SpareLookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  User: yuyao
  Date: 2007-11-12
  Time: 9:23:08
--%>
<html>
<head><title>备件送修</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

</head>
<body leftmargin="1" topmargin="1">
<jsp:include page="/message/MessageProcess"/>

<%
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.repair.servlet.BjSendRepairServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<%--<input type="hidden" name="flowSaveType" value="<%=%>">--%>
<%--<input type="hidden" name="distributeGroupId" value="<%=%>">--%>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr>
                    <td width="10%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=amsItemTransH.getTransNo()%>"></td>
                    <td width="10%" align="right">创建人：</td>
                    <td width="20%"><input type="text" name="createdUser" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=amsItemTransH.getCreatedUser()%>"></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="20%"><input type="text" name="creationDate" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=amsItemTransH.getCreationDate()%>"></td>
                </tr>
                <tr>
                    <td align="right">仓库：</td>
                    <td><input type="text" name="fromObjectName" class="blueborderYellow" readonly
                               style="width:100%"
                               value="<%=amsItemTransH.getFromObjectName()%>"></td>
                    <td><a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a></td>
                    <td></td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" class="blueborderGray" readonly
                               style="width:100%"
                               value="<%=amsItemTransH.getTransStatusName()%>"></td>
                </tr>
                <tr>
                    <td align="right"> 维修公司：</td>
                    <td><input type="text" name="company" class="blueborder"
                               style="width:100%"
                               value="<%=amsItemTransH.getCompany()%>"></td>
                    <td align="right">联系人：</td>
                    <td><input type="text" name="contact" class="blueborder" style="width:100%"
                               value="<%=amsItemTransH.getContact()%>"></td>

                    <td align="right">联系电话：</td>
                    <td><input type="text" name="tel" class="blueborder" style="width:100%"
                               value="<%=amsItemTransH.getTel()%>"></td>
                </tr>
                <tr>
                    <td align="right">传真：</td>
                    <td><input type="text" name="fax" class="blueborder"
                               style="width:100%"
                               value="<%=amsItemTransH.getFax()%>"></td>
                    <td align="right">委托书编号：</td>
                    <td><input type="text" name="attribute1" class="blueborder" style="width:100%"
                               value="<%=amsItemTransH.getAttribute1()%>"></td>
                    <td align="right">保值金额：</td>
                    <td><input type="text" name="attribute3" class="blueborder"
                               style="width:100%"
                               value="<%=amsItemTransH.getAttribute3()%>"></td>
                </tr>
                <tr>
                    <td align="right">承运人：</td>
                    <td><input type="text" name="attribute2" class="blueborder"
                               style="width:100%"
                               value="<%=amsItemTransH.getAttribute2()%>"></td>
                    <td align="right">联系地址：</td>
                    <td colspan="3"><input type="text" name="address" class="blueborder"
                                           style="width:100%"
                                           value="<%=amsItemTransH.getAddress()%>"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if (!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED) && amsItemTransH.getCreatedBy().equals(sfUser.getUserId())) {
        %>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行"
             onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <img src="/images/eam_images/save.jpg" alt="保存" id="img3" onClick="do_Save();">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_submit();">
        <%
            }
            if(!amsItemTransH.getTransId().equals("")){
        %>
        <img src="/images/eam_images/print_view.jpg" alt="显示打印页面" onclick="do_print()">
        <%
            }
        %>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "物料编码", "设备名称", "规格型号", "待修数量", "送修数量");
        var widthArr = new Array("2%", "12%", "15%", "15%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%"><input type="text" name="barcode" id="barcode0" class="noborderGray" style="width:100%">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <!--<td width="15%" name="objectName" id="objectName0"></td>-->
                <td width="8%"><input type="text" name="disrepairQuantity" id="disrepairQuantity0" value=""
                                      class="noborderGray" style="width:100%;text-align:RIGHT"></td>
                <td width="8%"><input type="text" name="quantity" id="quantity0" value="" onblur="checkQty(this);"
                                      class="blueborderYellow" style="width:100%;text-align:RIGHT"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
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

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%"><input type="text" name="barcode" class="noborderGray" style="width:100%"
                                       value="<%=row.getValue("BARCODE")%>" id="barcode<%=i%>"></td>
                <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%"><input type="text" name="disrepairQuantity" id="disrepairQuantity<%=i%>"
                                      value="<%=row.getValue("DISREPAIR_QUANTITY")%>"
                                      readonly class="noborderGray" style="width:100%;text-align:RIGHT">
                </td>
                <td width="8%"><input type="text" name="quantity" id="quantity<%=i%>"
                                      value="<%=row.getValue("QUANTITY")%>" onblur="checkQty(this);"
                                       class="blueborderYellow" style="width:100%;text-align:RIGHT">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="objectCategory" value="73">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
</form>
</body>
</html>
<script type="text/javascript">
    function do_Save() {
        if (mainForm.barcode.value == "") {
            alert("请确认数据!");
        } else {
            mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            mainForm.submit();
        }
    }
    function do_submit() {
        if(validateData()){
            mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            mainForm.submit();
        }
    }

    function validateData(){
        var validate = false;
        var fieldNames = "quantity";
        var fieldLabels = "送修数量";
        var validateType = EMPTY_VALIDATE
        validate = formValidate(fieldNames, fieldLabels, validateType);
        if (validate) {
            validateType = POSITIVE_INT_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }
    function do_SelectObject() {
        var projects = getSpareLookUpValues("<%=SpareLookUpConstant.OBJECT_NO%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
        if (projects) {
            //            dto2Frm(projects[0], "form1");
            document.mainForm.fromObjectName.value = projects[0].workorderObjectName;
            document.mainForm.fromObjectNo.value = projects[0].workorderObjectNo;
            //            document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
        }
    }
    function do_selectName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BF%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        //        lookProp.setMultipleChose(false);
        var userPara = "objectCategory=" + mainForm.objectCategory.value;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                mainForm.toObjectName.value = users[0].workorderObjectName;
                mainForm.toObjectNo.value = users[0].workorderObjectNo;
                mainForm.addressId.value = users[0].addressId;
            }
        }
    }
    function do_SelectItem() {
        //检查是否已选仓库
        var fromObjectNo = document.mainForm.fromObjectNo.value;
        if (fromObjectNo == "") {
            alert("请先选择一个仓库!");
            return;
        }
        var items = getLookUpValues("<%=LookUpConstant.BJSX_ITEM_INFO3%>", 51, 33, "objectNo=" + fromObjectNo);
        if (items) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < items.length; i++) {
                data = items[i];
                if(!isItemExist(data))   {
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
    function do_print() {
        var url = "/servlet/com.sino.ams.spare2.repair.servlet.BjSendRepairServlet?act=print&transId=" + mainForm.transId.value;
        //        var popscript = "center:yes;dialogwidth:1020px;dialogheight:700px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, null, popscript);
    }
    <%--  function do_submit() {
        mainForm.flowSaveType.value="<%=DictConstant.FLOW_COMPLETE%>";
        var paramObj = new Object();
        paramObj.orgId = "<%=sfUser.getOrganizationId()%>";
        paramObj.useId = "<%=sfUser.getUserId()%>";
        paramObj.groupId = mainForm.distributeGroupId.value;
        paramObj.procdureName = "<%=AmsFlowConstant.NET_NEW%>";
        paramObj.flowCode = "";
        paramObj.submitH = "do_Save()";
        assign(paramObj);
    }--%>
    function checkQty(obj) {
        var val = obj.value;
        if (val != ""){
            if( isNaN(val)) {
                alert("请输入数字！");
                obj.focus();
            }
            if(Number(val) <= 0){
                alert("请输入大于0的正整数！");
                obj.focus();
            }else{
                var tempId = obj.id.substring(8,obj.id.length);
                var disrepairQuantity = document.getElementById("disrepairQuantity"+tempId).value;
                if(Number(val) > Number(disrepairQuantity)){
                    alert("送修数量不能大于待修数量，请重新输入！");
                    obj.focus();
                }
            }
        }
    }
</script>