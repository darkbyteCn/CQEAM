<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.nm.ams.inv.storeman.bean.LookUpInvConstant" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/nm/instrument/headerInclude.html"%>

<%--
  Created by MyEclipse.
  User: yushibo
  Date: 2008-12-30
  Time: 15:55:20
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<title>仪器仪表检修－检修历史</title>  
</head>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	Row row = null;
	
    AmsInstrumentEamYbChkMaintainDTO dto = (AmsInstrumentEamYbChkMaintainDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_CHK_HISTORY);
    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    
    Date date = new Date();
    String nowYear = Integer.toString(date.getYear() + 1900);
    String nowMonth = Integer.toString(date.getMonth() + 1);
    String nowDay = Integer.toString(date.getDate());
    String month = "-01";
    String day = "-01";
%>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
<body onload="do_initPage();" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkHistoryServlet" name="mainFrm" method="post">
    <script type="text/javascript">
    	printTitleBar("仪器仪表检修－检修历史")
	</script>
	<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="15%"><select style="width:143px;" name="organizationId"><%=organizationId%></select></td>
            <td width="10%" align="right">目录编号：</td>
          	<td width="15%">
          	<input type="text" size="24" name="itemCategory2" value="<%=dto.getItemCategory2()%>"><a href="#" title="点击选择目录编号" class="linka" onclick="do_SelectItemCategory2();">[…]</a>  
          	</td>   
            <td width="10%" align="right">条码编号：</td>
	        <td width="15%">
	        <input type="text" size="24" name="barcode" value="<%=dto.getBarcode()%>">
	        <!-- <a href="#" title="点击选择起始物品编号" class="linka" onclick="do_SelectBarcode();">[…]</a> -->
	        </td>
        </tr>
        <tr>
        	<td width="10%" align="right">检修日期(≥)：</td>
        	<td width="15%"><input type="text" readonly="true" class="readonlyInput" name="minTime" size="25" value="<%=nowYear + month  + day%>" style="width:70%" onclick="gfPop.fPopCalendar(minTime)"><img src = "/images/calendar.gif" alt = "点击选择日期" onclick = "gfPop.fPopCalendar(minTime)"></td>
        	<td width="10%" align="right">检修日期(≤)：</td>
        	<td width="15%"><input type="text" readonly="true" class="readonlyInput" name="maxTime" size="25" value="<%=nowYear + "-" + nowMonth + "-" + nowDay %>" style="width:70%" onclick="gfPop.fPopCalendar(maxTime)"><img src = "/images/calendar.gif" alt = "点击选择日期" onclick = "gfPop.fPopCalendar(maxTime)"></td>
        	<td width="15%" align="right">
	          	<img src="/images/button/query.gif" alt="查询检修历史" onClick="do_Search(); return false;">
	          	<img src="/images/button/toExcel.gif" id="queryImg"
							style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
          	</td>
        </tr>
    </table>
    <input type="hidden" name="act" value="">
    
    <div id="headDiv">
    <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="8%" align="center">条码编号</td>
            <td width="13%" align="center">品名</td>
            <td width="10%" align="center">规格型号</td>
            <td width="10%" align="center">检修结果</td>
            <td width="10%" align="center">备注</td>
            <td width="5%" align="center">检修人</td>
            <td width="6%" align="center">检修日期</td>
        </tr>
    </table>
</div>

    <div style="overflow-y:scroll;left:0px;width:100%;height:300px">
     
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
    <%
        if (hasData) {
        	for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
    %>
            <tr class="dataTR">
                <td height="22" width="8%"><%=row.getValue("BARCODE")%></td>
                <td height="22" width="13%">
                <input type="text" value="<%=row.getValue("ITEM_NAME")%>" class="finput">
                </td>
                <td height="22" width="10%">
                <input type="text" value="<%=row.getValue("ITEM_SPEC")%>" class="finput">
                </td>
                <td height="22" width="10%"><%=row.getValue("CHECK_STATUS_VALUE")%></td>
                <td height="22" width="10%"><%=row.getValue("REMARK")%></td>
                <td height="22" width="5%"><%=row.getValue("CHECK_USER_NAME")%></td>
                <td height="22" width="6%"><%=row.getValue("CHECK_DATE")%></td>
            </tr>
            <%
              	}     
              }
            %>
        </table>
    </div>
</form>
<!--  
<div style="left:0; right:20"><%--=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))--%>
</div>
-->
<%
	if(hasData){
%>
	<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}	
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
	do_SetPageWidth();
}

function do_Search() {
    with (mainFrm) {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        submit();
    }
}

function do_exportToExcel() {
   mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
   mainFrm.action = "<%=URLDefineList.INSTRUMENT_YB_CHK_HISTORY_SERVLET%>";
   mainFrm.submit();
   //alert(getRadioValue("workorderObjectNo"));
}

//查找目录编号
function do_SelectItemCategory2() {
	var lookUpItemCategory2 = "<%=LookUpConstant.LOOK_UP_ITEM_CATEGORY2%>";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var itemCategory2s = getLookUpValues(lookUpItemCategory2, dialogWidth, dialogHeight);
	if(itemCategory2s){
		/*
		var itemCategory2 = null;
		for(var i=0; i<itemCategory2s.length; i++){
			itemCategory2 = itemCategory2s[i];
			dto2Frm(itemCategory2, "mainFrm");
		}
		*/
		document.forms[0].itemCategory2.value = itemCategory2s[0].catalogCode;
	}
}

//选择标签编号
function do_SelectBarcode(){
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_BARCODE%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].barcode.value = vendorNames[0].barcodeFlag;
       	//document.forms[0].userName.value = vendorNames[0].userName;
   	}
}
</script>