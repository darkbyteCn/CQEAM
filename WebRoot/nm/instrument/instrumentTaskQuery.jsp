<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkTaskDTO" %>
<%@ page import="com.sino.nm.ams.instrument.constant.InstrumentLookUpConstant" %>
<%--
  created by YSB
  Date: 2009-01-23
  Time: 13:53:38
--%>

<html>
<head>
    <title>仪器仪表检修任务维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    Row row = null;
    AmsInstrumentEamYbChkTaskDTO dto = (AmsInstrumentEamYbChkTaskDTO)request.getAttribute(QueryConstant.QUERY_DTO);//针对Servlet里的dto.setXXX()方法写的 
%>
<form method="post" name="mainFrm" action="/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet">
    <script type="text/javascript">
        printTitleBar("仪器仪表检修任务维护")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
        	<td width="20%" align="right">任务名称：</td>
            <td width="40%">
            	<input type="text" name="taskName" style="width:50%" value="<%=dto.getTaskName() %>"><a href="#" title="点击选择任务" class="linka" onclick="do_SelectTaskName();">[…]</a>
            </td>
            
            <td width="45%" align="right" colspan="2">
            	<img src="/images/button/query.gif" style="cursor:'hand'" onclick="do_search();" alt="查询">&nbsp;&nbsp;&nbsp;
            	<img src="/images/button/new.gif" alt="新增" onClick="do_add();">
            </td>
            <td></td>
        </tr>
    </table>
    
    <input type="hidden" name="act">

	<script type="text/javascript">
        var columnArr = new Array("公司","任务代码", "任务名称", "备注" );
        var widthArr = new Array("15%", "15%", "20%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:0px;width:100%;height:330px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR">
                 <td width="15%" align="center"
                    onclick="show_detail('<%=row.getValue("TASK_ID")%>')">
                    <input type="text" value="<%=row.getValue("COMPANY")%>" readonly="readonly" class="finput">
                </td>
                <td width="15%" align="right"
                    onclick="show_detail('<%=row.getValue("TASK_ID")%>')"><%=row.getValue("TASK_ID")%>
                </td>
                <td width="20%" align="left"
                    onclick="show_detail('<%=row.getValue("TASK_ID")%>')">
                    <input type="text" value="<%=row.getValue("TASK_NAME")%>" readonly="readonly" class="finput">
                </td>
                <td width="10%" align="left"
                    onclick="show_detail('<%=row.getValue("TASK_ID")%>')">
                    <input type="text" value="<%=row.getValue("REMARK")%>" readonly="readonly" class="finput">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%
	if(hasData){
%>
	<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}	
%>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet";
    mainFrm.submit();
}

function show_detail(taskId) {
    var url = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&taskId=" + taskId;
    var popscript = 'width=600,height=400,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}

function do_add() {
    var url = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet?act=<%=WebActionConstant.NEW_ACTION%>";
    var popscript = 'width=600,height=400,top=200,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=yes';
    window.open(url, 'basePot', popscript);
}

function do_SelectTaskName() {
	var url="/servlet/com.sino.nm.ams.instrument.bean.InstrumentLookUpServlet?lookUpName=<%=InstrumentLookUpConstant.LOOK_UP_TASK_ID%>";
	var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
	var taskNames = window.showModalDialog(url, null, popscript);
	if(taskNames) {
		mainFrm.taskName.value = taskNames[0].taskName;
	}
}
</script>