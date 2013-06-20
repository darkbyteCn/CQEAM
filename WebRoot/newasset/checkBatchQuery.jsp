<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_Search();')">
<%=WebConstant.WAIT_TIP_MSG%>

<%
    SfUserDTO sfUser=(SfUserDTO)SessionUtil.getUserAccount(request);
	AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	String srcPage = request.getParameter("srcPage");
	String title = "资产盘点管理>>创建盘点任务";
	if(srcPage == null){
		srcPage = "";
	}
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
		title = "资产盘点管理>>撤销盘点任务";
	} else if(srcPage.equals(AssetsActionConstant.DISTRIBUTE_ACTION)){
		title = "资产盘点管理>>下发盘点任务";
	} else if(srcPage.equals(AssetsActionConstant.QUERY_ACTION)){
		title = "资产盘点管理>>盘点任务查询";
	}
%>
<form action="<%=AssetsURLList.CHECK_BATC_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    printTitleBar("<%=title%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="orderType" value="<%=dto.getOrderType()%>">
    <input type="hidden" name="srcPage" value="<%=srcPage%>">
    <table width=100% class="queryTable">
        <tr>
            <td width="10%" align="right">盘点部门：</td>
            <td width="30%"><select class="select_style1" name="checkDept" style="width:100%"><%=dto.getCheckDeptOption()%></select></td>
            <td width="10%" align="right">创建日期：</td>
            <td width="30%">
            <input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:35%" title="点击选择开始日期" readonly class="input_style2"  onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate);">
            <input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:35%" title="点击选择截至日期" readonly class="input_style2"  onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop(startDate, endDate);">
            </td>
<%
	if(srcPage.equals(AssetsActionConstant.QUERY_ACTION)){
%>
            <td width="10%" align="right">任务状态：</td>
            <td width="15%"><select name="batchStatus"><%=dto.getBatchStatusOption()%></select></td>
<%
	}	
%>
            <td width="16%" align="right">
			<img src="/images/eam_images/search.jpg" alt="暂存查询" onclick="do_Search();">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>
            <img src="/images/eam_images/revoke.jpg" alt="点击撤销" onclick="do_Cancel();">
<%
	} else if(srcPage.equals(AssetsActionConstant.DISTRIBUTE_ACTION)){	
%>
        <img src="/images/button/redploy.gif" alt="下发" onClick="do_Distribute(); return false;">
<%
	} else if(srcPage.equals("")){
%>
            <img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_Create();">
<%
	}
%>
			</td>
        </tr>
    </table>
</form>
  
	<div id="headDiv" style="overflow:hidden;position:absolute;top:47px;left:1px;width:830px">
		<table class="headerTable" border="1" width="110%%">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION) || srcPage.equals(AssetsActionConstant.DISTRIBUTE_ACTION)){
%>  		
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
<%
	} else {
%>  		
	        <tr height=20px >
<%
	}
%>		            
	            <td align=center width="18%">任务编号</td>
	            <td align=center width="13%">公司名称</td>
	            <td align=center width="16%">部门名称</td>
	            <td align=center width="10%">开始日期</td>
	            <td align=center width="10%">执行周期(天)</td>
	            <td align=center width="10%">创建日期</td>
	            <td align=center width="19%">任务描述</td>
	        </tr>
		</table>   
	</div>
	<div id="dataDiv" style="overflow:scroll;height:70%;width:847px;position:absolute;top:69px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="110%%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if (hasData) {
		for (int i = 0; i < rows.getSize(); i++) {
			Row row=rows.getRow(i);
			if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION) || srcPage.equals(AssetsActionConstant.DISTRIBUTE_ACTION)){
%>
	        <tr class="dataTR">

<%			
			} else {
%>
	        <tr class="dataTR" onclick="showDetail('<%=row.getValue("BATCH_ID")%>')">
<%
			}
			if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION) || srcPage.equals(AssetsActionConstant.DISTRIBUTE_ACTION)){
%>
	          <td width="3%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
<%
			}	
%>
			  <td width="18%" align="left"><input type="text" class="finput2" readonly value="<%=row.getValue("BATCH_NO")%>"></td>
			  <td width="13%" align="left"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
	          <td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
	          <td width="10%" align="center"><input type="text" class="finput2" readonly value="<%=row.getValue("BATCH_START_TIME")%>"></td>
	          <td width="10%" align="right"><input type="text" class="finput3" readonly value="<%=row.getValue("BATCH_IMPLEMENT_DAYS")%>"></td>
	          <td width="10%" align="center"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
	          <td width="19%"><input type="text" class="finput" readonly value="<%=row.getValue("TASK_DESC")%>"></td>
	        </tr>
<%
		}
	}
%>	
	    </table>
	</div>

<%		
	if(hasData){
%>
<div style="position:absolute;top:438px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%	
	}
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_Search() {
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Create() {
    if ("<%=sfUser.isDptAssetsManager()%>" == "true") {
        var url = '/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName=' + 'checkapp';
        var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
        window.open(url, 'discardWin', style);
    } else {
        alert("必须是部门资产管理员才能进行操作！");
    }
}

function showDetail(batchId){
	var srcPage = mainFrm.srcPage.value;
	var act = "<%=AssetsActionConstant.EDIT_ACTION%>";
//    var url =  "/servlet/com.sino.sinoflow.servlet.AppProcessCase?appName="+"checkapp"+"&appId="+batchId;
    if(srcPage == "<%=AssetsActionConstant.QUERY_ACTION%>"){
		act = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	} else if(srcPage == "<%=AssetsActionConstant.CANCEL_ACTION%>"){
		act = "<%=AssetsActionConstant.CANCEL_ACTION%>";
	}
    var url = "<%=AssetsURLList.CHECK_BATC_SERVLET%>?act=" + act + "&batchId="+batchId;
    <%--var url = "<%=AssetsURLList.CHECK_BATC_SERVLET%>?act=" + act + "&batchId="+batchId;--%>
     if(act=='EDIT_ACTION'){
         url =  "/servlet/com.sino.sinoflow.servlet.AppProcessCase?appName="+"checkapp"+"&appId="+batchId;
     }
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'discardWin', style);
}

function do_Distribute(){
    mainFrm.act.value = "<%=AssetsActionConstant.DISTRIBUTE_ACTION%>";
    mainFrm.submit();
}

function do_Cancel(){
	if(confirm("确认要撤销盘点单据吗？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
	    mainFrm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
	    mainFrm.submit();
    }
}
</script>
</html>
