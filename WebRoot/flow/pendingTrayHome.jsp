<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.sinoflow.constant.ButtonMask" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>

<HEAD>
<TITLE>待办事宜</TITLE>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css" />
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/WebLibary/js/api.js"></script>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
</HEAD>

<BODY topmargin=0 rightMargin=0 leftmargin=0 bottommargin=0 scroll=yes onLoad="init()" onResize="window_onresize()">
<form name="listform" action="/servlet/com.sino.sinoflow.servlet.PendingTray" method="POST">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <%
    String keyword = (String) request.getAttribute(WebAttrConstant.SF_KEYWORD);
    if(keyword == null)
        keyword = "";
    String subject = (String) request.getAttribute(WebAttrConstant.SF_SUBJECT);
    if(subject == null)
        subject = "";
    String createby = (String) request.getAttribute(WebAttrConstant.SF_CREATEBY);
    if(createby == null)
        createby = "";
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.SF_ACT_INFO_DTO);
    boolean batchProcess = false;

    int allTaskCount;
    if(rows != null)
        allTaskCount = rows.getSize();
    else
        allTaskCount = 0;
    List rowsetList;
    rowsetList = (List)request.getAttribute(WebAttrConstant.SF_PENDINGTRAY_TOP_LIST);
    if(rowsetList != null) {
        for(int i = 0; i < rowsetList.size(); i++) {
            RowSet rs = (RowSet)rowsetList.get(i);
            allTaskCount += rs.getSize();
        }
    }

    rowsetList = (List)request.getAttribute(WebAttrConstant.SF_PENDINGTRAY_BOTTOM_LIST);
    if(rowsetList != null) {
        for(int i = 0; i < rowsetList.size(); i++) {
            RowSet rs = (RowSet)rowsetList.get(i);
            allTaskCount += rs.getSize();
        }
    }

    rowsetList = null;

    int topFlag = 0;
    int procCount = 0;
    int taskCount = 1, taskNumber = 1;
%>
     

        <SCRIPT LANGUAGE=javascript>
<!--
function doReload()
{
	document.location.href=document.location;

}

function do_search()
{
/*
    var url = "/servlet/com.sino.sinoflow.servlet.PendingTray?keyword='" +
              document.getElementById("keyword").value + "'&subject='" +
              document.getElementById("subject").value + "'&createby='" +
              document.getElementById("createby").value + "'";
    window.open(url, 'main');
*/
    listform.submit();
}
//-->
</SCRIPT>
        <script language="javascript">
    var gsinoServerPath="/SinoFlow";
  var DocLinkWinStyle="status=yes, toolbar=no, menubar=yes, location=no, resizable=yes, scrollbars=yes, width=640, height=480"
  function doOpenUrl(url,target)
  {
     	if(target=="_blank" && DocLinkWinStyle!=''){
 			window.open(url,target,DocLinkWinStyle);
		}else{
 			window.open(url,target);
		}
  }
  </script>
        <script language="JavaScript" src="/WebLibary/js/dynlayer.js">
</script>
        <SCRIPT LANGUAGE="JavaScript">
<!--
setTimeout("initTimer()", 3000)
var resizeTimerID = 0;
var iReadWindow = false;
var iHRatio = 0;
var iHRatio2 = 0;
var iListH = 0;
var iListReadH = 0;
var iMenuH = document.body.scrollHeight;
var iInit = false;
function initTimer() {
	iReadWindow = (eval(parent) && eval(parent.document) && eval(parent.document.body) && eval(top.topframe));
	if (iReadWindow) {
		iListH = document.body.clientHeight;
		iListReadH = parent.document.body.clientHeight;
		iHRatio = (iListH)*100/(iListReadH);
	}
}
var menulayer;
function init() {
menulayer = new DynLayer("menuDiv");
menulayer.slideInit();
iInit = true;
movemenulayer();
}
function movemenulayer() {
	if (!iInit)
		init();
	menulayer.setWidth(document.body.clientWidth)
	menulayer.setHeight(document.body.clientHeight-iMenuH)
}
function window_onresize() {
	movemenulayer()
}
//-->
</SCRIPT>
        <DIV id=menuDiv style="scroll:no">
          <script language=javascript>
