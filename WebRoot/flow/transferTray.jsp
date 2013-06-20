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
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript" src="/WebLibary/js/destinationSelect.js"></script>

<script language=javascript>
var groupSelect = "";
function SFQueryOpen () {
Error_Msg = "";
Launch_Continue = true;
}
function SFPostOpen () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQuerySign () {
Error_Msg = "";
Launch_Continue = true;
}
function SFPostSign () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryComplete () {
Error_Msg = "";
Launch_Continue = true;
}
function SFGroupReview () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryCycleReview () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryConditionalFlow () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryGroup () {
Error_Msg = "";
Launch_Continue = true;
if(groupSelect == "") {
  manOrDeptObj.manOrDept="";
  manOrDeptObj.manOrDeptLogin="";
  var midValue=allDeptSelect("",manOrDeptObj);
  if(midValue == "" || midValue == "&&&clear&&&") {
    Launch_Continue = false;
    return;
  }
  var midArray=midValue.split(":");
  groupSelect = Launch_HandleGroup = getMaskGroup("***", document.getElementById("sf_handlerGroup").value, document.getElementById("sf_plusGroup").value) + "." + midArray[1];
} else {
  Launch_HandleGroup = groupSelect;
}
}
function SFParallelFlow () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryAssistFlow () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryDistribute () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQueryGoBack () {
Error_Msg = "";
Launch_Continue = true;
}
function SFQuerySave () {
Error_Msg = "";
Launch_Continue = true;
}
function SFPostSave () {
Error_Msg = "";
Launch_Continue = true;
}

</script>

<HEAD>
<TITLE>流程调度</TITLE>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css" />
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/WebLibary/js/api.js"></script>
<link rel="stylesheet" type="text/css" href="/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="/WebLibary/exj/ext/ext-base.js"></script>
<script type="text/javascript" src="/WebLibary/exj/ext/ext-all.js"></script>
<script language="javascript" src="/WebLibary/exj/ext/SessionProvider.js"></script>
<script type="text/javascript" src="/WebLibary/exj/ext/layout.js"></script>
<script type="text/javascript" src="/WebLibary/exj/shared/examples.js"></script>
<script type="text/javascript" src="/extjs/ux/MultiSelect.js"></script>
<script type="text/javascript" src="/extjs/ux/ItemSelector.js"></script>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<script type="text/javascript" src="/WebLibary/js/util2.js"></script>

</HEAD>

<BODY topmargin=0 rightMargin=0 leftmargin=0 bottommargin=0 scroll=yes onLoad="init()" onResize="window_onresize()">
<form name="listform" action="/servlet/com.sino.sinoflow.servlet.TransferTray" method="POST">
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

    int allTaskCount;
    if(rows != null)
        allTaskCount = rows.getSize();
    else
        allTaskCount = 0;

    int topFlag = 0;
    int procCount = 0;
    int taskCount = 1, taskNumber = 1;
