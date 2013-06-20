<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.rent.dto.RentDTO" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: 李轶
  Date: 2009-05-17
  Time: 15:57:18
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>租赁资产信息</title>
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

</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    RentDTO dto = (RentDTO) request.getAttribute("RENT_QUERY_DTO");
%>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<body onkeydown="autoExeFunction('do_search()');" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.rent.servlet.RentServlet?accessType=AMS_RENT_INFO" name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("租赁资产>><%=(dto.getCompanyName()).equals("nothing") ? dto.getResponsibilityDeptName() : dto.getCompanyName()%>")
</script>
     <div id="headDiv" style="overflow:hidden;position:absolute;top:19px;left:1px;width:300%">
   	  <table  border="1" width="300%" class="headerTable" cellpadding="0" cellspacing="0">
   	  	<input type = "hidden" name = "act">
        <tr height="22">
            <!--<td width="1%" align="center">序号</td>-->
            <td width="3%" align="center">公司OU</td>
            <td width="3%" align="center">租赁资产标签号</td>
            <td width="4%" align="center">资产名称</td>
            <td width="3%" align="center">规格型号</td>
            <td width="2%" align="center">单位</td>
            
            <td width="6%" align="center">生产厂商名称</td>
            <td width="2%" align="center">额定功率</td>
            <td width="3%" align="center">设备性能</td>
            <td width="4%" align="center">资产类别代码组合</td>
            <td width="7%" align="center">资产类别描述</td>
            <td width="3%" align="center">责任人编号</td>
            <td width="3%" align="center">责任人姓名</td>
            <td width="11%" align="center">资产地点</td>
            
            <td width="2%" align="center">使用人</td>
            <td width="7%" align="center">使用部门</td>
            
            <td width="3%" align="center">起始日期</td>
            <td width="3%" align="center">截止日期</td>
            
            <td width="7%" align="center">签约单位</td>
            
            <td width="2%" align="center">租期(年)</td>
            <td width="2%" align="center">年租金(元)</td>
            <td width="2%" align="center">月租金</td>
            
            <td width="4%" align="center">备注</td>
        </tr>
      </table>
    </div>

	<div id="dataDiv" style="overflow:scroll;height:90%;width:300%;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="300%" border="1" bordercolor="#666666" id="dataTab" >
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        	    boolean hasData = (rows != null && !rows.isEmpty());
                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
                <tr class="dataTR">
                <%--<td width="5%" align="center"><%=row.getValue("ROWNUM")%></td>--%>
                <td width="3%" align= "center"><%=row.getValue("COMPANY")%></td>
                <td width="3%" align= "center"><%=row.getValue("BARCODE")%></td>
                <td width="4%"><%=row.getValue("ITEM_NAME")%></td>
                <td width="3%"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="2%" align= "center"><%=row.getValue("ITEM_UNIT")%></td>
                
                <td width="6%" align= "center"><%=row.getValue("MANUFACTURER_NAME")%></td>
                <td width="2%" align= "center"><%=row.getValue("POWER")%></td>                
                <td width="3%" align= "center"><%=row.getValue("OTHER_INFO")%></td>
                <td width="4%" align= "center"><%=row.getValue("CONTENT_CODE")%></td>
                <td width="7%" align= "left"><%=row.getValue("CONTENT_NAME")%></td>
                <td width="3%" align= "center"><%=row.getValue("RESPONSIBILITY_USER")%></td>
                <td width="3%" align= "center"><%=row.getValue("USER_NAME")%></td>
                <td width="11%"><%=row.getValue("OBJECT_NAME")%></td>
            
                <td width="2%"><%=row.getValue("MAINTAIN_USER")%></td>
                <td width="7%"><%=row.getValue("MAINTAIN_DEPT")%></td>
                
                <td width="3%" align="center"><%=row.getValue("RENT_DATE")%></td>
                <td width="3%" align="center"><%=row.getValue("END_DATE")%></td>
                
                <td width="7%"><%=row.getValue("RENT_PERSON")%></td>
                
                <td width="2%" align="right"><%=row.getValue("TENANCY")%></td>
                <td width="2%" align="right"><%=row.getValue("YEAR_RENTAL")%> </td>
                <td width="2%" align="right"><%=row.getValue("MONTH_REANTAL")%></td>
                
                <td width="4%" align="left"><%=row.getValue("REMARK")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>

<%--<%--%>
<%--    if (hasData) {--%>
<%--%>--%>
<%--<div id="navigatorDiv" style="position:absolute;top:94%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>--%>
<%--<%--%>
<%--    }--%>
<%--%>--%>

<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script type="text/javascript">
	function initPage(){
		do_SetPageWidth();
		do_TransData();
	}

    function show_detail(barcode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.RentServlet?barcode=" + barcode;
        mainFrm.submit();
    }

</script>