<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentInfoDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-10-30
  Time: 16:45:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>详细页面</title>
      <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
  </head>
  <script type="text/javascript">
       printTitleBar("详细页面")
   </script>
   <jsp:include page="/message/MessageProcess"/>

  <body>
 <%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
    String action = parser.getParameter("act");
    SfUserDTO userDTO = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);

%>
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentInfoServlet" name="mainForm" method="post">
    <table>
        <%
            if (dto.getBarcode1().equals("")) {
        %>
        <tr>
            <td align="right" width="5%">仪器仪表条码</td>
            <td width="35%"><input name="barcode" type="text" value="<%=userDTO.getCompanyCode()%>" class="noEmptyInput" style="width:46%"></td>
        </tr>
        <%
            }else{


        %>
          <tr>
            <td align="right" width="5%">仪器仪表条码</td>
            <td width="35%"><input name="barcode1" type="text" value="<%=dto.getBarcode1()%>" class="noEmptyInput" style="width:46%"></td>
        </tr>
        <%
            }
        %>
        <input type="hidden" name="itemCode" value="<%=dto.getItemCode()%>">
        <%-- <tr>
            <td align="right" width="5%">仪器仪表代码</td>
            <td width="35%"><input name="itemCode" type="text" value="<%=dto.getItemCode()%>" style="width:46%"></td>
        </tr>--%>
        <tr>
            <td align="right" width="5%">设备类型</td>
            <td  width="15%"><input type="text" name="" class="noEmptyInput" readonly value="仪器仪器仪表"  style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="5%">仪器仪表名称</td>
            <td width="15%"><input type="text" name="itemName" value="<%=dto.getItemName()%>" class="noEmptyInput" readonly  style="width:46%"><a
                    class="linka"
                    style="cursor:'hand'"
                    onclick="do_selectName();"></a>
            </td>
        </tr>
        <tr>
            <td align="right" width="5%">规格型号</td>
            <td width="35%"><input name="itemSpec" type="text" class="noEmptyInput" readonly  value="<%=dto.getItemSpec()%>" style="width:46%"></td>
        </tr>
        <tr>
            <td align="right" width="5%">供应商</td>
            <td width="35%"><input name="vendorName" type="text" class="noEmptyInput" readonly  value="<%=dto.getVendorName()%>" style="width:46%"><a
                    class="linka"
                    style="cursor:'hand'"
                    onclick="do_selectVerdorName();"></a></td>
        </tr>

        <tr>
            <td align="right" width="5%">仪器仪表用途</td>
            <td width="35%"><textarea rows="10" cols="35" name="instruUsage"><%=dto.getInstruUsage()%>
            </textarea></td>
        </tr>

        <tr>
            <td align="right"></td>
            <td align="left">


                <img src="/images/eam_images/close.jpg" alt="关闭"
                     onclick="do_concel();return false;"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="itemCategory" value="INSTRUMENT">
    <input type="hidden" name="barcode1" value="<%=dto.getBarcode1()%>">
    <input type="hidden" name="itemCode" value="<%=dto.getItemCode()%>">

</form>

</body>
</html>
<script type="text/javascript">
    function do_savePlan() {
        var fieldNames = "itemName";
        var fieldLabels = "仪器仪表名称";
        var validateType = EMPTY_VALIDATE;
        var isValid = formValidate(fieldNames, fieldLabels, validateType);
        if (isValid) {
            if (mainForm.barcode1.value == "") {
                mainForm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
            }
            else {
                mainForm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            }
            mainForm.submit();
        }

    }
    function do_concel() {
        with (mainForm) {
            window.close();
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            submit();
        }
    }
    function do_selectName() {
        //alert(mainForm.itemCategory.value);
        var lookUpName = "<%=LookUpConstant.LOOK_UP_SYS_ITEM%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var userPara = "itemCategory=" + mainForm.itemCategory.value;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
    function do_selectVerdorName(){
       var lookUpName = "<%=LookUpConstant.LOOK_UP_PURVEY%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainForm");
            }
        }
    }
</script>