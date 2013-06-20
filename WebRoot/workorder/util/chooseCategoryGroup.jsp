<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfGroupDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>

<%--
  User: zhoujs
  Date: 2007-9-28
  Time: 10:05:36
  Function:选择用户当前有专业的组别
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    SfUserDTO userAccount = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String workorderType = StrUtil.nullToString(request.getParameter("workorderType"));
    DTOSet groups = (DTOSet) request.getAttribute(WebAttrConstant.GROUP_DTOSET);
%>
<html>
  <head>
      <title>选择当前组别</title>
   <script type="text/javascript">
       function do_select() {
           var idx = mainFrm.curGroupId.selectedIndex;
           if (idx > -1) {
               var val = mainFrm.curGroupId.value;
               var arr=val.split(";");
               var groupId=arr[0];
               var groupName=arr[1];
               var category=arr[2];
               var workorderType = mainFrm.workorderType.value;
			   var url = "";
			   var winStyle = "";
<%--			   if(workorderType != "<%=DictConstant.ORDER_TYPE_HDV%>"){--%>
//					url = "/servlet/com.sino.ams.workorder.servlet.OrderEntryServlet?workorderType=" + workorderType;
                 var url =  '/servlet/com.sino.sinoflow.servlet.NewCase?sf_appName='+'workorderapp';
                    winStyle = "width="+window.screen.width+",height="+window.screen.height+",top=0,left=0,toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=yes";
<%--			   } else {--%>
<%--					url = "/servlet/com.sino.ams.workorder.servlet.HandOverBatchServlet?workorderType=" + workorderType;--%>
<%--					url += "&act=<%=WebActionConstant.NEW_ACTION%>";--%>
<%--					winStyle = "height=680px,width=1014px,top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=yes,help=no";--%>
<%--			   }--%>
			   url += "&category=" + category;
			   url += "&groupId=" + groupId;
			   url += "&groupName=" + groupName;
               window.open(url, "_blank", winStyle);
               window.close();
           } else {
               alert("请选择你的组别！");
           }
       }
    
   </script>
  </head>
<body>
  <form name="mainFrm" action="/servlet/com.sino.ams.workorder.servlet.OrderEntryServlet">
    <table width="100%" border="0">
    <tr valign="top">
      <td colspan="2" height="191">
        <select name="curGroupId" size="8" style="width:250" >
            <%
                if (groups != null && groups.getSize() > 0) {
                    SfGroupDTO sfGroup=null;
                    for (int i = 0; i < groups.getSize(); i++) {
                         sfGroup =(SfGroupDTO) groups.getDTO(i);
                        String groupCat=sfGroup.getGroupId()+";"+sfGroup.getGroupName()+";"+"BTS";
                        System.out.println(groupCat);
            %>
            <option value="<%=groupCat%>"><%=sfGroup.getGroupName()%></option>
            <%
                    }
                }
            %>
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