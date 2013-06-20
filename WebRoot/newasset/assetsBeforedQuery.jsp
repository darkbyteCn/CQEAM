<%--
  Created by sunny
  User: sunny
  Date: 2008-4-8
  Time: 7:42:26
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
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentInfoDTO"%>
<html>

<head>
    <title>设备查询</title>
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
    AmsInstrumentInfoDTO dto=(AmsInstrumentInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String assetNumber =parser.getParameter("assetNumber");
    String barcode = parser.getParameter("barcode");
    String itemName = parser.getParameter("itemName");
    String itemSpec =parser.getParameter("itemSpec");
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;

%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("预龄资产查询")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="20%"><select name="organizationId"><%=StrUtil.nullToString(cityOption)%>
            </select></td>
              <td width="10%" align="right">资产编号：</td>
            <td width="20%"><input name="assetNumber" type=text  value="<%=StrUtil.nullToString(assetNumber)%>"></td>
              <td width="10%" align="right">资产条码：</td>
            <td width="20%"><input name="barcode" type=text  value="<%=StrUtil.nullToString(barcode)%>"></td>

    </tr>
    <tr>
        <td width="10%" align="right">资产名称：</td>
        <td width="20%"><input name=itemName type=text  value="<%=StrUtil.nullToString(itemName)%>"><a
                href=# title="点击选择项目" class="linka" onclick="do_SelectSpec();">[…]</a></td>
        <td width="10%" align="right">规格型号：</td>
        <td width="20%">
           <input type=text name=itemSpec value="<%=StrUtil.nullToString(itemSpec)%>">
           <a href = # title = " 点击选择规格型号" class  = "linka" onclick = "do_SelectSpec();">[…]</a>
        </td>
         <td width=10% align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();"
                                          alt="查询"></td>
          <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()"
                 alt="导出到Excel"></td>
    </tr>
</table>

<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

  <script type="text/javascript">
        var columnArr = new Array("","公司", "资产编号", "资产条码", "资产类别", "资产名称","规格型号","折旧年限","启用日期","供应商");
        var widthArr = new Array("4%", "10%", "10%", "15%", "8%","10%","15%","15%","8%","5%");
        printTableHead(columnArr, widthArr);
    </script>

<div style="overflow:scroll;left:0px;width:100%;height:350px">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr class="dataTR">
            <td width="4%" align="center">
            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("COMPANY_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("ASSET_NUMBER")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("BARCODE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="8%" align="center"><%=row.getValue("ITEM_CATEGORY")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("LIFE_IN_YEARS")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="8%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="8%" align="center"><%=row.getValue("VENDOR_NAME")%>
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
        mainFrm.action = "com.sino.ams.newasset.servlet.AssetsBeforedServlet";
        mainFrm.submit();
    }

    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "com.sino.ams.newasset.servlet.AssetsBeforedServlet";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
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

</script>