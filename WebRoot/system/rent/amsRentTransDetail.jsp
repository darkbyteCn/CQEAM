<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.*" %>
<%@ page import="com.sino.base.dto.*" %>
<%@ page import="com.sino.base.util.*" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.*" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfRoleDTO" %>
<%@ page import="com.sino.ams.newasset.dto.*" %>
<%@ page import="com.sino.ams.newasset.constant.*" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.sms.dto.SfMsgCategoryDTO" %>
<%@ page import="com.sino.ams.system.user.dto.EtsOuCityMapDTO" %>
<%@ page import="com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfGroupDTO" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-7-10
  Time: 15:06:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm" %>
<html>
<%
    AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String status = headerDTO.getTransStatus();
    String transType = headerDTO.getTransType();
    String transferType = headerDTO.getTransferType();
    String transId = headerDTO.getTransId();
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String orgId = userAccount.getOrganizationId();
    String userId = userAccount.getUserId();
    String attribute1 = headerDTO.getAttribute1();
%>
<head>
    <title><%=headerDTO.getTransTypeValue()%>
    </title>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
</head>
<body leftmargin="0" topmargin="0">
<form action="/servlet/com.sino.ams.system.rent.servlet.AMSRentChangeServlet" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
    <tr>
        <td>
            <table width=100% border="0">
                <tr>
                    <td align=right width=8%>单据号：</td>
                    <td width=17%>
                        <input name="transNo" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getTransNo()%>">
                    </td>
                    <td align=right width=8%>单据状态：</td>
                    <td width=17%>
                        <input name="transStatusDesc"class="input_style2"  readonly style="width:100%; "
                               value="<%=headerDTO.getTransStatusDesc()%>">
                    </td>
                    <td align=right width=8%>创建日期：</td>
                    <td width=17%>
                        <input name="creationDate" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getCreationDate()%>"></td>
                    
                </tr>
                <tr>
                    <td align=right width=8%>经办人：</td>
                    <td width=17%>
                        <input name="created" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getCreated()%>">
                    </td>
                    <td align=right>公司名称：</td>
                    <td>
                        <input name="userCompanyName" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getFromCompanyName()%>"></td>
                    <td align=right width=8%>部门名称：</td>
                    <td width=17%>
                        <input name="userDeptName" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getUserDeptName()%>" size="20"></td>
                </tr>

                <tr>
                    <td align=right width=8%>手机号码：</td>
                    <td width=17%>
                        <input name="phoneNumber1" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getPhoneNumber()%>" size="20"></td>
                    <td align=right>电子邮件：</td>
                    <td>
                        <input name="email1" class="input_style2" readonly style="width:100%; "
                               value="<%=headerDTO.getEmail()%>" size="20"></td>
                    <td align=right width=8%>调入部门：</td>
                    <td width=17%  colspan="3">
                        <select name="toDept" style="width:100%"  disabled="true" class="input_style2"><%=headerDTO.getToDeptOption()%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align=right width=8% align="right" height="40">调拨说明：　</td>
                    <td colspan="7" height="40"><textarea name="createdReason" readonly style="width:100%;height:100%" class="input_style2" rows="1" cols="20"><%=headerDTO.getCreatedReason()%> </textarea></td>
                </tr>
                <input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">

            </table>
        </td>
    </tr>
</table>
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<%
    if (!transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
%>
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<%
    }
