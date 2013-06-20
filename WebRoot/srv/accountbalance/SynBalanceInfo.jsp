<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="srv.ams.accountbalance.dto.SrvAccountBalanceDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    String periodname = (String)request.getAttribute("PERIODNAME");
%>

<html>
<head>
    <title>科目余额服务</title>
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
	SrvAccountBalanceDTO dto = (SrvAccountBalanceDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String assetsType = dto.getAssetsType();
	String pageTitle = "同步科目余额信息";
	
	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
		pageTitle = "同步TD科目余额信息";
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="/servlet/srv.ams.accountbalance.servlet.SrvAccountBalanceServlet">
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>");
    </script>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
        <tr>
			<td align="right" width="8%">公司名称：</td>
            <td align="left" width="12%"><select name="companyCode" class="noEmptyInput" id="companyCode" style="width:80%"><%=dto.getCompanyCode()%></select></td>
			<td align="right" width="8%">账套名称：</td>
			<td align="left" width="15%" nowrap="nowrap"><input type="text" name="setOfBooks" class="noEmptyInput" id="setOfBooks" value="<%=dto.getSetOfBooks()%>" >例如：SOB_XXMC</td>
		</tr>
		<tr>
			<td align="right" width="8%">科目组合类别：</td>
			<td align="left" width="12%">
                <select size="1" name="accountType" id="accountType" style="width:80%">
                    <option value="">--请选择--</option>
                    <option value="A" <%=dto.getAccountType().equals("A") ? "selected" : ""%> >资产类账户</option>
                    <option value="L" <%=dto.getAccountType().equals("L") ? "selected" : ""%>>负债类账户</option>
                    <option value="E" <%=dto.getAccountType().equals("E") ? "selected" : ""%>>费用类账户</option>
                    <option value="O" <%=dto.getAccountType().equals("O") ? "selected" : ""%>>所有者权益类账户</option>
                    <option value="R" <%=dto.getAccountType().equals("R") ? "selected" : ""%>>收入类账户</option>
                </select>
			</td>
			<td align="right" width="8%">期间名称：</td>
			<td align="left" width="15%" nowrap="nowrap"><input type="text" name="periodName" class="noEmptyInput" id="periodName" value="<%=dto.getPeriodName()%>" >例如：Sep-06</td>
            <!-- <td width="5%" align="center" valign="top"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td> -->
            <td width="5%"  valign="top"><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="同步"></td>
        </tr>
    </table>
    <input name="act" type="hidden">
    <input name="company" type="hidden">
    <input name="assetsType" type="hidden" value="<%=assetsType%>">


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
		var companyCode = document.getElementById("companyCode").value;
		if(companyCode ==""){
			alert("公司名称不能为空！");
			return false;
		}
		if(setofbook == ""){
			alert("账套名称不能为空！");
			return false;
		}
		if(periodName ==""){
			alert("期间名称不能为空");
			return false;
		}
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		mainFrm.action = "/servlet/srv.ams.accountbalance.servlet.SrvAccountBalanceServlet";
		mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
		mainFrm.submit();
	}
	
    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>

