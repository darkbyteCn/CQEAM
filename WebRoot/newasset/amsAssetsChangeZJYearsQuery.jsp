<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-4-7
  Time: 17:20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsCJYCDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>资产折旧年限查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body>
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsAssetsCJYCDTO dto = (AmsAssetsCJYCDTO) request.getAttribute("AMSBJTRANSNOHDTO");
//    out.print(dto);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.AmsAssetsChangeZJQueryServlet" name="mainForm" method="post">

    <script type="text/javascript">
        printTitleBar("资产折旧年限查询")
    </script>
    <table border="1" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td width=13% align="right"> 公司名称：</td>
            <td width="22%"><select name="organizationId" id="organizationId"
                                    style="width:100%"><%=request.getAttribute("OU")%>
            </select></td>
            <td width="13%" align="right">资产分类名称：</td>
            <td><select name="segment1" style="width:100%">
                                    style="width:100%"><%=request.getAttribute("SEGMENT1")%>
              </select></td>
            <td width=10% align="right">资产分类代码：</td>
            <td width=20% align="left"><input type="text" name="segment2" value="<%=dto.getSegment2()%>"></td>
        </tr>
         <tr>
            <td width=13% align="right"> 资产名称：</td>
           <td><input type="text" style="width:100%" width="100%" name="assetsDescription" value="<%=dto.getAssetsDescription()%>"></td>
            <td width="13%" align="right">规格型号：</td>
            <td><input type="text" style="width:100%" name="modelNumber" value="<%=dto.getModelNumber()%>"></td>
             <td width=10% align="right"><img src="/images/eam_images/search.jpg" alt="查询"
                                              onClick="do_search(); return false;"></td>
             <td  width=20% align="left"><img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_export()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <input type="hidden" name="years" value="">

    <script type="text/javascript">
        var columnArr = new Array("公司名称","资产编号", "资产分类","资产名称","规格型号", "新折旧年限", "新折旧金额", "启用日期");
        var widthArr = new Array( "10%", "10%", "10%", "10%","10%", "10%", "10%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr  >
                <td width="10%" align="left"><%=row.getValue("ORGNIZATION_NAME")%></td>
                <td width="10%" align="left"><%=row.getValue("ASSET_NUMBER")%></td>
                <td width="10%" align="left"><%=row.getValue("FA_CATEGORY1")%></td>
                <td width="10%" align="left"><%=row.getValue("ASSETS_DESCRIPTION")%></td>
                <td width="10%" align="left"><%=row.getValue("MODEL_NUMBER")%></td>
                <td width="10%" align="center"><%=row.getValue("NEW_YEARS")%></td>
                <td width="10%" align="left"><%=row.getValue("CHANGE_AMOUNT")%></td>
                <td width="10%" align="left"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>

</body>
<div style="position:absolute;top:480px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_batch() {
        var url = "/newasset/addBatchYears.jsp";
        var style = "dialogleft:200px;dialogtop:200px;dialogwidth:300px;dialogheight:180px;toolbar:no;directories:no;status:no;menubar:no;scrollbars:no;revisable:no";
        var years = window.showModalDialog(url, null, style);
        if (years) {
            document.getElementById('years').value = years;
        } else {
            //if(confirm(""))
            //alert("");
            //return false;
        }
        mainForm.act.value = "BATCH";
        mainForm.submit();
    }
    function do_save() {
        var checkCount = getCheckedBoxCount("subCheck");
        if (checkCount < 1) {
            alert("请选择一条记录，然后设置！");
        } else {
            mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
            mainForm.submit();
        }
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_drop() {
        var transType = document.getElementById("transType")   ;
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transType, 'BJBFS');
        //        dropSpecialOption(transStatus, 'APPROVED');
    }
    function do_ShowDetail(transId, transType) {
        mainForm.transId.value = transId;
        mainForm.transType.value = transType;
        var url = "/servlet/com.sino.ams.spare.query.servlet.AmsBjTransQueryServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId + "&transType=" + transType;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);
    }
</script>