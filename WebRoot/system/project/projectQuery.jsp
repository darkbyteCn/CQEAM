<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.sinoflow.user.dto.SfProjectDTO" %>
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
	        var ArrAction = new Array("展开", "act_expand.gif","expendAll2","zk_t");
	        var ArrAction1 = new Array("晢叠", "act_collapse.gif","collAll2","zd_t");
	        var ArrAction2 = new Array("工程管理", "menulist.gif","queryList","xz_t");
	        var ArrAction3 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1, ArrAction2,ArrAction3);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "工程维护";
	        toolBar.treeViewTitle = new Array("工程名称","提交日期","提交人","生效日期","&nbsp;");
			toolBar.treeViewWidth = new Array("10%","10%","10%","","2%");
	       	toolBar.print();
	    }
	    printTool();
     </script>
  </head>
  <body>
  


<%
	int x = 0,y=0;
    DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>


<jsp:include page="/message/MessageProcess"/>
  	  <div style="overflow-y:scroll;height:320px;width:100%;left:1px;margin-left:0px" align="left">
  		 <table width="100%" border="0" bordercolor="#666666">
  		 <%
        	if(ds != null && !ds.isEmpty()){
        		SfProjectDTO sd = (SfProjectDTO)ds.getDTO(0);
         %>	
        	<tr class="dataTR" id="tr_<%=++x%>" onclick="do_ShowDetail('<%=sd.getProjectId()%>'); return false;">
  				<td width="" align="left" colspan="4">
  					&nbsp;&nbsp;&nbsp;&nbsp;
  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl2(this,'tr_<%=x%>')"/>
  					<b style="color: #0E8880;font-size:14px;"><%= sd.getProjectName() %></b>
  				</td>
  			</tr>
  			<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=++y%>" onclick="do_ShowDetail('<%=sd.getProjectId()%>'); return false;">
  				<td width="10%"></td>
  				<td width="" align="left" colspan="3">
  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')" />
  					<b style="font-size: 14px"><%= sd.getLastUpdateDate() %></b>
  				</td>
  			</tr>
  			<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=y%>_tr" onclick="do_ShowDetail('<%=sd.getProjectId()%>'); return false;">
  				<td width="10%"></td>
  				<td width="10%"></td>
  				<td width="10%" align="left"><%= sd.getLastUpdatedBy() %></td>
  				<td width="" align="left"><%= sd.getEffectiveDate() %></td>
  			</tr>
  			
  			
  		<% 
  				for(int i =1;i<ds.getSize();i++){
  					SfProjectDTO sd2 = (SfProjectDTO)ds.getDTO(i);
  					if(sd2.getProjectName().equals(sd.getProjectName())){
  						if(sd2.getLastUpdateDate().equals(sd.getLastUpdateDate())){
  		%>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr" onclick="do_ShowDetail('<%=sd2.getProjectId()%>'); return false;">
  						<td width="10%"></td>
  						<td width="10%"></td>		
  					
  						<%}else{%>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=++y%>" onclick="do_ShowDetail('<%=sd2.getProjectId()%>'); return false;">
  						<td width="10%"></td>
						<td width="" align="left" colspan="3">
							<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')"/>
							<b style="font-size: 14px"><%=sd2.getLastUpdateDate()%></b>
						</td>
  					</tr>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr" onclick="do_ShowDetail('<%=sd2.getProjectId()%>'); return false;">
						<td width="10%"></td>
						<td width="10%"></td>
  		
  						<%}%>
  		
 						<td width="10%" align="left"><%= sd2.getLastUpdatedBy() %></td>
		  				<td width="" align="left"><%= sd2.getEffectiveDate() %></td>
					</tr>
  						
  					<%}else{ y=0;%>
  		
					<tr class="dataTR" id="tr_<%=++x%>" onclick="do_ShowDetail('<%=sd2.getProjectId()%>'); return false;">
  						<td width="10%" align="left" colspan="4">
  							&nbsp;&nbsp;&nbsp;&nbsp;
  							<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl2(this,'tr_<%=x%>')" />
  							<b style="color: #0E8880;font-size:14px;"><%= sd2.getProjectName() %></b>
  						</td>
  					</tr>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=++y%>" onclick="do_ShowDetail('<%=sd2.getProjectId()%>'); return false;">
  						<td width="10%"></td>
  						<td width="" align="left" colspan="3">
  						<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')"/>
  						<b style="font-size: 14px"><%= sd2.getLastUpdateDate() %></b>
  					</td>
  				</tr>
	  			<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr" onclick="do_ShowDetail('<%=sd2.getProjectId()%>'); return false;">
	  				<td width="10%"></td>
	  				<td width="10%"></td>
	  				<td width="10%" align="left"><%= sd2.getLastUpdatedBy() %></td>
	  				<td width="" align="left"><%= sd2.getEffectiveDate() %></td>
	  			</tr>
  		<%
  					}
  				sd = sd2;
  				}//for结束
  			}//if的
        %>
  		</table>
  	</div>
  
  <div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
	</div>
	<%= WebConstant.WAIT_TIP_MSG%>
  </body>
  </html>
  
  <script type="text/javascript" language="JavaScript">
  <!--
	function do_ShowDetail(){//接id查询api
		
	}
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.user.servlet.SfProjectServlet");
   	}
	function queryList(){//工程管理员列表
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAdminAuthorityServlet?act=");
	}
 //-->
</script>

