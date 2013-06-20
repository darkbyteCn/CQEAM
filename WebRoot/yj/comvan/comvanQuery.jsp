<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.yj.constant.YJWebAttribute" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>

<%@ include file="/newasset/headerInclude.htm" %>

<html>
<head>
    <title>应急通信车信息维护</title>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onkeydown="autoExeFunction('do_search()');">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
<%//=WebConstant.EXPORT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String formAction="/servlet/com.sino.ams.yj.comvan.servlet.AmsYjComvanServlet";
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String orgOpt = (String) request.getAttribute(YJWebAttribute.ORG_OPTION);
	boolean hasData = (rows != null && !rows.isEmpty());
    Row row = null;
    String deptName = parser.getParameter("deptName");
%>
<form method="post" name="mainFrm"   action="<%=formAction%>">
<script type="text/javascript">
    printTitleBar("应急通信车信息维护");
</script>
<table width="100%" border="0" id="table1">
    <tr>
        <td width="12%">公司名称：</td>
        <td width="25%"><select class="select_style1" style="width:80%" name="organizationId" width="100%"><%=orgOpt%></select> </td>
        <td width="12%">储备单位：</td>
        <td width="25%"><input class="input_style1" style="width:100%" type="text" name="deptName" width="100%" value="<%=deptName%>"> </td>
		<td  align="right" nowrap="nowrap">
			<img src="/images/eam_images/search.jpg" style="cursor:hand" onclick="do_search();" title="查询">
			<img src="/images/eam_images/new.jpg" style="cursor:hand" onClick="do_new();" title="新增">
			<img src="/images/eam_images/delete.jpg" style="cursor:hand" onClick="do_delete();" title="删除">
			<img src="/images/eam_images/export.jpg" style="cursor:hand" onClick="do_export();" title="导出">
		</td>
    </tr>
</table>

<input type="hidden" name="act">
<input type="hidden" name="isExp" value="0">
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:150%">
        <table  border="1" style="color: #FFFFFF" bgcolor="2390E3" width="300%" height="40">
        <tr height="20">
            <td width="1%" align="center" rowspan="2">&nbsp;</td>
            <td width="2%" align="center" rowspan="2">序号</td>
            <td width="3%" align="center" rowspan="2">储备单位</td>
            <td width="33%" align="center" colspan="11">车体及改装</td>
            <td width="18%" align="center" colspan="4">BTS</td>
            <td width="4%" align="center" rowspan="2">是否安装BSC</td>
            <td width="6%" align="center" rowspan="2">安装的其他GSM系统网元</td>
            <td width="13%" align="center" colspan="4">传输</td>
            <td width="5%" align="center" rowspan="2">可提供业务种类</td>
            <td width="14%" align="center" colspan="3">近3年内的使用情况</td>
            <td width="3%" align="center" rowspan="2">其他说明</td>
        </tr>
        <tr height="20">
            <td width="2%" align="center">生产商</td>
            <td width="4%" align="center">型号</td>
            <td width="5%" align="center">车辆改装厂</td>
            <td width="3%" align="center">整车长度(mm)</td>
            <td width="3%" align="center">整备质量(吨)</td>
            <td width="3%" align="center">天线桅杆形式</td>
            <td width="3%" align="center">是否有油机</td>
            <td width="2%" align="center">现有车牌照</td>
            <td width="2%" align="center">车架号</td>
            <td width="2%" align="center">长×宽×高(mm)</td>
            <td width="2%" align="center">资产原值(万元)</td>
            
            <td width="4%" align="center">厂家</td>
            <td width="8%" align="center">型号</td>
            <td width="3%" align="center">载频分配</td>
            <td width="3%" align="center">总载频数</td>

            <td width="3%" align="center">传输方式</td>
            <td width="6%" align="center">设备型号</td>
            <td width="2%" align="center">带宽</td>
            <td width="2%" align="center">是否有卫星传输</td>

            <td width="3%" align="center">使用次数</td>
            <td width="5%" align="center">使用时提供的业务</td>
            <td width="6%" align="center">主要使用场景及地点</td>

        </tr>
    </table>
    <%--</table>--%>
</div>

<div id="dataDiv" style="overflow:scroll;height:67%;width:150%;position:absolute;top:96px;left:1px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="300%" border="1"  style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (hasData) {
		String objectNo = "";
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
            objectNo=row.getStrValue("COMVAN_ID");
%>
        <tr height="22" style="cursor:hand" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'">
            <td width="1%"><input type="checkbox" name="comvanIds" value="<%=row.getValue("COMVAN_ID")%>"> </td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("COMVAN_ID")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>

            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("MANUFACTURER")%>"></td>
            <td width="4%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("MODEL")%>"></td>
            <td width="5%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("REFIT_FIRM")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("LENGTH")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("QUALITY")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ANTENNA_MAST_FORM")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("HAS_OILENGINE")%>"></td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("LICENSE_PLATE")%>"></td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("FRAME_NUMBER")%>"></td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("L_W_H")%>"></td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ORIGINAL_COST")%>"></td>

            <td width="4%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BTS_MANUFACTURER")%>"></td>
            <td width="8%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BTS_MODEL")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("CARRIER_FREQUENCYV_ALLOCATE")%>"></td>
            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("CARRIER_FREQUENCYV_QTY")%>"></td>

            <td width="4%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("INSTALLED_BSC")%>"></td>
            <td width="6%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("OTHER_GSM_UNIT")%>"></td>

            <td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("TRANS_FORM")%>"></td>
            <td width="6%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("TRANS_ITEM_MODEL")%>"></td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BANDWIDTH")%>"></td>
            <td width="2%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("HAS_SATELLITE_TRANSMISSIONS")%>"></td>
            
			<td width="5%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("TYPE_OF_TRAFFIC")%>"></td>
            
			<td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("USE_TIMES")%>"></td>
			<td width="5%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("USED_TRAFFIC")%>"></td>
			<td width="6%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("USE_SCENE")%>"></td>
			<td width="3%" onclick="show_detail('<%=objectNo%>');"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("REMARK")%>"></td>
		</tr>
<%
	    }
	}
%>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div id="navigatorDiv" style="position:absolute;bottom:0px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>

<%--</fieldset>--%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script type="text/javascript">
	function initPage(){
		do_SetPageWidth();
	}

	function do_search() {
	    document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	    document.mainFrm.submit();
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	}
	
	function show_detail(comvanId) {
	    var url = "<%=formAction%>";
		url += "?act=<%=WebActionConstant.DETAIL_ACTION%>";
		url += "&comvanId=" + comvanId;
	    var popscript = "width=840,height=550,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
	    window.open(url, "commObj", popscript);
	}
	
	function do_new() {
	    var url = "<%=formAction%>";
		url += "?act=<%=WebActionConstant.NEW_ACTION%>";
	    var popscript = "width=840,height=550,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
	    window.open(url, "commObj", popscript);
	}

	 function do_delete(){
	     document.mainFrm.act.value="<%=WebActionConstant.DELETE_ACTION%>";
	     document.mainFrm.submit();
	 }
	
	 function do_export(){
	     document.mainFrm.act.value="<%=WebActionConstant.EXPORT_ACTION%>";
	     document.mainFrm.submit();
	 }

</script>