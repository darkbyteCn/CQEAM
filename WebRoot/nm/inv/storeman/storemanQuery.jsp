<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/nm/inv/headerInclude.jsp"%>
<%@ include file="/nm/inv/headerInclude.htm"%>
<%@ page import="com.sino.nm.ams.inv.storeman.base.constant.web.WebInvActionConstant" %>

<%--
  created by yu
  Date: 2008-12-12
  Time: 09:30:32
--%>

<html>
	<head>
		<title>默认仓管员维护</title>
		<style>
			.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
			.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
			.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
		</style>
	</head>
<body onkeydown="autoExeFunction('do_search()')"> 
<%    
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);   
	boolean hasData = (rows != null && !rows.isEmpty()); 
	EamInvStoremanDTO dto = (EamInvStoremanDTO)request.getAttribute(QueryConstant.QUERY_DTO);//针对Servlet里的dto.setXXX()方法写的  
	Row row = null;   
%>
	<form action="" name="mainFrm" method="post">
		<script type="text/javascript">
			printTitleBar("默认仓管员维护")
		</script>
		<%=WebInvConstant.WAIT_TIP_MSG%>
		<table width="100%" border="0" class="queryHeadBg">
			<tr>
				<!--  
				<td width="5%" align="right">公司：</td>
				<td width="12%" align="right"><select name="organizationId" style="width: 100%"><%=dto.getOuOption()%></select></td>
				-->
				<td width="8%" align="right">仓库类型：</td>
				<td width="10%" align="right"><select name="objectCategory" style="width: 100%" type="text"><%=dto.getInvCategoryOpt()%></select></td>
				<td width="8%" align="right">业务类型：</td>
				<td width="10%" align="right"><select name="businessCategory" style="width: 100%" type="text"><%=dto.getBizCategoryOpt()%></select></td>
				<td width="5%" align="right">仓库：</td>
				<td width="16%" align="right">
					<input type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>" style="width:80%" readonly="readonly" class="readonlyInput"><a href="#" title="点击选择仓库" class="linka" onclick="do_SelectStore();">[…]</a>
					<input type="hidden" name="workorderObjectNo1">
				</td>
				<td align="right" width="26%">
					<img src="/images/button/query.gif" style="cursor: 'hand'" alt="查询" onclick="do_search();">
					<!--  
					<img src="/images/button/add.gif" style="cursor: 'hand'" alt="新增" onclick="do_add();">
					-->
					<img src="/images/button/updateBatch.gif" style="cursor: 'hand'" alt="批量设置仓管员" onclick="do_BatchSet();">
				</td>
			</tr>
		</table>
		<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
			<table width="100%" class="headerTable" border="1">
				<tr height="20" style="cursor:hand" title="点击全选或取消全选" onClick="executeClick(this)"> 
					<td width="3%" align="center" style="padding: 0">
						<input type="checkbox" name="titleCheck" class="headCheckbox" id="controlBox" onPropertyChange="checkAll('titleCheck','subCheck')">
					</td>
					<td width="17%" align="center">仓库名称</td>
					<td width="10%" align="center">仓库类型</td>
					<td width="10%" align="center">业务类型</td>
					<td width="15%" align="center">仓管员</td>
					<td width="10%" align="center">创建人</td>
					<td width="10%" align="center">创建日期</td>
					<td width="10%" align="center">修改人</td>
					<td width="10%" align="center">修改日期</td>
				</tr>
			</table>
		</div>
		<div style="overflow-y:scroll;left:0px;width:100%;height:330px">
			<table width="100%" border="1" bordercolor="#666666">
