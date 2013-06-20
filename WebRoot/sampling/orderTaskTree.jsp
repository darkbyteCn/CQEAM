<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/sampling/headerInclude.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <script language="JavaScript" src="/WebLibary/js/MzTreeView10.js"></script>
    <title>抽查任务</title>
    <style type="text/css">
    body, td
    {
      font-family: 宋体;
      font-size: 12px;
    }
    A:LINK, A:VISITED, A:ACTIVE, A:HOVER
    {
      color: #225AD9;
      font-size: 12px;
      padding-left: 3px;
      TEXT-DECORATION: NONE;
    }
    </style>
    <base target="orderMain">
  </head>
<body leftmargin="0" topmargin="0" rightmargin="0">
<div id="$$$waitTipMsg$$$" style="position:absolute; bottom:45%; left:5; z-index:10; visibility:hidden">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td bgcolor="#ff9900">
				<table width="100%" height="60" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td bgcolor="#eeeeee" align="center">正在请求数据，请稍候...<img src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<%
	String taskTree = (String)request.getAttribute(SamplingWebAttributes.TASK_TREE);
	out.print(taskTree);
%>
<form name="mainFrm" action="" method="post">
</form>
</body>
</html>
<script>
function do_AssetsQuery(){
	parent.orderMain.document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}
</script>
