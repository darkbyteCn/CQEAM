<%@ page import="com.sino.base.config.ConfigLoader" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<%
    String orderPkName =StrUtil.nullToString(request.getParameter("orderPkName"));
    String orderTable =StrUtil.nullToString(request.getParameter("orderTable"));
    String frmAction =StrUtil.nullToString(request.getParameter("frmAction"));
    RowSet rows = (RowSet) request.getAttribute("UPLOAD_FILES");
    String pageTitle = "";
    boolean canEdit = false;
    if(frmAction.equals("MANAGE")){
        pageTitle = "附件管理";
        canEdit = true;
    } else {
        pageTitle = "附件列表";
    }
    SfUserDTO userDTO = (SfUserDTO)SessionUtil.getUserAccount(request);
    String userId = String.valueOf(userDTO.getUserId());
%>
<html>
<head>
    <title><%=pageTitle%></title>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script> 
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script language="javascript" src="/WebLibary/js/BarVarSX.js"></script>
</head>

<base target="_self">
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();">
<script type="text/javascript">
    var frmAction = "<%=frmAction%>";
    printTitleBar("<%=pageTitle%>");
    if(frmAction == "MANAGE"){
        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
        var ArrAction1 = new Array(true, "上传附件", "action_save.gif", "上传附件", "uploadAttaches");
        var ArrAction2 = new Array(true, "删除附件", "action_cancel.gif", "删除附件", "deleteFile");
        ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
    } else {
        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
        ArrActions = new Array(ArrAction0);
    }
    printToolBar();
</script>
<form name="mainFrm" action="" method="post">
     <input type="hidden" name="orderPkName" value="<%=orderPkName%>">
     <input type="hidden" name="orderTable" value="<%=orderTable%>">
     <input type="hidden" name="frmAction" value="<%=frmAction%>">
     <input type="hidden" name="forward" value="">
<%
    if (rows != null && rows.getSize() > 0) {
%>
    <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:48px;left:0px;width:100%">
        <table class="eamHeaderTable" border="1">
            <tr height="23px">
<%
    if(canEdit){
%>
                <td width="3%" align="center"><input type="checkBox" name="controlBox" onclick="checkAll('controlBox','subBox')"></td>
<%
    }
%>
                <td width="35%" align="center">附件名称</td>
                <td width="62%" align="center">附件说明&nbsp;&nbsp;&nbsp;</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:45px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < rows.getSize(); i++) {
            Row row = rows.getRow(i);
            String fileName = StrUtil.nullToString(row.getValue("FILE_NAME"));
            String description= StrUtil.nullToString(row.getValue("DESCRIPTION"));
            String createdBy = row.getStrValue("CREATED_BY");
            String disableProp = "disabled=\"true\"";
            if(createdBy.equals(userId)){
                disableProp = "";
            }
%>
            <tr height="22" class="dataTR">
<%
            if(canEdit){
%>
                <td width="3%" align="center"><input type="checkbox" name="subBox" value="<%=row.getValue("ORDER_PK_VALUE")%>" <%=disableProp%>></td>
<%
            }
%>
                <td width="35%" onclick="downLoadAttach('<%=row.getValue("ORDER_PK_VALUE")%>')"><font color="blue"><%=fileName%></font></td>
                <td width="62%" onclick="downLoadAttach('<%=row.getValue("ORDER_PK_VALUE")%>')"><font color="blue"><%=description%></font></td>
            </tr>
<%
        }
%>
        </table>
    </div>
<%
    }
