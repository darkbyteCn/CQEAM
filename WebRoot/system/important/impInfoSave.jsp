<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-7-9
  Time: 17:33:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.important.dto.ImpInfoDTO" %>
<html>
  <head><title>修改重要信息</title>
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
    printTitleBar("修改重要信息");
    printToolBar();
</script>
  <body onload="do_initPage()">
  <%
        ImpInfoDTO dto = (ImpInfoDTO) request.getAttribute("IMPINFO_DTO");
        String seeUserType = (String) request.getAttribute("SEE_USER_TYPE");
        String docType = (String) request.getAttribute("IMP_TYPES");
        if (dto == null) {
            dto = new ImpInfoDTO();
        }
    %>
  <form action="" name="impForm" method="post">
      <input type="hidden" name="publishId" value="<%=dto.getPublishId() %>">
      <input type="hidden" name="isExist">
    <input type="hidden" name="forward">
      <table align="center" style="width:100%"  border="0">
      	<tr>
            <td width="10%" align="right">是否失效:&nbsp;</td>
            <td width="85%">
                <input type="radio" name="disabled" id="title3" value="Y" <%=dto.getDisabled().equals("Y")? "checked":""%>>失效
                <input type="radio" id="title4" name="disabled" value="N" <%=(dto.getDisabled().equals("N")||dto.getDisabled().equals(""))? "checked":""%>>生效
            </td>
              <td width="5%"></td>
      	</tr>
      	<tr>
            <td width="10%" align="right" >发布标题：</td>
            <td width="85%"><input type="text" name="title" style="width:100%"  value="<%=dto.getTitle()%>"  maxlength="50"></td>
            <td width="5%"><font style="color: red">*</font></td>
        </tr>
        <tr style="height:320px">
            <td width="10%" style="height:320px" align="right">发布内容：</td>
            <td width="85%" style="height:320px"><textarea style="width:100%;height:100%" name="contents" onkeyup="return isMaxLen(this)"><%=dto.getContents()%></textarea></td>
            <td width="5%" style="height:320px"><font style="color: red">*</font></td>
        </tr>
      </table>
  </form>
  </body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script type="text/javascript">
    function do_Save() {
        if (impForm.title.value.length<1) {
            alert("标题必须输入！");
            return;
        }
        document.impForm.action = "/servlet/com.sino.ams.important.servlet.ImpInfoServlet";
        document.impForm.forward.value = "save";
        document.impForm.submit();
    }

	function isMaxLen(o){
 		var nMaxLen=o.getAttribute? parseInt(o.getAttribute("maxlength")):"";
 		if(o.getAttribute && o.value.length>nMaxLen){
 			o.value=o.value.substring(0,nMaxLen)
 		}
	}

    function do_initPage(){
        usedInProcedure = false;
        do_ControlProcedureBtn();
    }

    function setAttachmentConfig(){
        var attachmentConfig = new AttachmentConfig();
        attachmentConfig.setOrderPkName("publishId");
        return attachmentConfig;
    }

</script>
</html>
