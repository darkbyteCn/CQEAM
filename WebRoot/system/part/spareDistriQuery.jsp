<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-7
  Time: 14:43:55
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
    <title>设备分配确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("设备分配确认")
</script>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String itemName = parser.getParameter("itemName");
    String misItemCode = parser.getParameter("misItemCode");
    String vendorId = parser.getParameter("vendorId");
    String vendorName = parser.getParameter("vendorName");
    String itemCategory = (String) request.getAttribute(WebAttrConstant.EQUIPMENT_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()')" >
<%=WebConstant.WAIT_TIP_MSG%>
<table width="100%" topmargin="0" border="0" bgcolor="#eeeeee">
    <form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet">
        <input type="hidden" name="act">
        <input type="hidden" name="orgIds" value="">
        <tr>
            <td width="12%" align="right">MIS物料编码：</td>
            <td width="20%"><input style="width:90%" type="text" name="misItemCode" value="<%=misItemCode%>"></td>
            <td width="10%" align="right">设备名称：</td>
            <td width="20%"><input style="width:85%" type="text" name="itemName" value="<%=itemName%>"></td>
            <td width="10%" align="left"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
        <tr>
            <td align="right">设备类型：</td>
            <td><select name="itemCategory" style="width:90%">
                <%=itemCategory%>
            </select></td>
            <td align="right">生产厂家：</td>
            <td><input type="text" name="vendorName"  readonly value="<%=vendorName%>" style="width:85%"><a href="#" onClick="SelectVendorId(); "
                                                                                                  title="点击选择生产厂家"  class="linka">[…]</a></td>
            <td align="left">         </td>
        </tr>
</table>
<input type="hidden" name="vendorId" value="<%=vendorId%>">

<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="10%" align="center">设备类型</td>
            <td width="20%" align="center">设备名称</td>
            <td width="10%" align="center">规格型号</td>
            <td width="5%" align="center">单位</td>
            <td width="10%" align="center">MIS物料编码</td>
            <td width="10%" align="center">使用年限</td>
            <td width="20%" align="center">生产厂家</td>
        </tr>
    </table>
</div>
<div style="overflow-y:scroll;left:0px;width:100%;height:350px">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">

            <td width="10%" align="center" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')" ><%=row.getValue("ITEM_TYPE")%>
            </td>
            <td width="20%" align="left" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')" ><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="10%" align="left" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="5%" align="center" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')"><%=row.getValue("ITEM_UNIT")%>
            </td>
            <td width="10%" align="left" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')"><%=row.getValue("MIS_ITEM_CODE")%>
            </td>
            <td width="10%" align="right" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')"><%=row.getValue("YEARS")%>
            </td>
            <td width="20%" align="left" onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')"><%=row.getValue("VENDOR_NAME")%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%
    }
%>
</body>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=AMSActionConstant.DISTRIBUTE_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(itemCode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        var url = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&itemCode=" + itemCode;
        var popscript = 'width=550,height=300,top=100,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes';
        window.open(url, 'confir', popscript);
//        mainFrm.submit();
    }


 function SelectVendorId() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var popscript = "dialogWidth:35;dialogHeight:28;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                dto2Frm(vendorName, "mainFrm");
            }
        }else{
            document.mainFrm.vendorName.value ="";
            document.mainFrm.vendorId.value ="";
        }
    }
</script>