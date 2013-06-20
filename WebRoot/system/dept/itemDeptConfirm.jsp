<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2009-7-9
  Time: 16:27:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ include file="/newasset/headerInclude.jsp"%>

<html>

<head>
    <title>部门资产变更确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<body onkeydown="autoExeFunction('do_search()');">

<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("systemId");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String ou=(String)request.getAttribute(WebAttrConstant.ALL_ROLE_OPTION)  ;
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    String org=Integer.toString(user.getOrganizationId());
    String attribute1=reqParser.getParameter("attribute1");
%>

<form method="post" name="mainFrm">
    <script type="text/javascript">
            printTitleBar("部门资产变更确认")
    </script>
    <input type="hidden" name="matchType" value="11">
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司名称：</td>
            <td width="15%" align="left"><select name="organizationId"  style="width:80%"><%=ou%></select></td>
             <td width="10%" align="right">部门名称：</td>
            <td width="25%" align="left"><input name="deptName" style="width:80%" type="text" value="<%=reqParser.getParameter("deptName")%>"></td>
             <td width="10%" align="right">有效性：</td>
            <td width="25%" align="left"><select name="attribute1"><option value="">请选择</option><option value="Y" <%=attribute1.equals("Y")?"selected":""%>>有效</option><option value="N"  <%=attribute1.equals("N")?"selected":""%>>无效</option></select></td>

            <td align="center" valign="top" width="10%"><img src="/images/button/query.gif" style="cursor:'hand'" onclick="do_search();" alt="查询">
                <img src="/images/button/ok.gif" style="cursor:'hand'" onclick="do_match();" alt="确认">
            </td>
          </tr>
    </table>
    <input type="hidden" name="act" value="<%=reqParser.getParameter("act")%>">

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
          <%-- <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/> --%>
            <tr>
                <td width="2%" align="center">
                    <input type="checkBox" name="controlBox" class="headCheckbox" onClick="checkAll(this.name, 'systemId')">
                </td>
                <td height="22" width="10%" align="center">公司名称</td>
                <td height="22" width="40%" align="center">部门名称</td>
                <td height="22" width="8%" align="center">有效性</td>
                <td height="22" width="40%" align="center">新部门</td>
            </tr>

        </table>
    </div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:312px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" id="dataT" border="1" bordercolor="#666666">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onClick="executeClick(this)">
                <td width="2%" align="center"><input type="checkbox" name="systemId" id="systemId<%=i%>" value="<%=row.getStrValue("DEPT_CODE")%>;<%=i%>">
                    <input  type="hidden" name="ouId" id="ouId<%=i%>"  value="<%=row.getValue("COMPANY_CODE")%>">
                    <input type="hidden" name="deptCode" id="deptCode<%=i%>" value="<%=row.getValue("DEPT_CODE")%>">
                </td>
                <td style="word-wrap:break-word" height="22" width="10%"
                    align="center"><%=row.getValue("COMPANY")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="40%"
                    align="center"><%=row.getValue("DEPT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="8%"
                    align="center"><%=row.getValue("ENABLED_NAME")%>
                </td>
                <td  height="22" width="100%"
                    align="center" onclick="select_dept(this)"><input type="text" style="width:100%" name="newDeptName" id="newDeptName<%=i%>" value="" ><input name="newDept" type="hidden" id="newDept<%=i%>" value="">
                </td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>

<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<%=WebConstant.WAIT_TIP_MSG%>
<%--<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/> --%>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_choose(obj){
          var targetAction = "/match/rentEntity.jsp";
    var winstyle = "dialogWidth:20.1;dialogHeight:10.8;center:yes;status:no";
    var result = window.showModalDialog(targetAction, null, winstyle);
    if (result) obj.value = result;
    }
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.dept.servlet.AmsItemDeptConfirmServlet";
        mainFrm.submit();
    }
    function select_dept(obj) {
    
     var objName = obj.childNodes[0].name;
    var objId = obj.childNodes[0].id;
    var idNumber = objId.substring(objName.length);
    var ouName = document.getElementById("ouId" + idNumber).value;

      var upName = "<%=LookUpConstant.LOOK_UP_CHAN_MIS_DEPT%>";
      
    var dialogWidth = 50;
    var dialogHeight = 30;
	
    var userPara = "companyCode=" + document.getElementById("ouId" + idNumber).value;
	
    var users = getLookUpValues(upName, dialogWidth, dialogHeight,userPara);
    if (users) {
        obj.childNodes[0].value = users[0].deptName;
        obj.childNodes[1].value = users[0].deptCode;
    }else{
        obj.childNodes[0].value = "";
        obj.childNodes[1].value ="";
    }
	
    }
    function do_match() {

       
        var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
               var org= "<%=org%>";
          var obj = document.getElementById("dataT");
        var type=""          ;

           for (var i = 0; i < obj.rows.length; i++) {
                    var chk = document.getElementById("systemId" + i);
                    if (chk.checked) {
                         if(document.getElementById("newDept" + i).value==""){
                            alert("请选择新的责任部门！");
                              return;
                             break;

                         }
                    }
                }
                 if(confirm("确认需要更换资产的责任部门？确认请选择“确定”按钮，否定请点击“取消”按钮")){
            mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.system.dept.servlet.AmsItemDeptConfirmServlet";
            mainFrm.submit();
                     }
        }
    }
    function do_batch() {
        var checkedCount = getCheckedBoxCount("systemId");
        if (checkedCount < 1) {
            alert("请至少选择一条数据！");
            return;
        } else {
            var targetAction = "/match/rentEntity.jsp";
            var winstyle = "dialogWidth:20.1;dialogHeight:10.8;center:yes;status:no";
            var result = window.showModalDialog(targetAction, null, winstyle);
            var obj = document.getElementById("dataT");
            if (result) {
                for (var i = 0; i < obj.rows.length; i++) {
                    var chk = document.getElementById("systemId" + i);
                    if (chk.checked) {
                        document.getElementById("rentTypeName" + i).value = result;
                    }
                }
            }
        }
    }
</script>