%>
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">
<%--<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureName()%>">--%>
<input type="hidden" name="act" value="">
<fieldset style="border:1px solid #397DF3; position:absolute;top:123px;width:100%;height:70%">
    <legend>


        <span id="warn"></span>

        <%--<img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="look_option()">--%>
        <%--<img src="/images/button/viewFlow.gif" alt="查阅流程" onClick="viewFlow(); return false;">--%>

        <img src="/images/button/close.gif" id="img6" alt="关闭" onClick="do_Close(); return false;">


    </legend>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
        <table class="headerTable" border="1" width="140%">
            <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
                <%--<td align=center width="3%"><input type="checkbox" name="mainCheck" value=""--%>
                                                   <%--onPropertyChange="checkAll('mainCheck','subCheck')"></td>--%>
                <td align=center width="8%">资产标签</td>
                <td align=center width="8%">资产名称</td>
                <td align=center width="8%">资产型号</td>
                <td align=center width="3%">数量</td>
                <td align=center width="5%">原责任人</td>
                <td align=center width="5%">新责任人</td>
                <td align=center width="11%">新地点</td>
                <td align="center" width="5%">调拨日期</td>
                <td align=center width="8%">备注</td>
                <td style="display:none">隐藏域字段</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="test1" value="1">

    <div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666">
            <%
                DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (lineSet == null || lineSet.isEmpty()) {
            %>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="display:none">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
                <td width="8%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)">
                    <input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
                <td width="8%" align="left" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input
                        type="text" name="itemName" id="itemName0" class="finput" readonly value=""></td>
                <td width="8%" align="left" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input
                        type="text" name="itemSpec" id="itemSpec0" class="finput" readonly value=""></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input
                        type="text" name="itemQty" id="itemQty0" class="finput3" readonly value=""></td>
                <td width="5%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input
                        type="text" name="userName" id="userName0" class="finput" readonly value=""></td>
                <td width="5%" align="left"><input type="text" name="responsibilityUserName"      class="finputNoEmpty"
                                                   id="responsibilityUserName0" class="finput" readonly value=""

                                                   style="cursor:hand"></td>
                <td width="11%"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finputNoEmpty" readonly value=""  style="cursor:hand"></td>
                <td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0"
                                                     style="width:100%; border: 1px solid #FFFFFF;cursor:hand" readonly
                                                     ></td>
                <td width="8%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
                <td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation0" value=""></td>
                <td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0"
                                                value=""></td>
                <td style="display:none"><input type="hidden" name="responsibilityDept" id="responsibilityDept0"
                                                value=""></td>
                <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation0"
                                                value=""></td>
                <td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser0"
                                                value=""></td>
                <td style="display:none"><input type="hidden" name="addressId" id="addressId0" value=""></td>
            </tr>
            <%
            } else {
                AmsAssetsTransLineDTO lineDTO = null;
                String barcode = "";
                for (int i = 0; i < lineSet.getSize(); i++) {
                    lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                    barcode = lineDTO.getBarcode();
            %>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="cursor:hand">
                <%--<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"--%>
                                                     <%--value="<%=barcode%>"></td>--%>
                <td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand"
                    onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput"
                                                         readonly value="<%=barcode%>"></td>
                <td width="8%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand"
                    onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName<%=i%>" class="finput"
                                                         readonly value="<%=lineDTO.getItemName()%>"></td>
                <td width="8%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand"
                    onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput"
                                                         readonly value="<%=lineDTO.getItemSpec()%>"></td>
                <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand"
                    onClick="do_ShowDetail(this)"><input type="text" name="itemQty" id="itemQty<%=i%>" class="finput3"
                                                         readonly value="<%=lineDTO.getItemQty()%>"></td>
                <td width="5%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand"
                    onClick="do_ShowDetail(this)"><input type="text" name="userName" id="userName<%=i%>" class="finput"
                                                         readonly value="<%=lineDTO.getUserName()%>"></td>
                <td width="5%" align="left"><input type="text" name="responsibilityUserName"  class="finput"
                                                   id="responsibilityUserName<%=i%>" class="finput" readonly
                                                   value="<%=lineDTO.getResponsibilityUserName()%>"

                                                   style="cursor:hand"></td>
                <td width="11%"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>" style="cursor:hand"></td>
                <td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>"
                                                     style="width:100%; border: 1px solid #FFFFFF;cursor:hand" readonly
                                                     value="<%=lineDTO.getLineTransDate()%>"

                                                   ></td>
                <td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput"
                                                   value="<%=lineDTO.getRemark()%>"></td>
                <td style="display:none">
                    <input type="hidden" name="oldLocation" id="oldLocation<%=i%>"
                           value="<%=lineDTO.getOldLocation()%>">
                    <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>"
                           value="<%=lineDTO.getOldResponsibilityUser()%>">
                    <input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>"
                           value="<%=lineDTO.getResponsibilityDept()%>">
                    <input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>"
                           value="<%=lineDTO.getAssignedToLocation()%>">
                    <input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>"
                           value="<%=lineDTO.getResponsibilityUser()%>">
                    <input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
                </td>
            </tr>
            <%
                    }
                }%>
        </table>
    </div>
</fieldset>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
var srcToOrgValue = "";
var srcDeptValue = null;
var transferType = "";
var attribute1 = "<%=attribute1%>";

