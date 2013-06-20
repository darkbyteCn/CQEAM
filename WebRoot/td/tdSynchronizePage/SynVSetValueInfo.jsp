<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="srv.ams.valueinfo.dto.MFndFlexValuesDTO" %>
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
    <title>TD值集值信息服务</title>
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
	MFndFlexValuesDTO dto = (MFndFlexValuesDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="/servlet/srv.tdams.servlet.VSetValueInfoServlet">
    <script type="text/javascript">
        printTitleBar("同步TD值集值信息");
    </script>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
        <input name="act" type="hidden">
        <tr>
			<td align="right" width="8%">值集名称：</td>
			<td align="left" width="12%">
                <select size="1" name="flexValueName" class="noEmptyInput" style="width:100%">
                    <option value="">--请选择--</option>
                    <option value="SNMC_COA_CC" <%=dto.getFlexValueName().equals("SNMC_COA_CC") ? "selected" : ""%>>SNMC_COA_CC</option>
                    <option value="SNMC_COA_AC" <%=dto.getFlexValueName().equals("SNMC_COA_AC") ? "selected" : ""%>>SNMC_COA_AC</option>
                    <option value="SNMC_COA_SB" <%=dto.getFlexValueName().equals("SNMC_COA_SB") ? "selected" : ""%>>SNMC_COA_SB</option>
                    <option value="SNMC_COA_PJ" <%=dto.getFlexValueName().equals("SNMC_COA_PJ") ? "selected" : ""%>>SNMC_COA_PJ</option>
                    <option value="SNMC_COA_IC" <%=dto.getFlexValueName().equals("SNMC_COA_IC") ? "selected" : ""%>>SNMC_COA_IC</option>
                    <option value="SNMC_COA_CO" <%=dto.getFlexValueName().equals("SNMC_COA_CO") ? "selected" : ""%>>SNMC_COA_CO</option>
                    <option value="SNMC_COA_SP" <%=dto.getFlexValueName().equals("SNMC_COA_SP") ? "selected" : ""%>>SNMC_COA_SP</option>
                    <option value="CMCC_FA_CAT_1" <%=dto.getFlexValueName().equals("CMCC_FA_CAT_1") ? "selected" : ""%>>CMCC_FA_CAT_1</option>
                    <option value="CMCC_FA_CAT_2" <%=dto.getFlexValueName().equals("CMCC_FA_CAT_2") ? "selected" : ""%>>CMCC_FA_CAT_2</option>
                    <option value="CMCC_FA_CAT_3" <%=dto.getFlexValueName().equals("CMCC_FA_CAT_3") ? "selected" : ""%>>CMCC_FA_CAT_3</option>
                    <option value="CMCC_FA_LOC_1" <%=dto.getFlexValueName().equals("CMCC_FA_LOC_1") ? "selected" : ""%>>CMCC_FA_LOC_1</option>
                    <option value="CMCC_FA_LOC_2" <%=dto.getFlexValueName().equals("CMCC_FA_LOC_2") ? "selected" : ""%>>CMCC_FA_LOC_2</option>
                    <option value="CMCC_FA_LOC_3" <%=dto.getFlexValueName().equals("CMCC_FA_LOC_3") ? "selected" : ""%>>CMCC_FA_LOC_3</option>
                </select>
			</td>
			<td align="right" width="6%">最后更新日期：</td>
            <td align="left" style="width:20%">
                <input type="text" name="lastUpdateDate" value="<%=dto.getLastUpdateDate()%>" id="lastUpdateDate" class="noEmptyInput" style="width:60%" title="点击选择日期" readonly
                       class="readonlyInput" ><img
                    src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(lastUpdateDate, '');">
            </td> 
            <td width="10%" align="left"><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="同步"></td>
        </tr>
    </table>
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
		var flexValueName = document.getElementById("flexValueName").value;
		if(flexValueName ==""){
			alert("请选择值集名称！");
			return false;
			}
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		mainFrm.action = "/servlet/srv.tdams.servlet.VSetValueInfoServlet";
		mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
		mainFrm.submit();
	}
	
    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>

