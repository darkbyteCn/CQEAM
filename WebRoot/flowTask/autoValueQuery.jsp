<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.sinoflow.dto.SfAutoValueDTO"%>
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
	        var ArrAction3 = new Array("删除", "del.gif","del_A","del_t");
	        var ArrAction4 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1, ArrAction2,ArrAction3,ArrAction4);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "自动赋值维护";
	        toolBar.treeViewTitle = new Array("工程","过程","<img src='../images/buttonbar/t_check.gif'  onclick=\"sdAll('mdc');\"/>","任务","域名","赋值内容","退回","特送","完成","暂存","签收","&nbsp;","&nbsp;");
			toolBar.treeViewWidth = new Array("10%", "8%","2%", "15%","15%","10%","5%", "5%",  "5%", "5%", "5%",  "","2%");
	        toolBar.print();
	    }
	    printTool();
     </script>
  </head>
  <body>
  <jsp:include page="/message/MessageProcess"/>
 		<div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px" align="left">
 		<form action="" name="mainFrm" method="post">
  		 	<table width="100%" border="0" bordercolor="#666666">
  	 		
  		 	<%
	        	if(ds != null && !ds.isEmpty()){
	        		SfAutoValueDTO sav = (SfAutoValueDTO)ds.getDTO(0);
         	%>	
        	<tr class="dataTR" id="tr_<%=++x%>">
  				<td width="" align="left" colspan="12">
  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl2(this,'tr_<%=x%>')"/>
  					<b style="font-size: 14;color: #0E8880;"><%= sav.getProjectName() %></b>
  				</td>
  			</tr>
  			<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=++y%>">
  				<td width="10%"></td>
  				<td width="" align="left" colspan="11">
  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')" />
  					<b><%= sav.getProcedureName() %></b>
  				</td>
  			</tr>
  			<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=y%>_tr">
  				<td width="10%"></td>
  				<td width="8%"></td>
				<td width="2%"><input type="checkbox" name="mdc" value="<%= sav.getAutoValueId()%>"/></td>
  				<td width="16%" align="left" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');"><%= sav.getTaskName() %></td>
  				<td width="15%" align="left" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');"><%= sav.getFieldName() %></td>
  				<td width="10%" align="left" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');">
		  			<%= sav.getAssignValue() == 0 ? "当前用户" : (sav.getAssignValue() == 1 ? "当前时间":"当前部门" )%>
		  		</td>
  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');">
  					<%if(sav.isTh()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
  				</td>
  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');">
  					<%if(sav.isTs()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
  				</td>
  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');">
  					<%if(sav.isWc()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
  				</td>
  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');">
  					<%if(sav.isZc()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
  				</td>
  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav.getAutoValueId()%>');">
  					<%if(sav.isQs()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
  				</td>
  				<td align="left" width="">
  				</td>
  			</tr>
		
  		 	<% 
  				for(int i =1;i<ds.getSize();i++){
  					SfAutoValueDTO sav2 = (SfAutoValueDTO)ds.getDTO(i);
  					if(sav2.getProjectName().equals(sav.getProjectName())){
  						if(sav2.getProcedureName().equals(sav.getProcedureName())){
  			%>
  					<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=y%>_tr">
  						<td width="10%"></td>
  						<td width="8%"></td>		
  					<%}else{%>
  					<tr class="dataTR" style="display:none" id="tr_<%=x%>_<%=++y%>">
  						<td width="10%"></td>
						<td width="" align="left" colspan="10">
							<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')"/>
							<b><%=sav2.getProcedureName()%></b>
						</td>
  					</tr>
  					<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr">
						<td width="10%"></td>
						<td width="8%"></td>
  		
  						<%}%>
  						<td width="2%"><input type="checkbox" name="mdc" value="<%= sav2.getAutoValueId()%>"/></td>
 						<td width="16%" align="left" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');"><%= sav2.getTaskName() %></td>
		  				<td width="15%" align="left" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');"><%= sav2.getFieldName() %></td>
		  				<td width="10%" align="left" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
		  					<%= sav2.getAssignValue() == 0 ? "当前用户" : (sav2.getAssignValue() == 1 ? "当前时间":"当前部门" )%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
  							<%if(sav2.isTh()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
		  					<%if(sav2.isTs()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
		  					<%if(sav2.isWc()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
		  					<%if(sav2.isZc()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
		  					<%if(sav2.isQs()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="">
  						</td>
					</tr>
  						
  					<%}else{ y=0;%>
  		
					<tr class="dataTR" id="tr_<%=++x%>">
  						<td width="" align="left" colspan="12">
  							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  							<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl2(this,'tr_<%=x%>')"/>
  							<b style="font-size: 14;color: #0E8880;"><%= sav2.getProjectName() %></b>
  						</td>
  					</tr>
		  			<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=++y%>">
		  				<td width="8%"></td>
		  				<td width="" align="left" colspan="11">
		  					<img src="../images/buttonbar/Collapse.gif" onclick="expendOrColl(this,'tr_<%=x%>_<%=y%>_tr')" />
		  					<b><%= sav2.getProcedureName() %></b>
		  				</td>
		  			</tr>
		  			<tr style="display:none" class="dataTR" id="tr_<%=x%>_<%=y%>_tr">
		  				<td width="10%"></td>
		  				<td width="8%"></td>
		  				<td width="2%"><input type="checkbox" name="mdc" value="<%= sav2.getAutoValueId()%>"/></td>
		  				<td width="16%" align="left" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')"><%= sav2.getTaskName() %></td>
		  				<td width="15%" align="left" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')"><%= sav2.getFieldName() %></td>
		  				<td width="10%" align="left" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>');">
		  					<%= sav2.getAssignValue() == 0 ? "当前用户" : (sav2.getAssignValue() == 1 ? "当前时间":"当前部门" )%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')">
		  					<%if(sav2.isTh()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')">
		  					<%if(sav2.isTs()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')">
		  					<%if(sav2.isWc()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')">
		  					<%if(sav2.isZc()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="5%" onclick="do_ShowDetail('<%=sav2.getAutoValueId()%>')">
		  					<%if(sav2.isQs()){%><img src="../images/buttonbar/viewyes.gif"/><%}%>
		  				</td>
		  				<td align="left" width="">
  						</td>
		  			</tr>
		  		
  				<%
  					}
  					sav = sav2;
  				}//for结束
  			}//if的
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
	 	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAutoValueServlet?act=<%= WebActionConstant.NEW_ACTION %>");
	}
	  	
	function do_ShowDetail(id){//查询详细
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAutoValueServlet?act=<%= WebActionConstant.DETAIL_ACTION %>&autoValueId="+id);
	}
	
	function del_A(){//删除
		var str = "/servlet/com.sino.sinoflow.servlet.SfAutoValueServlet?act=<%= WebActionConstant.DELETE_ACTION %>";
		del(str);
	}
	
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfAutoValueServlet");
   	}
  	//-->	
</script>

