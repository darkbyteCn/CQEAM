<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
	<title>未匹配的MIS资产信息</title>
</head>
<%
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute("MIS_HEADER");
    String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
%>
<body topmargin="0" leftmargin="0" style="overflow:auto;width:700px">
<form action="/servlet/com.sino.ams.match.servlet.AmsAssetsChangeServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("未匹配的MIS资产信息")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act" value="<%=act%>">
    <table width="100%" class="queryTable">
        <tr>
            <td width="10%" align="right">条码：</td>
            <td width="30%"><input type="text" name="tagNumber" value="<%=dto.getTagNumber()%>" style="width:80%"
                                   class="input_style1"></td>
            <td width="10%" align="right">名称：</td>
            <td width="30%"><input type="text" name="assetsDescription" value="<%=dto.getAssetsDescription()%>" style="width:80%"
                                   class="input_style1"></td>
        </tr>
        <tr>
           <td width="10%" align="right">型号：</td>
            <td width="30%"><input type="text" name="modelNumber" value="<%=dto.getModelNumber()%>" style="width:80%"
                                   class="input_style1"></td>
            <td></td>
            <td><img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()"></td>
        </tr>
    </table>
    <div id="headDiv" style="overflow-y:scroll;width:100%" class="crystalScroll">
        <table width="100%" border="1" class="headerTable">
            <tr height="22">
                <td align="center" width="5%"></td>
                <td align="center" width="10%">资产条码</td>
                <td align="center" width="15%">资产名称</td>
                <td align="center" width="10%">规格型号</td>
                <td align="center" width="10%">启用日期</td>
                <td align="center" width="10%">资产原值</td>
                <td align="center" width="10%">责任人</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;height:490px;width:100%" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                Row row = null;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
//                        Logger.logInfo("size = " + rows.getSize());
                        row = rows.getRow(i);

            %>
            <tr class="hei" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td height="22" width="5%" align="center"><input type="radio" name="assetId"
                                                                 value="<%=row.getValue("ASSET_ID")%>">
                </td>
                <td height="22" width="10%"><%=row.getValue("TAG_NUMBER") %></td>
                <td height="22" width="15%"><%=row.getValue("ASSETS_DESCRIPTION") %></td>
                <td height="22" width="10%"><%=row.getValue("MODEL_NUMBER") %> </td>
                <td height="22" width="10%"><%=row.getValue("DATE_PLACED_IN_SERVICE") %></td>
                <td height="22" width="10%"><%=row.getValue("COST") %></td>
                <td height="22" width="10%"><%=row.getValue("ASSIGNED_TO_NAME") %></td>
            </tr>

            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
<jsp:include page="/message/MessageProcess"/>
</body>
<script type="text/javascript">
    function do_query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.forms[0].act.value = "<%=WebActionConstant.QUERY_ACTION%>"
        document.forms[0].submit();
    }

    function do_selectAssetsLocation() {
        document.mainForm.assetsLocation.value  = "";
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_LOCATION%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            document.mainForm.assetsLocation.value = projects[0].workorderObjectLocation;
        }
    }
</script>
</html>