<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsURLList" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-12
  Time: 9:23:08
--%>
<html>
<head><title>备件送修</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<body leftmargin="1" topmargin="1" >
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>

<%
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String procName = "备件送修流程";
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
    boolean isFirstNode=sectionRight.equals("")||sectionRight.equals("NEW");
    boolean isPrint=sectionRight.equals("2")||sectionRight.equals("3");
    boolean isNode = sectionRight.equals("1")||sectionRight.equals("2")||sectionRight.equals("3")||sectionRight.equals("4")||sectionRight.equals("NEW");
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="24">
                    <td width="10%" align="right">单据号：</td>
                    <td width="15%"><input type="text" name="transNo" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=amsItemTransH.getTransNo()%>"></td>
                    <td width="10%" align="right"> 维修公司：</td>
                    <td width="40%"><input type="text" name="company" class="blueborderYellow"
                                           style="width:70%"   readonly
                                           value="<%=amsItemTransH.getVendorName()%>"><a href= "#" onclick="chooseBJWXCS();" title = "点机选择工程" class="linka">[…]</a></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">创建人：</td>
                    <td width="15%"><input type="text" name="createdUser" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=amsItemTransH.getCreatedUser()%>"></td>
                     <td width="10%" align="right">委托书编号：</td>
                    <td width="40%"><input type="text" name="att1" class="blueborder" style="width:70%"
                                           value="<%=amsItemTransH.getAttribute1()%>"></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">创建时间：</td>
                    <td width="15%"><input type="text" name="creationDate" class="blueborderGray" readonly
                                          style="width:100%"
                                          value="<%=amsItemTransH.getCreationDate()%>"></td>
                    <td width="10%" align="right">承运人：</td>
                    <td width="40%"><input type="text" name="att2" class="blueborder"
                                           style="width:70%"
                                           value="<%=amsItemTransH.getAttribute2()%>"></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">单据状态：</td>
                    <td width="15%"><input type="text" name="transStatusName" class="blueborderGray" readonly
                                           style="width:100%"
                                           value="<%=amsItemTransH.getTransStatusName()%>"></td>
                     <td width="10%" align="right">保值金额：</td>
                    <td width="40%"><input type="text" name="att3" class="blueborder"
                                           style="width:70%"   onblur = "do_verify();"
                                           value="<%=amsItemTransH.getAttribute3()%>"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <%
            //单据非完成状态并且当前用户是创建人才有操作权限
            if (isFirstNode) {
        %>
        <%--<img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">--%>
        <!--<img src="/images/button/deleteLine.gif" alt="删除行"-->
             <!--onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">-->
        <%}%>
        <%--<img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_Save();">--%>
        <%--<img src="/images/button/submit.gif" alt="提交" id="img4" onClick="do_submit();">--%>
        <%--<img src="/images/button/viewFlow.gif" alt="查看流程" id="img5" onClick="viewFlow();">--%>
        <img src="/images/button/printView.gif" alt="显示打印页面" onclick="do_print()">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "部件号", "设备名称", "规格型号","厂商","待修数量","送修数量");
        var widthArr = new Array("2%", "12%", "12%", "15%","10%","8%","8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%"><input type="text" name="barcode" id="barcode0" class="noborderGray" style="width:100%">
                </td>
                <td width="12%" ><input type="text" name="itemName" id="itemName0" class="noneborderInput" style="width:100%"></td>
                <td width="15%" ><input type="text" name="itemSpec" id="itemSpec0" class="noneborderInput" style="width:100%"></td>
                 <td width="10%" name ="vendorName" id = "vendorName0"></td>
                 <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty0" readonly
                                                                           value="" class="noborderGray"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="8%"><input type="text" name="quantity" id="quantity0" value=""    onblur="checkQty(this);"
                                      class="blueborderYellow" style="width:100%"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                </td>
            </tr>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%"><input type="text" name="barcode" class="noborderGray" style="width:100%"
                                       value="<%=row.getValue("BARCODE")%>" id="barcode<%=i%>"></td>
                <td width="12%"><input type="text" name="itemName" id="itemName<%=i%>" class="noneborderInput" style="width:100%" value="<%=row.getValue("ITEM_NAME")%>">
                </td>
                <td width="15%"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="noneborderInput" style="width:100%" value="<%=row.getValue("ITEM_SPEC")%>">
                </td>
                <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>"  readonly
                                                                           value="<%=row.getValue("ONHAND_QTY")%>" class="noborderGray"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="8%"><input type="text" name="quantity" id="quantity<%=i%>"     onblur="checkQty(this);"
                                      value="<%=row.getValue("QUANTITY")%>"
                                      class="blueborderYellow" style="width:100%">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="fromObjectNo" value="<%=amsItemTransH.getFromObjectNo()%>">
<input type="hidden" name="procName" value="<%=procName%>">
<input type="hidden" name="flowSaveType" value="<%=DictConstant.FLOW_COMPLETE%>">
<input type="hidden" name="fromDept" value="<%=amsItemTransH.getFromDept()%>">
<input type="hidden" name="fromOrganizationId" value="<%=amsItemTransH.getFromOrganizationId()%>">
<input type="hidden" name="vendorCode" value = "<%=amsItemTransH.getVendorCode()%>">
</form>
</body>
</html>
<script type="text/javascript">
function do_Save() { //暂存
    if (getvalues()) {
        if (validateData()) {
            if (mainForm.barcode.value == "") {
                alert("请确认数据!");
            } else {
                mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                mainForm.flowSaveType.value = "<%=DictConstant.FLOW_SAVE%>"
                doSubmit();
            }
        }
    }
}
    function do_submit() {
        /* if (mainForm.barcode.value == "" || mainForm.toObjectName.value == "") {
          alert("请确认送修数据和库存!");

      } else {*/
      if(doVendor()){
        if (getvalues()) {
//            alert(document.mainForm.vendorCode.value);
            if (validateData()) {
                mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";

                var paramObj = new Object();
                var procdureName = mainForm.procName.value;
                var groupId = mainForm.fromDept.value;
                procdureName = "<%=procName%>";
                paramObj.orgId = "<%=sfUser.getOrganizationId()%>";
                paramObj.useId = "<%=sfUser.getUserId()%>";
                paramObj.groupId = groupId;
                paramObj.procdureName = procdureName;
                paramObj.flowCode = "";
                paramObj.submitH = "doSubmit()";
                assign(paramObj);
            }
        }
    }
 }
    function doSubmit() {
        mainForm.submit();
    }

   function doVendor(){
         if(document.mainForm.company.value==""){
             alert("请选择服务方公司");
             return false;
         }
       return true;
   }
    function do_selectName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BF%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        //        lookProp.setMultipleChose(false);
        var userPara = "objectCategory=73";
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                mainForm.toObjectName.value = users[0].workorderObjectName;
                mainForm.toObjectNo.value = users[0].workorderObjectNo;
                mainForm.addressId.value = users[0].addressId;
            }
        }
    }
    function do_SelectItem() {
        var fromObjectNo = mainForm.fromObjectNo.value;
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BJSX%>&organizationId=<%=sfUser.getOrganizationId()%>&objectNo="+fromObjectNo;
        var popscript = "dialogWidth:65;dialogHeight:33;center:yes;status:no;scrollbars:no";
        var items = window.showModalDialog(url, null, popscript);
        if (items) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < items.length; i++) {
                data = items[i];
                if(!isItemExist(data)){
                appendDTORow(tab, data);
                    }
            }
        }
    }
    function do_print() {
        var url = "/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet?act=print&transId=" + mainForm.transId.value;
//                var popscript = "center:yes;dialogwidth:1020px;dialogheight:700px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
        var popscript =  'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, null, popscript);
    }



