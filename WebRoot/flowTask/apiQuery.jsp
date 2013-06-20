<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.sinoflow.dto.SfApiDTO"%>
<html>
	<head>
		<title>接口定义</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
		<script type="text/javascript">
       function printTool(){
	        var ArrAction = new Array("展开", "act_expand.gif","expendAll","expendAll");
	        var ArrAction1 = new Array("折叠", "act_collapse.gif","collAll","collAll");
	        var ArrAction2 = new Array("新增", "action_draft.gif","create","xz_t");
	        var ArrAction3 = new Array("刷新", "act_refresh.gif","doReload","sh_t");
	        var ArrAction4 = new Array("删除", "del.gif","del","del_t");
            var ArrAction5 = new Array("暂停使用", "suspend.gif", "suspend","dis_t");
            var ArrAction6 = new Array("恢复使用", "unsuspend.gif","unsuspend","ena_t");

            var toolBar = new SinoPrintToolBar();
	        toolBar.SinoActions = new Array(ArrAction,ArrAction1, ArrAction2,ArrAction4,ArrAction5,ArrAction6,ArrAction3);
	        toolBar.imagePath = "../images/buttonbar/";
	        toolBar.titleStr = "接口维护";
//	        toolBar.treeViewTitle = new Array("工程","过程","<img src='../images/buttonbar/t_check.gif' onclick=\"sdAll('mdc');\"/>","任务名称","事件","&nbsp;");
            toolBar.treeViewTitle = new Array("工程","<img src='../images/buttonbar/t_check.gif' onclick=\"sdAll('mdc');\"/>","","接口名称","事件","&nbsp;");
            toolBar.treeViewWidth = new Array("6%","2%","3%","20%","","2%");
	       	toolBar.print();
	    }
	    printTool();
     </script>
	</head>
	<body>
		<jsp:include page="/message/MessageProcess" />
		<%
			int x = 0;
			DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
		%>
		<div style="overflow-y: auto; overflow-x: auto; height: 85%; width: 100%; left: 1px; margin-left: 0px" align="left">
			<form action="" method="post" name="mainFrm">
				<table width="100%" border="0" bordercolor="#666666">
					<%
						if (ds != null && !ds.isEmpty()) {
							SfApiDTO sd = (SfApiDTO) ds.getDTO(0);
					%>
					<tr class="dataTR" id="tr_<%=++x%>">
						<td style="width: 100%;" align="left" colspan="8">
							&nbsp;&nbsp;
							<img src="../images/buttonbar/Expand.gif" onclick="expendOrColl(this,'tr_<%=x%>_tr')" />
							<b style="color: #0E8880; font-size: 14px;"><%=sd.getProjectName()%></b>
						</td>
					</tr>
					<tr class="dataTR" id="tr_<%=x%>_tr">
						<td width="6%"></td>
						<td width="2%">
							<input name="mdc" value="<%=sd.getApiId()%>" type="checkbox" />
						</td>
                        <%
                            if(sd.getEnabled().equals("N")) {
                        %>
                        <td width="3%">
                            <img src="../images/buttonbar/disabled.gif" />
                        </td>
                        <%
                            } else {
                        %>
                        <td width="3%">
                        </td>
                        <%
                            }
                        %>
                        <td width="20%" onclick="do_ShowDetail('<%=sd.getApiId()%>');" align="left"><%=sd.getApiName()%></td>
						<td width="" onclick="do_ShowDetail('<%=sd.getApiId()%>');" align="left"><%=sd.getApiControlStr()%></td>
					</tr>
                    <%
						for (int i = 1; i < ds.getSize(); i++) {
								SfApiDTO sd2 = (SfApiDTO) ds.getDTO(i);
								if (sd2.getProjectName().equals(sd.getProjectName())) {
                    %>
                    <tr class="dataTR" id="tr_<%=x%>_tr">
						<td width="6%"></td>
						<td width="2%">
							<input name="mdc" value="<%=sd2.getApiId()%>" type="checkbox" />
						</td>
                        <%
                            if(sd2.getEnabled().equals("N")) {
                        %>
                        <td width="3%">
                            <img src="../images/buttonbar/disabled.gif" />
                        </td>
                        <%
                            } else {
                        %>
                        <td width="3%">
                        </td>
                        <%
                            }
                        %>
                        <td width="20%" onclick="do_ShowDetail('<%=sd2.getApiId()%>');" align="left"><%=sd2.getApiName()%></td>
						<td width="" onclick="do_ShowDetail('<%=sd2.getApiId()%>');" align="left"><%=sd2.getApiControlStr()%></td>
					</tr>
					<%
						} else {
					%>
					<tr class="dataTR" id="tr_<%=++x%>">
						<td width="6%" align="left" colspan="8">
							&nbsp;&nbsp;
							<img src="../images/buttonbar/Expand.gif" onclick="expendOrColl(this,'tr_<%=x%>_tr')" />
							<b style="color: #0E8880; font-size: 14px;"><%=sd2.getProjectName()%></b>
						</td>
					</tr>
					<tr class="dataTR" id="tr_<%=x%>_tr">
						<td width="6%"></td>
						<td width="2%">
							<input name="mdc" value="<%=sd2.getApiId()%>" type="checkbox" />
						</td>
                        <%
                            if(sd2.getEnabled().equals("N")) {
                        %>
                        <td width="3%">
                            <img src="../images/buttonbar/disabled.gif" />
                        </td>
                        <%
                            } else {
                        %>
                        <td width="3%">
                        </td>
                        <%
                            }
                        %>
						<td width="20%" onclick="do_ShowDetail('<%=sd2.getApiId()%>');" align="left"><%=sd2.getApiName()%></td>
						<td width="" onclick="do_ShowDetail('<%=sd2.getApiId()%>');" align="left"><%=sd2.getApiControlStr()%></td>
					</tr>
					<%
						}
								sd = sd2;
							}//for结束
						}//if的
					%>
				</table>
			</form>
		</div>
		<div><%=StrUtil.nullToString(request
							.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
		</div>
		<%=WebConstant.WAIT_TIP_MSG%>
	</body>
</html>
<script type="text/javascript" language="JavaScript">

    function do_ShowDetail(id){//查询详细
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&apiId="+id);
	}
    function suspend() {
        var tc = document.getElementsByName("mdc");
        for(var i=0;i<tc.length;i++){
            if(tc.item(i).checked){
                if(confirm("确定暫停所选项吗？ 继续请点击“确定” 否则点击“取消”")){
                    mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=<%=WebActionConstant.DISABLED_ACTION%>";
                    mainFrm.submit();
                    return;
                }else{
                    return;
                }
            }
        }
    }

    function unsuspend() {
        var tc = document.getElementsByName("mdc");
        for(var i=0;i<tc.length;i++){
            if(tc.item(i).checked){
                if(confirm("确定恢复所选项吗？ 继续请点击“确定” 否则点击“取消”")){
                    mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=<%=WebActionConstant.CHECK_ACTION%>";
                    mainFrm.submit();
                    return;
                }else{
                    return;
                }
            }
        }
    }

    function del(){//删除
		var tc = document.getElementsByName("mdc");
		for(var i=0;i<tc.length;i++){
			if(tc.item(i).checked){
				if(confirm("确定删除所选项吗？ 继续请点击“确定” 否则点击“取消”")){
					mainFrm.action = "/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=<%=WebActionConstant.DELETE_ACTION%>";
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
       	window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=");
   	}
	
	function create(){//新建
		window.location.assign("/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=<%=WebActionConstant.NEW_ACTION%>");
	}
</script>