function ReverseCheckListEx()
{
	var listform=document.listform;
   var msgids=listform.all("msgid");
	if(msgids ==null)
		return;
	if(!eval(msgids.length))
	{
		msgids.status=!msgids.status;
		return;
	}
	for(var i=0; i<msgids.length; i++)
	{
      	if((msgids[i] != null)&&(msgids[i].name=="msgid"))
			msgids[i].status = !msgids[i].status;
	}
	
}
</script>
          <table height=25 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF
border=1 cellpadding=0 cellspacing=0  STYLE='cursor:pointer;scroll:no'>
            <THEAD>
              <tr>
                  <th nowrap width=15% > <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
  border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">审批流水号:</td>
                      </tr>
                    </table></th>
                  <th width=20%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
  border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">名称</td>
                      </tr>
                    </table></th>
                  <th width=20%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">流程类型</td>
                      </tr>
                    </table></th>
                  <th width=10%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">任务狀态</td>
                      </tr>
                    </table></th>
                  <th nowrap width=12% > <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
  border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">拟稿部门</td>
                      </tr>
                    </table></th>
                  <th nowrap width=6% > <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
  border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">拟稿人</td>
                      </tr>
                    </table></th>
                <th nowrap width=17%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center"> 接收时间</td>
                    </tr>
                  </table></th>
              </tr>
            </THEAD>
<script language="javascript">
            
    function OpenApplication(actid, caseId, mask, newWindow) {
        var caselink = "/servlet/com.sino.sinoflow.servlet.ProcessCase?sf_actID='"+
            actid + "'&sf_appMask="+mask+"&sf_caseID="+caseId+"&signAct=0";
        if(newWindow) {
            h = window.screen.height;
            w = window.screen.width;
            f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,scroll=yes,resizable=yes";
            //top=0,left=0,width=" + w + ",height=" + h + ",
            window.open(caselink,"", f1);
            
        } else {
            window.location = caselink;
        }
    }
