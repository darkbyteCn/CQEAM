<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.newasset.constant.*" %>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.*"%>
<%@ page import="com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<%
	AssetSharingHeaderDTO dto = (AssetSharingHeaderDTO)request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String action = request.getAttribute("action")==null?AssetsActionConstant.QUERY_ACTION:request.getAttribute("action").toString();
	String isPrint=request.getAttribute("isPrint")==null?"":request.getAttribute("isPrint").toString();
    String transType = dto.getTransType();                                
    String title=request.getAttribute("title")==null?AssetSharingConstant.SHARE_TITLE1:request.getAttribute("title").toString();

%>
<head>
	<title><%=title%></title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>

<body onkeydown="autoExeFunction('do_SearchOrder()');" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="<%=AssetSharingConstant.ASSET_SHARE_SERVLET%>" name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("<%=title%>")
    </script>
 <input type="hidden" name="act" value="">
 <input type="hidden" name="transType" value="<%=transType%>"> 

    <table border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="10%" align="right">单据编号：</td>
            <td width="20%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="10%" align="right">创建日期：</td>
            <td width="30%">
				<input class="input_style1" type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(startDate, endDate);">
				<input class="input_style1" type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(startDate, endDate);">
            </td>
            <td width="25%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
			<img src="/images/eam_images/export.jpg" title="点击导出" onclick="do_ExportOrder();">
			</td>
     </tr>
    </table>
</form>

    <div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:49px;left:0px;width:100%">
   	  <table  border="1" width="100%" class="eamHeaderTable" cellpadding="0" cellspacing="0">
        <tr class="eamHeaderTable" height="23px">
            <td align=center width="18%">单据编号</td>
            <td align=center width="10%">单据状态</td>
            <td align=center width="16%">申请部门</td>
            <td align=center width="12%">申请人</td>
            <td align=center width="10%">申请日期</td>
	        </tr>
      </table>
    </div>

	<div id="dataDiv" style="overflow:scroll;height:72%;width:100%;position:absolute;top:71px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab" >
            <%
            RowSet sets = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                if (sets != null && !sets.isEmpty()) {
                    for (int i = 0; i < sets.getSize(); i++) {
                       Row row=sets.getRow(i);
            %>
            <tr class="dataTR" title="点击打开该单据" onclick="showDetail('<%=row.getStrValue("TRANS_ID")%>')">
              <td width="18%"><input type="text" class="finput2" readonly value="<%=row.getStrValue("TRANS_NO")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getStrValue("TRANS_STATUS_DESC")%>"></td>
              <td width="16%"><input type="text" class="finput" readonly value="<%=row.getStrValue("COMPANY")%>"></td>
              <td width="12%"><input type="text" class="finput" readonly value="<%=row.getStrValue("USER_NAME")%>"></td>
              <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getStrValue("CREATION_DATE")%>"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
<%
    if (sets != null && !sets.isEmpty()) {
%>
		<div id="pageNaviDiv" style="position:absolute;top:87%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
}

function do_SearchOrder() {
	if(mainFrm.transferType){
		mainFrm.transferType.disabled = false;
	}
    mainFrm.act.value = "<%=action%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function showDetail(transId){
    var transType = mainFrm.transType.value;
    var url = "<%=AssetSharingConstant.ASSET_SHARE_SERVLET%>?act=<%=AssetSharingConstant.QUERY_DETAIL%>&transType=" + transType+"&transId="+transId;
    var isPrint="<%=isPrint%>";
    if(isPrint!=''){
    	url += "&isPrint=Y";
    }
    var factor = 0.92;
    var width = window.screen.availWidth * factor;
    var height = window.screen.availHeight * factor;
    var left = window.screen.availWidth * (1 - factor)/ 2;
    var top = window.screen.availHeight * (1 - factor)/ 10;
    var style = "width="
            + width
            + "px,height="
            + height
            + "px,top="
            + top
            + "px,left="
            + left
            + "px,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    var newWin = window.open(url, 'printWin', style);
	newWin.focus();
}

function do_ExportOrder(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

</script>