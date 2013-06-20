<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<%@ page import="java.util.List"%> 


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
RequestParser reqParser = new RequestParser();
reqParser.transData(request); 
//EtsWorkorderDTO dto = (EtsWorkorderDTO)reqParser.getAttribute(QueryConstant.QUERY_DTO);
String action = reqParser.getParameter("act"); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <base target="_self" />
  <body leftmargin="0" topmargin="0" onload="do_SetPageWidth();">
  <jsp:include page="/message/MessageProcess"/>
<jsp:include page="/public/exportMsg.jsp"/>
  
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.TrunListQueryServlet">
     <table class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
      <input type="hidden" name="act" value="<%=action%>">
        <tr>
            <td width="10%" align="right">工程编码：</td>
            <td width="20%">
            <input type="text" name="prjId" class="input_style1" style="width:100%" value="">
            </td>
            
        	 <td width="5%" align="right">地点编码：</td>
            <td width="20%"><input type="text" name="contentCode" class="input_style1" style="width:100%" value=""></td>
            <td width="20%" align="right"> 
			    <img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_SearchOrder(); return false;">&nbsp;&nbsp;&nbsp;
				<img src="/images/eam_images/confirm.jpg" onclick="do_confirm()" alt="确认">
			</td> 
        </tr>
   	 </table>
	<table class="headerTable" border="1" width="100%">
		<tr style="cursor:pointer;height:23px">
			<td align="center" width="4%"><input type="checkbox" name="titleCheck"  onPropertyChange="checkAll('titleCheck','params')"/></td> 
			<td align=center width="15%">工程编码</td>
			<td align=center width="25%">工程名称</td>
			<td align=center width="20%">地点编码</td>
			<td align=center width="30%">地点名称</td>	
		</tr>
	</table>
<div id="dataDiv" style="overflow-y:scroll;height:88%;width:855px;position:absolute;top:53px;left:1px" align="left">
	<table class="dataTable" border="1" width="100%">
	  <%
        	List rows = (List)request.getAttribute("addList");
    		boolean hasData = (rows != null &&  rows.size()>0);
        	if (hasData) {
        		EtsWorkorderDTO row = null;
            for (int i = 0; i < rows.size(); i++) {
                row = (EtsWorkorderDTO)rows.get(i);
        %>
        <tr class="dataTR" height="22">
        	<td align="center" width="4%"><input type="checkbox"   name="params" value="<%=row.getPrjId()%>-<%=row.getContentCode()%>" /></td>  
			<td align=center width="15%"><%=row.getProjectCode()%></td>
			<td align=center width="25%"><%=row.getProjectName()%></td>
			<td align=center width="20%"><%=row.getWorkorderObjectCode()%></td>
			<td align=center width="31%"><%=row.getWorkorderObjectName()%></td>
            <%

                }    }

            %>
	 
	</table>
     </div>
  </form>
  </body>
</html>

<script language="javascript">
	function initPage(){
		do_SetPageWidth();
	}
	
	function do_confirm() {
		 
		if(confirm("确定提交吗？ 继续请点击“确定” 否则点击“取消”")){
				mainFrm.act.value="CONFIRM";
				//mainFrm.target = "_self";
	        	mainFrm.submit();
				return;
		}else{
				return;
		}
	}
	
	 function do_SearchOrder() { 
		// alert();
        //document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";	
        mainFrm.act.value = "addressQy"; 
	    mainFrm.submit();
    }
</script>