<%--
  Created by  sunny.
  User: sunny
  Date: 2008-4-11
  Time: 3:43:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>
<html>
<head>
    <title>库存现状与MIS差异报表</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

</head>
<body onkeydown="autoExeFunction('do_search()')">


<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String itemSpec = parser.getParameter("itemSpec");
    String itemName = parser.getParameter("itemName");
    String misItemCode = parser.getParameter("misItemCode");
    String projectName = parser.getParameter("projectName");
    String workorderObjectName = parser.getParameter("workorderObjectName");

%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("库存现状与MIS差异报表")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">物料编码：</td>
            <td width="20%">
                <input type=text name="misItemCode" value="<%=misItemCode%>">
            </td>
            <td width="10%" align="right">设备名称：</td>
            <td width="20%">
                <input type=text name=itemName value="<%=itemName%>">
                <a href = # title = " 点击选择规格型号" class  = "linka" onclick = "do_SelectSpec();">[…] </a>
            </td>
            <td width="10%" align="right">规格型号：</td>
            <td width="20%"><input type=text name=itemSpec value="<%=itemSpec%>"><a href = # title = " 点击选择规格型号" class
                = "linka" onclick = "do_SelectSpec();">[…]
            </a>
        </td>
        <td width=10% align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();"
                                          alt="查询"></td>

    </tr>
    <tr>
        <td width="10%" align="right">地市：</td>
            <td width="20%"><select name=organizationId><%=cityOption%>
            </select></td>
        <td align="right">地点：</td>
        <td><input name=workorderObjectName type=text  value="<%=workorderObjectName%>"><a
                href=# title="点击选择地点" class="linka" onclick="do_SelectObjct();">[…]</a></td>
        <td></td>
         <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()"
                 alt="导出到Excel"></td>
    </tr>
</table>

<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

  <script type="text/javascript">
        var columnArr = new Array("","地市", "设备名称", "规格型号", "地点", "物料编码","EAM数量","MIS数量");
        var widthArr = new Array("4%", "10%", "10%", "15%", "10%","10%","8%","8%");
        printTableHead(columnArr, widthArr);
    </script>

<div style="overflow-y:scroll;left:0px;width:100%;height:350px">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr class="dataTR">
            <td width="4%" align="center"></td>
            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("COMPANY")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("ITEM_SPEC")%>
            </td>

            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("MIS_ITEM_CODE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="8%" align="center"><%=row.getValue("EAM_QUANTITY")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="8%" align="center"><%=row.getValue("QUANTITY")%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>

</form>

<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.net.statistic.servlet.MisDiffServlet";
        mainFrm.submit();
    }

    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.net.statistic.servlet.MisDiffServlet";
        mainFrm.submit();
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
                dto2Frm(spec, "mainFrm");
            }
        }
    }
    function do_SelectObjct() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var objs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (objs) {
            var obj = null;
            for (var i = 0; i < objs.length; i++) {
                obj = objs[i];
                dto2Frm(obj, "mainFrm");
            }
        }
    }
     function do_ShowDetail(barcode){
	if(barcode == ""){
		alert("自定义显示字段中不含“标签号”，无法显示该资产详细信息。");
		return;
	}
	var url = "/servlet/com.sino.ams.net.equip.servlet.PlantMessageServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=600,height=340,left=100,top=150";
	window.open(url, winName, style);
}
</script>