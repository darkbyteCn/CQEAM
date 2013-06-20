<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<%
	
	RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
   
%>
<head>
	<title>零购报账标记</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/DefaultLocationProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("零购报账标记");
</script>

<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();helpInit('4.4.4');">

<form action="/servlet/com.sino.ams.workorder.servlet.ZeroTurnImportServlet" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:29px;left:1px;width:100%">
  <table style="width:100%;">
        <input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="excel" value="">
<input type="hidden" name="excelPath"  value="" >
        <tr align="right">  
            <td width="80%">
           <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"  onClick="do_Sub();">
            </td>
        </tr>
    </table>
</div>
  <div id="headDiv" style="overflow:hidden;position:absolute;top:88px;left:0px;width:250%">
	<table class="headerTable" border="1" width="260%">
		<tr height=22px >
			<td width="2%" align="center"><span class="resizeDivClass"></span>序号</td>
			<td width="4%" align="center"><span class="resizeDivClass"></span>资产标签号</td>
            <td width="5%" align="center"><span class="resizeDivClass"></span>资产名称</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>规格型号</td>
			<td width="6%" align="center"><span class="resizeDivClass"></span>厂商</td>
			
            <td width="4%" align="center"><span class="resizeDivClass"></span>资产类别</td>
            <td width="2%" align="center"><span class="resizeDivClass"></span>数量</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>使用年限</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>资产原值</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>启用日期</td>
           
            <td width="5%" align="center"><span class="resizeDivClass"></span>业务平台</td>
            <td width="5%" align="center"><span class="resizeDivClass"></span>网络层次</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>是否共建</td>
            <td width="5%" align="center"><span class="resizeDivClass"></span>成本中心</td>
            <td width="10%" align="center"><span class="resizeDivClass"></span>地点名称</td>
            
            <td width="5%" align="center"><span class="resizeDivClass"></span>地点编码</td>
            <td width="2%" align="center"><span class="resizeDivClass"></span>状态</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>员工编号</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>员工姓名</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>请购类别</td>
            
            <td width="3%" align="center"><span class="resizeDivClass"></span>联系人</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>联系方式</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>要求到货时间</td>
            <td width="5%" align="center" ><span class="resizeDivClass"></span>错误信息</td>
		</tr>
	</table>
</div>

<div id="dataDiv" style="overflow:scroll;height:65%;width:250%;position:absolute;top:105px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="260%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:keep-all">
        <%
       		RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    		boolean hasData = (rows != null && !rows.isEmpty());
        	if (hasData) {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                String barcode=row.getValue("BARCODE").toString().trim();
        %>
        <tr >
        		
        	<td width="2%" align = "center" ><%=row.getValue("ROW_NUM")%></td>
        	<td width="4%" align = "center" ><%=row.getValue("BARCODE")%></td>
			<td width="5%" align = "left" ><%=row.getValue("ITEM_NAME")%></td>
            <td width="4%" align = "left" ><%=row.getValue("ITEM_SPEC")%></td>
            <td width="6%" align = "left" ><%=row.getValue("MANUFACTURER_NAME")%></td>
            
            <td width="4%" align = "left" ><%=row.getValue("CONTENT_CODE")%></td>
            <td width="2%" align = "left" ><%=row.getValue("ITEM_QTY")%></td>
            <td width="3%" align = "center" ><%=row.getValue("LIFE_IN_YEARS")%> </td>
            <td width="4%" align = "center" ><%=row.getValue("COST")%></td>
            <td width="4%" align = "right" ><%=row.getValue("START_DATE")%></td>
            
            <td width="5%" align = "left" ><%=row.getValue("OPE_NAME")%></td>
            <td width="5%" align = "left" ><%=row.getValue("NLE_NAME")%></td>
            <td width="3%" align = "left" ><%=row.getValue("CONSTRUCT_STATUS")%></td>
			<td width="5%" align = "left" ><%=row.getValue("LOCATION_SEGMENT1")%></td>
            <td width="10%" align = "left" ><%=row.getValue("LOCATION_SEGMENT2_NAME")%></td>
            
            <td width="5%" align = "center" ><%=row.getValue("LOCATION_SEGMENT2")%></td>
            <td width="2%" align = "center" ><%=row.getValue("LOCATION_SEGMENT3")%></td>
            <td width="4%" align = "center" ><%=row.getValue("EMPLOYEE_NUMBER")%> </td>
            <td width="3%" align = "center" ><%=row.getValue("EMPLOYEE_NAME")%></td>
            <td width="4%" align = "right" ><%=row.getValue("APPLY_TYPE")%></td>
            
            <td width="3%" align = "left" ><%=row.getValue("CONTACT_PERSON")%></td>
            <td width="3%" align = "left" ><%=row.getValue("CONTACT_NUMBER")%></td>
            <td width="4%" align = "left" ><%=row.getValue("EXPECT_ARRIVAL_TIME")%></td>
			<td width="5%" align = "left" ><%=row.getValue("ERROR_MSG")%></td>
           
        	</td>
            <%

                }    }

            %>
    </table>
</div>



</form>
<div id="pageNaviDiv"  style="position:absolute;top:93%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>

<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
<div id="hiddenDiv" style="position: absolute; overflow:scroll;width:550px;height:180px; display:none;background-color:#C6EBF4;color:red"></div>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function initPage(){
		do_SetPageWidth();
	}

 function do_Sub() {
        excelType = "6";
    var returnValue = do_ImportExcelData();
    alert(returnValue);
    if (returnValue) {
        isSave=true;
        mainFrm.excelPath.value = returnValue;
        mainFrm.excel.value = "1";
       	mainFrm.act.value = "ZERO_IMPORT";
        mainFrm.submit();
    }
    }
</script>