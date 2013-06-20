<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.yj.constant.YJWebAttribute" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ include file="/newasset/headerInclude.htm" %>
<html>
<head>
    <title>战备应急通信资源维护</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('do_search()');" style="background-color:#EEEEEE">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
<%//=WebConstant.EXPORT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String formAction = "/servlet/com.sino.ams.yj.resource.servlet.AmsYjCommunicateResourceServlet";
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String orgOpt = (String) request.getAttribute(YJWebAttribute.ORG_OPTION);
    boolean hasData = (rows != null && !rows.isEmpty());
    Row row = null;
    String equipmentName = parser.getParameter("equipmentName");
%>
<form method="post" name="mainFrm" action="<%=formAction%>">
    <script type="text/javascript">
        printTitleBar("战备应急通信资源维护");
    </script>
    <table width="100%" border="0" bgcolor="#EEEEEE">
        <tr>
            <td width="12%">公司名称：</td>
            <td width="25%"><select name="organizationId" class="select_style1" width="100%"><%=orgOpt%>
            </select></td>
            <td width="12%">装备名称：</td>
            <td width="25%"><input type="text" name="equipmentName" class="input_style1"  width="100%" value="<%=equipmentName%>"></td>
            <td align="right" nowrap="nowrap">
                <img src="/images/eam_images/search.jpg" style="cursor:hand" onclick="do_search();" title="查询">
                <img src="/images/eam_images/new.jpg" style="cursor:hand" onClick="do_new();" title="新增">
                <img src="/images/eam_images/delete.jpg" style="cursor:hand" onClick="do_delete();" title="删除">
                <img src="/images/eam_images/export.jpg" style="cursor:hand" onClick="do_export();" title="导出">
                <img src="/images/eam_images/export.jpg" style="cursor:hand" onClick="do_export2();" title="导出统计">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act">
    <input type="hidden" name="isExp" value="0">
    <div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:150%">
        <table border="1" style="color: #FFFFFF" bgcolor="#2983CB" width="300%">
            <tr height="20">
                <td width="2%" align="center">&nbsp;</td>
                <td width="3%" align="center">序号</td>
                <td width="8%" align="center">单位名称</td>
                <td width="12%" align="center">装备名称</td>
                <td width="4%" align="center">数量</td>
                <td width="12%" align="center">置放位置</td>
                <td width="16%" align="center">规格</td>
                <td width="5%" align="center">基本性能</td>
                <td width="8%" align="center">主管部门</td>
                <td width="5%" align="center">负责人</td>
                <td width="5%" align="center">手机</td>
                <td width="5%" align="center">保管人</td>
                <td width="5%" align="center">手机</td>
                <td width="10%" align="center">备注</td>
            </tr>
        </table>
        <%--</table>--%>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:67%;width:150%;position:absolute;top:67px;left:1px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="300%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (hasData) {
                    String resourceId = "";
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        resourceId = row.getStrValue("RESOURCE_ID");
            %>
            <tr height="22" style="cursor:hand" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="2%"><input type="checkbox" name="resourceIds" value="<%=row.getValue("RESOURCE_ID")%>"></td>
                <td width="3%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("RESOURCE_ID")%>"></td>
                <td width="8%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("DEPT_NAME")%>"></td>
                <td width="12%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                 value="<%=row.getValue("EQUIPMENT_NAME")%>"></td>

                <td width="4%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("RESOURCE_QTY")%>"></td>
                <td width="12%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput2" readonly
                                                                                 value="<%=row.getValue("LOCATION")%>"></td>
                <td width="16%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                 value="<%=row.getValue("MODEL")%>"></td>
                <td width="5%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("NORMAL_STATUS")%>"></td>
                <td width="8%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("CHARGE_DEPT")%>"></td>
                <td width="5%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("CHARGER")%>"></td>
                <td width="5%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("CHARGER_MOBILE")%>"></td>
                <td width="5%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("KEEPER")%>"></td>
                <td width="5%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                value="<%=row.getValue("KEEPER_MOBILE")%>"></td>
                <td width="10%" onclick="show_detail('<%=resourceId%>');"><input type="text" class="finput" readonly
                                                                                 value="<%=row.getValue("REMARK")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%
    if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;bottom:0px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>

<%--</fieldset>--%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no"
        frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script type="text/javascript">
    function initPage() {
        do_SetPageWidth();
    }

    function do_search() {
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }

    function show_detail(resourceId) {
        var url = "<%=formAction%>";
        url += "?act=<%=WebActionConstant.DETAIL_ACTION%>";
        url += "&resourceId=" + resourceId;
        var popscript = "width=640,height=480,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
        window.open(url, "commObj", popscript);
    }

    function do_new() {
        var url = "<%=formAction%>";
        url += "?act=<%=WebActionConstant.NEW_ACTION%>";
        var popscript = "width=640,height=480,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
        window.open(url, "commObj", popscript);
    }

    function do_delete() {
        document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
        document.mainFrm.submit();
    }
    function do_export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }
    function do_export2(){
        document.mainFrm.act.value="EXP";
        document.mainFrm.submit();
    }

</script>