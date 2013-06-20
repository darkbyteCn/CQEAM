<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.*" %>
<%@ page import="com.sino.base.data.*" %>
<%@ page import="com.sino.base.dto.*" %>
<%@ page import="com.sino.base.util.*" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.*" %>
<%@ page import="com.sino.flow.constant.FlowConstant" %>
<%@ page import="com.sino.ams.constant.*"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfRoleDTO" %>
<%@ page import="com.sino.ams.ct.dto.*" %>

<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.sms.dto.SfMsgCategoryDTO" %>
<%@ page import="com.sino.ams.system.user.dto.EtsOuCityMapDTO" %>
<%@ page import="com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfGroupDTO" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>