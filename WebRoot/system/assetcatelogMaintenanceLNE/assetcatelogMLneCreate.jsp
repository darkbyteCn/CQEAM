<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
    <title>资产目录加多维度对应LNE</title>
</head>
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<script type="text/javascript" src="/WebLibary/js/jquery-1.2.6.js"></script>
  <body onload="jilianLne(),jilianselect(),jilianData();">
   <script language="javascript">
        printTitleBar("资产目录加多维度对应LNE--新增");
        var ArrAction0 = new Array(true, "保存", "action_save.gif", "保存", "do_Save()");
	    var ArrAction1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "history.go(-1);");
	    var ArrActions = new Array(ArrAction0,ArrAction1);
	    var ArrSinoViews = new Array();
	    printToolBar();
	</script>
	<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.assetcatelogMaintenanceLNE.servlet.AssetcatelogMLneServlet">
		<input type="hidden" name="act">
		<input type="hidden" name="count">
		<input type="hidden" name="contentCode">
		<input type="hidden" name="nleName">
		<input type="hidden" name="matchDesc">
		<input type="hidden" name="naCode">
		<input type="hidden" name="lneName">
		<table width="90%" align="center" border="0" bordercolor="#666666" >
        <tr>
            <td width="20%" align="right" >资产目录：</td>
            <td width="50%" colspan="3"> <input name="contentName" type="text" id="contentName"  class='input_style1' style="width:70%" onclick="do_SelectContent();"><a href="" onclick="do_SelectContent(); return false;" title="请选择资产">[…]</a></td>
            <td></td>
        </tr>
        <tr> <td> &nbsp;   <td> </tr>
        <tr> <td> &nbsp;   <td> </tr>
        <tr>
            <td width="20%" align="right" >请选择：</td>
		        <td>
			        <tr>
			            <td width="20%" align="right"><input id="radio1" name="radio1" type="radio" onclick="offButton0()" />网络层次：</td>
			            <td width="50%" colspan="3"><select  id="select" name="nleCode"  style="width:70%"></select></td>
			        </tr>
			        <tr>
			            <td width="20%" align="right"><input id="radio2" name="radio2" type="radio" onclick="offButton1()" />地点类型：</td>
			            <td width="50%" colspan="3"><select id="selectData" name="matchCode"  style="width:70%"></select></td>
			        </tr>
			        <tr>
			            <td width="20%" align="right"><input id="radio3" name="radio3" type="radio"  onclick="offButton2()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N/A：</td>
			        </tr>
		        </td>
            <td></td>
        </tr>
        <tr> <td> &nbsp;   <td> </tr>
        <tr> <td> &nbsp;   <td> </tr>
        <tr>
            <td align="right" width="20%">选择逻辑网络元素：</td>
            <td width="50%" colspan="3"><select id="lneCodes" name="lneCode"  style="width:70%"></select></td>
            <td></td>
        </tr>
        </table>
	</form>
  </body>
</html>
<script>
function do_Save() {
		document.mainFrm.nleName.value = document.getElementById("select").options[document.getElementById("select").selectedIndex].text
		document.mainFrm.matchDesc.value = document.getElementById("selectData").options[document.getElementById("selectData").selectedIndex].text;
		document.mainFrm.lneName.value = document.getElementById("lneCodes").options[document.getElementById("lneCodes").selectedIndex].text;
		if(document.mainFrm.contentCode.value == ""){
			alert("请选择资产目录！");
			return;
		}
		if(document.mainFrm.radio1.checked == false && document.mainFrm.radio2.checked == false && document.mainFrm.radio3.checked == false)
		{
			alert("请选择单选框，最少选择一个！");
			return;
		}
		if(document.mainFrm.lneCode.value == ""){
			alert("请选择逻辑网络元素！");
			return;
		}
		mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
		mainFrm.submit();
}
function do_SelectContent(){
    var lookUpName = "";
        lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FA_CONTENTS%>";
    var dialogWidth = 55;
    var dialogHeight = 30;
    var userPara = "";
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (objs) {
        var obj = objs[0];
        dto2Frm(obj, "mainFrm");
        document.mainFrm.contentCode.value = obj["contentCode"];
        document.mainFrm.contentName.value = obj["contentName"];
    } else {
        document.mainFrm.contentCode.value = "";
        document.mainFrm.contentName.value = "";
    }
}
function jilianData(){
	$.ajax({
    url: '/servlet/com.sino.ams.system.assetcatelogMaintenanceLNE.servlet.AssetAjaxServlet?count='+'1'+'',
    type: 'POST',
    success: function (data)
    {
		var obj=document.getElementById("selectData");
		obj.outerHTML = "<select id='selectData' name='matchCode' style='width:70%'>"+data+"</select>";
    }
});
}

function jilianselect(){
	$.ajax({
    url: '/servlet/com.sino.ams.system.assetcatelogMaintenanceLNE.servlet.AssetAjaxServlet?count='+'2'+'',
    type: 'POST',
    success: function (data)
    {
		var obj=document.getElementById("select");
		obj.outerHTML = "<select id='select' name='nleCode' style='width:70%'>"+data+"</select>";
    }
});
}

function jilianLne(){
	$.ajax({
    url: '/servlet/com.sino.ams.system.assetcatelogMaintenanceLNE.servlet.AssetAjaxServlet?count='+'3'+'',
    type: 'POST',
    success: function (data)
    {
		var obj=document.getElementById("lneCodes");
		obj.outerHTML = "<select id='lneCodes' name='lneCode' style='width:70%'>"+data+"</select>";
    }
});
}

function offButton0(){
	var the_box = document.mainFrm.radio1;
		
	if (the_box.checked == true){
		document.mainFrm.radio2.checked = false;
		document.mainFrm.radio3.checked = false;
		document.mainFrm.matchCode.value = "";
		document.mainFrm.selectData.disabled=true;
		document.mainFrm.naCode.value = "";
		jilianselect()
	}
}
function offButton1(){
	var the_box = document.mainFrm.radio2;
		
	if (the_box.checked == true){
		document.mainFrm.radio1.checked = false;
		document.mainFrm.radio3.checked = false;
		document.mainFrm.nleCode.value = "";
		document.mainFrm.select.disabled=true;
		document.mainFrm.naCode.value = "";
		jilianData();
	}
}
function offButton2(){
	var the_box = document.mainFrm.radio3;
		
	if (the_box.checked == true){
		document.mainFrm.radio2.checked = false;
		document.mainFrm.radio1.checked = false;
		document.mainFrm.matchCode.value = "";
		document.mainFrm.nleCode.value = "";
		document.mainFrm.select.disabled=true;
		document.mainFrm.selectData.disabled=true;
		document.mainFrm.naCode.value = "N/A";
	}
}


</script>