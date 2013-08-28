<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.lookup.LookUpProp" %>
<%@ page import="com.sino.base.util.ArrUtil" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@page import="com.sino.framework.security.dto.FilterConfigDTO"%>
<%
	FilterConfigDTO filterDTO = SessionUtil.getFilterConfigDTO(request);
	String[] ignoreFields = {"act", "$$$WebGridCurrPage$$$", "$$$WebGridTotalRecord$$$", "REDIRECT_URL", "$$$CHECK_TRANS_FIELD$$$", "$$$WebGridTotalPages$$$", "$$$CHECK_BOX_HIDDEN$$$", "$$$WebGridCustomPageSize$$$", "$$$WebGridLastPageSize$$$", "$$$fromWebPrint$$$", "resourceId", filterDTO.getLoginName(), filterDTO.getLoginPwd()};

	response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

	LookUpProp lookProp = SessionUtil.getLookUpProp(request);
	String[] retFields = lookProp.getRetFields();
	String[] viewPercent = lookProp.getViewPercent();
	String[] disFieldNames = lookProp.getDisFieldNames();
	String[] disFieldLabels = lookProp.getDisFieldLabels();

    String[] qryFieldNames = lookProp.getQryFieldNames();
	String[] qryFieldLabels = lookProp.getQryFieldLabels();
	int totalWidth = lookProp.getTotalWidth();
	RequestParser parser = new RequestParser();
	parser.transData(request);
	Iterator fieldNames = parser.getParameterNames();
	StringBuffer userParameters = new StringBuffer();
	String fieldName = "";
	String[] fieldValues = null;
	String dbFieldName = "";
	String tabPercent = "100%";
    switch(disFieldNames.length){
		case 7 : tabPercent = "110%";break;
		case 8 : tabPercent = "120%";break;
		case 9 : tabPercent = "130%";break;
		case 10 : tabPercent = "140%";break;
		case 11 : tabPercent = "150%";break;
		case 12: tabPercent = "160%";break;
		case 13 : tabPercent = "170%";break;
		case 14 : tabPercent = "200%";break;
		case 15 : tabPercent = "210%";break;
		case 16 : tabPercent = "220%";break;
		case 17 : tabPercent = "230%";break;
	}
	while(fieldNames.hasNext()){
		fieldName = (String)fieldNames.next();
		if(ArrUtil.isInArr(ignoreFields, fieldName)){
			continue;
		}
		dbFieldName = StrUtil.getDbField(fieldName);
		if(ArrUtil.isInArr(qryFieldNames, dbFieldName)){
			continue;
		}
		fieldValues = parser.getParameterValues(fieldName);
		if(fieldValues != null){
			for(int i = 0; i < fieldValues.length; i++){
				userParameters.append("<input type=\"hidden\" name=\"");
				userParameters.append(fieldName);
				userParameters.append("\" value=\"");
				userParameters.append(fieldValues[i]);
				userParameters.append("\">\r\n");
			}
		}
	}
    boolean multipleChose = lookProp.isMultipleChose();
%>
<html>
<head><title>通用查询页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<meta charset=GBK/>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
    <link href="/WebLibary/css/eam.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
	<script type="text/javascript" src="/WebLibary/js/jquery.js"></script>
</head>
<base target="_self">
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<script type="text/javascript">
    printTitleBar("通用查询页面");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search");
    var ArrAction1 = new Array(<%=multipleChose%>, "确定", "action_save.gif", "确定", "do_TransData");
    var ArrAction2 = new Array(<%=multipleChose%>, "关闭", "action_cancel.gif", "关闭", "do_Close");
    var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
    printToolBar();
</script>

<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" action="/servlet/com.sino.base.lookup.LoopUpServlet" method="post">
    <div id="searchDiv" style="overflow:hidden;position:absolute;top:50px;left:1px;width:100%">
	<table style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr style="height:23px">
<%
	String tdWidth = "30%";
	if(!lookProp.isMultipleChose()){
		tdWidth = "10%";
	}
	for(int i = 0; i < qryFieldNames.length; i++){
		fieldName = qryFieldNames[i];
		fieldName = StrUtil.getJavaField(fieldName);
%>
            <td align="left"><%=qryFieldLabels[i]%>：<input class="input_style1" type="text" name="<%=fieldName%>" value="<%=parser.getParameter(fieldName)%>"  style="width:50%"></td>
<%
	}
%> 


<%

	if( lookProp.getLookUpName().equals(AssetsLookUpConstant.LOOK_TRANSFER_DIS_ASSETS)  ){
%>
	<td align="left"> 
			是否预报废:
			<input type="radio" name="itemStatus" value="Y" id="iaY" <%=parser.getParameter("itemStatus").equals("Y")?"checked":""%>><label for="iaY">是</label>
        	<input type="radio" name="itemStatus" value="N" id="iaN" <%=(parser.getParameter("itemStatus").equals("N") || parser.getParameter("itemStatus").equals(""))?"checked":""%>><label for="iaN">否</label>
    </td>
    <td align="left"> 
			是否逾龄:
			<input type="radio" name="isOverageAssets" value="Y" id="iaY2" <%=parser.getParameter("isOverageAssets").equals("Y")?"checked":""%>><label for="iaY2">是</label>
        	<input type="radio" name="isOverageAssets" value="N" id="iaN2" <%=(parser.getParameter("isOverageAssets").equals("N") )?"checked":""%>><label for="iaN2">否</label>
    </td>
<%
	}
