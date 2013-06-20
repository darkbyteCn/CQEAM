<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-2
  Time: 22:13:29
  To change this template use File | Settings | File Templates.
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
<title>专业资产构成报表</title>
 </head>
<body style="left:0;top:0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
	SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.SpecialAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    var specialAssetsType = "<%=dto.getSpecialAssetsType()%>";
    if (specialAssetsType == "LOSE") {
        printTitleBar("资产基础报表>>专业资产构成分布(盘亏资产)")
    } else if (specialAssetsType == "SPECIAL_SPARE") {
        printTitleBar("资产基础报表>>专业资产构成分布(备品备件)")
    } else if (specialAssetsType == "ASSETS_TRANS") {
        printTitleBar("资产基础报表>>专业资产构成分布(调拨)")
    } else {
        printTitleBar("资产基础报表>>专业资产构成分布")
    }
</script>
<%
if (dto.getSpecialAssetsType().equals("LOSE")) {
%>
    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="30%" align="left">
                <select name="organizationId" class="select_style1" style="width:50%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="10%" align="right">会计期间：</td>
            <td width="20%"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>-<select class="select_style1" style="width:40%" name="month"><%=monthOption%></select></td>
            <td width = "20%" align="right" >
                <img src="/images/eam_images/search.jpg"  onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%
} else {
%>
    <table width="100%" border="0">
		<tr>
            <td width="7%" align="right">公司：</td>
			<td width="18%" align="left">
                <select name="organizationId" class="select_style1" style="width:70%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="7%" align="right">目录：</td>
			<td width="20%" align="left">
                <input class="input_style2" type="text" name="contentName" value="<%=dto.getContentName()%>" style="width:80%" readonly>
                <a href="#" onClick="do_SelectContent();" title="点击选择目录" class="linka">[…]</a>
                <input type= "hidden" name = "contentCode" value = "">
            </td>
            <td width="8%" align="right">会计期间：</td>
            <td width="14%"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:40%" name="month"><%=monthOption%></select></td>
			<td width="15%" align = "right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%}%>
    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="specialAssetsType" value="<%=dto.getSpecialAssetsType()%>">
</form>

<%
if(dto.getSpecialAssetsType().equals("SPECIAL_SPARE")){
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="130%" height="40">
	  <tr height="20">
			<td width="15%" align="center" rowspan="2" height="10">类</td>
			<td width="10%" align="center" rowspan="2" height="10">项</td>
            <td width="5%" align="center" rowspan="2" height="10">数量</td>
            
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总数比重</td>
            <td width="5%" align="center" height="10" rowspan="2">比上月增长率</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="9%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="3%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.toString(Integer.parseInt(dto.getYear())-3) %></td>
            <td width="3%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.toString(Integer.parseInt(dto.getYear())-2) %></td>
            <td width="3%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.toString(Integer.parseInt(dto.getYear())-1) %></td>
        </tr>
   </table>
</div>

<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="130%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        //Long sumCount = 0L;
        //Float sumRate = 0f;
        long sumCount = 0L;
        float sumRate = 0f;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            boolean isMainLine = "".equals(row.getValue("ASSETS_NAPE"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
		<tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
			<td width="15%"><%=isNew?"":ASSETS_SPECIES%></td>
			<td width="10%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
			<td width="5%" align="right"><%=row.getValue("SUM_COUNT")%></td>
			
			<td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
            <td width="5%" align="right"><%=row.getValue("LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("LAST_MONTH_RATE")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>

            <td width="3%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
        </tr>
<%
				if(bkColor){
					sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
					if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
						sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
					}
				}
				
            }
		}
%>
		<tr>
			<td width="8%" colspan = "2" height= "22" align = "left">合计：</td>
			
			<td width="6%"  align="right"><%=sumCount %></td>
			<!-- <td width="6%"  align="right"><%//=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+4) :sumRate %>%</td> -->
			<td width="6%"  align="right"><%=(Float.toString(sumRate).length() - Float.toString(sumRate).indexOf(".")) > 3 ? Float.toString(sumRate).substring(0, Float.toString(sumRate).indexOf(".")+4 ) : Float.toString(sumRate) %>%</td>
			<td width="6%" align="right" colspan = "5"></td>  
		</tr>
<%        
	}
%>
	</table>
</div>

<%
} else if(dto.getSpecialAssetsType().equals("ASSETS_TRANS")){
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="150%" >
		<tr height="22">
			<td width="10%" align="center">类</td>
			<td width="10%" align="center">项</td>
			<td width="10%" align="center" >目</td>
			<td width="10%" align="center" >节</td>
			
			<td width="6%" align="center">资产数量</td>
			<td width="6%" align="center">资产原值</td>
            <td width="6%" align="center">累计折旧</td>
            <td width="6%" align="center">资产净值</td>
            <td width="6%" align="center">累计减值准备</td>
            <td width="6%" align="center">资产净额</td>			
            <td width="6%" align="center">当期折旧</td>            
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        //Double sumCost = 0d;
        //Long sumCount = 0L;
        //Double sumDeprnReserve = 0d;
        //Double sumNetBookValue = 0d;
        //Double sumImpairmentReserve = 0d;
        //Double sumLimitValue = 0d;
        //Double sumPtdDeprn = 0d;
        
        double sumCost = 0d;
        long sumCount = 0L;
        double sumDeprnReserve = 0d;
        double sumNetBookValue = 0d;
        double sumImpairmentReserve = 0d;
        double sumLimitValue = 0d;
        double sumPtdDeprn = 0d;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            boolean isMainLine = "".equals(row.getValue("ASSETS_NAPE"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
		<tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
			<td width="10%"><%=isNew || !isMainLine ?"":ASSETS_SPECIES%></td>
			<td width="10%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
			<td width="10%" align="right"><%=row.getValue("ASSETS_ORDER") %></td>
			<td width="10%" align="right"><%=row.getValue("ASSETS_SECTION") %></td>
			
			<td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>			
			<td width="6%" align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
			<td width="6%" align="right"><%=row.getValue("DEPRN_RESERVE")%></td>	<!-- 累计折旧 -->
			<td width="6%" align="right"><%=row.getValue("NET_BOOK_VALUE")%></td>   <!-- 资产净值 -->
			<td width="6%" align="right"><%=row.getValue("IMPAIRMENT_RESERVE")%></td>   <!-- 累计减值准备 -->
            <td width="6%" align="right"><%=row.getValue("LIMIT_VALUE")%></td>		<!-- 资产净额 -->
			
            <td width="6%" align="right"><%=row.getValue("PTD_DEPRN")%></td>
        </tr>
<%
				if(bkColor){
					sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
					if(row.getValue("SUM_COST") != null && !"".equals(row.getValue("SUM_COST"))){
						sumCost += Double.parseDouble(row.getValue("SUM_COST").toString());
					}
					if(row.getValue("DEPRN_RESERVE") != null && !"".equals(row.getValue("DEPRN_RESERVE"))){
						sumDeprnReserve += Double.parseDouble(row.getValue("DEPRN_RESERVE").toString());
					}
					if(row.getValue("NET_BOOK_VALUE") != null && !"".equals(row.getValue("NET_BOOK_VALUE"))){
						sumNetBookValue += Double.parseDouble(row.getValue("NET_BOOK_VALUE").toString());
					}
					if(row.getValue("IMPAIRMENT_RESERVE") != null && !"".equals(row.getValue("IMPAIRMENT_RESERVE"))){
						sumImpairmentReserve += Double.parseDouble(row.getValue("IMPAIRMENT_RESERVE").toString());
					}
					if(row.getValue("LIMIT_VALUE") != null && !"".equals(row.getValue("LIMIT_VALUE"))){
						sumLimitValue += Double.parseDouble(row.getValue("LIMIT_VALUE").toString());
					}
					if(row.getValue("PTD_DEPRN") != null && !"".equals(row.getValue("PTD_DEPRN"))){
						sumPtdDeprn += Double.parseDouble(row.getValue("PTD_DEPRN").toString());
					}
					
				}				
            }
		}
%>
		<tr>
			<td width="8%" colspan = "4" height= "22" align = "left">合计：</td>
			
			<td width="6%" align="right" ><%=sumCount %></td>
			
			<!-- <td width="6%" align="right" ><%//=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost) %></td>	 -->
			<!-- <td width="6%" align="right" ><%//=BigDecimal.valueOf(sumDeprnReserve).toString().length() - BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumDeprnReserve).toString().substring(0, BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumDeprnReserve) %></td>   -->
			<!-- <td width="6%" align="right" ><%//=BigDecimal.valueOf(sumNetBookValue).toString().length() - BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumNetBookValue).toString().substring(0, BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumNetBookValue) %></td>   -->
			<!-- <td width="6%" align="right" ><%//=BigDecimal.valueOf(sumImpairmentReserve).toString().length() - BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumImpairmentReserve).toString().substring(0, BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumImpairmentReserve) %></td>  --> 
			<!-- <td width="6%" align="right" ><%//=BigDecimal.valueOf(sumLimitValue).toString().length() - BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumLimitValue).toString().substring(0, BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumLimitValue) %></td>   -->
			<!-- <td width="6%" align="right" ><%//=BigDecimal.valueOf(sumPtdDeprn).toString().length() - BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumPtdDeprn).toString().substring(0, BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumPtdDeprn) %></td>   -->
		
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumDeprnReserve).toString().length() - BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumDeprnReserve).toString().substring(0, BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumDeprnReserve).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumNetBookValue).toString().length() - BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumNetBookValue).toString().substring(0, BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumNetBookValue).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumImpairmentReserve).toString().length() - BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumImpairmentReserve).toString().substring(0, BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumImpairmentReserve).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumLimitValue).toString().length() - BigDecimal.valueOf(sumLimitValue).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumLimitValue).toString().substring(0, BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumLimitValue).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumPtdDeprn).toString().length() - BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumPtdDeprn).toString().substring(0, BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumPtdDeprn).toString() %></td>  
		</tr>
