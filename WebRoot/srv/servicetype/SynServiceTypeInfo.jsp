<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="srv.ams.servicetype.dto.SrvServiceTypeInfoDTO" %>
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
    <title>服务类型信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>


</head>

<body  onkeydown="autoExeFunction('do_Search()');" onload="initPage();">
<%
	SrvServiceTypeInfoDTO dto = (SrvServiceTypeInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="/servlet/srv.ams.servicetype.servlet.SrvServiceTypeInfoServlet">
    <script type="text/javascript">
        printTitleBar("同步服务类型信息");
    </script>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
    	<input type="hidden" name="orgName"  style="width:100%">
        <tr>
            <td align="right" width="8%">公司名称：</td>
            <td align="left" width="12%"><select name="orgCode" id="orgCode" class="noEmptyInput" style="width:80%"><%=dto.getOuOption()%></td>
            <td align="right" width="10%">项目类型：</td>
            <td align="left" width="12%"><input type="text" name="projectType" value="<%=dto.getProjectType()%>"  style="width:80%"></td>
           <!--  <td width="5%" align="center" valign="top"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td> -->
            <td width="15%" align="left" ><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="同步"></td>
        </tr>
    </table>
    <input name="act" type="hidden">
    <input name="company" type="hidden">

</form>

</body>

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

		var projectType = document.getElementById("projectType").value;
		var dc=document.getElementById("orgCode");
		if(dc.value ==""){
			alert("请选择OU名称！");
			return false;
			}
		document.mainFrm.orgName.value=dc.options[dc.selectedIndex].innerText;
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		mainFrm.action = "/servlet/srv.ams.servicetype.servlet.SrvServiceTypeInfoServlet";
		mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
		mainFrm.submit();
	}

    function initPage() {
        do_SetPageWidth();
    }
</script>
</html>

