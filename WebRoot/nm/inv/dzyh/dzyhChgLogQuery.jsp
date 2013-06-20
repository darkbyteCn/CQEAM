<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/inv/headerInclude.jsp"%>
<%@ include file="/inv/headerInclude.htm"%>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.inv.dzyh.dto.EamDhBillLDTO" %>

<html>
<head>
    <title>低值易耗品出库－物资领用清单查询</title>
    <style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>

<body leftmargin = "0" rightmargin = "0" topmargin = "0">
 
<% 
 
    RequestParser parser = new RequestParser(); 
    parser.transData(request); 
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW); 
    boolean hasData = (rows != null && !rows.isEmpty()); 
    Row row = null; 
	EamDhBillLDTO dto = (EamDhBillLDTO)request.getAttribute(QueryConstant.QUERY_DTO); //针对Servlet里的dto.setXXX()方法写的   
%>
<form method="post" name="mainFrm">
    <script type = "text/javascript">
        printTitleBar("低值易耗品出库－物资领用清单查询")
    </script>

	<iframe width = 174 height = 189 name = "gToday:normal:calendar.js" id = "gToday:normal:calendar.js"
        src = "/WebLibary/js/DateHTML.htm" scrolling = "no" frameborder = "0"
        style = "visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
	</iframe>

    <table width = "100%" border = "0" class = "queryHeadBg">
        <tr>
        	<td width = "10%" align = "right">目录编号：</td>
            <td width = "20%">
            <input type="text" name="catalogValueId" value="<%=dto.getCatalogValueId()%>"  size="16" readonly="readonly" style="width:70%" class="readonlyInput">
            <a href="#" title="点击选择目录编号" class="linka" onclick="do_SelectCatalogValueId();">[…]</a>
            </td>
            <td width = "10%" align = "right">条&nbsp;&nbsp;码：</td>
            <td width = "20%">
            <input type="text" name="barcode" style="width:70%" value="<%=dto.getWorkorderObjectName() %>" size="13"><a href="#" title="点击选择条码" class="linka" onclick="do_SelectBarcode();">[…]</a>
			</td>
            <td width = "10%" align = "right">领用日期：</td>
            <td width = "20%">
            <input type = "text" readonly = "true" class = "readonlyInput" name = "minTime" size = "25" style = "width:70%" onclick = "gfPop.fPopCalendar(minTime)">
            <img src = "/images/calendar.gif" alt = "点击选择日期" onclick = "gfPop.fPopCalendar(minTime)">
            </td>
            <td width = "10%" align = "right">名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
            <td width = "20%">
            <input type="text" name="itemName" style = "width:100%" value="<%=dto.getItemName()%>" size="13">
            </td>
         </tr>
         <tr>
            <td width = "10%" align = "right">领用部门：</td>
            <td width = "20%">
            	<input type="text" name="deptName" style="width:70%" value="<%=dto.getDeptName()%>" size="16" readonly="readonly" class="readonlyInput">
            	<a href="#" title="点击选择领用部门" class="linka" onclick="do_SelectDept();">[…]</a>
            	<input type="hidden" name="deptCode" value="">
            </td>
            <td width = "8%" align = "right">领用人：</td>
            <td width = "16%">
            	<input type="text" name="userName" style="width:70%" value="<%=dto.getUserName()%>" size="13" readonly="readonly" class="readonlyInput"><a href="#" title="点击选择领用人" class="linka" onclick="do_SelectUser();">[…]</a>
            	<input type="hidden" name="employeeId" value="">
            </td>
            <td align = "right">到：</td>
            <td width="18%" align = "left">
            <input type = "text" readonly = "true" class = "readonlyInput" name = "maxTime" size = "25" value = "" style = "width:70%" onclick = "gfPop.fPopCalendar(maxTime)">
            <img src = "/images/calendar.gif" alt = "点击选择日期" onclick = "gfPop.fPopCalendar(maxTime)">
            </td>
            
            <td width = "10%" align = "right">规格型号：</td>
            <td width = "22%">
            <input type="text" name="itemSpec" value="<%=dto.getItemSpec()%>" size="14"><a href="#" title="点击选择规格型号" class="linka" onclick="do_SelectItemSpec();">[…]</a>
            </td>       
        </tr>
        <tr>
            <td width = "10%" align = "right">
                <img src = "/images/button/query.gif" style = "cursor:'hand'" onclick = "do_search();" alt = "查询">
                
            </td>
            <td width = "10%" align = "left">
                <img src="/images/button/toExcel.gif" id="queryImg"
							style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
            </td>
        </tr>
    </table>
    
    <div style = "/*overflow-x:scroll;width:100%*/">
		
        <div style="overflow-y:scroll;overflow-x:scroll;left:0px;width:100%;height:390px">
        <!--  
        <script type="text/javascript">
        	var columnArr = new Array("目录编号","条码号", "品名", "规格型号", "数量", "单价","使用部门","领用人","地点","保管人","领用日期","厂家","备注");
        	var widthArr = new Array("6%", "10%", "8%", "8%", "5%","5%","8%","8%","8%","8%","8%","10%","12%");
        	printTableHead(columnArr, widthArr);
    	</script>
    	-->
			<table width="160%" border="1" bordercolor="#666666">
			    
			   <tr height = "20" class = "headerTable">
                    <td width = "6%" align = "center">目录编号</td>
                    <td width = "10%" align = "center">条码号</td>
                    <td width = "8%" align = "center">品名</td>
                    <td width = "8%" align = "center">规格型号</td>
                    <td width = "5%" align = "center">数量</td>
                    <td width = "5%" align = "center">单价</td>
                    <td width = "8%" align = "center">使用部门</td>
                    <td width = "8%" align = "center">领用人</td>
                    <td width = "8%" align = "center">地点</td>
                    <td width = "8%" align = "center">保管人</td>
                    <td width = "8%" align = "center">领用日期</td>
                    <td width = "10%" align = "center">厂家</td>
                    <td width = "12%" align = "center">备注</td>
               </tr>
               
