<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.system.fixing.dto.EtsItemInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-8-25
  Time: 15:13:05
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>部门</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <style>
        .finput {
            WIDTH: 100%;
            BORDER-RIGHT: 0px ridge;
            BORDER-TOP: 0px ridge;
            BORDER-LEFT: 0px ridge;
            BORDER-BOTTOM: 0px ridge;
            font-size: 12px;
        }

        .finput2 {
            WIDTH: 100%;
            BORDER-RIGHT: 0px ridge;
            BORDER-TOP: 0px ridge;
            BORDER-LEFT: 0px ridge;
            BORDER-BOTTOM: 0px ridge;
            font-size: 12px;
            text-align: center;
        }

        .finput3 {
            WIDTH: 100%;
            BORDER-RIGHT: 0px ridge;
            BORDER-TOP: 0px ridge;
            BORDER-LEFT: 0px ridge;
            BORDER-BOTTOM: 0px ridge;
            font-size: 12px;
            text-align: right;
        }
    </style>
</head>
<%
    EtsItemInfoDTO dto = (EtsItemInfoDTO) request.getAttribute("AMS_HEADER");
    String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.ams.newasset.servlet.AmsDeptMatch" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("部门")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>

    <table width="100%" class="queryHeadBg" id="tb" border="0">
        <tr>
            <td width="15%" align="right">部门：</td>
            <td width="25%"><input type="text" class="blueBorder" name="responsibilityDept" style="width:80%" value="<%=dto.getResponsibilityDept()%>"><a href="#" onClick="SelectDeptName(); " class="linka">[…]</a></td>
            <td width="60%" align="right">
                <img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel">
                <img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=act%>">
    <input type="hidden" name="tempRadio">
    <input type="hidden" name="companyCode">

    <div id="headDiv" style="overflow:hidden;position:absolute;top:44px;left:0px;width:487px">
        <table class="headerTable" border="1" width="100%" title="点击自适应窗口宽度" onClick="do_SetPageWidth()">
            <tr height="22">
                <!--<td align="center" width="2%"><input type="checkbox" name="titleCheck" class="headCheckbox"-->
                                                     <%--onclick="checkAll(this.name,'subCheck');"></td>--%>
                <td align="center" width="2%"></td>
                <td align="center" width="5%">公司</td>
                <td align="center" width="3%">部门代码</td>
                <td align="center" width="10%">部门名称</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:75%;width:504px;position:absolute;top:67px;left:0px;height:530px"
         align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable"
               style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                Row row = null;
                int size = 0;
                if (rows != null && rows.getSize() > 0) {
                    size = rows.getSize();
                    for (int i = 0; i < size; i++) {
                        row = rows.getRow(i);

            %>
            <tr onclick="executeClick(this);" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <%--<td height="22" width="2%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("DEPT_CODE")%>"></td>--%>
                <td height="22" width="2%" align="center"><input type="radio" name="deptCode" value="<%=row.getValue("DEPT_CODE")%>"></td>
                <td height="22" width="5%"><input readonly class="finput2" value="<%=row.getValue("COMPANY")%>"></td>
                <td height="22" width="3%"><input readonly class="finput" value="<%=row.getValue("DEPT_CODE")%>"></td>
                <td height="22" width="10%"><input readonly class="finput" value="<%=row.getValue("DEPT_NAME")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="position:absolute;top:600px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
    <jsp:include page="/message/MessageProcess"/>
</body>

<script type="text/javascript">
function init() {
    document.getElementById("scrollTb").height = document.getElementById("dataTable").offsetHeight;
}
function do_query() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    document.forms[0].act.value = "<%=WebActionConstant.QUERY_ACTION%>"
    document.forms[0].submit();
}

function do_export() {
    document.forms[0].act.value = "<%=WebActionConstant.EXPORT_ACTION%>"
    document.forms[0].submit();
}

function matchIt() {
    var deptObj = parent.amsInfo.mainForm.deptCode;
    if (deptObj == null || deptObj == '') {
        alert('请选择部门后再操作！');
        return;
    }
    if (deptObj) {
        var deptObjValue = parent.amsInfo.getRadioValue("deptCode");
        if (deptObjValue == null || deptObjValue == '') {
            alert('请选择部门后再操作！');
            return;
        }
    }
    var countyObj = parent.misInfo.mainForm.countyCode;
    if (countyObj == null || countyObj == '') {
        alert('请选择成本中心后再操作！');
        return;
    }
    if (countyObj) {
        var countyObjValue = parent.misInfo.getRadioValue("countyCode");
        if (countyObjValue == null || countyObjValue == '') {
            alert('请选择成本中心后再操作！');
            return;
        }
    }
    match();
}

function match() {
    var countyCode = window.parent.misInfo.getRadioValue("countyCode");
    document.forms[0].tempRadio.value = countyCode.split(";")[0];
    document.forms[0].companyCode.value = countyCode.split(";")[1];
    document.forms[0].act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    document.forms[0].submit();
}

window["onscroll"] = function() {
    if (document.getElementById('scrollDiv')) {
        document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
    }
}
window["onresize"] = function() {
    if (document.getElementById('scrollDiv')) {
        document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
    }
}


function SelectDeptName() {
    var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if (vendorNames) {
        document.forms[0].responsibilityDept.value = vendorNames[0].deptName;
    }
}
</script>
</html>
