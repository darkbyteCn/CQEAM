<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.ams.system.user.dto.SfGroupDTO"%>
<%--
  User: zhoujs
  Date: 2007-9-28
  Time: 10:05:36
  Function:选择用户当前组别
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    SfUserDTO userAccount=(SfUserDTO)session.getAttribute(WebConstant.USER_INFO);
    DTOSet groups=userAccount.getUserGroups();
%>
<html>
  <head>
      <title>选择当前组别</title>
   <script type="text/javascript">
       function do_select(){
           var idx=mainFrm.curGroupId.selectedIndex;
           if (idx > -1) {
               var curGroupId = mainFrm.curGroupId.options[idx].value;
               var curGroupName = mainFrm.curGroupId.options[idx].text;
               window.returnValue = curGroupId + "," + curGroupName;
               window.close();
           }else{
               alert("请选择你的组别！");
           }
       }
   </script>
  </head>
<body leftmargin="0" topmargin="0" background="/images/HeaderSlice.jpg">
  <form name="mainFrm" action="/servlet/com.sino.ams.workorder.servlet.WorkorderFlow">
    <table width="100%" border="0">
    <tr valign="top">
      <td colspan="2" height="191">
        <select name="curGroupId" size="12" style="width:250" >
            <%
                if (groups != null && groups.getSize() > 0) {
                    SfGroupDTO sfGroup=null;
                    for (int i = 0; i < groups.getSize(); i++) {
                         sfGroup =(SfGroupDTO) groups.getDTO(i);
            %>
            <option value="<%=sfGroup.getGroupId()%>"><%=sfGroup.getGroupname()%></option>
            <%
                    }
                }
            %>
        </select>
      </td>
      <td width="10%" height="191">
        <p>
          <%--<input type="button" name="Submit" value="确定" onClick="do_select()"><BR><br>--%>
          <%--<input type="button" name="Submit2" value="取消" onClick="do_cancel()">--%>
            <img border="0" src="/images/eam_images/ok.jpg" onclick="do_select();"><br><br><br><br>
			<img border="0" src="/images/eam_images/close.jpg" onclick="self.close();">
        </p>
      </td>
    </tr>
  </table>
  </form>
</body>
</html>