%>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a6c3eb">
          <tr>
            <td width="63%" height="25" background="/image/table_bg1.jpg" bgcolor="#FFFFFF" class="blue">个人工作台 &gt; 流程调度(共<%=allTaskCount%>个)</td>
            <td width="10%" background="/image/table_bg1.jpg"  align="center" bgcolor="#FFFFFF" onClick="doTransfer()"><table width="76%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="28%"><img src="/image/shenpi.png" width="16" height="16" /></td>
                  <td width="72%" style="cursor:pointer;">调度</td>
                </tr>
              </table></td>
            <td width="9%"  align="center" background="/image/table_bg1.jpg" bgcolor="#FFFFFF" onClick="doReload()"><table width="82%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="28%"><img src="/image/shuaxin.png" width="16" height="16" /></td>
                  <td width="72%" style="cursor:pointer;">刷新</td>
                </tr>
              </table></td>
            <!--<td width="9%"  align="center" background="/image/table_bg1.jpg" bgcolor="#FFFFFF" onClick="doCollapseAll()"><table width="80%" height="17" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="28%" ><img src="/image/zhankai.png" width="16" height="16" /></td>
                  <td width="72%" style="cursor:pointer;">折叠</td>
                </tr>
              </table>
            </td>
            <td width="9%"  align="center" background="/image/table_bg1.jpg" bgcolor="#FFFFFF" onClick="doExpandAll()"><table width="77%" height="19" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="28%"><img src="/image/zengjia.png" width="16" height="16" /></td>
                  <td width="72%" style="cursor:pointer;">展开</td>
                </tr>
              </table></td>-->
          </tr>
        </table>
      </tr>
      </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <table width="100%">
      <tr>
          <td width="8%" align="right">合同流水号:</td>
          <td width="20%"><input type="text" name="keyword" value="<%=keyword%>" class="input_14"></td>
          <td width="8%" align="right">合同名称:</td>
          <td width="20%"><input type="text" name="subject" value="<%=subject%>" class="input_14"></td>
          <td width="8%" align="right">拟稿人</td>
          <td width="20%"><input type="text" name="createby" value="<%=createby%>" class="input_14"></td>
          <td width="4%"></td>
          <td width="10%"><input name="Search" type="button" class="but4" id="button" value="查询" onClick="do_search()"/></td>
      </tr>
      </table>

        <SCRIPT LANGUAGE=javascript>
<!--
function doReload()
{
	document.location.href=document.location;

}

