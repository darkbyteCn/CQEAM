<%--
  User: zhoujs
  Date: 2007-10-31
  Time: 14:19:44
  Function:选择专业
--%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>


<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    SfUserDTO userAccount = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String categoryOpt = (String) request.getAttribute(WebAttrConstant.CATEGORY_OPTION);
    String workorderType = StrUtil.nullToString(request.getParameter("workorderType"));
%>
<html>
  <head>
      <title>选择专业</title>
   <script type="text/javascript">
       function do_select(){
           var idx=mainFrm.category.selectedIndex;
           if (idx > -1) {
               var category=mainFrm.category.value;
               var workorderType=mainFrm.workorderType.value;
               var url="/servlet/com.sino.ams.workorder.servlet.OrderEntryServlet?workorderType="+workorderType+"&category="+category;
               var winStyle="height=680px,width=1014px,top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes,help=no";
               window.open(url,"_blank",winStyle);                                   
               window.close();
           }else{
               alert("请选专业！");
           }
       }
   </script>
  </head>
<body>
  <form name="mainFrm" action="/servlet/com.sino.ams.workorder.servlet.OrderEntryServlet">
    <table width="100%" border="0">
    <tr valign="top">
      <td colspan="2" height="191">
        <select name="category" size="10" style="width:250" >
            <%=categoryOpt%>
        </select>
      </td>
      <td width="10%" height="191">
        <p>
          <input type="button" name="Submit" value="确定" onClick="do_select()"><BR><br>
          <%--<input type="button" name="Submit2" value="取消" onClick="do_cancel()">--%>
        </p>
      </td>
    </tr>
  </table>
      <input type="hidden" name="workorderType" value="<%=workorderType%>">
  </form>
</body>
</html>