if (mainFrm.toOrganizationId) {
    srcToOrgValue = mainFrm.toOrganizationId.value;
}
if (mainFrm.fromDept) {
    srcDeptValue = mainFrm.fromDept.value;
}
if (mainFrm.transferType) {
    transferType = mainFrm.transferType.value;
}
var dataTable = document.getElementById("dataTable");

/**
 * 功能：选择资产
 */
function do_SelectAssets() {
    var dialogWidth = 52;
    var dialogHeight = 29;
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_RENT%>";


    var userPara = "&deptCode=" +<%=userAccount.getDeptCode()%>;


    var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (assets) {
        var data = null;
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            appendDTO2Table(dataTable, data, false, "barcode");
        }
    }
}

function deleteLine() {
    deleteTableRow(dataTable, 'subCheck');
}


function doInitArray() {
    segment10Array = new Array();
    projectNameArray = new Array();
    projectNameIndex = -1;
    var segment10 = document.getElementsByName("segment10");
    for (var i = 2; i < segment10.length; i++) {
        segment10Array[i - 2] = segment10[i].value;
    }
    var projectName = document.getElementsByName("projectName");
    for (var i = 2; i < projectName.length; i++) {
        if (!isEmpty(projectName[i].value)) {
            projectNameIndex++;
            projectNameArray[projectNameIndex] = projectName[i].value;
        }
    }
}

function do_SaveOrder() {
    mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_SubmitOrder() {
    var tab = document.getElementById("dataTable") ;
    var rowcount = tab.rows.length;
    if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        return;
    }
    for (var i = 0; i < rowcount; i++) {
        var name = tab.rows[i].cells[6].childNodes[0].value;
        var lineDat = tab.rows[i].cells[7].childNodes[0].value;
        if (name == "" || lineDat == "") {
            alert("请选择新责任人和时间！");
            return;
        }
    }
     var togroup=mainFrm.toGroup.value;
    if(togroup==""){
        alert("请选择调入组别！");
        return;
    }
    var res=mainFrm.createdReason.value;
    if(res==""){
      alert("请填写调拨说明！");
        return;
    }
    var orgId = "<%=orgId%>";
    var userId = "<%=userId%>";
    var groupId = mainFrm.toGroup.value;
    var procdureName = "租赁资产调拨流程";
    var flowCode = "";
    var paramObj = new Object();

    paramObj.orgId = orgId;
    paramObj.useId = userId;
    paramObj.groupId = "<%=userAccount.getCurrGroupId()%>";
    paramObj.procdureName = procdureName;
    paramObj.flowCode = flowCode;
    paramObj.submitH = 'submitH()';
    //	alert("flowCode = " + flowCode);
    assign(paramObj);
}


function submitH() {
    //	document.getElementById("table1").disabled = false;
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
        mainFrm.submit();
}

function initPage() {
    window.focus();
    if (attribute1 == "<%=AssetsDictConstant.TRANS_EDIT_YES%>") {//审批过程中进入该页面
        document.getElementById("table1").disabled = true;
        //		alert(document.getElementById("table1").disabled);
    } else {
        var fromGroup = mainFrm.fromGroup.value;
        if (fromGroup == "") {
            do_SelectCreateGroup();
        }
    }
}


function do_SelectCreateGroup() {
    var fromGroup = mainFrm.fromGroup.value;
    var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>";
    url += "?transType=<%=transType%>";
    url += "&transferType=<%=transferType%>";
    var popscript = "dialogWidth:20;dialogHeight:10;center:yes;status:no;scrollbars:no;help:no";
    var group = window.showModalDialog(url, null, popscript);
    if (group) {
        dto2Frm(group, "mainFrm");
    }
}


/**
 * 功能：点击“调出部门”下拉框时，触发该操作。
 */
function do_ConfirmChange() {
    var rows = dataTable.rows;
    if (rows) {
        var rowCount = rows.length;
        var tr = rows[0];
        if (!(rows.length == 0 || (rows.length == 1 && tr.style.display == "none"))) {
            if (confirm("改变部门将清除已经选择的数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")) {
                deleteRow(dataTable);
                srcDeptValue = mainFrm.fromDept.value;
                setCheckBoxState("mainCheck", false);
            } else {
                selectSpecialOptionByItem(mainFrm.fromDept, srcDeptValue);
            }
        }
    }
}


