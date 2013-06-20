<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2007-12-23
  Time: 17:01:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<head><title>备件申领</title>
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
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
</head>
<body leftmargin="1" topmargin="1">


<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.servlet.BjslServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<input type="hidden" name="groupId" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">申请公司：</td>
                    <td width="25%"><%=amsItemTransH.getToOrganizationName()%>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%></td>
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
        var columnArr = new Array("checkbox", "设备名称", "规格型号", "数量");
        var widthArr = new Array("2%", "12%", "15%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%"><input type="text" name="quantity" id="quantity0" value=""
                                      class="blueborderYellow" style="width:100%;text-align:center"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value=""><input type="hidden" name="itemCode"
                                                                                    id="itemCode0" value="">
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
                <td width="12%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%"><%=row.getValue("QUANTITY")%>
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>"><input
                        type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">
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
<div>
    <jsp:include page="/message/MessageProcess"/>
</div>
</body>
<script type="text/javascript">
    function init() {
        var length = "<%=user.getUserGroups().getSize()%>";

        var groupId = document.mainForm.groupId;
        //        var toDeptName = document.mainForm.toDeptName;
        if (length == 1) {
            groupId.value = "<%=user.getCurrGroupId()%>";
        <%--toDept.value = "<%=user.getCurrGroupId()%>";--%>
        <%--toDeptName.value = "<%=user.getCurrGroupName()%>";--%>
        } else {
            var style = "dialogleft:200px;dialogtop:200px;dialogwidth:350px;dialogheight:250px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
            var url = "/public/chooseGroup.jsp";
            var ret = window.showModalDialog(url, null, style);
            if (ret) {
                var arr = ret.split(",");
                groupId.value = arr[0];
                //                toDept.value = arr[0];
                //                toDeptName.value = arr[1];
            }
        }
    }
    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            //            dto2Frm(projects[0], "form1");
            document.mainForm.toObjectName.value = projects[0].workorderObjectName;
            document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
            document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
        }
    }
    function do_SelectItem() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJ_SYSTEM_ITEM%>";
        var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
        var assets = window.showModalDialog(url, null, popscript);
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
            var itemValue = itemObj.itemCode;
            var rowValue = null;
            for (var i = 0; i < trCount; i++) {
                trObj = trObjs[i];
                rowValue = trObj.cells[4].childNodes[1].value;
                if (itemValue == rowValue) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }
    function do_SavePo(flag) {
        if (flag == 1) {
            document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            document.mainForm.submit();
        } else {
            var orgId = "<%=amsItemTransH.getToOrganizationId()%>";
            var userId = "<%=amsItemTransH.getCreatedBy()%>";
            var groupId = document.mainForm.groupId.value;
            var procdureName = "备件申领流程";
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

    function submitH() {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainForm.submit();
    }
</script>
</html>