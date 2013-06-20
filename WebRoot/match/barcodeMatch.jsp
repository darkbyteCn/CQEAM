<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.match.dto.BarcodeMatchDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ include file="/newasset/headerInclude.htm"%>

<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>

<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-11-29
  Time: 9:46:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <% 	
    	String titleText="";
    	String titleBar="";
    	SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    	if ("Y".equalsIgnoreCase(user.getIsTd())) { 
    		titleText="自动匹配(TD)";
    		titleBar="EAM-MIS自动匹配(TD)";
    	} else {
    		titleText="自动匹配";
    		titleBar="EAM-MIS自动匹配";
    	}	
    %>
    <title><%=titleText%></title>
    
<script type="text/javascript">
<%
	BarcodeMatchDTO barcodeMatchDTO=(BarcodeMatchDTO)request.getAttribute(WebAttrConstant.BARCODE_MATCH_DTO);
	String isMatch = (String)request.getAttribute(WebAttrConstant.IS_MATCH_OPTION);
	if(barcodeMatchDTO.getMatch().equals("Y")) {
%>
        var ArrAction1 = new Array(true, "已匹配信息", "act_query.gif", "已匹配信息", "initialized");
        var ArrActions = new Array(ArrAction1);
        var ArrSinoViews = new Array();
        var ArrSinoTitles = new Array();
<% 
	   } else {
%>
		var ArrAction1 = new Array(true, "匹配", "act_query.gif", "匹配", "itemMatch");
		var ArrActions = new Array(ArrAction1);
		var ArrSinoViews = new Array();
		var ArrSinoTitles = new Array();
<%
	}
%>
</script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String itemName = parser.getParameter("itemName");
    String itemSpec = parser.getParameter("itemSpec");
    String workorderObjectLocation = parser.getParameter("workorderObjectLocation");
    String domethod = parser.getParameter("domethod");
    String ret = parser.getParameter("ret");
    String barcode = parser.getParameter("barcode");
    String responsibilityUser = parser.getParameter("responsibilityUser");
    String costCenter = parser.getParameter("costCenter");
    String nameSame = parser.getParameter("nameSame");
    String specSame = parser.getParameter("specSame");
    String resSame = parser.getParameter("resSame");
    String locSame = parser.getParameter("locSame");
    
    String check = parser.getParameter("check");
    ServletConfigDTO configDto = SessionUtil.getServletConfigDTO(request);
    String shanXi = "";
     if (configDto.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
         shanXi = "Y";
     }
%>
<body onkeydown="autoExeFunction('do_search()')" onload="do_initPage();mainFrm.barcode.focus();">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/public/exportMsg.jsp"/>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.match.servlet.BarcodeMatchServlet">
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    printTitleBar("<%=titleBar%>")
    printToolBar();
