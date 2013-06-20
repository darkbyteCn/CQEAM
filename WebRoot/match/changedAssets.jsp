<%@ page contentType="text/html;charset=GB2312" language="java" buffer="none" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%--
  User: V-jiachuanchuan
  Date: 2007-11-22
  Time: 9:17:32
--%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>EAM转资匹配</title>

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
    <script type="text/javascript">

        function match() {
            var systemid = parent.itemInfoView.getRadioValue("systemid");
            var assetId = parent.systemInfoView.getRadioValue("assetId");
            if (systemid == "" || assetId == "") {
                alert("请选择EAM工程物资条码和MIS资产代码");
            } else {
                if (confirm("确定匹配吗?")) {
                    mainForm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
                    mainForm.systemid.value = systemid;
                    mainForm.assetId.value = assetId;
                    document.mainForm.action = "/servlet/com.sino.ams.match.servlet.ChangedAssetsLeftServlet?systemid=" + mainForm.systemid.value + "&assetId=" + mainForm.assetId.value + "&act=<%=AMSActionConstant.MATCH_ACTION%>";
                    document.mainForm.submit();
                }
            }
        }

        var ArrAction1 = new Array(true, "退出", "act_refresh.gif", "退出", "window.close");
        var ArrAction2 = new Array(true, "匹配", "act_query.gif", "匹配", 'match()');
        var ArrActions = new Array(ArrAction1, ArrAction2);
        var ArrSinoViews = new Array();
    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 onload="initPage();">
<script>
    printTitleBar("EAM转资匹配");
    printToolBar();
</script>
<%
    String ret = request.getParameter("ret");
//    System.out.println("ret=" + ret);
//    if(ret.equals("Y"))  {
%>
<%--<table width="100%" border="2" cellpadding="0" cellspacing="0" onload="initPage();">--%>
<%--<tr>--%>
<%--<td width="50%">--%>
<iframe name="itemInfoView" src="/match/changedMatchL.jsp" height="93%" width="50%"
        scrolling="auto" frameborder="1"></iframe>
<!--</td>-->
<!--<td width="50%">-->
<iframe name="systemInfoView" src="/match/changedMatchR.jsp" height="93%" width="50%"
        ></iframe>
<!--</td>-->
<!--</tr>-->
<!--</table>-->
<form name="mainForm" action="">
    <input type="hidden" name="systemid">
    <input type="hidden" name="assetId">
    <input type="hidden" name="act">

</form>
</body>
</html>
<script type="text/javascript">
    function initPage() {
        var ret = "<%=ret%>";
        //        alert(ret);
        if (ret == "Y") {
            alert("匹配成功！");
        } else if (ret == "N") {
            alert("匹配失败！");
        }
    }
</script>