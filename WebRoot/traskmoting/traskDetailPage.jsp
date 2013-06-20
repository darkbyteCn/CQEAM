<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import=" com.sino.traskmoting.dto.SfActInfoDTO"%>


<html>
	<head>
		<title>办理过程</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
		<script type="text/javascript">
       function printTool(){
	        var ArrAction2 = new Array("取消", "action_cancel.gif","cancel","xz_t");
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array( ArrAction2);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "办理过程";
	        toolBar.treeViewTitle = new Array("","办理人","任务名称","办理时间","完成时间","");
			toolBar.treeViewWidth = new Array("2%","20%","20%","16%","16%","2%");
	       	toolBar.print();
	    }
	    printTool();
     </script>
	</head>
	<%  %>
	<body>
		<jsp:include
			page="/message/MessageProcess" />
		<%
           DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
         %>
		<div
			style="overflow-y: auto; overflow-x: auto; height: 450px; width: 100%; left: 1px; margin-left: 0px"
			align="left">
			 	<table width="100%">
					<%if(ds!=null&&!ds.isEmpty()){
			     SfActInfoDTO std=(SfActInfoDTO)ds.getDTO(0);
			     for(int i=0;i<ds.getSize();i++){
                     std = (SfActInfoDTO)ds.getDTO(i);
                 %>
					<tr class="dataTR">
	        			<td width="2%"></td>
	        			<td align="left" width="19%"  ><%= std.getSfactPickUser()%></td>
						<td align="left" width="19%"  ><%= std.getSfactTaskName() %></td>
						<td align=left width="19%"><%= std.getSfactSignDate() %></td>
						<td align="left" width="19%"  >
						<IMG alt=expire src="/images/notExpire.gif" border=0><%= std.getSfactCompleteDate() %></td>
			     		<td width="2%"></td>
					</tr>
					<%} 
					%>
					<%}
			     %>
				</table>
			 
		</div>
		<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
		</div>
		<%=WebConstant.WAIT_TIP_MSG%>
	</body>
</html>

<script type="text/javascript" language="JavaScript">

   function cancel(){
    		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    		window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoServlet");
    	}
	function doReload(){//返回
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoServlet?act=<%=WebActionConstant.DETAIL_ACTION%>");
   	}
</script>
