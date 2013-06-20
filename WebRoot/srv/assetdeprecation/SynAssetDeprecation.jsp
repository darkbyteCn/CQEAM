<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="srv.ams.assetperiod.dto.SrvAssetPeriodStatusDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    
%>

<html>
<head>
    <title>固定资产折旧信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>


</head>

<body  onkeydown="autoExeFunction('do_Search()');" onload="initPage();">
<%
	SrvAssetPeriodStatusDTO dtoOpt = (SrvAssetPeriodStatusDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="/servlet/srv.ams.assetdeprecation.servlet.TransAssetDeprecationServlet">
    <script type="text/javascript">
        printTitleBar("固定资产折旧信息同步");
    </script>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
        <tr>
            <td align="right" width="20%">期间名称：</td>
            <td align="left" width="60%"><input type="text" name="periodName" value="<%=dtoOpt.getPeriodName() %>"  class="noEmptyInput" style="width:30%">例如：Oct-03</td>
            <td width="20%" align="left" ><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="同步"></td>
        </tr>
    </table>

    <input name="act" type="hidden">

</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm"
        scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }
    
   /**
	* 功能：同步数据
	*/
	function do_syschronize() {

		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
		document.mainFrm.submit();
	}
	
    function initPage() {
        do_SetPageWidth();
    }
</script>

