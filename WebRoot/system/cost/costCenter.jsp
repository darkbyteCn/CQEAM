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
<%@ page import="com.sino.ams.system.cost.dto.AmsMisCostMatchDTO" %>
<%--
  Created by Administrator.
  Date: 2008-7-31
  Time: 16:22:29
--%>
<html>
<head><title>成本中心信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>
<%
    AmsMisCostMatchDTO dto = (AmsMisCostMatchDTO) request.getAttribute("AMS_HEADER");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.ams.system.cost.servlet.CostCenterServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("成本中心信息")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" class="queryHeadBg" id="tb" border = "0">
        <tr>
            <td width="15%" align="right">代码：</td>
            <td width="30%"><input type="text" name="costCenterCode" value="<%=dto.getCostCenterCode()%>" style="width:95%" class="blueBorder"></td>
			<td width="15%" align="right">名称：</td>
            <td width="30%"><input type="text" class="blueBorder" name="costCenterName" style="width:80%" value ="<%=dto.getCostCenterName()%>"></td>
            <td width="10%"><img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=act%>" onclick="do_SetPageWidth();">
    <div  id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:0px;width:100%">
        <table class="headerTable" border="1" width="100%" title="点击自适应窗口宽度" onClick="do_SetPageWidth()">
            <tr height= "23px">
                <td align="center" width="5%"></td>
                <td align="center" width="18%">成本中心代码</td>
                <td align="center" width="65%">成本中心</td>
                <td align="center" width="12%">公司代码</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:80%;width:100%;position:absolute;top:69px;left:0px;height:490px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;" >
        <table width="100%" border="1"  id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
	int size = 0;
	if (rows != null && rows.getSize() > 0) {
		size = rows.getSize();
		for (int i = 0; i < size; i++) {
			row = rows.getRow(i);

%>
            <tr onclick="executeClick(this);" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td  width="5%" align="center"><input type="radio" name="dept" value="<%=row.getValue("COST_CENTER_CODE")%>"><input type="hidden" class="finput2" value="<%=row.getValue("COMPANY_CODE")%>"></td>
                <td  width="18%"><input readonly class="finput2" value="<%=row.getValue("COST_CENTER_CODE")%>"></td>
                <td  width="65%"><input readonly class="finput" value="<%=row.getValue("COST_CENTER_NAME")%>"></td>
                <td  width="12%"><input readonly class="finput2" value="<%=row.getValue("COMPANY_CODE")%>"></td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>
</form>
<div id="pageNaviDiv" style="position:absolute;top:600px;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
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


function do_selectObject() {
    //        var objectCategory = getRadioValue("objectCategory");
    var projects = getLookUpValues("LOOK_UP_ASSETS_OBJECT", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        document.mainForm.workorderObjectName.value = projects[0].workorderObjectName;
        document.mainForm.workorderObjectNo.value = projects[0].workorderObjectNo;
    }else{
      document.mainForm.workorderObjectName.value = "";
        document.mainForm.workorderObjectNo.value = "";
    }
}
function do_SelectUser() {
     var projects = getLookUpValues("LOOK_UP_USER", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        document.mainForm.maintainUser.value = projects[0].executeUserName;
//        document.mainForm.workorderObjectNo.value = projects[0].workorderObjectNo;
    }else {
            document.mainForm.maintainUser.value="";
    }
}

function validateData() {
    if (parent.document.getElementById("working").value == '1') {
        alert('正在处理中，请稍候...');
        return false;
    }
    var j = getCheckedBoxCount("subCheck");
    if (j != 1) {
        alert("左边应选择并只能选择一条记录！");
        return false;
    }

    var assetId = window.parent.misInfo.getRadioValue("assetId");
    if (assetId == "") {
        alert("右边应选择一条记录！");
        return false;
    }
    return true;
}
function matchByLocation() {
    if (validateData()) {
        if (!confirm("确定按地点匹配吗？"))
            return false;
        matchByCondition("MatchByLocation")
    }
}
function matchByCounty() {
    if (validateData()) {
        if (!confirm("确定按县匹配吗？"))
            return false;
        matchByCondition("MatchByCounty");
    }
}

function matchByCity() {
    if (validateData()) {
        if (!confirm("确定按市匹配吗？"))
            return false;
        matchByCondition("MatchByCity");
    }
}

function matchByCondition(condition) {
    var radioValue = parent.misInfo.getRadioValue('assetId');
    var arr = radioValue.split(';');
    var assid = arr[0];
    //alert (checkedCount);
    var systemid = getCheckBoxValue('subCheck');
    var url = "/match/wait.jsp?act=" + condition + "&systemid=" + systemid + "&assetId=" + assid;
    var popscript = "dialogWidth:20;dialogHeight:7.5;center:yes;status:no;scroll:no";
    parent.document.getElementById("working").value = 1;
    //    window.open(url, "", "status=yes");
    window.showModalDialog(url, "", popscript);
    parent.document.getElementById("working").value = 0;
    do_query();
    parent.misInfo.do_query();
}

window["onscroll"] = function() {
    if (document.getElementById('scrollDiv')) {
        //    if(/safari/i.test(navigator.userAgent)){
        document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
        //    }else{
        //        document.getElementById('scrollDiv').style.left=document.documentElement.scrollLeft+document.getElementById("scrollDiv").offsetHeight/3+"px";
        //    }
    }
}
window["onresize"] = function() {
    if (document.getElementById('scrollDiv')) {
        document.getElementById('scrollDiv').style.left = document.body.scrollLeft + document.getElementById("tb").offsetWidth - 18 + "px";
    }
}


function SelectDeptName(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
       document.forms[0].responsibilityDept.value = vendorNames[0].deptName;
    }
}


</script>
</html>