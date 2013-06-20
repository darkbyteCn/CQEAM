<%--
  Created by IntelliJ IDEA.
  User: jialongchuan
  Date: 2008-11-26
  Time: 11:23:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <title>地点信息变更同步</title>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    EamSyschronizeDTO dto = (EamSyschronizeDTO) request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
    String action = parser.getParameter("act");
    Row row = null;
    SfUserDTO userAccount=(SfUserDTO) SessionUtil.getUserAccount(request);
%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_search()')" onload="do_SetPageWidth()">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.synchronize.servlet.AddressChangedServlet">
    <script type="text/javascript">
        printTitleBar("地点信息变更同步")
    </script>
    <input type="hidden" name="act">
    <input type="hidden" name="orgIds" value="">
    <table width="100%" border="0" bgcolor="#EFEFEF" style="width:100%">
        <tr>
            <td align="right" width="10%">地点代码：</td>
            <td align="left" width="25%"><input type="text" name="workorderObjectCode" value="<%=dto.getWorkorderObjectCode()%>" style="width:100%"></td>
            <td align="right" width="12%">EAM地点描述：</td>
            <td align="left" width="25%"><input type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" style="width:100%"></td>
               </tr>
        <tr>
            <td align="right" width="10%">MIS地点描述：</td>
            <td align="left" width="25%"><input type="text" name="assetsLocation" value="<%=dto.getAssetsLocation()%>" style="width:100%"></td>
            <td align="right" width="12%">上次修改人</td>
            <td align="left" width="25%"><input type="text" name="lastUpdateByName" value="<%=dto.getLastUpdateByName()%>" style="width:100%" onclick="do_selectUser();" title="点击选择用户"
                       class="readonlyInput"                         readonly><input type="hidden" name="lastUpdateBy" value="<%=dto.getLastUpdateBy()%>"></td>
            <td align="right" width="20%">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出"><img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="同步">
            </td>
        </tr>
    </table>
    <div id="headDiv" style="position:absolute;width:841px;overflow:hidden;top:73px;padding:0px; margin:0px;">
        <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="4%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox" onclick="checkAll('titleCheck','systemids')"></td>
                <td width="15%" align="center">地点代码</td>
                <td width="33%" align="center">EAM地点描述</td>
                <td width="33%" align="center">MIS地点描述</td>
                <td width="12%" align="center">上次修改人</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:72%;width:859px;position:absolute;top:96px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="4%" align="center"><input type="checkbox" name="systemids" value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
                <td width="15%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
                <td width="33%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
                <td width="33%"><input class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
                <td width="12%"><input class="finput" readonly value="<%=row.getValue("USERNAME")%>"></td>
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
<div style="position:absolute;top:90%;left:0"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%
    }
%>
</body>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
    }
    function do_selectUser() {
        var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 44;
        var dialogHeight = 29;
        var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);

        if (objs) {
            var obj = objs[0];
            document.mainFrm.lastUpdateBy.value = obj["userId"];
            document.mainFrm.lastUpdateByName.value = obj["userName"];
        } else {
            document.mainFrm.lastUpdateBy.value = "";
            document.mainFrm.lastUpdateByName.value = "";
        }
    }
    /**
     * 功能：同步数据
     */
    <%--function do_syschronize(){--%>
    <%--mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.EamNewLocusServlet";--%>
    <%--mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";--%>
    <%--mainFrm.submit();--%>
    <%--}--%>

</script>
