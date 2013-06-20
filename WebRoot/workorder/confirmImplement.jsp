<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-24
  Time: 13:09:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>选择执行人</title></head>
 <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
  <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/json.js"></script>
<%
    String userOfGroupOpt = (String) request.getAttribute("userOfGroup");
    String groupOpt = (String) request.getAttribute("GROUPATTR");
%>

<body topMargin=0 leftMargin=0>
<form action="chooseImplement.jsp" name="mainFrm">
<script type="text/javascript">
    printTitleBar("选择执行人")
</script>
    <table width="100%" align="center" bgcolor="#eeeeee">
        <%--<tr height=20px>--%>
        <%--<td bgcolor="#5BBBEF" style="border:none">请选择工单执行人：</td>--%>
        <%--</tr>--%>
        <tr height=20px>
            <td width="70px" align = "right">部 门：</td>
            <td style="border:none">
                <select name="dept" style="width:80%;" onchange="getImpMenu();">
                    <%=groupOpt%>
                </select>
            </td>
        </tr>
        <tr height=20px>
            <td width="70px" align = "right">执行人：</td>
            <td style="border:none">
                <select name="implement" style="width:80%;">
                    <%=userOfGroupOpt%>
                </select>
            </td>
        </tr>
        <tr height=20px align=right>
            <td width="50px" style="border:none">&nbsp;</td>
            <td style="border:none">
                <a href="#" onclick="chooseUser()"
                   style="cursor:pointer;text-decoration:underline;color:blue">[确定]</a>
                <a href="#" onclick="window.close();"
                   style="cursor:pointer;text-decoration:underline;color:blue">[关闭]</a>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
        <tr height=20px>
            <td width="70px"></td>
            <td style="border:none">
                    </td>
        </tr>
    </table>

</form>
</body>
</html>

<script type="text/javascript">
    function chooseUser(){
        var  userId= mainFrm.implement.value;
        var  groupId=mainFrm.dept.value;
        window.returnValue=userId;
        window.close();
    }

  var xmlHttp;
function getImpMenu() {
    var implement = document.getElementById("implement")  ;

    if (implement.options.length > 0) {
        dropOption('implement', false);
    }

    var dept = document.getElementById("dept").value ;
    var url = "/servlet/com.sino.ams.workorder.servlet.WorkPersonServlet?act=implement&dept=" + dept;
    xmlHttp = GetXmlHttpObject(setImpMenu);
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
    xmlHttp.send("a=1");


}
function setImpMenu() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        var flexValues = new Array();
        var descriptions = new Array();
        var resText = xmlHttp.responseText;
        var resArray = resText.parseJSON();
        if (resArray == "ERROR") {
            alert(resText);
        } else {
            if (resArray.length > 0) {

                var implement = document.getElementById("implement");
//                var emptyOption = new Option("--请选择--", "");
//                littleCategoryObj.add(emptyOption)
                var retStr;
                for (var i = 0; i < resArray.length; i++) {
                    retStr = resArray[i];
                    var arr = retStr.split("$");
                    var option = new Option(arr[1], arr[0]);
                    implement.add(option)
                }
            }
        }
        xmlHttp = null;
    }
}
</script>