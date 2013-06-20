<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%--
  User: 李轶
  Date: 2009-5-18
  Time: 16:55:29
  JSP Name:		assetsDHSpecialReport.jsp
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
<title>专业资产构成分布(重要低耗)</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.DHSpecialAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    var specialAssetsType = "<%=dto.getSpecialAssetsType()%>";
    printTitleBar("资产基础报表>>专业资产构成分布(重要低耗)")
</script>


    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="20%" align="left">
                <select class="select_style1" name="organizationId" style="width:75%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			<td width="10%" align="right">目录：</td>
			<td width="30%" align="left">
                <input type="text" name="contentName"  onClick="do_SelectContent();" class="input_style2" value="<%=dto.getContentName()%>" style="width:90%" readonly title="点击选择目录">
                <input type= "hidden" name = "contentCode" value = "">
            </td>
			<td width="10%" align="right"></td>
			<td width="20%" align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>

    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="specialAssetsType" value="<%=dto.getSpecialAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="200%" height="40">
		<tr height="20">
			<td width="7%" align="center" rowspan="2" height="10">类</td>
			<td width="12%" align="center" rowspan="2" height="10">项</td>
            <td width="6%" align="center" rowspan="2" height="10">原值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">净值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净额</td>
            
            <td width="6%" align="center" rowspan="2" height="10">当期折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">资产数量</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总数比重</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" height="10" rowspan="2">比上月增长率</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="15%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="5%" align="center" height="5">2006</td>
            <td width="5%" align="center" height="5">2007</td>
            <td width="5%" align="center" height="5">2008</td>
        </tr>
    </table>
</div>
			
<div id="dataDiv" style="overflow:scroll;height:340px;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
        <tr height="22" <% if (bkColor){%> bgcolor="YELLOW" <%}%> >
			<td width="7%"><%=isNew?"":ASSETS_SPECIES%></td>
			<td width="12%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
            <td width="6%" align="right"></td>
			<td width="6%" align="right"></td>
            <td width="6%" align="right"></td>
            <td width="6%" align="right"></td>
			<td width="6%" align="right"></td>
            <td width="6%" align="right"></td>
			<td width="6%" align="right"><%=row.getValue("SUM_NAPE")%></td>
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE")%></td>
            <td width="6%" align="right"></td>
            <td width="6%" align="right"></td>
            <td width="6%" align="right"></td>

            <td width="5%" align="right"></td>
            <td width="5%" align="right"></td>
            <td width="5%" align="right"></td>
        </tr>
<%
            }
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:430px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
	mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	var selObj = mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	mainFrm.companyName.value = companyName;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    mainFrm.target = "assWin";
    mainFrm.submit();
}
function do_SelectContent() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
            mainFrm.contentName.value = "";
            mainFrm.contentCode.value = "";
        }
}
</script>