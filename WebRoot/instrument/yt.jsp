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
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-10-26
  Time: 15:57:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title></title>
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
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>

</head>
<script type="text/javascript">
    printTitleBar("创建仪器仪表借用单")
</script>
  <body>
  <%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsInstrumentHDTO dto = (AmsInstrumentHDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
    SfUserDTO userDTO = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String action = parser.getParameter("act");

%>
  <form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet" name="mainForm" method="post">
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
                        <td width="21%"><input type="text" name="borrowName" class="readonlyInput" readonly
                                               value="<%=dto.getBorrowName()%>"></td>
                        <td width="10%" align="right">借用日期：</td>
                        <td width="10%"><input type="text" name="borrowDate" class="readonlyInput" readonly
                                               value="<%=dto.getBorrowDate()%>"></td>
                        <td width="10%" align="right">单据状态：</td>
                        <td width="10%"><input type="text" name="transStatus" class="readonlyInput" readonly
                                               value="<%=dto.getTransStatus()%>"></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
      
   <input type="hidden" name="type" value="INSTRUMENT">
    <input type="hidden" name="transType" value="<%=dto.getTransType()%>">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="borrowUser" value="<%=dto.getBorrowUser()%>">
    
</form>
  </body>
</html>