<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	TdAssetsTransHeaderDTO dtoPara = (TdAssetsTransHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<body onkeydown="autoExeFunction('do_Search()');">
<form action="/servlet/com.sino.td.newasset.servlet.TdOrderAssignServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("TD资产调拨管理>>调拨资产分配");
</script>
<jsp:include page="/message/MessageProcess"/>


    <table width=100% bgcolor="#E9EAE9">
        <tr>
            <td width="7%" align="right">调出公司：</td>
            <td width="11%"><input type="text" name="fromCompanyName" style="width:100%" value="<%=dtoPara.getFromCompanyName()%>" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectCompany();"></td>
            <td width="7%" align="right">调出部门：</td>
            <td width="11%"><input type="text" name="fromDeptName" style="width:100%" value="<%=dtoPara.getFromDeptName()%>" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectDept();" ></td>
            <td width="7%" align="right">调拨单号：</td>
            <td width="15%"><input type="text" name="transNo" style="width:100%" value="<%=dtoPara.getTransNo()%>"></td>
            <td width="7%" align="right">调出日期：</td>
            <td width="8%"><input type="text" name="startDate" value="<%=dtoPara.getStartDate()%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"></td>
            <td width="8%"><input type="text" name="endDate" value="<%=dtoPara.getEndDate()%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"></td>
            <td width="15%">
				<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_Search();" align="right">
			</td>
        </tr>
	</table>    
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="<%=AssetsDictConstant.ASS_RED%>">
    <input type="hidden" name="fromOrganizationId" value="<%=dtoPara.getFromOrganizationId()%>">
    <input type="hidden" name="fromDept" value="<%=dtoPara.getFromDept()%>">
<script type="text/javascript">
    var columnArr = new Array("调拨单号", "调出公司","调出部门", "调出日期", "调出人姓名");
    var widthArr = new Array("20%","20%","20%", "20%", "20%");
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
%>
        <tr class="dataTR" onclick="showDetail('<%=transId%>')" title="点击以分配该单据的资产">
		  <td width="20%" align="center"><%=row.getValue("TRANS_NO")%></td>
          <td width="20%" align="left"><%=row.getValue("FROM_COMPANY_NAME")%></td>
          <td width="20%" align="left"><%=row.getValue("FROM_DEPT_NAME")%></td>
          <td width="20%" align="center"><%=row.getValue("APPROVED_DATE")%></td>
          <td width="20%" align="left"><%=row.getValue("CREATED")%></td>
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
}

function showDetail(transId){
    var url = "/servlet/com.sino.td.newasset.servlet.TdReceiveServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&transId="+transId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'orderWin', style);
}

/**
 * 功能:选择调出部门
 */
function do_SelectCompany() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_COMPANY%>";
	var dialogWidth = 38;
	var dialogHeight = 30;
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

