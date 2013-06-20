<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
	String srcPage = parser.getParameter("srcPage");
	TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)parser.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	String provinceCode = dto.getServletConfig().getProvinceCode();
	String disapleProp = "";
	if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)){
		disapleProp = "disabled";
	}
	String transferType = dto.getTransferType();
    String transType = dto.getTransType();
	String pageTitle = "";
	String orderNoName = "";
	String deptNameDesc = "";
    if (transType.equals(AssetsDictConstant.ASS_RED)) {//调拨单据查询
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)){
				disapleProp = "disabled";
				if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){
					pageTitle = "TD部门内调拨>>撤销调拨申请";
				} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){
					pageTitle = "TD部门间调拨>>撤销调拨申请";
				} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
					pageTitle = "TD盟市间调拨>>撤销调拨申请";
				}
			} else {
				pageTitle = "TD资产调拨管理>>撤销调拨申请";
			}
		} else {
			if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)){
				if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){
					pageTitle = "TD部门内调拨>>创建调拨申请";
				} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){
					pageTitle = "TD部门间调拨>>创建调拨申请";
				} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
					pageTitle = "TD盟市间调拨>>创建调拨申请";
				}
			} else {
				pageTitle = "TD资产调拨管理>>创建调拨申请";
			}
		}
		orderNoName = "调拨单号";
		deptNameDesc = "调出部门";
    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {//处置单据查询
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "TD资产处置管理>>撤销处置申请";
		} else {
			pageTitle = "TD资产处置管理>>创建处置申请";
		}
		orderNoName = "处置单号";
		deptNameDesc = "申请部门";
    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {//报废单据查询
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "TD资产报废管理>>撤销报废申请";
		} else {
			pageTitle = "TD资产报废管理>>创建报废申请";
		}
		orderNoName = "报废单号";
		deptNameDesc = "申请部门";
    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {//闲置单据查询
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "撤销闲置申请";
		} else {
			pageTitle = "创建闲置申请";
		}
		orderNoName = "闲置单号";
		deptNameDesc = "闲置部门";
    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {//减值单据查询
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "撤销减值申请";
		} else {
			pageTitle = "创建减值申请";
		}
		orderNoName = "减值单号";
		deptNameDesc = "减值部门";
    } else if (transType.equals(AssetsDictConstant.ASS_SHARE)){   //共享单据查询
    	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "撤销共享申请";
		} else {
			pageTitle = "创建共享申请";
		}
		orderNoName = "共享单号";
		deptNameDesc = "共享部门";    	
    } else if (transType.equals(AssetsDictConstant.ASS_SELL)){
    	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "撤销销售申请";
		} else {
			pageTitle = "创建销售申请";
		}
		orderNoName = "销售单号";
		deptNameDesc = "销售部门"; 
    } else if (transType.equals(AssetsDictConstant.ASS_RENT)){
    	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "撤销出租申请";
		} else {
			pageTitle = "创建出租申请";
		}
		orderNoName = "出租单号";
		deptNameDesc = "出租部门"; 
    } else if (transType.equals(AssetsDictConstant.ASS_DONA)){
    	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
			pageTitle = "撤销捐赠申请";
		} else {
			pageTitle = "创建捐赠申请";
		}
		orderNoName = "捐赠单号";
		deptNameDesc = "捐赠部门"; 
    }
%>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();mainFrm.transferType.focus();" onkeydown="autoExeFunction('do_SearchOrder();')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<form action="<%=TdURLDefineList.ASSETS_TRANS_SERVLET_TD%>" method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="<%=transType%>">
	<input type="hidden" name="srcPage" value="<%=srcPage%>">
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
<%
	if(transType.equals(AssetsDictConstant.ASS_RED)){
%>
            <td width="10%" align="right">单据类型：</td>
            <td width="15%"><select name="transferType" style="width:100%" <%=disapleProp%>><%=dto.getTransferTypeOption()%></select></td>
            <td width="10%" align="right"><%=orderNoName%>：</td>
            <td width="15%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="10%" align="right">创建日期：</td>
			<td width="25%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" 
					style="width: 35%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
				到：
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>"
							style="width: 35%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
			</td>
            <td width="15%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
<%
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>
            <img src="/images/eam_images/revoke.jpg" alt="点击撤销" onclick="do_CancelOrder();">
<%
		} else {
%>
			<img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
<%
		}