function do_search()
{
/*
    var url = "/servlet/com.sino.sinoflow.servlet.TransferTray?keyword='" +
              escape(document.getElementById("keyword").value) + "'&subject='" +
              escape(document.getElementById("subject").value) + "'&createby='" +
              escape(document.getElementById("createby").value) + "'";
    window.open(url, 'main');
*/
    listform.submit();
}
//-->
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
                <!--<th nowrap width=5%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT">&nbsp; </td>
                    </tr>
                  </table></th>
                <th nowrap width=4%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT">&nbsp; </td>
                    </tr>
                  </table></th>-->
                <th nowrap> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100%  onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#505050'"
 onMouseOut="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF'"
 onMouseUp="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF'"
 STYLE='cursor:pointer'>
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align=center><img alt="t_check" src="/images/t_check.gif" width=12 height=12></td>
                    </tr>
                  </table></th>
                <th nowrap width=15%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center"> 合同流水号</td>
                    </tr>
                  </table></th>
                <th nowrap width=27% > <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center">合同名称</td>
                    </tr>
                  </table></th>
                  <th nowrap width=20%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100% >
                      <tr>
                        <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center"> 流程名称</td>
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
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center"> 签收时间</td>
                    </tr>
                  </table></th>
                <th nowrap width=20%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
                    <tr>
                      <td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="center"> 预定时间</td>
                    </tr>
                  </table></th>
                <th nowrap width=100%> <table height=25 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=0 cellpadding=0 cellspacing=0 width=100%>
                    <tr>
                      <td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>&nbsp;</td>
                    </tr>
                  </table></th>
              </tr>
            </THEAD>
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
//            String sortColumn1 = row.getStrValue("SFACT_SORT_COLUMN_1");
//            String sortColumn2 = row.getStrValue("SFACT_SORT_COLUMN_2");
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
            String attribute1 = row.getStrValue("SFACT_TASK_ATTRIBUTE_1");
            String attribute2 = row.getStrValue("SFACT_TASK_ATTRIBUTE_2");
            String projName = row.getStrValue("PROJECT_NAME");
            String taskGroup = row.getStrValue("SFACT_TASK_GROUP");
            String applColumn8 = row.getStrValue("SFACT_APPL_COLUMN_8").replaceAll(",", ";");
            String trayType = row.getStrValue("TRAY_TYPE");
            String applColumn4 = row.getStrValue("SFACT_APPL_COLUMN_4");
            String taskName = row.getStrValue("SFACT_TASK_NAME");
            String checkValue = actId + "&,&" + procName + "&,&" + flowDesc + "&,&" + attribute2 + "&,&" + handler +
                    "&,&" + handlerGroup + "&,&" + plusGroup + "&,&" + projName + "&,&" + taskGroup + "&,&" + applColumn8 +
                    "&,&" + trayType + "&,&" + caseId + "&,&" + applColumn4 + "&,&" + procId + "&,&" + taskName;
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
            <!--<tr id='<%=tridName%>' height=18 bgcolor=white>
              <td class="tr_top1"><img id='<%=btidName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/image/sub.png" onClick="doOutline(this)"></td>
              <td class="tr_top1" colspan="8">&nbsp;<font color="blue"><span class="blue"><%=procName%></span></font></td>
              <td class="tr_top1" bordercolordark=white bordercolorlight=white nowrap></td>
            </tr>-->
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
            <!--<tr height=25 bgcolor=white id="<%=subIdName%>">
              <td></td>
              <td class="tr_top2"><img id='<%=btidSubName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/image/sub.png" onClick="doOutline(this)"></td>
              <td class="tr_top2"  colspan="7">&nbsp;<font color="blue"><%=flowDesc%></font></td>
              <td class="tr_top2" bordercolordark=white bordercolorlight=white nowrap></td>
            </tr>-->
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
              <!--<td bordercolordark=black bordercolorlight=black nowrap></td>
              <td bordercolordark=black bordercolorlight=black nowrap></td>-->
              <td bordercolordark=black bordercolorlight=black><input type=checkbox name="msgid" STYLE='cursor:pointer' value="<%=checkValue%>"></td>
              <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer' align="left">
                  <tr>
                    <td nowrap align="top" onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="priority" src="<%=priorityGif%>"><%=applColumn1%></td>
                  </tr>
                </table>
                </font></td>
              <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer' align="right">
                  <tr align="right">
				    <!--<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>-->
                    <td width="260px" align="left"nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=applColumn2%></td>
                  </tr>
                </table>
                </font></td>
                <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="black">
                  <table border=0 width=100% style='cursor:pointer' align="right">
                    <tr align="right">
                      <td></td>
                      <td align="left" nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=procName%></td>
                    </tr>
                  </table>
                  </font></td>
                <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="black">
                  <table border=0 width=100% style='cursor:pointer' align="right">
                    <tr align="right">
                      <td></td>
                      <td width="120px" align="left"nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=handlerDept%></td>
                    </tr>
                  </table>
                  </font></td>
                <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="black">
                  <table border=0 width=100% style='cursor:pointer' align="right">
                    <tr align="right">
                      <td></td>
                      <td width="40px" align="left"nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)" style="WORD-BREAK: break-all; BORDER-COLLAPSE: collapse"><%=createBy%></td>
                    </tr>
                  </table>
                  </font></td>
              <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="black">
                <table border=0 width=100% style='cursor:pointer'>
                  <tr>
				    <!--<td>&nbsp;&nbsp;&nbsp;</td>-->
                    <td  align="center" nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=signDate%></td>
                  </tr>
                </table>
                </font></td>
              <td bordercolordark=black bordercolorlight=black nowrap align="right"><font color="red">
                <table border=0 width=100% style='cursor:pointer'>
                  <tr>
				    <!--<td>&nbsp;&nbsp;&nbsp;</td>-->
                    <td align="center" nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="expire" src="<%=timeGif%>"> <%=signDueDate%></td>
                  </tr>
                </table>
                </font></td>
              <td bordercolordark=white bordercolorlight=white nowrap></td>
              <%
    } }
    topFlag = 1;
    procCount = j;
%>

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

var taskActId;
var fromTask;
var toTask;

