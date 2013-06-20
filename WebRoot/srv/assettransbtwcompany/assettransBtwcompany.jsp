<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-9-15
  Time: 16:23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.soa.common.SrvWebActionConstant" %>
<%@ page import="com.sino.soa.mis.srv.assettransbtwcompanysrv.dto.SBFIFAAssetsTransBtwCompanyDTO" %>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
    <title>公司间资产调拨同步</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	SBFIFAAssetsTransBtwCompanyDTO dto = (SBFIFAAssetsTransBtwCompanyDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	Row row = null;
%>
<body onkeydown="autoExeFunction('do_search()')" leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.soa.mis.srv.assettransbtwcompanysrv.servlet.SBFIFAAssetsTransBtwCompanyServlet">
<jsp:include page="/message/MessageProcess"/>

<script type="text/javascript">
    printTitleBar("公司间资产调拨同步");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_search");
    var ArrAction1 = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
    var ArrAction2 = new Array(true, "同步", "cancel.gif", "同步", "do_syschronize");
    var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
    printToolBar();

</script>

        <input type="hidden" name="act">
        <input type="hidden" name="orgIds" value="">
        <input type="hidden" name="companyName" value="">
<table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF"  style="width:100%">
	<tr>
		<td align="right" width="10%">资产标签：</td>
		<td align="left" width="15%"><input type="text" name="newBarcode" value="<%=dto.getNewBarcode()%>" style="width:100%"></td>
        <td align="right" width="10%">调拨单号：</td>
		<td align="left" width="15%"><input type="text" name="transNo" value="<%=dto.getTransNo()%>" style="width:100%" size="20"></td>
		<td align="right" width="10%">地点代码：</td>
		<td align="left" width="15%"><input type="text" name="workorderObjectCode" value="<%=dto.getWorkorderObjectCode()%>" style="width:100%"></td>
		<td align="right" width="10%">资产描述：</td>
		<td align="left" width="15%"><input type="text" name="nameTo" value="<%=dto.getNameTo()%>" style="width:100%"></td>
	</tr>
</table>


<div id ="headDiv" style="position:absolute;width:841px;overflow:hidden;top:74px;padding:0px; margin:0px;">
    <table width="200%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">

            <td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox" onclick="checkAll('titleCheck','systemids')"></td>

            <td width="4%" align="center">调拨方式</td>
            <td width="8%" align="center">调拨单号</td>
            <td width="6%" align="center">资产标签(旧)</td>
            <td width="6%" align="center">资产标签(新)</td>
            <td width="10%" align="center">资产描述</td>
            <td width="10%" align="center">资产型号</td>
            <td width="15%" align="center">原资产地点</td>
            <td width="15%" align="center">新资产地点</td>
            <td width="8%" align="center">原责任部门</td>
            <td width="8%" align="center">新责任部门</td>
            <td width="4%" align="center">原责任人</td>
            <td width="4%" align="center">新责任人</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:scroll;height:368px;width:856px;position:absolute;top:93px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >

           	<td width="2%" align="center"><input type="checkbox" name="systemids" value="<%=row.getValue("SYSTEMID")%>"></td>
		    <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANSFER_TYPE")%>"></td>
            <td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("FROM_TAG_NUMBER")%>"></td>
            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("TO_TAG_NUMBER")%>"></td>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_ITEM_NAME")%>"></td>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_ITEM_SPEC")%>"></td>
            <td width="15%"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_ASSETS_LOCATION")%>"></td>
			<td width="15%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_ASSETS_LOCATION")%>"></td>
            <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_DEPT_NAME")%>"></td>
            <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_DEPT_NAME")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_USER_NAME")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_USER_NAME")%>"></td>
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
		document.mainFrm.act.value = "<%=SrvWebActionConstant.QUERY_ACTION%>";
		document.mainFrm.submit();
	}

    function do_Export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    
	/**
	 * 功能：同步数据
	 */
	function do_syschronize(){
	    var count=getCheckedBoxCount("systemids");
	    if (count==0) {
	        alert("请选择数据后再进行同步！");
	        return;
	    }
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		document.mainFrm.action = "/servlet/com.sino.soa.mis.srv.assettransbtwcompanysrv.servlet.SBFIFAAssetsTransBtwCompanyServlet";
		document.mainFrm.act.value = "<%=SrvWebActionConstant.INFORSYN%>";
		document.mainFrm.submit();
	}
</script>