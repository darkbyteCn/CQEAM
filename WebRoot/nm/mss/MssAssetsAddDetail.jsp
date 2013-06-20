<%@ page import="com.sino.ams.newasset.dto.AssetsAddDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.nm.ams.mss.dto.MssAssetsAddDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<head><title>新增管理资产</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/arrUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
     <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
     <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <%--<style type="text/css">
        input {
            width: 90%
        }
    </style>--%>
</head>
<body topmargin="0" leftmargin="0" onload="init();">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    MssAssetsAddDTO assetsAddH = (MssAssetsAddDTO) request.getAttribute("ASSETS_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.nm.ams.mss.servlet.MssAssetsAddServlet" method="post">
<input type="hidden" name="act" value="">
<input type="hidden" name="headId" value="<%=assetsAddH.getHeadId()%>">
<table border="1" bordercolor="#9FD6FF" class="detailHeader" id="table1">
    <tr>
        <td>
            <table width="100%" id="table2" cellspacing="1">
                <tr height="22">
                    <td width="15%" align="right">单据编号：</td>
                    <td width="25%"><input type="text" style="width:60%" name="billNo"
                                           value="<%=assetsAddH.getBillNo()%>" readonly
                                           class="blueborderGray">
                    </td>
                    <td width="15%" align="right">单据状态：</td>
                    <td width="25%"><input type="text" style="width:50%" name="status" readonly
                                           value="<%=assetsAddH.getStatus()%>"
                                           class="blueborderGray">
                    </td>
                </tr>
                <tr height="22">
                    <td align="right">创建人：</td>
                    <td><input type="text" style="width:60%" name="createUser" value="<%=assetsAddH.getCreateUser()%>"
                               readonly
                               class="blueborderGray">
                    </td>
                    <td align="right">创建日期：</td>
                    <td><input type="text" name="createdDate" readonly style="width:50%"
                               value="<%=assetsAddH.getCreatedDate()%>"
                               class="blueborderGray">
                    </td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td colspan="7"><textarea name="remark" rows="3" cols="" style="width:90%" class="blueBorder"><%=assetsAddH.getRemark()%></textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<fieldset style="border:1px solid #397DF3; position:absolute;top:111px;width:100%;height:70%">
<legend style="top:1">
    <%
        //单据非完成状态并且当前用户是创建人才有操作权限 (daizb 20111107 modify)
        //if (assetsAddH.getStatus().equals("未完成") && assetsAddH.getCreatedBy().equals(user.getUserId())) {
        if (assetsAddH.getStatus() == 0 && assetsAddH.getCreatedBy() == user.getUserId()) {
    %>
    <img src="/images/button/addData.gif" alt="添加数据" onclick="do_SelectItem();">
    <img src="/images/button/deleteLine.gif" alt="删除行"
         onClick="deleteTableRow(document.getElementById('dataTable'),'subCheck');">
    <%--<img src="/images/button/save.gif" alt="保存" id="img3" onClick="do_Save(1);">--%>
    <img src="/images/button/ok.gif" alt="确定" id="img4" onClick="do_Save(2);">
    <%--<img src="/images/button/toExcel_addr.GIF" alt="导出地点" id="img5" onClick="do_expAdress();">--%>
    <%--<img src="/images/button/toExcel_dept.GIF" alt="导出部门" id="img6" onClick="do_expDept();">--%>
    <%--<img src="/images/button/toExcel_name.GIF" alt="导出名称型号" id="img7" onClick="do_expItem();">--%>
    <img src="/images/button/toExcel_template.gif" alt="EXCEL模板下载" id="img8" onclick="do_exportToExcel();">
    <img src="/images/button/pasteData.gif"  alt="粘贴EXCEL" onClick="doPaste(); return false;">
    <img src="/images/button/buildBarcode.gif"  alt="生成条码" onClick="do_bulidBarcode(); return false;">
    <img src="/images/button/toExcel.gif" alt="导出EXCLE" onclick="do_export()">
    <%
        }
    %>
      <span id="warn"></span>
    <%//(daizb 20111107 modify)
    if((!(assetsAddH.getStatus() == 0))&&(!assetsAddH.getHeadId().equals(""))){%>
    <img src="/images/button/toExcel.gif" alt="导出EXCLE" onclick="do_export_barcode()">
    <%}%>
     <span id="warn"></span>
    <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
      <input type="checkbox" name="allLseeType" id="allLseeType"><label for="allLseeType">批量</label>
</legend>
<script type="text/javascript">
    function printTableHeadInfo(columnArr,widthArr){
          document.write("<div id=\"headDiv\" style=\"overflow:hidden;left:1px;width:985px\" class=\"crystalScroll\" align=\"left\">");
    document.write("<table id=\"$$$headerTable$$$\" border=\"1\" width=\"320%\" class=\""+constTHeadClass+"\" cellpadding=\"0\" cellspacing=\"0\">");
    document.write("<tr height=\"22\" onClick='executeClick(this)'>");
    if(columnArr[0] == "checkbox"){
         document.write("<td width=\""+widthArr[0]+"\" align=\"center\"><input type=\"checkBox\" name=\"titleCheck\" onclick=\"checkAll('titleCheck','subCheck');\" class=\"headCheckbox\"></td>");
    } else if(columnArr[0] == "radio"){
         document.write("<td width=\""+widthArr[0]+"\" align=\"center\"></td>");
    } else{
	     document.write("<td width=\""+widthArr[0]+"\" align=\"center\">"+columnArr[0]+"</td>")
    }
    for(var i=1;i<columnArr.length;i++){
        document.write("<td width=\""+widthArr[i]+"\" align=\"center\">"+columnArr[i]+"</td>")
    }
    document.write("</tr></table></div>")
}
    var columnArr = new Array("checkbox", "物资条码", "资产条码","名称", "型号","品牌","状态", "地点", "责任部门", "责任人","使用部门","使用人","一级类别","二级类别", "序列号","创建日期","补丁版本","License数目","授权证书","存放介质","承载系统","产品号","内存","CPU","IP地址","硬盘信息","操作系统","托管类型","保密等级","完整等级","可用等级","所属类别","第三方公司","备注");
    var widthArr = new Array("1%", "3%", "3%", "6%", "4%", "2%", "2%", "6%","6%","2%","2%","2%","3%","3%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%","2%");
    printTableHeadInfo(columnArr, widthArr);
//     printTableHead(columnArr, widthArr);
</script>
<div style="overflow:scroll;height:500px;width:1002px;top:148px;left:1px;margin-left:0"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="320%" border="1"  bordercolor="#9FD6FF" id="dataTable" cellpadding="1" cellspacing="0">
        <%
            RowSet rows = (RowSet) request.getAttribute("ASSETS_LINES");
            if (rows == null || rows.isEmpty()) {
        %>
        <tr id="mainTr0" style="display:none" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">

            <td width="1%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="3%" align="center"><input type="text" name="mssBarcode" id="mssBarcode"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%">
            </td>
            <td width="3%" align="center"><input type="text" name="barcode" id="barcode"
                                                  value="" class="noneborderInput"
                                                  style="width:100%">
            </td>
            <td width="6%" align="center"><input type="text" name="itemName1"   id="itemName10"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="4%" align="center"><input type="text" name="itemSpec1"  id="itemSpec10"
                                                  value="" class="noneborderInput"
                                                  style="width:100%"></td>
            <td width="2%" align="center"><input type="text" name="itemBrand"  id="itemBrand0"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="2%" align="center"><input type="text" name="itemStatus"  id="itemStatus0"
                                                  value="" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="6%" align="center" onclick="do_SelectAddress(this);"><input type="text"
                                                                                    name="workorderObjectName"
                                                                                    id="workorderObjectName0"
                                                                                    value="" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                     title="点击选择资产地点"><input
                    type="hidden" name="addressId" id="addressId0" value="">
            </td>
            <td width="6%" align="center" onclick="do_SelectRespDept(this);"><input type="text" name="deptName"
                                                                                     id="deptName0"
                                                                                     value="" class="blueborderYellow"
                                                                                     style="width:100%;text-align:center"
                                                                                     readonly title="点击选择责任部门"><input
                    type="hidden" name="deptCode" id="deptCode0" value="">
            </td>
            <td width="2%" align="center" onclick="do_SelectRespUser(this);"><input type="text" name="userName"
                                                                                    id="userName0"
                                                                                    value="" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                    readonly title="点击选择责任人"><input
                    type="hidden" name="employeeId" id="employeeId0" value="">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput" name="maintainDept" id="maintainDept0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="maintainUser" id="maintainUser0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="3%" align="center"><input type="text" class="noneborderInput"  name="itemCategory1" id="itemCategory10"
                                                  value="" style="width:100%;text-align:right">
                <%--<select style="width:100%" onchange="do_SetType(this)" name="itemCategory1" id="itemCategory10" class="blueborderYellow"><%=assetsAddH.getItemUsageOp()%></select>--%></td>
            <td  width="3%" align="center"><input type="text" class="noneborderInput"  name="itemCategory2" id="itemCategory20"
                                                  value="" style="width:100%;text-align:right">
                <%--<select style="width:100%" name="itemCategory2" onchange="do_setStatus(this)" id="itemCategory20" class="blueborderYellow"><%=assetsAddH.getItemUsageStatusOp()%></select>--%></td>

             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="itemSerial" id="itemSerial0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="createdDate" id="createdDate0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="updateVersion" id="updateVersion0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="licenseNum" id="licenseNum0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="licenseName" id="licenseName0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="softMedium" id="softMedium0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="useBySystem" id="useBySystem0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="productNum" id="productNum0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="memory" id="memory0"
                                                  value="" style="width:100%;text-align:right">
            </td>
              <td width="2%" align="center"><input type="text" class="noneborderInput"  name="cpu" id="cpu0"
                                                  value="" style="width:100%;text-align:right">
            </td>
              <td width="2%" align="center"><input type="text" class="noneborderInput"  name="ipAddress" id="ipAddress0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="diskInformation" id="diskInformation0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="systemName" id="systemName0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="trusteeshipType" id="trusteeshipType0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="secureLevel" id="secureLevel0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="completenessLevel" id="completenessLevel0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text"  class="noneborderInput" name="userLevel" id="userLevel0"
                                                  value="" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="itemCategory3" id="itemCategory30"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="thirdCompany" id="thirdCompany0"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="remark1" id="remark10"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId0" value=""><input type="hidden" name="itemCode"
                                                                                id="itemCode0" value="">
            </td>


        </tr>
        <%
        } else {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">
             <td width="1%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                 style="height:20px;margin:0;padding:0">
            </td>
            <td width="3%" align="center"><input type="text" name="mssBarcode" id="mssBarcode<%=i%>"
                                                  value="<%=row.getValue("MSS_BARCODE")%>" class="blueborderYellow"
                                                  style="width:100%">
            </td>
            <td width="3%" align="center"><input type="text" name="barcode" id="barcode<%=i%>"
                                                  value="<%=row.getValue("BARCODE")%>" class="blueborderYellow"
                                                  style="width:100%">
            </td>
            <td width="6%" align="center"><input type="text" name="itemName1"   id="itemName1<%=i%>"
                                                  value="<%=row.getValue("ITEM_NAME")%>" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="4%" align="center"><input type="text" name="itemSpec1"  id="itemSpec1<%=i%>"
                                                  value="<%=row.getValue("ITEM_SPEC")%>" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="2%" align="center"><input type="text" name="itemBrand"  id="itemBrand<%=i%>"
                                                  value="<%=row.getValue("ITEM_BRAND")%>" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="2%" align="center"><input type="text" name="itemStatus"  id="itemStatus<%=i%>"
                                                  value="<%=row.getValue("ITEM_STATUS")%>" class="blueborderYellow"
                                                  style="width:100%"></td>
            <td width="6%" align="center" onclick="do_SelectAddress(this);"><input type="text"
                                                                                    name="workorderObjectName"
                                                                                    id="workorderObjectName<%=i%>"
                                                                                    value="<%=row.getValue("ADDRESS")%>" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                     title="点击选择资产地点"><input
                    type="hidden" name="addressId" id="addressId<%=i%>" value="">
            </td>
            <td width="6%" align="center" onclick="do_SelectRespDept(this);"><input type="text" name="deptName"
                                                                                     id="deptName<%=i%>"
                                                                                     value="<%=row.getValue("RESPONSIBILITY_DEPT")%>" class="blueborderYellow"
                                                                                     style="width:100%;text-align:center"
                                                                                     readonly title="点击选择责任部门"><input
                    type="hidden" name="deptCode" id="deptCode<%=i%>" value="">
            </td>
            <td width="2%" align="center" onclick="do_SelectRespUser(this);"><input type="text" name="userName"
                                                                                    id="userName<%=i%>"
                                                                                    value="<%=row.getValue("RESPONSIBILITY_USER")%>" class="blueborderYellow"
                                                                                    style="width:100%;text-align:center"
                                                                                    readonly title="点击选择责任人"><input
                    type="hidden" name="employeeId" id="employeeId<%=i%>" value="">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput" name="maintainDept" id="maintainDept<%=i%>"
                                                  value="<%=row.getValue("MAINTAIN_DEPT")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="maintainUser" id="maintainUser<%=i%>"
                                                  value="<%=row.getValue("MAINTAIN_USER")%>" style="width:100%;text-align:right">
            </td>
            <td width="3%" align="center"><input type="text" class="noneborderInput"  name="itemCategory1" id="itemCategory1<%=i%>"
                                                  value="<%=row.getValue("ITEM_CATEGORY1")%>" style="width:100%;text-align:right">
                <%--<select style="width:100%" onchange="do_SetType(this)" name="itemCategory1" id="itemCategory10" class="blueborderYellow"><%=assetsAddH.getItemUsageOp()%></select>--%></td>
            <td  width="3%" align="center"><input type="text" class="noneborderInput"  name="itemCategory2" id="itemCategory2<%=i%>"
                                                  value="<%=row.getValue("ITEM_CATEGORY2")%>" style="width:100%;text-align:right">
                <%--<select style="width:100%" name="itemCategory2" onchange="do_setStatus(this)" id="itemCategory20" class="blueborderYellow"><%=assetsAddH.getItemUsageStatusOp()%></select>--%></td>

             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="itemSerial" id="itemSerial<%=i%>"
                                                  value="<%=row.getValue("ITEM_SERIAL")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="createdDate" id="createdDate<%=i%>"
                                                  value="" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="updateVersion" id="updateVersion<%=i%>"
                                                  value="<%=row.getValue("UPDATE_VERSION")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="licenseNum" id="licenseNum<%=i%>"
                                                  value="<%=row.getValue("LICENSE_NUM")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="licenseName" id="licenseName<%=i%>"
                                                  value="<%=row.getValue("LICENSE_NAME")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="softMedium" id="softMedium<%=i%>"
                                                  value="<%=row.getValue("SOFT_MEDIUM")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="useBySystem" id="useBySystem<%=i%>"
                                                  value="<%=row.getValue("USE_BY_SYSTEM")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="productNum" id="productNum<%=i%>"
                                                  value="<%=row.getValue("PRODUCT_NUM")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="memory" id="memory<%=i%>"
                                                  value="<%=row.getValue("MEMORY")%>" style="width:100%;text-align:right">
            </td>
              <td width="2%" align="center"><input type="text" class="noneborderInput"  name="cpu" id="cpu<%=i%>"
                                                  value="<%=row.getValue("CPU")%>" style="width:100%;text-align:right">
            </td>
              <td width="2%" align="center"><input type="text" class="noneborderInput"  name="ipAddress" id="ipAddress<%=i%>"
                                                  value="<%=row.getValue("IP_ADDRESS")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="diskInformation" id="diskInformation<%=i%>"
                                                  value="<%=row.getValue("DISK_INFORMATION")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="systemName" id="systemName<%=i%>"
                                                  value="<%=row.getValue("SYSTEM_NAME")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="trusteeshipType" id="trusteeshipType<%=i%>"
                                                  value="<%=row.getValue("TRUSTEESHIP_TYPE")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="secureLevel" id="secureLevel<%=i%>"
                                                  value="<%=row.getValue("SECURE_LEVEL")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="completenessLevel" id="completenessLevel<%=i%>"
                                                  value="<%=row.getValue("COMPLETENESS_LEVEL")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text"  class="noneborderInput" name="userLevel" id="userLevel<%=i%>"
                                                  value="<%=row.getValue("USER_LEVEL")%>" style="width:100%;text-align:right">
            </td>
             <td width="2%" align="center"><input type="text" class="noneborderInput"  name="itemCategory3" id="itemCategory3<%=i%>"
                                                  value="<%=row.getValue("ITEM_CATEGORY3")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="thirdCompany" id="thirdCompany<%=i%>"
                                                  value="<%=row.getValue("THIRD_COMPANY")%>" style="width:100%;text-align:right">
            </td>
            <td width="2%" align="center"><input type="text" class="noneborderInput"  name="remark1" id="remark1<%=i%>"
                                                  value="<%=row.getValue("REMARK")%>" style="width:100%;text-align:right">
            </td>
            <td style="display:none">
                <input type="hidden" name="lineId" id="lineId<%=i%>" value=""><input type="hidden" name="itemCode"
                                                                                id="itemCode<%=i%>" value="">
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
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function init() {
//    do_SetPageWidth()  ;
}

function do_SelectItem() {

    var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_SYSITEM%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var userdata =  "masterOrganizationId=" + <%=user.getOrganizationId()%>;
    var assets = getLookUpValues(lookUpName, dialogWidth, dialogHeight,userdata);

    <%--var url = "/servlet/com.sino.tfeam.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_ASSETS_SYSITEM%>";--%>
    <%--var popscript = "dialogWidth:51;dialogHeight:33;center:yes;status:no;scrollbars:no";--%>
    <%--var assets = window.showModalDialog(url, null, popscript);--%>
    if (assets) {
        var data = null;
        var tab = document.getElementById("dataTable");
        for (var i = 0; i < assets.length; i++) {
            data = assets[i];
            if (!isItemExist(data)) {
                appendDTORow(tab, data);
            }
        }
    }
}
function isItemExist(itemObj) {
    var retVal = false;
    var tab = document.getElementById("dataTable");
//    if (tab.rows) {
//        var trObjs = tab.rows;
//        var trCount = trObjs.length;
//        var trObj = null;
//        var itemValue = itemObj.itemCode;
//        var rowValue = null;
//        for (var i = 0; i < trCount; i++) {
//            trObj = trObjs[i];
//            rowValue = trObj.cells[5].childNodes[1].value;
//            if (itemValue == rowValue) {
//                retVal = true;
//            }
//        }
//    }
    return retVal;
}

function do_SelectRespUser(obj) {

    obj.childNodes[0].value = "";
    obj.childNodes[1].value = "";
     obj.childNodes[0].style.color="black";
    var objName = obj.childNodes[0].name;
    var objId = obj.childNodes[0].id;

    var idNumber = objId.substring(objName.length);

    var deptName = document.getElementById("deptName" + idNumber).value;

    if (deptName == "") {
        alert("请先选择责任部门，再选择责任人！");
        return;
    }

    var upName = "<%=LookUpConstant.LOOK_UP_MIS_USER%>";
    var dialogWidth = 50;
    var dialogHeight = 30;

    var userPara = "deptCode=" + document.getElementById("deptCode" + idNumber).value;

    var users = getLookUpValues(upName, dialogWidth, dialogHeight, userPara);
    if (users) {
        obj.childNodes[0].value = users[0].userName;
        obj.childNodes[1].value = users[0].employeeId;
    }
}

function do_SelectRespDept(obj) {
     var objId = obj.childNodes[0].id;
    obj.childNodes[0].value = "";
    obj.childNodes[1].value = "";
     obj.childNodes[0].style.color="black";
    var objName = obj.childNodes[0].name;



    var idNumber = objId.substring(objName.length);
    //        mainForm.get
    //alert(idNumber) ;
    document.getElementById("userName" + idNumber).value = "";
    var lookUpSpec = "<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var dialogWidth = 50;
    var dialogHeight = 30;
    var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
    if (specs) {
        obj.childNodes[0].value = specs[0].deptName;
        obj.childNodes[1].value = specs[0].deptCode;
    }
    //        if (specs) {
    //            var spec = null;
    //            for (var i = 0; i < specs.length; i++) {
    //                spec = specs[i];
    //                dto2Frm(spec, "mainForm");
    //            }
    //        }
}

/**
 * 功能:选择地点
 */
function do_SelectAddress(obj) {
//    objid=obj.childNodes[0].id;
//    alert(objid)
    obj.childNodes[0].value = "";
    obj.childNodes[1].value = "";
    obj.childNodes[0].style.color="black";
    var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var userPara = "organizationId=" +<%=user.getOrganizationId()%>;
    var locations = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (locations) {
        obj.childNodes[0].value = locations[0].toObjectName;
        obj.childNodes[1].value = locations[0].addressId;
    }
}

var sflag = null;
function do_Save(flag) {
    var tb = document.getElementById("dataTable");
    if (tb.rows.length == 0 || (tb.rows[0].style.display == 'none' && tb.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
       return;
    }
//        document.getElementById("")
//      do_check();
    if (true) {
                sflag = flag;
//        validateData()
//                if (isBarcodeRepeated()) {
//                    alert("设备条码不能重复，请确认输入的设备条码！");
//                }
        if (flag == 2) {//submit
           mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
        } else {    //save
           mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        }
        mainForm.submit();
    }
}
function do_check(){
   var tb = document.getElementById("dataTable");
}
function validateData() {
    var validate = false;
    var fieldNames = "mssBarcode;workorderObjectName;deptName;userName";
    var fieldLabels = "物资条码;资产地点;责任部门;责任人;设备名称型号;责任人";
    var validateType = EMPTY_VALIDATE;
    validate = formValidate(fieldNames, fieldLabels, validateType);
    return validate;
}

//var xmlHttp = null;
//var isRepeated = true;
//function isBarcodeRepeated() {
//    var repeated = true;
//    var barcodes = document.getElementsByName("barcode");
//    var barcodeArr = new Array();
//    var barcodeArr2 = new Array();
//    for (var i = 0; i < barcodes.length; i++) {
//        if (barcodes[i].value == "") {
//            alert("请输入设备条码！");
//            barcodes[i].focus();
//            return;
//        }
//        barcodeArr[i] = barcodes[i].value;
//        barcodeArr2[i] = barcodes[i].value;
//    }
//    if (barcodeArr.length == barcodeArr2.uniqueStrArr().length) {
//        repeated = false;
//    }
//    if (!repeated) {
//        //到数据库验证是否有重复
//        var url = "/servlet/com.sino.ams.spare.servlet.CheckBarcodeServlet";
//        xmlHttp = GetXmlHttpObject(checkBarcode);
//        xmlHttp.open('POST', url, true);
//        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
//        xmlHttp.send("barcodes=" + barcodeArr);
//    }
//    return repeated;
//}

function checkBarcode() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var flexValues = new Array();
        var descriptions = new Array();
        var resText = xmlHttp.responseText;
        if (resText == "ERROR") {
            alert(resText);
        } else if (resText == "OK") {
            isRepeated = false;
            if (sflag == 1) {
                document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            } else {
                document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
            }
            document.mainForm.submit();
        } else {
            var resArray = resText.parseJSON();
            if (resArray.length > 0) {
                var tabObj = document.getElementById("dataTable");
                var retStr;
                for (var i = 0; i < resArray.length; i++) {
                    retStr = resArray[i];
                    if (retStr == 1) {
                        tabObj.rows[i].cells[1].childNodes[0].style.color = "red";

                    }
                }
                alert("标记为红色的设备条码已存在，请修改！");
            }
        }
        xmlHttp = null;
    }
}
    var xmlHttp;
    var segment10Array = new Array();
    var projectNameArray = new Array();
    var segment10Index = -1;
    var projectNameIndex = -1;
    var mark = -1;
function doPaste() {
	try {
		if (confirm("确定粘贴到当前页面？")) {
			var text = window.clipboardData.getData("text");
			if (text == null || text == "") {
				alert("请先在EXCEL摸板里复制数据行数据，然后再粘贴！");
				return;
			}
			var rows = text.split('\n');
			for (var i = 0; i < rows.length - 1; i++) {
				mark ++;
				var row = rows[i];
				insertRow(row);
            }
//			pageVerifySegment10();
//             to_value();
        }
    } catch(e) {
//        alert(e.description);
        alert("粘贴失败!");
    }
}
function pageVerifySegment10() {
    var warn = document.getElementById('warn');
    warn.innerText = '';
    doInitArray();
    xmlHttp = createXMLHttpRequest();
    var url = "/servlet/com.sino.tfeam.newasset.servlet.AmsAssetsTransHeaderServlet" ;
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(segment10Array.toJSONString());
}

function insertRow(row) {
    var cols;
    if (typeof(row) == 'string') {
        cols = row.split('\t');
    } else {
        cols = row;
    }

    ;
    var newRow;
    var rowCount =document.getElementById("dataTable").rows.length

    var row1=document.getElementById("mainTr0")
    row1=document.getElementById("dataTable").rows[rowCount-1]  ;
    if(rowCount == 1 && row1.style.display == "none"){
		newRow = row1;
	} else {
		newRow = row1.cloneNode(true);
//        var num = newRow.childNodes[5].childNodes[0].id.substring(newRow.childNodes[5].childNodes[0].name.length);
//         var newId = newRow.childNodes[5].childNodes[0].name + (Number(num) + 1);
//        newRow.childNodes[5].childNodes[0].id=newId;//"deptName"+rowCount;
         for(var i=0;i<34;i++)
        {
            var num = newRow.childNodes[i].childNodes[0].id.substring(newRow.childNodes[i].childNodes[0].name.length);
            var newId = newRow.childNodes[i].childNodes[0].name + (Number(num) + 1);
            newRow.childNodes[i].childNodes[0].id=newId;
//            if(i==4 ||i==5 ||i==6 ||i==9)
//            {
//                var num = newRow.childNodes[i].childNodes[1].id.substring(newRow.childNodes[i].childNodes[1].name.length);
//            var newId = newRow.childNodes[i].childNodes[1].name + (Number(num) + 1);
//            newRow.childNodes[i].childNodes[1].id=newId;
//            }
       }

    }

    newRow.style.display = 'block';
    newRow.childNodes[0].childNodes[0].value = mark;
    newRow.childNodes[1].childNodes[0].value = cols[0];
    newRow.childNodes[2].childNodes[0].value = cols[1];
    newRow.childNodes[3].childNodes[0].value = cols[2];
    newRow.childNodes[4].childNodes[0].value = cols[3];
    newRow.childNodes[5].childNodes[0].value = cols[4];


    newRow.childNodes[6].childNodes[0].value = cols[5];
    newRow.childNodes[7].childNodes[0].value = cols[6];
    newRow.childNodes[8].childNodes[0].value = cols[7];
    newRow.childNodes[9].childNodes[0].value = cols[8];
    newRow.childNodes[10].childNodes[0].value = cols[9];

    newRow.childNodes[11].childNodes[0].value = cols[10];
    newRow.childNodes[12].childNodes[0].value = cols[11];
    newRow.childNodes[13].childNodes[0].value = cols[12];
    newRow.childNodes[14].childNodes[0].value = cols[13];
    newRow.childNodes[15].childNodes[0].value = cols[14];
    newRow.childNodes[16].childNodes[0].value = cols[15];
    newRow.childNodes[17].childNodes[0].value = cols[16];
    newRow.childNodes[18].childNodes[0].value = cols[17];
    newRow.childNodes[19].childNodes[0].value = cols[18];
    newRow.childNodes[20].childNodes[0].value = cols[19];

    newRow.childNodes[21].childNodes[0].value = cols[20];
    newRow.childNodes[22].childNodes[0].value = cols[21];
    newRow.childNodes[23].childNodes[0].value = cols[22];
    newRow.childNodes[24].childNodes[0].value = cols[23];
    newRow.childNodes[25].childNodes[0].value = cols[24];
    newRow.childNodes[26].childNodes[0].value = cols[25];
    newRow.childNodes[27].childNodes[0].value = cols[26];
    newRow.childNodes[28].childNodes[0].value = cols[27];
    newRow.childNodes[29].childNodes[0].value = cols[28];
    newRow.childNodes[30].childNodes[0].value = cols[29];

     newRow.childNodes[31].childNodes[0].value = cols[30];
    newRow.childNodes[32].childNodes[0].value = cols[31];
    newRow.childNodes[33].childNodes[0].value = cols[32];
    document.getElementById("mainTr0").parentNode.appendChild(newRow);



}
function doInitArray() {
    segment10Array = new Array();
    projectNameArray = new Array();
    projectNameIndex = -1;
    var segment10 = document.getElementsByName("segment10");
    for (var i = 2; i < segment10.length; i++) {
        segment10Array[i - 2] = segment10[i].value;
    }
    var projectName = document.getElementsByName("projectName");
    for (var i = 2; i < projectName.length; i++) {
        if (!isEmpty(projectName[i].value)) {
            projectNameIndex++;
            projectNameArray[projectNameIndex] = projectName[i].value;
        }
    }
}
var glb_rownum;
function to_value(){
    var tab=document.getElementById("dataTable");
    var rCount=tab.rows.length;
    var iName;
    var iSpec;
    var addes;
    var depName;
   var userName;
    var s;
    for(var i=0;i<rCount;i++){
        glb_rownum=i;
       iName= tab.rows[i].cells[2].childNodes[0].value;
        iSpec=tab.rows[i].cells[3].childNodes[0].value;
        addes=tab.rows[i].cells[4].childNodes[0].value;
        depName=tab.rows[i].cells[5].childNodes[0].value;
        userName=tab.rows[i].cells[6].childNodes[0].value;
        var pars="&iName=" + iName +  "&iSpec=" + iSpec;
        var addre="&adressName=" + addes;
        var deptName= "&deptName=" + depName;
        var useName= "&userName=" + userName;

//         requestAjaxWait("GET_ITEM_CODE", getId, null, pars);
//         requestAjaxWait("GET_ADRESS", getaId, null, addre);
//         requestAjaxWait("GET_DEPT", getbId, null, deptName);
//         requestAjaxWait("GET_USE", getcId, null, useName);
//        getId(i,s);
    }


}
  function getId() {
//      var j=glb_rownum;
      var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '') {
             var rCount=tab.rows.length;
             //  for(var j=0;j<rCount;j++){
                 //  if(j==glb_rownum){
                      tab.rows[glb_rownum].cells[9].childNodes[1].value = ret;

              //     }
              // }
            }else{
                   tab.rows[glb_rownum].cells[2].childNodes[0].style.color="red";
            }
        }
    }
         function getaId() {
//      var j=glb_rownum;
      var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '') {
             var rCount=tab.rows.length;
              // for(var j=0;j<rCount;j++){
              //     if(j==glb_rownum){
                      tab.rows[glb_rownum].cells[4].childNodes[1].value = ret;
              //     }
             //  }


            }else{
                   tab.rows[glb_rownum].cells[4].childNodes[0].style.color="red";
            }
        }
    }
         function getbId() {
//      var j=glb_rownum;
      var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '') {
             var rCount=tab.rows.length;
              // for(var j=0;j<rCount;j++){
              //     if(j==glb_rownum){
                      tab.rows[glb_rownum].cells[5].childNodes[1].value = ret;
               //    }
             //  }


            }else{
                   tab.rows[glb_rownum].cells[5].childNodes[0].style.color="red";
            }
        }
    }
         function getcId() {
//      var j=glb_rownum;
      var tab=document.getElementById("dataTable");
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var ret = getRet(xmlHttp);
            if (ret != 'ERROR' && ret != '') {
             var rCount=tab.rows.length;
              // for(var j=0;j<rCount;j++){
             //      if(j==glb_rownum){
                      tab.rows[glb_rownum].cells[6].childNodes[1].value = ret;
            //       }
             //  }


            }else{
                   tab.rows[glb_rownum].cells[6].childNodes[0].style.color="red";
            }
        }
    }
 function isBarcodeRepeated(){
        var repeated = true;
        var barcodes = document.getElementsByName("barcodeNo");
        var barcodeArr = new Array();
        var barcodeArr2 = new Array();
        var tab=document.getElementById("dataTable");
        var rCount=tab.rows.length;
        for(var j=0;j<rCount;j++){
           var barcode=  tab.rows[j].cells[1].childNodes[0].value  ;
            if(barcode==""){
                alert("请输入设备条码！");
               tab.rows[j].cells[1].childNodes[0].focus();
                return;
            }
           barcodeArr[j] =tab.rows[j].cells[1].childNodes[0].value;
            barcodeArr2[j] = tab.rows[j].cells[1].childNodes[0].value;
        }
        if(barcodeArr.length == barcodeArr2.uniqueStrArr().length){
            repeated = false;
        }
        if(!repeated){
            //到数据库验证是否有重复
            var url = "/servlet/com.sino.tfeam.spare.servlet.CheckBarcodeServlet";
            xmlHttp = GetXmlHttpObject(checkBarcode);
            xmlHttp.open('POST', url, false);
            xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
            xmlHttp.send("barcodes=" + barcodeArr);
        }
        return repeated;
    }

    function checkBarcode() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            var flexValues = new Array();
            var descriptions = new Array();
            var resText = xmlHttp.responseText;
            if (resText == "ERROR") {
                alert(resText);
            } else if (resText == "OK") {
                isRepeated = false;
                if(sflag == 1){
                    document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
                }else{
                    document.mainForm.act.value = "<%=WebActionConstant.SUBMIT_ACTION%>";
                    document.mainForm.transStatus.value = "<%=DictConstant.COMPLETED%>";
                }
                document.mainForm.submit();
            } else {
                var resArray = resText.parseJSON();
                if (resArray.length > 0) {
                    var tabObj = document.getElementById("dataTable");
                    var retStr;
                    for (var i = 0; i < resArray.length; i++) {
                        retStr = resArray[i];
                        if (retStr == 1) {
                            tabObj.rows[i].cells[1].childNodes[0].style.color = "red";

                        }
                    }
                    alert("标记为红色的设备条码为非网络设备条码，请修改！");
                }
            }
            xmlHttp = null;
        }
    }
