<%--
  User: 李轶
  Date: 2009-8-10
  Time: 18:23:55
  Function:基站显示批号下的临时表
--%>
<%@ page import="com.sino.ams.constant.URLDefineList,
                 com.sino.ams.constant.WebAttrConstant,
                 com.sino.base.constant.db.QueryConstant,
                 com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
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
<style type="text/css">
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;padding-right:4px}
.textareaNoEmpty {WIDTH:100%;height:100%;BACKGROUND-COLOR: #FFFF99;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
</style>
</head>
<%@ include file="/newasset/headerInclude.htm"%>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>

<%
    String workorderType=StrUtil.nullToString(request.getParameter("workorderType"));
    String workorderBatchNo=StrUtil.nullToString(request.getParameter("workorderBatchNo"));
    String groupId=StrUtil.nullToString(request.getParameter("groupId"));
    System.out.println("groupId = " + groupId);
     String distributeGroupId=StrUtil.nullToString(request.getParameter("distributeGroupId"));
    System.out.println("distributeGroupId = " + distributeGroupId);
    boolean isFirstNode = StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");//登记节点

boolean hasTransObject=true;
   String category=StrUtil.nullToString(request.getParameter("objectCategory"));
%>
<body leftmargin="1" topmargin="0" >

<form name="mainFrm"  method="post" action="/servlet/com.sino.ams.workorder.servlet.WorkOrderTmpServlet">
<%if(isFirstNode){%>
    <table width="100%" align="left" border="0" cellpadding="2" cellspacing="0"  style="position:absolute;left:1px;top:0px">
        <tr>
            <td align="left">
                <img src="/images/eam_images/choose.jpg" alt="选择地点" onClick="do_SelectLocation(); return false;">
                <img src="/images/eam_images/delete_line.jpg" alt="删除选择行" onClick="deleteOrders(); return false;">
                <img src="/images/eam_images/execute.jpg" alt="选择执行人" onClick="chooseExcuter(); return false;">
                <%if(workorderType.equals(DictConstant.ORDER_TYPE_CHECK) || workorderType.equals(DictConstant.ORDER_TYPE_HDV)){%>
                <img src="/images/eam_images/achieve.jpg" alt="选择归档人" onClick="chooseArcUser(); return false;">
                <%}%>
            </td>
        </tr>
    </table>
<%}%>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:23px;left:1px;width:100%">
	    <table width="130%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
			<tr>
			<td height="22" width="4%" align="center"><input type="checkbox" class="headCheckbox" name="ctlBox" onclick="checkAll(this.name,'systemids')"> </td>
			<td height="22" width="10%" align="center">工单号</td>
			<td height="22" width="10%" align="center">工单地点</td>
			<td height="22" width="16%" align="center">地点名称</td>
			<td height="22" width="14%" align="center">开始时间</td>
            <td height="22" width="8%" align="center">实施周期(天)</td>
            <td height="22" width="12%" align="center">执行部门/人<font color="red">*</font></td>
            <td height="22" width="10%" align="center">归档人<font color="red">*</font></td>
            <td height="22" width="8%" align="center">计划配置</td>
		</tr>
    </table>
</div>
<%
	RowSet rows = (RowSet)request.getAttribute(WebAttrConstant.WORKORDER_ATTR);
	if(rows != null && !rows.isEmpty()){
%>
    <div id="dataDiv" style="overflow:scroll;height:86%;width:100%;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="130%" id="dataTable" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        Row row = null;
		for(int i = 0; i < rows.getSize(); i++){

            row = rows.getRow(i);
            String systemid=row.getStrValue("SYSTEMID");
            hasTransObject=hasTransObject&&(!row.getStrValue("ATTRIBUTE1").equals(""));
%>
        <tr class="dataTR" id="<%=row.getValue("WORKORDER_NO")%>">
            <td width="4%" align="center"  ><input type="checkbox" name="systemids" value="<%=row.getStrValue("SYSTEMID")%>"></td>
            <td width="10%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_NO")%>"></td>
            <td width="10%"><input class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="16%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
            <td width="14%"><input class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
            <td width="8%"><input class="finput3" readonly value="<%=row.getValue("IMPLEMENT_DAYS")%>"></td>
            <td width="12%"><input class="textareaNoEmpty" readonly name="implement" id="implement<%=i%>" value="<%=row.getValue("IMPLEMENT")%>"></td>
            <td width="10%"><input class="textareaNoEmpty" readonly name="arcUserBy" id="arcUserBy<%=i%>" value="<%=row.getValue("ARC_USER")%>"></td>
            <td width="8%"><input class="finput" readonly value="<%=row.getValue("HASCONFIG")%>"></td>
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
<%
    }else{
        hasTransObject=false;
    }
%>
<%=WebConstant.WAIT_TIP_MSG%>
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
    function init(){
          do_ChooseGroup();
    }
    function do_ChooseGroup(){
        var groupId = "<%=groupId%>";
        if(groupId == "-1"||groupId =="0"){
            var winStyle = "dialogWidth:22.1;dialogHeight:14.8;center:yes;status:no;help:no";
            var groupUrl = "/public/chooseGroup.jsp";
            var retValue = window.showModalDialog(groupUrl, null, winStyle);
            if(retValue){
                var arr = explode(retValue, ",");
                mainFrm.distributeGroupId.value = arr[0];
                mainFrm.distributeGroupName.value = arr[1];
            } else{
                if(confirm("不选择组别将关闭本窗口，确定吗？")){
                    self.close();
                } else {
                    do_ChooseGroup();
                }
            }
        } else {
            mainFrm.distributeGroupId.value = groupId;
            <%--mainFrm.distributeGroupName.value = "<%=groupName%>";--%>
        }
    }
    function do_ShowDetail(systemid) {
        var screenHeight = window.screen.height;
        var screenWidth = window.screen.width;
        var isFirstNode =document.mainFrm.isFirstNode.value;
        var winstyle = "dialogWidth=" + screenWidth + ";dialogHeight=" + screenHeight + ";help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
        var url = "<%=URLDefineList.WORKORDER_TMP_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&systemid="+systemid+"&isFirstNode="+isFirstNode +"&objectCategory=<%=category%>";
        var retVal=window.showModalDialog(url,null,winstyle);
<%--        alert(retVal);--%>
        if (retVal) {
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
            var groupId = this.parent.document.mainFrm.distributeGroupId.value;
            var url = "/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet?act=CHOOSE&groupId=" + groupId;
            var factor = 0.4;
            var dialogWidth = window.screen.availWidth * factor;
            var dialogHeight = window.screen.availHeight * factor;
            var winstyle = "dialogWidth:"
                    + dialogWidth
                    + "px;dialogHeight:"
                    + dialogHeight
                    + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
            var retVal = window.showModalDialog(url, null, winstyle);
            
            var rets = retVal.split(";");
            if (retVal) {
                document.mainFrm.implementBy.value = rets[0];
                document.mainFrm.groupId.value = rets[1];
                document.mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                document.mainFrm.submit();
            }
        } else {
            alert(UPDATE_CHECK_MSG);
        }
    }

    function chooseArcUser() {//选择归档人
    	if (getCheckedBoxCount("systemids") > 0) {
            var groupId = this.parent.document.mainFrm.distributeGroupId.value;
            var url = "/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet?act=arc&groupId=" + groupId;
            var factor = 0.4;
            var dialogWidth = window.screen.availWidth * factor;
            var dialogHeight = window.screen.availHeight * factor;
            var winstyle = "dialogWidth:"
                    + dialogWidth
                    + "px;dialogHeight:"
                    + dialogHeight
                    + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
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

    function do_SelectLocation(){
        parent.addOrders();
    }
</script>