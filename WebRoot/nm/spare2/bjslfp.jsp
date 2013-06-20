<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by HERRY.
  Date: 2007-10-31
  Time: 14:19:50
--%>
<html>
<head><title>备件申领分配</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
     <script type="text/javascript">
       printToolBar();
    </script>
</head>
<body leftmargin="1" topmargin="1" onload="init()" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()" >
<%@ include file="/flow/flowNoButton.jsp" %>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("BJSL_HEADER");
    if (amsItemTransH == null) {
        amsItemTransH = new AmsItemTransHDTO();
    }
    String attribute1 = amsItemTransH.getAttribute1(); //是分公司内部申领还是向区公司申领
    String str2 = "向区公司申领";
    if (attribute1.equals("S")) {
        str2 = "分公司内部申领";
    }
    String sectionRight = request.getParameter("sectionRight");
    SfUserDTO userd = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.BjslApproveServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
    <%@ include file="/flow/flowPara.jsp"%>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="processing" value="0">
<input type="hidden" name="toSaveItemCode" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="fromGroup" value="<%=amsItemTransH.getFromGroup()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<input type="hidden" name="attribute1" value="<%=amsItemTransH.getAttribute1()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1" width="100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
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
                               class="input_style2" readonly>
                    </td>
                    <td align="right">类型：</td>
                    <td><input type="text" style="width:80%"border="0" value="<%=str2%>" class="input_style2" readonly>
                    </td>
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
                    <td align="right">单据状态：</td>
                    <td><input style="width:80%" type="text" name="transStatusName" readonly
                               value="<%=amsItemTransH.getTransStatusName()%>"
                               class="input_style2" readonly>
                    </td>
                    <td align="right">预计归还日期：</td>
                    <td><input style="width:80%" type="text" name="respectReturnDate" readonly
                               value="<%=amsItemTransH.getRespectReturnDate()%>"
                               class="input_style2">
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">用途：</td>
                    <td colspan="3"><input type="text" name="reason"      style="width:90%"
                               value="<%=amsItemTransH.getReason()%>"
                              class="input_style2" size="75">
                    </td>
                    <td align="right">授权人：</td>
                    <td><input type="text" name="authorizationUser"    style="width:80%"
                               value="<%=amsItemTransH.getAuthorizationUser()%>"
                               class="input_style2"></td>
                    <td align="right">仓管员：</td>
                    <td><input type="text" name="invManager"     style="width:80%"
                               value="<%=amsItemTransH.getInvManager()%>"
                               class="input_style2"></td>
                </tr>
                <%--<% if(sectionRight.equals("ALLOCATE")){%>--%>
                   <tr>
                    <td align="right" height="50">备注：</td>
                    <td colspan="3"><textarea name="remark" rows="3" cols="" style="width:100%"
                                              class="blueBorder"><%=amsItemTransH.getRemark()%></textarea>
                    </td>
                       <%if(!attribute1.equals("S")){%>
                        <td>备件类型：</td>
                     <td><input type="radio" name="spareType" id="spareType1" onclick="do_showdiv()"  value="0"<%=amsItemTransH.getSpareType().equals("0")?"checked":""%>>实物备件 <input type="radio"  onclick="do_showdiv()" name="spareType" id="spareType2" value="1" <%=amsItemTransH.getSpareType().equals("1")?"checked":""%>>服务备件</td>
                     <td align="right">服务备件厂商：</td>
                     <td><select name="spareManufacturer"  class=""><%=amsItemTransH.getSpareManufacturerOpt()%></select></td>
                       <%}%>
                </tr>
                <%--<%}else if(sectionRight.equals("FEEDBACK")){%>--%>
                   <%--<tr>--%>
                    <%--<td align="right" height="50">备注：</td>--%>
                    <%--<td colspan="3"><%=amsItemTransH.getRemark()%>--%>
                    <%--</td>--%>
                         <%--<td align="right">服务备件厂商：</td>--%>
                     <%--<td><%=amsItemTransH.getSpareManufacturerName()%><input type="hidden" name="spareManufacturer" value="<%=amsItemTransH.getSpareManufacturer()%>">--%>
                         <%--<input type="hidden" name="spareType" value="<%=amsItemTransH.getSpareType()%>">--%>
                     <%--</td>--%>
                <%--</tr>--%>
                <%--<%}else{%>--%>
                <%--<tr>--%>
                    <%--<td align="right" height="50">备注：</td>--%>
                    <%--<td colspan="5"><%=amsItemTransH.getRemark()%>--%>
                    <%--</td>--%>

                <%--</tr>--%>
                <%--<%}%>--%>
            </table>
        </td>
    </tr>
