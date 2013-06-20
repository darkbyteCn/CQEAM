<%--
  Created by HERRY.
  Date: 2008-8-7
  Time: 10:54:42
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.nm.spare2.bean.SpareLookUpConstant" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.nm.spare2.constant.SpareConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>

<html>
<head><title>备件子库转移</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/checkBarcode.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <style type="text/css">
        input {
            font-size: 11px
        }
    </style>
</head>
<body leftmargin="1" topmargin="1">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String flag = StrUtil.nullToString(request.getParameter("flag"));
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.BjInvTransServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="<%=flag%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
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
                    <td width="9%" align="right">源仓库：</td>
                    <td width="25%"><input type="text" name="fromObjectName"
                                           value="<%=amsItemTransH.getFromObjectName()%>" class="blueBorderYellow"
                                           style="width:80%">
                        <a href="#" id="objectSelecter" onClick="do_SelectFromObject();" title="点击选择仓库" class="linka">[…]</a>

                    </td>
                </tr>
                <tr>
                    <td width="9%" align="right">目的公司：</td>
                    <td width="25%"><input type="text" name="toOrganizationName"
                                           value="<%=amsItemTransH.getToOrganizationName()%>" class="blueBorderYellow"
                                           style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择目的公司和仓库" class="linka">[…]</a>
                    </td>
                    <td width="9%" align="right">目的仓库：</td>
                    <td width="25%"><input type="text" name="toObjectName" value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueBorderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择目的公司和仓库" class="linka">[…]</a>
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                               readonly style="width:80%"
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:60%"
                               value="<%=amsItemTransH.getCreationDate()%>"
                               class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" readonly
                               value="<%=amsItemTransH.getTransStatusName()%>"
                               class="blueborderGray"></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="7"><textarea name="remark" rows="3" cols="" style="width:90%"
                                              class="blueBorder"><%=amsItemTransH.getRemark()%></textarea>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<fieldset>
<legend>
    <%
        if (flag.equals("CONFIRM")&&amsItemTransH.getTransStatus().equals(SpareConstant.TO_CONFIRM)) {
    %>
    <img src="/images/button/confirm.gif" alt="确认" onclick="do_confirm();">
    <%
    }
        //单据非完成状态并且当前用户是创建人才有操作权限
        if (!amsItemTransH.getTransStatus().equals(SpareConstant.TO_CONFIRM) && amsItemTransH.getCreatedBy() == (user.getUserId())) {
    %>
    <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
    <img src="/images/button/deleteLine.gif" alt="删除行"
         onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_save(1);">
    <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_submit();">
    <%
        }

    %>

    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
</legend>

