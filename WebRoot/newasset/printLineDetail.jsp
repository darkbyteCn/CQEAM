<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.newasset.scrap.constant.ScrapAppConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Calendar" %>

<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	AmsAssetsTransLineDTO lineDTO = null; 
	String barcode = "";
	
	float TOTAL_COST = 0;
    float TOTAL_DEPRECIATION = 0;
    float TOTAL_NET_ASSET_VALUE = 0;
    float TOTAL_IMPAIR_RESERVE = 0;
    float TOTAL_DEPRN_COST = 0;
    float TOTAL_SCRAP_VALUE = 0;
	Calendar c = Calendar.getInstance();
	int month = c.get(Calendar.MONTH) + 1;
 	String curDate = c.get(Calendar.YEAR) + "年" + month + "月";
 	
	if(transType.equals(AssetsDictConstant.ASS_SUB)){//资产减值
%>
		<table class="headerTable" border="1" width="100%">
	        <tr height=20px>
	            <td align=center width="4%">网元编号</td>
	            <td align=center width="4%">资产编号</td>
                <td align=center width="7%">资产标签号</td>
                <td align=center width="8%">资产名称</td>
	            <td align=center width="8%">供应商</td>
	            <td align=center width="4%">规格型号</td>
	            <td align=center width="4%">启用日期</td>
	            <td align=center width="5%">软件在用版本</td>
				<td align=center width="5%">软件减值版本</td>
	            <td align=center width="4%">原值</td>
	            <td align=center width="4%">累计折旧</td>
	            <td align=center width="4%">净值</td>
	            <td align=center width="4%">拟提减值</td>
	            <td align=center width="6%">备注</td>
            </tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="4%" align="center"><%=lineDTO.getNetUnit()%></td>
				<td width="4%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="7%" align="center"><%=barcode%></td>
				<td width="8%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="8%" align="left"><%=lineDTO.getVendorName()%></td>
				<td width="4%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="4%" align="right"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="5%" align="center"><%=lineDTO.getSoftInuseVersion()%></td>
				<td width="5%" align="center"><%=lineDTO.getSoftDevalueVersion()%></td>
				<td width="4%" align="right"><%=lineDTO.getCost()%></td>
				<td width="4%" align="right"><%=lineDTO.getDepreciation()%></td>
				<td width="4%" align="right"><%=lineDTO.getDeprnCost()%></td>
				<td width="4%" align="right"><%=lineDTO.getPrepareDevalue()%></td>
				<td width="6%" align="right"><%=lineDTO.getRemark()%></td>
            </tr>
<%
		}
	} else if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置
