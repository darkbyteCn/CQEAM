<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <script language="JavaScript" src="/WebLibary/js/MzTreeView10.js"></script>
    <title>资产权限管理</title>
    <style type="text/css">
    body, td
    {
      font-family: 宋体;
      font-size: 12px;
    }
    A:LINK, A:VISITED, A:ACTIVE, A:HOVER
    {
      color: #08447C;
      font-size: 12px;
      padding-left: 3px;
      TEXT-DECORATION: NONE;
    }
    </style>
  </head>
<body leftmargin="0" rightmargin="0" topmargin="0">
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);    
	String deptTree = (String) request.getAttribute(AssetsWebAttributes.PRIVI_DEPT_TREE);
	if (!StrUtil.isEmpty(deptTree)) {
		out.print(deptTree);
	} else {
		out.print("there's no dept data");
	}
%>
</body>
</html>
<script>
function do_ConfirmSvaePrivi(){
	var isValid = true;
	if(parent.priviMain.mainFrm.saved){
		var hasSaved = parent.priviMain.mainFrm.saved.value;
		if(hasSaved == "false"){
			if(!confirm("不保存而切换部门或公司，你的工作将丢失，是否继续？继续请点击“确定”按钮？否则请点击“取消”按钮！")){
				isValid = false;
			}
		}
	}
	return isValid;
}
</script>
