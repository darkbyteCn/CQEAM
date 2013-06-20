<%@ page contentType="text/html;charset=GBK" 
		 import="com.sino.ams.newasset.dto.*,
		 		 com.sino.ams.newasset.constant.*,
		 		 com.sino.base.dto.*,
		 		 com.sino.base.util.*" 
		 language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%
    AmsAssetsCheckHeaderDTO chkOrder = (AmsAssetsCheckHeaderDTO) request.getAttribute(AssetsWebAttributes.CHECK_HEADER_DATA);
    String orderStatus = chkOrder.getOrderStatus();
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.CHECK_LINE_DATAS);
    int lineCount = 0;
    if (lineSet != null && !lineSet.isEmpty()) {
        lineCount = lineSet.getSize();
    }
    String srcPage = request.getParameter("srcPage");
    srcPage = StrUtil.nullToString(srcPage);
%>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('do_ArchiveOrder()');">
<form name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.ChkOrderArchiveServlet" method="post">
<script type="text/javascript">
    printTitleBar("设备盘点管理>>工单归档信息");
</script>
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="headerId" value="<%=chkOrder.getHeaderId()%>">
<input type="hidden" name="orderStatus" value="<%=chkOrder.getOrderStatus()%>">
<div id="searchDiv" style="position:absolute;top:25px;left:1px;width:100%">
<table border="0" bordercolor="#226E9B" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
    <tr>
        <td>

            <table width=100% border="1" bordercolor="#226E9B">
                <tr>
                    <td align=right width="8%" height="22">盘点单号：</td>
                    <td width="17%" height="22"><input type="text" class="input_style2"  name="transNo" value="<%=chkOrder.getTransNo()%>"
                                                       readonly
                                                       style="width:100%; border-style: solid; border-width: 0;background-color: #F2F9FF">
                    </td>
                    <td align=right width="8%" height="22">盘点部门：</td>
                    <td width="9%" height="22"><input type="text"    class="input_style2"
                                                      style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                      name="checkDeptName" value="<%=chkOrder.getCheckDeptName()%>">
                    </td>
                    <td align=right height="22" width="7%">地点编号：</td>
                    <td height="22" width="17%">
                        <input type="text"   class="input_style2"
                               style="border-style:solid; border-width:0; width:137; background-color:#F2F9FF; height:15"
                               name="objectCode" readonly value="<%=chkOrder.getObjectCode()%>" size="20"></td>
                    <td align=right width="8%" height="22">盘点地点：</td>
                    <td width="25%" height="22"><input type="text" class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="objectLocation" readonly
                                                       value="<%=chkOrder.getObjectLocation()%>"></td>
                </tr>
                <tr>
                    <td align=right height="22" width="8%">开始日期：</td>
                    <td height="22" width="17%"><input type="text"    class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="startTime" value="<%=chkOrder.getStartTime()%>"></td>
                    <td align=right height="22" width="8%">执行周期：</td>
                    <td height="22" width="9%"><input type="text"     class="input_style2"
                                                      style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                      name="implementDays" value="<%=chkOrder.getImplementDays()%>">
                    </td>
                    <td align=right height="22" width="7%">执行人：</td>
                    <td height="22" width="17%"><input type="text"       class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="implementUser" value="<%=chkOrder.getImplementUser()%>">
                    </td>
                    <td align=right width="8%" height="22">单据状态：</td>
                    <td width="25%" height="22"><input type="text"    class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="statusName" value="<%=chkOrder.getStatusName()%>"></td>
                </tr>
                <tr>
                    <td align=right height="22" width="8%">创建时间：</td>
                    <td height="22" width="17%"><input type="text"   class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="creationDate" value="<%=chkOrder.getCreationDate()%>"></td>
                    <td align=right height="22" width="8%">下发时间：</td>
                    <td height="22" width="9%"><input type="text"    class="input_style2"
                                                      style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                      name="distributeDate" value="<%=chkOrder.getDistributeDate()%>">
                    </td>
                    <td align=right height="22" width="7%">下载时间：</td>
                    <td height="22" width="17%"><input type="text"   class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="downloadDate" value="<%=chkOrder.getDownloadDate()%>"></td>
                    <td align=right height="22" width="8%">上传时间：</td>
                    <td height="22" width="25%"><input type="text"  class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="uploadDate" value="<%=chkOrder.getUploadDate()%>"></td>
                </tr>
                <tr>
                    <td align=right height="22" width="8%">创建人：</td>
                    <td height="22" width="17%"><input type="text"   class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="createdUser" value="<%=chkOrder.getCreatedUser()%>"></td>
                    <td align=right height="22" width="8%">下发人：</td>
                    <td height="22" width="9%"><input type="text"    class="input_style2"
                                                      style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                      name="distributeUser" value="<%=chkOrder.getDistributeUser()%>">
                    </td>
                    <td align=right height="22" width="7%">下载人：</td>
                    <td height="22" width="17%"><input type="text" class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="downloadUser" value="<%=chkOrder.getDownloadUser()%>"></td>
                    <td align=right height="22" width="8%">上传人：</td>
                    <td height="22" width="25%"><input type="text"      class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="uploadUser" value="<%=chkOrder.getUploadUser()%>"></td>
                </tr>
                <tr>
                    <td align=right height="22" width="8%">归档时间：</td>
                    <td height="22" width="17%"><input type="text"    class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="archivedDate" value="<%=chkOrder.getArchivedDate()%>"></td>
                    <td align=right height="22" width="8%">归档人：</td>
                    <td height="22" width="9%"><input type="text"   class="input_style2"
                                                      style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                      name="archivedUser" value="<%=chkOrder.getArchivedUser()%>"></td>
                    <td align=right width="7%" height="22">任务描述：</td>
                    <td width=17% height="22"><input type="text"   class="input_style2"
                                                     style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                     name="taskDesc" value="<%=chkOrder.getTaskDesc()%>"></td>
                    <td align=right width="8%" height="22">　</td>
                    <td width="25%" height="22">　</td>
                </tr>
                <tr>
                    <td align=right height="22" width="8%">扫描专业：</td>
                    <td height="22" width="17%"><input type="text"  class="input_style2"
                                                       style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF"
                                                       name="checkCategoryName"
                                                       value="<%=chkOrder.getCheckCategoryName()%>"></td>
                    <td align=right height="44" width="8%">差异原因：</td>
                    <%
                        if (orderStatus.equals(AssetsDictConstant.CHK_STATUS_UPLOADED)) {
                    %>
                    <td height="44" width="90%" colspan="5"><textarea class="input_style1"  name="differenceReason" style="width:100%;height:100%"><%=chkOrder.getDifferenceReason()%></textarea></td>
                    <%
                    } else {
                    %>
                    <td height="44" width="1%" colspan="5"><%=StrUtil.htmlStrEncode(chkOrder.getDifferenceReason())%>
                    </td>
                    <%
                        }
                    %>
                </tr>
            </table>

        </td>
    </tr>
