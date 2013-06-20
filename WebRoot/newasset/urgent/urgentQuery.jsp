<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@ page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@ page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
	<title>单据打印查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<%
	String userOp = (String)request.getAttribute(WebAttrConstant.USER_OPTION)  ;
	UrgentDTO dto = (UrgentDTO)request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	UrgentHeaderDTO headerDTO=dto.getHeaderDTO();
%>

<body onkeydown="autoExeFunction('do_SearchOrder()');" onload="initPage();helpInit('4.6.2');" style="overflow:auto;">
<input type="hidden" name="helpId" value="">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="com.sino.ams.newasset.urgenttrans.servlet.UrgentServlet" name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("紧急调拨查询")
    </script>
 <input type="hidden" name="act" value="">
<table class="queryTable" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
         

            <td width="10%" align="right">单据编号：</td>
            <td width="20%"><input type="text" name="transNo" class="input_style1" style="width:100%" value="<%=headerDTO.getTransNo()%>"></td>
            <td width="10%" align="right">创建日期：</td>
            <td width="30%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" class="input_style1" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(startDate, endDate);">
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>" class="input_style1" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(startDate, endDate);">
            </td>
            <td width="25%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
			<img src="/images/eam_images/export.jpg" title="点击导出" onclick="do_ExportOrder();">
			</td>
     </tr>
     <tr>
     		<td width="10%" align="right">创建人：</td>
     		<td width="12%">
						<select name="createdBy" class="select_style1"
							style="width: 100%"><%=userOp%></select>
			</td>
			<td align="right" width="8%">工单状态：</td>
			<td><select name="status" >
				<option value="">-请选择-</option>
				<option value="DISTRIBUTED">下发</option>
				<option value="ARCHIEVED">归档</option>
				<option value="TRANS_OUT">调出</option>
				<option value="IN_PROCESS">处理中</option>
				<option value="CANCELED">撤消</option>
			</select>
			</td>
     </tr>
    </table>
</form>

    <div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:75px;left:0px;width:100%" class="crystalScroll">
   	  <table  border="1" width="100%" class="eamHeaderTable" cellpadding="0" cellspacing="0">
        <tr class="eamHeaderTable" height="20px">
            <td align=center width="18%">单据编号</td>
            <td align=center width="10%">单据状态</td>
            <td align=center width="16%">申请部门</td>
            <td align=center width="12%">申请人</td>
            <td align=center width="10%">申请日期</td>
	        </tr>
      </table>
    </div>

	<div style="overflow:scroll;height:72%;width:100%;position:absolute;top:95px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab" >
            <%
            RowSet sets = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                if (sets != null && !sets.isEmpty()) {
                    for (int i = 0; i < sets.getSize(); i++) {
                       Row row=sets.getRow(i);
            %>
            <tr class="dataTR" title="点击打开该单据" onclick="showDetail('<%=row.getStrValue("TRANS_ID")%>')">
              <td width="18%"><input type="text" class="finput2" readonly value="<%=row.getStrValue("TRANS_NO")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getStrValue("TRANS_STATUS_DESC")%>"></td>
              <td width="16%"><input type="text" class="finput" readonly value="<%=row.getStrValue("FROM_COMPANY_NAME")%>"></td>
              <td width="12%"><input type="text" class="finput" readonly value="<%=row.getStrValue("CREATED")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getStrValue("CREATION_DATE")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
<%
    if (sets != null && !sets.isEmpty()) {
%>
		<div id="navigatorDiv" style="position:absolute;top:87%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
}

function do_SearchOrder() {
	if(mainFrm.transferType){
		mainFrm.transferType.disabled = false;
	}
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function showDetail(transId){
    var url = "com.sino.ams.newasset.urgenttrans.servlet.UrgentServlet?act=<%=AssetSharingConstant.QUERY_DETAIL%>&transId="+transId;
    //var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    var style = "width="+screen.width+",height="+screen.height+",top=0,left=0,toolbar =no, menubar=no, scrollbars=no, resizable=yes, location=no, status=yes"
    var newWin = window.open(url, 'printWin', style);
	newWin.focus();
}

function do_ExportOrder(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

</script>