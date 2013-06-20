<%--
  Created by HERRY.
  Date: 2008-3-12
  Time: 17:40:17
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<head><title>备件调拨确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
</head>
<body leftmargin="1" topmargin="1" onload="init();">
<jsp:include page="/message/MessageProcess"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    RequestParser rp = new RequestParser();
    rp.transData(request);
//    String transType = rp.getParameter("transType");
//    String sectionRight = rp.getParameter("sectionRight");
    String divHeight = "500";
    /*if (sectionRight.equals("OUT")) {
        divHeight = "200";
    }*/
//    String itemCodes = "";
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.servlet.BjdbApproveServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="toObjectNo" value="<%=amsItemTransH.getToObjectNo()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="8%" align="right">单据号：</td>
                    <td width="16%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="8%" align="right">调出地市：</td>
                    <td width="16%"><%=amsItemTransH.getFromOrganizationName()%>
                    </td>
                    <td width="8%" align="right">调出仓库：</td>
                    <td width="18%"><%=amsItemTransH.getFromObjectName()%>
                    </td>
                    <td width="8%" align="right">调入地市：</td>
                    <td width="16%"><%=amsItemTransH.getToOrganizationName()%>
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
                    <td><%=amsItemTransH.getTransStatusName()%></td>
                </tr>
                <tr>
                    <td align="right" height="50">备注：</td>
                    <td colspan="9"><%=amsItemTransH.getRemark()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/eam_images/ok.jpg" alt="确认" onclick="do_Approve(1);">
        <%--<img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">--%>
        <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
        <%--<img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve(2);">--%>
        <%--<img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow();">--%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("物料编码","设备名称","规格型号","现有数量","调拨数量");
        var widthArr = new Array("15%","22%","25%","10%","10%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:<%=divHeight%>px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="xhTable" cellpadding="1" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                RowSet details = (RowSet) request.getAttribute("AIT_DETAILS");
                if (rows != null && !rows.isEmpty())  {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
//                        itemCodes += row.getValue("ITEM_CODE") + ",";
            %>
            <tr id="xhTr<%=i%>" height="20" onMouseMove = "style.backgroundColor='#EFEFEF'"
                    onMouseOut = "style.backgroundColor='#ffffff'" onclick="this.cells[0].childNodes[0].checked=true;">
                <td width="15%"><%=row.getValue("BARCODE")%>
                </td>
                <td width="22%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="25%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="10%" align="right"><%=row.getValue("ONHAND_QUANTITY")%>
                </td>
                <td width="10%" align="right"><%=row.getValue("QUANTITY")%>
                </td>
                <td style="display:none">
                    <%--<input type="hidden" name="xhlineId" id="xhlineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">--%>
                    <input type="hidden" name="xhItemCode" id="xhItemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                }
//                itemCodes = itemCodes.substring(0,itemCodes.length()-1);
            }
            %>
        </table>
    </div>
</fieldset>

</form>
</body>
<script type="text/javascript">
    function init() {
    }
    function do_SelectObject() {
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>", 48, 30,"organizationId=<%=user.getOrganizationId()%>");
        if(projects){
//            dto2Frm(projects[0], "form1");
            document.mainForm.toObjectName.value = projects[0].workorderObjectName;
            document.mainForm.toObjectNo.value = projects[0].workorderObjectNo;
            document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
        }
    }
    function do_SelectItem() {
        var selectedItemCode = getRadioValue("selectItemCode");
        if(selectedItemCode == null || selectedItemCode == ""){
            alert("请先选择一种设备名称型号！")  ;
            return;
        }
        var ic = selectedItemCode.split(":");
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJSL_ITEM_INFO2%>&itemCodes="+ic[0]+"&organizationId=<%=amsItemTransH.getFromOrganizationId()%>";
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
    function do_SavePo(flag){
        if(flag == 1){
            document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        }else{
            document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
            document.mainForm.transStatus.value = "<%=DictConstant.IN_PROCESS%>";
        }
        document.mainForm.submit();
    }
    function do_Approve(flag){
        document.mainForm.flag.value = flag;
        var paramObj = new Object();
        paramObj.orgId = "<%=user.getOrganizationId()%>";
        paramObj.useId = "<%=user.getUserId()%>";
        paramObj.groupId = "<%=user.getCurrGroupId()%>";
        paramObj.procdureName = "备件调拨流程";
        paramObj.flowCode = "";
        paramObj.submitH = "submitH()";
        assign(paramObj);
    }
    function submitH(){
        var flag = Number(document.mainForm.flag.value);
        var actVal = "";
        switch(flag){
            case 1: actVal = "<%=WebActionConstant.APPROVE_ACTION%>"; break;
            case 2: actVal = "<%=WebActionConstant.REJECT_ACTION%>"; break;
            case 3: actVal = "<%=WebActionConstant.RECEIVE_ACTION%>"; break;
            default :actVal = "<%=WebActionConstant.APPROVE_ACTION%>";
        }
//        alert("actVal="+actVal)
        document.mainForm.act.value = actVal;
        document.mainForm.submit();
    }

function viewFlow() {
    var actId = document.getElementById("actId").value;
    if (actId == '') {
        alert("没有找到流程的信息");
        return;
    }
    var url = "/servlet/ViewFlowServlet?actId=" + actId;
    var style = "width=1020,height=668,top=0,left=0";
    var winName = "flowDetail";
    window.open(url, winName, style);
}

   function checkQty(obj){
       var quantity; //该设备型号分配审批通过的数量
       var tempQty = 0;
       var selectedItemCode = getRadioValue("selectItemCode");
       var si = selectedItemCode.split(":");
       quantity = si[1];
       var itemCode = document.getElementById("itemCode"+obj.id.substring(11,obj.id.length)).value;
       var allocateQty = document.getElementById("allocateQty"+obj.id.substring(11,obj.id.length)).value;
       var itemCodes  = document.getElementsByName("itemCode");
       var tempId;
       var tempObj;
       if(itemCodes.length){
           tempObj = itemCodes[0];
           for(var i=0; i<itemCodes.length;i++){
               var item = itemCodes[i];
               if(item.value == itemCode){
                   tempId = item.id.substring(8,item.id.length);
                   tempQty += Number(document.getElementById("allocateQty"+tempId).value);
               }
           }

       }else{
           tempId = itemCodes.id.substring(8,item.id.length);
//            alert("tempId="+tempId);
           tempQty += Number(document.getElementById("allocateQty"+tempId).value);
//                   alert("tempQty="+tempQty);
           tempObj = itemCodes;
       }
       if(tempQty > Number(quantity)){
           alert("调拨数量不能超过分配数量，请重新输入！");
           document.getElementById("allocateQty"+tempId).focus();
       }
   }
</script>
</html>