<%@ page import="com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant" %>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.AssetSharingHeaderDTO"%>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AssetSharingHeaderDTO headerDTO = (AssetSharingHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String isSpecialityDept = String.valueOf(request.getAttribute("isSpecialityDept"));
%>
<head>
	<title>资产共享申请</title>
	<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoAttachment.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<script type="text/javascript">
	printTitleBar("共享单“<%=headerDTO.getTransNo()%>”详细信息");
	printToolBar();

function initPage() {
    window.focus();
    doLoad();
    do_SetPageWidth();
    do_ControlProcedureBtn();
}

</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<jsp:include page="/message/MessageProcess"/>
<form style="margin: 0" action="<%=AssetSharingConstant.ASSET_SHARE_SERVLET%>" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<div id="searchDiv" style="position:absolute;top:52px;left:1px;width:100%">
    <table width=100% border="0">
        <tr>
            <td align=right width=8%>单据号：</td>
            <td width=13%>
                <input type="text" name="transNo" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getTransNo()%>">
            </td>
            <td align=right width=8%>单据类型：</td>
            <td width=13%>
                <input type="text" name="transTypeValue" class="input_style2" readonly style="width:100%" value="<%=AssetSharingConstant.ASSET_SHARE_CODE_DESC%>">
            </td>
             <td align=right>公司名称：</td>
            <td>
                <input name="company" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCompany()%>"></td>
             <td align=right width=8%>部门名称</td>
            <td width=28%>
                <input name="deptName" style="width:100%" class="input_style2" value="<%=headerDTO.getDeptName()%>"/>
            </td>
        </tr>
        <tr> <td align=right width=8%>创建人：</td>
            <td width=13%>
                <input type="text" name="currUserName" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getCurrUserName()%>" >
            </td>
            <td align=right>创建日期：</td>
            <td>
                <input name="creationDate" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>" ></td>

            <td align=right width=8%>手机号码：</td>
             <td>
                <input name="mobilPhone" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getMobilePhone()%>"></td>
            <td align=right width=8%>电子邮件：</td>
             <td>
                <input name="email" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getEmail()%>"></td>
        </tr>
        <tr>
            <td align=right>紧急程度：</td>
            <td >
                <select name="emergentLevel" disabled class="select_style1" style="width:100%" ><%=headerDTO.getEmergentLevelOption()%></select>
            </td>
            <td width="8%" height="60" align="right">备注：</td>
            <td width="92%" height="60" colspan="5"><textarea name="remark" readonly style="width:100%;height:100%" class="input_style1"><%=headerDTO.getRemark()%></textarea></td>
        </tr>
    </table>
</div>
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" id="transId" value="<%=headerDTO.getTransId()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="excel" value="">
<input type="hidden" name="groupId" value="<%=headerDTO.getGroupId()%>">
<input type="hidden" name="flowCode" value="<%=headerDTO.getFlowCode()%>">
<input type="hidden" name="isSpecialityDept" id="isSpecialityDept" value="<%=isSpecialityDept%>">

	<div id="headDiv" style="overflow:hidden;position:absolute;top:200px;left:1px;width:100%">
		<table class="headerTable" border="1" width="140%">
	        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="8%">标签号</td>
                <td align=center width="6%">资产名称</td>
	            <td align=center width="6%">资产型号</td>
	            <td align=center width="8%">资产地点描述</td>
                <td align=center width="6%">共享</td>
                <td align=center width="6%">责任人员工编号</td>
                <td align=center width="6%">责任人姓名</td>
                <td align=center width="6%">启用日期</td>
				 <td align=center width="6%">资产目录</td>
	            <td align=center width="8%">资产目录描述</td>
				<td style="display:none">隐藏域字段</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:198px;left:1px"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
     <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
          List<AssetSharingLineDTO> lines= headerDTO.getLines();
          if(lines != null && lines.size() > 0){
              for(int i=0;i<lines.size();i++){
                  AssetSharingLineDTO line=lines.get(i);
%>
         <tr class="dataTR" onClick="executeClick(this)"title="资产详细信息">
             <td width="8%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=line.getBarcode()%>"></td>
             <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName<%=i%>" class="finput2" readonly value="<%=line.getItemName()%>"></td>
             <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" ><input name="itemSpec" id="itemSpec<%=i%>" style="width:100%" class="finput2" onChange="do_SetDisType(this)" value="<%=line.getItemSpec() %>"/></td>
             <td width="8%" align="left" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" class="finput" readonly value="<%=line.getWorkorderObjectLocation()%>"></td>
             <td width="6%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><select  type="text" name="shareStatus" id="shareState<%=i%>"style="width:100%" class="select_style1"  ><%=line.getShareStatusOpt()%></select></td>
             <td width="6%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="employeeNumber" id="employeeNumber<%=i%>" class="finput3" readonly value="<%=line.getEmployeeNumber()%>"></td>
             <td width="6%" align="right"><input type="text" name="userName"  id="userName<%=i%>" class="finput2" value="<%=line.getUserName()%>"></td>
             <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="startDate" id="startDate<%=i%>" class="finput2" readonly value="<%=line.getStartDate()%>"></td>
             <td width="6%" align="center" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="contentCode" id="contentCode<%=i%>" class="finput" readonly value="<%=line.getContentCode()%>"></td>
             <td width="8%" align="right" title="资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="contentName" id="contentName<%=i%>" class="finput" readonly value="<%=line.getContentName()%>"></td>
             <td style="display:none">
                 <input type="hidden" name="systemId" id="systemId<%=i%>" value="<%=line.getSystemId()%>">
                 <input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=line.getLineId()%>">
                 <input type="hidden" name="adressId" id="adressId<%=i%>" value="<%=line.getAdressId()%>">
             </td>
           </tr>
<%
        }
    }
%>
        </table>
        </div>

</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>