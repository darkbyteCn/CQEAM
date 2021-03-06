<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.rent.dto.RentDTO" %>
<%@ page import="java.math.BigDecimal"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Author:		李轶
  Date: 2008-5-25
  Time: 15:57:33
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
	<title>经营租赁到期资产</title>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String organizationId = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    RentDTO dto = (RentDTO) request.getAttribute("RENT_QUERY_DTO");
%>

<body onkeydown="autoExeFunction('do_search()');" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.rent.servlet.RentServlet?accessType=AMS_RENT_INFO" name="mainFrm" method="post">
    <script type="text/javascript">
        printTitleBar("经营租赁到期资产")
    </script>
<table  border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="8%" align="right">公司：</td>
            <td width="17%" ><select class="select_style1" style="width:85%"  name="organizationId" ><%=organizationId%></select></td>
            <td width="8%" align="right">资产条码：</td>
            <td width="17%" align="left"><input class="input_style1" type="text" name="barcode" style="width:85%" value="<%=dto.getBarcode()%>"></td>
            <td width="10%" align="right">资产名称：</td>
            <td width="17%" align="left"><input class="input_style1" type="text" name="itemName" style="width:85%" value="<%=dto.getItemName()%>"></td>
            <td width="8%" align="right">规格型号：</td>
            <td width="17%" align="left"><input class="input_style1" type="text" name="itemSpec" style="width:80%" value="<%=dto.getItemSpec()%>"></td>
        </tr>
        <tr>
            <td width="8%" align="right">责任人：</td>
            <td width="17%" align="left">
                <input class="input_style1" type="text" name="username" style="width:85%" value="<%=dto.getUsername()%>">
            </td>
            <td width="8%" align="right">使用部门：</td>
            <td width="17%" align="left"><input class="input_style1" type="text" name="maintainDeptName" style="width:85%"
                                                value="<%=dto.getMaintainDeptName()%>">
            <td width="8%" align="right">资产地点：</td>
            <td width="17%" align="left"><input class="input_style1" type="text" name="addressloc" style="width:85%"
                                                value="<%=dto.getAddressloc()%>"><a class="linka" style="cursor:'hand'" onclick="lookAddressId();">[…]</a>
            <td width="8%" align="right">剩余租期：</td>
            <td width="17%">
            	<input class="input_style1" type="text" name="toRentTime" style="width:80%" value="<%=dto.getToRentTime()%>"> 月
            </td>
        </tr>
        <tr>
            <td width="8%" align="right">租赁日期：</td>
            <td width="17%" align="left"><input  type="text" name="fromDate" style="width:85%"   class="input_style2"
                                                value="<%=dto.getFromDate()%>" alt="点击选择开始日期"  onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td width="8%" align="right">至：</td>
            <td width="17%" align="left"><input type="text" name="toDate" style="width:85%"   class= "input_style2"
                                                value="<%=dto.getToDate()%>" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            <td width="8%" align="right"></td>
            <td width="17%">
            	
            </td>
            <td width="25%" align="right" colspan = "2">
            	<img src="/images/eam_images/search.jpg" alt="查询租赁信息" onClick="do_search(); ">&nbsp;&nbsp;&nbsp;
            	<img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export();">&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
