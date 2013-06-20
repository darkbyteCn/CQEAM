<%@ page import="java.sql.Connection" %>
<%@ page import="com.sino.base.db.conn.DBManager" %>
<%@ page import="com.sino.base.db.query.SimpleQuery" %>
<%@ page import="com.sino.base.db.sql.model.SQLModel" %>
<%--
  User: zhoujs
  Date: 2009-6-26 17:22:23
  Function:
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>数据库测试页面</title></head>
  <body>
  <%
      Connection conn=null;
      try {
          conn=DBManager.getDBConnection();
          SQLModel sqlModel=new SQLModel();
          String sqlStr="SELECT getdate()";
          sqlModel.setSqlStr(sqlStr);
          SimpleQuery sq=new SimpleQuery(sqlModel,conn);
          sq.executeQuery();
          if (sq.hasResult()) {
              out.println("数据连接正常");
          }else{
              response.sendRedirect("dbError.jsp");
          }
      } finally {
          DBManager.closeDBConnection(conn);
      }
  %>

  </body>
</html>