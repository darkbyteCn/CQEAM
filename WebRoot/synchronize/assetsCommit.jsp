<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2008-3-18
  Time: 13:21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
    <title>资产调拨结果同步</title>
</head>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	EamSyschronizeDTO dto = (EamSyschronizeDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	Row row = null;
	String pageTitle = "资产调拨结果同步";
	String transferType = dto.getTransferType();
	if(transferType.equals("BTW_COMP")){
		pageTitle = "公司间调拨同步";
	}
%>
<body onkeydown="autoExeFunction('do_search()')" leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.synchronize.servlet.AssetsCommitServlet">
<jsp:include page="/message/MessageProcess"/>

<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>

        <input type="hidden" name="act">
        <input type="hidden" name="orgIds" value="">
        <input type="hidden" name="companyName" value="">
<table width="100%" topmargin="0" border="0" style="width:100%">
	<tr>
		<td align="right" width="10%">调拨方式：</td>
		<td align="left" width="18%"><select name="transferType" class="select_style1" style="width:100%"><%=dto.getTransferTypeOption()%></select></td>
		<td align="right" width="10%">资产标签：</td>
		<td align="left" width="16%"><input type="text" name="newBarcode" class="input_style1" value="<%=dto.getNewBarcode()%>" style="width:100%"></td>
		<td align="right" width="10%">地点代码：</td>
		<td align="left" width="16%"><input type="text" name="workorderObjectCode" class="input_style1" value="<%=dto.getWorkorderObjectCode()%>" style="width:100%"></td>
		<td align="right" width="20%">
            <img src="/images/eam_images/search.jpg" style="cursor:'hand'"  onclick="do_search();" alt="查询">
            <img src="/images/eam_images/export.jpg" style="cursor:'hand'"  onclick="do_Export();" alt="导出">
        </td>                                                                                           
	</tr>
	<tr>
		<td align="right" width="10%">调拨单号：</td>
		<td align="left" width="18%"><input type="text" class="input_style1" name="transNo" value="<%=dto.getTransNo()%>" style="width:100%" size="20"></td>
		<td align="right" width="10%">资产描述：</td>
		<td align="left" width="16%"><input type="text" class="input_style1" name="nameTo" value="<%=dto.getNameTo()%>" style="width:100%"></td>
		<td align="right" width="10%">地点位置：</td>
		<td align="left" width="16%"><input type="text" class="input_style1" name="newAssetsLocation" value="<%=dto.getNewAssetsLocation()%>" style="width:100%"></td>
        <td align="right" width="20%">
            <img src="/images/eam_images/synchronize.jpg" style="cursor:'hand'" onclick="do_syschronize();" alt="ERP同步">
        </td>
	</tr>
</table>


<div id ="headDiv" style="position:absolute;width:841px;overflow:hidden;top:72px;padding:0px; margin:0px;">
    <table width="200%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td width="2%" align="center" style="padding:0"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td width="5%" align="center">调拨方式</td>
            <td width="10%" align="center">调拨单号</td>
<%
	if(transferType.equals("BTW_COMP")){
%>
            <td width="6%" align="center">资产标签(旧)</td>
            <td width="6%" align="center">资产标签(新)</td>
<%
	} else {
%>
            <td width="6%" align="center">资产标签</td>
            <td width="6%" align="center">资产编号</td>
<%
	}	
%>
            <td width="10%" align="center">资产描述</td>
            <td width="10%" align="center">资产型号</td>
            <td width="16%" align="center">原资产地点</td>

            <td width="16%" align="center">新资产地点</td>
            <td width="11%" align="center">责任部门</td>
            <td width="4%" align="center">原责任人</td>
            <td width="4%" align="center">新责任人</td>
        </tr>
    </table>
 </div>

 <div id="dataDiv" style="overflow:scroll;height:368px;width:856px;position:absolute;top:95px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
           	<td width="2%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
		    <td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANSFER_TYPE")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
<%
	if(transferType.equals("BTW_COMP")){
%>

            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("OLD_BARDOE")%>"></td>
            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("NEW_BARCODE")%>"></td>
<%
	} else {
%>
            <td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("NEW_BARCODE")%>"></td>
			<td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
<%
	}	
%>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_ITEM_NAME")%>"></td>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_ITEM_SPEC")%>"></td>
            <td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_ASSETS_LOCATION")%>"></td>

			<td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("NEW_ASSETS_LOCATION")%>"></td>
            <td width="11%"><input type="text" class="finput" readonly value="<%=row.getValue("OLD_DEPT_NAME")%>"></td>
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
<div style="position:absolute;top:470px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>

<script type="text/javascript">

function do_search() {
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}


function do_Export(){
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

/**
 * 功能：同步数据
 */
function do_syschronize(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询再进行同步");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请先选择数据再进行同步");
		return;
	}
	mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.AssetsCommitServlet";
	mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
	mainFrm.submit();
}
</script>