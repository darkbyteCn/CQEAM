<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-9-20
  Time: 13:06:49
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
    <title>设备分配维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">	
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
    String itemCode = parser.getParameter("itemCode");
    String itemName = parser.getParameter("itemName");
    String itemSpec = parser.getParameter("itemSpec");
    String misItemCode = parser.getParameter("misItemCode");
    String vendorId = parser.getParameter("vendorId");
    String vendorName = parser.getParameter("vendorName");
    String itemCategory = (String) request.getAttribute(WebAttrConstant.EQUIPMENT_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>

<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.item.servlet.EtsSystemItemServlet">
    <script type="text/javascript">
        printTitleBar("设备分配维护")
    </script>
    <table width="100%" topmargin="0" border="0" >
        <input type="hidden" name="act">
        <tr>
        	<td width="8%" align="right">分配OU：</td>
            <td width="20%"><select name="masterOrganizationId" class="select_style1" style="width:100%" ><%= request.getAttribute( WebAttrConstant.OU_OPTION ) %></select></td>
            <td width="8%" align="right">设备名称：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="itemName" value="<%=itemName%>"></td>

            <%-- <td width="10%" align="right">设备名称：</td>
            <td width="20%"><input style="width:85%" type="text" name="itemName" value="<%=itemName%>"></td>--%>

            <td width="8%" align="right">型号：</td>
            <td width="20%"><input class="input_style1" style="width:85%" type="text" name="itemSpec" value="<%=itemSpec%>"></td>
        </tr>
        <tr>
            <td align="right">设备类型：</td>
            <td><select name="itemCategory" class="select_style1" style="width:100%"><%=itemCategory%></select></td>
            <td align="right">生产厂家：</td>
            <td><nobr><input type="text" name="vendorName" value="<%=vendorName%>" class="input_style1" style="width:90%">
                <a href="#"  onClick="SelectVendorId(); "  title="点击选择生产厂家"   class="linka">[…]</a></nobr>
            </td>
            <td align="center" colspan="2">
            	<img align="middle" src="/images/eam_images/new.jpg" alt="新增项目" onclick="do_add(); return false;">
             	<img align="middle" src="/images/eam_images/search.jpg" alt="查询" onclick="do_search(); return false;">
            </td>
        </tr>
    </table>
    <input type="hidden" name="vendorId" value="<%=vendorId%>">

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="8%" align="center">设备类型</td>
                <td width="10%" align="center">设备名称</td>
                <td width="20%" align="center">规格型号</td>
                <td width="4%" align="center">单位</td>
                <td width="10%" align="center">MIS物料编码</td>
                <td width="5%" align="center">使用年限</td>
                <td width="15%" align="center">生产厂家</td>
                <td width="13%" align="center">分配公司</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;left:0px;width:100%;height:350px">
        <table width="100%" border="1" >
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="show_detail('<%=row.getValue("ITEM_CODE")%>')">
                <td width="8%" align="center"><%=row.getValue("ITEM_TYPE")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="4%" align="center"><%=row.getValue("ITEM_UNIT")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("MIS_ITEM_CODE")%>
                </td>
                <td width="5%" align="right"><%=row.getValue("YEARS")%>
                </td>
                <td width="15%" align="left"><%=row.getValue("VENDOR_NAME")%>
                </td>
                 <td width="13%" align="left"><%=row.getValue("COMPANY")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="position:absolute;top:89%;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

</body>
</html>

<script type="text/javascript">

    function do_search() {
    	if( mainFrm.masterOrganizationId.value == "" ){
    		alert( "请选择OU" );
    		return false;
    	}
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(itemCode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.item.servlet.EtsSystemItemServlet?itemCode=" + itemCode;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function SelectVendorId() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_VENDOR%>";
        var popscript = "dialogWidth:48;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var vendorNames = window.showModalDialog(url, null, popscript);
        if (vendorNames) {
            var vendorName = null;
            for (var i = 0; i < vendorNames.length; i++) {
                vendorName = vendorNames[i];
                dto2Frm(vendorName, "mainFrm");
            }
        }
    }


</script>