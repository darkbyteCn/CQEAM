<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="srv.ams.inquiryassetlocation.dto.SrvAssetLocationDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>MIS资产地点查询</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	SrvAssetLocationDTO dto = (SrvAssetLocationDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/srv.ams.inquiryassetlocation.servlet.SearchLocationServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("MIS资产地点查询")
</script>
	<table width="100%" border="0" class="queryHeadBg">

		<tr>
            <td width="5%" align="right">组织代码：</td>
			<td width="5%">
				<select name="organizationId" id="organizationId" style="width:100%" size="1"><%=dto.getOrgOption()%></select>
			</td>
			<td width="20%" align="left" colspan="6"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="left" onclick="do_Search();"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="8%" align="center">资产账簿代码</td>
            <td width="20%" align="center">资产地点描述</td>
            <td width="6%" align="center">创建日期</td>
            <td width="6%" align="center">创建人</td>
            <td width="6%" align="center">上次修改日期</td>
            <td width="6%" align="center">上次修改人</td>
            <td width="6%" align="center">组织ID</td>
            <td width="15%" align="center">地点组和代码</td>
            <td width="6%" align="center">是否有效</td>

		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:360px;width:857px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="23">
			<td width="8%" align="center"><%=row.getValue("BOOK_TYPE_CODE")%></td>
            <td width="20%" align="right"><%=row.getValue("ASSETS_LOCATION")%></td>
            <td width="6%" align="right"><%=row.getValue("CREATION_DATE")%></td>
			<td width="6%" align="right"><%=row.getValue("CREATED_BY")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_UPDATE_DATE")%></td>
			<td width="6%" align="right"><%=row.getValue("LAST_UPDATE_BY")%></td>
            <td width="6%" align="right"><%=row.getValue("ORG_ID")%></td>
            <td width="15%" align="right"><%=row.getValue("ASSETS_LOCATION_CODE")%></td>
            <td width="6%" align="right"><%=row.getValue("ENABLED_FLAG")%></td>

		</tr>
<%
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:468px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}


    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50.5;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }
        }
  function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);

    if (objs) {
        var obj = objs[0];
		dto2Frm(obj, "mainFrm");
		mainFrm.workorderObjectName.value = obj["workorderObjectLocation"];
	} else {
        mainFrm.workorderObjectName.value = "";
    }
}
</script>