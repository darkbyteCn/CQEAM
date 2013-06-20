<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>备件报废单</title>
      <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
      <script language="javascript" src="/WebLibary/js/Constant.js"></script>
      <script language="javascript" src="/WebLibary/js/calendar.js"></script>
      <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
      <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
      <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
      <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
      <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
      <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
      <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
      <script language="javascript" src="/WebLibary/js/ajax.js"></script>
      <script language="javascript" src="/WebLibary/js/json.js"></script>
      <script language="javascript" src="/WebLibary/js/checkBarcode.js"></script>
      <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
      <style>
.approveInput{WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 1px ridge;font-size: 12px;text-align:left;}
</style>
  </head>
  <body leftmargin="1" topmargin="1">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String transNo = amsItemTransH.getTransNo(); 
      float total = 0;
%>
  <form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareBFServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transNo" value="<%=amsItemTransH.getTransNo()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">
<table border="0" width= "100%">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                 <tr height="22">
                    <td width="9%" align="right"></td>
                    <td width="20%"></td>
                    <td width="9%" align="right"></td>
                    <td width="25%"><h3>备件报废单</h3></td>
                   <td width="11%" align="right"></td>
                    <td width="20%"></td>
                </tr>
                 <tr height="22">
                     <td width="9%" align="right"></td>
                    <td width="20%"></td>
                    <td width="9%" align="right"></td>
                    <td width="20%"><h3></h3></td>
                   <td width="9%" align="right"></td>
                    <td width="20%"></td>
                </tr>
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%>
                    </td>
                    <td width="9%" align="right">来源仓库：</td>
                    <td width="25%">
                        <%=request.getAttribute("SPARE_INVBF")%>
                    </td>
                   <td width="11%" align="right">单据类型：</td>
                    <td width="20%">备件报废单
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%>
                    </td>
                    <td align="right">报废日期：</td>
                    <td><%=String.valueOf(amsItemTransH.getCreationDate())%>
                    </td>
                    <td align="right">单据状态：</td>
                    <td>备件报废</td>
                </tr>
                 <tr height="22">
                    <td align="right">备 注：</td>
                    <td colspan="3" align="left"><%=amsItemTransH.getRemark()%></td>
                    <td align="right">报废总金额（元）：</td>
                    <td><%=amsItemTransH.getAttribute4()%></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
        <DIV align=center id='hiddenDiv'>
            <img src="/images/eam_images/page_config.jpg" alt="打印页面设置" onclick="printSetup();">
            <img src="/images/eam_images/print_view.jpg" alt="打印预览" onclick="printView();">
            <img src="/images/eam_images/print.jpg" alt="打印页面" onclick="do_print();">
            <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
        </DIV>
 <div style="left:1px;width:100%;overflow-y:visible" class="crystalScroll">
    <table  border="1" width="100%"  cellpadding="0" cellspacing="0" bordercolor="#666666">
        <tr height="30">
            <td width="10%" align="center">设备名称</td>
            <td width="15%" align="center">设备型号</td>
            <td width="10%" align="center">设备类型</td>
            <td width="10%" align="center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="5%" align="center">报废数量</td>
            <td width="5%" align="center">报废金额(元)</td>
            <td width="10%" align="center">报废原因</td>
        </tr>
    </table>
</div>
<div style="width:100%;left:1px;margin-left:0;overflow-y:visible;" class="crystalScroll">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%if (rows == null || rows.isEmpty()) {%>
            <tr id="mainTr0" style="display:none" height="30" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="10%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="10%" name="itemCategory" id="itemCategory0"></td>
                <td width="10%" name="spareUsage" id="spareUsage0"></td>
                <td width="10%" name ="vendorName" id = "vendorName0"></td>
                <td width="5%" align="center"><input type="text" name="quantity" id="quantity0" value="" style="width:100%;text-align:right">
                </td>
                <td width="5%" align="center"><input type="text" name="bfje" id="bfje0" value="" style="width:100%;text-align:right">
                </td>
                <td width="10%" align="left"><input type="text" name="remarkl" id="remarkl0" value="" style="width:100%;text-align:left">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="barcode" id="barcode0" value="">
                </td>
            </tr>
            <%
                }else{
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                            String price = String.valueOf(row.getValue("BFJE"));
                            if (price.equals("")) {
                                price = "0";
                            }
                           float  money = Float.parseFloat(price);
                            total += money;
            %>
            <tr id="mainTr<%=i%>" height="30" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="10%" name="itemCategory" id="itemCategory<%=i%>"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td width="10%" name="spareUsage" id="spareUsage<%=i%>"><%=row.getValue("SPARE_USAGE")%>
                </td>
                <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td width="5%" align="center"><%=row.getValue("QUANTITY")%>
                </td>
                <td width="5%" align="center"><%=row.getValue("BFJE")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("REMARK")%>
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="barcode" id="barcode<%=i%>" value="<%=row.getValue("BARCODE")%>">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
 <div style="left:1px;width:100%;position:relative;top:300px;">
<table border="0" bordercolor="#666666" style="left:1px;width:100%;">
                <tr height="30">
                    <td width="8%" align="right">库管员：</td>
                    <td width="12%"><%if(user.getOrganizationId() == 82 && transNo.substring(0,4).equals("4110")) {%><img src="/images/fanjianqiang.jpg"><%}else{%><input type="text"  style="width:100%" readonly  class="approveInput" value=""><%}%></td>
                    <td width="8%" align="right">审批人：</td>
                    <td width="12%"><%if(user.getOrganizationId() == 82 && transNo.substring(0,4).equals("4110")) {%><img src="/images/qianming.JPG"><%}else{%><input type="text"  style="width:100%"  readonly class="approveInput" value=""><%}%></td>
                </tr>
</table>
 </div>
<div id="showMsg" style="color:red"><jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/></div>
 <OBJECT id=wb height=0 width=0  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
  </form>
<%=WebConstant.WAIT_TIP_MSG%>
  </body>
<script type="text/javascript">
   function do_print() {
        if (confirm('确定打印吗？')) {
            document.getElementById('hiddenDiv').style.visibility = 'hidden';
            document.getElementById('wb').execwb(6, 6);
            document.getElementById('hiddenDiv').style.visibility = 'visible';
        }
    }
    //打印设置函数
    function printSetup() {
        document.getElementById('wb').execwb(8, 1);
    }

    //打印预览函数
    function printView() {
        document.getElementById('hiddenDiv').style.visibility = 'hidden';
        document.getElementById('wb').execwb(7, 1);
        document.getElementById('hiddenDiv').style.visibility = 'visible';
    }
</script>
</html>