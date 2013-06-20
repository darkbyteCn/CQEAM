<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.ct.dto.EtsItemInfoDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.ct.bean.LookUpCtConstant" %>
<%--
  Created by Yu.
  Date: 2008-12-08
  Time: 10:28:29
--%>
<html>
<head>
	<title>村通资产匹配-EAM设备信息</title>
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
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>
<%
    EtsItemInfoDTO dto = (EtsItemInfoDTO) request.getAttribute("AMS_HEADER");
    String countyOption = (String) request.getAttribute("COUNTY_OPTION");
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    
    String act = StrUtil.nullToString(request.getParameter("act"));
%>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_query();')">
<form action="/servlet/com.sino.ams.match.servlet.ManualCtMatchAMS" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("村通资产匹配-EAM设备信息")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>



	<table width="100%" class="queryHeadBg" id="tb" border = "0">
	<tr>
		<td width="15%" align="right">关键字：</td>
		<td width="30%"><input type="text" name="key" value="<%=dto.getKey()%>" style="width:100%" class="blueBorder"></td>
		<td width="15%" align="right">地点：</td>
		<td width="30%"><input type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" style="width:70%" class="blueBorder"><a href="#" onClick="do_selectObject();" title="点击选择地点" class="linka">[…]</a></td>
		<td width="10%" align= "right"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel"></td>
	</tr>
	<tr>
		<td width="15%" align="right">名称：</td>
		<td width="30%"><input type="text" name="itemName" value="<%=dto.getItemName()%>" style="width:70%" class="blueBorder"><a href="#" onClick="do_SelectItemName();" title="点击选择名称" class="linka">[…]</a></td>
		<td width="15%" align="right">型号：</td>
		<td width="30%"><input type="text" name="itemSpec" value="<%=dto.getItemSpec()%>" style="width:70%" class="blueBorder"><a href="#" onClick="do_SelectItemSpec();" title="点击选择型号" class="linka">[…]</a></td>
		<td width="10%" align= "right"><img src="/images/eam_images/search.jpg" alt="查询" onclick="do_query()"></td>
	</tr>
</table>
	<input type="hidden" name="act" value="<%=act%>">
    <input type="hidden" name="tempRadio">
    <input type="hidden" name="workorderObjectNo" value="<%=dto.getWorkorderObjectNo()%>">
    <div  id="headDiv" style="overflow:hidden;position:absolute;top:66px;left:0px;width:487px">
        <table class="headerTable" border="1" width="100%" title="点击自适应窗口宽度" onClick="do_SetPageWidth()">
            <tr>
                <td align="center" width="1%"><input type="checkbox" name="titleCheck" class="headCheckbox" onclick="checkAll(this.name,'subCheck');"></td>
                <td align="center" width="3%">标签号</td>
				
                <td align="center" width="5%">设备名称</td>
                <td align="center" width="4%">规格型号</td>
                <td align="center" width="3%">责任人</td>
                
                <td align="center" width="6%">地点</td>
                
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:75%;width:504px;position:absolute;top:89px;left:0px;height:490px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;" >
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
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
            	  
                <td height="22" align="center" width="1%"><input type="checkbox" name="subCheck" value="<%=row.getValue("SYSTEMID")%>"></td>
                
                <td height="22" width="3%"><input readonly class="finput2" value="<%=row.getValue("BARCODE")%>"></td>
				
                <td height="22" width="5%"><input readonly class="finput" value="<%=row.getValue("ITEM_NAME")%>"></td>
                <td height="22" width="4%"><input readonly class="finput" value="<%=row.getValue("ITEM_SPEC")%>"></td>
                  
                <td height="22" width="3%"><input readonly class="finput" value="<%=row.getValue("USER_NAME")%>"></td>
				<!--
				<td height="22" width="3%"><input readonly class="finput" value="<%--=row.getValue("RESPONSIBILITY_USER")--%>"></td>
				-->
                <td height="22" width="6%"><input readonly class="finput" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
                
                
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
//    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    document.forms[0].act.value = "<%=WebActionConstant.EXPORT_ACTION%>"
    document.forms[0].submit();
}

