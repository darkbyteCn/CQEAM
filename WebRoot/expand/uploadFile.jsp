<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: srf
  Date: 2008-3-13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>附件处理</title>
   <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
   <script language="javascript" src="/WebLibary/js/ajax.js"></script>
   <script language="javascript" src="/WebLibary/js/jslib.js"></script>
   <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
</head>
<base target="_self">
<%
    RowSet rs = (RowSet) request.getAttribute("FILE_ROW_SET");
%>
<body leftmargin="1" topmargin="1">
<script type="text/javascript">
    printTitleBar("附件信息")
</script>
    
<form action="/servlet/com.sino.ams.newasset.servlet.UploadFileServlet" method="post" name="mainForm">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transId" value="<%=request.getParameter("transId")%>">
    <input type="hidden" name="docType" value="UPLOAD">
    <table class=headerTable style="position:absolute;top:0px;left:1px;width:600px;">
         <tr class="headerTable" >>>双击附件名称下载附件</tr>
        <tr>
            <td width=10% algin=center><input type=checkbox name=mainCheck onclick="checkAll('mainCheck','subCheck')">
            </td>
            <td width=30% algin=center>附件名称</td>
            <td width=30% algin=center>附件描述</td>
            <td width=30% algin=center>附件类型</td>
        </tr>
    </table>
    <div style="position:absolute;top:47px;left:1px;width:617px;overflow-y:scroll;height:200px">
        <table style="width:100%">
            <%
                if (rs != null && rs.getSize() > 0) {
                    for (int i = 0; i < rs.getSize(); i++) {
                        Row row = rs.getRow(i);
                        String desc = StrUtil.nullToString(row.getValue("FILE_DESC"));
                        String name = StrUtil.nullToString(row.getValue("FILE_PATH"));
                        if (desc.equals("")) {
                            int index = name.lastIndexOf(".");
                            desc = name.substring(0, index);
                        }

            %>
            <tr>
                <td width=10%><input type="checkbox" name="subCheck" value="<%=row.getValue("SYSTEM_ID")%>"></td>
                <td width=30%><a class=linka onclick="downLoad(<%=row.getValue("SYSTEM_ID")%>)"
                        ><%=name%></a></td>
                <td width=30%><input type = "text" name = "FILE_DESC" class="input2" readonly="readonly" value = "<%=desc%>"></td>
                <td width=30%><%=row.getValue("FILE_DESC")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<form action="" name="fileForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="barcode" value="">
    <input type="hidden" name="transStatus" value="<%=request.getParameter("transStatus")%>" >
    <input type="hidden" name="transId" value="<%=request.getParameter("transId")%>">
    <input type="hidden" name="docType" value="UPLOAD">
    <%
    String transStatus=request.getParameter("transStatus");
        if(transStatus.equals("")||transStatus.equals("SAVE_TEMP")){


    %>
    <table style="position:absolute;left:1px;top:250px;width:600px;">
        <%
            for (int i = 0; i < 3; i++) {
        %>
        <tr>
            <td class=noborder><input type="file" style="width:80%" name="file<%=i%>" value=""></td>
        </tr>
        <%
            }
        %>
        <tr>
            <td class=noborder>
                <input type=button value="上传" class=button2 onclick="upload()">
                <input type=button value="删除" class=button2 onclick="deleteFile()">
                <input type=button value="关闭" class=button2 onclick="window.close()">
            </td>
        </tr>
    </table> 
    <%}%>
</form>
<%--<jsp:include page="/public/wait.jsp" flush="true"/>--%>
<script type="text/javascript">
    var docType = "";

    function upload() {
//      alert(mainForm.transId.value)
        var transId=mainForm.transId.value;
        fileForm.barcode.value=transId;
        var transStatus=fileForm.transStatus.value;
        document.fileForm.action = "/servlet/com.sino.ams.expand.servlet.UploadFileServlet?docType="+docType+"&transId="+transId+"&transStatus="+transStatus;
        document.fileForm.submit();
    }
    function deleteFile() {
     	var  act = "DELETE_ACTION";
        document.mainForm.action = "/servlet/com.sino.ams.expand.servlet.FileServlet?act="+act;

        mainForm.target = "fileFrm";
        document.mainForm.submit();
    }
    function downLoad(fileId) {
        var act = "<%=WebActionConstant.DOWNLOAD_ACTION%>";
        mainForm.action = "/servlet/com.sino.ams.expand.servlet.FileServlet?fileId=" + fileId + "&act=" + act;
        mainForm.target = "fileFrm";
        document.mainForm.submit();
    }
    /*function changeDesc(fileId, columnId){
        var description = document.getElementById("FILE_DESC" + columnId).value;
        var paras = "&description=" + description +  "&fileId=" + fileId;
        requestAjax("UPDATE_FILE_DESC", file_desc, null, paras);
//        mainForm.action = "/servlet/com.sino.eas.servlet.FileServlet?fileId=" + fileId + "&process=" + process + "&desc=" + description;
    }
    function file_desc() {
    }*/
</script>
</body>

<iframe name="fileFrm" style="display:none"></iframe>
</html>