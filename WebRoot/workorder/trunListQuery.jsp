<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%--
  Function:		转资清单查询
--%>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        EtsWorkorderDTO dto = (EtsWorkorderDTO)reqParser.getAttribute(QueryConstant.QUERY_DTO);
        String action = reqParser.getParameter("act");
        String deptOption = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
        out.print("<title>转资清单查询</title>");
        int ou = Integer.parseInt("0");
    %>

</head>

<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();helpInit('4.4.4');">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>

<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.TrunListQueryServlet">
<script type="text/javascript">
      printTitleBar("转资清单查询");
</script>

<table width="100%" border=0 class="queryTable" cellpadding="2" cellspacing="0"
        style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <input type="hidden" name="act" value="<%=action%>">
    <tr>
    	<td align="right" width="8%">公司：</td>
        <td width="20%"><select style="width:60%" class="select_style1" name="organizationId" onchange="changeOu();"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%> </select> </td>
        <td width="7%" align="right">责任部门：</td>
        <%-- <td width="22%" width="8%"><select style="width:100%" class="select_style1" name="deptCode"><%=deptOption%></select></td> --%>
        <td width="22%">
        	<input style="width:100%" type="text" readonly class="input_style1" name="deptName" value="<%=dto.getDeptName() %>"  onclick="chooseDeptInfo();" title="点击选择责任部门" >
        	<input style="width:100%" type="hidden" readonly class="input_style1" name="deptCode" value="<%=dto.getDeptCode() %>" >
        </td>
        <td width="8%" align="right" >资产状态：</td>
        <td width="20%" >
        	<select style="width:100%" class="select_style1" name="financeProp" ><%=request.getAttribute("FA_OPTION")%></select>
        </td>
        <td align="right" width="8%"></td>
       
        <%-- 
        <td width="20%">
            <input style="width:100%" class="input_style1" type="text" name="responsibilityUser" value="<%=dto.getResponsibilityUser()%>">
        </td>
        <td align="right" width="20%">责任部门：</td>
        <td width="30%">
        	<input style="width:100%" type="text" readonly class="input_style2" name="responsibilityDept" value="<%=dto.getResponsibilityDept() %>"  onclick="showResponsibilityDept()" title="点击选择责任部门" >
        </td>
        --%>
    </tr>
    <tr>
    	<td align="right" width="10%">项目名称：</td>
        <td width="30%" >
        	<input name="prjName" type="text" id="prjName" value="<%=dto.getPrjName()%>" class="input_style1" onClick="choosePrj()" title="点击选择项目" readonly style="width:60%">
        	<input name="prjId" type="hidden" value="<%=dto.getPrjId() %>">
        </td>
        <td align="right" width = "10%">地点名称：</td>
        <td width="30%"><input style="width:100%" type="text" readonly class="input_style1" name="workorderObjectName" value="<%=dto.getWorkorderObjectName() %>" onclick="showLocation()" title="点击选择地点" ></td>
        <input type="hidden" name="workorderObjectCode" value="<%=dto.getWorkorderObjectCode() %>">
        <td align="right" colspan="3">
        	<img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_SearchOrder(); return false;">&nbsp;&nbsp;&nbsp;
        	<img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
        	<img src="/images/eam_images/confirm.jpg" onclick="do_confirm()" alt="确认">
        </td>
        
    </tr>
</table>

<div id="headDiv" style="overflow:hidden;position:absolute;top:72px;left:0px;width:250%">
	<table class="headerTable" border="1" width="250%">
		<tr height=22px >
			<td width="5%" align="center">地点编号</td>
            <td width="10%" align="center">地点名称</td>  
			<td width="3%" align="center">项目编号</td>
            <td width="6%" align="center">项目名称</td>
            <td width="4%" align="center">公司</td>
                   
            <td width="4%" align="center">标签号</td>
            <td width="4%" align="center">资产名称</td>
            <td width="4%" align="center">规格型号</td>            
            <td width="2%" align="center">数量</td>
            <td width="2%" align="center">计量单位</td> 
            <td width="3%" align="center">资产类别</td>    
            
            <td width="4%" align="center">资产目录编码</td>
            <td width="9%" align="center">资产目录名称</td>   
                    
            <td width="10%" align="center">责任部门</td>
            <td width="2%" align="center">责任人</td>
            <td width="6%" align="center">使用部门</td>
            <td width="2%" align="center">使用人</td>
            <td width="3%" align="center">创建日期</td>
            <td width="3%" align="center">最近修改日期</td>
		</tr>
	</table>
