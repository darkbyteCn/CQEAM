<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-7-9
  Time: 16:33:28
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-7-4
  Time: 15:38:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.others.dto.NoBarcodeDTO" %>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>

</head>
<body leftmargin="1"  topmargin="0" onkeydown="autoExeFunction('do_Search()');">

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form name="mainFrm"  method="post" action="/servlet/com.sino.ams.others.servlet.NoBarcodeQueryServlet">
<script type="text/javascript">
    printTitleBar("非条码设备库存查询");
</script>
<table border="0" width="850" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
	<tr>
		<td width="10%" height="22" align="right">地市：</td>
		<td width="22%" height="22"><select name="toOrganizationId" style="width:100%"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%></select></td>
		<td width="10%" height="22" align="right">仓库名称：</td>
		<td width="22%" height="22"><select name="toObjectNo" style="width:100%"><%=request.getAttribute(WebAttrConstant.INV_OPTION)%></select></td>
        <td height="22" colspan="2" align="center"><img src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
    </tr>
	<tr>
		<td height="22" align="right">设备名称：</td>
		<td height="22"><input type="text" name="itemName" value="<%=parser.getParameter("itemName")%>" style="width:100%" >
        </td>
		<td height="22" align="right">规格型号：</td>
		<td height="22"><input type="text" name="itemSpec" value="<%=parser.getParameter("itemSpec")%>" style="width:100%">
        </td>
	</tr>
</table>
	<input type="hidden" name="transType" value="FTMRK">
	<input type="hidden" name="flexValueId" value="">
	<input type="hidden" name="transId" value="">
	<input type="hidden" name="act" value="">
<script type="text/javascript">
    var columnArr = new Array("所属地市","所属批次","设备名称","规格型号","入库数量","当前数量","计量单位","所属仓库");
    var widthArr = new Array("10%","10%","10%","15%","5%","5%","5%","20%");
    printTableHead(columnArr,widthArr);

</script>
<div style="overflow-y:scroll;left:1px;width:100%;height:70%">
    <table width="100%" border="1" bordercolor="#666666">
<%
    if (rows != null && rows.getSize() > 0) {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" 
                >
            <td width="10%" align="center"><%=row.getValue("TO_ORGANIZATION_NAME")%></td>
            <td width="10%"><%=row.getValue("BATCH_NO")%></td>
            <td width="10%" align="left" ><%=row.getValue("ITEM_NAME")%></td>
            <td width="15%" align="center" ><%=String.valueOf(row.getValue("ITEM_SPEC"))%></td>
            <td width="5%" align="center" ><%=String.valueOf(row.getValue("QUANTITY"))%></td>
            <td width="5%" align="center"><%=row.getValue("NOW_QTY")%></td>
            <td width="5%" align="center"><%=row.getValue("ITEM_UNIT")%></td>
            <td width="20%" align="center"><%=row.getValue("TO_OBJECT_NAME")%></td>
        </tr>
<%
	    }
    }
%>
    </table>
</div>
</form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
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
    var url = "/servlet/com.sino.ams.others.servlet.NoBarcodeServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url,"xgrkOrder",popscript);
}

function do_ShowDetail(primaryKey){
//	document.mainFrm.flexValueId.value = primaryKey;
	<%--document.mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
	var url = "/servlet/com.sino.ams.others.servlet.NoBarcodeServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId="+primaryKey;
    var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
    window.open(url,"xgrkOrder",popscript);
}

</script>