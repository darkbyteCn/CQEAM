<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import = "com.sino.base.util.StrUtil"%>
<%@ page import = "com.sino.sinoflow.dto.SfWorkScheduleDTO" %>
<%@ page import ="com.sino.base.dto.DTOSet"%>

<%
    DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>

<html>
  <head>
    <title>工作时间</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
    <script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
    <script type="text/javascript">
       function printTool(){
	        var ArrAction = new Array("新增", "action_draft.gif","create","xz_t");
	        var ArrAction2 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        var ArrAction1 = new Array("删除", "del.gif","del","de_t");
	        
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1,ArrAction2);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "工作时间定义";
	        toolBar.align="center";
	        toolBar.treeViewTitle = new Array("&nbsp;","<img src='../images/buttonbar/t_check.gif' onclick=\"sdAll('mdc');\"/>","名称","第一段工作时间","第二段工作时间","工作日","节假日","");
			toolBar.treeViewWidth = new Array("2%","1%","12%","16%","16%","","20%","2%");
	      	toolBar.print();
	    }
	    printTool();
     </script>
  </head>
  	
  	<body>
  		<jsp:include page="/message/MessageProcess"/>
  		<div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px" align="left">
  		<form name="mainFrm" action="" method="post">
  			<table width="100%" border="0" bordercolor="#666666" id="tab">
  			<% 
  				if(ds != null && !ds.isEmpty()){
  					for(int i=0;i<ds.getSize();i++){
  						SfWorkScheduleDTO sd = (SfWorkScheduleDTO)ds.getDTO(i);
			%>  						
  					<tr class="dataTR">
  						<td width="1%"></td>
  						<td width="2%" align="center">
  							&nbsp;<input type="checkbox" name="mdc" value="<%=sd.getWorkScheduleId()%>"/>
  						</td>
  						<td width="12%"  align="center" onclick="do_ShowDetail('<%= sd.getWorkScheduleId()%>');">
  							<%= sd.getWorkScheduleName()%>
  						</td>
  						<td width="16%" align="center" onclick="do_ShowDetail('<%= sd.getWorkScheduleId()%>');">
  							<%= sd.getWorkBegin1()%>-<%= sd.getWorkEnd1()%>
  						</td>
  						<td width="16%" align="center" onclick="do_ShowDetail('<%= sd.getWorkScheduleId()%>');">
  							<%= sd.getWorkBegin2()%>-<%= sd.getWorkEnd2()%>
  						</td>
  						<td width="" align="center" onclick="do_ShowDetail('<%= sd.getWorkScheduleId()%>');">  
  							<%= sd.getWorkStr()%>
  						</td>
  						<td width="20%" align="center" onclick="do_ShowDetail('<%= sd.getWorkScheduleId()%>');">
  							<%= sd.getHolidays()%>
  						</td>
  					</tr>	
  			<%		
  					} 
  				}
  			%>
  			</table>
  			
  			
  		</form>
  		
  		</div>
  		<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
		<%= WebConstant.WAIT_TIP_MSG%>
  	</body>
  </html>
  
  <script type="text/javascript">
	 <!-- 
	function create(){//新建
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet?act=<%= WebActionConstant.NEW_ACTION %>");
	}
  	
	function do_ShowDetail(id){//详细
		window.location.assign(
		"/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet?act=<%= WebActionConstant.DETAIL_ACTION %>&workScheduleId="+id);
	}
	
	function del(){//删除
		var tc = document.getElementsByName("mdc");
		for(var i=0;i<tc.length;i++){
			if(tc.item(i).checked){
				if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
					mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet?act=<%= WebActionConstant.DELETE_ACTION %>";
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
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet");
   	}
	  	
	  //-->
  </script>