<%        
	}
%>
	</table>
</div>

<%} else { %>
<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table height="40" class="eamDbHeaderTable" border="1" width="200%">
		<tr height="20">
			<td width="8%" align="center" rowspan="2" height="10">类</td>
			<td width="8%" align="center" rowspan="2" height="10">项</td>
			
			<td width="6%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">资产原值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净额</td>
			
            <td width="6%" align="center" rowspan="2" height="10">当期折旧</td>
            
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" rowspan="2" height="10">比上月增长率</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="10%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr height="20" class="eamDbHeaderTr">
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.toString(Integer.parseInt(dto.getYear())-3) %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.toString(Integer.parseInt(dto.getYear())-2) %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.toString(Integer.parseInt(dto.getYear())-1) %></td>
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        //Double sumCost = 0d;
        //Long sumCount = 0L;
        //Double sumDeprnReserve = 0d;
        //Double sumNetBookValue = 0d;
        //Double sumImpairmentReserve = 0d;
        //Double sumLimitValue = 0d;
        //Double sumPtdDeprn = 0d;
        //Float sumRate = 0f;
        
        double sumCost = 0d;
        long sumCount = 0L;
        double sumDeprnReserve = 0d;
        double sumNetBookValue = 0d;
        double sumImpairmentReserve = 0d;
        double sumLimitValue = 0d;
        double sumPtdDeprn = 0d;
        float sumRate = 0f;
        
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            boolean isMainLine = "".equals(row.getValue("ASSETS_NAPE"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
		<tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
			<td width="8%"><%=isNew || !isMainLine ?"":ASSETS_SPECIES%></td>
			<td width="8%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
			
			<td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>			
			<td width="6%"  align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
			<td width="6%" align="right"><%=row.getValue("DEPRN_RESERVE")%></td>	<!-- 累计折旧 -->
			<td width="6%" align="right"><%=row.getValue("NET_BOOK_VALUE")%></td>   <!-- 资产净值 -->
			<td width="6%" align="right"><%=row.getValue("IMPAIRMENT_RESERVE")%></td>   <!-- 累计减值准备 -->
            <td width="6%" align="right"><%=row.getValue("LIMIT_VALUE")%></td>		<!-- 资产净额 -->
			
            <td width="6%" align="right"><%=row.getValue("PTD_DEPRN")%></td>
            
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("LAST_MONTH_RATE")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>
            

            <td width="4%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
        </tr>
<%
				if(bkColor){
					sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
					if(row.getValue("SUM_COST") != null && !"".equals(row.getValue("SUM_COST"))){
						sumCost += Double.parseDouble(row.getValue("SUM_COST").toString());
					}
					if(row.getValue("DEPRN_RESERVE") != null && !"".equals(row.getValue("DEPRN_RESERVE"))){
						sumDeprnReserve += Double.parseDouble(row.getValue("DEPRN_RESERVE").toString());
					}
					if(row.getValue("NET_BOOK_VALUE") != null && !"".equals(row.getValue("NET_BOOK_VALUE"))){
						sumNetBookValue += Double.parseDouble(row.getValue("NET_BOOK_VALUE").toString());
					}
					if(row.getValue("IMPAIRMENT_RESERVE") != null && !"".equals(row.getValue("IMPAIRMENT_RESERVE"))){
						sumImpairmentReserve += Double.parseDouble(row.getValue("IMPAIRMENT_RESERVE").toString());
					}
					if(row.getValue("LIMIT_VALUE") != null && !"".equals(row.getValue("LIMIT_VALUE"))){
						sumLimitValue += Double.parseDouble(row.getValue("LIMIT_VALUE").toString());
					}
					if(row.getValue("PTD_DEPRN") != null && !"".equals(row.getValue("PTD_DEPRN"))){
						sumPtdDeprn += Double.parseDouble(row.getValue("PTD_DEPRN").toString());
					}
					if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
						sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
					}
				}			
            }
		}
