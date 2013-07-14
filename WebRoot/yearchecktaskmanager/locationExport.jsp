<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
    <form name="MyForm" action="/servlet/com.sino.ams.yearchecktaskmanager.servlet.AssetsRespMapLocationServlet" method="post">
		 <input type="hidden" name="action" value="EXPORT_LOCATION">
    	 <table border = "0" width="100%">
    		<a href="#" onclick="do_export_locations()"  style="cursor:pointer;text-decoration:underline;color:blue"><FONT COLOR="0000ff" size ="2">无线地点集合导出</FONT></a>
    	</table>
    </form>
  </body>
<script type="text/javascript">
	function do_export_locations(){
        document.MyForm.submit();
	}
</script>  
</html>
