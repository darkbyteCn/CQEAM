<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<html>
<head>
    <title>逻辑网络元素属性维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
	<script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<body leftmargin="1" topmargin="0"  onload="initPage();">
<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String[] subChecks = reqParser.getParameterValues("subCheck");
    if (subChecks != null) {
        for (int i = 0; i < subChecks.length; i++) {
            System.out.print(subChecks[i] + ";");
        }
    }
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    AmsElementMatchDTO aemDTO = (AmsElementMatchDTO)request.getAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO);
    String action = StrUtil.nullToString(request.getParameter("act"));
%>
	<script type="text/javascript">
		printTitleBar("逻辑网络元素属性维护");
	    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");
	    var ArrAction1 = new Array(true, "新增", "action_edit.gif", "新增", "do_Create()");
	    var ArrAction2 = new Array(true, "失效", "action_cancel.gif", "失效", "do_Delete()");
	    var ArrAction3 = new Array(true, "导出Excel", "toexcel.gif", "导出Excel", 'do_Export()');
	    var ArrActions = new Array(ArrAction0, ArrAction1,ArrAction2,ArrAction3);
	    var ArrSinoViews = new Array();
	    var ArrSinoTitles = new Array();
	    printToolBar();
    </script>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.manydimensions.servlet.LneServlet">
    <input type="hidden" name="act" >
    <input type="hidden" name="alId">
    <jsp:include page="/message/MessageProcess"/>
    <table border="0" width="100%"  id="table1">
	    <tr>
	    	<td width="15%" align="right">网络专业1</td>
	        <td width="15%"><input class="input_style1" style="width:100%" type="text" name="netCategory1" value="<%=aemDTO.getNetCategory1() %>"></td>
	        <td width="15%" align="right">网络专业2</td>
	        <td width="15%"><input class="input_style1" style="width:100%" type="text" name="netCategory2" value="<%=aemDTO.getNetCategory2() %>"></td>
			<td width="15%" align="right">网元编码</td>
	        <td width="15%"><input class="input_style1" type="text" name="netUnitCode" value = "<%=aemDTO.getNetUnitCode() %>" style="width:100%"></td>
	    </tr>
	    <tr>	
	    	<td width="15%" align="right">逻辑网络元素</td>
	        <td width="15%"><input type="text" name="logNetEle" class="input_style1" style="width:100%" value="<%=aemDTO.getLogNetEle()%>">
	    	<td width="15%" align="right">是否有效</td>
	        <td width="15%"><select class="select_style1" name="enabled" style="width:50%"><option value="Y">是</option><option value="N" >否</option></select></td>
	        <td align="right" width="15%">成本属性</td>
			<td><select name="costType" >
				<option value="">-请选择-</option>
				<option value="偏短期增量成本">偏短期增量成本</option>
				<option value="偏长期增量成本">偏长期增量成本</option>
				<option value="偏固定成本">偏固定成本</option>
			</select>
			</td>
	    </tr>    
	</table>   
</form>
		
<input type="hidden" name="act">    
<div id="headDiv" style="overflow:hidden;position:absolute;top:115px;left:1px;width:838px">
	<table class="headerTable" border="1" width="100%">
	    <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td width="2%" align="center"><input type="checkbox" name="subCheck" class="headCheckbox" id="controlBox" onclick="checkAll('subCheck','amsLneId')"></td>
	        <%-- <td align=center width="3%">序号</td> --%>
	        <td align=center width="9%">网络专业1</td>
	        <td align=center width="9%">专业1编码</td>
	        <td align=center width="9%">网络专业2</td>
	        <td align=center width="9%">专业2编码</td>
	        <td align=center width="9%">网元编码</td>
	        <td align=center width="9%">逻辑网络元素</td>
	        <td align=center width="9%">英文缩写</td>
	        <td align=center width="10%">成本属性</td>
	        <td align=center width="9%">资产范围</td>
	        <td align=center width="9%">序号</td>
	        <td align=center width="9%">是否有效</td>
		</tr>
    </table>
</div>
	
<div id="dataDiv" style="overflow:scroll;height:75%;width:847px;position:absolute;top:138px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
  <form  name="mainFrm1" method="post" action="/servlet/com.sino.ams.system.manydimensions.servlet.LneServlet">
    <table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
	    <input type="hidden" name="act">
	<%
		RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
		boolean hasData = (rows != null && !rows.isEmpty());
		if (hasData) {
				Row row = null;
				for (int i = 0; i < rows.getSize(); i++) {
					row = rows.getRow(i);
	%>
		<tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="2%" align="center"><input type="checkbox" name="amsLneId" value="<%=row.getValue("AMS_LNE_ID")%>"></td>
			<%-- <td align=center width="3%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ROWNUM")%>"></td> --%>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("NET_CATEGORY1")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("NET_CATEGORY1_CODE")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("NET_CATEGORY2")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("NET_CATEGORY2_CODE")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("NET_UNIT_CODE")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("LOG_NET_ELE")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ENG_AB")%>"></td>
			<td align=center width="10%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("COST_TYPE")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ASSET_RANGE")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ORDER_ID")%>"></td>
			<td align=center width="9%"  onclick="show_Detail('<%=row.getValue("AMS_LNE_ID")%>')"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ENABLED")%>"></td>
		</tr>
	<%
			    }
		}
	%>
    </table>
  </form>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:91%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>

</body>
</html>

<script type="text/javascript">
//调整页面加载时和数据的table框进行调节
function initPage(){
	do_SetPageWidth();
}

function do_Search() {
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function show_Detail(alId){
	mainFrm.alId.value=alId;//修改
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.submit();
}

function do_Create() {
    mainFrm.act.value = "create";
    mainFrm.submit();
}

function do_Delete() {
    var checkedCount = getCheckedBoxCount("amsLneId");
    if (checkedCount < 1) {
        alert("请至少选择一行数据！");
        return;
    } else {
    	if(confirm("是否确定逻辑网络元素属性失效？继续请点击“确定”，否则请点击“取消”按钮")){
    		 mainFrm1.act.value = "<%=WebActionConstant.DELETE_ACTION %>";
    		 mainFrm1.submit();
    	}       
    }
}

</script>
