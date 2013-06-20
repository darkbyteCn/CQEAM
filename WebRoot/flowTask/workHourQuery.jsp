<%@ page contentType = "text/html; charset=GBK" language = "java" errorPage = "" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%
    RowSet rs = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<html>
	<head>
		<title></title>
		<link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
		<script type = "text/javascript" src = "/WebLibary/js/Constant.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/CommonUtil.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/FormValidate.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/FormProcess.js"></script>
		<script type = "text/javascript" src = "/WebLibary/js/SelectProcess.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/OperationProjectGroupRole.js"></script>
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
		 <script type="text/javascript">
	       function printTool(){
		        var ArrAction = new Array("新增", "action_draft.gif","create","xz_t");
		        var ArrAction3 = new Array("返回", "cancel.gif","fh","de_t");
		        var ArrAction2 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
		        var ArrAction1 = new Array("删除", "del.gif","del","de_t");
		        
		        var toolBar = new SinoPrintToolBar();            
		        toolBar.SinoActions = new Array(ArrAction,ArrAction3,ArrAction1,ArrAction2);
		        toolBar.imagePath = "../images/buttonbar/";
		        toolBar.titleStr = "工作时间定义";
		        toolBar.align="center";
		        toolBar.treeViewTitle = new Array("&nbsp;","<img src='../images/buttonbar/t_check.gif' onclick=\"sdAll('mdc');\"/>","名称","第一段工作时间","第二段工作时间","工作日","");
				toolBar.treeViewWidth = new Array("2%","2%;","15%","15%","15%","","2%");
		      	toolBar.print();
		    }
		    printTool();
	     </script> 
	</head>

	<body>
	<jsp:include page = "/message/MessageProcess" flush = "true" />

		<div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px" align="left">
	  		<form name="mainFrm" action="" method="post">
	  			<table width="100%" border="0" bordercolor="#666666" id="tab">
	  				<% 
	  					if(rs != null && !rs.isEmpty()){
  							for(int i=0;i<rs.getSize();i++){
  								Row r = (Row)rs.getRow(i);%>
	  				<tr class="dataTR">
	  					<td width="2%"></td>
	  					<td width="2%" align="center">
  							<input type="checkbox" name="mdc" value="<%= r.getStrValue("WORK_HOUR_ID") %>"/>
  						</td>
  						<td align="center" width="15%" onclick="do_ShowDetail('<%= r.getStrValue("WORK_HOUR_ID") %>');">
	  						<%= r.getStrValue("WORK_HOUR_NAME") %>
	  					</td>
	  					<td align="center" width="15%" onclick="do_ShowDetail('<%= r.getStrValue("WORK_HOUR_ID") %>');">
	  						<%= r.getStrValue("WORK_BEGIN_1") %>
	  						-<%= r.getStrValue("WORK_END_1") %>
	  					</td>
	  					<td align="center" width="15%" onclick="do_ShowDetail('<%= r.getStrValue("WORK_HOUR_ID") %>');">
	  						<%= r.getStrValue("WORK_BEGIN_2") %>
	  						-<%= r.getStrValue("WORK_END_2") %>
	  					</td>
	  					<td align="center" width="" onclick="do_ShowDetail('<%= r.getStrValue("WORKING_DATE") %>');">
	  						<%= r.getStrValue("wrok_str") %>
	  					</td>
	  					<td width="2%"></td>
	  				</tr>
	  				<%}}%>
	  			</table>
	  		</form>
  		</div>

		<div>
			<%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
			<%=WebConstant.WAIT_TIP_MSG%>
		</div>
	</body>
</html>

<script type="text/javascript">
	 <!-- 
	function create(){//新建
		window.location.assign("/flowTask/workHourDetail.jsp");
	}
  	
	function do_ShowDetail(id){//详细
		window.location.assign(
		"/servlet/com.sino.sinoflow.servlet.SfWorkHourServlet?act=<%= WebActionConstant.DETAIL_ACTION %>&workHourId="+id);
	}
	
	function del(){//删除
		var tc = document.getElementsByName("mdc");
		for(var i=0;i<tc.length;i++){
			if(tc.item(i).checked){
				if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
					mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfWorkHourServlet?act=<%= WebActionConstant.DELETE_ACTION %>";
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
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkHourServlet");
   	}
   	
   	function fh(){//返回
   		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
   		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet");
   	}
	  	
	  //-->
  </script>