<%--
  Created by IntelliJ IDEA.
  User: yu
  Date: 2007-5-29
  Time: 14:34:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.important.dto.ImpInfoDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
   <title>重要信息发布</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoAttachment.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>

</head>
 <script type="text/javascript">
    printTitleBar("重要信息发布>>增加");
    printToolBar();     
  </script>
<%
    ImpInfoDTO dto = (ImpInfoDTO) request.getAttribute("IMPINFO_DTO");
    if (dto == null) {
        dto = new ImpInfoDTO();
    }
%>
<body onload="do_initPage()">
<form action="" name="imForm" method="post">
    <input type="hidden" name="isExist">
    <input type="hidden" name="forward">
    <input type="hidden" name="publishId" value="">
    <table align="center" style="width:100%" border="0">
        <tr>
            <td width="10%" align="right">发布标题：</td>
            <td width="85%"><input type="text" name="title" class="input_style1" style="width:100%"  value="<%=dto.getTitle()%>"  maxlength="50"/>
            </td>
            <td width="5%"><font style="color: red">*</font></td>
        </tr>
        <tr style="height:320px">
            <td width="10%" height="320px" align="right">发布内容：</td>
            <td width="85%" height="320px"><textarea name="contents" style="width:100%;height:100%" maxlength="500" onkeyup="return isMaxLen(this)"><%=dto.getContents()%></textarea></td>
            <td width="5%" height="320px"><font style="color: red">*</font></td>
        </tr>
    </table>
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
function do_initPage(){
    usedInProcedure = false;
    do_ControlProcedureBtn();
}

function do_Save() {
    if (imForm.title.value.length<1) {
        alert("标题必须输入！");
        return;
    }
    document.imForm.action = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet";
    document.imForm.forward.value = "add_User";
    document.imForm.submit();
}

function isMaxLen(o){
    var nMaxLen=o.getAttribute? parseInt(o.getAttribute("maxlength")):"";
    if(o.getAttribute && o.value.length>nMaxLen){
        o.value=o.value.substring(0,nMaxLen)
    }
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("publishId");
    return attachmentConfig;
}
</script>