<% 
	if(hasData) {
		for(int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
				<tr height="20" style="cursor: 'hand'" onmousemove="style.backgroundColor='#EFEFEF'" onmouseout="style.backgroundColor='#ffffff'">			  
					<td width="3%" align="center" style="padding: 0"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>			
					<td width="17%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					
					<input type="text" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>" class="finput"> 
					</td>
					<td width="10%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<%=row.getValue("INV_CATEGORY_NAME")%>
					</td>
					<td width="10%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<%=row.getValue("BIZ_CATEGORY_NAME")%>
					</td>
					<td width="15%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<%=row.getValue("USER_NAME") %>
					</td>
					<td width="10%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<%=row.getValue("CREATED_USER")%>
					</td>
					<td width="10%" align="center" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<%=row.getValue("CREATION_DATE")%>
					</td>
					<td width="10%" align="left" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<input type="text" name="updatedUser" value="<%=row.getValue("UPDATED_USER")%>" class="finput">
					</td>
					<td width="10%" align="center" onclick="show_detail('<%=row.getValue("WORKORDER_OBJECT_NO") %>', '<%=row.getValue("STOREMAN_ID") %>')">
					<%=row.getValue("LAST_UPDATE_DATE")%>
					</td>
				</tr>
<% 
		}
	}
%>
			</table>
		</div>

		<input name="act" type="hidden">  
		<input type="hidden" name="userId" value="">
	</form>
<%
	if(hasData){
%>
	<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}	
%>
	<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"></jsp:include>
</body>
</html>
<script type="text/javascript">

function do_search(){
	
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.action = "/servlet/com.sino.nm.ams.inv.storeman.servlet.EamInvStoremanServlet";
	
	mainFrm.submit();
}

function show_detail(workorderObjectNo, storemanId){
	/*
	if(storemanId != ""){
		var url = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&workorderObjectNo=" + workorderObjectNo + "&storemanId=" + storemanId;
		var popscript = 'width=600,height=400,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
		window.open(url, 'basePot', popscript);
	} else {
		do_add(workorderObjectNo, storemanId);
	}
	*/
    
    if (storemanId != "") {
    	var url="/servlet/com.sino.nm.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_USER_ID%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var objs = window.showModalDialog(url, null, popscript);
    	if(objs){
       		dto2Frm(objs[0], "mainFrm");
       		mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
    		mainFrm.action = "/servlet/com.sino.nm.ams.inv.storeman.servlet.EamInvStoremanServlet?storemanId=" + storemanId;
    		mainFrm.submit();
    	}
    } else {
		//do_add(workorderObjectNo, storemanId);
		var url="/servlet/com.sino.nm.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_USER_ID%>";
    	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    	var objs = window.showModalDialog(url, null, popscript);
    	if(objs){
       		dto2Frm(objs[0], "mainFrm");
       		mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
	    	mainFrm.action = "/servlet/com.sino.nm.ams.inv.storeman.servlet.EamInvStoremanServlet?workorderObjectNo=" + workorderObjectNo + "&storemanId=" + storemanId;
	    	mainFrm.submit();
    	}
	}
}

function do_SelectStore(){
	
	var  url="/servlet/com.sino.nm.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
       var vendorName = null;
       document.forms[0].workorderObjectNo1.value = vendorNames[0].workorderObjectNo;
       document.forms[0].workorderObjectName.value = vendorNames[0].workorderObjectName;
    }
}

function do_add(workorderObjectNo, storemanId){
    
    var url="/servlet/com.sino.nm.ams.inv.storeman.servlet.EamInvStoremanServlet?act=<%=WebActionConstant.NEW_ACTION%>&workorderObjectNo=" + workorderObjectNo + "&storemanId=" + storemanId;
    //var url = "/inv/storeman/addStoreman.jsp";
    var popscript = 'width=600,height=400,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
	window.open(url, 'basePot', popscript);
    //opener.location.href = "/servlet/com.sino.ams.carddepot.servlet.YsCardDefineServlet?act=<%--=WebActionConstant.QUERY_ACTION--%>";	
}


function do_save(){
	
}




function do_BatchSet(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询后再执行本操作！");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请选择数据后再执行本操作！");
		return;
	}
	var url="/servlet/com.sino.nm.ams.inv.storeman.bean.AMSInvLookUpServlet?lookUpName=<%=LookUpInvConstant.LOOK_UP_USER_ID%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var objs = window.showModalDialog(url, null, popscript);
    if(objs){
		dto2Frm(objs[0], "mainFrm");
		mainFrm.act.value = "<%=WebInvActionConstant.UPDATE_ACTION_USERS%>";
    	mainFrm.action = "/servlet/com.sino.nm.ams.inv.storeman.servlet.EamInvStoremanServlet"; 
    	mainFrm.submit();
    }
}
</script>