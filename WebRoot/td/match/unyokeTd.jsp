<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  created by YS
  Date: 2007-09-28
  Time: 8:23:36
--%>
<html>
<head>
    <title>撤销资产匹配关系(TD)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
</head>

<body onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();mainFrm.workorderObjectName.focus();">
<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("systemId");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String matchType = StrUtil.nullToString(reqParser.getParameter("matchType"));
    String itemName = StrUtil.nullToString(reqParser.getParameter("itemName"));
    String itemSpec = StrUtil.nullToString(reqParser.getParameter("itemSpec"));
    String workorderObjectNo = StrUtil.nullToString(reqParser.getParameter("workorderObjectNo"));
    String workorderObjectName = StrUtil.nullToString(reqParser.getParameter("workorderObjectName"));
    String tagNumber = StrUtil.nullToString(reqParser.getParameter("tagNumber"));
    String workorderobjectCode = StrUtil.nullToString(reqParser.getParameter("workorderobjectCode"));
    String barcode = StrUtil.nullToString(reqParser.getParameter("barcode"));
    String matchUserName = StrUtil.nullToString(reqParser.getParameter("matchUserName"));
    String matchUserId = StrUtil.nullToString(reqParser.getParameter("matchUserId"));
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("撤销资产匹配关系(TD)")
</script>
<table width="100%" border="1" class="queryHeadBg">
    <tr>
        <td width="8%" align="right">地点：</td>
        <td width="24%" align="left" colspan="3">
            <input name="workorderObjectName" type="text" style="width:80%" value="<%=workorderObjectName%>"><a href=#
                                                                                                                title="点击选择地点"
                                                                                                                class="linka"
                                                                                                                onclick="do_SelectProj();">[…]</a>
            <input type=hidden name="workorderObjectNo" value="<%=workorderObjectNo%>">
        </td>
        <td align="right" width="8%">MIS标签号：</td>
        <td align="center" width="10%"><input name="tagNumber" style="width:100%" type="text" value="<%=tagNumber%>">
        </td>
        <td align="right" width="8%">EAM标签号：</td>
        <td align="center" width="12%"><input name="barcode" style="width:100%" type="text" value="<%=barcode%>"></td>
        <td align="right" width="8%"><img src="/images/eam_images/unmatch.jpg" style="cursor:'hand'" onclick="do_unmatch();"
                                          alt="解除匹配"></td>
    </tr>
    <tr>
        <td align="right">匹配方式：</td>
        <td width="8%"><select name=unyokeFlag style="width:100%">

            <option value="1" <%if (reqParser.getParameter("unyokeFlag").equals("1")) {%> selected <%}%>>资产匹配
            </option>
            <option value="0" <%if (reqParser.getParameter("unyokeFlag").equals("0")) {%> selected <%}%>>转资匹配
            </option>
        </select>
        </td>
        <td align="right">匹配人：</td>
        <td align="left" width="8%"><input class="readonlyInput" type="text" name="matchUserName"
                                           value="<%=matchUserName%>" style="width:100%" onclick="do_selectUser();"
                                           title="点击选择用户" readonly><input type="hidden" name="matchUserId"
                                                                          value="<%=matchUserId%>"></td>
        <td align="right">设备名称：</td>
        <td><input name="itemName" style="width:100%" type="text" value="<%=itemName%>"></td>
        <td align="right">规格型号：</td>
        <td><input type=text name=itemSpec style="width:80%" value="<%=itemSpec%>"><input
                name=item_code type=hidden><a href=# title="点击选择名称型号" class="linka"
                                              onclick="do_SelectSpec();">[…]</a></td>
        <td align="right"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
        </td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=reqParser.getParameter("act")%>">
<input type="hidden" name="flag" value="0">

<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:840px">
    <table class="headerTable" border="1" width="150%">
        <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
        <tr onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <!--<td width="3%" align="center"><input type="checkBox" name="controlBox" class="headCheckbox"-->
                                                 <%--onClick="checkAll(this.name, 'systemId')"></td>--%>
            <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox" id="controlBox" onclick="checkAll('titleCheck','subCheck')"></td>
            <td height="22" width="7%" align="center">EAM条码</td>
            <td height="22" width="7%" align="center">MIS条码</td>
            <td height="22" width="14%" align="center">设备名称</td>
            <td height="22" width="15%" align="center">规格型号</td>
            <td height="22" width="10%" align="center">地点编码</td>
            <td height="22" width="18%" align="center">地点简称</td>
            <td height="22" width="8%" align="center">匹配人</td>
            <td height="22" width="10%" align="center">匹配时间</td>
            <td height="22" width="8%" align="center">原资产类型</td>
        </tr>

    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:857px;position:absolute;top:94px;left:1px" align="left"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <%--    <div style="overflow-y:scroll;height:362;width:852;position:absolute;top:95px;left:1px;margin-left:0px"
    align="left">--%>
    <table width="150%" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onClick="executeClick(this)">
            <td width="3%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%>
            <!--<td width="3%" align="center">-->
                <%--<input type="checkbox" name="systemId" value="<%=row.getStrValue("SYSTEM_ID")%>">--%>
                <%--<input type="hidden" name="assetId" value="<%=row.getStrValue("ASSET_ID")%>">--%>
            <!--</td>-->
            <td style="word-wrap:break-word" height="22" width="7%"
                align="center"><%=row.getValue("BARCODE")%>
                <%--<input type="hidden" name="assetId" value="<%=row.getValue("BARCODE")%>">--%>
            </td>
            <td style="word-wrap:break-word" height="22" width="7%"
                align="center"><%=row.getValue("TAG_NUMBER")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="14%"
                align="left"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%"
                align="left"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%"
                align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="18%"
                align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="8%"
                align="center"><%=row.getValue("MATCH_USER_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%"
                align="center"><%=row.getValue("MATCH_DATE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%"
                align="center"><%=row.getValue("OLD_FINANCE_PROP")%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>

</form>

<div style="position:absolute;bottom:1px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
    function do_search() {
    	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_TD_SERVLET%>?matchType=<%=matchType%>";
        mainFrm.submit();
    }
    function do_unmatch() {
//        var checkCount = getCheckedBoxCount("systemId")
        var checkCount = getCheckedBoxCount("subCheck")
        if (checkCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
            if (document.mainFrm.flag.value == "1") {
                alert("正在撤销匹配，请等待。");
                return;
            }
            if (confirm("确定撤销匹配吗?")) {
                document.mainFrm.flag.value="1";
                document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
                mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
                mainFrm.action = "<%=URLDefineList.FINANCE_PROP_SET_TD_SERVLET%>?matchType=<%=matchType%>";
                mainFrm.submit();
            }

        }
    }
    function do_SelectProj() {
        var lookUpAddr = "<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>";
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var Addrs = getLookUpValues(lookUpAddr, dialogWidth, dialogHeight);
        if (Addrs) {
            var Addr = null;
            for (var i = 0; i < Addrs.length; i++) {
                Addr = Addrs[i];
                dto2Frm(Addr, "mainFrm");
            }
        }
    }
    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 51;
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
    function do_selectUser() {
        var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 44;
        var dialogHeight = 29;
        var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
        var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);

        if (objs) {
            var obj = objs[0];
            document.mainFrm.matchUserId.value = obj["userId"];
            document.mainFrm.matchUserName.value = obj["userName"];
        } else {
            document.mainFrm.matchUserId.value = "";
            document.mainFrm.matchUserName.value = "";
        }
    }
</script>