</form>
     <div id="headDiv" style="overflow:hidden;position:absolute;top:105px;left:1px;width:300%">
   	  <table  border="1" width="300%" class="eamHeaderTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="1%" align="center">序号</td>
            <td width="3%" align="center">公司</td>
            <td width="3%" align="center">资产标签</td>
            <td width="4%" align="center">资产名称</td>
            <td width="2%" align="center">规格型号</td>
            <td width="2%" align="center">单位</td>
            
            <td width="6%" align="center">生产厂商名称</td>
            <td width="2%" align="center">额定功率</td>
            <td width="3%" align="center">设备性能</td>
            <td width="7%" align="center">资产类别代码组合</td>
            <td width="7%" align="center">资产类别描述</td>
            <td width="3%" align="center">责任人编号</td>
            <td width="3%" align="center">责任人姓名</td>
            <td width="10%" align="center">资产地点</td>
            
            <td width="2%" align="center">使用人</td>
            <td width="7%" align="center">使用部门</td>
            
            <td width="3%" align="center">起始日期</td>
            <td width="3%" align="center">到期时间</td>
            
            <td width="7%" align="center">出租方</td>
            
            <td width="2%" align="center">租期(年)</td>
            <td width="2%" align="center">月租金</td>
            <td width="2%" align="center">年租金(元)</td>            
            <%--<td width="3%" align="center">较去年同期增长率</td>--%>
        </tr>
      </table>
    </div>

	<div id="dataDiv" style="overflow:scroll;height:280px;width:300%;position:absolute;top:129px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="300%" border="1" bordercolor="#666666" id="dataTab" >
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        	    boolean hasData = (rows != null && !rows.isEmpty());
                if (hasData) {
                    Row row = null;
                    Double sumMonthRental = 0d;
                    Double sumYearRental = 0d;
                    Float sumLastYearRate = 0f;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="show_detail('<%=row.getValue("BARCODE")%>')"
                onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="1%" align="center"><%=row.getValue("ROWNUM")%></td>
                <td width="3%" align= "center"><%=row.getValue("COMPANY")%></td>
                <td width="3%" align= "center"><%=row.getValue("BARCODE")%></td>
                <td width="4%" align= "center"><%=row.getValue("ITEM_NAME")%></td>
                <td width="2%" align= "center"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="2%" align= "center"><%=row.getValue("ITEM_UNIT")%></td>
                
                <td width="6%" align= "center"><%=row.getValue("MANUFACTURER_NAME")%></td>
                <td width="2%" align= "center"><%=row.getValue("POWER")%></td>                
                <td width="3%" align= "center"><%=row.getValue("OTHER_INFO")%></td>
                <td width="7%" align= "center"><%=row.getValue("CONTENT_CODE")%></td>
                <td width="7%" align= "center"><%=row.getValue("CONTENT_NAME")%></td>
                <td width="3%" align= "center"><%=row.getValue("RESPONSIBILITY_USER")%></td>
                <td width="3%" align= "center"><%=row.getValue("USER_NAME")%></td>
                <td width="10%"  align= "center"><%=row.getValue("OBJECT_NAME")%></td>
            
                <td width="2%" align= "center"><%=row.getValue("MAINTAIN_USER")%></td>
                <td width="7%" align= "center"><%=row.getValue("MAINTAIN_DEPT")%></td>
                
                <td width="3%" align="center"><%=row.getValue("RENT_DATE")%></td>
                <td width="3%" align="center"><%=row.getValue("END_DATE")%></td>
                
                <td width="7%" align= "center"><%=row.getValue("RENT_PERSON")%></td>
                
                <td width="2%" align="right"><%=row.getValue("TENANCY")%></td>
                <td width="2%" align="right"><%=row.getValue("YEAR_RENTAL")%> </td>
                <td width="2%" align="right"><%=row.getValue("MONTH_REANTAL")%></td>
                <%--<td width="3%" align="right">0%</td>--%>
            </tr>
            <%
		            if(row.getValue("YEAR_RENTAL") != null && !"".equals(row.getValue("YEAR_RENTAL"))){
		            	sumYearRental += Double.parseDouble(row.getValue("YEAR_RENTAL").toString());
					}
		            if(row.getValue("MONTH_REANTAL") != null && !"".equals(row.getValue("MONTH_REANTAL"))){
		            	sumMonthRental += Double.parseDouble(row.getValue("MONTH_REANTAL").toString());
					}
					//if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
					//	sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
					//}
                 }
            %>
       		<tr>
       			<td colspan = "20" height= "22" align = "left">合计：</td>
       			<td width="2%" align="right"><%=BigDecimal.valueOf(sumMonthRental).toString().length() - BigDecimal.valueOf(sumMonthRental).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumMonthRental).toString().substring(0, BigDecimal.valueOf(sumMonthRental).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumMonthRental) %></td>	
       			<td width="2%" align="right"><%=BigDecimal.valueOf(sumYearRental).toString().length() - BigDecimal.valueOf(sumYearRental).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumYearRental).toString().substring(0, BigDecimal.valueOf(sumYearRental).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumYearRental) %></td>
       			<%--<td width="2%"  align="right">0%--%><%--<%=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+4) :sumRate %>--%><%--</td>  --%>
       		</tr>
            <%	} %>
        </table>
    </div>


<%
    if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:94%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<%--<iframe name="downFrm" src="" style="display:none"></iframe>--%>

<script type="text/javascript">
	function initPage(){
		do_SetPageWidth();
		do_TransData();
	}

	function do_TransData(){
		var transTarget = null;
		if(!parent.updateDataFrm.document){
			return;
		}
		if(!parent.updateDataFrm.document.mainFrm){
			return;
		}
		if(!parent.updateDataFrm.document.mainFrm.checkedData){
			return;
		}
		transTarget = parent.updateDataFrm.document.mainFrm.checkedData;
		if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
			transTarget.value = "";
		} else {
			var barcodes = new String(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value);
			barcodes = replaceStr(barcodes, "BARCODE:", "");
			barcodes = replaceStr(barcodes, "$$$", "  ");
			parent.updateDataFrm.document.mainFrm.checkedData.value = barcodes;
		}
	}

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(barcode) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.RentServlet?barcode=" + barcode;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

	var clickNum = 0;
    function do_export() {
    	clickNum++;
    	if(clickNum > 1){
    		alert("已执行导出命令，请您不要重复点击！");
    		return;
    	}
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
    }


    function lookAddressId() {    //查找地点
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (projects) {
            var user = null;
            for (var i = 0; i < projects.length; i++) {
                mainFrm.addressloc.value = projects[0].workorderObjectName;
            }
        }
    }

</script>