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
<style type="text/css">
.resizeDivClass{
text-align:right;
width:2px;
margin:0px 0 0px 0;
border:0px;
background-color:gray;
float:right;
cursor:e-resize;
}
</style>
<HTML>
<HEAD>
<TITLE>待办事宜</TITLE><STYLE TYPE=text/css>
A:link {color : black; text-decoration : none; font-size: 9pt}
A:visited {color : black; text-decoration : none; font-size: 9pt}
A:active {color : black; text-decoration : none; font-size: 9pt}
A:hover {color : black; text-decoration : none; font-size: 9pt}
TD,TH {color : black; text-decoration : none; font-size: 9pt}
INPUT {color : black; text-decoration : none; font-size: 9pt}
SELECT {color : black; text-decoration : none; font-size: 9pt}
TEXTAREA {color : black; text-decoration : none; font-size: 9pt}
</STYLE>
</HEAD>
<BODY topmargin=0 rightMargin=0 leftmargin=0 bottommargin=0 scroll=no bgcolor="white" onload="init();doCount();" onresize="window_onresize()">
<form name="listform" action="/servlet/com.sino.sinoflow.servlet.PendingTray3" method="POST">
<table height=20 width=100% bgcolor=#0073BF border=0 cellpadding=0 cellspacing=0>
<tr>
<%
    String keyword = (String) request.getAttribute(WebAttrConstant.SF_KEYWORD);
    if(keyword == null)
        keyword = "";
    String subject = (String) request.getAttribute(WebAttrConstant.SF_SUBJECT);
    if(subject == null)
        subject = "";
    String others = (String) request.getAttribute(WebAttrConstant.SF_OTHERS);
    if(others == null)
        others = "";
    String fromDateSearch = (String) request.getAttribute(WebAttrConstant.FROM_DATE);
    if(fromDateSearch == null)
        fromDateSearch = "";
    String toDateSearch = (String) request.getAttribute(WebAttrConstant.TO_DATE);
    if(toDateSearch == null)
        toDateSearch = "";
    String sortType = (String)request.getAttribute(WebAttrConstant.SORT_TYPE);
    String sortTypeStr = (String)request.getAttribute(WebAttrConstant.SORT_TYPE_STR);
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.SF_ACT_INFO_DTO);
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
<td nowrap align=left valign=bottom >&nbsp;<font  style="font-size: 10pt" color=#FFFFF><b> 个人工作台>>待办事宜</b></font> <font  style="font-size: 8pt" color=#FFFFF><b>(共<%=allTaskCount%>个)</b></font></td>

<td nowrap align=right valign=bottom ></td>

<td nowrap align=left valign=middle width=20><img alt="bull" src='/images/bull.gif' width=16 height=16></td>
</tr>
</table>
<div id="query" style="visibility:hidden;display:none">
<table  width="100%" bgcolor=#EFEFEF>
<tr>
    <td width="6%" align="right">工单号:</td>
    <td width="12%"><input type="text" name="keyword" value="<%=keyword%>" ></td>
    <td width="6%" align="right">主题:</td>
    <td width="12%"><input type="text" name="subject" value="<%=subject%>" ></td>
    <td width="6%" align="right">承办人:</td>
    <td width="12%"><input type="text" name="others" value="<%=others%>" ></td>
    <td width="8%" align="right">转发日期从:</td>
    <td width="12%"><input type="text" name="fromDate" id="fromDate" value="<%=fromDateSearch%>" onclick="gfPop.fStartPop(fromDate, toDate)"></td>
    <td width="4%" align="right">到:</td>
    <td width="12%"><input type="text" name="toDate" id="toDate" value="<%=toDateSearch%>" onclick="gfPop.fEndPop(fromDate, toDate)"></td>
    <td colspan="3" width="40%"></td>
    <td width="10%"><input name="Search" type="button" class="but4" id="button" value="查询" onClick="do_search()"/></td>
