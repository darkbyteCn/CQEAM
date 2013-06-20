<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<script type="text/javascript" src="/WebLibary/js/help.js"></script>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>其它个人报废资产</title>
</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String itemStatusOption = parser.getAttribute("OTHER_DISCARDED_ITEM").toString();
    //String assetNumber = parser.getParameter("assetNumber");
    String barcode = parser.getParameter("barcode");
    String itemName = parser.getParameter("itemName"); //assetsDescription
    String itemSpec = parser.getParameter("itemSpec"); //modelNumber

%>
<body onkeydown="autoExeFunction('do_search()')" onload="helpInit('2.N.4');">
<input type="hidden" name="helpId" value="">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.servlet.OdEtsFaAssetsServlet">
    <script type="text/javascript">
        printTitleBar("其它个人报废资产")
    </script>
    <table width="100%" topmargin="0" border="0" class="queryTable">
        <input type="hidden" name="act">
        <tr>
            <td align="right" width="8%">资产名称：</td>
            <td width="12%">
            	<input type=text name=itemName style="width:90%" class="input_style1" value="<%=itemName%>">
            		<!-- <a href=# title="点击选择资产名称" class="linka" onclick="do_SelectSpec();">[…]</a> -->
            </td>
            <td align="right" width="8%">资产型号：</td>
            <td width="12%">
            	<input type=text name=itemSpec style="width:90%" class="input_style1" value="<%=itemSpec%>">
            		<!-- <a href=# title="点击选择规格型号" class="linka" onclick="do_SelectSpec();">[…]</a> -->
            </td>
 	        <td align="right" width="8%">标签号：</td>
	        <td width="12%"><input class="input_style1" type="text" name="barcode" style="width:90%" value="<%=barcode%>"></td>
	        <td align="right" width="10%">资产状态：已报废</td>
	        <!-- <td><select class="select_style1" name="itemStatus" id="itemStatus"><%//=itemStatusOption%></select></td> -->
            <td align="center" colspan="2">
            	<img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            	<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
            </td>
        </tr>
    </table>

	<script type="text/javascript">
        var columnArr = new Array("资产名称", "资产型号", "标签号", "资产状态","地点代码","地点名称","责任人","员工号","责任部门","使用人");
        var widthArr = new Array("15%","8%","7%","5%","7%", "10%", "5%","5%","8%","5%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:0px;width:100%;height:300px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
                <td width="15%" align="center"><%=row.getValue("ITEM_NAME")%></td>
                <td width="8%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="7%" align="left"><%=row.getValue("BARCODE")%></td>
                <td width="5%" align="left"><%=row.getValue("ITEM_STATUS")%></td>
                <td width="7%" align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
                <td width="10%" align="right"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
                <td width="5%" align="right"><%=row.getValue("RESPONSIBILITY_USER_NAME")%></td>
                <td width="5%" align="center"><%=row.getValue("EMPLOYEE_NUMBER")%></td>
                <td width="8%" align="left"><%=row.getValue("DEPT_NAME")%></td>
                <td width="5%" align="left"><%=row.getValue("MAINTAIN_USER_NAME")%></td>
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

</body>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
        //setTimeout('myrefresh()',5000); 
    }
    
    function myrefresh() {
		window.location.reload();
	}

    function show_detail(itemCode) {
        <%--mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
//        mainFrm.action = "/servlet/com.sino.ams.system.intangible.servlet.IntangibleServlet";
//        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_SelectSpec() {
        document.mainFrm.itemName.value ="";
        document.mainFrm.itemSpec.value ="";
        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_SYSITEM%>";
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
    
</script>