/**
 * 功能:选择接收部门
 */
function do_SelectDept(lineBox) {
    var toOrganizationId = mainFrm.toOrganizationId.value;
    if (toOrganizationId == "") {
        alert("请先选择调往公司，再选择新责任部门。");
        return;
    }
    var userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType;
    var deptCode = "";
    if (transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>") {
        deptCode = mainFrm.fromDept.value;
    }
    userPara += "&deptCode=" + deptCode;

    var objName = lineBox.name;
    var objId = lineBox.id;
    var idNumber = objId.substring(objName.length);
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_DEPT%>";
    var dialogWidth = 44;
    var dialogHeight = 30;
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var deptChk = mainFrm.allDept;
    if (!deptChk.checked) {
        if (objs) {
            obj = objs[0];
            document.getElementById("responsibilityDept" + idNumber).value = obj["toDept"];
            lineBox.value = obj["toDeptName"];
        } else {
            lineBox.value = "";
            document.getElementById("responsibilityDept" + idNumber).value = "";
            document.getElementById("responsibilityUserName" + idNumber).value = "";
            document.getElementById("responsibilityUser" + idNumber).value = "";
            document.getElementById("assignedToLocationName" + idNumber).value = "";
            document.getElementById("assignedToLocation" + idNumber).value = "";
            document.getElementById("addressId" + idNumber).value = "";
        }
    } else {
        var obj = null;
        var emptyData = false;
        if (objs) {
            obj = objs[0];
        } else {
            emptyData = true;
        }
        var deptNames = document.getElementsByName("responsibilityDeptName");
        var depts = document.getElementsByName("responsibilityDept");
        var userNames = document.getElementsByName("responsibilityUserName");
        var users = document.getElementsByName("responsibilityUser");
        var addressNames = document.getElementsByName("assignedToLocationName");
        var addressNos = document.getElementsByName("assignedToLocation");
        var addressIds = document.getElementsByName("addressId");
        var count = addressNames.length;
        for (var i = 0; i < count; i++) {
            if (emptyData) {
                deptNames[i].value = "";
                depts[i].value = "";
                addressNames[i].value = "";
                addressNos[i].value = "";
                addressIds[i].value = "";
                userNames[i].value = "";
                users[i].value = "";
            } else {
                deptNames[i].value = obj["toDeptName"];
                depts[i].value = obj["toDept"];
            }
        }
    }
}

//选择调入地点
function do_SelectLocation(lineBox) {
    var deptCode = "";
    var objName = lineBox.name;
    var objId = lineBox.id;
    var idNumber = objId.substring(objName.length);
    var userPara = "organizationId=<%=userAccount.getOrganizationId()%>" ;

    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var addressChk = mainFrm.allLocation;
    if (!addressChk.checked) {
        if (objs) {
            var obj = objs[0];
            document.getElementById("assignedToLocation" + idNumber).value = obj["toObjectNo"];
            document.getElementById("addressId" + idNumber).value = obj["addressId"];
            lineBox.value = obj["toObjectName"];
        } else {
            document.getElementById("assignedToLocation" + idNumber).value = "";
            document.getElementById("addressId" + idNumber).value = "";
            lineBox.value = "";
        }
    } else {
        var obj = null;
        var emptyData = false;
        if (objs) {
            obj = objs[0];
        } else {
            emptyData = true;
        }
        var addressNames = document.getElementsByName("assignedToLocationName");
        var addressNos = document.getElementsByName("assignedToLocation");
        var addressIds = document.getElementsByName("addressId");
        var count = addressNames.length;
        for (var i = 0; i < count; i++) {
            if (emptyData) {
                addressNames[i].value = "";
                addressNos[i].value = "";
                addressIds[i].value = "";
            } else {
                addressNames[i].value = obj["toObjectName"];
                addressNos[i].value = obj["toObjectNo"];
                addressIds[i].value = obj["addressId"];
            }
        }
    }
}


