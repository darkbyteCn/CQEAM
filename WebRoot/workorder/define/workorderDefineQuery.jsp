<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.workorderDefine.dto.WorkorderDefineDTO"%>
<html>
<head>
    <title>巡检自定义</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>

<body leftmargin="1" topmargin="0" onload="do_SetPageWidth()" onkeydown="do_check()">
<%
    RequestParser parser = new RequestParser();
	parser.transData(request);
    WorkorderDefineDTO dto = (WorkorderDefineDTO) parser.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>

<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet">
<script type="text/javascript">
    printTitleBar("巡检自定义")
</script>
    <jsp:include page="/message/MessageProcess"/>
    <table width="100%" border=0 cellpadding="2" cellspacing="0">
    <input type="hidden" name="act" value="">
    <tr>
        <td>
            <table width="99%" align='right' bordercolor="#666666" bordercolor="#666666" border=0>
                <tr>
                    <td align="right" width="6%"> 公司：</td>
                    <td width="15%">
                        <select style="width:100%"  disabled   class="select_style1"
                                name="organizationId"><%=dto.getOrganizationName() %></select>                      
                    </td>
                    <td align="right" width="8%">地点专业：</td>
                    <td width="20%"><select class='select_style1' name="objectCategory" style="width:100%"  ><%=dto.getObjectCategoryOption()%></select></td>
                    <td align="right" width="9%">成本中心：</td>
                    <td width="15%"><select class='select_style1' name="costCenterCode" style="width:100%"  ><%=dto.getCostCenterName()%></select></td>                    
                    <td align="right" width="6%"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"
                                                                               onClick="do_Search(); return false;"></a>
                    <td align="right" width="6%"><a style="cursor:'hand'"><img src="/images/eam_images/new.jpg"
                                                                               onClick="do_Create(); return false;"></a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script type="text/javascript">
    var columnArr = new Array("公司名称", "地点专业", "市" ,"所属区县", "成本中心", "执行人", "归档人", "巡检周期", "是否有效", "上次执行日期", "创建日期");
    var widthArr = new Array("12%", "8%", "8%", "8%", "12%", "6%", "6%", "6%", "4%", "6%", "6%");
    printTableHead(columnArr, widthArr);
</script>
<%
    if (rows != null && !rows.isEmpty()) {
%>
    <div id="dataDiv" style="overflow-y:scroll;height:300px;width:100%;left:1px;margin-left:0px;" align="left" onscroll="document.getElementById('headDiv').scrollLeft=this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" style="table-layout:fixed;">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" height="22" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_DEFINE_ID")%>'); return false;">
            <td width="12%"><%=row.getValue("COMPANY")%></td>
            <td width="8%"><%=row.getValue("OBJECT_CATEGORY")%></td>
            <td width="8%"><%=row.getValue("CITY_NAME")%></td>
            <td width="8%"><%=row.getValue("COUNTY_NAME")%></td>
            <td width="12%"><%=row.getValue("COST_CENTER_CODE_NAME")%></td>
            <td width="6%"><%=row.getValue("IMPLEMENT_BY")%></td>
            <td width="6%"><%=row.getValue("CHECKOVER_BY")%></td>            
			<td width="6%"><%=row.getValue("WORKORDER_CYCLE")%></td> 
			<td width="4%"><%=row.getValue("ENABLED")%></td> 
			<td width="6%"><%=row.getValue("WORKORDER_EXEC_DATE")%></td> 
			<td width="6%"><%=row.getValue("CREATION_DATE")%></td> 
            <%
                }   }
            %>
    </table>
</div>
 </form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">
    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
    
    function do_Create() {
    	mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    	mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_DEFINE_ID)
    {
        mainFrm.action = "/servlet/com.sino.ams.workorderDefine.servlet.WorkorderDefineServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&workorderDefineId="+WORKORDER_DEFINE_ID;
    	mainFrm.submit();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_Search();
        }
    }
    
    function showBatch() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BATCH%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Batchs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Batchs) {
            var Batch = null;
            for (var i = 0; i < Batchs.length; i++) {
                Batch = Batchs[i];
                dto2Frm(Batch, "mainFrm");
            }
        }
    }
</script>
