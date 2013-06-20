<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%--
  Created by IntelliJ IDEA.
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
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
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
                <tr height="24">
                    <td width="10%" align="right">单据号：</td>
                    <td width="15%"><%=amsItemTransH.getTransNo()%></td>
                    <td width="10%" align="right">创建人：</td>
                    <td width="10%"><%=amsItemTransH.getCreatedUser()%></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="8%"><%=amsItemTransH.getCreationDate()%></td>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="10%"><%=amsItemTransH.getTransStatusName()%></td>
                  </tr>
                 <tr height="50">
                        <td align="right">备注：</td>
                        <td colspan="6"><%=amsItemTransH.getRemark()%></td>
                    </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>
     <script type="text/javascript">
        var columnArr = new Array("checkbox", "物料编码", "设备名称", "规格型号", "待修数量", "送修数量");
        var widthArr = new Array("2%", "12%", "15%", "15%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%"><input type="text" name="barcode" id="barcode0" class="noborderGray" style="width:100%"></td>
                <td width="12%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%"><input type="text" name="serialNo" id="serialNo0" value=""
                                      class="blueborderYellow" style="width:100%"></td>
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
            <tr id="mainTr<%=i%>">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%"><input type="text" name="barcode" class="noborderGray" style="width:100%"
                                       value="<%=row.getValue("BARCODE")%>" id="barcode<%=i%>"></td>
                <td width="12%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
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
<%--<input type="hidden" name="addressId" value="<%=amsItemTransH.getAddressId()%>">--%>
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
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
       /* if (mainForm.barcode.value == "" || mainForm.toObjectName.value == "") {
            alert("请确认送修数据和库存!");

        } else {*/
            mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
             mainForm.submit();
//        }
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

        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJSX_ITEM_INFO2%>";
        var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
        var items = window.showModalDialog(url, null, popscript);
        if (items) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < items.length; i++) {
                data = items[i];
                appendDTORow(tab, data);
            }
        }
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
</script>