%>
		<table class="headerTable" border="1" width="100%">
	        <tr height=20px>
	            <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂家</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">闲置说明</td>
	            <td align=center width="8%">其他说明</td>
	        </tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><%=barcode%></td>
				<td width="6%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="10%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="6%" align="right"><%=lineDTO.getVendorName()%></td>
				<td width="6%" align="center"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="4%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="3%" align="center"><%=lineDTO.getUnitOfMeasure()%></td>
				<td width="16%" align="right"><%=lineDTO.getOldLocationName()%></td>
				<td width="8%" align="left"><%=lineDTO.getLineReason()%></td>
				<td width="8%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
		}
	} else if( transType.equals( ScrapAppConstant.TRANS_TYPE )){//其他报废资产
%>
		<table class="headerTable" border="1" width="100%">
			<tr height="20px">
				<td align=center width="10%">资产标签</td>
				<td align=center width="10%">资产名称</td>
				<td align=center width="10%">资产型号</td>
				<td align=center width="5%">启用日期</td>
				<td align=center width="15%">所在地点</td>
				<td align=center width="5%">责任人</td>
				<td align=center width="15%">责任部门</td>
			</tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
		if( null != lineSet ){
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><%=barcode%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="10%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="5%" align="center"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="15%" align="right"><%=lineDTO.getOldLocationName()%></td>
				<td width="5%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="15%" align="left"><%=lineDTO.getOldResponsibilityDeptName()%></td>
            </tr>
            
<%
			}
		}
	} else if(!transType.equals(AssetsDictConstant.ASS_RED)){//报废或处置	
	
%>
<!-- 
		<table class="headerTable" border="1" width="100%">
			<tr height="20px">
				<td align=center width="10%">资产标签</td>
				<td align=center width="10%">资产编号</td>
				<td align=center width="10%">资产名称</td>
				<td align=center width="10%">资产型号</td>
				<td align=center width="9%">资产原值</td>
				<td align=center width="9%">启用日期</td>
				<td align=center width="9%">净值</td>
				<td align=center width="9%">所在地点</td>
				<td align=center width="10%">责任人</td>
				<td align=center width="10%">责任部门</td>
			</tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
 -->       
		<table border="0" width="100%" >
		    <tr>
		        <td colspan=3 align=center width=100% height=30>固定资产报废清单</td>
		    </tr>
		    <tr>
		        <td colspan=3 align=center width=100% height=20><%=curDate%></td>
		    </tr>
		    <tr>
		        <td colspan=3 align=center width=100% height=20></td>
		    </tr>
		    <tr>
            	<td align=left width=20%>单位名称：<%=headerDTO.getFromCompanyName()%></td>
                <td align=left width=60%>报废单号：<%=headerDTO.getTransNo()%></td>
                <td align=right width="20%">(单位：元)</td>
		    </tr>
		</table> 
		
		<table class="headerTable4" border="1" width="100%" bordercolor="#666666" style="text-align:center;font-size:12px">
			<tr height="40px">
				<td width="2%">序号</td>
				<td width="3%">网元<br>编号</td>
				<td width="5%">资产<br>编号</td>

				<td width="8%">资产<br>标签</td>
				<td width="5%">资产<br>名称</td>
				<td width="11%">类项<br>目节</td>

				<td width="5%">资产类别<br>末级名称</td>
				<td width="4%">供应商</td>
				<td width="7%">规格<br>程式</td>

				<td width="6%">启用<br>日期</td>
				<td width="4%">剩余折<br>旧月数</td>
				<td width="4%">软件在<br>用版本</td>

				<td width="4%">软件报<br>废版本</td>
				<td width="4%">资产<br>原值</td>
				<td width="5%">累计<br>折旧</td>

				<td width="4%">资产<br>净值</td>
				<td width="4%">减值<br>准备</td>
				<td width="4%">资产<br>净额</td>
				<td width="2%">备注</td>
			</tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" >
<%
		if( null != lineSet ){
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
				TOTAL_COST += lineDTO.getCost();
    			TOTAL_DEPRECIATION += lineDTO.getDepreciation();
    			TOTAL_NET_ASSET_VALUE += Float.parseFloat(lineDTO.getNetAssetValue());
    			TOTAL_IMPAIR_RESERVE += lineDTO.getImpairReserve();
    			TOTAL_DEPRN_COST += lineDTO.getDeprnCost();
%>

            <tr style="height:23px;text-align:center;font-size:12px">
				<td width="2% " align="center"><%=i+1%></td>
				<td width="3% " align="left"><%=lineDTO.getNetUnit()%></td>
				<td width="5% " align="center"><%=lineDTO.getAssetNumber()%></td>

				<td width="8% " align="center"><%=barcode%></td>
				<td width="5% " align="left"  ><%=lineDTO.getAssetsDescription()%></td>
				<td width="11% " align="center"><%=lineDTO.getContentCode()%></td>

				<td width="5% " align="left"  ><%=lineDTO.getContentName()%></td>
				<td width="4% " align="left"  ><%=lineDTO.getManufacturerName()%></td>
				<td width="7% " align="left"  ><%=lineDTO.getModelNumber()%></td>

				<td width="6% " align="center" ><%=lineDTO.getDatePlacedInService()%></td>
				<td width="4% " align="right"><%=lineDTO.getDeprnLeftMonth()%></td>
				<td width="4% " align="left"  ><%=lineDTO.getSoftInuseVersion()%></td>

				<td width="4% " align="left"  ><%=lineDTO.getSoftDevalueVersion()%></td>
				<td width="4% " align="right" ><%=lineDTO.getCost()%></td>
                <td width="5% " align="right" ><%=lineDTO.getDepreciation()%></td>

                <td width="4% " align="right" ><%=lineDTO.getNetAssetValue()%></td>
				<td width="4% " align="right" ><%=lineDTO.getImpairReserve()%></td>
				<td width="4% " align="right" ><%=lineDTO.getDeprnCost()%></td>
				<td width="2% " align="right" ><%=lineDTO.getRemark()%></td>
            </tr>
<%
			}
%>
        <tr bgcolor="yellow" >
            <td colspan="13">&nbsp;合&nbsp;&nbsp;计&nbsp;</td>
			<td width="4%" align="right"><%=TOTAL_COST%></td>
			<td width="5%" align="right"><%=TOTAL_DEPRECIATION%></td>
			<td width="4%" align="right"><%=TOTAL_NET_ASSET_VALUE%></td>
			<td width="4%" align="right"><%=TOTAL_IMPAIR_RESERVE%></td>
			<td width="4%" align="right"><%=TOTAL_DEPRN_COST%></td>
            <td width="2%" align="right"></td>
        </tr>
<%
        }
	} else {//资产调拨
		if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){//部门内调拨
%>
		<table class="headerTable" border="1" width="100%">
			<tr height="20px">
				<td align=center width="9%">资产标签</td>
				<td align=center width="6%">资产编号</td>
				<td align=center width="8%">资产名称</td>
				<td align=center width="8%">资产型号</td>
				<td align=center width="4%">数量</td>
				<td align=center width="20%">调出地点</td>
				<td align=center width="6%">原责任人</td>
				<td align=center width="20%">调入地点</td>
				<td align=center width="6%">新责任人</td>
				<td align=center width="7%">调拨日期</td>
				<td align=center width="7%">摘要</td>
			</tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="9%" align="center"><%=barcode%></td>
				<td width="6%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="8%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="8%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="4%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="20%"><%=lineDTO.getOldLocationName()%></td>
				<td width="6%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="20%"><%=lineDTO.getAssignedToLocationName()%></td>
				<td width="6%" align="left"><%=lineDTO.getResponsibilityUserName()%></td>
				<td width="7%" align="center"><%=lineDTO.getLineTransDate()%></td>
				<td width="7%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){//部门间调拨
%>
		<table class="headerTable" border="1" width="100%">
	        <tr height="20px">
	            <td align=center width="10%">资产标签</td>
	            <td align=center width="7%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="3%">数量</td>
	            <td align=center width="10%">调出地点</td>
	            <td align=center width="6%">原责任人</td>
	            <td align=center width="10%">调入部门</td>
				<td align=center width="10%">调入地点</td>
	            <td align=center width="6%">新责任人</td>
				<td align="center" width="5%">调拨日期</td>
	            <td align=center width="10%">备注</td>
	        </tr>
	    </table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><%=barcode%></td>
				<td width="7%" align="left"><%=lineDTO.getAssetNumber()%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="10%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="3%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="10%" align="center"><%=lineDTO.getOldLocationName()%></td>
				<td width="6%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="10%" align="right"><%=lineDTO.getResponsibilityDeptName()%></td>
				<td width="10%" align="right"><%=lineDTO.getAssignedToLocationName()%></td>
				<td width="6%" align="left"><%=lineDTO.getResponsibilityUserName()%></td>
				<td width="5%" align="center"><%=lineDTO.getLineTransDate()%></td>
				<td width="10%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){//公司间调拨
%>
<!-- 
	    <table width="100%" border="1" class="headerTable">
	        <tr height="20px">
	            <td align=center width="64%" colspan="14">调出方</td>
	            <td align=center width="25%" colspan="5">调入方</td>
	            <td align=center width="5%" rowspan="2">调拨日期</td>
	            <td align=center width="7%" rowspan="2">备注</td>
	        </tr>
	        <tr height="20px">
	            <td align=center width="5%">资产标签</td>
	            <td align=center width="5%">资产编号</td>
	            <td align=center width="5%">资产名称</td>
	            <td align=center width="5%">资产型号</td>
	            <td align=center width="3%">数量</td>
	            <td align=center width="3%">原值</td>
	            <td align=center width="5%">累计折旧</td>
	            <td align=center width="3%">残值</td>
	            <td align=center width="5%">启用日期</td>
				<td align=center width="5%">调出部门</td>
	            <td align=center width="5%">调出地点</td>
	            <td align=center width="5%">原责任人</td>
	            <td align=center width="5%">原折旧账户</td>
	            <td align=center width="5%">原类别</td>
	            <td align=center width="5%">调入部门</td>
				<td align=center width="5%">调入地点</td>
				<td align=center width="5%">新责任人</td>
				<td align=center width="5%">新折旧账户</td>
	            <td align=center width="5%">新类别</td>
	        </tr>
	    </table>
 -->	    
		<table border="0" width="100%" >
		    <tr>
		        <td colspan=4 align=center width=100% height=30>固定资产调拨清单(公司间)</td>
		    </tr>
		    <tr>
		        <td colspan=43 align=center width=100% height=20></td>
		    </tr>
		    <tr>
            	<td align=left width=20%>调出单位：<%=headerDTO.getFromCompanyName()%></td>
                <td align=left width=20%>调入单位：<%=headerDTO.getToCompanyName()%></td>
                <td align=left width=25%>调拨单号：<%=headerDTO.getTransNo()%></td>
                <td align=right width="35%">填报日期：<%=curDate%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(单位：元)</td>
		    </tr>
		</table> 
	    <table width="100%" border="1" class="headerTable4">
	        <tr height="20px">
	            <td align=center width="2% "><font style="font-size:11px">序号</font></td>
	            <td align=center width="7% "><font style="font-size:11px">原资产编号</font></td>
	            <td align=center width="8% "><font style="font-size:11px">原资产标签</font></td>
	            <td align=center width="12%"><font style="font-size:11px">资产名称</font></td>
	            <td align=center width="11%"><font style="font-size:11px">规格型号</font></td>
	            <td align=center width="4% "><font style="font-size:11px">单位</font></td>
	            <td align=center width="5% "><font style="font-size:11px">数量</font></td>
	            <td align=center width="6% "><font style="font-size:11px">启用日期</font></td>
	            <td align=center width="5% "><font style="font-size:11px">折旧年限(月)</font></td>
	            <td align=center width="10%"><font style="font-size:11px">类项目节</font></td>
	            <td align=center width="7% "><font style="font-size:11px">原值</font></td>
	            <td align=center width="7% "><font style="font-size:11px">累计折旧</font></td>
	            <td align=center width="3% "><font style="font-size:11px">减值准备</font></td>
	            <td align=center width="7% "><font style="font-size:11px">净额</font></td>
	            <td align=center width="7% "><font style="font-size:11px">残值</font></td>
	        </tr>
	    </table>
	    
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
				TOTAL_COST += lineDTO.getCost();
    			TOTAL_DEPRECIATION += lineDTO.getDepreciation();
    			TOTAL_NET_ASSET_VALUE += Float.parseFloat(lineDTO.getNetAssetValue());
    			TOTAL_IMPAIR_RESERVE += lineDTO.getImpairReserve();
    			TOTAL_DEPRN_COST += lineDTO.getDeprnCost();
    			TOTAL_SCRAP_VALUE += Float.parseFloat(lineDTO.getScrapValue());
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
            	<td width="2% " align="center"><font style="font-size:11px"><%=i+1%></font></td>
            	<td width="7% " align="left"  ><font style="font-size:11px"><%=lineDTO.getAssetNumber()%></font></td>
				<td width="8% " align="left"  ><font style="font-size:11px"><%=barcode%></font></td>
				<td width="12%" align="left"  ><font style="font-size:11px"><%=lineDTO.getAssetsDescription()%></font></td>
				<td width="11%" align="left"  ><font style="font-size:11px"><%=lineDTO.getModelNumber()%></font></td>
				<td width="4% " align="left"  ><font style="font-size:11px"><%=lineDTO.getUnitOfMeasure()%></font></td>
				<td width="5% " align="right" ><font style="font-size:11px"><%=lineDTO.getCurrentUnits()%></font></td>
				<td width="6% " align="center"><font style="font-size: 9px"><%=lineDTO.getDatePlacedInService()%></font></td>
				<td width="5% " align="right" ><font style="font-size:11px"><%=lineDTO.getLifeInYears()%></font></td>
				<td width="10%" align="left"  ><font style="font-size: 9px"><%=lineDTO.getContentCode()%></font></td>
				<td width="7% " align="right" ><font style="font-size:11px"><%=lineDTO.getCost()%></font></td>
				<td width="7% " align="right" ><font style="font-size:11px"><%=lineDTO.getDepreciation()%></font></td>
				<td width="3% " align="right" ><font style="font-size:11px"><%=lineDTO.getImpairReserve()%></font></td>
				<td width="7% " align="right" ><font style="font-size:11px"><%=lineDTO.getDeprnCost()%></font></td>
				<td width="7% " align="right" ><font style="font-size:11px"><%=lineDTO.getScrapValue()%></font></td>
            </tr>
<%
			}
