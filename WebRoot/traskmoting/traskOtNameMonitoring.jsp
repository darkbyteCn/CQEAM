<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import=" com.sino.traskmoting.dto.SfActInfoDTO"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<html>
	<head>
		<title>流程监控</title>
		<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
		<script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
		<!-- 
		<script language="javascript" src="/WebLibary/js/clientRowSet.js"></script>
		<script language="javascript" src="/WebLibary/js/expendCollapse.js"></script>
		<script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
		 -->
	</head>
	<%  %>
	<body>
    <form name="listform" action="/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet" method="POST">
        <jsp:include
			page="/message/MessageProcess" />
        <script type="text/javascript">
       function printTool(){
            var ArrAction = new Array("展开", "act_expand.gif","doExpandAll","zk_t");
            var ArrAction1 = new Array("晢叠", "act_collapse.gif","doCollapseAll","zd_t");
           var ArrAction4 = new Array("监控项<select style='width:100' id='check'  onchange='getArrActionValue(this)'><option value='0'>--请选择--</option><option value='users'>按用户</option><option value='role'>按角色</option><option value='traskName'>按任务</option><option value='Otrole'>超时/按角色</option><option value='traskOtName'>超时/按任务</option><option value='Otusers'>超时/按用户</option><option value='Enote'>按催办</option></select>","","doNull","sh_t");
           var ArrAction5 = new Array("催办", "../buttonbar/mn_alarm.gif", "doENoteActs", "cb_t");
           var toolBar = new SinoPrintToolBar();
           toolBar.SinoActions = new Array(ArrAction,ArrAction1,ArrAction4,ArrAction5);
            toolBar.imagePath = "../images/buttonbar/";
            toolBar.titleStr = "流程监控";
            toolBar.treeViewTitle = new Array("任务名称","<input type=checkbox name='checkAll' STYLE='cursor:pointer' onclick='ChangeCheckListEx();'>","角色名称","关键字","姓名","签收时间","预定时间","");
            toolBar.treeViewWidth = new Array("3%","2%","15%","15%","15%","15%","20%","2%");
               toolBar.print();
        }
         var getValue='<%=request.getParameter("checkValue")%>';
        window.onload=Intload;
        function Intload()
        {
          var intValue=document.getElementById("check");
          for(var i=0;i<intValue.length;i++)
          {
             if(getValue==intValue[i].value)
             {
                 intValue[i].selected=true;
                 break;
             }
           }
        }
        function getArrActionValue(obj)
        {
            var name =obj.options[obj.selectedIndex].value;
            reqServlet(name);
        }
        printTool();
     </script>
     </form>
        <%
		 int x=0;
           DTOSet ds = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
         %>
		<div
			style="position:absolute; overflow-y: auto; overflow-x: auto; height: 80%; width: 100%; left: 1px; margin-left: 0px"
			align="left">
			<input type="hidden" name="act" value="">
			 	<table  width="100%">
					<%
					    	int i, j = 0, k = 1, l = 1;
						if (ds != null && !ds.isEmpty()) {
							SfActInfoDTO std;
       							String tridName = null, subIdName = null, btidName = null, btidSubName = null, taskName = null, procName1 = null;
      							for (i = 0; i < ds.getSize(); i++) {
      								std = (SfActInfoDTO) ds.getDTO(i);
                                    //String user = std.getSfactPickUser();
                                    String procName = std.getSfactProcName();
                                    String name = std.getSfactTaskName();
                                    //String status = std.getSfactSignStatus();
                                    //String applColumn = std.getSfactApplColumn1();
                                    String appMsg = std.getSfactApplMsg();
                                    boolean isNewStatus = false;
                                    if(!procName.equals(procName1)) {
                                        j++;
                                        k = 1;
                                        tridName="tr_"+ j;
                                        btidName="bt_"+ j;
                                        taskName = name;
                                        isNewStatus = true;
                    %>
                    <tr id="<%=tridName%>">
						<td colspan="7">
							<img id='<%=btidName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/images/expand.gif" onclick="doOutline(this)" />&nbsp;<font color="blue"><%=std.getSfactProcName()%></font></td>
                        <td bordercolordark=white bordercolorlight=white nowrap>
						</td>
					</tr>
                    <%
                        }
                        String subName;
                        if(!name.equals(taskName) || isNewStatus) {
                            isNewStatus = false;
                            l = 1;
                            subIdName = tridName + "_" + k;
                            btidSubName = btidName + "_" + k;
                            k++;
                    %>
                    <tr height=18 bgcolor=white id="<%=subIdName%>">
                    <td width="2%"></td>
                    <td colspan="6" ><img id='<%=btidSubName%>' STYLE='cursor:pointer' border="0" alt="expand" src="/images/expand.gif" onclick="doOutline(this)">&nbsp;<font color="blue"><%=std.getSfactTaskName()%></font></td><td bordercolordark=white bordercolorlight=white nowrap></td>
                    </tr>
                    <%
                            l = 1;
                    		taskName = name;
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
                            signDueDateCal.setTime(dformat.parse(std.getSfactSignDueDate().toString()));
                        } catch (ParseException ex) {
                            signDueDateCal.setTime(now.getTime());
                        }
                        String timeGif = "/images/notExpire.gif";
                        if(now.after(signDueDateCal))
                            timeGif = "/images/expire.gif";
                        String priorityGif = "/images/normal.gif";
                       if(std.getSfactDeliveryPriority().equals("1")) {
                            priorityGif = "/images/normalHigh.gif";
                       } else if(std.getSfactDeliveryPriority().equals("2")) {
                            priorityGif = "/images/urgent.gif";
                       } else if(std.getSfactDeliveryPriority().equals("3")) {
                            priorityGif = "/images/topUrgent.gif";
                        }
                    %>
                    <tr id='<%=subName%>'height=18 bgcolor=white  onMouseOver='this.style.backgroundColor="#DFDFDF"'  onMouseOut='this.style.backgroundColor="white"'>
                    <td width="2%" bordercolordark=white bordercolorlight=white nowrap>
                    </td>
                    <td width="2%" bordercolordark=white bordercolorlight=white nowrap>&nbsp;&nbsp;
                    </td>
                    <td width="2%"><input type=checkbox name="msgid" STYLE='cursor:pointer' value="<%=std.getSfactActId()%>"></td>
                    <td width="15%"bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><img border="0" alt="priority" src="<%=priorityGif%>"> <%=std.getSfactTaskRole()%></td></tr></table></font></td>
                    <td width="15%"bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><img border="0" alt="priority" src="<%=priorityGif%>"> <%=std.getSfactApplColumn1()%></td></tr></table></font></td>
                    <td width="15%"bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td  nowrap  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><img border="0" alt="priority" src="<%=priorityGif%>"> <%=std.getSfactPickUser()%></td></tr></table></font></td>
                    <%
                        if(std.getSfactSignStatus().trim().equals("1")) {
                    %>
                    <td width="15%" bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><%=std.getSfactSignDate()%></td></tr></table></font></td>
                    <td width="20%" bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"><img border="0" alt="expire" src="<%=timeGif%>"><%=std.getSfactSignDueDate()%></td></tr></table></font></td>
                    <%
                        } else {
                    %>
                    <td width="15%" bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;">未签收</td></tr></table></font></td>
                    <td width="20%" bordercolordark=white bordercolorlight=white nowrap align="LEFT"><font color="black"><table border=0 width=100% style='cursor:pointer'><tr><td nowrap  onclick="do_ShowDetail('<%=std.getSfactActId()%>'); return false;"></td></tr></table></font></td>
                    <%
                        }
                    %>
                    <td width="2%" bordercolordark=white bordercolorlight=white nowrap></td></tr>
                    <%
                        } }
                    %>
				</table>
		<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
		</div>
		<%=WebConstant.WAIT_TIP_MSG%>
        </div>
    <!--/form-->
    </body>
