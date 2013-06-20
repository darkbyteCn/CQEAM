<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>

<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.*" %>

<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>

<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>

<%@ page import="com.sino.ams.match.dto.BarcodeMatchDTO" %>
<%@ page import="com.sino.ams.synchronize.dto.EamSyschronizeDTO" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
