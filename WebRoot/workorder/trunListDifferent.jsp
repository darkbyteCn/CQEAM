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

        out.print("<title>转资清单差异</title>");
    %>

</head>

<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();helpInit('4.4.3');">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.TrunListDifferentServlet">
<script type="text/javascript">
      printTitleBar("转资清单差异");
</script>

<table width="100%"border=0 class="queryTable" cellpadding="2" cellspacing="0"
        style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="num" value="">
    <input type="hidden" name="workorderType" value="<%=DictConstant.ORDER_TYPE_HDV%>">

    <tr>
        <td align="right" width = "10%">项目名称：</td>
        <td width = "25%">
        	<input name="prjName" type="text" id="prjName" value="<%=dto.getPrjName()%>" class="input_style1"  onClick="choosePrj()" title = "点击选择项目"  readonly style="width:100%">
        	<input name = "prjId" type = "hidden" value = "<%=dto.getPrjId() %>">
        </td>
        <td align="right" width="10%">工单号：</td>
        <td width="20%">
        	<input style="width:100%" class="input_style1" type="text" name="workorderBatch" value="<%=dto.getWorkorderNo()%>">
        </td>
        <td align="right" widht = "10">执行人：</td>
        <td width = "25%"><input style="width:90%" type="text" name="executeUserName" readonly class="input_style1"  onclick="showUser()" title="点击选择执行人"  value="<%=dto.getExecuteUserName()%>"></td>
    </tr>
    <tr>
        <td align="right">地点编号：</td>
        <td>
        	<input style="width:100%" type="text" readonly class="input_style1" name="workorderObjectCode" onclick="showLocation()" title="点击选择地点"  value="<%=dto.getWorkorderObjectCode() %>">
        </td>
        <td align="right">公司：</td>
        <td>
        	<select style="width:100%" class="select_style1"  name="organizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%> </select> 
        </td>
        <td align="right">责任部门</td>
        <td>
        	<input style="width:90%" type="text" readonly class="input_style1" name="deptName" value="<%=dto.getDeptName() %>" onclick="chooseDeptInfo();" title="">
        	<input style="width:90%" type="hidden" readonly class="input_style1" name="deptCode" value="<%=dto.getDeptCode() %>" >
        </td>
    </tr>
    <tr>
        <td align = "right" colspan = "6">
        	<img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_SearchOrder(); return false;">&nbsp;&nbsp;&nbsp;
        	<img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">&nbsp;&nbsp;&nbsp;
        	<img src="/images/eam_images/batch_update.jpg" onclick="do_UpdateBatch();" alt="批量修改">&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
</table>

<div id="headDiv" style="overflow:hidden;position:absolute;top:100px;left:0px;width:320%">
	<table class="headerTable" border="1" width="320%">
		<tr height=22px >
			<td align=center width="1%">
				<input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')">
			</td>
			<td width="3%" align="center">项目编号</td>
            <td width="7%" align="center">项目名称</td>
            <td width="4%" align="center">公司</td>
            <td width="5%" align="center">地点编号</td>
            <td width="12%" align="center">地点简称</td>         
            <td width="6%" align="center">差异原因</td>
            <td width="4%" align="center">工单号</td>      
            
            <td width="3%" align="center">执行人</td>      
            <td width="3%" align="center">归档人</td>      
            
            <td width="3%" align="center">标签号</td>
            <td width="4%" align="center">资产名称</td>
            <td width="6%" align="center">规格型号</td>
            <td width="3%" align="center">资产当前状态</td>
            <td width="2%" align="center">扫描状态</td>
            <td width="2%" align="center">数量</td>
            <td width="3%" align="center">资产类别</td>            
            <td width="10%" align="center">责任部门</td>
            <td width="3%" align="center">责任人</td>
            <td width="11%" align="center">差异处理原因</td>
		</tr>
	</table>
