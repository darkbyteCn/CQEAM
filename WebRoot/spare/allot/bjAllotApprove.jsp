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
  Date: 2007-11-20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件分配流程审批</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    
</head>

<body leftmargin="0" topmargin="0">
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
            <table width="100%" id="table2" cellspacing="1" border="0">
                <tr height="22">
                    <td align="right">单据号：</td>
                    <td><input type="text" class="detailHeader" name="transNo" readonly style="width:100%;border:none"
                               value="<%=amsItemTransH.getTransNo()%>">
                    </td>
                    <td align="right">创建人：</td>
                    <td><input type="text" class="detailHeader" name="createdUser" readonly    style="width:100%;border:none"
                               value="<%=amsItemTransH.getCreatedUser()%>">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" class="detailHeader" name="creationDate" readonly   style="width:100%;border:none"
                               value="<%=amsItemTransH.getCreationDate()%>">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" class="detailHeader" name="transStatusName" readonly   style="width:100%;border:none"
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
        <img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">
        <img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve2();">
        <img src="/images/button/viewFlow.gif" alt="查阅流程" onClick="viewFlow(); return false;">
        <img src="/images/button/viewOpinion.gif" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
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
<input type="hidden" name="flag" value="">
</form>
</body>
</html>
<script type="text/javascript">
    <%-- function do_SavePo(flag){
        if(flag == 1){
            document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        }else{
            document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        }
        document.mainForm.submit();
    }--%>
    function do_Approve(flag) {
        document.mainForm.flag.value = flag;
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = "<%=user.getCurrGroupId()%>";
        paramObj.procdureName = "备件分配流程";
        paramObj.flowCode = "";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    }
    function do_Approve2() {
        ouList.mainForm.act.value = "<%=WebActionConstant.REJECT_ACTION%>";
        ouList.mainForm.transId.value = "<%=amsItemTransH.getTransId()%>";
        ouList.mainForm.transNo.value = "<%=amsItemTransH.getTransNo()%>";
        transData();
        ouList.mainForm.submit();
    }
    function submitH() {
        var flag = document.mainForm.flag.value;
        var sessionR = "<%=sectionRight%>";
        var actVal = "";
        switch (flag) {
            case 1: actVal = "<%=WebActionConstant.APPROVE_ACTION%>"; break;
            case 2: actVal = "<%=WebActionConstant.REJECT_ACTION%>"; break;
            case 3: actVal = "<%=WebActionConstant.RECEIVE_ACTION%>"; break;
            default :actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
        }
        ouList.mainForm.act.value = actVal;
        ouList.mainForm.transId.value = "<%=amsItemTransH.getTransId()%>";
        ouList.mainForm.transNo.value = "<%=amsItemTransH.getTransNo()%>";
        transData();
        if (sessionR == "OUT") {
            ouList.mainForm.sectionRight.value = sessionR;
            alert(ouList.mainForm.sectionRight.value);
        }
        ouList.mainForm.submit();
        //        mainForm.action = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotouServlet";
        //        document.mainForm.act.value = actVal;
        //        document.mainForm.submit();
    }

    function do_allot(obj) {
        var sessionR = "<%=sectionRight%>";
        var rows = document.getElementById("mtlTable").rows;
        for (var i = 0; i < rows.length; i++) {
            rows[i].style.backgroundColor = "#FFFFFF"
        }
        obj.style.backgroundColor = "#9FD6FF";
        document.mainForm.checkedIndex.value = obj.rowIndex;
        //        alert(document.mainForm.checkedIndex.value)
        //        obj.cells[5].childNodes[1].checked = true;
        var count = obj.cells[3].childNodes[0].value;
        if (sessionR == "OUT") {

            ouList.mainForm.sectionRight.value = sessionR;
            alert(ouList.mainForm.sectionRight.value);
        }
        ouList.mainForm.itemCode.value = obj.cells[4].childNodes[0].value;
        ouList.mainForm.transId.value = "<%=amsItemTransH.getTransId()%>";
        ouList.mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        ouList.mainForm.submit();
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
        ouList.mainForm.currDeptId.value = document.mainForm.currDeptId.value;
        ouList.mainForm.currDeptName.value = document.mainForm.currDeptName.value;
        ouList.mainForm.approveOpinion.value = document.mainForm.approveOpinion.value;
    }
</script>
