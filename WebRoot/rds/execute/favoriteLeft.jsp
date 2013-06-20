<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.rds.share.form.FavoriteHeaderFrm" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<html>
<head>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <title>收藏夹列表</title>
</head>
<body bottomMargin="0" leftMargin="0" topMargin="0" rightMargin="0" onload="do_initPage()">
<html:form action="/rds/favoriteLeft">
    <script type="text/javascript">
        printTitleBar("收藏夹列表");
    </script>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="headerId" styleId="headerId"/>
</html:form>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:20px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center">
                <td width="8%"></td>
                <td width="72%">收藏夹名称</td>
                <td width="20%">创建日期</td>
            </tr>
        </table>
    </div>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>

    <div id="dataDiv" style="overflow:scroll;height:50%;width:100%;position:absolute;top:44px;left:1px" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" class="gridTable" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            FavoriteHeaderFrm frm = (FavoriteHeaderFrm)dtos.getDTO(i);
%>
            <tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" class="dataTR">
                <td width="8%" align="center"><input type="radio" name="subRadio" value="<%=frm.getHeaderId()%>" onclick="do_ProcessRightPage(this)"> </td>
                <td width="72%"><input type="text" readonly class="tableInput1" value="<%=frm.getFavoriteName()%>"></td>
                <td width="20%"><input type="text" readonly class="tableInput2" value="<%=frm.getCreationDate()%>"></td>
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

function do_initPage(){
    do_ProcessRightPage(null);
    do_SetPageWidth();
}

function do_ProcessRightPage(obj){
    var headerId = "";
    if(obj){
        headerId = obj.value;
    }
    var rightFrm = parent.frames["rightFrm"];
    rightFrm.location.href = "<%=contextPath%>/rds/favoriteRight.do?act=QUERY_ACTION&headerId=" + headerId;
}

function do_DeleteFavorite(){
    var headerId = getRadioValue("subRadio");
    if(headerId == ""){
        alert("没有选中待删除的收藏夹，不能继续！");
    } else {
        if(confirm("确定要删除选中的收藏夹吗？")){
            document.getElementById("act").value = "DELETE_ACTION";
            document.getElementById("headerId").value = headerId;
            document.forms[0].submit();
        }
    }
}
</script>