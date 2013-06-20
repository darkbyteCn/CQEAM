<%@ page import="com.sino.ams.newasset.constant.AssetsURLList"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
</head>
<%
    String mainPage = AssetsURLList.ITEM_MAINTAIN_SERVLET3;
    String financeReportURL = "/servlet/com.sino.ams.newasset.servlet.ItemFinanceReportServlet";
    //==========================由于目前数据库资产属性数据不准确，导致查询结果与实物台账查询不一致，暂时注释掉，今后再开放，唐明胜备注，切勿删除该段代码financeReportURL===============================
%>
<frameset name="contentFrm" cols="270,9,*" framespacing="0" border="0" frameborder="0" onload="window.focus()">
	<frame name="leftTree" scrolling="no" target="orderMain" src="<%=AssetsURLList.ITEM_FRM_TREE%>">
	<frame name="bar" src="/newasset/splitor2.htm" scrolling="no" noresize>
	<frameset rows="*,15px">
		<frame name="orderMain" src="<%=mainPage%>">
		<%--<frame name="footnotes" src="<%=financeReportURL%>">--%>

		<frame name="footnotes" src="">
	</frameset>
</frameset>
</html>
