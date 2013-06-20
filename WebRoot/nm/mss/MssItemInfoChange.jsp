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
<%@ include file="/newasset/headerInclude.jsp" %>

<html>

<head>
    <title>部门物资信息维护</title>
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
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>

<!-- <body onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();do_drop()">-->
<body onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();">

<%
    RequestParser reqParser = new RequestParser();
    CheckBoxProp checkProp = new CheckBoxProp("systemId");
    reqParser.setCheckBoxProp(checkProp);
    reqParser.transData(request);
    String ou = (String) request.getAttribute(WebAttrConstant.ALL_ROLE_OPTION);
    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
    //String org = user.getOrganizationId();    
    String comp = user.getCompanyCode();
    String attribute1 = reqParser.getParameter("attribute1");
%>

<form method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("部门物资信息维护")
</script>
<input type="hidden" name="matchType" value="11">
<table width="100%" border="1" class="queryHeadBg">
    <tr>
        <td width="10%" align="right">是否有效：</td>
        <td width="10%" align="left"><select name="disable" style="width:80%"><option value="">请选择</option><option value="Y">是</option><option value="N">否</option>
        </select></td>
        <td width="10%" align="right">实物名称：</td>
        <td width="15%" align="left"><input name="itemName" style="width:80%" type="text"
                                            value="<%=reqParser.getParameter("itemName")%>"></td>
        <td width="10%" align="right">实物型号：</td>
        <td width="15%" align="left"><input name="itemSpec" style="width:80%" type="text"
                                            value="<%=reqParser.getParameter("itemSpec")%>"></td>
        <td align="center" valign="top"width="35%"><img src="/images/button/query.gif" style="cursor:'hand'"
                                                         onclick="do_search();" alt="查询">
            <img src="/images/button/batchenable.gif" style="cursor:'hand'" onclick="do_efficient();" alt="批量生效">

        </td>
    </tr>
    <tr>
        <td width="10%" align="right">实物条码：</td>
        <td width="10%" align="left"><input name="barcode1" style="width:80%" type="text"
                                            value="<%=reqParser.getParameter("barcode1")%>"></td>
        <td width="10%" align="right">部门名称：</td>
        <td width="15%" align="left"><input name="responsibilityDeptName" style="width:80%" type="text"
                                            value="<%=reqParser.getParameter("responsibilityDeptName")%>"></td>
        <td width="10%" align="right">地点：</td>
        <td width="15%" align="left"><input name="workorderObjectName" style="width:80%" type="text"
                                            value="<%=reqParser.getParameter("workorderObjectName")%>"></td>
        <td align="center"width="35%"><img src="/images/button/ok.gif" style="cursor:'hand'" onclick="do_match();" alt="确认">
            <img src="/images/button/batchdisable.gif" style="cursor:'hand'" onclick="do_disabled();" alt="批量失效">
             <img src="/images/button/export.gif" style="cursor:'hand'" onclick="do_export();" alt="导出">
            </td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=reqParser.getParameter("act")%>">
<legend align="left">
    统一设置:
    <input type="checkbox" name="allDept" id="allDept"><label for="allDept">使用状态</label>
    <input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
    <input type="checkbox" name="allUser" id="allMUser"><label for="allUser">新使用人</label>
    <input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
</legend>


<div id="headDiv" style="overflow:hidden;position:absolute;top:100px;left:1px;width:800px">
    <table width="350%" align="left" border="1" cellpadding="2" cellspacing="0" class="headerTable">
        <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
        <tr>
            <td width="1%" align="center">
                <input type="checkBox" name="controlBox" class="headCheckbox" onClick="checkAll(this.name, 'systemId')">
            </td>
            <td height="22" width="3%" align="center">一级分类</td>
            <td height="22" width="3%" align="center">二级分类</td>
            <td height="22" width="3%" align="center">是否有效</td>
            <td height="22" width="5%" align="center">物资条码</td>
            <td height="22" width="5%" align="center">物资名称</td>
            <td height="22" width="5%" align="center">物资型号</td>
            <td height="22" width="3%" align="center">责任人</td>
            <td height="22" width="3%" align="center">使用人</td>
            <td height="22" width="5%" align="center">责任部门</td>
            <td height="22" width="5%" align="center">地点</td>
            <td height="22" width="3%" align="center">使用状态</td>
            <td height="22" width="3%" align="center">新责任人</td>
            <td height="22" width="3%" align="center">新使用人</td>
            <td height="22" width="5%" align="center">新地点</td>
            <td height="22" width="3%" align="center">机密等级</td>
            <td height="22" width="3%" align="center">可用等级</td>
            <td height="22" width="3%" align="center">完整等级</td>
            <td height="22" width="3%" align="center">承载系统</td>
            <td height="22" width="3%" align="center">内存</td>
            <td height="22" width="3%" align="center">CPU</td>
            <td height="22" width="3%" align="center">IP地址</td>
            <td height="22" width="3%" align="center">硬盘信息</td>
            <td height="22" width="3%" align="center">操作系统</td>
            <td height="22" width="3%" align="center">托管类型</td>
            <td height="22" width="3%" align="center">软件版本</td>
        </tr>

    </table>
