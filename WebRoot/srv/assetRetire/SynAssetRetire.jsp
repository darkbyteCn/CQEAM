<%--
  User: zhoujs
  Date: 2009-5-21 15:46:56
  Function:资产报废同步（SOA）
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.soa.mis.srv.assetretire.dto.AssetRetirementDTO" %>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
    <title>资产报废同步</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
	AssetRetirementDTO dto = (AssetRetirementDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rs = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    String orgOption=(String)request.getAttribute("OU_OPTION");
	Row row = null;
	String pageTitle="资产报废同步";
%>
<body onkeydown="autoExeFunction('do_search()');" leftmargin="0" topmargin="0" onload="do_SetPageWidth();">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.soa.mis.srv.assetretire.servlet.ImportAssetRetirmentServlet">
<jsp:include page="/message/MessageProcess"/>

<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_search");
    var ArrAction1 = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
    var ArrAction2 = new Array(true, "同步", "cancel.gif", "同步", "do_syschronize");
    var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
    printToolBar();

</script>
<input type="hidden" name="act">
<input type = "hidden" name = "isFirst" value = "0">
<table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF"  style="width:100%">
	<tr>
        <td align="right" width="10%">公司名称：</td>
        <td align="left" width="18%"><select style="width:60%" name="organizationId"><%=orgOption%></select> </td>
        <td align="right" width="10%">资产标签：</td>
        <td align="left" width="12%"><input type="text" name="barcode" value="<%=dto.getBarcode()%>" style="width:100%"></td>
        <td align="right" width="10%">资产名称：</td>
        <td align="left" width="12%"><input type="text" name="itemname" value="<%=dto.getItemname()%>" style="width:100%"></td>
	</tr>
</table>

<div id ="headDiv" style="position:absolute;width:100%;overflow:hidden;top:75px;padding:0px; margin:0px;">
    <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22" onClick="executeClick(this);" style="cursor:hand" title="点击全选或取消全选">
            <td width="2%" align="center" style="padding:0"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','systemId');"></td>
            <td width="15%" align="center">公司名称</td>
            <td width="15%" align="center">账簿</td>
            <td width="15%" align="center">标签号</td>
            <td width="15%" align="center">资产名称</td>
            <td width="15%" align="center">成本</td>
            <td width="15%" align="center">报废成本</td>
            <td width="15%" align="center">报废日期</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:scroll;height:368px;width:100%;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (rs != null && rs.getSize() > 0) {
        for (int i = 0; i < rs.getSize(); i++) {
            row = rs.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="this.style.backgroundColor='#EFEFEF'" onMouseOut="this.style.backgroundColor='#FFFFFF'" >
           	<td width="2%" align="center"><input type="checkbox" name="systemId" value="<%=row.getStrValue("SYSTEMID")%>"></td>
		    <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("COMPANY")%>"></td>
		    <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("BOOK_TYPE_CODE")%>"></td>
            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("COST")%>"></td>
            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("RETIREMENT_COST")%>"></td>
            <td width="15%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_RRETIRED")%>"></td>

</tr>
 <%
        }
    }
%>
  </table>
    </div>
</form>
<div id="pageNaviDiv" style="position:absolute;top:470px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>

<script type="text/javascript">

	function do_search() {
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		document.mainFrm.submit();
	}

    function do_Export() {
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    
	/**
	 * 功能：同步数据
	 */
	function do_syschronize(){
	    var isFirst = document.mainFrm.isFirst.value;
	    if (isFirst == "1") {
	        alert("正在同步数据，不能执行此操作！");
	        return false;
	    }
	    var count=getCheckedBoxCount("systemId");
	    if(count==0){
	        alert("请选择数据后再进行同步！")
	        return;
	    }
	    document.mainFrm.isFirst.value = "1";
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
		document.mainFrm.submit();
	}
</script>