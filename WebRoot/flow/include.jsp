<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by wwb.
  User: demo
  Date: 2006-12-20
  Time: 11:29:52
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<input type="hidden" name="actId" id="actId" value="<%=StrUtil.nullToString(request.getParameter("actId"))%>">
<input type="hidden" name="taskProp" id="taskProp" value="<%=StrUtil.nullToString(request.getParameter("taskProp"))%>">
<input type="hidden" name="currTaskId" id="currTaskId" value="<%=StrUtil.nullToString(request.getParameter("currTaskId"))%>">
<input type="hidden" name="prevTaskId" id="prevTaskId" value="<%=StrUtil.nullToString(request.getParameter("prevTaskId"))%>">
<input type="hidden" name="prevUserId" id="prevUserId" value="<%=StrUtil.nullToString(request.getParameter("prevUserId"))%>">
<input type="hidden" name="prevUserName" id="prevUserName" value="<%=StrUtil.nullToString(request.getParameter("prevUserName"))%>">
<input type="hidden" name="prevPositionId" id="prevPositionId" value="<%=StrUtil.nullToString(request.getParameter("prevPositionId"))%>">
<input type="hidden" name="prevPositionName" id="prevPositionName" value="<%=StrUtil.nullToString(request.getParameter("prevPositionName"))%>">
<input type="hidden" name="needHander" id="needHander" value="<%=StrUtil.nullToString(request.getParameter("needHander"))%>">
<input type="hidden" name="isHandUser" id="isHandUser" value="<%=StrUtil.nullToString(request.getParameter("isHandUser"))%>">


<input type="hidden" name="nextTaskId" id="flownextTaskId">
<input type="hidden" name="nextDeptId" id="flownextDeptId">
<input type="hidden" name="nextDeptName" id="flownextDeptName">
<input type="hidden" name="nextPositionId" id="flownextPositionId">
<input type="hidden" name="nextPositionName" id="flownextPositionName">
<input type="hidden" name="nextUserId" id="flownextUserId">
<input type="hidden" name="nextUserName" id="flownextUserName">
<input type="hidden" name="procId" id="flowprocId" value="<%=StrUtil.nullToString(request.getParameter("procId"))%>">
<input type="hidden" name="currDeptId" id="flowcurrDeptId">
<input type="hidden" name="currDeptName" id="flowcurrDeptName">
<input type="hidden" name="approveOpinion" id="approveOpinion">
<input type="hidden" name="transfer" id="transfer"><!--转发标记-->                 



