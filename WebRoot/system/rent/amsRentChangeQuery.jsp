<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-7-10
  Time: 10:34:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
	String srcPage = parser.getParameter("srcPage");
	AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO)parser.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.system.rent.servlet.AMSRentChangeServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("实物调拨");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
	<input type="hidden" name="srcPage" value="<%=srcPage%>">
    <table class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="10%" align="right">单据编号：</td>
            <td width="15%"><input type="text" name="transNo" class="input_style1" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="10%" align="right">创建日期：</td>
            <td width="20%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:48%" title="点击选择日期" readonly class="input_style2" onclick="gfPop.fStartPop(startDate, endDate)">
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:48%" title="点击选择日期" readonly class="input_style2" onclick="gfPop.fEndPop(startDate, endDate)">
            </td>
            <td width="20%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
<%
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>
            <img src="/images/eam_images/revoke.jpg" alt="点击撤销" onclick="do_CancelOrder();">
<%
		} else {
%>
			<img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
<%
		}
%>
			</td>

        </tr>
    </table>
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>
			<td align=center width="3%"><input type="checkbox" name="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
<%
	}
%>
			<td align=center width="18%">调拨单号</td>
			<td align=center width="10%">单据类型</td>
			<td align=center width="16%">调出部门</td>
			<td align=center width="12%">调拨申请人</td>
			<td align=center width="10%">调拨申请日期</td>
			<td align=center width="10%">调拨单状态</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		String transId = "";
		if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				Row row=rows.getRow(i);
				transId = row.getStrValue("TRANS_ID");
%>
        <tr class="dataTR">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>
          <td width="3%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
<%
	}
%>
		  <td width="18%" onclick="showDetail('<%=transId%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
		  <td width="10%" onclick="showDetail('<%=transId%>')"><input type="text" class="finput2" readonly value="<%=row.getStrValue("TRANS_TYPE_VALUE")%>"></td>
          <td width="16%" onclick="showDetail('<%=transId%>')"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_DEPT_NAME")%>"></td>
          <td width="12%" onclick="showDetail('<%=transId%>')"><input type="text" class="finput" readonly value="<%=row.getValue("CREATED")%>"></td>
          <td width="10%" onclick="showDetail('<%=transId%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
          <td width="10%" onclick="showDetail('<%=transId%>')"><input type="text" class="finput" readonly value="<%=row.getValue("TRANS_STATUS_DESC")%>"></td>
        </tr>
<%
			}
		}
%>
    </table>
</div>
<%

    if (rows != null && !rows.isEmpty()) {
%>
<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_SearchOrder() {
	if(mainFrm.transferType){
		mainFrm.transferType.disabled = false;
	}
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_CreateOrder() {
 /*   var transType = mainFrm.transType.value;
	if(mainFrm.transferType){
		if(mainFrm.transferType.value == ""){
			alert("请先选择调拨单类型！");
			mainFrm.transferType.focus();
			return;
		}
		do_SubmitTransfer();
	} else {*/
	    //var url = "/servlet/com.sino.ams.system.rent.servlet.AMSRentChangeServlet?act=<%=AssetsActionConstant.NEW_ACTION%>";
	    var url = "/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName=wzdbApp";
	    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	    window.open(url, "transferWin", style);
   /* }*/
}


function do_SubmitTransfer(){
	var transType = mainFrm.transType.value;
	var url = "<%=AssetsURLList.ASSETS_TRANS_SERVLET%>?act=<%=AssetsActionConstant.NEW_ACTION%>&transType=" + transType;
	url += "&transferType=" + mainFrm.transferType.value;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, "transferWin", style);
}

function do_CloseDiv(){
	document.getElementById("transferDiv").style.visibility = "hidden";
}


function showDetail(transId){
    var transType = mainFrm.transType.value;
    <%--var url = "/servlet/com.sino.ams.system.rent.servlet.AMSRentChangeServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&transType=" + transType+"&transId="+transId;--%>
    var url =  "/servlet/com.sino.sinoflow.servlet.AppProcessCase?appName="+"wzdbApp"+"&appId="+transId;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'transferWin', style);
}

function do_CancelOrder(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能执行指定的操作。");
		return;
	}
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$.value){
		alert("没有选择数据，不能执行指定的操作。");
		return;
	}
	if(confirm("确定要撤销选择的单据吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
	    mainFrm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
	    mainFrm.submit();
    }
}
</script>
</html>
