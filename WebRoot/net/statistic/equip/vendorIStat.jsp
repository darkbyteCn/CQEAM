<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.net.statistic.dto.EquipStatDTO" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>

<%--
  created by V-yuanshuai
  Date: 2007-09-28
  Time: 8:23:36
--%>

<html>

<head>
    <title>仓库现有量--按厂家</title>
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

<body>

<%

    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String itemCateOption = parser.getAttribute("ITEM_CATEGORY_OPTION").toString();
    String financePropOption = parser.getAttribute("FINANCE_PROP_OPTION").toString();
    String itemStatusOption = parser.getAttribute("ITEM_STATUS_OPTION").toString();
    String itemSpec = parser.getParameter("itemSpec");
    String vendorName = parser.getParameter("vendorName");
%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("全省统计--按厂家")
    </script>
    <table width="100%" border="0"  class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="20%"><select name=organizationId style="width:100%"><%=cityOption%></select></td>
            <td width="10%" align="right">规格型号：</td>
            <td width="20%"><input type=text value="<%=itemSpec%>" name=itemSpec><a href=# title="点击选择规格型号" class="linka"
                                                                                    onclick="do_SelectSpec();">[…]</a>
            </td>
            <td width="20%"></td>
            <td><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()"
                     alt="导出到Excel"></td>
        </tr>
        <tr>
            <td align="right">设备状态：</td>
            <td><select name=itemStatus style="width:100%"><%=itemStatusOption%></select></td>
            <td align="right">厂家：</td>
            <td><input type=text readonly class="readonlyInput" value="<%=vendorName%>" name=vendorName><a href=#
                                                                                                          title="点击选择厂家"
                                                                                                          class="linka"
                                                                                                          onclick="do_SelectVendor();">[…]</a>
            </td>


        </tr>
    </table>

    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="qryType" value="<%=WebAttrConstant.BY_VENDOR%>2">
    <input type="hidden" name="vendorId" value="<%=parser.getParameter("vendorId")%>">
  <script type="text/javascript">
        var columnArr = new Array("厂家", "设备名称", "规格型号", "现有量");
        var widthArr = new Array("20%","25%", "25%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR">
                <td style="word-wrap:break-word" height="22" width="20%"
                    align="center"><%=row.getValue("VENDOR_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="25%"
                    align="center"><%=row.getValue("ITEM_NAME")%></td>
                <td style="word-wrap:break-word" height="22" width="25%"
                    align="center"><%=row.getValue("ITEM_SPEC")%></td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("CNT")%></td>
            </tr>
            <%
                }   }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
 <%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>

<script type="text/javascript">
    function do_search() {
         document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.STAT_EQP_SERVLET%>";
        mainFrm.submit();
    }

    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.STAT_EQP_SERVLET%>";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
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
    function do_SelectVendor() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_VENDORS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }else{
            mainFrm.vendorId.value="";
            mainFrm.vendorName.value="";
        }
    }
</script>