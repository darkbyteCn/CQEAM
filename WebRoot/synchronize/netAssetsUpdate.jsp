<%--
  User: sunny song
  Date: 2008-3-14
  Time: 7:55:54
--%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant" %>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>网络资产变动处理</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<%
    EamSyschronizeDTO assetsDTO = (EamSyschronizeDTO)request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
%>
<body onkeydown="autoExeFunction('do_search()')">
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.synchronize.servlet.NetAssetsUpdateServlet">
    <script type="text/javascript">
        printTitleBar("网络资产变动处理")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" topmargin="0" border="0" bgcolor="#EFEFEF" style="width:100%">
        <input type="hidden" name="act">
        <input type="hidden" name="transferType" value="<%=AssetsDictConstant.TRANS_INN_DEPT%>">
        <input type="hidden" name="faContentName" value="<%=AssetsDictConstant.FA_CONTENT_NET%>">
        <tr>
            <td width="10%" align="right">资产标签：</td>
            <td width="14%"><input style="width:100%" type="text" name="barCode" value="<%=assetsDTO.getBarCode()%>">
            </td>
            <td width="10%" align="right">资产描述：</td>
            <td width="14%"><input style="width:100%" type="text" name="assetsDescription"
                value="<%=assetsDTO.getAssetsDescription()%>"></td>
            <td align="right" width="7%">部门：</td>
            <td width="22%">
                <select name="newDeptName" style="width:100%"><%=assetsDTO.getNewDeptNameOption()%>
                </select>
            </td>
            <td align="center">
                <img src="/images/eam_images/search.jpg"
                     onclick="do_search();" alt="查询"></td>
            <td ><img src="/images/eam_images/apply_allot.jpg" id="transImg"
                     onclick="do_SubmitTransfer();" alt="申请调出">
            </td>
        </tr>
    </table>

    <div id="headDiv" style="width:839px;overflow:hidden;padding:0; margin:0;">
        <table width="130%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="2%" align="center" ><input type="checkbox" name="mainCheck"
                                                                       class="headCheckbox"
                                                                       id="controlBox"
                                                                       onclick="checkAll('mainCheck','subCheck')"></td>
                <td width="6%" align="center">EAM资产标签号</td>
                <td width="6%" align="center">ERP资产标签号</td>
                <td width="6%" align="center">ERP资产编号</td>
                <td width="8%" align="center">资产描述</td>
                <td width="12%" align="center">资产型号</td>
                <td width="10%" align="center">原资产地点</td>
                <td width="10%" align="center">新资产地点</td>
                <td width="10%" align="center">责任部门</td>
                <td width="4%" align="center">原责任人</td>
                <td width="4%" align="center">新责任人</td>
            </tr>
        </table>
    </div>

    <div style="overflow:scroll;height:400px;width:856px;" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="130%" border="1" bordercolor="#666666" cellpadding="0" cellspacing="0">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="2%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%>
                </td>
                <td width="6%" align="center"><%=row.getValue("OLD_BARDOE")%>
                </td>
                <td width="6%" align="center"><%=row.getValue("NEW_BARCODE")%>
                </td>
                <td width="6%" align="left"><%=row.getValue("ASSET_NUMBER")%>
                </td>
                <td width="8%" align="left"><%=row.getValue("OLD_ASSETS_DESCRIPTION")%>
                </td>
                <td width="12%" align="left"><%=row.getValue("OLD_MODEL_NUMBER")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("OLD_ASSETS_LOCATION")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("NEW_ASSETS_LOCATION")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("OLD_DEPT_NAME")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("OLD_USER_NAME")%>
                </td>
                <td width="4%" align="left"><%=row.getValue("NEW_USER_NAME")%>
                </td>
                <input type="hidden" name="newdeptName" value="<%=row.getValue("NEW_DEPT_NAME")%>">
                <input type="hidden" name="deptCode" value="<%=row.getValue("DEPT_CODE")%>">

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
        mainFrm.target = "_self";
        mainFrm.submit();
    }

    function do_SubmitTransfer() {
        if (!mainFrm.$$$CHECK_BOX_HIDDEN$$$) {
            alert("没有数据，不能执行指定的操作。");
            return;
        }
        if (!mainFrm.$$$CHECK_BOX_HIDDEN$$$.value) {
            alert("没有选择数据，不能执行指定的操作。");
            return;
        }

        var transferType = mainFrm.transferType.value;
        var faContentName = mainFrm.faContentName.value ;
        var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
        window.open("/public/waiting.htm", "transWin", style);
        mainFrm.target = "transWin";
        mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.NetAssetsUpdateServlet";
        mainFrm.act.value = "<%=AssetsActionConstant.TRANSFER_ACTION%>";
        mainFrm.submit();
    }

</script>