<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>选中的报表</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/rds/WebLibary/css/rds.css">
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/Constant.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/CommonUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/SinoToolBar.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/CheckboxProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/table/TableProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/AppStandard.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/ActionButton.js"></script>
</head>
<body bottomMargin="0" leftMargin="0" topMargin="0" rightMargin="0" onload="do_initPage()">
<html:form action="/rds/favoriteRight">
    <script type="text/javascript">
        printTitleBar("待收藏的报表");
        ArrActions[4][0] = true;
        ArrActions[15][0] = true;
        printToolBar();
    </script>
    <table border="0" width="100%" id="table1" class="queryHeadBg">
        <tr>
            <td width="15%" align="right">已有收藏夹：</td>
            <td width="35%">
                <html:select property="headerId" styleId="headerId" style="width:100%" onchange="do_Search()">
                    <bean:write name="favoriteFrm" property="myFavoriteOptions"/>
                </html:select>
            </td>
            <td width="15%" align="right">新增收藏夹：</td>
            <td width="35%"><html:text property="favoriteName" styleId="favoriteName" styleClass="fInput1"/> </td>
        </tr>
    </table>
    <html:hidden property="act" styleId="act"/>
</html:form>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:71px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center">
                <td width="5%"><input type="checkbox" name="controlChk" onPropertyChange="checkAll(this.name, 'subCheck')"></td>
                <td width="40%">报表代码</td>
                <td width="55%">报表名称</td>
                <td style="display:none">隐藏域字段</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:95px;left:1px" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <%=StrUtil.nullToString(request.getAttribute("TABLE_REPORT"))%>
    </div>
<div id="pageNaviDiv"></div>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    do_SetPageWidth();
}
</script>