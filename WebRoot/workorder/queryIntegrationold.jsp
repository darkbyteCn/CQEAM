<%--
  Created by IntelliJ IDEA.
  User: V-jiachuanchuan
  Date: 2007-11-5
  Time: 16:25:23
  Function:���ղ�ѯ���Ͳ�ѯ�������ۺϣ�δ���أ�δ��ɣ����鵵���ѹ鵵����ʱ��
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ include file="/newasset/headerInclude.htm"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        String workorderBatch = reqParser.getParameter("workorderBatch");
        String workorderBatchName = reqParser.getParameter("workorderBatchName");
        String workorderNo = reqParser.getParameter("workorderNo");
        String workorderObjectCode = reqParser.getParameter("workorderObjectCode");
        String workorderObjectName = reqParser.getParameter("workorderObjectName");
        String executeUserName = reqParser.getParameter("executeUserName");
        String startDate = reqParser.getParameter("startDate");
        String action = reqParser.getParameter("act");
        String queryType = reqParser.getParameter("queryType");
        String prjId= reqParser.getParameter("prjId");
        String prjName= reqParser.getParameter("prjName");

        if (queryType.equals(DictConstant.WOR_STATUS_NEW)) {
            out.print("<title>δ���ع�����ѯ</title>");
        } else if (queryType.equals(DictConstant.WOR_STATUS_DOWNLOAD)) {
            out.print("<title>δ��ɹ�����ѯ</title>");
        } else if (queryType.equals(DictConstant.WOR_STATUS_UPLOAD)) {
            out.print("<title>���鵵������ѯ</title>");
        } else if (queryType.equals(DictConstant.WOR_STATUS_ARCHIVED)) {
            out.print("<title>�ѹ鵵������ѯ</title>");
        } else if (queryType.equals(DictConstant.WOR_STATUS_OVERTIME)) {
            out.print("<title>��ʱ������ѯ</title>");
        } else if (queryType.equals(DictConstant.WOR_STATUS_INTEGAZATION)) {
            out.print("<title>�����ۺϲ�ѯ</title>");
        }
    %>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.QueryIntegrationServlet">
<script type="text/javascript">
    if (<%=queryType.equals(DictConstant.WOR_STATUS_NEW)%>)
    {
        printTitleBar("δ���ع�����ѯ");
    }
    else if (<%=queryType.equals(DictConstant.WOR_STATUS_DOWNLOAD)%>)
    {
        printTitleBar("δ��ɹ�����ѯ");
    }
    else if (<%=queryType.equals(DictConstant.WOR_STATUS_UPLOAD)%>)
    {
        printTitleBar("���鵵������ѯ");
    }
    else if (<%=queryType.equals(DictConstant.WOR_STATUS_ARCHIVED)%>)
    {
        printTitleBar("�ѹ鵵������ѯ");
    }
    else if (<%=queryType.equals(DictConstant.WOR_STATUS_OVERTIME)%>)
    {
        printTitleBar("��ʱ������ѯ");
    }
    else if (<%=queryType.equals(DictConstant.WOR_STATUS_INTEGAZATION)%>)
    {
        printTitleBar("�����ۺϲ�ѯ");
    }
</script>

<table width="100%" border=0 cellpadding="2" cellspacing="0"
        style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">
    <input type="hidden" name="queryType" value="<%=queryType%>">

    <tr>
        <td align="right" width="8%">�������ţ�</td>
        <td width="17%"><input style="width:80%" type="text" class="input_style1" name="workorderBatch" value="<%=workorderBatch%>">
        </td>

        <td align="right" width="8%">��Ŀ��ţ�</td>
        <td><input style="width:80%" type="text" class="input_style1" name="prjId"
                   value="<%=prjId%>">
            <a href="#" onclick="showProject()" title="���ѡ����Ŀ���" class="linka">[��]</a></td>

        <td align="right" width="8%">�����ţ�</td>
        <td width="17%"><input style="width:80%" type="text" class="input_style1" name="workorderNo" value="<%=workorderNo%>"></td>

        <td align="right" width="8%">�������ͣ�</td>
        <td width="17%"><select style="width:100%" class="select_style1"
                                name="workorderType"><%=request.getAttribute(WebAttrConstant.PLAN_TYPE_OPTION)%>
        </select></td>
    </tr>
    <tr>
        <td align="right">�ص��ţ�</td>
        <td><input style="width:80%" type="text" class="input_style1" name="workorderObjectCode"
                   value="<%=workorderObjectCode%>"><a
                href="#" onclick="	()" title="���ѡ��ص�" class="linka">[��]</a></td>
        <td align="right">�ص��ƣ�</td>
        <td><input style="width:80%" type="text"  class="input_style1"
                   name="workorderObjectName"
                   value="<%=workorderObjectName%>"></td>
        <td align="right">ִ���ˣ�</td>
        <td><input style="width:80%" type="text" name="executeUserName" class="input_style1" value="<%=executeUserName%>"><a
                href="#" onclick="showUser()" title="���ѡ��ִ����" class="linka">[��]</a></td>

        <%
            if (queryType.equals(DictConstant.WOR_STATUS_INTEGAZATION)) {
        %>
        <td align="right">����״̬��</td>
        <td><select style="width:100%"  class="select_style1"
                    name="workorderFlag"><%=request.getAttribute(WebAttrConstant.PLAN_STATUS_OPTION)%>
        </select>
        </td>
        <%}%>

    </tr>
    <tr>
        <td align="right">��˾��</td>
        <td><select style="width:80%"    class="select_style1"
                    name="organizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
        </select>
        </td>

        <td align="right" width="12%">��ʼʱ����ڣ�</td>
        <td ><input type="text" name="startDate"
                               value="<%=startDate%>"  class="input_style1"
                               style="width:80%" title="���ѡ������"
                               readonly  onclick="gfPop.fEndPop('',startDate)">
            <img src="/images/calendar.gif" alt="���ѡ������" onclick="gfPop.fEndPop('',startDate)"></td>
         <td align="right">����רҵ��</td>
        <td><select style="width:80%"     class="select_style1"
                    name="objectCategory"><%=request.getAttribute("CATEGORY")%>
        </select>
        </td>
        
       <td align="right" width="8%">�������ƣ�</td>
        <td width="17%"><input style="width:100%" type="text" class="input_style1" name="workorderBatchName" value="<%=workorderBatchName%>">
        </td>
    </tr>
    <tr>
    <td colspan="2" align="right" >
    </td>
    <td colspan="2" align="right" >
    </td>
    <td colspan="2" align="right" >
    </td>
    	 <td colspan="2" align="right" width="100%">
            <img src="/images/eam_images/search.jpg"  style="cursor:pointer"  alt="�����ѯ" onClick="do_SearchOrder(); return false;">
            <img src="/images/eam_images/export.jpg" style="cursor:pointer" onclick="do_export();" alt="������Excel">
        </td>
    	
    </tr>
