<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.*" %>
<%@ page import="com.sino.base.dto.*" %>
<%@ page import="com.sino.base.util.*" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.*" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfRoleDTO" %>
<%@ page import="com.sino.ams.newasset.dto.*" %>
<%@ page import="com.sino.ams.newasset.constant.*" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
	<title>综合查询设置</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/DateProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/flow/flow.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
<head>
<body leftmargin="1" topmargin="1" onkeydown="autoExeFunction('do_search()')">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" action="/servlet/com.sino.ams.net.equip.servlet.IntegratedServlet" method="post">
  <script type="text/javascript">
        printTitleBar("设备查询--设备信息综合查询")
  </script>
<table width="100%" border="0"  >
  <tr>
      <td  width="100%" height="30"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
  </tr>
  <tr>
      <td  width="100%" height="22" bgcolor="#0073BF" ></td>
  </tr>
</table>

<table border="0" width="100%"  cellspacing="1" >
	<tr>
		<td>
			<table border="0" width="100%" id="table1">
				<tr>
					<td width="100%" height="22" colspan="5" align="center">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     综合查询自定义</td>
				</tr>
				<tr>
					<td width="20%" height="22">　</td>
					<td width="25%" height="22" align="center">待选字断</td>
					<td width="20%" height="22" align="center">操作</td>
					<td width="25%" height="22" align="center">已选字段</td>
					<td width="10%" height="22">　</td>
				</tr>
				<tr>
					<td width="20%" height="70" align="right"><b>查询字段定义：</b></td>
					<td width="25%" height="70">
						<select id="allQueryFields" size="10" style="width:100%;height:100%" ondblclick="do_AddField(1)" name="allQueryFields" title="双击选择该字段" multiple>
						<%=request.getAttribute(AssetsWebAttributes.ALL_QRY_FIELDS)%>
						</select>
					</td>
					<td width="20%" height="70" align="center">
						<img src="/images/eam_images/new.jpg" onClick="do_AddField(1);"><p>
						<img src="/images/eam_images/add_all.jpg" onClick="do_AddAllField(1);"><p>
						<img src="/images/eam_images/delete.jpg" onClick="do_RemoveField(1)"><p>
						<img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAllField(1)">
					</td>
					<td width="25%" height="70">
						<select id="chkQueryField" size="10" style="width:100%;height:100%" ondblclick="do_RemoveField(1)" name="chkQueryField" title="双击移出该字段" multiple>
						<%=request.getAttribute(AssetsWebAttributes.CHK_QRY_FIELDS)%>
						</select>
					</td>
					<td width="10%" height="70">
						<input type="button" value="至顶" onClick="do_Move2Top(1)"><p>
						<input type="button" value="向上" onClick="do_MoveUp(1)"><p>
						<input type="button" value="向下" onClick="do_MoveDown(1)"><p>
						<input type="button" value="至底" onClick="do_Move2Bottom(1)">
					</td>
				</tr>
			</table>

            <table border="0" width="100%" id="table1">
				<tr>
					<td width="20%" height="70" align="right"><b>显示字段定义：</b></td>
					<td width="25%" height="70">
						<select id="allDisplayFields" size="10" style="width:100%;height:100%" ondblclick="do_AddField(2)" name="allDisplayFields" title="双击选择该字段" multiple>
						<%=request.getAttribute(AssetsWebAttributes.ALL_DIS_FIELDS)%>
						</select>
					</td>
					<td width="20%" height="70" align="center">
						<img src="/images/eam_images/new.jpg" onClick="do_AddField(2);"><p>
						<img src="/images/eam_images/add_all.jpg" onClick="do_AddAllField(2);"><p>
						<img src="/images/eam_images/delete.jpg" onClick="do_RemoveField(2)"><p>
						<img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAllField(2)">
					</td>
					<td width="25%" height="70">
						<select id="chkDisplayField" size="10" style="width:100%;height:100%" ondblclick="do_RemoveField(2)" name="chkDisplayField" title="双击移出该字段" multiple>
						<%=request.getAttribute(AssetsWebAttributes.CHK_DIS_FIELDS)%>
						</select>
					</td>
					<td width="10%" height="70">
						<input type="button" value="至顶" onClick="do_Move2Top(2)"><p>
						<input type="button" value="向上" onClick="do_MoveUp(2)"><p>
						<input type="button" value="向下" onClick="do_MoveDown(2)"><p>
						<input type="button" value="至底" onClick="do_Move2Bottom(2)">
					</td>
				</tr>
				<tr>
					<td width="20%" height="22" align="center">
						　</td>
					<td width="25%" height="22" align="center">
						　</td>
					<td width="20%" height="22" align="center">
						<img border="0" src="/images/eam_images/save.jpg" onclick="do_SaveCustomizeFields();">&nbsp;
					</td>
					<td width="25%" height="22" align="center">
						　</td>
					<td width="10%" height="22" align="center">
						　</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<input type="hidden" name="act" value="">
</form>
</body>
</html>
<script type="text/javascript">

function do_search() {

    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.net.equip.servlet.IntegratedServlet";
    mainFrm.submit();
}

function do_AddField(arg){
	if(arg == 1){
		moveSelectedOption("allQueryFields", "chkQueryField");
	} else {
		moveSelectedOption("allDisplayFields", "chkDisplayField");
	}
}

function do_AddAllField(arg){
	if(arg == 1){
		moveAllOption("allQueryFields", "chkQueryField");
	} else {
		moveAllOption("allDisplayFields", "chkDisplayField");
	}
}

function do_RemoveField(arg){
	if(arg == 1){
		moveSelectedOption("chkQueryField", "allQueryFields");
	} else {
		moveSelectedOption("chkDisplayField", "allDisplayFields");
	}
}

function do_RemoveAllField(arg){
	if(arg == 1){
		moveAllOption("chkQueryField", "allQueryFields");
	} else {
		moveAllOption("chkDisplayField", "allDisplayFields");
	}
}

function do_MoveUp(arg){
	if(arg == 1){
		moveUp(mainFrm.chkQueryField);
	} else {
		moveUp(mainFrm.chkDisplayField);
	}
}


function do_Move2Top(arg){
	if(arg == 1){
		moveUp(mainFrm.chkQueryField, true);
	} else {
		moveUp(mainFrm.chkDisplayField, true);
	}
}

function do_MoveDown(arg){
	if(arg == 1){
		moveDown(mainFrm.chkQueryField);
	} else {
		moveDown(mainFrm.chkDisplayField);
	}
}


function do_Move2Bottom(arg){
	if(arg == 1){
		moveDown(mainFrm.chkQueryField, true);
	} else {
		moveDown(mainFrm.chkDisplayField, true);
	}
}

function do_SaveCustomizeFields(){
	selectAll("chkQueryField");
	if(mainFrm.chkQueryField.length <= 0){
		alert("没有定义查询字段，不能保存设置");
		return;
	}
	selectAll("chkDisplayField");
	if(mainFrm.chkDisplayField.length <= 0){
		alert("没有定义显示字段，不能保存设置");
		return;
	}
	addText2Value(mainFrm.chkQueryField);
	addText2Value(mainFrm.chkDisplayField);
	mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
	mainFrm.submit();
}
</script></html>