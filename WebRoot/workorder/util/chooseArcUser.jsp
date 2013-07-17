<%--
  User: zhoujs
  Date: 2007-10-22
  Time: 10:40:14
  Function:
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>选择归档人</title>
 <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
 	<script type="text/javascript" src="/WebLibary/js/jquery.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
<%
    String userOfGroupOpt = (String) request.getAttribute("userOfGroup");
    String groupOpt = (String) request.getAttribute("GROUPATTR");
%>
<script type="text/javascript">
    printTitleBar("选择归档人");
    var ArrAction0 = new Array(true, "确定", "action_save.gif", "确定", "chooseUser");
    var ArrAction1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
    var ArrActions = new Array(ArrAction0, ArrAction1);
    printToolBar();
</script>
</head>
<body topMargin=0 leftMargin=0  style="background-color: #eeeeee">
<form action="chooseImplement.jsp" name="mainFrm">

    <table width="100%" align="center" bgcolor="#eeeeee">
        <tr height=20px>
            <td width="70px" align = "right">部 门：</td>
            <td style="border:none">
                <select name="dept" id="dept" style="width:80%;">
                    <%=groupOpt%>
                </select>
            </td>
        </tr>
        <tr height=20px>
            <td width="70px" align = "right">归档人：</td>
            <%-- 
            <td style="border:none">
                <select name="implement" style="width:80%;">
                    <%=userOfGroupOpt%>
                </select>
            </td>
            --%>
            <td width="85%">
                <input type="tex" id="implementName" name="implementName" style="width:80%" readonly="true" value=""><a href="" title="点击选择归档人" onclick="do_SelectPerson(); return false;">[…]</a>
            	<input type="hidden" id="implement" name="implement" value="">
            </td>
        </tr>
    </table>

</form>
</body>
</html>

<script type="text/javascript">
    function chooseUser(){
        var userId = $("#implement").val();
        if(userId == ""){
            alert("请选择归档人。");
            return;
        }        
        window.returnValue=userId;
        window.close();
    }

    function do_Close(){
        window.close();
    }

    function do_SelectPerson(){      
            var lookUpName = "LOOK_UP_USER_ACHIEVE";
            var dialogWidth = 47;
            var dialogHeight = 30;
            var groupId = "groupId=" + $("#dept").val();
            var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, groupId);
            if (users) {
                var user = users[0];
                $("#implementName").val(user["userName"]);
                $("#implement").val(user["userId"]);
            } else {
                $("#implementName").val("");
                $("#implement").val("");
            }       
    }

</script>