</table>
	<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:125px;left:1px;width:100%"  >
		<table class="headerTable" border="1" width="150%" style="text-align:center">
	        <tr height="23px">
	        	<td width="8%">��Ŀ���</td>
	        	<td width="8%">��Ŀ����</td>
		        <td width="8%">��˾����</td>
	            <td width="8%">������</td>
	            <td width="6%">����״̬</td>
	            <td width="6%">��������</td>
	            <td width="12%">�ص���</td>
                <td width="14%">�ص���</td>
                <td width="6%">��ʼ����</td>
                <td width="5%">ʵʩ����</td>
                <td width="5%">ִ����</td>
                <td width="6%">�������</td>
                <td width="4%">����</td>
                <td width="4%">��ʱ</td>
	        </tr>
		</table>
	</div>
<div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:74px;left:1px" align="left" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
    if (hasData) {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr class="dataTR" height="22" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;">
			<td width="8%"><input class="finput" readonly="true" value="<%=row.getValue("SEGMENT1")%>"></td>
			<td width="8%"><input class="finput" readonly="true" value="<%=row.getValue("NAME")%>"></td>
            <td width="8%"><input class="finput" readonly="true" value="<%=row.getValue("ORG_NAME")%>"></td>
            <td width="8%"><input class="finput" readonly="true" value="<%=row.getValue("WORKORDER_NO")%>"></td>
            <td width="6%"><input class="finput" readonly="true" value="<%=row.getValue("WORKORDER_FLAG_DESC")%>"></td>
            <td width="6%"><input class="finput" readonly="true" value="<%=row.getValue("WORKORDER_TYPE_DESC")%>"></td>
            <td width="12%"><input class="finput" readonly="true" value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="14%"><input class="finput" readonly="true" value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
            <td width="6%"><input class="finput" readonly="true" value="<%=row.getValue("START_DATE")%>"></td>
            <td width="5%"><input class="finput" readonly="true" value="<%=row.getValue("IMPLEMENT_DAYS")%>"></td>
            <td width="5%"><input class="finput" readonly="true" value="<%=row.getValue("IMPLEMENT_USER")%>"></td>
            <td width="6%"><input class="finput" readonly="true" value="<%=row.getValue("UPLOAD_DATE")%>"></td>
            <td width="4%"><input class="finput" readonly="true" value="<%=row.getValue("DIFF")%>"></td>
            <td width="4%"><input class="finput" readonly="true" value="<%=row.getValue("OVERTIME")%>"></td>
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
<div id="pageNaviDiv" style="position:absolute;top:90%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>


<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">

    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.target = "_self";
        mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>";
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        url = "/public/wait.jsp?title=������ϸ��Ϣ&src="+url+"&act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        window.open(url, "", winstyle);
    }

    function doChecked() {
        var transType = document.getElementById("objectCategory")   ;
        dropSpecialOption(transType, '80');
        do_SetPageWidth();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchOrder();
        }
    }
    function showLocation() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
            }
        }
    }
    function showProject() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT3%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Projects) {
            var prjId = null;
            for (var i = 0; i < Projects.length; i++) {
                prjId = Projects[i];
                mainFrm.prjId.value=prjId["segment1"];
                //dto2Frm(prjId, "mainFrm");
            }
        }
    }
    function showUser() {
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }


</script>