<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.print.dto.BarcodeMaxMaintainDTO" %>
<html>
<head>
    <title>标签号规则定制</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String orgOption = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    String action = StrUtil.nullToString(request.getParameter("act"));
    BarcodeMaxMaintainDTO bmmDTO = (BarcodeMaxMaintainDTO)reqParser.getAttribute(WebAttrConstant.BARCODE_MAX_MAINTAIN_DTO);
    String allResName = (String) request.getAttribute( WebAttrConstant.ALL_RES_NAME );
    
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.print.servlet.BarcodeMaxMaintainServlet">
    <script language="javascript">
        printTitleBar( "<%= allResName %>" );
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="userId" value="">
    <jsp:include page="/message/MessageProcess"/>
    <table border="0" width="100%"  class="queryTable" id="table1">
        <tr>
            <td width="10%" align="right">公司名称：</td>
            <td width="15%"><select style="width:100%" class="select_style1" name="companyId" onchange="getGroupOpt();"><%=orgOption%></select></td>
            <td width="10%"  align="right">标签类型：</td>
            <td width="15%"  align="right">
            	<select style="width:100%" class="select_style1" name="assetsType">
            	    <option value = "" >--请选择--</option>
            		<option value = "TD" <%="TD".equals(bmmDTO.getAssetsType()) ? "selected" : ""%>>TD资产</option>
            		<option value = "ZY" <%="ZY".equals(bmmDTO.getAssetsType()) ? "selected" : ""%>>自有资产</option>
            		<option value = "ZL" <%="ZL".equals(bmmDTO.getAssetsType()) ? "selected" : ""%>>租赁资产</option>
            		<option value = "DH" <%="DH".equals(bmmDTO.getAssetsType()) ? "selected" : ""%>>低值易耗</option>
            		<option value = "CT" <%="CT".equals(bmmDTO.getAssetsType()) ? "selected" : ""%>>村通资产</option>
            	</select>
            </td>
            <td width="15%"  align="right">
            	<img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Search();" alt="查询">&nbsp;&nbsp;
 				<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出Excel">
            </td>
        </tr>
	</table>
</form>
    <div id="headDiv" style="overflow:hidden;position:absolute;top:48px;left:1px;width:840px">
	    <table class="headerTable" border="1" width="100%">
            <tr height=20px style="cursor:hand" title="点击查看用户信息">
                <td align=center width="8%">公司代码</td>
                <td align=center width="14%">公司名称</td>
                <td align=center width="14%">最大可用标签号</td>
                <td align=center width="10%">标签类型</td>
            </tr>
	    </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	if (hasData) {
			Row row = null;
			for (int i = 0; i < rows.getSize(); i++) {
				row = rows.getRow(i);
%>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("COMPANY_ID")%>', '<%=row.getValue("ASSETS_TYPE")%>', '<%=row.getValue("TAG_SEQ")%>'); return false;">
	            <td height="22" width="8%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("COMPANY_ID")%>"></td>
	            <td height="22" width="14%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("COMPANY_NAME")%>"></td>
	            <td height="22" width="14%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly 
	            	value="<%=row.getValue("TAG_SEQ").toString().length()==1 ? row.getValue("COMPANY_ID")+ "-" + row.getValue("ASSETS_TYPE")+ "00000" + row.getValue("TAG_SEQ") : 
	            	row.getValue("TAG_SEQ").toString().length()==2 ? row.getValue("COMPANY_ID")+ "-" + row.getValue("ASSETS_TYPE")+ "0000" + row.getValue("TAG_SEQ") : 
	            		row.getValue("TAG_SEQ").toString().length()==3 ? row.getValue("COMPANY_ID")+ "-" + row.getValue("ASSETS_TYPE")+ "000" + row.getValue("TAG_SEQ") : 
	            			row.getValue("TAG_SEQ").toString().length()==4 ? row.getValue("COMPANY_ID")+ "-" + row.getValue("ASSETS_TYPE")+ "00" + row.getValue("TAG_SEQ") : 
	            				row.getValue("TAG_SEQ").toString().length()==5 ? row.getValue("COMPANY_ID")+ "-" + row.getValue("ASSETS_TYPE")+ "0" + row.getValue("TAG_SEQ") : 
	            					row.getValue("COMPANY_ID")+ "-" + row.getValue("ASSETS_TYPE")+ row.getValue("TAG_SEQ")
	            			%>">
	            </td>
	            <td height="22" width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ASSETS_TYPE")%>"></td>
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
<div style="position:absolute;top:91%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>
</body>


<script language="javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.print.servlet.BarcodeMaxMaintainServlet";
        mainFrm.submit();
    }

    function do_ShowDetail(companyId, assetsType, tagSeq) {
        mainFrm.action = "/servlet/com.sino.ams.print.servlet.BarcodeMaxMaintainServlet?act=<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action += "&companyId=" + companyId + "&assetsType=" + assetsType + "&tagSeq=" + (parseFloat(tagSeq)-1);
        mainFrm.submit();
    }
    
    function do_Export(){                  //导出execl
	    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	    mainFrm.action = "/servlet/com.sino.ams.print.servlet.BarcodeMaxMaintainServlet";
	    mainFrm.submit();
	}

    function doChecked() {
		do_SetPageWidth();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchUser();
        }
    }
</script>
</html>
