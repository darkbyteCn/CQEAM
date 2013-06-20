<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
<head>
<title>分隔线页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<%
    String contextPath = request.getContextPath();
%>
<body text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="white">
<table width="7" border="0" height="100%" cellpadding="0" cellspacing="0" align="left">
  <tr align="center">
    <td background="<%=contextPath%>/images/splitter_bg.gif">
      <div id="taskTree" onClick="do_ControlFrm();" title="隐藏工具栏"><img src="<%=contextPath%>/images/splitter_l.gif" name="frameshow" width="9" height="79" id=frameshow ></div>
    </td>
  </tr>
</table>
</body>
</html>
<script>
function do_ControlFrm(){
	if(parent.contentFrm.cols=="270,9,*"){
		frameshow.src="<%=contextPath%>/images/splitter_r.gif";
		taskTree.title="隐藏工具栏";
		parent.contentFrm.cols="0,9,*";
	} else{
		frameshow.src="<%=contextPath%>/images/splitter_l.gif";
		taskTree.title="显示工具栏";
		parent.contentFrm.cols="270,9,*";
	}
}
</script>
