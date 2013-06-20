<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.yj.dto.AmsYjUserDTO" %>
<%@ page import="com.sino.ams.yj.constant.YJWebAttribute" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: jialongchuan
  Date: 2010-7-13
  Time: 15:24:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>卫星电话维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>

</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String isatphoneId = parser.getParameter("isatphoneId");
    String isatphoneAddress = parser.getParameter("isatphoneAddress");
    String orgOption = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()')" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet">
    <script type="text/javascript">
        printTitleBar("卫星电话维护")
    </script>
    <table width="100%" border="1" class="queryHeadBg">
        <input type="hidden" name="act">
        <tr>
            <td width="6%" align="right">公司名称：</td>
            <td width="11%"><select class="select_style1" style="width:100%" name="organizationId"><%=orgOption%>
            </select></td>
            <td width="6%" align="right">序号：</td>
            <td width="6%"><input class="input_style1" style="width:100%" type="text" name="isatphoneId" value="<%=isatphoneId%>"></td>
            <td width="10%" align="right">存储地点或单位：</td>
            <td width="10%"><input class="input_style1" style="width:100%" type="text" name="isatphoneAddress" value="<%=isatphoneAddress%>"></td>
                       <td width="15%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"><img
                    src="/images/eam_images/new.jpg" alt="新增" onClick="do_add();"><img src="/images/eam_images/export.jpg" style="cursor:'hand'"
                                                                                   onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
    </table>

      <div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:100%">
        <table border="1" style="color: #FFFFFF" bgcolor="#2983CB" width="150%">
            <tr height="20">
                <td width="8%" align="center">公司名称</td>
                <td width="4%" align="center">序号</td>
                <td width="10%" align="center">储备物资名称</td>
                <td width="6%" align="center">类别</td>
                <td width="8%" align="center">型号</td>
                <td width="10%" align="center">存储地点或单位</td>
                <td width="8%" align="center">电话号码</td>
                <td width="5%" align="center">购置时间</td>
                <td width="7%" align="center">资产原值(万元)</td>
                <td width="7%" align="center">使用次数（年）</td>
            </tr>
        </table>
    </div>
  <div id="dataDiv" style="overflow:scroll;height:70%;width:100%;position:absolute;top:67px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="show_detail('<%=row.getValue("ISATPHONE_ID")%>')">
                <td width="8%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("ORGANIZATION_NAME")%>"></td>
                <td width="4%" align="center" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                           value="<%=row.getValue("ISATPHONE_ID")%>">
                </td>
                <td width="10%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                          value="<%=row.getValue("ISATPHONE_NAME")%>">
                </td>
                <td width="6%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("ISATPHONE_TYPE")%>">
                </td>
                <td width="8%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("ISATPHONE_MODEL")%>">
                </td>
                <td width="10%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                          value="<%=row.getValue("ISATPHONE_ADDRESS")%>">
                </td>
                <td width="8%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("TEL")%>">
                </td>
                <td width="5%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("BUYING_TIME")%>">
                </td>
                <td width="7%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                          value="<%=row.getValue("COST")%>">
                </td>
                <td width="7%" align="left" onclick="show_detail('<%=isatphoneId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("USED_NUMBER")%>">
                </td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="navigatorDiv" style="position:absolute;bottom:0px;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

</body>
</html>

<script type="text/javascript">
    function initPage() {
            do_SetPageWidth();
        }


    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(isatphoneId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjIsatphoneServlet?isatphoneId=" + isatphoneId;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }

</script>