//选择新责任人
function do_SelectPerson(lineBox) {
    var objName = lineBox.name;
    var objId = lineBox.id;
    var idNumber = objId.substring(objName.length);
    var deptCode = "";

    deptCode = mainFrm.toDept.value;
    		if(deptCode == ""){
    			alert("请先选择调往部门，再选择新责任人");
    			return;
    		}

    var userPara = "organizationId=" + <%=userAccount.getOrganizationId()%> + "&deptCode=" + deptCode;
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_RECIIVER%>";
    var dialogWidth = 47;
    var dialogHeight = 30;
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var userChk = mainFrm.allUser;
    if (!userChk.checked) {
        if (objs) {
            var obj = objs[0];
            document.getElementById("responsibilityUser" + idNumber).value = obj["employeeId"];
            lineBox.value = obj["userName"];
        } else {
            document.getElementById("responsibilityUser" + idNumber).value = "";
            lineBox.value = "";
        }
    } else {
        var obj = null;
        var emptyData = false;
        if (objs) {
            obj = objs[0];
        } else {
            emptyData = true;
        }
        var userNames = document.getElementsByName("responsibilityUserName");
        var users = document.getElementsByName("responsibilityUser");
        var count = userNames.length;
        for (var i = 0; i < count; i++) {
            if (emptyData) {
                userNames[i].value = "";
                users[i].value = "";
            } else {
                userNames[i].value = obj["userName"];
                users[i].value = obj["employeeId"];
            }
        }
    }
}

function do_SelectFaCategoryCode(lineBox) {
    var objName = lineBox.name;
    var objId = lineBox.id;
    var idNumber = objId.substring(objName.length);
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FACAT_CODE%>";
    var dialogWidth = 54;
    var dialogHeight = 30;
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    var faCatChk = mainFrm.allFaCategory;
    if (!faCatChk.checked) {
        if (objs) {
            obj = objs[0];
            document.getElementById("faCategoryCode" + idNumber).value = obj["faCategoryCode"];
        } else {
            lineBox.value = "";
        }
    } else {
        var obj = null;
        if (objs) {
            obj = objs[0];
        } else {
            obj = new Object();
            obj.faCategoryCode = "";
            obj.faCategoryName = "";
        }
        var catCodes = document.getElementsByName("faCategoryCode");
        var count = catCodes.length;
        for (var i = 0; i < count; i++) {
            catCodes[i].value = obj["faCategoryCode"];
        }
    }
}


function do_SelectDepreciationAccount(lineBox) {
    var toOrganizationId = mainFrm.toOrganizationId.value;
    if (toOrganizationId == "") {
        alert("请先选择调往公司，再选择新责任人。");
        return;
    }
    var objName = lineBox.name;
    var objId = lineBox.id;
    var idNumber = objId.substring(objName.length);
    var deptCode = "";
    if (transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>") {
        deptCode = mainFrm.fromDept.value;
    } else {
        deptCode = document.getElementById("responsibilityDept" + idNumber).value;
        if (deptCode == "") {
            alert("请先选择调往部门，再选择折旧帐户。");
            var deptObj = document.getElementById("responsibilityDeptName" + idNumber);
            do_SelectDept(deptObj);
            return;
        }
    }
    var userPara = "organizationId=" + toOrganizationId + "&accountCode2=" + deptCode;
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ACCOUNT%>";
    var dialogWidth = 51;
    var dialogHeight = 29;
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    var accountChk = mainFrm.allAccount;
    if (!accountChk.checked) {
        if (objs) {
            obj = objs[0];
            lineBox.value = obj["accountCode"];
        } else {
            lineBox.value = "";
        }
    } else {
        var obj = null;
        var emptyData = false;
        if (objs) {
            obj = objs[0];
        } else {
            emptyData = true;
        }
        var accounts = document.getElementsByName("depreciationAccount");
        var count = accounts.length;
        for (var i = 0; i < count; i++) {
            if (emptyData) {
                accounts[i].value = "";
            } else {
                accounts[i].value = obj["accountCode"];
            }
        }
    }
}


