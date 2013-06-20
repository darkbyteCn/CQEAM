<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotDDto" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-6
  Time: 11:26:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件分配</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <style type="text/css" rel="stylesheet">
        .noneinput {
            BORDER-RIGHT: 0px ridge;
            BORDER-TOP: 0px ridge;
            BORDER-LEFT: 0px ridge;
            BORDER-BOTTOM: 0px ridge;
            font-size: 12px;
        }
    </style>
</head>

<body topMargin=0 leftMargin=0>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.ALLOT_H_DTO);
    if (amsItemTransH == null) {
        amsItemTransH = new AmsBjsAllotHDTO();
    }
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);

    RequestParser rp = new RequestParser();
    rp.transData(request);
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.ALLOT_D_DTO);
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
%>
<form action="/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet" name="mainForm" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1" width="100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1" border="1">
                <tr height="22">
                    <td align="right">单据号：</td>
                    <td><input type="text" class="noneinput" name="transNo" readonly style="width:100%"
                               value="<%=amsItemTransH.getTransNo()%>">
                    </td>
                    <td align="right">创建人：</td>
                    <td><input type="text" class="noneinput" name="createdUser" readonly
                               value="<%=amsItemTransH.getCreatedUser()%>">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" class="noneinput" name="creationDate" readonly
                               value="<%=amsItemTransH.getCreationDate()%>">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" class="noneinput" name="transStatusName" readonly
                               value="<%=amsItemTransH.getTransStatusName()%>">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<input type="hidden" name="abc" value="aaaa">
