<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2008-7-31
  Time: 11:05:33
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>成本部门对照</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<body  leftmargin= "0" topmargin = "0" onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String deptName = StrUtil.nullToString(request.getParameter("deptName"));
    String costCenterCode = StrUtil.nullToString(request.getParameter("costCenterCode")); 
%>
<form method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("成本部门对照")
    var ArrAction1 = new Array(true, "取消对照关系", "act_refresh.gif", "取消匹配", "dodematch()");
    var ArrAction2 = new Array(true, "关闭", "del.gif", "关闭", "window.close()");
    var ArrActions = new Array(ArrAction1, ArrAction2);
    var ArrSinoViews = new Array();
    printToolBar();
</script>
<table width="100%" border="0" bgcolor="#eeeeee">
        <tr>
            <td width="12%" align="right">成本中心：</td>
            <td width="26%"><input  style="width:85%"  name="costCenterCode" value = "<%=costCenterCode%>"><a href="#" onclick= "SelectCostCent();" class= "linka">[…]</a></td>
            <td width="13%" align="right">部门：</td>
            <td width="27%"><input name="deptName" style="width:85%" value = "<%=deptName%>"><a href= "#" onclick= "SelectDeptName()" class="linka">[…]</a></td>
            <td width="22%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
   <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">

    <table  border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
           <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('titleCheck','subCheck')"></td>
            <!--<td width="8%" align="center">地市</td>-->
            <td width="20%" align="center">成本中心代码</td>
            <td width="25%" align="center">成本中心</td>
            <td width="15%" align="center">部门代码</td>
            <td width="30%" align="center">部门名称</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">
<div style="overflow-y:scroll;left:0px;width:100%;height:360px">
    <table width="100%" border="1" >
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <%--<td width="8%" align="center" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("COMPANY")%></td>--%>
            <td height="22" width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("DEPT_ID")%>">
            </td>
            <td width="20%" ><%=row.getValue("COST_CODE")%></td>
            <td width="25%" align="left"><%=row.getValue("COST_NAME")%></td>
            <td width="15%" align="left"><%=row.getValue("DEPT_ID")%></td>
            <td width="30%" align="left"><%=row.getValue("DEPT_NAME")%></td>
        </tr>
<%
	    }
    }
%>
    </table>
</div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.cost.servlet.AmsMisCostMatchServlet";
    mainFrm.submit();
}

function show_detail(systemid) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.cost.servlet.AmsMisCostMatchServlet?systemid="+systemid;
    mainFrm.submit();
}

function do_add() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.cost.servlet.AmsMisCostMatchServlet";
    mainFrm.submit();
}

   function initPage() {
    }

   function dodematch() {
        if (getCheckedBoxCount("subCheck") <1) {
            alert("请先选择一条数据！");
            return;
        }
        if (confirm("确定要解除对照关系？")) {
             document.forms[0].action = "/servlet/com.sino.ams.system.cost.servlet.AmsMisCostMatchServlet?act=UN_MATCH";
//             window.opener.document.forms[0].action = "/system/cost/costDeptMatchFrame.jsp";
//             window.opener.document.forms[0].submit();
             document.forms[0].submit();
        }
    }

function SelectDeptName(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_MIS_DEPT%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
       document.mainFrm.deptName.value = vendorNames[0].deptName;
    }
}

 function SelectCostCent(){
    var  url="/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.COST_CENTER%>";
    var popscript = "dialogWidth:47.5;dialogHeight:30;center:yes;status:no;scrollbars:no";
    var vendorNames = window.showModalDialog(url, null, popscript);
    if(vendorNames){
        var vendorName = null;
//       document.mainFrm.costCenterCode.value = vendorNames[0].costCenterCode;
       document.mainFrm.costCenterCode.value = vendorNames[0].costCenterName;
        
    }
}

</script>