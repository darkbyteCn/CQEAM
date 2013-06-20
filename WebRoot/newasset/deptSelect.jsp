<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>

<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
<html>
<head>
    <title>选择部门</title>
</head>
<body leftmargin="0" topmargin="0" background="/images/HeaderSlice.jpg">
<table border="0" width="100%" id="table1">
    <tr>
        <td width="5%" height="25">　</td>
        <td width="90%" height="25" align="center">选择部门</td>
        <td width="5%" height="25">　</td>
    </tr>
    <tr>
        <td width="5%" height="160">　</td>
        <td width="90%" height="160">
            <select name="deptCode" size="10" style="width:100%;height:100%" ondblclick="do_SelectDept()"
                    title="双击选择该部门">
                <%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%>
			</select>
		</td>
		<td width="5%" height="160">
			<img border="0" src="/images/eam_images/ok.jpg" onclick="do_SelectDept();"><br><br><br><br>
			<img border="0" src="/images/eam_images/close.jpg" onclick="self.close();">
		</td>
	</tr>
</table>
</body>
</html>
<script>
function do_SelectDept(){
	var dept = new Object();
	dept.deptCode = getSelectValue("deptCode", ";");
	dept.deptName = getSelectText("deptCode", ";");
	window.returnValue = dept;
	window.close();
}
</script>
