<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
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
</head>
<body leftmargin="1" topmargin="1">
<jsp:include page="/message/MessageProcess"/>
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
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.servlet.BjslApproveServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="processing" value="0">
<input type="hidden" name="toSaveItemCode" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
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
                    <td width="18%"><%=amsItemTransH.getTransNo()%>
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
                    <td width="16%"><%=amsItemTransH.getFromOrganizationName()%>
                    </td>
                    <td width="10%" align="right">领用部门(室)：</td>
                    <td width="20%"><%=amsItemTransH.getDeptName()%>
                    </td>
                    <td align="right">类型：</td>
                    <td><%=str2%>
                    </td>
                </tr>

                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">创建日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td><%=amsItemTransH.getTransStatusName()%>
                    </td>
                    <td align="right">预计归还日期：</td>
                    <td><%=amsItemTransH.getRespectReturnDate()%>
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">用途：</td>
                    <td colspan="3"><%=amsItemTransH.getReason()%>
                    </td>
                    <td align="right">授权人：</td>
                    <td><%=amsItemTransH.getAuthorizationUser()%></td>
                    <td align="right">仓管员：</td>
                    <td><%=amsItemTransH.getInvManager()%></td>
                </tr>
                <tr>
                    <td align="right" height="50">备注：</td>
                    <td colspan="5"><%=amsItemTransH.getRemark()%>
                    </td>

                </tr>
            </table>
        </td>
    </tr>
</form>
</table>
<fieldset>
    <legend>
        <img src="/images/eam_images/save.jpg" alt="保存" id="img3" onClick="do_save();">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_submit();">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" id="img5" onClick="viewFlow();">
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅意见" onClick="viewOpinion();">
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
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
            <td width="50%" style="vertical-align:top">
                <iframe width="100%" height="500" name="ouList" frameborder="none"
                        src="/servlet/com.sino.ams.spare2.servlet.ItemCountByOuServlet"></iframe>

            </td>
        </tr>
    </table>
</fieldset>
</body>
<script type="text/javascript">
    function init() {

    }
    var g_itemCode = "";
    var g_lineId = "";
    function do_listQty(itemCode, lineId) {
        var processing = mainForm.processing.value;
        if(processing == "1"){
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
        //window.setTimeout("do_query();",2000);
    }
    function do_query(){
    	makeRadioChecked('itemRadio', g_itemCode);
        mainForm.toSaveItemCode.value = g_itemCode;
        ouList.mainForm.itemCode.value = g_itemCode;
        //        alert(ouList.mainForm.itemCode.value);
        ouList.mainForm.lineId.value = g_lineId;
        ouList.mainForm.transId.value = document.mainForm.transId.value;
        ouList.mainForm.attribute1.value = '<%=attribute1%>';
        ouList.mainForm.action = '/servlet/com.sino.ams.spare2.servlet.ItemCountByOuServlet';
        ouList.mainForm.act.value = '<%=WebActionConstant.QUERY_ACTION%>';
        ouList.mainForm.submit();
    }
    function do_save() {
        var itemCode = mainForm.toSaveItemCode.value
//        alert("save itemCode = " + itemCode)
        var index = getCheckedRadioIndex("itemRadio");
        document.getElementById("itemCheckBox" + index).checked = true;
        ouList.mainForm.itemCode.value = itemCode;
        //        alert(ouList.mainForm.itemCode.value);
        ouList.mainForm.action = "/servlet/com.sino.ams.spare2.servlet.AmsItemTransDServlet";
        ouList.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        ouList.mainForm.transId.value = document.mainForm.transId.value;
        ouList.mainForm.submit();
    }
    function do_submit() {
        do_save();
        if (confirm("请确定所有行的分配信息都已保存！确定请点击“确定”按钮，否则请点击“取消”按钮。")) {
            var paramObj = new Object();
            paramObj.orgId = "<%=user.getOrganizationId()%>";
            paramObj.useId = "<%=user.getUserId()%>";
            paramObj.groupId = "<%=user.getCurrGroupId()%>";
            paramObj.procdureName = "备件申领流程";
            paramObj.flowCode = "";
            paramObj.submitH = "submitH()";
            assign(paramObj);
        }
    }
    function submitH() {
        document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
        document.mainForm.submit();
    }
</script>
</html>