</div>

<div id="dataDiv" style="overflow:scroll;height:70%;width:250%;position:absolute;top:95px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:keep-all">
        <%
        	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    		boolean hasData = (rows != null && !rows.isEmpty());
        	if (hasData) {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" height="22">
			<td width="5%" align = "center"><%=row.getValue("ASSETS_LOCATION_CODE")%></td>
            <td width="10%" align = "left"><%=row.getValue("ASSETS_LOCATION")%></td>
            <td width="3%" align = "left"><%=row.getValue("SEGMENT1")%></td>
            <td width="6%" align = "left"><%=row.getValue("NAME")%></td>
            <td width="4%" align = "left"><%=row.getValue("ORG_NAME")%></td>
            
            <td width="4%" align = "center"><%=row.getValue("BARCODE")%></td>
            <td width="4%" align = "left"><%=row.getValue("ITEM_NAME")%></td>
            <td width="4%" align = "left"><%=row.getValue("ITEM_SPEC")%></td>
             <td width="2%" align = "right"><%=row.getStrValue("ITEM_QTY").trim()%></td>
             <td width="2%" align = "left"><%=row.getValue("UNIT_OF_MEASURE")%></td>           
            <td width="3%" align = "left"><%=row.getValue("ITEM_CATEGORY_DESC")%></td>
            
            <td width="4%" align = "left"><%=row.getValue("CONTENT_CODE")%></td>
            <td width="9%" align = "left"><%=row.getValue("CONTENT_NAME")%></td>  
            
            <td width="10%" align = "left"><%=row.getValue("DEPT_NAME")%></td>
            <td width="2%" align = "left"><%=row.getValue("USER_NAME")%></td>
			<td width="6%" align = "left"><%=row.getValue("MAINTAIN_DEPT")%></td>
            <td width="2%" align = "left"><%=row.getValue("MAINTAIN_USER")%></td>
            <td width="3%" align = "center"><%=row.getValue("CREATION_DATE") != "" ? row.getValue("CREATION_DATE").toString().substring(0,10) : ""%></td>
            <td width="3%" align = "center"><%=row.getValue("LAST_UPDATE_DATE") != "" ? row.getValue("LAST_UPDATE_DATE").toString().substring(0,10) : "" %></td>
            <%

                }    }

            %>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div id="pageNaviDiv"  style="position:absolute;top:93%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	
	function do_confirm() {
		  var url = "/workorder/trunListAddressQuery.jsp";
    dialogWidth = "1000";
    dialogHeight = "500";
    var popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:yes;help:no;resizable:yes";
    return window.showModalDialog(url, null, popscript);
	}

    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit()
    }
	var expNum = 0;
	function do_Export() {
		if(expNum > 0){
			alert("已执行导出命令，请不要重复点击！");
			return;
		}
		expNum++;
	    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
		mainFrm.target = "_self";
	    mainFrm.submit();
	    
	    openExportMsgDiv();
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
        	mainFrm.workorderObjectName.value = "";
        }
    }
    function showUser() {
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
	    } else {
	    	mainFrm.deptName.value = "";
	    	mainFrm.deptCode.value = "";
	    }
	}
	
	var xmlHttp;
    function getDeptOpt() {
        var organizationId = document.getElementById("organizationId").value ;
        var url = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=CHOOSE_GROUP&organizationId=" + organizationId;
        xmlHttp = GetXmlHttpObject(setDeptOpt);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("a=1");
    }

    function setDeptOpt() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            if (xmlHttp.status == 200) {//成功
                var resText = xmlHttp.responseText;
                var resArray = resText.parseJSON();
                if (resArray == "ERROR") {
                    alert(resText);
                } else {
                    var littleCategoryObj = document.getElementById("deptCode");
                    littleCategoryObj.length = 0;
                    var emptyOption = new Option("--请选择--", "");
                    littleCategoryObj.add(emptyOption)
                    if (resArray.length > 0) {
                        var retStr;
                        for (var i = 0; i < resArray.length; i++) {
                            retStr = resArray[i];
                            var arr = retStr.split("$");
                            var option = new Option(arr[1], arr[0]);
                            littleCategoryObj.add(option)
                        }
                    }
                }
                xmlHttp = null;
            }
        }
    }
</script>