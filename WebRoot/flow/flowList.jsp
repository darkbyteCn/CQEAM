<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.exception.ContainerException" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>

<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择用户当前的流向
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    RowSet flows = (RowSet) request.getAttribute(WebAttrConstant.TASK_FLOW_ROWSET);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
      <title>选择当前流向</title>
   <script type="text/javascript">
       function do_select() {
           var idx = document.getElementById("sf_curFlowDesc").selectedIndex;
           if (idx > -1) {
               window.returnValue = document.getElementById("sf_curFlowDesc").options[idx].value;
               window.close();
           } else {
               alert("请选择你的流向！");
           }
       }

       function do_close() {
            var idx = document.getElementById("sf_curFlowDesc").selectedIndex;
            if (idx > -1) {
                window.returnValue = document.getElementById("sf_curFlowDesc").options[idx].value;
//                window.close();
            } else {
                window.returnValue = "";
                event.returnValue = "请选择你的流向！不选择流向將不能继续";
            }
        }

       function do_cancel() {
           window.returnValue = "";
           window.close();
       }

   </script>
  </head>
<body>
  <!--form name="mainFrm" action=do_select() -->
    <table width="425" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
      <td width="90%" align="center" colspan="2" height="191">
        <select name="sf_curFlowDesc" size="8" style="width:250px" ondblclick="do_close()">
            <%
                if (flows != null && flows.getSize() > 0) {
                    Row row;
                    String flowDesc = "";
                    String jsonFlowStr = "";
                    for (int i = 0; i < flows.getSize(); i++) {
                        try {
                          jsonFlowStr = "";
                            row = flows.getRow(i);
                            flowDesc = row.getStrValue("FLOW_DESC");
                            jsonFlowStr += "[{taskID:'" + row.getStrValue("TASK_ID") + "', ";
                            jsonFlowStr += "procName:'" + row.getStrValue("PROCEDURE_NAME") + "', ";
                            jsonFlowStr += "taskName:'" + row.getStrValue("TASK_NAME") + "', ";
                            jsonFlowStr += "taskDesc:'" + row.getStrValue("TASK_DESC") + "', ";
                            jsonFlowStr += "groupName:'" + row.getStrValue("GROUP_NAME") + "', ";
                            jsonFlowStr += "roleName:'" + row.getStrValue("ROLE_NAME") + "', ";
                            jsonFlowStr += "taskDur:'"  + row.getStrValue("DURATION") + "', ";
                            jsonFlowStr += "taskWorktype:'" + row.getStrValue("DURATION_UNIT") + "', ";
                            jsonFlowStr += "taskCtlType:'" + row.getStrValue("CONTROL_TYPE") + "', ";
                            jsonFlowStr += "taskCycleType:'" + row.getStrValue("CYCLE_TYPE") + "', ";
                            jsonFlowStr += "taskApi:'" + row.getStrValue("API") + "', ";
                            jsonFlowStr += "taskDivRight:'" + row.getStrValue("DIV_RIGHT") + "', ";
                            jsonFlowStr += "taskDivHidden:'" + row.getStrValue("DIV_HIDDEN") + "', ";
                            jsonFlowStr += "taskFlowType:'" + row.getStrValue("TASK_FLOW_TYPE") + "', ";
                            jsonFlowStr += "realnames:'" + row.getStrValue("REAL_NAMES") + "', ";
                            jsonFlowStr += "usernames:'" + row.getStrValue("USERNAMES") + "', ";
                            jsonFlowStr += "flowProp:'" + row.getStrValue("FLOW_PROP") + "', ";
                            jsonFlowStr += "flowID:'" + row.getStrValue("FLOW_ID") + "', ";
                            jsonFlowStr += "flowDesc:'" + flowDesc + "', ";
                            jsonFlowStr += "flowCode:'" + row.getStrValue("FLOW_CODE") + "', ";
                            jsonFlowStr += "flowType:'" + row.getStrValue("FLOW_TYPE") + "',";
                            jsonFlowStr += "cycleQty:'0', ";
                            jsonFlowStr += "cycleUser:'', ";
                            jsonFlowStr += "cycleType:'', ";
                            jsonFlowStr += "reviewQty:'0', ";
                            jsonFlowStr += "reviewUsers:'', ";
                            jsonFlowStr += "reviewType:''}]";
                        } catch(ContainerException e) {

                        }
            %>
            <option value="<%=jsonFlowStr%>"><%=flowDesc%></option>
            <%
                    }
                }
            %>
        </select>
      </td>
        <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><input name="Submit" type="button" class="but2" id="button" value="确定" onClick="do_select()"/></td>
            <td align="center"><input name="Submit2" type="button" class="but2" id="button2" value="取消" onClick="do_cancel()"/></td>
          </tr>
        </table>

    </tr>
  </table>
  <!--/form-->
</body>
</html>