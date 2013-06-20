<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.sinoflow.dto.SfValidationDTO"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import = "com.sino.base.util.StrUtil"%>

<%
	int x = 0,y=0;
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
	        var ArrAction = new Array("展开", "act_expand.gif","expendAll2","zk_t");
	        var ArrAction1 = new Array("晢叠", "act_collapse.gif","collAll2","zd_t");
	        var ArrAction2 = new Array("新增", "action_draft.gif","create","xz_t");
	        var ArrAction3 = new Array("删除", "del.gif","del_V","del_t");
	        var ArrAction4 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        
	        
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1, ArrAction2,ArrAction3,ArrAction4);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "合法性检查维护";
	        toolBar.treeViewTitle = new Array("工程","工作程序","<img src='../images/buttonbar/t_check.gif'  onclick=\"sdAll('mdc');\"/>","任务","域名","不为空","大小","匹配模式","&nbsp;");
			toolBar.treeViewWidth = new Array("10%","8%","2%","14%","15%","5%","10%","","2%");
	       	toolBar.print();
	    }
	    printTool();
     </script>
  </head>
  <body>
  <jsp:include page="/message/MessageProcess"/>
  	  <div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px" align="left">
  	  <form action="" method="post" name="mainFrm">
  		 <table width="100%" border="0" bordercolor="#666666">
  		 <%
        	if(ds != null && !ds.isEmpty()){
        		SfValidationDTO sd = (SfValidationDTO)ds.getDTO(0);
         %>	
        	<tr class="dataTR" id="tr_<%=++x%>">
  				<td width="" align="left" colspan="8">
  					&nbsp;&nbsp;&nbsp;&nbsp;
  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl2(this,'tr_<%=x%>')"/>
  					<b style="color: #0E8880;font-size:14px;"><%= sd.getProjectName() %></b>
  				</td>
  			</tr>
  			<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=++y%>">
  				<td width="10%"></td>
  				<td width="" align="left" colspan="7">
  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')" />
  					<b style="font-size: 14px"><%= sd.getProcedureName() %></b>
  				</td>
  			</tr>
  			<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=y%>_tr">
  				<td width="10%"></td>
  				<td width="8%"></td>
  				<td width="2%"><input name="mdc" value="<%=sd.getValidateId()%>" type="checkbox"/></td>
  				<td width="15%" onclick="do_ShowDetail('<%=sd.getValidateId()%>');" align="left"><%= sd.getTaskName() %></td>
  				<td width="15%" onclick="do_ShowDetail('<%=sd.getValidateId()%>');" align="left"><%= sd.getFieldName() %></td>
  				<td width="5%" onclick="do_ShowDetail('<%=sd.getValidateId()%>');" align="left">
				<% if(sd.getEmpty()){%>
					<img src="../images/buttonbar/viewyes.gif"/>	 
				<%}%> 
  				</td>
  				<td width="10%" onclick="do_ShowDetail('<%=sd.getValidateId()%>');" align="left"><%= sd.getCheckSize() %></td>
  				<td  width="" onclick="do_ShowDetail('<%=sd.getValidateId()%>');" align="left"><%= sd.getMatchPattern() %></td>
  			</tr>
  			
  			
  		<% 
  				for(int i =1;i<ds.getSize();i++){
  					SfValidationDTO sd2 = (SfValidationDTO)ds.getDTO(i);
  					if(sd2.getProjectName().equals(sd.getProjectName())){
  						if(sd2.getProcedureName().equals(sd.getProcedureName())){
  		%>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr">
  						<td width="10%"></td>
  						<td width="8%"></td>		
  					
  						<%}else{%>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=++y%>">
  						<td width="10%"></td>
						<td width="" align="left" colspan="7">
							<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')"/>
							<b style="font-size: 14px"><%=sd2.getProcedureName()%></b>
						</td>
  					</tr>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr">
						<td width="10%"></td>
						<td width="8%"></td>
  		
  						<%}%>
  						<td width="2%"><input name="mdc" value="<%=sd2.getValidateId()%>" type="checkbox"/></td>
 						<td width="15%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getTaskName() %></td>
		  				<td width="15%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getFieldName() %></td>
		  				<td width="5%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left">
		  				<% if(sd2.getEmpty()){%>
							<img src="../images/buttonbar/viewyes.gif"/>	 
						<%}%> 
		  				</td>
		  				<td width="10%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getCheckSize() %></td>
		  				<td width="" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getMatchPattern() %></td>
					</tr>
  						
  					<%}else{ y=0;%>
  		
					<tr class="dataTR" id="tr_<%=++x%>">
  						<td width="10%" align="left" colspan="8">
  							&nbsp;&nbsp;&nbsp;&nbsp;
  							<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl2(this,'tr_<%=x%>')" />
  							<b style="color: #0E8880;font-size:14px;"><%= sd2.getProjectName() %></b>
  						</td>
  					</tr>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=++y%>">
  						<td width="10%"></td>
  						<td width="" align="left" colspan="7">
  						<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')"/>
  						<b style="font-size: 14px"><%= sd2.getProcedureName() %></b>
  					</td>
  				</tr>
	  			<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr">
	  				<td width="10%"></td>
	  				<td width="8%"></td>
	  				<td width="2%"><input name="mdc" value="<%=sd2.getValidateId()%>" type="checkbox"/></td>
	  				<td width="15%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getTaskName() %></td>
	  				<td width="15%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getFieldName() %></td>
	  				<td width="5%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left">
	  				<% if(sd2.getEmpty()){%>
						<img src="../images/buttonbar/viewyes.gif"/>	 
					<%}%>
	  				</td>
	  				<td width="10%" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getCheckSize() %></td>
	  				<td width="" onclick="do_ShowDetail('<%=sd2.getValidateId()%>');" align="left"><%= sd2.getMatchPattern() %></td>
	  			</tr>
  		<%
  					}
  				sd = sd2;
  				}//for结束
  			}//if的
        %>
  		</table>
  		
			<input type="hidden" name="validateId" value=""/>
  		</form>
  	</div>
  	
  	<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
	</div>
	<%= WebConstant.WAIT_TIP_MSG%>
  </body>
  </html>
  
  <script type="text/javascript" language="JavaScript">
  <!--
  	
  	function create(){//新建
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfValidationServlet?act=<%= WebActionConstant.NEW_ACTION %>");
	}
  	
	function do_ShowDetail(id){//查询详细
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfValidationServlet?act=<%= WebActionConstant.DETAIL_ACTION %>&validateId="+id);
	}
	function del_V(){//删除
		var str = "/servlet/com.sino.sinoflow.servlet.SfValidationServlet?act=<%= WebActionConstant.DELETE_ACTION %>";
		del(str);
	}
	
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfValidationServlet");
   	}
 
	//-->
</script>