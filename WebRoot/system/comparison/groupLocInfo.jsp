<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-2-21
  Time: 15:31:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.comparison.dto.EtsObjectCatGroupDTO" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>组别地点对照</title>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<body leftmargin="1" topmargin="0">
<%
    String groupId = (String) request.getAttribute(WebAttrConstant.GROUP_OPTION);
    EtsObjectCatGroupDTO resource = (EtsObjectCatGroupDTO) request.getAttribute(WebAttrConstant.OBJECT_CATEGORY_DTO);
%>
<script type="text/javascript">
    printTitleBar("组别地点对照")
</script>
<form name="mainFrm" action="/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCatGroupServlet" method="post">
<table border="0" width="100%" id="table1">

        <tr>
            <td width="25%" height="22" align="right"></td>
            <td width="50%" height="22" align="center">
              <font color="" size="2"><a>组别：</a></font>
              <select name="groupId" class="select_style1" style="width:60%"  onchange="do_show();"><%=groupId%></select>
            </td>
            <td width="25%" height="22" align="right">
            </td>
        </tr>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
        <td width="40%" align="center"><span lang="zh-cn">可选专业地点</span></td>
        <td width="20%" align="center">操作</td>
        <td width="40%" align="center">已选专业地点</td>
    </tr>
</table>
<hr size="1" color="#3366EE">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr height="250px">
        <td width="40%">
            <select name="allCategory" style="width:100%;height:100%" multiple size="10" ondblclick="do_Add()" title="双击选择该地点">
                <%=request.getAttribute(WebAttrConstant.LEFT_CATEGORY_OPTION)%>
            </select>
        </td>
        <td width="20%" align="center">
            <img src="/images/eam_images/new.jpg" onClick="do_Add();" alt="添加">

            <p>
                <img src="/images/eam_images/add_all.jpg" onClick="do_AddAll();" alt="添加全部">

            <p>
                <img src="/images/eam_images/delete.jpg" onClick="do_Remove()" alt="删除">

            <p>
                <img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAll()" alt="删除所有">
        </td>
        <td width="40%">
            <select name="codes" style="width:100%;height:100%" multiple size="10" ondblclick="do_Remove()" title="双击移出该地点">
                <%=request.getAttribute(WebAttrConstant.RIGHT_CATEGORY_OPTION)%>
            </select>
        </td>
    </tr>
</table>
<table border="0" width="100%">
    <tr>
        <td width="25%" height="22" align="right"></td>
        <td width="50%" height="22" align="center"><img src="/images/eam_images/save.jpg" alt="保存" onClick="do_Save(); return false;">
            &nbsp;<img src="/images/eam_images/back.jpg" alt="返回" onClick="do_back(); return false;"></td>
        <td width="25%" height="22" align="right"></td>
    </tr>
</table>
<input type="hidden" name="act">
<input type="hidden" name="systemid" value="<%= resource.getSystemid()  %>">
</form>
</body>
</html>
<script>
    function do_Add() {
        moveSelectedOption("allCategory", "codes");
    }

    function do_AddAll() {
        moveAllOption("allCategory", "codes");
    }

    function do_Remove() {
        moveSelectedOption("codes", "allCategory");
    }

    function do_RemoveAll() {
        moveAllOption("codes", "allCategory");
    }

    function do_Save() {
        selectAll("codes");
        if (mainFrm.codes.value == "") {
            alert("至少应当选择一个地点才能保存");
            return;
        }
        if(mainFrm.groupId.value==""){
            alert("请选择组别信息！");
            return;
        }
        //    if (mainFrm.systemid.value == "") {
        //        alert("1111");
                <%--mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";--%>
        //        mainFrm.submit();
        //    } else {
        mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
        mainFrm.submit();
        //    }
    }

    function do_back() {
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCatGroupServlet";
        document.mainFrm.submit();
    }

    function do_show() {
        mainFrm.act.value = "<%=WebActionConstant.CHECK_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCatGroupServlet";
        document.mainFrm.submit();
    }
</script>