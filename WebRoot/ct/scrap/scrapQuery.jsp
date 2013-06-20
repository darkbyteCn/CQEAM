<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.URLDefineList"%>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>

<%--
  created by Yu
  Date: 2008-12-04
  Time: 13:15:37
--%>
<html>
	<head>
		<title>村通资产报废查询</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script language="javascript" src="/WebLibary/js/Constant.js"></script>
		<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
		<script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
		<script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
		<script language="javascript" src="/WebLibary/js/jslib.js"></script>
		<script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
		<script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
		<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
		<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
	</head>


	<body onkeydown="autoExeFunction('do_search()')">
		<%
			RequestParser parser = new RequestParser();
			parser.transData(request);
			RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
			//boolean hasData = (rows != null && !rows.isEmpty());
			Row row = null;
			
			//String assetsStatusOption = parser.getAttribute("ASSETSSTATUS_OPTION").toString();
			
			String assetsDescription = parser.getParameter("assetsDescription");
			String modelNumber = parser.getParameter("modelNumber");
			String assetNumber = parser.getParameter("assetNumber");
			String tagNumber = parser.getParameter("tagNumber");
		%>
		<form method="post" name="mainFrm">
			<script type="text/javascript">printTitleBar("村通资产报废查询")</script>
			<table width="100%" border="0" class="queryHeadBg">
				<tr>
					<td width="10%" align="right">资产名称：</td>
            		<td width="20%" colspan=2><input name=assetsDescription type=text style="width:80%" value="<%=assetsDescription%>"></td>
            		
					<td width="10%" align="right">资产型号：</td>
            		<td width="20%" colspan=2><input name=modelNumber type=text style="width:80%" value="<%=modelNumber%>"></td>
            		
            		<td width="10%" align="right">资产编号：</td>
            		<td width="20%" colspan=2><input name=assetNumber type=text style="width:80%" value="<%=assetNumber%>"></td>
            	</tr>
            	<tr>	
            		<td width="10%" align="right">资产标签号：</td>
            		<td width="20%" colspan=2><input name=tagNumber type=text style="width:80%" value="<%=tagNumber%>"></td>
            		<!--  
            		<td width="10%" align="right">资产状态：</td>
            		<td align = "lift"><select name = assetsStatus style = "width:100%"><%--=assetsStatusOption--%></select></td>
            		-->
            		<td width="10%" align="right">资产状态：</td>
            		<td width="20%" align = "lift">
            			<select name = assetsStatus style = "width:100%">
            				<option value="">---请选择---</option>
            				<option value="0">正常</option>
            				<option value="1">已失效</option>
            				<option value="2">已经超过使用年限</option>
            			</select>
            		</td>
            		<td width="10%" align="center">
						<img src="/images/eam_images/search.jpg" style="cursor:'hand'"
							onclick="do_search();" alt="查询">
					</td>
					<td>
						<img src="/images/eam_images/export.jpg" id="queryImg"
							style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
					</td>
				</tr>
				<!-- 
				<tr>
					<td align="center">
						<img src="/images/eam_images/search.jpg" style="cursor:'hand'"
							onclick="do_search();" alt="查询">
					</td>
					 
					<td>
						<img src="/images/button/modifySummary.gif" id="particularImg"
							style="cursor:'hand'" onclick="do_showDetail()" alt="详细信息">
					</td>
					
					<td>
						<img src="/images/eam_images/export.jpg" id="queryImg"
							style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
					</td>
					
				</tr>
				-->
			</table>

			<input type="hidden" name="act" value="<%=parser.getParameter("act")%>">


			<script type="text/javascript">
        	var columnArr = new Array("","资产标签号", "资产编号", "资产名称", "资产型号", "资产原值","启用日期","净值","责任人","责任部门","报废日期");
        	var widthArr = new Array("4%","12%", "8%", "10%", "8%","6%","9%","4%","8%","20%","9%");
        	printTableHead(columnArr, widthArr);
    		</script>

			<div style="overflow-y:scroll;left:0px;width:100%;height:350px">
				<table width="100%" border="1" bordercolor="#666666">
					<%
						//if(hasData) {
						if (rows != null && rows.getSize() > 0) {
							for (int i = 0; i < rows.getSize(); i++) {
								row = rows.getRow(i);
					%>
					<tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("TAG_NUMBER")%>')">
						<!--
						<td width="4%" align="center">
							<input type="radio" id="systemid" name="systemid"
								value="<%=row.getValue("TAG_NUMBER")%>">
						</td>
						-->
						<td width="4%" align="center">
							<input type="radio" id="tagNumber" name="tagNumber"
								value="<%=row.getValue("TAG_NUMBER")%>">
						</td>
						<td style="word-wrap:break-word" height="22" width="12%"
							align="center">
							<%=row.getValue("TAG_NUMBER")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="8%"
							align="right">
							<%=row.getValue("ASSET_NUMBER")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="10%"
							align="left">
							<%=row.getValue("ASSETS_DESCRIPTION")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="8%"
							align="center">
							<%=row.getValue("MODEL_NUMBER")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="6%"
							align="right">
							<%=row.getValue("ORIGINAL_COST")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="9%"
							align="center">
							<%=row.getValue("DATE_PLACED_IN_SERVICE")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="4%"
							align="right">
							<%=row.getValue("DEPRN_COST")%>
						</td>
						<td style="word-wrap:break-word" height="22" width="8%"
							align="center">
							<%--=row.getValue("RESPONSIBILITY_USER")--%>
							<%=row.getValue("USER_NAME") %>
						</td>
						<td style="word-wrap:break-word" height="22" width="20%"
							align="center">
							<%--=row.getValue("RESPONSIBILITY_DEPT")--%>
							<input type="text" value="<%=row.getValue("DEPT_NAME") %>" readonly="readonly" style="width: 100%" class="finput">
						</td>
						<td style="word-wrap:break-word" height="22" width="9%"
							align="center">
							<%=row.getValue("RETIRE_DATE")%>
							<!--  
							<input type="text" value="" readonly="readonly" size="8" style="width: 100%">
							-->
						</td>
					</tr>
					<%
							}
						}
					%>
				</table>
			</div>

		</form>
		
		<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
		</div>
		<%=WebConstant.WAIT_TIP_MSG%>
		<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true" />
	</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.QRY_ETS_FA_ASSETS_SERVLET%>";
        //window.alert('数百米特');
        mainFrm.submit();
    }
	/*
    function do_showDetail() {
      var barcode = getRadioValue("systemid");

        if (barcode != "") {
            var url = "/servlet/com.sino.ams.newasset.servlet.AmsItemInfoHistoryServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
            var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
            window.open(url, 'historyWin', style);
        }
    }
	*/
	
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.QRY_ETS_FA_ASSETS_SERVLET%>";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
    }
    
    function do_SelectProj() {
        var lookUpProj = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var projs = getLookUpValues(lookUpProj, dialogWidth, dialogHeight);
        if (projs) {
            var proj = null;
            for (var i = 0; i < projs.length; i++) {
                proj = projs[i];
                dto2Frm(proj, "mainFrm");
            }
        }
    }
    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
        var dialogWidth = 50;
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
function do_ShowDetail(tagNumber) {
    var url = "/servlet/com.sino.ams.ct.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&tagNumber=" + tagNumber;
    var winName = "faAssetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
}
</script>