<script type="text/javascript">
    var columnArr = new Array("checkbox", "物料编码", "设备名称", "规格型号", "可用量", "转移数量");
    var widthArr = new Array("2%", "10%", "15%", "18%", "8%", "8%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
        <%if (rows == null || rows.isEmpty()) {%>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">

            <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                 style="height:20px;margin:0;padding:0">
            </td>

            <td width="10%"><input type="text" name="barcode" id="barcode0"
                                   value=""
                                   readonly class="noborderGray"
                                   style="width:100%;">
            </td>
            <td width="15%" name="itemName" id="itemName0"></td>
            <td width="18%" name="itemSpec" id="itemSpec0"></td>
            <td width="8%">
                <input type="text" name="onhandQty" id="onhandQty0" value="" readonly
                       style="width:100%;text-align:right;border:none">
            </td>
            <td width="8%">
                <input type="text" name="quantity" id="quantity0" value="" class="blueborderYellow"
                       onblur="checkQty(this);"
                       style="width:100%;text-align:right;border:none">
            </td>
            <td style="display:none"><input type="hidden" name="lineId" id="lineId0"
                                            value=""><input type="hidden" name="itemCode" id="itemCode0"
                                                            value="">
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

            <td width="10%"><input type="text" name="barcode"
                                   id="barcode<%=i%>"
                                   value="<%=row.getValue("BARCODE")%>"
                                   readonly class="noborderGray"
                                   style="width:100%">
            </td>
            <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="18%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
            </td>

            <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                                                 value="<%=row.getValue("ONHAND_QUANTITY")%>" class="noborderYellow"
                                                 style="width:100%;text-align:right">
            </td>

            <td width="8%">
                <input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>"
                       onblur="checkQty(this);"  <%if(!amsItemTransH.getTransStatus().equals(SpareConstant.SAVE_TEMP)){%>readonly<%}%>
                       style="width:100%;text-align:right;border:none">
            </td>

            <td style="display:none"><input type="hidden" name="lineId" id="lineId<%=i%>"
                                            value="<%=row.getValue("LINE_ID")%>"><input type="hidden"
                                                                                        name="itemCode"
                                                                                        id="itemCode<%=i%>"
                                                                                        value="<%=row.getValue("ITEM_CODE")%>">
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</fieldset>
<div id="showMsg" style="color:red">
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</div>
</form>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script type="text/javascript">
function do_SelectFromObject() {
    var projects = getSpareLookUpValues("<%=SpareLookUpConstant.OBJECT_NO%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        document.mainForm.fromObjectName.value = projects[0].workorderObjectName;
        document.mainForm.fromObjectNo.value = projects[0].workorderObjectNo;
        //            document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}

function do_SelectObject() {
    var projects = getSpareLookUpValues("<%=SpareLookUpConstant.OU_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        document.mainForm.toObjectName.value = projects[0].workorderObjectName;
        document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
        document.mainForm.toOrganizationId.value = projects[0].organizationId;
        document.mainForm.toOrganizationName.value = projects[0].company;
    }
}
function do_SelectItem() {
    var fromObjectNo = document.mainForm.fromObjectNo.value;
    if (fromObjectNo == "") {
        alert("请先选择仓库!");
        return;
    }
    var items = getSpareLookUpValues("<%=SpareLookUpConstant.BJCK_SPARE_INFO%>", 51, 33, "objectNo=" + fromObjectNo);
    if (items) {
        document.getElementById("objectSelecter").disabled = true;
        document.getElementById("objectSelecter").onclick = "";
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < items.length; i++) {
            data = items[i];
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
function do_save(flag) {
    if (flag == 1) {
        document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    } else {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    }
    document.mainForm.submit();
}
function do_submit() {
    //检查输入数据是否合法
    if (validateData()) {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=SpareConstant.TO_CONFIRM%>";
        document.mainForm.submit();
    }
}

function validateData() {
    var isvalid = false;
    var toObjectNo = document.mainForm.toObjectNo.value;
    if(toObjectNo == "") {
        alert("请选择目的子库！");
        return false;
    }
    var tabObj = document.getElementById("dataTable");
    if (tabObj.rows.length == 0) {
        alert("请选择要转移的设备！");
    } else {
        var fieldNames = "quantity";
        var fieldLabels = "转移数量";
        isvalid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
    }
    return isvalid;
}

function checkQty(obj) {
    var val = obj.value;
    if (val == "") {
        //            obj.value = 0;
    } else if (isNaN(val)) {
        alert("请输入数字！");
        obj.value = "";
        obj.focus();
    } else if (Number(val) < 0) {
        alert("请输入不小于0的整数！");
        obj.value = "";
        obj.focus();
    } else {
        var tempId = obj.id.substring(8, obj.id.length);
        var onhandQty = Number(document.getElementById("onhandQty" + tempId).value);
        var quantity = Number(obj.value);
        if (onhandQty < quantity) {
            alert("转移数量不能大于可用量，请重新输入！");
            obj.value = "";
            obj.focus();
        }
    }

}

function do_confirm() {
    document.mainForm.act.value = "<%=WebActionConstant.RECEIVE_ACTION%>";
    document.mainForm.submit();
}

function do_untread() {
    document.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
    document.mainForm.submit();
}

</script>
</html>