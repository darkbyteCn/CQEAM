<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.sinoflow.constant.ButtonMask" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<TITLE>收件箱</TITLE><STYLE TYPE=text/css>
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
<BODY topmargin=0 rightMargin=0 leftmargin=0 bottommargin=0 scroll=no bgcolor="white" onload="init()" onresize="window_onresize()">
<form name="listform" action="/servlet/com.sino.sinoflow.servlet.InTray" method="POST">
<table height=20 width=100% bgcolor=#0073BF border=0 cellpadding=0 cellspacing=0>
<tr>
<%
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.SF_ACT_INFO_DTO);
%>
<td nowrap align=left valign=bottom >&nbsp;<font  style="font-size: 10pt" color=#FFFFF><b> 个人工作台>>收件箱</b></font><font  style="font-size: 8pt" color=#FFFFF><b>(共<%=rows.getSize()%>个)</b></font></td>

<td nowrap align=right valign=bottom ></td>

<td nowrap align=left valign=middle width=20><img alt='bull' src='/images/bull.gif' width=16 height=16></td>
</tr>
</table>
<table height=4 bgcolor=#EFEFEF bordercolorlight=#808080 bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#808080 bordercolordark=#808080></td></tr></table>
<table height=22 width=100% bgcolor=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr>
<td bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>
<table bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0>
<tr bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>
<td nowrap>
<table height=22 border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF valign=middle><img alt='toolbar1' src=/images/toolbar1.gif width=3 height=20></td></tr>
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
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt='toolbar2' src=/images/toolbar2.gif width=2 height=22></td></tr>
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
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt='toolbar2' src=/images/toolbar2.gif width=2 height=22></td></tr>
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
<td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt='toolbar2' src=/images/toolbar2.gif width=2 height=22></td></tr>
</table>
</td><td nowrap  align=absmiddle>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=1 cellpadding=0 cellspacing=0
onMouseOver="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF';"
onMouseOut="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF';"
onMouseDown="this.borderColorDark='#FFFFFF';this.borderColorLight='#808080'"
onMouseUp="this.borderColorDark='#EFEFEF';this.borderColorLight='#EFEFEF'"
onClick="doActSignActs()"
STYLE='cursor:pointer'>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle>
<img alt='action_attach' src="/images/action_attach.gif" width=16 height=16 align=absmiddle> <font color=#000000>批签收</font> </td></tr>
</table>
</td>
<td nowrap>
<table height=22 bordercolorlight=#EFEFEF bordercolordark=#EFEFEF border=0 cellpadding=0 cellspacing=0>
<tr><td nowrap align=center bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF valign=middle><img alt='toolbar2' src=/images/toolbar2.gif width=2 height=22></td></tr>
</table>
</td><table height=5 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF></td></tr></table>
<!--</td>--></tr></table></td></tr></table>
<SCRIPT LANGUAGE=javascript>
<!--
function doReload()
{
	document.location.href=document.location;

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
	iReadWindow = (eval(parent) && eval(parent.document) && eval(parent.document.body) && eval(top.topframe)) ? true : false;
	if (iReadWindow) {
		iListH = document.body.clientHeight;
		iListReadH = parent.document.body.clientHeight;
		iHRatio = (iListH)*100/(iListReadH);
	}
}
function init() {
menulayer = new DynLayer("menuDiv")
menulayer.slideInit()
iInit = true;
movemenulayer();
}
function movemenulayer() {
	if (iInit == false)
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
	for(i=0; i<msgids.length; i++)
	{
      	if((msgids[i] != null)&&(msgids[i].name=="msgid"))
			msgids[i].status = !msgids[i].status;
	}
	
}
</script>	
<table height=20 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF 
border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>
<THEAD>
<tr>
<th nowrap width=4%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> &nbsp;</td></tr>
</table>
</th>
<th nowrap width=3%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> &nbsp;</td></tr>
</table>
</th>
<th nowrap>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100%  onMouseDown="his.borderColorDark='#FFFFFF';this.borderColorLight='#505050'"
 onMouseOut="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF'"
 onMouseUp="this.borderColorDark='#808080';this.borderColorLight='#FFFFFF'"
 onClick="ReverseCheckListEx()" STYLE='cursor:pointer'>
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align=center> <img alt='t_check' src="/images/t_check.gif" width=12 height=12></td></tr>
</table>
</th>
<!--th nowrap width=3%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> &nbsp;</td></tr>
</table>
</th-->
<th nowrap width=18%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 关键字</td></tr>
</table>
</th>
<th nowrap width=30%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 主题</td></tr>
</table>
</th>
<th nowrap width=12%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 发件人</td></tr>
</table>
</th>
<th nowrap width=26%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="LEFT"> 转发时间</td></tr>
</table>
</th>
<th nowrap width=100%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>&nbsp;</td></tr>
</table>
</th>
</tr>
</THEAD>
<script language="javascript">
    function OpenApplication(actid, caseId, mask, newWindow) {
        var caselink = "/servlet/com.sino.sinoflow.servlet.ProcessCase?sf_actID='"+
            actid + "'&sf_appMask="+mask + "&sf_caseID=" + caseId + "&signAct=1";
        if(newWindow) {
            h = window.screen.height;
            w = window.screen.width;
            f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scroll=yes,resizable=yes";
            window.open(caselink,"", f1);
        } else {
            window.location = caselink;
        }
    }
</script>

