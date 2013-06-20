<%@ page language="java" contentType="text/html; charset=GBK" errorPage="/error/ErrorDetail.jsp"%>
<%@page import="com.sino.ams.constant.URLDefineList"%>
<%
    RequestDispatcher rdis = request.getRequestDispatcher(URLDefineList.HN_PORTAL_SERVLET);
    rdis.forward(request, response);
%>