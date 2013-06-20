<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-5-24
  Time: 19:04:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ include file="/newasset/headerInclude.jsp"%>

<%
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "No-cache");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Excel通服资产导入</title>
    <script type="text/javascript">
        var ArrAction0 = new Array(true, "提交", "action_save.gif", "上传", "doSub");
        var ArrAction1 = new Array(true, "粘贴", "action_sign.gif", "粘贴", "aa");
        var ArrActions = new Array(ArrAction0, ArrAction1);
        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0>
<form name="mainFrm" action="/servlet/com.sino.ams.system.object.servlet.ImportVillageAssetsServletJt?act=UPLOAD_ACTION" method="post" enctype="multipart/form-data">
    <input type="hidden" name="flag" value="0">
    <input type="hidden" name="act">
    <script type="text/javascript">
        printTitleBar("Excel通服资产导入")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table border = "0" width="100%">
        <tr>
            <td width="80%">
            <td width="20%">
            <td></td>
        </tr>
        <tr>
            <td width="80%">
            <input type="file" name="flName" class="select_style1" style="width:45%"><input type="button" name="sub" value="提交Excel" onclick="doSub();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img src="/images/right-3.jpg" width="5" height="9"/>
            <a href="/document/template/VillageImportingJt.zip"  style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2">Excel集团村通资产导入模版</FONT></a><td width="20%">
            <td></td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    function doSub() {
    	var excelName = document.mainFrm.flName.value;
    	if (excelName.indexOf('.xls') == -1) {
    		alert("请确保您导入的文件格式为xls！");
    		return;
    	}
        if (document.mainFrm.flag.value == "1") {
            alert("正在提交数据，请等待......");
            return;
        }
        if (document.mainFrm.flName.value !== "") {
            document.mainFrm.flag.value="1";
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            mainFrm.submit();
        } else {
            alert("请输入文件！");
        }
    }
</script>
</html>