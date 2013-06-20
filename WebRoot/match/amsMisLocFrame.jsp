<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-3-25
  Time: 14:03:14
  To change this template use File | Settings | File Templates.
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>地点匹配</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script   type = "text/javascript">
        var ArrAction1 = new Array(true, "退出", "act_refresh.gif", "退出", "window.close");
        var ArrAction2 = new Array(true, "匹配", "act_query.gif", "匹配", 'match()');
        var ArrAction3 = new Array(true, "已匹配信息", "act_query.gif", "已匹配信息", 'havematch()');
        var ArrActions = new Array(ArrAction1, ArrAction2, ArrAction3);
        var ArrSinoViews = new Array();
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 >
<script  type = "text/javascript">
    printTitleBar("地点匹配");
    printToolBar();
</script >
            <iframe name="amsLocView" src="/match/amsLocMatch.jsp" height="93%" width="50%" scrolling="auto" frameborder="1"></iframe>
            <iframe name="misLocView" src="/match/misLocMatch.jsp" height="93%" width="50%" ></iframe>
<form name="mainForm" action="">
    <input type="hidden" name="workorderObjectNo">
    <input type="hidden" name="assetsLocation">
    <input type="hidden" name="act">
</form>
</body>
</html>
<script type = "text/javascript">
    function match() {
             var workorderObjectNo = parent.amsLocView.getRadioValue("workorderObjectNo");
             var assetsLocation = parent.misLocView.getRadioValue("assetsLocation");
             if (workorderObjectNo == "" || assetsLocation == "") {
                 alert("请选择EAM地点和MIS地点");
                 return;
             }  else{
                 confirm("确定匹配吗?");
             }
             mainForm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
             mainForm.workorderObjectNo.value = workorderObjectNo;
             mainForm.assetsLocation.value = assetsLocation;
             document.mainForm.action = "/servlet/com.sino.ams.match.amsMisLocMatch.servlet.amsMisLocMatchServlet?workorderObjectNo=" + mainForm.workorderObjectNo.value + "&assetsLocation=" + mainForm.assetsLocation.value + "&act=<%=AMSActionConstant.MATCH_ACTION%>";
             document.mainForm.submit();
         }

    function initPage(){
    }

    function havematch() {
        var url = "/servlet/com.sino.ams.match.amsMisLocMatch.servlet.amsMisLocMatchServlet?act=HAVE_MACHED";
//        var url = "/match/locMatchInfo.jsp";
        var popscript = "width=800,height=600";
        window.open(url,"matched",popscript);
    }
 </script>