%>
</form>
<%
    if(frmAction.equals("MANAGE")){
%>
    <form action="" name="fileForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="orderPkName" value="<%=orderPkName%>">
        <input type="hidden" name="orderTable" value="<%=orderTable%>">
        <input type="hidden" name="frmAction" value="<%=frmAction%>">
        <div id="pageNaviDiv" style="height:160px">
            <table style="width:80%" align="center" >
                <tr height="22px">
                    <td width="100%" align="center" colspan="3"><font color="red" size="2"><%="单个文件不能超过"+ConfigLoader.loadUploadConfig().getSingleSize()/1024+" M"%></font></td>
                </tr>
<%
        for (int i = 0; i < 5; i++) {
%>
                <tr height="22">
                    <td width="15%" align="right">附件说明：</td>
                    <td width="45%" align="left"><input type="text" name="description<%=i%>" id="description<%=i%>" value="" maxlength="250" class="input_style1"></td>
                    <td width="40%" align="center"><span id="pfile<%=i%>"><input type="file" class="input_style1" style="width:100%" name="file<%=i%>" value="" onChange="fileChange(this,this.value,<%=i%>);"></span></td>
                </tr>
<%
        }
%>
            </table>
        </div>
    </form>
<%
    }
%>
</body>
</html>
<iframe name="fileFrm" style="display:none"></iframe>
 
<script type="text/javascript"> 
  var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
  function fileChange(target,value,i) {   
    var fileSize = 0;   
    if (isIE && !target.files  ) {
        if(appInfo().version!='7.0' && appInfo().version!='8.0' && appInfo().version!='9.0'){
			var filePath = target.value;    
			var fileSystem = new ActiveXObject("Scripting.FileSystemObject");       
			var file = fileSystem.GetFile (filePath);    
			fileSize = file.Size;  
		}else{
		    fileSize=1024;
		}
		
    } else {   
     	fileSize = target.files[0].size;    
    }  
     var size = fileSize / 1024;      
       setDescription(value,i);
}
function appInfo(){
    var browser = {
            msie: false, firefox: false, opera: false, safari: false, 
            chrome: false, netscape: false, appname: 'unknown', version: 0
        },
        userAgent = window.navigator.userAgent.toLowerCase();
    if ( /(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test( userAgent ) ){
        browser[RegExp.$1] = true;
        browser.appname = RegExp.$1;
        browser.version = RegExp.$2;
    } else if ( /version\D+(\d[\d.]*).*safari/.test( userAgent ) ){ // safari
        browser.safari = true;
        browser.appname = 'safari';
        browser.version = RegExp.$2;
    }
    return browser;
}
	function testBrowser(){
		// 调用示例
		var myos = appInfo();
		// 如果当前浏览器是IE，弹出浏览器版本,否则弹出当前浏览器名称和版本
		if ( myos.msie ){
		    alert( myos.version );
		} else {
		    alert( myos.appname + myos.version );
		}
	}

    function uploadAttaches() {
    	var tmp = "";
        var desId = "";
        var i = 0;
        for(i = 0; i < 5; i++){
            desId = "description" + i;
            tmp += document.getElementById(desId).value;
        }
    	if (tmp == "") {
    		alert("文件信息不能为空");
    		return;
    	}
        var orderPkName = fileForm.orderPkName.value;
        var orderTable = fileForm.orderTable.value;
        var frmAction = fileForm.frmAction.value;
        fileForm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=UPLOAD_ACTION&orderPkName=" + orderPkName + "&orderTable=" + orderTable + "&frmAction=" + frmAction;
        fileForm.submit();
    }

    function downLoadAttach(orderPkValue) {
        mainFrm.forward.value = "DOWNLOAD_ACTION";
        mainFrm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?orderPkValue=" + orderPkValue;
        mainFrm.target = "fileFrm";
        mainFrm.submit();
    }

    function deleteFile() {
        var checkedCount = getCheckedBoxCount("subBox");
        if(checkedCount == 0){
            alert("未选择待删除的附件。");
            return;
        }
        mainFrm.action = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=DELETE_ACTION";
        fileForm.target = "_self";
        mainFrm.submit();
    }

    function setDescription(fileName,i) {
        var n=fileName.lastIndexOf("\\");
        if (n<0) {
            n=fileName.lastIndexOf("\/");
        }
        if (n>0) {
            fileName=fileName.substring(n+1);
        }
        document.getElementById("description"+i).value = fileName;
    }

    function do_Close(){
        self.close();
    }
</script>