<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
%>
<%--
  User: 李轶
  Date: 2009-05-25
  Time: 18:22:55
  JSP Name :		责任人与部门差异查询
--%>
<html>
<head>
	<title>责任人与部门差异查询</title>
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



    String orgOption = (String) request.getAttribute(AssetsWebAttributes.CITY_OPTION);
    String deptOption = (String) request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS);
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute(QueryConstant.QUERY_DTO);

%>

<body onkeydown="autoExeFunction('do_search()');" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.object.servlet.AssetsNoMatchResponsibleServlet" name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("<%= allResName %>");
    </script>
<table border="0" width="100%" class="queryTable">
        <tr>
            <td  width="9%" align="right">公司名称：</td>
            <%--<td width="17%" ><select style="width:90%"  name="organizationId" onclick = "getDeptOpt();"><%=orgOption%></select></td>--%>
            <td width="17%"><select class="select_style1" style="width:100%" name="organizationId" onchange="getDeptOpt();"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select></td>
            <td width="7%" align="right">责任人：</td>
            <td width="15%" align="left"><input class="input_style1" type="text" name="userName" style="width:80%" value="<%=dto.getUserName()%>"></td>
            <td width="9%" align="right">资产名称：</td>
            <td width="16%" align="left"><input class="input_style1" type="text" name="itemName" style="width:90%" value="<%=dto.getItemName()%>"></td>
            <td width="9%" align="right">目录名称：</td>
			<td width="23%">
	            <input class="input_style2" type="text" name="contentName" value="<%=dto.getContentName()%>" class="readonlyInput" title="点击选择目录" onclick="do_SelectContent(); return false;" readonly style="width:100%;cursor:hand" >
	            <input type= "hidden" name = "contentCode"  value = "<%=dto.getContentCode()%>">
	        </td>
        </tr>
        <tr>
            <td width="9%" align="right">责任部门：</td>
            <%--<td width="29%" align="left" colspan = "3"><select style="width:92.5%"  name="responsibilityDept" ><%=deptOption%></select></td>--%>
            <td width="29%" align="left" colspan = "3"><div id="responsibilityDept1"><select class="select_style1" style="width:92.5%" name="responsibilityDept"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select></div></td>
            <td width="7%" align="right">地点名称：</td>
            <td width="24%" align="left" colspan = "2">
            	<input type = "hidden" name = "addressId" value = "<%=dto.getAddressId() %>">
            	<input class="input_style2" type="text" name="workorderObjectName" style="width:100%;cursor:hand" value="<%=dto.getWorkorderObjectName()%>" class="readonlyInput" readonly onclick="lookAddressId();">
            </td>
            <td width="23%" align="right" valign = "bottom">
            	<img align="bottom" src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_search(); return false;">&nbsp;&nbsp;&nbsp;
            	<img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export();">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
</form>
     <div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:74px;left:1px;width:100%" class="crystalScroll">
   	  <table  border="1" width="220%" class="eamHeaderTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="3%" align="center">公司名称</td>
            <td width="8%" align="center">责任部门</td>
            <td width="4%" align="center">标签号</td>
            <td width="4%" align="center">资产名称</td>
            <td width="3%" align="center">资产型号</td>
            <td width="3%" align="center">责任人</td>
            <td width="8%" align="center">责任人所属部门</td>
            <td width="5%" align="center">资产目录代码</td>
            <td width="10%" align="center">资产目录名称</td>
            <td width="4%" align="center">地点编号</td>
            <td width="14%" align="center">地点名称</td>
        </tr>
      </table>
    </div>

	<div style="overflow:scroll;height:70%;width:100%;position:absolute;top:98px;left:1px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table width="220%" border="1" bordercolor="#666666" id="dataTab" >
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        	    boolean hasData = (rows != null && !rows.isEmpty());
                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>

            <tr class="dataTR" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="3%" align= "center"><%=row.getValue("COMPANY")%></td>
                <td width="8%" align= "center"><%=row.getValue("RESPONSIBILITY_DEPT")%></td>
                <td width="4%" align= "center"><%=row.getValue("BARCODE")%></td>
                <td width="4%" align= "center"><%=row.getValue("ITEM_NAME")%></td>
                <td width="3%" align= "center"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="3%" align= "center"><%=row.getValue("USER_NAME")%></td>
                <td width="8%" align= "center"><%=row.getValue("DEPT_NAME")%></td>

                <td width="5%" align= "center"><%=row.getValue("CONTENT_CODE")%></td>
                <td width="10%" align= "center"><%=row.getValue("CONTENT_NAME")%></td>
                <td width="4%" align= "center"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
                <td width="14%" align= "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>


<%
    if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:92%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<%--<iframe name="downFrm" src="" style="display:none"></iframe>--%>

<script type="text/javascript">
	function initPage(){
		do_SetPageWidth();
	}

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }


	var clickNum = 0;
    function do_export() {
    	clickNum++;
    	if(clickNum > 1){
    		alert("已执行导出命令，请您不要重复点击！");
    		return;
    	}
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
        alert("已执行导出命令，请耐心等待！");
    }


    function lookAddressId() {    //查找地点
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (projects) {
            var user = null;
            for (var i = 0; i < projects.length; i++) {
            	mainFrm.addressId.value = projects[0].addressId;
                mainFrm.workorderObjectName.value = projects[0].workorderObjectName;
            }
        } else {
        	mainFrm.addressId.value = "";
            mainFrm.workorderObjectName.value = "";
        }
    }

    function do_SelectContent() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
            mainFrm.contentName.value = "";
            mainFrm.contentCode.value = "";
        }
	}

	var xmlHttp;

    //-- checkObjectCode
    function getDeptOpt() {
        var url = "";
        var organizationId = document.getElementById("organizationId").value;
        createXMLHttpRequest();
        if (organizationId != "") {
            url = "/servlet/com.sino.ams.newasset.servlet.ItemCostEasyServlet?act=GET_DEPT_OPTION&organizationId=" + organizationId;
            xmlHttp.onreadystatechange = handleReadyStateChange1;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
    }
    function createXMLHttpRequest() {     //创建XMLHttpRequest对象
        try {
            xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
            } catch(e) {
                try {
                    xmlHttp = new XMLHttpRequest();
                } catch(e) {
                    alert("创建XMLHttpRequest对象失败！");
                }
            }
        }
    }
    function handleReadyStateChange1() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                unescape(xmlHttp.responseText);
                var cf = document.getElementById("responsibilityDept1");
                cf.innerHTML = "<select name = responsibilityDept style=\"width:100%\">" + xmlHttp.responseText + "</select>";

            } else {
                alert(xmlHttp.status);
            }
        }
    }
</script>