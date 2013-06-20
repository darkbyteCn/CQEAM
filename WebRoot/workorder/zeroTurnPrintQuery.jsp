<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnLineDTO"%>
<html>
<head> 
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        ZeroTurnLineDTO dto = (ZeroTurnLineDTO)reqParser.getAttribute(QueryConstant.QUERY_DTO);
        String procureCode=dto.getProcureCode();
        String action = reqParser.getParameter("act");
    %>
<title>转资条码打印</title>
</head>

<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onLoad="initPage();helpInit('4.4.4');">
<input type="hidden" name="helpId" value=""> 
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.ZeroTurnPrintQueryServlet">
<script type="text/javascript">
      printTitleBar("零购资产转资条码打印");
</script>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="checkedBarcode" id="checkedBarcode" value="">
<table width="100%" border=0 class="queryTable" cellpadding="2" cellspacing="0"
        style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all"> 
    <tr>
    	<td align="right" width="8%">公司</td>
        <td width="18%"><select style="width:80%" class="select_style1" name="organizationId" onchange="changeOu();"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%> </select> </td>
       	<td align="right" width="8%">采购单号</td>	
        <td width="15%" height="18">
              <input name="procureCode" type="text" id="procureCode" value="<%=procureCode%>"    style="width:80%">
        </td> 
        <td align="right" width="8%"></td>
        <td width="8%">
        </td>
        <td width="2%">&nbsp</td>
   		<td align="right" width="8%">标签选项</td>
        <td width="22%">
        	<input type="radio" name="printType" checked="checked" value="1"/>大60*38mm
        	<input type="radio" name="printType" value="2"/>小60*10mm
        </td>
  		 
    </tr>
    <tr>
    	<td align="right" width="8%"></td>
        <td width="18%">
        </td>
         <td align="right" width="8%"</td>
        <td width="18%" colspan="4">
        </td>
        <td colspan="2" align="left" width="30%"> 
        	<img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_Search(); return false;">&nbsp
        	<img src="/images/eam_images/print.jpg" onClick="do_Print();" alt="打印条码">&nbsp
			<img src="/images/eam_images/export.jpg" alt="导出EXCLE" onClick="do_export()">
        </td>
    </tr>
</table>

<div id="headDiv" style="overflow:hidden;position:absolute;top:90px;left:0px;width:250%">
	<table class="headerTable" border="1" width="2500">
		<tr height=22px >
			<td align=center width="1.5%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
			<td width="4%" align="center"><span class="resizeDivClass"></span>标签号</td>
			<td width="3%" align="center"><span class="resizeDivClass"></span>历史打印数 </td>
            <td width="6%" align="center"><span class="resizeDivClass"></span>地点编号</td>
            <td width="12%" align="center"><span class="resizeDivClass"></span>地点简称</td>
			<td width="3%" align="center"><span class="resizeDivClass"></span>采购单号</td>
            <td width="4%" align="center"><span class="resizeDivClass"></span>公司</td>
                   
            <td width="4%" align="center"><span class="resizeDivClass"></span>资产名称</td>
            <td width="6%" align="center"><span class="resizeDivClass"></span>规格型号</td>
            <td width="2%" align="center"><span class="resizeDivClass"></span>数量</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>资产类别</td>
            <td width="13%" align="center"><span class="resizeDivClass"></span>责任部门</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>责任人</td>
            <td width="10%" align="center"><span class="resizeDivClass"></span>使用部门</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>使用人</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>创建日期</td>
            <td width="3%" align="center"><span class="resizeDivClass"></span>最近修改日期</td>
            <td width="0%" align="center" style="display: none"></td>
		</tr>
	</table>
</div>

