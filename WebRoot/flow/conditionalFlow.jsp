<%--
  User: Yung, Kam Hing
  Date: 2008-9-8
  Time: 10:05:36
  Function:选择用户当前的流向
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    String bypass = request.getParameter("bypass");
    if(bypass == null) {
        bypass = "";
    } else if(bypass.startsWith("\"") || bypass.startsWith("'")) {
        bypass = bypass.substring(1,bypass.length()-1);
    }
%>
<script type="text/javascript" src="/WebLibary/js/util.js">
</script>

<html>
  <head>
      <title>选择当前流向</title>
      <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
      <link href="/WebLibary/cms_css/cms_css.css" rel="stylesheet" type="text/css"/>
   <script type="text/javascript">
       window.returnValue = "";
       function fill_select() {
           var arg = window.dialogArguments;
           var flowsStr = arg[0];
           if(flowsStr.charAt(0) == '[')
               flowsStr = flowsStr.substring(1, flowsStr.length - 1);
           var flows = getFlowStr(flowsStr);
           var selectBox = document.getElementById("sf_curFlowDesc");
           var bypass = "<%=bypass%>";
           for(var i = 0; i < flows.length; i++) {
               if(bypass != "") {
                   var flowCode = getJsonData(flows[i], "flowCode:");
                   if(inList(bypass, flowCode, ";"))
                        continue;
               }
               var opt = new Option();
               opt.value = flows[i];
               opt.text = getJsonData(flows[i], "flowDesc:");
               selectBox.options[selectBox.options.length] = opt;
           }
       }

       function do_select() {
           var idx = document.getElementById("sf_curFlowDesc").selectedIndex;
           if (idx > -1) {
               var temp = document.getElementById("sf_curFlowDesc").options[idx].value;
               if(temp.charAt(0) != "[")
                   temp = "[" + temp + "]";
               window.returnValue = temp;
               window.close();
           } else {
               alert("请选择你的流向！");
           }
       }
/*
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
*/
       function do_cancel() {
           window.returnValue = "";
           window.close();
       }

   </script>
   
   <style>
	  .selec{border:0px solid #FFA500;}   
   </style>
   
  </head>
<body onload="fill_select()">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>
    <tr valign="top" height="100%">
      <td width="5%"></td>
      <td width="90%"colspan="2" align="center">
      
        <select name="sf_curFlowDesc" size="6" style="width:250px;" ondblclick="do_select()">
        </select>

      </td>
      </tr>
      <tr height="100%">
          <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
            <tr>
              <td align="center"><input name="Submit" type="button" class="but2" id="Submit" value="确定" onClick="do_select()"/></td>
              <td align="center"><input name="Submit2" type="button" class="but2" id="Submit2" value="取消" onClick="do_cancel()"/></td>
            </tr>
          </table>

    </tr>
  </table>

</body>
</html>