<fieldset style="width:1015;height:650;border:2px groove">
    <legend>
        <img src="/images/button/addData.gif" alt="添加数据" id="img2" onClick="do_add();">
        <img src="/images/button/deleteLine.gif" alt="删除行" id="img1" onClick="do_delete();">
        <img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_save();">
        <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_submit();">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <table width="100%" border="1" style="height:650px;border:2px groove">
        <tr>
            <td width="60%" style="vertical-align:top">
                <table id="itemTable" class="headerTable" width="100%" border="1">
                    <tr>
                        <td width="5%"><input type="checkbox" height="5" name="mainCheck" value=""
                                              onclick="checkAll('mainCheck','subCheck')"></td>
                        <td width="30%" align="center">设备名称</td>
                        <td width="35%" align="center">规格型号</td>
                        <td width="15%" align="center">现有数量</td>
                        <td width="10%" align="center">已分配</td>
                    </tr>
                </table>
                <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
                    <%if (set == null || set.isEmpty()) {%>
                    <tr height=20px style="display:none"
                        onclick="do_allot(this)">
                        <td align=center width=5%><input type="checkbox" name="subCheck" id="subCheck0" value="">
                        </td>
                        <td align=center width="30%" name="itemName" id="itemName0"></td>
                        <td align=center width="35%" name="itemSpec" id="itemSpec0"></td>
                        <td align=center width="15%"><input type="text" class="noneinput" name="itemAmount"   style="width:100%"
                                                            id="itemAmount0"></td>
                        <td align=center width="10%" type="checkbox" name="allot" id="allot0"></td>
                        <td style="display:none">
                            <input type="hidden"    name="itemCode" id="itemCode0">
                            <!--<input type="radio" name="tempRadio" id="tempRadio0">-->
                        </td>
                        <td style="display:none">
                            <%if (sectionRight.equals("OUT")) {%> <input type="hidden" name="detailId"
                                                                         id="detailId0"><%}%>
                        </td>
                    </tr>
                    <%
                    } else {
                        for (int i = 0; i < set.getSize(); i++) {
                            AmsBjsAllotDDto dto1 = (AmsBjsAllotDDto) set.getDTO(i);
                    %>
                    <tr height="20px" onclick="do_allot(this)">
                        <td align=center width=5%><input onchange="do_allot('<%=dto1.getItemCode()%>');"
                                                         type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                         value="<%=dto1.getItemCode()%>"></td>
                        <td align=center width="30%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
                        </td>
                        <td align=center width="35%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
                        </td>
                        <td align=center width="15%"><input name="itemAmount" class="noneinput" readonly
                                                            style="width:100%"
                                                            id="itemAmount<%=i%>" value="<%=dto1.getItemAmount()%>">
                        </td>
                        <td align=center width="10%"><input type="checkbox" name="allot" id="allot<%=i%>" disabled></td>
                        <td style="display:none">
                            <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=dto1.getItemCode()%>"><%--<input type="radio" name="tempRadio" id="tempRadio<%=i%>" value="<%=i%>">--%>
                        </td>
                        <%if (sectionRight.equals("OUT")) {%>
                        <td style="display:none">
                            <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=dto1.getDetailId()%>">
                        </td>
                        <%}%>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </td>

            <td width="40%" style="vertical-align:top" style="height:500px;border:2px groove">
                <iframe width="100%" name="ouList" height="100%" scrolling="auto"
                        src="/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotouServlet"></iframe>
            </td>
        </tr>
    </table>
</fieldset>
<input type="hidden" name="act" value="">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="text" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="value1" id="value1" value="">
<input type="hidden" name="checkedIndex" value="">
<input type="hidden" name="groupId" value="">
</form>
</body>
</html>
<script type="text/javascript">
function do_add() {
    var lookUpName = "<%=LookUpConstant.LOOKE_UP_BEIJIAN%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    var tab = document.getElementById("mtlTable")
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            appendDTORow(tab, user);
        }
    }
}
function do_save() {
    if (!getvalues()) {
        return;
    } else {
        var rows = document.getElementById("mtlTable").rows;
        var checkedIndex = document.mainForm.checkedIndex.value;
        //            rows[checkedIndex].cells[4].childNodes[0].checked = true;
        var value3 = ouList.mainForm.value1.value;
        if (value3 != "") {
            var count1 = ouList.document.getElementById("count")
            ouList.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            ouList.mainForm.transId.value = "<%=amsItemTransH.getTransId()%>";
            mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>";
            ouList.mainForm.action = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotouServlet?transId=" + "<%=amsItemTransH.getTransId()%>";
            ouList.mainForm.submit();
        } else {
            alert("请确认数量！");
        }
    }
}
function do_submit() {
    var orgId = "<%=amsItemTransH.getFromOrganizationId()%>";
    var userId = "<%=amsItemTransH.getCreatedBy()%>";
    var groupId = "<%=user.getCurrGroupId()%>";
    var procdureName = "备件分配流程";
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
function submitH() {
    if (!getvalues()) {
        return;
    }
    var rows = document.getElementById("mtlTable").rows;
    var checkedIndex = document.mainForm.checkedIndex.value;
    //        rows[checkedIndex].cells[4].childNodes[0].checked = true;
<%--ouList.mainForm.sectionRight.value="<%=sectionRight%>"--%>
    ouList.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    ouList.mainForm.transId.value = "<%=amsItemTransH.getTransId()%>";
    mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
    transData();
    ouList.mainForm.submit();
}
function do_allot(obj) {
    var cc = "<%=sectionRight%>"    ;
    var rows = document.getElementById("mtlTable").rows;
    for (var i = 0; i < rows.length; i++) {
        rows[i].style.backgroundColor = "#FFFFFF"
    }
    obj.style.backgroundColor = "#9FD6FF";
    document.mainForm.checkedIndex.value = obj.rowIndex;
    //        alert(document.mainForm.checkedIndex.value)
    //        obj.cells[5].childNodes[1].checked = true;
    ouList.mainForm.itemCode.value = obj.cells[5].childNodes[0].value;
    ouList.mainForm.transId.value = "<%=amsItemTransH.getTransId()%>";
    ouList.mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";

    ouList.mainForm.submit();

}
function do_delete() {
    var tab = document.getElementById("mtlTable");
    deleteTableRow(tab, 'subCheck');
}
function getvalues() {
    var tab = document.getElementById("mtlTable");
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        return false;
    }
    return true;
}
function init() {

}
function transData() {
    ouList.mainForm.actId.value = document.mainForm.actId.value;
    ouList.mainForm.taskProp.value = document.mainForm.taskProp.value;
    ouList.mainForm.currTaskId.value = document.mainForm.currTaskId.value;
    ouList.mainForm.prevTaskId.value = document.mainForm.prevTaskId.value;
    ouList.mainForm.prevUserId.value = document.mainForm.prevUserId.value;
    ouList.mainForm.prevUserName.value = document.mainForm.prevUserName.value;
    ouList.mainForm.prevPositionId.value = document.mainForm.prevPositionId.value;
    ouList.mainForm.prevPositionName.value = document.mainForm.prevPositionName.value;
    ouList.mainForm.nextTaskId.value = document.mainForm.nextTaskId.value;
    ouList.mainForm.nextDeptId.value = document.mainForm.nextDeptId.value;
    ouList.mainForm.nextDeptName.value = document.mainForm.nextDeptName.value;
    ouList.mainForm.nextPositionId.value = document.mainForm.nextPositionId.value;
    ouList.mainForm.nextPositionName.value = document.mainForm.nextPositionName.value;
    ouList.mainForm.nextUserId.value = document.mainForm.nextUserId.value;
    ouList.mainForm.nextUserName.value = document.mainForm.nextUserName.value;
    ouList.mainForm.procId.value = document.mainForm.procId.value;
    ouList.mainForm.procName.value = document.mainForm.procName.value;
    ouList.mainForm.currDeptId.value = document.mainForm.currDeptId.value;
    ouList.mainForm.currDeptName.value = document.mainForm.currDeptName.value;
    ouList.mainForm.approveOpinion.value = document.mainForm.approveOpinion.value;
}
</script>