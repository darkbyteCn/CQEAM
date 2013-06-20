<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.ams.system.cost.dto.AmsMisCostMatchDTO" %>
<%--
  Created by Administrator.
  Date: 2008-7-31
  Time: 16:22:29
--%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
<head><title>部门信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>

</head>
<%
    AmsMisCostMatchDTO dto = (AmsMisCostMatchDTO) request.getAttribute("MIS_HEADER");
//    String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String act = StrUtil.nullToString(request.getParameter("act"));
//    ServletConfigDTO configDto = SessionUtil.getServletConfigDTO(request);
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();"  onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.ams.system.cost.servlet.DeptMisServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("部门信息")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act" value="<%=act%>" onclick="do_SetPageWidth();">
    <table width="100%" class="queryHeadBg" id="tb" border = "0">
        <tr>
            <td width="20%" align="right">部门名称：</td>
            <td width="60%"><input type="text" class="blueBorder" name="deptName" style="width:80%" value ="<%=dto.getDeptName()%>"><a href="#"  onClick="SelectDeptName(); "  class="linka"></a></td>
            <td width="20%" align="right"><img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()"></td>
        </tr>
    </table>
     <input type="hidden" name="tempRadio" >
     <input type="hidden" name="tempCompanyCode" >
    <div  id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:0px;width:100%">
        <table width="100%" border="1" class="headerTable"  title="点击自适应窗口宽度" onClick="do_SetPageWidth()">
            <tr height="23px">
                <td align="center" width="5%"><input type="checkbox" name="titleCheck" class="headCheckbox" onclick="checkAll(this.name,'subCheck');"></td>
                <td align="center" width="12%">公司代码</td>
                <td align="center" width="15%">部门代码</td>
                <td align="center" width="68%">部门名称</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:80%;width:100%;position:absolute;top:69px;left:0px;height:490px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;" >
        <table width="100%" border="1"  id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
	if (rows != null && rows.getSize() > 0) {
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
            <tr onclick="executeClick(this);" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            	<td  width="5%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("DEPT_CODE")%>"></td>
                <td  width="12%"><%=row.getValue("COMPANY_CODE")%></td>
                <td  width="15%"><%=row.getValue("DEPT_CODE")%></td>
                <td  width="68%"><%=row.getValue("DEPT_NAME")%>
                <input type="hidden" class="finput2" value="<%=row.getValue("COMPANY_CODE")%>" />
                <input type="hidden" class="finput" value="<%=row.getValue("DEPT_CODE")%>" />
                <input type="hidden" class="finput" value="<%=row.getValue("DEPT_NAME")%>" />
                </td> 
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
        if (document.getElementById("dataTable")) {
            document.getElementById("scrollTb").height = document.getElementById("dataTable").offsetHeight;
        }
    }
    function do_query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.forms[0].act.value = "<%=WebActionConstant.QUERY_ACTION%>"
        document.forms[0].submit();
    }

    function do_export() {
//        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.forms[0].act.value = "<%=WebActionConstant.EXPORT_ACTION%>"
        document.forms[0].submit();
    }

    function do_selectAssetsLocation() {
        document.mainForm.assetsLocation.value = "";
        var projects = getLookUpValues("<%=LookUpConstant.LOOK_UP_ASSETS_LOCATION%>", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            document.mainForm.assetsLocation.value = projects[0].assetsLocation;
        }
    }
    function do_SelectUser() {
        var projects = getLookUpValues("LOOK_UP_USER", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
        if (projects) {
            //            dto2Frm(projects[0], "form1");
            document.mainForm.assignedToName.value = projects[0].executeUserName;
            //        document.mainForm.workorderObjectNo.value = projects[0].workorderObjectNo;
        } else {
            document.mainForm.assignedToName.value = "";
        }
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


function matchIt() {
    var success = false;
   　var radioObj = parent.amsInfo.mainForm.dept;
    if (radioObj == null || radioObj == '')
    {
        alert('请选择成本中心后再操作！');
        return false;
    }

    if (!getCheckBoxValue("subCheck", ";")) {
        alert("请选择部门后，再执行本操作！");
        return false;
    }


    var radioCode;
    var companyCode;
    if (radioObj.length) {
        for (var i = 0; i < radioObj.length; i++) {
            if (radioObj[i].checked) {
                radioCode = radioObj[i].value;
                companyCode = radioObj[i].parentElement.lastChild.value;
                
                break;
            }
        }
    } else {
        if (radioObj.checked) {
            radioCode = radioObj.value;
            companyCode = radioObj.parentElement.lastChild.value;
        }
    }
    
    if (!radioCode) {
        alert("请选择一个成本中心后，再执行本操作！");
        return false;
    }
    var j = getCheckedBoxCount("subCheck");
    j = (j == null) ? "0" : j;
    var radioObjArr = radioCode.split(";");
    var companyCodeArr = companyCode.split(";");
    var num = radioObjArr[1];
    if (j > num) {
        alert("对不起，左边选定的设备数量大于右边选定的设备数量，无法匹配！");
        return false;
    } else {
        document.forms[0].tempRadio.value = radioObjArr[0];
        document.forms[0].tempCompanyCode.value = companyCodeArr[0];
        
        document.forms[0].act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        //            alert("starting match")
        document.forms[0].submit();
    }
    return true;
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