function matchIt() {
    var success = false;
    if (!getCheckBoxValue("subCheck", ";")) {
        alert("请选择标签号后，再执行本操作！");
        return false;
    }
    var radioObj = parent.misInfo.mainForm.assetId;
    if (radioObj == null || radioObj == '')
    {
        alert('请选择资产后再操作！');
        return false;
    }
    var radioCode;
    if (radioObj.length) {
        for (var i = 0; i < radioObj.length; i++) {
            if (radioObj[i].checked) {
                radioCode = radioObj[i].value;
                break;
            }
        }
    } else {
        if (radioObj.checked) {
            radioCode = radioObj.value;
        }
    }
    if (!radioCode) {
        alert("请选择一个设备后，再执行本操作！");
        return false;
    }
    var j = getCheckedBoxCount("subCheck");
    j = (j == null) ? "0" : j;
    var radioObjArr = radioCode.split(";");
    var num = radioObjArr[1];
    var user2="<%=user.getCompanyCode()%>";
    var use1= user2.substring(0,2);
    if(use1=="42"){
       	document.forms[0].tempRadio.value = radioObjArr[0];
        document.forms[0].act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        //alert("starting match")
        document.forms[0].submit();
    }else{
        if (j > num) {
        alert("对不起，左边选定的设备数量大于右边选定的设备数量，无法匹配！");
        return false;
    } else {
        document.forms[0].tempRadio.value = radioObjArr[0];
        document.forms[0].act.value = "<%=WebActionConstant.SAVE_ACTION%>";
        //alert("starting match")
        document.forms[0].submit();
    }
    }
//    if (j > num) {
//        alert("对不起，左边选定的设备数量大于右边选定的设备数量，无法匹配！");
//        return false;
//    } else {
        <%--document.forms[0].tempRadio.value = radioObjArr[0];--%>
        <%--document.forms[0].act.value = "<%=WebActionConstant.SAVE_ACTION%>";--%>
        <%--//            alert("starting match")--%>
        <%--document.forms[0].submit();--%>
//    }
    return true;
}

function do_selectObject() {
    //        var objectCategory = getRadioValue("objectCategory");

    var projects = getLookUpValues("LOOK_UP_ASSETS_OBJECT", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    if (projects) {
        //            dto2Frm(projects[0], "form1");
        document.mainForm.workorderObjectName.value = projects[0].workorderObjectName;
        document.mainForm.workorderObjectNo.value = projects[0].workorderObjectNo;
    }else{
      	document.mainForm.workorderObjectName.value = "";
        document.mainForm.workorderObjectNo.value = "";
    }
}
function do_SelectItemName() {
    var  url="/servlet/com.sino.ams.ct.bean.AMSCtLookUpServlet?lookUpName=<%=LookUpCtConstant.LOOK_UP_SYS_ITEM_NAME%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
       document.forms[0].itemName.value = vendorNames[0].itemName;
    }
}

function do_SelectItemSpec(){
    var  url="/servlet/com.sino.ams.ct.bean.AMSCtLookUpServlet?lookUpName=<%=LookUpCtConstant.LOOK_UP_SYS_ITEM_SPEC%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
       document.forms[0].itemSpec.value = vendorNames[0].itemSpec;
    }
}

function validateData() {
    if (parent.document.getElementById("working").value == '1') {
        alert('正在处理中，请稍候...');
        return false;
    }
    var j = getCheckedBoxCount("subCheck");
    //systemid
    if (j != 1) {
        alert("左边应选择并只能选择一条记录！");
        return false;
    }

    var assetId = window.parent.misInfo.getRadioValue("assetId");
    //    var sysid = window.parent.itemInfoView.manualL.getCheckBoxValue('sysid');
    //    var radioValue = window.parent.systemInfoView.manualR.getRadioValue('assid');
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

</script>
</html>