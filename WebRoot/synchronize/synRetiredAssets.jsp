<%--
 * User: zhoujs
 * Date: 2008-7-3
 * Time: 11:10:34
 * Function:报废资产同步
--%>

<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
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
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>报废资产同步</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript">
        <%
         EamSyschronizeDTO assetsDTO=(EamSyschronizeDTO)request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
    //    AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);

        SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
   //     AmsAssetsPriviDTO dtoParameter = (AmsAssetsPriviDTO)request.getAttribute(QueryConstant.QUERY_DTO);

        %>

    </script>
</head>
<script type="text/javascript">
    printTitleBar("资产报废结果同步")
</script>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
%>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>


<form name="mainFrm" method="post" action="/servlet/com.sino.ams.synchronize.servlet.SynRetiredAssetsServlet">

    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="position:absolute;top:23px;width:100%">
        <input type="hidden" name="act">
        <input type="hidden" name="orgIds" value="">
        <input type="hidden" name="companyName" value="">

        <tr height="22">
            <td align="right" width="10%">报废单号：</td>
            <td align="left" width="30%">
                <input type="text" name="transNo" value="<%=assetsDTO.getTransNo()%>" style="width:100%">
            </td>
            <td align="right" width="10%">资产地点：</td>
            <td align="left" width="30%">
                <input type="text" name="newAssetsLocation" value="<%=assetsDTO.getNewAssetsLocation()%>"  style="width:100%">
            </td>
            <td align="center">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            </td>

        </tr>
        <tr height="22">
            <td align="right" width="10%">资产标签号：</td>
            <td align="left" width="30%">
                <input type="text" name="newBarcode" value="<%=assetsDTO.getNewBarcode()%>" style="width:100%">
            </td>
            <td align="right" width="10%">资产描述：</td>
            <td align="left" width="30%">
                <input type="text" name="nameTo" value="<%=assetsDTO.getNameTo()%>" style="width:100%">
            </td>

            <td align="center">
                <img src="/images/button/synchronize.gif" style="cursor:'hand'" onclick="do_syschronize();" alt="ERP同步">
                <img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_export();" alt="导出Excel">
            </td>
        </tr>
    </table>

    <div id="headDiv" style="position:absolute;width:841px;overflow:hidden;top:71px;padding:0px; margin:0px;">
        <table width="150%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center" style="padding:0">
                    <input type="checkbox" name="mainCheck" class="headCheckbox" id="controlBox"
                           onclick="checkAll('mainCheck','systemids')">
                </td>
                <td width="7%" align="center">资产标签号</td>
                <td width="7%" align="center">资产编号</td>
                <td width="7%" align="center">资产描述</td>
                <td width="7%" align="center">资产型号</td>
                <td width="7%" align="center">资产地点</td>
                <td width="7%" align="center">责任部门</td>
                <td width="7%" align="center">责任人</td>
                <td width="7%" align="center">资产成本</td>
                <td width="7%" align="center">累计折旧</td>
                <td width="7%" align="center">残值</td>
                <td width="7%" align="center">启用日期</td>
                <td width="7%" align="center">折旧年限</td>
                <td width="7%" align="center">剩余月份</td>
            </tr>
        </table>
    </div>

    <div style="overflow:scroll;height:68%;width:856px;position:absolute;top:93px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="150%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="2%" align="center">
                    <input type="checkbox" name="systemids" value="<%=row.getStrValue("SYSTEMID")%>">
                </td>
                <td width="7%" align="center"><%=row.getStrValue("NEW_BARCODE")%></td>
                <td width="7%" align="left"><%=row.getStrValue("ASSET_NUMBER")%></td>
                <td width="7%" align="left"><%=row.getStrValue("NEW_ITEM_NAME")%></td>
                <td width="7%" align="left"><%=row.getStrValue("NEW_ITEM_SPEC")%></td>
                <td width="7%" align="left"><%=row.getStrValue("OLD_ASSETS_LOCATION")%></td>
                <td width="7%" align="left"><%=row.getStrValue("OLD_DEPT_NAME")%></td>
                <td width="7%" align="left"><%=row.getStrValue("OLD_USER_NAME")%></td>
                <td width="7%" align="left"><%=row.getStrValue("COST")%></td>
                <td width="7%" align="left"><%=row.getStrValue("DEPRN_RESERVE")%></td>
                <td width="7%" align="left"><%=row.getStrValue("SCRAP_VALUE")%></td>
                <td width="7%" align="left"><%=row.getStrValue("DATE_PLACED_IN_SERVICE")%></td>
                <td width="7%" align="left"><%=row.getStrValue("LIFE_IN_YEARS")%></td>
                <td width="7%" align="left"><%=row.getStrValue("REMAIN_MONTHS")%></td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
</html>

<script type="text/javascript">
    function do_search() {
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }


    /**
     * 功能：同步数据
     */
    function do_syschronize() {
        var systemid = getCheckBoxValue("systemids");
        if (systemid == "") {
            alert("请选择同步数据");
            return;
        } else {
            document.mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.SynRetiredAssetsServlet?act=SYSCHRONIZE_ACTION";
            document.mainFrm.act.value = "<%=WebActionConstant.SYSCHRONIZE_ACTION%>";
            document.mainFrm.submit();
        }
    }
    function do_export() {
        document.mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.SynRetiredAssetsServlet";
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }


</script>