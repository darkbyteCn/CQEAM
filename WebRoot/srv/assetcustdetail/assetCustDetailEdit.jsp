<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.transassetcustdetail.dto.SBFIFATransAssetCustDetailDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-10-13
  Time: 17:51:20
  To change this template use File | Settings | File Templates.
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String projectName = parser.getParameter("projectName");
%>
<html>
<head>
    <title>MIS系统转资资产清单(ODI)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>

<%
    SBFIFATransAssetCustDetailDTO dtoOpt = (SBFIFATransAssetCustDetailDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    String pageTitle = "MIS系统转资资产清单(ODI)";
%>
<%=WebConstant.WAIT_TIP_MSG%>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();helpInit('4.4.1');"
      onkeydown="autoExeFunction('query();')">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.soa.mis.srv.transassetcustdetail.servlet.SBFIFATransAssetCustDetailServlet" method="post"
      name="mainFrm">
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>");
    </script>
    <input type="hidden" name="act" value="">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="8%" align="right">资产账簿：</td>
            <td width="20%">
                <select name="bookTypeCode" id="bookTypeCode" style="width:80%"><%=dtoOpt.getOrgOption()%> </select>
            </td>
            <td width="8%" align="right">项目编号：</td>
            <td width="20%">
                <input name="projectNumber" value="<%=dtoOpt.getProjectNumber()%>" type=text style="width:80%">
            </td>
            <td width="8%" align="right">任务编号：</td>
            <td width="20%">
                <input name="taskNum" value="<%=dtoOpt.getTaskNum()%>" type=text style="width:80%">
            </td>
        </tr>
        <tr>
            <td width="8%" align="right">转资开始日期：</td>
            <td width="20%">
                <input name="capitalizedDateFrom" value="<%=dtoOpt.getCapitalizedDateFrom()%>" title="点击选择日期" readonly style="width:80%" class="readonlyInput" onclick="gfPop.fStartPop(capitalizedDateFrom, capitalizedDateTo)"/>
            </td>
            <td width="8%" align="right">转资结束日期：</td>
            <td width="20%">
                <input name="capitalizedDateTo" value="<%=dtoOpt.getCapitalizedDateTo()%>" title="点击选择日期" readonly style="width:80%" class="readonlyInput" onclick="gfPop.fEndPop(capitalizedDateFrom, capitalizedDateTo)"/>
            </td>
            <td width="28%" colspan="2" align="right">
                <img src="/images/eam_images/synchronize.jpg" alt="点击同步" onclick="do_SubmitSyn();">
            </td>
        </tr>
    </table>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>

<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

    function do_SubmitSyn() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
        mainFrm.submit();
    }
    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>