%>
			</td>
<%
	} else {
%>
            <td width="10%" align="right"><%=orderNoName%>：</td>
            <td width="20%"><input type="text" name="transNo" style="width:100%" value="<%=dto.getTransNo()%>"></td>
            <td width="10%" align="right">创建日期：</td>
            <td width="30%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(startDate, endDate);">
				<input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:40%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fEndPop(startDate, endDate);">
            </td>
            <td width="25%" align="right">
			<img src="/images/eam_images/search.jpg" alt="点击查询" onclick="do_SearchOrder();">
<%
		if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>
            <img src="/images/eam_images/revoke.jpg" alt="点击撤销" onclick="do_CancelOrder();">
<%
		} else {
%>
			<img src="/images/eam_images/new_add.jpg" alt="点击新增" onclick="do_CreateOrder();">
<%
		}
%>
			</td>
<%
	}
%>

        </tr>
    </table>
<%
    if (transType.equals(AssetsDictConstant.ASS_RED)){
%>

<div id="transferDiv" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
	<table width="100%" height="120">
		<tr>
			<td height="60%" width="40%"></td>
			<td height="60%" width="20%"></td>
			<td height="60%" width="40%"></td>
		</tr>
		<tr>
			<td height="60%" width="40%"></td>
			<td bgcolor="#2984CB" width="20%" align="center"><font color="#FFFFFF">选择资产类别</font> </td>
			<td height="60%" width="40%"></td>
		</tr>
		<tr>
			<td height="60%" width="40%"></td>
			<td bgcolor="#2984CB" width="20%" align="center"><img src="/images/eam_images/ok.jpg" onClick="do_SubmitTransfer();">&nbsp;<img src="/images/eam_images/close.jpg" onClick="do_CloseDiv();"></td>
			<td height="60%" width="40%"></td>
		</tr>
		<tr>
			<td height="60%" width="40%"></td>
			<td bgcolor="#2984CB" width="20%"><select style="width:100%" name="faContentCode"><%=dto.getFaContentOption()%></select></td>
			<td height="60%" width="40%"></td>
		</tr>
	</table>
</div>
<%
	}
%>
</form>
<%
	if(!transType.equals(AssetsDictConstant.ASS_RED)){//非调拨单据数据
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>    
			<td align=center width=4%><input type="checkbox" name="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
<%
	}
%>        
			<td align=center width="18%"><%=orderNoName%></td>
			<td align=center width="10%">单据状态</td>
			<td align=center width="26%"><%=deptNameDesc%></td>
			<td align=center width="12%">申请人</td>
			<td align=center width="10%">创建日期</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:855px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				Row row=rows.getRow(i);
%>
        <tr class="dataTR" onclick="showDetail('<%=row.getValue("TRANS_ID")%>')">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>        
          <td width=4% align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
<%
	}
%>          
			<td width="18%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("TRANS_STATUS_DESC")%>"></td>
			<td width="26%"><input type="text" class="finput" readonly value="<%=row.getValue("FROM_DEPT_NAME")%>"></td>
			<td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("CREATED")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
        </tr>
<%
			}
		}
%>
    </table>
</div>
<%
	} else {//调拨单据数据
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:100%">
	<table border=1 width="100%" class="headerTable">
		<tr class=headerTable height="20px">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>    
			<td align=center width="3%"><input type="checkbox" name="mainCheck" onClick="checkAll(this.name, 'subCheck')"></td>
<%
	}