</table>
</div>
<%--<fieldset style="border:1px solid #226E9B; position:absolute;top:194px;width:100%;height:68%">--%>
<%--<legend>--%>
<div id="buttonDiv" style="position:absolute;top:195px;left:1px;width:100%">
    <%
        if (orderStatus.equals(AssetsDictConstant.CHK_STATUS_UPLOADED) && !srcPage.equals(AssetsActionConstant.QUERY_ACTION)) {
    %>
    <img border="0" src="/images/eam_images/archive.jpg" alt="归档" onclick="do_ArchiveOrder()">
    <img border="0" src="/images/eam_images/reject.jpg" alt="退回" onclick="do_BackOrder()" >
    <img border="0" src="/images/eam_images/scan_result.jpg" alt="以扫描结果为准" onClick="do_SelectArchive('<%=AssetsDictConstant.ARCHIVE_AS_SCAN%>')">
    <img border="0" src="/images/eam_images/system_status.jpg" alt="以目前状态为准" onClick="do_SelectArchive('<%=AssetsDictConstant.ARCHIVE_AS_CURR%>')">
    <img border="0" src="/images/eam_images/init_value.jpg" alt="赋初始值" onClick="to_def()">
    <%
        }
    %>
    <!--<img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="do_ViewOpinion(); return false;">-->
    <img border="0" src="/images/eam_images/export.jpg" alt="导出" onclick="do_ExportData()">
    <img border="0" src="/images/eam_images/close.jpg" alt="关闭" onclick="do_Close(); return false;">
    <%
        if (lineCount > 0) {
    %>
    共<%=lineCount%>条设备
    <%
        }
    %>
