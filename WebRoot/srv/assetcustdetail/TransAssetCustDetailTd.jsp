<%//@ page import="srv.ams.assetcustdetail.dto.SrvAssetCustDetailDTO" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.assetcustdetail.dto.ODIAssetCustDetailDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
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
	ODIAssetCustDetailDTO dto = (ODIAssetCustDetailDTO) request.getAttribute(QueryConstant.QUERY_DTO);
	String assetsType = dto.getAssetsType();
	String pageTitle = "同步转资资产清单";
	
	if((AssetsWebAttributes.TD_ASSETS_TYPE).equals(assetsType)){
		pageTitle = "同步TD转资资产清单";
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('query();')">
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.soa.td.srv.assetcustdetail.servlet.TDTransAssetCustDetailServlet" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="assetsType" value="<%=assetsType%>">
    <input type="hidden" name="projectNumber" value="<%=dto.getProjectNumber()%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="10%" align="right">资产账簿：</td>
            <td width="30%">
            	<select name="bookTypeCode" id="bookTypeCode" class="noEmptyInput" style="width:80%" ><%=dto.getOrgOption()%></select>
            </td>
             <td width="10%" align="right">项目名称：</td>
             <td width = "30%">
             	<input name ="projectName" class="noEmptyInput" value="<%=dto.getProjectName()%>"  type = text style = "width:80%"><input type = hidden name = projectId><input type ="hidden" name ="segment1"><a href = # title = "点击选择项目信息" class = "linka" onclick = "do_SelectProj();">[…]</a>
             </td>
        </tr>
        <tr>
            <td width="10%" align="right">转资开始日期：</td>
            <td width="30%">
            	<input  name="capitalizedDateFrom" value="<%=dto.getCapitalizedDateFrom()%>" title="点击选择日期" readonly style="width:80%" class="readonlyInput" onclick="gfPop.fStartPop(capitalizedDateFrom, capitalizedDateTo)"/>
            </td>
            <td width="10%" align="right">转资结束日期：</td>
            <td width="30%">
            	<input  name="capitalizedDateTo"   value="<%=dto.getCapitalizedDateTo()%>" title="点击选择日期" readonly style="width:80%" class="readonlyInput" onclick="gfPop.fEndPop(capitalizedDateFrom, capitalizedDateTo)"/>
            </td>
           	<td width="15%">
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

	function do_SearchOrder() {
		if(document.mainFrm.transferType){
			document.mainFrm.transferType.disabled = false;
		}
	    document.mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
	    document.mainFrm.submit();
	}
	function do_SubmitSyn() {
		var bookTypeCode = document.getElementById("bookTypeCode").value;
		if(bookTypeCode ==""){
			alert("资产账簿不能为空！");
			return ;
		}
        document.mainFrm.projectNumber.value=document.mainFrm.segment1.value;
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	    document.mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
	    document.mainFrm.submit();
	}
    function initPage() {
        do_SetPageWidth();
    }
    function do_SelectProj() {
	    var lookUpProj = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
	    var dialogWidth = 50;
	    var dialogHeight = 30;
	    var projs = getLookUpValues(lookUpProj, dialogWidth, dialogHeight);
	    if (projs) {
	        var proj = null;
	        for (var i = 0; i < projs.length; i++) {
	            proj = projs[i];
	            dto2Frm(proj, "mainFrm");
	        }
	    }
    }
</script>
</html>