<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentHDTO" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentLDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-2-17
  Time: 11:26:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>创建仪器仪表借用单</title>
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
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>

    <style type="text/css" rel="stylesheet">
        .noneinput {
            BORDER-RIGHT: 0px ridge;
            BORDER-TOP: 0px ridge;
            BORDER-LEFT: 0px ridge;
            BORDER-BOTTOM: 0px ridge;
            font-size: 12px;
        }
    </style>
</head>

<body topMargin=0 leftMargin=0>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsInstrumentHDTO dto = (AmsInstrumentHDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
    SfUserDTO userDTO = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
%>
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet" name="mainForm" method="post">
<script type="text/javascript">
    printTitleBar("创建仪器仪表借用单")
</script>
<table width="100%" border="1" bordercolor="#9FD6FF" bgcolor="F2F9FF" id="table1" style="border-collapse: collapse">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="22">
                    <td width="10%" align="right">借用单号：</td>
                    <td width="18%"><input type="text" name="transNo" class="readonlyInput" readonly
                                           style="width:100%"
                                           value="<%=dto.getTransNo()%>"></td>
                    <td width="10%" align="right">借用人：</td>
                    <td width="21%"><input type="text" name="borrowName" class="readonlyInput"
                                           value="<%=dto.getBorrowName()%>"><a
                              class="linka" style="cursor:'hand'"  onclick="do_selectUserName();">[…]</a></td>
                    <!--<td width="10%" align="right">借用地市：</td>-->
                    <%--<td width="21%"><select  name="borrCityUser" style="width:100%"><%=request.getAttribute(WebAttrConstant.BORROW_OPTION)%></select>--%>
                    <td width="10%" align="right">借用日期：</td>
                    <td width="10%"><input type="text" name="borrowDate" class="readonlyInput" readonly
                                           value="<%=dto.getBorrowDate()%>"></td>
                    <td width="10%" align="right">单据状态：</td>
                    <td width="10%"><input type="text" name="transStatusName" class="readonlyInput" readonly
                                           value="<%=dto.getTransStatusName()%>"></td>
                </tr>
                <tr>
                    <td width="10%" align="right">预计归还日期：</td>
                    <td width="18%"><input type="text" name="preReturnDate"   readonly
                                           style="width:80%"
                                           value="<%=dto.getPreReturnDate()%>"><img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fEndPop('',preReturnDate);"></td>
                    <td width="10%" align="right">借用原因：</td>
                    <td width="21%"><input type="text" name="borrowReason"
                                           value="<%=dto.getBorrowReason()%>"></td>
                    <td width="10%" align="right">备注：</td>
                    <td width="10%"><input type="text" name="remark"
                                           value="<%=dto.getRemark()%>"></td>
                    <td width="10%" align="right"></td>
                    <td width="10%"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset style="width:100%;height:500px;border:2px groove">
    <legend>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_add();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="do_Delete();">
      <%--  <img src="/images/button/borrow.gif" alt="借用" onclick="do_borrow()">
        <img src="/images/eam_images/save.jpg" alt="保存信息" onClick="do_save()">
        <img src="/images/eam_images/ok.jpg" alt="确定" onClick="do_ok()">--%>
        <%--<img src="/images/button/borrow.gif" alt="借用" onclick="do_borrow()">--%>

        <%
          if(!dto.getTransStatus().equals("COMPLETED")){
        %>
        <img src="/images/eam_images/ok.jpg" alt="确定" onClick="do_complete()">
        <%
            }
        %>
        <img src="/images/eam_images/close.jpg" alt="关闭" onclick="window.close()">
    </legend>
     <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" border="1" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="20">
            <td align=center width=5%>
                <input type="checkbox" name="mainCheck" value=""
                       onclick="checkAll('mainCheck','subCheck')" class="headCheckbox"></td>
            <td width="10%" align="center">仪器仪表条码</td>
            <td width="10%" align="center">仪器仪表名称</td>
            <td width="12%" align="center">规格型号</td>
            <td width="8%" align="center">责任人</td>
            <td width="20%" align="center">供应商</td>
            <td width="25%" align="center">仪器仪表用途</td>
        </tr>
    </table>
         </div>
    <div style="width:100%;overflow-y:scroll;height:500px;left:1px">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
            <%
                if (set == null || set.isEmpty()) {
            %>
            <tr height=20px style="display:none">
                <td align=center width=5%><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
                <td align=center width="10%"><input class="noneinput"  style="width:100%" type="text" name="barcode" id="barcode0"></td>
                <td align=center width="10%" name="itemName" id="itemName0"></td>
                <td align=center width="12%" name="itemSpec" id="itemSpec0"></td>
                <td align=center width="8%" name="cname" id="cname0"></td>
                <td align=center width="20%" name="vendorName" id="vendorName0"></td>
                <td align=center width="25%" name="instruUsage" id="instruUsage0"></td>
                <td style="display:none">
                </td>
            </tr>
            <%
            } else {
                for (int i = 0; i < set.getSize(); i++) {
                    AmsInstrumentLDTO dto1 = (AmsInstrumentLDTO) set.getDTO(i);


            %>
            <tr height=20px>
                <td align=center width=5%><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value=""></td>
                <td align=center width="10%"><input type="text" class="noneinput" style="width:100%" name="barcode" id="barcode<%=i%>" value="<%=dto1.getBarcode()%>"></td>
                <%--<td align=center width="10%" name="barcode" id="barcode<%=i%>"><%=dto1.getBarcode()%></td>--%>
                <td align=center width="10%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
                </td>
                <td align=center width="12%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
                </td>
                <td align=center width="8%" name="cname" id="cname<%=i%>"><%=dto1.getCname()%>
                </td>
                <td align=center width="20%" name="vendorName" id="vendorName<%=i%>"><%=dto1.getVendorName()%>
                </td>
                <td align=center width="25%" name="instruUsage" id="instruUsage<%=i%>"><%=dto1.getInstruUsage()%>
                </td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
<input type="hidden" name="type" value="INSTRUMENT">
<input type="hidden" name="transType" value="<%=dto.getTransType()%>">
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="borrowUser" value="<%=dto.getBorrowUser()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="objectCategory" value="77">
<input type="hidden" name="userId">
<input type="hidden" name="userName">
<input type="hidden" name="organizationId">
<input type="hidden" name="addressId">

</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_add() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_INSTRUMENT%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var userPara = "transType=" + mainForm.transType.value + "&organizationId=" + "<%=userDTO.getOrganizationId()%>" + "&objectCategory=" + mainForm.objectCategory.value;
        //LOOKUP传参数 必须和DTO中一致
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        var tab = document.getElementById("mtlTable")
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                 if (!isItemExist(user)) {
                    appendDTORow(tab, user);
                    //增加整行信息时候用到的方法
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
    function do_Delete() {
        var tab = document.getElementById("mtlTable");
        deleteTableRow(tab, 'subCheck');
    }
    function do_ok() {     //生成借用单
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.CREATE%>";
        mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        mainForm.submit();

    }
    function do_borrow() {
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
        mainForm.act.value = "borrow";
        mainForm.submit();
    }
    function do_save() {    //暂存
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>"
        mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        mainForm.submit();
    }
    function getvalues() {
        var tab = document.getElementById("mtlTable");
        if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return false;
        }
        return true;
    }
    function do_repal() {       //撤销
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.CANCELED%>"
        mainForm.act.value = "repal";
        mainForm.submit();
    }
//-----------------------------------------------------------------------
     function do_complete() {     //生成借用单
        if (!getvalues()) {
            return;
        }
         if(mainForm.borrowName.value!==""){
        <%--mainForm.transStatus.value = "<%=DictConstant.CREATE%>";    //单据状态：新增--%>
        mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";    //单据状态：新增
        <%--mainForm.act.value = "<%=AMSActionConstant.BORROW_ACTION%>";--%>
        mainForm.act.value = "borrowProv";
        mainForm.submit();
             }else{
              alert("请选择借用人!");
         }
    }

    function do_tempSave() {    //暂存
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.SAVE_TEMP%>"    //单据状态：暂存
        mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        mainForm.submit();
    }

    function do_cancel() {       //撤销
        if (!getvalues()) {
            return;
        }
        mainForm.transStatus.value = "<%=DictConstant.CANCELED%>"
        mainForm.act.value = "repal";
        mainForm.submit();
    }

   function do_selectUserName(){
    var lookUpName = "<%=LookUpConstant.LOOK_UP_CITYINSTR%>";
    var dialogWidth = 48;
    var dialogHeight = 30;
    //LOOKUP传参数 必须和DTO中一致
    var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
//       alert(users);
    if (users) {
        var user = null;
        for (var i = 0; i < users.length; i++) {
            user = users[i];
            dto2Frm(user, "mainForm");
        }
    }
    document.mainForm.borrowName.value= document.mainForm.userName.value;
}
</script>