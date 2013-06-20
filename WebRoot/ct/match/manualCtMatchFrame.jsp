<%--
  Created by Yu.
  Date: 2008-12-08
  Time: 10:47:01
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
	<title>村通设备－村通资产匹配</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>

</head>
<body leftmargin="0" topmargin="0" onload="window.focus();">
<script>
    var ArrAction1 = new Array(true, "退出", "act_refresh.gif", "退出", "window.close()");
    var ArrAction2 = new Array(true, "匹配", "act_query.gif", "匹配", 'do_match()');
//    var ArrAction3 = new Array(true, "个性化查询", "act_query.gif", "个性化查询", "searchIt()");
//    var ArrAction4 = new Array(true, "按地点匹配", "act_query.gif", "按地点匹配", "matchByLocation()");
//    var ArrAction5 = new Array(true, "按县匹配", "act_query.gif", "按县匹配", "matchByCounty()");
//    var ArrAction6 = new Array(true, "按市匹配", "act_query.gif", "按市匹配", "matchByCity()");
//    var ArrAction7 = new Array(true, "匹配历史", "act_query.gif", "查看历史匹配记录", "matchLog()");
//    var ArrAction8 = new Array(true, "方式2", "act_query.gif", "方式2", "Method2()");
//    var ArrAction9 = new Array(false, "方式1", "act_query.gif", "方式1", "Method1()");
//    var ArrActions = new Array(ArrAction1, ArrAction2, ArrAction4, ArrAction5, ArrAction6, ArrAction7);
	var ArrActions = new Array(ArrAction1, ArrAction2);
    var ArrSinoViews = new Array();
    printTitleBar("村通设备－村通资产匹配");
    printToolBar();
</script>

<iframe name='amsInfo' src='/servlet/com.sino.ams.match.servlet.ManualCtMatchAMS' height='93%' width='50%' scrolling="no" frameborder="1"></iframe>
<iframe name='misInfo' src='/servlet/com.sino.ams.match.servlet.ManualCtMatchMIS' height='93%' width='50%' scrolling="no" frameborder="1"></iframe>

<input type="hidden" name="working">
</body>
<script type="text/javascript">
    function do_match() {
    	
        if (document.getElementById("working").value == '1')
        {
            alert('正在处理中，请稍候...');
            return false;
        }
		
        amsInfo.matchIt();
        
        document.getElementById("working").value = 0;
        
    }

    function matchByLocation() {
        amsInfo.matchByLocation();
    }
    function matchByCounty() {
        amsInfo.matchByCounty();
    }
    function matchByCity() {
        amsInfo.matchByCity();
    }
    function searchIt() {
        alert("Constructing...")
    }
    function matchLog() {
        var url = "/servlet/com.sino.ams.match.servlet.BatchCtMatchLogServlet";
        var popscript = "width=900,height=600";
        window.open(url,"matchLog",popscript);
    }
    function Method2() {
        alert("Constructing...")
    }
    function Method1() {
        alert("Constructing...")
    }

</script>
</html>