</script>
    <%@ include file="/flow/PendingTrayOthers.jsp" %>
            <%
    int i, j = 0, k = 1, l = 1;
    j = procCount;
    if (rows != null && rows.getSize() > 0) {
        Row row;
        String tridName = null, subIdName = null, btidName = null, btidSubName = null, procedureName = null, flowDescription = null;
        for (i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
            String procName = (String)row.getValue("SFACT_PROC_NAME");
            String flowDesc;
            if((row.getStrValue("SFACT_COMMENT_QTY").equals("0") || row.getStrValue("SFACT_COMMENT_QTY").equals(""))
                    || (Integer.parseInt(row.getStrValue("SFACT_COMMENT_TYPE")) & 0x20) == 0)
                flowDesc = row.getStrValue("SFACT_APPL_MSG");
            else
                flowDesc = row.getStrValue("SFACT_COMMENT_APPL_MSG");            
            String signDate = (String)row.getValue("SFACT_SIGN_DATE");
            String signDueDate = (String)row.getValue("SFACT_SIGN_DUE_DATE");
            String applColumn1 = row.getStrValue("SFACT_APPL_COLUMN_1");
            String applColumn2 = row.getStrValue("SFACT_APPL_COLUMN_2");
            String createBy = row.getStrValue("SFACT_COMPOSE_USER");
            String procId = row.getStrValue("SFACT_PROC_ID");
            String actId = row.getStrValue("SFACT_ACT_ID");
            String caseId = row.getStrValue("SFACT_CASE_ID");
            String handler = row.getStrValue("SFACT_HANDLER");
            String handlerGroup = row.getStrValue("SFACT_HANDLER_GROUP");
            String handlerDept = handlerGroup.replace(".", "\\");
            String plusGroup = row.getStrValue("SFACT_PLUS_GROUP");
            String bitMask = row.getStrValue("ALLOW_OPERATION");
            String dPriority = row.getStrValue("SFACT_DELIVERY_PRIORITY");
            String attribute1 = null;
            String attribute2 = null;
            String projName = row.getStrValue("PROJECT_NAME");
            String taskGroup = row.getStrValue("SFACT_TASK_GROUP");
            String taskName = row.getStrValue("SFACT_TASK_NAME");
            String applColumn8 = row.getStrValue("SFACT_APPL_COLUMN_8").replaceAll(",", ";");
            String trayType = row.getStrValue("TRAY_TYPE");
            String applColumn4 = row.getStrValue("SFACT_APPL_COLUMN_4");
            String checkValue = actId + "," + procName + "," + flowDesc + "," + attribute2 + "," + handler +
                    "," + handlerGroup + "," + plusGroup + "," + projName + "," + taskGroup + "," + applColumn8 +
                    "," + trayType + "," + caseId + "," + applColumn4;
            int bMask = 0;
            if(!bitMask.equals(""))
                bMask = Integer.parseInt(bitMask);

            if(((String)row.getValue("FINISH_MESSAGE")).equals("1")) {
                bMask += ButtonMask.FINISHMESSAGE_MASK;
            }
            bitMask = String.valueOf(bMask);
            boolean isNewWindow = ((String)row.getValue("WINDOW_TYPE")).equals("1");
            boolean newProc = false;
            if (!procName.equals(procedureName)) {
                j++;
                k = 1;
                tridName="tr_"+ j;
                btidName="bt_"+ j;
                procedureName = procName;
                newProc = true;
%>
            <%
    }
    String subName;
    if(!flowDesc.equals(flowDescription) || newProc) {
        newProc = false;
        l = 1;
        subIdName = tridName + "_" + k;
        btidSubName = btidName + "_" + k;
        k++;
%>
            <%
        l = 1;
        flowDescription = flowDesc;
    } else {
        l++;
    }
    subName = subIdName + "_" + l;
    Calendar now = Calendar.getInstance();
    now.setTime(new Date());
    DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar signDueDateCal = Calendar.getInstance();
//    signDueDateCal.setTime(now.getTime());
    try {
        signDueDateCal.setTime(dformat.parse(signDueDate));
    } catch (ParseException ex) {
        signDueDateCal.setTime(now.getTime());
    }
    String timeGif = "/images/notExpire.gif";
    if(now.after(signDueDateCal))
        timeGif = "/images/expire.gif";
    String priorityGif = "/images/normal.gif";
    if(dPriority.equals("1")) {
        priorityGif = "/images/normalHigh.gif";
    } else if(dPriority.equals("2")) {
        priorityGif = "/images/urgent.gif";
    } else if(dPriority.equals("3")) {
        priorityGif = "/images/topUrgent.gif";
    }
%>
            <tr id='<%=subName%>'height=25 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>

              <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer' align="left">
                  <tr>
                    <td nowrap align="top" onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="priority" src="<%=priorityGif%>"><%=applColumn1%></td>
                  </tr>
                </table>
                </font></td>
              <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer' align="right">
                  <tr align="right">
				    <!--<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>-->
                    <td width="260px" align="left"nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=applColumn2%></td>
                  </tr>
                </table>
                </font></td>
                <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer' align="left">
                  <tr>
                    <td align="left" onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=procName%></td>
                  </tr>
                </table>
                </font></td>
                <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer' align="left">
                  <tr>
                    <td align="left" onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=taskName%></td>
                  </tr>
                </table>
                </font></td>
                <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                  <table border=0 width=100% style='cursor:pointer' align="center">
                    <tr align="right">
                      <!--<td></td>-->
                      <td width="120px" align="center" nowrap onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=handlerDept%></td>
                    </tr>
                  </table>
                  </font></td>
                <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                  <table border=0 width=100% style='cursor:pointer' align="center">
                    <tr align="right">
                      <!--<td></td>-->
                      <td width="40px" align="center" nowrap onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=createBy%></td>
                    </tr>
                  </table>
                  </font></td>
              <td bordercolordark=black bordercolorlight=#c0c0c0 nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer'>
                  <tr>
				    <!--<td>&nbsp;&nbsp;&nbsp;</td>-->
                    <td  align="center" nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=signDate%></td>
                  </tr>
                </table>
                </font></td>
              <%
    } }
    topFlag = 1;
    procCount = j;
