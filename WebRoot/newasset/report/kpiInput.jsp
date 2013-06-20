<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@page import="com.sino.ams.newasset.report.dto.KpiInputDTO"%>
<%@ include file="/newasset/headerInclude.htm" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
    <title></title>
</head>
 <jsp:include page="/message/MessageProcess"/>
 <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
 <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
 <body  onload="initPage();">
<%	
	KpiInputDTO dto = (KpiInputDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
%>

  <script type="text/javascript">
    printTitleBar("数据指标维护");
    var ArrAction0 = new Array(true, "查询", "action_view.gif", "查询", "do_Search()");  
    var ArrAction1 = new Array(true, "保存", "action_save.gif", "保存", "do_Save()");
    var ArrActions = new Array(ArrAction0, ArrAction1);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
    printToolBar();

</script>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.newasset.report.servlet.KpiInputServlet">
	<input type="hidden" name="act">
	<input type="hidden" name="listValue" value="">
	<input type="hidden" name="listKpiType" value="">
	<input type="hidden" name="pvalue" id="pvalue" value="<%=dto.getPeriod()%>">
	<input type="hidden" name="periodtypes" id="periodtypes">
	<table width="90%" align="center" border="0" bordercolor="#666666" style="overflow:hidden;position:absolute;top:50px;">
 		<tr>
 			<td width="8%" align="right">指标类型:</td>
 			<td width="12%">
             <select  style="width:100%" name="indexType"  id="indexType" onchange="assetsTypeChange();">
             <option value="ADM_INDEX" >管理指标</option>
             <option value="SYS_INDEX" >系统指标</option>
             </select>
            </td>
            <td width="8%" align="right">资产类型:</td>
 			<td width="12%">
             <select  style="width:100%" name="assetsType"  id="assetsType">
             <option value="ASSETS" >资产</option>
             <option value="TD_ASSETS">TD资产</option>
             <option value="TT_ASSETS">铁通资产</option>
             </select>
            </td>
            <td width="8%" align="right">期间类型：</td>
            <td width="12%">
             <select  style="width:100%" name="periodtype"  id="periodtype" onchange="periodChange();">
             <option value="YEAR" >年度</option>
             <option value="MONTH">月度</option>
             </select>
            </td>
 
            <td width="8%" align="right">期间值：</td>
            <td width="12%" id="gp">
             <select  style="width:100%" name="period" id="period">
             </select>
            </td>
        </tr>
 
 

	</table>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:150%">
	    <table class="headerTable" border="1" style="width:100%">
	        <tr >
				<td width="20%" align="center">指标名称</td>
				<td width="20%" align="center">指标值</td>
	            <td width="40%" align="center">指标描述</td>
	        </tr>
	    </table>
	</div>
	<div id="dataDiv" style="overflow:hidden;position:absolute;top:90px;left:1px;width:150%">
	    <table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
	 <%
			
	 if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				 row=rows.getRow(i);
	%>
			<tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
	            <td width="20%" ><input name="kpiName" type="text" class="finput" readonly value="<%=row.getValue("KPI_NAME")%>"></td>
	            <td width="20%"><input name="value" type="text" class="finput2"  value="<%=row.getValue("VALUE")%>" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"></td>
	            <td width="40%"><input type="kpiDesc" class="finput" readonly value="<%=row.getValue("KPI_DESC")%>"></td>
	            <input name="kpiType" type="hidden" class="finput2"  value="<%=row.getValue("KPI_CODE")%>">
			</tr>
	<%  
		}
	 }
	%>
	</table>
	</div>
	</form>
  </body>
</html>
<script type="text/javascript">
function initPage(){
	do_SetPageWidth();
	do_SetControlBtnEnable();
	showPeriod();
}

function do_SetControlBtnEnable(){
	var enPic = 5;
	var disPic = 4;
        ShowSinoButton(disPic);
        HideSinoButton(enPic);
}
function showPeriod(){
	if("<%=dto.getPeriodType()%>" != ""){
		document.getElementById("periodType").value="<%=dto.getPeriodType()%>"
	}
	if("<%=dto.getIndexType()%>" != ""){
		document.getElementById("indexType").value="<%=dto.getIndexType()%>"
	}
	if("<%=dto.getAssetsType()%>" != ""){
		document.getElementById("assetsType").value="<%=dto.getAssetsType()%>"
	}
	assetsTypeChange();
	mainFrm.period.value=document.getElementById("pvalue").value
	
}

function do_Search() {
	if(Verification()){
		return;
	}
	document.getElementById("periodTypes").value=document.getElementById("periodType").value;;
	if(document.getElementById("periodType").value== "")
	{
		alert("请选择期间！");
        return;
	}
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}
function do_Save() {
	if(Verification()){
		return;
	}
	document.getElementById("periodTypes").value=document.getElementById("periodType").value
	var txt=document.getElementsByName("value");
	var txt1=document.getElementsByName("kpiType");
	for(var i=0;i<txt1.length;i++){     
		mainFrm.listKpiType.value +=txt1[i].value + ";";
		mainFrm.listValue.value +=txt[i].value + ";";
	}
    mainFrm.act.value = "<%=WebActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
}

function assetsTypeChange(){
	if(document.getElementById("indexType").value == "SYS_INDEX"){
		document.getElementById("assetsType").value ="";
		document.getElementById("assetsType").disabled=true;
		document.getElementById("periodtype").value ="MONTH";
		document.getElementById("periodtype").disabled=true;
	}else if(document.getElementById("indexType").value == "ADM_INDEX"){
		//document.getElementById("assetsType").value ="ASSETS";
		document.getElementById("assetsType").disabled=false;
		document.getElementById("periodtype").disabled=false;
	}
	periodChange();
	
	
}

function periodChange()
{  
    var opValue=document.getElementById("period");
    var n=opValue.options.length;
    if(n>0){
       for(var i= n-1; i>=0; i--)
       {
　　        opValue.remove(i);
　　    }
    }
    
     if(document.getElementById("periodtype").value=="YEAR") {
     var year = new Date().getFullYear();    
      for (var m=0;m<10;m++){
            //alert(year);            
             opValue.options.add(new Option(year,year));   
             year = year-1;
         }     
     }
     else if(document.getElementById("periodtype").value=="MONTH")
     {
          var year = new Date().getFullYear();
          var month = new Date().getMonth()+1;
          //alert(month);
      for (var i=0;i<24;i++){
    	  var str=year+"-"+month;          
          opValue.options.add(new Option(str,str));  
         if ( i%12==0){
              year = year-1;
            }
            month=month-1;
            if(month==0){
               month=12;
            }
            if(month<10){
              month='0'+month;
            }
         }    
     }
}
function Verification(){
	if(document.getElementById("indexType").value == "ADM_INDEX"){
		if(document.getElementById("assetsType").value ==""){
			alert("请选择资产类型！");
			return true;
		}
	}
}

</script>
