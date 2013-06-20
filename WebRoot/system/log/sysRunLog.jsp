<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="java.io.File" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="java.util.Arrays" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
</head>
<body style="overflow-y:auto">
<script type="text/javascript">
    printTitleBar("系统日志监控");
</script>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.log.servlet.SystemRunLogServlet">
<%
	String logPath = (String)request.getAttribute("LOG_PATH");
	File parent = new File(logPath);
	File[] logFiles = parent.listFiles();

	if (logFiles != null && logFiles.length > 0) {
        java.util.Arrays.sort(logFiles);
		int fileCount = logFiles.length;
		String fileName = "";
		out.print("<p>");
		for (int i = 0; i < fileCount; i++) {
%>
		<a href="" onClick="do_DownLoad('<%=logFiles[i].getName()%>'); return false;"><img border="0" src="/images/eventLog.png">系统日志<%=logFiles[i].getName()%></a>
		<a href="" onClick="do_Delete('<%=logFiles[i].getName()%>'); return false;"><img border="0" src="/images/delete.png" alt="点击删除该日志"></a>
<%
			if ((i + 1) % 4 == 0) {
				out.print("</p><p>");
			}
		}
	}
%>

<input type="hidden" name="act">
<input type="hidden" name="fileName">
</form>
<iframe src="" style="display:none" name="downFrm" id="downFrm"></iframe>
</body>
</html>
<script>
function do_DownLoad(fileName){
	mainFrm.fileName.value = fileName;
	mainFrm.act.value = "<%=WebActionConstant.DOWNLOAD_ACTION%>";
	mainFrm.target = "downFrm";
	mainFrm.submit();
}
function do_Delete(fileName){
	if (confirm("是否确定要删除!")) {
		mainFrm.fileName.value = fileName;
		mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
		//	mainFrm.target = "downFrm";
		mainFrm.submit();
	}
}
</script>