<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.rds.share.form.ModelFieldFrm" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>模型字段维护</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/rds/WebLibary/css/rds.css">
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/Constant.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/CommonUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/SinoToolBar.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/AppStandard.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/ActionButton.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<html:form action="/rds/modelFieldSearch" method="post">
     <script type="text/javascript">
        var title = "模型字段维护";
        printTitleBar(title);
        ArrActions[0][0] = true;
        ArrActions[1] = new Array(true, "编辑", "edit.gif", "编辑", "do_Create");
        printToolBar();
    </script>
    <table  border="0" width="100%" id="table1" class="queryHeadBg">
        <tr style="height:22px">
            <td width="10%" align="right">模型名称：</td>
            <td width="23%"><html:select property="modelId" style="width:100%"><bean:write name="modelFieldFrm" property="modelOptions" filter="false"/></html:select></td>
            <td width="10%" align="right">字段名称：</td>
            <td width="23%"><html:text property="fieldName" styleId="fieldName" style="width:100%"/> </td>
            <td width="10%" align="right">是否有效：</td>
            <td width="23%"><bean:write name="modelFieldFrm" property="enabledRadio" filter="false"/></td>
        </tr>
    </table>
    <html:hidden property="act"/>
    </html:form>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:73px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center">
                <td width="20%">模型名称</td>
                <td width="15%">字段名称</td>
                <td width="15%">字段描述</td>
                <td width="10%">字段类型</td>
                <td width="10%">模型类型</td>
                <td width="10%">所属Oracle用户</td>
                <td width="10%">是否有效</td>
                <td width="10%">创建日期</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:98px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            ModelFieldFrm frm = (ModelFieldFrm)dtos.getDTO(i);
%>
            <tr id="fieldId=<%=frm.getFieldId()%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" class="dataTR">
                <td width="20%"><%=frm.getModelName()%></td>
                <td width="15%"><%=frm.getFieldName()%></td>
                <td width="15%"><%=frm.getFieldDesc()%></td>
                <td width="10%"><%=frm.getFieldType()%></td>
                <td width="10%"><%=frm.getDataSrcTypeName()%></td>
                <td width="10%"><%=frm.getOwner()%></td>
                <td width="10%" align="center"><%=frm.getEnabledName()%></td>
                <td width="10%" align="center"><%=frm.getCreationDate()%></td>
            </tr>
<%
        }
%>
        </table>
       </div>
<%
    }
%>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<div id="hiddenDiv" style="position: absolute; width: 450px;display:none;background-color:#CCFFCC;"></div>
</body>
</html>
<script type="text/javascript">
function configCreatePage() {
    var pageConfig = new DataPageConfig();
    pageConfig.setActionURL("/rds/modelFieldAction.do");
    pageConfig.setAct("DETAIL_ACTION");
    pageConfig.setWidthPercent(0.7);
    pageConfig.setHeightPercent(0.7);
    pageConfig.setWindowName("fieldData");
    return pageConfig;
}


function configDetailPage() {
    var pageConfig = configCreatePage();
    pageConfig.setActionURL("/rds/modelFieldSearch.do");
    pageConfig.setAct("DETAIL_ACTION");
    var obj = event.srcElement;
    while(obj.tagName != "TR"){
        obj = obj.parentNode;
    }
    pageConfig.setParameters(obj.id);
    return pageConfig;
}
</script>
