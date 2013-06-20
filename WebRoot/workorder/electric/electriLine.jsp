<%--
  User: zhoujs
  Date: 2007-11-5
  Time: 17:02:25
  Function:电力专业显示批号下的临时表
--%>
<%@ page import="com.sino.ams.constant.URLDefineList,
                 com.sino.ams.constant.WebAttrConstant,
                 com.sino.base.constant.db.QueryConstant,
                 com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>工单临时表数据</title>
</head>


<link href="/WebLibary/css/view.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>

<body bgcolor="#FFFFFF" text="#000000">

<%
    String workorderType=StrUtil.nullToString(request.getParameter("workorderType"));
    String workorderBatchNo=StrUtil.nullToString(request.getParameter("workorderBatchNo"));
    String groupId=StrUtil.nullToString(request.getParameter("groupId"));
    boolean hasTransObject=true;
    boolean isFirstNode = StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");//登记节点

   String category=StrUtil.nullToString(request.getParameter("objectCategory"));
%>
<body leftmargin="1" topmargin="0" >

<form name="mainFrm"  method="post" action="/servlet/com.sino.ams.workorder.servlet.WorkOrderTmpServlet">
<%if(isFirstNode){%>
    <table width="980" align="left" border="0" cellpadding="2" cellspacing="0"  style="position:absolute;left:1px;top:0px">
        <tr>
            <td align="left">
                <img src="/images/eam_images/delete_line.jpg" alt="删除选择行" onClick="deleteOrders(); return false;">
                <img src="/images/eam_images/execute.jpg" alt="选择执行人" onClick="chooseExcuter(); return false;">
                <%if(workorderType.equals("12")){%>
                <img src="/images/eam_images/achieve.jpg" alt="选择归档人" onClick="chooseArcUser(); return false;">
                <%}%>
            </td>
        </tr>
    </table>
<%}%>
    <table width="991" align="left" border="1" cellpadding="2" cellspacing="0" style="position:absolute;left:1px;top:22px" class="headerTable">
		<tr>
			<td height="22" width="4%" align="center"><input type="checkbox" class="headCheckbox" name="ctlBox" onclick="checkAll(this.name,'systemids')"> </td>
			<td height="22" width="10%" align="center">工单号</td>
			<td height="22" width="10%" align="center">地点编号</td>
			<td height="22" width="16%" align="center">地点名称</td>
			<td height="22" width="14%" align="center">开始时间</td>
            <td height="22" width="8%" align="center">实施周期</td>
            <td height="22" width="12%" align="center">执行部门/人</td>
            <td height="22" width="10%" align="center">归档人</td>
            <td height="22" width="8%" align="center">计划配置</td>
		</tr>
    </table>

<%
	RowSet rows = (RowSet)request.getAttribute(WebAttrConstant.WORKORDER_ATTR);
	if(rows != null && !rows.isEmpty()){
%>
<div style="overflow-y:scroll;height:340;width:1007;position:absolute;top:47px;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#9FD6FF">
<%
        Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            String systemid=row.getStrValue("SYSTEMID");
            hasTransObject=hasTransObject&&(!row.getStrValue("ATTRIBUTE1").equals(""));
%>
        <tr class="dataTR">
            <td class="" style="word-wrap:break-word" height="22" width="4%" align="center"  ><input type="checkbox" name="systemids" value="<%=row.getStrValue("SYSTEMID")%>"></td>
            <td style="word-wrap:break-word" height="22" width="10%" onclick="do_ShowDetail('<%=systemid%>');" ><%=row.getValue("WORKORDER_NO")%></td>
            <td style="word-wrap:break-word" height="22" width="10%" onclick="do_ShowDetail('<%=systemid%>');"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
            <td style="word-wrap:break-word" height="22" width="16%" onclick="do_ShowDetail('<%=systemid%>');"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
            <td style="word-wrap:break-word" height="22" width="14%" onclick="do_ShowDetail('<%=systemid%>');"><%=row.getValue("START_DATE")%></td>
            <td style="word-wrap:break-word" height="22" width="8%" onclick="do_ShowDetail('<%=systemid%>');"><%=row.getValue("IMPLEMENT_DAYS")%>(天)</td>
            <td style="word-wrap:break-word" height="22" width="12%" onclick="do_ShowDetail('<%=systemid%>');" ><%=row.getValue("IMPLEMENT")%></td>
            <td style="word-wrap:break-word" height="22" width="10%" onclick="do_ShowDetail('<%=systemid%>');"><%=row.getValue("ARC_USER")%></td>
            <td style="word-wrap:break-word" height="22" width="8%" onclick="do_ShowDetail('<%=systemid%>');"><%=row.getValue("HASCONFIG")%></td>
        </tr>
<%
		}
