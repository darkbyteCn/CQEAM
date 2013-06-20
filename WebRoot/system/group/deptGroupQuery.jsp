<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.sinoflow.constant.LookUpConstant" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>部门与组别对应关係维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/help.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
</head>
<style type="text/css">
    .finput {
        WIDTH: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
    }

    .finput2 {
        WIDTH: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        text-align: center;
    }

    .finput3 {
        WIDTH: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        text-align: right;
        padding-right: 4px
    }

    .fDtlInput {
        WIDTH: 100%;
        border-style: solid;
        background-color: #F2F9FF;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        text-align: left
    }

    ;
    .finputNoEmpty {
        WIDTH: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        text-align: left;
        BACKGROUND-COLOR: #FFFF99
    }

    ;
    .finputNoEmpty2 {
        WIDTH: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        text-align: center;
        BACKGROUND-COLOR: #FFFF99
    }

    ;
    .finputNoEmpty3 {
        WIDTH: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        text-align: right;
        BACKGROUND-COLOR: #FFFF99;
        padding-right: 4px
    }

    .inputNoEmptySelect {
        WIDTH: 100%;
        height: 100%;
        BORDER-RIGHT: 0px ridge;
        BORDER-TOP: 0px ridge;
        BORDER-LEFT: 0px ridge;
        BORDER-BOTTOM: 0px ridge;
        font-size: 12px;
        BACKGROUND-COLOR: #FFFF99;
        cursor: pointer;
    }
</style>

<body leftmargin="1" topmargin="0" onload="do_SetPageWidth()">
<input type="hidden" name="helpId" value="">
<jsp:include page="/message/MessageProcess"/>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String groupName = reqParser.getParameter("group");
    String deptName = reqParser.getParameter("dept");
    String projectName = reqParser.getParameter("project");
    String action = reqParser.getParameter("act");
    if(action.equals("SAVE_ACTION") || action.equals("CREATE_ACTION")) {
        action = reqParser.getParameter("preAct"); 
    }
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.sinoflow.servlet.DeptGroupServlet">
    <script language="javascript">
        var ArrAction1 = new Array(true, "查询", "act_query.gif", "查询", "do_search()");
        var ArrAction2 = new Array(true, "查询未匹配部门", "act_search.gif", "查询未匹配部门", 'do_match()');
        var ArrAction3 = new Array(true, "保存此页选中行", "action_draft.gif", "保存此页选中行", 'do_save()');
        var ArrAction4 = new Array(true, "导出Excel", "toexcel.gif", "导出Excel", 'do_Export()');
        var ArrActions = new Array(ArrAction1, ArrAction2, ArrAction3, ArrAction4);
        var ArrSinoViews = new Array();
        printTitleBar("部门与组别对应关係维护");
        printToolBar();
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="preAct" value="<%=action%>">
    <input type="hidden" name="sfProject" value="资产管理工程">

    <table border="0" width="100%" id="table1">
        <tr>
            <td width="12%" align="right">项目名称：</td>
            <td width="15%"><input type="text" name="project"  class="input_style1" style="width:100%" value="<%=projectName%>"></td>
            <td width="1%"></td>
            <td width="12%" align="right">部门名称：</td>
            <td width="15%"><input type="text" name="dept"  class="input_style1" style="width:100%" value="<%=deptName%>"></td>
            <td width="9%"></td>
            <td width="12%" align="right">组别名称：</td>
            <td width="15%"><input type="text" name="group"  class="input_style1" style="width:100%" value="<%=groupName%>"></td>
            <td width="9%"></td>
        </tr>
    </table>

    <script type="text/javascript">
