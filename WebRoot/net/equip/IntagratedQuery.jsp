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
<%@ page import="com.sino.ams.net.equip.dto.IntadgratedQueryDTO" %>
<%@ page import="com.sino.ams.net.equip.dto.IntegratedDTO"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsURLList"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>


<html>
<head>
	<title>设备信息综合查询</title>
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
<body leftmargin="1" topmargin="1" onkeydown="autoExeFunction('do_CommonQuery()')">
<%
	DTOSet fields = (DTOSet)request.getAttribute(AssetsWebAttributes.COMM_QUERY_HEAD);
	DTOSet datas = (DTOSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if (datas != null && !datas.isEmpty()){
		hasData = true;
	}
	String headerDivTopPx = (String)request.getAttribute(AssetsWebAttributes.HEADER_DIV_TOP);
	String dataDivTopPx = (String)request.getAttribute(AssetsWebAttributes.DATA_DIV_TOP);
	String tableWidthPx = (String)request.getAttribute(AssetsWebAttributes.TABLE_WIDTH);
	String tdWidthPx = (String)request.getAttribute(AssetsWebAttributes.TD_WIDTH);
	String dataDivHeightPx = (String)request.getAttribute(AssetsWebAttributes.DATA_DIV_HEIGHT);
%>
<form name="mainFrm" action="/servlet/com.sino.ams.net.equip.servlet.IntegratedServlet" method="post">
  <script type="text/javascript">
        printTitleBar("设备信息综合查询")
  </script>
<%=request.getAttribute(AssetsWebAttributes.COMM_QUERY_PARA)%>
<input type="hidden" name="act" value="">
<br>
<img src="/images/eam_images/search.jpg" onclick="do_CommonQuery()">&nbsp;
<img src="/images/eam_images/back.jpg"  onClick="do_fandin();">
<br>
<div id="headDiv" style="overflow:hidden;position:absolute;left:1px;width:845px">
	<table class="headerTable" border="1" width="<%=tableWidthPx%>">
		<tr height="20px">
<%
	IntegratedDTO fieldDTO = null;
	int fieldCount = fields.getSize();
	for(int i = 0; i < fieldCount; i++){
		fieldDTO = (IntegratedDTO)fields.getDTO(i);
%>
			<td align=center width="<%=tdWidthPx%>"><%=fieldDTO.getFieldDesc()%></td>
<%
	}
%>
		</tr>
	</table>
</div>
    <br><br>
<%
	IntadgratedQueryDTO data = null;
	String fieldName = "";
	if (hasData){
%>
<div style="overflow:scroll;height:300px;width:845px;position:absolute;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="<%=tableWidthPx%>" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
		for(int i = 0; i < datas.getSize(); i++){
			data = (IntadgratedQueryDTO)datas.getDTO(i);
%>
		<tr class="dataTR" onclick="do_ShowDetail('<%=ReflectionUtil.getProperty(data, "barcode")%>')">
<%
			for(int j = 0; j < fieldCount; j++){
				fieldDTO = (IntegratedDTO)fields.getDTO(j);
				fieldName = fieldDTO.getFieldName();
				fieldName = StrUtil.getJavaField(fieldName);
%>
			<td align=center width="<%=tdWidthPx%>"><input readonly type="text" style="width:100%; border: 1px solid #FFFFFF" value="<%=ReflectionUtil.getProperty(data, fieldName)%>"></td>
<%
			}
%>
		</tr>
<%
		}
%>
	</table>
</div>
<%
	}
%>
</form>
<%
	if (hasData){
%>
<div style="position:absolute;top:450px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div></body>
<%
	}
%>

</html>
<script type="text/javascript">
function do_CommonQuery(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.net.equip.servlet.IntagratedQueryServlet";
    mainFrm.submit();
}
function do_fandin(){
    mainFrm.action = "/servlet/com.sino.ams.net.equip.servlet.IntegratedServlet";
    mainFrm.submit();
}
function do_ShowDetail(barcode) {
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
}
</script>
