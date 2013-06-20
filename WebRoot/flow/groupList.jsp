<%@ page import="com.sino.base.dto.DTOSet"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<%@ page import="com.sino.sinoflow.dto.SfGroupDTO" %>
<%--
  User: Yung,Kam Hing
  Date: 2008-9-08
  Time: 10:05:36
  Function:选择用户当前有专业的组别
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    DTOSet groups = (DTOSet) request.getAttribute(WebAttrConstant.GROUP_DTOSET);
%>
<html>
  <head>
      <title>选择当前组别</title>
   <script type="text/javascript">
       window.returnValue = "";
       function do_select() {
           var idx = document.getElementById("sf_curGroupId").selectedIndex;
           if (idx > -1) {
               window.returnValue = document.getElementById("sf_curGroupId").options[idx].value;
               window.close();
           } else {
               alert("请选择组别！");
           }
       }

       function do_close() {
            var idx = document.getElementById("sf_curGroupId").selectedIndex;
            if (idx > -1) {
                window.returnValue = document.getElementById("sf_curGroupId").options[idx].value;
//                window.close();
            } else {
                window.returnValue = "";
                event.returnValue = "请选择组别！不选择组别將会自动取消任务, 所有更改都会作废！";
            }
        }

   </script>
  </head>
<body onbeforeunload=do_close()>
  <!--form name="mainFrm" action=do_select() -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
       <td colspan="2">&nbsp;</td>
    </tr>
    <tr valign="top">
      <td width="5%" />
      <td width="90%" colspan="2" align="center">
        <select name="sf_curGroupId" size="10" style="width:100%" ondblclick="do_select()">
            <%
                if (groups != null && groups.getSize() > 0) {
                    SfGroupDTO sfGroup;
                    for (int i = 0; i < groups.getSize(); i++) {
                         sfGroup =(SfGroupDTO) groups.getDTO(i);
            %>
            <option value="<%=sfGroup.getGroupName()%>"><%=sfGroup.getGroupName()%></option>
            <%
                    }
                }
            %>
        </select>
      </td>
      </tr>
        <tr>
            <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td colspan="2">&nbsp;</td>
                </tr>
              <tr>
                <td align="center"><input name="Submit" type="button" class="but2" id="Submit" value="确定" onClick="do_select()"/></td>
              </tr>
            </table>

      </tr>        
  </table>
  <!--/form-->
</body>
</html>