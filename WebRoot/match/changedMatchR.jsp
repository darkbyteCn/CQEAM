<%--
  Created by IntelliJ IDEA.
  User: V-jiachuanchuan
  Date: 2007-11-22
  Time: 10:56:38
  Function:转资匹配MIS资产信息查询
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head><title>MIS资产信息</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        String action = reqParser.getParameter("act");
        String workorderObjectNo = reqParser.getParameter("workorderObjectNo");
        String showAll = request.getParameter("showAll");
        String assetsLocation = reqParser.getParameter("assetsLocation");
        String projectId = reqParser.getParameter("projectId");
        showAll = showAll == null ? "N" : showAll;
        String itemName = reqParser.getParameter("itemName");
        String itemSpec = reqParser.getParameter("itemSpec");
        String segment1 = reqParser.getParameter("segment1");
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    %>


</head>
<base target="_self">

<body leftmargin=0 topmargin=0>

<form name="mainForm" action="/servlet/com.sino.ams.match.servlet.ChangedAssetsRightServlet" method="post">
    <script language="javascript">
        printTitleBar("MIS资产信息");
    </script>
    <table width="100%" border=0 cellpadding="2" cellspacing="0">

        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="organizationId" value="">
        <jsp:include page="/message/MessageProcess"/>

                    <input type="hidden" name="action" value="">
                    <input type="hidden" name="tempRadio" value="">
                    <input type="hidden" name="workorderObjectNo" value="<%=workorderObjectNo%>">
                    <input type="hidden" name="projectId" value="<%=projectId%>">
                    <input type="hidden" name="showAll" value="<%=showAll%>">

                    <tr>
                        <td align="right" width="15%">项目号：</td>
                        <td align="left" width="25%">
                            <input class="input_style1" name="segment1" type="text" value="<%=segment1%>" style="width:75%">
                            <a style="cursor:'hand'" onclick="do_SelectProj();" title="点击选择项目号" class="linka">[…]</a>
                        </td>
                        <td align="right" width="15%">所在地点：</td>
                        <td align="left" width="25%">
                            <input class="input_style1" style="width:75%" type="text" name="assetsLocation" value="<%=assetsLocation%>">
                            <a style="cursor:'hand' " onclick="showLocation();" title="点击选择所在地点" class="linka">[…]</a>
                        </td>
                        <td width="20%">&nbsp;</td>
                    </tr>


                    <tr>
                        <td align="right" width="15%">设备名称：</td>
                        <td align="left" width="25%">
                            <input class="input_style1" style="width:75%" type="text" name="itemName" value="<%=itemName%>">
                            <a style="cursor:'hand' " onclick="do_SelectSpec()" title="点击选择设备名称" class="linka">[…]</a>
                        </td>
                        <td align="right" width="15%">规格型号：</td>
                        <td align="left" width="25%">
                            <input class="input_style1" style="width:75%" type="text" name="itemSpec" value="<%=itemSpec%>">
                        </td>

                        <td align="right" width="20%">
                            <img src="/images/eam_images/search.jpg" onClick="do_Search(); return false;">
                        </td>
                    </tr>
                </table>

    <script type="text/javascript">
        var columnArr = new Array("", "标签号", "设备名称", "规格型号", "所在地点", "所属工程", "数量");
        var widthArr = new Array("5%", "13%", "18%", "18%", "18%", "18%", "10%");
        printTableHead(columnArr, widthArr);
    </script>


    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>

    <div style="overflow-y:scroll;height:480px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);

            %>
            <tr class="dataTR" height="22">
                <td width="5%" align="center"><input type="radio" id="assetId" name="assetId"
                                                     value="<%=row.getValue("ASSET_ID")%>"></td>
                <td width="13%"><%=row.getValue("TAG_NUMBER")%>
                </td>
                <td width="18%"><%=row.getValue("ASSETS_DESCRIPTION")%>
                </td>
                <td width="18%"><%=row.getValue("MODEL_NUMBER")%>
                </td>
                <td width="18%"><%=row.getValue("ASSETS_LOCATION")%>
                </td>
                <td width="18%"><%=row.getValue("SEGMENT1")%>
                </td>
                <td width="10%"><%=row.getValue("CURRENT_UNITS")%>
                </td>
                <%



                     }



                %>
        </table>
    </div>
    <%
        }
    %>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">
    function showLocation() {
        var lookUpLocation = "<%=LookUpConstant.LOOK_UP_ASSETS_LOCATION%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpLocation, dialogWidth, dialogHeight);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainForm");
            }
        }
    }
    function do_SelectProj() {
        var lookUpProj = "<%=LookUpConstant.LOOK_UP_PROJECT3%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var projs = getLookUpValues(lookUpProj, dialogWidth, dialogHeight);
        if (projs) {
            var proj = null;
            for (var i = 0; i < projs.length; i++) {
                proj = projs[i];
                dto2Frm(proj, "mainForm");
            }
        }
    }
    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainForm");
            }
        }
    }

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";

        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";

    <%--mainForm.act.value = "<%=WebAttrConstant.CHANGED_ASSETS_RIGHT%>";--%>
    <%--document.mainForm.action  = "/servlet/com.sino.ams.match.servlet.ChangedAssetsLeftServlet?act=<%=WebAttrConstant.CHANGED_ASSETS_RIGHT%>"--%>
        document.mainForm.submit();
    }

    //    function do_showDetail() {
    //        var TAG_NUMBER = getRadioValue("TAG_NUMBER");
    //        if (TAG_NUMBER != "") {
    <%--var url = "<%=URLDefineList.QRY_ITEM_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&TAG_NUMBER" + TAG_NUMBER;--%>
    //        }
    //    }
</script>
