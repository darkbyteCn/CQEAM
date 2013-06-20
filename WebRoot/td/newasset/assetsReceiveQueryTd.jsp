<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String transferType = dto.getTransferType();
	String provinceCode = dto.getServletConfig().getProvinceCode();
	String pageTitle = "TD资产调拨管理>>待接收调拨单";
	if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){
		pageTitle = "TD部门间调拨>>待接收调拨单";
	} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
		pageTitle = "TD盟市间调拨>>待接收调拨单";
	} 
%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.td.newasset.servlet.TdReceiveServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
<jsp:include page="/message/MessageProcess"/>


    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="7%" align="right">调出公司：</td>
            <td width="11%"><input type="text" name="fromCompanyName" style="width:100%" value="<%=dto.getFromCompanyName()%>" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectCompany();"></td>
            <td width="7%" align="right">调出部门：</td>
            <td width="11%"><input type="text" name="fromDeptName" style="width:100%" value="<%=dto.getFromDeptName()%>" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectDept();" ></td>
            <td width="7%" align="right">调拨单号：</td>
            <td width="15%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="7%" align="right">调出日期：</td>
            <td width="8%"><input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"></td>
            <td width="8%"><input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"></td>
            <td width="15%" align="right">
				<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();" align="right">
			</td>
        </tr>
	</table>    
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="<%=AssetsDictConstant.ASS_RED%>">
    <input type="hidden" name="fromOrganizationId" value="<%=dto.getFromOrganizationId()%>">
    <input type="hidden" name="fromDept" value="<%=dto.getFromDept()%>">
    <input type="hidden" name="transferType" value="<%=dto.getTransferType()%>">
<script type="text/javascript">
    var columnArr = new Array("调拨单号", "单据类型", "调出公司","调出部门", "调出日期", "调出人姓名");
    var widthArr = new Array("20%","16%","16%", "16%", "12%", "16%");
    printTableHead(columnArr,widthArr);
</script>
    <div style="overflow-y:scroll;height:365px;width:100%;left:1px;margin-left:0">
    <table width=100% border=1 bordercolor="#666666">
</form>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}	
	if (hasData) {
		Object transId = "";
		for (int i = 0; i < rows.getSize(); i++) {
			Row row=rows.getRow(i);
			transId = row.getValue("TRANS_ID");
			transferType = row.getStrValue("TRANSFER_TYPE");
			int index = ArrUtil.getArrIndex(AssetsDictConstant.TRANS_OPT_VALUES, transferType);
			transferType = AssetsDictConstant.TRANS_OPT_LABELS_TD[index];
%>
        <tr class="dataTR" onclick="showDetail('<%=transId%>')">
		  <td width="20%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
          <td width="16%"><input type="text" class="finput2" readonly value="<%=transferType%>"></td>
          <td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_COMPANY_NAME")%>"></td>
          <td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_DEPT_NAME")%>"></td>
          <td width="12%"><input type="text" class="finput2" readonly value="<%=row.getValue("APPROVED_DATE")%>"></td>
          <td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("CREATED")%>"></td>
        </tr>
<%
		}
	}
%>
    </table>
</div>
<%
    if (hasData) {
%>
<div style="position:absolute;top:440px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script type="text/javascript">
function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function showDetail(transId){
    var url = "/servlet/com.sino.td.newasset.servlet.TdReceiveServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&transId="+transId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'orderWin', style);
}

function do_Receive(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能执行该操作");
		return;
	}
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$.value){
		alert("没有选择数据，不能执行该操作");
		return;
	}
    mainFrm.act.value = "<%=AssetsActionConstant.RECEIVE_ACTION%>";
    mainFrm.submit();
}

/**
 * 功能:选择调出部门
 */
function do_SelectCompany() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_COMPANY%>";
	var dialogWidth = 40;
	var dialogHeight = 28;
	var companys = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (companys) {
		var comObj = companys[0];
		with(mainFrm){
			var valueChanged = (fromCompanyName.value != comObj["companyName"]);
			fromCompanyName.value = comObj["companyName"];
			fromOrganizationId.value = comObj["organizationId"];
			if(valueChanged ){
				fromDeptName.value = "";
				fromDept.value = "";
			}
		}
	} else {
		mainFrm.fromCompanyName.value = "";
		mainFrm.fromOrganizationId.value = "";
	}
}


/**
 * 功能:选择调出公司
 */
function do_SelectDept() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_DEPT%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var depts = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (depts) {
		var deptObj = depts[0];
		with(mainFrm){
			fromCompanyName.value = deptObj["toCompanyName"];
			fromOrganizationId.value = deptObj["toOrganizationId"];
			fromDept.value = deptObj["toDept"];
			fromDeptName.value = deptObj["toDeptName"];
		}
	} else {
		with(mainFrm){
			fromOrganizationId.value = "";
			fromCompanyName.value = "";
			fromDept.value = "";
			fromDeptName.value = "";
		}
	}
}
</script>

