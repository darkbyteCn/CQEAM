<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.sinoflow.constant.ButtonMask" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<HTML>
<HEAD>
<STYLE TYPE=text/css>
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
<form name="listform" action="/servlet/com.sino.sinoflow.servlet.CaseList" method="POST">
<table height=20 width=100% bgcolor=#0073BF border=0 cellpadding=0 cellspacing=0>
<tr>
<td nowrap align=left valign=bottom >&nbsp;<font  style="font-size: 10pt" color=#FFFFF><b> 任务列表</b></font></td>

<td nowrap align=right valign=bottom ></td>

<td nowrap align=left valign=middle width=20><img alt="bull" src="/images/bull.gif" width=16 height=16></td>
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
<img alt="act_collapse" src="/images/act_collapse.gif" width=16 height=16 align=absmiddle> <font color=#000000>全部折叠</font> </td></tr>
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
<img alt="act_expand" src="/images/act_expand.gif" width=16 height=16 align=absmiddle> <font color=#000000>全部展开</font> </td></tr>
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
<img alt="act_refresh" src="/images/act_refresh.gif" width=16 height=16 align=absmiddle> <font color=#000000>刷新</font> </td></tr>
</table>
</td>
<table height=5 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF></td></tr></table>
</tr></table></td></tr></table>
<script language="javascript">function doReload() {
 	document.location.reload(true);
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
function init() {
menulayer = new DynLayer("menuDiv")
menulayer.slideInit()
iInit = true;
movemenulayer()
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
<table height=20 width=100% bgcolor=white bordercolorlight=#FFFFFF bordercolordark=#EFEFEF
border=0 cellpadding=0 cellspacing=0  STYLE='cursor:pointer'>
<THEAD>
<tr>
<th nowrap colspan=2>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="Left"> 任务名称</td></tr>
</table>
</th>
<th nowrap width=20%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080
border=1 cellpadding=0 cellspacing=0 width=100% >
<tr><td bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF nowrap align="Left"> 操作</td></tr>
</table>
</th>
<th nowrap width=100%>
<table height=20 bgcolor=#EFEFEF bordercolorlight=#FFFFFF bordercolordark=#808080 border=1 cellpadding=0 cellspacing=0 width=100%>
<tr><td nowrap bgcolor=#EFEFEF bordercolorlight=#EFEFEF bordercolordark=#EFEFEF>&nbsp;</td></tr>
</table>
</th>
</tr>
</THEAD>
    <%
        RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.SF_APPLICATION_DTO);
        int i, j = 0, k = 1;
        if (rows != null && rows.getSize() > 0) {
            Row row;
            String tridName = null, btidName, subIdName, categoryName = null;
            for (i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                String cateName = (String)row.getValue("CATEGORY_NAME");
                boolean isFlow = ((String)row.getValue("IS_FLOW_PROCESS")).toUpperCase().equals("Y");
                String projName = (String)row.getValue("PROJECT_NAME");
                String procName = (String)row.getValue("PROCEDURE_NAME");
                String appId = (String)row.getValue("APP_ID");
                String procLink = (String)row.getValue("URL");

                String bitMask = (String)row.getValue("ALLOW_OPERATION");
                int bMask = 0;
                if(!bitMask.equals(""))
                    bMask = Integer.parseInt(bitMask);
                bitMask = String.valueOf(bMask);

                boolean isFlowProcess = ((String)row.getValue("IS_FLOW_PROCESS")).toUpperCase().equals("Y");
                boolean isNewWindow = ((String)row.getValue("WINDOW_TYPE")).equals("1");
 //               String viewLink = "/servlet/com.sino.sinoflow.servlet.ViewProcess?projectName=\"" +
 //                   projName + "\"&procedureName=\"" +
 //                   procName + "\"";
                if (!cateName.equals(categoryName)) {
                    
                    j++;
                    tridName="tr_"+ j;
                    btidName="bt_"+ j;
                    k = 1;
                    categoryName = cateName;
    %>

    <tr id='<%=tridName%>'>
    <td width="100%" colspan="4" >
    <img alt="expand" border="0" src="/images/expand.gif" id="<%=btidName%>" onclick="doOutline(this)"><%=cateName%></td>
    </tr>
    <%
        } else {
            k++;
        }
        subIdName = tridName + "_" + k;
    %>
    <tr height=18 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"' id='<%=subIdName%>'>
    <td bordercolordark=white bordercolorlight=white nowrap>
    &nbsp;&nbsp;</td>
    <td bordercolordark=white bordercolorlight=white nowrap onClick='openCaseWindow("<%=appId%>", "<%=projName%>","<%=procName%>","<%=procLink%>","<%=bitMask%>",<%=isNewWindow%>,<%=isFlowProcess%>)' STYLE='cursor:pointer'>
    &nbsp;&nbsp;&nbsp;<%=row.getValue("APP_NAME")%>&nbsp;&nbsp;</td>
     <%
        if(isFlow) {
    %>
    <td bordercolordark=white bordercolorlight=white nowrap onClick='openViewWindow("<%=projName%>", "<%=procName%>")' STYLE='cursor:pointer'>
    [查看流程图]&nbsp;</td>
    <%
        }
    %>
    <td bordercolordark=white bordercolorlight=white nowrap></td>
    </tr>
    <%
        } }
    %>

</table>
<script language="javascript">
    function openViewWindow(pjName, pcName) {
        var vplink =  "/servlet/com.sino.sinoflow.servlet.ViewProcess?sf_projectName=\"" +
                pjName + "\"&sf_procedureName=\"" +
                pcName + "\"";
//            window.open(vplink,"", "fullscreen,scrollbars");
/*
        h = window.screen.height;
        w = window.screen.width;
        f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,scroll=yes,resizable=yes";
*/
        h = window.screen.height;
        w = window.screen.width;
    //    f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,scroll=yes,resizable=yes";

        var f1 = "dialogWidth:" + w
                + ";dialogHeight:" + h
                + ";center:yes;status:no;scrollbars:no;help:no";

        window.showModalDialog(vplink,"",f1);
    }
            
    function openCaseWindow(appId, pjName, pcName, formUrl, mask, newWindow, flowProcess) {
        var caselink;
        if(flowProcess)
//                caselink = "/servlet/com.sino.sinoflow.servlet.NewCase?sf_appID=\""+
//                    appId+"\"&sf_projectName=\"" +
//                    pjName + "\"&sf_procedureName=\"" +
//                    pcName + "\"&sf_appMask=" +
//                    mask + "&sf_url=" + formUrl;

            caselink = "/servlet/com.sino.sinoflow.servlet.NewCase?sf_appID=\""+
                appId+"\"";
        else
            caselink = formUrl;

        if(newWindow) {
            h = window.screen.height;
            w = window.screen.width;
            f1 = "top=0,left=0,width=" + w + ",height=" + h + ",scrollbars=yes,scroll=yes,resizable=yes";
             window.open(caselink,"", f1);
        } else {
            window.location = caselink;
        }
    }
</script>

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

     var depth=CountSplit(objid,"_");

     bExpanded = (img.toLowerCase() == ("expand.gif"));
 	
 	for(element in document.all)
 	{ 
 		if((element.substr(0,objidlen)==objid))
 		{
 		   if(bExpanded)
 		   { 
 		   		
 		   		if(CountSplit(element,"_")<=depth+1) 		   			
 					eval("document.all."+element+".src='/images/collapse.gif'");
 		   		else
 					eval("document.all."+element+".src='/images/expand.gif'");
 		   }
 		   else
 		   {		
				if(CountSplit(element,"_")<depth+1) 		   			
 					eval("document.all."+element+".src='/images/expand.gif'");
 		   		else
 					eval("document.all."+element+".src='/images/collapse.gif'");
				 		
 		   }
 		}
 		if((element.substr(0,tridlen)==trid)&&(element.length!=tridlen))
 		{
			
 				if(bExpanded)
 				{
 					if(CountSplit(element,"_")<depth+1) 		   			
 						eval("document.all."+element+".style.display=''");
 					else
 						eval("document.all."+element+".style.display='none'"); 					
 				}
 				else
 				{
					if(CountSplit(element,"_")<=depth+1) 		   			
 						eval("document.all."+element+".style.display=''");
 					else
 						eval("document.all."+element+".style.display='none'"); 		
 					
 				}		
 		}
 		
 		 	
 	} 	
 }
 function CountSplit(srcStr,splitStr)
 {
 	var ret=0;
     var n;

     while(srcStr!="")
 	{
 		n=srcStr.indexOf(splitStr);
 		if(n<0)
 			break;
 		ret++;
 		srcStr=srcStr.substr(n+1,srcStr.length);	
 	
 	} 	
 	return ret;
 
 }
</script>

</DIV>



<SCRIPT LANGUAGE="JavaScript">
<!--
init();
//-->
</SCRIPT>
</form>
</BODY>
</HTML>
