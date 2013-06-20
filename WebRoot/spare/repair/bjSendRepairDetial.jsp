<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-12
  Time: 9:23:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.spare.repair.dto.AmsCustomerInfoDTO" %>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>
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

</head>
<body leftmargin="1" topmargin="1">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>

<%
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    AmsCustomerInfoDTO cust = (AmsCustomerInfoDTO) request.getAttribute("CUSTOMER_INFO");
//    ServletConfigDTO configDto=(ServletConfigDTO)getServletConfig();
 /*     RowSet rows1 = (RowSet) request.getAttribute("CUSTOMER_INFO");
    if(rows1==null||rows1.equals("")){

    }else{
            Row row = null;
        row=rows1.getRow(1) ;*/

    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet" method="post">
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="act" value="">
<%--<input type="hidden" name="flowSaveType" value="<%=%>">--%>
<%--<input type="hidden" name="distributeGroupId" value="<%=%>">--%>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" bgcolor="#F2F9FF">
                <tr height="30">
                     <td width="10%" align="right">用户名称：</td>
                    <td width="15%">陕西省移动通信公司</td>
                </tr>
                <tr height="24">
                     <td width="10%" align="right">送修方：</td>
                    <td width="10%"><%=cust.getCustomer()%></td>
                    <td width="10%" align="right"> 维修公司：</td>
                    <td width="40%"><%=amsItemTransH.getCompany()%></td>
                </tr>
                <tr height="24">
                     <td width="10%" align="right">送修方联系人：</td>
                   <td width="40%"><%=cust.getContact()%></td>
                    <td width="10%" align="right">联系人：</td>
                    <td width="40%"><%=amsItemTransH.getContact()%></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">送修方电话：</td>
                    <td width="8%"><%=cust.getTel()%></td>

                    <td width="10%" align="right">联系电话：</td>
                    <td width="40%"><%=amsItemTransH.getTel()%></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">送修方传真：</td>
                    <td width="10%"><%=cust.getFax()%></td>
                    <td width="10%" align="right">传真：</td>
                    <td width="10%"><%=amsItemTransH.getFax()%></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">委托书编号：</td>
                    <td width="10%"><%=amsItemTransH.getAttribute1()%></td>
                    <td width="10%" align="right">保值金额</td>
                    <td width="10%"><%=amsItemTransH.getAttribute3()%></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">送修方地址：</td>
                    <td width="10%"><%=cust.getAddress()%></td>
                    <td width="10%" align="right">联系地址：</td>
                    <td width="40%"><%=amsItemTransH.getAddress()%></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    
    <legend>
          <DIV align=center id='hiddenDiv' >
        <img src="/images/button/pageSet.gif" alt="打印页面设置" onclick="printSetup()">
        <img src="/images/button/printView.gif" alt="打印预览" onclick="printView()">
        <img src="/images/button/print.gif" alt="打印页面" onclick="do_print()">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
         </DIV>
    </legend>

    <script type="text/javascript">
        var columnArr = new Array( "部件号", "设备名称", "规格型号", "序列号");
        var widthArr = new Array( "12%", "12%", "15%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none" height="24">


                <td width="12%"><input type="text" name="barcode" id="barcode0" class="noborderGray" style="width:100%">
                </td>
                <td width="12%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%"  name="quantity" id="quantity0"></td>
                <%--<td style="display:none">--%>
                    <%--<input type="hidden" name="lineId" id="lineId0" value="">--%>
                    <%--<input type="hidden" name="itemCode" id="itemCode0" value="">--%>
                <%--</td>--%>
            </tr>
            <%
            } else {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr id="mainTr<%=i%>" height="24">

               
                <td width="12%"><input type="text" name="barcode" class="noborderGray" style="width:100%" readonly  
                                       value="<%=row.getValue("BARCODE")%>" id="barcode<%=i%>"></td>
                <td width="12%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%"><%=row.getValue("SERIAL_NO")%>
                </td>
                <%--<td style="display:none">--%>
                    <%--<input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">--%>
                    <%--<input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">--%>
                <%--</td>--%>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
    <OBJECT id=wb height=0 width=0
         classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
</form>
<script type="text/javascript">
  function do_print(){
//       if (confirm('确定打印吗？')) {
            document.getElementById('hiddenDiv').style.visibility = 'hidden';
//      alert(document.getElementById("wb"));
            document.getElementById("wb").execwb(6, 6);
            document.getElementById('hiddenDiv').style.visibility = 'visible';
//        }
  }
      function printSetup() {
        document.getElementById("wb").execwb(8, 1);
    }

    //打印预览函数
    function printView() {
        document.getElementById('hiddenDiv').style.visibility = 'hidden';
        document.getElementById("wb").execwb(7, 1);
         document.getElementById('hiddenDiv').style.visibility = 'visible';
    }
</script>
</body>
</html>

