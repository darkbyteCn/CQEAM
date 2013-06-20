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
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-23
  Time: 13:09:55
  To change this template use File | Settings | File Templates.
--%>
<head>
    <title>工单分配</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script
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

    %>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.WorkPersonServlet">
    <script type="text/javascript">
        printTitleBar("工单分配");
    </script>
    <table width="100%" border="0" bgcolor='#efefef' cellpadding="2" cellspacing="0"
            >
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="groupId" value="">
        <input type="hidden" name="queryType" value="<%=queryType%>">
        <input type="hidden" name="implementBy">
        <tr>
            <td align="right" width="10%">工单号：</td>
            <td width="20%"><input style="width:80%" type="text" name="workorderNo" value="<%=workorderNo%>"></td>

            <td align="right" width="12%">开始时间大于：</td>
            <td width="20%"><input type="text" name="startDate"
                                   value="<%=startDate%>" class="readonlyInput"
                                   style="width:80%" title="点击选择日期"
                                   readonly onclick="gfPop.fEndPop('',startDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop('',startDate)"></td>
            <td width="10%">
            </td>
            <td align="right" width="10%"></td>
            <td width="20%"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"
                                                          alt="点击查询" onClick="do_SearchOrder(); return false;"><img src="/images/button/distriAgain.gif" alt="选择执行人" onClick="distri(); return false;"></a></td>
        </tr>
    </table>
    <script type="text/javascript">
        var columnArr = new Array("checkbox", "公司", "工单号", "工单状态", "所属专业", "工单类型", "地点编号", "地点简称", "开始日期", "实施周期(天)", "执行人", "实际完成日期", "差异", "超时");
        var widthArr = new Array("3%", "8%", "9%", "6%", "6%", "6%", "7%", "7%", "7%", "8%", "5%", "8%", "3%", "3%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:330px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" height="22" >
                <td width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("SYSTEMID")%>"></td>
                <td width="8%"><%=row.getValue("ORG_NAME")%>
                </td>
                <td width="9%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("WORKORDER_NO")%>
                </td>
                <td width="6%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("WORKORDER_FLAG_DESC")%>
                </td>
                <td width="6%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("ATTRIBUTE4")%>
                </td>
                <td width="6%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("WORKORDER_TYPE_DESC")%>
                </td>
                <td width="7%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="7%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="7%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("START_DATE")%>
                </td>
                <td width="8%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("IMPLEMENT_DAYS")%>
                </td>
                <td width="5%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("IMPLEMENT_USER")%>
                </td>
                <td width="8%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("UPLOAD_DATE")%>
                </td>
                <td width="3%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("DIFF")%>
                </td>
                <td width="3%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;"><%=row.getValue("OVERTIME")%>
                </td>

                <%

                    }    }

                %>
        </table>
    </div>
</form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

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
        mainFrm.act.value = "query2";
        mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        window.open(url, "", winstyle);
    }

    function doChecked() {

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

  function distri(){
    var checkedCount = getCheckedBoxCount("subCheck");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return false;
        } else  {
         chooseExcuter();
                 }
}


    function chooseExcuter() {//选择执行人
//        if (getCheckedBoxCount("subCheck") > 0) {
//        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        <%--mainFrm.act.value = "<%=WebActionConstant.CHECK_ACTION%>";--%>
//        mainFrm.submit();
            <%--var groupId = mainFrm.distributeGroupId.value;--%>
            <%--var systemids = mainFrm.systemids.value;--%>

            var url = "/servlet/com.sino.ams.workorder.servlet.WorkPersonServlet?act=CHECK_ACTION";
            var winstyle = "dialogWidth=250px;dialogHeight=131px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
            var retVal = window.showModalDialog(url, null, winstyle);
//               alert(retVal);
            if (retVal) {
                mainFrm.implementBy.value = retVal;
                mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                mainFrm.submit();
            }
//        } else {
//            alert(UPDATE_CHECK_MSG);
//        }


//            var url = "/servlet/com.sino.ams.workorder.servlet.WorkPersonServlet?act=CHECK_ACTION";
//            var winstyle = "dialogWidth=250px;dialogHeight=131px;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scrollbars=no";
//            var popscript = 'width=870,height=700,top=1,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=no, status=yes';
//             window.open (url, null, "");
    }
</script>