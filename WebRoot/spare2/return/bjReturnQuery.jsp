<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.ams.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-12
  Time: 9:30:15
--%>
<html>
<head><title>备件送修返还</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
%>
<body topMargin=1 leftMargin=1>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.spare2.returnBj.servlet.BjReturnRepairServlet" name="mainForm" method="post">
<table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="24">
                    <td width="10%" align="right">单据号：</td>
                    <td width="15%"><input type="text" name="transNo" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=dto.getTransNo()%>"></td>
                    <td width="10%" align="right">创建人：</td>
                    <td width="10%"><input type="text" name="createdUser" class="blueborderGray" readonly
                                           value="<%=dto.getCreatedUser()%>"></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="8%"><input type="text" name="creationDate" class="blueborderGray" readonly
                                          value="<%=dto.getCreationDate()%>"></td>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="10%"><input type="text" name="transStatusName" class="blueborderGray" readonly
                                           value="<%=dto.getTransStatusName()%>"></td>
                    <td width="8%">库存：</td>
                    <td width="10%"><input type="text" name="toObjectName" class="blueborderYellow" readonly
                                           value="<%=dto.getToObjectName()%>"><a href="#"
                                                                                 class="linka" style="cursor:'hand'"
                                                                                 onclick="do_selectName();">[…]</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if (!dto.getTransStatus().equals(DictConstant.COMPLETED) && dto.getCreatedBy().equals(user.getUserId())) {
        %>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_add();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="do_Delete();">
        <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_save()">
        <img src="/images/eam_images/ok.jpg" alt="确定" onClick="do_ok()">
        <%
            }
        %>
        <img src="/images/eam_images/close.jpg" alt="关闭" onclick="window.close()">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "部件号", "设备名称", "规格型号", "序列号");
        var widthArr = new Array("2%", "15%", "25%", "35%", "20%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style="width:1007px;overflow-y:scroll;height:500px">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
            <%
                if (set == null || set.isEmpty()) {
            %>
            <tr height=20px style="display:none"
                onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width=2%><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
                <td align=center width="15%">
                    <input class="noborderGray" type="text" name="barcode" id="barcode0" style="width:100%"
                           readonly></td>
                <td align=center width="25%" name="itemName" id="itemName0"></td>
                <td align=center width="35%" name="itemSpec" id="itemSpec0"></td>
                <td align=center width="20%"><input type="text" class="blueborderYellow" name="serialNo"
                                                    id="serialNo0"
                                                    style="width:100%"></td>
                <td style="display:none"><input class="noneinput" type="hidden" name="itemCode" id="itemCode0"></td>
            </tr>
            <%
            } else {
                AmsItemTransLDTO dto1 = null;
                for (int i = 0; i < set.getSize(); i++) {
                    dto1 = (AmsItemTransLDTO) set.getDTO(i);
            %>
            <tr height=20px onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width=2%><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value=""></td>
                <td align=center width="15%">
                    <input type="text" class="noborderGray" name="barcode" id="barcode<%=i%>" readonly
                           value="<%=dto1.getBarcode()%>" style="width:100%">
                </td>
                <td align=center width="25%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
                </td>
                <td align=center width="35%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
                </td>
                <td align=center width="20%"><input type="text" class="blueborderYellow" name="serialNo"
                                                    id="serialNo<%=i%>"
                                                    value="<%=dto1.getSerialNo()%>" style="width:100%">
                </td>
                <td style="display:none"><input type="hidden" name="itemCode" id="itemCode<%=i%>"
                                                value="<%=dto1.getItemCode()%>"></td>

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
<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
</form>
</body>
</html>
<script type="text/javascript">
    function do_add() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_FH2%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        //LOOKUP传参数 必须和DTO中一致
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        var tab = document.getElementById("mtlTable")
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                //if (!isItemExist(user)) {
                appendDTORow(tab, user);
                //增加整行信息时候用到的方法}
                //                }
            }
        }
    }
    function isItemExist(itemObj) {
        var retVal = false;
        var tabObj = document.getElementById("mtlTable");
        if (tabObj.rows) {
            var trObjs = tabObj.rows;
            var trCount = trObjs.length;
            var trObj = null;
            var itemValue = itemObj.itemCode;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                rowValue = trObj.cells[5].childNodes[0].value;
                if (itemValue == rowValue) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }
    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_KUCUN%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var users = window.showModalDialog(url, null, popscript);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                mainForm.toObjectName.value = users[0].workorderObjectName;
                mainForm.toObjectNo.value = users[0].workorderObjectNo;
            }
        }
    }
    function do_Delete() {
        var tab = document.getElementById("mtlTable");
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
            alert("请选择库存！");
        } else {
            if (!getvalues()) {
                return;
            }
            mainForm.transStatus.value = "<%=DictConstant.CREATE%>";
            mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            mainForm.submit();
        }
    }
    function getvalues() {
        var tab = document.getElementById("mtlTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
    }
</script>