<%
    int i, j = 0, k = 1, l = 1;
    if (rows != null && rows.getSize() > 0) {
        Row row;
        String tridName = null, subIdName = null, btidName = null, btidSubName = null, procedureName = null, flowDescription = null;
        for (i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
            String procName = row.getStrValue("SFACT_PROC_NAME");
            String flowDesc;
            if((row.getStrValue("SFACT_COMMENT_QTY").equals("0") || row.getStrValue("SFACT_COMMENT_QTY").equals(""))
                    || (Integer.parseInt(row.getStrValue("SFACT_COMMENT_TYPE")) & 0x20) == 0)
                flowDesc = row.getStrValue("SFACT_APPL_MSG");
            else
                flowDesc = row.getStrValue("SFACT_COMMENT_APPL_MSG");
//            String sortColumn1 = row.getStrValue("SFACT_SORT_COLUMN_1");
//            String sortColumn2 = row.getStrValue("SFACT_SORT_COLUMN_2");
            String applColumn1 = row.getStrValue("SFACT_APPL_COLUMN_1");
            String applColumn2 = row.getStrValue("SFACT_APPL_COLUMN_2");
            String taskUsers = row.getStrValue("SFACT_FROM_TASK_USER");
            int userIndex = taskUsers.indexOf(";");
            if(userIndex >= 0)
                taskUsers = taskUsers.substring(0, userIndex);
            String fromDate = row.getStrValue("SFACT_FROM_DATE");
            String procId = row.getStrValue("SFACT_PROC_ID");
            String actId = row.getStrValue("SFACT_ACT_ID");
            String caseId = row.getStrValue("SFACT_CASE_ID");
            String bitMask = row.getStrValue("ALLOW_OPERATION");
            String dPriority = (String)row.getStrValue("SFACT_DELIVERY_PRIORITY");
            int bMask = 0;
            if(!bitMask.equals(""))
                bMask = Integer.parseInt(bitMask);

            if(((String)row.getValue("FINISH_MESSAGE")).equals("1")) {
                bMask += ButtonMask.FINISHMESSAGE_MASK;
            }
            bitMask = String.valueOf(bMask);
            boolean isNewWindow = ((String)row.getValue("WINDOW_TYPE")).equals("1");
            boolean isNewProc = false;
            if (!procName.equals(procedureName)) {
                j++;
                k = 1;
                tridName="tr_"+ j;
                btidName="bt_"+ j;
                procedureName = procName;
                isNewProc = true;
%>

<tr id='<%=tridName%>' height=18 bgcolor=white>
<td colspan="7" ><img id='<%=btidName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/images/expand.gif" onclick="doOutline(this)">&nbsp;<font color="blue"><%=procName%></font></td><td bordercolordark=white bordercolorlight=white nowrap></td>
</tr>
<%
    }
    String subName;
    if(!flowDesc.equals(flowDescription) || isNewProc) {
        isNewProc = false;
        l = 1;
        subIdName = tridName + "_" + k;
        btidSubName = btidName + "_" + k;
        k++;
%>
<tr height=18 bgcolor=white id="<%=subIdName%>">
<td></td>
<td colspan="6" ><img id='<%=btidSubName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/images/expand.gif" onclick="doOutline(this)">&nbsp;<font color="blue"><%=flowDesc%></font></td><td bordercolordark=white bordercolorlight=white nowrap></td>
</tr>
<%
        l = 1;
        flowDescription = flowDesc;
    } else {
        l++;
    }
    subName = subIdName + "_" + l;
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
<td><input type=checkbox name="msgid" STYLE='cursor:pointer' value="<%=actId%>"></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><img border="0" alt="priority" src="<%=priorityGif%>"> <%=applColumn1%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=applColumn2%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=taskUsers%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="OpenApplication('<%=actId%>','<%=caseId%>','<%=bitMask%>',<%=isNewWindow%>)"><%=fromDate%></td></tr></table></font></td>
<td bordercolordark=white bordercolorlight=white nowrap></td>
<%
    } }
%>
</table>
<script language=javascript>
var outcount=<%=j%>;
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
           if((element.substr(0,4)==trid)&& element.length>4)
               eval("document.all."+element+".style.display='none'");
           if(element.substr(0,4)==btid)
               eval("document.all."+"bt"+element.substr(2,element.length)+".src='/images/collapse.gif'");
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
        if((element.substr(0,objidlen)==objid))
        {
           if(bExpanded)
               eval("document.all."+element+".src='/images/collapse.gif'");
           else
                eval("document.all."+element+".src='/images/expand.gif'");
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

function batchSignActs(actids) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.BatchSignActs?sf_actIDs="
            +actids;
    var popscript;
    popscript = "dialogWidth:200px"
            + ";dialogHeight:100px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function doActSignActs()
{
	var arr=GetSelectedKey();

	if(arr==null||arr==""||arr.length==0)
	{
		alert("请选准条目后签收!");
		return;
	}
        if(!confirm('确定要批签收吗?'))
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

    batchSignActs(ids);
    location.reload(true);

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
<input type=hidden name="unid" value="A36C404F47FE888848256C1800265FE9">
<input type=hidden name="start" value="2.1">
<input type=hidden name="prepos" value="2.1">
<input type=hidden name="nextpos" value="2.1">
<input type=hidden name="count" value=30>
<input type=hidden name="appname" value="收件箱">
<input type=hidden name="view" value="Intray">
<input type=hidden name="flagname" value=''>
<input type=hidden name="flagvalue" value=''>
<input type=hidden name="topublish" value=''>
<script language=javascript>
if(eval(window.parent.sinoapi)!=null)
{ 	unid=document.all.unid.value;
 	start=document.all.start.value;
	view=document.all.view.value;
	fnc=document.all.fnc.value;
 	url="/servlet/SinoFlow?mod=view&fnc="+fnc+"&unid="+unid+"&start="+start+"&view="+view;
	window.parent.sinoapi.document.all.boxTarget.value=url;
}
</script>
</form>
</BODY>
</HTML>
