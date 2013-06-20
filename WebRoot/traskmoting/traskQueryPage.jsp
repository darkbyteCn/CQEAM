<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<%@ page import="com.sino.traskmoting.dto.SfActInfoDTO"%>



<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>流程跟踪</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
		<script type="text/javascript"
			src="/WebLibary/js/OperationProjectGroupRole.js"></script>
		<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
		<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
		<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
		<script type="text/javascript">
       function printTool(){
	        var ArrAction2 = new Array("流程跟踪", "action_draft.gif","trackFlow","xz_t");
	        var ArrAction3 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        var toolBar = new SinoPrintToolBar();            
	        toolBar.SinoActions = new Array( ArrAction2,ArrAction3);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "流程跟踪";
	        toolBar.treeViewTitle = new Array("","关键字","主题","其他","办理人","办理时间","办理组别","办理角色","");
			toolBar.treeViewWidth = new Array("2%","19%","19%","13%","10%","10%","15%","10%","2%");
	       	toolBar.print();
	    }
	    printTool();
	    
	    function TrackSerach(){
    	    changeValue(mainFrm.projectName);
    	    changeValue(mainFrm.procedureName);
    		mainFrm.action="/servlet/com.sino.traskmoting.servlet.SfActInfoServlet";
    		mainFrm.submit();
        }
     </script>
	</head>
	<body>
		<jsp:include
			page="/message/MessageProcess" />
		<%
			DTOSet ds = (DTOSet) request
					.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        %>
		<div id="findDiv"
			style="display: none; position: absolute; width: 272px; height: 200px; top:expression((document.body.clientHeight-this.offsetHeight)/ 2 ); left: expression((document.body.clientWidth-this.offsetWidth)/ 2 ); z-index: 2; background-color: #EFEFEF; overflow: hidden; border: #dde9f5 solid 1px;">
			<form action="" name="mainFrm"
				style="VALIGN: middle; align: center; margin-top: 20px;">
				<input type="hidden" name="act"
					value="<%=WebActionConstant.QUERY_ACTION%>" />
				<div>
					<div style="text-align: center; margin-bttom: 5px;">
						流程跟踪条件
					</div>
					<table align="center" width="60%" border="1" bordercolor="#666666">
						<TR VALIGN=top>
							<TD WIDTH="150" VALIGN=middle>
								<DIV ALIGN=right>
									工程名称
								</DIV>
							</TD>
							<TD WIDTH="227" VALIGN=middle>
								<SELECT NAME="projectName" onchange="getProcedure2()"
									STYLE="HEIGHT: 19px; WIDTH: 183px">
									<option>
										--请选择--
									</option>
									<%=request.getAttribute(WebAttrConstant.PROJECT_OPTION_STR)%>
								</SELECT>
							</TD>
						</TR>
						<TR VALIGN=top>
							<TD WIDTH="150" VALIGN=middle>
								<DIV ALIGN=right>
									流程名称
								</DIV>
							</TD>
							<TD WIDTH="227" VALIGN=middle>
								<SELECT NAME="procedureName" STYLE="HEIGHT: 19px; WIDTH: 183px">
									<option>
										--请选择--
									</option>
									<%=request
									.getAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION)%>
								</SELECT>
							</TD>
						</TR>
						<TR VALIGN=top>
							<TD WIDTH="150">
								<DIV ALIGN=right>
									关键字
								</DIV>
							</TD>
							<TD WIDTH="227">
								<INPUT NAME="sfactSortColumn1" VALUE=""
									STYLE="HEIGHT: 19px; WIDTH: 183px">
							</TD>
						</TR>
						<TR VALIGN=top>
							<TD WIDTH="150">
								<DIV ALIGN=right>
									主题
								</DIV>
							</TD>
							<TD WIDTH="227">
								<INPUT name="sfactSortColumn2" VALUE=""
									STYLE="HEIGHT: 19px; WIDTH: 183px">
							</TD>
						</TR>


						<TR VALIGN=top>
							<TD WIDTH="150">
								<DIV ALIGN=right>
									其他
								</DIV>
							</TD>
							<TD WIDTH="227">
								<INPUT NAME="sfactSortColumn3" VALUE=""
									STYLE="HEIGHT: 19px; WIDTH: 183px">
							</TD>
						</TR>
						<TR VALIGN="center">
							<TD colspan="2">
								<DIV ALIGN=center>
									<input type="submit" name="submit" onClick="TrackSerach()"
										value="提交">
									&nbsp;&nbsp;&nbsp;
									<input type="reset" name="reset" value="重置">
								</DIV>
							</TD>

						</TR>
					</table>
				</div>
			</form>
		</div>
		<div
			style="overflow-y: auto; overflow-x: auto; height: 80%; width: 100%; left: 1px; margin-left: 0px"
			align="left">
			 
				<table  width="100%">
					<%
						if (ds != null && !ds.isEmpty()) {
							SfActInfoDTO std = (SfActInfoDTO) ds.getDTO(0);
                            for(int i=0;i<ds.getSize();i++){
                                std = (SfActInfoDTO)ds.getDTO(i);
                    %>
					<tr class="dataTR">
						<td width="2%">
							<input type="checkbox" name="mdc"
								value="<%=std.getSfactActId()%>" />
						</td>
						<td width="18%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactApplColumn1()%></td>
						<td width="19%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactApplColumn2()%></td>
						<td width="13%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactApplColumn3()%></td>
						<td width="10%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactPickUser()%></td>
						<td width="10%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactSignDate()%></td>
						<td width="15%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactTaskGroup()%></td>
						<td width="10%"
							onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactTaskRole()%></td>
						<td width="2%">
					</tr>

					<%
                            }
                        }
					%>
				</table>
		 
		<div><%=StrUtil.nullToString(request
							.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
		</div>
		<%=WebConstant.WAIT_TIP_MSG%>
        </div>
	</body>
</html>

<script type="text/javascript" language="JavaScript">
	function do_ShowDetail(id){//查询详细
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&actId="+id);
	}
	 function trackFlow(){//流程跟踪
	 if(document.getElementById("findDiv").style.display=='none'){
	  	document.getElementById("findDiv").style.display="";
	 }else{
		 document.getElementById("findDiv").style.display="none";
	 }
	}
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoServlet?act=");
   	}
</script>