</script>
<table width="100%" topmargin="0" border="0" class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>

            <td width="10%" align="right">EAM标签号：</td>
            <td width="20%"><input style="width:75%" type="text" name="barcode" value="<%=barcode%>"></td>
            <td width="10%" align="right">EAM资产名称：</td>
            <td width="20%"><input style="width:75%" type="text" name="itemName" value="<%=itemName%>"></td>
            <td width="10%" align="right">EAM资产型号：</td>
            <td width="20%"><input style="width:100%" type="text" name="itemSpec" value="<%=itemSpec%>"></td>

        </tr>
        <tr>
            <td align="right" width="10%">所属地点：</td>
            <td width="20%"><input type="text" name="workorderObjectLocation" value="<%=workorderObjectLocation%>" style="width:75%"><a href="#" onClick="SelectVendorId(); " class="linka">[…]</a></td>
            <td width="5%" align="right">责任人：</td>
            <td width="20%"><input style="width:75%" type="text" name="responsibilityUser" value="<%=responsibilityUser%>"><a href="#" onClick="do_SelectUser(); " class="linka">[…]</a></td>
            <td width="10%" align="right">匹配分类：</td>
            <td width="20%"><select class="select_style1" type= "text" name="isMatch" style="width:100%"><%=isMatch%></select></td>

        </tr>
    <tr>
        <td width="10%" align="right">名称相同</td>
        <td width="20%">
        	<select class="select_style1" name="nameSame" style="width: 70%">
        		<option value=""  <%=nameSame.equals("" ) ? "selected" : ""%>>--请选择--</option>
        		<option value="N" <%=nameSame.equals("N") ? "selected" : ""%>>否</option>
				<option value="Y" <%=nameSame.equals("Y") ? "selected" : ""%>>是</option>
			</select>
		</td>
        <td width="10%" align="right">型号相同</td>
        <td width="20%">
        	<select class="select_style1" name="specSame" style="width: 70%">
        		<option value=""  <%=specSame.equals("" ) ? "selected" : ""%>>--请选择--</option>
        		<option value="N" <%=specSame.equals("N") ? "selected" : ""%>>否</option>
				<option value="Y" <%=specSame.equals("Y") ? "selected" : ""%>>是</option>
			</select>
		</td>
        <td width="10%" align="right">已盘点：</td>
        	<td width="20%">
        		<select name="check" class="select_style1"  style="width: 100%">
                	<option value="N" <%=check.equals("N") ? "selected" : ""%>>全部</option>
					<option value="Y" <%=check.equals("Y") ? "selected" : ""%>>是</option>
                </select>
		</td>
    </tr>
    <tr>
        <td width="10%" align="right">责任人相同</td>
        <td width="20%">
        	<select class="select_style1" name="resSame" style="width: 70%">
        		<option value=""  <%=resSame.equals("" ) ? "selected" : ""%>>--请选择--</option>
        		<option value="N" <%=resSame.equals("N") ? "selected" : ""%>>否</option>
				<option value="Y" <%=resSame.equals("Y") ? "selected" : ""%>>是</option>
			</select>
		</td>
        <td width="10%" align="right">地点相同</td>
        <td width="20%">
        	<select class="select_style1" name="locSame" style="width: 70%">
        		<option value=""  <%=locSame.equals("" ) ? "selected" : ""%>>--请选择--</option>
        		<option value="N" <%=locSame.equals("N") ? "selected" : ""%>>否</option>
				<option value="Y" <%=locSame.equals("Y") ? "selected" : ""%>>是</option>
			</select>
		</td>
        <td width="15%" align="right">
        	<img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
        </td>
        <td width="15%" align="left">
        	<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel">
        </td>
    </tr>
    
    
</table>
	<input type="hidden" name="domethod" value="<%=domethod%>">
	<input type="hidden" name="ret" value="<%=ret%>">
	<input type="hidden" name="shanXi" value="<%=shanXi%>">
	<input type="hidden" name="act">
	<input type="hidden" name="orgIds" value="">
    <input type="hidden" name="flag" value="0">
<div id="headDiv" style="overflow:hidden;position:absolute;top:148px;left:1px;width:840px">
    <table class="headerTable" border="1" width="220%" height="22">
        <tr height="20px" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox" id="controlBox" onclick="checkAll('titleCheck','subCheck')"></td>
            <td width="5%" align="center">EAM标签号</td>
            <td width="5%" align="center">MIS标签号</td>
            <td width="6%" align="center">EAM资产名称</td>
            <td width="6%" align="center">MIS资产名称</td>
            <td width="6%" align="center">EAM资产型号</td>
            <td width="6%" align="center">MIS资产型号</td>
            <td width="8%" align="center">EAM地点代码</td>
            <td width="12%" align="center">EAM地点名称</td>
            <td width="8%" align="center">MIS地点代码</td>
            <td width="12%" align="center">MIS地点名称</td>
            <td width="4%" align="center">EAM责任人</td>
            <td width="4%" align="center">MIS责任人</td>
            <td width="4%" align="center">EAM目录代码</td>
            <td width="4%" align="center">EAM目录描述</td>
            <td width="4%" align="center">MIS目录代码</td>
            <td width="4%" align="center">MIS目录描述</td>
            <!--<td width="4%" align="center">MIS数量</td>-->
		</tr>
    </table>
