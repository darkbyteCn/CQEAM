<%@ page import = "com.sino.ams.constant.LookUpConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import = "com.sino.ams.constant.DictConstant" %>
<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%--
  created by ys
  Date: 2007-09-27
  Time: 18:23:30
--%>
<link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
<script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
<script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
<script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
<script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
<script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
<script language = "javascript" src = "/WebLibary/js/calendar.js"></script>
<script language = "javascript" src = "/WebLibary/js/FormValidate.js"></script>
<script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
<script language = "javascript" src = "/WebLibary/js/LookUp.js"></script>
<HTML>
<head>
    <%
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String category = request.getParameter("objectCategory");
        EtsObjectDTO spotDTO = (EtsObjectDTO) request.getAttribute(WebAttrConstant.ETS_OBJECT_DTO);
        String countyCode = (String) request.getAttribute(WebAttrConstant.COUNTY_OPTION);
        String isall = (String) request.getAttribute(WebAttrConstant.CHECK_OPTION);

        if (category.equals(DictConstant.NETADDR_BTS)) {
            out.print("<title>基站专业--地点维护</title>");
        } else if (category.equals(DictConstant.NETADDR_DATA)) {
            out.print("<title>数据专业--地点维护</title>");
        } else if (category.equals(DictConstant.NETADDR_TRANS)) {
            out.print("<title>传输专业--地点维护</title>");
        } else if (category.equals(DictConstant.NETADDR_EXCHG)) {
            out.print("<title>交换专业--地点维护</title>");
        } else if (category.equals(DictConstant.NETADDR_BSC)) {
            out.print("<title>监控专业--地点维护</title>");
        } else if (category.equals(DictConstant.NETADDR_NETOPT)) {
            out.print("<title>网优专业--地点维护</title>");
        } else if (category.equals(DictConstant.NETADDR_ELE)) {
            out.print("<title>电力专业--地点维护</title>");
        }
    %>
</head>

<BODY TEXT = "000000" BGCOLOR = "FFFFFF" leftmargin = 0 topmargin = 0 class = "STYLE1" onload = "window.focus()">
<form name = "form1" method = "post">
    <script type = "text/javascript">
        if (<%=category.equals(DictConstant.NETADDR_BTS)%>) {
            printTitleBar("基站专业--地点信息")
        } else if (<%=category.equals(DictConstant.NETADDR_DATA)%>) {
            printTitleBar("数据专业--地点信息")
        } else if (<%=category.equals(DictConstant.NETADDR_TRANS)%>) {
            printTitleBar("传输专业--地点信息")
        } else if (<%=category.equals(DictConstant.NETADDR_EXCHG)%>) {
            printTitleBar("交换专业--地点信息")
        } else if (<%=category.equals(DictConstant.NETADDR_BSC)%>) {
            printTitleBar("监控专业--地点信息")
        } else if (<%=category.equals(DictConstant.NETADDR_NETOPT)%>) {
            printTitleBar("网优专业--地点信息")
        } else if (<%=category.equals(DictConstant.NETADDR_ELE)%>) {
            printTitleBar("电力专业--地点信息")
        }
    </script>
    <fieldset style = "margin-left:0;height:1350">
        <legend><img src = "/images/eam_images/close.jpg" onClick = "do_close();" alt = "关闭"></legend>
        <table>
            <tr>
                <td width = "8%" align = "right">地点编号：</td>
                <td width = "40%"><input name = "workorderObjectCode" type = "text" id = "workorderObjectCode" value = "<%=spotDTO.getWorkorderObjectCode()%>" readonly class = readonlyInput style = "width:80%"></td>
            </tr>
            <tr>
                <td align = "right">地点简称：</td>
                <td><input name = "workorderObjectName" type = "text" id = "workorderObjectName" value = "<%=spotDTO.getWorkorderObjectName()%>" readonly class = readonlyInput style = "width:80%"></td>
            </tr>

            <tr>
                <td align = "right">所在地点：</td>
                <td><input name = "workorderObjectLocation" type = "text" id = "workorderObjectLocation" value = "<%=spotDTO.getWorkorderObjectLocation()%>" style = "width:80%" readonly class = readonlyInput></td>
            </tr>

            <tr>
                <td align = "right">所属区县：</td>
                <td><select readonly class = readonlyInput name = "countyCode" style = "width:80%"><%=countyCode%></select></td>
            </tr>
            <tr>
                <td align = "right">所属工程：</td>
                <td><input name = "projectName" readonly class = "readonlyInput" type = "text" id = "projectName" value = "<%=spotDTO.getProjectName()%>" style = "width:80%"></td>
            </tr>
            <tr>
                <td align = "right">巡检模式：</td>
                <td><select name = "isall" style = "width:80%" readonly class = readonlyInput><%=isall%></select></td>
            </tr>
            <tr>
                <td align = "right">备注：</td>
                <td><textarea name = "remark" readonly class = readonlyInput type = "areatext" id = "remark" value = "<%=spotDTO.getRemark()%>" style = "width:80%"><%=spotDTO.getRemark()%></textarea></td>
            </tr>
        </table>
    </fieldset>
</form>
</BODY>
</HTML>
<script type = "text/javascript">
    function do_close() {
        window.close();
    }
</SCRIPT>