<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.form.DBConnectionFrm" %>

<html>
<head>
    <title>已定义数据源</title>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<html:form action="/rds/dbConnAction" method="post">
    <html:hidden property="act"/>
    </html:form>
<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:0px;left:1px;width:100%">
    <table id="headTable" border="1" width="100%">
        <tr class="headerTR" style="text-align:center">
            <td width="12%">数据源</td>
            <td width="29%">驱动程序</td>
            <td width="35%">连接URL</td>
            <td width="12%">用户名</td>
            <td width="12%">用户密码</td>
        </tr>
    </table>
</div>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:46px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            DBConnectionFrm frm = (DBConnectionFrm)dtos.getDTO(i);
%>
            <tr style="height:23px;cursor:pointer" onclick="do_ViewLovDetail(this)" id="<%=frm.getConnectionId()%>">
                <td width="12%"><input type="text" class="tableInput1" readonly value="<%=frm.getConnectionName()%>"></td>
                <td width="29%"><input type="text" class="tableInput1" readonly value="<%=frm.getDbDriver()%>"></td>
                <td width="35%"><input type="text" class="tableInput1" readonly value="<%=frm.getDbUrl()%>"></td>
                <td width="12%"><input type="text" class="tableInput1" readonly value="<%=frm.getDbUser()%>"></td>
                <td width="12%"><input type="text" class="tableInput1" readonly value="<%=frm.getDbPwd()%>"></td>
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
</body>
</html>
<script type="text/javascript">
function do_ViewLovDetail(obj){
    var actionURL = "/rds/dbConnAction.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&connectionId=" + obj.id;
    window.parent.frames["connectionDataFrm"].location = actionURL;
}
</script>