function do_expAdress(){
     document.mainForm.act.value = "ADRESS";
    mainForm.submit();
}
function do_expDept(){
        document.mainForm.act.value = "DEPT";
    mainForm.submit();
}
function do_expItem(){
        document.mainForm.act.value = "ITEM";
    mainForm.submit();
}
function do_exportToExcel() {
	mainForm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainForm.submit();
}
        var barcodes=null;
function do_bulidBarcode(){
   var tab=document.getElementById("dataTable");
     if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
       return;
    }
    var leg=tab.rows.length;
     do_creatBarcode(leg);


}
function do_creatBarcode(leg) {
    var url = "";
    xmlHttp = createXMLHttpRequest();
    url = "/servlet/com.sino.nm.ams.mss.servlet.MssAssetsAddServlet?act=barcode&count="+leg;
    xmlHttp.onreadystatechange = handleReadyStateChange1;
    xmlHttp.open("post", url, true);
    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp.send(null);
}

function handleReadyStateChange1() {
    var tab = document.getElementById("dataTable");
    var leg = tab.rows.length;
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            unescape(xmlHttp.responseText);
            barcodes = xmlHttp.responseText;
            var barcodeList = barcodes.split(";") ;
            for (var i = 0; i < leg; i++) {
                tab.rows[i].cells[1].childNodes[0].value = barcodeList[i];
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
function do_export(){
     var tab = document.getElementById("dataTable");
      if (tab.rows.length == 0 || (tab.rows[0].style.display == 'none' && tab.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
       return;
    }
    var leg = tab.rows.length;
    var barcodeList="";
    var itemNameList="";
    var itemSpecList="";
    var startDate=null;
    for(var i=0;i<leg;i++){
       barcodeList+= tab.rows[i].cells[1].childNodes[0].value+';';
        itemNameList+= tab.rows[i].cells[2].childNodes[0].value+';';
         itemSpecList+=tab.rows[i].cells[3].childNodes[0].value+';';
    }
    mainForm.action="/servlet/com.sino.nm.ams.mss.servlet.MssAssetsAddServlet?count="+leg+"&barcodeList="+barcodeList+"&itemNameList="+itemNameList+"&itemSpecList="+itemSpecList;
    mainForm.act.value = "EXPORT_BARCODE";
	mainForm.submit();
}
function do_export_barcode(){
    mainForm.act.value = "EXPORT_BARCODE_OK";
	mainForm.submit();
}
function do_SetType(lineBox) {
        if (!mainForm.allLseeType.checked) {
            return;
        }
        var objs = document.getElementsByName("itemUsage");
        var dataCount = objs.length;
        var selectedVal = lineBox.value;
        if (objs && objs.length) {
            var chkObj = null;
            var dataTable = document.getElementById("dataTable");
            var rows = dataTable.rows;
            var row = null;
            var checkObj = null;
            var checkedCount = getCheckedBoxCount("subCheck");
            for (var i = 0; i < dataCount; i++) {
                if (checkedCount > 0) {
                    row = rows[i];
                    checkObj = row.childNodes[0].childNodes[0];
                    if (!checkObj.checked) {
                        continue;
                    }
                }
                chkObj = objs[i];
                selectSpecialOptionByItem(chkObj, selectedVal);
            }
        }
    }
function do_setStatus(lineBox){

     if (!mainForm.allLseeType.checked) {
            return;
        }
        var objs = document.getElementsByName("itemUsageStatus");
        var dataCount = objs.length;
        var selectedVal = lineBox.value;
        if (objs && objs.length) {
            var chkObj = null;
            var dataTable = document.getElementById("dataTable");
            var rows = dataTable.rows;
            var row = null;
            var checkObj = null;
            var checkedCount = getCheckedBoxCount("subCheck");
            for (var i = 0; i < dataCount; i++) {
                if (checkedCount > 0) {
                    row = rows[i];
                    checkObj = row.childNodes[0].childNodes[0];
                    if (!checkObj.checked) {
                        continue;
                    }
                }
                chkObj = objs[i];
                selectSpecialOptionByItem(chkObj, selectedVal);
            }
        }
}
</script>
</html>