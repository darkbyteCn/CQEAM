<%--
  User: zhoujs
  Date: 2007-10-22
  Time: 10:40:14
  Function:
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>ѡ��ִ����</title>
 <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/jquery.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AssetsLookUp.js"></script>	
    <script type="text/javascript">
        printTitleBar("ѡ��ִ����");
        var ArrAction0 = new Array(true, "ȷ��", "action_save.gif", "ȷ��", "chooseUser");
        var ArrAction1 = new Array(true, "�ر�", "action_cancel.gif", "�ر�", "do_Close");
        var ArrActions = new Array(ArrAction0, ArrAction1);
        printToolBar();
    </script>
</head>
<%
    String userOfGroupOpt = (String) request.getAttribute("userOfGroup");
    String groupOpt = (String) request.getAttribute("GROUPATTR");
%>

<body topMargin=0 leftMargin=0 style="background-color: #eeeeee">
<form action="chooseImplement.jsp" name="mainFrm">

    <table width="100%" align="center">
        <tr height=20px>
            <td width="70px" align = "right">��&nbsp&nbsp�ţ�</td>
            <td style="border:none">
                <select name="dept" id="dept" style="width:80%;">
                    <%=groupOpt%>
                </select>
            </td>
        </tr>
        <tr height=20px>
            <td width="70px" align = "right">ִ���ˣ�</td>
            <%-- 
            <td style="border:none">
                <select name="implement" style="width:80%;">
                    <%=userOfGroupOpt%>
                </select>
            </td>
            --%>
            <td width="85%">
                <input type="tex" id="implementName" name="implementName" style="width:80%" readonly="true" value=""><a href="" title="���ѡ��ִ����" onclick="do_SelectPerson(); return false;">[��]</a>
            	<input type="hidden" id="implement" name="implement" value="">
            </td>
        </tr>
    </table>

</form>
</body>
</html>

<script type="text/javascript">
    function chooseUser(){
        var userId = mainFrm.implement.value;
        if(userId == ""){
            alert("��ѡ��ִ���ˡ�");
            return;
        }
        var groupId = mainFrm.dept.value;
        //var groupNmae = mainFrm.dept.options[mainFrm.dept.selectedIndex].text;
        //window.returnValue = userId;
        window.returnValue = userId + ";" + groupId ;
        window.close();
    }

    function do_Close(){
        window.close();
    }
    
    function do_SelectPerson(){
        with(mainFrm){
            var lookUpName = "LOOK_UP_USER_WITH_DEPT";
            var dialogWidth = 47;
            var dialogHeight = 30;
            var userPara = "groupId=" +$("#dept").val();
            var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
            if (users) {
                var user = users[0];
                $("#implementName").val(user["userName"]);
                $("#implement").val(user["userId"]);               
                $("#dept").val(user["groupId"]);
            } else {
                $("#implementName").val("");
                $("#implement").val("");
                $("#dept").val("");
            }
        }
    }

</script>