%>
	        <tr bgcolor="yellow" >
	            <td colspan=10 width="69%" >&nbsp;合&nbsp;&nbsp;计&nbsp;</td>
				<td width="7%" align="right"><%=TOTAL_COST%></td>
				<td width="7%" align="right"><%=TOTAL_DEPRECIATION%></td>
				<td width="3%" align="right"><%=TOTAL_IMPAIR_RESERVE%></td>
				<td width="7%" align="right"><%=TOTAL_DEPRN_COST%></td>
				<td width="7%" align="right"><%=TOTAL_SCRAP_VALUE%></td>
	        </tr>
        </table>
        
        <table id="signTable" width="100%" border="0" bordercolor="#666666">
			<tr height=40 >
				<td width="5%" ></td>
				<td width="55%" align="left">拨出单位财务主管签字：</td>
				<td width="40%" align="left">拨入单位财务主管签字：</td>
			</tr>
			<tr height=40 >
				<td width="5%" ></td>
				<td width="55%" align="left">拨出单位专业主管签字：</td>
				<td width="40%" align="left">拨入单位专业主管签字：</td>
			</tr>
			<tr><td colspan=3 width="100%" ></td>&nbsp;</tr>
			<tr height=40 >
				<td width="5%" ></td>
				<td width="55%" align="left">拨出单位负责人签章：</td>
				<td width="40%" align="left">拨入单位负责人签章：</td>
			</tr>
        	<tr><td colspan=3 width="100%" ></td>&nbsp;</tr>
			<tr><td colspan=3 width="100%" ></td>&nbsp;</tr>
			<tr height=80 >
				<td width="5%" ></td>
				<td width="55%" align="left">拨出单位盖章：</td>
				<td width="40%" align="left">拨入单位盖章：</td>
			</tr>
        </table>
<%
		}
	}
%>


        