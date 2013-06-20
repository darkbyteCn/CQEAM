<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newSite.dto.EamAddressAddHDTO" %>
<html>
<head>
	<title>资产物理地点维护单据查询</title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    EamAddressAddHDTO dto = (EamAddressAddHDTO)parser.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<body onkeydown="autoExeFunction('do_search()');" onload="initPage();">
	<%=WebConstant.WAIT_TIP_MSG%>
	<form action="/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet" name="mainFrm" method="post">
		<input type="hidden" name="act" value="">
		<script type="text/javascript">
		    printTitleBar("资产物理地点维护单据查询")
		</script>
		<table border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        	<tr>
	            <td width="8%" align="right">单据号：</td>
	            <td width="12%"><input class="input_style1" type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
	            <td width="8%" align="right">创建日期：</td>
	            <td width="19%">
					<input class="input_style1" type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:48%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
					<input class="input_style1" type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:48%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
            	</td>
	            <td width="25%" align="right">
					<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
					<img src="/images/eam_images/export.jpg" title="点击导出" onclick="do_ExportOrder();">
				</td>
 			</tr>
    	</table>
	</form>
	<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:49px;left:0px;width:100%">
   	  <table border="1" width="100%" class="eamHeaderTable">
        <tr height="23px">
            <td align=center width="18%">地点维护单号</td>
            <td align=center width="8%">单据状态</td>
            <td align=center width="12%">地点维护公司</td>
            <td align=center width="22%">地点维护部门</td>
            <td align=center width="8%">申请人</td>
            <td align=center width="10%">创建日期</td>
        </tr>
      </table>
    </div>
    <div style="overflow:scroll;height:72%;width:100%;position:absolute;top:71px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && !rows.isEmpty()) {
                for (int i = 0; i < rows.getSize(); i++) {
                    Row row=rows.getRow(i);
            %>
            
            <tr class="dataTR" onclick="showDetail('<%=row.getValue("TRANS_ID")%>')">
                <td width="18%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
                <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("TRANS_STATUS_DESC")%>"></td>
                <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("ORGANIZATION_NAME")%>"></td>
                <td width="22%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
                <td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("CREATED_BY_NAME")%>"></td>
                <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
    <div id="pageNaviDiv" style="position:absolute;top:87%;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
	function initPage(){
		do_SetPageWidth();
	}
	
	function do_SearchOrder() {
	    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	    mainFrm.submit();
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	}
	
	function showDetail(transId){
	   	var url = "/servlet/com.sino.ams.newSite.servlet.EamAddressSecondAddServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&transId="+transId;
	    var style = "width="+screen.width+",height="+screen.height+",top=0,left=0,toolbar =no, menubar=no, scrollbars=no, resizable=yes, location=no, status=yes"
	    window.open(url, 'transferWin', style);
	}
	
	function do_ExportOrder(){
		mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
		mainFrm.submit();
	}
</script>