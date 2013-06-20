<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/sampling/headerInclude.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-3-14
  Time: 12:07:48
  To change this template use File | Settings | File Templates.
--%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <script language="JavaScript" src="/WebLibary/js/MzTreeView10.js"></script>
    <script language="JavaScript" src="/WebLibary/js/AppStandard.js"></script>
    <title>公司部门</title>
    <style type="text/css">
    body, td
    {
      font-family: 宋体;
      font-size: 12px;
    }
    A:LINK, A:VISITED, A:ACTIVE, A:HOVER
    {
      color: #373737;
      font-size: 12px;
      padding-left: 3px;
      TEXT-DECORATION: NONE;
    }
    </style>
    <script type="text/javascript">
        var doc = parent.orderMain.document;
    </script>
    <base target="orderMain">
  </head>
<body leftmargin="0" topmargin="0" rightmargin="0" onload="do_SetPageWidth()">
<div id="headDiv" style="position: relative;width: 100%">
</div>
<div id="dataDiv" style="overflow: scroll; height: 100%; width: 100%; position: absolute; top: 25px; left: 0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
	String itemTree = (String)request.getAttribute(AssetsWebAttributes.ITEM_TREE);
	out.print(itemTree);
%>
</div>
<div id="pageNaviDiv">
</div>
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
<form name="mainFrm" action="" method="post">
</form>
</body>
</html>
<script>
function do_AssetsQuery(){
	parent.orderMain.document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}
</script>