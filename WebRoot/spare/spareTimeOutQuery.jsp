<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransHDTO" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  User: wangzp
  Date: 2011-12-01
  Function;调拨批准后调出入超时统计.
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>备件调出入超时统计</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body onkeydown="autoExeFunction('do_search()')" >
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsItemTransHDTO situsdto = (AmsItemTransHDTO)request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("备件调出入超时统计")
</script>
<!--<table width="100%" border="1" bgcolor="#EFEFEF">-->
 <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td align="right" width=10%>调出公司：</td>
            <td align="left" width=15%><select class="select_style1" style="width:80%" name="organizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%></select></td>
            <td align="right" width="10%">单据号：</td>
            <td align="left" width="15%"><input type="text" name="transNo" class="input_style1"  style="width:80%" value="<%=situsdto.getTransNo()%>"></td>
            <td align="right" width="10%">单据状态：</td>
            <td align="left" width="15%"><select name="transStatus" id="transStatus" class="select_style1" style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%></select></td>
            <td align="right" width=10%><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_Export()"></td>
        </tr>
        <tr>
            <!--<td align="right" width=10%>单据类型：</td>-->
            <%--<td align="left" width="15%"><select name="transType" id="transType" style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_TYPE)%></select></td>--%>
            <td align="right" width=10%>调入公司：</td>
            <td align="left" width=15%><select class="select_style1" style="width:80%" name="toOrganizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION2)%></select></td>
            <td align="right" width="10%">超时天数：</td>
            <td align="left" width="15%"><input type="text" name="attribute1" class="input_style1"  style="width:80%" value="<%=situsdto.getAttribute1()%>"></td>
            <td align="right" width="10%">创建人：</td>
            <td align="left" width="15%"><input type="text" name="createdUser" class="input_style1"  style="width:80%" value="<%=situsdto.getCreatedUser()%>"></td>
            <td align="right" width=10%><img src="/images/eam_images/search.jpg" alt="查询" onClick="do_search(); return false;"></td>
        </tr>
        <tr>
            <td width="" align="right">创建日期：</td>
            <td><input type="text" name="fromDate" value="<%=situsdto.getFromDate()%>"
                       style="width:80%" title="点击选择开始日期" readonly class="input_style1" 
                       onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td width="" align="right">到：</td>
            <td><input type="text" name="toDate" value="<%=situsdto.getToDate()%>"
                       style="width:80%" title="点击选择截止日期" readonly class="input_style1" 
                       onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
        </tr>
    </table>
      <script type="text/javascript">
            var columnArr = new Array("单据号","单据日期","创建人","单据状态","超时天数(天)");
            var widthArr = new Array("10%","10%","10%","10%","5%");
            printTableHead(columnArr,widthArr);
        </script>
<input type="hidden" name="act">
<div style="overflow-y:scroll;left:0px;width:100%;height:325px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && rows.getSize() > 0) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>')">
            <td width="10%" align="center"  ><%=row.getValue("TRANS_NO")%></td>
            <%--<td width="10%" align="center" ><%=row.getValue("BARCODE")%></td>--%>
            <%--<td width="10%" align="center"><%=row.getValue("ITEM_NAME")%></td>--%>
            <%--<td width="10%" align="center"><%=row.getValue("ITEM_SPEC")%></td>--%>
            <td width="10%" align="center"><%=row.getValue("CREATION_DATE")%></td>
            <td width="10%" align="center" ><%=row.getValue("CREATED_USER")%></td>
            <td width="10%" align="center" ><%=row.getValue("TRANS_STATUS_NAME")%></td>
            <td width="5%" align="center" ><%=row.getValue("TIME_OUT")%></td>
            <%--<td width="10%" align="center" ><%=row.getValue("CREATION_DATE")%></td>--%>
        </tr>
<%
	    }     }
%>
    </table>
</div>
</form>
<div id="navigatorDiv" style="position:absolute;top:442px;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet";
    mainFrm.submit();
}

function init(){
    var transStatus = document.getElementById("transStatus");
    var transType = document.getElementById("transType");
    dropSpecialOption(transStatus,'CANCELED;COMPLETED;CREATE;DISTRIBUTED;IN_PROCESS;REJECTED;SAVE_TEMP;SCANING')
    dropSpecialOption(transType,'BJBF;BJBFS;BJCK;BJFH;BJFK;BJFP;BJRK;BJSL;BJSX;FXSQ;BJPD')
}

 function do_ShowDetail(transId) {
        var url = "/servlet/com.sino.ams.spare.servlet.SpareMoveTimeOutServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);
    }
</script>