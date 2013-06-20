<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@page import="com.sino.base.data.RowSet"%>
<%@page import="com.sino.base.constant.db.QueryConstant"%>
<%@page import="com.sino.base.data.Row"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    	<title>同步权限定义</title>
    	<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    	<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
		<script language="javascript" src="/WebLibary/js/Constant.js"></script>
    	<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    	<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    	<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    	<script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    	<script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
	</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_SearchRole();')">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String ou = (String)request.getAttribute(WebAttrConstant.CITY_OPTION)  ;
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
		<form name="mainFrm" method="post" action="/servlet/com.sino.ams.assetsynchronization.AssetsynchronizationServlet">
			<input type="hidden" name="act" value="" />
			<input type="hidden" name="check" value=""/>
			<input type="hidden" name="employeeNumber" value=""/>
			<script language="javascript">
        		printTitleBar("同步权限定义");
            </script>
			<table border="0" width="100%" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
				<tr>
					<td width="12%" align="right">
						公司名称：
					</td>
					<td width="12%">
						<select name="organizationId" class="select_style1"
							style="width: 100%"><%=ou%></select>
					</td>
					
					</td>
					<td width="30%" align="right">
						<img src="/images/eam_images/search.jpg" id="queryImg" style="cursor: 'hand'" onclick="do_SearchRole();" alt="查询">
						<img src="/images/eam_images/delete.jpg" id="queryImg" style="cursor: 'hand'" onclick="deleteData();" alt="删除">
						<img src="/images/eam_images/new.jpg" id="queryImg"  style="cursor: 'hand'" onclick="create();" alt="新增">
					</td>
				</tr>
			</table>
    		<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:838px">
			    <table class="headerTable" border="1" width="100%">
			        <tr height="22px" onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
			            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','check')"></td>
			            <td align=center width="15%">公司名称</td>
			            <td align=center width="15%">用户ID</td>
			            <td align=center width="15%">员工编号</td>
			            <td align=center width="15%">职责ID</td>
			            <td align=center width="15%">职责应用ID</td>
			            <td align=center width="15%">是否默认</td>
			        </tr>
			    </table>
			</div>
    		<div id="dataDiv" style="overflow:scroll;height:75%;width:847px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
				<table id="dataTable" width="100%" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
			<%
					if (rows != null && !rows.isEmpty()) {
						for (int i = 0; i < rows.getSize(); i++) {
							Row row=rows.getRow(i);
			%>
			        <tr class="dataTR"> 
			        	<td width="3%" align="center"><input type="checkbox" name="check" id="check" value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
						<td width="15%" onclick="show_Detail('<%=row.getValue("EMPLOYEE_NUMBER")%>')" align="center"><%=row.getValue("COMPANY")%></td>
						<td width="15%" onclick="show_Detail('<%=row.getValue("EMPLOYEE_NUMBER")%>')" align="center"><%=row.getValue("USER_ID")%></td>
						<td width="15%" onclick="show_Detail('<%=row.getValue("EMPLOYEE_NUMBER")%>')" align="center"><%=row.getValue("EMPLOYEE_NUMBER")%></td>
						<td width="15%" onclick="show_Detail('<%=row.getValue("EMPLOYEE_NUMBER")%>')" align="center"><%=row.getValue("RESP_ID")%></td>
						<td width="15%" onclick="show_Detail('<%=row.getValue("EMPLOYEE_NUMBER")%>')" align="center"><%=row.getValue("RESP_APPL_ID")%></td>
						<td width="15%" onclick="show_Detail('<%=row.getValue("EMPLOYEE_NUMBER")%>')" align="center"><%=row.getValue("IS_DEFAULT")%></td>
			        </tr>
			<%
						}
					}
			%>
			    </table>
			</div> 

		</form>
		
		<% 
    if (rows != null && !rows.isEmpty()) {
%>
<div style="position: absolute; bottom:1px; left: 0; right: 20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>

<%
    }
%>
	</body>
</html>
<script type="text/javascript">
		function do_SearchRole() {
	        mainFrm.act.value="query";
	        mainFrm.submit();
	    }
	    function create(){//新建
	        mainFrm.act.value="create";
	        mainFrm.submit();
		}
	    function deleteData(){
	    	var checkedCount = getCheckedBoxCount("check");
	    	if (checkedCount < 1) {
        		alert("请至少选择一行数据！");
        		return;
    		}
			if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
				mainFrm.act.value="delete";
	        	mainFrm.submit();
				return;
			}else{
				return;
			}	
		}
	    function show_Detail(employeeNumberNo){//新建
	    	mainFrm.employeeNumber.value=employeeNumberNo;
	        mainFrm.act.value="detail";
	        mainFrm.submit();
		}		
</script>