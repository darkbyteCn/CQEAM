<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-4-28
  Time: 16:18:52
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
	<title>个人工单查询</title>
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
    RequestParser parser = new RequestParser();
    parser.transData(request);
	AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO)parser.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>

<body onkeydown="autoExeFunction('do_search()');" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="<%=AssetsURLList.ASSETS_INVI_SERVLET%>" name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("个人工单查询")
    </script>
 <input type="hidden" name="act" value="">  
<table bgcolor="#E9EAE9" border="1" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="7%" align="right">工单类型：</td>
            <td width="10%"><select name="transType" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.TRANS_TYPE_OPTION)%></select></td>
            <td width="7%" align="right">工单编号：</td>
            <td width="15%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="7%" align="right">工单状态</td>
            <td width="10%"><select name="transStatus" style="width:100%"><%=dto.getStatusOption()%></select></td>
            <td width="7%" align="right">创建日期：</td>
            <td width="20%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:48%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:48%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
            </td>
            <td width="10%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
			</td>
        </tr>
    </table>
    <%--<input type="hidden" name="act" value="<%=action%>">--%>
</form>
     <div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:100%">
   	  <table  border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr class=headerTable height="20px">
            <td align=center width="10%">工单类型</td>
            <td align=center width="10%">工单编号</td>
            <td align=center width="30%">工单主题</td>
            <td align=center width="10%">工单状态</td>
            <td align=center width="10%">创建日期</td>
            <td align=center width="15%">当前办理人</td>
        </tr>
      </table>
    </div>

	<div id="dataDiv" style="overflow:scroll;height:72%;width:100%;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab" >
            <%
                if (rows != null && !rows.isEmpty()) {
                for (int i = 0; i < rows.getSize(); i++) {
                    Row row=rows.getRow(i);
                    String transType = row.getStrValue("TRANS_TYPE");
                    String transId = row.getStrValue("TRANS_ID");
            %>
            
            <tr class="dataTR" onclick="showDetail('<%=transId%>','<%=transType%>')">
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_TYPE_NAME")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
              <td width="30%"><input type="text" class="finput" readonly value="<%=row.getValue("REASON")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_STATUS")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
              <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("CURR_USER")%>"></td>
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

function showDetail(transId, transType){
    if (transType == "ASS-CHK") {
        var url = "<%=AssetsURLList.CHECK_HEADER_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&headerId="+transId;
        var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
        window.open(url, 'orderWin', style);
    } else {
        var url = "<%=AssetsURLList.ASSETS_TRANS_SERVLET%>?act=<%=AssetsActionConstant.DETAIL_ACTION%>&transType=" + transType+"&transId="+transId;
        var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
        window.open(url, 'transferWin', style);
    }
}
</script>