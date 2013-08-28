<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "No-cache");

RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
Row row = null;

String action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsRespMapLocationServlet";
String templateFile = "/document/template/AssetsRespMapLocation.zip";
String resSimpleName = "导入盘点责任人和地点匹配关系";


%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title><%= resSimpleName %></title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" action="<%= action %>" method="post" enctype="multipart/form-data">
<jsp:include page="/message/MessageProcess"/>
    <script type="text/javascript">
        printTitleBar("<%= resSimpleName %>"); 
    </script>
    
    <input type="hidden" name="act">
    <input type="hidden" name="flag" value="0">
    <table border = "0" width="100%">
        <tr>
            <td width="50%">
            <td width="20%">
            <td width="10%">
            <td></td>
        </tr>
        <tr>
            <td width="40%">
            	<input type="file" name="flName" style="width:60%">
            	<input type="button" name="sub" value="提交Excel" onclick="doSub();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td width="20%">
            	<img src="/images/right-3.jpg" width="5" height="9"/>
            	<a href="<%= templateFile %>"  style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2"><%= resSimpleName %>模板</FONT></a>
            </td>
            <td width="10%">
            	<img src="/images/right-3.jpg" width="5" height="9"/>
                <a href="#" onclick="do_export_locations()" style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2">无线地点集合导出</FONT></a></td>
            </td>
            <td>
            	<img src="/images/right-3.jpg" width="5" height="9"/>
                <a href="#" onclick="do_export_checkPersons()" style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2">盘点责任人集合导出</FONT></a></td>
            </td>
        </tr>
    </table>
    <table border="1" width="100%" style="padding:10px; margin:20px;">
    	<tr height="20" >
    		<td style="padding:10px; margin:20px;"><font>注意事项:</font></td>
    	</tr>
    	<tr>
    		<td style="padding:10px; margin:20px;">1. 上传的文件类型为excel,且格式为2003的格式.</td>
    	</tr>
    	<tr>
    		<td style="padding:10px; margin:20px;">2. 上传文件的名称不能包含中文或者特殊字符.</td>
    	</tr>
    </table>
</form>
</body>
<script type="text/javascript">
	function do_export_locations(){
		document.mainFrm.act.value = "EXPORT_LOCATION";
        //document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsRespMapLocationExportServlet";
        //alert(document.mainFrm.action);
        document.mainFrm.submit();
	}

	function do_export_checkPersons(){
		document.mainFrm.act.value = "EXPORT_CHECK_PERSONS";
        //document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsRespMapCheckPersonsExportServlet";
        //alert(document.mainFrm.action);
        document.mainFrm.submit();
	}
	
    function doSub() {
        if (document.mainFrm.flag.value == "1") {
            alert("正在提交数据，请等待......");
            return;
        }
        if (document.mainFrm.flName.value !== "") {
        	document.mainFrm.flag.value="1";
        	document.mainFrm.act.value = "EXCEL";
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            document.mainFrm.submit();
        } else {
            alert("请输入文件！");
        }
    }
</script>
</html>