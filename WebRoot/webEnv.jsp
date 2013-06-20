<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.sino.soa.common.SOAConstant"%>
<%@page import="com.sino.hn.todo.util.HnOAConfig"%>
<%@page import="com.sino.framework.security.bean.SessionUtil"%>
<%@page import="com.sino.framework.security.dto.ServletConfigDTO"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLClassLoader"%>
<%-- 
 * @系统名称: 
 * @功能描述: 系统环境查看页面
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 30, 2011
--%>
<%
	try {
%>
<html>
	<head>
		<title>系统环境</title>    
	</head>

	<body > 
		<p>
			数据库信息：
			<%  
				
			%>
		</p>
		<p>
			SOA环境：<br>
			服务名:
			<%=SOAConstant.SERVER_NAME%><br>
			端口:
			<%=SOAConstant.SERVER_PORT%><br>
		</p>
		<br>

		<p>
			OA待办环境：<br>
			河南待办路径:
			<%=HnOAConfig.getTodo_url()%><br>
			河南待办用户名:
			<%=HnOAConfig.getTodo_username()%><br>
			Todo_password:
			<%=HnOAConfig.getTodo_password()%><br>
			EAM资产路径:
			<%=HnOAConfig.getEam_url()%><br>
		</p>
		<br>

		<p>
			SOA其他参数：<br>
			OrgStructureName:
			<%=HnOAConfig.getOrgStructureName()%><br>
			TDOrgStructureName:
			<%=HnOAConfig.getTDOrgStructureName()%><br>
			ProvinceCode:
			<%=HnOAConfig.getProvinceCode()%><br>
			TDProvinceCode:
			<%=HnOAConfig.getTDProvinceCode()%><br>
			ODIUser:
			<%=HnOAConfig.getODIUser()%><br>
			OaThreadSleepTime:
			<%=HnOAConfig.getOaThreadSleepTime()%><br>
			FlexValueSetNameMis:
			<%=HnOAConfig.getFlexValueSetNameMis()%><br>
			FlexValueSetNameTD:
			<%=HnOAConfig.getFlexValueSetNameTD()%><br>
			Loc1SetNameMis:
			<%=HnOAConfig.getLoc1SetNameMis()%><br>
			Loc1SetNameTD:
			<%=HnOAConfig.getLoc1SetNameTD()%><br>
		</p>
		<br>

		<%
			ServletConfigDTO servletConfig = SessionUtil
						.getServletConfigDTO(request);
		%>
		<p>
			web config 参数：<br>
			servletConfig.getEnvName :<%=servletConfig.getEnvName()%><br>
			servletConfig.getProvinceCode :<%=servletConfig.getProvinceCode()%><br>
			servletConfig.getProCompanyCode :<%=servletConfig.getProCompanyCode()%><br>
			servletConfig.getProCompanyName :<%=servletConfig.getProCompanyName()%><br>
			<br>

			servletConfig.getTdProvinceCode :<%=servletConfig.getTdProvinceCode()%><br>
			servletConfig.getTdProCompanyCode :<%=servletConfig.getTdProCompanyCode()%><br>
			servletConfig.getTdProvinceOrgId :<%=servletConfig.getTdProvinceOrgId()%><br>
			<br>


			servletConfig.getCompAssetsMgr :<%=servletConfig.getCompAssetsMgr()%><br>
			servletConfig.getDeptAssetsMgr :<%=servletConfig.getDeptAssetsMgr()%><br>
			servletConfig.getCityAdminRole :<%=servletConfig.getCityAdminRole()%><br>

		</p>
		<br>
	</body>
</html>

<% 

	} catch (Exception ex) {
		out.println( ex.getMessage() );
		ex.printStackTrace();
	}
%>


<%

ClassLoader systemClassloader=ClassLoader.getSystemClassLoader();
URL[] dd = ((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs();
for (int i = 0; i < dd.length; i++) {
    out.println("*********  "+dd[i] + "<BR>");
}
out.println(" system classloader : "+systemClassloader );


%>



<%

try{
	ClassLoader loader = this.getClass().getClassLoader();
	loader.loadClass( "org.apache.cxf.jaxws.spi.ProviderImpl" );
}catch(Throwable ex){
	out.println( "<br>Load Exception : " + ex.getMessage() );
}
%>


<%

try{ 
	ClassLoader.getSystemClassLoader().loadClass( "org.apache.cxf.jaxws.spi.ProviderImpl" );
}catch(Exception ex){
	out.println( "<br>System Load Exception : " + ex.getLocalizedMessage() );
}catch(Throwable ex){
	out.println( "<br>System Load Exception : " + ex.getLocalizedMessage() );
}
%>