</html>

<script language=javascript>
function ChangeCheckListEx()
{
//    var listform=document.listform;
   var msgids = document.all("msgid");
	if(msgids == null)
		return;
    var status = document.getElementById("checkAll").checked;
    if(!eval(msgids.length))
	{
        msgids.status = status;
        return;
	}
	for(var i=0; i<msgids.length; i++)
	{
      	if((msgids[i] != null) && (msgids[i].name == "msgid"))
			msgids[i].status = status;
	}

}

var outcount = <%=j %>;
function doExpandAll()
{
   var btid,trid,i,element;
   for(i=1; i<=outcount; i++)
   {
       btid = "bt_" + i;
       trid = "tr_" + i;
       for(element in document.all)
       {
           if((element.substr(0, 4) == trid) && element.length > 4)
               eval("document.all." + element + ".style.display = ''");
           if(element.substr(0,4) == btid)
               eval("document.all." + "bt" + element.substr(2, element.length) + ".src='/images/expand.gif'"); 
       }
   }
}

function doCollapseAll()
{
    var btid,trid,element,i;
   for(i=1; i<=outcount; i++)
   {
       btid = "bt_" + i;
       trid = "tr_" + i;
       for(element in document.all)
       {
           if((element.substr(0,4) == trid) && element.length > 4)
               eval("document.all." + element + ".style.display='none'"); 
           if(element.substr(0,4) == btid)
               eval("document.all." + "bt" + element.substr(2, element.length) + ".src='/images/collapse.gif'"); 
       }
   }
}
function doOutline(obj)
{
    var bExpanded;
    var objid = obj.id;
    var trid = "tr" + objid.substring(2, objid.length);
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

function batchEnoteActs(actids, msg) {
    var url;
    url = "/servlet/com.sino.sinoflow.servlet.BatchEnoteActs?sf_actIDs="
            +actids + "&msg='" + msg + "'";
    var popscript;
    popscript = "dialogWidth:200px"
            + ";dialogHeight:100px"
            + ";center:yes;status:no;scrollbars:no;help:no";
    return window.showModalDialog(url, null, popscript);
}

function doENoteActs()
{
    var arr=GetSelectedKey();
	if(arr==null||arr==""||arr.length==0)
	{
		alert("请选准条目后签收!");
		return;
	}
        if(!confirm('确定要催办吗?'))
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

    var temp = inputOpinion();
    if(temp == "~~cancel~~")
        return;
    batchEnoteActs(ids, temp);
    location.reload(true);

}

function inputOpinion(){
    var styleStr = "dialogWidth:425px;dialogHeight:330px;center:yes;status:no;scroll:no;";
    return window.showModalDialog(
            "/flow/inputApproveContent.jsp?flowDesc=''",null,styleStr);
}
</script>


<script type="text/javascript" language="JavaScript">
	function do_ShowDetail(otherName){//查询详细
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&actId="+otherName);
	}
	function reqServlet(name)
	{
	    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
		window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet?act=<%=WebActionConstant.QUERY_ACTION%>&checkValue="+name);
	}
	function doReload(){//刷新
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
       	window.location.assign("/servlet/com.sino.traskmoting.servlet.SfActInfoTaskServlet?act=");
   	}
   	
   	function doNull() {};
</script>