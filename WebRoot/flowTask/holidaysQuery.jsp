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
		        toolBar.titleStr = "节假日定义";
		        toolBar.align="center";
		        toolBar.treeViewTitle = new Array("&nbsp;","<img src='../images/buttonbar/t_check.gif' onclick=\"sdAll('mdc');\"/>","名称","年份","假日","&nbsp;","");
				toolBar.treeViewWidth = new Array("2%","2%;","15%","15%","20%","","2%");
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
					<% if(rs != null && !rs.isEmpty()){
  							for(int i=0;i<rs.getSize();i++){
  								Row r = (Row)rs.getRow(i);%>
						<tr class="dataTR">
	  						<td width="2%"></td>
	  						<td width="2%" align="center">
  								<input type="checkbox" name="mdc" value="<%= r.getStrValue("HOLIDAYS_ID") %>"/>
  							</td>
  							<td align="center" width="15%" onclick="do_ShowDetail('<%= r.getStrValue("HOLIDAYS_ID") %>');">
	  							<%= r.getStrValue("NAME") %>
	  						</td>
	  						<td align="center" width="15%" onclick="do_ShowDetail('<%= r.getStrValue("HOLIDAYS_ID") %>');">
	  							<%= r.getStrValue("YEAR") %>
	  						</td>
	  						<td align="center" width="20%" onclick="do_ShowDetail('<%= r.getStrValue("HOLIDAYS_ID") %>');">
	  							<%= r.getStrValue("HOLIDAYS") %>
	  						</td>
	  						<td width=""></td>
	  						<td width="2%"></td>
						</tr>
  					<%}} %>
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
		window.location.assign("/flowTask/holidaysDetail.jsp");
	}
  	
	function do_ShowDetail(id){//详细
		window.location.assign(
		"/servlet/com.sino.sinoflow.servlet.SfHolidaysServlet?act=<%= WebActionConstant.DETAIL_ACTION %>&holidaysId="+id);
	}
	
	function del(){//删除
		var tc = document.getElementsByName("mdc");
		for(var i=0;i<tc.length;i++){
			if(tc.item(i).checked){
				if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
					mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfHolidaysServlet?act=<%= WebActionConstant.DELETE_ACTION %>";
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
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfHolidaysServlet");
   	}
   	
   	function fh(){//返回
   		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
   		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet");
   	}
	  	
	  //-->
  </script>