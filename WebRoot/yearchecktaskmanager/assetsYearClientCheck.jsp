<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@page import="com.sino.ams.yearchecktaskmanager.dto.AssetsYearClientDTO"%>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>


<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    AssetsYearClientDTO dto = (AssetsYearClientDTO) request.getAttribute(QueryConstant.QUERY_DTO);
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    int orgId = userAccount.getOrganizationId();
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = false;
    if (rows != null && !rows.isEmpty()) {
        hasData = true;
    }
   
%>

<html>
<style>

FORM {
    margin-top: 0;
    margin-bottom: 0;
}
</style>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/yearchecktaskmanager/exportOrder.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();helpInit('2.1.1');" onkeydown="autoExeFunction('do_Search()');">
<script type="text/javascript">
    printTitleBar("确定客户端资产");
</script> 
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearClientCheckServlet" > 
<jsp:include page="/message/MessageProcess"/>
<input type="hidden" name="act" value="">
<input type="hidden" name="exportType" value="">
<input type="hidden" name="excelPath"  value="" >
<input type="hidden" name="typeStr" value="">
<table width="100%" border="0" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <tr>
        <td width="10%" align="right">资产名称：</td>
        <td width="10%"><input class="input_style1" type="text" name="assetsDescription" style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
        <%--<td width="10%" align="right">资产型号：</td>
        <td width="10%"><input class="input_style1" type="text" name="modelNumber" style="width:100%" value="<%=dto.getModelNumber()%>">--%></td>
        <td width="10%" align="right">标签号：</td>
        <td width="10%"><input class="input_style1" type="text" name="barcode" style="width:100%" value="<%=dto.getBarcode()%>"></td>
         <td width="30%" align="right">
           <img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:pointer" onclick="do_Search();" title="查询">&nbsp;
           <img src="/images/eam_images/confirm.jpg" id="confirmImg" style="cursor:pointer" onclick="do_Confirm();" title="确认设备">&nbsp;
           <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"  onClick="do_excel();">&nbsp;
           <img src="/images/eam_images/export.jpg" alt="Excel导出" onclick="do_ExportData()">&nbsp;
        </td>
    </tr>
</table>
       
</form>
<input type="hidden" name="helpId" value=""> 
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:50px;left:0px;width:100%">
    <table class="eamHeaderTable" border="1" width="100%">
        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
                <td align=center width="3%">
                  <input type="checkbox"  name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')">
                </td>
			    <td align=center width="17%">资产标签号</td>
				<td align=center width="20%">资产名称</td>
				<td align=center width="20%">应用领域描述</td> 
				<td align=center width="20%">地点编码</td>
				<td align=center width="20%">地点描述</td>
			<td style="display:none">隐藏域字段</td>
        </tr>
    </table>
</div>

<% 
    if (hasData) {
%>
<div id="dataDiv" style="overflow:scroll;height:100%;width:100%;position:absolute;top:91px;left:0px;height:485px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
 
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
Row row = null;
String barcode = "";
for (int i = 0; i < rows.getSize(); i++) {
    row = rows.getRow(i);
    barcode = row.getStrValue("BARCODE"); 
%>
    <tr class="dataTR" onclick="executeClick(this)">
        <td width="3%" align="center">
        <input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>" >
        </td> 
        <td width="17%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="barcode" id="barcode<%=i%>" type="text" class="finput2" readonly value="<%=barcode%>">
        </td>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="assetsDescription" id="assetsDescription<%=i%>" type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>">
        </td>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="faCategory1" id="faCategory1<%=i%>" type="text" class="finput2" readonly value="<%=row.getValue("FA_CATEGORY1")%>">
        </td>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="workorderObjectCode" id="workorderObjectCode<%=i%>" type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>">
        </td>
        <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')">
              <input  name="workorderObjectName" id="workorderObjectName<%=i%>" type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>">
        </td>
		<td style="display:none">
		</td>

    </tr>
 
<% 
		}
%>
</table>
</div>
<div id="pageNaviDiv"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
</div>
<%
    }
%>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<iframe style="display:none" src="" name="downFrm"></iframe>
<script type="text/javascript">

//query
function do_Search() {
    mainFrm.target = "_self";
    mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearClientCheckServlet";
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}


//确认状态
function do_Confirm() {
   	var checkArr = document.getElementsByName("subCheck");
   	var length   = checkArr.length; 
   	var flag =false;
	var Str="";//界面获取DTO集合对象
   	if(length==0){
	    alert("请选择数据后确认");
   	}else{
   		for(var i = 0; i < checkArr.length; i++) {
            if(checkArr[i].checked) {
            	var barcode=document.getElementById("barcode" + i).value;
		        var str=barcode+","
			    Str+=str;
           } 
        }
		//mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearClientCheckServlet?Str="+Str;
		document.mainFrm.typeStr.value=Str;
	    document.mainFrm.act.value = "CONFIRM_ACTION";
	    mainFrm.submit();
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	}
}


//import
function do_excel() {
    var returnValue=do_ImportClientExcel();
    if (returnValue) {
    	mainFrm.excelPath.value = returnValue;
        document.mainFrm.act.value = "excel";
        document.mainFrm.submit();
    }
}

//导出
function do_ExportData() {
    var exportType = "";
    if (confirm("是否导出资产目录范围内资产？导出请点击“确定”按钮，否则请点击“取消”按钮")) {
        exportType = "EXPORT_CLIENT";
    } else {
    	return false;
    }
    if (exportType == "") {
        return;
    }
    mainFrm.exportType.value = exportType;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    mainFrm.act.value = "EXPORT";
    mainFrm.target = "downFrm";
    mainFrm.action = "/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsYearClientCheckServlet";
    mainFrm.submit();
}

function initPage() {
    do_SetPageWidth();
}

</script>
