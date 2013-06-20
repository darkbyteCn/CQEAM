<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.spare2.bean.SpareLookUpConstant" %>
<%--
  Created by HERRY.
  Date: 2008-6-19
  Time: 17:16:13
--%>
<html>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
  <head><title>设备实物借用出库</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
  </head>
  <body onkeydown="autoExeFunction('do_Search()');">
  <form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.spare2.servlet.BjswjyckServlet">
<script type="text/javascript">
    printTitleBar("设备实物借用出库");
</script>
  <input type="hidden" name="transType" value="<%=DictConstant.BJDB%>">
    <input type="hidden" name="flexValueId" value="">
	<input type="hidden" name="transId" value="">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="fromObjectNo" value="<%=parser.getParameter("fromObjectNo")%>">
<table border="0" width="850" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">
	<tr>
		<td width="10%" height="22" align="right">调拨单号：</td>
		<td width="20%" height="22"><input type="text" name="transNo" style="width:100%" value="<%=parser.getParameter("transNo")%>"></td>
		
		<td width="10%" height="22" align="right">仓库名称：</td>
		<td width="20%" height="22"><input type="text" name="fromObjectName" style="width:80%" value="<%=parser.getParameter("fromObjectName")%>" readonly class="readonlyInput" >
            <a href="#" onClick="do_SelectObject();" title="点击选择仓库" class="linka">[…]</a>
        </td>
	</tr>
	<tr>
		<td height="22" align="right">创建日期：</td>
		<td height="22"><input type="text" name="fromDate" value="<%=parser.getParameter("fromDate")%>" style="width:80%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate, toDate)">
            <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
        </td>
		<td height="22" align="right">到：</td>
		<td height="22"><input type="text" name="toDate" value="<%=parser.getParameter("toDate")%>" style="width:80%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
            <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
        </td>
		<td height="22"  align="center"><img src="/images/eam_images/search.jpg" onclick="do_Search();" alt="查询"></td>
	</tr>
</table>

<script type="text/javascript">
    var columnArr = new Array("调拨单号","仓库名称","创建人","创建日期","单据状态");
    var widthArr = new Array("20%","30%","10%","10%","10%");
    printTableHead(columnArr,widthArr);

</script>
<div style="overflow-y:scroll;left:1px;width:100%;height:360px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    if (rows != null && rows.getSize() > 0) {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>')" title="点击打开单据">
            <td width="20%" align="center"><%=row.getValue("TRANS_NO")%></td>
            <td width="30%"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            <td width="10%" align="left" ><%=row.getValue("CREATED_USER")%></td>
            <td width="10%" align="center" ><%=String.valueOf(row.getValue("CREATION_DATE"))%></td>
            <td width="10%" align="center"><%=row.getValue("ORDER_STATUS_NAME")%></td>
        </tr>
<%
	    }
    }
%>
    </table>
</div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script language="javascript">

function do_Search(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    document.mainFrm.act.value="<%=WebActionConstant.QUERY_ACTION%>";
	document.mainFrm.submit();
}

function do_Create(){
    var url = "/servlet/com.sino.ams.spare2.servlet.AmsItemTransHServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url,"xgrkOrder",popscript);
}

function do_ShowDetail(primaryKey){
//	document.mainFrm.flexValueId.value = primaryKey;
	<%--document.mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
	var url = "/servlet/com.sino.ams.spare2.servlet.BjswjyckServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId="+primaryKey;
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url,"swjyckOrder",popscript);
}

function do_SelectObject() {
    document.mainFrm.fromObjectName.value = "";
    document.mainFrm.fromObjectNo.value = "";
    var projects = getSpareLookUpValues("<%=SpareLookUpConstant.OBJECT_NO%>", 48, 30, "objectCategory=<%=DictConstant.INV_NORMAL%>");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        document.mainFrm.fromObjectName.value = projects[0].workorderObjectName;
        document.mainFrm.fromObjectNo.value = projects[0].workorderObjectNo;
//        document.mainForm.toObjectLocation.value = projects[0].workorderObjectLocation;
    }
}
  </script>