%>
          <%@ include file="/flow/PendingTrayOthers.jsp" %>

          </table>
          <script language=javascript>
var outcount=<%=procCount%>;
function doExpandAll()
{
   var btid,trid,i,element;
   for(i=1;i<=outcount;i++)
   {
       btid="bt_"+i;
       trid="tr_"+i;
       for(element in document.all)
       {
           if((element.substr(0,4)==trid)&& element.length>4)
               eval("document.all."+element+".style.display=''");
           if(element.substr(0,4)==btid)
               eval("document.all."+"bt"+element.substr(2,element.length)+".src='/image/sub.png'");
       }
   }
}

function doCollapseAll()
{
    var btid,trid,element,i;
   for(i=1;i<=outcount;i++)
   {
       btid="bt_"+i;
       trid="tr_"+i;
       for(element in document.all)
       {
           if((element.substr(0,4)==trid)&& element.length>4)
               eval("document.all."+element+".style.display='none'");
           if(element.substr(0,4)==btid)
               eval("document.all."+"bt"+element.substr(2,element.length)+".src='/image/add.png'");
       }
   }
}
function doOutline(obj)
{
    var bExpanded;
    var objid=obj.id;
    var trid="tr"+objid.substring(2,objid.length);
    var tridlen=trid.length;
    var objidlen=objid.length;

    var imgsrc=obj.src;
    var img=imgsrc.substring(imgsrc.lastIndexOf("/")+1,imgsrc.length);
    var element;

    bExpanded = (img.toLowerCase() == ("sub.png"));

    for(element in document.all)
    {
        if((element.substr(0,objidlen)==objid))
        {
           if(bExpanded)
               eval("document.all."+element+".src='/image/add.png'");
           else
                eval("document.all."+element+".src='/image/sub.png'");
        }
        if((element.substr(0,tridlen)==trid)&&(element.length!=tridlen))
        {
            if(bExpanded)
                eval("document.all."+element+".style.display='none'");
            else
                eval("document.all."+element+".style.display=''");
        }
    }
}

function GetSelectedKey()
{
	var listform=document.listform;
	var arrRet=new Array();
        var msgids=listform.all("msgid");

	n=0;
	if(msgids ==null)
		return null;
	if(!eval(msgids.length))
	{
		if(msgids.checked){
			arrRet[n] = msgids.value;
			return arrRet;
			//return strRet=msgids.value;
		}else{
			return null;
		}
	}
	for(i=0; i<msgids.length; i++)
	{
      		if((msgids[i] != null)&&(msgids[i].name=="msgid"))
      		{
      			if(msgids[i].checked==true)
      			{
      				arrRet[n]=msgids[i].value;
      				n=n+1;
      			}
      		}

	}
	return arrRet;
}

