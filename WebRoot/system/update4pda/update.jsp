<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.system.update4pda.dto.EtsAutoupdateDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: V-yuanshuai
  Date: 2008-6-24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <title>PDA版本维护</title></head>
    
<%
    EtsAutoupdateDTO dto=(EtsAutoupdateDTO)request.getAttribute("PDA_MODULE");
    if(dto==null){
        dto=new EtsAutoupdateDTO();
    }
%>

<body onload="window.focus();isUpload()">
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<form name="mainFrm" enctype="multipart/form-data" method="post">
    <script type="text/javascript">
        printTitleBar("PDA版本维护")
    </script>

    <table align=center style="width: 90%">
    	<!-- 
        <tr align="center" style="width: 70%">
            <td align="right" width="40%">文件名:</td>
            <td align="left" width="50%">&nbsp;
              <select name="Module" class="select_style1" style="width: 60%" onchange="do_GetVersion();">
                <option value="" selected>--请选择--</option>
                <option value="ETSMain.dat">ETSMain.dat</option>
                <option value="WorkOrder.dat">WorkOrder.dat</option>
                <option value="ETSMain_XP.dat">ETSMain_XP.dat</option>
                <option value="WorkOrder_XP.dat">WorkOrder_XP.dat</option>
              </select>&nbsp;<font style="color: red">*</font>
            </td>
        </tr>
        <tr align="center" style="width: 70%">
            <td align="right" width="40%">版&nbsp;  本:</td>
            <td align="left" width="50%">&nbsp;
            <input type="text" name="version"  class="input_style1" style="width: 60%">&nbsp;<font style="color: red">*</font></td>
        </tr>
         -->
        
        <tr align="center" style="width: 70%">
            <td align="right" width="40%">模块名称:</td>
            <td align="left" width="50%">&nbsp;
              <input type="text" name="module" class="input_style2" style="width: 60%" value="<%=dto.getModule()%>" />
            </td>
        </tr>
        <tr align="center" style="width: 70%">
            <td align="right" width="40%">版&nbsp;  本:</td>
            <td align="left" width="50%">&nbsp;
            <input type="text" name="version"  class="input_style1" style="width: 60%" value="<%=dto.getVersion()%>">&nbsp;<font style="color: red">*</font></td>
        </tr>
        
        <tr align="center" style="width: 70%">
            <td align="right" width="40%">请选择:</td>
            <td align="left" width="50%">&nbsp;
            <input type=file name=filePath class="input_style1" style="width: 60%">&nbsp;<font style="color: red">*</font></td>
            <td style="cursor:pointer"></td>
        </tr>
        <tr align="center" style="width: 70%">
            <td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveFile(); return false;">
              <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_back(); return false;">
            </td>
        </tr>
    </table>
    <input name="act" type="hidden">

</form>
</body>
</html>
<script type="text/javascript">
    function isUpload() {
        if ("Y" == "<%=request.getParameter("flag")%>")alert("上传成功");
    }

    function do_SaveFile() {
    	//var module = document.mainFrm.Module.value;
        //mainFrm.act.value = "<%=WebActionConstant.UPLOAD_ACTION%>";
        //mainFrm.action = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet?module=" + module;
        //mainFrm.submit();
        if (document.mainFrm.filePath.value!="") {
            document.mainFrm.act.value = "<%=WebActionConstant.UPLOAD_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet";
            document.mainFrm.submit();
        } else {
            alert("请选择文件");
        }
    }
            
    var xmlHttp;
    function do_GetVersion() {
        var url = "";
        var segment1 = document.mainFrm.Module.value;
        createXMLHttpRequest();
        if (segment1) {
            url = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet?act=getVersion&module=" + segment1;
            xmlHttp.onreadystatechange = handleReadyStateChange1;
            xmlHttp.open("post", url, true);
            xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xmlHttp.send(null);
        }
    }

    function createXMLHttpRequest() {     //创建XMLHttpRequest对象
        try {
            xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
            } catch(e) {
                try {
                    xmlHttp = new XMLHttpRequest();
                } catch(e) {
                    alert("创建XMLHttpRequest对象失败！");
                }
            }
        }
    }

    function handleReadyStateChange1() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                var version = xmlHttp.responseText ;

                document.mainFrm.version.value = version;
            } else {
                alert(xmlHttp.status);
            }
        }
    }
    
    function do_back() {
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet";
        document.mainFrm.submit();
    }
    
</script>