</tr>
</table>
</div>
<table height=4 bgcolor=#EFEFEF bordercolorlight=#808080 bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#808080 bordercolordark=#808080></td></tr></table>
<table height=22 width=100% bgcolor=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr>
<td bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>
<table bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0>
<tr bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>
<td nowrap>
<table height=22 border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF valign=middle><img alt="toolbar1" src="/images/toolbar1.gif" width=3 height=20></td></tr>
</table>
</td><td nowrap  align=absmiddle>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0
onMouseOver="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';"
onMouseOut="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';"
onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'"
onMouseUp="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'"
onClick="doCollapseAll()"
STYLE='cursor:pointer'>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>
<img src="/images/act_collapse.gif" width=16 height=16 alt="折叠" align=absmiddle> <font color=#000000 title="折叠">折叠</font> </td></tr>
</table>
</td>
<td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt="toolbar2" src="/images/toolbar2.gif" width=2 height=22></td></tr>
</table>
</td><td nowrap  align=absmiddle>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0
onMouseOver="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';"
onMouseOut="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';"
onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'"
onMouseUp="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'"
onClick="doExpandAll() "
STYLE='cursor:pointer'>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>
<img src="/images/act_expand.gif" width=16 height=16 alt="展开" align=absmiddle> <font color=#000000 title="展开">展开</font> </td></tr>
</table>
</td>
<td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt="toolbar2" src="/images/toolbar2.gif" width=2 height=22></td></tr>
</table>
</td><td nowrap  align=absmiddle>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0
onMouseOver="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';"
onMouseOut="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';"
onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'"
onMouseUp="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'"
onClick="doReload()"
STYLE='cursor:pointer'>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>
<img src="/images/act_refresh.gif" width=16 height=16 alt="刷新" align=absmiddle> <font color=#000000 title="刷新">刷新</font> </td></tr>
</table>
</td>
<!--td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt="toolbar2" src="/images/toolbar2.gif" width=2 height=22></td></tr>
</table>
</td>
<td nowrap  align=absmiddle>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0
onMouseOver="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';"
onMouseOut="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';"
onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'"
onMouseUp="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'"
onClick="doSuspend()"
STYLE='cursor:pointer'>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>
<img src="/images/buttonbar/suspend.gif" width=16 height=16 alt="暂停" align=absmiddle> <font color=#000000 title="暂停">暂停</font> </td></tr>
</table>
</td-->
<td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt="toolbar2" src="/images/toolbar2.gif" width=2 height=22></td></tr>
</table>
</td>
<td nowrap  align=absmiddle>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0
onMouseOver="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';"
onMouseOut="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';"
onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'"
onMouseUp="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'"
onClick="doShowSearch()"
STYLE='cursor:pointer'>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>
<img src="/images/buttonbar/act_search.gif" width=16 height=16 alt="查询" align=absmiddle> <font color=#000000 title="查询">查询</font> </td></tr>
</table>
</td>
<td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt="toolbar2" src="/images/toolbar2.gif" width=2 height=22></td></tr>
</table>
</td>
 <table height=5 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF></td></tr></table>
</tr></table></td></tr></table>
<input type="hidden" id="sortType" value="<%=sortType%>">
<input type="hidden" id="sortTypeStr" value="<%=sortTypeStr%>">

<SCRIPT LANGUAGE=javascript>
<!--
function doReload()
{
	document.location.href=document.location;
//    parent.contents.location.href = parent.contents.location;
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

function do_search()
{
    var sortType = document.getElementById("sortType").value;
    if(sortType == "a1") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_APPL_COLUMN_1";
    } else if(sortType == "d1") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_APPL_COLUMN_1 DESC";
    } else if(sortType == "a2") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_APPL_COLUMN_2";
    } else if(sortType == "d2") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_APPL_COLUMN_2 DESC";
    } else if(sortType == "a3") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_COMPOSE_USER";
    } else if(sortType == "d3") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_COMPOSE_USER DESC";
    } else if(sortType == "a4") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_FROM_TASK_USER";
    } else if(sortType == "d4") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_FROM_TASK_USER DESC";
    } else if(sortType == "a5") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_FROM_DATE";
    } else if(sortType == "d5") {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_FROM_DATE DESC";
    } else {
        document.getElementById("sortTypeStr").value = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_FROM_DATE DESC";
    }

    var url = "/servlet/com.sino.sinoflow.servlet.PendingTray3?keyword='" +
              document.getElementById("keyword").value + "'&subject='" +
              document.getElementById("subject").value + "'&createby='" +
              document.getElementById("others").value +
              "'&fromDate='" + document.getElementById("fromDate").value +
              "'&toDate='" + document.getElementById("toDate").value +
              "'&sortType='" + document.getElementById("sortType").value +
              "'&sortTypeStr='" + document.getElementById("sortTypeStr").value + "'";
    window.open(url, 'main');
}
  </script>