</div>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div id="dataDiv" style="overflow:scroll;height:72%;width:878px;position:absolute;top:122px;left:1px" align="left"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table width="350%" id="dataT" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onClick="executeClick(this)">
            <td width="1%" align="center"><input type="checkbox" name="systemId" id="systemId<%=i%>"
                                                 value="<%=row.getStrValue("ITEM_ID")%>;<%=i%>">
                <input type="hidden" name="barcode" value="<%=row.getValue("MSS_BARCODE")%>">

            </td>
            <td style="word-wrap:break-word" height="22" width="3%"
                align="center"><%=row.getValue("ITEM_CATEGORY1")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="3%"
                align="center"><%=row.getValue("ITEM_CATEGORY2")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="3%"
                align="center"><%=row.getValue("ENABLED")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="5%"
                align="center"><%=row.getValue("MSS_BARCODE")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="5%"
                align="center"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="5%"
                align="center"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="3%"
                align="center"><%=row.getValue("RESPONSIBILITY_USER")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="3%"
                align="center"><%=row.getValue("MAINTAIN_USER")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="5%"
                align="center"><%=row.getValue("RESPONSIBILITY_DEPT")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="5%"
                align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
            <td style="word-wrap:break-word" height="22" width="3%"
                align="center" onclick=""><input height="42" type="text" style="width:100%"
                                                                  name="newResponsibilityDeptName"
                                                                  id="newResponsibilityDeptName<%=i%>" value="<%=row.getValue("ITEM_STATUS")%>"><input
                    name="newResponsibilityDept" type="hidden" id="newResponsibilityDept<%=i%>" value="">
            </td>
            <td height="22" width="3%"
                align="center" onclick="select_user(this)"><input type="text" style="width:100%"
                                                                  name="newResponsibilityUserName"
                                                                  id="newResponsibilityUserName<%=i%>" value=""><input
                    name="newResponsibilityUser" type="hidden" id="newResponsibilityUser<%=i%>" value="">
            </td>
            <td height="22" width="3%"
                align="center"><input type="text" style="width:100%" name="newMaintainUser" id="newMaintainUser<%=i%>"
                                      value="">
            </td>
            <td height="22" width="5%"
                align="center" onclick="select_address(this)"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value=""><input name="newAddressId" type="hidden"
                                                                                     id="newAddressId<%=i%>" value="">
            </td>
               <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("SECURE_LEVEL")%>">
            </td>
            <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("USER_LEVEL")%>">
            </td>
            <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("COMPLETENESS_LEVEL")%>">
            </td>
            <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("USE_BY_SYSTEM")%>">
            </td>
            <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("MEMORY")%>">
            </td>
            <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("CPU")%>">
            </td>  <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("IP_ADDRESS")%>">
            </td>  <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("DISK_INFORMATION")%>">
            </td> <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("SYSTEM_NAME")%>">
            </td> <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("TRUSTEESHIP_TYPE")%>">
            </td> <td height="22" width="3%"
                align="center"><input type="text" style="width:100%"
                                                                     name="newAddressName" id="newAddressName<%=i%>"
                                                                     value="<%=row.getValue("UPDATE_VERSION")%>">
            </td>

        </tr>
        <%
                }
            }
        %>
    </table>
</div>

</form>
 <%if (rows != null && !rows.isEmpty()) {%>	
<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<% }%>

