<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%--
  Created by IntelliJ IDEA.
  User: V-yuanshuai
  Date: 2007-9-29
  Time: 17:45:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GB2312" language="java" %>
<html>
<head><title>Upload your file</title></head>

<body onload="window.focus()">
<jsp:include page="/message/MessageProcess"/>
<%
//    RequestParser parser = new RequestParser();
//    parser.transData(request);
    String companyId = request.getParameter("companyId");
%>
<form name="mainFrm" enctype="multipart/form-data" method="post">
    上传文件：
    <table>
        <tr>
            <td>文件描述</td>
            <td><input type="text" name="fileDescription" value=""></td>
        </tr>
        <tr>
            <td>文件</td>
            <td><input type=file name=filePath value=""></td>
        </tr>
        <tr>
            <td style="cursor:pointer">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveFile(); return false;">&nbsp;
            </td>
            <td>
                <img src="/images/eam_images/close.jpg" alt="关闭" onClick="do_Cancel(); return false;">
            </td>
        </tr>
    </table>
    <input name="act" type="hidden">
    <input name="companyId" type="hidden" value="<%=companyId%>">
</form>
</body>
</html>
<script type="text/javascript">

    function do_SaveFile() {
        if(document.getElementsByName("fileDescription")[0].value=="" ){
          alert("请输入文件描述！");
          document.getElementsByName("fileDescription")[0].focus;
          return false;
        }
        if(document.getElementsByName("filePath")[0].value==""){
          alert("请选择文件！");
          document.getElementsByName("filePath")[0].focus;
          return false;
        }
        window.close();
        mainFrm.act.value = "<%=WebActionConstant.UPLOAD_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainFilesServlet";
        mainFrm.submit();
    }

    function do_Cancel() {
        window.close();
        opener.location.href = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainCompanyServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&companyId=<%=companyId%>";
    }
</script>