<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.traskmoting.dto.SfActInfoDTO"%>


<html>
	<head>
		<title>流程监控</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
		<script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
	</head>
	<body>
    <form name="listform" action="/servlet/com.sino.sinoflow.servlet.SfActInfoTaskServlet" method="POST">
        <jsp:include
			page="/message/MessageProcess" />
        <script type="text/javascript">
       function printTool(){
            var ArrAction = new Array("展开", "act_expand.gif","doExpandAll","zk_t");
            var ArrAction1 = new Array("晢叠", "act_collapse.gif","doCollapseAll","zd_t");
            var ArrAction3 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
            var ArrAction4 = new Array("监控项<select style='width:100' id='check'  onchange='getArrActionValue(this)'><option value='0'>--请选择--</option><option value='users'>按用户</option><option value='role'>按角色</option><option value='traskName'>按任务</option><option value='Otrole'>超时/按角色</option><option value='traskOtName'>超时/按任务</option><option value='Otusers'>超时/按用户</option><option value='Enote'>按催办</option></select>","","doNull","sh_t");
//            var ArrAction5 = new Array("催办", "../buttonbar/mn_alarm.gif", "doENoteActs", "cb_t");
            var toolBar = new SinoPrintToolBar();
//	        toolBar.SinoActions = new Array(ArrAction,ArrAction1,ArrAction4,ArrAction5);
            toolBar.SinoActions = new Array(ArrAction, ArrAction1, ArrAction4);
            toolBar.imagePath = "../images/buttonbar/";
            toolBar.titleStr = "流程监控";
            toolBar.treeViewTitle = new Array("","任务名称","关键字","主题","其他","签收时间","预定时间","");
            toolBar.treeViewWidth = new Array("2%","19%","19%","19%","10%","15%","10%","2%");
               toolBar.print();
        }
        var getValue='<%=request.getParameter("checkValue")%>';
        window.onload=Intload;
         function Intload()
        {
          var intValue=document.getElementById("check");
          for(var i=0;i<intValue.length;i++)
          {
             if(getValue==intValue[i].value)
             {
                 intValue[i].selected=true;
                 break;
             }
           }
        }
        function getArrActionValue(obj)
        {
            var checkValue =obj.options[obj.selectedIndex].value;
            reqServlet(checkValue);
        }
         printTool();
     </script>
     </form>
        <%
			DTOSet ds = (DTOSet) request
					.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
		%>
		<div
			style="position:absolute; overflow-y: auto; overflow-x: auto; height:80%; width: 100%; left: 1px; margin-left: 0px"
			align="left">
		 
			<input type="hidden" name="act" value=""/>
				<table border= 1 width="100%">
					<%
						if (ds != null && !ds.isEmpty()) {
							SfActInfoDTO std = (SfActInfoDTO) ds.getDTO(0);
					%>
					<tr class="dataTR">
						<td width="2%">
							<input type="checkbox" name="mdc"
								value="<%=std.getSfactActId()%>" />
						</td>
						<td align="left" width="19%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactTaskName()%></td>
						<td align="left" width="19%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactApplColumn1()%></td>
						<td align="left" width="19%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactApplColumn2()%></td>
						<td align="left" width="19%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactApplColumn2()%></td>
						<td align="left" width="10%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactSignDate()%></td>
						<td align="left" width="15%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactSignDueDate()%></td>
						<td width="2%">
					</tr>
					<%
						}
					%>
				</table>
			 
		<div><%=StrUtil.nullToString(request
							.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
		</div>
		<%=WebConstant.WAIT_TIP_MSG%>
        </div>
    <!--/form-->
    </body>
</html>

<script type="text/javascript" language="JavaScript">
	function do_ShowDetail(id){//查询详细
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&apiId="+id);
	}
	function reqServlet(name)
	{
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet?act=<%=WebActionConstant.QUERY_ACTION%>&checkValue="+name);
	}
	
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet?act=");
   	}

    function doNull() {}
</script>
