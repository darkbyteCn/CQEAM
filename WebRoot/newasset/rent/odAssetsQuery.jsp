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
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>送修未返还资产明细</title>
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
<body onkeydown="autoExeFunction('do_search()')" onload="do_SetPageWidth(); helpInit('2.N.4');">
<input type="hidden" name="helpId" value="">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.rent.servlet.AssetsQueryServlet">
    <script type="text/javascript">
        printTitleBar("送修未返还资产明细");
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
	        <td align="right" width="14%">资产状态：送修未返还</td>
	        <!-- <td><select class="select_style1" name="itemStatus" id="itemStatus"><%//=itemStatusOption%></select></td> -->
            <td align="center" colspan="2">
            	<img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            	<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
            </td>
        </tr>
    </table>

	<%
	String[] widthArr = { "10%" , "10%" , "8%" , "8%" , "12%" ,
						  "20%" , "6%" , "6%" , "14%" , "6%" };
	int widthIndex = 0;
	%>
    
    <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:49px;left:0px;width:100%">
   	  <table  border="1" width="140%" class="eamHeaderTable" cellpadding="0" cellspacing="0">
        <tr height="23px">
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产名称</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产型号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">标签号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">资产状态</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">地点代码</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">地点名称</td>
           	<td align=center width="<%= widthArr[ widthIndex ++ ] %>">责任人</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">员工号</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">责任部门</td>
            <td align=center width="<%= widthArr[ widthIndex ++ ] %>">使用人</td>
        </tr>
      </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:72%;width:100%;position:absolute;top:71px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="140%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                    	widthIndex = 0;
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("ITEM_NAME")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("ITEM_SPEC")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput2" value="<%=row.getValue("BARCODE")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("ITEM_STATUS")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput2" value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput2" value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("DEPT_NAME")%>"></td>
                <td width="<%= widthArr[ widthIndex ++ ] %>"><input readonly class="finput" value="<%=row.getValue("MAINTAIN_USER_NAME")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>

<div id="pageNaviDiv" style="position:absolute;top:87%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>


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