<%--
  Created by HERRY.
  Date: 2007-11-26
  Time: 11:17:01
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>成本中心部门对照</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetFrameWidth()">
<script>
    var ArrAction1 = new Array(true, "退出", "act_refresh.gif", "退出", "window.close()");
    var ArrAction2 = new Array(true, "匹配", "act_query.gif", "生成对照关系", 'do_match()');
    var ArrAction7 = new Array(true, "对照记录", "act_query.gif", "查看对照记录", "matchLog()");
    var ArrActions = new Array(ArrAction1, ArrAction2, ArrAction7);
    var ArrSinoViews = new Array();
    printTitleBar("成本中心部门对照");
    printToolBar();
</script>
<iframe name='amsInfo' id="amsInfo" src='/servlet/com.sino.ams.system.cost.servlet.CostCenterServlet' height='95%' width='50%' scrolling="no" frameborder="1"></iframe>
<iframe name='misInfo' id="misInfo" src='/servlet/com.sino.ams.system.cost.servlet.DeptMisServlet' height='95%' width='50%' scrolling="no" frameborder="1"></iframe>
<input type="hidden" name="working">
</body>
<script type="text/javascript">
    function do_match() {
        if (document.getElementById("working").value == '1')
        {
            alert('正在处理中，请稍候...');
            return false;
        }

        misInfo.matchIt();
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
        var url = "/servlet/com.sino.ams.system.cost.servlet.AmsMisCostMatchServlet";
        var popscript = "width=900,height=600";
        window.open(url,"matchLog",popscript);
    }
    function Method2() {
        alert("Constructing...")
    }
    function Method1() {
        alert("Constructing...")
    }

    function do_SetFrameWidth(){
        var bodyWidth = document.body.clientWidth;
        var bodyHeight = document.body.clientHeight;
        var screenWidth = window.screen.width;
        var screenHeight = window.screen.height;
        if(bodyWidth + 10 > screenWidth){
            bodyWidth = screenWidth - 10;
        }
        if(bodyHeight + 72 > screenHeight){
            bodyHeight = screenHeight - 72;
        }
        var misFrm = document.getElementById("misInfo");
        var amsFrm = document.getElementById("amsInfo");

//        misFrm.height = bodyHeight;
        misFrm.width = bodyWidth/2;
        if( misFrm.Document ){
        	misFrm.Document.getElementById("act").click();
        }
//        amsFrm.height = bodyHeight;
        amsFrm.width = bodyWidth/2;  
        if( amsFrm.Document ){
        	amsFrm.Document.getElementById("act").click();
        }
    }

    window["onresize"] = function() {
        do_SetFrameWidth();
    };
</script>
</html>