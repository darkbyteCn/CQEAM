<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-11-18
  Time: 15:33:30
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
    <title>转资清单同步</title>
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
	AmsAssetsAddressVDTO dtoOpt= (AmsAssetsAddressVDTO) request.getAttribute(QueryConstant.QUERY_DTO);
	String pageTitle = "同步转资资产清单";
%>
<%=WebConstant.WAIT_TIP_MSG%>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();')">
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.synchronize.servlet.SynAssetsTurnServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <table border="0" class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="30%">
            	<select name="organizationId" id="organizationId"  class="select_style1" style="width:80%" ><%=dtoOpt.getOrgOption()%></select> <font  color="red">*</font>
            </td>
             <td width="10%" align="right">项目名称：</td>
             <td width = "30%">
             	<input name ="projectName" id="projectName" class="input_style1" value="<%=dtoOpt.getProjectName()%>"  type = text style = "width:80%"><font  color="red">*</font><input type = hidden name = projectId><input type =hidden name = segment1><a href = # title = "点击选择项目信息" class = "linka" onclick = "do_SelectProj();">[…]</a>
             </td>
            <td width="20%" align="center">
              <img src="/images/button/synchronize.gif" alt="点击同步" onclick="do_SubmitSyn();">
            </td>
        </tr>
    </table>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>

<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
	function do_SubmitSyn() {
		var organizationId = document.getElementById("organizationId").value;
		if(organizationId ==""){
			alert("公司不能为空！");
			return false;
		}
        var projectName = document.getElementById("projectName").value;
        if(projectName ==""){
			alert("项目不能为空！");
			return false;
		}
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
	    mainFrm.submit();
	}
    function initPage() {
        do_SetPageWidth();
    }
    function do_SelectProj() {
	    var lookUpProj = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
	    var dialogWidth = 50;
	    var dialogHeight = 30;
	    var projs = getLookUpValues(lookUpProj, dialogWidth, dialogHeight);
        if(projs){
            var proj = projs[0];
            mainFrm.segment1.value = proj["segment1"];
            mainFrm.projectName.value = proj["projectName"];
        } else {
            mainFrm.projectName.value = "";
            mainFrm.segment1.value = "";
        }
    }
</script>
</html>