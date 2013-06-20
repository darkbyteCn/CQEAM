<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-6-10
  Time: 16:27:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ include file="/newasset/headerInclude.jsp"%>

<html>

<head>
    <title></title>
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
</head>

<body onkeydown="autoExeFunction('do_search()');">

<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("systemId");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String matchType = reqParser.getParameter("matchType");
    String itemName = reqParser.getParameter("itemName");
    String barcode = reqParser.getParameter("barcode");
    String workorderObjectNo = reqParser.getParameter("workorderObjectNo");
    String workorderObjectName = reqParser.getParameter("workorderObjectName");
    String item_code = reqParser.getParameter("item_code");
    String itemSpec = reqParser.getParameter("itemSpec");
%>

<form method="post" name="mainFrm">
    <script type="text/javascript">
            printTitleBar("租赁资产确认")
    </script>
    <input type="hidden" name="matchType" value="11">
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">条码：</td>
            <td width="25%" align="left"><input name="barcode" style="width:80%" type="text" value="<%=barcode%>"></td>
             <td width="10%" align="right">责任人：</td>
            <td width="25%" align="left"><input name="userName" style="width:80%" type="text" value="<%=reqParser.getParameter("userName")%>"></td>
             <td width="10%" align="right">使用人：</td>
            <td width="25%" align="left"><input name="maintainUser" style="width:80%" type="text" value="<%=reqParser.getParameter("maintainUser")%>"></td>

            <td align="center" valign="top" width="10%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
          </tr>
        <tr>
            <td width="10%" align="right">地点简称：</td>
            <td width="25%" align="left">
                <input type=hidden name=workorderObjectNo value="<%=workorderObjectNo%>">
                <input name=workorderObjectName type=text style="width:80%" value="<%=workorderObjectName%>"><a href=# title="点击选择地点" class="linka" onclick="do_SelectProj();">[…]</a>
            </td>
            <td width="10%" align="right">设备名称：</td>
                     <td width="25%"><input name="itemName" style="width:80%" type="text" value="<%=itemName%>"></td>

                   
            <td width="10%" align="right">规格型号：</td>
            <td width="25%">
                <input type=text name=itemSpec  style="width:80%" value="<%=itemSpec%>">
                <input type="hidden" name=item_code value="<%=item_code%>"><a href=# title="点击选择规格型号" class="linka" onclick="do_SelectSpec();">[…]</a>
            </td>
            <%
                if (matchType.equals(WebAttrConstant.MATCH_MODE_OTHER)) {
            %>
            <td align="center"><img src="/images/eam_images/hide.jpg" style="cursor:'hand'" onclick="do_match();" alt="屏蔽">
            </td>
            <% } else {
            %>
            <td align="center"><img src="/images/eam_images/ok.jpg" style="cursor:'hand'" onclick="do_match();" alt="确认">
            </td>
            <%}%>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=reqParser.getParameter("act")%>">

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
            <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
            <tr>
                <td width="2%" align="center">
                    <input type="checkBox" name="controlBox" class="headCheckbox" onClick="checkAll(this.name, 'systemId')">
                </td>
                <td height="22" width="15%" align="center">条码</td>
                <td height="22" width="10%" align="center">设备名称</td>
                <td height="22" width="15%" align="center">规格型号</td>
                <td height="22" width="10%" align="center">所属专业</td>
                <td height="22" width="20%" align="center">地点简称</td>
                <td height="22" width="7%" align="center">责任人</td>
                <td height="22" width="7%" align="center">使用人</td>
                <td height="22" width="14%" align="center">启用日期</td>
            </tr>

        </table>
    </div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onClick="executeClick(this)">
                <td width="2%" align="center"><input type="checkbox" name="systemId" id="systemId<%=i%>" value="<%=row.getStrValue("SYSTEM_ID")%>;<%=i%>">
                    <input  type="hidden" name="barcode1" id="barcode1<%=i%>"  value="<%=row.getValue("BARCODE")%>">
                                                     <!--onPropertyChange="setCheckBoxPropValue(this)">-->
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%"
                    align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    align="center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%"
                    align="center"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="20%"
                    align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="7%"
                    align="center"><%=row.getValue("USER_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="7%"
                    align="center"><%=row.getValue("MAINTAIN_USER")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="14%"
                    align="center"> <input  type="text" class='noEmptyInput' id="startDate<%=i%>" readonly name="startDate" style="width:100%" value="<%=row.getValue("START_DATE")%>" onclick="gfPop.fPopCalendar(startDate<%=i%>)" title="点击选择或更改启用日期" onBlur="do_SetLineTransDate(this)">
                    <%--<input type="text" name="startDate" id="startDate<%=i%>" style="width:100%; border: 1px solid #FFFFFF;cursor:hand" readonly value="<%=row.getValue("START_DATE")%>" onclick="gfPop.fPopCalendar(startdate<%=i%>)" >--%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>

<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.match.servlet.AmsRentAssetServlet?matchType=<%=11%>";
        mainFrm.submit();
    }
    function do_match() {
        var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
            mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.match.servlet.AmsRentAssetServlet?matchType=<%=11%>";
            mainFrm.submit();
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
</script>