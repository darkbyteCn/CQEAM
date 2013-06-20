<%--
  Created by wwb.
  User: demo
  Date: 2007-1-7
  Time: 17:05:59
  审批时，添加审批意见页面
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<head>
    <title>请填写退回意见</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
</head>
<script type="text/javascript">
    printTitleBar("请填写退回意见");
    var ArrAction1 = new Array(true, "确定", "action_save.gif", "确定", "do_Save");
    ArrActions[1] = ArrAction1;
    var btnCount = ArrActions.length;
    for(var i = 0; i < btnCount; i++){
        ArrActions[i][0] = (i < 2);
    }
    printToolBar();
</script>
<body topmargin=0 leftmargin=0>
<form name="mainForm" action="">
<div id="headDiv" style="overflow:hidden;position:absolute;top:38px;left:1px;height:90%;width:100%"  >
    <table style="width:100%;height:100%" align="center">
        <tr height="6">
            <td colspan="5"></td>
        </tr>
        <tr>
            <td colspan="5" height="100px">
                <textarea name="content" style="width:100%;height:100%"></textarea>
            </td>
        </tr>
        <tr height="6">
            <td colspan="5"></td>
        </tr>
    </table>
</div>
</form>
<script language="javascript">
    if (window.dialogArguments) {
        mainForm.content.value = window.dialogArguments;
    }
    function do_Save() {
        if(document.mainForm.content.value==""){
            alert("请填写退回原因！");
            return false;
        }
        window.returnValue = document.mainForm.content.value;
        window.close();
    }

    function do_Cancel() {
        window.returnValue = "";
        window.close();
    }
</script>
</body>

</html>