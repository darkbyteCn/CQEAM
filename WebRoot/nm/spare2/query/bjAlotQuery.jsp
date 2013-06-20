<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-12-20
  Time: 15:50:38
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.nm.spare2.allot.dto.AmsBjsAllotDDto" %>
<%@ page import="com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<head><title>备件分配(新)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>

</head>

<body topMargin=1 leftMargin=1>
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
<form action="/servlet/com.sino.nm.spare2.allot.servlet.AmsBjsAllotServlet" name="mainForm" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1" width="100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1" border="0">
                <tr height="22">
                    <td align="right">单据号：</td>
                    <td><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td align="right">分配地市：</td>
                    <td><%=amsItemTransH.getToOrganizationName()%>
                    </td>
                </tr>
                <tr>
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%>
                    </td>
                </tr>
                <tr height="50">
                    <td align="right">备注：</td>
                    <td colspan="6"><%=amsItemTransH.getRemark()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/button/viewFlow.gif" alt="查阅流程" onClick="viewFlow();">
        <img src="/images/button/viewOpinion.gif" alt="查阅意见" onClick="viewOpinion();">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
            var columnArr = new Array( "设备名称", "规格型号","现有数量", "分配数量");
            var widthArr = new Array("30%", "35%", "15%", "15%");
            printTableHead(columnArr, widthArr);
        </script>
    <div style="overflow-y:scroll;height:500px;width:100%;left:1px;margin-left:0"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" style="height:650;border:2px groove">
        <tr>
            <td width="60%" style="vertical-align:top">
                <table id="itemTable" class="headerTable" width="100%" border="1">
                    <tr height="20">
                        <td width="5%"><%--<input type="checkbox"  name="mainCheck" value=""
                                              onclick="checkAll('mainCheck','subCheck')">--%></td>

                        <td width="30%" align="center">设备名称</td>
                        <td width="35%" align="center">规格型号</td>
                        <td width="15%" align="center">现有数量</td>
                        <!--<td width="10%" align="center">已分配</td>-->
                    </tr>
                </table>
                <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
                    <%if (set == null || set.isEmpty()) {%>
                    <tr height=20px style="display:none"
                        onclick="do_allot(this)">
                        <td align=center width=5%><%--<input type="checkbox" name="subCheck" id="subCheck0" value="">--%>
                        </td>
                        <td width="30%" name="itemName" id="itemName0"></td>
                        <td width="35%" name="itemSpec" id="itemSpec0"></td>
                        <td width="15%"><input type="text" class="noborderGray" name="itemAmount"
                                                            id="itemAmount0" style="width:100%;text-align:right"></td>
                        <!--<td align=center width="10%" type="checkbox" name="allot" id="allot0"></td>-->
                        <td style="display:none">
                            <input type="hidden" name="itemCode" id="itemCode0">
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
                        <td align=center width=5%><%--<input onchange="do_allot('<%=dto1.getItemCode()%>');"
                                                         type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                         value="<%=dto1.getItemCode()%>">--%></td>
                        <td width="30%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
                        </td>
                        <td width="35%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
                        </td>
                        <td width="15%"><input name="itemAmount" class="noborderGray" readonly
                                                            style="width:100%;text-align:right"
                                                            id="itemAmount<%=i%>" value="<%=dto1.getItemAmount()%>">
                        </td>
                        <%--<td align=center width="10%"><input type="checkbox" name="allot" id="allot<%=i%>" disabled></td>--%>
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

            <td width="40%" style="vertical-align:top" style="height:500;border:2px groove">
                <iframe width="100%" name="ouList" height="100%" scrolling="auto"
                        src="/servlet/com.sino.ams.spare2.allot.servlet.AmsBjsAllotouServlet"></iframe>
            </td>

        </tr>
    </table>
   </div>
</fieldset>
<input type="hidden" name="act" value="">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="value1" id="value1" value="">
<input type="hidden" name="checkedIndex" value="">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="detailIds" value="">
</form>
</body>
</html>
<script type="text/javascript">
function do_add() {
    var lookUpName = "<%=LookUpConstant.LOOKE_UP_BEIJIAN2%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    var tab = document.getElementById("mtlTable")
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            if (!isItemExist(user)) {
                appendDTORow(tab, user);
            }
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
function do_save() {
    if (checkValues()) {
        mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>";
        mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        mainForm.submit();
    }
}
function do_submit() {
    var orgId = "<%=user.getOrganizationId()%>";
    var userId = "<%=user.getUserId()%>";
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
function do_repeal() {
    ouList.mainForm.act.value = "<%=WebActionConstant.CANCEL_ACTION%>";
    ouList.mainForm.submit();
}
function submitH() {
    if (checkValues()) {
        mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        mainForm.submit();
    }

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
    ouList.mainForm.itemCode.value = obj.cells[4].childNodes[0].value;
    ouList.mainForm.transId.value = document.mainForm.transId.value;
    //    ouList.mainForm.detailId.value=obj.cells[6].childNodes[0].value;
    ouList.mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";

    ouList.mainForm.submit();

}
function do_delete() {
    var tab = document.getElementById("mtlTable");
    //将删除的有LINE_ID的行的LINE_ID加到lineIds上
    var rowCount = tab.rows.length;
    if (rowCount > 0) {
        var boxArr = getCheckedBox("subCheck");
        var chkCount = boxArr.length;
        if (chkCount > 0) {
            var chkObj = null;
            for (var i = 0; i < chkCount; i++) {
                chkObj = boxArr[i];
                var checkboxId = chkObj.id;
                var id = checkboxId.substring(8, checkboxId.length);
                var detailId = document.getElementById("detailId" + id).value;
                if (detailId != "") {
                    mainForm.detailIds.value += detailId + ",";
                }
            }
        }
    }
    //        alert("mainForm.detailIds.value="+mainForm.detailIds.value)

    deleteTableRow(tab, 'subCheck');

}
function checkValues() {
    var retVal = true;
    var tab = document.getElementById("mtlTable");
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        retVal = false;
    } else {
        var qtys = document.getElementsByName("allotQty");
        for (var i = 0; i < qtys.length; i++) {
            if (qtys[i].value == "") {
                alert("请输入数量！");
                qtys[i].focus();
                retVal = false;
                break;
            }
        }
    }

    return retVal;
}
function init() {

}

</script>