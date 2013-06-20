<%--
  Created by HERRY.
  Date: 2008-8-15
  Time: 15:01:25
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<html>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String url = "/servlet/com.sino.ams.spare2.servlet.BjInvTransServlet";
%>
  <head><title>备件子库转移确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
  </head>
  <body>
  <jsp:include page="/message/MessageProcess"/>
  <form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.spare2.servlet.BjInvTransServlet">
<script type="text/javascript">
    printTitleBar("备件子库转移确认");
</script>
	<input type="hidden" name="transId" value="">
	<input type="hidden" name="transType" value="<%=DictConstant.ZKZY%>">
	<input type="hidden" name="act" value="">
<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">
	<tr>
		<td width="10%" height="22" align="right">单据编号：</td>
		<td width="17%" height="22"><input type="text" name="transNo" style="width:100%" value="<%=StrUtil.nullToString(request.getParameter("transNo"))%>"></td>
		<td width="10%" height="22" align="right">单据状态：</td>
		<td width="17%" height="22"><select name="transStatus" style="width:100%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%></select></td>
		<td width="10%" height="22" align="right"></td>
		<td width="17%" height="22"><%--<select name="fromObjectNo" style="width:100%"><%=request.getAttribute(WebAttrConstant.INV_OPTION)%></select>--%></td>
	</tr>
	<tr>
		<td height="22" align="right">创建日期：</td>
		<td height="22"><input type="text" name="fromDate" value="<%=StrUtil.nullToString(request.getParameter("fromDate"))%>" style="width:80%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate, toDate)">
            <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
        </td>
		<td height="22" align="right">到</td>
		<td height="22"><input type="text" name="toDate" value="<%=StrUtil.nullToString(request.getParameter("toDate"))%>" style="width:80%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
            <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
        </td>
		<td height="22" colspan="2" align="right"><img src="/images/eam_images/search.jpg" onclick="do_Search();" alt="查询"></td>
	</tr>
</table>

<script type="text/javascript">
    var columnArr = new Array("单据编号","源仓库","目的仓库","创建人","创建日期","单据状态");
    var widthArr = new Array("20%","20%","20%","10%","10%","10%");
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
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>')">
            <td width="20%" align="center"><%=row.getValue("TRANS_NO")%></td>
            <td width="20%"><%=row.getValue("FROM_OBJECT_NAME")%></td>
            <td width="20%"><%=row.getValue("TO_OBJECT_NAME")%></td>
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


function do_ShowDetail(primaryKey){
//	document.mainFrm.flexValueId.value = primaryKey;
	<%--document.mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
    var url = "<%=url%>";
    url += "?act=<%=WebActionConstant.DETAIL_ACTION%>&transId="+primaryKey+"&flag=CONFIRM";
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url,"bjOrder",popscript);
}
  </script>