<% 
	if(hasData) {
		for(int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%> 
				<tr height="20" style="cursor: 'hand'" onmousemove="style.backgroundColor='#EFEFEF'" onmouseout="style.backgroundColor='#ffffff'">			
					<td width="6%" align="right"><%=row.getValue("ITEM_CATEGORY2")%></td>
					<td width="10%" align="left"><%=row.getValue("BARCODE")%></td>
					<td width="8%" align="left"><%=row.getValue("ITEM_NAME")%></td>
					<td width="8%" align="left"><%=row.getValue("ITEM_SPEC") %></td>
					<td width="5%" align="right"><%=row.getValue("ITEM_QTY")%></td>
					<td width="5%" align="right"><%=row.getValue("PRICE")%></td>
					<td width="8%" align="left"><input type="text" value="<%=row.getValue("DEPT_NAME")%>" readonly="readonly" class="finput"></td>
					<td width="8%" align="center"><%=row.getValue("USER_NAME")%></td>
					<td width="8%" align="center"><input type="text" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>" readonly="readonly" class="finput"></td>
					<td width="8%" align="center"><input type="text" value="<%=row.getValue("MAINTAIN_USER") %>" readonly="readonly" class="finput"></td>
					<td width="8%" align="center"><%=row.getValue("LAST_LOC_CHG_DATE") %></td>
					<td width="10%" align="left"><input type="text" value="<%=row.getValue("ATTRIBUTE3")%>" readonly="readonly" class="finput"></td>
					<td width="12%" align="left"><input type="text" value="<%=row.getValue("REMARK")%>" readonly="readonly" class="finput"></td>
				</tr>
<% 
		}
	}
%>
			</table>
		</div>
		
		</div>

		<input name="act" type="hidden">
		<!--  
		<input type="hidden" name="responsibilityDept" value="">
		<input type="hidden" name="responsibilityUser" value="">
		<input type="hidden" name="workorderObjectNo">
		-->
	</form>
<%
	if(hasData){
%>
	<div style="position:absolute;top:470px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}	
%>
	<%=WebConstant.WAIT_TIP_MSG%>
	<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"></jsp:include>
</body>
</html>
<script type = "text/javascript">
function do_search() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.inv.dzyh.servlet.EamDhChgLogServlet";
	mainFrm.submit();
}

//导出Excel表格
function do_exportToExcel() {
   mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
   mainFrm.action = "<%=URLDefineList.DZYH_DH_CHG_LOG_SERVLET%>";
   mainFrm.submit();
}

//选择目录编号
function do_SelectCatalogValueId() {
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_CATALOG_VALUE_ID%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].catalogValueId.value = vendorNames[0].catalogValueId;
   	}
}

//选择条码
function do_SelectBarcode() {
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpConstant.BJSL_ITEM_INFO%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].barcode.value = vendorNames[0].barcode;
   	}
}

//选择领用部门
function do_SelectDept() {
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_RESPONSIBILITY_DEPT%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].deptCode.value = vendorNames[0].deptCode;
       	document.forms[0].deptName.value = vendorNames[0].deptName;
   	}
}

//选择领用人
function do_SelectUser() {
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_RESPONSIBILITY_USER%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].employeeId.value = vendorNames[0].employeeId;
       	document.forms[0].userName.value = vendorNames[0].userName;	
   	}
}

//选择规格型号
function do_SelectItemSpec() { 
	var url="/servlet/com.sino.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_SYS_ITEM_DZYH%>";
   	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
   	var vendorNames = window.showModalDialog(url, null, popscript);
   	if(vendorNames){
       	var vendorName = null;
       	document.forms[0].itemSpec.value = vendorNames[0].itemSpec;
   	}
}
</script>