<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-9-29
  Time: 17:45:42
  To change this template use File | Settings | File Templates.
--%>
<html>
<base target="_self">
<head><title>上传租赁设备附件</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<%
    String barcode = request.getParameter("barcode");
    String type = request.getParameter("type");
%>
<script type="text/javascript">
    printTitleBar("上传租赁设备附件(大小不能超过1M)")
</script>
<body onload="window.focus()" leftmargin="0" topmargin="0">

<form name="mainFrm" enctype="multipart/form-data" method="post">
    <jsp:include page="/message/MessageProcess"/>
    <table>
        <tr>
            <td align="right">文件描述：</td>
            <td><input type="text" name="fileDesc"></td>
        </tr>
        <tr>
            <td align="right">文件：</td>
            <td><input type=file name=filePath></td>
        </tr>
        <tr>
            <td style="cursor:pointer">
                
            </td>
            <td><img src="/images/button/upload.gif" alt="上传" onClick="do_SaveFile(); return false;">&nbsp;
                <img src="/images/button/clearUnitPrice.gif" alt="取消" onClick="do_Cancel(); return false;">
            </td>
        </tr>
    </table>
    <input name="act" type="hidden">
    <input name= "type" type = "hidden" value = "<%=type%>">
    <input name="barcode" type="hidden" value="<%=barcode%>">
</form>
</body>
</html>
<script type="text/javascript">

function do_SaveFile() {
    mainFrm.act.value = "<%=WebActionConstant.UPLOAD_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.AmsItemFilesServlet";
    mainFrm.submit();
}


function do_Cancel() {
    window.close();
    var type = document.mainFrm.type.value;
    if (type == "HOUSE") {
        opener.location.href = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=<%=barcode%>";
    } else {
        opener.location.href = "/servlet/com.sino.ams.system.house.servlet.AmsLandInfoServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=<%=barcode%>"
    }
}
</script>