<div id="dataDiv" style="overflow:scroll;height:65%;width:250%;position:absolute;top:112px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="2500" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:keep-all">
        <%
       		RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    		boolean hasData = (rows != null && !rows.isEmpty());
        	if (hasData) {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                String barcode=row.getValue("BARCODE").toString().trim();
        %>
        <tr >
        	<td width="1.5%" align="center">
        	<input type="checkbox" name="subCheck"  value="<%=row.getValue("BARCODE")%>"> 
        	</td>
        	
        	<td width="4%" align = "center" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><input type="text" style="width: 100%;border: none " name="barcode2" value="<%=row.getValue("BARCODE")%>" /></td>
        	<td width="3%" align = "center" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("PRINT_NUM")%></td>
			<td width="6%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("ASSETS_LOCATION_CODE")%></td>
            <td width="12%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("ASSETS_LOCATION")%></td>
            <td width="3%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("REMARK2")%></td>
            <td width="4%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("ORG_NAME")%></td>
            
            <td width="4%" align = "center" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><input type="text" style="width: 100%;border: none " name="itemName" value="<%=row.getValue("ITEM_NAME")%>" /> </td>
            <td width="6%" align = "center" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><input type="text" style="width: 100%;border: none " name="itemSpec" value="<%=row.getValue("ITEM_SPEC")%>" /></td>
            <td width="2%" align = "right" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("ITEM_QTY")%></td>
            <td width="3%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("ITEM_CATEGORY_DESC")%></td>
            <td width="13%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("DEPT_NAME")%></td>
            <td width="3%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("USER_NAME")%></td>
			<td width="10%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("MAINTAIN_DEPT")%></td>
            <td width="3%" align = "left" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("MAINTAIN_USER")%></td>
            <td width="3%" align = "center" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("CREATION_DATE") != "" ? row.getValue("CREATION_DATE").toString().substring(0,10) : ""%></td>
            <td width="3%" align = "center" title="点击查看“<%=barcode%>”打印详细信息"  onClick="do_History('<%=barcode%>')"><%=row.getValue("LAST_UPDATE_DATE") != "" ? row.getValue("LAST_UPDATE_DATE").toString().substring(0,10) : "" %></td>
            <td width="0%" align="center" style="display: none">
            <input type="hidden" name="opinionType">
        	<input type="hidden" name="partExport"/>
        	<input type="hidden" name="startDate2" value="<%=row.getValue("START_DATE")%>" />
        	<input type="hidden" name="companyName" value="<%=row.getValue("ORG_NAME")%>" />
        	<input type="hidden" name="printNum" value="<%=row.getValue("PRINT_NUM")%>" />
        	</td>
            <%

                }    }

            %>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div id="pageNaviDiv"  style="position:absolute;top:93%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">
	function initPage(){
		do_SetPageWidth();
	}
	
	function do_History(barcode){
		var url = "/servlet/com.sino.ams.workorder.servlet.TrunListPrintQueryServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	    var winName = "assetsWin";
	    var style="width=660,height=375,left=80,top=105";
	    window.open(url, winName, style);
    }
	
	function getTotal(){
		var total = 0;
		var chgPagerTb = document.getElementById( "$$$changePageTable$$$" );
		var td0 = chgPagerTb.rows[0].cells[0];
		
		var totaltext = td0.innerHTML;
		if( totaltext ){
			total = totaltext.substring( 1 , totaltext.length -1 );
		}
		return Number( total );
	}
	
	function do_export(){
		var judge;
		var isAllCheck=document.getElementsByName("subCheck");
		var opinion=document.getElementById("opinionType");
		var partEx=document.getElementById("partExport");
		var array="";
		for(i=0;i<isAllCheck.length;i++){
			if(isAllCheck[i].checked){
				array+=isAllCheck[i].value+"_";
				judge="checked";
			}
		}
		if(judge!="checked"){ 
			if(!confirm("是否导出全部数据")){
				return;	
			}
			if( getTotal() > 60000 ){
				alert( "数据量过大，请增加查询条件" );
				return;
			}
			/****
			var workorderObjectNameVal = document.getElementById("workorderObjectName").value;
			if( workorderObjectNameVal == null || workorderObjectNameVal == "" ){
				alert("数据量过大，建议增加查询条件导出");
				return;	
			}
			***/
			opinion.value="all";
		}else{
			opinion.value="part";
			partEx.value=array;
		}
		mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
		mainFrm.submit();
		
		try{
			openExportMsgDiv();
		}catch(ex){
			alert( ex.message );
		}
	}

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.target = "_self";
        mainFrm.submit()
    } 
    
    function showLocation() {
        var userPara = "organizationId=" + document.getElementById("organizationId").value ;
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
            }
        } else {
        	mainFrm.workorderObjectCode.value = "";
        	mainFrm.workorderObjectName.value = "";
        }
    }
    function showUser() {
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
        	mainFrm.executeUserName.value = "";
        }
    }

	var xmlHttp;
    function getDeptOpt() {
        var organizationId = document.getElementById("organizationId").value ;
        var url = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=CHOOSE_GROUP&organizationId=" + organizationId;
        xmlHttp = GetXmlHttpObject(setDeptOpt);
        xmlHttp.open('POST', url, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
        xmlHttp.send("a=1");
    }

    function setDeptOpt() {
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
            if (xmlHttp.status == 200) {//成功
                var resText = xmlHttp.responseText;
                var resArray = resText.parseJSON();
                if (resArray == "ERROR") {
                    alert(resText);
                } else {
                    var littleCategoryObj = document.getElementById("deptCode");
                    littleCategoryObj.length = 0;
                    var emptyOption = new Option("--请选择--", "");
                    littleCategoryObj.add(emptyOption)
                    if (resArray.length > 0) {
                        var retStr;
                        for (var i = 0; i < resArray.length; i++) {
                            retStr = resArray[i];
                            var arr = retStr.split("$");
                            var option = new Option(arr[1], arr[0]);
                            littleCategoryObj.add(option)
                        }
                    }
                }
                xmlHttp = null;
            }
        }
    }
    
    function getLinePrintCount( checkObj ){
    	var printNumObj = checkObj.parentElement.parentElement.lastChild.children[4]; 
    	return printNumObj;
    }
    
    function do_Print() { 
    	var checkedValue = getCheckBoxValue( "subCheck" , "," );
    	if( checkedValue == "" ){
    		alert( "请选中需要打印条码的记录!" );
    		return false;
    	}
    	document.getElementById("checkedBarcode").value = checkedValue;
    	
    	var isPrint = false;
    	var printMsg = "";
    	
    	var allCheckObj = document.getElementsByName("subCheck");
    	var printNum = 0;
    	for (var i = 0; i < allCheckObj.length; i++) {
            if (allCheckObj[i].type == "checkbox" && allCheckObj[i].checked) { 
               	  printNum = getLinePrintCount(allCheckObj[i]).value;
               	  //alert( printNum );
               	  if( Number( printNum ) > 0 ){
               	  	  isPrint = true;
               	  	  printMsg = allCheckObj[i].value + "打印了" + printNum + "次. " ;
               	  	  break;
               	  }
            }
        } 
        
        if( isPrint ){
        	if( !confirm( "所选条码中存在已打印条码，是否继续?" ) ){
        		//window.open( "print" );
			    return;
        	} 
        } 
        
        //window.open( 'about:blank', 'newwindow', 'height=500,width=800,top=120,left=120,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
       	mainFrm.act.value = "<%=AssetsActionConstant.PRINT_BARCODE_ACTION%>";
		mainFrm.target = "_blank";
	    mainFrm.submit(); 
    	mainFrm.target = "self";
    	
	}
	
	function changeOu() {
		document.getElementById("deptCode").value = "";
		document.getElementById("deptName").value = "";
		document.getElementById("workorderObjectCode").value = "";
		document.getElementById("workorderObjectName").value = "";
	}
	
	function chooseDeptInfo() {
		var userPara = "organizationId=" + document.getElementById("organizationId").value ;
	    var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU2%>";
	    var dialogWidth = 50.6;
	    var dialogHeight = 30;
	    var dept = getLookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
	    if(dept){
	        dto2Frm(dept[0], "mainFrm");
	        mainFrm.deptName.title = mainFrm.deptName.value;
	    } else {
	    	mainFrm.deptName.value = "";
	    	mainFrm.deptCode.value = "";
	    }
	}

	
</script>