function doTransfer() {
    var arr=GetSelectedKey();

    if(arr==null||arr==""||arr.length==0)
    {
        alert("请选择调度的合同案件!");
        return;
    } else if(arr.length > 1) {
        alert("一次只能调度一个合同案件!");
        return;
    }

    checkValue = arr[0];
    checkList = checkValue.split("&,&");
    var projName = checkList[7];
    var procName = checkList[1];
    fromTask = checkList[14];
    var procId = checkList[13];
    var handler = checkList[4];
    var handlerGroup = checkList[5];
    var plusGroup = checkList[6];
    var sendTask = checkTaskSend(projName, procName, fromTask, procId);
    if(sendTask == "")
        return;
    toTask = sendTask;
    taskActId = checkList[0];
    sf_project = checkList[7];
    toTask3(taskActId, fromTask, sendTask, procId, handlerGroup, plusGroup, handler);
}

function checkTaskSend(proj, proc, task, procId) {
    var getSendTaskURL = "/servlet/com.sino.sinoflow.servlet.GetSendTask?projName='"
        + proj + "'&procName='" + proc + "'&nowTask='" + task + "'&procId='"
        + procId + "'";

    h = window.screen.height;
    w = window.screen.width;
    var f1 = "dialogWidth:" + w
            + ";dialogHeight:" + h
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(getSendTaskURL, null, f1);
}

function toTask3(actId, from, to, id, handle, plus, handler) {
    var toTaskURL = "/servlet/com.sino.sinoflow.servlet.GetToTaskInfo?sf_actID='"
            + actId + "'&fromTask='"
            + from + "'&toTask='"
            + to + "'&procId='" + id
            + "'&handlerGroup='" + handle + "'";
    makeRequest(toTaskURL, ajaxFunction);
    if(ajaxReturn != "") {
        document.getElementById("sf_handler").value = handler;
        document.getElementById("sf_handlerGroup").value = handle;
        document.getElementById("sf_plusGroup").value = plus;
        groupSelect = "";
        ajaxReturn = fullSelect(ajaxReturn);
    }
    return ajaxReturn;
}

function do_Div_Complete_End(str) {
    var prop;
    eval("prop = " + str);
    var group = prop.groupName;
    var toUser = prop.usernames;
    var comment = "调度自" + fromTask;
    var msg = "调度";
    if(deliveryCase(taskActId, group, toUser, toTask, comment, msg).indexOf("SUCCESS") < 0) {
        alert("调度出现错误,请通知系统管理员!");
    } else {
        alert("调度成功!");
        document.location.href=document.location;
    }
}

function deliveryCase(actId, group, to, from, opinion, msg) {
    var deliveryURL = "/servlet/com.sino.sinoflow.servlet.DeliveryCase?actId='"
            + actId + "'&group='"
            + group + "'&toUser='"
            + to + "'&taskName='" + from
            + "'&comment='" + opinion + "'&msg='" + msg + "'";

    makeRequest(deliveryURL, ajaxFunction);
    return ajaxReturn;
}

function OpenApplication(actid, caseId, mask, newWindow) {
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
        <input type=hidden name="appname" value="流程调度">
        <input type=hidden name="view" value="InTray">
        <input type=hidden name="flagname" value=''>
        <input type=hidden name="flagvalue" value=''>
        <input type=hidden name="topublish" value=''>
        <input type=hidden name="sf_opinion" value=''>
  </table>

  <input type='hidden' name='sf_opinion' value='' readonly='readonly' />
  <input type='hidden' name='sf_trayType' value='1' readonly='readonly' />
  <input type='hidden' name='sf_setHandlerGroup' value='0' readonly='readonly' />
  <input type='hidden' name='sf_handler' value='' readonly='readonly' />
  <input type='hidden' name='sf_handlerGroup' value='' readonly='readonly' />
  <input type='hidden' name='sf_plusGroup' value='' readonly='readonly' />
  <input type='hidden' name='sf_handlerStatus' value='' readonly='readonly' />
  <input type="hidden" id="$$$waitTipMsg$$$" name="$$$waitTipMsg$$$">

</form>
</BODY>
</HTML>
