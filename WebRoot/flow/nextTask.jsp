<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.flow.constant.FlowURLDefineList" %>
<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page import="com.sino.flow.dto.SfTaskDefineDTO" %>


<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>下一任务选项</title>
    <link rel="stylesheet" type="text/css" href="../style/css/main.css">
    <script language="javascript" src="/style/js/IESTitleBar.js"></script>
</head>

<body>
<%
    String taskOptions = (String) request.getAttribute(ReqAttributeList.TASK_OPTIONS);
    if (StrUtil.isEmpty(taskOptions)) {
        return;
    }
    SfTaskDefineDTO taskDTO = (SfTaskDefineDTO) request.getAttribute(ReqAttributeList.TASK_DATA);
%>
<form name="mainFrm">

    <table width=100% align="center">
        <tr>
            <td>
                <script>
                    printTitleBar("选择下一任务流向")
                </script>
            </td>
        </tr>
        <tr>
            <td height=10px></td>
        </tr>
        <tr>
            <td align=center>
                <select name="nextTaskId" style="width:80%;text-align:center">
                    <option value="">请选择</option>
                    <%=taskOptions%>
                </select></td>
        </tr>
        <tr>
            <td height=10px></td>
        </tr>
        <tr>
            <td align=right>
                <p><img border="0" src="../images/eam_images/ok.jpg" width="63" height="18" onClick="do_TransPara();"></p>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script>
    var taskPosition = new Object();
    var url = '<%=FlowURLDefineList.USER_FIND_SERVLET%>'
    /*
forwardURL += "?nextTaskId=" + taskDTO.getTaskId();
    forwardURL += "&nextDeptId=" + taskDTO.getDeptId();
    forwardURL += "&nextDeptName=" + taskDTO.getDeptName();
    forwardURL += "&procId=" + taskDTO.getProcId();
    */
    function do_TransPara() {
        with (mainFrm) {
            /*   var taskValue = nextTaskId.value;
            var arr = taskValue.split("&&");
            taskPosition.nextTaskId = arr[0];
            taskPosition.nextDeptId = arr[1];
            taskPosition.nextDeptName = arr[2];
            taskPosition.procId=arr[3];
            taskPosition.nextPositionId = null;
            taskPosition.nextPositionName = null;
            taskPosition.nextUserId = null;
            window.returnValue = taskPosition;
            self.close();*/
            var vAction = url + '?nextTaskId=' + arr[0] + '&nextDeptId=' + arr[1] + '&nextDeptName=' + arr[2] + '&procId=' + arr[3];
            mainFrm.action = vAction;
            mainFrm.submit();
        }
    }
</script>
