<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.workorder.dto.EtsSuggestionDTO" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%--
  User: zhoujs
  Date: 2007-12-20 9:54:57
  Function:
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    SfUserDTO userAccount =(SfUserDTO)SessionUtil.getUserAccount(request);
    EtsSuggestionDTO suggestion = (EtsSuggestionDTO) request.getAttribute(WebAttrConstant.SUGGETION_DTO);
%>

<html>
<head>
<title>意见处理</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>

<script type="text/javascript">                        

function onRadChg(){
  var remark= document.mainFrm.remark;
  if(document.mainFrm.radio1[0].checked){
      if(remark.value=="不同意"||remark.value==""){
          remark.value="同意";
      }
  }else if(document.mainFrm.radio1[1].checked){
      if(remark.value=="同意"||remark.value==''){
          remark.value="不同意";
      }
  }
}
function doSub() {
    if (mainFrm.remark.value == "") {
        alert("请输入意见!");
        mainFrm.remark.focus();
        return;
    }

    mainFrm.title.value = mainFrm.remark.value.substring(0, 20);
    mainFrm.act.value="<%=WebActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
}
</script>
<base target="_self">
<body bgcolor="#E5FFF5" text="#000000" >

<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.EtsSuggestionServlet">
    <input type="hidden" name="systemid" value="<%=suggestion.getSystemid()%>">
    <input type="hidden" name="workorderBatch" value="<%=suggestion.getWorkorderBatch()%>">
    <input type="hidden" name="title"  value="<%=suggestion.getTitle()%>">
    <input type="hidden" name="completeFlag" value="<%=suggestion.getCompleteFlag()%>">
    <input type="hidden" name="handler" value="<%=suggestion.getHandler()%>">
    <input type="hidden" name="groupId" value="<%=suggestion.getGroupId()%>">
    <input type="hidden" name="act">
    <table align="center">
        <tr>
            <td width="59" height="9">
                <div align="center"><font size="2"></font></div>
            </td>
            <td><font size="2"><input type="radio" value="1" name="radio1" onClick="onRadChg();">同意&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
                    type="radio" value="0" name="radio1" onClick="onRadChg()">不同意</font></td>
        </tr>
        <tr>
            <td colspan='3' align=center><font size="2">
                <textarea cols="35" name="remark" rows="6"><%=suggestion.getRemark()%></textarea> </font>
            </td>
        </tr>
        <tr>
            <td width="100%" colspan='3'>
                <table width='70%' align='center'>
                    <tr>
                        <td align="center"><font size="2">
                            <input type="button" name="subButton" value="提交" onclick="doSub();">
                        </font>
                        </td>
                        <td align='center'><font size="2">
                            <input type="button" name="closeButtton" value="关闭" onClick="window.close();">
                        </font>
                        </td>

                    </tr>
                </table>
            </td>
        </tr>
    </table>

</form>
</body>
</html>