%>        
			<td align=center width="14%">调拨单号</td>
			<td align=center width="10%">单据类型</td>
			<td align=center width="22%">调出单位</td>
			<td align=center width="10%">调拨申请人</td>
			<td align=center width="10%">调拨申请日期</td>
			<td align=center width="10%">调拨单状态</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow: scroll; height: 74%; width: 855px; position: absolute; top: 66px; left: 1px" align="left"
			onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT: fixed; word-break: break-all">
		<%
		String transId = "";
		if (rows != null && !rows.isEmpty()) {
			for (int i = 0; i < rows.getSize(); i++) {
				Row row=rows.getRow(i);
				transferType = row.getStrValue("TRANSFER_TYPE");
				int index = ArrUtil.getArrIndex(AssetsDictConstant.TRANS_OPT_VALUES, transferType);
				transferType = AssetsDictConstant.TRANS_OPT_LABELS_TD[index];
				transId = row.getStrValue("TRANS_ID");
%>
        <tr class="dataTR">
<%
	if(srcPage.equals(AssetsActionConstant.CANCEL_ACTION)){
%>        
		<td width="3%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
<%
	}
%>          
		<td width="14%" onclick="showDetail('<%=transId%>')">
			<input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"> </td>
		<td width="10%" onclick="showDetail('<%=transId%>')">
			<input type="text" class="finput2" readonly value="<%=transferType%>"> </td>
		<td width="22%" onclick="showDetail('<%=transId%>')">
			<input type="text" class="finput" readonly value="<%=row.getValue("FROM_DEPT_NAME")%>"> </td>
		<td width="10%" onclick="showDetail('<%=transId%>')">
			<input type="text" class="finput" readonly value="<%=row.getValue("CREATED")%>"> </td>
		<td width="10%" onclick="showDetail('<%=transId%>')">
			<input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"> </td>
		<td width="10%" onclick="showDetail('<%=transId%>')">
			<input type="text" class="finput" readonly value="<%=row.getValue("TRANS_STATUS_DESC")%>"> </td>
        </tr>
<%
			}
		}
%>
    </table>
</div>
<%
	}
    if (rows != null && !rows.isEmpty()) {
%>
		<div style="position: absolute; bottom:1px; left: 0; right: 20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">

function do_SearchOrder() {
	if(mainFrm.transferType){
		mainFrm.transferType.disabled = false;
	}
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_CreateOrder() {
    var transType = mainFrm.transType.value;
	if(mainFrm.transferType){
		if(mainFrm.transferType.value == ""){
			alert("请先选择调拨单类型！");
			mainFrm.transferType.focus();
			return;
		}
		do_SubmitTransfer();
	} else {
	    var url = "<%=TdURLDefineList.ASSETS_TRANS_SERVLET_TD%>?act=<%=AssetsActionConstant.NEW_ACTION%>&transType=" + transType;
	    var style = "width="+screen.availWidth+",height="+screen.availHeight+",top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
        window.open(url, "transferWin", style);
    }
}


function do_SubmitTransfer(){
	var transType = mainFrm.transType.value;
	var url = "<%=TdURLDefineList.ASSETS_TRANS_SERVLET_TD%>?act=<%=AssetsActionConstant.NEW_ACTION%>&transType=" + transType;
	url += "&transferType=" + mainFrm.transferType.value;
    var style = "width="+screen.availWidth+",height="+screen.availHeight+",top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, "transferWin", style);
}

function do_CloseDiv(){
	document.getElementById("transferDiv").style.visibility = "hidden";
}


function showDetail(transId){
    var transType = mainFrm.transType.value;
    var url = "<%=TdURLDefineList.ASSETS_TRANS_SERVLET_TD%>?act=<%=AssetsActionConstant.EDIT_ACTION%>&transType=" + transType+"&transId="+transId;
//    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    var style = "width="+screen.availWidth+",height="+screen.availHeight+",top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'transferWin', style);
}
function do_CancelOrder(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能执行指定的操作。");
		return;
	}
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$.value){
		alert("没有选择数据，不能执行指定的操作。");
		return;
	}
	if(confirm("确定要撤销选择的单据吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
	    mainFrm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
	    mainFrm.submit();
    }
}
</script>
</html>
