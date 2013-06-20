<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.sino.base.calen.SimpleDate" %>
<%@ page import="com.sino.base.constant.calen.DateConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.resource.dto.SfResDefineDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.framework.security.dto.FilterConfigDTO" %>
<%@ page import="com.sino.framework.security.dto.ServletConfigDTO" %>

<html>
    <%--<base target="main" />--%>

    <head>
    <title>资产管理系统</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/top.css" />
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/topMenu.css" />
    </head>


<body text="#000000" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<%
    SimpleDate dateObj = new SimpleDate();
	dateObj.setDateValue(System.currentTimeMillis());
    dateObj.setDatePattern(DateConstant.CHINESE_PATTERN);

    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	FilterConfigDTO filterConfig = SessionUtil.getFilterConfigDTO(application);
	ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(application);
%>

<table style="width: 100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td  class="left" style="width: 255px"></td>
		<td style="width: 650px"  >
		<%String istd = userAccount.getIsTd();
          String uname = userAccount.getLoginName();                                        	  
          if("Y".equals(istd)){%>
          <img src="/images/main/logo_04.jpg" width="653" height="78" />
          <% }else{%>
          <img src="/images/main/logo_03.jpg" width="653" height="78" />
          <%}%>
		</td>
		<td style="background-image:url(/images/main/right_04.jpg)" width="758" align="center" valign="top" >
            <table width="350" border=0 cellpadding="0" cellspacing="0"  
                               style="table-layout:fixed;word-wrap:break-word;vertical-align:top;" >
                            <tr height="45" >
                                <td align="right" width="240"><font class="topNav"><%=userAccount.getUsername()%>，欢迎您！</font></td>
                                <%--<td align="center" width="50"><font class="topNav">帮助</font></td>--%>
                                <td align="left" width="50"><img alt="注销" style="cursor: hand" src="/images/main/logOut.gif" onClick="do_LogOut();"></td>
                                <td align="left" width="10">&nbsp;</td>
                            </tr>
                        </table>
        </td>
	</tr>

	<tr>
		<td  class="nav1" colspan="23" style="height: 35px">
            <div id="menucase">
                <div id="styletwo">
                    <ul>
                        <li style="width: 30px; height: 16px"></li>
                        <li style="width: 68px; height: 16px"><a href="#" onclick="do_GoHomePage();">首页</a></li>
                        <%
                            DTOSet resources = (DTOSet) request.getAttribute(WebAttrConstant.ROOT_RESOURCE);
                            if (resources != null && !resources.isEmpty()) {
                                 SfResDefineDTO resourceDTO = null;
                                int colspanCount = resources.getSize();
                                for (int i = 0; i < colspanCount; i++) {
                                    resourceDTO = (SfResDefineDTO) resources.getDTO(i);
                        %>
                        <li style="vertical-align:bottom;"><a href="#" onClick="do_GoURL('<%=resourceDTO.getResId()%>'); return false"><%=resourceDTO.getResName()%>
                        </a></li>

                        <%
                                }
                            }
                        %>
                    </ul>
                </div>
            </div>
        </td>
	</tr>

    <tr>
        <td class="shadow" colspan="3">
            &nbsp;</td>
    </tr>
	</table>
</body>

</html>
<script type="text/javascript">
    function do_LogOut() {
        parent.location.href = "/servlet/com.sino.framework.security.servlet.UserLogOutServlet";
    }

    function do_GoURL(resId) {
        parent.contents.location.href = "/servlet/com.sino.sinoflow.framework.resource.servlet.MenuResourceAuthServlet?resourcePid=" + resId;
    }

    function do_GoHomePage() {
        parent.main.location.href ="/home.jsp";
    }
</script>