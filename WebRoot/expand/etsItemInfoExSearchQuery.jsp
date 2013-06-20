<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
	<script language="javascript" src="/WebLibary/js/calendar.js"></script>

</head>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
	RequestParser parser = new RequestParser();
    parser.transData(request);
    
    String financePropName=(String)request.getAttribute(WebAttrConstant.FINANCE_PROP_OPTION);
%>
<form name="mainFrm"  method="POST" action="/servlet/com.sino.ams.expand.servlet.EtsItemInfoExSearchServlet">
<script type="text/javascript">
    printTitleBar("IT资产查询");
</script>
    <table border="0" width="100%" bordercolor="red" class="queryHeadBg" id="table1">
		<tr>
			<td width="12%" align="right">条 码 号：</td>
			<td width="20%">
				<input type="text" name="barcode" style="width:100%" value="<%=parser.getParameter("barcode")%>"></td>
			<td width="10%" align="right">名&nbsp;&nbsp;称：</td>
			<td width="20%">
				<input type="text" name="itemName" style="width:80%" value="<%=parser.getParameter("itemName")%>">
				<a href = # title = "点击选择名称" class = "linka" onclick = "do_SelectItemName();">[…]</a></td>
            <td width="10%" align="right">型&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
			<td width="20%">
				<input type="text" name="itemSpec" style="width:80%" value="<%=parser.getParameter("itemSpec")%>">
				<a href = # title = "点击选择规格型号" class = "linka" onclick = "do_SelectSpec();">[…]</a></td>
            <td>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="10%" align="right">责任部门：</td>
			<td width="15%">
				<input type="text" name="deptName" style="width:80%" readonly="readonly" class="readonlyInput" value="<%=parser.getParameter("deptName")%>">
				<a href="#" title="点击选择责任部门" class="linka" onclick="do_SelectResponsibilityDept();">[…]</a>
				<input type="hidden" name="responsibilityDept" value=""></td>
			<td width="10%" align="right">责任人：</td>
			<td width="15%">
				<input type="text" name="employeeName" style="width:80%" readonly="readonly" class="readonlyInput" value="<%=parser.getParameter("employeeName")%>">
				<a href="#" title="点击选择责任人" class="linka" onclick="do_SelectResponsibilityUser();">[…]</a>
				<input type="hidden" name="responsibilityUser" value=""></td>
			<td width="10%" align="right">财务属性：</td>
			<td width="15%">
				
				<select name="financeProp" style="width:100%"><%=financePropName %></select> </td>
            <td width="50%" colspan="3" align="center"><img src="/images/eam_images/search.jpg" alt="IT资产确认" onClick="do_Search(); return false;">
            </td>
            <td>&nbsp;&nbsp;</td>
		</tr>
	</table>
  <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"  class="headerTable">

	</table>
  </div>
    <div style="overflow-y:scroll;overflow-x:scroll; height:300px;width:100%;left:1px;margin-left:0" align="left">
	    <table width="120%" border="1" bordercolor="#666666">
	<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

		<tr class="headerTable">
			<td height="22" width="6%" align="center">条码号</td>
			<td height="22" width="10%" align="center">名称</td>
			<td height="22" width="10%" align="center">型号</td>
			<td height="22" width="5%" align="center">CPU频率</td>
			<td height="22" width="5%" align="center">内存容量</td>
			<td height="22" width="5%" align="center">硬盘容量</td>
			<td height="22" width="10%" align="center">显示器类型</td>
			<td height="22" width="5%" align="center">显示器尺寸</td>
			<td height="22" width="15%" align="center">责任部门</td>
			<td height="22" width="5%" align="center">责任人</td>
			<td height="22" width="5%" align="center">财务属性</td>
			<td height="22" width="7%" align="center">启用日期</td>
		</tr>
<%
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rows != null && !rows.isEmpty()){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>		
			<tr class="dataTR">
				<td height="22" width="6%" align="center"><%=row.getValue("BARCODE")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("ITEM_NAME")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("ATTRIBUTE1")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("ATTRIBUTE2")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("ATTRIBUTE3")%></td>
				<td height="22" width="10%" align="center"><%=row.getValue("ATTRIBUTE4")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("ATTRIBUTE5")%></td>
				<td height="22" width="15%" align="center"><%=row.getValue("DEPT_NAME")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("EMPLOYEE_NAME")%></td>
				<td height="22" width="5%" align="center"><%=row.getValue("FINANCE_PROP_NAME")%></td>
				<td height="22" width="7%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
			</tr>
<%
		}
	}
%>		
		</table>
	</div>
    </form>

<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script language="javascript">

function do_Search(){
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value="<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

//查找责任部门
function do_SelectResponsibilityDept() {
	document.forms[0].deptName.value="";
	var lookUpName = "<%=LookUpConstant.LOOK_UP_MIS_INFO%>";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var depts = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if(depts){
		document.forms[0].deptName.value = depts[0].deptName;
		document.forms[0].responsibilityDept.value = depts[0].deptCode;
		document.forms[0].employeeName.value = depts[0].userName;
		document.forms[0].responsibilityUser.value = depts[0].employeeId;
	}else{
		document.forms[0].deptName.value = "";
		document.forms[0].responsibilityDept.value = "";
		document.forms[0].employeeName.value = "";
		document.forms[0].responsibilityUser.value = "";
	}
}
//查找责任人
function do_SelectResponsibilityUser() {
	document.forms[0].employeeName.value="";
	var lookUpName = "<%=LookUpConstant.LOOK_UP_MIS_INFO%>";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if(users){
		document.forms[0].employeeName.value = users[0].userName;
		document.forms[0].responsibilityUser.value = users[0].employeeId;
		document.forms[0].deptName.value = users[0].deptName;
		document.forms[0].responsibilityDept.value = users[0].deptCode;
	}else{
		document.forms[0].deptName.value = "";
		document.forms[0].responsibilityDept.value = "";
		document.forms[0].employeeName.value = "";
		document.forms[0].responsibilityUser.value = "";
	}
}
//查找品名
function do_SelectItemName() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var itemNames = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (itemNames) {
            document.forms[0].itemName.value = itemNames[0].itemName;
            document.forms[0].itemSpec.value = itemNames[0].itemSpec;
        } else {
            document.forms[0].itemName.value = "";
            document.forms[0].itemSpec.value = "";
        }
    }

//查找规格型号
function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            document.forms[0].itemSpec.value = specs[0].itemSpec;
            document.forms[0].itemName.value = specs[0].itemName;
        } else {
            document.forms[0].itemName.value = "";
            document.forms[0].itemSpec.value = "";
        }
    }
</script>