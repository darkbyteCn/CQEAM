<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: Suhp
  Date: 2007-11-24
  Time: 12:58:45
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>MIS设备屏蔽</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>

<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String itemName = parser.getParameter("itemName");
    String itemSpec = parser.getParameter("itemSpec");
    String barcode = parser.getParameter("barcode");
    String item_code = parser.getParameter("item_code");
    String assetsLocation = parser.getParameter("assetsLocation");
    EtsFaAssetsDTO screendto = (EtsFaAssetsDTO) request.getAttribute(WebAttrConstant.ASSETS_DATA);
    String key = screendto.getKey();
%>
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.match.servlet.MisEquipmentScreenServlet">
<script type="text/javascript">
    if (<%=key.equals(WebAttrConstant.MATCH_MODE_0THER_RET)%>) {
        printTitleBar("MIS设备屏蔽撤消")
    } else {
        printTitleBar("MIS设备屏蔽")
    }
</script>
<table width="100%" border="0" class="queryHeadBg">
    <tr>
        <td width="10%" align="right">MIS条码：</td>
        <td width="25%" align="left"><input name="barcode" style="width:100%" type="text" value="<%=barcode%>"></td>
        <td width="10%" align="right">MIS设备名称：</td>
        <td width="25%"><input name="itemName" style="width:100%" type="text" value="<%=itemName%>"></td>
        <td align="center" valign="top" width="10%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'"
                                                         onclick="do_search();" alt="查询"></td>
    </tr>
    <tr>
        <td width="10%" align="right">MIS所在地点：</td>
        <td width="25%" align="left">
            <input name=assetsLocation type=text style="width:86%" value="<%=assetsLocation%>">
            <a href=# title="点击选择地点" class="linka" onclick="do_SelectProj();">[…]</a>
        </td>
        <td width="10%" align="right">MIS规格型号：</td>
        <td width="25%">
            <input type=text name=itemSpec style="width:86%" value="<%=itemSpec%>">
            <input type="hidden" name=item_code value="<%=item_code%>">
            <a href=# title="点击选择规格型号" class="linka" onclick="do_SelectSpec();">[…]</a>
        </td>
        <td align="center" width="10%">
            <%
                if (key.equals(WebAttrConstant.MATCH_MODE_0THER_RET)) {
            %>
            <img src="/images/eam_images/ok.jpg" style="cursor:'hand'" onclick="do_rescreen();" alt="撤销屏蔽">
            <%
            } else if (key.equals(WebAttrConstant.SCREEN_EXPROT)) {
            %>
            <img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
            <%
            } else {
            %>
            <img src="/images/eam_images/hide.jpg" style="cursor:'hand'" onclick="do_screen();" alt="屏蔽">
            <%}%>

        </td>
    </tr>
</table>
<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
        <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
        <tr>
            <%
                if (!key.equals(WebAttrConstant.MATCH_MODE_OTHER)) {
            %>
            <td width="3%" align="center"><input type="checkBox" name="controlBox" class="headCheckbox"
                                                 onClick="checkAll(this.name, 'systemId')"></td>
            <%}%>
            <td height="22" width="8%" align="center">MIS条码</td>
            <td height="22" width="15%" align="center">MIS设备名称</td>
            <td height="22" width="15%" align="center">MIS规格型号</td>
            <td height="22" width="20%" align="center">MIS所在地点</td>
        </tr>

    </table>
</div>
<input type="hidden" name="act">
<input type="hidden" name="key" value="<%=key%>">

<div style="overflow-y:scroll;left:0px;width:100%;height:360px">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr class="dataTR" onClick="executeClick(this)">
            <%
                if (!key.equals(WebAttrConstant.MATCH_MODE_OTHER)) {
            %>
            <td width="3%" align="center">
                <input type="checkbox" name="systemId" id="systemId0" value="<%=row.getStrValue("ASSET_ID")%>">
            </td>
            <%}%>
            <td style="word-wrap:break-word" height="22" width="8%"
                align="center"><%=row.getValue("BARCODE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%"
                align="center"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%"
                align="center"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="20%"
                align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/message/MessageProcess"/>

</body>

</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.match.servlet.MisEquipmentScreenServlet";
        mainFrm.submit();
    }

    function do_SelectProj() {
        var lookUpLocation = "<%=LookUpConstant.LOOK_UP_ASSETS_LOCATION%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpLocation, dialogWidth, dialogHeight);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
            }
        }
    }

    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50.5;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }
    }


    function do_screen() {
        var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
            if (confirm("确定要屏蔽吗？")) {
                mainFrm.act.value = "<%=WebActionConstant.DISABLED_ACTION%>";
                mainFrm.action = "/servlet/com.sino.ams.match.servlet.MisEquipmentScreenServlet";
                mainFrm.submit();
            }
        }
    }

    function do_rescreen() {
        var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
            if (confirm("确定要撤消屏蔽吗？")) {
                mainFrm.act.value = "<%=WebActionConstant.DISABLED_ACTION%>";
                mainFrm.action = "/servlet/com.sino.ams.match.servlet.MisEquipmentScreenServlet";
                mainFrm.submit();
            }
        }
    }

    function do_Export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.match.servlet.MisEquipmentScreenServlet";
        mainFrm.submit();
    }
</script>