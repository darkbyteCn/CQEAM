<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2008-3-17
  Time: 2:12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.system.project.dto.EtsPaProjectsAllDTO" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.calen.SimpleDate" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<
<html>
<head>
    <title>事务处理状态</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("事务处理状态")
</script>
<%
    EamSyschronizeDTO dto = (EamSyschronizeDTO) request.getAttribute(WebAttrConstant.SYSCHRONIZE_DTO);
    String projectType = (String) request.getAttribute(WebAttrConstant.PROJECT_TYPE_OPTION);

%>

<body leftmargin="0">
<jsp:include page="/message/MessageProcess"/>
<form action="" method="post" name="mainFrm">
    <input type="hidden" name="act">
    <input type="hidden" name="projectId" value="<%=dto.getLogId()%>">
    <table width="50%" align="center">
        <tr>
            <td width="15%" align="right">资产条码：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="barCode" value="<%=dto.getBarCode()%>"></td>
        </tr>
        <tr>
            <td align="right">资产编号：</td>
            <td><input type="text" style="width:90%" name="tagNumberFrom" value="<%=dto.getTagNumberFrom()%>" class="noEmptyInput"></td>
        </tr>

        <tr>
            <td align="right">资产名称：</td>
            <td width="25%"><input type="text" style="width:90%" name="nameFrom" value="<%=dto.getNameFrom()%>" class="noEmptyInput"></td>
        </tr>

        <tr>
            <td  width="20%" align="right">资产型号【EAM－>ERP】：</td>

            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="modelFrom" value="<%=dto.getModelFrom()%>"></td>
        </tr>
        <tr>
            <td align="right">资产地点【EAM－>ERP】：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="segment1" value="<%=dto.getLocationFrom()%>"></td>
        </tr>
         <tr>
            <td width="22%" align="right">资产责任部门【EAM－>ERP】：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="segment1" value=""></td>
        </tr>
         <tr>
            <td width="15%" align="right">责任人【EAM－>ERP】：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="ownerFrom" value="<%=dto.getOwnerFrom()%>"></td>
       </tr>
         <tr>
          <td width="26%" align="right" > 折旧账户成本中心：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="costCenterFrom" value="<%=dto.getCostCenterFrom()%>"></td>
        </tr>
         <tr>
            <td width="10%" align="right">状态：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="transStatus" value="<%=dto.getTransStatus()%>"></td>
        </tr>
         <tr>
            <td width="10%" align="right">备注：</td>
            <td width="35%"><input class="noEmptyInput" type="text" style="width:90%" name="remark" value="<%=dto.getRemark()%>"></td>
        </tr>
        <tr>
            <td align="center"> <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_HistoryBack(); return false;"></td>
        </tr>
    </table>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

   function do_HistoryBack() {

		do_Back();

}

function do_Back() {
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.TransDisposeServlet";
	mainFrm.submit();
}
</script>