%>
		<tr>
			<td width="8%" colspan = "2" height= "22" align = "left">合计：</td>
			
			<td width="6%" align="right" ><%=sumCount %></td>
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost).toString() %></td>	
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumDeprnReserve).toString().length() - BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumDeprnReserve).toString().substring(0, BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumDeprnReserve).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumNetBookValue).toString().length() - BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumNetBookValue).toString().substring(0, BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumNetBookValue).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumImpairmentReserve).toString().length() - BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumImpairmentReserve).toString().substring(0, BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumImpairmentReserve).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumLimitValue).toString().length() - BigDecimal.valueOf(sumLimitValue).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumLimitValue).toString().substring(0, BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumLimitValue).toString() %></td>  
			<td width="6%" align="right" ><%=(BigDecimal.valueOf(sumPtdDeprn).toString().length() - BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".")) > 3 ? BigDecimal.valueOf(sumPtdDeprn).toString().substring(0, BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumPtdDeprn).toString() %></td>
			<!-- <td width="6%"  align="right"><%//=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+3) :sumRate %>%</td> -->
			<td width="6%"  align="right"><%=(Float.toString(sumRate).length() - Float.toString(sumRate).indexOf(".")) > 3 ? Float.toString(sumRate).substring(0, Float.toString(sumRate).indexOf(".")+3) : Float.toString(sumRate) %>%</td>
			<td width="6%" align="right" colspan = "5"></td>  
		</tr>
<%        
	}
%>
	</table>
</div>
<%} %>
<%
	if(hasData){
%>
<div style="position:absolute;top:91%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
//	var accountingPeriod = mainFrm.accountingPeriod.value;
//	if(accountingPeriod == null || accountingPeriod == ""){
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//		return;
//	}
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialAssetsReportServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
//	var accountingPeriod = mainFrm.accountingPeriod.value;
//	if(accountingPeriod == null || accountingPeriod == ""){
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//		return;
//	}
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialAssetsReportServlet";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialAssetsReportServlet";
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