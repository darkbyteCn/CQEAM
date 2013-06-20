<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-3-18
  Time: 18:22:34
  Function; 备件盘点工单的明细.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>备件盘点差异明细</title>
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
  </head>
  <body leftmargin="1" topmargin="1" >
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
      int total = 0;
%>
  <form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.SpareOrderServlet" method="post">
<%--<jsp:include page="/flow/include.jsp" flush="true"/>--%>
<input type="hidden" name="act" value="">
<%--<input type="hidden" name="toOrganizationId" value="<%=amsItemTransH.getToOrganizationId()%>">--%>
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><%=amsItemTransH.getTransNo()%> </td>
                    <td width="9%" align="right">仓库：</td>
                    <td width="25%"><%=amsItemTransH.getFromObjectName()%></td>
                   <td width="11%" align="right">单据类型：</td>
                    <td width="20%">盘点单据</td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><%=amsItemTransH.getCreatedUser()%></td>
                    <td align="right">日期：</td>
                    <td><%=amsItemTransH.getCreationDate()%></td>
                    <td align="right"></td>
                    <td></td>
                </tr>
                 <tr height="22">
                    <td align="right">备 注：</td>
                    <td colspan="3" align="left"><textarea class="blueborder" cols="90" name="remark" style="width:90%" rows="2"><%=amsItemTransH.getRemark()%></textarea></td>
                    <td align="right"></td>
                    <td><input style="width:100%" type="text" class="noborderGray" id="attribute4" name="attribute4" value="<%=amsItemTransH.getAttribute4()%>"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<fieldset>
    <legend>
        <%
            //单据非完成状态并且当前用户是创建人才有操作权限
//            if(!amsItemTransH.getTransStatus().equals(DictConstant.COMPLETED)&& amsItemTransH.getCreatedBy().equals(user.getUserId())){
        %>
        <%--<img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">--%>
        <%--<img src="/images/button/deleteLine.gif" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">--%>
        <%--<img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_submit();">--%>
        <%
//            }
        %>
            <img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
        <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
    </legend>
    <script type="text/javascript">
        var columnArr = new Array("checkbox","部件号","设备名称","规格型号","设备厂商","现有数量","盘点数量","差异","原因");
        var widthArr = new Array("2%","12%","10%","10%","10%","8%","8%","10%","10%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:550px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%if (rows == null || rows.isEmpty()) {%>
            <tr id="mainTr0" style="display:none">
                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="12%" align="center" class="readonlyInput"><input type="text" name="barcode" id="barcode0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>
                <td width="10%" name="itemName" id="itemName0"></td>
                <td width="10%" name="itemSpec" id="itemSpec0"></td>
                <td width="10%" name ="vendorName" id = "vendorName0"></td>
                <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty0" readonly
                                                                           value="" class="noborderGray"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="8%" align="center"><input type="text" name="quantity" id="quantity0"
                                                                           value="" class="blueborderYellow"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="10%" align="center"><input type="text" name="diffNum" id="diffNum0"
                                                                           value="" class="blueborderYellow"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="10%" align="left"><input type="text" name="remarkl" id="remarkl0"   value="" class="blueborderYellow"
                                                                           style="width:100%;text-align:left">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                </td>
            </tr>
            <%
                }else{
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
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
                <td width="10%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="10%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=row.getValue("VENDOR_NAME")%>
                </td>
                <td width="8%" align="center"><input type="text" name="onhandQty" id="onhandQty<%=i%>"  readonly
                                                                           value="<%=row.getValue("ONHAND_QTY")%>" class="noborderGray"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="8%" align="center"><input type="text" name="quantity" id="quantity<%=i%>"  onblur="checkQty(this);"
                                                                           value="<%=row.getValue("QUANTITY")%>" class="blueborderYellow"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="10%" align="center"><input type="text" name="diffNum" id="diffNum<%=i%>"       onchange="validatePrice();"
                                                                           value="<%=row.getValue("DIFF_NUM")%>" class="noborderGray"
                                                                           style="width:100%;text-align:right">
                </td>
                <td width="10%" align="center"><input type="text" name="remarkl" id="remarkl<%=i%>"   value="<%=row.getValue("REMARK")%>"  class="blueborderYellow"
                                                                           style="width:100%;text-align:left">
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
<div id="showMsg" style="color:red"><jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/></div>
<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
  </form>
<%=WebConstant.WAIT_TIP_MSG%>
  </body>
<script type="text/javascript">
 function do_print() {
      var headerId=document.mainForm.transId.value;
        var url="/servlet/com.sino.ams.spare.servlet.SpareOrderServlet?act=print&transId="+headerId;
        var  style = 'width=1024,height=670,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, "", style);
    }
</script>
</html>