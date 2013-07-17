<%--
  User: zhoujs
  Date: 2007-11-5
  Time: 17:02:25
  Function:��վ��ʾ�����µ���ʱ��
--%>
<%@ page import="com.sino.base.constant.db.QueryConstant,
                 com.sino.base.constant.web.WebActionConstant,
                 com.sino.base.constant.web.WebConstant,
                 com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.DictConstant"%>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>������ʱ������</title>
<style type="text/css">
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;padding-right:4px}
.textareaNoEmpty {WIDTH:100%;height:100%;BACKGROUND-COLOR: #FFFF99;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
</style>
</head>

<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/jquery.js"></script>
<script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<%
    String workorderType=StrUtil.nullToString(request.getParameter("workorderType"));
    String workorderBatchNo=StrUtil.nullToString(request.getParameter("workorderBatchNo"));
    String groupId=StrUtil.nullToString(request.getParameter("distributeGroupId"));
    boolean isFirstNode = StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");//�Ǽǽڵ�

    boolean hasTransObject=true;
    String category=StrUtil.nullToString(request.getParameter("objectCategory"));

    String fromPage=StrUtil.nullToString(request.getParameter("fromPage"));//�������������˹�����ѯ���ʱ����Դ����ʱ����Ҫ��ʾ�κΰ�ť
%>
<body leftmargin="1" topmargin="0" onload="do_SetPageWidth();">

<form name="mainFrm" id="mainFrm"  method="post" action="/servlet/com.sino.ams.workorder.servlet.WorkOrderTmpServlet">
<%
    if(isFirstNode && !fromPage.equals("DETAIL_PAGE")){
%>
    <table width="100%" align="left" border="0" cellpadding="2" cellspacing="0"  style="position:absolute;left:1px;top:0px">
        <tr>
            <td align="left">
                <img src="/images/eam_images/choose.jpg" alt="ѡ��ص�" onClick="do_SelectLocation(); return false;">
                <img src="/images/eam_images/delete_line.jpg" alt="ɾ��ѡ����" onClick="deleteOrders(); return false;">
                <img src="/images/eam_images/execute.jpg" alt="ѡ��ִ����" onClick="chooseExcuter(); return false;">
<%
        if(workorderType.equals(DictConstant.ORDER_TYPE_CHECK) || workorderType.equals(DictConstant.ORDER_TYPE_HDV)){
%>
                <img src="/images/eam_images/achieve.jpg" alt="ѡ��鵵��" onClick="chooseArcUser(); return false;">
<%
        }
%>
                <img src="/images/eam_images/detail_info.jpg" id="img6" alt="�ʲ���ϸ" onClick="do_ViewAssetsData()">
            </td>
        </tr>
    </table>
<%
    }
%>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:23px;left:1px;width:100%">
	    <table width="130%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
			<tr>
				<td height="22" width="4%" align="center"><input type="checkbox" class="headCheckbox" name="ctlBox" onclick="checkAll(this.name,'systemids')"> </td>
				<td height="22" width="10%" align="center">������</td>
				<td height="22" width="10%" align="center">��վ�ص�</td>
				<td height="22" width="16%" align="center">�ص�����</td>
				<td height="22" width="14%" align="center">��ʼʱ��</td>
	            <td height="22" width="8%" align="center">ʵʩ����(��)</td>
	            <td height="22" width="12%" align="center">ִ�в���/��<font color="red">*</font></td>
	            <td height="22" width="10%" align="center">�鵵��<font color="red">*</font></td>
	            <td height="22" width="8%" align="center">�ƻ�����</td>
			</tr>
	    </table>
	</div>
<%
	RowSet rows = (RowSet)request.getAttribute(WebAttrConstant.WORKORDER_ATTR);
	if(rows != null && !rows.isEmpty()){
%>
<div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="130%" id="dataTable" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        Row row = null;
		for(int i = 0; i < rows.getSize(); i++){

            row = rows.getRow(i);
            String systemid=row.getStrValue("SYSTEMID");
            hasTransObject=hasTransObject&&(!row.getStrValue("ATTRIBUTE1").equals(""));
%>
        <tr class="dataTR">
            <td width="4%" align="center"><input type="checkbox" name="systemids" value="<%=row.getStrValue("SYSTEMID")%>"></td>
            <td width="10%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_NO")%>"></td>
            <td width="10%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="16%"><input class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
            <td width="14%"><input class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
            <td width="8%"><input class="finput3" readonly value="<%=row.getValue("IMPLEMENT_DAYS")%>"></td>
            <td width="12%"><input class="textareaNoEmpty" readonly name="implement" id="implement<%=i%>" value="<%=row.getValue("IMPLEMENT")%>"></td>
            <td width="10%"><input class="textareaNoEmpty" readonly name="arcUserBy" id="arcUserBy<%=i%>" value="<%=row.getValue("ARC_USER")%>"></td>
            <td width="8%"><input class="finput" readonly value="<%=row.getValue("HASCONFIG")%>">
            <input type="hidden" name="checkLocation" id="checkLocation<%=i%>" value="<%=row.getValue("WORKORDER_OBJECT_NO")%>"></td>
        </tr>
<%
		}
%>
    </table>
</div>

    <input type="hidden" name="act" id="act">
    <input type="hidden" name="implementBy" id="implementBy">
    <input type="hidden" name="arcUser" id="arcUser">
    <input type="hidden" name="systemid" id="systemid">
    <input type="hidden" name="isFirstNode" value="<%=isFirstNode%>" id="isFirstNode">
    <input type="hidden" name="workorderBatchNo" value="<%=workorderBatchNo%>" id="workorderBatchNo">
    <input type="hidden" name="workorderType" value="<%=workorderType%>" id="workorderType">
    <input type="hidden" name="distributeGroupId" value="<%=groupId%>" id="distributeGroupId">
    <input type="hidden" name="groupId" value="<%=groupId%>" id="groupId">
    <input type="hidden" name="objectCategory" value="<%=category%>" id="">
    <input type="hidden" name="hasTransObject" value="<%=hasTransObject%>" id="hasTransObject">
</form>
<div id="pageNaviDiv" style="position:absolute;top:428px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%
    }
