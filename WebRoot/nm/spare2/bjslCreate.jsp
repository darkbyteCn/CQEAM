<%--
  Created by HERRY.
  Date: 2007-10-22
  Time: 15:53:42
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("BJSL_HEADER");
    String attribute1 = amsItemTransH.getAttribute1(); //是分公司内部申领还是向区公司申领
    String str = "区";
    String str2 = "向区公司申领";
    String procName = "备件申领流程";
    if(attribute1.equals("S")){
        str = "分";
        str2 = "分公司内部申领";
        procName = "备件申领流程(分)";
    }
    SfUserDTO userd = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<html>
<head><title>备件申领(<%=str%>)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
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
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body leftmargin="1" topmargin="1" onload="init();"onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
 	<%@ include file="/flow/flowNoButton.jsp"%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.BjslServlet" method="post">
<%--<jsp:include page="/flow/include.jsp" flush="true"/>--%>
    	<%@ include file="/flow/flowPara.jsp"%>
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="deptCode" value="<%=amsItemTransH.getDeptCode()%>">
<input type="hidden" name="groupId" value="">
<input type="hidden" name="attribute1" value="<%=attribute1%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%"  border="0">
                <tr height="22">
                    <td width="8%" align="right">单据号：</td>
                    <td width="18%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>"
                                         style="width:80%"
                                           class="input_style2" readonly>
                    </td>
                    <%--<td width="9%" align="right">仓库名称：</td>
                    <td width="25%"><input type="text" name="toObjectName" value="<%=amsItemTransH.getToObjectName()%>"
                                           class="blueborderYellow" style="width:80%">
                        <a href="#" onClick="do_SelectObject();" title="点击选择仓库"
                           class="linka">[…]</a>
                    </td>
                    <td width="9%" align="right">仓库地点：</td>
                    <td width="25%"><input type="text" name="toObjectLocation" value="<%=amsItemTransH.getToObjectLocation()%>"
                                           class="blueborderGray">
                    </td>--%>
                    <td width="8%" align="right">申请公司：</td>
                    <td width="16%"><input style="width:80%" type="text" name="fromOrganizationName"
                                           value="<%=amsItemTransH.getFromOrganizationName()%>"
                                          class="input_style2" readonly>
                    </td>
                    <td width="10%" align="right">领用部门(室)：</td>
                    <td width="20%"><input type="text" name="deptName" value="<%=amsItemTransH.getDeptName()%>"
                               readonly style="width:80%"
                              class="input_style1"><font color="red">*</font>
                        <a href="#" onClick="do_selectDept();" title="点击选择领用部门"
                           class="linka">[…]</a>
                    </td>
                    <td width="8%" align="right">类型：</td>
                    <td width="15%"> <input type="text" style="width:80%"border="0" value="<%=str2%>" class="input_style2" readonly></td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                               readonly style="width:80%"
                                class="input_style2" readonly>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input style="width:80%" type="text" name="creationDate" readonly
                               value="<%=amsItemTransH.getCreationDate()%>"
                               c class="input_style2" readonly>
                    </td>
                    <td align="right">预计归还日期：</td>
                    <td><input style="width:80%" type="text" name="respectReturnDate" readonly
                               value="<%=amsItemTransH.getRespectReturnDate()%>"
                               class="input_style1"><font color="red">*</font>
                    <img src="/images/calendar.gif" alt="点击选择日期"
                             onclick="gfPop.fEndPop(creationDate,respectReturnDate)">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input style="width:80%" type="text" name="transStatusName" readonly
                               value="<%=amsItemTransH.getTransStatusName()%>"
                               class="input_style2" readonly></td>
                </tr>
                <tr height="22">

                    <td align="right">用途：</td>
                    <td colspan="3"><input type="text" name="reason"      style="width:90%"
                               value="<%=amsItemTransH.getReason()%>"
                              class="input_style1" size="75"><font color="red">*</font>
                    </td>
                    <td align="right">授权人：</td>
                    <td><input type="text" name="authorizationUser"    style="width:80%"
                               value="<%=amsItemTransH.getAuthorizationUser()%>"
                               class="input_style1"><font color="red">*</font></td>
                    <td align="right">仓管员：</td>
                    <td><input type="text" name="invManager"     style="width:80%"
                               value="<%=amsItemTransH.getInvManager()%>"
                               class="input_style1"><font color="red">*</font></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="5"><textarea name="remark" rows="3" cols="" style="width:100%"
                                              class="blueBorder"><%=amsItemTransH.getRemark()%></textarea>
                    </td>

                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img  src="/images/eam_images/choose.jpg" alt="选择数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行"
             onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">

        <%--<img src="/images/button/close.gif" alt="关闭" onClick="window.close();">--%>
    </legend>
    <%-- <div style="left:1px;width:100%;overflow-y:scroll" id="headDiv">
        <table id="headerTable" border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center"><input type="checkBox" name="titleCheck"
                                                     onclick="checkAll('titleCheck','subCheck');" class="headCheckbox">
                </td>
                <td width="12%" align="center">设备条码</td>
                <td width="15%" align="center">设备名称</td>
                <td width="15%" align="center">规格型号</td>
            </tr>
        </table>
    </div>--%>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "设备名称", "规格型号", "数量");
        var widthArr = new Array("2%", "12%", "15%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("BJSL_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <%--<td width="12%" align="center" class="readonlyInput"><input type="text" name="barcodeNo" id="barcodeNo0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>--%>
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
            <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%"><input type="text" name="quantity" id="quantity<%=i%>"
                                      value="<%=row.getValue("QUANTITY")%>"
                                      class="blueborderYellow" style="width:100%;text-align:center">
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
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
    function init() {
        <%--var length = "<%=user.getUserGroups().getSize()%>";

        var groupId = document.mainForm.groupId;
        //        var toDeptName = document.mainForm.toDeptName;
        if (length == 1) {
            groupId.value = "<%=user.getCurrGroupId()%>";
        --%><%--toDept.value = "<%=user.getCurrGroupId()%>";--%><%--
        --%><%--toDeptName.value = "<%=user.getCurrGroupName()%>";--%><%--
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
        }--%>
           do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();

	do_ComputeSummary();
    }
    function do_ComputeSummary(){
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "ljjzalue", "jingeralue", "bfzbValue");
        var summaryCell = new Array("currentUnits", "cost", "sumDepreciation", "impairReserve", "deprnCost", "retirementCost");
        var idCount = idArr.length;
        var sumValueArr = new Array();
        for(var i = 0; i < rowCount; i++){
            var tr =  rows[i];
            for(var j = 0; j < idCount; j++){
                var node = getTrNode(tr, summaryCell[j]);
                if(!sumValueArr[j]){
                    sumValueArr[j] = 0;
                }
                sumValueArr[j] += Number(node.value);
            }
        }
        for(j = 0; j < idCount; j++){
            node = document.getElementById(idArr[j]);
            if(j == 0){
                node.value = sumValueArr[j];
            } else {
                node.value = formatNum(sumValueArr[j], 2);
            }
        }
    }
}
    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30, "organizationId=<%=userd.getOrganizationId()%>");
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
        if(validateData()){
            document.getElementById("procName").value = "<%=procName%>";
            if (flag == 1) {
                document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                document.mainForm.submit();
            } else {
                var orgId = "<%=amsItemTransH.getFromOrganizationId()%>";
                var userId = "<%=amsItemTransH.getCreatedBy()%>";
                var groupId = document.mainForm.groupId.value;
                var procdureName = "<%=procName%>";
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
    }

    function submitH() {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainForm.submit();
    }
    function do_Complete_app_yy() {
    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        document.mainForm.submit();
}
    function validateData() {
        var fn = new Array("remark", "reason", "authorizationUser", "invManager");
        var fLabel= new Array("备注", "用途", "授权人", "仓管员");
        var fl = new Array(150, 150, 32, 32);
        var validate = valdateLength(fn, fLabel, fl);
        var fieldNames = "deptName;reason;authorizationUser;invManager";
        var fieldLabels = "领用部门;用途;授权人;仓管员";
        var validateType = EMPTY_VALIDATE
        if(validate){
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        if (validate) {
            fieldNames = "quantity";
            fieldLabels = "数量";
            validateType = EMPTY_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);

        }
        if (validate) {
                validateType = POSITIVE_INT_VALIDATE;
                validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }

    function do_selectDept(){
        var retVal = getLookUpValues("<%=LookUpConstant.LOOK_UP_MIS_DEPT%>", 48, 30, "");
        if (retVal) {
            //            dto2Frm(projects[0], "form1");
            document.mainForm.deptCode.value = retVal[0].deptCode;
            document.mainForm.deptName.value = retVal[0].deptName;
        }
    }
</script>

</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>