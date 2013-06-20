<%--
  User: zhoujs
  Date: 2008-3-23 21:17:41
  Function:送修返还单界面
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<html>
<head><title>备件送修返还单</title>
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
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <style>
.approveInput{WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 1px ridge;font-size: 12px;text-align:left;}
</style>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) request.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
    String action = parser.getParameter("act");
    DTOSet set = (DTOSet) request.getAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO);
    String transNo = dto.getTransNo();
%>
<body topMargin=1 leftMargin=1>
<form action="/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet" name="mainForm" method="post">
<table border="0" width= "100%" >
    <tr>
        <td>
            <table width="100%" id="table2" border="0" cellspacing="1" >
                <tr height="22">
                    <td width="10%" align="right"></td>
                    <td width="22%"></td>
                    <td width="10%" align="right"></td>
                    <td width="15%" colspan="2"><h3>备件送修返还单</h3></td>
                    <td width="15%"></td>
                    <td width="10%" align="right"></td>
                    <td width="10%"></td>
                </tr>
                 <tr height="22">
                     <td width="10%" align="right"></td>
                    <td width="22%"></td>
                    <td width="10%" align="right"></td>
                    <td width="15%"><h3></h3></td>
                   <td width="15%" align="right"></td>
                    <td width="10%"></td>
                     <td width="10%"></td>
                </tr>
                <tr height="24" >
                    <td width="10%" align="right">单据号：</td>
                    <td width="22%"><%=dto.getTransNo()%></td>
                    <td width="10%" align="right">创建人：</td>
                    <td width="15%"><%=dto.getCreatedUser()%></td>
                    <td width="10%" align="right">创建时间：</td>
                    <td width="15%"><%=dto.getCreationDate().toString()%></td>
                    <td width="10%" align="right"></td>
                    <td width="10%"></td>
                </tr>
                <tr height="24">
                    <td width="10%" align="right">来源库存：</td>
                    <td width="20%"><%=dto.getFromObjectName()%></td>
                    <td width="10%" align="right">入库原因：</td>
                    <td width="15%"><%=dto.getReason()%></td>
                    <td width="10%" align="right">目的库存：</td>
                    <td width="15%"><%=dto.getToObjectName()%></td>
                    <td width="10%"></td>
                    <td width="10%"></td>
                </tr>
                <tr>
                    <td width="10%" align="right">厂商：</td>
                    <td width="20%"><%=dto.getVendorName()%></td>
                    <td width="10%" align="right">备注：</td>
                    <td width="35%" colspan="6"><%=dto.getRemark()%></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<DIV align=center id='hiddenDiv'>
<img src="/images/button/pageSet.gif" alt="打印页面设置" onclick="printSetup();">
<img src="/images/button/printView.gif" alt="打印预览" onclick="printView();">
<img src="/images/button/print.gif" alt="打印页面" onclick="do_print();">
<img src="/images/button/close.gif" alt="关闭" onclick="window.close()">
</DIV>
<div style="left:1px;width:100%;overflow-y:visible" class="crystalScroll">
    <table  border="1" width="100%"  cellpadding="0" cellspacing="0" bordercolor="#666666">
        <tr height="30">
            <td width="10%" align="center">设备名称</td>
            <td width="15%" align="center">设备型号</td>
            <td width="10%" align="center">设备类型</td>
            <td width="10%" align="center">用途</td>
            <td width="10%" align="center">厂商</td>
            <td width="5%" align="center">返还数量</td>
            <td width="10%" align="center">备注</td>
        </tr>
    </table>
</div>

<div style="width:100%;left:1px;margin-left:0;overflow-y:visible;" class="crystalScroll">
    <table width="100%" border="1" bordercolor="#9FD6FF" id="mtlTable" cellpadding="0" cellspacing="0">
        <%
            if (set == null || set.isEmpty()) {
        %>
         <tr id="mainTr0" height="30" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="10%" name="itemName" id="itemName0"></td>
            <td width="15%" name="itemSpec" id="itemSpec0"></td>
            <td width="10%" name="itemCategory" id="itemCategory0"></td>
            <td width="10%" name="spareUsage" id="spareUsage0"></td>
            <td width="10%" name ="vendorName" id = "vendorName0"></td>
            <td width="5%" align="center"><input type="text" name="quantity" id="quantity0"   onblur="checkQty(this);" value="" class="blueborderYellow" style="width:100%;text-align:right">
            </td>
            <td width="10%" align="left"><input type="text" name="remarkl" id="remarkl0"   value="" class="blueborder" style="width:100%;text-align:left">
            </td>
        </tr>
        <%
        } else {
            AmsItemTransLDTO dto1 = null;
            for (int i = 0; i < set.getSize(); i++) {
                dto1 = (AmsItemTransLDTO) set.getDTO(i);
        %>
        <tr id="mainTr<%=i%>" height="30" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="10%" name="itemName" id="itemName<%=i%>"><%=dto1.getItemName()%>
            </td>
            <td width="15%" name="itemSpec" id="itemSpec<%=i%>"><%=dto1.getItemSpec()%>
            </td>
            <td width="10%" name="itemCategory" id="itemCategory<%=i%>"><%=dto1.getItemCategory()%>
            </td>
            <td width="10%" name="spareUsage" id="spareUsage<%=i%>"><%=dto1.getSpareUsage()%>
            </td>
            <td width="10%" name="vendorName" id="vendorName<%=i%>"><%=dto1.getVendorName()%>
            </td>
            <td width="5%" align="center"><%=dto1.getQuantity()%>
            </td>
            <td width="10%" align="center"><%=dto1.getRemark()%>
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
<!--<tr height="80"></tr>-->
<tr height="22">
    <td width="8%" align="right">库管员：</td>
    <%--<td width="12%"><input type="text"  style="width:100%"  readonly class="approveInput" value=""></td>--%>
    <td width="12%"><%if(userAccount.getOrganizationId().equals("82")&& transNo.substring(0,4).equals("4110")) {%><img src="/images/fanjianqiang.jpg"><%}else{%><input type="text"  style="width:100%" readonly  class="approveInput" value=""><%}%></td>
    <%--<td width="8%" align="right">运输人：</td>--%>
    <%--<td width="12%"><input type="text"  style="width:100%" readonly  class="approveInput" value=""></td>--%>
    <%--<td width="8%" align="right">接收人：</td>--%>
    <%--<td width="12%"><input type="text"  style="width:100%"  readonly class="approveInput" value=""></td>--%>
    <!--<td width="8%" align="right">申请人：</td>-->
    <%--<td width="12%"><input type="text"  style="width:100%"  readonly class="approveInput" value=""></td>--%>
    <td width="8%" align="right">审批人：</td>
    <td width="12%"><%if(userAccount.getOrganizationId().equals("82")&& transNo.substring(0,4).equals("4110")) {%><img src="/images/qianming.JPG"><%}else{%><input type="text"  style="width:100%"  readonly class="approveInput" value=""><%}%></td>
    <%--<td width="12%"><input type="text"  style="width:100%"  readonly class="approveInput" value=""></td>--%>
    <!--<td width="12%"><img src="/images/qianming.JPG"></td>-->
</tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=dto.getTransId()%>">
<input type="hidden" name="transStatus" value="<%=dto.getTransStatus()%>">
<input type="hidden" name="fromObjectNo" value="<%=dto.getToObjectNo()%>">

<input type="hidden" name="toObjectNo" value="<%=dto.getToObjectNo()%>">
<input type="hidden" name="fromOrgnizationId" value="<%=dto.getFromOrganizationId()%>">
</div>
<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
</form>
</body>
</html>
<script type="text/javascript">

  function do_print() {
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