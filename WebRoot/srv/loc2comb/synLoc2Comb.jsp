<%--
User: wangzhipeng
Date: 2011-09-15-11 
Function:地点信息同步(SOA)_第二段
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.soa.mis.srv.assetLoc2Comb.dto.SrvAssetLoc2CombDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%@ page import="com.sino.soa.common.SrvURLDefineList" %>
<%
	response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>地点第二段同步</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    SrvAssetLoc2CombDTO dto = (SrvAssetLoc2CombDTO) request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
    Row row = null;
%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/hintMessage.jsp" flush="true">
	<jsp:param name="showMsg" value="正在同步中，请等待"/>
</jsp:include>
<form name="mainFrm" method="post" action="<%=SrvURLDefineList.SRV_ASSET_LOC2_COMB_SERVLET%>">
    <script type="text/javascript">
        printTitleBar("地点第二段同步");
        var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_search");
        var ArrAction1 = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
        var ArrAction2 = new Array(true, "同步", "cancel.gif", "同步", "do_syschronize");
        var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
        printToolBar();
    </script>
    <input type="hidden" name="act">
    <input type="hidden" name="orgIds" value="">
    <table width="100%" border="0" bgcolor="#EFEFEF" style="width:100%">
        <tr>
            <td align="right" width="8%">公司：</td>
            <td align="left" width="20%"><select name="organizationId" style="width:100%"><%=dto.getOrganizationOpt()%></select> </td>
            <td align="right" width="13%">地点代码(中间段)：</td>
            <td align="left" width="20%"><input type="text" name="workorderObjectCode"
                                                value="<%=dto.getWorkorderObjectCode()%>" style="width:100%"></td>
            <td align="right" width="13%">地点描述(中间段)：</td>
            <td align="left" width="20%"><input type="text" name="newAssetsLocation"
                                                value="<%=dto.getNewAssetsLocation()%>" style="width:80%"></td>
            <td align="right" width="16%">
            </td>
        </tr>
    </table> 
    <div id="headDiv" style="position:absolute;width:100%;overflow:hidden;top:75px;padding:0px; margin:0px;">
        <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox" onclick="checkAll('titleCheck','objects')"></td>
                <td width="8%" align="center">公司</td>
                <td width="10%" align="center">地点代码(中间段)</td>
                <td width="20%" align="center">地点描述(中间段)</td>
                <td width="20%" align="center">原地点描述</td>
                <td width="5%" align="center">地点类型</td>
                <td width="5%" align="center">区域类型</td>
                <td width="5%" align="center">同步类型</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:72%;width:100%;position:absolute;top:67px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align="center" style="padding:0"><input type="checkbox" name="objects" value="<%=row.getValue("LOC2_CODE")+","+row.getValue("LOC2_DESC")%>"></td>
                <td width="8%"><input class="finput" readonly value="<%=row.getValue("COMPANY_NAME")%>"></td>
                <td width="10%"><input class="finput" readonly value="<%=row.getValue("LOC2_CODE")%>"></td>
                <td width="20%"><input class="finput" readonly value="<%=row.getValue("LOC2_DESC")%>"></td>
                <td width="20%"><input class="finput" readonly value="<%=row.getValue("OLD_LOC_DESC")%>"></td>
                <td width="5%"><input class="finput2" readonly value="<%=row.getValue("WORKORDER_CATEGORY")%>"></td>
                <td width="5%"><input class="finput2" readonly value="<%=row.getValue("AREA_TYPE_NAEM")%>"></td>
                <td width="5%"><input class="finput2" readonly value="<%=row.getValue("LOC_TYPE")%>"></td>
            </tr>
            <%
                    }
                }
            %>

        </table>
    </div>
</form>
<%
    if (rows != null && !rows.isEmpty()) {
%>
<div id="pageNaviDiv" style="position:absolute;top:90%;left:0"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%
    }
%>
</body>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }

    function do_Export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    /**
     * 功能：同步数据
     */
    function do_syschronize() {
        var count=getCheckBoxCount("objects");
        if (count==0) {
            alert("请先选择数据在进行同步！");
            return;
        }
        document.mainFrm.action = "/servlet/com.sino.soa.mis.srv.assetLoc2Comb.servlet.SrvAssetLoc2CombServlet";
        document.mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
        document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
        document.mainFrm.submit();
       
    }
</script>