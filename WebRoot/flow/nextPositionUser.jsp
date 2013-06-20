<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page import="com.sino.flow.dto.SfTaskDefineDTO" %>


<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>下一任务办理人</title>
    <link rel="stylesheet" type="text/css" href="../style/css/main.css">
    <script language="javascript" src="/style/js/IESTitleBar.js"></script>
</head>

<body onload='initPage()'>
<%
    String userOptions = (String) request.getAttribute(ReqAttributeList.APP_USER_OPTIONS);
    if (StrUtil.isEmpty(userOptions)) {
        return;
    }
    SfTaskDefineDTO taskDTO = (SfTaskDefineDTO) request.getAttribute(ReqAttributeList.TASK_DATA);
%>
<form name="mainFrm">
    <table width=100% align=center>
        <tr>
            <td align=left>
                <script>
                    printTitleBar("请选择下一办理人")
                </script>
            </td>
        </tr>
        <tr>
            <td height=10px></td>
        </tr>
        <tr width=100%>
            <td align=center width=60%>
                <select name="nextUserId" style="width:70%;text-align:center">
                    <option value="">---请选择---</option>
                    <%=userOptions%>
                </select>
            </td>
        </tr>
        <tr>
            <td height=10px></td>
        </tr>
        <tr>
            <td align=right style="right:5px">
                <input type="hidden" name="nextTaskId" value="<%=taskDTO.getTaskId()%>">
                <input type="hidden" name="nextPositionId" value="<%=taskDTO.getTaskPositionId()%>">
                <input type="hidden" name="nextPositionName" value="<%=taskDTO.getTaskPositionName()%>">
                <input type="hidden" name="nextDeptId" value="<%=taskDTO.getDeptId()%>">
                <input type="hidden" name="nextDeptName" value="<%=taskDTO.getDeptName()%>">
                <input type="hidden" name="procId" value="<%=taskDTO.getProcId()%>">

                <p><img border="0" src="../images/eam_images/ok.jpg" width="63" height="18" onClick="do_TransPara();"></p>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script>
    var taskPosition = new Object();

    function do_TransPara() {
        with (mainFrm) {
            if (nextUserId.value == "") {
                alert("请选择流程下一任务办理人");
                return;
            }
            taskPosition.nextTaskId = nextTaskId.value;
            taskPosition.nextDeptId = nextDeptId.value;
            taskPosition.nextDeptName = nextDeptName.value;
            taskPosition.nextPositionId = nextPositionId.value;
            taskPosition.nextPositionName = nextPositionName.value;
            var arr = nextUserId.value.split("&&");
            taskPosition.nextUserId = arr[0];
            taskPosition.nextUserName = arr[1];
            taskPosition.procId = procId.value;
            window.returnValue = taskPosition;
            self.close();
        }
    }
    function initPage() {
        var vNext = document.mainFrm.nextUserId;
        if (vNext.length == 2) {
            vNext.value = vNext.options[1].value;
            // do_TransPara();
        }
    }
</script>