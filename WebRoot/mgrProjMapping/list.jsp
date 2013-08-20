<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  User: igt
  Date: 2013-08-16
  Time: 10:22:03
  Function:项目及项目经理对应关系设置
--%>
<script type="text/javascript" src="/WebLibary/js/help.js"></script>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    //boolean matchRight = StrUtil.nullToString(request.getAttribute("matchEnable")).equalsIgnoreCase("TRUE");
    //String orderNo= StrUtil.nullToString(request.getParameter("orderNo"));
    //String locName= StrUtil.nullToString(request.getParameter("locName"));
    //String workorderType = StrUtil.nullToString(request.getParameter("workorderType"));
%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>设置项目</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    
    <script type="text/javascript">
        
        var ArrAction1 = new Array(false, "自动归档", "act_refresh.gif", "自动归档", "autoAchieve");
        var ArrActions = new Array(ArrAction1);
        var ArrSinoViews = new Array();


    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 onkeydown="do_check();" onload="helpInit('4.4.5');">
<input type="hidden" name="helpId" value="">
<form name="mainFrm">
<script type="text/javascript">
    printTitleBar("项目设置 ")
    printToolBar();
</script>
 	<input type="hidden" name="projectNumber">
 	<input type="hidden" name="userId">
    <table border="0" width="100%" class="queryTable" id="table1">
        <tr>            
            <td width="20%"  align="left" colspan="2">
            	<img src="/images/eam_images/search.jpg" alt="查询项目" onClick="do_SelectProj();">
            </td>
        </tr>
    </table>
<script type="text/javascript">
    var columnArr = new Array("项目编号", "项目名称", "处理状态", "负责人", "操作");
    var widthArr = new Array("12%", "42%", "17%", "17%", "12%");
    printTableHead(columnArr, widthArr);
</script>

<%
    RowSet rs = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	if(rs != null && !rs.isEmpty())
	{
%>
<div style="overflow-y:scroll;height:325;width:100%;position:absolute;top:73px;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#666666">

<%
        Row row = null;
		String status = "";
		for(int i = 0; i < rs.getSize(); i++){
			row = rs.getRow(i);
			status = row.getValue("PROJECT_STATUS_CODE").toString();
			status = status.trim().toUpperCase();
			if(status.equals("CLOSED"))
				status = "已完成";
			else if(status.equals("APPROVED"))
				status = "已批准";
			else if(status.equals("UNAPPROVED"))
				status = "未批准";
			else status = "未知状态";
          //  String prevOrder =  row.getStrValue("FIRSTPENDINGORDER");
          //  String dealState = (prevOrder.equals("")) ? ("待归档") : ("等待工单:" + prevOrder);
%>
			<tr class="dataTR">
				<td style="word-wrap:break-word" height="22" width="12%"><%=row.getValue("SEGMENT1") %></td>
				<td style="word-wrap:break-word" height="22" width="42%"><%=row.getValue("NAME") %></td>
				<td style="word-wrap:break-word" height="22" width="17%"><%=status %></td>
				<td style="word-wrap:break-word" height="22" width="17%"><%=row.getValue("USERNAME") %></td>
				<td style="word-wrap:break-word" text-align="center" height="22" width="12%">
					<center>
						<a href="#" onclick="javascript:do_ClickProj('<%=row.getValue("SEGMENT1") %>')">选择项目负责人</a>
						<a href="/servlet/com.sino.ams.zz.proj2mgr.mapping.bean.ProjectManagerMappingServlet?method=0&projectNumber=<%=row.getValue("SEGMENT1") %>">删除</a>
					</center>
				</td>
			</tr>
<%
		}
%>
    </table>
</div>
<%
    }
    %>
</form>
<div style="position:absolute;top:428px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script type="text/javascript">
         function showDiffDetail(val) {
            var url = "/workorder/order/waitOrder.jsp?val=" + val;
            var screenHeight = window.screen.height - 100;
            var screenWidth = window.screen.width;
            var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
            window.open(url, "", winstyle);
        }
        function autoAchieve() {
            document.mainFrm.act.value = "match";
            document.mainFrm.submit();
        }
        function do_Search() {
        	var wt = document.getElementById("workorderType").value;
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.workorder.servlet.PendingOrderServlet?workorderType=" + wt;
            document.mainFrm.submit();
        }
        function do_check() {
            if (event.keyCode == 13) {
                do_Search();
            }
        }
        
        function do_ClickProj(project) {
        	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT_MANAGER%>";
        	var dialogWidth = 55;
            var dialogHeight = 30;
            var userPara = "";
            var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
            
            if (objs) {            	
                var obj = objs[0];
                dto2Frm(obj, "mainFrm");
               document.mainFrm.userId.value = obj["userId"];
            }else {
                document.mainFrm.userId.value = "";
            }
            var url = "/servlet/com.sino.ams.zz.proj2mgr.mapping.bean.ProjectManagerMappingServlet?user=" + document.mainFrm.userId.value + "&method=1&projectNumber=" + project;
            window.location.href=url;
        }
        
        function do_SelectProj() {
     	   var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PROJECT%>";
             var dialogWidth = 55;
             var dialogHeight = 30;
             var userPara = "";
             var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
             if (objs) {
                 var obj = objs[0];
                 dto2Frm(obj, "mainFrm");
                document.mainFrm.projectNumber.value = obj["projectNumber"];
             }else {
                 document.mainFrm.projectNumber.value = "";
             }
             var url="/servlet/com.sino.ams.zz.proj2mgr.mapping.bean.ProjectManagerMappingServlet?user=&method=1&projectNumber=" + document.mainFrm.projectNumber.value;
             window.location.href=url;
         }
</script>
</html>