</div>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	int num = 0;
    if (hasData) {
%>
<div id="dataDiv" style="overflow:scroll;height:64%;width:320%;position:absolute;top:125px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="320%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:keep-all">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
<%--        <tr class="dataTR" height="22" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;">--%>
		<tr class="dataTR" height="22" >
			<td width="1%" align="center">
				<input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="">
			</td>
            <td width="3%" align = "center"><%=row.getValue("SEGMENT1")%></td>
            <td width="7%" align = "center"><%=row.getValue("NAME")%></td>
            <td width="4%" align = "center"><%=row.getValue("ORG_NAME")%></td>
            <td width="5%" align = "center"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
            <td width="12%" align = "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            <td width="6%" align = "center"><%=row.getValue("DIFFERENCE_REASON")%></td>
            <td width="4%" align = "center"><%=row.getValue("WORKORDER_NO")%></td>
            
            <td width="3%" align = "center"><%=row.getValue("IMPLEMENT_USER")%></td>            
            <td width="3%" align = "center"><%=row.getValue("CHECKOVER_USER")%></td>
            
            <td width="3%" align = "center"><input type="text" name = "barcode<%=num %>" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BARCODE")%>"></td>
            <td width="4%" align = "center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="6%" align = "center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="3%" align = "center"><%=row.getValue("ITEM_STATUS_NAME")%></td>
            <td width="2%" align = "center"><%=row.getValue("SCAN_STATUS_NAME")%></td>
            <td width="2%" align = "center"><%=row.getValue("ITEM_QTY")%></td>
            <td width="3%" align = "center"><%=row.getValue("ITEM_CATEGORY_DESC")%></td>
            <td width="10%" align = "center"><%=row.getValue("DEPT_NAME")%></td>
            <td width="3%" align = "center"><%=row.getValue("USER_NAME")%></td>
            <td width="11%" align = "center"><input type="text" id="diffProcessDesc<%=num %>" name = "diffProcessDesc<%=num %>" style="width:100%; border: 1px solid #FFFFFF;text-align: center" class = "noEmptyInput"></td>

            <%
            	num++; 
            }    
	}

            %>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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

    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>";
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        url = "/public/wait.jsp?title=工单详细信息&src="+url+"&act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        window.open(url, "", winstyle);
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
    	var userPara = "organizationId=" + document.getElementById("organizationId").value ;
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
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
    function showUser() {
    	var userPara = "organizationId=" + document.getElementById("organizationId").value ;
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight, userPara);
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
	
	function do_UpdateBatch(){
		var result = 0;
		var diffProcessDesc = "";
		var subChecked = false;
		for(var i = 0; i < <%=num %>; i++){
			diffProcessDesc = document.getElementById("diffProcessDesc" + i).value;
			subChecked = document.getElementById("subCheck" + i).checked;
			if(diffProcessDesc != null && diffProcessDesc != "" && subChecked){
				result++;
			}
		}
		if(result < 1){
			alert("您未修改任何数据，或未选择任何数据");
        	return;
		}
		if(confirm("是否确定批量修改差异处理原因？继续请点击“确定”，否则请点击“取消”按钮")){
			for(var i = 0; i < <%=num %>; i++){
				subChecked = document.getElementById("subCheck" + i).checked;
				if (subChecked != true) { 
					document.getElementById("diffProcessDesc" + i).value = "";
		    	}
			}
		}
   		mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION %>";
   		mainFrm.num.value = <%=num %>;
   		mainFrm.submit();
	}
	
	function changeOu() {
		document.getElementById("deptCode").value = "";
		document.getElementById("deptName").value = "";
		document.getElementById("workorderObjectCode").value = "";
		document.getElementById("workorderObjectName").value = "";
	}
	
	function chooseDeptInfo() {
		var userPara = "organizationId=" + document.getElementById("organizationId").value ;
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU2%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var dept = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	    if(dept){
	        dto2Frm(dept[0], "mainFrm");
	        mainFrm.deptName.title = mainFrm.deptName.value;
	    } else {
	    	mainFrm.deptName.value = "";
	    	mainFrm.deptCode.value = "";
	    }
	}
	
</script>