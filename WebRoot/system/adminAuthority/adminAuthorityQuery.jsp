<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.sinoflow.dto.SfAdminAuthorityDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<html>
  <head>
    <title>api</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
    <script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
    <script type="text/javascript">
       function printTool(){
	        var ArrAction = new Array("展开", "act_expand.gif","expendAll","zk_t");
	        var ArrAction1 = new Array("晢叠", "act_collapse.gif","collAll","zd_t");
	        var ArrAction2 = new Array("新增", "action_draft.gif","create","xz_t");
//            var ArrAction4 = new Array("删除", "del.gif","doDelete","d_t");
            var ArrAction3 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        
	        var toolBar = new SinoPrintToolBar();            
//	        toolBar.SinoActions = new Array(ArrAction,ArrAction1, ArrAction2,ArrAction4, ArrAction3);
            toolBar.SinoActions = new Array(ArrAction,ArrAction1,ArrAction2,ArrAction3)
            toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "工程维护";
	        toolBar.treeViewTitle = new Array("指定管理员","工程名称/组别名称","&nbsp;");
			toolBar.treeViewWidth = new Array("10%","","2%");
	       	toolBar.print();
	    }
	    printTool();
     </script>
  </head>
  <body>
   <jsp:include page="/message/MessageProcess"/>
   <% DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);int x = 0;%>
  <div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px">
    <table width="100%" border="0" bordercolor="#666666">
    	<%
    		if (ds != null && !ds.isEmpty()) {
        			SfAdminAuthorityDTO sad = (SfAdminAuthorityDTO)ds.getDTO(0);
        %>
        			<tr id="tr_<%=++x%>">
        				<td colspan="2">
        					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_tr')"/>
            				<b style="font-size: 14px"><%= sad.getLoginName() %></b>
        				</td>
        			</tr>
        			
        		<tr class="dataTR" id="tr_<%=x%>_tr" onclick="do_ShowDetail('<%=sad.getAdminId()%>'); return false;" style="display:none">
            		<td width="10%"></td>
	            	<td width="" align="left">
	            		<%= sad.getProjectName() %>
	            		/
	            		<%= sad.getGroupName() %>
	            	</td>
            	</tr>
        <%
        	for(int i=1;i<ds.getSize();i++){
        		SfAdminAuthorityDTO sad2 = (SfAdminAuthorityDTO)ds.getDTO(i);
        		if(sad2.getUserId() == sad.getUserId()){
        %>
       			 <tr class="dataTR" id="tr_<%=x%>_tr" onclick="do_ShowDetail('<%=sad2.getAdminId()%>'); return false;" style="display:none">
            		<td width="10%"></td>
	            	<td width="" align="left">
	            		<%= sad2.getProjectName() %>
	            		/
	            		<%= sad2.getGroupName() %>
	            	</td>
            	</tr>
        <%	}else{ %>
       		<tr id="tr_<%=++x%>">
   				<td colspan="2">
   					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_tr')"/>
       				<b style="font-size: 14px"><%= sad2.getLoginName() %></b>
   				</td>
   			</tr>
       		<tr class="dataTR" id="tr_<%=x%>_tr" onclick="do_ShowDetail('<%=sad2.getAdminId()%>'); return false;" style="display:none">
           		<td width="10%"></td>
            	<td width="" align="left">
            		<%= sad2.getProjectName() %>
            		/
            		<%= sad2.getGroupName() %>
            	</td>
           	</tr>
            	
        <%		}
        	sad = sad2;
	        }
    	}  
    	%>
    </table>
  </div>
  
  
  <div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
	<%= WebConstant.WAIT_TIP_MSG%>
  </body>
  </html>
  
<script type="text/javascript" language="JavaScript">
<!--
	function do_ShowDetail(){//接id查询api
		
	}
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAdminAuthorityServlet?act=");
   	}
	function create(){//新建
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAdminAuthorityServlet?act=<%= WebActionConstant.NEW_ACTION %>");
	}
/*
    function doDelete() {//删除
        window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAdminAuthorityServlet?act=<%= WebActionConstant.DELETE_ACTION %>");
    }
*/
//-->
</script>