<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
function do_choose(obj) {
    var targetAction = "/match/rentEntity.jsp";
    var winstyle = "dialogWidth:20.1;dialogHeight:10.8;center:yes;status:no";
    var result = window.showModalDialog(targetAction, null, winstyle);
    if (result) obj.value = result;
}
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet";
    mainFrm.submit();
}
function select_dept(obj) {
    var allDeptChk = mainFrm.allDept

    var objName = obj.childNodes[0].name;
    var objId = obj.childNodes[0].id;
    var idNumber = objId.substring(objName.length);
    var upName = "<%=LookUpConstant.LOOK_UP_CHAN_MIS_DEPT%>";
    var dialogWidth = 50;
    var dialogHeight = 30;

    var userPara = "companyCode=" + <%=comp%>;

    var users = getLookUpValues(upName, dialogWidth, dialogHeight, userPara);
    if (allDeptChk.checked) {
        var tab = document.getElementById("dataT");
        var dataCount = tab.rows.length;
        for (var i = 0; i < dataCount; i++) {
            var chk = document.getElementById("systemId" + i);
            if (chk.checked) {
                document.getElementById("newResponsibilityDeptName" + i).value = users[0].deptName;
                document.getElementById("newResponsibilityDept" + i).value = users[0].deptCode;
            }
        }


    } else {
        if (users) {
            obj.childNodes[0].value = users[0].deptName;
            obj.childNodes[1].value = users[0].deptCode;
        } else {
            obj.childNodes[0].value = "";
            obj.childNodes[1].value = "";
        }
    }


}
function select_user(obj) {

    var objName = obj.childNodes[0].name;
    var objId = obj.childNodes[0].id;
    var idNumber = objId.substring(objName.length);
    var upName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_USER%>";
    var dialogWidth = 50;
    var dialogHeight = 30;

    var userPara = "deptCode=" + 577;
//    if (document.getElementById("newResponsibilityDept" + idNumber).value == '') {
//        alert("请先选择新责任部门，然后选责任人！");
//        return;
//    }
    var users = getLookUpValues(upName, dialogWidth, dialogHeight, userPara);
    var alluse = mainFrm.allUser
    if (alluse.checked) {
        var tab = document.getElementById("dataT");
        var dataCount = tab.rows.length;
        for (var i = 0; i < dataCount; i++) {
            var chk = document.getElementById("systemId" + i);
            if (chk.checked) {
                document.getElementById("newResponsibilityUserName" + i).value = users[0].userName;
                document.getElementById("newResponsibilityUser" + i).value = users[0].employeeId;
            }
        }
    } else {
        if (users) {
            obj.childNodes[0].value = users[0].userName;
            obj.childNodes[1].value = users[0].employeeId;
        } else {
            obj.childNodes[0].value = "";
            obj.childNodes[1].value = "";
        }
    }

}
function select_address(obj) {
    var objName = obj.childNodes[0].name;
    var objId = obj.childNodes[0].id;
    var idNumber = objId.substring(objName.length);
    var upName = "<%=LookUpConstant.LOOK_UP_ITEM_ADDRES%>";
    var dialogWidth = 50;
    var dialogHeight = 30;

    var userPara = "organizationId=" + <%=String.valueOf(user.getOrganizationId())%>;

    var users = getLookUpValues(upName, dialogWidth, dialogHeight, userPara);
    var alladdr = mainFrm.allLocation
    if (alladdr.checked) {
        var tab = document.getElementById("dataT");
        var dataCount = tab.rows.length;
        for (var i = 0; i < dataCount; i++) {
            var chk = document.getElementById("systemId" + i);
            if (chk.checked) {
                document.getElementById("newAddressName" + i).value = users[0].houseAddress;
                document.getElementById("newAddressId" + i).value = users[0].addressId;
            }
        }
    } else {
        if (users) {
            obj.childNodes[0].value = users[0].houseAddress;
            obj.childNodes[1].value = users[0].addressId;
        } else {
            obj.childNodes[0].value = "";
            obj.childNodes[1].value = "";
        }
    }
}
function do_match() {


    var checkedCount = getCheckedBoxCount("systemId");
    if (checkedCount < 1) {
        alert("请至少选择一条数据！");
        return;
    } else {
        var org =
    <%=String.valueOf(user.getOrganizationId())%>
        var obj = document.getElementById("dataT");
        var type = ""          ;

        for (var i = 0; i < obj.rows.length; i++) {
            var chk = document.getElementById("systemId" + i);
            if (chk.checked) {
//                if (document.getElementById("financeProp" + i).value == "ASSETS") {
//                    alert("财务属性为资产的不能直接变更信息，必须通过调拨流程，请确认！");
//                    return;
//                    break;
//
//                }
//                if (document.getElementById("newResponsibilityDeptName" + i).value == "" && document.getElementById("newResponsibilityUserName" + i).value == "" && document.getElementById("newMaintainUser" + i).value == "" && document.getElementById("newAddressName" + i).value == "") {
//                    alert("没有更改实物的任何信息，请确认！");
//                    return;
//                    break;
//                }
//                if (document.getElementById("newResponsibilityDeptName" + i).value != "" && document.getElementById("newResponsibilityUserName" + i).value == "") {
//                    alert("已经变更了实物部门信息，必须选择新责任人，请确认！");
//                    return;
//                    break;
//                }
            }
        }
        if (confirm("确认需要更换实物的信息？确认请选择“确定”按钮，否定请点击“取消”按钮")) {
            mainFrm.act.value = "<%=AMSActionConstant.MATCH_ACTION%>";
            mainFrm.action = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet";
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
function do_drop(){
  var itemCategory = document.getElementById("financeProp")   ;
              dropSpecialOption(itemCategory, 'DZYH;YQYB;SPARE');
}
function do_efficient(){             //批量生效
    var checkedCount = getCheckedBoxCount("systemId");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
        } else {
        if(confirm(ENABLE_MSG))  {
            mainFrm.act.value = "ENABLE";
//            mainFrm.action = "/servlet/com.sino.ams.system.switches.servlet.EtsObjectServlet";
            mainFrm.submit();
            }
       }
}
function do_disabled(){             //批量生效
    var checkedCount = getCheckedBoxCount("systemId");
    if (checkedCount < 1) {
         alert("请至少选择一行数据！");
         return;
        } else {
        if(confirm(DISABLE_MSG))  {
            mainFrm.act.value = "DISABLE";
//            mainFrm.action = "/servlet/com.sino.ams.system.switches.servlet.EtsObjectServlet";
            mainFrm.submit();
            }
       }
}
function do_export(){
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
            mainFrm.submit();
}
</script>