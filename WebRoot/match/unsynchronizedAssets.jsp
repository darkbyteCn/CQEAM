<%--
  Created by IntelliJ IDEA.
  User: jialongchuan
  Date: 2008-9-7
  Time: 19:33:32
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>未同步资产清单</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/jslib.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>


</head>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    EamSyschronizeDTO dto = (EamSyschronizeDTO) request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
%>
<body onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.match.servlet.UnsynchronizedAssetsServlet">
    <script type="text/javascript">
        printTitleBar("未同步资产清单")
    </script>
    <input type="hidden" name="act">
    <table width="100%"  style="width:100%">
        <tr>
            <td align="right" width="8%">责任部门：</td>
            <td width="30%">
                <select class="select_style1" size="1" name="deptNameOption" style="width:80%"><%=dto.getDeptNameOption()%></select>
            </td>
            <td align="right" width="10%">EAM标签号：</td>
            <td align="left" width="15%"><input class="input_style1" type="text" name="newBarcode" value="<%=dto.getNewBarcode()%>" style="width:100%"></td>
            <td align="right" width="10%">项目编号：</td>
            <td align="left" width="9%">
                <input class="input_style1" type="text" name="projectNumber" value="<%=dto.getProjectNumber()%>" style="width:100%" size="20"></td>
            <td align="center" width="20%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
        <tr>
            <td align="right" width="10%">EAM地点：</td>
            <td width="15%"><input class="input_style1" style="width:80%" type="text" name="newAssetsLocation" value="<%=dto.getNewAssetsLocation()%>"><a href="#" onclick="chooseSit()"
                                                                                                                                     title="点击选择地点" class="linka">[…]</a></td>
            <td align="right" width="10%">EAM资产名称：</td>
            <td align="left" width="16%"><input class="input_style1" type="text" name="nameTo" value="<%=dto.getNameTo()%>" style="width:100%"></td>

            <td align="right" width="10%">项目名称：</td>
            <td align="left" width="12%">
                <input class="input_style1" type="text" name="projectName" value="<%=dto.getProjectName()%>" style="width:100%" size="20"></td>
            <td align="center" width="20%"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出"></td>
        </tr>
    </table>
    <div id="headDiv" style="position:absolute;width:840px;overflow:hidden;top:70px;padding:0px; margin:0px;">
        <table width="130%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                       class="headCheckbox"
                                                                       id="controlBox"
                                                                       onclick="checkAll('titleCheck','systemids')"></td>
                <td width="6%" align="center">EAM标签号</td>
                <td width="6%" align="center">MIS标签号</td>
                <!--<td width="8%" align="center">MIS资产编号</td>-->
                <td width="8%" align="center">MIS资产名称</td>
                <td width="8%" align="center">EAM资产名称</td>
                <td width="8%" align="center">MIS资产型号</td>
                <td width="8%" align="center">EAM资产型号</td>
                <td width="9%" align="center">MIS地点</td>
                <td width="9%" align="center">EAM地点</td>
                <td width="4%" align="center">MIS责任人</td>
                <td width="4%" align="center">EAM责任人</td>

            </tr>
        </table>
    </div>

    <div id="dataDiv" style="overflow:scroll;height:70%;width:857px;position:absolute;top:92px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="130%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="2%" align="center"><input type="checkbox" name="systemids" value="<%=row.getValue("ASSET_ID")%>"></td>
                <td width="6%" align="left"><%=row.getValue("BARCODE")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("TAG_NUMBER")%>
                </td>
                <%--<td width="8%" align="left"><%=row.getValue("ASSET_NUMBER")%>--%>
                <%--</td>--%>
                <td width="8%" align="left"><%=row.getValue("ASSETS_DESCRIPTION")%>
                </td>
                <td width="8%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="8%" align="left"><%=row.getValue("MODEL_NUMBER")%>
                </td>
                <td width="8%" align="left"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="9%" align="left"><%=row.getValue("ASSETS_LOCATION")%>
                </td>
                <td width="9%" align="left"><%=row.getValue("LOCATION_CODE")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("OLD_ASSIGNED_TO_NAME")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_USER_NAME")%>
                </td>
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
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
    }


    function chooseSit() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>";
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (projects) {
            document.mainFrm.newAssetsLocation.value = projects[0].workorderObjectLocation;
        }
    }

</script>