function batchProcessActs(actids) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.BatchProcessActs?sf_actIDs="
            +actids;
    var popscript;
    popscript = "dialogWidth:200px"
            + ";dialogHeight:100px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function doBatchProcess()
{
	var arr=GetSelectedKey();

	if(arr==null||arr==""||arr.length==0)
	{
		alert("请选择条目!");
		return;
	}

    var checkValue = arr[0];
    var checkList = checkValue.split(",");
    var procName = checkList[1];
    var flowDesc = checkList[2];
//    var ids = "";
    var lcode = checkList[3];
    var actId = checkList[0];
    sf_handler = checkList[4];
    sf_handler_group = checkList[5];
    sf_plus_group = checkList[6];
    var projName = checkList[7];
    var group = setMaskGroup(checkList[8], sf_handler_group, sf_plus_group);
    var trayType = checkList[10];
    for(i=1; i<arr.length;i++)
    {
        checkValue = arr[i];
        checkList = checkValue.split(",");
        if(procName != checkList[1] || flowDesc != checkList[2]) {
            alert("只能选择相同的任务作批量审批!");
            return;
        }
        sf_handler = checkList[4];
        sf_handler_group = checkList[5];
        sf_plus_group = checkList[6];
        if(group != setMaskGroup(checkList[8], sf_handler_group, sf_plus_group)) {
            alert("只能选择相同组别的任务作批量审批!");
            return;
        }
/*
        if(ids != "") {
            ids += ";" + checkList[0];
        } else {
            ids = checkList[0];
        }
*/
    }

    if(!confirm('确定要批量审批吗?'))
        return;

    var str = selectFlowByAct(actId, lcode,"0");
    var flowHint = "";
    var flows;
    eval("flows = " + str);
    if(flows.length > 1) {
        if(group.indexOf("省公司") == 0) {
            Launch_Code_Bypass = "Z1";
        } else {
            Launch_Code_Bypass = "Z2";
        }
        if(group.indexOf("市场经营部") > 0) {
            if(Launch_Code_Bypass == "")
                Launch_Code_Bypass = "002";
            else
                Launch_Code_Bypass += ";002";
        }
        str = conditionalFlowDlg(str, Launch_Code_Bypass);
        if(str == "")
            return;
        lcode = getJsonData(str, "flowCode:");
        flowHint = getJsonData(str, "flowHint:");
    }
//    alert("str = " + str);
    var allflow;
    eval("allflow = " + str);
    var isSplit = false;
    var isJoin = false;
    if(allflow.length > 2) {
        alert("批量审批不支持并发流程大于 1 的任务!")
        return;
    } else if(allflow.length == 2) {
        if(allflow[0].taskName == "SPLIT")
            isSplit = true;
    }
    var fstr = searchFlowStr(str, "groupName");
    var flowProp;
//    alert("str = " + str + ", fstr = " + fstr);

    if(fstr == "") {
        fstr = searchFlowStr(str, "taskName");
        eval("flowProp = " + fstr);
        if(flowProp.taskName != "STOP" && flowProp.taskName != "JOIN") {
            alert("找不到下一任务组别!");
            return;
        }
    } else {
        eval("flowProp = " + fstr);
    }

    var taskGroup = "";
    var roleName = "";
    var needSelect = false;
    var usernames = "";
    var realnames = "";
    var userSelected = false;

    if(flowProp.groupName) {
        taskGroup = flowProp.groupName;
        taskGroup = setMaskGroup(taskGroup, sf_handler_group, sf_plus_group);
        roleName = flowProp.roleName;
        if(needGroupSelected(taskGroup) != 0) {
/*
            taskGroup = taskGroup.replace("安阳分公司", "省公司");
            var group = checkGroupMask(projName, taskGroup, roleName)
            alert("group = " + group);
            alert("下一任务需要选择组别, 批量审批不支持此操作!");
            return;
*/
            needSelect = true;
        }

//      alert("taskGroup = " + taskGroup);
//      alert("roleName = " + roleName);
        usernames = flowProp.usernames;
//      alert("users = " + usernames);

        if(flowProp.groupName.indexOf("*") >= 0 && flowProp.groupName.indexOf("***") < 0
                && flowProp.groupName.indexOf("+") < 0) {
            needSelect = true;
        } else if(usernames == "" && taskGroup.indexOf("+") < 0 && taskGroup.indexOf("*") < 0) {
            alert("组别:" + taskGroup + ", 角色:" + roleName + "没有定义用户!");
            return;
        }

        realnames = flowProp.realnames;
    } else
        userSelected = true;
    var flowGroup = "";
    if(flowProp.flowType == '3') {
        userSelected = false;
        needSelect = false;
        flowGroup = flowProp.groupName;
    }
    var usersStr = "";

    for(i=0; i<arr.length;i++)
    {
        checkValue = arr[i];
        checkList = checkValue.split(",");
        sf_handler = checkList[4];
        sf_handler_group = checkList[5];
        sf_plus_group = checkList[6];
        var participants;
        var names;
//        alert("flowType = " + flowProp.flowType);
        if(!userSelected) {
            if(needSelect) {
//                alert("1");
                if(flowProp.groupName.indexOf("*") >= 0 && flowProp.groupName.indexOf("***") < 0
                        && flowProp.groupName.indexOf("+") < 0)
                    taskGroup = checkList[12];
                else
                    taskGroup = checkList[9];
//                alert("taskGroup = " + taskGroup);
                participants = getGroupsUsersNames(projName, taskGroup, flowProp.roleName);
//                alert("participants = " + participants);
                if(participants == "") {
                    alert("找不到下一并发任务的用户!");
                    return;
                }
//                alert("usernames = " + flowProp.usernames);
                var retName;
//                alert("participants = " + participants);
                eval("retName = " + participants);
                flowProp.usernames = retName[0].usernames;
                flowProp.realnames = retName[0].realnames;
                if(flowProp.usernames == "") {
                    alert("找不到下一并发任务的用户!");
                    return;
                }
                if(flowProp.usernames.indexOf(",") > 0 && trayType == "1") {
                    str = "[{taskName:'" + flowProp.taskName + "', groupName:'" + taskGroup +
                              "', roleName:'" + flowProp.roleName + "', ";
                    str += "realnames:'" + flowProp.realnames + "', usernames:'" + flowProp.usernames + "', flowProp:'0'}]";
                    var selectedStr = "";
                    participants = selectParticipantDlg(str, selectedStr, "2");
                } else {
                    participants = "[{realnames:'" + flowProp.realnames + "',usernames:'" + flowProp.usernames + "'}]";
                }
                if(participants == "")
                    return;
                eval("names = " + participants);
                flowProp.realnames = names[0].realnames;
                flowProp.usernames = names[0].usernames;
                userSelected = true;
            } else if(flowProp.flowType == '3') {
                if(flowGroup.indexOf("*") >= 0 && flowGroup.indexOf("***") < 0
                        && flowGroup.indexOf("+") < 0)
                    taskGroup = checkList[12];
                else
                    taskGroup = setMaskGroup(flowGroup, sf_handler_group, sf_plus_group);
                var names=getGroupsUsersNames(checkList[7],taskGroup,flowProp.roleName);
                var retNames;
                eval("retNames = "+names);
                flowProp.usernames = retNames[0].usernames;
                flowProp.realnames = retNames[0].realnames;
                flowProp.groupName = taskGroup;
                var handler = getHandler(sf_handler, flowProp.usernames, ",");
                if(handler == "" || handler.indexOf(";") >= 0) {
                    if(flowProp.usernames.indexOf(",") >= 0 || flowProp.usernames.indexOf(";") >= 0) {
                        var handlerName = getHandlerName(checkList[11],
                                flowProp.groupName, flowProp.roleName);
                        var handlerRealnames;
                        if(handlerName == "" || !inList(sf_handler, handlerName, ";")) {
                            if(handler != "") {
                                handlerRealnames = getHandlerRealnames(handler, flowProp, ";");
                                usersStr = "[{taskName:'" + flowProp.taskName + "', groupName:'" + flowProp.groupName +
                                          "', roleName:'" + flowProp.roleName + "', ";
                                usersStr += "realnames:'" + handlerRealnames + "', usernames:'" + handler + "', flowProp:'0'}]";
                            } else {
                                usersStr = str;
                            }
                            participants = selectParticipantDlg(usersStr, "", "0");
                            if(participants == "")
                                return "";
                            eval("names = " + participants);
        //                  handler = names[0].usernames;
                            flowProp.realnames = names[0].realnames;
                            flowProp.usernames = names[0].usernames;
                        } else {
                            handlerRealnames = getHandlerRealnames(handler, flowProp, ";");
                            flowProp.realnames = handlerRealnames;
                            flowProp.usernames = handlerName;
                        }
                    }
                } else {
                    flowProp.realnames = getRealname(handler) + "/" + flowProp.groupName;
                    flowProp.usernames = handler;
                }
//                userSelected = true;
            } else {
//                alert("usernames = " + usernames);
                if(usernames.indexOf(",") >= 0) {
                    usersStr = "[{taskName:'" + flowProp.taskName + "', groupName:'" + taskGroup +
                              "', roleName:'" + flowProp.roleName + "', ";
                    usersStr += "realnames:'" + flowProp.realnames + "', usernames:'" + flowProp.usernames + "', flowProp:'0'}]";
                    if(isSplit)
                        usersStr = selectParticipantDlg(usersStr, "", "1");
                    else
                        usersStr = selectParticipantDlg(usersStr, "", "0");
                    if(usersStr == "") {
                        return;
                    } else {
                        var users;
                        eval("users = " + usersStr);
                        flowProp.realnames = users[0].realnames;
                        flowProp.usernames = users[0].usernames;
                    }
                }

                userSelected = true;
            }
        }
        if(document.getElementById("sf_opinion").value == "") {
            var temp;
            if(flowHint == "")
                temp = inputOpinion(flowProp.flowHint);
            else
                temp = inputOpinion(flowHint);
            if(temp == "")
                return;
            else
                document.getElementById("sf_opinion").value = temp;
            if(document.getElementById("sf_opinion").value == "undefined") {
                document.getElementById("sf_opinion").value = "";
                return;
            }
            if(document.getElementById("sf_opinion").value == "")
                document.getElementById("sf_opinion").value = flowProp.flowHint;
        }

        checkValue = arr[i];
        checkList = checkValue.split(",");
        var retStr;
        if(flowProp.usernames)
            retStr = completeCaseWithOpinion(checkList[0], taskGroup, flowProp.usernames, lcode, document.getElementById("sf_opinion").value);
        else
            retStr = completeCaseWithOpinion(checkList[0], taskGroup, "", lcode, document.getElementById("sf_opinion").value);
//        alert("retStr = " + retStr);
        if(retStr.indexOf("ERROR") >= 0) {
            alert("批量审批完成出错!");
            return;
        }
    }

//    batchProcessActs(ids);
    location.reload(true);
}

function inputOpinion(message){
    var styleStr = "dialogWidth:425px;dialogHeight:330px;center:yes;status:no;scroll:no;";
    return window.showModalDialog(
            "/flow/inputApproveContent.jsp?flowDesc='" + message + "'",null,styleStr);
}
   </script>
        </DIV>
        <SCRIPT LANGUAGE="JavaScript">
<!--
init();
//-->
</SCRIPT>
        <input type=hidden name="mod" value="view">
        <input type=hidden name="fnc" value="openappbyprof">
        <input type=hidden name="unid" value="21E9F7BB7C12D9AC48256C1800265FE5">
        <input type=hidden name="start" value="4.1">
        <input type=hidden name="prepos" value="4.1">
        <input type=hidden name="nextpos" value="4.1">
        <input type=hidden name="count" value=30>
        <input type=hidden name="appname" value="在办箱">
        <input type=hidden name="view" value="InTray">
        <input type=hidden name="flagname" value=''>
        <input type=hidden name="flagvalue" value=''>
        <input type=hidden name="topublish" value=''>
        <input type=hidden name="sf_opinion" value=''>
  </table>
</form>
</BODY>
</HTML>
