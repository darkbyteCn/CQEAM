<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnLineDTO"%>
<html>
<head> 
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

    <%
    	ZeroTurnLineDTO lineDTO = (ZeroTurnLineDTO) request.getAttribute(QueryConstant.QUERY_DTO);
        String costCenterCode = StrUtil.nullToString(request.getParameter("costCenterCode"));
        String costCenterName = StrUtil.nullToString(request.getParameter("costCenterName"));
    %>
<title>零购转资差异核对查询界面</title>
</head>

<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onLoad="initPage();helpInit('4.4.4');">
<input type="hidden" name="helpId" value=""> 
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.ZeroTurnEditQueryServlet">
<script type="text/javascript">
      printTitleBar("零购转资差异核对修改查询界面");
</script>
<input type="hidden" name="costCenterCode" value="<%=costCenterCode%>">
<table width=100% border="0" align="center">
			    <tr align="center">
			        <td align="right" width="8%">采购单号</td>	
        			<td width="15%" height="18">
              			<input name="procureCode" type="text" id="procureCode" value="<%=lineDTO.getProcureCode()%>"    style="width:80%">
        			</td> 
			        <td align="right" width="8%">成本中心</td>
			        <td width="15%" height="18">
                		<input type="text" name="costCenterName" class="input_style1" readonly style="width:100%;cursor:hand" value="<%=costCenterName %>" title="点击选择或更改“成本中心”" onClick="chooseCostCenter()" size="20"> 
        			</td> 
        			 <td align="right" width="8%">标签号</td>
			        <td width="15%" height="18">
                		<input type="text" name="barcode" class="input_style1"  style="width:100%;cursor:hand" value="<%=lineDTO.getBarcode()%>"  > 
        			</td> 
        			<td align="right" width="30%"> 
        				<img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_Search(); return false;">&nbsp&nbsp&nbsp&nbsp
        			</td>
			    </tr>
			</table>
<input type="hidden" name="act" value="">
 <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:50px;left:0px;width:100%;">
    <table class=headerTable border=1 style="width:150%">
    		<td align=center width="2%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="11%">标签号</td>
            <td align=center width="10%">资产目录</td>
            <td align=center width="8%">规格型号</td>
            <td align=center width="8%">数量</td>
            <td align=center width="8%">数量单位</td>
            <td align=center width="11%">地点名称</td>
            <td align=center width="8%">成本中心</td>
            <td align=center width="8%">采购单号</td>
            <td align=center width="8%">启用日期</td>
            <td align=center width="10%">差异处理结果</td>
        </tr>
    </table>
</div>

 <div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:1px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
     <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
     <%
       		RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    		boolean hasData = (rows != null && !rows.isEmpty());
        	if (hasData) {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                String barcode=row.getValue("BARCODE").toString().trim();
        %>
            <tr class="dataTR" style="cursor:pointer">
            	<td width="2%" align="center">
        		<input type="checkbox" name="check"  value="<%=row.getValue("BARCODE")%>"> 
        		</td>
				<td width="11%" align="center" style="cursor:pointer" >
				  <input type="text" name="barcode" id="barcode<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput" readonly value="<%=row.getValue("BARCODE")%>"></td>
				<td width="10%" align="center" style="cursor:pointer" >
				  <input type="text" name="contentCode" id="contentCode<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("CONTENT_CODE")%>"></td>
				<td width="8%" align="center" style="cursor:pointer" >
				  <input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')"  value="<%=row.getValue("ITEM_SPEC")%>"></td>
				<td width="8%" align="center" style="cursor:pointer" >
				  <input type="text" name="itemQty" id="itemQty<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("ITEM_QTY")%>"></td>
				<td width="8%" align="center" style="cursor:pointer" >
				  <input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
				<td width="11%" align="center" style="cursor:pointer" >
				  <input type="text" name="workorderObjectName" id="workorderObjectName<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
				<td width="8%" align="center" style="cursor:pointer" >
				  <input type="text" name="costCenterName" id="costCenterName<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("COST_CENTER_NAME")%>"></td>
				<td width="8%" align="center" style="cursor:pointer" > 
				  <input type="text" name="procureCode" id="procureCode<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("PROCURE_CODE")%>"></td>
				  <td width="8%" align="center" style="cursor:pointer" > 
				  <input type="text" name="startDate" id="startDate<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("START_DATE")%>"></td>
				  <td width="10%" align="center" style="cursor:pointer" > 
				  <input type="text" name="diffResult" id="diffResult<%=i%>" title="点击修改“<%=barcode%>”信息"  onClick="do_Update('<%=barcode%>')" class="finput"  value="<%=row.getValue("DIFF_RESULT")%>"></td>
				  <input type="hidden" name="costCenterCode" value="<%=row.getValue("COST_CENTER_CODE")%>">
				  <input type="hidden" name="isShare" value="<%=row.getValue("IS_SHARE")%>">
				  <input type="hidden" name="isBulid" value="<%=row.getValue("IS_BULID")%>">
				  <input type="hidden" name="lneId"  value="<%=row.getValue("LNE_ID")%>">
				  <input type="hidden" name="opeId"  value="<%=row.getValue("OPE_ID")%>">
				  <input type="hidden" name="cexId"  value="<%=row.getValue("CEX_ID")%>">
				  <input type="hidden" name="nleId" value="<%=row.getValue("NLE_ID")%>">
            </tr>
   <%
			}
		}
   %>
     </table>
    </div>
 <input type="hidden" name="oneBarcode" >
	
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">
	function initPage(){
		do_SetPageWidth();
	}
	
	function do_Search() {
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit()
    }
	
	function do_Save() {
        mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        mainFrm.submit()
    }
	
	function chooseCostCenter() {
	    var lookUpName = "<%=LookUpConstant.COST_CENTER%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	    if (projects) {
	        dto2Frm(projects[0], "mainFrm");
	    } else {
	        document.mainFrm.costCenterCode.value = "";
	        document.mainFrm.costCenterName.value = "";
	    }
	}
	
	function do_Update(barcode){
		document.mainFrm.oneBarcode.value = barcode;
		mainFrm.act.value = "update";
        mainFrm.submit()
    }
	
</script>