//        var columnArr = new Array("checkbox","项目名称","部门名称", "组别名称", "上级部门", "显示序号", "二级部门", "专业部门");
//        var widthArr = new Array("3%","10%", "27%", "21%", "21%", "6%", "6%", "6%");
        var columnArr = new Array("checkbox","项目名称","部门名称", "组别名称", "上级部门", "二级部门", "专业部门");
        var widthArr = new Array("3%","10%", "30%", "21%", "24%", "6%", "6%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div id="dataDiv" style="overflow-y:scroll;width:100%;left:1px;margin-left:0px" onscroll="document.getElementById('headDiv').scrollLeft=this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" style="table-layout:fixed;">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    String iStr = String.valueOf(i);
                    String subCheckL = "subCheck" + iStr;
                    String isCheckL = "isCheck" + iStr;
                    String projectNameL = "projectName" + iStr;
                    String projectNameV = row.getStrValue("PROJECT_NAME");
                    String deptNameL = "deptName" + iStr;
                    String deptNameV = row.getStrValue("DEPT_NAME");
                    String deptIdL = "deptId" + iStr;
                    String deptIdV = row.getStrValue("DEPT_ID");
                    String groupNameL = "groupName" + iStr;
                    String groupNameV = row.getStrValue("GROUP_NAME");
                    String curGroupNameL = "curGroupName" + iStr;
                    String groupIdL = "groupId" + iStr;
                    String groupIdV = row.getStrValue("GROUP_ID");
                    String parentNameL = "parentName" + iStr;
                    String parentNameV = row.getStrValue("PARENT_NAME");
                    String parentIdL = "parentId" + iStr;
                    String parentIdV = row.getStrValue("PARENT_ID");
                    String displayOrderL = "displayOrder" + iStr;
                    String displayOrderV = row.getStrValue("DISPLAY_ORDER");
                    String secondDeptL = "secondDept" + iStr;
                    String secondDeptV = row.getStrValue("SECOND_DEPT") ;
                    String specialityDeptL = "specialityDept" + iStr;
                    String sDept = row.getStrValue("SPECIALITY_DEPT");
                    if(sDept.equals(""))
                        sDept = "N";
                    String specialityDeptV = sDept;
                    String orgIdV = row.getStrValue("ORG_ID");
            %>
            <tr class="dataTR">
                <td width="3%" style="cursor:pointer" align="center"><input type="checkbox" name="subCheck" ></td>
                <input type="hidden" name="isCheck" id="<%=isCheckL%>" value="">
                <td width="10%" style="cursor:pointer" ><input type="text" name="projectName" id="<%=projectNameL%>" value="<%=projectNameV%>" class="finput"></td>
                <td width="30%" style="cursor:pointer" ><input type="text" name="deptName" id="<%=deptNameL%>" value="<%=deptNameV%>" class="finput"></td>
                <input type="hidden" name="deptId" id="<%=deptIdL%>" value="<%=deptIdV%>">
                <td width="21%" style="cursor:pointer" ><input type="text" name="groupName" id="<%=groupNameL%>" value="<%=groupNameV%>" class="finput"></td>
                <input type="hidden" name="curGroupName" id="<%=curGroupNameL%>" value="<%=groupNameV%>">
                <input type="hidden" name="groupId" id="<%=groupIdL%>" value="<%=groupIdV%>">
                <td width="24%" style="cursor:pointer" ><input style="width:85%" type="text" name="parentName" id="<%=parentNameL%>" value="<%=parentNameV%>" class="finput" readonly="true" onclick="selectParentDept(<%=i%>,'<%=orgIdV%>')"><input style="width:14.9%" type="button" name="pButton" value="清空" onclick="clearParent(<%=i%>)"></td>
                <input type="hidden" name="parentId" id="<%=parentIdL%>" value="<%=parentIdV%>">
                <!--<td width="6%" style="cursor:pointer" ><input type="text" name="displayOrder" id="<%=displayOrderL%>" value="<%=displayOrderV%>" class="finput"></td>-->
                <td width="6%" style="cursor:pointer" ><input type="text" name="secondDept" id="<%=secondDeptL%>" value="<%=secondDeptV%>" class="finput" onkeypress="return yesOrNo(this)" onkeydown="return keysInvalid()"></td>
                <td width="6%" style="cursor:pointer" ><input type="text" name="specialityDept" id="<%=specialityDeptL%>" value="<%=specialityDeptV%>" class="finput" onkeypress="return yesOrNo(this)" onkeydown="return keysInvalid()"></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
    <%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">
function do_search() {
    document.mainFrm.act.value = "QUERY_ACTION";
    document.mainFrm.submit();
}

function do_match() {
    document.mainFrm.act.value = "MATCH_ACTION";
    document.mainFrm.submit();
}

function do_save() {
    var checkArr = document.getElementsByName("subCheck");
    var isCheckArr = document.getElementsByName("isCheck");
    var groupArr = document.getElementsByName("groupName");
    var curGroupArr = document.getElementsByName("curGroupName");
    var deptNameArr = document.getElementsByName("deptName");
    var indexsStr = "";
    var newStr = "";
    for(var i = 0; i < checkArr.length; i++) {
        if(checkArr[i].checked) {
            isCheckArr[i].value = "1";
            if(indexsStr == "") {
                indexsStr = "" + i;
            } else {
                indexsStr += "," + i;
            }
            if(curGroupArr[i].value != groupArr[i].value) {
                if(newStr == "") {
                    newStr = "'" + groupArr[i].value + "'";
                } else {
                    newStr += ",'" + groupArr[i].value + "'";
                }
            }
            if(deptNameArr[i].value == "") {
                alert("错误:第 " + i + " 行的部门名称为空!");
                return;
            }
            if(groupArr[i].value == "") {
                alert("错误:第 " + i + " 行的组别名称为空!");
                return;
            }
        } else {
            isCheckArr[i].value = "0";
        }
    }
    if(indexsStr == "") {
        alert("沒有选中任何数据!");
        return;
    }
    var errorGroups = checkNewGroupName(indexsStr, newStr);
    if(errorGroups != "") {
//        alert("组别名称检查错误, 请确认已设置父组别 " + errorGroups + "!");
        alert(errorGroups);
        return;
    }
    if(document.mainFrm.preAct.value == "QUERY_ACTION") {
        document.mainFrm.act.value = "SAVE_ACTION";
        document.mainFrm.submit();
    } else if(document.mainFrm.preAct.value == "MATCH_ACTION"){
        document.mainFrm.act.value = "CREATE_ACTION";
        document.mainFrm.submit();
    }
}

function checkNewGroupName(indexsStr, newGroups) {
    var groupArr = document.getElementsByName("groupName");
    var curGroupArr = document.getElementsByName("curGroupName");
    var selectArr;
    if(indexsStr.indexOf(",") >= 0) {
        selectArr = indexsStr.split(",");
    } else {
        selectArr = new Array(1);
        selectArr[0] = indexsStr;
    }
    var groups = "";
    for(var i = 0; i < selectArr.length; i++) {
        var index = selectArr[i];
        if(groups == "") {
            if(groupArr[index].value != curGroupArr[index].value)
                groups = groupArr[index].value;
        } else {
            if(groupArr[index].value != curGroupArr[index].value)
                groups += "," + groupArr[index].value;
        }
    }
    if(groups == "") {
        return "";
    }
    return checkValidGroup(groups, newGroups);
}

function checkValidGroup(groups, newGroups){
	var url = "/servlet/com.sino.sinoflow.servlet.IsGroupsValid?groups='"
            + groups.replaceAll("+", "%2B") + "'&newGroups=" + newGroups;
	makeRequest(url, ajaxFunction);
	return ajaxReturn;
}

function selectParentDept(index, orgId) {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_PARENT_DEPT%>";
    var dialogWidth = window.screen.availWidth * 0.8;
    var dialogHeight = window.screen.availHeight * 0.8;
    var userPara = "orgId=" + orgId
    var depts = lookUpValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (depts) {
        var parentNameArr = document.getElementsByName("parentName");
        var parentIdArr = document.getElementsByName("parentId");
        parentNameArr[index].value = depts[0].deptName;
        parentIdArr[index].value = depts[0].deptId;
    }
}

function lookUpValues(lookUpName, dialogWidth, dialogHeight, userPara) {
    var url = "/servlet/com.sino.sinoflow.bean.SfLookUpServlet?lookUpName=" + lookUpName;
    if(userPara != "undefined" && userPara != null){
        url += "&" + userPara;
    }
    var popscript = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;resizable:yes";
//			window.open(url);
    return window.showModalDialog(url, null, popscript);
}

function yesOrNo(obj) {
    // 只允许 'Y', 'y', 'N', 'n' 并把其換成大写
    if(event.keyCode==89||event.keyCode==121||event.keyCode==78||event.keyCode==110) {
        obj.value = "";
        if(event.keyCode == 121)
            event.keyCode = 89;
        else if(event.keyCode == 110)
            event.keyCode = 78
        return true;
    } else {
        return false;
    }
}

function keysInvalid() {
    // backspace 与 del 无效
    if(event.keyCode==8||event.keyCode==126||event.keyCode==46)
        return false;
    else
        return true;
}

function clearParent(index) {
    document.getElementsByName("parentName")[index].value = "";
    document.getElementsByName("parentId")[index].value = "";
}

function do_Export(){
    if(document.mainFrm.preAct.value == "QUERY_ACTION") {
        document.mainFrm.act.value = "EXPORT_ACTION";
    } else if(document.mainFrm.preAct.value == "MATCH_ACTION"){
        document.mainFrm.act.value = "MATCH_EXPORT_ACTION";
    }
	mainFrm.submit();
}
</script>