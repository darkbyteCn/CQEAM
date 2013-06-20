<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
DTOSet lineSetPri = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
	String widthArr[] = { "7%" , "3%" , "7%" , "8%" , "6%"
						, "3%" , "3%" , "3%"  , "3%" , "3%" 
						, "3%" , "3%" , "3%" , "3%" , "3%" 
						, "5%" , "8%" , "6%" , "8%" , "4%"
						, "7%" };
	int widthIndex = 0;
%>
<div id="headDiv" style="overflow: hidden; left: 1px; width: 100%">
    <table id="headTable" class="headerTable" border="1" width="230%">
        <tr height=23px onClick="executeClick(this)" style="cursor: pointer" title="点击全选或取消全选">
            <td align=center width="1%">
                <input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')">
            </td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">标签号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产编号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">报废类型<font color="red">*</font></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产名称</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产型号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">重要资产</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">数量</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">单位</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产原值</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">累计折旧</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">累计减值</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">净额</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">报废成本</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">剩余月数</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">启用日期</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产目录</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">所在地点</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">网元编号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">责任部门</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">责任人</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">备注</td>
            <td style="display: none">
                隐藏域字段
            </td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow: scroll; height: 400px; width: 100%;top: 48px; left: 1px;" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="230%" border="1" bordercolor="#666666" style="TABLE-LAYOUT: fixed; word-break: break-all">
<%
    if (lineSet == null || lineSet.isEmpty()) {
        widthIndex = 0;
%>
        <tr class="dataTR" onClick="executeClick(this)" style="display: none" title="点击查看资产详细信息">
            <td width="1%" align="center">
                <input type="checkbox" name="subCheck" id="subCheck0" value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="barcode" id="barcode0" class="finput2" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer">
                <select name="rejectType" id="rejectType0" style="width: 100%"onchange=do_SetCheckCategory(this); class="selectNoEmpty"><%=headerDTO.getRejectTypeHOpt()%></select>
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="importantFlag" id="importantFlag0" class="finput2" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput2" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="cost" id="cost0" class="finput3" readonly value="">
            </td>
             <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="sumDepreciation" id="sumDepreciation0" class="finput3" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="impairReserve" id="impairReserve0" class="finput3" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="deprnCost" id="deprnCost0" class="finput3" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right">
                <input type="text" name="retirementCost" id="retirementCost0" class="finput" value="" onchange="do_setQuantity();">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="deprnLeftMonth" id="deprnLeftMonth0" class="finput3" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode0" class="finput" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left">
                <input type="text" name="netUnit" id="netUnit0" class="finput" value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName0" class="finput" readonly value="">
            </td>
             <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value="">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left">
                <input type="text" name="remark" id="remark0" class="finput" value="">
            </td>
            <td style="display: none" width="0">
                <input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
                <input type="hidden" name="oldLocation" id="oldLocation0" value="">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
            </td>
        </tr>
    <%
        } else {
            AmsAssetsTransLineDTO lineDTO = null;
            String barcode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
                barcode = lineDTO.getBarcode();
                widthIndex = 0;
    %>
        <tr class="dataTR" onClick="executeClick(this)" style="cursor: pointer" title="点击查看资产“<%=barcode%>”详细信息">
            <td width="1%" align="center">
                <input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer">
                <select name="rejectType" id="rejectType<%=i%>" onchange="do_SetCheckCategory(this);" class="finput" style="width: 100%"><%=lineDTO.getRejectTypeOpt()%></select>
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="importantFlag" id="importantFlag<%=i%>" class="finput2" readonly value="<%=lineDTO.getImportantFlag()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput2" readonly value="<%=lineDTO.getUnitOfMeasure()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="sumDepreciation" id="sumDepreciation<%=i%>" class="finput3" readonly value="<%=lineDTO.getSumDepreciation()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="impairReserve" id="impairReserve<%=i%>" class="finput3" readonly value="<%=lineDTO.getImpairReserve()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="right">
                <input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finputNoEmpty" value="<%=lineDTO.getDeprnCost()%>" onchange="do_setQuantity();">
            </td>
             <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="deprnLeftMonth" id="deprnLeftMonth<%=i%>" class="finput2" readonly value="<%=lineDTO.getDeprnLeftMonth()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getOldFaCategoryCode()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left"><input type="text" name="netUnit" id="netUnit<%=i%>" class="finput" value="<%=lineDTO.getNetUnit() %>"></td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>">
            </td>
              <td width="<%= widthArr[ widthIndex ++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail(this)">
                <input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>">
            </td>
            <td width="<%= widthArr[ widthIndex ++ ] %>" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
            <td style="display: none" width="0">
                <input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
                <input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
                <input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
            </td>
        </tr>
<%
        }
    }
    widthIndex = 6;
%>
    </table>

    <table id="summaryTable" width="230%" border="1" bordercolor="#666666" style="TABLE-LAYOUT: fixed; word-break: break-all">
        <tr height=23px>
            <td align=center width="35%" colspan="7">合计</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="numValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="yuanzhiValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="ljzjValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="ljjzalue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="jingeralue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"><input readonly="readonly" class="finput2" id="bfzbValue" /></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>"></td>
            <td align=center width="<%= widthArr[ widthIndex] %>"></td>
            <td style="display: none" width="0"></td>
        </tr>
    </table>
</div>
<div id="transferDiv" style="position:relative; bottom: 0px; top: 0px; left: 0px; right: 0px; z-index: 10; visibility: hidden; width: 100%; height: 100%">
    <table width="100%" class="headerTable">
        <tr class="headerTable">
            <td height="10%" width="10%" align="center">
                行号
            </td>
            <td height="10%" width="10%" align="center">
                资产标签号
            </td>
            <td height="10%" width="30%" align="center">
                可能存在原因
            </td>
        </tr>
    </table>
    <div   style= "overflow:auto;height:70% "> 
    <table width="100%" bgcolor="#CCCCFF">
        <%
            if (lineSetPri == null || lineSetPri.isEmpty()) {
        %>
        <tr>
            <td height="10%" width="10%"></td>
            <td height="10%" width="10%"></td>
            <td height="10%" width="30%"></td>
        </tr>
        <%
            } else {
                int count = lineSetPri.getSize();
                for (int i = 0; i < count; i++) {
                    AmsAssetsTransLineDTO lineDTO = null;
                    lineDTO = (AmsAssetsTransLineDTO) lineSetPri.getDTO(i);
        %>
        <tr>
            <%
                if (lineSetPri.getSize() > i) {
            %>
            <td height="10%" width="10%" align="center">
                <font size="2" color="#FF0000"><%=lineDTO.getExcelLineId()%></font>
            </td>
            <td height="10%" width="10%" align="center">
                <font size="2" color="#FF0000"><%=lineDTO.getBarcode()%></font>
            </td>
            <td height="10%" width="30%" align="center">
                <font size="2" color="#FF0000"><%=lineDTO.getErrorMsg()%></font>
            </td>
            <%
                } else {
            %>
            <td height="10%" width="10%" align="center"></td>
            <td height="10%" width="10%" align="center"></td>
            <td height="10%" width="30%" align="center"></td>
            <%
                }
            %>
        </tr>
        <%
            }
            }
        %>
        <tr>
            <td height="10%" width="50%" align="center" bgcolor="#CCCCFF"
                colspan="2">
                <img src="/images/eam_images/close.jpg" onClick="do_CloseDiv();">
            </td>
        </tr>
    </table>
    </div>
</div>