function validate() {
    var isValid = true;
    var transType = mainFrm.transType.value;
    var fieldLabels = "";
    var fieldNames = "";
    var validateType = "";
    if (transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>") {
        var fieldLabels = "调出公司;调入公司;资产大类;调拨说明";
        var fieldNames = "fromOrganizationId;toOrganizationId;faContentCode;createdReason";
        var validateType = EMPTY_VALIDATE;
        isValid = formValidate(fieldNames, fieldLabels, validateType);
        if (isValid) {
            if (mainFrm.fromOrganizationId.value == mainFrm.toOrganizationId.value) {
                alert("公司间调拨时，调出公司和调入公司不能为同一公司");
                isValid = false;
            }
        }
    } else {
        if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            isValid = false;
        } else {
            var fieldLabels = "说明;资产大类";
            var fieldNames = "createdReason;faContentCode";
            var validateType = EMPTY_VALIDATE;
            if (transferType) {
                if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT%>") {
                    fieldLabels += ";调入部门";
                    fieldNames += ";responsibilityDeptName";
                } else {
                    fieldLabels += ";调入地点;新责任人";
                    fieldNames += ";assignedToLocationName;responsibilityUserName";
                }
            }
        }
        isValid = formValidate(fieldNames, fieldLabels, validateType);
        if (isValid) {
            var rows = dataTable.rows;
            if (transType == "<%=AssetsDictConstant.ASS_SUB%>" && rows[0].style.display != 'none') {
                var row = null;
                for (var i = 0; i < rows.length; i++) {
                    row = rows[i];
                    var barcode = getNodeObject(row, "barcode");
                    var deprnCost = getNodeObject(row, "deprnCost");
                    var prepareDevalue = getNodeObject(row, "prepareDevalue");
                    if (prepareDevalue.value >= deprnCost.value) {
                        alert("输入非法，原因是：资产“" + barcode.value + "”的“拟提减值" + prepareDevalue.value + "”大于或等于“净值" + deprnCost.value + "”。")
                        deprnCost.focus();
                        isValid = false;
                    }
                }
            }
        }
    }
    return isValid;
}

function do_ShowDetail(td) {
    var transType = mainFrm.transType.value;
    tr = td.parentNode;
    cells = tr.cells;
    var cell = cells[1];
    if (transType == "<%=AssetsDictConstant.ASS_SUB%>") {
        cell = cells[3];
    }
    var barcode = cell.childNodes[0].value;
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=800,height=360,left=70,top=100";
    window.open(url, winName, style);
}

function do_CancelOrder() {
    if (confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")) {
        mainFrm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
        mainFrm.submit();
    }
}

function do_SetLineTransDate(lineBox) {
    if (!mainFrm.allTransDate) {
        return;
    }
    if (!mainFrm.allTransDate.checked) {
        return
    }
    var id = lineBox.id;
    var lineDate = lineBox.value;
    var dateFields = document.getElementsByName("lineTransDate");
    for (var i = 0; i < dateFields.length; i++) {
        if (dateFields[i].id == id) {
            continue;
        }
        dateFields[i].value = lineDate;
    }
}

function do_ClearLineData() {
    var rows = dataTable.rows;
    if (rows) {
        var rowCount = rows.length;
        var tr = rows[0];
        if (!(rows.length == 0 || (rows.length == 1 && tr.style.display == "none"))) {
            var fieldNames = "responsibilityDeptName;assignedToLocationName;responsibilityUserName;depreciationAccount;faCategoryCode;assignedToLocation;responsibilityUser;responsibilityDept;addressId";
            if (!isAllEmapty(fieldNames)) {
                if (confirm("改变调入公司将清除“调入部门”、“调入地点”、“新责任人”、“新折旧帐户”、“新类别”等数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")) {
                    clearFieldValue(fieldNames);
                } else {
                    selectSpecialOptionByItem(mainFrm.toOrganizationId, srcToOrgValue);
                }
            }
        }
    }
    srcToOrgValue = mainFrm.toOrganizationId.value;
}

var al = 0;
function do_exportToExcel() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

var contentCode = mainFrm.faContentCode.value;
function do_ChangeContentCode() {
    var rows = dataTable.rows;
    var rowCount = rows.length;
    if (rowCount > 1 || (rowCount == 1 && rows[0].style.display == "block")) {
        if (confirm("改变资产大类将删除已经选择的资产数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮")) {
            deleteRow(dataTable);
            contentCode = mainFrm.fromDept.value;
            setCheckBoxState("mainCheck", false);
        } else {
            selectSpecialOptionByItem(mainFrm.faContentCode, contentCode);
        }
    }
}

function do_ChangeBookType(sel) {
    if (sel.value == "") {
        mainFrm.bookTypeName.value = "";
    } else {
        mainFrm.bookTypeName.value = getSpecialOptionText("bookTypeCode", sel.value);
    }
}
function look_option(){
       var appId = "<%=headerDTO.getTransId()%>";
	var tableName = "AMS_RENTASSETS_TRANS_H";
    viewOpinion(appId, tableName);
}
</script>