<script language="JavaScript" src="/WebLibary/js/dynlayer.js">
</script>
<STYLE TYPE="text/css">
<!--
#menuDiv {position:absolute; left:0; top:document.body.scrollHeight;  width:document.body.clientWidth; height:document.body.clientHeight-document.body.scrollHeight;
				clip:rect(0,3000,3000,0); layer-background-color:white; background-color:white; overflow:auto;}
-->
</STYLE>
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
<DIV id=menuDiv>
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
<table id="dataTable" width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF
border=0 cellpadding=1 cellspacing=0  STYLE='cursor:pointer;table-layout:fixed;'>
<THEAD>
<tr>
<th nowrap width=4% bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
 &nbsp;
</th>
<th nowrap width=3% bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
 &nbsp;
</th>
<th width=20px nowrap onClick="ReverseCheckListEx()" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
<img alt="t_check" src="/images/t_check.gif" width=12 height=12>
</th>
<th nowrap width=24% align="LEFT" onclick="sort(1)" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
<span class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></span><font style="font-weight:normal"> 工单号</font>
</th>
<th nowrap width=22% align="LEFT" onclick="sort(2)" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
<span class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></span><font style="font-weight:normal"> 主题</font>
</th>
<th nowrap width=15% align="LEFT" onclick="sort(3)" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
<span class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></span><font style="font-weight:normal"> 承办人</font>
</th>
<th nowrap width=15% align="LEFT" onclick="sort(4)" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
<span class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></span><font style="font-weight:normal"> 上一办理人</font>
</th>
<th nowrap width=21% align="LEFT" onclick="sort(5)" bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap>
<span class="resizeDivClass" onmousedown="MouseDownToResize(this);" onmousemove="MouseMoveToResize(this);" onmouseup="MouseUpToResize(this);"></span><font style="font-weight:normal"> 转发时间</font>
</th>
<!--th nowrap width=16%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 预定时间</td></tr>
</table>
</th>
<th nowrap width=100%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>&nbsp;</td></tr>
</table>
</th-->
</THEAD>
<script language="javascript">
    function OpenApplication(actid, caseId, mask, newWindow) {
        var caselink = "/servlet/com.sino.sinoflow.servlet.ProcessCase?sf_actID='"+
            actid + "'&sf_appMask="+mask+"&sf_caseID="+caseId+"&signAct=0";
        if(newWindow) {
            h = window.screen.height;
            w = window.screen.width;
            f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,resizable=yes";
            window.open(caselink,"", f1);
        } else {
            window.location = caselink;
        }
    }

    function OpenCopy(copyId, mask, newWindow) {
        var caselink = "/servlet/com.sino.sinoflow.servlet.ProcessCopy?sf_copyID='"+
            copyId + "'&sf_appMask="+mask+"&signAct=0";
        if(newWindow) {
            h = window.screen.height;
            w = window.screen.width;
            f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,resizable=yes";
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
        String ctidName = null, ctidSubName = null;
        for (i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
//            String procName = (String)row.getValue("SFACT_PROC_NAME");
            String procName = (String)row.getStrValue("CATEGORY_NAME");
            String flowDesc = (String)row.getValue("SFACT_APPL_MSG");
            String signDate = (String)row.getValue("SFACT_SIGN_DATE");
            String signDueDate = (String)row.getValue("SFACT_SIGN_DUE_DATE");
//            String sortColumn1 = row.getStrValue("SFACT_SORT_COLUMN_1");
//            String sortColumn2 = row.getStrValue("SFACT_SORT_COLUMN_2");
            String applColumn1 = row.getStrValue("SFACT_APPL_COLUMN_1");
            String applColumn2 = row.getStrValue("SFACT_APPL_COLUMN_2");
 //           String applColumn3 = row.getStrValue("SFACT_APPL_COLUMN_3");
            String composeUser = row.getStrValue("SFACT_COMPOSE_USER");
            String fromUser = row.getStrValue("SFACT_FROM_TASK_USER");
            String fromDate = row.getStrValue("SFACT_FROM_DATE");
//            String procId = (String)row.getValue("SFACT_PROC_ID");
            String actId = row.getStrValue("SFACT_ACT_ID");
            String caseId = row.getStrValue("SFACT_CASE_ID");
            if(row.getStrValue("SFACT_DOC_TYPE").equals("98")) {
                int index = caseId.indexOf(":");
                if(index >= 0)
                    caseId = caseId.substring(index + 1);
            }
            String bitMask = row.getStrValue("ALLOW_OPERATION");
            String dPriority = row.getStrValue("SFACT_DELIVERY_PRIORITY");
            int bMask = 0;
            if(!bitMask.equals(""))
                bMask = Integer.parseInt(bitMask);

            if((row.getStrValue("FINISH_MESSAGE")).equals("1")) {
                bMask += ButtonMask.FINISHMESSAGE_MASK;
            }
            bitMask = String.valueOf(bMask);
            boolean isNewWindow = (row.getStrValue("WINDOW_TYPE")).equals("1");
            boolean newProc = false;
            if (!procName.equals(procedureName)) {
                j++;
                k = 1;
                tridName="tr_"+ j;
                btidName="bt_"+ j;
                ctidName="ct_" + j;
                procedureName = procName;
                newProc = true;
%>

<tr id='<%=tridName%>' height=18 bgcolor=white>
<td id='<%=ctidName%>' colspan="6" ><img id='<%=btidName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/images/expand.gif" onclick="doOutlineAll(this)">&nbsp;<font color="blue" onclick="doOutline(this.parentNode.children[0])"><%=procName%></font></td><td bordercolordark=white bordercolorlight=white nowrap></td>
</tr>
<%
    }
    String subName;
    if(!flowDesc.equals(flowDescription) || newProc) {
        newProc = false;
        l = 1;
        subIdName = tridName + "_" + k;
        btidSubName = btidName + "_" + k;
        ctidSubName = ctidName + "_" + k;
        k++;
%>
<tr height=18 bgcolor=white id="<%=subIdName%>">
<td></td>
<td id='<%=ctidSubName%>' colspan="5" ><img id='<%=btidSubName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/images/expand.gif" onclick="doOutline(this)">&nbsp;<font color="blue" onclick="doOutline(this.parentNode.children[0])"><%=flowDesc%></font></td><td bordercolordark=white bordercolorlight=white nowrap></td>
</tr>
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
<tr id='<%=subName%>'height=18 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>
<td bordercolordark=white bordercolorlight=white nowrap>
</td>
<td bordercolordark=white bordercolorlight=white nowrap>
</td>
<%
    if(row.getStrValue("SFACT_DOC_TYPE").equals("98")) {
%>
<td></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenCopy('<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="priority" src="<%=priorityGif%>"> <%=applColumn1%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenCopy('<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=applColumn2%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenCopy('<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=composeUser%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenCopy('<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=fromUser%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenCopy('<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=fromDate%></td></tr></table></font></td>
<!--td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="red"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenCopy('<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=signDueDate%></td></tr></table></font></td-->
<%
    } else {
%>
<td><input type=checkbox name="msgid" STYLE='cursor:pointer' value="<%=actId%>"></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="priority" src="<%=priorityGif%>"> <%=applColumn1%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=applColumn2%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=composeUser%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=fromUser%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=fromDate%></td></tr></table></font></td>
<!--td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="red"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="expire" src="<%=timeGif%>"> <%=signDueDate%></td></tr></table></font></td-->
<%
    }
%>
<td bordercolordark=white bordercolorlight=white nowrap></td>
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
           if((element.substr(0,trid.length+1)==trid+'_')&& element.length>trid.length)
               eval("document.all."+element+".style.display=''");
           if(element.substr(0,btid.length)==btid)
               eval("document.all."+"bt"+element.substr(2,element.length)+".src='/images/expand.gif'");
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
           if((element.substr(0,trid.length+1)==trid+'_')&& element.length>trid.length)
               eval("document.all."+element+".style.display='none'");
           if(element.substr(0,btid.length)==btid)
               eval("document.all."+"bt"+element.substr(2,element.length)+".src='/images/collapse.gif'");
       }
   }
}

function doOutlineAll(obj)
{
    var bExpanded;
    var objid=obj.id;
    var trid="tr"+objid.substring(2,objid.length);
    var tridlen=trid.length;
    var objidlen=objid.length;

    var imgsrc=obj.src;
    var img=imgsrc.substring(imgsrc.lastIndexOf("/")+1,imgsrc.length);
    var element;

    bExpanded = (img.toLowerCase() == ("expand.gif"));

    for(element in document.all)
    {
        if(element.substr(0,objidlen)==objid)
        {
           if(bExpanded) {
                   eval("document.all."+element+".src='/images/collapse.gif'");
           } else {
               if(element.substr(0) == objid)
                   eval("document.all."+element+".src='/images/expand.gif'");
               else
                   eval("document.all."+element+".src='/images/collapse.gif'");

           }
        }
        if((element.substr(0,tridlen + 1)==trid + "_")&&(element.length!=tridlen))
        {
            if(bExpanded) {
                eval("document.all."+element+".style.display='none'");
            } else {
                if(element.substr(tridlen + 1).indexOf("_") >= 0)
                    eval("document.all."+element+".style.display='none'");
                else {
                    eval("document.all."+element+".style.display=''");
                }
            }
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

    bExpanded = (img.toLowerCase() == ("expand.gif"));

    for(element in document.all)
    {
        if(element.substr(0)==objid)
        {
           if(bExpanded) {
               eval("document.all."+element+".src='/images/collapse.gif'");
           } else {
                eval("document.all."+element+".src='/images/expand.gif'");
           }
        }
        if((element.substr(0,tridlen + 1)==trid + "_")&&(element.length!=tridlen))
        {
            if(bExpanded) {
                eval("document.all."+element+".style.display='none'");
            } else {
                eval("document.all."+element+".style.display=''");
            }
        }
    }
}

function doCount() {
    var tr = 'tr_';
    var ct = "ct_";
    var i = 1;
    count = 0;
    while(document.getElementById(tr+i)) {
        j = 1;
        var tri = tr + i;
        var cti = ct + i;
        while(document.getElementById(tri+"_"+j)) {
            k = 1;
            var trij = tri + "_" + j;
            var ctij = cti + "_" + j;
            while(document.getElementById(trij + "_" + k)) {
                k++;
                count++;
            }
            k--;
            document.getElementById(ctij).innerHTML += "<font color='navy'>&nbsp;&nbsp;(" + k + ")</font>" ;
            j++
        }
        document.getElementById(cti).innerHTML += "<font color='red'>&nbsp;&nbsp;(" + count + ")</font>";
        count = 0;
        i++;
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

function batchSuspendActs(actids, msg) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.BatchSuspendActs?sf_actIDs="
            +actids+"&msg='" + msg + "'";
    var popscript;
    popscript = "dialogWidth:200px"
            + ";dialogHeight:120px"
            + ";center:yes;status:no;scroll:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function doSuspend() {
    var arr=GetSelectedKey();

    if(arr==null||arr==""||arr.length==0)
    {
        alert("请选准暫停的案件!");
        return;
    }
        if(!confirm('确定要暫停吗?'))
         return;

    var ids = ""
    for(i=0;i<arr.length;i++)
    {
        if(ids != "") {
            ids += ";" + arr[i];
        } else {
            ids = arr[i];
        }
    }

    var desc = getSuspendReason()
    if(desc == "") {
        return;
    }
    batchSuspendActs(ids, desc);
    location.reload(true);
}

function getSuspendReason(){
    var styleStr = "dialogWidth:425px;dialogHeight:330px;center:yes;status:no;scroll:no;";
    return window.showModalDialog(
            "/flow/suspendReason.jsp",null,styleStr);
}

function doShowSearch() {
    var searchDiv = document.getElementById("query");
    if(searchDiv.style.visibility == "hidden") {
        searchDiv.style.visibility = "visible";
        searchDiv.style.display = "block";
    } else {
        searchDiv.style.visibility = "hidden";
        searchDiv.style.display = "none";
    }
}
   </script>

</DIV>

<SCRIPT LANGUAGE="JavaScript">
<!--
init();

var dragSize = false;
function sort(index)
{
    if(dragSize) {
        dragSize = false;
        return;
    }
    var sortIndex = document.getElementById("sortType").value;
    switch(index) {
        case 1:
            if(sortIndex == "a1") {
                document.getElementById("sortType").value = "d1";
            } else if(sortIndex = "d1") {
                document.getElementById("sortType").value = "a1";
            } else {
                document.getElementById("sortType").value = "d1";
            }
            break;
        case 2:
            if(sortIndex == "a2") {
                document.getElementById("sortType").value = "d2";
            } else if(sortIndex = "d2") {
                document.getElementById("sortType").value = "a2";
            } else {
                document.getElementById("sortType").value = "d2";
            }
            break;
        case 3:
            if(sortIndex == "a3") {
                document.getElementById("sortType").value = "d3";
            } else if(sortIndex = "d3") {
                document.getElementById("sortType").value = "a3";
            } else {
                document.getElementById("sortType").value = "d3";
            }
            break;
        case 4:
            if(sortIndex == "a4") {
                document.getElementById("sortType").value = "d4";
            } else if(sortIndex = "d4") {
                document.getElementById("sortType").value = "a4";
            } else {
                document.getElementById("sortType").value = "d4";
            }
            break;
        case 5:
            if(sortIndex == "a5") {
                document.getElementById("sortType").value = "d5";
            } else if(sortIndex = "d5") {
                document.getElementById("sortType").value = "a5";
            } else {
                document.getElementById("sortType").value = "d5";
            }
            break;
        default:
            document.getElementById("sortType").value = "";
            break;
    }
    do_search();
}

function MouseDownToResize(obj){
dragSize = true;
setTableLayoutToFixed("dataTable");
obj.mouseDownX=event.clientX;
obj.pareneTdW=obj.parentElement.offsetWidth;
var objTable = document.getElementById("dataTable");
if(objTable) {
    obj.pareneTableW=objTable.offsetWidth;
}
obj.setCapture();
}
function MouseMoveToResize(obj){
    if(!obj.mouseDownX) return false;
    var newWidth=obj.pareneTdW*1+event.clientX*1-obj.mouseDownX;
    if(newWidth>10)
    {
obj.parentElement.style.width = newWidth;
var objTable = document.getElementById("dataTable")
if(objTable) {
	objTable.style.width=newWidth;
    objTable.cells[obj.parentElement.cellIndex].style.width = newWidth;
}
}
}
function MouseUpToResize(obj){
obj.releaseCapture();
obj.mouseDownX=0;
}
function setTableLayoutToFixed(tableName)
{
 var theObjTable = document.getElementById(tableName);
 if(!theObjTable) return;
// if(theObjTable.style && theObjTable.style.tableLayout=='fixed') return;
   var headerTr=theObjTable.rows[0];
    for(var i=0;i<headerTr.cells.length;i++)
    {
    headerTr.cells[i].styleOffsetWidth=headerTr.cells[i].offsetWidth;
    }

    for(var i=0;i<headerTr.cells.length;i++)
    {
    headerTr.cells[i].style.width=headerTr.cells[i].styleOffsetWidth;
    }
    theObjTable.style.tableLayout='fixed';
}

//-->
</SCRIPT>
<input type=hidden name="mod" value="view">
<input type=hidden name="fnc" value="openappbyprof">
<input type=hidden name="unid" value="21E9F7BB7C12D9AC48256C1800265FE5">
<input type=hidden name="start" value="4.1">
<input type=hidden name="prepos" value="4.1">
<input type=hidden name="nextpos" value="4.1">
<input type=hidden name="count" value=30>
<input type=hidden name="appname" value="待办事宜">
<input type=hidden name="view" value="InTray">
<input type=hidden name="flagname" value=''>
<input type=hidden name="flagvalue" value=''>
<input type=hidden name="topublish" value=''>

</form>
</BODY>
</HTML>
<iframe width=174 height=189 name="gToday:normal:calendar.js"
    id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm"
    scrolling="no" frameborder="0"
    style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;">
</iframe>