</div>
 <div id="dataDiv" style="overflow:scroll;height:230px;width:856px;position:absolute;top:168px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="220%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" >
            <%--<td width="2%" align="center" onMouseMove="do_ViewLastChg('<%=row.getValue("BARCODE")%>')" onMouseOut="do_CloseViewChg();"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>--%>
            <td width="2%" align="center" onMouseOut="do_CloseViewChg();"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
            <td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
            <td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("FA_BARCODE")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_DESCRIPTION")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("SPEC")%>"></td>
            <td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_SPEC")%>"></td>
            <td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("ETS_LOCATION")%>"></td>
            <td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
            <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_LOCATION")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("CONTENT_CODE")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("CONTENT_NAME")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CODE")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
            <%--<td width="4%"><input type="text" class="finput3" readonly value="<%=row.getValue("CURRENT_UNITS")%>"></td>--%>
		</tr>
<%
        }
        }
%>
  </table>
</div>
<div id="barcodeChgMessage" style="position:absolute; bottom:40%; left:150; z-index:10; visibility:hidden">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="30%"></td>
			<td bgcolor="#ff9900">
				<table width="100%" height="60" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td bgcolor="#eeeeee" align="left" id="messageTd">正在请求数据，请稍候......</td>
					</tr>
				</table>
			</td>
			<td width="30%"></td>
		</tr>
	</table>
</div>



</form>

<div style="position:absolute;bottom:1px;left:0; right:20PX;top:400px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

</body>
</html>

<script type="text/javascript">

function do_search() {
    if (mainFrm.shanXi.value == "Y") {
        if(do_Validate()){
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            mainFrm.submit();
        }

    } else {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
}

function do_export() {
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>"
    mainFrm.submit();
    
    openExportMsgDiv();
}



function SelectVendorId(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BTS%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
        for(var i = 0; i < vendorNames.length; i++){
            vendorName = vendorNames[i];
            dto2Frm(vendorName, "mainFrm");
        }
    }
}
      //LOOK_UP_MIS_DEPT

function SelectDeptName(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
       mainFrm.responsibilityDept.value = vendorNames[0].deptName;
    }
}



function itemMatch(){    //进行匹配操作
     var checkedCount = getCheckedBoxCount("subCheck");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
        return;   
	} else {
        if (document.mainFrm.flag.value == "1") {
            alert("正在匹配，请等待。");
            return;
        }
        document.mainFrm.flag.value="1";
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
        mainFrm.submit();
    }
}

function do_initPage(){
	do_SetPageWidth();
//	if (mainFrm.ret.value == "Y"){
//		alert("匹配成功!");
//		mainFrm.ret.value ="";
//	} else  if (mainFrm.ret.value == "N"){
//		alert("匹配失败！");
//		mainFrm.ret.value ="";
//	}
}

function initialized(){}

function do_Validate(){
   var deptUser= mainFrm.responsibilityUser.value;
   var location= mainFrm.workorderObjectLocation.value;
    if(deptUser==""&&location==""){
        alert("地点和责任人必须有一个查询条件");
        return false;
    } else {
       return true;
    }
}


function do_ViewLastChg(barcode){
	var url = "/servlet/com.sino.ams.match.servlet.LastChangeViewServlet";
	url += "?barcode=" + barcode;
	do_ProcessSimpleAjax(url, null);
}


function do_ProcessResponse(responseContent){
	document.getElementById("messageTd").innerHTML = responseContent;
	document.getElementById("barcodeChgMessage").style.visibility = "visible";
}

function do_CloseViewChg(){
	document.getElementById("messageTd").innerHTML = "";
	document.getElementById("barcodeChgMessage").style.visibility = "hidden";
}
function do_SelectUser() {
    //var projects = getLookUpValues("LOOK_UP_PERSON", 48, 30, "organizationId=<%=user.getOrganizationId()%>");
    //if (projects) {
    //    document.mainForm.maintainUser.value = projects[0].executeUserName;
    //} else {
    //    document.mainForm.maintainUser.value = "";
    // }
    var lookUpName = "LOOK_UP_PERSON";
	var dialogWidth = 47;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(objs){
		var obj = objs[0];
		mainFrm.responsibilityUser.value = obj["userName"];
	}
}
</script>