//   function do_print() {
//        var url = "/spare/repair/bjSendRepairPrint.jsp";
        //        var popscript = "center:yes;dialogwidth:1020px;dialogheight:700px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
//        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
//        window.open(url, null, popscript);
//    }

    <%--  function do_submit() {
        mainForm.flowSaveType.value="<%=DictConstant.FLOW_COMPLETE%>";
        var paramObj = new Object();
        paramObj.orgId = "<%=sfUser.getOrganizationId()%>";
        paramObj.useId = "<%=sfUser.getUserId()%>";
        paramObj.groupId = mainForm.distributeGroupId.value;
        paramObj.procdureName = "<%=AmsFlowConstant.NET_NEW%>";
        paramObj.flowCode = "";
        paramObj.submitH = "do_Save()";
        assign(paramObj);
    }--%>

    function validateData(){
        var validate = false;
        var fieldNames = "quantity";
        var fieldLabels = "数量";
        var validateType = EMPTY_VALIDATE;
        validate = formValidate(fieldNames, fieldLabels, validateType);
        if(validate){
            validateType = POSITIVE_INT_VALIDATE;
            validate = formValidate(fieldNames, fieldLabels, validateType);
        }
        return validate;
    }

function getvalues() {
        var tab = document.getElementById("dataTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
    }


function isItemExist(itemObj) {
  var retVal = false;
  var tab = document.getElementById("dataTable");
  if (tab.rows) {
      var trObjs = tab.rows;
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


function checkQty(obj){
      var id = obj.id.substring(8,obj.id.length);
      var qtyObj = document.getElementById("quantity"+id);
      var onhandQty = document.getElementById("onhandQty"+id).value;
      if(Number(qtyObj.value)>Number(onhandQty)){
          alert("送修数量不能大于待修数量，请重新输入！");
          qtyObj.focus();
      }
  }


//  function checkQty(obj){
//        var id = obj.id.substring(8,obj.id.length);
//        var qtyObj = document.getElementById("quantity"+id);
//        var onhandQty = document.getElementById("onhandQty"+id).value;
//        if(Number(qtyObj.value)>Number(onhandQty)){
//            alert("出库数量不能大于现有量，请重新输入！");
//            qtyObj.focus();
//        }
//    }


function chooseBJWXCS() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_BJWXC%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
//        dto2Frm(projects[0], "mainForm");
           var user = null;
            for (var i = 0; i < projects.length; i++) {
                mainForm.company.value = projects[0].vendorName;
//                mainForm.vendorName.value = projects[0].vendorName;
                mainForm.vendorCode.value = projects[0].vendorCode;
            }
    }
//    alert(mainForm.vendorCode.value);
}

function do_verify() {
    var fieldNames = "att3";
    var fieldLabels = "保值金额";
    if (!formValidate(fieldNames, fieldLabels, POSITIVE_VALIDATE)) {
    }
}


function initPage() {
    window.focus();
	var fromGroup = mainForm.fromDept.value;
	if(fromGroup == ""){
		do_SelectCreateGroup();
	}else{
        alert(fromGroup);
    }
}


function do_SelectCreateGroup(){
	var fromGroup = mainForm.fromDept.value;
	var url = "/servlet/com.sino.ams.spare.servlet.BJGroupChooseServlet";
	var popscript = "dialogWidth:20;dialogHeight:15;center:yes;status:no;scrollbars:no;help:no";

    var group = window.showModalDialog(url, null, popscript);
//     if (aa) {
//        document.mainFrm.excel.value = aa;
//        document.mainFrm.act.value = "excel";
//        document.mainFrm.submit();
//    }
    if(group){
//		dto2Frm(group, "mainForm");
        document.mainForm.fromDept.value = group;
    }
}

</script>