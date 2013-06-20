<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.ams.newasset.report.dto.ReportInDataDTO"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-14
  Time: 20:30:51
  To change this template use File | Settings | File Templates.
--%>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>报表数据统一录入详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/style/js/calendar.js"></script>
    
<style type="text/css">
        <!--
        .STYLE1 {
            color: #0033FF
        }
        -->
</style>
</head>
<body onload="showPeriod();">
 <jsp:include page="/message/MessageProcess"/>
<%
    ReportInDataDTO dto = (ReportInDataDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    String managerGuideType = dto.getManagerGuideType();
    String action = dto.getAct();
%>
<form name="mainFrm" method="POST">
	<input type="hidden" name="isKpi" value="<%=dto.getKpi()%>">
<fieldset style="margin-left:0;height:450px">
<%
    if (managerGuideType.equals("TRUN_RATE")) {
%>
       <legend><span class="STYLE1">转资率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("IN_TIME_RATE")) { 
%>
       <legend><span class="STYLE1">资产管理系统决策分析报表上报及时率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("NICETY_RATE")) {
%>
       <legend><span class="STYLE1">转资信息准确率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("CHECK_RATE")) {
%>
       <legend><span class="STYLE1">资产实物管理抽查任务完成率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("MATCH_CASE_RATE")) {
%>
       <legend><span class="STYLE1">抽查盘点资产账实相符率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("COP_RATE")) {
%>
       <legend><span class="STYLE1">日常巡检资产盘点完成率</span></legend>
<%
    } else if (managerGuideType.equals("COP_MATCH_RATE")) {
%>
       <legend><span class="STYLE1">日常巡检资产盘点账实相符率</span></legend>
<%
    } else if (managerGuideType.equals("ACCOUNTING_ACCURATE")) {
%>
       <legend><span class="STYLE1">资产核算准确率</span></legend>
<%
    } else if (managerGuideType.equals("RETURN_RATE")) {
%>
        <legend><span class="STYLE1">固定资产回报率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("TURNOVER_RATE")) {
%>
         <legend><span class="STYLE1">周转率维护页面</span></legend>
<%
    } else if (managerGuideType.equals("FEYOFAI_RATE")) {
%>
        <legend><span class="STYLE1">每百元固定资产收入（年度）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("FEYOFACCT_RATE")) {
%>
        <legend><span class="STYLE1">每百元固定资产承载计费时长（年度）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("FEOFATTP_RATE")) {
%>
        <legend><span class="STYLE1">每百元固定资产净利润（年度）维护页面</span></legend>
<%
   	}  else if (managerGuideType.equals("FEYOFATPA_RATE")) {
%>
        <legend><span class="STYLE1">每百元固定资产利润总额（年度）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("ECCCT_RATE")) {
%>
        <legend><span class="STYLE1">每信道承载计费时长（年度）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("EAMSDARTR_RATE")) {
%>
        <legend><span class="STYLE1">EAM系统决策分析报表上报及时率维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("IATTCFALOAR_RATE")) {
%>
        <legend><span class="STYLE1">除载频外资产盘亏率（价值统计）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("IATTCFALOFS_RATE")) {
%>
        <legend><span class="STYLE1">除载频外资产盘亏率（数量统计）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("AISRS_RATE")) {
%>
        <legend><span class="STYLE1">资产盘亏率（数量统计）维护页面</span></legend>
<%
   	} else if (managerGuideType.equals("AISRVS_RATE")) {
%>
        <legend><span class="STYLE1">资产盘亏率（价值统计）维护页面</span></legend>
<%
   	}else if (dto.getKpi()){
%>
        <legend><span class="STYLE1"><%=dto.getKpiName()%></span></legend>
<%
    }
%>
    <table width="50%" align="center">
    	<tr>
    		<td width="10%" align="right" height="22"> 会计期间：</td>
    		<input id="period1" name="period1" type="hidden" value="<%=dto.getPeriod()%>"/>
			<td width="60%" align="left" height="22">
				<select fieldLabel="会计期间" name="period" class="noEmptyInput" id="period" style="width:60%;text-align:">
					<option value="">----请选择----</option>
					<option value="201201">2012-01</option>
					<option value="201202">2012-02</option>
					<option value="201203">2012-03</option>
					<option value="201204">2012-04</option>
					<option value="201205">2012-05</option>
					<option value="201206">2012-06</option>
					<option value="201207">2012-07</option>
					<option value="201208">2012-08</option>
					<option value="201209">2012-09</option>
					<option value="201210">2012-10</option>
					<option value="201211">2012-11</option>
					<option value="201212">2012-12</option>
					<option value="201301">2013-01</option>
					<option value="201302">2013-02</option>
					<option value="201303">2013-03</option>
					<option value="201304">2013-04</option>
					<option value="201305">2013-05</option>
					<option value="201306">2013-06</option>
					<option value="201307">2013-07</option>
					<option value="201308">2013-08</option>
					<option value="201309">2013-09</option>
					<option value="201310">2013-10</option>
					<option value="201311">2013-11</option>
					<option value="201312">2013-12</option>
					<option value="201401">2014-01</option>
					<option value="201402">2014-02</option>
					<option value="201403">2014-03</option>
					<option value="201404">2014-04</option>
					<option value="201405">2014-05</option>
					<option value="201406">2014-06</option>
					<option value="201407">2014-07</option>
					<option value="201408">2014-08</option>
					<option value="201409">2014-09</option>
					<option value="201410">2014-10</option>
					<option value="201411">2014-11</option>
					<option value="201412">2014-12</option>
					<option value="201501">2015-01</option>
					<option value="201502">2015-02</option>
					<option value="201503">2015-03</option>
					<option value="201504">2015-04</option>
					<option value="201505">2015-05</option>
					<option value="201506">2015-06</option>
					<option value="201507">2015-07</option>
					<option value="201508">2015-08</option>
					<option value="201509">2015-09</option>
					<option value="201510">2015-10</option>
					<option value="201511">2015-11</option>
					<option value="201512">2015-12</option>
					<option value="201601">2016-01</option>
					<option value="201602">2016-02</option>
					<option value="201603">2016-03</option>
					<option value="201604">2016-04</option>
					<option value="201605">2016-05</option>
					<option value="201606">2016-06</option>
					<option value="201607">2016-07</option>
					<option value="201608">2016-08</option>
					<option value="201609">2016-09</option>
					<option value="201610">2016-10</option>
					<option value="201611">2016-11</option>
					<option value="201612">2016-12</option>
				</select>
			</td>
    	</tr>
<%
    if (managerGuideType.equals("TRUN_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">考核期内工程转资额：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="projectTrunAssets" class="noEmptyInput" style="width:60%" value="<%=dto.getProjectTrunAssets()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">工程累计投入总额：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="projectSumAssets" class="noEmptyInput" style="width:60%"  value="<%=dto.getProjectSumAssets()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("IN_TIME_RATE")) {
%>
        <tr>
            <td width="17%" align="right" height="22">未及时上报次数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="noTimelyReportNum" class="noEmptyInput" style="width:60%" value="<%=dto.getNoTimelyReportNum()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">考核期应上报次数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsmentReportNum" class="noEmptyInput" style="width:60%"  value="<%=dto.getAssetsmentReportNum()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("NICETY_RATE")) {
%>
        <tr>
            <td width="30%" align="right" height="22">考核期内发生的转资资产不准确的数量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsmentFalseNum" class="noEmptyInput" style="width:60%" value="<%=dto.getAssetsmentFalseNum()%>">
            </td>
        </tr>
        <tr>
            <td width="20%" align="right" height="22">考核期内转资资产总量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsmentAssetsSum" class="noEmptyInput" style="width:60%"  value="<%=dto.getAssetsmentAssetsSum()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("CHECK_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">实际完成的资产实物管理抽查盘点任务工单数量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="completeCheckNum" class="noEmptyInput" style="width:60%" value="<%=dto.getCompleteCheckNum()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">计划规定的资产抽查盘点任务工单总数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="planCheckNum" class="noEmptyInput" style="width:60%"  value="<%=dto.getPlanCheckNum()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("MATCH_CASE_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">抽查中账实相符的资产数量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="accountMatchCase" class="noEmptyInput" style="width:60%" value="<%=dto.getAccountMatchCase()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">抽查资产总数量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="checkAssetsSum" class="noEmptyInput" style="width:60%"  value="<%=dto.getCheckAssetsSum()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("COP_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">已完成的日常巡检盘点的工单数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsCopNum" class="noEmptyInput" style="width:60%" value="<%=dto.getAssetsCopNum()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">计划的日常巡检盘点工单总数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsCopSum" class="noEmptyInput" style="width:60%"  value="<%=dto.getAssetsCopSum()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("COP_MATCH_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">盘点中账实相符的资产数量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsMatchCase" class="noEmptyInput" style="width:60%" value="<%=dto.getAssetsMatchCase()%>">
            </td>
        </tr>
        <tr>
            <td width="17%" align="right" height="22">盘点资产总数量：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="assetsCheckSum" class="noEmptyInput" style="width:60%"  value="<%=dto.getAssetsCheckSum()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("ACCOUNTING_ACCURATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">资产核算相关的差错次数：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="accurateErrorNumber" class="noEmptyInput" style="width:60%" value="<%=dto.getAccurateErrorNumber()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("RETURN_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea   class="noEmptyInput" style="width:60%"   disabled="disabled">（期初总固定资产净额+期末总固定资产净额）/2</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("TURNOVER_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea class="noEmptyInput" style="width:60%"  disabled="disabled">（期初总固定资产净额+期末总固定资产净额）/2</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
        
<%
    } else if (managerGuideType.equals("FEYOFAI_RATE")) {
%>
        <tr>
            <td width="25%" align="right" height="22">每百元固定资产收入（年度）：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("FEYOFACCT_RATE")) {
%>
        <tr>
            <td width="25%" align="right" height="22">每百元固定资产承载计费时长（年度）：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("FEOFATTP_RATE")) {
%>
        <tr>
            <td width="25%" align="right" height="22">每百元固定资产净利润（年度）：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("FEYOFATPA_RATE")) {
%>
        <tr>
            <td width="25%" align="right" height="22">每百元固定资产利润总额（年度）：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("ECCCT_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">每信道承载计费时长（年度）：</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("EAMSDARTR_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea  class="noEmptyInput" style="width:60%" disabled="disabled">及时上报次数/考核期应上报次数×100％</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("AISRS_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
          <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea  class="noEmptyInput" style="width:60%" disabled="disabled">系统自动计算的盘亏资产的卡片数量/资产卡片总数量×100％</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
        
<%
    } else if (managerGuideType.equals("AISRVS_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
         <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea  class="noEmptyInput" style="width:60%"  disabled="disabled">盘点中账实相符的资产原值/盘点资产原值×100％</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("IATTCFALOAR_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea  class="noEmptyInput" style="width:60%" disabled="disabled">盘点中账实相符的资产（不包括载频）原值/盘点资产（不包括载频）原值×100％</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (managerGuideType.equals("IATTCFALOFS_RATE")) {
%>
        <tr>
            <td width="10%" align="right" height="22">OU：</td>
            <td width="60%" align="left" height="22">
                <select name="organizationId" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值描述：</td>
            <td width="60%" align="left" height="22">
                <textarea  class="noEmptyInput" style="width:60%"  disabled="disabled">盘点中账实相符的资产（不包括载频）卡片数量/盘点资产（不包括载频）卡片总数量×100％</textarea>
            </td>
        </tr>
        <tr>
            <td width="30%" align="right" height="22">值</td>
            <td width="60%" align="left" height="22">
                <input type="text" name="value" class="noEmptyInput" style="width:60%" value="<%=dto.getValue()%>">
            </td>
        </tr>
<%
    } else if (dto.getKpi()){
%>        <input type="hidden" name="isKpi" value="<%=dto.getKpi()%>">
          <tr>
                <td width="10%" align="right" height="22">OU：</td>
                <td width="60%" align="left" height="22">
                    <select name="companyCode" style="width:60%" class="noEmptyInput"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
                </td>
            </tr>
            <tr>
                <td width="40%" align="right" height="22">当前值：</td>
                <td width="60%" align="left" height="22">
                    <input type="text" name="curValue" class="noEmptyInput" style="width:60%" value="<%=dto.getCurValue()%>">（计算时的分子）
                </td>
            </tr>
            <tr>
                <td width="40%" align="right" height="22">总值：</td>
                <td width="60%" align="left" height="22">
                    <input type="text" name="totalValue" class="noEmptyInput" style="width:60%"  value="<%=dto.getTotalValue()%>">（计算时的分母）
                </td>
            </tr>
<%
    }
%>
        <tr>
            <td width="50%" align="center" height="22" colspan="5">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveData();">&nbsp;
<%
    if (!action.equals(WebActionConstant.NEW_ACTION)) {
%>
                <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_DeleteData(); return false;">&nbsp;
<%
    }
%>
                <img src="/images/eam_images/back.jpg" alt="取消" onClick="do_Back(); return false;"></td>
        </tr>
    </table>
</fieldset>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="reportId" value="<%=dto.getReportId()%>">
    <input type="hidden" name="managerGuideType" value="<%=managerGuideType%>">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script>
function do_SaveData() {
	var period = mainFrm.period.value;
	if(period == null || period == ""){
		alert("会计期间不能为空！");
		mainFrm.period.focus();
		return;
	}
	var dates=mainFrm.period.value.split("-",3);
	if (dates.length==3) {
		mainFrm.period.value=dates[0]+dates[1];
	}
    var fieldNames;
    var fieldLabels;
    var managerGuideType = "<%=managerGuideType%>";
    if (managerGuideType == "TRUN_RATE") {
        fieldNames = "organizationId;period;projectTrunAssets;projectSumAssets";
        fieldLabels = "OU;会计期间;考核期内工程转资额;工程累计投入总额";
    } else if (managerGuideType == "IN_TIME_RATE") {
        fieldNames = "period;noTimelyReportNum;assetsmentReportNum";
        fieldLabels = "会计期间;未及时上报次数;考核期应上报次数";
    } else if (managerGuideType == "NICETY_RATE") {
        fieldNames = "period;assetsmentFalseNum;assetsmentAssetsSum";
        fieldLabels = "会计期间;考核期内发生的转资资产不准确的数量;考核期内转资资产总量";
    } else if (managerGuideType == "CHECK_RATE") {
        fieldNames = "organizationId;period;completeCheckNum;planCheckNum";
        fieldLabels = "OU;会计期间;实际完成的资产实物管理抽查盘点任务工单数量;计划规定的资产抽查盘点任务工单总数";
    } else if (managerGuideType == "MATCH_CASE_RATE") {
        fieldNames = "organizationId;period;accountMatchCase;checkAssetsSum";
        fieldLabels = "OU;会计期间;抽查中账实相符的资产数量;抽查资产总数量";
    } else if (managerGuideType == "COP_RATE") {
        fieldNames = "organizationId;period;assetsCopNum;assetsCopSum";
        fieldLabels = "OU;会计期间;已完成的日常巡检盘点的工单数;计划的日常巡检盘点工单总数";
    } else if (managerGuideType == "COP_MATCH_RATE") {
        fieldNames = "organizationId;period;assetsMatchCase;assetsCheckSum";
        fieldLabels = "OU;会计期间;盘点中账实相符的资产数量;盘点资产总数量";
    } else if (managerGuideType == "ACCOUNTING_ACCURATE") {
        fieldNames = "organizationId;period;accurateErrorNumber";
        fieldLabels = "OU;会计期间;资产核算相关的差错次数";
    } else if (<%=dto.getKpi()%>) {
        fieldNames = "companyCode;period;curValue;totalValue";
        fieldLabels = "OU;会计期间;当前值（计算时的分子）;总值（计算时的分母）";
    }  else if (managerGuideType == "FEYOFATPA_RATE") {
        fieldNames = "period;value";
        fieldLabels = "会计期间;每百元固定资产利润总额（年度）";
    } else if (managerGuideType == "FEOFATTP_RATE") {
        fieldNames = "period;value";
        fieldLabels = "会计期间;每百元固定资产净利润（年度）";
    } else if (managerGuideType == "FEYOFACCT_RATE") {
        fieldNames = "period;value";
        fieldLabels = "会计期间;每百元固定资产承载计费时长（年度）";
    } else if (managerGuideType == "FEYOFAI_RATE") {
        fieldNames = "period;value";
        fieldLabels = "会计期间;每百元固定资产收入（年度";
    } else {
        fieldNames = "organizationId;period;value";
        fieldLabels = "OU;会计期间;值";
    }
    var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
    if (isValid) {
        if (managerGuideType == "TRUN_RATE") {
            if (!isNumber(mainFrm.projectTrunAssets.value)) {
                alert("“考核期内工程转资额”请输入数字类型！");
                mainFrm.projectTrunAssets.focus();
                return;
            }
            if (!isNumber(mainFrm.projectSumAssets.value)) {
                alert("“工程累计投入总额”请输入数字类型！");
                mainFrm.projectSumAssets.focus();
                return;
            }
        } else if (managerGuideType == "IN_TIME_RATE") {
            if (!isInt(mainFrm.noTimelyReportNum.value)) {
                alert("“未及时上报次数”请输入整数！");
                mainFrm.noTimelyReportNum.focus();
                return;
            }
            if (!isInt(mainFrm.assetsmentReportNum.value)) {
                alert("“考核期应上报次数”请输入整数！");
                mainFrm.assetsmentReportNum.focus();
                return;
            }
        } else if (managerGuideType == "NICETY_RATE") {
            if (!isInt(mainFrm.assetsmentFalseNum.value)) {
                alert("“考核期内发生的转资资产不准确的数量”请输入整数！");
                mainFrm.assetsmentFalseNum.focus();
                return;
            }
            if (!isInt(mainFrm.assetsmentAssetsSum.value)) {
                alert("“考核期内转资资产总量”请输入整数！");
                mainFrm.assetsmentAssetsSum.focus();
                return;
            }
        } else if (managerGuideType == "CHECK_RATE") {
            if (!isInt(mainFrm.completeCheckNum.value)) {
                alert("“实际完成的资产实物管理抽查盘点任务工单数量”请输入整数！");
                mainFrm.assetsmentFalseNum.focus();
                return;
            }
            if (!isInt(mainFrm.planCheckNum.value)) {
                alert("“计划规定的资产抽查盘点任务工单总数”请输入整数！");
                mainFrm.assetsmentAssetsSum.focus();
                return;
            }
        } else if (managerGuideType == "MATCH_CASE_RATE") {
            if (!isInt(mainFrm.accountMatchCase.value)) {
                alert("“抽查中账实相符的资产数量”请输入整数！");
                mainFrm.accountMatchCase.focus();
                return;
            }
            if (!isInt(mainFrm.checkAssetsSum.value)) {
                alert("“抽查资产总数量”请输入整数！");
                mainFrm.checkAssetsSum.focus();
                return;
            }
        } else if (managerGuideType == "COP_RATE") {
            if (!isInt(mainFrm.assetsCopNum.value)) {
                alert("“已完成的日常巡检盘点的工单数”请输入整数！");
                mainFrm.assetsCopNum.focus();
                return;
            }
            if (!isInt(mainFrm.assetsCopSum.value)) {
                alert("“计划的日常巡检盘点工单总数”请输入整数！");
                mainFrm.assetsCopSum.focus();
                return;
            }
        } else if (managerGuideType == "COP_MATCH_RATE") {
            if (!isInt(mainFrm.assetsMatchCase.value)) {
                alert("“盘点中账实相符的资产数量”请输入整数！");
                mainFrm.assetsMatchCase.focus();
                return;
            }
            if (!isInt(mainFrm.assetsCheckSum.value)) {
                alert("“盘点资产总数量”请输入整数！");
                mainFrm.assetsCheckSum.focus();
                return;
            }
        } else if (managerGuideType == "ACCOUNTING_ACCURATE") {
            if (!isInt(mainFrm.accurateErrorNumber.value)) {
                alert("“资产核算相关的差错次数”请输入整数！");
                mainFrm.assetsMatchCase.focus();
                return;
            }
        } else if (<%=dto.getKpi()%>) {
            var curValue = mainFrm.curValue.value;
            var totalValue = mainFrm.totalValue.value;
            var Expression = /^[0-9]*$/;
            var objExp=new RegExp(Expression);
            if(!objExp.test(curValue)){
                alert("“当前值（计算时的分子）”不合法，请输入正整数！");
                mainFrm.curValue.focus();
                return;
            }
            if (!objExp.test(totalValue)) {
                alert("“总值（计算时的分母）”不合法，请输入正整数！");
                mainFrm.totalValue.focus();
                return;
            }
            if(parseInt(curValue) > parseInt(totalValue)){
                alert("“当前值（计算时的分子）” 不能超过 “总值（计算时的分母）”的值！");
                mainFrm.curValue.focus();
                return;
            }
            if(totalValue == "0"){
               alert("“总值（计算时的分母）”不能为零！");
                mainFrm.totalValue.value = "";
                mainFrm.totalValue.focus();
                return;
            }
        }else{
        	 if (!isNumber(mainFrm.value.value)) {
                 alert("“值”请输入数字类型！");
                 mainFrm.value.focus();
                 return;
             }
        }
    }
    if (isValid) {
		var action = "";
        if (mainFrm.reportId.value == "") {
                action = "<%=WebActionConstant.CREATE_ACTION%>";
        }else{
        	action =  "<%=WebActionConstant.UPDATE_ACTION%>";   
        	//if("<%=dto.getKpi()%>"== "true" && "<%=dto.getCurValue()%>"!= "" && "<%=dto.getTotalValue()%>" != ""){
            //	action = "<%=WebActionConstant.UPDATE_ACTION%>";    
       	 	//}
        }
		mainFrm.act.value = action;
		mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet";
		mainFrm.submit();
	}
}

function do_DeleteData() {
    var reportId = mainFrm.reportId.value;
    if (confirm("确认删除吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
        mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?reportId=" + reportId;
        mainFrm.submit();
    }
}


function do_Back() {
    var managerGuideType = "<%=managerGuideType%>";
    if (managerGuideType == "TRUN_RATE") {
        mainFrm.organizationId.value = "";
        mainFrm.period.value = "";
        mainFrm.projectTrunAssets.value = "";
        mainFrm.projectSumAssets.value = "";
    } else if (managerGuideType == "IN_TIME_RATE") {
        mainFrm.period.value = "";
        mainFrm.noTimelyReportNum.value = "";
        mainFrm.assetsmentReportNum.value = "";
    } else if (managerGuideType == "NICETY_RATE") {
        mainFrm.period.value = "";
        mainFrm.assetsmentFalseNum.value = "";
        mainFrm.assetsmentAssetsSum.value = "";
    } else if (managerGuideType == "CHECK_RATE") {
        mainFrm.organizationId.value = "";
        mainFrm.period.value = "";
        mainFrm.completeCheckNum.value = "";
        mainFrm.planCheckNum.value = "";
    } else if (managerGuideType == "MATCH_CASE_RATE") {
        mainFrm.organizationId.value = "";
        mainFrm.period.value = "";
        mainFrm.accountMatchCase.value = "";
        mainFrm.checkAssetsSum.value = "";
    } else if (managerGuideType == "COP_RATE") {
        mainFrm.organizationId.value = "";
        mainFrm.period.value = "";
        mainFrm.assetsCopNum.value = "";
        mainFrm.assetsCopSum.value = "";
    } else if (managerGuideType == "COP_MATCH_RATE") {
        mainFrm.organizationId.value = "";
        mainFrm.period.value = "";
        mainFrm.assetsMatchCase.value = "";
        mainFrm.assetsCheckSum.value = "";
    } else if (<%=dto.getKpi()%>) {
//        mainFrm.companyCode.value = "";
//        mainFrm.period.value = "";
        mainFrm.curValue.value = "";
        mainFrm.totalValue.value = "";
    }

    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet";
	mainFrm.submit();
}
function showPeriod(){
	if(document.getElementById("period1").value!=""){
		document.getElementById("period").value=document.getElementById("period1").value
	}
}
</script>