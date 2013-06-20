<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.soa.td.srv.accountbalance.dto.SBFIGLTdTransAccountBalanceDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-10-13
  Time: 11:45:04
  To change this template use File | Settings | File Templates.
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
    <title>TD系统科目余额(ODI)</title>
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
	SBFIGLTdTransAccountBalanceDTO dto = (SBFIGLTdTransAccountBalanceDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String pageTitle = "TD系统科目余额(ODI)";
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="/servlet/com.sino.soa.td.srv.accountbalance.servlet.SBFIGLTdTransAccountBalanceServlet">
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>");
    </script>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
        <tr>
			<%--<td align="right" width="8%">公司名称：</td>--%>
            <%--<td align="left" width="20%"><select name="companyCode" class="noEmptyInput" id="companyCode" style="width:80%"><%=dto.getCompanyCode()%></select></td>--%>
			<td align="right" width="8%">账套名称：</td>
			<td align="left" width="20%" nowrap="nowrap"><input type="text" name="setOfBooks" id="setOfBooks" value="<%=dto.getSetOfBooks()%>" >例如：SOB_XXMC</td>
			<td align="right" width="8%">期间名称：</td>
			<td align="left" width="20%" nowrap="nowrap"><input type="text" name="periodName" class="noEmptyInput" id="periodName" value="<%=dto.getPeriodName()%>" >例如：Sep-06</td>
            <td width="16%"  valign="top" align="right"><img src="/images/eam_images/synchronize.jpg" style="cursor:'hand'" onclick="do_syschronize();" alt="同步"></td>
        </tr>
    </table>
    <input name="act" type="hidden">
    <input name="company" type="hidden">
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm"
        scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
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
		var setofbook = document.getElementById("setOfBooks").value;
		var periodName = document.getElementById("periodName").value;
//		var companyCode = document.getElementById("companyCode").value;
//		if(companyCode ==""){
//			alert("公司名称不能为空！");
//			return ;
//		}
//		if(setofbook == ""){
//			alert("账套名称不能为空！");
//			return ;
//		}
		if(periodName ==""){
			alert("期间名称不能为空");
			return ;
		}
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.action = "/servlet/com.sino.soa.td.srv.accountbalance.servlet.SBFIGLTdTransAccountBalanceServlet";
		document.mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
		document.mainFrm.submit();
	}

    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>

