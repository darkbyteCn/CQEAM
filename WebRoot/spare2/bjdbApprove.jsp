<%--
  Created by HERRY.
  Date: 2007-10-26
  Time: 10:24:09
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
<head><title>备件调拨审批</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
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
    String transType = rp.getParameter("transType");
    String sectionRight = rp.getParameter("sectionRight");
    String divHeight = "500";
    if (sectionRight.equals("OUT")) {
        divHeight = "200";
    }
    String itemCodes = "";
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
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">调出地市：</td>
                    <td width="25%"><%=amsItemTransH.getFromOrganizationName()%>
                    </td>
                    <td width="9%" align="right">调入地市：</td>
                    <td width="25%"><%=amsItemTransH.getToOrganizationName()%>
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
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>规格型号
        <%--<img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">--%>
        <%--<img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">--%>
        <img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">
        <img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve(2);">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow();">
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>
   <%-- <div style="left:1px;width:100%;overflow-y:scroll" id="headDiv">
        <table id="headerTable" border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center"><input type="checkBox" name="titleCheck"
                                                     onclick="checkAll('titleCheck','subCheck');" class="headCheckbox">
                </td>
                <td width="12%" align="center">标签号</td>
                <td width="15%" align="center">设备名称</td>
                <td width="15%" align="center">规格型号</td>
            </tr>
        </table>
    </div>--%>
    <script type="text/javascript">
        var columnArr = new Array("","设备名称","规格型号","数量");
        var widthArr = new Array("3%","22%","25%","18%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:<%=divHeight%>px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="xhTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                RowSet details = (RowSet) request.getAttribute("AIT_DETAILS");
                if (rows != null && !rows.isEmpty())  {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                        itemCodes += row.getValue("ITEM_CODE") + ",";
            %>
            <tr id="xhTr<%=i%>" onMouseMove = "style.backgroundColor='#EFEFEF'"
                    onMouseOut = "style.backgroundColor='#ffffff'" onclick="this.cells[0].childNodes[0].checked=true;">
                <td width="3%" align="center"><input type="radio" name="selectItemCode" value="<%=row.getValue("ITEM_CODE")%>:<%=row.getValue("QUANTITY")%>">
                </td>
                <td width="22%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="25%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="18%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>"
                                       class="noborderYellow" style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="xhlineId" id="xhlineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="xhItemCode" id="xhItemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                }
                itemCodes = itemCodes.substring(0,itemCodes.length()-1);
            }
            %>
        </table>
    </div>
</fieldset>
<%
    if(sectionRight.equals("OUT")){
%>
<fieldset>
    <legend>标签号
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <%--<img src="/images/button/pass.gif" alt="通过" id="img3" onClick="do_Approve(1);">--%>
        <%--<img src="/images/button/noPass.gif" alt="不通过" id="img4" onClick="do_Approve(2);">--%>
        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    </legend>

    <script type="text/javascript">
        var columnArr = new Array("checkbox","部件号","设备名称","规格型号","现有量","调拨数量");
        var widthArr = new Array("2%","12%","15%","25%","8%","8%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:250px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode" id="barcode0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="15%" name="itemName" id="itemName0"></td>
                <td width="25%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="onhandQty" id="onhandQty0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="allocateQty" id="allocateQty0"
                                                                           value=""
                                                                           class="noborderYellow"  onblur="checkQty(this);"
                                                                           style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
                    <input type="hidden" name="objectNo" id="objectNo0" value="">
                </td>
            </tr>
            <%

                if (details != null && !details.isEmpty())  {
                Row row = null;
                for (int i = 0; i < details.getSize(); i++) {
                    row = details.getRow(i);
                    if(row.getValue("QUANTITY") == null||row.getValue("QUANTITY").equals("")) {
            %>
            <tr id="mainTr<%=i%>">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode"
                                                                           id="barcode<%=i%>"
                                                                           value="<%=row.getValue("BARCODE")%>"
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="15%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="25%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="onhandQty" id="onhandQty<%=i%>"
                                                                           value="<%=row.getValue("ONHAND_QTY")%>"
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="8%" align="center" class="readonlyInput"><input type="text" name="allocateQty" id="allocateQty<%=i%>"
                                                                           value="<%=row.getValue("ALLOCATE_QTY")%>"
                                                                           class="noborderYellow"
                                                                           style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                    }
                }
                }
            %>
        </table>
    </div>
</fieldset>
<%
    }
%>
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
        var flag = document.mainForm.flag.value;
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