<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-13
  Time: 22:56:04
--%>
<html>
<head><title>备件报废</title>
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
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>

</head>
<body topMargin=1 leftMargin=1>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
//    System.out.print("size:" + "\n");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsItemTransHDTO dto = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute("AIT_LINES");
    //  System.out.print("size:"+set.getSize()+"\n");
//    System.out.println("666666666");
%>
<form action="/servlet/com.sino.ams.spare.reject.servlet.AmsBjRejectServlet" name="mainForm" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1" style="border-collapse: collapse">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="22">
                    <td width="10%" align="right">报废单据：</td>
                    <td width="15%"><input type="text" name="transNo" class="blueBorderGray" readonly
                                           style="width:100%"
                                           value="<%=dto.getTransNo()%>"></td>
                    <%--<td width="10%" align="right">仓库：</td>
                    <td width="25%"><input type="text" name="fromObjectName" class="blueBorderYellow" readonly
                                           style="width:80%" value="<%=dto.getFromObjectName()%>">
                        <a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>
                </tr>
                <tr>--%>
                    <td width="10%" align="right">创建人：</td>
                    <td width="10%"><input type="text" name="createdUser" class="blueBorderGray" readonly
                                           value="<%=dto.getCreatedUser()%>"></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="8%"><input type="text" name="creationDate" class="blueBorderGray" readonly
                                          value="<%=dto.getCreationDate()%>"></td>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="20%"><input type="text" name="transStatusName" class="blueBorderGray" readonly
                                           value="<%=dto.getTransStatusName()%>"></td>

                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="7"><textarea name="remark" rows="3" cols="" style="width:90%"
                                              class="blueBorder"><%=dto.getRemark()%></textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/button/addData.gif" alt="添加数据" onclick="do_add();">
        <img src="/images/button/deleteLine.gif" alt="删除行" onClick="do_Delete();">
        <img src="/images/button/save.gif" alt="保存" onClick="do_save()">
        <img src="/images/button/submit.gif" alt="提交" onClick="do_submit()">
        <%
            if (dto.getTransStatus().equals(DictConstant.SAVE_TEMP)) {
        %>
        <img src="/images/button/repeal.gif" alt="撤销" onclick="do_cancle()">
        <%
            }else if(dto.getTransStatus().equals(DictConstant.REJECTED)){
        %>
         <img src="/images/button/viewOpinion.gif" alt="查阅意见" onClick="viewOpinion();">
        <%
            }
        %>
        <img src="/images/button/close.gif" alt="关闭" onclick="window.close()">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "部件号", "设备名称", "规格型号", "现有量", "报废数量");
        var widthArr = new Array("3%", "12%", "15%", "20%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="width:100%;overflow-y:scroll;height:500px">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="1" cellspacing="0">
            <%
                if (set == null || set.isEmpty()) {
            %>
            <tr height=20px style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width=3%><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
                <td align=center width="12%">
                    <input class="noBorderGray" type="text" name="barcode" id="barcode0" style="width:100%"></td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="20%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%"><input type="text" class="noBorderGray" name="onhandQty" id="onhandQty0"
                                      style="width:100%;text-align:right"></td>
                <td width="8%"><input type="text" class="blueBorderYellow" name="quantity" id="quantity0"
                                      style="width:100%;text-align:right"></td>
                <td width=1% style="display:none"><input type="hidden" name="itemCode" id="itemCode0">
                </td>

            </tr>
            <%
            } else {
                for (int i = 0; i < set.getSize(); i++) {
                    AmsItemTransLDTO lineDto = (AmsItemTransLDTO) set.getDTO(i);
//                    System.out.println("2");
            %>
            <tr height=20px onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td align=center width=3%><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value=""></td>
                <td align=center width="12%">
                    <input type="text" class="noBorderGray" name="barcode" id="barcode<%=i%>" style="width:100%"
                           value="<%=lineDto.getBarcode()%>">
                </td>
                <td width="15%" name="itemName" id="itemName<%=i%>"><%=lineDto.getItemName()%>
                </td>
                <td width="20%" name="itemSpec" id="itemSpec<%=i%>"><%=lineDto.getItemSpec()%>
                </td>
                <td width="8%"><input type="text" class="noBorderGray" name="onhandQty" id="onhandQty<%=i%>"
                                      style="width:100%;text-align:right"
                                      value="<%=lineDto.getOnhandQty()%>">
                </td>
                <td width="8%"><input type="text" class="blueBorderYellow" name="quantity" id="quantity<%=i%>"
                                      style="width:100%;text-align:right"
                                      value="<%=lineDto.getQuantity()%>">
                </td>
                <td width=1% style="display:none"><input type="hidden" name="itemCode" id="itemCode<%=i%>"
                                                         value="<%=lineDto.getItemCode()%>"></td>

            </tr>
            <%
                    }
                }
//                System.out.println("333333");

            %>
        </table>
    </div>
</fieldset>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transType" value="<%=DictConstant.BJBF%>">
<input type="hidden" name="fromObjectNo" value="<%=dto.getFromObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="objectCategory" value="76">

</form>
</body>
</html>
<script type="text/javascript">
function do_SelectObject() {
    var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>&objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        document.mainForm.fromObjectName.value = projects[0].workorderObjectName;
        document.mainForm.fromObjectNo.value = projects[0].workorderObjectNo;
        //        document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
function do_add() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_ITEM_BF2%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userPara = "objectNo=" + mainForm.fromObjectNo.value;
    //LOOKUP传参数 必须和DTO中一致
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var tab = document.getElementById("mtlTable")
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            if (!isItemExist(user)) {
                appendDTORow(tab, user);
            }
            //增加整行信息时候用到的方法
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
    var lookUpName = "<%=LookUpConstant.LOOK_UP_BF%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userPara = "objectCategory=" + mainForm.objectCategory.value;
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
    }
}
function do_Delete() {
    var tab = document.getElementById("mtlTable");
    deleteTableRow(tab, 'subCheck');
}
function do_save() {
    //        var value1 = mainForm.workorderObjectName.value;
    /*  if (value1 == "") {
      alert("请选择库存！");
  } else {*/
    if (!getvalues()) {
        return;
    }
    mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>";
    mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    mainForm.submit();
    /*}*/
}
function do_cancle() {
    mainForm.act.value = "<%=WebActionConstant.CANCEL_ACTION%>";
    mainForm.submit();
}
function do_submit() {
    var orgId = "<%=user.getOrganizationId()%>";
    var userId = "<%=user.getUserId()%>";
    var groupId = "<%=user.getCurrGroupId()%>";
    var procdureName = "备件报废流程";
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
    //        var value1 = mainForm.workorderObjectName.value;
    //        if (value1 == "") {
    //            alert("请选择库存！");
    //        } else {
    if (!getvalues()) {
        return;
    }
    mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
    mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
    mainForm.submit();
    //        }

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
