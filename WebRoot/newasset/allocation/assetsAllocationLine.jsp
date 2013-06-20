<%@ page import="com.sino.ams.newasset.dto.AmsAssetsTransLineDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng  
  Date: 2011-3-29
  Time: 15:55:53
  To change this template use File | Settings | File Templates.
--%>
<%
    AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String transType = headerDTO.getTransType();
    String transferType = headerDTO.getTransferType();
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    DTOSet lineSetPri = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    List list = (List) request.getAttribute("REMARK_LIST");
    if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {//部门内调拨
%>
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="120%">
        <tr height="22px" onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="10%">标签号</td>
            <td align=center width="7%">资产编号</td>
            <td align=center width="10%">资产名称</td>
            <td align=center width="10%">资产型号</td>
            <td align=center width="3%">数量</td>
            <td align=center width="13%">调出地点</td>
            <td align=center width="6%">原责任人</td>
            <td align=center width="13%">调入地点<font color="red">*</font></td>
            <td align=center width="6%">新责任人<font color="red">*</font></td>
            <td align=center width="8%">调拨日期<font color="red">*</font></td>
            <td align=center width="11%">摘要</td>
            <td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:45px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (lineSet == null || lineSet.isEmpty()) {
        %>
        <tr id="model" class="dataTR" style="display:none">
            <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
            <td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
            <td width="7%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
            <td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
            <td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
            <td width="13%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
            <td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
            <td width="13%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finputNoEmpty" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer;width:85%"><a href= "#" onClick="do_ChoseLocDesc(this)" title = "点机选择或更改组合调入地点" class="linka" >[…]</a></td>
            <td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finputNoEmpty" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:pointer"></td>
            <td width="8%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finputNoEmpty2" style="cursor:pointer" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期"></td>
            <td width="11%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
            <td style="display:none">
                <input type="hidden" name="oldLocation" id="oldLocation0" value="">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
                <input type="hidden" name="assignedToLocation" id="assignedToLocation0" value="">
                <input type="hidden" name="responsibilityUser" id="responsibilityUser0" value="">
                <input type="hidden" name="addressId" id="addressId0" value="">
            </td>
        </tr>
        <%
        } else {
            AmsAssetsTransLineDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode(); 
        %>
        <tr id="model" class="dataTR" style="cursor:pointer">
            <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
            <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
            <td width="7%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
            <td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
            <td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
            <td width="13%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
            <td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
            <td width="13%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer;width:85%"><a href= "#" onClick="do_ChoseLocDesc(this)" title = "点机选择或更改组合调入地点" class="linka" >[…]</a></td>
            <td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:pointer"></td>
            <td width="8%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finputNoEmpty2" style="cursor:pointer" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate<%=i%>)" title="点击选择或更改调拨日期"></td>
            <td width="11%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
            <td style="display:none">
                <input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
                <input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>">
                <input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
                <input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>

<%
    } else if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT_RFU)) {//紧急调拨(补汇总)
%>
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="100%">
        <tr height="22px" onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="14%">调拨单号</td>
            <td align=center width=" 8%">标签号</td>
            <td align=center width=" 7%">资产编号</td>
            <td align=center width="12%">资产名称</td>
            <td align=center width="10%">规则型号</td>
            <td align=center width=" 8%">数量</td>
            <td align=center width=" 5%">单位</td>
            <td align=center width="18%">调出地点</td>
            <td align=center width="18%">调入地点</td>
            <td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:45px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (lineSet == null || lineSet.isEmpty()) {
        %>
        <tr id="model" class="dataTR" style="display:none">
            <td width=" 3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
            <td width="14%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="transNo" id="transNo0" class="finput" readonly value=""></td>
            <td width=" 8%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
            <td width=" 7%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
            <td width="12%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
            <td width="10%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
            <td width=" 8%" align="right " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput" readonly value=""></td>
            <td width=" 5%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
            <td width="18%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="fromObjectName" id="fromObjectName0" class="finput" readonly value=""></td>
            <td width="18%" align="left  " title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="toObjectName" id="toObjectName0" class="finput" readonly value=""></td>

            <td style="display:none"><input type="hidden" name="transTypeDefine" id="transTypeDefine0" value=""></td>
            <%--
            <td style="display:none"><input type="hidden" name="transId" id="transId0" value=""></td>
            --%>
            <td style="display:none"><input type="hidden" name="assetId" id="assetId0" value=""></td>
            <td style="display:none"><input type="hidden" name="fromObjectNo" id="fromObjectNo0" value=""></td>
            <td style="display:none"><input type="hidden" name="toObjectNo" id="toObjectNo0" value=""></td>
            
            <td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation0" value=""></td>
            <td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value=""></td>
            <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation0" value=""></td>
            <td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser0" value=""></td>
            <td style="display:none"><input type="hidden" name="addressId" id="addressId0" value=""></td>
            <td style="display:none"><input type="hidden" name="remark" id="remark0" value=""></td>
        </tr>
        <%
        } else {
            AmsAssetsTransLineDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
        %>
        <tr id="model" class="dataTR" style="cursor:pointer">
            <td width=" 3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>"></td>
            <td width="14%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="transNo" id="transNo<%=i%>" class="finput" readonly value="<%=lineDTO.getTransNo()%>"></td>
            <td width=" 8%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
            <td width=" 7%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
            <td width="12%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
            <td width="10%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
            <td width=" 8%" align="right " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
            <td width=" 5%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
            <td width="18%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="fromObjectName" id="fromObjectName<%=i%>" class="finput" readonly value="<%=lineDTO.getFromObjectName()%>"></td>
            <td width="18%" align="left  " title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="toObjectName" id="toObjectName<%=i%>" class="finput" readonly value="<%=lineDTO.getToObjectName()%>"></td>

            <td style="display:none"><input type="hidden" name="transTypeDefine" id="transTypeDefine<%=i%>" value="<%=lineDTO.getTransTypeDefine()%>"></td>
            <td style="display:none"><input type="hidden" name="assetId" id="assetId<%=i%>" value="<%=lineDTO.getAssetId()%>"></td>
            <td style="display:none"><input type="hidden" name="fromObjectNo" id="fromObjectNo<%=i%>" value="<%=lineDTO.getFromObjectNo()%>"></td>
            <td style="display:none"><input type="hidden" name="toObjectNo" id="toObjectNo<%=i%>" value="<%=lineDTO.getToObjectNo()%>"></td>
        
            <td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>"></td>
            <td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>"></td>
            <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>"></td>
            <td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>"></td>
            <td style="display:none"><input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>"></td>
            <td style="display:none"><input type="hidden" name="remark" id="remark<%=i%>" value="<%=lineDTO.getRemark()%>"></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<%
	} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) {//部门间调拨
%>
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="140%">
        <tr height=23px>
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="10%">标签号</td>
            <td align=center width="7%">资产编号</td>
            <td align=center width="10%">资产名称</td>
            <td align=center width="10%">资产型号</td>
            <td align=center width="3%">数量</td>
            <td align=center width="10%">调出地点</td>
            <td align=center width="6%">原责任人</td>
            <td align=center width="10%">调入部门<font color="red">*</font></td>
            <td align=center width="10%">调入地点</td>
            <td align=center width="6%">新责任人</td>
            <td align="center" width="5%">调拨日期<font color="red">*</font></td>
            <td align=center width="10%">备注</td>
            <td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:45px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (lineSet == null || lineSet.isEmpty()) {
        %>
        <tr id="model" class="dataTR" style="display:none">
            <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
            <td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
            <td width="7%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
            <td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
            <td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
            <td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
            <td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
            <td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName0" class="finputNoEmpty" readonly value="" onClick="do_SelectDept(this)" title="点击选择或更改调入部门" style="cursor:pointer"></td>
            <td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finput" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer;width:85%"><a href= "#" onClick="do_ChoseLocDesc(this)" title = "点机选择或更改组合调入地点" class="linka" >[…]</a></td>
            <td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:pointer"></td>
            <td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finputNoEmpty2" style="cursor:pointer" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期"></td>
            <td width="10%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
            <td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation0" value=""></td>
            <td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value=""></td>
            <td style="display:none"><input type="hidden" name="responsibilityDept" id="responsibilityDept0" value=""></td>
            <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation0" value=""></td>
            <td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser0" value=""></td>
            <td style="display:none"><input type="hidden" name="addressId" id="addressId0" value=""></td>
        </tr>
        <%
        } else {
            AmsAssetsTransLineDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
        %>
        <tr id="model" class="dataTR" style="cursor:pointer">
            <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
            <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
            <td width="7%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
            <td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
            <td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
            <td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
            <td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
            <td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getResponsibilityDeptName()%>" onClick="do_SelectDept(this)" title="点击选择或更改调入部门" style="cursor:pointer"></td>
            <td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer;width:85%"><a href= "#" onClick="do_ChoseLocDesc(this)" title = "点机选择或更改组合调入地点" class="linka" >[…]</a></td>
            <td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:pointer"></td>
            <td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finputNoEmpty2" style="cursor:pointer" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate<%=i%>)" title="点击选择或更改调拨日期"></td>
            <td width="10%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
            <td style="display:none">
                <input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
                <input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>" value="<%=lineDTO.getResponsibilityDept()%>">
                <input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>">
                <input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
                <input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<%
} else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {//地市间调拨
%>
<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:25px;left:0px;width:100%">
    <table width="280%" border="1" class="eamDbHeaderTable">
        <tr height=22px >
            <td align=center width="1%" rowspan="2"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="66%" colspan="17">调出方</td>
            <td align=center width="26%" colspan="5">调入方</td>
            <td align=center width="3%" rowspan="2">调拨日期<font color="red">*</font></td>
            <td align=center width="4%" rowspan="2">备注</td>
            <td style="display:none" rowspan="2">隐藏域字段</td>
        </tr>
        <tr height=22px >
            <td align=center width="3%">标签号</td>
            <td align=center width="3%">资产编号</td>
            <td align=center width="4%">资产名称</td>

            <td align=center width="4%">资产型号</td>
            <td align=center width="3%">数量</td>
            <td align=center width="3%">原值</td>

            <td align=center width="3%">累计折旧</td>
            <td align=center width="3%">减值准备</td>
            <td align=center width="3%">残值</td>

            <td align=center width="3%">资产净额</td>
            <td align=center width="5%">厂商</td>
            <td align=center width="3%">启用日期</td>

            <td align=center width="6%">调出部门</td>
            <td align=center width="6%">调出地点</td>
            <td align=center width="2%">原责任人</td>

            <td align=center width="8%">原折旧账户</td>
            <td align=center width="4%">原类别</td>
            <td align=center width="6%">调入部门<font color="red">*</font></td>

            <td align=center width="6%">调入地点</td>
            <td align=center width="2%">新责任人</td>
            <td align=center width="8%">新折旧账户</td>
            <td align=center width="4%">新类别</td>
            <td style="display:none">隐藏域列</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:66px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="280%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (lineSet == null || lineSet.isEmpty()) {
        %>
        <tr id="model" class="dataTR" style="display:none">
            <td width="1%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>

            <td width="3%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
            <td width="3%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
            <td width="4%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>

            <td width="4%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost0" class="finput3" readonly value=""></td>

            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="sumDepreciation" id="sumDepreciation0" class="finput3" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="impairReserve" id="impairReserve0" class="finput3" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="scrapValue" id="scrapValue0" class="finput3" readonly value=""></td>

            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost0" class="finput3" readonly value=""></td>
            <td width="5%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="manufacturerName" id="manufacturerName0" class="finput3" readonly value=""></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>

            <td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName0" class="finput" readonly value=""></td>
            <td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
            <td width="2%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>

            <td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldDepreciationAccount" id="oldDepreciationAccount0" class="finput" readonly value=""></td>
            <td width="4%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode0" class="finput2" readonly value=""></td>
            <td width="6%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName0" class="finputNoEmpty" style="cursor:pointer" readonly value="" onClick="do_SelectDept(this)" title="点击选择或更改调入部门"></td>

            <td width="6%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finput" style="cursor:pointer;width:85%" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点"><a href= "#" onClick="do_ChoseLocDesc(this)" title = "点机选择或更改组合调入地点" class="linka" >[…]</a></td>
            <td width="2%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput" style="cursor:pointer" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人"></td>
            <td width="8%" align="center"><input type="text" name="depreciationAccount" id="depreciationAccount0" class="finput2" style="cursor:pointer" readonly value="" onClick="do_SelectDepreciationAccount(this)" title="点击选择或更改折旧账户"></td>

            <td width="4%" align="right"><input type="text" name="faCategoryCode" id="faCategoryCode0" class="finput2" readonly></td>
            <td width="3%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finputNoEmpty2" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期"></td>
            <td width="4%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>

            <td style="display:none">
                <input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
                <input type="hidden" name="oldLocation" id="oldLocation0" value="">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
                <input type="hidden" name="responsibilityDept" id="responsibilityDept0" value="">
                <input type="hidden" name="assignedToLocation" id="assignedToLocation0" value="">
                <input type="hidden" name="responsibilityUser" id="responsibilityUser0" value="">
                <input type="hidden" name="addressId" id="addressId0" value="">
            </td>
        </tr>
        <%
        } else {
            AmsAssetsTransLineDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
        %>
        <tr id="model" class="dataTR" style="cursor:pointer">
            <td width="1%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>

            <td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
            <td width="3%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
            <td width="4%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>

            <td width="4%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>

            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="sumDepreciation" id="sumDepreciation<%=i%>" class="finput3" readonly value="<%=lineDTO.getSumDepreciation()%>"></td>
            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="impairReserve" id="impairReserve" class="finput3" readonly value="<%=lineDTO.getImpairReserve()%>"></td>
            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="scrapValue" id="scrapValue<%=i%>" class="finput3" readonly value="<%=lineDTO.getScrapValue()%>"></td>

            <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
            <td width="5%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="manufacturerName" id="manufacturerName" class="finput3" readonly value="<%=lineDTO.getManufacturerName()%>"></td>
            <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>

            <td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>"  class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
            <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
            <td width="2%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>

            <td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldDepreciationAccount" id="oldDepreciationAccount<%=i%>" class="finput" readonly value="<%=lineDTO.getOldDepreciationAccount()%>"></td>
            <td width="4%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getOldFaCategoryCode()%>"></td>
            <td width="6%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finputNoEmpty" style="cursor:pointer" readonly value="<%=lineDTO.getResponsibilityDeptName()%>" onClick="do_SelectDept(this)" title="点击选择或更改调入部门"></td>

            <td width="6%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" style="cursor:pointer;width:85%" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点"><a href= "#" onClick="do_ChoseLocDesc(this)" title = "点机选择或更改组合调入地点" class="linka" >[…]</a></td>
            <td width="2%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人"></td>
            <td width="8%" align="center"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getDepreciationAccount()%>" onClick="do_SelectDepreciationAccount(this)" title="点击选择或更改折旧账户"></td>

            <td width="4%" align="right"><input type="text" name="faCategoryCode" id="faCategoryCode<%=i%>" class="finput2" readonly value="<%=lineDTO.getFaCategoryCode()%>"></td>
            <td width="3%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finputNoEmpty2" style="cursor:pointer" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate<%=i%>)" title="点击选择或更改调拨日期"></td>
            <td width="4%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>

            <td style="display:none">
                <input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
                <input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
                <input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>" value="<%=lineDTO.getResponsibilityDept()%>">
                <input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>">
                <input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
                <input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <table width="280%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <tr height="23px">
            <td align=center width="15%" colspan="5" rowspan="2">合计</td>
            <td align=center width="3%">数量</td>
            <td align=center width="3%">原值</td>

            <td align=center width="3%">累计折旧</td>
            <td align=center width="3%">减值准备</td>
            <td align=center width="3%">残值</td>

            <td align=center width="3%">资产净额</td>
            <td align=center width="5%"></td>
            <td align=center width="3%"></td>

            <td align=center width="6%"></td>
            <td align=center width="6%"></td>
            <td align=center width="2%"></td>

            <td align=center width="8%"></td>
            <td align=center width="4%"></td>
            <td align=center width="6%"></td>

            <td align=center width="6%"></td>
            <td align=center width="2%"></td>
            <td align=center width="8%"></td>
            <td align=center width="4%"></td>


            <td align=center width="3%"></td>
            <td align=center width="4%"></td>
            <td style="display:none">隐藏域列</td>
        </tr>
        <tr height="23px">
            <td align=center width="3%"><input readonly="readonly" class="finput2" id="numValue" /></td>
            <td align=center width="3%"><input readonly="readonly" class="finput2" id="yuanzhiValue" /></td>

            <td align=center width="3%"><input readonly="readonly" class="finput2" id="ljzjValue" /></td>
            <td align=center width="3%"><input readonly="readonly" class="finput2" id="jzzbValue" /></td>
            <td align=center width="3%"><input readonly="readonly" class="finput2" id="canzhiValue"/></td>

            <td align=center width="3%"><input readonly="readonly" class="finput2" id="jingerValue" /></td>
            <td align=center width="5%"></td>
            <td align=center width="3%"></td>

            <td align=center width="6%"></td>
            <td align=center width="6%"></td>
            <td align=center width="2%"></td>

            <td align=center width="8%"></td>
            <td align=center width="4%"></td>
            <td align=center width="6%"></td>

            <td align=center width="6%"></td>
            <td align=center width="2%"></td>
            <td align=center width="8%"></td>
            <td align=center width="4%"></td>


            <td align=center width="3%"></td>
            <td align=center width="4%"></td>
            <td style="display:none">隐藏域列</td>
        </tr>
    </table>
</div>
<%
    }
%>