%>
    </table>
</div>

    <input type="hidden" name="act">
    <input type="hidden" name="implementBy">
    <input type="hidden" name="arcUser">
    <input type="hidden" name="systemid">
    <input type="hidden" name="isFirstNode" value="<%=isFirstNode%>">
    <input type="hidden" name="workorderBatchNo" value="<%=workorderBatchNo%>">
    <input type="hidden" name="workorderType" value="<%=workorderType%>">
    <input type="hidden" name="distributeGroupId" value="<%=groupId%>">
    <input type="hidden" name="groupId" value="<%=groupId%>">
    <input type="hidden" name="objectCategory" value="<%=category%>">
    <input type="hidden" name="hasTransObject" value="<%=hasTransObject%>">
</form>
<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
</html>
<script type="text/javascript">
    function deleteOrders() {
        if (getCheckedBoxCount("systemids")>0) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.submit();
        }else{
            alert(DELETE_CHECK_MSG);
        }

    }
    function do_ShowDetail(systemid) {
        var screenHeight = window.screen.height;
        var screenWidth = window.screen.width;
        var isFirstNode = document.mainFrm.isFirstNode.value;
        var winstyle = "dialogWidth=" + screenWidth + ";dialogHeight=" + screenHeight + ";help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
        var url = "<%=URLDefineList.WORKORDER_TMP_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&systemid="+systemid+"&isFirstNode="+isFirstNode +"&objectCategory=<%=category%>";
        var retVal=window.showModalDialog(url,null,winstyle);
        if(retVal){
            url = "<%=URLDefineList.WORKORDER_TMP_SERVLET%>?workorderBatchNo=" + document.mainFrm.workorderBatchNo.value;
            url += "&workorderType=" + document.mainFrm.workorderType.value;
            url += "&isFirstNode=" + document.mainFrm.isFirstNode.value;
            url += "&objectCategory=" + document.mainFrm.objectCategory.value;
            url += "&groupId=" + document.mainFrm.groupId.value;
            document.location.href = url;
        }
    }

    function chooseExcuter() {//选择执行人
        if (getCheckedBoxCount("systemids") > 0) {
            var groupId = document.mainFrm.distributeGroupId.value;
            var url = "/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet?act=CHOOSE&groupId=" + groupId;
            var winstyle = "dialogWidth=250px;dialogHeight=131px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
            var retVal = window.showModalDialog(url, null, winstyle);
            if (retVal) {
                mainFrm.implementBy.value = retVal;
                mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                mainFrm.submit();
            }
        } else {
            alert(UPDATE_CHECK_MSG);
        }
    }
      function chooseArcUser() {//选择归档人
        if (getCheckedBoxCount("systemids") > 0) {
            var groupId = document.mainFrm.distributeGroupId.value;
            var url = "/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet?act=arc&groupId=" + groupId;
            var winstyle = "dialogWidth=250px;dialogHeight=131px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
            var retVal = window.showModalDialog(url, null, winstyle);
            if (retVal) {
                document.mainFrm.arcUser.value = retVal;
                document.mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                document.mainFrm.submit();
            }
        } else {
            alert(UPDATE_CHECK_MSG);
        }
    }
</script>