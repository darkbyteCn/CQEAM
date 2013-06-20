<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  Created by HERRY.
  Date: 2007-10-17
  Time: 15:48:45
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>备件调拨单</title>
      <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
  </head>
  <body leftmargin="1" topmargin="1"  onload="init();">
 <jsp:include page="/message/MessageProcess"/>
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    String cityOption = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.servlet.BjdbServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=amsItemTransH.getTransId()%>">
<input type="hidden" name="transType" value="<%=amsItemTransH.getTransType()%>">
<input type="hidden" name="transStatus" value="<%=amsItemTransH.getTransStatus()%>">
<input type="hidden" name="createdBy" value="<%=amsItemTransH.getCreatedBy()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="9%" align="right">单据号：</td>
                    <td width="20%"><input type="text" name="transNo" value="<%=amsItemTransH.getTransNo()%>" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="9%" align="right">调出地市：</td>
                    <td width="25%"><select name="fromOrganizationId" style="width:80%"><%=cityOption%>
                                           </select>

                    </td>
                    <td width="9%" align="right">调入地市：</td>
                    <td width="25%"><select name="toOrganizationId"  style="width:80%"><%=cityOption%>
                                           </select>
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" name="createdUser" value="<%=amsItemTransH.getCreatedUser()%>"
                                           readonly
                                           class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="creationDate" readonly style="width:80%"
                                           value="<%=amsItemTransH.getCreationDate()%>"
                                           class="blueborderGray">
                    </td>
                    <td align="right">单据状态：</td>
                    <td><input type="text" name="transStatusName" readonly
                                           value="<%=amsItemTransH.getTransStatusName()%>"
                                           class="blueborderGray"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset>
    <legend>
        <img src="/images/eam_images/add_data.jpg" alt="添加数据" onclick="do_SelectItem();">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
        <img src="/images/eam_images/save.jpg" alt="保存" id="img3" onClick="do_SavePo(1);">
        <img src="/images/eam_images/ok.jpg" alt="确定" id="img4" onClick="do_SavePo(2);">
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
        var columnArr = new Array("checkbox","设备名称","规格型号","数量");
        var widthArr = new Array("2%","12%","15%","8%");
        printTableHead(columnArr,widthArr);
    </script>
    <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
            <%
                RowSet rows = (RowSet) request.getAttribute("AIT_LINES");
                if (rows == null || rows.isEmpty()) {
            %>
            <tr id="mainTr0" style="display:none">

                <td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <%--<td width="12%" align="center" class="readonlyInput"><input type="text" name="barcodeNo" id="barcodeNo0"
                                                                           value=""
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>--%>
                <td width="12%" name="itemName" id="itemName0"></td>
                <td width="15%" name="itemSpec" id="itemSpec0"></td>
                <td width="8%" ><input type="text" name="quantity" id="quantity0" value=""
                                       class="noborderYellow" style="width:100%;text-align:center"></td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId0" value="">
                    <input type="hidden" name="itemCode" id="itemCode0" value="">
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
                <%--<td width="12%" align="center" class="readonlyInput"><input type="text" name="barcodeNo"
                                                                           id="barcodeNo<%=i%>"
                                                                           value="<%=row.getValue("BARCODE_NO")%>"
                                                                           readonly class="noborderGray"
                                                                           style="width:100%;text-align:center">
                </td>--%>
                <td width="12%"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>"
                                       class="noborderYellow" style="width:100%;text-align:center">
                </td>
                <td style="display:none">
                    <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=row.getValue("LINE_ID")%>">
                    <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=row.getValue("ITEM_CODE")%>">

                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</fieldset>
</form>
</body>
<script type="text/javascript">
    function init() {
        var fromOu = "<%=amsItemTransH.getFromOrganizationId()%>";
        var toOu = "<%=amsItemTransH.getToOrganizationId()%>";
        selectSpecialOption("fromOrganizationId",fromOu);
        selectSpecialOption("toOrganizationId",toOu);
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
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.BJ_SYSTEM_ITEM%>";
        var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
        var assets = window.showModalDialog(url, null, popscript);
        if (assets) {
            var data = null;
            var tab = document.getElementById("dataTable");
            for (var i = 0; i < assets.length; i++) {
                data = assets[i];
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
</script>
</html>