%>


<%--

	if( lookProp.getLookUpName().equals( AssetsLookUpConstant.LOOK_UP_PROJECT )  ){
%>
	<td align="left"> 
			项目类型:
			<select name="projectType"><%= request.getAttribute( "PROJECT_TYPE_OPT" ) %></select>
    </td> 
<%
	}
--%>



<%
    if (lookProp.getContentReadio().equals("Y")) {
%>
            <td align="left">已盘点:
                <input type="radio" name="contentBlurred" value="Y" id="yes1" <%=parser.getParameter("contentBlurred").equals("Y")?"checked":""%>><label for="yes1">是</label>
                <input type="radio" name="contentBlurred" value="N" id="no1" <%=(parser.getParameter("contentBlurred").equals("N") || parser.getParameter("contentBlurred").equals(""))?"checked":""%>><label for="no1">否</label>
            </td>
<%
    }
%>
        </tr>
	</table>
<%
	out.print(userParameters.toString());
%>
	<input type="hidden" name="act">
	<input type="hidden" id="workorderNo" name="workorderNo">
</div>
</form>
<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:48px;left:1px;width:100%">
	<table class="headerTable" id="$$$headerTable$$$" border="1" width="<%=tabPercent%>">
		<tr style="cursor:pointer;height:23px"
<%
	if(lookProp.isMultipleChose()){
%>
		title="点击全选或取消全选"
<%
	}	
%>
		><td width="3%" align="center" style="padding:0">
<%
	if(lookProp.isMultipleChose()){
%>
			<input type="checkbox" name="titleCheck" onPropertyChange="checkAll('titleCheck','subCheck')" class="headCheckBox">
<%
	}
%>
			</td>
<%
		for (int i = 0; i < viewPercent.length; i++) {
			out.println("<td onclick=\"do_ProcessClick()\" width=\"" + viewPercent[i] + "\" align=\"center\">" + disFieldLabels[i] + "</td>");
		}
%>
        </tr>
	</table>
</div>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
	if (rows != null && !rows.isEmpty()){
%>
	<div id="dataDiv" style="overflow:scroll;height:50%;width:100%;position:absolute;top:44px;left:1px" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="<%=tabPercent%>" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
			<tr onMouseOver="this.style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" style="cursor:pointer;height:23px" onclick="executeClick(this);" title="点击选择或取消选择本条数据">
				<td height="22" align="center" width="3%"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
<%
			for (int j = 0; j < disFieldNames.length; j++) {
				fieldName = disFieldNames[j];
				String fieldValue = row.getStrValue(fieldName);
				fieldName = StrUtil.getJavaField(fieldName);
                String fieldType = row.getType(fieldName);
                if(!StrUtil.isEmpty(fieldValue) && (fieldType.equals("INT") || fieldType.equals("DECIMAL") || fieldType.equals("NUMBER"))){
                    fieldValue = StrUtil.trim(fieldValue, "0", false);
                    if(fieldValue.endsWith(".")){
                        fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
                    }
                }
%>
				<td height="22" width="<%=viewPercent[j]%>"><input type="text" style="width:100%; border: 1px solid #FFFFFF" readonly onClick="do_ProcessClick();" name="<%=fieldName%>" id="<%=fieldName + i%>" value="<%=fieldValue%>"></td>
<%
			}
%>
    </tr>
<%
		}
%>
		</table>
	</div>
<div id="pageNaviDiv" style="width:100%;position:absolute;top:415px"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
</html>
<script type="text/javascript">
function do_Search(){
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	if(mainFrm.workorderNo)
	{
	    mainFrm.workorderNo.value="<%=request.getParameter("workorderNo")%>";
	}
	mainFrm.submit();
}

function do_CheckData(box){
	var trObj = box.parentNode.parentNode;
	var chkObj = trObj.cells[0].childNodes[0];
	if(chkObj){
		chkObj.click();
	}
}

function do_TransData(){
	if(document.forms["$$$WebGridSystemFrm$$$"]){
		window.returnValue = getDTOArr();
		self.close();
	}
}

function do_ProcessClick(){
    var obj = event.srcElement;
    while(obj.tagName != "TR"){
        obj = obj.parentNode;
    }
    if(obj.tagName == "TR"){
        var chkObjs = obj.cells[0].childNodes;
        if(chkObjs){
            for(var i = 0; i < chkObjs.length; i++){
                var chkObj = chkObjs[i];
                if(chkObj.type == "radio" || chkObj.type == "checkbox"){
                    chkObj.click();
                    return;
                }
            }
        }
    }
}

function do_Close(){
    var hasData = false;
    if(document.forms["$$$WebGridSystemFrm$$$"]){
        var dataArr = getDTOArr();
        if(dataArr && dataArr.length > 1){
            hasData = true;
        }
    }
    if(hasData){
        if(confirm("确定不选择数据返回吗？")){
            window.close();
        }
    } else {
        window.close();
    }
}
</script>