%>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
    function deleteOrders() {
        if (getCheckedBoxCount("systemids")>0) {
            $("#act").val("<%=WebActionConstant.DELETE_ACTION%>");
            document.mainFrm.submit();
        }else{
            alert(DELETE_CHECK_MSG);
        }

    }

    function do_ShowDetail(systemid) {
        var screenHeight = window.screen.height;
        var screenWidth = window.screen.width;
        var isFirstNode =document.mainFrm.isFirstNode.value;
        var winstyle = "dialogWidth=" + screenWidth + ";dialogHeight=" + screenHeight + ";help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
        var url = "<%=URLDefineList.WORKORDER_TMP_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&systemid="+systemid+"&isFirstNode="+isFirstNode +"&objectCategory=<%=category%>";
        var retVal=window.showModalDialog(url,null,winstyle);
        if (retVal) {
            url = "<%=URLDefineList.WORKORDER_TMP_SERVLET%>?workorderBatchNo=" + document.mainFrm.workorderBatchNo.value;
            url += "&workorderType=" + document.mainFrm.workorderType.value;
            url += "&isFirstNode=" + document.mainFrm.isFirstNode.value;
            url += "&objectCategory=" + document.mainFrm.objectCategory.value;
            url += "&groupId=" + document.mainFrm.groupId.value;
            url += "&distributeGroupId=" + document.mainFrm.distributeGroupId.value;
            document.location.href = url;
        }
    }

    function chooseExcuter() {//ѡ��ִ����
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
			
            if (retVal) {
                var rets = retVal.split(";");
                $("#implementBy").val(rets[0]);                
                $("#groupId").val(rets[1]);
                $("#act").val("<%=WebActionConstant.UPDATE_ACTION%>");
                $("#mainFrm").submit();
            }
        } else {
            alert(UPDATE_CHECK_MSG);
        }
    }

    function chooseArcUser() {//ѡ��鵵��
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
                $("#arcUser").val(retVal);
                $("#act").val("<%=WebActionConstant.UPDATE_ACTION%>");
                $("#mainFrm").submit();
            }
        } else {
            alert(UPDATE_CHECK_MSG);
        }
    }
    
    function do_ViewAssetsData(){
	    var tab = document.getElementById("dataTable");
	    var rows = tab.rows;
	    var para = "";
	    var effectiveTime = 0;
	    for(var i = 0; i < rows.length; i++){
	        var row = rows[i];
	        var checkObj = row.childNodes[0].childNodes[0];
	        if(!checkObj.checked){
	            continue;
	        }
	        var checkLocation = getTrNode(row, "checkLocation");
	        //var checkCategory = getTrNode(row, "checkCategory");
	        if(effectiveTime > 0){
	            para += "$";
	        }
	        para += checkLocation.value;
	        para += "_";//+ checkCategory.value;
	        effectiveTime++;
	    }
	    if (para == "") {
	        alert("����ѡҪ�鿴�ʲ���ϸ�ĵص㣡");
	        return;
	    }
	    var actionURL = "/servlet/com.sino.ams.newasset.servlet.ObjectAssetsServlet";
	    actionURL += "?checkLocation=" + para;
	    window.open(actionURL, "objectAssetsWin", "fullscreen=yes");
	}
	
	/**
	 * ���ܣ���ȡָ����ָ�����Ƶı�Ԫ�ض���
	 * @param tr
	 * @param objName
	 */
	function getTrNode(tr, objName) {
	    var obj = null;
	    var cells = tr.cells;
	    var index = -1;
	    for (var i = 0; i < cells.length; i++) {
	        var cell = cells[i];
	        var nodes = cell.childNodes;
	        var breaked = false;
	        for (var j = 0; j < nodes.length; j++) {
	            var node = nodes[j];
	            var nodeName = node.name;
	            if (!nodeName) {
	                continue;
	            }
	            if (nodeName == objName) {
	                obj = node;
	                breaked = true;
	                break;
	            }
	        }
	        if (breaked) {
	            break;
	        }
	    }
	    return obj;
	}

    function do_SelectLocation(){
        parent.addOrders();
    }
</script>