<%--</legend>--%>
</div>        
<%
    if (lineCount > 0) {
        AmsAssetsCheckLineDTO lineDTO = null;
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:225px;left:1px;width:100%">
    <table class="eamDbHeaderTable" border="1" style="width:170%">
        <tr style="height:20">
            <td width="2%" rowspan="2" align="center">
                <input type="checkBox" name="controlBox" class="headCheckbox" onClick="checkAll(this.name, 'barcodes')">
            </td>
            <td height="38" rowspan="2" align="center" width="9%">标签号</td>
            <td height="20" colspan="6" align="center" width="35%">系统属性</td>
            <td height="20" colspan="6" align="center" width="35%">扫描属性</td>
            <td height="20" align="center" width="8%" colspan="2">状态</td>
            <td height="40" rowspan="2" align="center" width="13%">归档结果</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td height="20" align="center" width="5%">专业</td>
            <td height="20" align="center" width="6%">名称</td>
            <td height="20" align="center" width="6%">型号</td>
            <td height="20" align="center" width="6%">责任人</td>
            <td height="20" align="center" width="6%">责任部门</td>
            <td height="20" align="center" width="6%">使用人</td>
            <td height="20" align="center" width="5%">专业</td>
            <td height="20" align="center" width="6%">名称</td>
            <td height="20" align="center" width="6%">型号</td>
            <td height="20" align="center" width="6%">责任人</td>
            <td height="20" align="center" width="6%">责任部门</td>
            <td height="20" align="center" width="6%">使用人</td>

            <td height="20" align="center" width="4%">系统</td>
            <td height="20" align="center" width="4%">扫描</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:267px;left:1px" align="left"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="170%" border="1" bordercolor="#666666"
           style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            String barcode = "";
            for (int i = 0; i < lineCount; i++) {
                lineDTO = (AmsAssetsCheckLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
        %>
        <tr class="dataTR">
            <td width="2%" align="center"><input type="checkbox" name="barcodes" id="barcodes<%=i%>"
                                                 value="<%=lineDTO.getBarcode()%>;<%=i%>">
                <!--onPropertyChange="setCheckBoxPropValue(this)">-->
            </td>
            <input type="hidden" name="scanStartDate" id="scanStartDate<%=i%>" value="<%=lineDTO.getScanStartDate()%>">
            <td align="center" width="9%"><input type="text" class="finput2" readonly name="barcode" id="barcode<%=i%>"
                                                 value="<%=barcode%>"></td>
            <td align="left" width="5%"><input type="text" class="finput2" readonly name="itemCategoryName"
                                               id="itemCategoryName<%=i%>"
                                               value="<%=lineDTO.getItemCategoryName()%>"><input type="hidden"
                                                                                                 name="itemCategory"
                                                                                                 id="itemCategory<%=i%>"
                                                                                                 value="<%=lineDTO.getItemCategory()%>">
            </td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="itemName" id="itemName<%=i%>"
                                               value="<%=lineDTO.getItemName()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="itemSpec" id="itemSpec<%=i%>"
                                               value="<%=lineDTO.getItemSpec()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="responsibilityUserName"
                                               id="responsibilityUserName<%=i%>"
                                               value="<%=lineDTO.getResponsibilityUserName()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="responsibilityDeptName"
                                               id="responsibilityDeptName<%=i%>"
                                               value="<%=lineDTO.getResponsibilityDeptName()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="maintainUser"
                                               id="maintainUser<%=i%>" value="<%=lineDTO.getMaintainUser()%>"></td>
            <td align="left" width="5%"><input type="text" class="finput" readonly name="scanItemCategoryName"
                                               id="scanItemCategoryName<%=i%>"
                                               value="<%=lineDTO.getScanItemCategoryName()%>"><input type="hidden"
                                                                                                     name="scanItemCategory"
                                                                                                     id="scanItemCategory<%=i%>"
                                                                                                     value="<%=lineDTO.getScanItemCategory()%>">
            </td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="scanItemName"
                                               id="scanItemName<%=i%>" value="<%=lineDTO.getScanItemName()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="scanItemSpec"
                                               id="scanItemSpec<%=i%>" value="<%=lineDTO.getScanItemSpec()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="scanResponsibilityUserName"
                                               id="scanResponsibilityUserName<%=i%>"
                                               value="<%=lineDTO.getScanResponsibilityUserName()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="scanResponsibilityDeptName"
                                               id="scanResponsibilityDeptName<%=i%>"
                                               value="<%=lineDTO.getScanResponsibilityDeptName()%>"></td>
            <td align="left" width="6%"><input type="text" class="finput" readonly name="scanMaintainUser"
                                               id="scanMaintainUser<%=i%>" value="<%=lineDTO.getScanMaintainUser()%>">
            </td>
            <td align="center" width="4%"><input type="text" class="finput2" readonly name="systemStatusName"
                                                 id="systemStatusName<%=i%>" value="<%=lineDTO.getSystemStatusName()%>">
            </td>
            <td align="center" width="4%"><input type="text" class="finput2" readonly name="scanStatusName"
                                                 id="scanStatusName<%=i%>" value="<%=lineDTO.getScanStatusName()%>">
            </td>
            <%
                if (orderStatus.equals(AssetsDictConstant.CHK_STATUS_UPLOADED)) {
            %>
            <td align="left" width="13%"><select style="width:100%" class="noEmptyInput" name="archiveStatus"
                                                 id="archiveStatus<%=i%>"><%=lineDTO.getArchiveOption()%>
            </select></td>
            <td style="display:none">
                <input type="hidden" name="systemStatus" id="systemStatus<%=i%>" value="<%=lineDTO.getSystemStatus()%>">
                <input type="hidden" name="scanStatus" id="scanStatus<%=i%>" value="<%=lineDTO.getScanStatus()%>">
                <input type="hidden" name="scanItemCode" id="scanItemCode<%=i%>" value="<%=lineDTO.getScanItemCode()%>">
                <input type="hidden" name="itemCode" id="itemCode<%=i%>" value="<%=lineDTO.getItemCode()%>">
                <input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
                <input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>"
                       value="<%=lineDTO.getResponsibilityUser()%>">
                <input type="hidden" name="scanResponsibilityUser" id="scanResponsibilityUser<%=i%>"
                       value="<%=lineDTO.getScanResponsibilityUser()%>">
                <input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>"
                       value="<%=lineDTO.getResponsibilityDept()%>">
                <input type="hidden" name="scanResponsibilityDept" id="scanResponsibilityDept<%=i%>"
                       value="<%=lineDTO.getScanResponsibilityDept()%>">
            </td>
            <%
            } else {
            %>
            <td align="left" width="13%"><input type="text" class="finput" readonly name="archiveRemark"
                                                id="archiveRemark<%=i%>" value="<%=lineDTO.getArchiveRemark()%>"></td>
            <%
                }
            %>
            <input type="hidden" name="manufacturerId" value="<%=lineDTO.getManufacturerId()%>">
            <input type="hidden" name="isShare" value="<%=lineDTO.getShare()%>">
            <input type="hidden" name="contentCode" value="<%=lineDTO.getContentCode()%>">
            <input type="hidden" name="contentName" value="<%=lineDTO.getContentName()%>">
            <input type="hidden" name="power" value="<%=lineDTO.getPower()%>">
            <input type="hidden" name="constructStatus" value="<%=lineDTO.getConstructStatus()%>">
            <input type="hidden" name="replaceFlag" value="<%=lineDTO.getReplaceFlag()%>">
            <input type="hidden" name="newTag" value="<%=lineDTO.getNewTag()%>">
            <input type="hidden" name="lneId" value="<%=lineDTO.getLneId()%>">
            <input type="hidden" name="cexId" value="<%=lineDTO.getCexId()%>">
            <input type="hidden" name="opeId" value="<%=lineDTO.getOpeId()%>">
            <input type="hidden" name="nleId" value="<%=lineDTO.getNleId()%>">
        </tr>
        <%
            }
        %>
    </table>
</div>
<%
    }
%>
<%--</fieldset>--%>
<input type="hidden" name="act">

<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</form>
</body>

<script>
function initPage() {
    window.focus();
    do_SetPageWidth();
    do_SetDiffColor();
}


function do_SetDiffColor() {
    var orderStatus = mainFrm.orderStatus.value;
    if (orderStatus == "<%=AssetsDictConstant.CHK_STATUS_UPLOADED%>") {
        var dataTab = document.getElementById("dataTable");
        if (!dataTab || !dataTab.rows) {
            return;
        }
        var rows = dataTab.rows;
        var row = null;
        var cells = null;
        var cell = null;
        var node = null;
        var sysObj = null;
        var scanObj = null;
        for (var i = 0; i < rows.length; i++) {
            sysObj = document.getElementById("systemStatusName" + i);
            scanObj = document.getElementById("scanStatusName" + i);
            if (sysObj.value == "有" && sysObj.value != scanObj.value) {
                row = rows[i];
                cells = row.cells;
                for (var j = 0; j < cells.length - 2; j++) {
                    if (j == 0 || j == cells.length - 4 || j == cells.length - 3) {
                        cell = cells[j];
                        node = cell.childNodes[0];
                        node.style.backgroundColor = "#FF6600";
                        //#CCFF99
                    }
                }
            }
            if (sysObj.value == "无" && sysObj.value != scanObj.value) {
                row = rows[i];
                cells = row.cells;
                for (var j = 0; j < cells.length - 2; j++) {
                    if (j == 0 || j == cells.length - 4 || j == cells.length - 3) {
                        cell = cells[j];
                        node = cell.childNodes[0];
                        node.style.backgroundColor = "#CCFF99";
                        //#CCFF99
                    }
                }
            }
        }
    }
}

function do_ShowDetail(barcode) {
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
}


function do_ArchiveOrder() {
    var dataTab = document.getElementById("dataTable");
    var doAchive = false;
    if (!dataTab || !dataTab.rows) {
        if (!confirm("本工单未扫描到任何设备，确定要归档吗？继续请点击“确定”按钮，否则请点击“取消”按钮！")) {
            return;
        }
        doAchive = true;
    } else {
        var fieldNames = "archiveStatus";
        var fieldLabels = "归档结果";
        var validateType = EMPTY_VALIDATE;
        if (!formValidate(fieldNames, fieldLabels, validateType)) {
            return;
        }
        if (confirm("确认要归档吗？继续请点击“确定”按钮，否则请点击“取消”按钮")) {
            var fieldNames = "archiveStatus";
            var fieldLabels = "归档结果";
            var validateType = EMPTY_VALIDATE;
            if (!formValidate(fieldNames, fieldLabels, validateType)) {
                return;
            }
            doAchive = true;
        }
    }
    if (doAchive) {
        mainFrm.act.value = "<%=AssetsActionConstant.ARCHIVE_ACTION%>";
        mainFrm.submit();
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    }
}

function do_BackOrder() {
	if (confirm("本次盘点工单将退回，继续请点击“确定”按钮，否则请点击“取消”按钮")) {
      	mainFrm.act.value = "<%=AssetsActionConstant.BACK_ACTION %>";
        mainFrm.submit();
    }
}

 function to_def(){
     var tbObj= document.getElementById("dataTable");
     var tb= document.getElementById("dataTable").rows.length;
         for(var i=0;i<tb;i++){
                    if(tbObj.rows[i].cells[14].childNodes[0].value=='有'){
                        document.getElementById("archiveStatus" + i).value = '1';
                    }else if(tbObj.rows[i].cells[14].childNodes[0].value=='无'){
                         document.getElementById("archiveStatus" + i).value = '0';
                    }
         }
 }
function do_SelectArchive(val) {
    if (getCheckedBoxCount("barcodes") == 0) {
        alert("请选择一条记录后，再执行本操作！");
        return;
    }
    var selectedValue = new Array();
    var chCount = getCheckBoxCount("barcodes");
    if (chCount == 1) {
        mainFrm.archiveStatus.value = val;
    } else {
        var checkboxObj = document.all["barcodes"];
        var checkboxLength = checkboxObj.length;
        for (var i = 0; i < checkboxLength; i++) {
            if (checkboxObj[i].checked) {
                document.getElementById("archiveStatus" + checkboxObj[i].value.split(";")[1]).value = val;
            }
        }
    }


    /* var objs = document.getElementsByName("archiveStatus");
     var objCount = objs.length;
     var obj = null;
     for(var i = 0; i < objCount; i++){
         obj = objs[i];
         selectSpecialOptionByItem(obj, val);
     }*/
}

function do_ExportData() {
    mainFrm.act.value = "<%=AssetsActionConstant.EPT_DTL_ACTION%>";
    mainFrm.submit();
}

</script>
