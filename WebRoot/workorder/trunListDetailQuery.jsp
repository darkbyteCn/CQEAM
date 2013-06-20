<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        EtsWorkorderDTO dto = (EtsWorkorderDTO)reqParser.getAttribute(QueryConstant.QUERY_DTO);
        String action = reqParser.getParameter("act");

        out.print("<title>交接工单明细查询</title>");
    %>

</head>

<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();helpInit('4.4.7');">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.TrunListDetailQueryServlet">
<script type="text/javascript">
      printTitleBar("交接工单明细查询");
</script>

<table width="100%" border=0 class="queryTable" cellpadding="2" cellspacing="0"
        style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="num" value="">
    <input type="hidden" name="workorderType" value="<%=DictConstant.ORDER_TYPE_HDV%>">

    <tr>
        <td align="right" width = "10%">项目名称：</td>
        <td width = "15%">
        	<input name="prjName" type="text" id="prjName" value="<%=dto.getPrjName()%>" class='input_style1'  onClick="choosePrj()" title = "点击选择项目"  readonly style="width:100%">
        	<input name = "prjId" type = "hidden" value = "<%=dto.getPrjId() %>">
        </td>
        <td align="right" width="10%">工单批号：</td>
        <td width="15%"><input style="width:100%" type="text" class='input_style1' name="workorderBatch" value="<%=dto.getWorkorderBatch()%>">
        </td>
        <td align="right" width = "10%">执行人：</td>
        <td width = "15%"><input style="width:100%" type="text" name="executeUserName" readonly class='input_style1'  onclick="showExecuteUser()" title="点击选择执行人"  value="<%=dto.getExecuteUserName()%>"></td>
        <td align="right" width = "10%">工单状态：</td>
        <td width = "15%"><select style="width:100%" class="select_style1"    name="workorderFlag"><%=request.getAttribute(WebAttrConstant.PLAN_STATUS_OPTION)%> </select></td>
    </tr>
    <tr>
        <td align="right">地点编号：</td>
        <td><input style="width:100%" type="text" readonly class='input_style1' name="workorderObjectCode" onclick="showLocation()" title="点击选择地点"  value="<%=dto.getWorkorderObjectCode() %>"></td>
        <td align="right">公司：</td>
        <td><select style="width:100%" class="select_style1"    name="organizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%> </select> </td>
		<td align="right">责任部门：</td>
		<td><input style="width:84%" type="text" name="deptName" readonly class='input_style1' value="<%=dto.getDeptName() %>"><a href="" onclick="do_SelectDept(); return false;" title="点击选择责任部门">[…]</a>
		   	<input type="hidden" name="deptCode" value="<%=dto.getDeptCode() %>">
		</td>
		<td align="right">责任人：</td>
		<td><input style="width:100%" type="text" name="userName" readonly class='input_style1' value="<%=dto.getUserName() %>" onclick="showUserName(); return false;" title="点击选择责任人">
			<input type="hidden" name="employeeNumber" value="<%=dto.getEmployeeNumber() %>">
		</td>
	</tr>
	<tr>
		<td align="right">资产标签：</td>
        <td><input style="width:100%" type="text" class='input_style1' name="barcode"  value="<%=dto.getBarcode() %>"></td>
        <td align="right">资产名称：</td>
        <td><input style="width:100%" type="text" class='input_style1' name="itemName"  value="<%=dto.getItemName() %>"></td>
        <td align="right">规格型号：</td>
        <td><input style="width:100%" type="text" class='input_style1' name="itemSpec"  value="<%=dto.getItemSpec() %>"></td>
        <td align = "right" colspan = "2">
        	<img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_SearchOrder(); return false;">&nbsp;&nbsp;&nbsp;
        	<img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
        </td>
    </tr>
</table>