</form>
</table>
<fieldset>
    <legend>
        <img src="/images/button/save.gif" alt="暂存" id="img3" onClick="do_save();">
        <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_submit();">
        <img src="/images/button/viewFlow.gif" alt="查阅流程" id="img5" onClick="viewFlow();">
        <img src="/images/button/viewOpinion.gif" alt="查阅意见" onClick="viewOpinion();">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
        <%--<%if(sectionRight.equals("FEEDBACK")){%>--%>
        <%--统一设置：--%>
        <%--<input type="checkbox" name="allLseeType" id="allLseeType"><label for="allLseeType">反馈意见</label>--%>
        <%--<%}%>--%>
    </legend>

    <table width="100%" border="1" borderColor="red">
        <tr>
            <td width="50%" style="vertical-align:top">
                <table id="itemTable" class="headerTable" width="100%" border="1">
                    <tr>
                        <td width="5%"></td>
                        <td width="30%" align="center">设备名称</td>
                        <td width="35%" align="center">规格型号</td>
                        <td width="15%" align="center">申领数量</td>
                        <td width="15%" align="center">已处理</td>
                    </tr>

                </table>
                <table width="100%" border="1" borderColor="#9FD6FF">
                    <%
                        RowSet rows = (RowSet) request.getAttribute("BJSL_LINES");
                        if (rows != null && !rows.isEmpty()) {
                            Row row = null;
                            for (int i = 0; i < rows.getSize(); i++) {
                                row = rows.getRow(i);
                    %>
                    <tr id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
                        onMouseOut="style.backgroundColor='#ffffff'"
                        onclick="do_listQty('<%=row.getValue("ITEM_CODE")%>','<%=row.getValue("LINE_ID")%>');">
                        <td width="5%" align="center"><input type="radio" name="itemRadio" onclick=""
                                                             value="<%=row.getValue("ITEM_CODE")%>"></td>
                        <td width="30%"><%=row.getValue("ITEM_NAME")%>
                        </td>
                        <td width="35%"><%=row.getValue("ITEM_SPEC")%>
                        </td>
                        <td width="15%"><input type="text" name="quantity" id="quantity<%=i%>"
                                               value="<%=row.getValue("QUANTITY")%>"
                                               class="noborderYellow" readonly style="width:100%;text-align:center">
                        </td>
                        <td width="15%" align="center"><input type="checkbox" name="itemCheckBox"
                                                              id="itemCheckBox<%=i%>"
                                                              disabled="true" <%=row.getValue("DEALED_WITH").equals("Y")?"checked":""%>>
                        </td>
                        <td style="display:none">
                            <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                            <input type="hidden" name="itemCode" id="itemCode<%=i%>"
                                   value="<%=row.getValue("ITEM_CODE")%>">
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </td>
            <td width="50%" id="swspare" style="vertical-align:top">
                <iframe width="100%" height="500" name="ouList" frameborder="none"
                        src="/servlet/com.sino.nm.spare2.servlet.ItemCountByOuServlet"></iframe>

            </td>
        </tr>
    </table>
</fieldset>
</body>
<script type="text/javascript">
      function init() {

           do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();

	do_ComputeSummary();
    }
            function  do_showdiv(){
             var show = document.mainForm.spareType
    for (var i = 0; i < show.length; i++) {
        if (show[0].checked) {
          document.getElementById("swspare").style.display="" ;
        }else{
           document.getElementById("swspare").style.display="none" ;
        }
    }
            }

    var g_itemCode = "";
    var g_lineId = "";
    function do_listQty(itemCode, lineId) {
        var slType = "<%=attribute1%>";
        if (slType == "") {
            var td = mainForm.spareType;
            var istd = false;
            var spareTp = "";
            for (var i = 0; i < td.length; i++) {
                if (td[i].checked) {
                    istd = true;
                    spareTp = td[i].value;
                    break;
                }
            }

            if (!istd) {
                alert("请确认备件类型!");
                return;
            }
            if (spareTp == "0") {
                var processing = mainForm.processing.value;
                if (processing == "1") {
                    alert("正在处理，请稍候....");
                    return;
                }
                mainForm.processing.value = "1";
                g_itemCode = itemCode;
                g_lineId = lineId;
                //        alert("selected itemCode="+itemCode) ;
                if (ouList.mainForm.itemCode.value != "") {
                    do_save();
                } else {
                    do_query();
                }
            }
        } else {
            var processing = mainForm.processing.value;
            if (processing == "1") {
                alert("正在处理，请稍候....");
                return;
            }
            mainForm.processing.value = "1";
            g_itemCode = itemCode;
            g_lineId = lineId;
            if (ouList.mainForm.itemCode.value != "") {
                do_save();
            } else {
                do_query();
            }
        }
    }
    function do_query(){
    	makeRadioChecked('itemRadio', g_itemCode);
        mainForm.toSaveItemCode.value = g_itemCode;
        ouList.mainForm.itemCode.value = g_itemCode;
        //        alert(ouList.mainForm.itemCode.value);
        ouList.mainForm.lineId.value = g_lineId;
        ouList.mainForm.transId.value = document.mainForm.transId.value;
        ouList.mainForm.attribute1.value = '<%=attribute1%>';
        ouList.mainForm.action = '/servlet/com.sino.nm.spare2.servlet.ItemCountByOuServlet';
        ouList.mainForm.act.value = '<%=WebActionConstant.QUERY_ACTION%>';
        ouList.mainForm.submit();
    }
    function do_save() {
           var sltype="<%=attribute1%>"   ;
        if(!sltype=="S"){
             var td=mainForm.spareType;
        var istd=false;
        for(var i=0;i<td.length;i++){
            if(td[i].checked){
             istd=true;
                break;
            }
        }
        if(!istd){
          alert("请确认备件类型!") ;
         return;
        }
        }

        var itemCode = mainForm.toSaveItemCode.value
//        alert("save itemCode = " + itemCode)
        var index = getCheckedRadioIndex("itemRadio");
        document.getElementById("itemCheckBox" + index).checked = true;
        ouList.mainForm.itemCode.value = itemCode;
        //        alert(ouList.mainForm.itemCode.value);
        ouList.mainForm.action = "/servlet/com.sino.nm.spare2.servlet.AmsItemTransDServlet";
        ouList.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        ouList.mainForm.transId.value = document.mainForm.transId.value;
        ouList.mainForm.submit();
    }
    function do_submit() {
        do_save();
        if (confirm("请确定所有行的分配信息都已保存！确定请点击“确定”按钮，否则请点击“取消”按钮。")) {
            var paramObj = new Object();
             var spareTp="";
                var sltype="<%=attribute1%>"   ;
        if(!sltype=="S"){
            var td=mainForm.spareType;
        var istd=false;

        for(var i=0;i<td.length;i++){
            if(td[i].checked){
             istd=true;
                spareTp=td[i].value;
                break;
            }
        }
        if(!istd){
          alert("请确认备件类型!") ;
         return;
        }  }
         if(spareTp=="0"){
            paramObj.flowCode="SW";
         }else if(spareTp=="1"){
              paramObj.flowCode="FW";
         }else {
               paramObj.flowCode="";
         }
            paramObj.orgId = "<%=userd.getOrganizationId()%>";
            paramObj.useId = "<%=userd.getUserId()%>";
            paramObj.groupId = "<%=amsItemTransH.getFromGroup()%>";
            paramObj.procdureName = "备件申领流程";
//            paramObj.flowCode = "";
            paramObj.submitH = "submitH()";
            assign(paramObj);
        }
    }
    function submitH() {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
        document.mainForm.submit();
    }
function do_SetType(lineBox) {
        if (!mainForm.allLseeType.checked) {
            return;
        }
        var objs = document.getElementsByName("feedbackType");
        var dataCount = objs.length;
        var selectedVal = lineBox.value;
        if (objs && objs.length) {
            var chkObj = null;
            var dataTable = document.getElementById("dataTable");
            var rows = dataTable.rows;
            var row = null;
            var checkObj = null;
            var checkedCount = getCheckedBoxCount("subCheck");
            for (var i = 0; i < dataCount; i++) {
                if (checkedCount > 0) {
                    row = rows[i];
                    checkObj = row.childNodes[0].childNodes[0];
                    if (!checkObj.checked) {
                        continue;
                    }
                }
                chkObj = objs[i];
                selectSpecialOptionByItem(chkObj, selectedVal);
            }
        }
    }
    function do_Complete_app_yy() {
     document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
        document.mainForm.submit();
}
</script>
</html>