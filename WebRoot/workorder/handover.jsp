<%--
  User: zhoujs
  Date: 2009-8-10
  Time: 17:57:26
  Function: 交接流程起始页面
--%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.DictConstant"%>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.constant.URLDefineList"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderBatchDTO" %>
<%@ page import="com.sino.ams.workorder.util.WorkOrderUtil" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.ams.system.user.dto.SfGroupDTO" %>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    EtsWorkorderBatchDTO etsWorkorderBatch = (EtsWorkorderBatchDTO) request.getAttribute(WebAttrConstant.WORKORDER_BATCH_ATTR);
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String workorderType=etsWorkorderBatch.getWorkorderType();
    boolean isNeed=workorderType.equals(DictConstant.ORDER_TYPE_NEW)||workorderType.equals(DictConstant.ORDER_TYPE_EXT) || workorderType.equals(DictConstant.ORDER_TYPE_HDV) ;
    String prjClass=isNeed?"textareaNoEmpty":"input_style2";
    String chooseGroup = request.getParameter("chooseGroup");
    boolean isFirstNode = StrUtil.nullToString(request.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");
    String category = StrUtil.nullToString(request.getParameter("objectCategory"));
    
    String procName = WorkOrderUtil.getOrderProcdureName(etsWorkorderBatch.getWorkorderType(), category);

    SfUserDTO userAccount=(SfUserDTO)session.getAttribute(WebConstant.USER_INFO);
    DTOSet groups=userAccount.getUserGroups();
    int groupId = -1;
    String groupName = "";
    if(groups != null && groups.getSize() == 1){
        SfGroupDTO sfGroup = (SfGroupDTO)groups.getDTO(0);
        groupId = sfGroup.getGroupId();
        groupName = sfGroup.getGroupname();
    }
%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
	<meta charset=GBK/>
</head>
<link href="/WebLibary/css/view.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/eam.css" rel="stylesheet" type="text/css">
<link  href="/WebLibary/css/toolBar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/flow/flow.js"></script>
<script type="text/javascript" src="/WebLibary/js/json.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
<script type="text/javascript" src="/WebLibary/js/help.js"></script>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<style type="text/css">
.textareaNoEmpty {WIDTH:100%;height:100%;BACKGROUND-COLOR: #FFFF99;}
</style>
<script type="text/javascript">
    var ArrAction0 = new Array(true, "取消", "action_cancel.gif", "取消", "do_Cancel");
    var ArrAction1 = new Array(true, "暂存", "action_save.gif", "暂存", "do_Save");
    var ArrAction2 = new Array(true, "完成", "action_sign.gif", "完成", "do_Complete");
    var ArrAction3 = new Array(false, "退回", "arrow_pleft.gif", "退回", "do_Back");
    var ArrAction4 = new Array(false, "特送", "action_guiview.gif", "特送", "do_Cancel");
    var ArrAction5 = new Array(true, "流程查阅", "actn023.gif", "流程查阅", "viewFlow") ;
    var ArrAction6 = new Array(false, "附加文件", "download.gif", "附加文件", "do_attach(0)");
    var ArrAction7 = new Array(false, "浏览文件", "year.gif", "浏览文件", "do_attach(1)");
    var ArrAction8 = new Array(true, "填写意见", "checkin.gif", "填写意见", "do_suggestion(0)");
    var ArrAction9 = new Array(true, "查阅意见", "bbsdoc2.gif", "查阅意见", "do_suggestion(1)");
    var ArrAction10 = new Array(false, "发送信息", "compose1.gif", "发送信息", "do_Cancel(0)");
    var ArrAction11 = new Array(false, "查阅信息", "fhome.gif", "查阅信息", "do_Cancel(1)");
    var ArrActions = new Array(ArrAction0,ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5, ArrAction6, ArrAction7, ArrAction8, ArrAction9, ArrAction10, ArrAction11);
    var ArrSinoViews = new Array();
    var ArrSinoTitles = new Array();
</script>
<head>
    <title>工单任务登记</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<%if(isFirstNode){%>
<script type="text/javascript">
    
</script>
<%}%>

<body bgcolor="#FFFFFF" text="#000000" topMargin=0 leftMargin=0 onload="initPage();helpInit('4.4.2');">
<input type="hidden" name="helpId" value="">
    <form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.EtsWorkorderBatchServlet">
<script>
  printToolBar();
<%if(isFirstNode){%>
     HideASinoButton(1);
     HideASinoButton(5);
     HideASinoButton(8);
     HideASinoButton(9);
<%}%>
</script>
    <table id="batchTable" width="100%" height="120px" align="center" border="0" cellpadding="2" cellspacing="0" >
        <tr>
            <td width="10%" align="right">工单批号：</td>
            <td width="20%"><input readonly type="text" style="width:100%" name="workorderBatch" value="<%=etsWorkorderBatch.getWorkorderBatch()%>" class="input_style2"></td>
            <td width="10%" align="right">工单类型：</td>
            <td width="20%"><input class="input_style2" style="width:85%" readonly="true" name="workorderTypeDesc" type="text" value="<%=etsWorkorderBatch.getWorkorderTypeDesc()%>"></td>
            <td width="10%" align="right">下单组别：</td>
            <td width="20%"><input readonly="true"  style="width:100%" class="input_style2"  size="50"   name="distributeGroupName" type="text" value="<%=etsWorkorderBatch.getDistributeGroupName()%>" ></td>
            <td width="10%"></td>
        </tr>
        <tr>
            <td width="10%" align="right">创 建 人：</td>
            <td width="20%"><input readonly="true" style="width:100%" class="input_style2"  name="created_by_desc" type="text" value="<%=etsWorkorderBatch.getCreateUser()%>"></td>
            <td width="10%" align="right">所属工程：</td>
            <td width="20%">
                <input class="<%=prjClass%>" type="text" style="width:85%" name="prjName" value="<%=etsWorkorderBatch.getPrjName()%>" readonly>
<%
    if(isFirstNode){
%>
                <a href="#" onclick="choosePrj();" title="点击选择工程"><font color="blue"><%=WebConstant.CHOOSE_DICT%></font><font color="red">*</font></a>
<%
    }
%>
            </td>
            <td width="10%" align="right">任务名称：</td>
            <td width="20%"><input  class="textareaNoEmpty" type="text" style="width:100%" name="workorderBatchName" value="<%=etsWorkorderBatch.getWorkorderBatchName()%>"><font color="red">*</font></td>
            <td width="10%"></td>
        </tr>
        <tr style="height:69px">
            <td width="10%" height="100%" align="right">任务描述：</td>
            <td width="80%" height="100%" colspan="5">
                <textarea name="remark" class="input_style1" style="width:100%;height:100%"><%=etsWorkorderBatch.getRemark()%></textarea>
            </td>
            <td width="10%" height="100%"></td>
        </tr>
    </table>

    <table width="100%" align="center" id="woTable">
        <tr>
            <td>
                <iframe name="wo" id="wo" frameborder="0" style="width:100%;height:100%" src="" scrolling="no"></iframe>
            </td>
        </tr>
    </table>

    <input name="workorderType" type="hidden" value="<%=etsWorkorderBatch.getWorkorderType()%>">
    <input name="distributeGroupId" type="hidden" value="<%=etsWorkorderBatch.getDistributeGroupId()%>" >
    <input name="createdBy" type="hidden"  value="<%=etsWorkorderBatch.getCreatedBy()%>" >
    <input type="hidden" name="prjId" value="<%=etsWorkorderBatch.getPrjId()%>" >
    <input type="hidden" name="systemid" value="<%=etsWorkorderBatch.getSystemid()%>">
    <input type="hidden" name="flowSaveType">
    <input type="hidden" name="objectCategory" value="<%=category%>">
    <input type="hidden" name="isFirstNode" value="<%=isFirstNode%>">
    <input type="hidden" name="procName" value="<%=procName%>">
        <%=WebConstant.WAIT_TIP_MSG%>
</form>

</body>
<script type="text/javascript">
    function do_Cancel() {//取消
    	if (confirmCancel()) {
            window.close();
        }
    }

    function do_Save() {//暂存
        document.mainFrm.action = "/servlet/com.sino.ams.workorder.servlet.EtsWorkorderBatchServlet?flowSaveType=<%=DictConstant.FLOW_SAVE%>";
        document.mainFrm.submit();
    }

    function do_Complete() {//完成

		var tab = null;
    	var rowCount = 0;
	    var imp = "";
	    var arc = "";
		var implement = "";
		var arcUserBy = "";

    	if (<%=isNeed%>) {
            fieldNames = "workorderBatchName;prjName";
            fieldLabels = "任务名称;所属工程";
            emptyValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
        } 
        if (emptyValid) {
            if (parent.wo.document.all("systemids")) {

				tab = parent.wo.document.all("dataTable");
				rowCount = tab.rows.length;
				for(var i = 0; i < rowCount; i++) { 
					imp = parent.wo.document.all("implement" + i).value;
					implement = imp.substring(imp.indexOf("/") + 1, 100);
					arc = parent.wo.document.all("arcUserBy" + i).value;
					arcUserBy = arc.substring(arc.indexOf("/") + 1, 100);
					if (implement == "") {
						alert("请选择执行人");
						parent.wo.document.all("implement" + i).focus();
						return false;
					}
					if (arcUserBy == "") {
						alert("请选择归档人");
						parent.wo.document.all("arcUserBy" + i).focus();
						return false;
					}
				}

                if (document.mainFrm.workorderType.value == "<%=DictConstant.ORDER_TYPE_TRANS%>"){//搬迁工单
                    if (parent.wo.document.all("hasTransObject").value=="false") {
                        alert("搬迁工单必须填写搬迁至地点！");
                        return;
                    }

                }
                //step1:处理应用业务
                //step2: 调用流程方法
                /*参数说明：
                    orgId:当前用户的OU，主要是用于新建一条流程记录时，找到该流程的开始节点。
                    userId:：当前用户的userId;
                    deptId：如果下节点是多部门的，该处就必须传值。下一个节点取此部门的办理人。
                    procdureName:流程名称，新开始一条流程时，根据orgId和流程名去找流程ID。
                    flowCode:流程分流控制码。
                    'do_ApproveApply()'：提交页面的方法。但assign取到必要的参数时，调用此方法提交页面。
                */
                document.mainFrm.flowSaveType.value = "<%=DictConstant.FLOW_COMPLETE%>";
                var paramObj = new Object();
                var procdureName = mainFrm.procName.value;
                procdureName = "<%=procName%>";
                paramObj.orgId = "<%=sfUser.getOrganizationId()%>";
                paramObj.useId = "<%=sfUser.getUserId()%>";
                paramObj.groupId = document.mainFrm.distributeGroupId.value;
                paramObj.procdureName = procdureName;
                paramObj.flowCode = "";
                paramObj.submitH = "submitH()";
//                assign(paramObj);
                document.mainFrm.submit();
                alert("成功新增交接工单！")
            } else {
                alert("您没有创建工单！");
            }
        }
    }

    function do_Back(){//退回

    }

    function submitH(){
    	mainFrm.submit();
    }

   	function choosePrj() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT_AUTHORIZED %>";
        var dialogWidth = 50.6;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if(projects){
            dto2Frm(projects[0], "mainFrm");
        }else{
           document.mainFrm.prjName.value="";
           document.mainFrm.prjId.value="";
        }
    }

    function addOrders() {//添加工单
        var prjId = mainFrm.prjId.value;
        var prjName = mainFrm.prjName.value;
        if(prjId == null || prjId == "" || prjName == null || prjName == ""){
            alert("所属工程不能为空！");
            mainFrm.prjName.focus();
            return;
        }
        var screenHeight = window.screen.height;
        var screenWidth = window.screen.width;

        var dialogStyle = "dialogWidth=" + screenWidth + ";dialogHeight=" + screenHeight + ";help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
        var workorderBatchNo = document.mainFrm.workorderBatch.value;
        var workorderType = document.mainFrm.workorderType.value;
        var category = document.mainFrm.objectCategory.value;
        var groupId = document.mainFrm.distributeGroupId.value;
        var groupName = document.mainFrm.distributeGroupName.value;
        var workorderTypeDesc = document.mainFrm.workorderTypeDesc.value;
        var url = "<%=URLDefineList.WORKORDER_CHOOSE_SERVLET%>?act=<%=WebActionConstant.NEW_ACTION%>&objectCategory=" + category;
        url += "&workorderType=" + workorderType + "&workorderBatch=" + workorderBatchNo + "&distributeGroupId=" + groupId;
        url += "&workorderTypeDesc=" + workorderTypeDesc + "&groupName=" + groupName + "&prjId=" + prjId + "&prjName=" + prjName;

        var retValue = window.showModalDialog(url, null, dialogStyle);
        if (retValue) {//刷新 Iframe
            var ifra=document.all("wo");
            url=ifra.src;
            ifra.src=url+"&update=y";
        }
    }

    function addMyOrders() {//添加工单
        var prjId = mainFrm.prjId.value;
        var prjName = mainFrm.prjName.value;
        if(prjId == null || prjId == "" || prjName == null || prjName == ""){
            alert("所属工程不能为空！");
            mainFrm.prjName.focus();
            return;
        }
        var screenHeight = window.screen.height;
        var screenWidth = window.screen.width;

        var dialogStyle = "dialogWidth=" + screenWidth + ";dialogHeight=" + screenHeight + ";help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
        var workorderBatchNo = document.mainFrm.workorderBatch.value;
        var workorderType = document.mainFrm.workorderType.value;
        var category = document.mainFrm.objectCategory.value;
        var groupId =document.mainFrm.distributeGroupId.value;
        var groupName = document.mainFrm.distributeGroupName.value;
        var workorderTypeDesc = document.mainFrm.workorderTypeDesc.value;
        var url = "<%=URLDefineList.WORKORDER_CHOOSE_SERVLET%>?act=mine&objectCategory=" + category;
        url += "&workorderType=" + workorderType + "&workorderBatch=" + workorderBatchNo + "&distributeGroupId=" + groupId;
        url += "&workorderTypeDesc=" + workorderTypeDesc + "&groupName=" + groupName + "&prjId=" + prjId + "&prjName=" + prjName;

        var retValue = window.showModalDialog(url, null, dialogStyle);
        if (retValue) {//刷新 Iframe
            var ifra=document.all("wo");
            url=ifra.src;
            ifra.src=url+"&update=y";
        }
    }

    function initPage(){
        do_ComputeWOHeight();
        do_LoadTmpWorkorderURL();
        do_ChooseGroup();
    }

    function do_LoadTmpWorkorderURL(){
        var url="<%=URLDefineList.WORKORDER_TMP_SERVLET%>?workorderBatchNo=<%=etsWorkorderBatch.getWorkorderBatch()%>";
        url+="&workorderType=<%=etsWorkorderBatch.getWorkorderType()%>&isFirstNode=<%=isFirstNode%>&objectCategory=<%=category%>";
        url += "&groupId=" + mainFrm.distributeGroupId.value;
        var ifra = document.all("wo");
        ifra.src = url;
    }

    function do_ChooseGroup(){
        var groupId = "<%=groupId%>";
        if(groupId == "-1"){
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
            mainFrm.distributeGroupName.value = "<%=groupName%>";
        }
    }


    function do_ComputeWOHeight(){
        var bodyHeight = document.body.clientHeight;
        var batchHeight = document.getElementById("batchTable").offsetHeight;
        var woTable = document.getElementById("woTable");
        woTable.style.height = bodyHeight - batchHeight - 30;
    }

    function do_attach(flag) {//附件处理
        var  popOpenStyle="width=300px,height=200px,center=yes,status=no";
        var workorderBatch = mainFrm.workorderBatch.value;
        var url = "/servlet/com.sino.ams.workorder.servlet.BatchFileServlet?workorderBatch=" + workorderBatch;
        window.open(url, "", popOpenStyle);
    }
    function do_suggestion(flag) {//意见处理
        var popOpenStyle;
        var workorderBatch = document.mainFrm.workorderBatch.value;
        var groupId = document.mainFrm.distributeGroupId.value;
        var url = "/servlet/com.sino.ams.workorder.servlet.EtsSuggestionServlet?workorderBatch=" + workorderBatch+"&groupId="+groupId;
        if (flag == 0) {
            popOpenStyle="width=300px,height=200px,center=yes,status=no";
            url += "&act=<%=WebActionConstant.NEW_ACTION%>&actId="<%=etsWorkorderBatch.getActid() %>;
        } else {
            popOpenStyle="width=500px,height=330px,center=yes,status=no";
            url+="&act=<%=WebActionConstant.QUERY_ACTION%>";
        }
        window.open(url, "", popOpenStyle);
    }

</script>
</html>