<div id="headDiv" style="overflow:hidden;position:absolute;top:100px;left:0px;width:320%">
	<table class="headerTable" border="1" width="261%">
		<tr height=22px >
			<td width="3%" align="center">项目编号</td>
            <td width="7%" align="center">项目名称</td>
            <td width="4%" align="center">公司</td>
            <td width="5%" align="center">地点编号</td>
            <td width="12%" align="center">地点简称</td>         
     
            <td width="4%" align="center">工单号</td>      
            
            <td width="3%" align="center">执行人</td>      
            <td width="3%" align="center">归档人</td>      
            
            <td width="3%" align="center">标签号</td>
            <td width="4%" align="center">资产名称</td>
            <td width="6%" align="center">规格型号</td>
            <td width="3%" align="center">资产当前状态</td>
            <td width="3%" align="center">资产类别</td>            
            <td width="10%" align="center">责任部门</td>
            <td width="3%" align="center">责任人</td>
            <td width="3%" align="center">工单状态</td>
            
		</tr>
	</table>
</div>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	int num = 0;
    if (hasData) {
%>
<div id="dataDiv" style="overflow:scroll;height:69%;width:320%;position:absolute;top:122px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="261%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:keep-all">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
<%--        <tr class="dataTR" height="22" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;">--%>
		<tr class="dataTR" height="22" >
            <td width="3%" align = "center"><%=row.getValue("SEGMENT1")%></td>
            <td width="7%" align = "center"><%=row.getValue("NAME")%></td>
            <td width="4%" align = "center"><%=row.getValue("ORG_NAME")%></td>
            <td width="5%" align = "center"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
            <td width="12%" align = "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
    
            <td width="4%" align = "center"><%=row.getValue("WORKORDER_NO")%></td>
            
            <td width="3%" align = "center"><%=row.getValue("IMPLEMENT_USER")%></td>            
            <td width="3%" align = "center"><%=row.getValue("CHECKOVER_USER")%></td>
            
            <td width="3%" align = "center"><input type="text" name = "barcode<%=num %>" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BARCODE")%>"></td>
            <td width="4%" align = "center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="6%" align = "center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="3%" align = "center"><%=row.getValue("ITEM_STATUS_NAME")%></td>
            <td width="3%" align = "center"><%=row.getValue("ITEM_CATEGORY_DESC")%></td>
            <td width="10%" align = "center"><%=row.getValue("DEPT_NAME")%></td>
            <td width="3%" align = "center"><%=row.getValue("USER_NAME")%></td>
            <td width="3%" align = "center"><%=row.getValue("WORKORDER_FLAG_DESC")%></td>
            
            <%
               num++; }    }

            %>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:95%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">
	function initPage(){
		do_SetPageWidth();
	}
	
	function do_SelectDept(){
    	var lookUpName = "LOOK_UP_DEPT_TRUNLIST";
	    var dialogWidth = 48;
	    var dialogHeight = 30;
	    var userParameters = "multipleChose=true";
	    var returnValues = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userParameters);
	    var content = new Object();
	    content["deptCode"] = "";
	    content["deptName"] = "";
	    content["costCenterName"] = "";
	    if (returnValues) {
	        var valueCount = returnValues.length;
	        for (var i = 0; i < valueCount; i++) {
	            var returnValue = returnValues[i];
	            //content["deptCode"] += "'" + returnValue["deptCode"] + "'";
	            content["deptCode"] += "" + returnValue["deptCode"] + "";
	            content["deptName"] += "" + returnValue["deptName"] + "";
	            content["costCenterName"] += "" + returnValue["costCenterName"] + "";
	            if(i < valueCount - 1){
	                content["deptCode"] += ", ";
	                content["deptName"] += ", ";
	                content["costCenterName"] += ", ";
	            }
	        }
	    }
	    dto2Frm(content, "mainFrm");
	}
	
    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
	
	function do_Export() {
	    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
		mainFrm.target = "_self";
	    mainFrm.submit();
	}

    function doChecked() {
           var transType = document.getElementById("objectCategory")   ;
        dropSpecialOption(transType, '80');
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchOrder();
        }
    }
    function showLocation() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
            }
        } else {
        	mainFrm.workorderObjectCode.value = "";
        }
    }
    function showExecuteUser() {
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
        	mainFrm.executeUserName.value = "";
        }
    }
	function showUserName() {
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER_NAME%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
        	mainFrm.userName.value = "";
        	mainFrm.employeeNumber.value = "";
        }
    }
	function choosePrj() {
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	    if(projects){
	        dto2Frm(projects[0], "mainFrm");
	    } else {
	    	mainFrm.prjId.value = "";
	    	mainFrm.prjName.value = "";
	    }
	}
	

</script>