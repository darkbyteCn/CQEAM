<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import = "com.sino.base.util.StrUtil"%>
<%@ page import = "com.sino.sinoflow.dto.SfApplicationDTO"%>

<%

DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);

%>
<html>
  <head>
    <title>validationQuery</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
    <script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
    <script type="text/javascript">
    	
       function printTool(){
	        var ArrAction2 = new Array("新增", "action_draft.gif","create","xz_t");
	        var ArrAction3 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        var ArrAction1 = new Array("删除", "del.gif","del","de_t");
	        
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array( ArrAction2,ArrAction1,ArrAction3);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "应用定义";
	       	toolBar.align = 'center';
	        toolBar.treeViewTitle = new Array("","<img src='../images/buttonbar/t_check.gif' onclick=\"sdAll('mdc');\"/>","应用名称","应用分类名称","组别/角色","显示窗口类弄","是否流程任务","&nbsp;");
			toolBar.treeViewWidth = new Array("4%","2%","20%","20%","","5%","10%","17px");
	       	toolBar.print();
	    }
	    printTool();
     </script>
  </head>
  <body>

  <jsp:include page="/message/MessageProcess"/>
  
 	<div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px" align="left"> 
	     <form action="/servlet/com.sino.sinoflow.servlet.SfApplicationServlet" name="mainFrm" method="post">
	        <table width="100%" border="0" bordercolor="#666666">
	        	<%for(int i=0;i<ds.getSize();i++){
	        		SfApplicationDTO sfa = (SfApplicationDTO)ds.getDTO(i);
	        	%>
	        		<tr class="dataTR">
	        			<td width="3%"></td>
	        			<td width="1%"><input type="checkbox" name="mdc" value="<%= sfa.getAppId()%>"/></td>
		        		<td align="center" width="19%" onclick="do_ShowDetail('<%= sfa.getAppId()%>'); return false;"><%= sfa.getAppName() %></td>
		        		<td align="center" width="20%" onclick="do_ShowDetail('<%= sfa.getAppId()%>'); return false;"><%= sfa.getCategoryName()%></td>
		        		<td align="center" width="" onclick="do_ShowDetail('<%= sfa.getAppId()%>'); return false;"><%= sfa.getGroupName() %>&nbsp;&nbsp;/&nbsp;&nbsp;<%= sfa.getRoleName() %></td>
		        		<td align="center" width="10%" onclick="do_ShowDetail('<%= sfa.getAppId()%>'); return false;"><% if(sfa.getWindowType() == 0){%>右框架中<%}else{%>新窗口中<%} %></td>
		        		<td align="center" width="9%" onclick="do_ShowDetail('<%= sfa.getAppId()%>'); return false;">
		        			<% if(sfa.getIsFlowProcess().equals("Y")){%>&nbsp;<img src="../images/buttonbar/viewyes.gif"/><%}%>
		        		</td>
	        		</tr>	
	        	<%}%>
	       	</table> 
	       	</form>
    </div>  
	<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>  
		<%= WebConstant.WAIT_TIP_MSG%>
  	</body>
 </html>
 <script type="text/javascript" language="JavaScript">
	function do_ShowDetail(id){//查询详细
		window.location.assign(
		"/servlet/com.sino.sinoflow.servlet.SfApplicationServlet?act=<%= WebActionConstant.DETAIL_ACTION %>&appId="+id);
	}
	function del(){//删除
		var tc = document.getElementsByName("mdc");
		for(var i=0;i<tc.length;i++){
			if(tc.item(i).checked){
				if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
					mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfApplicationServlet?act=<%= WebActionConstant.DELETE_ACTION %>";
					mainFrm.submit();
					return;
				}else{
					return;
				}	
			}
		}
	}
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApplicationServlet");
   	}
	function create(){//新建
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApplicationServlet?act=<%= WebActionConstant.NEW_ACTION %>");
	}
</script>